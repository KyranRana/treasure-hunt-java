package cmd.locator;

import cmd.model.Location;
import java.util.List;
import java.util.Map;
import org.javatuples.Triplet;

/**
 * Contract for treasure locator class.
 *
 * @author kyranrana
 */
public interface TreasureLocator {

  /**
   * Given different modes of travel and steps to travel to get to the treasure, locates where the
   * treasure is and returns its location relative to the origin.
   *
   * @param travels Different modes of travel.
   * @param steps Steps to take to get to the treasure.
   * @return The location relative from origin.
   */
  Location locate(Map<String, Integer> travels, List<Triplet<String, String, String>> steps);
}
