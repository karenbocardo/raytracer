/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.raytracer.objects;

import up.edu.raytracer.IIntersectable;
import up.edu.raytracer.Vector3D;

import java.awt.*;

/**
 * Object3D is defined as an abstract class since it only has the general attributes that an object
 * meant to be put in a scene should have. All Objets are interesectable, but there is not a generic way to
 * calculate an Object's intersection, it is always calculated differently, so, IIntersectable methods stay
 * abstract until each object calculates its intersection.
 *
 * @author Jafet Rodríguez
 * @author Karen Bocardo
 */
public abstract class Object3D implements IIntersectable {



    private Vector3D position;
    private Color color;
    private float reflectiveness = 0.0f;
    private float refractiveness = 0.0f;

    /**
     * Returns the components of the 3D Object position as a 3D Vector.
     * @return Vector3D position
     */
    public Vector3D getPosition() {
        return position;
    }

    /**
     * Sets the 3D Vector components of the 3D Object position in a Scene.
     * @param position components as Vector3D
     */
    public void setPosition(Vector3D position) {
        this.position = position;
    }

    /**
     * Returns the color of the object.
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the object.
     * @param color of the Object
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Contructor of the third dimensional object; parameters are only the color and its position as an instance of
     * Vector3D.
     * @param position of the Object
     * @param color of the Object
     */
    public Object3D(Vector3D position, Color color) {
        setPosition(position);
        setColor(color);
    }

    /**
     * Returns wether the object reflects or not; it is determined by the reflectiveness value, which, in case is
     * zero, says the Object3D does not reflect.
     * @return true if object reflects
     */
    public boolean isReflective() {
        if (getReflectiveness() > 0.0f) return true;
        else return false;
    }

    /**
     * Returns wether the object refracts or not; it is determined by the refractiveness value, which, in case is
     * zero, says the Object3D does not refract.
     * @return true if object refracts
     */
    public boolean isRefractive() {
        if (getRefractiveness() > 0.0f) return true;
        else return false;
    }

    /**
     * Returns the value of how much an Object3D reflects.
     * @return reflection value
     */
    public float getReflectiveness() {
        return reflectiveness;
    }

    /**
     * Sets the value of how much an Object3D reflects.
     * @param reflectiveness reflection value
     */
    public void setReflectiveness(float reflectiveness) {
        this.reflectiveness = reflectiveness;
    }

    /**
     * Returns the value of how much an Object3D refracts.
     * @return refraction value
     */
    public float getRefractiveness() {
        return refractiveness;
    }

    /**
     * Sets the value of how much an Object3D reflects.
     * @param refractiveness refraction value
     */
    public void setRefractiveness(float refractiveness) {
        this.refractiveness = refractiveness;
    }

}
