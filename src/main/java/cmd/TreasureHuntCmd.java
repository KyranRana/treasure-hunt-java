package cmd;

import cmd.locator.DefaultTreasureLocator;
import cmd.model.Dimension;
import cmd.model.Location;
import cmd.util.FileUtil;
import cmd.validation.DefaultInputDataValidation;
import cmd.validation.DefaultInputFileValidation;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.javatuples.Triplet;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Treasure hunt command.
 *
 * <p>This program utilises a package called Picoli which is a mini command line framework. This
 * framework makes it easy to create and manage commands through the use of native Java classes and
 * annotations. You can find out more about Picocli here: https://picocli.info/
 *
 * @author kyranrana
 */
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

  @Option(
      names = {"-v", "--verbose"},
      description = "True to enable verbose mode")
  private boolean verbose;

  /**
   * Main entry point. Will take args provided by the user and pass them through to the Picocli
   * {@link CommandLine} framework, this framework will validate mandatory arguments have been
   * provided and finally mutate all annotated properties with their respective values. Once this is
   * done {@link TreasureHuntCmd#call()} will be invoked.
   *
   * @see TreasureHuntCmd#call() for business logic.
   * @param args The raw command line arguments.
   */
  public static void main(String[] args) {
    int exitCode =
        new CommandLine(new TreasureHuntCmd())
            .setExecutionExceptionHandler(new TreasureHuntCmdExceptionHandler())
            .execute(args);

    System.exit(exitCode);
  }

  /**
   * Handles business logic for {@link TreasureHuntCmd}. This involves:
   *
   * <p>1. Validates input file exists, is accessible, and is readable.
   *
   * <p>2. Validates input file provides data required by the command.
   *
   * <p>3. Extracts travels and directions from input and locates how far the location of the
   * treasure is from origin.
   *
   * @return The exit code.
   */
  @Override
  public Integer call() throws Exception {
    (new DefaultInputFileValidation()).validate(textFile);

    String[] linesInFile = FileUtil.readFile(textFile).split(System.lineSeparator());
    (new DefaultInputDataValidation()).validate(linesInFile);

    Map<String, Integer> travels = getTravels(linesInFile);

    if (verbose) {
      System.out.println();
    }

    List<Triplet<String, String, String>> directions = getDirections(linesInFile, travels.size());
    Location treasureLocation = (new DefaultTreasureLocator()).locate(travels, directions);

    Dimension dimensionX = treasureLocation.getX();
    Dimension dimensionY = treasureLocation.getY();

    if (verbose) {
      System.out.println();
      System.out.println("-- X dimension: " + dimensionX);
      System.out.println("-- Y dimension: " + dimensionY);
      System.out.println();
    }

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

  /**
   * Get travels.
   *
   * @param linesInFile Lines in file.
   * @return Travels.
   */
  private Map<String, Integer> getTravels(String[] linesInFile) {
    Map<String, Integer> travels = new HashMap<>();

    int numberOfTravels = Integer.parseInt(linesInFile[0]);
    for (int i = 1; i <= numberOfTravels; i++) {
      String[] travelAndSpeed = linesInFile[i].split(" *, *");

      int speed = Integer.parseInt(travelAndSpeed[1].replaceFirst("mph", ""));
      travels.put(travelAndSpeed[0], speed);

      if (verbose) {
        System.out.println("-- Noted travel: " + linesInFile[i]);
      }
    }
    return travels;
  }

  /**
   * Get directions.
   *
   * @param linesInFile Lines in file.
   * @return Directions.
   */
  private List<Triplet<String, String, String>> getDirections(
      String[] linesInFile, int numberOfTravels) {

    List<Triplet<String, String, String>> directions = new ArrayList<>();

    int numberOfDirections = Integer.parseInt(linesInFile[1 + numberOfTravels]);
    for (int i = 0; i < numberOfDirections; i++) {
      int currentIndex = i + numberOfTravels + 2;

      String[] direction = linesInFile[currentIndex].split(" *, *");
      directions.add(new Triplet<>(direction[0], direction[1], direction[2]));

      if (verbose) {
        System.out.println("-- Noted step: " + linesInFile[currentIndex]);
      }
    }
    return directions;
  }
}
