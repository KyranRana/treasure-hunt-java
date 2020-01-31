package cmd.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for {@link Time}
 *
 * @author kyranrana
 */
public class TimeUnitTest {

  /**
   * <p>Given {@link Time} with days, hours and minutes.
   *
   * <p>When {@link Time#getTotalMinutes()} is called.
   *
   * <p>Then expect total minutes utilised by {@link Time}
   */
  @Test
  public void getTotalMinutes_isOk() {
    assertEquals(30, new Time("30 mins").getTotalMinutes());
    assertEquals(180, new Time("3 hours").getTotalMinutes());
    assertEquals(1440, new Time("1 day").getTotalMinutes());
    assertEquals(4320, new Time("3 days").getTotalMinutes());
    assertEquals(4350, new Time("3 days 30 mins").getTotalMinutes());
    assertEquals(4410, new Time("3 days 1 hour 30 mins").getTotalMinutes());
  }
}
