package cpen221.mp2.graph;


import cpen221.mp2.util.Groups;
import cpen221.mp2.util.Pair;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Dependencies: Vertex.java, Edge.java
 *
 * Represents a graph with vertices of type V using an Adjacency List.
 *
 * Representation Invariant:
 * - VertexMap associates each vertex with its neighbors, none of which can be null.
 * - The size of vertexMap is equal to the number of Vertex's in the graph, n.
 * - The number of neighbors (value in vertexMap) associated with each vertex cannot exceed n - 1
 * - No value in edgeMap can be null
 * - edgeMap keys must be a Pair containing two vertexes contained in vertexMap
 * - The number of edges (values in edgeMap) cannot exceed n(n-1)/2
 * - Each vertex associated to an edge must be within the graph
 *
 * Abstraction Function:
 * Represents a graph of n vertices as a vertexMap HashMap of size n.
 * Each value in vertexMap represents a list of neighboring vertexes.
 * Represents the m connections between vertices as an edgeMap HashMap of size m.
 * Each key in edgeMap is a Pair of the vertices being connected by the edge stored as the value in edgeMap.
 *
 * @param <V> represents a vertex type
 */
public class Graph<V extends Vertex, E extends Edge<V>> implements ImGraph<V, E>, IGraph<V, E> {

    private Map<V, List<V>> vertexMap;
    private Map<Pair<V>, E> edgeMap;

    public Graph() {
        vertexMap = new HashMap<V, List<V>>();
        edgeMap = new HashMap<Pair<V>, E>();
    }


    /**
     * Add a vertex to the graph
     *
     * @param v vertex to add
     * @return true if the vertex was added successfully and false if it already exists in graph
     */
    @Override
    public boolean addVertex(V v) {

        if (vertex(v)) {
            return false;
        }

        vertexMap.putIfAbsent(v, new ArrayList<>());

        return true;
    }

    /**
     * Check if a vertex is part of the graph
     *
     * @param v vertex to check in the graph
     * @return true if v is part of the graph and false otherwise
     */
    @Override
    public boolean vertex(V v) {

        for (V vertex : vertexMap.keySet()) {
            if (vertex.id() == v.id()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Add an edge of the graph
     *
     * @param e the edge to add to the graph
     * @return true if the edge was successfully added
     * @return false if the edge already exists in the graph
     * @return false if either vertex does not exist in the graph
     */
    @Override
    public boolean addEdge(E e) {

        if (!vertex(e.v1()) || !vertex(e.v2())) {
            return false;
        }

        if (edge(e)) {
            return false;
        }

        vertexMap.get(e.v1()).add(e.v2());
        vertexMap.get(e.v2()).add(e.v1());

        Pair<V> edgePair = new Pair<V>(e.v1(),e.v2());
        edgeMap.putIfAbsent(edgePair, e);

        return true;
    }

    /**
     * Check if an edge is part of the graph
     *
     * @param e the edge to check in the graph
     * @return true if e is an edge in the graph and false otherwise
     */
    @Override
    public boolean edge(E e) {
        return edge(e.v1(), e.v2());
    }

    /**
     * Check if v1-v2 is an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return true of the v1-v2 edge is part of the graph and false otherwise
     */
    @Override
    public boolean edge(V v1, V v2) {
        Pair<V> edgePair = new Pair<V>(v1,v2);

        if(vertexMap.get(v1).contains(v2) && vertexMap.get(v2).contains(v1) && edgeMap.get(edgePair) != null) {
            return true;
        }

        return false;
    }

    /**
     * Determine the length on an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return the length of the edge formed by v1 and v2
     * @throws IllegalArgumentException if edge formed by v1 and v2 does not exist in the graph
     */
    @Override
    public int edgeLength(V v1, V v2) throws IllegalArgumentException {

        Pair<V> edgePair = new Pair<V>(v1,v2);

        E newEdge = edgeMap.get(edgePair);

        if (newEdge == null) {
            throw new IllegalArgumentException();
        }

        return newEdge.length();

    }

    /**
     * Obtain the sum of the lengths of all edges in the graph
     *
     * @return the sum of the lengths of all edges in the graph
     */
    @Override
    public int edgeLengthSum() {
        int sum = 0;

        for (E edge: edgeMap.values()) {
            sum += edge.length();
        }

        return sum;
    }

    /**
     * Remove an edge from the graph
     *
     * @param e the edge to remove
     * @param e is an edge within the graph
     * @return true if e was successfully removed and false otherwise
     */
    @Override
    public boolean remove(E e) {

        if(!edge(e)) {
            return false;
        }

        vertexMap.get(e.v1()).remove(e.v2());
        vertexMap.get(e.v2()).remove(e.v1());

        Pair<V> edgePair = new Pair<V>(e.v1(), e.v2());
        edgeMap.remove(edgePair);

        edgeMap.values().remove(e);

        return true;
    }

    /**
     * Remove a vertex from the graph
     *
     * @param v the vertex to remove
     * @param v is a vertex within the graph
     * @return true if v was successfully removed and false otherwise
     */
    @Override
    public boolean remove(V v) {

        if(!vertex(v)) {
            return false;
        }

        vertexMap.remove(v);

        for (List<V> planetList : vertexMap.values()) {
            planetList.remove(v);
        }

        for (Pair<V> edgePair : edgeMap.keySet()) {
            if (edgePair.contains(v)) {
                edgeMap.remove(edgePair);
                edgeMap.values().remove(edgeMap.remove(edgePair));
            }
        }

        return true;
    }

    /**
     * Obtain a set of all vertices in the graph.
     * Access to this set **should not** permit graph mutations.
     *
     * @return a set of all vertices in the graph
     */
    @Override
    public Set<V> allVertices() {

        Set<V> vertexSet = new HashSet<>(vertexMap.keySet());

        return vertexSet;
    }

    /**
     * Obtain a set of all vertices incident on v.
     * Access to this set **should not** permit graph mutations.
     *
     * @param v the vertex of interest
     * @return all edges incident on v
     */
    @Override
    public Set<E> allEdges(V v) {

        Set<E> edgeSet = new HashSet<>();

        for (E edge: edgeMap.values()) {
            if (edge.v1() == v || edge.v2() == v) {
                edgeSet.add(edge);
            }
        }

        return edgeSet;
    }

    /**
     * Obtain a set of all edges in the graph.
     * Access to this set **should not** permit graph mutations.
     *
     * @return all edges in the graph
     */
    @Override
    public Set<E> allEdges() {
        Set<E> edgeSet = new HashSet<E>();

        for(E edge : edgeMap.values()) {
            edgeSet.add(edge);
        }

        return edgeSet;
    }

    /**
     * Obtain all the neighbours of vertex v.
     * Access to this map **should not** permit graph mutations.
     *
     * @param v is the vertex whose neighbourhood we want.
     * @return a map containing each vertex w that neighbors v and the edge between v and w.
     * @throws IllegalArgumentException if vertex v does not exist in the graph
     */
    @Override
    public Map<V, E> getNeighbours(V v) throws IllegalArgumentException {
        Map<V,E> neighbourMap = new HashMap<>();

        if (!vertex(v)) {
            throw new IllegalArgumentException();
        }

        for (V planet : vertexMap.get(v)) {
            Pair<V> edgePair = new Pair<>(planet, v);
            E edge = edgeMap.get(edgePair);
            neighbourMap.put(planet, edge);
        }


        return neighbourMap;
    }

    /**
     * Call the dijkstra method to compute the shortest path
     * from the source vertex to the sink vertex
     *
     * @param source the start vertex
     * @param sink   the end vertex
     * @return the vertices, in order, on the shortest path from source to sink (both end points are part of the list),
     * if source and sink are not connected by any combination of edges in the graph, returns empty list.
     */
    @Override
    public List<V> shortestPath(V source, V sink) {
        Map<V,Integer> distFromSource = new HashMap<V, Integer>();
        Map<V,List<V>> pathFromSource = new HashMap<V, List<V>>();

        dijkstra(source, distFromSource, pathFromSource);

        return pathFromSource.get(sink);
    }

    /**
     * Compute the shortest path from the given source to the given sink.
     * The given sink will be used as a parameter within the driver method.
     *
     * @param source the start vertex
     * @param distFromSource a hashmap of vertices with respective distances from source
     * @param pathFromSource a hashmap of vertices and list of vertices representing the path from the source
     */
    private void dijkstra (V source, Map<V,Integer> distFromSource, Map<V,List<V>> pathFromSource) {
        List<V> visited = new ArrayList<V>();

        for (V planet: vertexMap.keySet()) {
            //Initialize the distFromSource map to be all the vertices in planetMap as keys and set each of their values to Integer.MAX_VALUE
            distFromSource.put(planet,Integer.MAX_VALUE);

            pathFromSource.put(planet, new ArrayList<>());

            //add source as the first planet in the shortest path from source to any other Vertex in the graph
            pathFromSource.get(planet).add(source);
        }

        //Give the source vertex a distance value of 0 as it is 0 away from itself
        distFromSource.replace(source, 0);

        for (int i = 0; i < vertexMap.size(); i++) {
            //Get the closest Vertex by calling the shortestDist method
            V minVertex = shortestDist(distFromSource, pathFromSource, visited, source);

            visited.add(minVertex);

            //Call the updateDistance method to then update the distance from the source to the minVertex neighbor Vertexes
            updateDistance(minVertex, visited, distFromSource, pathFromSource);

            if (pathFromSource.get(minVertex).size() == 1 && minVertex != source) {
                pathFromSource.get(minVertex).remove(0);
                distFromSource.replace(minVertex, Integer.MAX_VALUE);
            }
        }


    }

    /**
     * Finds the vertex which is the shortest distance to the next vertex.
     *
     * @param distFromSource a hashmap of vertices with respective distances from source
     * @param pathFromSource a hashmap of vertices and list of vertices representing the path from the source
     * @param visited a list of all the visited vertices
     * @param source the start vertex
     * @return the vertex which gives the shortest distance to the next vertex
     */
    private V shortestDist(Map<V,Integer> distFromSource, Map<V,List<V>> pathFromSource, List<V> visited, V source) {
        int minDist = Integer.MAX_VALUE;
        V minVertex = source;

        for (V planet : vertexMap.keySet()) {
            //If the vertex given in planetMap has not been visited and the distance from the source to that vertex is less than minDist
            if (!visited.contains(planet) && distFromSource.get(planet) <= minDist) {
                minDist = distFromSource.get(planet);
                minVertex = planet;
            }
        }

        return minVertex;
    }

    /**
     * Update the distance from the source and the path from the source for closePlanet's neighbor Vertexes
     *
     * @param closePlanet the closest planet to the current vertex
     * @param visited a list of all the visited vertices
     * @param distFromSource a hashmap of vertices with respective distances from source
     * @param pathFromSource a hashmap of vertices and list of vertices representing the path from the source
     */
    private void updateDistance (V closePlanet, List<V> visited, Map<V,Integer> distFromSource, Map<V,List<V>> pathFromSource) {
        Map<V, E> neighbors =  getNeighbours(closePlanet);

        // iterate over the adjacent planets
        for (V neighbor : neighbors.keySet()) {
            if (!visited.contains(neighbor)) {
                E edge = neighbors.get(neighbor);
                int distance = distFromSource.get(closePlanet) + edge.length();
                List<V> path = new ArrayList<>(pathFromSource.get(closePlanet));
                path.add(neighbor);

                //Replace the key-value pair in both distFromSource and pathFromSource if this new distance is less than distance from the source to neighbor
                if (distance < distFromSource.get(neighbor)) {
                    distFromSource.replace(neighbor, distance);
                    pathFromSource.replace(neighbor, path);
                }
            }
        }
    }

    /**
     * Compute the minimum spanning tree of the graph.
     * Graph must not have any "islands" or disconnected componenets.
     *
     * @return a list of edges that forms a minimum spanning tree of the graph
     */
    @Override
    public List<E> minimumSpanningTree() {
        List<E> tree = new ArrayList<E>();
        Groups groups = new Groups();

        // sort edges in acsending order
        List<E> edgeList = edgeMap.values().stream().sorted((e1, e2) -> e1.length() - (e2.length())).collect(Collectors.toList());

        for (E edge : edgeList) {
            groups.add(edge.v1());
        }

        int i = 0;
        while (tree.size() != vertexMap.keySet().size() - 1) {
            E next = (E) edgeList.get(i++);

            Vertex head1 = groups.find(next.v1());
            Vertex head2 = groups.find(next.v2());

            if (head1 != head2) {
                // if the head vertex for each vertex that forms edge is different
                // we know that they do not form a loop in the graph and can be added to the
                // minimum spanning tree
                tree.add(next);
                groups.merge(head1, head2);
            }

        }

        return tree;
    }

    /**
     * Compute the length of a given path
     *
     * @param path indicates the vertices on the given path. Each pair of
     *             contiguous Vertexes in path must form an edge within graph.
     * @return the length of path.
     */
    @Override
    public int pathLength(List<V> path) {
        int totalLength = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            Pair<V> edgePair = new Pair<>(path.get(i), path.get(i + 1));
            E edge = edgeMap.get(edgePair);

            totalLength += edge.length();
        }

        return totalLength;
    }

    /**
     * Call the recursiveSearch method.
     *
     * @param v     the vertex to start the search from.
     *              v must exist in the graph.
     * @param range the radius of the search. range >= 1
     * @return a set of vertices that are within range of v (this set does not contain v).
     */
    @Override
    public Set<V> search(V v, int range) {

        Set<V> vertexList = new HashSet<V>();

        vertexList = recursiveSearch(v, range, vertexList, v);

        return vertexList;
    }

    /**
     * Recursively obtain all vertices that are no more than a path distance of range from v.
     *
     * @param v     the vertex to start the search from.
     * @param range the radius of the search.
     * @param vertexList the new set of vertices to be added to and returned.
     * @param orig the original vertex to start the search from, which doesn't change.
     * @return a set of vertices that are within range of v (this set does not contain v).
     */
    private Set<V> recursiveSearch(V v, int range, Set<V> vertexList, V orig) {
        //Base case: adds a vertex from planetMap to vertexList if it is not the same as the original vertex
        if (range == 1) {
            for (V vertex : vertexMap.get(v)) {
                if (vertex.equals(orig)) {
                    continue;
                } else {
                    vertexList.add(vertex);
                }
            }
        }
         //Recursive case: Call recursiveSearch with each vertex in planetMap, decrementing the range by one each call
         else {
            for (V vertex: vertexMap.get(v)) {
                vertexList = recursiveSearch(vertex, range - 1, vertexList, orig);
            }
        }

        return vertexList;
    }

    /**
     * Compute the diameter of the graph by finding the
     * length of the longest shortest path in the graph.
     *
     * @return the diameter of the graph.
     */
    @Override
    public int diameter() {
        int max = 0;

        for (V planet : vertexMap.keySet()) {
            Map<V,Integer> distFromSource = new HashMap<V, Integer>();
            Map<V,List<V>> pathFromSource = new HashMap<V, List<V>>();

            //Call the dijkstra method to compute all shortest paths
            dijkstra(planet, distFromSource, pathFromSource);

            List diameters = new ArrayList(distFromSource.values());

            if (diameters.contains(Integer.MAX_VALUE)) {
                diameters.removeAll(Collections.singleton(Integer.MAX_VALUE));
            }

            //Sort all the diameters
            Collections.sort(diameters, Collections.reverseOrder());

            //Find length of the the longest shortest path
            if (max < (int) diameters.get(0)) {
                max = (int) diameters.get(0);
            }
        }

        if (max == 0) {
            return Integer.MAX_VALUE;
        }

        return max;
    }

    /**
     * Find the edge that connects two vertices if such an edge exists.
     * This method should not permit graph mutations.
     *
     * @param v1 one end of the edge
     * @param v2 the other end of the edge
     * @return the edge connecting v1 and v2.
     * @throws IllegalArgumentException if v1, v2 don't form an edge in the graph
     */
    @Override
    public E getEdge(V v1, V v2) throws IllegalArgumentException {
        Pair<V> edgePair = new Pair<>(v1, v2);

        if (edgeMap.get(edgePair) != null) {
            return edgeMap.get(edgePair);
        }

        throw new IllegalArgumentException();
    }

    /**
     * This method removes some edges at random while preserving connectivity
     * <p>
     * DO NOT CHANGE THIS METHOD
     * </p>
     * <p>
     * You will need to implement allVertices() and allEdges(V v) for this
     * method to run correctly
     *</p>
     * <p><strong>requires:</strong> this graph is connected</p>
     *
     * @param rng random number generator to select edges at random
     */
    public void pruneRandomEdges(Random rng) {
        class VEPair {
            V v;
            E e;

            public VEPair(V v, E e) {
                this.v = v;
                this.e = e;
            }
        }
        /* Visited Nodes */
        Set<V> visited = new HashSet<>();
        /* Nodes to visit and the cpen221.mp2.graph.Edge used to reach them */
        Deque<VEPair> stack = new LinkedList<VEPair>();
        /* Edges that could be removed */
        ArrayList<E> candidates = new ArrayList<>();
        /* Edges that must be kept to maintain connectivity */
        Set<E> keep = new HashSet<>();

        V start = null;
        for (V v : this.allVertices()) {
            start = v;
            break;
        }
        if (start == null) {
            // nothing to do
            return;
        }
        stack.push(new VEPair(start, null));
        while (!stack.isEmpty()) {
            VEPair pair = stack.pop();
            if (visited.add(pair.v)) {
                keep.add(pair.e);
                for (E e : this.allEdges(pair.v)) {
                    stack.push(new VEPair(e.distinctVertex(pair.v), e));
                }
            } else if (!keep.contains(pair.e)) {
                candidates.add(pair.e);
            }
        }
        // randomly trim some candidate edges
        int iterations = rng.nextInt(candidates.size());
        for (int count = 0; count < iterations; ++count) {
            int end = candidates.size() - 1;
            int index = rng.nextInt(candidates.size());
            E trim = candidates.get(index);
            candidates.set(index, candidates.get(end));
            candidates.remove(end);
            remove(trim);
        }
    }
}
