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
 * Cameras are 3D objects meant to be put in a Scene just to "capture" an image of the objects in it. This object
 * is the one that throws the rays to the objects and so understand how is illumination affecting them, and how
 * it should look in real life.
 *
 * For the moment, this camera just points forward, so no rotation transformations are calculated.
 *
 * @author Jafet Rodríguez
 * @author Karen Bocardo
 */
public class Camera extends Object3D {
    /**
     * This attribute sets a default value for <i>z</i> component.
     */
    private float defaultZ = 15f;
    /**
     * The Camera's field of view is declared as a float array to save both field of view horizontal and
     * vertical, in positions 0 and 1 respectively. The field of view of a camera is practically the amount of
     * degrees a camera lens is open, or how much, of what is in front of it, the camera can see. Each value
     * represent precisely the degrees for the horizontal and vertical view respectively.
     */
    // 0 is fovh
    // 1 is fovv
    private float[] fieldOfView = new float[2];
    /**
     * This attribute saves the Camera resolution width and height in an array, that is, just in one variable.
     */
    // 0 is width
    // 1 is height
    private int[] resolution = new int[2];
    /**
     * Camera's clipping planes are stored in a float array; in position 0 is the near plane and in 1 is far one.
     */
    // 0 is near
    // 1 is far
    private float[] clippingPlanes = new float[2];

    /**
     * The Camera's constructor sets its attributes values when creating one, it includes Object3D constructor
     * because this class extends that one. Its parameters are the resolution width and heigh, which are sent
     * separately but joined in the resolution two int array; field of view is, as well, recieved in two separate
     * parameters but saved in the same fieldOfView array attribute.
     * It does not receive a color since cameras are not visible so its color does not really matter; sets black as a default color.
     * This constructor uses encapsulation.
     * @param position
     * @param fieldOfViewHorizontal
     * @param fieldOfViewVertical
     * @param widthResolution width of resolution
     * @param heightResolution height of resolution
     * @param nearPlane
     * @param farPlane
     */
    public Camera(Vector3D position, float fieldOfViewHorizontal, float fieldOfViewVertical, int widthResolution,
                  int heightResolution, float nearPlane, float farPlane) {
        super(position, Color.black);
        setFieldOfViewHorizontal(fieldOfViewHorizontal);
        setFieldOfViewVertical(fieldOfViewVertical);
        setResolution(new int[]{widthResolution, heightResolution});
        setClippingPlanes(new float[]{nearPlane, farPlane});
    }

    /**
     * Returns Camera's field of view both horizontal and vertical in a two size int array.
     * @return field of view array
     */
    public float[] getFieldOfView() {
        return fieldOfView;
    }

    /**
     * Sets Camera's field of view both horizontal and vertical assigning them in a two size int array.
     * @param fieldOfView two size float array
     */
    public void setFieldOfView(float[] fieldOfView) {
        this.fieldOfView = fieldOfView;
    }

    /**
     * Returns horizontal Camera's field of view, which is value in position 0 of the field of view float array.
     * @return horizontal field of view
     */
    public float getFieldOfViewHorizontal() {
        return fieldOfView[0];
    }

    /**
     * Sets horizontal Camera's field of view, which is value in position 0 of the field of view float array.
     * @param fov horizontal field of view
     */
    public void setFieldOfViewHorizontal(float fov) {
        fieldOfView[0] = fov;
    }

    /**
     * Returns vertical Camera's field of view, which is value in position 1 of the field of view float array.
     * @return vertical field of view
     */
    public float getFieldOfViewVertical() {
        return fieldOfView[1];
    }

    /**
     * Sets vertical Camera's field of view, which is value in position 1 of the field of view float array.
     * @param fov vertical field of view
     */
    public void setFieldOfViewVertical(float fov) {
        fieldOfView[1] = fov;
    }

    /**
     * Returns the default value defined for <i>z</i> component.
     * @return <i>z</i> component default value
     */
    public float getDefaultZ() {
        return defaultZ;
    }

    /**
     * Replaces the default value defined for <i>z</i> component with the value sent as parameter.
     * @param defaultZ new value for <i>z</i> component
     */
    public void setDefaultZ(float defaultZ) {
        this.defaultZ = defaultZ;
    }

    /**
     * Returns the array of numbers with the resolution's both width and height of the Camera.
     * @return resolution
     */
    public int[] getResolution() {
        return resolution;
    }

    /**
     * Sets the Camera resolution with the given array that contains both width and height.
     * @param resolution of the Camera
     */
    public void setResolution(int[] resolution) {
        this.resolution = resolution;
    }

    /**
     * Returns only the Camera's resolution width value.
     * @return width of resolution
     */
    public int getResolutionWidth() {
        return getResolution()[0];
    }

    /**
     * Returns only the Camera's resolution height value.
     * @return height of resolution
     */
    public int getResolutionHeight() {
        return getResolution()[1];
    }

    /**
     * Returns Camera's near and far clipping planes in an array of floats, with 0 as near and 1 as far.
     * @return clipping planes array
     */
    public float[] getClippingPlanes() {
        return clippingPlanes;
    }

    /**
     * Returns Camera's near clipping plane.
     * @return near clipping plane
     */
    public float getNearPlane() {
        return clippingPlanes[0];
    }

    /**
     * Returns Camera's far clipping plane.
     * @return far clipping plane
     */
    public float getFarPlane() {
        return clippingPlanes[1];
    }

    /**
     * Sets Camera's near and far clipping planes.
     * @param clippingPlanes array to set
     */
    public void setClippingPlanes(float[] clippingPlanes) {
        this.clippingPlanes = clippingPlanes;
    }

    /**
     * This method calculates, from the Camera, all the positions to the ray that the camera throws. It uses polar
     * coordinates along with the camera's field of view. It "cuts" each field of view to the half to make the
     * calculations. To set a standard measurement, it defines a maximum angle for both horizontal and vertical
     * field of view by subtracting to 90 degrees the half of the corresponding field of view, as well as a maximum
     * radius obtained from dividing <i>z</i> component value (that is the distance between the camera and the array
     * of pixels, over the maximum angle cosine (which represents the maximum distance between the ray to the center
     * of the field of view). Also, the maximum for <i>x</i> and <i>y</i> is gotten by the maximum angle sine times
     * the radius (in the case of <i>x</i> this is a negative value).
     *
     * The size of the two-dimensional array to return is defined by the Camera's resolution; it is where positions
     * are saved. Position in <i>x</i> is obtained by adding to the minimum the division of the range (maximum minus
     * minimum) over the resolution, all that, times the pixel <i>x</i> in the array. On the other hand, position in
     * <i>y</i> comes from subtracting to the maximum the division of the range over the resolution height, all of
     * that times the pixel <i>y</i> in the array.
     *
     * Finally the position is assigned to the pixel as a 3D Vector with all the positions as components (<i>z</i>
     * is always the default value).
     * @return two-dimensional array of 3D Vectors that represent the positions of the pixels in the image grid to ray
     */
    public Vector3D[][] calculatePositionsToRay() {
        float angleMaxX = 90 - (getFieldOfViewHorizontal() / 2f);
        float radiusMaxX = getDefaultZ() / (float) Math.cos(Math.toRadians(angleMaxX));

        float maxX = (float) Math.sin(Math.toRadians(angleMaxX)) * radiusMaxX;
        float minX = - maxX;

        float angleMaxY = 90 - (getFieldOfViewVertical() / 2f);
        float radiusMaxY = getDefaultZ() / (float) Math.cos(Math.toRadians(angleMaxY));

        float maxY = (float) Math.sin(Math.toRadians(angleMaxY)) * radiusMaxY;
        float minY = -maxY;

        Vector3D[][] positions = new Vector3D[getResolutionWidth()][getResolutionHeight()];
        float posZ = getDefaultZ();
        for (int x = 0; x < positions.length; x++) {
            for (int y = 0; y < positions[x].length; y++) {
                float posX = minX + (((maxX - minX) / (float) getResolutionWidth()) * x);
                float posY = maxY - (((maxY - minY) / (float) getResolutionHeight()) * y);
                positions[x][y] = new Vector3D(posX, posY, posZ);
            }
        }
        return positions;
    }

    /**
     * Since the camera does not matter visually in a Scene and any Ray intersects it, the calculation of an
     * Intersection is defined as a not valid Intersection.
     * @param ray that intersects
     * @return not valid Intersection
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }

    /**
     * Returns zero since the number of intersections with a Camera is irrelevant.
     * @param ray
     * @return 0
     */
    @Override
    public int getIntersections(Ray ray) {
        return 0;
    }
}
