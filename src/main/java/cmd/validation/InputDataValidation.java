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
   *   <li>Number of ways to travel (format: {integer}).
   *   <li>Approximations on speeds for different ways of travel (format: {string},{integer}mph).
   *   <li>Number of directions (format: {integer}).
   *   <li>Directions to take to locate the treasure from origin (format:
   *       {string},{Time},{Direction})
   * </ul>
   *
   * <p>Where:
   *
   * <ul>
   *   <li>{Time} = One of
   *       <ul>
   *         <li>{integer} days [{integer} hours [{integer} mins]]
   *         <li>{integer} hours [{integer} mins]
   *         <li>{integer} mins
   *       </ul>
   *   <li>{Direction} = One of: NS,E,W,NW,NE,SW,SE
   * </ul>
   *
   * <p>Also checks directions do not contain an unspecified travel method.
   *
   * @param lines Lines.
   * @throws InputMalformedException if data doesn't comply with above structure.
   */
  void validate(String[] lines) throws InputMalformedException;
}
