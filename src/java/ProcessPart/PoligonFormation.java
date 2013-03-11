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

//    public static void main(String[] args) {
//        getPolygons(loadOvo());
//    }

//    public static List<List<Tuple>> getPolygons(List<Tuple> toponyms) {
//        List<List<Tuple>> polygons = new ArrayList<List<Tuple>>();
//        DblClusters<Double> clusters = new DblClusters<Double>(2, 3);
//        for (Tuple toponym : toponyms) {
//            clusters.add(1.0, new double[]{(Double) toponym.first, (Double) toponym.second}, Double.NaN);
//        }
////        clusters.reduce(-1, 1);
//        List<DblResult<Double>> results = clusters.results();
//        for (DblResult<Double> result: results) {
//            List<Tuple> singleGroupPoligons = new ArrayList<Tuple>(result.getCount());
//
//            polygons.add(singleGroupPoligons);
//        }
//
//        return polygons;
//    }

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

//    public static List<Tuple> loadOvo() {
//        List<Tuple> toponyms = new ArrayList<Tuple>();
//        toponyms.add(new Tuple(59.63, 28.93));
//        toponyms.add(new Tuple(59.73333, 28.81667));
//        toponyms.add(new Tuple(59.56667, 28.78333));
//        toponyms.add(new Tuple(59.56667, 28.76667));
//        toponyms.add(new Tuple(59.53333, 28.02));
//        toponyms.add(new Tuple(59.65, 28.03333));
//        toponyms.add(new Tuple(59.55, 28.96667));
//        toponyms.add(new Tuple(59.7, 28.76667));
//        toponyms.add(new Tuple(59.56667, 28.88333));
//        toponyms.add(new Tuple(59.93333, 31.88333));
//        toponyms.add(new Tuple(59.48333, 28.78333));
//        toponyms.add(new Tuple(59.68333, 28.03333));
//        toponyms.add(new Tuple(59.41667, 28.88333));
//        toponyms.add(new Tuple(59.66667, 28.45));
//        toponyms.add(new Tuple(59.76667, 28.10001));
//        toponyms.add(new Tuple(59.76667, 28.18333));
//        toponyms.add(new Tuple(59.6, 28.78333));
//        toponyms.add(new Tuple(59.36667, 28.85));
//        toponyms.add(new Tuple(59.61667, 28.93333));
//        toponyms.add(new Tuple(59.68333, 28.76667));
//        toponyms.add(new Tuple(59.61667, 28.83333));
//        toponyms.add(new Tuple(59.8, 28.63333));
//        toponyms.add(new Tuple(59.68333, 28.78333));
//        toponyms.add(new Tuple(59.55, 28.73333));
//        toponyms.add(new Tuple(59.65, 28.58333));
//        toponyms.add(new Tuple(59.41667, 28.35));
//        toponyms.add(new Tuple(59.56667, 28.73333));
//        toponyms.add(new Tuple(59.73333, 28.78333));
//        toponyms.add(new Tuple(59.8, 28.66667));
//        toponyms.add(new Tuple(59.61667, 28.13333));
//        toponyms.add(new Tuple(59.71667, 28.03333));
//        toponyms.add(new Tuple(59.61667, 28.9));
//        toponyms.add(new Tuple(59.63333, 28.75));
//        toponyms.add(new Tuple(59.61667, 28.5));
//        toponyms.add(new Tuple(59.71667, 28.46667));
//        return toponyms;
//    }
//
//    public static List<Tuple> loadAll() {
//        List<Tuple> toponyms = new ArrayList<Tuple>();
//        toponyms.add(new Tuple(59.42, 28.52));
//        toponyms.add(new Tuple(59.43, 28.76));
//        toponyms.add(new Tuple(59.63, 28.93));
//        toponyms.add(new Tuple(59.58, 28.57));
//        toponyms.add(new Tuple(59.67, 28.57));
//        toponyms.add(new Tuple(59.38333, 28.88333));
//        toponyms.add(new Tuple(59.66667, 28.71667));
//        toponyms.add(new Tuple(59.65, 28.53333));
//        toponyms.add(new Tuple(59.58333, 28.18333));
//        toponyms.add(new Tuple(59.73333, 28.81667));
//        toponyms.add(new Tuple(59.56667, 28.78333));
//        toponyms.add(new Tuple(59.75, 28.66667));
//        toponyms.add(new Tuple(59.4, 28.83333));
//        toponyms.add(new Tuple(59.78333, 28.48333));
//        toponyms.add(new Tuple(60.33333, 28.5));
//        toponyms.add(new Tuple(59.65, 28.78333));
//        toponyms.add(new Tuple(59.63333, 28.6));
//        toponyms.add(new Tuple(59.6, 28.86667));
//        toponyms.add(new Tuple(59.48333, 28.08333));
//        toponyms.add(new Tuple(59.65, 28.65));
//        toponyms.add(new Tuple(59.3, 28.85));
//        toponyms.add(new Tuple(59.78333, 28.48333));
//        toponyms.add(new Tuple(59.56667, 28.76667));
//        toponyms.add(new Tuple(59.53333, 28.02));
//        toponyms.add(new Tuple(59.66667, 28.21667));
//        toponyms.add(new Tuple(59.65, 28.03333));
//        toponyms.add(new Tuple(59.54, 28.755));
//        toponyms.add(new Tuple(59.8, 28.5));
//        toponyms.add(new Tuple(59.7, 28.88333));
//        toponyms.add(new Tuple(59.55, 28.9));
//        toponyms.add(new Tuple(59.68458, 29.3999));
//        toponyms.add(new Tuple(59.55236, 29.63571));
//        toponyms.add(new Tuple(59.46667, 28.09));
//        toponyms.add(new Tuple(59.55, 28.96667));
//        toponyms.add(new Tuple(59.75, 28.46667));
//        toponyms.add(new Tuple(59.48333, 29.76667));
//        toponyms.add(new Tuple(59.06667, 28.2));
//        toponyms.add(new Tuple(58.94, 29.63278));
//        toponyms.add(new Tuple(59.37851, 29.50013));
//        toponyms.add(new Tuple(59.4, 29.79));
//        toponyms.add(new Tuple(59.43333, 28.41667));
//        toponyms.add(new Tuple(58.96833, 29.36917));
//        toponyms.add(new Tuple(59.2861, 28.8708));
//        toponyms.add(new Tuple(59.7, 28.76667));
//        toponyms.add(new Tuple(59.36667, 28.90001));
//        toponyms.add(new Tuple(59.66667, 28.88333));
//        toponyms.add(new Tuple(59.56667, 28.88333));
//        toponyms.add(new Tuple(59.5, 28.15));
//        toponyms.add(new Tuple(59.63333, 28.66667));
//        toponyms.add(new Tuple(59.93333, 31.88333));
//        toponyms.add(new Tuple(59.46667, 28.25));
//        toponyms.add(new Tuple(59.48333, 28.78333));
//        toponyms.add(new Tuple(59.5, 28.75));
//        toponyms.add(new Tuple(59.46667, 28.73333));
//        toponyms.add(new Tuple(59.397, 28.57));
//        toponyms.add(new Tuple(59.3, 28.71667));
//        toponyms.add(new Tuple(59.36667, 28.33333));
//        toponyms.add(new Tuple(59.68333, 28.03333));
//        toponyms.add(new Tuple(59.71667, 28.75));
//        toponyms.add(new Tuple(59.63333, 28.65));
//        toponyms.add(new Tuple(59.48333, 28.13333));
//        toponyms.add(new Tuple(59.41667, 28.88333));
//        toponyms.add(new Tuple(59.66667, 28.45));
//        toponyms.add(new Tuple(59.58333, 28.751));
//        toponyms.add(new Tuple(59.6, 28.75));
//        toponyms.add(new Tuple(59.43333, 28.38333));
//        toponyms.add(new Tuple(59.73333, 28.48333));
//        toponyms.add(new Tuple(59.65, 28.28333));
//        toponyms.add(new Tuple(59.97354, 29.33929));
//        toponyms.add(new Tuple(59.76667, 28.10001));
//        toponyms.add(new Tuple(59.53333, 28.26667));
//        toponyms.add(new Tuple(59.48333, 28.93333));
//        toponyms.add(new Tuple(59.58333, 28.9));
//        toponyms.add(new Tuple(59.76667, 28.18333));
//        toponyms.add(new Tuple(59.46667, 28.88333));
//        toponyms.add(new Tuple(59.8, 28.5));
//        toponyms.add(new Tuple(59.8, 28.5));
//        toponyms.add(new Tuple(59.58333, 28.96667));
//        toponyms.add(new Tuple(58.8925, 28.525));
//        toponyms.add(new Tuple(59.85909, 29.92342));
//        toponyms.add(new Tuple(59.45, 28.85));
//        toponyms.add(new Tuple(59.4, 28.9));
//        toponyms.add(new Tuple(59.65, 28.73333));
//        toponyms.add(new Tuple(59.61667, 28.18333));
//        toponyms.add(new Tuple(59.6, 28.78333));
//        toponyms.add(new Tuple(59.45, 28.31667));
//        toponyms.add(new Tuple(59.36667, 28.85));
//        toponyms.add(new Tuple(59.41667, 28.56667));
//        toponyms.add(new Tuple(59.48333, 28.85));
//        toponyms.add(new Tuple(59.661, 28.618));
//        toponyms.add(new Tuple(59.58333, 28.93333));
//        toponyms.add(new Tuple(59.65, 28.68333));
//        toponyms.add(new Tuple(59.61667, 28.65));
//        toponyms.add(new Tuple(59.63333, 28.23333));
//        toponyms.add(new Tuple(59.63333, 28.78333));
//        toponyms.add(new Tuple(59.18234, 29.04259));
//        toponyms.add(new Tuple(59.61667, 28.93333));
//        toponyms.add(new Tuple(59.35, 28.91667));
//        toponyms.add(new Tuple(59.68333, 28.76667));
//        toponyms.add(new Tuple(59.61667, 28.83333));
//        toponyms.add(new Tuple(59.8, 28.63333));
//        toponyms.add(new Tuple(59.76667, 28.83333));
//        toponyms.add(new Tuple(59.38333, 28.56667));
//        toponyms.add(new Tuple(59.69452, 29.08307));
//        toponyms.add(new Tuple(59.41667, 28.91667));
//        toponyms.add(new Tuple(59.43333, 28.83333));
//        toponyms.add(new Tuple(59.48333, 28.25));
//        toponyms.add(new Tuple(59.68333, 28.78333));
//        toponyms.add(new Tuple(59.4, 28.55));
//        toponyms.add(new Tuple(59.76667, 28.51667));
//        toponyms.add(new Tuple(59.76667, 28.73333));
//        toponyms.add(new Tuple(59.63333, 28.96667));
//        toponyms.add(new Tuple(59.55, 28.73333));
//        toponyms.add(new Tuple(59.58333, 28.46667));
//        toponyms.add(new Tuple(59.65, 28.58333));
//        toponyms.add(new Tuple(59.26667, 28.85));
//        toponyms.add(new Tuple(59.65, 28.2));
//        toponyms.add(new Tuple(59.41667, 28.35));
//        toponyms.add(new Tuple(59.58333, 28.73333));
//        toponyms.add(new Tuple(59.71667, 28.66667));
//        toponyms.add(new Tuple(59.5, 28.83333));
//        toponyms.add(new Tuple(59.56667, 28.73333));
//        toponyms.add(new Tuple(59.56667, 28.16667));
//        toponyms.add(new Tuple(59.76667, 28.48333));
//        toponyms.add(new Tuple(59.61667, 28.71667));
//        toponyms.add(new Tuple(59.6, 28.61667));
//        toponyms.add(new Tuple(59.46667, 28.85));
//        toponyms.add(new Tuple(59.43333, 28.4));
//        toponyms.add(new Tuple(59.48333, 28.06667));
//        toponyms.add(new Tuple(59.61867, 29.00734));
//        toponyms.add(new Tuple(59.7, 28.83333));
//        toponyms.add(new Tuple(59.43333, 28.31667));
//        toponyms.add(new Tuple(59.60481, 29.03291));
//        toponyms.add(new Tuple(59.6017, 29.51571));
//        toponyms.add(new Tuple(59.73333, 28.78333));
//        toponyms.add(new Tuple(59.31667, 28.81667));
//        toponyms.add(new Tuple(59.8, 28.66667));
//        toponyms.add(new Tuple(59.61667, 28.13333));
//        toponyms.add(new Tuple(59.3, 28.88333));
//        toponyms.add(new Tuple(59.545, 28.65));
//        toponyms.add(new Tuple(59.71667, 28.03333));
//        toponyms.add(new Tuple(59.38333, 28.9));
//        toponyms.add(new Tuple(59.58333, 28.88333));
//        toponyms.add(new Tuple(59.61667, 28.9));
//        toponyms.add(new Tuple(59.6, 28.96667));
//        toponyms.add(new Tuple(59.63333, 28.75));
//        toponyms.add(new Tuple(59.75, 28.81667));
//        toponyms.add(new Tuple(59.66667, 28.31667));
//        toponyms.add(new Tuple(59.54971, 28.98904));
//        toponyms.add(new Tuple(59.53333, 28.93333));
//        toponyms.add(new Tuple(59.61667, 28.5));
//        toponyms.add(new Tuple(59.56667, 28.15));
//        toponyms.add(new Tuple(59.71667, 28.76667));
//        toponyms.add(new Tuple(59.71667, 28.46667));
//        toponyms.add(new Tuple(59.26667, 28.91667));
//        toponyms.add(new Tuple(59.41667, 28.81667));
//        toponyms.add(new Tuple(59.43333, 28.3));
//        toponyms.add(new Tuple(59.65, 28.08333));
//        toponyms.add(new Tuple(59.62056, 30.39));
//        toponyms.add(new Tuple(59.4, 28.58333));
//        toponyms.add(new Tuple(59.48333, 28.86667));
//        toponyms.add(new Tuple(59.4, 28.38333));
//        toponyms.add(new Tuple(59.43333, 28.43333));
//        toponyms.add(new Tuple(59.4, 28.7));
//        toponyms.add(new Tuple(59.39384, 29.42692));
//        return toponyms;
//    }
}
