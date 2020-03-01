package cmd.treasurelocator;

import cmd.model.Location;
import cmd.model.TreasureInputData;
import java.util.List;
import java.util.Map;
import org.javatuples.Triplet;

public interface TreasureLocator {

  Location locate(TreasureInputData treasureInputData);
}
