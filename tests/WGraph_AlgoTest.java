package ex1.tests;
import ex1.src.*;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    @Test
    void init() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.addNode(9);
        g.connect(0, 1, 0.5);
        g.connect(1, 2, 1.7);
        g.connect(2, 3, 3.6);
        g.connect(3, 4, 9.8);
        g.connect(4, 5, 4.5);
        g.connect(5, 6, 1.4);
        g.connect(6, 7, 8.3);
        g.connect(7, 8, 2.3);
        g.connect(8, 0, 33.2);
        g.connect(1, 4, 23.7);
        g.connect(7, 9, 6.4);
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        assertEquals(true, g1.isConnected());
        assertEquals(36.2, g1.shortestPathDist(0, 9));
        assertEquals(11, g1.getGraph().edgeSize());
        assertEquals(10, g1.getGraph().nodeSize());
    }

    @Test
    void getGraph() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.addNode(9);
        g.connect(0, 1, 0.5);
        g.connect(1, 2, 1.7);
        g.connect(2, 3, 3.6);
        g.connect(3, 4, 9.8);
        g.connect(4, 5, 4.5);
        g.connect(5, 6, 1.4);
        g.connect(6, 7, 8.3);
        g.connect(7, 8, 2.3);
        g.connect(8, 0, 33.2);
        g.connect(1, 4, 23.7);
        g.connect(7, 9, 6.4);
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        assertEquals(g, g1.getGraph());
        g.connect(5, 8, 6.4);
        assertEquals(g, g1.getGraph());
    }

    @Test
    void copy() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.addNode(9);
        g.connect(0, 1, 0.5);
        g.connect(1, 2, 1.7);
        g.connect(2, 3, 3.6);
        g.connect(3, 4, 9.8);
        g.connect(4, 5, 4.5);
        g.connect(5, 6, 1.4);
        g.connect(6, 7, 8.3);
        g.connect(7, 8, 2.3);
        g.connect(8, 0, 33.2);
        g.connect(1, 4, 23.7);
        g.connect(7, 9, 6.4);
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        weighted_graph g2 = g1.copy();
        assertEquals(g.getMC(), g2.getMC());
        assertEquals(g.getEdge(7, 8), g2.getEdge(7, 8));
        assertEquals(g.edgeSize(), g2.edgeSize());
        assertEquals(g.nodeSize(), g2.nodeSize());
        g.removeNode(9);
        assertNotEquals(g.getMC(), g2.getMC());
        assertNotEquals(g.getEdge(7, 9), g2.getEdge(7, 9));
        assertNotEquals(g.edgeSize(), g2.edgeSize());
        assertNotEquals(g.nodeSize(), g2.nodeSize());
    }

    @Test
    void isConnected() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.addNode(9);
        g.connect(0, 1, 0.5);
        g.connect(1, 2, 1.7);
        g.connect(2, 3, 3.6);
        g.connect(3, 4, 9.8);
        g.connect(4, 5, 4.5);
        g.connect(5, 6, 1.4);
        g.connect(6, 7, 8.3);
        g.connect(7, 8, 2.3);
        g.connect(8, 0, 33.2);
        g.connect(1, 4, 23.7);
        g.connect(7, 9, 6.4);
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        assertEquals(true, g1.isConnected());
        g.removeEdge(1, 4);
        g.removeEdge(7, 8);
        g1.init(g);
        assertEquals(true, g1.isConnected());
        g.removeEdge(7, 9);
        g1.init(g);
        assertEquals(false, g1.isConnected());
        g.removeNode(9);
        g1.init(g);
        assertEquals(true, g1.isConnected());


        weighted_graph g2 = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.addNode(9);
        g.connect(0, 1, 0.5);
        g.connect(0, 2, 1.7);
        g.connect(0, 3, 3.6);
        g.connect(0, 4, 9.8);
        g.connect(0, 5, 4.5);
        g.connect(0, 6, 1.4);
        g.connect(0, 7, 8.3);
        g.connect(0, 8, 2.3);
        g.connect(8, 9, 5.1);
        g1.init(g);
        assertEquals(true, g1.isConnected());
        g.removeEdge(8, 9);
        g1.init(g);
        assertEquals(false, g1.isConnected());
        g.removeNode(9);
        g1.init(g);
        assertEquals(true, g1.isConnected());

        weighted_graph g3 = new WGraph_DS();
        g3.addNode(0);
        g1.init(g3);
        assertEquals(true, g1.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.addNode(9);
        g.connect(0, 1, 0.5);
        g.connect(1, 2, 1.7);
        g.connect(2, 3, 3.6);
        g.connect(3, 4, 9.8);
        g.connect(4, 5, 4.5);
        g.connect(5, 6, 1.4);
        g.connect(6, 7, 8.3);
        g.connect(7, 8, 2.3);
        g.connect(8, 0, 33.2);
        g.connect(1, 4, 23.7);
        g.connect(7, 9, 6.4);
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        assertEquals(32.1, g1.shortestPathDist(0, 8));
        assertEquals(-1, g1.shortestPathDist(3, 10));
        assertEquals(-1, g1.shortestPathDist(13, 10));
        g.removeEdge(4, 5);
        g1.init(g);
        assertEquals(33.2, g1.shortestPathDist(0, 8));
        g.removeEdge(8, 0);
        g1.init(g);
        assertEquals(-1, g1.shortestPathDist(0, 8));
    }

    @Test
    void shortestPath() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.addNode(9);
        g.connect(0, 1, 0.5);
        g.connect(1, 2, 1.6);
        g.connect(2, 3, 3.6);
        g.connect(3, 4, 9.8);
        g.connect(4, 5, 4.5);
        g.connect(5, 6, 1.4);
        g.connect(6, 7, 8.3);
        g.connect(7, 8, 2.3);
        g.connect(8, 0, 33.2);
        g.connect(1, 4, 23.7);
        g.connect(7, 9, 6.4);
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        List<node_info> a = g1.shortestPath(0, 9);
        int[] checkKey = {0, 1, 2, 3, 4, 5, 6, 7, 9};
        double[] checkTag = {0, 0.5, 2.1, 5.7, 15.5, 20.0, 21.4, 29.7, 36.1};
        int i = 0;
        for (node_info n : a) {
            assertEquals(checkTag[i], n.getTag());
            assertEquals(checkKey[i], n.getKey());
            i++;
        }

        g.removeEdge(7, 9);
        g1.init(g);
        List<node_info> b = g1.shortestPath(0, 9);
        List<node_info> c = new LinkedList<>();
        assertEquals(c, b);
    }

    @Test
    void save() {
        weighted_graph g0 = new WGraph_DS();
        g0.addNode(0);
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.addNode(4);
        g0.connect(0, 1, 0.5);
        g0.connect(1, 2, 1.7);
        g0.connect(2, 3, 3.6);
        g0.connect(3, 4, 9.8);

        weighted_graph_algorithms g2 = new WGraph_Algo();
        g2.init(g0);

        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.connect(0, 1, 0.5);
        g.connect(1, 2, 1.7);
        g.connect(2, 3, 3.6);
        g.connect(3, 4, 9.8);

        String st = "g0.obj";
        g2.save(st);
        assertEquals(g, g2.getGraph());
        g.removeEdge(1, 2);
        assertNotEquals(g, g2.getGraph());
        g0.removeEdge(1, 2);
        g2.save(st);
        assertEquals(g, g2.getGraph());


    }

    @Test
    void load() {
        weighted_graph g0 = new WGraph_DS();
        g0.addNode(0);
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.addNode(4);
        g0.connect(0, 1, 0.5);
        g0.connect(1, 2, 1.7);
        g0.connect(2, 3, 3.6);
        g0.connect(3, 4, 9.8);

        weighted_graph_algorithms g2 = new WGraph_Algo();
        g2.init(g0);

        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.connect(0, 1, 0.5);
        g.connect(1, 2, 1.7);
        g.connect(2, 3, 3.6);
        g.connect(3, 4, 9.8);

        String st = "g0.obj";
        g2.save(st);
        assertEquals(g, g2.getGraph());
        g.removeEdge(1, 2);
        assertNotEquals(g, g2.getGraph());
    }
}