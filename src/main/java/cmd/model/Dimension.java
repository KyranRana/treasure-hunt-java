package cmd.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a dimension (X or Y)
 *
 * <p>Consists of:
 *
 * <ul>
 *   <li>Radical - for supporting diagonal units of travel (NW, NE, SW, SE). When moving in a
 *       diagonal direction for example NW, it is guaranteed we move the same amount of units N as
 *       we do W. This is because the angle between our line-of-travel and the N or W line is 1/2 of
 *       90 degrees which is 45. Considering the diagonal, horizontal (N), and vertical (W) lines
 *       form a right-angle triangle, we can find out the unit we travel for both N and W
 *       individually by using the formula (B = (H sqrt(2) / 2)). H is the hypotenuse, and B is the
 *       opposite and adjacent.
 *       <pre>
 *         How we end up with (B = H sqrt(2) / 2)
 *
 *         A_2 + B_2 = C_2
 *         B_2 + B_2 = C_2 (since A and B are equivalent)
 *         2B_2 = C_2
 *         B_2 = C_2/2
 *         B = C/sqrt(2)
 *         B = C sqrt(2)/2
 *       </pre>
 *       <pre>
 *            L
 *          ------
 *          \    |
 *           \   |
 *          H \  | L
 *             \ |
 *              \|
 *         -------------
 *               |
 *               |
 *               |
 *
 *       </pre>
 *   <li>Whole - for supporting horizontal (X=E|W) and vertical (Y=N|S) units of travel
 * </ul>
 *
 * @author kyranrana
 */
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Dimension {

  private Fraction radical;
  private Fraction number;

  public Dimension() {
    radical = new Fraction(1, 0, 1);
    number = new Fraction(1, 0, 1);
  }

  public double toDecimal() {
    return (number.getSign() * ((double) number.getNumerator() / (double) number.getDenominator()))
        + (radical.getSign() * ((radical.getNumerator() * Math.sqrt(2)) / (double) radical.getDenominator()));
  }
}
