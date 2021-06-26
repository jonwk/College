// * @author John Wesley Kommala

/*
 * A Contest to Meet (ACM) is a reality TV contest that sets three contestants at three random
 * city intersections. In order to win, the three contestants need all to meet at any intersection
 * of the city as fast as possible.
 * It should be clear that the contestants may arrive at the intersections at different times, in
 * which case, the first to arrive can wait until the others arrive.
 * From an estimated walking speed for each one of the three contestants, ACM wants to determine the
 * minimum time that a live TV broadcast should last to cover their journey regardless of the contestantsâ€™
 * initial positions and the intersection they finally meet. You are hired to help ACM answer this question.
 * You may assume the following:
 *    ï‚· Each contestant walks at a given estimated speed.
 *    ï‚· The city is a collection of intersections in which some pairs are connected by one-way
 * streets that the contestants can use to traverse the city.
 *
 * This class implements the competition using Floyd-Warshall algorithm
 */

import java.io.*;
import java.util.*;

public class CompetitionFloydWarshall {
    private static final int INVALID_CODE = -1;
    private static final double POSITIVE_INFINITY = Double.POSITIVE_INFINITY;
    private static final double NEGATIVE_INFINITY = Double.NEGATIVE_INFINITY;
    private static final int MINIMUM_SPEED = 50;
    private static final int MAXIMUM_SPEED = 100;

    private double[][] distances;
    private int speedA, speedB, speedC;
    private int vertices;
    private boolean isInvalid;

    /**
     * @param filename: A filename containing the details of the city road network
     * @param sA,       sB, sC: speeds for 3 contestants
     */
    public CompetitionFloydWarshall(String filename, int sA, int sB, int sC) {
        try {
            initDistArr(filename);
        } catch (IOException e) {
            this.isInvalid = true;
        }
        this.speedA = sA;
        this.speedB = sB;
        this.speedC = sC;
    }

    /**
     * @return int: minimum minutes that will pass before the three contestants can
     *         meet
     */
    public int timeRequiredforCompetition() {
        int minSpeedAB = Math.min(speedA, speedB);
        int maxSpeedAB = Math.max(speedA, speedB);
        int minSpeed = Math.min(minSpeedAB, speedC);
        int maxSpeed = Math.max(maxSpeedAB, speedC);
        if (minSpeed < MINIMUM_SPEED || maxSpeed > MAXIMUM_SPEED)
            return INVALID_CODE;
        createDistArr();
        double maxDistBtwAnyTwoNodes = maxDistance();
        if (maxDistBtwAnyTwoNodes <= 0)
            return INVALID_CODE;
        int metersInKilometers = 1000;
        int TimeRequired = (int) Math.ceil((maxDistBtwAnyTwoNodes * metersInKilometers) / minSpeed);
        // System.out.println("max distance "+maxDistBtwAnyTwoNodes+"\n time
        // "+TimeRequired);
        return TimeRequired;
    }

    // returns the distance between two vertices that are farthest apart from each
    // other
    private double maxDistance() {
        // if (this.isInvalid || distances == null)
        //     return INVALID_CODE;
        double maxDist = NEGATIVE_INFINITY;
        for (int i = 0; i < this.vertices; i++) {
            for (int j = 0; j < this.vertices; j++) {
                if (i == j)
                    continue;
                double IJDist = distances[i][j];
                if (IJDist == POSITIVE_INFINITY)
                    return -1;
                maxDist = Math.max(maxDist, IJDist);
            }
        }
        return maxDist;
    }

    // Floyd warshall's algorithm on the graph, with the distances in an array
    private void createDistArr() {
        if (this.isInvalid)
            return;
        for (int k = 0; k < this.vertices; k++)
            for (int i = 0; i < this.vertices; i++)
                for (int j = 0; j < this.vertices; j++)
                    if (distances[k][j] + distances[i][k] < distances[i][j])
                        distances[i][j] = distances[k][j] + distances[i][k];
    }

    private void initDistArr(String FileName) throws IOException {
        String EmptyStr = "";
        if (FileName == null || EmptyStr.equals(FileName))
            return;
        // System.out.println("graph for "+FileName);
        BufferedReader reader = new BufferedReader(new FileReader(FileName));
        this.vertices = Integer.parseInt(reader.readLine());
        this.distances = new double[this.vertices][this.vertices];
        for (double[] distance : distances) {
            Arrays.fill(distance, POSITIVE_INFINITY);
        }
        int edges = Integer.parseInt(reader.readLine());
        int i = 0;
        String line = reader.readLine();
        while (line != null) {
            Scanner scanner = new Scanner(line);
            int vertexFrom = scanner.nextInt();
            int vertexTo = scanner.nextInt();
            double cost = scanner.nextDouble();
            distances[vertexFrom][vertexFrom] = 0;
            distances[vertexFrom][vertexTo] = cost;
            scanner.close();
            line = reader.readLine();
            i++;
        }
        if (i != edges)
            this.isInvalid = true;
        else
            reader.close();
    }

    // public static void main(String[] args) {
    // int a = 50,b=60,c=75;
    // CompetitionFloydWarshall competitionFloydWarshall = new
    // CompetitionFloydWarshall(null, a, b, c);

    // competitionFloydWarshall = new CompetitionFloydWarshall("tinyEWD.txt", a, b,
    // c);

    // System.out.println("Time -
    // "+competitionFloydWarshall.timeRequiredforCompetition());
    // System.out.println("done");

    // }
}