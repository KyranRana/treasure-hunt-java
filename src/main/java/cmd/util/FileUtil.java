package cmd.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * File utility class.
 *
 * @author kyranrana
 */
public class FileUtil {

  /**
   * Retrieves all data from {@link File}.
   *
   * @param file {@link File}
   * @return Data.
   * @throws IOException If problem occurs reading {@link File}.
   */
  public static String readFile(File file) throws IOException {
    return new String(Files.readAllBytes(file.toPath()));
  }
}