package cmd.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathUtilUnitTest {

  @Test
  public void gcd_isOk1() {
    assertEquals(5, MathUtil.gcd(5, 30));
  }

  @Test
  public void gcd_isOk2() {
    assertEquals(3, MathUtil.gcd(33, 90));
  }

  @Test
  public void gcd_isOk3() {
    assertEquals(100, MathUtil.gcd(100, 200));
  }

  @Test
  public void lcm_isOk1() {
    assertEquals(30, MathUtil.lcm(6, 5));
  }

  @Test
  public void lcm_isOk2() {
    assertEquals(300, MathUtil.lcm(12, 15, 10, 75));
  }

  @Test
  public void lcm_isOk3() {
    assertEquals(120, MathUtil.lcm(10, 20, 30, 40));
  }
}
