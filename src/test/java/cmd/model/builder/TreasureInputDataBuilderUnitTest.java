package cmd.model.builder;

import static org.junit.Assert.assertEquals;

import cmd.model.Direction;
import cmd.model.Time;
import cmd.model.TreasureInputData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.javatuples.Triplet;
import org.junit.Test;

public class TreasureInputDataBuilderUnitTest {

  @Test
  public void buildFromLines_isOk() {
    assertEquals(
        new TreasureInputData(
            Map.ofEntries(
                Map.entry("Walk", 3),
                Map.entry("Run", 6),
                Map.entry("Horse Trot", 4),
                Map.entry("Horse Gallop", 15),
                Map.entry("Elephant Ride", 6)),
            new ArrayList<>() {
              {
                add(new Triplet<>("Walk", new Time(32, 0, 20), Direction.N));
                add(new Triplet<>("Run", new Time(0, 1, 0), Direction.E));
                add(new Triplet<>("Horse Trot", new Time(0, 3, 0), Direction.NW));
                add(new Triplet<>("Elephant Ride", new Time(0, 1, 30), Direction.SE));
                add(new Triplet<>("Horse Gallop", new Time(1, 0, 20), Direction.SE));
                add(new Triplet<>("Walk", new Time(0, 0, 30), Direction.SW));
                add(new Triplet<>("Horse Trot", new Time(0, 1, 1), Direction.W));
              }
            }),
        TreasureInputDataBuilder.buildFromLines(
            List.of(
                "5",
                "Walk,3mph",
                "Run,6mph",
                "Horse Trot,4mph",
                "Horse Gallop,15mph",
                "Elephant Ride,6mph",
                "7",
                "Walk,32 days 20 mins,N",
                "Run,1 hour,E",
                "Horse Trot,3 hours,NW",
                "Elephant Ride,1 hour 30 mins,SE",
                "Horse Gallop,1 day 20 mins,SE",
                "Walk,30 mins,SW",
                "Horse Trot,1 hour 1 min,W")));
  }
}
