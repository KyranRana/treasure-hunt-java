package cmd;

import cmd.model.Location;
import cmd.model.builder.TreasureInputDataBuilder;
import cmd.treasurelocator.DefaultTreasureLocator;
import cmd.util.FileUtil;
import cmd.validation.DefaultInputFileValidation;
import cmd.validation.TreasureInputDataValidation;
import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(
    name = "treasurehunt",
    mixinStandardHelpOptions = true,
    version = "treasurehunt 1.0",
    description =
        "Given an input file specifying:"
            + "\n- Number of ways to travel\n\t(format: {integer})."
            + "\n- Approximations on speeds for different ways of travel\n\t(format: {string},{integer}mph)."
            + "\n- Number of directions\n\t(format: {integer})."
            + "\n- Directions to take to locate the treasure from origin\n\t(format: {string},{Time},{Direction})"
            + "\n"
            + "\nWhere custom data types:"
            + "\n{Time} = One of:"
            + "\n\t{integer} days [{integer} hours [{integer} mins]],"
            + "\n\t{integer} hours [{integer} mins],"
            + "\n\t{integer} mins"
            + "\n{Direction} = One of: N,S,E,W,NW,NE,SW,SE"
            + "\n"
            + "\nOutputs how far treasure location is from origin.")
public class TreasureHuntCmd implements Callable<Integer> {

  @Parameters(index = "0", description = "The input file.")
  private File textFile;

  public static void main(String[] args) {
    System.exit(
        new CommandLine(new TreasureHuntCmd())
            .setExecutionExceptionHandler(new TreasureHuntCmdExceptionHandler())
            .execute(args));
  }

  @Override
  public Integer call() throws Exception {
    (new DefaultInputFileValidation()).validate(textFile);

    List<String> linesInFile = FileUtil.readFileLineByLine(textFile);
    (new TreasureInputDataValidation()).validate(linesInFile);

    Location treasureLocation =
        (new DefaultTreasureLocator()).locate(TreasureInputDataBuilder.buildFromLines(linesInFile));

    double x = treasureLocation.getX().toDecimal();
    double y = treasureLocation.getY().toDecimal();

    if (y == 0 && x == 0) {
      System.out.println("The treasure is beneath you!");
    } else {
      if (y >= 0) {
        System.out.printf("%.2f miles to the south\n", y);
      } else {
        System.out.printf("%.2f miles to the north\n", Math.abs(y));
      }

      if (x >= 0) {
        System.out.printf("%.2f miles to the east\n", x);
      } else {
        System.out.printf("%.2f miles to the west\n", Math.abs(y));
      }
    }

    return 1;
  }
}
