package ex1.tests;

import ex1.src.*;

import org.junit.jupiter.api.Test;
import java.util.Collection;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {

    @Test
    void getNode() {
        WGraph_DS g = new WGraph_DS();
        g.addNode(0);
        node_info n = g.getNode(1);
        assertEquals(null, n);
        n = g.getNode(0);
        assertEquals(0, n.getKey());
    }

    @Test
    void hasEdge() {
        WGraph_DS g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0, 1, 5.0);
        boolean a = g.hasEdge(0, 1);
        boolean b = g.hasEdge(1, 0);
        boolean c = g.hasEdge(1, 1);
        boolean d = g.hasEdge(0, 2);
        boolean e = g.hasEdge(5, 4);
        boolean f = g.hasEdge(1, 3);
        assertEquals(true, a);
        assertEquals(true, b);
        assertEquals(false, c);
        assertEquals(false, d);
        assertEquals(false, e);
        assertEquals(false, f);
    }

    @Test
    void getEdge() {
        WGraph_DS g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.connect(0, 1, 5.5);
        double a = g.getEdge(0, 1);
        double b = g.getEdge(1, 0);
        double c = g.getEdge(1, 1);
        double d = g.getEdge(4, 5);
        assertEquals(-1, c);
        assertEquals(-1, d);
        assertEquals(5.5, a);
        assertEquals(5.5, b);
    }

    @Test
    void addNode() {
        WGraph_DS g = new WGraph_DS();
        g.addNode(0);
        node_info n = g.getNode(0);
        assertEquals(0, n.getKey());
    }

    @Test
    void connect() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0, 1, 1);
        g.connect(0, 2, 2);
        g.connect(1, 1, 5);
        assertEquals(2, g.getEdge(0, 2));
        assertEquals(-1, g.getEdge(1, 1));
        g.removeEdge(0, 1);
        assertEquals(-1, g.getEdge(0, 1));
        g.connect(0, 1, 1);
        double t = g.getEdge(1, 0);
        assertEquals(1, t);
    }

    @Test
    void getV() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(0);
        Collection<node_info> n = new LinkedList<>();
        n.add(g.getNode(0));
        n.add(g.getNode(1));
        n.add(g.getNode(2));
        n.add(g.getNode(3));
        Collection<node_info> v = g.getV();
        assertEquals(n.size(), v.size());
    }

    @Test
    void testGetV() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.connect(0, 1, 0.5);
        g.connect(0, 2, 6.3);
        g.connect(0, 3, 8.7);
        g.connect(0, 4, 87.0);
        Collection<node_info> v = g.getV(0);
        assertEquals(4, v.size());
        g.removeNode(0);
        g.addNode(0);
        Collection<node_info> v1 = g.getV(0);
        assertEquals(0, v.size());
    }

    @Test
    void removeNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.connect(0, 1, 0.5);
        g.connect(3, 2, 6.3);
        g.connect(4, 3, 8.7);
        g.connect(1, 4, 87.0);
        node_info n = g.getNode(1);
        assertEquals(1, n.getKey());
        g.removeNode(1);
        assertEquals(null, g.getNode(1));
        assertEquals(0, g.getNode(0).getKey());
    }

    @Test
    void removeEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.connect(0, 1, 0.5);
        g.connect(3, 2, 6.3);
        g.connect(4, 3, 8.7);
        g.connect(1, 4, 87.0);
        assertEquals(0.5, g.getEdge(1, 0));
        g.removeEdge(1, 0);
        assertEquals(-1, g.getEdge(1, 0));
        assertEquals(87, g.getEdge(1, 4));
        g.removeNode(4);
        g.addNode(4);
        assertEquals(-1, g.getEdge(1, 4));
    }

    @Test
    void nodeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        assertEquals(5, g.nodeSize());
        g.removeNode(1);
        assertEquals(4, g.nodeSize());
        g.removeNode(1);
        assertEquals(4, g.nodeSize());
        g.addNode(3);
        assertEquals(4, g.nodeSize());
    }

    @Test
    void edgeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.connect(0, 1, 0.5);
        g.connect(3, 2, 6.3);
        g.connect(4, 3, 8.7);
        g.connect(1, 4, 87.0);
        assertEquals(4, g.edgeSize());
        g.removeEdge(1, 4);
        assertEquals(3, g.edgeSize());
        g.removeEdge(1, 4);
        assertEquals(3, g.edgeSize());
        g.connect(0, 1, 1);
        assertEquals(3, g.edgeSize());
    }

    @Test
    void getMC() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.connect(0, 1, 0.5);
        g.connect(3, 2, 6.3);
        g.connect(4, 3, 8.7);
        g.connect(1, 4, 87.0);
        g.removeEdge(0, 1);
        assertEquals(10, g.getMC());
        g.removeEdge(0, 1);
        g.addNode(0);
        assertEquals(10, g.getMC());
        g.removeNode(4);
        assertEquals(13, g.getMC());
    }
}
