package ex1.src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class implements the interface of a weighted graph, representing a undirectional graph:
 * It has getters, Setters, copy constructor, check for edges between
 * nodes, add and remove nodes(vertices) and edges. Return amount of vertices, edges,
 * and mode changes. Contain list of neighbors for each node. Contains list of all edges and their length.
 */
public class WGraph_DS implements weighted_graph, java.io.Serializable {

    private int edgeC = 0;
    private int vertC = 0;
    private int MC = 0;
    private HashMap<Integer, node_info> vert = new HashMap<>();
    private HashMap<String, Double> edges = new HashMap<>();

    public WGraph_DS(){
        this.edgeC = 0;
        this.vertC = 0;
        this.MC = 0;
        this.vert = new HashMap<>();
        this.edges = new HashMap<>();
    }

    /**
     * Deep copy constructor
     * @param g Graph
     */
    public WGraph_DS(weighted_graph g) {
        this.vert = new HashMap<>();
        this.edges = new HashMap<>();

        for (node_info n: g.getV()){
            NodeInfo temp = new NodeInfo(n.getKey());
            temp.Info = n.getInfo();
            temp.Tag = n.getTag();
            vert.put(temp.getKey(), temp);
        }
        for (node_info n: g.getV()){
            NodeInfo n1 = (NodeInfo) n;
            for (node_info ni: n1.getNi()){
                connect(n.getKey(), ni.getKey(), g.getEdge(n.getKey(), ni.getKey()));
            }
        }


        this.edgeC = g.edgeSize();
        this.MC = g.getMC();
        this.vertC = g.nodeSize();

    }

    /**
     * Function return the node_info of the given key
     * @param key -  the node_id
     * @return node_info of given key
     */
    @Override
    public node_info getNode(int key) {
        if (vert.containsKey(key))
            return (node_info) vert.get(key);

        return null;
    }

    /**
     * This function return true only if there is an edge between given node key's.
     * @param node1 (int key of node1)
     * @param node2 (int key of node2)
     * @return true if is edge false if no edge.
     */
    @Override
    public boolean hasEdge(int node1, int node2) {

        if (vert.containsKey(node1) && vert.containsKey(node2))
            return (((NodeInfo) (vert.get(node1))).hasNi(node2) && ((NodeInfo) (vert.get(node2))).hasNi(node1));
        return false;
    }

    /**
     * This function returns the length of the edge between 2 given node keys.
     * @param node1 Int representing ID of node_info.
     * @param node2 Int representing ID of node_info.
     * @return Double representing the length of the Edge.
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            String SrcDest = String.valueOf(node1) + "-" + String.valueOf(node2);
            String DestSrc = String.valueOf(node2) + "-" + String.valueOf(node1);
            if (edges.containsKey(SrcDest))
                return (edges.get(SrcDest));
            return edges.get(DestSrc);
        }
        return -1;
    }

    /**
     * This function adds a new node with a unique key to graph.
     * @param key representing ID of node info
     */
    @Override
    public void addNode(int key) {
        if (vert.containsKey(key)) return;
        node_info temp = new NodeInfo(key);
        vert.put(key, temp);
        MC++;
        vertC++;
    }

    /**
     * This function adds an "edge" between two given nodes.
     * Adds each node to the others neighbor list, and gives the edge a length.
     * Increases the edge and the mode count amount by one.
     * Only add "edge" if graph contains both nodes, if both node are not
     * the same node, and if there is not an "edge" between them already.
     * note: if there is an edge already but distance is different change distance.
     * @param node1 Int representing ID of node_data.
     * @param node2 Int representing ID of node_data.
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (vert.containsKey(node1) && vert.containsKey(node2)) {
            if (node1 == node2) return;
            if (hasEdge(node1, node2) && getEdge(node1, node2) == w) return;
            String SrcDest = String.valueOf((node1) + "-" + String.valueOf(node2));
            String DestSrc = String.valueOf((node2) + "-" + String.valueOf(node1));
            if (hasEdge(node1, node2) && getEdge(node1, node2) != w){
                if(edges.containsKey(SrcDest))
                    edges.put(SrcDest, w);
                else
                    edges.put(DestSrc, w);
            }
            else {
                ((NodeInfo) (vert.get(node1))).addNi(vert.get(node2));
                ((NodeInfo) (vert.get(node2))).addNi(vert.get(node1));
                edges.put(SrcDest, w);
                edgeC++;
            }
            MC++;
        }

    }

    /**
     * This function returns a list of all vertices in graph.
     * @return Collection of the graph's node info.
     */
    @Override
    public Collection<node_info> getV() {
        return vert.values();
    }

    /**
     * The function return's a list of neighbors of given ID
     * @param node_id int representing the ID of node.
     * @return collection of node_info representing given nodes neighbors.
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        Collection<node_info> empty = new LinkedList<>();
        if(!(vert.containsKey(node_id))) return empty;
        return ((NodeInfo) (getNode(node_id))).getNi();
    }

    /**
     * This function removes a given node from a graph.
     * Removes by creating an iterator to go through the given nodes
     * list of neighbors and remove the "edge" between the node and his
     * neighbors. And at the end remove the node!
     * Checks that give int is ID of a node before. Increase mode count by 1.
     * @param key - Int representing ID of node.
     * @return Deleted node (if deleted). null if doesn't exist.
     */
    @Override
    public node_info removeNode(int key) {
        if (vert.containsKey(key)) {
            Collection<node_info> nei = ((NodeInfo) (getNode(key))).getNi();
            Iterator<node_info> it = nei.iterator();

            while (it.hasNext()) {
                node_info temp = it.next();
                removeEdge(key, temp.getKey());
                it = ((NodeInfo) (getNode(key))).getNi().iterator();
            }
            vert.remove(key);
            MC++;
            vertC--;
            return (getNode(key));
        }
        return null;
    }

    /**
     * This function removes the "edge" between two given nodes.
     * Removes by removing both nodes from the other's list of neighbors.
     * And remove from list of edges.
     * It will only remove after making 3 check's:
     * 1) Check if graph contains both nodes. 2) Check that both given nodes
     * are not the same node. 3) Check if nodes are connected by edge.
     * Decrease edge count by one, and increase mode count by 1.
     * @param node1 Int representing ID of node_info.
     * @param node2 Int representing ID of node_info.
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (vert.containsKey(node1) && vert.containsKey(node2)) {
            if (node1 == node2) return;

            if (hasEdge(node1, node2)) {
                ((NodeInfo)(getNode(node1))).removeNode(node2);
                ((NodeInfo)(getNode(node2))).removeNode(node1);
                String SrcDest = String.valueOf(node1) + "-" + String.valueOf(node2);
                String DestSrc = String.valueOf(node2) + "-" + String.valueOf(node1);
                if (edges.containsKey(SrcDest))
                    edges.remove(SrcDest);
                else
                    edges.remove(DestSrc);


                edgeC--;
                MC++;
            }
        }

    }

    /**
     * This function returns the amount of vertices in the given graph.
     * @return Int representing vertices amount.
     */
    @Override
    public int nodeSize() {
        return this.vertC;
    }

    /**
     * This function returns the amount of edges on graph.
     * @return Int representing edges amount.
     */
    @Override
    public int edgeSize() {
        return this.edgeC;
    }

    /**
     * This function returns the MC (mode count) made of graph.
     * @return Int representing mode count.
     */
    @Override
    public int getMC() {
        return this.MC;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WGraph_DS g = (WGraph_DS) o;
        if ((vert.equals(g.vert)) && (edges.equals(g.edges)) && (vertC == g.vertC) && (edgeC == g.edgeC))
            return true;
        return false;
    }

    /**
     * This class implements interface node_info, represents a NodeInfo:
     * Each node has a unique ID, Tag, String og info, and HashMap<> of neighbors.
     * getters and setters, copy constructor.
     */
    public class NodeInfo implements node_info, java.io.Serializable {

        private int counter = 0;
        private int ID;
        private double Tag;
        private String Info ="";
        private HashMap<Integer, node_info> nei = new HashMap<>();

        /**
         * default constructor
         */
        public NodeInfo() {
            this.ID = counter++;
            this.Tag = Tag;
            this.Info = "";
            this.nei = nei;
        }

        /**
         * Constructor, creates NodeInfo from given key.
          * @param key Integer representing the ID of created node.
         */
        public NodeInfo(int key) {
            this.ID = key;
            this.Tag = Tag;
            this.Info = "";
            this.nei = nei;
        }

        /**
         * Copy constructor,
         * This function creates a deep copy of given node_info
         * and converts it to NodeInfo
         * @param node info
         */
        public NodeInfo(node_info node) {
            if (node != null) {
                this.Info = node.getInfo();
                this.Tag = node.getTag();
                this.ID = node.getKey();

                NodeInfo node1 = (NodeInfo) node;
                Collection<node_info> nei = node1.getNi();
                Iterator<node_info> it = nei.iterator();
                while (it.hasNext()) {
                    node_info temp = it.next();
                }
            }
        }

        /**
         * This function returns the "key" of node(note: each node has a unique "key")
         * @return int key
         */
        @Override
        public int getKey() {
            return ID;
        }

        /**
         * This function returns a String of info specified to node
         * @return String - "info"
         */
        @Override
        public String getInfo() {
            return Info;
        }

        /**
         * This function sets specified nodes Info
         * @param s String
         */
        @Override
        public void setInfo(String s) {
            this.Info = s;
        }

        /**
         * This function returns the "tag" of node. used mainly for algorithms
         * @return double representing nodes Tag.
         */
        @Override
        public double getTag() {
            return Tag;
        }

        /**
         * This function allows to change the "tag" data of the node
         * used mainly for algorithms.
         * @param t double
         */
        @Override
        public void setTag(double t) {
            this.Tag = t;
        }

        /**
         * This function returns a collection of node_info representing the neighbors of selected node
         * @return collection<node_info> nei
         */
        public Collection<node_info> getNi() {
            return nei.values();
        }

        /**
         * This function returns whether the selected node has neighbors
         * @param key - int representing the ID of each node
         * @return returns true if selected node has neighbors and false if it does not
         */
        public boolean hasNi(int key) {
            return this.nei.containsKey(key);
        }

        /**
         * This function adds a selected node_data to the collection of neighbors of another node_info(if node is already
         * a neighbor - then function will not add)
         * @param t - the node_info that will be added to list of neighbors
         */
        public void addNi(node_info t) {
            if (!(this.hasNi(t.getKey())))
                this.nei.put(t.getKey(), t);
        }

        /**
         * This function removes specified node from other node collection
         * of neighbors
         * @param key - the key of node that will be removed
         */
        public void removeNode(int key) {
            this.nei.remove(key);
        }

        public String toString() {
            return "" + ID;
        }

        public boolean equals(Object o) {
            if(!(o instanceof NodeInfo)) return false;
            NodeInfo n = (NodeInfo) o;
            if (!(ID == n.ID) || !(Tag == n.Tag) || !(Info.equals(n.Info))) return false;
            if(!(getV(ID).equals(getV(n.getKey())))) return false;

            return true;
        }
    }


}