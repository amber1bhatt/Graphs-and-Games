package cpen221.mp2;

import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.graph.Vertex;
import cpen221.mp2.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class EdgeTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullException() {
        Vertex v1 = null;
        Vertex v2 = new Vertex(1,"A");
        Edge e1 = new Edge(v1,v2,5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEqualVertices() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(1,"A");
        Edge e1 = new Edge(v1,v2,5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeLength() {
        Vertex v1 = new Vertex(2,"B");
        Vertex v2 = new Vertex(1,"A");
        Edge e1 = new Edge(v1,v2,-2);
    }

    @Test
    public void testV1() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Edge e1 = new Edge(v1,v2,3);


        assertEquals(v1, e1.v1());
    }

    @Test
    public void testV2() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Edge e1 = new Edge(v1,v2,3);


        assertEquals(v2, e1.v2());
    }

    @Test
    public void testLength() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Edge e1 = new Edge(v1,v2,3);

        assertEquals(3,e1.length());
    }

    @Test
    public void testEquals() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Vertex v3 = new Vertex(1,"C");
        Vertex v4 = new Vertex(1,"A");
        Edge e1 = new Edge(v1,v2,3);
        Edge e2 = new Edge(v1,v3,6);
        Edge e3 = new Edge(v1,v2,3);
        Edge e4 = new Edge(v2,v1,3);

        assertTrue(!e1.equals(e2));
        assertTrue(e1.equals(e3));
        assertTrue(e1.equals(e4));
    }

    @Test
    public void testHashCode() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Edge e1 = new Edge(v2,v1,3);

        assertEquals(134, e1.hashCode());
    }

    @Test
    public void testIncident() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Vertex v3 = new Vertex(3,"C");
        Vertex v4 = null;
        Edge e1 = new Edge(v1,v2);
        assertTrue(e1.incident(v2));
        assertTrue(!e1.incident(v3));
        assertFalse(e1.incident(v4));

    }

    @Test
    public void testIntersects() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Vertex v3 = new Vertex(3,"C");
        Vertex v4 = null;
        Edge<Vertex> e = new Edge<Vertex>(v1,v2);
        assertTrue(e.intersects(e));
        assertFalse(e.incident(v4));
    }

    @Test
    public void testIntersectsException() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Vertex v3 = new Vertex(3,"C");

        Edge e2 = new Edge(v3,v2,10);
        Edge<Vertex> e = null;

        assertFalse(e2.intersects(e));
    }

    @Test(expected =  NoSuchElementException.class)
    public void testIntersectionExceptionNull() throws NoSuchElementException {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Vertex v3 = new Vertex(3,"C");
        Vertex v4 = new Vertex(4,"D");
        Edge e1 = null;
        Edge e2 = new Edge(v3,v4,10);

        assertEquals(v2, e2.intersection(e1));
    }

    @Test(expected =  NoSuchElementException.class)
    public void testIntersectionException() throws NoSuchElementException {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Vertex v3 = new Vertex(3,"C");
        Vertex v4 = new Vertex(4,"D");
        Edge e2 = new Edge(v3,v4,10);
        Edge e3 = new Edge(v1,v2,10);

        assertEquals(v2, e3.intersection(e2));
    }

    @Test
    public void testIntersection() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Vertex v3 = new Vertex(3,"C");
        Edge e1 = new Edge(v1,v2,3);
        Edge e3 = new Edge(v3,v1,5);

        assertEquals(v1,e1.intersection(e3));
    }

    @Test
    public void testDistinctVertex() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Vertex v3 = new Vertex(3,"C");
        Edge e1 = new Edge(v1,v2,10);
        Edge e2 = new Edge(v1,v3,10);

        assertEquals(v2,e1.distinctVertex(e2));
    }

    @Test(expected = NoSuchElementException.class)
    public void testDistinctVertexList() throws NoSuchElementException {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Edge e1 = new Edge(v1,v2,1);
        Edge<Vertex> e = new Edge<Vertex>(v1,v2);

        assertEquals(v2, e1.distinctVertex(e));
    }

    @Test
    public void testNoDistinctVertex() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(2,"B");
        Vertex v3 = new Vertex(3,"C");
        Vertex v4 = new Vertex(4,"C");

        Edge e1 = new Edge(v1,v2,10);
        Edge e2 = new Edge(v4,v3,10);

        assertEquals(v1,e1.distinctVertex(e2));
    }

}
