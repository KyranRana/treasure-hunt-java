package cmd.exception.validation;

/**
 * Input validation exception thrown when input is malformed.
 *
 * @author kyranrana
 */
public class InputMalformedException extends RuntimeException {
  public InputMalformedException(String message) {
    super(message);
  }
}
