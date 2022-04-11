
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/*
 * Code and comments adapted from:
 *  https://algs4.cs.princeton.edu/99hull/ClosestPair.java.html
 *  https://algs4.cs.princeton.edu/13stacks/Point2D.java.html
 *  https://algs4.cs.princeton.edu/99hull/kw1260.txt
 */

public class ClosestPair {

    private double bestDistance = Double.POSITIVE_INFINITY;

    public ClosestPair(Point2D[] points) {
        int n = points.length;
        if (n <= 1) return;

        // sort by x-coordinate (breaking ties by y-coordinate via stability)

        Point2D[] pointsSortedByX = new Point2D[n];
        for (int i = 0; i < n; i++)
            pointsSortedByX[i] = points[i];

        //  first sort by Y.   Then sort by X.  This ultimately sorts by x, with ties broken by the y value.
        //  this assumes that Arrays.sort() is a stable sort, namely that it doesn't swap ties, which indeed it is.

        Arrays.sort(pointsSortedByX, Point2D.Y_ORDER);
        Arrays.sort(pointsSortedByX, Point2D.X_ORDER);

        // if there are two co-incident points, then we are done because minimum distance is 0

        for (int i = 0; i < n-1; i++) {
            if (pointsSortedByX[i].equals(pointsSortedByX[i+1])) {
                bestDistance = 0.0;
                return;
            }
        }

        // set up the array that eventually will hold the points sorted by y-coordinate

        Point2D[] pointsSortedByY = new Point2D[n];
        for (int i = 0; i < n; i++)
            pointsSortedByY[i] = pointsSortedByX[i];

        // auxiliary array
        Point2D[] aux = new Point2D[n];

        closest(pointsSortedByX, pointsSortedByY, aux, 0, n-1);
    }

    // find closest pair of points in pointsSortedByX[lo..hi]

    // precondition:  pointsSortedByX[low..high] and pointsByY[low..high] are the same sequence of points
    // precondition:  pointsSortedByX[low..high] sorted by x-coordinate
    // postcondition: pointsByY[low..high] sorted by y-coordinate

    private double closest(Point2D[] pointsSortedByX, Point2D[] pointsSortedByY, Point2D[] aux, int low, int high) {

        //  low and high refer to indices in the list of points.

        // base case is that low==high (one point) or low > high (zero points); in either case, return POSITIVE_INFINITY

        if (high <= low) return Double.POSITIVE_INFINITY;

        //  otherwise at least one point is present in the low,high interval

        int mid = low + (high - low) / 2;            // if low==high then mid==low
        Point2D median = pointsSortedByX[mid];

        // compute closest pair with such that both points in the pair are either in left subarray or both points are in right subarray
        // the two closest calls below will return with pointsSortedByY[low .. mid] and pointsSortedByY[mid+1 .. high] sorted by Y
        // then the pointsSortedByY will be merged so the range [low,high] is sorted by Y

        double delta1 = closest(pointsSortedByX, pointsSortedByY, aux, low, mid);     // if low==mid,  then only one point, so it will return POSITIVE_INFINITY
        double delta2 = closest(pointsSortedByX, pointsSortedByY, aux, mid+1, high);   // if low==high, then high < mid+1, so it will return POSITIVE_INFINITY
        double delta = Math.min(delta1, delta2);

        // As mentioned above,  now merge back so that pointsSortedByY[low..high] are sorted by y-coordinate.
        // We know the low < high.  Also see preconditions on merge().

        merge(pointsSortedByY, aux, low, mid, high);

        // The aux array has the same size as point2D[] but we only use the first high-low+1 slots here.

        // aux[0..m-1] = go through the [low,high] range of pointsSortedByY and make a list of those points
        // whose x value is within delta from the median of x;  keep them sorted by y-coordinate
        // These are the points in a 2-delta strip around the x median for [low,high].
        // Note this wipes out any values in aux that were previously there, which is fine since aux is temporary only.

        int m = 0;
        for (int i = low; i <= high; i++) {
            if (Math.abs(pointsSortedByY[i].x() - median.x()) < delta)
                aux[m++] = pointsSortedByY[i];
        }

        // Compare pairs of points within the strip;  we only need to test points with a y separation less than delta
        // Find the closest pair of points and return the distance between them
        // The two points are called best1 and best2

        for (int i = 0; i < m; i++) {

            // a geometric packing argument shows that this loop iterates at most 7 times
            // (we don't need to test explicitly that the max is 7)

            for (int j = i+1; (j < m) && (aux[j].y() - aux[i].y() < delta); j++) {
                double distance = aux[i].distanceTo(aux[j]);
                if (distance < delta) {
                    delta = distance;
                    if (distance < bestDistance)
                        bestDistance = delta;
                }
            }
        }
        return delta;
    }

    public double distance() {
        return bestDistance;
    }

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // stably merge a[low .. mid] with a[mid+1 ..high] using aux[low .. high]
    // precondition: a[low .. mid] and a[mid+1 .. high] are sorted subarrays, namely sorted by y coordinate

    private static void merge(Point2D[] a, Point2D[] aux, int low, int mid, int high) {
        // copy to aux[]
        // note this wipes out any values that were previously in aux in the [low,high] range we're currently using

        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
        }

        int i = low, j = mid+1;
        for (int k = low; k <= high; k++) {
            if      (i > mid)              a[k] = aux[j++];   // already finished with the low list ?  then dump the rest of high list
            else if (j > high)               a[k] = aux[i++];   // already finished with the high list ?  then dump the rest of low list
            else if (aux[i].compareByY(aux[j]) < 0 )   a[k] = aux[i++];   //  break ties by comparing x coordinate
            else                           a[k] = aux[j++];
        }
    }

    public static void main(String[] args) {
        String localDir = System.getProperty("user.dir");

        //  You'll need to modify the location in your own directories.
        String base = localDir + "/src/";

        String s = base + "kw1260.txt";
        // String s = base + "rs1423.txt";
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(s));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int n = scanner.nextInt();
        Point2D[]  points = new Point2D[n];
        int i = 0;
        int x, y;
        while(scanner.hasNextInt()) {
            x = scanner.nextInt();
            y = scanner.nextInt();
            points[i] = new Point2D(x,y);
            i++;
        }

        ClosestPair closest = new ClosestPair(points);
        System.out.println(closest.distance());
    }
}
