package cpen221.mp2;

import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.graph.Vertex;
import cpen221.mp2.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class VertexTest {

    @Test
    public void testEquals() {
        Vertex v1 = new Vertex(1,"A");
        Vertex v2 = new Vertex(1,"A");
        Vertex v3 = new Vertex(2,"A");

        assertTrue(v1.equals(v2));
        assertTrue(!v1.equals(v3));
    }

    @Test
    public void testHashCode() {
        Vertex v1 = new Vertex(1,"A");
        assertEquals(67,v1.hashCode()+v1.id());
    }

    @Test
    public void testID() {
        Vertex v1 = new Vertex(1,"A");
        assertEquals(1,v1.id());
    }

    @Test
    public void testName() {
        Vertex v1 = new Vertex(1,"A");
        assertEquals("A",v1.name());
    }

    @Test
    public void testUpdateName() {
        Vertex v1 = new Vertex(1,"A");
        v1.updateName("B");
        assertEquals("B",v1.name());
    }

}
