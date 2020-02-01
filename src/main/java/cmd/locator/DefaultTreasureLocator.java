package cmd.locator;

import cmd.model.Dimension;
import cmd.model.Fraction;
import cmd.model.Location;
import cmd.model.Time;
import java.util.List;
import java.util.Map;
import org.javatuples.Triplet;

/**
 * Default treasure locator class.
 *
 * <p>Given travels and steps, follows steps and provides location of the treasure relative from the
 * origin (allowing it to be directly travelled to).
 *
 * @author kyranrana
 */
public class DefaultTreasureLocator implements TreasureLocator {

  private static final int MINUTES_IN_HOUR = 60;

  @Override
  public Location locate(
      Map<String, Integer> travels, List<Triplet<String, String, String>> steps) {

    // start at origin (x=0, y=0)
    Dimension x = new Dimension();
    Dimension y = new Dimension();

    for (Triplet<String, String, String> step : steps) {
      String travel = step.getValue0();
      int travelInMph = travels.get(travel);

      Time time = new Time(step.getValue1());
      String direction = step.getValue2();

      switch (direction) {
        case "N":
        case "S":
        case "W":
        case "E":
          boolean northOrWest = direction.startsWith("N") || direction.startsWith("W");

          Fraction distanceGoingHorizontalOrVertical =
              new Fraction(northOrWest ? -1 : 1, time.getTotalMinutes(), MINUTES_IN_HOUR)
                  .multiply(new Fraction(1, travelInMph, 1));

          Dimension dimension = direction.startsWith("N") || direction.startsWith("S") ? y : x;
          dimension.setNumber(dimension.getNumber().add(distanceGoingHorizontalOrVertical));

          break;
        case "NE":
        case "NW":
        case "SE":
        case "SW":
          /*
           * A_2 + B_2  = C_2
           * B_2 + B_2  = C_2 (since A and B are equivalent)
           * 2B_2       = C_2
           * B_2        = C_2/2
           * B          = C/sqrt(2)
           * B          = C sqrt(2)/2
           */
          Fraction distanceGoingDiagonal =
              new Fraction(1, time.getTotalMinutes(), MINUTES_IN_HOUR)
                  .multiply(new Fraction(1, travelInMph, 1));

          Fraction distanceGoingHorizontalAndVertical =
              distanceGoingDiagonal.multiply(new Fraction(1, 1, 2));

          y.setRadical(
              y.getRadical()
                  .add(
                      new Fraction(
                          direction.startsWith("N") ? -1 : 1,
                          distanceGoingHorizontalAndVertical.getNumerator(),
                          distanceGoingHorizontalAndVertical.getDenominator())));

          x.setRadical(
              x.getRadical()
                  .add(
                      new Fraction(
                          direction.endsWith("W") ? -1 : 1,
                          distanceGoingHorizontalAndVertical.getNumerator(),
                          distanceGoingHorizontalAndVertical.getDenominator())));

          break;
      }
    }

    return new Location(x, y);
  }
}
