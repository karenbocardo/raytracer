/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.raytracer.objects;

import up.edu.raytracer.IIntersectable;
import up.edu.raytracer.Intersection;
import up.edu.raytracer.Ray;
import up.edu.raytracer.Vector3D;

/**
 * Triangles are the simple shape to create any Polygon, and therefore, any shape. Triangles were chosen because it is
 * easier to calculate an intersection with it, base on barycentric coordinates and Möller and Trumbore algorithm. Triangles
 * use normals for ilumination and shading.
 *
 * @author Jafet Rodríguez
 * @author Karen Bocardo
 */
public class Triangle implements IIntersectable {

    public static final double EPSILON = 0.000000001;

    /**
     * Triangles have three vertices.
     */
    private Vector3D[] vertices;
    /**
     * Both vertices and faces have, each one, normals.
     */
    private Vector3D[] normals;

    /**
     * Triangle constructor asks for three separate vertices to avoid trying to build a Triangle with more than
     * three vertices.
     * @param vertex1 for Triangle
     * @param vertex2 for Triangle
     * @param vertex3 for Triangle
     */
    public Triangle(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3) {
        setVertices(vertex1, vertex2, vertex3);
        setNormals(null);
    }

    /**
     * Different constructor for Triangles; it, aside of three separate vertices, receives as parameters three normals,
     * each one for vertices
     * @param vertex1 for Triangle
     * @param vertex2 for Triangle
     * @param vertex3 for Triangle
     * @param normal1 for first vertex
     * @param normal2 for second vertex
     * @param normal3 for third vertex
     */
    public Triangle(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3, Vector3D normal1, Vector3D normal2, Vector3D normal3) {
        this(vertex1, vertex2, vertex3);
        setNormals(normal1, normal2, normal3);
    }

    /**
     * This constructor receives the two lists of vertices and normals as arrays and assigns them but conditioning
     * vertices to be only three.
     * @param vertices
     * @param normal
     */
    public Triangle(Vector3D[] vertices, Vector3D[] normal) {
        if (vertices.length == 3) setVertices(vertices[0], vertices[1], vertices[2]);
        else setVertices(Vector3D.ZERO(), Vector3D.ZERO(), Vector3D.ZERO());
        setNormals(normal);
    }

    /**
     * Returns Triangle vertices array.
     * @return vertices array
     */
    public Vector3D[] getVertices() {
        return vertices;
    }

    /**
     * This receives three vertices to construct a Triangle and not use more than three (because a shape with
     * more than three vertices is not a triangle. It is an overload that assigns the whole array of vertices with
     * the other setVertices method.
     * @param vertex1 for Triangle
     * @param vertex2 for Triangle
     * @param vertex3 for Triangle
     */
    public void setVertices(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3) {
        Vector3D[] vertices = new Vector3D[]{vertex1, vertex2, vertex3};
        setVertices(vertices);
    }

    /**
     * This method its private to use encapsulation and avoid building a Triangle with more than three vertices; it
     * assings the whole array of vertices.
     * @param vertices
     */
    private void setVertices(Vector3D[] vertices) {
        this.vertices = vertices;
    }

    /**
     * Returns the normals of the Triangle vertices; in case they are not calculated, uses the Triangle face
     * normal and assigns it equally to all the vertices.
     * @return
     */
    public Vector3D[] getNormals() {
        if(normals == null){
            Vector3D normal = getNormal();
            setNormals(new Vector3D[]{normal, normal, normal});
        }
        return normals;
    }

    /**
     * Returns the Triangle normal vector.
     * @return normal vector
     */
    //Modified
    public Vector3D getNormal() {
        Vector3D normal = Vector3D.ZERO();

        Vector3D[] normals = this.normals;
        if (normals == null) {
            Vector3D[] vertices = getVertices();
            Vector3D v = Vector3D.subtract(vertices[1], vertices[0]);
            Vector3D w = Vector3D.subtract(vertices[2], vertices[0]);

            normal = Vector3D.scalarMultiplication(Vector3D.normalize(Vector3D.crossProduct(v, w)), -1.0);
        } else {
            for(int i = 0; i < normals.length; i++){
                normal.setX(normal.getX() + normals[i].getX());
                normal.setY(normal.getY() + normals[i].getY());
                normal.setZ(normal.getZ() + normals[i].getZ());
            }
            normal.setX(normal.getX() / normals.length);
            normal.setY(normal.getY() / normals.length);
            normal.setZ(normal.getZ() / normals.length);
        }

        return normal;
    }

    public void setNormals(Vector3D[] normals) {
        this.normals = normals;
    }

    public void setNormals(Vector3D normal1, Vector3D normal2, Vector3D normal3) {
        Vector3D[] normals = new Vector3D[]{normal1, normal2, normal3};
        setNormals(normals);
    }

    /**
     * Since a Triangle implements IIntersectable, it should calculate an interesection with it. Generally, it
     * uses Möller-Trumbore algorithm to get it. When a Ray does not intersect a Triangle, the returned Intersection
     * is invalid. Otherwise, constructs an Intersection only setting its distance (with position, normal and object
     * as null), and returning it.
     * @see <a href="https://cadxfem.org/inf/Fast%20MinimumStorage%20RayTriangle%20Intersection.pdf">Moller-Trumbore intersection algorithm</a>
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        Intersection intersection = new Intersection(null, -1, null, null);

        Vector3D[] vertices = getVertices();
        Vector3D v2v0 = Vector3D.subtract(vertices[2], vertices[0]);
        Vector3D v1v0 = Vector3D.subtract(vertices[1], vertices[0]);
        Vector3D vectorP = Vector3D.crossProduct(ray.getDirection(), v1v0);
        double determinant = Vector3D.dotProduct(v2v0, vectorP);
        double invertedDeterminant = 1.0 / determinant;
        Vector3D vectorT = Vector3D.subtract(ray.getOrigin(), vertices[0]);
        double u = Vector3D.dotProduct(vectorT, vectorP) * invertedDeterminant;
        if (u < 0 || u > 1) return intersection;

        Vector3D vectorQ = Vector3D.crossProduct(vectorT, v2v0);
        double v = Vector3D.dotProduct(ray.getDirection(), vectorQ) * invertedDeterminant;
        if (v < 0 || (u + v) > (1.0 + EPSILON)) return intersection;

        double t = Vector3D.dotProduct(vectorQ, v1v0) * invertedDeterminant;
        intersection.setDistance(t);

        return intersection;
    }

    /**
     * Returns one since it is known a Ray always intersects a triangle once (because is a plane), but since Triangles do
     * not make shadows on themselves it is arbitrary.
     * @param ray
     * @return 1
     */
    @Override
    public int getIntersections(Ray ray) {
        return 1;
    }
}