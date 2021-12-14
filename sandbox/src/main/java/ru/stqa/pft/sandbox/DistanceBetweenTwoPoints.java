package ru.stqa.pft.sandbox;

public class DistanceBetweenTwoPoints {
  public static void main(String[] args) {

    Point p1 = new Point(4, 5);


    Point p2 = new Point(2, 3);

    System.out.println("Расстояние между двумя точками = " + p1.distance(p2));
  }
}
