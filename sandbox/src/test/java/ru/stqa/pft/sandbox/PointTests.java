package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {
  @Test
  public void distance() {
    Point p = new Point(4, 5, 2, 3);
    Assert.assertEquals(p.distance(), 2.8284271247461903);
  }
}
