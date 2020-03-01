package cmd.validation;

import cmd.exception.validation.InputMalformedException;
import java.util.List;

public interface InputDataValidation {

  void validate(List<String> lines) throws InputMalformedException;
}
