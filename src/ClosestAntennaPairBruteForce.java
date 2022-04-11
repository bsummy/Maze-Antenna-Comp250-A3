
// A brute force method to find the closest distance between points in A and B by pairwise comparison.
public class ClosestAntennaPairBruteForce {
    private double closestDistanceBrute = Double.POSITIVE_INFINITY;
    private Point2D aPointBig;
    private Point2D bPointBig;

    public ClosestAntennaPairBruteForce(Point2D[] aPoints, Point2D[] bPoints) {
        for (Point2D aPointBrute : aPoints) {
            for (Point2D bPointBrute : bPoints) {
                double curDistance = aPointBrute.distanceTo(bPointBrute);
                if (curDistance < closestDistanceBrute) {
                    aPointBig = aPointBrute;
                    bPointBig = bPointBrute;
                    closestDistanceBrute = curDistance;
                }
            }
        }
    }

    public double getClosestDistance() {
        return closestDistanceBrute;
    }
}
