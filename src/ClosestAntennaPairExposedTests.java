import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class ClosestAntennaPairExposedTests {

    // base case: empty pointsA
    @Test
    @Tag("score:2")
    @DisplayName("Base case test 1")
    void baseCaseTest1() {
        Point2D p1 = new Point2D(-0.2, 0.4);
        Point2D p2 = new Point2D(0.15, -0.1);
        Point2D p3 = new Point2D(-0.06, 0.15);
        Point2D[] pointsA = {p1, p2, p3};
        Point2D[] pointsB = {};
        ClosestAntennaPair closestAntennaPair = new ClosestAntennaPair(pointsA, pointsB);
        assertEquals(Double.POSITIVE_INFINITY, closestAntennaPair.distance());
        assertEquals(0, closestAntennaPair.getCounter());
    }

    // base case: empty pointsB
    @Test
    @Tag("score:2")
    @DisplayName("Base case test 2")
    void baseCaseTest2() {
        Point2D p1 = new Point2D(-0.2, 0.4);
        Point2D p2 = new Point2D(0.15, -0.1);
        Point2D p3 = new Point2D(-0.06, 0.15);
        Point2D[] pointsA = {};
        Point2D[] pointsB = {p1, p2, p3};
        ClosestAntennaPair closestAntennaPair = new ClosestAntennaPair(pointsA, pointsB);
        assertEquals(Double.POSITIVE_INFINITY, closestAntennaPair.distance());
        assertEquals(0, closestAntennaPair.getCounter());
    }

    // base case: one point in pointsA
    @Test
    @Tag("score:3")
    @DisplayName("Base case test 3")
    void baseCaseTest3() {
        Point2D p1 = new Point2D(-0.2, 0.4);
        Point2D p2 = new Point2D(0.15, -0.1);
        Point2D p3 = new Point2D(-0.06, 0.15);
        Point2D[] pointsA = {p2};
        Point2D[] pointsB = {p1, p3};
        ClosestAntennaPair closestAntennaPair = new ClosestAntennaPair(pointsA, pointsB);
        assertEquals(p2.distanceTo(p3), closestAntennaPair.distance());
        assertEquals(1, closestAntennaPair.getCounter());
    }

    // base case: one point in pointsB
    @Test
    @Tag("score:3")
    @DisplayName("Base case test 4")
    void baseCaseTest4() {
        Point2D p1 = new Point2D(-0.2, 0.4);
        Point2D p2 = new Point2D(0.15, -0.1);
        Point2D p3 = new Point2D(-0.06, 0.15);
        Point2D[] pointsA = {p1, p3};
        Point2D[] pointsB = {p2};
        ClosestAntennaPair closestAntennaPair = new ClosestAntennaPair(pointsA, pointsB);
        assertEquals(p2.distanceTo(p3), closestAntennaPair.distance());
        assertEquals(1, closestAntennaPair.getCounter());
    }

    // recursive case: aPoints on the right of bPoints
    @Test
    @Tag("score:3")
    @DisplayName("Recursive case 1")
    void recursiveTest1() {
        Point2D p1 = new Point2D(0.1, 1);
        Point2D p2 = new Point2D(-0.1, 1);
        Point2D p3 = new Point2D(0.2, 2);
        Point2D p4 = new Point2D(-0.2, 2);
        Point2D p5 = new Point2D(0.3, 3);
        Point2D p6 = new Point2D(-0.3, 3);
        Point2D[] aPoints = {p1, p3, p5};
        Point2D[] bPoints = {p2, p4, p6};

        ClosestAntennaPair pair = new ClosestAntennaPair(aPoints, bPoints);
        assertEquals(0.2, pair.distance());
        assertTrue(pair.getCounter() > 1);
    }

    // recursive case: aPoints on the left of bPoints
    @Test
    @Tag("score:3")
    @DisplayName("Recursive case 2")
    void recursiveTest2() {
        Point2D p1 = new Point2D(0.1, 1);
        Point2D p2 = new Point2D(-0.1, 1);
        Point2D p3 = new Point2D(0.2, 2);
        Point2D p4 = new Point2D(-0.2, 2);
        Point2D p5 = new Point2D(0.3, 3);
        Point2D p6 = new Point2D(-0.3, 3);
        Point2D[] aPoints = {p2, p4, p6};
        Point2D[] bPoints = {p1, p3, p5};

        ClosestAntennaPair pair = new ClosestAntennaPair(aPoints, bPoints);
        assertEquals(0.2, pair.distance());
        assertTrue(pair.getCounter() > 1);
    }

    // recursive case: aPoints below bPoints
    @Test
    @Tag("score:2")
    @DisplayName("Recursive case 3")
    void recursiveTest3() {
        Point2D p1 = new Point2D(1, 0.1);
        Point2D p2 = new Point2D(1, -0.1);
        Point2D p3 = new Point2D(2, 0.2);
        Point2D p4 = new Point2D(2, -0.2);
        Point2D p5 = new Point2D(3, 0.3);
        Point2D p6 = new Point2D(3, -0.3);
        Point2D[] aPoints = {p2, p4, p6};
        Point2D[] bPoints = {p1, p3, p5};


        ClosestAntennaPair pair = new ClosestAntennaPair(aPoints, bPoints);
        assertEquals(0.2, pair.distance());
    }

    // recursive case: aPoints above bPoints
    @Test
    @Tag("score:2")
    @DisplayName("Recursive case 4")
    void recursiveTest4() {
        Point2D p1 = new Point2D(1, 0.1);
        Point2D p2 = new Point2D(1, -0.1);
        Point2D p3 = new Point2D(2, 0.2);
        Point2D p4 = new Point2D(2, -0.2);
        Point2D p5 = new Point2D(3, 0.3);
        Point2D p6 = new Point2D(3, -0.3);
        Point2D[] aPoints = {p1, p3, p5};
        Point2D[] bPoints = {p2, p4, p6};


        ClosestAntennaPair pair = new ClosestAntennaPair(aPoints, bPoints);
        assertEquals(0.2, pair.distance());
        assertTrue(pair.getCounter() > 1);
    }

    // recursive case: aPoints and bPoints are well mixed
    @Test
    @Tag("score:3")
    @DisplayName("Recursive case 5")
    void recursiveTest5() {
        Point2D p1 = new Point2D(-0.2, 0.4);
        Point2D p2 = new Point2D(0.15, -0.1);
        Point2D p3 = new Point2D(-0.06, 0.15);
        Point2D p4 = new Point2D(0.3, 0.5);
        Point2D p5 = new Point2D(-0.15, 0.25);
        Point2D p6 = new Point2D(0.15, 0.3);
        Point2D p7 = new Point2D(0.35, -0.1);

        Point2D[] aPoints = {p1, p3, p4, p7};
        Point2D[] bPoints = {p2, p5, p6};

        ClosestAntennaPair closestAntennaPair = new ClosestAntennaPair(aPoints, bPoints);
        assertEquals(p3.distanceTo(p5), closestAntennaPair.distance());
        assertTrue(closestAntennaPair.getCounter() > 1);
    }

    // Test with random points. Feel free to use this as a template to make your own tests!
    @Test
    @Tag("score:2")
    @DisplayName("Recursive case 6")
    void recursiveTest6() {
        // The test may take too long and potentially lead to a timeout error if n > 10000
        int n = 100;
        // Each point in A will take value in the box [aXMin, aXMax) x [aYMin, aYMax)
        // and each point in B will be in the box [bXMin, bXMax) x [bYMin, bYMax).
        // Feel free to modify the values to change the size and location of the boxes.
        // However, if your box is invalid, this test will fail with a RunTimeException.
        double aXMin = -1;
        double aXMax = 1;
        double aYMin = 1;
        double aYMax = 2;
        double bXMin = 1;
        double bXMax = 2;
        double bYMin = 0;
        double bYMax = 4;

        if (aXMax < aXMin || aYMax < aYMin || bXMax < bXMin || bYMax < bYMin) {
            throw new RuntimeException("Box constraint is not valid");
        }


        /*
        Point2D[] aPoints = new Point2D[5];
        aPoints[0] = new Point2D(0.25355665203642985, 1.3678899789396362);
        aPoints[1] = new Point2D(0.5454320359712006, 1.5436666716985334);
        aPoints[2] = new Point2D(0.43341253414302816, 1.1228110984441362);
        aPoints[3] = new Point2D(0.6353731093541, 1.6730736749489865);
        aPoints[4] = new Point2D(0.14595935512076985, 1.3463447459733229);
        


        Point2D[] aPoints = new Point2D[10];

        aPoints[0] = new Point2D(-0.5380150359035685, 1.0574311496536621);
        aPoints[1] = new Point2D(0.25355665203642985, 1.3678899789396362);
        aPoints[2] = new Point2D(-0.587523158979963, 1.065063367743034);
        aPoints[3] = new Point2D(-0.8618066484079872, 1.0238581474079997);
        aPoints[4] = new Point2D(-0.8434479274515587, 1.73035755963038);
        aPoints[5] = new Point2D(-0.32742222525110676, 1.2993971494701952);
        aPoints[6] = new Point2D(0.5454320359712006, 1.5436666716985334);
        aPoints[7] = new Point2D(0.43341253414302816, 1.1228110984441362);
        aPoints[8] = new Point2D(0.14595935512076985, 1.3463447459733229);
        aPoints[9] = new Point2D(0.6353731093541, 1.6730736749489865);
        */



        /*
        Point2D[] bPoints = new Point2D[5];

        bPoints[0] = new Point2D(1.7051494582256153, 1.3876135155756506);
        bPoints[1] = new Point2D(1.2354436270569238, 0.7989972491774071);
        bPoints[2] = new Point2D(1.2134233624227102, 0.15297157181469467);
        bPoints[3] = new Point2D(1.4454542037616664, 1.0085708162423415);
        bPoints[4] = new Point2D(1.9216743570993886, 2.268938408289287);


        Point2D[] bPoints = new Point2D[10];
        bPoints[0] = new Point2D(1.421793369364576, 3.7157293436506613);
        bPoints[1] = new Point2D(1.7051494582256153, 1.3876135155756506);
        bPoints[2] = new Point2D(1.2354436270569238, 0.7989972491774071);
        bPoints[3] = new Point2D(1.86493912998202, 3.172039445108728);
        bPoints[4] = new Point2D(1.2134233624227102, 0.15297157181469467);
        bPoints[5] = new Point2D(1.4454542037616664, 1.0085708162423415);
        bPoints[6] = new Point2D(1.364582010543339, 2.7824597078439375);
        bPoints[7] = new Point2D(1.704826664284856, 3.1911414235068407);
        bPoints[8] = new Point2D(1.5670722606838203, 2.340457368440712);
        bPoints[9] = new Point2D(1.9216743570993886, 2.268938408289287);
        */



        Point2D[] aPoints = new Point2D[n];
        Point2D[] bPoints = new Point2D[n];

        for (int i = 0; i < n; i++) {


            double aX = aXMin + Math.random() * (aXMax - aXMin);
            double aY = aYMin + Math.random() * (aYMax - aYMin);
            double bX = bXMin + Math.random() * (bXMax - bXMin);
            double bY = bYMin + Math.random() * (bYMax - bYMin);
            aPoints[i] = new Point2D(aX, aY);
            bPoints[i] = new Point2D(bX, bY);
        }



        ClosestAntennaPair closestAntennaPair = new ClosestAntennaPair(aPoints, bPoints);
        ClosestAntennaPairBruteForce closestAntennaPairBruteForce = new ClosestAntennaPairBruteForce(aPoints, bPoints);
        assertEquals(closestAntennaPairBruteForce.getClosestDistance(), closestAntennaPair.distance());
    }


    @Test
    @DisplayName("Random Points")
    void randomPoints() {
        int n = 200;
        Point2D[] arrayA = new Point2D[n];
        Point2D[] arrayB = new Point2D[n];
        for (int i = 0; i < n; i++) {
            float xA = new Random().nextFloat();
            float yA = new Random().nextFloat();
            Point2D pointA = new Point2D(xA, yA);
            float xB = new Random().nextFloat();
            float yB = new Random().nextFloat();
            Point2D pointB = new Point2D(xB, yB);
            arrayA[i] = pointA;
            arrayB[i] = pointB;
        }
        ClosestAntennaPair closestAntennaPair = new ClosestAntennaPair(arrayA, arrayB);
        ClosestAntennaPairBruteForce closestAntennaPairBruteForce = new ClosestAntennaPairBruteForce(arrayA, arrayB);
        assertEquals(closestAntennaPairBruteForce.getClosestDistance(), closestAntennaPair.distance());
        assertTrue(closestAntennaPair.getCounter() > 1);
    }

}
