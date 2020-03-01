package cmd.validation;

import cmd.exception.validation.InputMalformedException;
import cmd.model.Direction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TreasureInputDataValidation implements InputDataValidation {

  @Override
  public void validate(List<String> lines) {
    validateIsTrue(lines.size(), size -> size > 3, "Input should consist of at least 4 lines!");
    validateIsTrue(
        lines.get(0), this::validateIsNumeric, "Line 1 - expected number of ways to travel!");

    int numberOfTravels = Integer.parseInt(lines.get(0));
    validateTravelApproximations(numberOfTravels, lines);

    int nextIndex = 1 + numberOfTravels;
    String message = "Line " + (nextIndex + 1) + " - expected number of directions!";
    validateIsTrue(nextIndex, size -> lines.size() > size, message);
    validateIsTrue(lines.get(nextIndex), this::validateIsNumeric, message);

    Map<String, Boolean> travelsSeen = new HashMap<>();
    for (int i = 1; i <= numberOfTravels; i++) {
      String line = lines.get(i);
      travelsSeen.put(line.substring(0, line.indexOf(",")), true);
    }
    int numberOfDirections = Integer.parseInt(lines.get(nextIndex));
    validateDirections(numberOfDirections, nextIndex, lines, travelsSeen);
  }

  private void validateTravelApproximations(int ways, List<String> lines) {
    Map<String, Boolean> travelsSeen = new HashMap<>();

    for (int i = 1; i <= ways; i++) {
      String prefix = "Line " + (i + 1);
      validateIsTrue(i, index -> lines.size() > index, prefix + " must be a travel approximation!");

      String[] parts = lines.get(i).split(" *, *");
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

  private void validateDirections(
      int numberOfDirections, int startFrom, List<String> lines, Map<String, Boolean> travelsSeen) {

    for (int i = 1; i <= numberOfDirections; i++) {
      int currentIndex = startFrom + i;
      String prefix = "Line " + (currentIndex + 1);
      validateIsTrue(
          currentIndex, index -> lines.size() > index, prefix + " - must be a direction!");

      String[] parts = lines.get(currentIndex).split(" *, *");
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
          prefix
              + " - valid directions are: "
              + Arrays.stream(Direction.values()).map(Enum::name).collect(Collectors.joining(",")));
    }
  }

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

  private void validate1PartTime(String[] parts, String exceptionMsg) {
    validateIsTrue(parts[0], this::validateIsNumeric, exceptionMsg);

    int daysHoursMinutes = Integer.parseInt(parts[0]);
    validateIsTrue(daysHoursMinutes, part -> part > 0, exceptionMsg);
    String daysHoursMinutesText = daysHoursMinutes > 1 ? "^(days|hours|mins)$" : "^(day|hour|min)$";

    validateIsTrue(parts[1], thePart -> thePart.matches(daysHoursMinutesText), exceptionMsg);
  }

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

  private <T> void validateIsTrue(T string, Function<T, Boolean> condition, String exceptionMsg) {
    if (!condition.apply(string)) {
      throw new InputMalformedException(exceptionMsg);
    }
  }

  private boolean validateIsCompassDirection(String direction) {
    try {
      Direction.valueOf(direction);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private boolean validateIsNumberAndMph(String string) {
    int lengthOfString = string.length();

    String measure = string.substring(lengthOfString - 3);
    if (!measure.equals("mph")) {
      return false;
    }

    return validateIsNumeric(string.substring(0, lengthOfString - 3));
  }

  private boolean validateIsText(String string) {
    for (int i = 0; i < string.length(); i++) {
      char character = string.charAt(i);
      if (!(Character.isLetter(character) || Character.isSpaceChar(character))) {
        return false;
      }
    }
    return true;
  }

  private boolean validateIsNumeric(String string) {
    try {
      Integer.parseInt(string);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
