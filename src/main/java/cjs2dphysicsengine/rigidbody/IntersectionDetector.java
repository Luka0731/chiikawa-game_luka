package cjs2dphysicsengine.rigidbody;

import cjs2dphysicsengine.primitives.*;
import util.CJSMath;
import org.joml.Vector2f;

import static util.CJSMath.compare;

// todo: as improvement, i could make some private methods, cause multiple methods use similar code
// todo: look at every acuation again, cause i dont understand everything
public class IntersectionDetector {
    private static final float EPSILON = 0.000001f; // higher epsilon for comparisons with square root numbers


    // |--- point vs primitive tests ---|

    public static boolean isPointOnLine(Vector2f point, Line line) {
        float dy = line.getToPosition().y - line.getFromPosition().y;
        float dx = line.getToPosition().x - line.getFromPosition().x;

        if (compare(dx, 0f)) return compare(point.x, line.getFromPosition().x);

        float slob = dy / dx;
        float yIntersect = line.getToPosition().y - (slob * line.getToPosition().x);
        return compare(point.y, slob * point.x + yIntersect);
    }

    public static boolean isPointInCircle(Vector2f point, Circle circle) {
        Vector2f circleCenter = circle.getCenter();
        Vector2f centerToPoint = new Vector2f(point).sub(circleCenter);
        return centerToPoint.lengthSquared() <= circle.getRadius() * circle.getRadius();
    }

    public static boolean isPointInAABR (Vector2f point, AABR aabr) {
        Vector2f min = aabr.getMin();
        Vector2f max = aabr.getMax();
        return point.x <= max.x && min.x <= point.x
                && point.y <= max.y && min.y <=point.y;
    }

    public static boolean isPointInRectangle(Vector2f point, Rectangle rectangle) {
        // translate the point into local space
        Vector2f pointLocalRectangleShape = new Vector2f(point);
        CJSMath.rotate(pointLocalRectangleShape, rectangle.getRigidbody().getRotation(),
                rectangle.getRigidbody().getPosition());

        Vector2f min = rectangle.getLocalMin();
        Vector2f max = rectangle.getLocalMax();
        return pointLocalRectangleShape.x <= max.x && min.x <= pointLocalRectangleShape.x
                && pointLocalRectangleShape.y <= max.y && min.y <= pointLocalRectangleShape.y;
    }


    // |--- line vs primitive tests ---|

    public static boolean doesLineIntersectCircle(Line line, Circle circle) {
        if (isPointInCircle(line.getFromPosition(), circle) || isPointInCircle(line.getToPosition(), circle )) return true;

        Vector2f ab = new Vector2f(line.getToPosition()).sub(line.getFromPosition());

        // project point (circle position) onto ab (line segment)
        // parameterized position t
        Vector2f circleCenter = circle.getCenter();
        Vector2f centerToLineFromPosition = new Vector2f(circleCenter).sub(line.getFromPosition());
        float t = centerToLineFromPosition.dot(ab) / ab.dot(ab);

        if (t < EPSILON || t > 1.0f) return false;

        // finde the closest point to the line segment
        Vector2f closesPoint = new Vector2f(line.getFromPosition()).add(ab.mul(t));

        return isPointInCircle(closesPoint, circle);
    }

    public static boolean doesLineIntersectAABR(Line line, AABR aabr) {
        if (isPointInAABR(line.getFromPosition(), aabr) || isPointInAABR(line.getToPosition(), aabr)) {
            return true;
        }

        Vector2f unitVector = new Vector2f(line.getToPosition()).sub(line.getFromPosition());
        unitVector.normalize();
        unitVector.x = !compare(unitVector.x, 0f) ? 1.0f / unitVector.x : Float.MAX_VALUE;
        unitVector.y = !compare(unitVector.y, 0f) ? 1.0f / unitVector.y : Float.MAX_VALUE;

        Vector2f min = aabr.getMin();
        min.sub(line.getFromPosition()).mul(unitVector);
        Vector2f max = aabr.getMax();
        max.sub(line.getFromPosition()).mul(unitVector);

        float tMin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tMax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));
        if (tMax < -EPSILON || tMin > tMax) return false;
        float t = tMin < 0f ? tMax : tMin;
        return t > 0f && t * t < line.getLengthSquared();
    }

    public static boolean doesLineIntersectRectangle(Line line, Rectangle rectangle) {
        float theta = -rectangle.getRigidbody().getRotation();
        Vector2f center = rectangle.getRigidbody().getPosition();
        Vector2f localStart = new Vector2f(line.getFromPosition());
        Vector2f localEnd = new Vector2f(line.getToPosition());
        CJSMath.rotate(localStart, theta, center);
        CJSMath.rotate(localEnd, theta, center);

        Line localLine = new Line(localStart, localEnd);
        AABR aabr = new AABR(rectangle.getLocalMin(), rectangle.getLocalMax());

        return doesLineIntersectAABR(localLine, aabr);
    }


    // |--- ray vs primitive tests (raycasting) ---|

    public static boolean raycast(Circle circle, Ray ray, RaycastResult result) {
        RaycastResult.reset(result);

        Vector2f originToCircle = new Vector2f(circle.getCenter()).sub(ray.getOrigin());
        float radiusSquared = circle.getRadius() * circle.getRadius();
        float originToCircleLengthSquared = originToCircle.lengthSquared();

        // project the vector from the ray origin onto the direction of the ray
        float a = originToCircle.dot(ray.getDirection());
        float bSquared = originToCircleLengthSquared - a * a;
        if (radiusSquared - bSquared < -EPSILON) return false;

        float f = (float)Math.sqrt(radiusSquared - bSquared); // this is why raycasts are expensive
        float t = 0;
        if (originToCircleLengthSquared < radiusSquared) {
            // if, then ray starts inside the circle
            t = a + f;
        } else {
            t = a- f;
        }

        if (t < -EPSILON) return false;
        if (result != null) {
            Vector2f point = new Vector2f(ray.getOrigin()).add(new Vector2f(ray.getDirection().mul(t)));
            Vector2f normal = new Vector2f(point).sub(circle.getCenter());
            normal.normalize();
            result.init(point, normal, t, true);
        }
        return true;
    }

    public static boolean raycast(Circle circle, Ray ray) {
        return raycast(circle, ray, null);
    }

    public static boolean raycast(AABR aabr, Ray ray, RaycastResult result) {
        RaycastResult.reset(result);

        Vector2f unitVector = new Vector2f(ray.getDirection());
        unitVector.normalize();
        unitVector.x = !compare(unitVector.x, 0f) ? 1.0f / unitVector.x : Float.MAX_VALUE;
        unitVector.y = !compare(unitVector.y, 0f) ? 1.0f / unitVector.y : Float.MAX_VALUE;

        Vector2f min = aabr.getMin();
        min.sub(ray.getOrigin()).mul(unitVector);
        Vector2f max = aabr.getMax();
        max.sub(ray.getOrigin()).mul(unitVector);

        float tMin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tMax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));
        if (tMax < -EPSILON || tMin > tMax) return false;

        float t = tMin < -EPSILON ? tMax : tMin;
        boolean hasHit = t > EPSILON; // && t * t < ray.getMaxLength(); todo: implement a max in ray
        if (!hasHit) return hasHit;
        if (result != null) {
            Vector2f point = new Vector2f(ray.getOrigin()).add(new Vector2f(ray.getDirection().mul(t)));
            Vector2f normal = new Vector2f(ray.getOrigin()).sub(point);
            normal.normalize(); // todo: there is a more efficient way, then always having to normalize it
            result.init(point, normal, t, true);
        }
        return true;
    }

    public static boolean raycast(AABR aabr, Ray ray) {
        return raycast(aabr, ray, null);
    }

    public static boolean raycast(Rectangle rectangle, Ray ray, RaycastResult result) {
        RaycastResult.reset(result);

        Vector2f halfSize = rectangle.getHalfSize();
        Vector2f xAxis = new Vector2f(1, 0);
        Vector2f yAxis = new Vector2f(0, 1);
        CJSMath.rotate(xAxis, rectangle.getRigidbody().getRotation(), new Vector2f(0, 0));
        CJSMath.rotate(yAxis, rectangle.getRigidbody().getRotation(), new Vector2f(0, 0));

        Vector2f p = new Vector2f(rectangle.getRigidbody().getPosition()).sub(ray.getOrigin());
        Vector2f f = new Vector2f(xAxis.dot(ray.getDirection()), yAxis.dot(ray.getDirection()));

        // next, project p onto every axis of the box
        Vector2f e = new Vector2f(xAxis.dot(p), yAxis.dot(p));

        float[] tArray = {0, 0, 0, 0};
        for (int i = 0; i < 2; i++) {
            if (compare(f.get(i), 0f)) {
                // if the ray is parallel to the current axis, and the origin of the ray is not inside, we have ho hit
                if(-e.get(i) - halfSize.get(i) > EPSILON || -e.get(i) + halfSize.get(i) < -EPSILON) return false;
                f.setComponent(i, EPSILON);
            }
            tArray[i * 2] = (e.get(i) + halfSize.get(i)) / f.get(i);     // tMax for this axis
            tArray[i * 2 + 1] = (e.get(i) - halfSize.get(i)) / f.get(i); // tMin for this axis
        }

        float tMin = Math.max(Math.min(tArray[0], tArray[1]), Math.min(tArray[2], tArray[3]));
        float tMax = Math.min(Math.max(tArray[0], tArray[1]), Math.max(tArray[2], tArray[3]));

        float t = tMin < -EPSILON ? tMax : tMin;
        boolean hasHit = t > EPSILON; // && t * t < ray.getMaxLength(); todo: implement a max in ray
        if (!hasHit) return hasHit;
        if (result != null) {
            Vector2f point = new Vector2f(ray.getOrigin()).add(new Vector2f(ray.getDirection()).mul(t));
            Vector2f normal = new Vector2f(ray.getOrigin()).sub(point);
            normal.normalize(); // todo: there is a more efficient way, then always having to normalize it
            result.init(point, normal, t, true);
        }
        return true;
    }

    public static boolean raycast(Rectangle rectangle, Ray ray) {
        return raycast(rectangle, ray, null);
    }


    // |--- circle vs primitive tests ---|

    public static boolean doesCircleIntersectLine(Circle circle, Line line) {
        return doesLineIntersectCircle(line, circle);
    }

    public static boolean doesCircleIntersectCircle(Circle circle1, Circle circle2) {
        Vector2f vectorBetweenCenters = new Vector2f(circle1.getCenter()).sub(circle2.getCenter());
        float radiusSum = circle1.getRadius() + circle2.getRadius();
        return vectorBetweenCenters.lengthSquared() <= radiusSum * radiusSum;
    }

    public static boolean doesCircleIntersectAABR(Circle circle, AABR aabr) {
        Vector2f min = aabr.getMin();
        Vector2f max = aabr.getMax();

        Vector2f closestPointToCircle = new Vector2f(circle.getCenter());
        if (closestPointToCircle.x < min.x) {
            closestPointToCircle.x = min.x;
        } else if (closestPointToCircle.x > max.x) {
            closestPointToCircle.x = max.x;
        }
        if (closestPointToCircle.y < min.y) {
            closestPointToCircle.y = min.y;
        } else if (closestPointToCircle.y > max.y) {
            closestPointToCircle.y = max.y;
        }

        Vector2f circleToAabr = new Vector2f(circle.getCenter()).sub(closestPointToCircle);
        return circleToAabr.lengthSquared() <= circle.getRadius() * circle.getRadius();
    }

    public static boolean doesCircleIntersectRectangle(Circle circle, Rectangle rectangle) {
        // rotate rectangle and then treat it like an AABR
        Vector2f min = new Vector2f();
        Vector2f max = new Vector2f(rectangle.getHalfSize()).mul(2.0f);

        // create a circle in box's local space
        Vector2f r = new Vector2f(circle.getCenter()).sub(rectangle.getRigidbody().getPosition());
        CJSMath.rotate(r, -rectangle.getRigidbody().getRotation(), new Vector2f(0, 0));
        Vector2f localCirclePos = new Vector2f(r).add(rectangle.getHalfSize());

        Vector2f closestPointToCircle = new Vector2f(localCirclePos);
        if (closestPointToCircle.x < min.x) {
            closestPointToCircle.x = min.x;
        } else if (closestPointToCircle.x > max.x) {
            closestPointToCircle.x = max.x;
        }
        if (closestPointToCircle.y < min.y) {
            closestPointToCircle.y = min.y;
        } else if (closestPointToCircle.y > max.y) {
            closestPointToCircle.y = max.y;
        }

        Vector2f circleToAabr = new Vector2f(localCirclePos).sub(closestPointToCircle);
        return circleToAabr.lengthSquared() <= circle.getRadius() * circle.getRadius();
    }

    // |--- AABR vs primitive tests ---|

    public static boolean doesAABRIntersectLine(AABR aabr, Line line) {
        return doesLineIntersectAABR(line, aabr);
    }

    public static boolean doesAABRIntersectCircle(AABR aabr, Circle circle) {
        return doesCircleIntersectAABR(circle, aabr);
    }

    public static boolean doesAABRIntersectAABR(AABR aabr1, AABR aabr2) {
        Vector2f[] axisToTest = {new Vector2f(0, 1), new Vector2f(1, 0)};
        for (int i = 0; i < axisToTest.length; i++) {
            if (!doesAxisOverlap(aabr1, aabr2, axisToTest[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean doesAABRIntersectRectangle(AABR aabr, Rectangle rectangle) {
        Vector2f[] axisToTest = {
                new Vector2f(0, 1), new Vector2f(1, 0),
                new Vector2f(0, 1),  new Vector2f(1, 0)
        };
        CJSMath.rotate(axisToTest[2], aabr.getRigidbody().getRotation(), new Vector2f(0, 0));
        CJSMath.rotate(axisToTest[3], aabr.getRigidbody().getRotation(), new Vector2f(0, 0));
        for (int i = 0; i < axisToTest.length; i++) {
            if (!doesAxisOverlap(aabr, rectangle, axisToTest[i])) {
                return false;
            }
        }
        return true;
    }


    // |--- Rectangle vs primitive tests ---|

    public static boolean doesRectangleIntersectLine(Rectangle rectangle, Line line) {
        return doesLineIntersectRectangle(line, rectangle);
    }

    public static boolean doesRectangleIntersectCircle(Rectangle rectangle, Circle circle) {
        return doesCircleIntersectRectangle(circle, rectangle);
    }

    public static boolean doesRectangleIntersectAABR(Rectangle rectangle, AABR aabr) {
        return doesAABRIntersectRectangle(aabr, rectangle);
    }

    public static boolean doesRectangleIntersectRectangle (Rectangle rectangle1, Rectangle rectangle2) {
        // create the axes to test
        Vector2f[] axesToTest = {
                new Vector2f(1, 0),
                new Vector2f(0, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 1)
        };

        // rotate the first two axes by rectangle1's rotation, this transforms them from world axes to rectangle1's local axes
        float rotationOfRectangle1 = rectangle1.getRigidbody().getRotation();
        CJSMath.rotate(axesToTest[0], rotationOfRectangle1, new Vector2f(0, 0));
        CJSMath.rotate(axesToTest[1], rotationOfRectangle1, new Vector2f(0, 0));
        // same like bevor, but Rotate the last two axes by rectangle2's rotation
        float rotationOfRectangle2 = rectangle2.getRigidbody().getRotation();
        CJSMath.rotate(axesToTest[2], rotationOfRectangle2, new Vector2f(0, 0));
        CJSMath.rotate(axesToTest[3], rotationOfRectangle2, new Vector2f(0, 0));

        // for each axis, we project both rectangles onto it and check if the projections (intervals) overlap.
        for (int i = 0; i < axesToTest.length; i++) {
            Vector2f currentAxis = axesToTest[i];
            boolean axisHasOverlap = doesAxisOverlap(rectangle1, rectangle2, currentAxis);
            if (!axisHasOverlap) {
                return false;
            }
        }
        return true;
    }


    // |--- helper methods for sat calculations ---|

    private static Vector2f getInterval(AABR aabr, Vector2f axis) {
        Vector2f result = new Vector2f(0, 0);

        Vector2f min = aabr.getMin();
        Vector2f max = aabr.getMax();

        Vector2f[] vertices = {
                new Vector2f(min.x, min.y), new Vector2f(min.x, max.y),
                new Vector2f(max.x, min.y), new Vector2f(max.x, max.y)
        };

        result.x = axis.dot(vertices[0]);
        result.y = result.x;
        for (int i = 1; i < vertices.length; i++) { // todo: maybe problem, cause why i = 1 in the start?
            float projection = axis.dot(vertices[i]);
            if (projection < result.x) {
                result.x = projection;
            }
            if (projection > result.y) {
                result.y = projection;
            }
        }
        return result;
    }

    private static Vector2f getInterval(Rectangle rectangle, Vector2f axis) {
        Vector2f result = new Vector2f(0, 0);

        Vector2f min = rectangle.getLocalMin();
        Vector2f max = rectangle.getLocalMax();

        Vector2f[] vertices = {
                new Vector2f(min.x, min.y), new Vector2f(min.x, max.y),
                new Vector2f(max.x, min.y), new Vector2f(max.x, max.y)
        };

        result.x = axis.dot(vertices[0]);
        result.y = result.x;
        for (int i = 1; i < vertices.length; i++) { // todo: maybe problem, cause why i = 1 in the start?
            float projection = axis.dot(vertices[i]);
            if (projection < result.x) {
                result.x = projection;
            }
            if (projection > result.y) {
                result.y = projection;
            }
        }
        return result;
    }

    // axis it to be expected normalized
    private static boolean doesAxisOverlap(AABR aabr1, AABR aabr2, Vector2f axis) {
        Vector2f interval1 = getInterval(aabr1, axis);
        Vector2f interval2 = getInterval(aabr2, axis);
        return interval2.x <= interval1.y && interval1.x <= interval2.y;
    }

    private static boolean doesAxisOverlap(AABR aabr, Rectangle rectangle, Vector2f axis) {
        Vector2f interval1 = getInterval(aabr, axis);
        Vector2f interval2 = getInterval(rectangle, axis);
        return interval2.x <= interval1.y && interval1.x <= interval2.y;
    }

    private static boolean doesAxisOverlap(Rectangle rectangle1, Rectangle rectangle2, Vector2f axis) {
        Vector2f interval1 = getInterval(rectangle1, axis);
        Vector2f interval2 = getInterval(rectangle2, axis);
        return interval2.x <= interval1.y && interval1.x <= interval2.y;
    }
}