import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Rand Ismaael
 * @version 1.0
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 * <p>
 * By typing 'I agree' below, you are agreeing that this is your
 * own work and that you are responsible for all the contents of
 * this file. If this is left blank, this homework will receive a zero.
 * <p>
 * Agree Here: I agree
 * @userid rismaael3
 * @GTID 903885377
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Input is invalid");
        }

        Set<Vertex<T>> visited = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> order = new LinkedList<>();

        // 1. Start with first node
        visited.add(start);
        queue.add(start);
        order.add(start);

        // 2. Process queue until empty
        while (!queue.isEmpty()) {
            Vertex<T> current = queue.remove();  // grab next node to process

            // 3. Check all neighbors
            for (VertexDistance<T> edge : graph.getAdjList().get(current)) {
                Vertex<T> neighbor = edge.getVertex();

                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);   // mark as seen
                    queue.add(neighbor);     // add to queue for later
                    order.add(neighbor);     // record in result
                }
            }
        }
        return order;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * <p>
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Input is invalid");
        }

        Set<Vertex<T>> visited = new HashSet<>();
        List<Vertex<T>> order = new LinkedList<>();

        dfsHelper(start, graph, visited, order);
        return order;
    }

    private static <T> void dfsHelper(Vertex<T> current,
                                      Graph<T> graph,
                                      Set<Vertex<T>> visited,
                                      List<Vertex<T>> order) {
        // 1. Process current node
        order.add(current);      // record in result
        visited.add(current);    // mark as seen

        // 2. Go deep into each unvisited neighbor
        for (VertexDistance<T> edge : graph.getAdjList().get(current)) {
            Vertex<T> neighbor = edge.getVertex();

            if (!visited.contains(neighbor)) {
                dfsHelper(neighbor, graph, visited, order);  // recurse (go deep)
            }
        }
    }
    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check if all the vertices have been visited.
     * 2) Check if the PQ is empty.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        // === SETUP ===
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Map<Vertex<T>, Integer> distances = new HashMap<>();  // final answer

        // Initialize all distances to infinity, start to 0
        for (Vertex<T> vertex : graph.getAdjList().keySet()) {
            if (vertex.equals(start)) {
                distances.put(start, 0);
            } else {
                distances.put(vertex, Integer.MAX_VALUE);
            }
        }

        // === ALGORITHM ===
        pq.add(new VertexDistance<>(start, 0));

        while (!pq.isEmpty() && visited.size() < graph.getVertices().size()) {

            // Grab closest unvisited vertex
            VertexDistance<T> current = pq.remove();
            Vertex<T> currentVertex = current.getVertex();
            int distToCurrentVertex = current.getDistance();

            if (!visited.contains(currentVertex)) {
                // Mark visited + record final distance
                visited.add(currentVertex);
                distances.put(currentVertex, distToCurrentVertex);

                // Check all neighbors
                for (VertexDistance<T> edge : graph.getAdjList().get(currentVertex)) {
                    Vertex<T> neighbor = edge.getVertex();
                    int edgeWeight = edge.getDistance();
                    int distToNeighbor = distToCurrentVertex + edgeWeight;

                    if (!visited.contains(neighbor)) {
                        pq.add(new VertexDistance<>(neighbor, distToNeighbor));
                    }
                }
            }
        }
        return distances;
    }
    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * You should NOT allow self-loops or parallel edges in the MST.
     * <p>
     * You may import/use java.util.PriorityQueue, java.util.Set, and any
     * class that implements the aforementioned interface.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        // === SETUP ===
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();
        Set<Edge<T>> mst = new HashSet<>();           // final answer: MST edges
        Set<Vertex<T>> visited = new HashSet<>();

        // === START: Add start's edges to PQ ===
        visited.add(start);
        for (VertexDistance<T> neighborInfo : graph.getAdjList().get(start)) {
            Vertex<T> neighbor = neighborInfo.getVertex();
            int edgeWeight = neighborInfo.getDistance();
            pq.add(new Edge<>(start, neighbor, edgeWeight));
        }

        // === ALGORITHM ===
        while (!pq.isEmpty()) {
            // Grab cheapest edge
            Edge<T> cheapestEdge = pq.remove();
            Vertex<T> oneEnd = cheapestEdge.getU();
            Vertex<T> otherEnd = cheapestEdge.getV();

            // Figure out which end is new (unvisited)
            Vertex<T> newVertex;
            if (visited.contains(oneEnd)) {
                newVertex = otherEnd;
            } else {
                newVertex = oneEnd;
            }

            // If new vertex is unvisited, add edge to MST
            if (!visited.contains(newVertex)) {
                visited.add(newVertex);

                // Add both directions (undirected graph)
                mst.add(cheapestEdge);
                mst.add(new Edge<>(otherEnd, oneEnd, cheapestEdge.getWeight()));

                // Add all edges from newVertex to PQ
                for (VertexDistance<T> neighborInfo : graph.getAdjList().get(newVertex)) {
                    Vertex<T> neighbor = neighborInfo.getVertex();
                    int edgeWeight = neighborInfo.getDistance();

                    if (!visited.contains(neighbor)) {
                        pq.add(new Edge<>(newVertex, neighbor, edgeWeight));
                    }
                }
            }
        }

        // === CHECK: Did we reach all vertices? ===
        // MST should have 2*(V-1) edges (both directions)
        if (mst.size() != 2 * (graph.getVertices().size() - 1)) {
            return null;  // Graph was disconnected
        }
        return mst;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     * <p>
     * An MST should NOT have self-loops or parallel edges.
     * <p>
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskal's to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        // === SETUP ===

        // Initialize disjoint set — each vertex starts in its own set
        DisjointSet<Vertex<T>> ds = new DisjointSet<>();
        for (Vertex<T> vertex : graph.getVertices()) {
            ds.find(vertex);  // creates singleton set for each vertex
        }

        Set<Edge<T>> mst = new HashSet<>();

        // Sort all edges by weight (PQ does this)
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();
        pq.addAll(graph.getEdges());

        // === ALGORITHM ===
        // Stop when MST has V-1 edges
        while (!pq.isEmpty() && mst.size() < graph.getVertices().size() - 1) {

            // Grab cheapest edge
            Edge<T> cheapestEdge = pq.remove();
            Vertex<T> oneEnd = cheapestEdge.getU();
            Vertex<T> otherEnd = cheapestEdge.getV();

            // Check: are they in different sets? (different roots = no cycle)
            Vertex<T> root1 = ds.find(oneEnd);
            Vertex<T> root2 = ds.find(otherEnd);

            if (!root1.equals(root2)) {
                // Safe to add — won't create cycle
                mst.add(cheapestEdge);
                ds.union(oneEnd, otherEnd);
            }
            // If same root → skip (would create cycle)
        }

        // === CHECK: Did we get enough edges? ===
        if (mst.size() < graph.getVertices().size() - 1) {
            return null;  // Graph was disconnected
        }

        // === Add both directions (undirected graph) ===
        Set<Edge<T>> result = new HashSet<>();
        for (Edge<T> edge : mst) {
            result.add(edge);
            result.add(new Edge<>(edge.getV(), edge.getU(), edge.getWeight()));
        }

        return result;
    }
}