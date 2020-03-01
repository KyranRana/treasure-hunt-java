package cmd.treasurelocator;

import static org.junit.Assert.assertEquals;

import cmd.model.Dimension;
import cmd.model.Direction;
import cmd.model.Fraction;
import cmd.model.Location;
import cmd.model.Time;
import cmd.model.TreasureInputData;
import java.util.List;
import java.util.Map;
import org.javatuples.Triplet;
import org.junit.Test;

public class DefaultTreasureLocatorUnitTest {

  private final TreasureLocator treasureLocator;

  public DefaultTreasureLocatorUnitTest() {
    treasureLocator = new DefaultTreasureLocator();
  }

  @Test
  public void locate_backToOrigin_isOk1() {
    assertEquals(
        new Location(
            new Dimension(new Fraction(1, 0, 1), new Fraction(1, 0, 1)),
            new Dimension(new Fraction(1, 0, 1), new Fraction(1, 0, 1))),
        treasureLocator.locate(
            new TreasureInputData(
                Map.of("Walk", 3, "Horse Trot", 4),
                List.of(
                    new Triplet<>("Walk", new Time(0, 0, 20), Direction.NE),
                    new Triplet<>("Horse Trot", new Time(0, 1, 0), Direction.E),
                    new Triplet<>("Walk", new Time(0, 0, 20), Direction.SW),
                    new Triplet<>("Horse Trot", new Time(0, 1, 0), Direction.W)))));
  }

  @Test
  public void locate_backToOrigin_isOk2() {
    assertEquals(
        new Location(
            new Dimension(new Fraction(1, 0, 1), new Fraction(1, 0, 1)),
            new Dimension(new Fraction(1, 0, 1), new Fraction(1, 0, 1))),
        treasureLocator.locate(
            new TreasureInputData(
                Map.of("Walk", 3, "Horse Trot", 4),
                List.of(
                    new Triplet<>("Walk", new Time(0, 0, 20), Direction.NE),
                    new Triplet<>("Horse Trot", new Time(0, 1, 0), Direction.E),
                    new Triplet<>("Horse Trot", new Time(0, 1, 0), Direction.E),
                    new Triplet<>("Walk", new Time(0, 0, 20), Direction.SW),
                    new Triplet<>("Horse Trot", new Time(0, 1, 0), Direction.W),
                    new Triplet<>("Horse Trot", new Time(0, 1, 0), Direction.W)))));
  }

  @Test
  public void locate_isSouthEast_isOk3() {
    assertEquals(
        new Location(
            new Dimension(new Fraction(1, 0, 1), new Fraction(1, 28, 15)),
            new Dimension(new Fraction(1, 0, 1), new Fraction(1, 7, 2))),
        treasureLocator.locate(
            new TreasureInputData(
                Map.of("Walk", 3, "Horse Trot", 4, "Horse Gallop", 6),
                List.of(
                    new Triplet<>("Walk", new Time(0, 0, 20), Direction.N),
                    new Triplet<>("Horse Gallop", new Time(0, 1, 0), Direction.E),
                    new Triplet<>("Horse Trot", new Time(0, 1, 2), Direction.W),
                    new Triplet<>("Walk", new Time(0, 1, 30), Direction.S)))));
  }

  @Test
  public void locate_isSouthEast_isOk4() {
    assertEquals(
        new Location(
            new Dimension(new Fraction(1, 1, 4), new Fraction(1, 29, 15)),
            new Dimension(new Fraction(1, 7, 4), new Fraction(-1, 1, 1))),
        treasureLocator.locate(
            new TreasureInputData(
                Map.of(
                    "Walk", 3, "Run", 6, "Horse Trot", 4, "Horse Gallop", 15, "Elephant Ride", 6),
                List.of(
                    new Triplet<>("Walk", new Time(0, 0, 20), Direction.N),
                    new Triplet<>("Run", new Time(0, 1, 0), Direction.E),
                    new Triplet<>("Horse Trot", new Time(0, 3, 0), Direction.NW),
                    new Triplet<>("Elephant Ride", new Time(0, 1, 30), Direction.SE),
                    new Triplet<>("Horse Gallop", new Time(0, 0, 20), Direction.SE),
                    new Triplet<>("Walk", new Time(0, 0, 30), Direction.SW),
                    new Triplet<>("Horse Trot", new Time(0, 1, 1), Direction.W)))));
  }
}
