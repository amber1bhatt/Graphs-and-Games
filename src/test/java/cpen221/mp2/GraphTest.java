package cpen221.mp2;

import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.graph.Vertex;
import cpen221.mp2.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class GraphTest {

    @Test
    public void testCreateGraph() {
    }


    @Test
    public void testCheckVertexFalse() {
        Vertex v1 = new Vertex(1, "A");

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        assertTrue(!g.vertex(v1));
    }

    @Test
    public void testAddVertex() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(-1, "Z");
        Vertex v3 = new Vertex(4, "C");
        Vertex v4 = new Vertex(4, "F");

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);

        //failure for adding pre-existing vertex
        assertTrue(!g.addVertex(v1));

        assertTrue(g.addVertex(v3));

        //failure for adding vertex with same id
        assertTrue(!g.addVertex(v4));
    }

    @Test
    public void testCheckEdgeFalse() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(-1, "Z");

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        Edge e1 = new Edge<>(v1,v2,4);

        g.addVertex(v1);
        g.addVertex(v2);

        assertTrue(!g.edge(e1));

    }

    @Test
    public void testAddEdge() {

        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Edge e1 = new Edge<>(v1,v2,5);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        // failure for adding pre-existing edge
        assertTrue(!g.addEdge(new Edge<>(v2,v1,7)));

        assertTrue(g.edge(e1));

    }

    @Test
    public void testEdgeLength() {

        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Edge e1 = new Edge<>(v1,v2,5);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertEquals(5, g.edgeLength(v1,v2));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testEdgeLengthException() throws IllegalArgumentException {

        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Edge e1 = new Edge<>(v1,v2,5);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);

        assertEquals(5, g.edgeLength(v1,v2));
    }

    @Test
    public void testEdgeLengthSum() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Vertex v3 = new Vertex(3,"C");
        Vertex v4 = new Vertex(4,"D");

        Edge e1 = new Edge<>(v1,v2,5);
        Edge e2 = new Edge<>(v2,v3,12);
        Edge e3 = new Edge<>(v1,v4,25);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertEquals(42, g.edgeLengthSum());
    }

    @Test
    public void testRemoveEdge() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");

        Edge e1 = new Edge<>(v1,v2,5);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertTrue(g.remove(e1));

    }

    @Test
    public void testRemoveVertex() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");

        Edge e1 = new Edge<>(v1,v2,5);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertTrue(g.remove(v1));
    }

    @Test
    public void testRemoveVertexFail() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");

        Edge e1 = new Edge<>(v1,v2,5);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v2);
        g.addEdge(e1);

        assertTrue(!g.remove(v1));
    }

    @Test
    public void testRemoveEdgeFail() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");

        Edge e1 = new Edge<>(v1,v2,5);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);

        assertTrue(!g.remove(e1));
    }

    @Test
    public void testAllVertices() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);

        Set<Vertex> vertexSet = new HashSet<>();

        vertexSet.add(v1);
        vertexSet.add(v2);

        assertEquals(vertexSet, g.allVertices());
    }

    @Test
    public void testAllEdgesVertices() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Vertex v3 = new Vertex(3,"C");
        Vertex v4 = new Vertex(4,"D");

        Edge e1 = new Edge<>(v1,v2,5);
        Edge e2 = new Edge<>(v2,v3,12);
        Edge e3 = new Edge<>(v1,v4,25);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        Set<Edge> edgeSet = new HashSet<>();

        edgeSet.add(e1);
        edgeSet.add(e3);

        assertEquals(edgeSet, g.allEdges(v1));
    }

    @Test
    public void testAllEdges() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Vertex v3 = new Vertex(3,"C");
        Vertex v4 = new Vertex(4,"D");

        Edge e1 = new Edge<>(v1,v2,5);
        Edge e2 = new Edge<>(v2,v3,12);
        Edge e3 = new Edge<>(v1,v4,25);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        Set<Edge> edgeSet = new HashSet<>();

        edgeSet.add(e1);
        edgeSet.add(e2);
        edgeSet.add(e3);

        assertEquals(edgeSet, g.allEdges());
    }

    @Test
    public void testGetNeighbours() {

        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Vertex v3 = new Vertex(3,"C");
        Vertex v4 = new Vertex(4,"D");

        Edge e1 = new Edge<>(v1,v2,5);
        Edge e2 = new Edge<>(v2,v3,12);
        Edge e3 = new Edge<>(v1,v4,25);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        Map<Vertex, Edge> neigbhourMap = new HashMap<>();

        neigbhourMap.put(v2,e1);
        neigbhourMap.put(v4,e3);

        assertEquals(neigbhourMap, g.getNeighbours(v1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNeighboursExcpetion() throws IllegalArgumentException {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Vertex v3 = new Vertex(3,"C");
        Vertex v4 = new Vertex(4,"D");

        Edge e1 = new Edge<>(v1,v2,5);
        Edge e2 = new Edge<>(v2,v3,12);
        Edge e3 = new Edge<>(v1,v4,25);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        Map<Vertex, Edge> neigbhourMap = new HashMap<>();

        neigbhourMap.put(v2,e1);
        neigbhourMap.put(v4,e3);

        assertEquals(neigbhourMap, g.getNeighbours(v2));
    }

    @Test
    public void testMinimumSpanningTree() {
        Vertex v0 = new Vertex(0,"0");
        Vertex v1 = new Vertex(1,"1");
        Vertex v2 = new Vertex(2,"2");
        Vertex v3 = new Vertex(3,"3");
        Vertex v4 = new Vertex(4,"4");
        Vertex v5 = new Vertex(5,"5");
        Vertex v6 = new Vertex(6,"6");
        Vertex v7 = new Vertex(7,"7");
        Vertex v8 = new Vertex(8,"8");

        Edge e1 = new Edge<>(v7,v6,1);
        Edge e2 = new Edge<>(v8,v2,2);
        Edge e3 = new Edge<>(v6,v5,2);
        Edge e4 = new Edge<>(v0,v1,4);
        Edge e5 = new Edge<>(v2,v5,4);
        Edge e6 = new Edge<>(v8,v6,6);
        Edge e7 = new Edge<>(v2,v3,7);
        Edge e8 = new Edge<>(v7,v8,7);
        Edge e9 = new Edge<>(v0,v7,8);
        Edge e10 = new Edge<>(v1,v2,8);
        Edge e11 = new Edge<>(v3,v4,9);
        Edge e12 = new Edge<>(v5,v4,10);
        Edge e13 = new Edge<>(v1,v7,11);
        Edge e14 = new Edge<>(v3,v5,14);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addVertex(v7);
        g.addVertex(v8);
        g.addVertex(v0);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);
        g.addEdge(e7);
        g.addEdge(e8);
        g.addEdge(e9);
        g.addEdge(e10);
        g.addEdge(e11);
        g.addEdge(e12);
        g.addEdge(e13);
        g.addEdge(e14);

        List<Edge> edgeList = new ArrayList<>();

        edgeList.add(e1);
        edgeList.add(e2);
        edgeList.add(e3);
        edgeList.add(e4);
        edgeList.add(e5);
        edgeList.add(e7);
        edgeList.add(e10);
        edgeList.add(e11);


        assertEquals(edgeList, g.minimumSpanningTree());
    }

    @Test
    public void testPathLength() {
        Vertex v1 = new Vertex(1,"1");
        Vertex v2 = new Vertex(2,"2");
        Vertex v3 = new Vertex(3,"3");
        Vertex v4 = new Vertex(4,"4");
        Vertex v5 = new Vertex(5,"5");

        Edge e1 = new Edge<>(v1,v2,5);
        Edge e2 = new Edge<>(v2,v3,12);
        Edge e3 = new Edge<>(v3,v4,25);
        Edge e4 = new Edge<>(v4,v5,24);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);

        List<Vertex> path = new ArrayList<>();

        path.add(v1);
        path.add(v2);
        path.add(v3);
        path.add(v4);

        assertEquals(42, g.pathLength(path));
    }

    @Test
    public void testSearch() {
        Vertex v0 = new Vertex(0,"0");
        Vertex v1 = new Vertex(1,"1");
        Vertex v2 = new Vertex(2,"2");
        Vertex v3 = new Vertex(3,"3");
        Vertex v4 = new Vertex(4,"4");
        Vertex v5 = new Vertex(5,"5");
        Vertex v6 = new Vertex(6,"6");
        Vertex v7 = new Vertex(7,"7");
        Vertex v8 = new Vertex(8,"8");

        Edge e1 = new Edge<>(v7,v6,1);
        Edge e2 = new Edge<>(v8,v2,2);
        Edge e3 = new Edge<>(v6,v5,2);
        Edge e4 = new Edge<>(v0,v1,4);
        Edge e5 = new Edge<>(v2,v5,4);
        Edge e6 = new Edge<>(v8,v6,6);
        Edge e7 = new Edge<>(v2,v3,7);
        Edge e8 = new Edge<>(v7,v8,7);
        Edge e9 = new Edge<>(v0,v7,8);
        Edge e10 = new Edge<>(v1,v2,8);
        Edge e11 = new Edge<>(v3,v4,9);
        Edge e12 = new Edge<>(v5,v4,10);
        Edge e13 = new Edge<>(v1,v7,11);
        Edge e14 = new Edge<>(v3,v5,14);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addVertex(v7);
        g.addVertex(v8);
        g.addVertex(v0);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);
        g.addEdge(e7);
        g.addEdge(e8);
        g.addEdge(e9);
        g.addEdge(e10);
        g.addEdge(e11);
        g.addEdge(e12);
        g.addEdge(e13);
        g.addEdge(e14);

        Set<Vertex> vertexSet = new HashSet<>();

        vertexSet.add(v8);
        vertexSet.add(v1);
        vertexSet.add(v2);
        vertexSet.add(v6);
        vertexSet.add(v7);

        assertEquals(vertexSet, g.search(v0, 2));
    }

    @Test
    public void testShortestPath() {
        Vertex v0 = new Vertex(0,"0");
        Vertex v1 = new Vertex(1,"1");
        Vertex v2 = new Vertex(2,"2");
        Vertex v3 = new Vertex(3,"3");
        Vertex v4 = new Vertex(4,"4");
        Vertex v5 = new Vertex(5,"5");
        Vertex v6 = new Vertex(6,"6");
        Vertex v7 = new Vertex(7,"7");
        Vertex v8 = new Vertex(8,"8");

        Edge e1 = new Edge<>(v7,v6,1);
        Edge e2 = new Edge<>(v8,v2,3);
        Edge e3 = new Edge<>(v6,v5,2);
        Edge e4 = new Edge<>(v0,v1,4);
        Edge e5 = new Edge<>(v2,v5,4);
        Edge e6 = new Edge<>(v8,v6,6);
        Edge e7 = new Edge<>(v2,v3,7);
        Edge e8 = new Edge<>(v7,v8,7);
        Edge e9 = new Edge<>(v0,v7,8);
        Edge e10 = new Edge<>(v1,v2,8);
        Edge e11 = new Edge<>(v3,v4,9);
        Edge e12 = new Edge<>(v5,v4,10);
        Edge e13 = new Edge<>(v1,v7,11);
        Edge e14 = new Edge<>(v3,v5,14);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addVertex(v7);
        g.addVertex(v8);
        g.addVertex(v0);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);
        g.addEdge(e7);
        g.addEdge(e8);
        g.addEdge(e9);
        g.addEdge(e10);
        g.addEdge(e11);
        g.addEdge(e12);
        g.addEdge(e13);
        g.addEdge(e14);

        List<Vertex> vertexList = new ArrayList<>();

        vertexList.add(v0);
        vertexList.add(v7);
        vertexList.add(v8);

        List<Vertex> vertexListR = g.shortestPath(v0, v8);

        assertEquals(vertexList, g.shortestPath(v0, v8));
    }

    @Test
    public void testShortestPathDisconnected() {
        Vertex v0 = new Vertex(0,"0");
        Vertex v1 = new Vertex(1,"1");
        Vertex v2 = new Vertex(2,"2");
        Vertex v3 = new Vertex(3,"3");
        Vertex v4 = new Vertex(4,"4");
        Vertex v5 = new Vertex(5,"5");
        Vertex v6 = new Vertex(6,"6");
        Vertex v7 = new Vertex(7,"7");
        Vertex v8 = new Vertex(8,"8");

        Edge e1 = new Edge<>(v7,v6,1);
        Edge e2 = new Edge<>(v8,v2,2);
        Edge e3 = new Edge<>(v6,v5,2);
        Edge e4 = new Edge<>(v0,v1,4);
        Edge e5 = new Edge<>(v2,v5,4);
        Edge e6 = new Edge<>(v8,v6,6);
        Edge e7 = new Edge<>(v2,v3,7);
        Edge e8 = new Edge<>(v7,v8,7);
        Edge e9 = new Edge<>(v0,v7,8);
        Edge e10 = new Edge<>(v1,v2,8);

        Edge e13 = new Edge<>(v1,v7,11);
        Edge e14 = new Edge<>(v3,v5,14);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addVertex(v7);
        g.addVertex(v8);
        g.addVertex(v0);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);
        g.addEdge(e7);
        g.addEdge(e8);
        g.addEdge(e9);
        g.addEdge(e10);
        g.addEdge(e13);
        g.addEdge(e14);

        List<Vertex> vertexList = new ArrayList<>();

        assertEquals(vertexList, g.shortestPath(v0, v4));
    }

    @Test
    public void testDiameter() {
        Vertex v0 = new Vertex(0,"0");
        Vertex v1 = new Vertex(1,"1");
        Vertex v2 = new Vertex(2,"2");
        Vertex v3 = new Vertex(3,"3");
        Vertex v4 = new Vertex(4,"4");
        Vertex v5 = new Vertex(5,"5");
        Vertex v6 = new Vertex(6,"6");
        Vertex v7 = new Vertex(7,"7");
        Vertex v8 = new Vertex(8,"8");

        Edge e1 = new Edge<>(v7,v6,1);
        Edge e2 = new Edge<>(v8,v2,3);
        Edge e3 = new Edge<>(v6,v5,2);
        Edge e4 = new Edge<>(v0,v1,4);
        Edge e5 = new Edge<>(v2,v5,4);
        Edge e6 = new Edge<>(v8,v6,6);
        Edge e7 = new Edge<>(v2,v3,7);
        Edge e8 = new Edge<>(v7,v8,7);
        Edge e9 = new Edge<>(v0,v7,8);
        Edge e10 = new Edge<>(v1,v2,8);
        Edge e11 = new Edge<>(v3,v4,9);
        Edge e12 = new Edge<>(v5,v4,10);
        Edge e13 = new Edge<>(v1,v7,11);
        Edge e14 = new Edge<>(v3,v5,14);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addVertex(v7);
        g.addVertex(v8);
        g.addVertex(v0);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);
        g.addEdge(e7);
        g.addEdge(e8);
        g.addEdge(e9);
        g.addEdge(e10);
        g.addEdge(e11);
        g.addEdge(e12);
        g.addEdge(e13);
        g.addEdge(e14);

        assertEquals(22, g.diameter());
    }

    @Test
    public void testDiameterDisconnected() {
        Vertex v0 = new Vertex(0,"0");
        Vertex v1 = new Vertex(1,"1");
        Vertex v2 = new Vertex(2,"2");
        Vertex v3 = new Vertex(3,"3");
        Vertex v4 = new Vertex(4,"4");
        Vertex v5 = new Vertex(5,"5");
        Vertex v6 = new Vertex(6,"6");
        Vertex v7 = new Vertex(7,"7");
        Vertex v8 = new Vertex(8,"8");

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addVertex(v7);
        g.addVertex(v8);
        g.addVertex(v0);

        Edge e11 = new Edge<>(v3,v4,9);

        g.addEdge(e11);

        assertEquals(9, g.diameter());
    }

    @Test
    public void testDiameterFullDisconnected() {
        Vertex v0 = new Vertex(0,"0");
        Vertex v1 = new Vertex(1,"1");
        Vertex v2 = new Vertex(2,"2");
        Vertex v3 = new Vertex(3,"3");
        Vertex v4 = new Vertex(4,"4");
        Vertex v5 = new Vertex(5,"5");
        Vertex v6 = new Vertex(6,"6");
        Vertex v7 = new Vertex(7,"7");
        Vertex v8 = new Vertex(8,"8");

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addVertex(v7);
        g.addVertex(v8);
        g.addVertex(v0);

        assertEquals(Integer.MAX_VALUE, g.diameter());
    }

    @Test
    public void testGetEdge() {
        Vertex v1 = new Vertex(1,"1");
        Vertex v2 = new Vertex(2,"2");
        Vertex v4 = new Vertex(4,"4");

        Edge e1 = new Edge(v1,v2,6);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);

        g.addVertex(v4);

        g.addEdge(e1);

        assertEquals(e1, g.getEdge(v1, v2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEdgeException() throws IllegalArgumentException {
        Vertex v1 = new Vertex(1,"1");
        Vertex v2 = new Vertex(2,"2");
        Vertex v3 = new Vertex(3,"3");
        Vertex v4 = new Vertex(4,"4");

        Edge e1 = new Edge(v1,v2,6);
        Edge e2 = new Edge(v3,v4,7);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        g.addEdge(e1);

        assertEquals(e2, g.getEdge(v3,v4));
    }

}