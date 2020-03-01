package cmd.model.builder;

import cmd.model.Time;

public class TimeBuilder {

  public static Time buildFromString(String time) {
    String[] timeParts = time.split(" +");

    int tmpDays = 0;
    int tmpHours = 0;
    int tempMinutes = 0;

    if (timeParts.length == 6) {
      tmpDays = Integer.parseInt(timeParts[0]);
      tmpHours = Integer.parseInt(timeParts[2]);
      tempMinutes = Integer.parseInt(timeParts[4]);
    } else if (timeParts.length == 4) {
      if (timeParts[1].startsWith("day")) {
        tmpDays = Integer.parseInt(timeParts[0]);
      } else {
        tmpHours = Integer.parseInt(timeParts[0]);
      }

      if (timeParts[3].startsWith("hour")) {
        tmpHours = Integer.parseInt(timeParts[2]);
      } else {
        tempMinutes = Integer.parseInt(timeParts[2]);
      }
    } else if (timeParts.length == 2) {
      if (timeParts[1].startsWith("day")) {
        tmpDays = Integer.parseInt(timeParts[0]);
      } else if (timeParts[1].startsWith("hour")) {
        tmpHours = Integer.parseInt(timeParts[0]);
      } else {
        tempMinutes = Integer.parseInt(timeParts[0]);
      }
    }

    return new Time(tmpDays, tmpHours, tempMinutes);
  }
}
