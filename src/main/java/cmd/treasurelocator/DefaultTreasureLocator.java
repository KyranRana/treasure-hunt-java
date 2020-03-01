package cmd.treasurelocator;

import cmd.model.Dimension;
import cmd.model.Direction;
import cmd.model.Fraction;
import cmd.model.Location;
import cmd.model.Time;
import cmd.model.TreasureInputData;
import cmd.model.builder.TimeBuilder;
import java.util.List;
import java.util.Map;
import org.javatuples.Triplet;

public class DefaultTreasureLocator implements TreasureLocator {

  private static final int MINUTES_IN_HOUR = 60;

  @Override
  public Location locate(TreasureInputData treasureInputData) {
    Dimension x = new Dimension();
    Dimension y = new Dimension();

    Map<String, Integer> travels = treasureInputData.getTravels();
    List<Triplet<String, Time, Direction>> directions = treasureInputData.getDirections();

    for (Triplet<String, Time, Direction> direction : directions) {
      int travelSpeed = travels.get(direction.getValue0());

      Time theTime = direction.getValue1();
      Direction theDirection = direction.getValue2();

      switch (theDirection) {
        case N:
        case S:
        case W:
        case E:
          boolean northOrWest = theDirection == Direction.N || theDirection == Direction.W;

          Fraction distanceGoingHorizontalOrVertical =
              new Fraction(northOrWest ? -1 : 1, theTime.getTotalMinutes(), MINUTES_IN_HOUR)
                  .multiply(new Fraction(1, travelSpeed, 1));

          Dimension dimension = theDirection == Direction.N || theDirection == Direction.S ? y : x;
          dimension.setNumber(dimension.getNumber().add(distanceGoingHorizontalOrVertical));

          break;
        case NE:
        case NW:
        case SE:
        case SW:
          /*
           * A_2 + B_2  = C_2
           * B_2 + B_2  = C_2 (since A and B are equivalent)
           * 2B_2       = C_2
           * B_2        = C_2/2
           * B          = C/sqrt(2)
           * B          = C sqrt(2)/2
           */
          Fraction distanceGoingDiagonal =
              new Fraction(1, theTime.getTotalMinutes(), MINUTES_IN_HOUR)
                  .multiply(new Fraction(1, travelSpeed, 1));

          Fraction distanceGoingHorizontalAndVertical =
              distanceGoingDiagonal.multiply(new Fraction(1, 1, 2));

          y.setRadical(
              y.getRadical()
                  .add(
                      new Fraction(
                          theDirection == Direction.N
                                  || theDirection == Direction.NW
                                  || theDirection == Direction.NE
                              ? -1
                              : 1,
                          distanceGoingHorizontalAndVertical.getNumerator(),
                          distanceGoingHorizontalAndVertical.getDenominator())));

          x.setRadical(
              x.getRadical()
                  .add(
                      new Fraction(
                          theDirection == Direction.W
                                  || theDirection == Direction.NW
                                  || theDirection == Direction.SW
                              ? -1
                              : 1,
                          distanceGoingHorizontalAndVertical.getNumerator(),
                          distanceGoingHorizontalAndVertical.getDenominator())));

          break;
      }
    }

    return new Location(x, y);
  }
}
