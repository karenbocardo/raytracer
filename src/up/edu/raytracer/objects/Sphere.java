/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.raytracer.objects;

import up.edu.raytracer.Intersection;
import up.edu.raytracer.Ray;
import up.edu.raytracer.Vector3D;

import java.awt.*;

/**
 * A Sphere is an object that exists in any third dimension, which is why it is an Object3D child.
 *
 * @author Jafet Rodríguez
 * @author Karen Bocardo
 */
public class Sphere extends Object3D {

    private float radius;

    /**
     * Returns the Sphere radius value.
     * @return radius value
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Sets the Sphere radius value.
     * @param radius value
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * Constructor of Sphere objects. It includes the constructor of its parent class Object3D. The parameters to
     * create a Sphere are the position it will have in a Scene , its radius and its color.
     * @param position
     * @param radius
     * @param color
     */
    public Sphere(Vector3D position, float radius, Color color) {
        super(position, color);
        setRadius(radius);
    }


    /**
     * It returns the Intersection between the Sphere and the thrown Ray by the Camera. To get it, it first initializes
     * the distance with a negative value (-1), because in case sthat it is behind the Ray, the Intersection is
     * not rendered; normal and position vectors are as well first defined with generic values. Then, the direction
     * between the Sphere and the Ray is calculated with the standard subtraction; the two intersections of the Ray
     * are also computed here: the first one, with the dot product between Ray and Sphere's directions; second one
     * is the Ray-Sphere direction magnitude squared.
     *
     * After all needed values are obtained, Interection calculation uses Pythagoras theorem. If intersection is
     * negative it is not valid so the return is null. If not, distance, position and normal is calculated. The distance
     * is the minimum between the two intersections (the closest). The position is the addition of the Ray origin
     * and the scalar multiplication of the Ray's direction and the distance. Finally, the normal is the normalized
     * substraction of the Intersection's position and the 3D Object position.
     * @param ray that intersects
     * @return Intersection between Sphere and Ray
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        double distance = -1;
        Vector3D normal = Vector3D.ZERO();
        Vector3D position = Vector3D.ZERO();

        Vector3D directionSphereRay = Vector3D.subtract(ray.getOrigin(), getPosition());
        double firstP = Vector3D.dotProduct(ray.getDirection(), directionSphereRay);
        double secondP = Math.pow(Vector3D.magnitude(directionSphereRay), 2);
        double intersection = Math.pow(firstP, 2) - secondP + Math.pow(getRadius(), 2);

        if(intersection >= 0){
            double sqrtIntersection = Math.sqrt(intersection);
            double part1 = - firstP + sqrtIntersection;
            double part2 = - firstP - sqrtIntersection;

            distance = Math.min(part1, part2);
            position = Vector3D.add(ray.getOrigin(), Vector3D.scalarMultiplication(ray.getDirection(), distance));
            normal = Vector3D.normalize(Vector3D.subtract(position, getPosition()));
        } else return null;

        return new Intersection(position, distance, normal, this);
    }

    /**
     * Returns one since it is known a Ray always intersects a Sphere twice (unless is tangible), but since Spheres do
     * not make shadows on themselves it is arbitrary.
     * @param ray
     * @return 1
     */
    @Override
    public int getIntersections(Ray ray) {
        return 1;
    }
}
