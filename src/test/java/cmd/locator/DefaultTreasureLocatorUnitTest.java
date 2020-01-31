package cmd.locator;

import static org.junit.Assert.assertEquals;

import cmd.model.Dimension;
import cmd.model.Fraction;
import cmd.model.Location;
import java.util.List;
import java.util.Map;
import org.javatuples.Triplet;
import org.junit.Test;

/**
 * Unit tests for {@link DefaultTreasureLocator}.
 *
 * @author kyranrana
 */
public class DefaultTreasureLocatorUnitTest {

  private TreasureLocator treasureLocator;

  public DefaultTreasureLocatorUnitTest() {
    this.treasureLocator = new DefaultTreasureLocator();
  }

  /**
   * Scenario.
   *
   * <p>Given a series of travels and steps.
   *
   * <p>When {@link TreasureLocator#locate(Map, List)} is called.
   *
   * <p>Then expect the correct distance to treasure (in form: N,S,E,W).
   */
  @Test
  public void locate_isOk() {
    // Test 1 - Treasure is where we started
    Map<String, Integer> travels1 = Map.of("Walk", 3, "Horse Trot", 4);

    List<Triplet<String, String, String>> steps1 =
        List.of(
            new Triplet<>("Walk", "20 mins", "NE"),
            new Triplet<>("Horse Trot", "1 hour", "E"),
            new Triplet<>("Walk", "20 mins", "SW"),
            new Triplet<>("Horse Trot", "1 hour", "W"));

    assertEquals(
        new Location(
            new Dimension(new Fraction(1, 0, 1), new Fraction(1, 0, 1)),
            new Dimension(new Fraction(1, 0, 1), new Fraction(1, 0, 1))),
        treasureLocator.locate(travels1, steps1));

    // Test 2 - Treasure is where we started
    Map<String, Integer> travels2 = Map.of("Walk", 3, "Horse Trot", 4);

    List<Triplet<String, String, String>> steps2 =
        List.of(
            new Triplet<>("Walk", "20 mins", "NE"),
            new Triplet<>("Horse Trot", "1 hour", "E"),
            new Triplet<>("Horse Trot", "1 hour", "E"),
            new Triplet<>("Walk", "20 mins", "SW"),
            new Triplet<>("Horse Trot", "1 hour", "W"),
            new Triplet<>("Horse Trot", "1 hour", "W"));

    assertEquals(
        new Location(
            new Dimension(new Fraction(1, 0, 1), new Fraction(1, 0, 1)),
            new Dimension(new Fraction(1, 0, 1), new Fraction(1, 0, 1))),
        treasureLocator.locate(travels2, steps2));

    // Test 3 - Treasure is south east.
    Map<String, Integer> travels3 = Map.of("Walk", 3, "Horse Trot", 4, "Horse Gallop", 6);

    List<Triplet<String, String, String>> steps3 =
        List.of(
            new Triplet<>("Walk", "20 mins", "N"),
            new Triplet<>("Horse Gallop", "1 hour", "E"),
            new Triplet<>("Horse Trot", "1 hour 2 minutes", "W"),
            new Triplet<>("Walk", "1 hour 30 mins", "S"));

    assertEquals(
        new Location(
            new Dimension(new Fraction(1, 0, 1), new Fraction(1, 28, 15)),
            new Dimension(new Fraction(1, 0, 1), new Fraction(1, 7, 2))),
        treasureLocator.locate(travels3, steps3));

    // Test 4 - Treasure is south east.
    Map<String, Integer> travels4 =
        Map.of("Walk", 3, "Run", 6, "Horse Trot", 4, "Horse Gallop", 15, "Elephant Ride", 6);

    List<Triplet<String, String, String>> steps4 =
        List.of(
            new Triplet<>("Walk", "20 mins", "N"),
            new Triplet<>("Run", "1 hour", "E"),
            new Triplet<>("Horse Trot", "3 hours", "NW"),
            new Triplet<>("Elephant Ride", "1 hour 30 mins", "SE"),
            new Triplet<>("Horse Gallop", "20 mins", "SE"),
            new Triplet<>("Walk", "30 mins", "SW"),
            new Triplet<>("Horse Trot", "1 hour 1 min", "W"));

    assertEquals(
        new Location(
            new Dimension(new Fraction(1, 1, 4), new Fraction(1, 29, 15)),
            new Dimension(new Fraction(1, 7, 4), new Fraction(-1, 1, 1))),
        treasureLocator.locate(travels4, steps4));
  }
}
