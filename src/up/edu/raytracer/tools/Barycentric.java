/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.raytracer.tools;

import up.edu.raytracer.Vector3D;
import up.edu.raytracer.objects.Triangle;

/**
 * Barycentric is a tool class that calculates barycentric coordinates for a point in a Triangle, based on
 *  Christer Ericson's Real-Time Collision Detection.
 *
 * @author Jafet Rodríguez
 * @see <a href="https://www.amazon.com/Real-Time-Collision-Detection-Interactive-Technology/dp/1558607323">Christer Ericson's Real-Time Collision Detection</a>
 */
public class Barycentric {

    private Barycentric() {
    }

    /**
     * This method calculates the barycentric coordinates for the given point in a Triangle.
     * @param point in triangle
     * @param triangle
     * @return barycentric coordinates
     */
    // Based on Christer Ericson's Real-Time Collision Detection
    public static double[] CalculateBarycentricCoordinates(Vector3D point, Triangle triangle) {
        double u, v, w;
        Vector3D[] vertices = triangle.getVertices();
        Vector3D a = vertices[0];
        Vector3D b = vertices[1];
        Vector3D c = vertices[2];

        Vector3D v0 = Vector3D.subtract(b, a);
        Vector3D v1 = Vector3D.subtract(c, a);
        Vector3D v2 = Vector3D.subtract(point, a);
        double d00 = Vector3D.dotProduct(v0, v0);
        double d01 = Vector3D.dotProduct(v0, v1);
        double d11 = Vector3D.dotProduct(v1, v1);
        double d20 = Vector3D.dotProduct(v2, v0);
        double d21 = Vector3D.dotProduct(v2, v1);
        double denominator = d00 * d11 - d01 * d01;
        v = (d11 * d20 - d01 * d21) / denominator;
        w = (d00 * d21 - d01 * d20) / denominator;
        u = 1.0 - v - w;

        return new double[]{u, v, w};
    }

}
