package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {
  @Test
  public void distance() {
    Point p1 = new Point(4, 5);
    Point p2 = new Point(2, 3);
    Assert.assertEquals(p1.distance(p2), 2.8284271247461903);
    Assert.assertEquals(p1.distance(p2), 3);
  }



}
