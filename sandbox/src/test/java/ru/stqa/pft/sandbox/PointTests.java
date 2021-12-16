package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {
  @Test
  public void distance() {
    Point p1 = new Point(4, 5);
    Point p2 = new Point(2, 3);
    Assert.assertEquals(p1.distance(p2), 2.8284271247461903);
  }

  @Test
  public void distance2() {
    Point p1 = new Point(6, 7);
    Point p2 = new Point(9, 10);
    Assert.assertEquals(p1.distance(p2), 4.242640687119285);
  }


}
