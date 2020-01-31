package cmd.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import org.junit.Test;

public class FileUtilIntegrationTest {

  private static final String TEST_DIR_PREFIX = "util/fileutil";

  /**
   * Scenario
   *
   * <p>Given text file does not exist.
   *
   * <p>When {@link FileUtil#readFile(File)} is called.
   *
   * <p>Then {@link IOException} should be thrown.
   */
  @Test(expected = IOException.class)
  public void readFile_whereFileDontExists() throws Exception {
    FileUtil.readFile(new File("some/wrong/path/nope.txt"));
  }

  /**
   * Scenario
   *
   * <p>Given text file does exist
   *
   * <p>When {@link FileUtil#readFile(File)} is called
   *
   * <p>Then file data should be returned.
   */
  @Test
  public void readFile_whereFileDoesExist() throws Exception {
    ClassLoader classLoader = getClass().getClassLoader();

    URL testFileURL = classLoader.getResource(TEST_DIR_PREFIX + "/read-file-test.txt");
    String fileURL = Objects.requireNonNull(testFileURL).getFile();

    assertEquals("This file does have content.", FileUtil.readFile(new File(fileURL)));
  }

}
