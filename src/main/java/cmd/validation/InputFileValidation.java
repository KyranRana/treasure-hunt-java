package cmd.validation;

import java.io.File;
import java.io.IOException;

public interface InputFileValidation {

  void validate(File inputFile) throws IOException;
}
