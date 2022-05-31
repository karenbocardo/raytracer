/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.raytracer.lights;

import up.edu.raytracer.Intersection;
import up.edu.raytracer.Ray;
import up.edu.raytracer.Vector3D;
import up.edu.raytracer.objects.Object3D;

import java.awt.*;

/**
 * Lights interact with 3D Objects in a Scene adding shading to them, lights interact differently with
 * each object. They are also added to Scenes. It is abstract since a Light must have a type.
 *
 * @author Jafet Rodríguez
 * @author Karen Bocardo
 */
public abstract class Light extends Object3D {
    private double intensity;

    /**
     * Light constructor only needs its position, color (which affects importantly Objects in Scene) and its intensity.
     * @param position
     * @param color
     * @param intensity
     */
    public Light(Vector3D position, Color color, double intensity){
        super(position, color);
        setIntensity(intensity);
    }

    /**
     * Returns Light intensity value.
     * @return intensity
     */
    public double getIntensity() {
        return intensity;
    }

    /**
     * This method returns the intensity of a Light in a specific point of an Object3D in the Scene implementing
     * light falloff (dependency on distance to the Light).
     * @param intersectionPosition illuminated point
     * @return light intensity with falloff
     */
    public double getIntensity(Vector3D intersectionPosition) {
        // light falloff
        double distance = Math.sqrt(Math.pow(getPosition().getX() - intersectionPosition.getX(), 2) +
                                    Math.pow(getPosition().getY() - intersectionPosition.getY(), 2) +
                                    Math.pow(getPosition().getZ() - intersectionPosition.getZ(), 2));
        return intensity / Math.pow(distance, 2);
    }

    /**
     * Sets Light intensity value.
     * @param intensity value
     */
    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    /**
     * N dot L variates according to Light type, so it it an abstract method, but it always needs an intersecion
     * to calculate it. This value refers to shaders and how much light is reaching a Polygon face.
     * @param intersection with Polygon
     * @return N dot L value
     */
    public abstract float getNDotL(Intersection intersection);

    /**
     * Returns an invalid Intersection because it does not matter for Light.
     * @param ray
     * @return invalid Intersection
     */
    public Intersection getIntersection(Ray ray){
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
}
