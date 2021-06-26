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
 * This class implements the competition using Dijkstra's algorithm
 */

import java.io.*;
import java.util.*;

public class CompetitionDijkstra {
    private static final int INVALID_CODE = -1;
    private static final double POSITIVE_INFINITY = Double.POSITIVE_INFINITY;
    private static final double NEGATIVE_INFINITY = Double.NEGATIVE_INFINITY;
    private static final int MINIMUM_SPEED = 50;
    private static final int MAXIMUM_SPEED = 100;

    private Graph graph;
    private int speedA;
    private int speedB;
    private int speedC;

    /**
     * @param filename: A filename containing the details of the city road network
     * @param sA, sB, sC: speeds for 3 contestants
     */

    public CompetitionDijkstra(String filename, int sA, int sB, int sC) {
        try {
            this.graph = new Graph(filename);
        } catch (IOException e) {
            this.graph = null;
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
        double maxDistBtwTwoNodes = maxDistance();
        // System.out.println(" maxDistace - "+maxDistBtwTwoNodes);
        int metersInKilometers = 1000;
        if (maxDistBtwTwoNodes <= 0)
            return INVALID_CODE;
        int TimeRequired = (int) Math.ceil((maxDistBtwTwoNodes * metersInKilometers) / minSpeed);
        return TimeRequired;
    }

    /**
     * @return the distance of the two vertices which are the furthest from each
     *         other
     */
    private double maxDistance() {
        if (graph == null || graph.isInvalid)
            return INVALID_CODE;
        int vertices = graph.vertices;
        double maxDist = NEGATIVE_INFINITY;
        for (int i = 0; i < vertices; i++) {
            double[] distances = createShortestPaths(i);
            for (int j = 0; j < vertices; j++) {
                if (i == j)
                    continue;
                double IJDist = distances[j];
                if (IJDist == POSITIVE_INFINITY)
                    return INVALID_CODE;
                maxDist = Math.max(maxDist, IJDist);
            }
        }
        return maxDist;
    }

    // returns the shortest path table created with the dijkstra's algorithm
    private double[] createShortestPaths(int StartVertex) {
        Map<Integer, List<Edge>> adjs = graph.adjs;
        boolean[] observed = new boolean[graph.vertices];
        double[] ToDistance = new double[graph.vertices];
        double filler = POSITIVE_INFINITY;
        Arrays.fill(ToDistance, filler);
        ToDistance[StartVertex] = 0;
        Queue<Integer> PQ = new PriorityQueue<>(Comparator.comparing(vertex -> ToDistance[vertex]));
        PQ.add(StartVertex);
        while (!PQ.isEmpty()) {
            int current = PQ.poll();
            observed[current] = true;
            for (Edge adjacent : adjs.getOrDefault(current, new ArrayList<>())) {
                int vertex = adjacent.ToEdge;
                if (!observed[vertex]) {
                    double newDistance = ToDistance[current] + adjacent.cost;
                    if (newDistance < ToDistance[vertex])
                        ToDistance[vertex] = newDistance;
                    PQ.remove(vertex);
                    PQ.add(vertex);
                }
            }
        }
        return ToDistance;
    }

    private static class Graph {
        private int vertices;
        private Map<Integer, List<Edge>> adjs;
        private boolean isInvalid;

        private Graph(String FileName) throws IOException {
            String EmptyStr = "";
            if (FileName == null || EmptyStr.equals(FileName))
                return;
            // System.out.println("Graph for "+FileName);
            adjs = new HashMap<>();
            BufferedReader reader = new BufferedReader(new FileReader(FileName));
            this.vertices = Integer.parseInt(reader.readLine());
            int edges = Integer.parseInt(reader.readLine());
            int i = 0;
            String line = reader.readLine();
            while (line != null) {
                Scanner scanner = new Scanner(line);
                int vertexFrom = scanner.nextInt();
                int vertexTo = scanner.nextInt();
                double cost = scanner.nextDouble();
                List<Edge> adjList = adjs.getOrDefault(vertexFrom, new ArrayList<>());
                adjList.add(new Edge(vertexFrom, vertexTo, cost));
                adjs.put(vertexFrom, adjList);
                scanner.close();
                line = reader.readLine();
                i++;
            }
            if (i != edges)
                this.isInvalid = true;
            else
                reader.close();
        }
    }

    private static class Edge {
        private int FromEdge;
        private int ToEdge;
        double cost;

        private Edge(int FromEdge, int ToEdge, double cost) {
            this.FromEdge = FromEdge;
            this.ToEdge = ToEdge;
            this.cost = cost;
        }
    }

    // public static void main(String[] args) {
    // int a = 50,b=60,c=75;
    // CompetitionDijkstra competitionDijkstra = new CompetitionDijkstra(null, a, b,
    // c);
    // System.out.println("e");
    // competitionDijkstra = new CompetitionDijkstra("input-E.txt", a, b, c);
    // System.out.println("Time -
    // "+competitionDijkstra.timeRequiredforCompetition());

    // System.out.println("f");
    // competitionDijkstra = new CompetitionDijkstra("input-F.txt", a, b, c);
    // System.out.println("Time -
    // "+competitionDijkstra.timeRequiredforCompetition());

    // System.out.println("g");
    // competitionDijkstra = new CompetitionDijkstra("input-G.txt", a, b, c);
    // System.out.println("Time -
    // "+competitionDijkstra.timeRequiredforCompetition());

    // System.out.println("h");
    // competitionDijkstra = new CompetitionDijkstra("input-H.txt", a, b, c);
    // System.out.println("Time -
    // "+competitionDijkstra.timeRequiredforCompetition());

    // // assertEquals("Testing Dijkstra for tinyEWD.txt",38, );
    // System.out.println("Time -
    // "+competitionDijkstra.timeRequiredforCompetition());
    // System.out.println("done");

    // }
}
