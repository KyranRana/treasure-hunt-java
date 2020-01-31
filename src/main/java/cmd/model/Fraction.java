package cmd.model;

import cmd.util.MathUtil;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a fraction.
 *
 * @author kyranrana
 */
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class Fraction {

  private final int sign;
  private final long numerator;
  private final long denominator;

  /**
   * Multiplies this fraction by another fraction, returning a new simplified fraction.
   *
   * @param fraction Other fraction.
   * @return Simplified fraction.
   */
  public Fraction multiply(Fraction fraction) {
    long newNumerator = numerator * fraction.getNumerator();
    long newDenominator = denominator * fraction.getDenominator();

    long gcd = MathUtil.gcd(newNumerator, newDenominator);

    newNumerator /= gcd;
    newDenominator /= gcd;

    return new Fraction(sign * fraction.getSign(), newNumerator, newDenominator);
  }

  /**
   * Adds this fraction to another fraction, returning a new simplified fraction.
   *
   * @param fraction Other fraction.
   * @return Simplified fraction.
   */
  public Fraction add(Fraction fraction) {
    long commonDenominator = MathUtil.lcm(denominator, fraction.getDenominator());

    long thisFractionMultiplier = commonDenominator / denominator;
    long thatFractionMultiplier = commonDenominator / fraction.getDenominator();

    long newNumerator =
        (sign * numerator * thisFractionMultiplier)
            + (fraction.getSign() * fraction.getNumerator() * thatFractionMultiplier);

    long gcd = MathUtil.gcd(newNumerator, commonDenominator);

    newNumerator /= gcd;
    commonDenominator /= gcd;

    return new Fraction(newNumerator < 0 ? -1 : 1, Math.abs(newNumerator), commonDenominator);
  }
}
