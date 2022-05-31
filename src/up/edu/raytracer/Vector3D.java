/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.raytracer;

/**
 * This class is implemented to be used as a Vector in a 3D space (specifically, in a Scene). It is basically the
 * whole core of the entire RayTracer because every thing to be manipulated is a Vector. Vector 3D can be both a point
 * and a directional vector.
 *
 * @author Jafet Rodríguez
 * @author Karen Bocardo
 */
public class Vector3D {

    /**
     * This Vector has all components equal to zero to represent the vector zero; it is not instantiable so
     * every class can use it and its values can not change. It is available to not create it every
     * time is needed (because it is really useful).
     */
    private static final Vector3D ZERO = new Vector3D(0.0, 0.0, 0.0);
    /**
     * The three components needed for any Vector in a third dimension.
     */
    private double x, y, z;

    /**
     * 3D Vector constructor with bigger encapsulation. It receives the three components of the vector and
     * sets all those values with encapsulation.
     * @param x component value
     * @param y component value
     * @param z component value
     */
    public Vector3D(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    /**
     * Returns x component of the 3D Vector.
     * @return x component value
     */
    public double getX() {
        return x;
    }

    /**
     * Sets x component of the 3D Vector.
     * @param x component value
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns y component of the 3D Vector.
     * @return y component value
     */
    public double getY() {
        return y;
    }

    /**
     * Sets y component of the 3D Vector.
     * @param y component value
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Returns z component of the 3D Vector.
     * @return z component value
     */
    public double getZ() {
        return z;
    }

    /**
     * Sets z component of the 3D Vector.
     * @param z component value
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * This method returns the dot product between two 3D Vectors. The calculation of the dot product consists in
     * the addition of each component multiplication.
     * @param vectorA first vector to use
     * @param vectorB second vector to use
     * @return the dot product between the two Vectors
     */
    public static double dotProduct(Vector3D vectorA, Vector3D vectorB){
        return (vectorA.getX() * vectorB.getX()) + (vectorA.getY() * vectorB.getY()) + (vectorA.getZ() * vectorB.getZ());
    }

    /**
     * Returns a 3D Vector with components of a cross product result. That is, the combination of component products
     * in a determinant (the first 3D Vector name is A and the second one is B):
     * <ul>
     *     <li>vector A <i>y</i> component times the vector B <i>z</i> component minus vector A <i>z</i> times vector B
     *     <i>y</i> is the first component of the result</li>
     *     <li>vector A <i>z</i> times vector B <i>x</i> minus vector A <i>x</i> times vector B <i>z</i> is the
     *     second (<i>y</i>) component of the result</li>
     *     <li>vector A <i>x</i> times vector B <i>y</i> minus vector A <i>y</i> times vector B <i>x</i> is the
     *     last component of the result</li>
     * </ul>
     * Finally it creates an instance of Vector3D with the components mentioned before and return it.
     * @param vectorA first vector to use
     * @param vectorB second vector to use
     * @return
     */
    public static Vector3D crossProduct(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D((vectorA.getY() * vectorB.getZ()) - (vectorA.getZ() * vectorB.getY()),
                            (vectorA.getZ() * vectorB.getX()) - (vectorA.getX() * vectorB.getZ()),
                            (vectorA.getX() * vectorB.getY()) - (vectorA.getY() * vectorB.getX()));
    }

    /**
     * This method returns the magnitude value of the given 3D Vector. Magnitude is the result of the square root
     * of the dot product of the vector with itself; it can be obtained by other calculations but this one was
     * choose to make it easier.
     * @param vector to get magnitude
     * @return magnitude of the vector
     */
    public static double magnitude(Vector3D vector){
        return Math.sqrt(dotProduct(vector, vector));
    }

    /**
     * This method allows two vectors to be added; two 3D Vectors are sent as parameters and their components
     * are each added to become a new Vector component respectively.
     * @param vectorA first vector to add
     * @param vectorB first vector to add
     * @return new Vector3D with the two Vectors' components added
     */
    public static Vector3D add(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() + vectorB.getX(),
                            vectorA.getY() + vectorB.getY(),
                            vectorA.getZ() + vectorB.getZ());
    }

    /**
     * This method allows two vectors to be subtracted; two 3D Vectors are sent as parameters and their components
     * are each subtracted to become a new Vector component respectively.
     * @param vectorA first vector to subtract
     * @param vectorB first vector to subtract
     * @return new Vector3D with the two Vectors' components subtracted
     */
    public static Vector3D subtract(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() - vectorB.getX(),
                            vectorA.getY() - vectorB.getY(),
                            vectorA.getZ() - vectorB.getZ());
    }

    /**
     * This method normalizes a 3D Vector: makes its components' values remain between minus one and one (as if it
     * were percentual). It takes the Vector3D as a parameter and uses its magnitude to normalize it:
     * the return is a new instance of Vector3D with the sent vector components' values divided by its magnitude.
     * @param vector to normalize
     * @return normalized vector
     */
    public static Vector3D normalize(Vector3D vector){
        double mag = Vector3D.magnitude(vector);
        return new Vector3D(vector.getX() / mag, vector.getY() / mag, vector.getZ() / mag);
    }

    /**
     * This allows a 3D Vector to be multiplied by any scalar. That is done by multiplying each component
     * by this scalar and using the result as a component of a new Vector.
     * @param vector to be multiplied
     * @param scalar to multiply by
     * @return the vector multiplied by a scalar
     */
    public static Vector3D scalarMultiplication(Vector3D vector, double scalar){
        return new Vector3D(vector.getX() * scalar, vector.getY() * scalar, vector.getZ() * scalar);
    }

    /**
     * toString method gives the possibility of printing the components of the Vector3D instead of just the Object
     * direction when casting the Vector3D to String.
     * @return Vector3D components in a String
     */
    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
    }

    /**
     * This method allows to make a Vector3D clone of the Vector who calls it; it is possible by simply
     * returning a new instance of Vector3D with the same values of the components.
     * @return Vector3D with same components' values
     */
    public Vector3D clone(){
        return new Vector3D(getX(), getY(), getZ());
    }

    /**
     * Returns a clone of the static Vector3D named ZERO.
     * @return clone of Vector3D ZERO
     */
    public static Vector3D ZERO(){
        return ZERO.clone();
    }
}
