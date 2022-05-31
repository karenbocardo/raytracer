/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.raytracer.objects;

import up.edu.raytracer.Intersection;
import up.edu.raytracer.Ray;
import up.edu.raytracer.Vector3D;
import up.edu.raytracer.tools.Barycentric;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Polygon is a 3D Object conformed by a list of Triangles; it can shape anything.
 *
 * @author Jafet Rodríguez
 * @author Karen Bocardo
 */
public class Polygon extends Object3D {

    public List<Triangle> triangles;

    /**
     * Returns the list of Triangles that conform the Polygon.
     * @return
     */
    public List<Triangle> getTriangles() {
        return triangles;
    }

    /**
     * Polygon constructor receives as parameters the Polygon position, the list of Triangles as an array and its 
     * color. Uses Object3D constructor to set position and color.
     * @param position of Triangle
     * @param triangles array
     * @param color
     */
    public Polygon(Vector3D position, Triangle[] triangles, Color color){
        super(position, color);
        setTriangles(triangles);
    }

    /**
     * Receives as parameter an array of Triangles. Solves the problem of repeated vertices when joining Triangles
     * in a Polygon, using a set data structure. It also sets each vertex position globally (with the Polygon
     * position) adding the position of the Polygon so the vertex is placed in a Scene correctly.
     * @param triangles
     */
    public void setTriangles(Triangle[] triangles) {
        Vector3D position = getPosition();
        Set<Vector3D> uniqueVertices = new HashSet<Vector3D>();
        for(Triangle triangle : triangles) uniqueVertices.addAll(Arrays.asList(triangle.getVertices()));

        for(Vector3D vertex : uniqueVertices){
            vertex.setX(vertex.getX() + position.getX());
            vertex.setY(vertex.getY() + position.getY());
            vertex.setZ(vertex.getZ() + position.getZ());
        }

        this.triangles = Arrays.asList(triangles);
    }

    /**
     * This method finds the "best" (closest) Intersection of all the Intersections with each Triangle of the Polygon's
     * list. When reading the intersection normal, uses barycentric coordinates to determine the normal of the point in
     * the Triangle according to the normals of its vertices, adding them multiplied by its respective
     * barycentric coordinate.
     *
     * @param ray that intersects
     * @return closest intersection of Polygon Triangles
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        double distance = -1;
        Vector3D normal = Vector3D.ZERO();
        Vector3D position = Vector3D.ZERO();

        for(Triangle triangle : getTriangles()){
            Intersection intersection = triangle.getIntersection(ray);
            double intersectionDistance = intersection.getDistance();
            if(intersection != null && intersectionDistance > 0 && (intersectionDistance < distance || distance < 0)){
                distance = intersectionDistance;
                position = Vector3D.add(ray.getOrigin(), Vector3D.scalarMultiplication(ray.getDirection(), distance));

                // normal
                normal = Vector3D.ZERO();

                // barycentric part for normals
                double[] uvw = Barycentric.CalculateBarycentricCoordinates(position, triangle);
                Vector3D[] normals = triangle.getNormals();
                for(int i = 0; i < uvw.length; i++)
                    normal = Vector3D.add(normal, Vector3D.scalarMultiplication(normals[i], uvw[i]));
            }
        }

        if(distance == -1) return null;

        return new Intersection(position, distance, normal, this);
    }

    /**
     * Returns the number of Intersections a Ray does to the Polygon. This method is implemented in Ray Tracing shadows.
     * @param ray
     * @return number of intersections
     */
    @Override
    public int getIntersections(Ray ray) {
        int intersections = 0;
        for(Triangle triangle : getTriangles()){
            Intersection intersection = triangle.getIntersection(ray);
            if(intersection != null && intersection.getDistance() > 0 ) intersections++;
        }
        return intersections;
    }


}
