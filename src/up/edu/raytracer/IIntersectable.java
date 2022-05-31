/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.raytracer;

/**
 * This interface indicates when an Object, specially a 3D Object, can be intersected, and each one calculates
 * that intersection differently. It was created to allow triangles to be intersected and not extend from Object3D.
 *
 * @author Jafet Rodríguez
 * @author Karen Bocardo
 */
public interface IIntersectable {

    /**
     * When a 3D Object is intersectable, it calculates an Intersection that a thrown Ray by a Camera does with
     * it. This method returns that calculated Intersection.
     * @param ray that intersects
     * @return Intersection between Ray and Object3D
     */
    public abstract Intersection getIntersection(Ray ray);

    /**
     * Returns the number of intersections a Ray does with an intersectable 3D Object.
     * @param ray
     * @return number of intersections with ray
     */
    public abstract int getIntersections(Ray ray);
}
