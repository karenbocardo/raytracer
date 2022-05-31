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
 *
 * @author Jafet Rodríguez
 * @author Karen Bocardo
 */
public class DirectionalLight extends Light {
    private Vector3D direction;

    /**
     * Directional Light constructor receives its color and position (because it is a 3D Object, even though Directional
     * Lights position does not really matter for in Scenes ray tracing, its direction and normalizes it and its intensity.
     * @param position
     * @param direction
     * @param color
     * @param intensity
     */
    public DirectionalLight(Vector3D position, Vector3D direction, Color color, double intensity){
        super(position, color, intensity);
        setDirection(Vector3D.normalize(direction));
    }

    /**
     * Returns the Light direction.
     * @return direction
     */
    public Vector3D getDirection() {
        return direction;
    }

    /**
     * Sets the Light direction.
     * @param direction of Directional Light
     */
    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

    /**
     * Returns N dot L value with a Directional Light by using the Interesection normal in dot product with a
     * reversed/inverted of the Light Direction (that is, Lambertian surfaces), and when N dot L value is negative
     * returns zero
     * @param intersection with Polygon
     * @return N dot L value
     */
    @Override
    public float getNDotL(Intersection intersection) {
        return (float)Math.max(Vector3D.dotProduct(intersection.getNormal(), Vector3D.scalarMultiplication(getDirection(), -1.0)), 0.0);
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
