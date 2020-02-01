package cmd.validation;

import cmd.exception.validation.FileNotReadableException;
import cmd.exception.validation.FileNotTextFileException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;

/**
 * Default input file validation class.
 *
 * @author kyranrana
 */
public class DefaultInputFileValidation implements InputFileValidation {

  @Override
  public void validate(File inputFile) throws IOException {
    String absolutePath = inputFile.toPath().toString();

    if (!inputFile.exists())
      throw new FileNotFoundException(absolutePath);

    if (!inputFile.canRead())
      throw new FileNotReadableException(absolutePath);

    if (!URLConnection.getFileNameMap().getContentTypeFor(inputFile.getName()).equals("text/plain"))
      throw new FileNotTextFileException(absolutePath);
  }
}
