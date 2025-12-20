package cjs2dphysicsengine;

import cjs2dphysicsengine.primitives.AABR;
import cjs2dphysicsengine.primitives.Circle;
import cjs2dphysicsengine.primitives.Line;
import cjs2dphysicsengine.primitives.Ray;
import cjs2dphysicsengine.primitives.Rectangle;
import cjs2dphysicsengine.rigidbody.IntersectionDetector;
import cjs2dphysicsengine.rigidbody.Rigidbody;
import org.joml.Vector2f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntersectionDetectorTests {

    // ////////////////////////////////////////////////////////////////// //
    //    point vs primitive tests                                        //
    // ////////////////////////////////////////////////////////////////// //

    // |--- isPointOnLine tests ---|

    @Test
    public void pointOnLineBeginningShouldReturnTrueTest() {
        Line line = new Line(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(0, 0);
        boolean result = IntersectionDetector.isPointOnLine(point, line);
        assertTrue(result);
    }

    @Test
    public void pointOnLineEndShouldReturnTrueTest() {
        Line line = new Line(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(12, 4);
        boolean result = IntersectionDetector.isPointOnLine(point, line);
        assertTrue(result);
    }

    @Test
    public void pointOnVerticalLineShouldReturnTrueTest() {
        Line line = new Line(new Vector2f(0, 0), new Vector2f(0, 10));
        Vector2f point = new Vector2f(0, 5);
        boolean result = IntersectionDetector.isPointOnLine(point, line);
        assertTrue(result);
    }

    @Test
    public void pointNotOnLineShouldReturnFalseTest() {
        Line line = new Line(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(-2, 3);
        boolean result = IntersectionDetector.isPointOnLine(point, line);
        assertFalse(result);
    }

    @Test
    public void pointVeryNearToLineShouldReturnTrueTest() {
        Line line = new Line(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(Float.MIN_VALUE, 0);
        boolean result = IntersectionDetector.isPointOnLine(point, line);
        assertTrue(result);
    }

    @Test
    public void pointInMiddleOfLineShouldReturnTrueTest() {
        Line line = new Line(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(6, 2);
        boolean result = IntersectionDetector.isPointOnLine(point, line);
        assertTrue(result);
    }

    @Test
    public void pointWithWrongSlopeOnLineShouldReturnFalseTest() {
        Line line = new Line(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(4, 2);
        boolean result = IntersectionDetector.isPointOnLine(point, line);
        assertFalse(result);
    }

    @Test
    public void pointOnLineAtOffsetPositionShouldReturnTrueTest() {
        Line line = new Line(new Vector2f(10, 10), new Vector2f(22, 14));
        Vector2f point = new Vector2f(16, 12);
        boolean result = IntersectionDetector.isPointOnLine(point, line);
        assertTrue(result);
    }

    @Test
    public void pointNotOnLineAtOffsetPositionShouldReturnFalseTest() {
        Line line = new Line(new Vector2f(10, 10), new Vector2f(22, 14));
        Vector2f point = new Vector2f(14, 12);
        boolean result = IntersectionDetector.isPointOnLine(point, line);
        assertFalse(result);
    }


    // |--- isPointInCircle tests ---|

    @Test
    public void pointInCircleShouldReturnTrueTest() {
        Circle circle = new Circle(3.0f, new Rigidbody());
        Vector2f point = new Vector2f(0, 0);
        boolean result = IntersectionDetector.isPointInCircle(point, circle);
        assertTrue(result);
    }

    @Test
    public void pointNextToCircleShouldReturnFalseTest() {
        Circle circle = new Circle(3.0f, new Rigidbody());
        Vector2f point = new Vector2f(4.0f, 0);
        boolean result = IntersectionDetector.isPointInCircle(point, circle);
        assertFalse(result);
    }

    @Test
    public void pointVeryNearToCircleShouldReturnTrueTest() {
        Circle circle = new Circle(3.0f, new Rigidbody());
        Vector2f point = new Vector2f(3.0f + Float.MIN_VALUE, 0);
        boolean result = IntersectionDetector.isPointInCircle(point, circle);
        assertTrue(result);
    }

    @Test
    public void pointNearEdgeInsideCircleShouldReturnTrueTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        Vector2f point = new Vector2f(-4.9f, 0);
        boolean result = IntersectionDetector.isPointInCircle(point, circle);
        assertTrue(result);
    }

    @Test
    public void pointInCircleAtOffsetPositionShouldReturnTrueTest() {
        Circle circle = new Circle(5.0f, new Rigidbody(new Vector2f(10, 10), 0));
        Vector2f point = new Vector2f(13, 8);
        boolean result = IntersectionDetector.isPointInCircle(point, circle);
        assertTrue(result);
    }

    @Test
    public void pointOutsideCircleAtOffsetPositionShouldReturnFalseTest() {
        Circle circle = new Circle(5.0f, new Rigidbody(new Vector2f(10, 10), 0));
        Vector2f point = new Vector2f(4, 4);
        boolean result = IntersectionDetector.isPointInCircle(point, circle);
        assertFalse(result);
    }

    @Test
    public void pointNearEdgeInsideCircleAtOffsetShouldReturnTrueTest() {
        Circle circle = new Circle(5.0f, new Rigidbody(new Vector2f(10, 10), 0));
        Vector2f point = new Vector2f(5.1f, 10);
        boolean result = IntersectionDetector.isPointInCircle(point, circle);
        assertTrue(result);
    }


    // |--- isPointInAABR tests ---|

    @Test
    public void pointInAABRShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Vector2f point = new Vector2f(3.0f, 0);
        boolean result = IntersectionDetector.isPointInAABR(point, aabr);
        assertTrue(result);
    }

    @Test
    public void pointInCenterOfAABRShouldReturnTrueTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(5, 5), 0);
        AABR aabr = new AABR(new Vector2f(0, 0), new Vector2f(10, 10), rigidbody);
        Vector2f point = new Vector2f(5, 5);
        boolean result = IntersectionDetector.isPointInAABR(point, aabr);
        assertTrue(result);
    }

    @Test
    public void pointOnEdgeOfAABRShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Vector2f min = aabr.getMin();
        Vector2f point = new Vector2f(min.x, 0);
        boolean result = IntersectionDetector.isPointInAABR(point, aabr);
        assertTrue(result);
    }

    @Test
    public void pointOutsideAABRShouldReturnFalseTest() {
        AABR aabr = new AABR();
        Vector2f max = aabr.getMax();
        Vector2f point = new Vector2f(max.x + 1, max.y + 1);
        boolean result = IntersectionDetector.isPointInAABR(point, aabr);
        assertFalse(result);
    }

    @Test
    public void pointOnCornerOfAABRShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Vector2f max = aabr.getMax();
        boolean result = IntersectionDetector.isPointInAABR(max, aabr);
        assertTrue(result);
    }


    // |--- isPointInRectangle tests ---|

    @Test
    public void pointInRectangleShouldReturnTrueTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody);
        Vector2f point = new Vector2f(0, 0);
        boolean result = IntersectionDetector.isPointInRectangle(point, rectangle);
        assertTrue(result);
    }

    @Test
    public void pointOutsideRectangleShouldReturnFalseTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody);
        Vector2f point = new Vector2f(10, 10);
        boolean result = IntersectionDetector.isPointInRectangle(point, rectangle);
        assertFalse(result);
    }

    @Test
    public void pointOnEdgeOfRectangleShouldReturnTrueTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody);
        Vector2f point = new Vector2f(5, 0);
        boolean result = IntersectionDetector.isPointInRectangle(point, rectangle);
        assertTrue(result);
    }

    @Test
    public void pointInRotatedRectangleShouldReturnTrueTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 45);
        Rectangle rectangle = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody);
        Vector2f point = new Vector2f(0, 0);
        boolean result = IntersectionDetector.isPointInRectangle(point, rectangle);
        assertTrue(result);
    }

    @Test
    public void pointOutsideRotatedRectangleShouldReturnFalseTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 45);
        Rectangle rectangle = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody);
        Vector2f point = new Vector2f(6, 6);
        boolean result = IntersectionDetector.isPointInRectangle(point, rectangle);
        assertFalse(result);
    }

    @Test
    public void pointInRotatedRectangleAtOffsetShouldReturnTrueTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(10, 10), 45);
        Rectangle rectangle = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody);
        Vector2f point = new Vector2f(9, 9);
        boolean result = IntersectionDetector.isPointInRectangle(point, rectangle);
        assertTrue(result);
    }

    @Test
    public void pointOutsideRotatedRectangleAtOffsetShouldReturnFalseTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(10, 10), 45);
        Rectangle rectangle = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody);
        Vector2f point = new Vector2f(3.36f, 3.36f);
        boolean result = IntersectionDetector.isPointInRectangle(point, rectangle);
        assertFalse(result);
    }



    // ////////////////////////////////////////////////////////////////// //
    //    line vs primitive tests                                         //
    // ////////////////////////////////////////////////////////////////// //

    // |--- doesLineIntersectCircle tests ---|

    @Test
    public void lineInsideCircleShouldReturnTrueTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        Line line = new Line(new Vector2f(-2, 0), new Vector2f(2, 0));
        boolean result = IntersectionDetector.doesLineIntersectCircle(line, circle);
        assertTrue(result);
    }

    @Test
    public void lineThroughCircleShouldReturnTrueTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        Line line = new Line(new Vector2f(-10, 0), new Vector2f(10, 0));
        boolean result = IntersectionDetector.doesLineIntersectCircle(line, circle);
        assertTrue(result);
    }

    @Test
    public void lineOutsideCircleShouldReturnFalseTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        Line line = new Line(new Vector2f(10, 10), new Vector2f(20, 10));
        boolean result = IntersectionDetector.doesLineIntersectCircle(line, circle);
        assertFalse(result);
    }

    @Test
    public void lineTangentToCircleShouldReturnTrueTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        Line line = new Line(new Vector2f(-10, 5), new Vector2f(10, 5));
        boolean result = IntersectionDetector.doesLineIntersectCircle(line, circle);
        assertTrue(result);
    }

    @Test
    public void lineStartsInCircleShouldReturnTrueTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        Line line = new Line(new Vector2f(0, 0), new Vector2f(20, 0));
        boolean result = IntersectionDetector.doesLineIntersectCircle(line, circle);
        assertTrue(result);
    }

    @Test
    public void lineEndsInCircleShouldReturnTrueTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        Line line = new Line(new Vector2f(-20, 0), new Vector2f(0, 0));
        boolean result = IntersectionDetector.doesLineIntersectCircle(line, circle);
        assertTrue(result);
    }


    // |--- doesLineIntersectAABR tests ---|

    @Test
    public void lineInsideAABRShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Line line = new Line(new Vector2f(-2, 0), new Vector2f(2, 0));
        boolean result = IntersectionDetector.doesLineIntersectAABR(line, aabr);
        assertTrue(result);
    }

    @Test
    public void lineThroughAABRShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Line line = new Line(new Vector2f(-20, 0), new Vector2f(20, 0));
        boolean result = IntersectionDetector.doesLineIntersectAABR(line, aabr);
        assertTrue(result);
    }

    @Test
    public void lineOutsideAABRShouldReturnFalseTest() {
        AABR aabr = new AABR();
        Vector2f max = aabr.getMax();
        Line line = new Line(new Vector2f(max.x + 5, max.y + 5), new Vector2f(max.x + 10, max.y + 10));
        boolean result = IntersectionDetector.doesLineIntersectAABR(line, aabr);
        assertFalse(result);
    }

    @Test
    public void lineStartsInAABRShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Line line = new Line(new Vector2f(0, 0), new Vector2f(50, 50));
        boolean result = IntersectionDetector.doesLineIntersectAABR(line, aabr);
        assertTrue(result);
    }

    @Test
    public void verticalLineThroughAABRShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Line line = new Line(new Vector2f(0, -50), new Vector2f(0, 50));
        boolean result = IntersectionDetector.doesLineIntersectAABR(line, aabr);
        assertTrue(result);
    }

    @Test
    public void diagonalLineThroughAABRShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Line line = new Line(new Vector2f(-20, -20), new Vector2f(20, 20));
        boolean result = IntersectionDetector.doesLineIntersectAABR(line, aabr);
        assertTrue(result);
    }


    // |--- doesLineIntersectRectangle tests ---|

    @Test
    public void lineInsideRectangleShouldReturnTrueTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle = new Rectangle(new Vector2f(-10, -10), new Vector2f(10, 10), rigidbody);
        Line line = new Line(new Vector2f(-2, 0), new Vector2f(2, 0));
        boolean result = IntersectionDetector.doesLineIntersectRectangle(line, rectangle);
        assertTrue(result);
    }

    @Test
    public void lineThroughRectangleShouldReturnTrueTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle = new Rectangle(new Vector2f(-10, -10), new Vector2f(10, 10), rigidbody);
        Line line = new Line(new Vector2f(-20, 0), new Vector2f(20, 0));
        boolean result = IntersectionDetector.doesLineIntersectRectangle(line, rectangle);
        assertTrue(result);
    }

    @Test
    public void lineOutsideRectangleShouldReturnFalseTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle = new Rectangle(new Vector2f(-10, -10), new Vector2f(10, 10), rigidbody);
        Line line = new Line(new Vector2f(20, 20), new Vector2f(30, 30));
        boolean result = IntersectionDetector.doesLineIntersectRectangle(line, rectangle);
        assertFalse(result);
    }

    @Test
    public void lineThroughRotatedRectangleShouldReturnTrueTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 45);
        Rectangle rectangle = new Rectangle(new Vector2f(-10, -10), new Vector2f(10, 10), rigidbody);
        Line line = new Line(new Vector2f(-20, 0), new Vector2f(20, 0));
        boolean result = IntersectionDetector.doesLineIntersectRectangle(line, rectangle);
        assertTrue(result);
    }

    @Test
    public void lineOutsideRotatedRectangleShouldReturnFalseTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 45);
        Rectangle rectangle = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody);
        Line line = new Line(new Vector2f(20, 20), new Vector2f(30, 30));
        boolean result = IntersectionDetector.doesLineIntersectRectangle(line, rectangle);
        assertFalse(result);
    }



    // ////////////////////////////////////////////////////////////////// //
    //    raycasting tests                                                //
    // ////////////////////////////////////////////////////////////////// //

    // |--- raycast circle ---|

    @Test
    public void raycastHitsCircleShouldReturnTrueTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        Ray ray = new Ray(new Vector2f(-10, 0), new Vector2f(1, 0));
        boolean result = IntersectionDetector.raycast(circle, ray);
        assertTrue(result);
    }

    @Test
    public void raycastMissesCircleShouldReturnFalseTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        Ray ray = new Ray(new Vector2f(-10, 10), new Vector2f(1, 0));
        boolean result = IntersectionDetector.raycast(circle, ray);
        assertFalse(result);
    }

    @Test
    public void raycastFromInsideCircleShouldReturnTrueTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(1, 0));
        boolean result = IntersectionDetector.raycast(circle, ray);
        assertTrue(result);
    }

    @Test
    public void raycastAwayFromCircleShouldReturnFalseTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        Ray ray = new Ray(new Vector2f(-10, 0), new Vector2f(-1, 0));
        boolean result = IntersectionDetector.raycast(circle, ray);
        assertFalse(result);
    }

    @Test
    public void raycastDiagonalHitsCircleShouldReturnTrueTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        Ray ray = new Ray(new Vector2f(-10, -10), new Vector2f(1, 1));
        boolean result = IntersectionDetector.raycast(circle, ray);
        assertTrue(result);
    }


    // |--- raycast AABR ---|

    @Test
    public void raycastHitsAABRShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Ray ray = new Ray(new Vector2f(-20, 0), new Vector2f(1, 0));
        boolean result = IntersectionDetector.raycast(aabr, ray);
        assertTrue(result);
    }

    @Test
    public void raycastMissesAABRShouldReturnFalseTest() {
        AABR aabr = new AABR();
        Vector2f max = aabr.getMax();
        Ray ray = new Ray(new Vector2f(-20, max.y + 5), new Vector2f(1, 0));
        boolean result = IntersectionDetector.raycast(aabr, ray);
        assertFalse(result);
    }

    @Test
    public void raycastFromInsideAABRShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(1, 0));
        boolean result = IntersectionDetector.raycast(aabr, ray);
        assertTrue(result);
    }

    @Test
    public void raycastAwayFromAABRShouldReturnFalseTest() {
        AABR aabr = new AABR();
        Ray ray = new Ray(new Vector2f(-20, 0), new Vector2f(-1, 0));
        boolean result = IntersectionDetector.raycast(aabr, ray);
        assertFalse(result);
    }

    @Test
    public void raycastDiagonalHitsAABRShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Ray ray = new Ray(new Vector2f(-20, -20), new Vector2f(1, 1));
        boolean result = IntersectionDetector.raycast(aabr, ray);
        assertTrue(result);
    }


    // |--- raycast rectangle ---|

    @Test
    public void raycastHitsRectangleShouldReturnTrueTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle = new Rectangle(new Vector2f(-10, -10), new Vector2f(10, 10), rigidbody);
        Ray ray = new Ray(new Vector2f(-20, 0), new Vector2f(1, 0));
        boolean result = IntersectionDetector.raycast(rectangle, ray);
        assertTrue(result);
    }

    @Test
    public void raycastMissesRectangleShouldReturnFalseTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle = new Rectangle(new Vector2f(-10, -10), new Vector2f(10, 10), rigidbody);
        Ray ray = new Ray(new Vector2f(-20, 20), new Vector2f(1, 0));
        boolean result = IntersectionDetector.raycast(rectangle, ray);
        assertFalse(result);
    }

    @Test
    public void raycastFromInsideRectangleShouldReturnTrueTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle = new Rectangle(new Vector2f(-10, -10), new Vector2f(10, 10), rigidbody);
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(1, 0));
        boolean result = IntersectionDetector.raycast(rectangle, ray);
        assertTrue(result);
    }

    @Test
    public void raycastHitsRotatedRectangleShouldReturnTrueTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 45);
        Rectangle rectangle = new Rectangle(new Vector2f(-10, -10), new Vector2f(10, 10), rigidbody);
        Ray ray = new Ray(new Vector2f(-20, 0), new Vector2f(1, 0));
        boolean result = IntersectionDetector.raycast(rectangle, ray);
        assertTrue(result);
    }

    @Test
    public void raycastMissesRotatedRectangleShouldReturnFalseTest() {
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 45);
        Rectangle rectangle = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody);
        Ray ray = new Ray(new Vector2f(-20, 15), new Vector2f(6, 6));
        boolean result = IntersectionDetector.raycast(rectangle, ray);
        assertFalse(result);
    }



    // ////////////////////////////////////////////////////////////////// //
    //    shape vs shape tests                                            //
    // ////////////////////////////////////////////////////////////////// //

    // |--- doesCircleIntersectCircle ---|

    @Test
    public void overlappingCirclesShouldReturnTrueTest() {
        Circle circle1 = new Circle(5.0f, new Rigidbody());
        Circle circle2 = new Circle(5.0f, new Rigidbody(new Vector2f(8, 0), 0));
        boolean result = IntersectionDetector.doesCircleIntersectCircle(circle1, circle2);
        assertTrue(result);
    }

    @Test
    public void touchingCirclesShouldReturnTrueTest() {
        Circle circle1 = new Circle(5.0f, new Rigidbody());
        Circle circle2 = new Circle(5.0f, new Rigidbody(new Vector2f(10, 0), 0));
        boolean result = IntersectionDetector.doesCircleIntersectCircle(circle1, circle2);
        assertTrue(result);
    }

    @Test
    public void separateCirclesShouldReturnFalseTest() {
        Circle circle1 = new Circle(5.0f, new Rigidbody());
        Circle circle2 = new Circle(5.0f, new Rigidbody(new Vector2f(15, 0), 0));
        boolean result = IntersectionDetector.doesCircleIntersectCircle(circle1, circle2);
        assertFalse(result);
    }

    @Test
    public void concentricCirclesShouldReturnTrueTest() {
        Circle circle1 = new Circle(5.0f, new Rigidbody());
        Circle circle2 = new Circle(3.0f, new Rigidbody());
        boolean result = IntersectionDetector.doesCircleIntersectCircle(circle1, circle2);
        assertTrue(result);
    }


    // |--- doesCircleIntersectAABR ---|

    @Test
    public void circleOverlapsAABRShouldReturnTrueTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        AABR aabr = new AABR();
        boolean result = IntersectionDetector.doesCircleIntersectAABR(circle, aabr);
        assertTrue(result);
    }

    @Test
    public void circleOutsideAABRShouldReturnFalseTest() {
        Circle circle = new Circle(5.0f, new Rigidbody(new Vector2f(50, 50), 0));
        AABR aabr = new AABR();
        boolean result = IntersectionDetector.doesCircleIntersectAABR(circle, aabr);
        assertFalse(result);
    }

    @Test
    public void circleTouchesAABRCornerShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Vector2f max = aabr.getMax();
        Circle circle = new Circle(5.0f, new Rigidbody(new Vector2f(max.x + 3, max.y + 3), 0));
        boolean result = IntersectionDetector.doesCircleIntersectAABR(circle, aabr);
        assertTrue(result);
    }

    @Test
    public void circleTouchesAABREdgeShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Vector2f max = aabr.getMax();
        Circle circle = new Circle(5.0f, new Rigidbody(new Vector2f(max.x + 4, 0), 0));
        boolean result = IntersectionDetector.doesCircleIntersectAABR(circle, aabr);
        assertTrue(result);
    }


    // |--- doesCircleIntersectRectangle ---|

    @Test
    public void circleOverlapsRectangleShouldReturnTrueTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle = new Rectangle(new Vector2f(-10, -10), new Vector2f(10, 10), rigidbody);
        boolean result = IntersectionDetector.doesCircleIntersectRectangle(circle, rectangle);
        assertTrue(result);
    }

    @Test
    public void circleOutsideRectangleShouldReturnFalseTest() {
        Circle circle = new Circle(5.0f, new Rigidbody(new Vector2f(50, 50), 0));
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle = new Rectangle(new Vector2f(-10, -10), new Vector2f(10, 10), rigidbody);
        boolean result = IntersectionDetector.doesCircleIntersectRectangle(circle, rectangle);
        assertFalse(result);
    }

    @Test
    public void circleOverlapsRotatedRectangleShouldReturnTrueTest() {
        Circle circle = new Circle(5.0f, new Rigidbody());
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 45);
        Rectangle rectangle = new Rectangle(new Vector2f(-10, -10), new Vector2f(10, 10), rigidbody);
        boolean result = IntersectionDetector.doesCircleIntersectRectangle(circle, rectangle);
        assertTrue(result);
    }


    // |--- doesAABRIntersectAABR ---|

    @Test
    public void overlappingAABRsShouldReturnTrueTest() {
        AABR aabr1 = new AABR();
        Rigidbody rigidbody = new Rigidbody(new Vector2f(5, 5), 0);
        AABR aabr2 = new AABR(new Vector2f(0, 0), new Vector2f(10, 10), rigidbody);
        boolean result = IntersectionDetector.doesAABRIntersectAABR(aabr1, aabr2);
        assertTrue(result);
    }

    @Test
    public void separateAABRsShouldReturnFalseTest() {
        AABR aabr1 = new AABR();
        Rigidbody rigidbody = new Rigidbody(new Vector2f(50, 50), 0);
        AABR aabr2 = new AABR(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody);
        boolean result = IntersectionDetector.doesAABRIntersectAABR(aabr1, aabr2);
        assertFalse(result);
    }

    @Test
    public void touchingAABRsShouldReturnTrueTest() {
        AABR aabr1 = new AABR();
        Vector2f max = aabr1.getMax();
        Rigidbody rigidbody = new Rigidbody(new Vector2f(max.x + 5, 0), 0);
        AABR aabr2 = new AABR(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody);
        boolean result = IntersectionDetector.doesAABRIntersectAABR(aabr1, aabr2);
        assertTrue(result);
    }

    @Test
    public void containedAABRShouldReturnTrueTest() {
        Rigidbody rigidbody1 = new Rigidbody(new Vector2f(0, 0), 0);
        AABR aabr1 = new AABR(new Vector2f(-20, -20), new Vector2f(20, 20), rigidbody1);
        Rigidbody rigidbody2 = new Rigidbody(new Vector2f(0, 0), 0);
        AABR aabr2 = new AABR(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody2);
        boolean result = IntersectionDetector.doesAABRIntersectAABR(aabr1, aabr2);
        assertTrue(result);
    }


    // |--- doesAABRIntersectRectangle ---|

    @Test
    public void overlappingAABRAndRectangleShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody);
        boolean result = IntersectionDetector.doesAABRIntersectRectangle(aabr, rectangle);
        assertTrue(result);
    }

    @Test
    public void separateAABRAndRectangleShouldReturnFalseTest() {
        AABR aabr = new AABR();
        Rigidbody rigidbody = new Rigidbody(new Vector2f(50, 50), 0);
        Rectangle rectangle = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody);
        boolean result = IntersectionDetector.doesAABRIntersectRectangle(aabr, rectangle);
        assertFalse(result);
    }

    @Test
    public void overlappingAABRAndRotatedRectangleShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Rigidbody rigidbody = new Rigidbody(new Vector2f(0, 0), 45);
        Rectangle rectangle = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody);
        boolean result = IntersectionDetector.doesAABRIntersectRectangle(aabr, rectangle);
        assertTrue(result);
    }


    // |--- doesRectangleIntersectRectangle ---|

    @Test
    public void overlappingRectanglesShouldReturnTrueTest() {
        Rigidbody rigidbody1 = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle1 = new Rectangle(new Vector2f(-10, -10), new Vector2f(10, 10), rigidbody1);
        Rigidbody rigidbody2 = new Rigidbody(new Vector2f(5, 5), 0);
        Rectangle rectangle2 = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody2);
        boolean result = IntersectionDetector.doesRectangleIntersectRectangle(rectangle1, rectangle2);
        assertTrue(result);
    }

    @Test
    public void separateRectanglesShouldReturnFalseTest() {
        Rigidbody rigidbody1 = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle1 = new Rectangle(new Vector2f(-10, -10), new Vector2f(10, 10), rigidbody1);
        Rigidbody rigidbody2 = new Rigidbody(new Vector2f(50, 50), 0);
        Rectangle rectangle2 = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody2);
        boolean result = IntersectionDetector.doesRectangleIntersectRectangle(rectangle1, rectangle2);
        assertFalse(result);
    }

    @Test
    public void overlappingRotatedRectanglesShouldReturnTrueTest() {
        Rigidbody rigidbody1 = new Rigidbody(new Vector2f(0, 0), 0);
        Rectangle rectangle1 = new Rectangle(new Vector2f(-10, -10), new Vector2f(10, 10), rigidbody1);
        Rigidbody rigidbody2 = new Rigidbody(new Vector2f(0, 0), 45);
        Rectangle rectangle2 = new Rectangle(new Vector2f(-5, -5), new Vector2f(5, 5), rigidbody2);
        boolean result = IntersectionDetector.doesRectangleIntersectRectangle(rectangle1, rectangle2);
        assertTrue(result);
    }
}