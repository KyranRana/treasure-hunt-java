package cmd.validation;

import cmd.exception.validation.FileNotReadableException;
import cmd.exception.validation.FileNotTextFileException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import org.junit.Test;

public class DefaultInputFileValidationIntegrationTest {

  private static final String TEST_DIR_PREFIX = "validation/defaultinputfilevalidation";

  private final InputFileValidation inputFileValidation;
  private final String testDirPrefix;

  public DefaultInputFileValidationIntegrationTest() {
    inputFileValidation = new DefaultInputFileValidation();

    testDirPrefix =
        Objects.requireNonNull(getClass().getClassLoader().getResource(TEST_DIR_PREFIX)).getFile();
  }

  @Test(expected = FileNotFoundException.class)
  public void validate_whereFileIsNotFound() throws Exception {
    inputFileValidation.validate(new File("nope.txt"));
  }

  @Test(expected = FileNotReadableException.class)
  public void validate_whereFileIsNotReadable() throws Exception {
    inputFileValidation.validate(new File(testDirPrefix + "/file-not-readable-test.txt"));
  }

  @Test(expected = FileNotTextFileException.class)
  public void validate_whereFileIsNotTextFile() throws Exception {
    inputFileValidation.validate(new File(testDirPrefix + "/file-not-text-file-test.pdf"));
  }

  @Test
  public void validate_isOk() throws Exception {
    inputFileValidation.validate(new File(testDirPrefix + "/file-ok-test.txt"));
  }
}
