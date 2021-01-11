package cpen221.mp2.spaceship;

import cpen221.mp2.controllers.GathererStage;
import cpen221.mp2.controllers.HunterStage;
import cpen221.mp2.controllers.Kamino;
import cpen221.mp2.controllers.Spaceship;
import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.graph.ImGraph;
import cpen221.mp2.graph.Vertex;
import cpen221.mp2.models.Link;
import cpen221.mp2.models.Planet;
import cpen221.mp2.models.PlanetStatus;
import cpen221.mp2.util.Heap;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * An instance implements the methods needed to complete the mission.
 */
public class MillenniumFalcon implements Spaceship {
    long startTime = System.nanoTime(); // start time of rescue phase

    @Override
    public void hunt(HunterStage state) {
        Vertex currentPlanet = new Vertex(state.currentID(), "Earth");
        List<Vertex> visited = new ArrayList<>();

        Stack<Vertex> stack = new Stack<>();
        stack.push(currentPlanet);

        boolean onKamino = traverse(state, currentPlanet, visited);

        if (onKamino) {
            return;
        }
    }

    private boolean traverse(HunterStage state, Vertex currentPlanet, List<Vertex> visited) {
        if (state.onKamino() == true) {
            return true;
        }

        visited.add(currentPlanet);

        PlanetStatus[] neighbors = state.neighbors();

        List<PlanetStatus> neighborList = Arrays.stream(neighbors).sorted((p1,p2) -> Double.compare(p2.signal(),(p1.signal()))).collect(Collectors.toList());

        for (PlanetStatus neighbor : neighborList) {
            Vertex neighborPlanet = new Vertex(neighbor.id(), neighbor.name());
            if (!visited.contains(neighborPlanet)) {
                state.moveTo(neighbor.id());
                boolean onKamino = traverse(state, neighborPlanet, visited);
                if (onKamino) {
                    return true;
                }
                state.moveTo(currentPlanet.id());
            }
        }
        return false;
    }

    @Override
    public void gather(GathererStage state) {
        ImGraph g = state.planetGraph();
        List<Vertex> visited = new ArrayList<>();

        Stack<Planet> stack = new Stack<>();
        stack.push(state.currentPlanet());

        Map<Planet, Integer> minimumFuel = dijkstra(state.earth(), g, state);

        collectSpice(state, g, visited, stack, minimumFuel);
    }

    private void collectSpice(GathererStage state, ImGraph g, List<Vertex> visited, Stack<Planet> stack, Map<Planet, Integer> minimumFuel) {
//        long currentTime = System.nanoTime();
//        long maxTime = 14500000000L;
//
//        if (currentTime - startTime >= maxTime) {
//            goToEarth(state, state.currentPlanet(), state.earth(), g);
//            return;
//        }

        int remainingFuel = state.fuelRemaining();

        Planet earth = state.earth();
        Planet currentPlanet = state.currentPlanet();

        visited.add(currentPlanet);

        Set<Planet> neighborSet = g.search(currentPlanet, 1);
        List<Planet> neighborList = neighborSet.stream().sorted((p1, p2) -> p2.spice() - (p1.spice())).collect(Collectors.toList());

        int minimumFuelRequired = minimumFuel.get(state.currentPlanet()) + 1000;

        if (visited.containsAll(neighborList)) {
            if (stack.peek() == currentPlanet) {
                stack.pop();
            }
            Link path = (Link) g.getEdge(currentPlanet, stack.peek());
            if (remainingFuel - path.fuelNeeded() > minimumFuelRequired) {
                state.moveTo(stack.peek());
                collectSpice(state, g, visited, stack, minimumFuel);
                return;
            } else {
                goToEarth(state, state.currentPlanet(), earth, g);
                return;
            }
        }

        for (Planet neighbor: neighborList) {
            Link path = (Link) g.getEdge(currentPlanet, neighbor);

            if (!visited.contains(neighbor)) {
                if (remainingFuel - path.fuelNeeded() > minimumFuelRequired) {
                    stack.add(neighbor);
                    state.moveTo(neighbor);
                    collectSpice(state, g, visited, stack, minimumFuel);
                    return;
                } else {
                    goToEarth(state, state.currentPlanet(), earth, g);
                    return;
                }
            }
        }
    }

    private void goToEarth(GathererStage state, Planet currentPlanet, Planet earth, ImGraph g) {
        List<Planet> pathToEarth = g.shortestPath(currentPlanet, earth);

        dijkstra (earth, g, state);

        for (int i = 1; i < pathToEarth.size(); i++) {
            state.moveTo(pathToEarth.get(i));
        }

        return;
    }

    private Map<Planet,Integer> dijkstra (Planet source, ImGraph g, GathererStage state) {
        List<Planet> visited = new ArrayList<>();

        Map<Planet,Integer> distFromSource = new HashMap<>();

        for (Planet planet: state.planets()) {
            //Initialize the distFromSource map to be all the vertices in planetMap as keys and set each of their values to Integer.MAX_VALUE
            distFromSource.put(planet,Integer.MAX_VALUE);

        }

        //Give the source vertex a distance value of 0 as it is 0 away from itself
        distFromSource.replace(source, 0);

        for (int i = 0; i < state.planets().size(); i++) {
            //Get the minVertex by calling the shortestDist method
            Planet minVertex = shortestDist(distFromSource, visited, source, state);
            //Add minVertex to the list of visited vertices
            visited.add(minVertex);
            //Call the updateDistance method to then check the next minVertex
            updateDistance(minVertex, visited, distFromSource, state, g);
        }

        return distFromSource;
    }

    /**
     * Finds the vertex which is the shortest distance to the next vertex.
     *
     * @param distFromSource a hashmap of vertices with respective distances from source
     * @param visited a list of all the visited vertices
     * @param source the start vertex
     * @return the vertex which gives the shortest distance to the next vertex
     */
    private Planet shortestDist(Map<Planet,Integer> distFromSource, List<Planet> visited, Planet source, GathererStage state) {
        int minDist = Integer.MAX_VALUE;
        Planet minVertex = source;

        for (Planet planet : state.planets()) {
            //If the vertex given in planetMap has not been visited and the distance from the source to that vertex is less than minDist
            if (!visited.contains(planet) && distFromSource.get(planet) <= minDist) {
                minDist = distFromSource.get(planet);
                minVertex = planet;
            }
        }

        return minVertex;
    }

    /**
     * Update the distance from the source and the path from the source maps.
     *
     * @param closePlanet the closest planet to the current vertex
     * @param visited a list of all the visited vertices
     * @param distFromSource a hashmap of vertices with respective distances from source
     */
    private void updateDistance (Planet closePlanet, List<Planet> visited, Map<Planet,Integer> distFromSource, GathererStage state, ImGraph g) {
        Set<Planet> neighborSet = g.search(closePlanet, 1);

        // iterate over the adjacent planets
        for (Planet neighbor : neighborSet) {
            if (!visited.contains(neighbor)) {
                Link path = (Link) g.getEdge(closePlanet, neighbor);
                //Define distance as the sum of the distance of closePlanet to the source and the length of the edge
                int fuel = distFromSource.get(closePlanet) + path.fuelNeeded();

                //Replace the key-value pair in both maps if this new distance is less than distance from the source to neighbor
                if (fuel < distFromSource.get(neighbor)) {
                    distFromSource.replace(neighbor, fuel);
                }
            }
        }
    }

}
