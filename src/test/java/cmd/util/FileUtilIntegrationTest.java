package cmd.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import org.junit.Test;

public class FileUtilIntegrationTest {

  @Test(expected = IOException.class)
  public void readFile_whereFileDontExists() throws Exception {
    FileUtil.readFileLineByLine(new File("some/wrong/path/nope.txt"));
  }

  @Test
  public void readFileLineByLine_whereFileDoesExist() throws Exception {
    ClassLoader classLoader = getClass().getClassLoader();

    URL testFileURL = classLoader.getResource("util/fileutil/read-file-line-by-line-test.txt");
    String fileURL = Objects.requireNonNull(testFileURL).getFile();

    assertEquals(
        List.of("This", "is", "a", "simple", "test"),
        FileUtil.readFileLineByLine(new File(fileURL)));
  }
}
