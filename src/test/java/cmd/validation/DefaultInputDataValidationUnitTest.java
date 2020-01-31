package cmd.validation;

import cmd.exception.validation.InputMalformedException;
import org.junit.Test;

/**
 * Unit tests for {@link DefaultInputDataValidation}.
 *
 * @author kyranrana
 */
public class DefaultInputDataValidationUnitTest {

  private InputDataValidation inputDataValidation;

  public DefaultInputDataValidationUnitTest() {
    this.inputDataValidation = new DefaultInputDataValidation();
  }

  /**
   * Scenario.
   *
   * <p>Given less than 4 lines is given.
   *
   * <p>When {@link DefaultInputDataValidation#validate(String[])} is called
   *
   * <p>Then expect {@link InputMalformedException}
   */
  @Test(expected = InputMalformedException.class)
  public void validate_whereLessThan4Lines() {
    inputDataValidation.validate(new String[] {"", ""});
  }

  /**
   * Scenario.
   *
   * <p>Given ways of travel (line 1) is not numeric.
   *
   * <p>When {@link DefaultInputDataValidation#validate(String[])} is called
   *
   * <p>Then expect {@link InputMalformedException}
   */
  @Test(expected = InputMalformedException.class)
  public void validate_whereTravelIsNotANumber() {
    inputDataValidation.validate(new String[] {"test", "Car,1mph", "1", "Car,1 min,NW"});
  }

  /**
   * Scenario.
   *
   * <p>Given approximation of travel is malformed.
   *
   * <p>When {@link DefaultInputDataValidation#validate(String[])} is called
   *
   * <p>Then expect {@link InputMalformedException}
   */
  @Test(expected = InputMalformedException.class)
  public void validate_whereApproxOfTravelIsMalformed() {
    inputDataValidation.validate(new String[] {"1", "Ca1mph", "1", "Car,1 min,NW"});
  }

  /**
   * Scenario.
   *
   * <p>Given approximation of travel, where travel is not text or space.
   *
   * <p>When {@link DefaultInputDataValidation#validate(String[])} is called
   *
   * <p>Then expect {@link InputMalformedException}
   */
  @Test(expected = InputMalformedException.class)
  public void validate_whereApproxOfTravelContainsTravelWhichIsNotText() {
    inputDataValidation.validate(new String[] {"1", "Ca$,1mph", "1", "Car,1 min,NW"});
  }

  /**
   * Scenario.
   *
   * <p>Given approximation of travel, where travel is duplicated.
   *
   * <p>When {@link DefaultInputDataValidation#validate(String[])} is called
   *
   * <p>Then expect {@link InputMalformedException}
   */
  @Test(expected = InputMalformedException.class)
  public void validate_whereApproxOfTravelContainsTravelAlreadyDefined() {
    inputDataValidation.validate(
        new String[] {"3", "Car,1mph", "Train,3mph", "Car,5mph", "1", "Car,1 min,NW"});
  }

  /**
   * Scenario.
   *
   * <p>Given approximation of travel, where number and mph is wrong.
   *
   * <p>When {@link DefaultInputDataValidation#validate(String[])} is called
   *
   * <p>Then expect {@link InputMalformedException}
   */
  @Test(expected = InputMalformedException.class)
  public void validate_whereApproxOfTravelHasInvalidNumberAndMph() {
    inputDataValidation.validate(new String[] {"1", "Car,13mp", "1", "Car,1 min,NW"});
  }

  /**
   * Scenario.
   *
   * <p>Given number of directions is not numeric.
   *
   * <p>When {@link DefaultInputDataValidation#validate(String[])} is called
   *
   * <p>Then expect {@link InputMalformedException}
   */
  @Test(expected = InputMalformedException.class)
  public void validate_whereNumberOfDirectionsIsNotNumeric() {
    inputDataValidation.validate(new String[] {"1", "Car,13mph", "test", "Car,1 min,NW"});
  }

  /**
   * Scenario.
   *
   * <p>Given direction is malformed.
   *
   * <p>When {@link DefaultInputDataValidation#validate(String[])} is called
   *
   * <p>Then expect {@link InputMalformedException}
   */
  @Test(expected = InputMalformedException.class)
  public void validate_whereDirectionIsMalformed() {
    inputDataValidation.validate(
        new String[] {"2", "Car,13mph", "Train,3mph", "2", "Car,1 min,NW", "TrainNE"});
  }

  /**
   * Scenario.
   *
   * <p>Given direction, where type of travel is not text.
   *
   * <p>When {@link DefaultInputDataValidation#validate(String[])} is called
   *
   * <p>Then expect {@link InputMalformedException}
   */
  @Test(expected = InputMalformedException.class)
  public void validate_whereDirectionHasTravelWhichIsNotText() {
    inputDataValidation.validate(
        new String[] {"2", "Car,13mph", "Train,3mph", "2", "Car,1 min,NW", "Trai$,1 hour,SE"});
  }

  /**
   * Scenario.
   *
   * <p>Given direction, where type of travel has not been defined
   *
   * <p>When {@link DefaultInputDataValidation#validate(String[])} is called
   *
   * <p>Then expect {@link InputMalformedException}
   */
  @Test(expected = InputMalformedException.class)
  public void validate_whereDirectionHasTravelNotDefined() {
    inputDataValidation.validate(
        new String[] {
          "2", "Car,13mph", "Train,3mph", "2", "Car,1 min,NW", "Horse trot,1 hour,SE"
        });
  }

  /**
   * Scenario.
   *
   * <p>Given direction, where time is invalid.
   *
   * <p>When {@link DefaultInputDataValidation#validate(String[])} is called
   *
   * <p>Then expect {@link InputMalformedException}
   */
  @Test(expected = InputMalformedException.class)
  public void validate_whereDirectionHasIncorrectTime() {
    inputDataValidation.validate(
        new String[] {"2", "Car,13mph", "Train,3mph", "2", "Car,1 min,NW", "Train,1 test,SE"});
  }

  /**
   * Scenario.
   *
   * <p>Given direction, where actual direction of travel is not valid.
   *
   * <p>When {@link DefaultInputDataValidation#validate(String[])} is called
   *
   * <p>Then expect {@link InputMalformedException}
   */
  @Test(expected = InputMalformedException.class)
  public void validate_whereDirectionHasIncorrectDirection() {
    inputDataValidation.validate(
        new String[] {"2", "Car,13mph", "Train,3mph", "2", "Car,1 min,NW", "Train,1 test,SET"});
  }

  /**
   * Scenario.
   *
   * <p>Given valid input
   *
   * <p>When {@link DefaultInputDataValidation#validate(String[])} is called
   *
   * <p>Then expect no exception.
   */
  @Test
  public void validate_isOk1() {
    inputDataValidation.validate(
        new String[] {
          "5",
          "Walk,3mph",
          "Run,6mph",
          "Horse trot,4mph",
          "Horse gallop,15mph",
          "Elephant ride,6mph",
          "7",
          "Walk,20 mins,N",
          "Run,1 hour,E",
          "Horse trot,3 hours,NW",
          "Elephant ride,1 hour 30 mins,SE",
          "Horse gallop,20 mins,SE",
          "Walk,30 mins,SW",
          "Horse trot,1 hour 1 min,W"
        });
  }
}
