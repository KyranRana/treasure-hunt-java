package cmd.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a location.
 *
 * @author kyranrana
 */
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Location {

  private Dimension x;
  private Dimension y;
}
