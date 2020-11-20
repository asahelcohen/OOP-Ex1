package ex1.src;
/**
 * This class implements interface weighted_graph_algorithms, representing Graph Theory algorithms including:
 * 1) Create a deep copy of a graph, init.
 * 2) Check if all nodes of graph are connected.
 * 3) Find the shortest distance between 2 nodes.
 * 4) return a list of the nodes in the shortest path.
 * 5) reset all tags of graph to chosen Int.
 * 6) save, load, and equals
 */
import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph wg;

    public WGraph_Algo() {
        this.wg = new WGraph_DS();
    }

    /**
     * the graph on which this functions operate on.
     * @param g weighted Graph
     */
    @Override
    public void init(weighted_graph g) {
        this.wg = g;
    }

    /**
     * @return Return the underlying graph of which this class works.
     */
    @Override
    public weighted_graph getGraph() {
        return wg;
    }

    /**
     * converts weighted graph to WGraph_DS so all function will work.
     * @return WGraph_DS
     */
    @Override
    public weighted_graph copy() {
        weighted_graph g1 = new WGraph_DS(wg);

        return g1;
    }

    /**
     * This function checks if all nodes of the graph are connected.
     * The function picks a random node, with an Iterator it goes through all the
     * neighbors. And then does the same with all neighbors(as nodes) and so on.
     * Every time a node tagged 0 is met it is changed to 1 and our counter increases by one.
     * Finally check if counter is equal to the amount of vertices, if it is- the graph is connected.
     * @return True if vertices are connected, false if not.
     */
    @Override
    public boolean isConnected() {
        if (wg.nodeSize() == 0 || wg.nodeSize() == 1)
            return true;

        if ((wg.nodeSize() - 1) > wg.edgeSize())
            return false;

        resetTag(wg, 0.0);
        int counter = 1;

        Queue<node_info> checklist = new LinkedList<>();

        Collection<node_info> points = wg.getV();
        Iterator<node_info> st = points.iterator();
        node_info head = st.next();
        head.setTag(1);
        checklist.add(head);

        while (!checklist.isEmpty()) {
            node_info currentNode = checklist.poll();

            Collection<node_info> nei = wg.getV(currentNode.getKey());
            Iterator<node_info> it = nei.iterator();
            while (it.hasNext()) {
                node_info temp = it.next();
                if (temp.getTag() == 0) {
                    checklist.add(temp);
                    temp.setTag(1);
                    counter++;
                }
            }
        }
        if (counter == wg.nodeSize())
            return true;

        return false;
    }

    /**
     * This function returns the length of the shortest path between src and dest.
     * At first it sets all nodes tags to Max Double value And by starting from src neighbors with an Iterator
     * visits all neighbors and tags with distance of Edge. Then repeats on neighbors tagging with their tag + the edge...
     * When reaches dest node return dest tag. every time a node is reached with a lower tag we mark in its info the node
     * which he came from.
     * note1: if dest isn't found return -1.
     * note2: if a node is reached with a tag higher then existing one, it wont continue.
     * @param src - start node
     * @param dest - end (target) node
     * @return double representing the distance between src and dest.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (wg.getNode(src) == null || wg.getNode(dest) == null)
            return (-1);

        if (src == dest)
            return (0);

        resetTag(wg, Double.MAX_VALUE);
        Queue<node_info> checklist = new LinkedList<>();

        node_info start = wg.getNode(src);
        start.setTag(0);
        checklist.add(start);

        while (!checklist.isEmpty()) {
            node_info node = checklist.poll();
            Collection<node_info> nei = wg.getV(node.getKey());
            Iterator<node_info> it = nei.iterator();
            while (it.hasNext()) {
                node_info temp = it.next();
                Double EdgeL = wg.getEdge(temp.getKey(), node.getKey());

                if ((node.getTag() + EdgeL) < temp.getTag()) {
                    temp.setTag(node.getTag() + EdgeL);
                    temp.setInfo(String.valueOf(node.getKey()));
                    checklist.add(temp);
                }
            }
        }
        if (wg.getNode(dest).getTag() == Double.MAX_VALUE)
            return -1;
        return (wg.getNode(dest).getTag());
    }

    /**
     * This function returns LinkedList of the shortest path between src and dest.
     * It sends the nodes to: "shortestPathDist". Then creates a Linked list, and starts from dest
     * everytime adding the node connected to its info and so on until src.
     * @param src - start node
     * @param dest - end (target) node
     * @return LinkedList of nodes representing shortest path.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        LinkedList<node_info> path = new LinkedList<>();

        Double distance = shortestPathDist(src, dest);
        if (distance == -1)
            return path;
        if (distance == 0)
            return path;
        node_info destNode = wg.getNode(dest);
        path.add(destNode);
        if (distance == 1) {
            path.add(destNode);
            path.addFirst(wg.getNode(src));
            return path;
        }

        Collection<node_info> nei = wg.getV(dest);
        Iterator<node_info> it = nei.iterator();
        while (it.hasNext()) {
            node_info temp = it.next();
            if (Integer.parseInt(wg.getNode(dest).getInfo()) == (temp.getKey())) {
                if (temp.getTag() == 0) break;
                path.addFirst(temp);
                Collection<node_info> nei2 = wg.getV(temp.getKey());
                it = nei2.iterator();
                dest = temp.getKey();
            }
        }
        path.addFirst(wg.getNode(src));
        return path;
    }

    @Override
    public boolean save(String file) {
        System.out.println("Starting Serialize to " + file + "\n");
        try {
            FileOutputStream file1 = new FileOutputStream(file);
            ObjectOutputStream outS = new ObjectOutputStream(file1);
            outS.writeObject(this.wg);
            outS.close();
            file1.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("End of Serialization \n\n");
        return true;
    }

    @Override
    public boolean load(String file) {
        System.out.println("Deserialize from: " + file);

        try {
            FileInputStream file1 = new FileInputStream(file);
            ObjectInputStream inS = new ObjectInputStream(file1);

            wg = (weighted_graph) inS.readObject();
            file1.close();
            inS.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * This function resets the tag of all nodes in graph to requested Num.
     * @param g Graph.
     * @param tag double representing Tag number.
     */
    public void resetTag(weighted_graph g, Double tag) {
        Collection<node_info> points = g.getV();
        Iterator<node_info> st = points.iterator();

        while (st.hasNext()) {
            node_info temp = st.next();
            temp.setTag(tag);
        }
    }
}
