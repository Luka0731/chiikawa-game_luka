package chiikawa2dphysicsengine;

import ch.noseryoung.chiikawa2dphysicsengine.primitives.AABR;
import ch.noseryoung.chiikawa2dphysicsengine.primitives.Circle;
import ch.noseryoung.chiikawa2dphysicsengine.primitives.Line;
import ch.noseryoung.chiikawa2dphysicsengine.rigidbody.IntersectionDetector;
import ch.noseryoung.chiikawa2dphysicsengine.rigidbody.Rigidbody;
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


    // |--- isPointInAABR tests ---|

    @Test
    public void pointInAABRShouldReturnTrueTest() {
        AABR aabr = new AABR();
        Vector2f point = new Vector2f(3.0f, 0);
        boolean result = IntersectionDetector.isPointInAABR(point, aabr);
        assertTrue(result);
    }


    // |--- isPointInRectangle tests ---|



    // ////////////////////////////////////////////////////////////////// //
    //    line vs primitive tests                                         //
    // ////////////////////////////////////////////////////////////////// //

    // |--- doesLineIntersectCircle tests ---|

    // |--- doesLineIntersectAABR ---|

    //  |--- doesLineIntersectRectangle ---|



    // ////////////////////////////////////////////////////////////////// //
    //    raycasting tests                                                //
    // ////////////////////////////////////////////////////////////////// //

    // |--- raycast circle ---|

    // |--- raycast AABR ---|

    // |--- raycast rectangle ---|



    // ////////////////////////////////////////////////////////////////// //
    //    shape vs shape tests                                            //
    // ////////////////////////////////////////////////////////////////// //

    // |--- doesCircleIntersectCircle ---|

    // |--- doesCircleIntersectAABR ---|

    // |--- doesCircleIntersectRectangle ---|

    // |--- doesAABRIntersectAABR ---|

    // |--- doesAABRIntersectRectangle ---|

    // |--- doesRectangleIntersectRectangle ---|
}