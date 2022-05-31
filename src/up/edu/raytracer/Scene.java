/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.raytracer;

import up.edu.raytracer.lights.Light;
import up.edu.raytracer.objects.Camera;
import up.edu.raytracer.objects.Object3D;

import java.util.ArrayList;

/**
 * A Scene is were objects are meant to be put, including a camera that captures what is in it and how it looks and
 * the lights that change what and how is seen.
 *
 * @author Jafet Rodríguez
 * @author Karen Bocardo
 */
public class Scene {

    /**
     * Scenes will always have a camera that captures what is in the scene.
     */
    private Camera camera;
    /**
     * List of 3D Objects to place in the Scene.
     */
    private ArrayList<Object3D> objects;
    /**
     * List of Lights to place in the Scene.
     */
    private ArrayList<Light> lights;

    /**
     * Camera's constructor initializes the Scene attributes to be able to start adding objects and lights to it
     */
    public Scene(){
        setObjects(new ArrayList<Object3D>());
        setLights(new ArrayList<Light>());
    }

    /**
     * Returns the Scene Camera object.
     * @return Scene's camera object
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * Sets the Scene Camera.
     * @param camera
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * This method gives the possibility of adding a 3D Object to the list of objects in the Scene.
     * @param object to add
     */
    public void addObject(Object3D object){
        getObjects().add(object);
    }

    /**
     * Returns the list of 3D Objects that are currently placed in the Scene.
     * @return list of 3D Objects as an ArrayList
     */
    public ArrayList<Object3D> getObjects() {
        return objects;
    }

    /**
     * Sets the list of 3D Objects in the Scene assigning the sent list to the Scene list.
     * @param objects list
     */
    public void setObjects(ArrayList<Object3D> objects) {
        this.objects = objects;
    }

    /**
     * Returns the list of Lights palced in the Scene.
     * @return list of Lights
     */
    public ArrayList<Light> getLights() {
        return lights;
    }

    /**
     * Sets the list of Lights placed in the Scene with an ArrayList as parameter.
     * @param lights
     */
    public void setLights(ArrayList<Light> lights) {
        this.lights = lights;
    }

    /**
     * Adds a Light to the Scene's list of Lights.
     * @param light to add
     */
    public void addLight(Light light){
        getLights().add(light);
    }
}
