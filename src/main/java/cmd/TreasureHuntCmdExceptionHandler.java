package cmd;

import java.util.Arrays;
import picocli.CommandLine;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.ParseResult;

/**
 * Custom execution exception handler for {@link TreasureHuntCmd}
 *
 * @author kyranrana
 */
public class TreasureHuntCmdExceptionHandler implements IExecutionExceptionHandler {

  /**
   * Handles business logic exceptions thrown from {@link TreasureHuntCmd#call()}
   *
   * @param ex - The exception
   * @param commandLine - The associated commandLine
   * @param parseResult - The associated parseResult
   * @return The exit code
   */
  public int handleExecutionException(Exception ex,
      CommandLine commandLine,
      ParseResult parseResult) {

    commandLine.getErr().println(ex.getMessage());

    return commandLine.getExitCodeExceptionMapper() != null
        ? commandLine.getExitCodeExceptionMapper().getExitCode(ex)
        : commandLine.getCommandSpec().exitCodeOnExecutionException();
  }
}