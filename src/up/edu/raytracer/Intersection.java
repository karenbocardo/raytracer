/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.raytracer;

import up.edu.raytracer.objects.Object3D;

/**
 * An Intersection is used when the thrown Ray by a Camera collides with an Object (specifically, a 3D Object.
 *
 * *****How much ilumination reaches an object face is determined by its normal.
 * @author Jafet Rodríguez
 * @author Karen Bocardo
 */
public class Intersection {

    private double distance;
    private Vector3D normal;
    private Vector3D position;
    private Object3D object;

    /**
     * Intersection constructor (uses encapsulation). Receives the Intersection's position, distance between the
     * thrown Ray origin and the intersection itself, the intersected face's normal and the object intersected.
     * @param position
     * @param distance between Ray origin and intersection with Object3D
     * @param normal of intersected face
     * @param object intersected
     */
    public Intersection(Vector3D position, double distance, Vector3D normal, Object3D object) {
        setPosition(position);
        setDistance(distance);
        setNormal(normal);
        setObject(object);
    }

    /**
     * Returns distance between the thrown Ray origin and the Intersection itself.
     * @return distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Sets distance between the thrown Ray origin and the Intersection itself.
     * @param distance between Ray origin and intersection with Object3D
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Returns the normal 3D Vector of the intersected face.
     * @return normal
     */
    public Vector3D getNormal() {
        return normal;
    }

    /**
     * Sets the normal 3D Vector of the intersected face.
     * @param normal of intersected face
     */
    public void setNormal(Vector3D normal) {
        this.normal = normal;
    }

    /**
     * Returns the position 3D Vector of the Intersection.
     * @return Interesection's position
     */
    public Vector3D getPosition() {
        return position;
    }

    /**
     * Returns the position 3D Vector of the Intersection.
     * @return Interesection's position
     */
    public void setPosition(Vector3D position) {
        this.position = position;
    }

    /**
     * Returns the intersected 3D Object.
     * @return object intersected
     */
    public Object3D getObject() {
        return object;
    }

    /**
     * Returns the intersected 3D Object.
     * @param object intersected
     */
    public void setObject(Object3D object) {
        this.object = object;
    }

}
