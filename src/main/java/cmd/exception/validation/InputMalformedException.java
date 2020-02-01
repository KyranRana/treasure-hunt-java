package cmd.exception.validation;

public class InputMalformedException extends RuntimeException {

  public InputMalformedException(String message) {
    super(message);
  }
}
