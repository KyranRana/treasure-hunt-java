package cmd.util;

import java.util.Arrays;

public class MathUtil {

  public static long gcd(long x, long y) {
    return (y == 0) ? x : gcd(y, x % y);
  }

  public static long lcm(long... numbers) {
    return Arrays.stream(numbers).reduce(1, (x, y) -> Math.abs(x * y) / gcd(x, y));
  }
}
