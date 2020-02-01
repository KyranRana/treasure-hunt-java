package cmd.validation;

import cmd.exception.validation.InputMalformedException;

/**
 * Contract for input data validation class.
 *
 * @author kyranrana
 */
public interface InputDataValidation {

  /**
   * Validates data in file on a line-by-line basis complies with instructions below:
   *
   * <ul>
   *   <li>Number of ways to travel
   *   <li>Approximations on speeds for different ways of travel
   *   <li>Number of directions.
   *   <li>Directions to take to locate the treasure from origin
   * </ul>
   *
   * @param lines Lines.
   */
  void validate(String[] lines) throws InputMalformedException;
}
