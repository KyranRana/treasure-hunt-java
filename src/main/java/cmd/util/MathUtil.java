package cmd.util;

import java.util.Arrays;

/**
 * Math utility class.
 *
 * @author kyranrana
 */
public class MathUtil {

  /**
   * Gets the GCD of x and y.
   *
   * @param x The x
   * @param y The y
   * @return The GCD
   */
  public static long gcd(long x, long y) {
    return (y == 0) ? x : gcd(y, x % y);
  }

  /**
   * Gets the LCM of a list of numbers.
   *
   * @param numbers The numbers.
   * @return The LCM
   */
  public static long lcm(long... numbers) {
    return Arrays.stream(numbers).reduce(1, (x, y) -> Math.abs(x * y) / gcd(x, y));
  }
}
