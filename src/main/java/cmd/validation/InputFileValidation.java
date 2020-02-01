package cmd.validation;

import java.io.File;
import java.io.IOException;

/**
 * Contract for input file validation class.
 *
 * @author kyranrana
 */
public interface InputFileValidation {

  /**
   * Validates file exists, is accessible, and is a text file.
   *
   * @param inputFile Input file.
   */
  void validate(File inputFile) throws IOException;
}
