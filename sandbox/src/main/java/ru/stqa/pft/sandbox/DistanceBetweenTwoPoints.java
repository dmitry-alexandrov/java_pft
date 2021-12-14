package ru.stqa.pft.sandbox;

public class DistanceBetweenTwoPoints {
  public static void main(String[] args) {

//    Point p1 = new Point();
 //   p1.x = 4;
//    p1.y = 5;

//    Point p2 = new Point();
//    p2.x = 2;
//    p2.y = 3;
//    System.out.println("Расстояние между двумя точками = " + distance(p1 , p2));
//  }
// public  static double distance(Point p1, Point p2) {
//   return Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));


    Point p = new Point(4, 5, 2, 3);

    System.out.println("Расстояние между двумя точками = " + p.distance());
  }

}
