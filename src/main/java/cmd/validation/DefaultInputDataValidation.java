package cmd.validation;

import cmd.exception.validation.InputMalformedException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Default input data validation class.
 *
 * <p>Responsibilities
 *
 * <ul>
 *   <li>Checks data in file is syntactically correct following:
 *       <ul>
 *         <li>Number of ways to travel (format: {integer}).
 *         <li>Approximations on speeds for different ways of travel (format:
 *             {string},{integer}mph).
 *         <li>Number of directions (format: {integer}).
 *         <li>Directions to take to locate the treasure from origin (format:
 *             {string},{Time},{Direction})
 *       </ul>
 *       <p>Where:
 *       <ul>
 *         <li>{Time} = One of
 *             <ul>
 *               <li>{integer} days [{integer} hours [{integer} mins]]
 *               <li>{integer} hours [{integer} mins]
 *               <li>{integer} mins
 *             </ul>
 *         <li>{Direction} = One of: NS,E,W,NW,NE,SW,SE
 *       </ul>
 *   <li>Checks directions do not specify an unknown travel method.
 * </ul>
 *
 * @author kyranrana
 */
public class DefaultInputDataValidation implements InputDataValidation {

  @Override
  public void validate(String[] lines) {
    validateIsTrue(lines.length, size -> size > 3, "Input should consist of at least 4 lines!");
    validateIsTrue(
        lines[0], this::validateIsNumeric, "Line 1 - expected number of ways to travel!");

    int numberOfTravels = Integer.parseInt(lines[0]);
    validateTravelApproximations(numberOfTravels, lines);

    int nextIndex = 1 + numberOfTravels;
    String message = "Line " + nextIndex + " - expected number of directions!";
    validateIsTrue(nextIndex, size -> lines.length > size, message);
    validateIsTrue(lines[nextIndex], this::validateIsNumeric, message);

    Map<String, Boolean> travelsSeen = new HashMap<>();
    for (int i = 1; i <= numberOfTravels; i++) {
      String line = lines[i];
      travelsSeen.put(line.substring(0, line.indexOf(",")), true);
    }
    int numberOfDirections = Integer.parseInt(lines[nextIndex]);
    validateDirections(numberOfDirections, nextIndex, lines, travelsSeen);
  }

  /**
   * Validates lines 1...ways are valid travel approximations.
   *
   * @param ways Ways.
   * @param lines Lines.
   */
  private void validateTravelApproximations(int ways, String[] lines) {
    Map<String, Boolean> travelsSeen = new HashMap<>();

    for (int i = 1; i <= ways; i++) {
      String prefix = "Line " + i;
      validateIsTrue(i, index -> lines.length > index, prefix + " must be a travel approximation!");

      String[] parts = lines[i].split(" *, *");
      validateIsTrue(
          parts,
          theParts -> theParts.length == 2,
          prefix
              + "must be a travel approximation. Expected 2 parts but found "
              + parts.length
              + " parts");

      validateIsTrue(parts[0], this::validateIsText, prefix + " - type of travel should be text!");
      validateIsTrue(
          parts[0],
          part -> !travelsSeen.containsKey(part),
          prefix + " - type of travel should be unique!");

      validateIsTrue(
          parts[1],
          this::validateIsNumberAndMph,
          prefix + " - travel speed should be in format: {integer}mph");

      travelsSeen.put(parts[0], true);
    }
  }

  /**
   * Validates lines startFrom...(startFrom + numberOfDirection) are valid directions.
   *
   * @param numberOfDirections Number of directions.
   * @param startFrom Starting point.
   * @param lines Lines.
   * @param travelsSeen Travels.
   */
  private void validateDirections(
      int numberOfDirections, int startFrom, String[] lines, Map<String, Boolean> travelsSeen) {

    for (int i = 1; i <= numberOfDirections; i++) {
      int currentIndex = startFrom + i;
      String prefix = "Line " + currentIndex;
      validateIsTrue(currentIndex, index -> lines.length > index, prefix + " must be a direction!");

      String[] parts = lines[currentIndex].split(" *, *");
      validateIsTrue(
          parts,
          theParts -> theParts.length == 3,
          prefix + "must be a direction. Expected 3 parts but found " + parts.length + " parts");

      validateIsTrue(parts[0], this::validateIsText, prefix + " - type of travel should be text!");
      validateIsTrue(parts[0], travelsSeen::containsKey, prefix + " - type of travel not seen!");

      validateTime(prefix, parts[1]);
      validateIsTrue(
          parts[2],
          this::validateIsCompassDirection,
          prefix + " - valid directions are: NS,E,W,NW,NE,SW,SE");
    }
  }

  /**
   * Validates string is in either of the following formats:
   *
   * <ul>
   *   <li>{integer} days [{integer} hours [{integer} mins]]
   *   <li>{integer} hours [{integer} mins]
   *   <li>{integer} mins
   * </ul>
   *
   * @param prefix Prefix (used to show line)
   * @param string String
   */
  private void validateTime(String prefix, String string) {
    String[] parts = string.split(" +");
    String exceptionMsg = prefix + " - time is invalid!";

    if (parts.length == 6) {
      validate3PartTime(parts, exceptionMsg);
    } else if (parts.length == 4) {
      validate2PartTime(parts, exceptionMsg);
    } else if (parts.length == 2) {
      validate1PartTime(parts, exceptionMsg);
    } else {
      throw new InputMalformedException(exceptionMsg);
    }
  }

  /**
   * Validates time part complies with format: {integer} mins
   *
   * @param parts Parts
   * @param exceptionMsg Exception message in failure.
   */
  private void validate1PartTime(String[] parts, String exceptionMsg) {
    validateIsTrue(parts[0], this::validateIsNumeric, exceptionMsg);

    int daysHoursMinutes = Integer.parseInt(parts[0]);
    validateIsTrue(daysHoursMinutes, part -> part > 0, exceptionMsg);
    String daysHoursMinutesText = daysHoursMinutes > 1 ? "^(days|hours|mins)$" : "^(day|hour|min)$";

    validateIsTrue(parts[1], thePart -> thePart.matches(daysHoursMinutesText), exceptionMsg);
  }

  /**
   * Validates time parts complies with format: {integer} hours {integer} mins
   *
   * @param parts Parts
   * @param exceptionMsg Exception message in failure.
   */
  private void validate2PartTime(String[] parts, String exceptionMsg) {
    validateIsTrue(parts[0], this::validateIsNumeric, exceptionMsg);

    int daysOrHours = Integer.parseInt(parts[0]);
    validateIsTrue(daysOrHours, part -> part > 0, exceptionMsg);
    String daysOrHoursText = daysOrHours > 1 ? "^(days|hours)$" : "^(day|hour)$";

    validateIsTrue(parts[1], thePart -> thePart.matches(daysOrHoursText), exceptionMsg);

    validateIsTrue(parts[2], this::validateIsNumeric, exceptionMsg);

    int hoursOrMinutes = Integer.parseInt(parts[2]);
    validateIsTrue(hoursOrMinutes, part -> part > 0, exceptionMsg);
    String hoursOrMinutesText = hoursOrMinutes > 1 ? "^(hours|mins)$" : "^(hour|min)$";

    validateIsTrue(
        parts[3],
        thePart -> (thePart.matches(hoursOrMinutesText)) && !parts[1].equals(thePart),
        exceptionMsg);
  }

  /**
   * Validates time parts complies with format: {integer} days {integer} hours {integer} mins
   *
   * @param parts Parts
   * @param exceptionMsg Exception message in failure.
   */
  private void validate3PartTime(String[] parts, String exceptionMsg) {
    validateIsTrue(parts[0], this::validateIsNumeric, exceptionMsg);

    int days = Integer.parseInt(parts[0]);
    validateIsTrue(days, part -> part > 0, exceptionMsg);
    validateIsTrue(parts[1], thePart -> thePart.equals(days > 1 ? "days" : "day"), exceptionMsg);

    validateIsTrue(parts[2], this::validateIsNumeric, exceptionMsg);

    int hours = Integer.parseInt(parts[2]);
    validateIsTrue(hours, part -> part > 0, exceptionMsg);
    validateIsTrue(parts[3], thePart -> thePart.equals(hours > 1 ? "hours" : "hour"), exceptionMsg);

    validateIsTrue(parts[4], this::validateIsNumeric, exceptionMsg);

    int minutes = Integer.parseInt(parts[4]);
    validateIsTrue(minutes, part -> part > 0, exceptionMsg);
    validateIsTrue(parts[5], thePart -> thePart.equals(minutes > 1 ? "mins" : "min"), exceptionMsg);
  }

  /**
   * Validates string meets condition, otherwise throws exception message.
   *
   * @param string The string.
   * @param condition The condition.
   * @param exceptionMsg The exceptionMessage.
   */
  private <T> void validateIsTrue(T string, Function<T, Boolean> condition, String exceptionMsg) {
    if (!condition.apply(string)) {
      throw new InputMalformedException(exceptionMsg);
    }
  }

  /**
   * Validates string is a valid compass direction.
   *
   * @param string String
   */
  private boolean validateIsCompassDirection(String string) {
    return string.equals("N")
        || string.equals("NS")
        || string.equals("E")
        || string.equals("W")
        || string.equals("NW")
        || string.equals("NE")
        || string.equals("SW")
        || string.equals("SE");
  }

  /**
   * Validates string is in format: {integer}mph
   *
   * @param string The string.
   * @return True if string is in format: {integer}mph
   */
  private boolean validateIsNumberAndMph(String string) {
    int lengthOfString = string.length();

    String measure = string.substring(lengthOfString - 3);
    if (!measure.equals("mph")) {
      return false;
    }

    return validateIsNumeric(string.substring(0, lengthOfString - 3));
  }

  /**
   * Validates string consists of letters.
   *
   * @param string The string.
   * @return TRUE if string is text.
   */
  private boolean validateIsText(String string) {
    for (int i = 0; i < string.length(); i++) {
      char character = string.charAt(i);
      if (!(Character.isLetter(character) || Character.isSpaceChar(character))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Validates string is numeric.
   *
   * @param string The string.
   * @return TRUE if string is numeric.
   */
  private boolean validateIsNumeric(String string) {
    try {
      Integer.parseInt(string);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
