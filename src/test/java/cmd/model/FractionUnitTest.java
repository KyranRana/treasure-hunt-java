package cmd.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for {@link Fraction}
 *
 * @author kyranrana
 */
public class FractionUnitTest {

  /**
   * Scenario.
   *
   * <p>Given two fractions
   *
   * <p>When {@link Fraction#multiply(Fraction)} is called
   *
   * <p>Then expect the right simplified fraction.
   */
  @Test
  public void multiply_isOk() {
    assertEquals(
        new Fraction(1, 61, 30), (new Fraction(1, 61, 15)).multiply(new Fraction(1, 1, 2)));

    assertEquals(
        new Fraction(1, 1, 2), (new Fraction(1, 60, 60)).multiply(new Fraction(1, 1, 2)));

    assertEquals(
        new Fraction(-1, 4, 1), (new Fraction(1, 2, 1)).multiply(new Fraction(-1, 2, 1)));

    assertEquals(
        new Fraction(-1, 322, 645), (new Fraction(1, 14, 15)).multiply(new Fraction(-1, 23, 43)));

  }

  /**
   * Scenario.
   *
   * <p>Given two fractions
   *
   * <p>When {@link Fraction#add(Fraction)} is called
   *
   * <p>Then expect the right simplified fraction.
   */
  @Test
  public void add_isOk() {
    assertEquals(
        new Fraction(1, 241, 30), (new Fraction(1, 61, 30)).add(new Fraction(1, 6, 1)));

    assertEquals(
        new Fraction(1, 3083, 2880), (new Fraction(1, 32, 45)).add(new Fraction(1, 23, 64)));

    assertEquals(
        new Fraction(1, 3, 10), (new Fraction(-1, 2, 4)).add(new Fraction(1, 4, 5)));

    assertEquals(
        new Fraction(1, 481, 30), (new Fraction(1, 61, 30)).add(new Fraction(1, 14, 1)));

    assertEquals(
        new Fraction(1, 5, 2), (new Fraction(1, 61, 30)).add(new Fraction(1, 14, 30)));

  }

}
