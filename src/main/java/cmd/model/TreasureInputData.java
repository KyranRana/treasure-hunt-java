package cmd.model;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.javatuples.Triplet;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class TreasureInputData {

  private Map<String, Integer> travels;
  private List<Triplet<String, Time, Direction>> directions;
}