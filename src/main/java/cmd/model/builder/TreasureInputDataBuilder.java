package cmd.model.builder;

import cmd.model.Direction;
import cmd.model.Time;
import cmd.model.TreasureInputData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.javatuples.Triplet;

public class TreasureInputDataBuilder {

  public static TreasureInputData buildFromLines(List<String> lines) {
    Map<String, Integer> travels = new HashMap<>();

    int numberOfTravels = Integer.parseInt(lines.get(0));
    for (int i = 0; i < numberOfTravels; i++) {
      String[] parts = lines.get(i + 1).split(",");
      int speed = Integer.parseInt(parts[1].replace("mph", ""));
      travels.put(parts[0], speed);
    }

    List<Triplet<String, Time, Direction>> directions = new ArrayList<>();

    int numberOfDirections = Integer.parseInt(lines.get(numberOfTravels + 1));
    for (int i = 0; i < numberOfDirections; i++) {
      String[] parts = lines.get(i + numberOfTravels + 2).split(",");
      Time time = TimeBuilder.buildFromString(parts[1]);
      directions.add(new Triplet<>(parts[0], time, Direction.valueOf(parts[2])));
    }

    return new TreasureInputData(travels, directions);
  }
}
