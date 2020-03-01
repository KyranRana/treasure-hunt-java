package cmd.validation;

import cmd.exception.validation.InputMalformedException;
import java.util.Arrays;
import org.junit.Test;

public class DefaultInputDataValidationUnitTest {

  private final InputDataValidation inputDataValidation;

  public DefaultInputDataValidationUnitTest() {
    this.inputDataValidation = new TreasureInputDataValidation();
  }

  @Test(expected = InputMalformedException.class)
  public void validate_whereLessThan4Lines() {
    inputDataValidation.validate(Arrays.asList("", ""));
  }

  @Test(expected = InputMalformedException.class)
  public void validate_whereTravelIsNotANumber() {
    inputDataValidation.validate(Arrays.asList("test", "Car,1mph", "1", "Car,1 min,NW"));
  }

  @Test(expected = InputMalformedException.class)
  public void validate_whereApproxOfTravelIsMalformed() {
    inputDataValidation.validate(Arrays.asList("1", "Ca1mph", "1", "Car,1 min,NW"));
  }

  @Test(expected = InputMalformedException.class)
  public void validate_whereApproxOfTravelContainsTravelWhichIsNotText() {
    inputDataValidation.validate(Arrays.asList("1", "Ca$,1mph", "1", "Car,1 min,NW"));
  }

  @Test(expected = InputMalformedException.class)
  public void validate_whereApproxOfTravelContainsTravelAlreadyDefined() {
    inputDataValidation.validate(
        Arrays.asList("3", "Car,1mph", "Train,3mph", "Car,5mph", "1", "Car,1 min,NW"));
  }

  @Test(expected = InputMalformedException.class)
  public void validate_whereApproxOfTravelHasInvalidNumberAndMph() {
    inputDataValidation.validate(Arrays.asList("1", "Car,13mp", "1", "Car,1 min,NW"));
  }

  @Test(expected = InputMalformedException.class)
  public void validate_whereNumberOfDirectionsIsNotNumeric() {
    inputDataValidation.validate(Arrays.asList("1", "Car,13mph", "test", "Car,1 min,NW"));
  }

  @Test(expected = InputMalformedException.class)
  public void validate_whereDirectionIsMalformed() {
    inputDataValidation.validate(
        Arrays.asList("2", "Car,13mph", "Train,3mph", "2", "Car,1 min,NW", "TrainNE"));
  }

  @Test(expected = InputMalformedException.class)
  public void validate_whereDirectionHasTravelWhichIsNotText() {
    inputDataValidation.validate(
        Arrays.asList("2", "Car,13mph", "Train,3mph", "2", "Car,1 min,NW", "Trai$,1 hour,SE"));
  }

  @Test(expected = InputMalformedException.class)
  public void validate_whereDirectionHasTravelNotDefined() {
    inputDataValidation.validate(
        Arrays.asList("2", "Car,13mph", "Train,3mph", "2", "Car,1 min,NW", "Horse trot,1 hour,SE"));
  }

  @Test(expected = InputMalformedException.class)
  public void validate_whereDirectionHasIncorrectTime() {
    inputDataValidation.validate(
        Arrays.asList("2", "Car,13mph", "Train,3mph", "2", "Car,1 min,NW", "Train,1 test,SE"));
  }

  @Test(expected = InputMalformedException.class)
  public void validate_whereDirectionHasIncorrectDirection() {
    inputDataValidation.validate(
        Arrays.asList("2", "Car,13mph", "Train,3mph", "2", "Car,1 min,NW", "Train,1 test,SET"));
  }

  @Test
  public void validate_isOk1() {
    inputDataValidation.validate(
        Arrays.asList(
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
            "Horse trot,1 hour 1 min,W"));
  }
}
