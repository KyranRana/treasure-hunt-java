package cmd.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimeUnitTest {

  @Test
  public void getTotalMinutes_isOk1() {
    assertEquals(30, new Time(0, 0, 30).getTotalMinutes());
  }

  @Test
  public void getTotalMinutes_isOk2() {
    assertEquals(180, new Time(0, 3, 0).getTotalMinutes());
  }

  @Test
  public void getTotalMinutes_isOk3() {
    assertEquals(1440, new Time(1, 0, 0).getTotalMinutes());
  }

  @Test
  public void getTotalMinutes_isOk4() {
    assertEquals(4320, new Time(3, 0, 0).getTotalMinutes());
  }

  @Test
  public void getTotalMinutes_isOk5() {
    assertEquals(4350, new Time(3, 0, 30).getTotalMinutes());
  }

  @Test
  public void getTotalMinutes_isOk6() {
    assertEquals(4410, new Time(3, 1, 30).getTotalMinutes());
  }
}
