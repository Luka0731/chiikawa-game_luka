package chiikawa2dphysicsengine;

import ch.noseryoung.chiikawa2dphysicsengine.primitives.Circle;
import ch.noseryoung.chiikawa2dphysicsengine.primitives.Line;
import ch.noseryoung.chiikawa2dphysicsengine.primitives.Rectangle;
import ch.noseryoung.chiikawa2dphysicsengine.rigidbody.IntersectionDetector;
import ch.noseryoung.chiikawa2dphysicsengine.rigidbody.Rigidbody;
import org.joml.Vector2f;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class IntersectionDetectorTests {
    private final float EPSILON = 0.000001f;

    // ============================================================================================
    // Line Intersection tests
    // ============================================================================================
    @Test
    public void isPointOnLineShouldReturnTrueTest() {
        Line line = new Line(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(0, 0);

        assertTrue(IntersectionDetector.isPointOnLine(point, line));
    }

    @Test
    public void isPointOnLineShouldReturnTrueTestTwo() {
        Line line = new Line(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(12, 4);

        assertTrue(IntersectionDetector.isPointOnLine(point, line));
    }

    @Test
    public void pointOnVerticalLineShouldReturnTrue() {
        Line line = new Line(new Vector2f(0, 0), new Vector2f(0, 10));
        Vector2f point = new Vector2f(0, 5);

        boolean result = IntersectionDetector.isPointOnLine(point, line);
        assertTrue(result);
    }

    @Test
    public void isPointOnLineShouldReturnTrueTestOne() {
        Line line = new Line(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(0, 0);

        assertTrue(IntersectionDetector.isPointOnLine(point, line));
    }

    @Test
    public void isPointOnLineShouldReturnTrueTestThree() {
        Line line = new Line(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(6, 2);

        assertTrue(IntersectionDetector.isPointOnLine(point, line));
    }

    @Test
    public void isPointOnLineShouldReturnFalseTestOne() {
        Line line = new Line(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(4, 2);

        assertFalse(IntersectionDetector.isPointOnLine(point, line));
    }

    @Test
    public void isPointOnLineShouldReturnTrueTestFour() {
        Line line = new Line(new Vector2f(10, 10), new Vector2f(22, 14));
        Vector2f point = new Vector2f(10, 10);

        assertTrue(IntersectionDetector.isPointOnLine(point, line));
    }

    @Test
    public void isPointOnLineShouldReturnTrueTestFive() {
        Line line = new Line(new Vector2f(10, 10), new Vector2f(22, 14));
        Vector2f point = new Vector2f(16, 12);

        assertTrue(IntersectionDetector.isPointOnLine(point, line));
    }

    @Test
    public void isPointOnLineShouldReturnFalseTestTwo() {
        Line line = new Line(new Vector2f(10, 10), new Vector2f(22, 14));
        Vector2f point = new Vector2f(14, 12);

        assertFalse(IntersectionDetector.isPointOnLine(point, line));
    }

//    TODO: SHOULD THESE BE IMPLEMENTED
//    @Test
//    public void closestPointToLineTestOne() {
//        Line line = new Line(new Vector2f(0, 0), new Vector2f(12, 4));
//        Vector2f point = new Vector2f(6, 2);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, line);
//        Vector2f actualClosestPoint = new Vector2f(6, 2);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToLineTestTwo() {
//        Line line = new Line(new Vector2f(0, 0), new Vector2f(12, 4));
//        Vector2f point = new Vector2f(13, 3);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, line);
//        Vector2f actualClosestPoint = new Vector2f(12, 4);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToLineTestThree() {
//        Line line = new Line(new Vector2f(0, 0), new Vector2f(12, 4));
//        Vector2f point = new Vector2f(7, 4);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, line);
//        Vector2f actualClosestPoint = new Vector2f(7.5f, 2.5f);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToLineTestFour() {
//        Line line = new Line(new Vector2f(10, 10), new Vector2f(22, 14));
//        Vector2f point = new Vector2f(16, 12);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, line);
//        Vector2f actualClosestPoint = new Vector2f(16, 12);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToLineTestFive() {
//        Line line = new Line(new Vector2f(10, 10), new Vector2f(22, 14));
//        Vector2f point = new Vector2f(23, 13);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, line);
//        Vector2f actualClosestPoint = new Vector2f(22, 14);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToLineTestSix() {
//        Line line = new Line(new Vector2f(10, 10), new Vector2f(22, 14));
//        Vector2f point = new Vector2f(17, 14);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, line);
//        Vector2f actualClosestPoint = new Vector2f(17.5f, 12.5f);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }

    // =========================================================================================================
    // Raycast IntersectionDetector tests
    // =========================================================================================================
    // TODO: SHOULD THESE BE IMPLEMENTED?
//    @Test
//    public void pointOnRayShouldReturnTrueTestOne() {
//        Ray2D ray = new Ray2D(new Vector2f(0), new Vector2f(0.948683f, 0.316228f));
//        Vector2f point = new Vector2f(0, 0);
//
//        assertTrue(IntersectionDetector.pointOnRay(point, ray));
//    }
//
//    @Test
//    public void pointOnRayShouldReturnTrueTestTwo() {
//        Ray2D ray = new Ray2D(new Vector2f(0), new Vector2f(0.948683f, 0.316228f));
//        Vector2f point = new Vector2f(6, 2);
//
//        assertTrue(IntersectionDetector.pointOnRay(point, ray));
//    }
//
//    @Test
//    public void pointOnRayShouldReturnFalseTestOne() {
//        Ray2D ray = new Ray2D(new Vector2f(0), new Vector2f(0.948683f, 0.316228f));
//        Vector2f point = new Vector2f(-6, -2);
//
//        assertFalse(IntersectionDetector.pointOnRay(point, ray));
//    }
//
//    @Test
//    public void pointOnRayShouldReturnFalseTestTwo() {
//        Ray2D ray = new Ray2D(new Vector2f(0), new Vector2f(0.948683f, 0.316228f));
//        Vector2f point = new Vector2f(4, 2);
//
//        assertFalse(IntersectionDetector.pointOnRay(point, ray));
//    }
//
//    @Test
//    public void pointOnRayShouldReturnTrueTestThree() {
//        Ray2D ray = new Ray2D(new Vector2f(10, 10), new Vector2f(0.948683f, 0.316228f));
//        Vector2f point = new Vector2f(10, 10);
//
//        assertTrue(IntersectionDetector.pointOnRay(point, ray));
//    }
//
//    @Test
//    public void pointOnRayShouldReturnTrueTestFour() {
//        Ray2D ray = new Ray2D(new Vector2f(10, 10), new Vector2f(0.948683f, 0.316228f));
//        Vector2f point = new Vector2f(16, 12);
//
//        assertTrue(IntersectionDetector.pointOnRay(point, ray));
//    }
//
//    @Test
//    public void pointOnRayShouldReturnFalseTestThree() {
//        Ray2D ray = new Ray2D(new Vector2f(10, 10), new Vector2f(0.948683f, 0.316228f));
//        Vector2f point = new Vector2f(-6 + 10, -2 + 10);
//
//        assertFalse(IntersectionDetector.pointOnRay(point, ray));
//    }
//
//    @Test
//    public void pointOnRayShouldReturnFalseTestFour() {
//        Ray2D ray = new Ray2D(new Vector2f(10, 10), new Vector2f(0.948683f, 0.316228f));
//        Vector2f point = new Vector2f(14, 12);
//
//        assertFalse(IntersectionDetector.pointOnRay(point, ray));
//    }
//
//    @Test
//    public void closestPointToRayTestOne() {
//        Ray2D ray = new Ray2D(new Vector2f(0, 0), new Vector2f(0.948683f, 0.316228f));
//        Vector2f point = new Vector2f(-1, -1);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, ray);
//        Vector2f actualClosestPoint = new Vector2f(0, 0);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToRayTestTwo() {
//        Ray2D ray = new Ray2D(new Vector2f(0, 0), new Vector2f((float)(3.0 / Math.sqrt(10f)), (float)(1.0 / Math.sqrt(10f))));
//        Vector2f point = new Vector2f(6, 2);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, ray);
//        Vector2f actualClosestPoint = new Vector2f(6, 2);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint, EPSILON));
//    }
//
//    @Test
//    public void closestPointToRayTestThree() {
//        Ray2D ray = new Ray2D(new Vector2f(0, 0), new Vector2f((float)(3.0 / Math.sqrt(10f)), (float)(1.0 / Math.sqrt(10f))));
//        Vector2f point = new Vector2f(7, 4);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, ray);
//        Vector2f actualClosestPoint = new Vector2f(7.5f, 2.5f);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint, EPSILON));
//    }
//
//    @Test
//    public void closestPointToRayTestFour() {
//        Ray2D ray = new Ray2D(new Vector2f(10, 10), new Vector2f(0.948683f, 0.316228f));
//        Vector2f point = new Vector2f(9, 9);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, ray);
//        Vector2f actualClosestPoint = new Vector2f(10, 10);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToRayTestFive() {
//        Ray2D ray = new Ray2D(new Vector2f(10, 10), new Vector2f((float)(3.0 / Math.sqrt(10f)), (float)(1.0 / Math.sqrt(10f))));
//        Vector2f point = new Vector2f(16, 12);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, ray);
//        Vector2f actualClosestPoint = new Vector2f(16, 12);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint, EPSILON));
//    }
//
//    @Test
//    public void closestPointToRayTestSix() {
//        Ray2D ray = new Ray2D(new Vector2f(10, 10), new Vector2f((float)(3.0 / Math.sqrt(10f)), (float)(1.0 / Math.sqrt(10f))));
//        Vector2f point = new Vector2f(17, 14);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, ray);
//        Vector2f actualClosestPoint = new Vector2f(17.5f, 12.5f);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint, EPSILON));
//    }

    // =========================================================================================================
    // Circle intersection tester tests
    // =========================================================================================================
    @Test
    public void isPointInCircleShouldReturnTrueTestOne() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody body = new Rigidbody();
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(3, -2);

        boolean result = IntersectionDetector.isPointInCircle(point, circle);
        assertTrue(result);
    }

    @Test
    public void isPointInCircleShouldReturnTrueTestTwo() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody body = new Rigidbody();
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(-4.9f, 0);

        boolean result = IntersectionDetector.isPointInCircle(point, circle);
        assertTrue(result);
    }

    @Test
    public void isPointInCircleShouldReturnFalseTestOne() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody body = new Rigidbody();
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(-6, -6);

        boolean result = IntersectionDetector.isPointInCircle(point, circle);
        assertFalse(result);
    }

    @Test
    public void isPointInCircleShouldReturnTrueTestFour() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody body = new Rigidbody();
        body.setTransform(new Vector2f(10));
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(3 + 10, -2 + 10);

        boolean result = IntersectionDetector.isPointInCircle(point, circle);
        System.out.println(result);
        assertTrue(result);
    }

    @Test
    public void isPointInCircleShouldReturnTrueTestFive() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody body = new Rigidbody();
        body.setTransform(new Vector2f(10));
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(-4.9f + 10, 0 + 10);

        boolean result = IntersectionDetector.isPointInCircle(point, circle);
        assertTrue(result);
    }

    @Test
    public void isPointInCircleShouldReturnFalseTestTwo() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody body = new Rigidbody();
        body.setTransform(new Vector2f(10));
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(-6 + 10, -6 + 10);

        boolean result = IntersectionDetector.isPointInCircle(point, circle);
        assertFalse(result);
    }

//    TODO: IMPLEMENT THESE
//    @Test
//    public void closestPointToCircleTestOne() {
//        Circle circle = new Circle();
//        circle.setRadius(1f);
//        Rigidbody body = new Rigidbody();
//        circle.setRigidbody(body);
//
//        Vector2f point = new Vector2f(5, 0);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, circle);
//        Vector2f actualClosestPoint = new Vector2f(1, 0);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToCircleTestTwo() {
//        Circle circle = new Circle();
//        circle.setRadius(1f);
//        Rigidbody body = new Rigidbody();
//        circle.setRigidbody(body);
//
//        Vector2f point = new Vector2f(2.5f, -2.5f);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, circle);
//        Vector2f actualClosestPoint = new Vector2f(0.5773502f, -0.5773502f);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToCircleTestThree() {
//        Circle circle = new Circle();
//        circle.setRadius(1f);
//        Rigidbody body = new Rigidbody();
//        body.setTransform(new Vector2f(10));
//        circle.setRigidbody(body);
//
//        Vector2f point = new Vector2f(5 + 10, 0 + 10);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, circle);
//        Vector2f actualClosestPoint = new Vector2f(1 + 10, 0 + 10);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToCircleTestFour() {
//        Circle circle = new Circle();
//        circle.setRadius(1f);
//        Rigidbody body = new Rigidbody();
//        body.setTransform(new Vector2f(10));
//        circle.setRigidbody(body);
//
//        Vector2f point = new Vector2f(2.5f + 10, -2.5f + 10);
//
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, circle);
//        Vector2f actualClosestPoint = new Vector2f(0.5773502f + 10, -0.5773502f + 10);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }

    // =========================================================================================================
    // Rectangle intersection tester tests
    // =========================================================================================================
    @Test
    public void isPointInRectangleShouldReturnTrueTestOne() {
        Rectangle box = new Rectangle();
        box.setSize(new Vector2f(10));
        Rigidbody body = new Rigidbody();
        box.setRigidbody(body);

        Vector2f point = new Vector2f(4, 4.3f);

        assertTrue(IntersectionDetector.isPointInRectangle(point, box));
    }

    @Test
    public void isPointInRectangleShouldReturnTrueTestTwo() {
        Rectangle box = new Rectangle();
        box.setSize(new Vector2f(10));
        Rigidbody body = new Rigidbody();
        box.setRigidbody(body);

        Vector2f point = new Vector2f(-4.9f, -4.9f);

        assertTrue(IntersectionDetector.isPointInRectangle(point, box));
    }

    @Test
    public void isPointInRectangleShouldReturnFalseTestOne() {
        Rectangle box = new Rectangle();
        box.setSize(new Vector2f(10));
        Rigidbody body = new Rigidbody();
        box.setRigidbody(body);

        Vector2f point = new Vector2f(0, 5.1f);

        assertFalse(IntersectionDetector.isPointInRectangle(point, box));
    }

    @Test
    public void isPointInRectangleShouldReturnTrueTestThree() {
        Rectangle box = new Rectangle();
        box.setSize(new Vector2f(10));
        Rigidbody body = new Rigidbody();
        body.setTransform(new Vector2f(10));
        box.setRigidbody(body);

        Vector2f point = new Vector2f(4 + 10, 4.3f + 10);

        assertTrue(IntersectionDetector.isPointInRectangle(point, box));
    }

    @Test
    public void isPointInRectangleShouldReturnTrueTestFour() {
        Rectangle box = new Rectangle();
        box.setSize(new Vector2f(10));
        Rigidbody body = new Rigidbody();
        body.setTransform(new Vector2f(10));
        box.setRigidbody(body);

        Vector2f point = new Vector2f(-4.9f + 10, -4.9f + 10);

        assertTrue(IntersectionDetector.isPointInRectangle(point, box));
    }

    @Test
    public void isPointInRectangleShouldReturnFalseTestTwo() {
        Rectangle box = new Rectangle();
        box.setSize(new Vector2f(10));
        Rigidbody body = new Rigidbody();
        body.setTransform(new Vector2f(10));
        box.setRigidbody(body);

        Vector2f point = new Vector2f(0 + 10, 5.1f + 10);

        assertFalse(IntersectionDetector.isPointInRectangle(point, box));
    }

    @Test
    public void pointInRotatedRectangleShouldReturnTrueTestOne() {
        Rectangle box = new Rectangle();
        box.setSize(new Vector2f(10));
        Rigidbody body = new Rigidbody();
        body.setTransform(new Vector2f(0), 45);
        box.setRigidbody(body);

        Vector2f point = new Vector2f(-1, -1);

        assertTrue(IntersectionDetector.isPointInRectangle(point, box));
    }

    @Test
    public void pointInRotatedShouldReturnTrueTestTwo() {
        Rectangle box = new Rectangle();
        box.setSize(new Vector2f(10));
        Rigidbody body = new Rigidbody();
        body.setTransform(new Vector2f(0), 45);
        box.setRigidbody(body);

        Vector2f point = new Vector2f(-3.43553390593f, 3.43553390593f);

        assertTrue(IntersectionDetector.isPointInRectangle(point, box));
    }

    @Test
    public void pointInRotatedShouldReturnFalseTestOne() {
        Rectangle box = new Rectangle();
        box.setSize(new Vector2f(10));
        Rigidbody body = new Rigidbody();
        body.setTransform(new Vector2f(10), 45);
        box.setRigidbody(body);

        Vector2f point = new Vector2f(-3.63553390593f, 3.63553390593f);

        assertFalse(IntersectionDetector.isPointInRectangle(point, box));
    }

    @Test
    public void pointInRotatedRectangleShouldReturnTrueTestThree() {
        Rectangle box = new Rectangle();
        box.setSize(new Vector2f(10));
        Rigidbody body = new Rigidbody();
        body.setTransform(new Vector2f(10), 45);
        box.setRigidbody(body);

        Vector2f point = new Vector2f(-1 + 10, -1 + 10);

        assertTrue(IntersectionDetector.isPointInRectangle(point, box));
    }

    @Test
    public void pointInRotatedShouldReturnTrueTestFour() {
        Rectangle box = new Rectangle();
        box.setSize(new Vector2f(10));
        Rigidbody body = new Rigidbody();
        body.setTransform(new Vector2f(10), 45);
        box.setRigidbody(body);

        Vector2f point = new Vector2f(-3.43553390593f + 10, 3.43553390593f + 10);

        assertTrue(IntersectionDetector.isPointInRectangle(point, box));
    }

    @Test
    public void pointInRotatedShouldReturnFalseTestTwo() {
        Rectangle box = new Rectangle();
        box.setSize(new Vector2f(10));
        Rigidbody body = new Rigidbody();
        body.setTransform(new Vector2f(10), 45);
        box.setRigidbody(body);

        Vector2f point = new Vector2f(-3.63553390593f + 10, 3.63553390593f + 10);

        assertFalse(IntersectionDetector.isPointInRectangle(point, box));
    }


//    TODO: IMPLEMENT THESE FUNCTIONS
//    @Test
//    public void closestPointToRectangleTestOne() {
//        Rectangle box = new Rectangle();
//        box.setSize(new Vector2f(10));
//        Rigidbody body = new Rigidbody();
//        box.setRigidbody(body);
//
//        Vector2f point = new Vector2f(0, 10);
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, box);
//        Vector2f actualClosestPoint = new Vector2f(0, 5);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToRectangleTestTwo() {
//        Rectangle box = new Rectangle();
//        box.setSize(new Vector2f(10));
//        Rigidbody body = new Rigidbody();
//        box.setRigidbody(body);
//
//        Vector2f point = new Vector2f(-6, -4);
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, box);
//        Vector2f actualClosestPoint = new Vector2f(-5, -4);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToRectangleTestThree() {
//        Rectangle box = new Rectangle();
//        box.setSize(new Vector2f(10));
//        Rigidbody body = new Rigidbody();
//        box.setRigidbody(body);
//
//        Vector2f point = new Vector2f(3, -4);
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, box);
//        Vector2f actualClosestPoint = new Vector2f(3, -4);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToRectangleTestFour() {
//        Rectangle box = new Rectangle();
//        box.setSize(new Vector2f(10));
//        Rigidbody body = new Rigidbody();
//        body.setTransform(new Vector2f(10));
//        box.setRigidbody(body);
//
//        Vector2f point = new Vector2f(0 + 10, 10 + 10);
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, box);
//        Vector2f actualClosestPoint = new Vector2f(0 + 10, 5 + 10);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToRectangleTestFive() {
//        Rectangle box = new Rectangle();
//        box.setSize(new Vector2f(10));
//        Rigidbody body = new Rigidbody();
//        body.setTransform(new Vector2f(10));
//        box.setRigidbody(body);
//
//        Vector2f point = new Vector2f(-6 + 10, -4 + 10);
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, box);
//        Vector2f actualClosestPoint = new Vector2f(-5 + 10, -4 + 10);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToRectangleTestSix() {
//        Rectangle box = new Rectangle();
//        box.setSize(new Vector2f(10));
//        Rigidbody body = new Rigidbody();
//        body.setTransform(new Vector2f(10));
//        box.setRigidbody(body);
//
//        Vector2f point = new Vector2f(3 + 10, -4 + 10);
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, box);
//        Vector2f actualClosestPoint = new Vector2f(3 + 10, -4 + 10);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint));
//    }
//
//    @Test
//    public void closestPointToRotatedRectangleTestOne() {
//        Rectangle box = new Rectangle();
//        box.setSize(new Vector2f(10));
//        Rigidbody body = new Rigidbody();
//        body.setTransform(new Vector2f(10), 45);
//        box.setRigidbody(body);
//
//        Vector2f point = new Vector2f(10, 0);
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, box);
//        Vector2f actualClosestPoint = new Vector2f(7.07106781187f, 0);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint, EPSILON));
//    }
//
//    @Test
//    public void closestPointToRotatedRectangleTestTwo() {
//        Rectangle box = new Rectangle();
//        box.setSize(new Vector2f(10));
//        Rigidbody body = new Rigidbody();
//        body.setTransform(new Vector2f(10), 45);
//        box.setRigidbody(body);
//
//        Vector2f point = new Vector2f(-5.5355339f, -5.5355339f);
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, box);
//        Vector2f actualClosestPoint = new Vector2f(-3.5355339f, -3.5355339f);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint, EPSILON));
//    }
//
//    @Test
//    public void closestPointToRotatedRectangleTestThree() {
//        Rectangle box = new Rectangle();
//        box.setSize(new Vector2f(10));
//        Rigidbody body = new Rigidbody();
//        body.setTransform(new Vector2f(10), 45);
//        box.setRigidbody(body);
//
//        Vector2f point = new Vector2f(0, 7.07106781187f);
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, box);
//        Vector2f actualClosestPoint = new Vector2f(0, 7.07106781187f);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint, EPSILON));
//    }
//
//    @Test
//    public void closestPointToRotatedRectangleTestFour() {
//        Rectangle box = new Rectangle();
//        box.setSize(new Vector2f(10));
//        Rigidbody body = new Rigidbody();
//        body.setTransform(new Vector2f(10), 45);
//        box.setRigidbody(body);
//
//        Vector2f point = new Vector2f(10 + 10, 0 + 10);
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, box);
//        Vector2f actualClosestPoint = new Vector2f(7.07106781187f + 10, 0 + 10);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint, EPSILON));
//    }
//
//    @Test
//    public void closestPointToRotatedRectangleTestFive() {
//        Rectangle box = new Rectangle();
//        box.setSize(new Vector2f(10));
//        Rigidbody body = new Rigidbody();
//        body.setTransform(new Vector2f(10), 45);
//        box.setRigidbody(body);
//
//        Vector2f point = new Vector2f(-5.5355339f + 10, -5.5355339f + 10);
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, box);
//        Vector2f actualClosestPoint = new Vector2f(-3.5355339f + 10, -3.5355339f + 10);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint, EPSILON));
//    }
//
//    @Test
//    public void closestPointToRotatedRectangleTestSix() {
//        Rectangle box = new Rectangle();
//        box.setSize(new Vector2f(10));
//        Rigidbody body = new Rigidbody();
//        body.setTransform(new Vector2f(10), 45);
//        box.setRigidbody(body);
//
//        Vector2f point = new Vector2f(0 + 10, 7.07106781187f + 10);
//        Vector2f calculatedClosestPoint = IntersectionDetector.closestPoint(point, box);
//        Vector2f actualClosestPoint = new Vector2f(0 + 10, 7.07106781187f + 10);
//
//        assertTrue(JMath.compare(calculatedClosestPoint, actualClosestPoint, EPSILON));
//    }
}