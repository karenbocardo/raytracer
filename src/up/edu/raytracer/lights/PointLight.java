/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.raytracer.lights;

import up.edu.raytracer.Intersection;
import up.edu.raytracer.Ray;
import up.edu.raytracer.Vector3D;

import java.awt.*;

/**
 * Point Lights are placed in a position in a Scene to illuminate the different objects placed in the same Scene.
 *
 * @author Jafet Rodríguez
 * @author Karen Bocardo
 */
public class PointLight extends Light {

    /**
     * Point Light constructor only sets its position, color and intensity.
     * @param position
     * @param color
     * @param intensity
     */
    public PointLight(Vector3D position, Color color, double intensity) {
        super(position, color, intensity);
    }

    /**
     * Returns the N dot L value for the Point Light Intersection with a 3D Object, or specifically a Polygon. This value
     * is calculated by the dot product of the Intersection's normal and the normalized 3D Vector that goes from
     * the position of the light to the position of the intersection. It also checks this value is not negative and
     * in that case, it return zero.
     * @param intersection with Polygon
     * @return N dot L value
     */
    @Override
    public float getNDotL(Intersection intersection) {
        return (float) Math.max(Vector3D.dotProduct(intersection.getNormal(), Vector3D.normalize(Vector3D.subtract(getPosition(), intersection.getPosition()))), 0.0);
    }

    /**
     * Returns zero since the number of intersections with a light is irrelevant.
     * @param ray
     * @return 0
     */
    @Override
    public int getIntersections(Ray ray) {
        return 0;
    }
}
