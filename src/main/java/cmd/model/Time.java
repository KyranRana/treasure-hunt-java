package cmd.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Time {

  private final int days;
  private final int hours;
  private final int minutes;

  public Time(int days, int hours, int minutes) {
    this.days = days;
    this.hours = hours;
    this.minutes = minutes;
  }

  public long getTotalMinutes() {
    return (days * (24 * 60)) + (hours * 60) + minutes;
  }
}
