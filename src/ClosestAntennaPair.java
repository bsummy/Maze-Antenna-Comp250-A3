import java.util.Arrays;

/*
Essentially, everything is duplicated from Closest Pair with two arrays. However, there is a stack overflow
in the closest method, so other changes will need to be made here. It's a good start to the assignment, and
I'll need to research the method more in closest pair to fully understand the problem.
 */


public class ClosestAntennaPair {

    private double closestDistance = Double.POSITIVE_INFINITY;
    private long counter = 0;
    private Point2D aPoint;
    private Point2D bPoint;

    public ClosestAntennaPair(Point2D[] aPoints, Point2D[] bPoints) {

        int n = aPoints.length;
        int m = bPoints.length;

        if (n < 1) return;

        if (m < 1) return;

        Point2D[] aPointsSortedByX = new Point2D[n];
        for (int i = 0; i < n; i++)
            aPointsSortedByX[i] = aPoints[i];

        Point2D[] bPointsSortedByX = new Point2D[m];
        for (int i = 0; i < m; i++)
            bPointsSortedByX[i] = bPoints[i];

        if (n == 1){
            for (int i = 0; (i < m) && (aPointsSortedByX[0].distanceTo(bPointsSortedByX[i]) < closestDistance) ; i++) {
                closestDistance = aPointsSortedByX[0].distanceTo(bPointsSortedByX[i]);
            }
        }

        if (m == 1){
            for (int i = 0; (i < n) && (bPointsSortedByX[0].distanceTo(aPointsSortedByX[i]) < closestDistance); i++) {
                closestDistance = bPointsSortedByX[0].distanceTo(aPointsSortedByX[i]);
            }
        }

        // sort APoints by x-coordinate (breaking ties by y-coordinate via stability)
        Arrays.sort(aPointsSortedByX, Point2D.Y_ORDER);
        Arrays.sort(aPointsSortedByX, Point2D.X_ORDER);


        // sort BPoints by x-coordinate (breaking ties by y-coordinate via stability)
        Arrays.sort(bPointsSortedByX, Point2D.Y_ORDER);
        Arrays.sort(bPointsSortedByX, Point2D.X_ORDER);


        // set up the array that eventually will hold the points sorted by y-coordinate
        Point2D[] aPointsSortedByY = new Point2D[n];
        for (int i = 0; i < n; i++)
            aPointsSortedByY[i] = aPointsSortedByX[i];

        Point2D[] bPointsSortedByY = new Point2D[m];
        for (int i = 0; i < m; i++)
            bPointsSortedByY[i] = bPointsSortedByX[i];

        Point2D[] auxA = new Point2D[n];
        Point2D[] auxB = new Point2D[m];

        closest(aPointsSortedByX, bPointsSortedByX, aPointsSortedByY, bPointsSortedByY, auxA, auxB, 0, 0, n-1, m-1);
    }

    public double closest(Point2D[] aPointsSortedByX, Point2D[] bPointsSortedByX, Point2D[] aPointsSortedByY, Point2D[] bPointsSortedByY, Point2D[] auxA, Point2D[] auxB, int lowA, int lowB, int highA, int highB) {
        // please do not delete/modify the next line!
        counter++;


        //this could be wrong
        if ((highA <= lowA) || (highB <= lowB)) return Double.POSITIVE_INFINITY;

        int n = aPointsSortedByX.length;
        int m = bPointsSortedByY.length;

        // fairly confident this is correct, meant to find a good distance between both types of points to check 1147 to restore
        int midA = lowA + (highA - lowA) / 2; //a's mid
        Point2D median = aPointsSortedByX[midA];
        int midB = 0;

        for (int i = 0; i < bPointsSortedByX.length; i++) {
            if (bPointsSortedByX[i].x() > median.x()){
                midB = i;
                break;
            }
        }

        //I think this is right? not totally sure, but seems to be handling the recursion well
        double delta1 = closest(aPointsSortedByX, bPointsSortedByX, aPointsSortedByY, bPointsSortedByY, auxA, auxB, lowA, lowB, midA, midB);
        double delta2 = closest(aPointsSortedByX, bPointsSortedByX, aPointsSortedByY, bPointsSortedByY, auxA, auxB, midA+1, midB+1, highA, highB);
        double delta = Math.min(delta1, delta2);

        //is this useful?
        merge(aPointsSortedByY, auxA, lowA, midA, highA);
        merge(bPointsSortedByY, auxB, lowB, midB, highB);


        int h = 0;
        for (int i = lowA; i <= highA; i++) {
            if (Math.abs(aPointsSortedByY[i].x() - median.x()) < delta)
                auxA[h++] = aPointsSortedByY[i];
        }

        int t = 0;
        for (int i = lowB; i <= highB; i++) {
            if (Math.abs(bPointsSortedByY[i].x() - median.x()) < delta)
                auxB[t++] = bPointsSortedByY[i];
        }


        for (int i = 0; i < h; i++) {
            for (int j = 0; ((j < t) && Math.abs(auxA[i].y() - auxB[j].y()) < delta); j++) {
                double curDistance = auxA[i].distanceTo(auxB[j]);
                if (curDistance < delta) {
                    delta = curDistance;
                    if (delta < closestDistance) {

                        closestDistance = delta;
                    }
                }
            }

        }

        return delta;



    }

    public double distance() {
        return closestDistance;
    }

    public long getCounter() {
        return counter;
    }

    // stably merge a[low .. mid] with a[mid+1 ..high] using aux[low .. high]
    // precondition: a[low .. mid] and a[mid+1 .. high] are sorted subarrays, namely sorted by y coordinate
    // this is the same as in ClosestPair
    private static void merge(Point2D[] a, Point2D[] aux, int low, int mid, int high) {
        // copy to aux[]
        // note this wipes out any values that were previously in aux in the [low,high] range we're currently using

        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
        }

        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) a[k] = aux[j++];   // already finished with the low list ?  then dump the rest of high list
            else if (j > high) a[k] = aux[i++];   // already finished with the high list ?  then dump the rest of low list
            else if (aux[i].compareByY(aux[j]) < 0)
                a[k] = aux[i++]; // aux[i] should be in front of aux[j] ? position and increment the pointer
            else a[k] = aux[j++];
        }
    }
}
