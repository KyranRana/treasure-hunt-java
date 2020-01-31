package cmd.validation;

import cmd.exception.validation.FileNotReadableException;
import cmd.exception.validation.FileNotTextFileException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;
import org.junit.Test;

/**
 * Unit tests for {@link DefaultInputFileValidation}.
 *
 * @author kyranrana
 */
public class DefaultInputFileValidationIntegrationTest {

  private static final String TEST_DIR_PREFIX = "validation/defaultinputfilevalidation";

  private InputFileValidation inputFileValidation;

  public DefaultInputFileValidationIntegrationTest() {
    inputFileValidation = new DefaultInputFileValidation();
  }

  /**
   * Scenario.
   *
   * <p>Given text file does not exist.
   *
   * <p>When {@link DefaultInputFileValidation#validate(File)} is called
   *
   * <p>Then expect {@link FileNotFoundException}
   */
  @Test(expected = FileNotFoundException.class)
  public void validate_whereFileIsNotFound() throws Exception {
    inputFileValidation.validate(new File("nope.txt"));
  }

  /**
   * Scenario.
   *
   * <p>Given text file is not readable.
   *
   * <p>When {@link DefaultInputFileValidation#validate(File)} is called
   *
   * <p>Then expect {@link FileNotReadableException}
   */
  @Test(expected = FileNotReadableException.class)
  public void validate_whereFileIsNotReadable() throws Exception {
    ClassLoader classLoader = getClass().getClassLoader();

    URL testFileURL = classLoader.getResource(TEST_DIR_PREFIX);
    String fileURL = Objects.requireNonNull(testFileURL).getFile();

    inputFileValidation.validate(new File(fileURL + "/file-not-readable-test.txt"));
  }

  /**
   * Scenario.
   *
   * <p>Given text file is NOT a text file.
   *
   * <p>When {@link DefaultInputFileValidation#validate(File)} is called
   *
   * <p>Then expect {@link FileNotTextFileException}
   */
  @Test(expected = FileNotTextFileException.class)
  public void validate_whereFileIsNotTextFile() throws Exception {
    ClassLoader classLoader = getClass().getClassLoader();

    URL testFileURL = classLoader.getResource(TEST_DIR_PREFIX);
    String fileURL = Objects.requireNonNull(testFileURL).getFile();

    inputFileValidation.validate(new File(fileURL + "/file-not-text-file.pdf"));
  }
}
