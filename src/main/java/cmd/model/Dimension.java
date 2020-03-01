package cmd.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Dimension {

  private Fraction radical;
  private Fraction number;

  public Dimension() {
    radical = new Fraction(1, 0, 1);
    number = new Fraction(1, 0, 1);
  }

  public double toDecimal() {
    double newNumber = (double) number.getNumerator() / (double) number.getDenominator();
    double newRadical = (radical.getNumerator() * Math.sqrt(2)) / (double) radical.getDenominator();

    return (number.getSign() * newNumber) + (radical.getSign() * newRadical);
  }
}
