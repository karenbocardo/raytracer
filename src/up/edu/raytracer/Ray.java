/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.raytracer;

import up.edu.raytracer.lights.Light;
import up.edu.raytracer.objects.Object3D;

import java.util.ArrayList;

/**
 *
 * @author Jafet Rodríguez
 */
public class Ray {

    /**
     * Where the Ray starts.
     */
    private Vector3D origin;
    /**
     * To where the Ray points.
     */
    private Vector3D direction;

    /**
     * Constructor of a Ray; sets both origin and direction components getting them as 3D Vectors. This uses
     * encapsulation.
     * @param origin of the Ray
     * @param direction of the Ray
     */
    public Ray(Vector3D origin, Vector3D direction) {
        setOrigin(origin);
        setDirection(direction);
    }

    /**
     * Returns the Ray 3D Vector origin.
     * @return origin
     */
    public Vector3D getOrigin() {
        return origin;
    }

    /**
     * Sets the 3D Vector origin.
     * @param origin
     */
    public void setOrigin(Vector3D origin) {
        this.origin = origin;
    }

    /**
     * Returns the Ray's normalized direction.
     * @return normalized direction
     */
    public Vector3D getDirection() {
        return Vector3D.normalize(direction);
    }

    /**
     * Sets Ray direction with a 3D Vector.
     * @param direction
     */
    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

}
