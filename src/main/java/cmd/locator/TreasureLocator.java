package cmd.locator;

import cmd.model.Location;
import cmd.model.TreasureInputData;

public interface TreasureLocator {

  Location locate(TreasureInputData treasureInputData);
}
