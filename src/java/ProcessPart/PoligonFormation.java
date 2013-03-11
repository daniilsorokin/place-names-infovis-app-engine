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
        if (toponyms.size() < 3) {
            polygons.add(toponyms);
            return polygons;
        }

        double[][] points = new double[toponyms.size()][2];
        for (int i = 0; i < toponyms.size(); i++) {
            points[i][0] = (Double) toponyms.get(i).first;
            points[i][1] = (Double) toponyms.get(i).second;
        }
        Random random = new Random(System.currentTimeMillis());
        int numClusters = (int) Math.sqrt(toponyms.size()/2); // http://en.wikipedia.org/wiki/Determining_the_number_of_clusters_in_a_data_set
        double[][] centroids = new double[numClusters][2];
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
            if (cluster.size() > 2) {
                List<Point> tuplesAsPoints = new ArrayList<Point>(cluster.size());
                for (Tuple tuple : cluster) {
                    tuplesAsPoints.add(tupleToPoint(tuple));
                }
                List<Point> polygon = null;
                try {
                    polygon = GrahamScanOriginal.getConvexHull(tuplesAsPoints);
                } catch (IllegalArgumentException ex) { // if all points are in one line
                    polygon = tuplesAsPoints;
                }
                cluster.clear();
                for (Point point : polygon) {
                    cluster.add(pointToTuple(point));
                }
            }
        }

        return polygons;
    }

    private static Point tupleToPoint(Tuple<Double, Double> tuple) {
        return new Point((int)(tuple.first * 100000), (int)(tuple.second * 100000));
    }

    private static Tuple pointToTuple(Point point) {
        return new Tuple((double)point.x / 100000, (double)point.y / 100000);
    }


}
