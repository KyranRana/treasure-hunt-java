package cmd.model;

import cmd.util.MathUtil;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Fraction {

  private final int sign;
  private final long numerator;
  private final long denominator;

  public Fraction multiply(Fraction fraction) {
    long newNumerator = numerator * fraction.getNumerator();
    long newDenominator = denominator * fraction.getDenominator();

    long gcd = MathUtil.gcd(newNumerator, newDenominator);

    newNumerator /= gcd;
    newDenominator /= gcd;

    return new Fraction(sign * fraction.getSign(), newNumerator, newDenominator);
  }

  public Fraction add(Fraction fraction) {
    long newDenominator = MathUtil.lcm(denominator, fraction.getDenominator());

    long newNumerator = sign * numerator * (newDenominator / denominator);
    newNumerator += fraction.getSign() * fraction.getNumerator() * (newDenominator / fraction.getDenominator());

    long gcd = MathUtil.gcd(newNumerator, newDenominator);

    newNumerator /= gcd;
    newDenominator /= gcd;

    int newSign = newNumerator < 0 ? -1 : 1;
    newNumerator *= newSign;

    return new Fraction(newSign, newNumerator, newDenominator);
  }
}
