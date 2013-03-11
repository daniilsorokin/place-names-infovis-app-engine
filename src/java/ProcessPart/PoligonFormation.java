package ProcessPart;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import baseclasses.Tuple;
import com.google.code.ekmeans.EKmeans;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//import com.tomgibara.cluster.gvm.dbl.*;

/**
 *
 * @author agnia
 */
public class PoligonFormation {


    public static List<List<Tuple>> getPolygons(List<Tuple> toponyms) {
        List<List<Tuple>> polygons = new ArrayList<List<Tuple>>();

        double[][] points = new double[toponyms.size()][2];
        for (int i = 0; i < toponyms.size(); i++) {
            points[i][0] = (Double) toponyms.get(i).first;
            points[i][1] = (Double) toponyms.get(i).second;
        }
        Random random = new Random(System.currentTimeMillis());
        int numClusters = 3;
        double[][] centroids = new double[numClusters][2]; // say we want 3 clusters
        for (int i = 0; i < numClusters; i++) { // pick the necessary amount of existing points to use as centroids
            centroids[i][0] = (Double) toponyms.get(Math.abs(random.nextInt(toponyms.size()))).first;
            centroids[i][1] = (Double) toponyms.get(Math.abs(random.nextInt(toponyms.size()))).second;
            polygons.add(new ArrayList<Tuple>());
        }
        EKmeans eKmeans = new EKmeans(centroids, points);
        eKmeans.run();

        int[] assignments = eKmeans.getAssignments();
        for (int j = 0; j < eKmeans.getAssignments().length; j++) {
            polygons.get(assignments[j]).add(new Tuple(points[j][0], points[j][1]));
        }

        for (List<Tuple> cluster : polygons) {
            System.out.println("Cluster before: " + cluster.size());
            if (cluster.size() > 2) {
                List<Point> tuplesAsPoints = new ArrayList<Point>(cluster.size());
                for (Tuple tuple : cluster) {
                    tuplesAsPoints.add(tupleToPoint(tuple));
                }
                List<Point> polygon = GrahamScanOriginal.getConvexHull(tuplesAsPoints);
                cluster.clear();
                for (Point point : polygon) {
                    cluster.add(pointToTuple(point));
                }
            }
            System.out.println("Cluster after: " + cluster.size());
        }

        return polygons;
    }

    private static Point tupleToPoint(Tuple<Double, Double> tuple) {
        return new Point((int)(tuple.first * 10000), (int)(tuple.second * 10000));
    }

    private static Tuple pointToTuple(Point point) {
        return new Tuple((double)point.x / 10000, (double)point.y / 10000);
    }
}
