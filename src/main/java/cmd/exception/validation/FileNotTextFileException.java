package cmd.exception.validation;

import java.io.IOException;

public class FileNotTextFileException extends IOException {

  public FileNotTextFileException(String message) {
    super(message);
  }
}
