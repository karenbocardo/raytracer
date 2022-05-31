/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.raytracer;

import up.edu.raytracer.lights.DirectionalLight;
import up.edu.raytracer.lights.Light;
import up.edu.raytracer.lights.PointLight;
import up.edu.raytracer.objects.*;
import up.edu.raytracer.tools.OBJReader;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * This project consists in the construction of a RayTracer (a render engine) started from zero. A Raytracer is
 * is a system in which rays are thrown to detect collisions and, in case there are collisions, paint a pixel with
 * a certain color. Scenes are raytraced in a perspective view.
 *
 * @author Jafet Rodríguez
 * @author Karen Bocardo
 * @version 1.0
 */
public class Raytracer {

    /**
     * This attribute is declared variable to make code readable. It sets a standard for the type of the files to save.
     */
    private static String EXTENSION = "png";
    private static String NAME = "06-refraction";
    public static double BIAS = 0.001;
    public static double SHININESS = 40f;
    public static double AMBIENT = 0.2f;
    public static double DIFFUSE = 0.5f;
    public static double SPECULAR = 0.7f;
    public static Color BG = new Color(91,176,200);

    /**
     * Maximum recursion for reflection and refraction cases.
     */
    public static int MAX_DEPTH = 3;
    public static int DEPTH = 0;

    /**
     * In main method all objects are added to a Scene (or scenes), including a Camera for each one. Then, the
     * raytracing happens calling raytrace method; that method returns a Buffered Image which is finally written in a
     * PNG file. It is the method for the demo of all the Raytracer project.
     *
     * It prints the date and time before the raytracing starts and when all processes are done.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(new Date());
        Scene scene = sample();
        BufferedImage image = raytrace(scene, BG);
        saveImage(image, NAME);
        System.out.println(new Date());
    }

    /**
     * Returns a sample scene to show Ray Tracer v1.0 process.
     * @return sample scene
     */
    private static Scene sample(){
        Scene scene01 = new Scene();
        scene01.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, 800, 800, 5f, 50f));
        scene01.addLight(new DirectionalLight(Vector3D.ZERO(), new Vector3D(0.0, -1.0f, 0f), Color.WHITE, 1.0f));
        //scene01.addLight(new PointLight(new Vector3D(-0.5f, 1.0f, 0.0f), Color.WHITE, 0.5f));
        scene01.addLight(new PointLight(new Vector3D(0.5f, 1.0f, 0.0f), Color.WHITE, 0.2f));
        Sphere sphere =new Sphere(new Vector3D(0.0f, 1.0f, 3f), 1.5f, new Color(150,82,184));
        //Sphere sphere =new Sphere(new Vector3D(0.0f, 1.0f, 3f), 1.5f, Color.WHITE);
        //sphere.setReflectiveness(0.9f);
        scene01.addObject(sphere);
        scene01.addObject(new Sphere(new Vector3D(-2f, -1.0f, 1.0f), 0.6f, new Color(77,87,185)));
        scene01.addObject(new Sphere(new Vector3D(-2f, -1.0f, 1.0f), 0.6f, new Color(209,133,117)));
        scene01.addObject(new Sphere(new Vector3D(1.5f, -0.7f, -4.0f), 0.6f, new Color(163,67,63)));
        scene01.addObject(new Sphere(new Vector3D(2.0f, 2.0f, 1.0f), 0.5f, Color.PINK));
        scene01.addObject(new Sphere(new Vector3D(-2.0f, 1.7f, 1.0f), 0.3f, Color.GRAY));
        scene01.addObject(new Sphere(new Vector3D(-2.0f, 0.5f, 0.0f), 0.7f, Color.WHITE));
        scene01.addObject(new Sphere(new Vector3D(0.0f, 4.0f, 0.0f), 0.f, new Color(40,119,112)));
        Polygon tipot = OBJReader.GetPolygon("SmallTeapot.obj", new Vector3D(1.2f, -1.5f, 0.5f), new Color(209,133,117));
        Sphere sphere1 = new Sphere(new Vector3D(1.1f, -1f, -1f), 0.7f, Color.WHITE);
        sphere1.setRefractiveness(1.5f);
        scene01.addObject(sphere1);
        //tipot.setRefractiveness(0.7f);
        scene01.addObject(tipot);
        //tipot.setRefractiveness(1.5f);
        scene01.addObject(OBJReader.GetPolygon("floor.obj", new Vector3D(0.0f, -2.0f, 0f), Color.PINK));
        return scene01;
    }

    /**
     * Saves the Buffered Image sent in a file with the name sent as parameter (along with the image).
     * @param image to save
     * @param filename of final file
     */
    private static void saveImage(BufferedImage image, String filename){
        File outputImage = new File(filename + "." + EXTENSION);
        try {
            ImageIO.write(image, EXTENSION, outputImage);
        } catch (IOException ioe) {
            System.out.println("Something failed");
        }
    }

    /**
     * This method renders the Scene parameter and returns a Buffered Image in which the rendered scene is saved.
     *
     * It first defines and assigns the principal variables to use in the raytracing, by getting them from the sent
     * Scene; that is: the Scene's Camera (and its resolution to initialize the final Buffered Image with it),
     * the list of objects, its lights, and the near and far planes.
     *
     * What raytrace method does is practically generate an array of squares, who are actually pixels. So first,
     * it defines a two-dimensional array of the positions to which the raytrace will be made, the responsibility of
     * creating that grid belongs to the scene's camera; so, positions to the raytrace are only assigned by
     * calling the Camera's calculatePositionsToRay method.
     *
     * To be able to move the Camera, its position is added, and finally the absolute components of the position are
     * obtained. Then, a ray is needed to be thrown to each position of the grid, so it is is defined with origin
     * in Camera's position and direction to the final position calculated before.
     *
     * Objects intersections are calculated parametrically (where are two contact points to objects) and is gotten
     * from the Ray's origin vector plus the distance to the intersection times the Ray direction. Then, a raycast
     * is done to select the closest intersection of all. It reads the 3D Object color of the closest intersection,
     * and paints the pixel with that color checking how much light gets to the Object3D going Light by Light and
     * calculating the ambient, diffuse and specular color, considering reflexion and refraction in objects.
     *
     * It finally sets the pixel color to a Buffered Image and returns the colored image.
     * @param scene to raytrace
     * @return image with Scene
     */
    public static BufferedImage raytrace(Scene scene, Color background) {
        Camera mainCamera = scene.getCamera();
        ArrayList<Light> lights = scene.getLights();
        float nearPlane = mainCamera.getNearPlane();
        float farPlane = mainCamera.getFarPlane();
        BufferedImage image = new BufferedImage(mainCamera.getResolutionWidth(), mainCamera.getResolutionHeight(), BufferedImage.TYPE_INT_RGB);
        ArrayList<Object3D> objects = scene.getObjects();

        float cameraZ = (float) mainCamera.getPosition().getZ();
        Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
        float[] clippingPlanes = new float[]{cameraZ + nearPlane, cameraZ + farPlane};

        for (int i = 0; i < positionsToRaytrace.length; i++) {
            for (int j = 0; j < positionsToRaytrace[i].length; j++) {
                double x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
                double y = positionsToRaytrace[i][j].getY() + mainCamera.getPosition().getY();
                double z = positionsToRaytrace[i][j].getZ() + mainCamera.getPosition().getZ();

                Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));
                Intersection closestIntersection = raycast(ray, objects, null, clippingPlanes);

                DEPTH = 0;
                Color pixelColor = getPixelColor(ray, closestIntersection, lights, background, objects, clippingPlanes, 1.0f);
                image.setRGB(i, j, pixelColor.getRGB());
            }
        }

        return image;
    }

    /**
     * This method is part of the complex Ray Tracing part. After a Ray is thrown from the camera, this checks wether
     * the Intersection Object reflects or refracts other 3D Objects. It goes adding color to a Color for a pixel
     * which is initialized in BLACK. At the end, it returns the color that the pixel should be set with. The colors
     * consider shininess, ambient, diffuse and specular coefficients.
     *
     * This methos is recursive but its depth is only three.
     * @param intersection by Ray thrown by main Camera
     * @param lights in Scene
     * @param background color
     * @param objects in Scene
     * @param clippingPlanes of main Camera
     * @return color for a pixel
     */
    public static Color getPixelColor(Ray ray, Intersection intersection, ArrayList<Light> lights, Color background, ArrayList<Object3D> objects, float[] clippingPlanes, float reflect){
        DEPTH++;

        // background color
        Color pixelColor = Color.BLACK;

        float refract = 1.0f;
        if (intersection != null) {
            pixelColor = Color.BLACK;

            // object/pixel illumination
            for (Light light : lights) {
                Object3D intersectionObject = intersection.getObject();
                Color objColor = intersectionObject.getColor();

                // normalized colors
                float[] objColors = normalizeColor(objColor);

                // shadows
                Intersection shadowIntersection = getShadowIntersection(light, intersection, objects, clippingPlanes);

                if (shadowIntersection == null) {
                    Color ambient = getAmbientColor(objColors.clone());
                    Color diffuse = getDiffuseColor(light, intersection, objColors.clone());
                    Color specular = getSpecularColor(light, intersection, objColors.clone());

                    // blinn phong
                    // reflection
                    float reflectiveness = intersectionObject.getReflectiveness();
                    pixelColor = addColor(pixelColor, getColorPercentage(addColor(ambient, addColor(diffuse, specular)), (1 - reflectiveness) * reflect));
                    if (intersection.getObject().isReflective()) {
                        if (DEPTH == MAX_DEPTH) return pixelColor;
                        else {
                            Ray reflectionRay = getReflectionRay(intersection, ray);
                            Intersection reflectionIntersection = raycast(reflectionRay, objects, intersection.getObject(), clippingPlanes);
                            if (intersection == null) return getColorPercentage(background,  reflectiveness*reflect);
                            reflect = intersection.getObject().getReflectiveness();
                            pixelColor = getPixelColor(reflectionRay, reflectionIntersection, lights, background, objects, clippingPlanes, reflect);
                        }
                    }

                    if (intersection.getObject().isRefractive()) {
                        if (DEPTH == MAX_DEPTH) return pixelColor;
                        else {
                            Ray refractionRay = getRefractionRay(intersection, ray);
                            Intersection refractionIntersection = raycast(refractionRay, objects, intersection.getObject(), clippingPlanes);
                            pixelColor = addColor(pixelColor, getPixelColor(refractionRay, refractionIntersection, lights, background, objects, clippingPlanes, reflect));
                        }
                    }

                }
            }
    } else pixelColor = addColor(pixelColor, background);

    return pixelColor;
    }

    /**
     * This method throws a Ray from the Intersection of a main Camera thrown Ray to a Point Lights in a Scene,
     * to see if there is any Intersection before getting to the Light. This intersection, if there is any, is later
     * used to determine wether the Intersection point is in a shadow.
     * @param light in Scene
     * @param intersection of Ray thrown by main C
     * @param objects in Scene
     * @param clippingPlanes of main Camera
     * @return intersection for shadows, null when is illuminated
     */
    public static Intersection getShadowIntersection(Light light, Intersection intersection, ArrayList<Object3D> objects, float[] clippingPlanes) {
        Intersection shadowIntersection = null;
        if (light instanceof PointLight){
            Ray shadowRay = new Ray(Vector3D.add(intersection.getPosition(), new Vector3D(BIAS,BIAS,BIAS)), light.getPosition());
            shadowIntersection = raycast(shadowRay, objects, intersection.getObject(), clippingPlanes);
        }
        return shadowIntersection;
    }

    /**
     * Returns the Intersection of a Ray thrown by a point in a 3D Object that can do refraction. It is later used
     * to paint pixels of that Object. It uses the simple mathematic geometric rules that light follows.
     * @param intersection that got to the point
     * @param ray thrown to the point
     * @return refraction ray
     */
    public static Ray getRefractionRay(Intersection intersection, Ray ray){
        float n = (float) (1.0 / intersection.getObject().getRefractiveness());
        Vector3D I = ray.getDirection();
        Vector3D N = intersection.getNormal();
        double c1 = Vector3D.dotProduct(N, I);
        double c2 = Math.sqrt(1 - Math.pow(n, 2) * (1 - Math.pow(c1, 2)));

        Vector3D refraction =  Vector3D.add(Vector3D.scalarMultiplication(I, n), Vector3D.scalarMultiplication(N, n * c1 - c2));

        return new Ray(Vector3D.add(intersection.getPosition(), new Vector3D(BIAS,BIAS,BIAS)), refraction);
    }

    /**
     * Returns the Intersection of a Ray thrown by a point in a 3D Object that reflects. It is later used
     * to paint pixels of that Object. It is determined by the Ray 3D Vector and the normal of the intersected surface.
     * @param intersection with point
     * @param ray that intersects
     * @return intersection for reflection Rays
     */
    public static Ray getReflectionRay(Intersection intersection, Ray ray){
        Vector3D N = intersection.getNormal();
        Vector3D I = ray.getDirection();
        Vector3D reflection = Vector3D.subtract(I, Vector3D.scalarMultiplication(Vector3D.scalarMultiplication(N, Vector3D.dotProduct(N, I)), 2));

        return new Ray(Vector3D.add(intersection.getPosition(), new Vector3D(BIAS, BIAS, BIAS)), reflection);
    }

    /**
     * Normalizes the values of the given color and assigns them to a float array.
     * @param color to normalize
     * @return normalized color values in float array
     */
    public static float[] normalizeColor(Color color){
        return new float[]{color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f};
    }

    /**
     * Returns the percentage of a Color with the given percentage value.
     * @param color
     * @param percentage (value between 0.0 and 1.0)
     * @return color percentage
     */
    public static Color getColorPercentage(Color color, float percentage){
        return new Color(color.getRed() / 255.0f * percentage, color.getGreen() / 255.0f * percentage, color.getBlue() / 255.0f * percentage);
    }

    /**
     * Returns the diffuse Color of a point in an Object3D considering the light that is affecting it, including
     * its intensity and the light falloff.
     * @param light affecting the Object
     * @param intersection with the Object
     * @param objColors colors of the intersected Object
     * @return diffuse color of intersected point
     */
    public static Color getDiffuseColor(Light light, Intersection intersection, float[] objColors){
        float nDotL = light.getNDotL(intersection);
        // light falloff
        float intensity = (float) light.getIntensity(intersection.getPosition()) * nDotL;
        Color lightColor = light.getColor();
        float[] lightColors = normalizeColor(lightColor);
        float[] diffColors = objColors.clone();

        for (int colorIndex = 0; colorIndex < diffColors.length; colorIndex++)
            diffColors[colorIndex] *= intensity * lightColors[colorIndex] * DIFFUSE;

        return clampColors(diffColors, 0, 1);
    }

    /**
     * Returns the ambient Color of a point in an Object3D; it only considers the object color and the global ambient
     * coefficient.
     * @param objColors in a float array
     * @return ambient color of intersected point
     */
    public static Color getAmbientColor(float[] objColors){
        for (int colorIndex = 0; colorIndex < objColors.length; colorIndex++)
            objColors[colorIndex] *= AMBIENT;

        return clampColors(objColors, 0, 1);
    }

    /**
     * Returns the specular Color of a point in an Object3D considering the Blinn Phong half vector algorithm and
     * multiplying the value for the global specular coefficient..
     * @param light that affects the point
     * @param intersection with point
     * @param objColors colors of the object as a float array
     * @return specular color of intersect point
     */
    public static Color getSpecularColor(Light light, Intersection intersection, float[] objColors){
        Vector3D halfVector = Vector3D.normalize(Vector3D.add(light.getPosition(), intersection.getPosition()));
        double nDotH = Math.pow(Vector3D.dotProduct(intersection.getNormal(), halfVector), SHININESS);

        for (int colorIndex = 0; colorIndex < objColors.length; colorIndex++)
            objColors[colorIndex] *= nDotH * SPECULAR;

        return clampColors(objColors, 0, 1);
    }

    /**
     * This method uses clamp() to return a Color with its values only between the minimum and the maximum value.
     * @param colors
     * @return new color with clamped values
     */
    public static Color clampColors(float[] colors, float min, float max){
        return new Color(clamp(colors[0], min, max),
                        clamp(colors[1], min, max),
                        clamp(colors[2], min, max));
    }

    /**
     * Clamps a value to be between the minimum value and the maximum value.
     * @param value
     * @param min
     * @param max
     * @return
     */
    private static float clamp(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    /**
     * Adds two colors clamping each value to not go over 1 and before 0.
     * @param original color
     * @param otherColor to add
     * @return Color with the two colors components added
     */
    private static Color addColor(Color original, Color otherColor){
        float red = clamp((original.getRed() / 255.0f) + (otherColor.getRed() / 255.0f), 0, 1);
        float green = clamp((original.getGreen() / 255.0f) + (otherColor.getGreen() / 255.0f), 0, 1);
        float blue = clamp((original.getBlue() / 255.0f) + (otherColor.getBlue() / 255.0f), 0, 1);
        return new Color(red, green, blue);
    }

    /**
     * A raycast is to find the closest intersection of a thrown Ray. This method checks all objects to get
     * the closest intersection (if there are any) between de clipping planes. It gets as parameters the Ray to
     * cast, the list of 3D Objects in the Scene, the caster (who throwns the Ray) to not intersect itself, and
     * finally, the clipping planes for the Scene.
     *
     * The raycasting consists in checking object by object, the intersection with it (if there is any) until
     * the closes is found, also conditioning it to be between the near and far clipping planes.
     * @param ray to cast
     * @param objects list
     * @param caster of Ray
     * @param clippingPlanes of Scene
     * @return closest Intersection
     */
    private static Intersection raycast(Ray ray, ArrayList<Object3D> objects, Object3D caster, float[] clippingPlanes) {
        Intersection closestIntersection = null;
        boolean planes = false;
        if (clippingPlanes != null) planes = true;

        for (int k = 0; k < objects.size(); k++) {
            Object3D currentObj = objects.get(k);
            // not same triangle
            if (caster == null || !currentObj.equals(caster) || (currentObj.equals(caster) && currentObj.getIntersections(ray) > 1)) {
                Intersection intersection = currentObj.getIntersection(ray);

                if (intersection != null) {
                    double distance = intersection.getDistance();
                    double positionZ = intersection.getPosition().getZ();
                    boolean isCloser = closestIntersection == null || distance < closestIntersection.getDistance();
                    if (distance >= 0 && isCloser) {
                        if (planes) {
                            float nearPlane = clippingPlanes[0], farPlane = clippingPlanes[1];
                            boolean betweenPlanes = positionZ >= nearPlane && positionZ <= farPlane;
                            if (betweenPlanes) closestIntersection = intersection;

                        } else closestIntersection = intersection;
                    }
                }
            }
        }

        return closestIntersection;
    }

}
