package cmd.model.builder;

import static org.junit.Assert.assertEquals;

import cmd.model.Time;
import org.junit.Test;

public class TimeBuilderUnitTest {

  @Test
  public void buildFromString_isOk1() {
    assertEquals(new Time(0, 2, 0), TimeBuilder.buildFromString("2 hours"));
  }

  @Test
  public void buildFromString_isOk2() {
    assertEquals(new Time(4, 0, 0), TimeBuilder.buildFromString("4 days"));
  }

  @Test
  public void buildFromString_isOk3() {
    assertEquals(new Time(0, 0, 30), TimeBuilder.buildFromString("30 minutes"));
  }

  @Test
  public void buildFromString_isOk4() {
    assertEquals(new Time(0, 7, 35), TimeBuilder.buildFromString("7 hours 35 minutes"));
  }

  @Test
  public void buildFromString_isOk5() {
    assertEquals(new Time(9, 0, 45), TimeBuilder.buildFromString("9 days 45 minutes"));
  }

  @Test
  public void buildFromString_isOk6() {
    assertEquals(new Time(23, 12, 0), TimeBuilder.buildFromString("23 days 12 hours"));
  }

  @Test
  public void buildFromString_isOk7() {
    assertEquals(new Time(5, 23, 103), TimeBuilder.buildFromString("5 days 23 hours 103 minutes"));
  }
}
