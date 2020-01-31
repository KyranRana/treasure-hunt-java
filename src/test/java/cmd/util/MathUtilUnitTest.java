package cmd.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for {@link MathUtil}
 *
 * @author kyranrana
 */
public class MathUtilUnitTest {

  /**
   * Scenario.
   *
   * <p>When 2 numbers are given.
   *
   * <p>When {@link MathUtil#gcd(long, long)} is called
   *
   * <p>Then expect the GCD of those 2 numbers.
   */
  @Test
  public void gcd_isOk() {
    assertEquals(5, MathUtil.gcd(5, 30));
    assertEquals(3, MathUtil.gcd(33, 90));
    assertEquals(100, MathUtil.gcd(100, 200));
  }

  /**
   * Scenario.
   *
   * <p>When x numbers are given.
   *
   * <p>When {@link MathUtil#lcm(long...)} is called
   *
   * <p>Then expect the LCM of those numbers.
   */
  @Test
  public void lcm_isOk() {
    assertEquals(30, MathUtil.lcm(6, 5));
    assertEquals(300, MathUtil.lcm(12, 15, 10, 75));
    assertEquals(120, MathUtil.lcm(10, 20, 30, 40));
  }
}
