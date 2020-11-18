package ex1;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
/**
 * This class represents an undirectional weighted graph.
 * It supports a large number of nodes (over 10^6, with average degree of 10).
 * The implementation is based on a HashMap list that include list of nodes
 *and list of edges in this graph.
 *
 */
public class WGraph_DS implements weighted_graph,java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<Integer,node_info> vertix = new HashMap<>();
	private HashMap<String,Double> edges = new HashMap<>();
	private  int edgeSize;
	private  int MC;
	/**
	 * Default constructor
	 */
	public WGraph_DS() {

		this.edgeSize = 0;
		this.MC=0;
		this.vertix = new HashMap<>();

	}
	/**
	 * creates a deep copy of another graph
	 * @param gr
	 */
	public WGraph_DS(weighted_graph gr) {

		edgeSize = gr.edgeSize();
		MC= gr.getMC();

		this.vertix = new HashMap<>();
		Iterator<node_info> i = gr.getV().iterator();
		while (i.hasNext()) {
			node_info temp = i.next();
			node_info res = new NodeInfo((NodeInfo)temp);
			vertix.put(res.getKey(), res);
		}
	}
	/**
	 * return the node_data by the node_id,
	 * @param key - the node_id
	 * @return node
	 */
	@Override
	public node_info getNode(int key) {
		// TODO Auto-generated method stub
		if (vertix.containsKey(key))
			return vertix.get(key);
		return null;
	}
	/**
	 * @return a hashmap of nodes in the graph
	 */
	public HashMap<Integer, node_info> getVhash(){
		return vertix;
	}
	/**
	 * @return a hashmap of edges in the graph
	 */
	public HashMap<String, Double> getEhash(){
		return edges;
	}
	/**
	 * @param Key1, Key 2
	 * @return boolean whether an edge exists between two nodes in graph
	 */
	@Override
	public boolean hasEdge(int node1, int node2) {
		// TODO Auto-generated method stub
		if(node1==node2) return true;
		if (vertix.containsKey(node1)&&vertix.containsKey(node2)) {
			if (((NodeInfo) vertix.get(node1)).hasNi(node2))
				return true;
		}
		return false;
	}
	/**
	 * @param Key1,Key2
	 * @return the length of an edge
	 * ** if the edge does not exists return -1
	 */
	@Override
	public double getEdge(int node1, int node2) {
		// TODO Auto-generated method stub
		if (vertix.containsKey(node1)&&vertix.containsKey(node2)) {
			if (edges.containsKey("<"+node1+","+node2+">"))
				return edges.get("<"+node1+","+node2+">");
			else if (edges.containsKey("<"+node2+","+node1+">"))
				return edges.get("<"+node2+","+node1+">");
		}
		return -1;
	}
	/**
	 * add's a consecutive node to the graph by it's ID name
	 * @param key
	 */
	@Override
	public void addNode(int key) {
		// TODO Auto-generated method stub
		if (!vertix.containsKey(key)) {
			node_info n = new NodeInfo(key);
			vertix.put(n.getKey(), n);
			MC++;
		}
	}
	/**
	 * Connect an edge between node1 and node2, with an edge with weight >=0.
	 * This method adds the edge to a hashMap list that stores as keys a String <node1,node2>
	 * and as Values the weight of the edge
	 * @param int :Key1,Key2
	 * @param double : weight of edge
	 */
	@Override
	public void connect(int node1, int node2, double w) {
		// TODO Auto-generated method stub
		if (w>=0) {
			if (vertix.containsKey(node1)&&vertix.containsKey(node2)&&node1!=node2){

				//adding each node to the neighbor's list
				if (!((NodeInfo) vertix.get(node1)).hasNi(node2) &&
						!((NodeInfo) vertix.get(node2)).hasNi(node1)) {

					((NodeInfo) vertix.get(node1)).addNi(vertix.get(node2));
					((NodeInfo) vertix.get(node2)).addNi(vertix.get(node1));
					edges.put("<"+node2+","+node1+">",w);
					edgeSize++;
					MC++;
				}
				else if (edges.containsKey("<"+node1+","+node2+">")){
					edges.remove("<"+node1+","+node2+">");
					edges.put("<"+node1+","+node2+">",w);
					MC++;
				}
				else if  (edges.containsKey("<"+node2+","+node1+">")){
					edges.remove("<"+node2+","+node1+">");
					edges.put("<"+node2+","+node1+">",w);
					MC++;
				}
			}
		}
	}
	/**
	 * This method returns a pointer (shallow copy) for a
	 * Collection representing all the nodes in the graph.
	 **/
	@Override
	public Collection<node_info> getV() {
		// TODO Auto-generated method stub
		return vertix.values();
	}
	/**
	 * @param node_id 
	 * @return a Collection containing all the nodes connected to node_id
	 */
	@Override
	public Collection<node_info> getV(int node_id) {
		// TODO Auto-generated method stub
		if(!vertix.containsKey(node_id)) return null;

		return ((NodeInfo) vertix.get(node_id)).getNi();
	}
	/**
	 * @param int Key
	 * this method is based on removing a node from the graph,
	 * thus removing it both from the node's list and the edge's list
	 * and from every neighbor's list
	 */

	@Override
	public node_info removeNode(int key) {
		// TODO Auto-generated method stub
		node_info empty = getNode(key);
		if (getV().contains(getNode(key))) {

			edgeSize=edgeSize - getV(key).size();
			MC = MC + 1 + getV(key).size();
			//remove this node from every neighbor's list
			Iterator<node_info> i1 = getV(key).iterator();

			// this while will loop through the node's neighbors and remove the node from their list of neighbors. 
			while(i1.hasNext()) {
				node_info temp = i1.next();
				if (edges.containsKey("<"+key+","+temp.getKey()+">"))
					edges.remove("<"+key+","+temp.getKey()+">");
				else if (edges.containsKey("<"+temp.getKey()+","+key+">"))			
					edges.remove("<"+temp.getKey()+","+key+">");

				((NodeInfo) temp).getNi().remove(getNode(key));
			}
			//remove every neighbor connected to the node
			getV(key).clear();

			//remove node from list of graph's nodes
			getV().remove(getNode(key));
		}

		vertix.remove(key);
		return empty ;
	}
	/**
	 * @param Key1, Key2
	 * Delete the edge from the graph
	 */
	@Override
	public void removeEdge(int node1, int node2) {
		// TODO Auto-generated method stub
		if (vertix.containsKey(node1)&&vertix.containsKey(node2)) {
			if (node1!=node2) { 
				node_info ver1 = vertix.get(node1);
				node_info ver2 = vertix.get(node2);
				if (((NodeInfo) ver1).hasNi(node2) && ((NodeInfo)ver2).hasNi(node1)) {
					((NodeInfo)ver1).getNi().remove(ver2);
					((NodeInfo)ver2).getNi().remove(ver1);
					edges.remove("<"+node1+","+node2+">");
					edges.remove("<"+node2+","+node1+">");
					MC++;
					edgeSize--;
				}
			}
		}
	}
	/** 
	 * @return the number of vertices (nodes) in the graph.
	 */
	@Override
	public int nodeSize() {
		// TODO Auto-generated method stub
		return vertix.size();
	}
	/**
	 * @return the number of edges (undirectional graph).
	 */
	@Override
	public int edgeSize() {
		// TODO Auto-generated method stub

		//return edges.size()/2;
		return edgeSize;
	}
	/**
	 * @return the Mode Count - for testing changes in the graph.
	 */
	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return MC;
	}
	/**
	 * @param object
	 * this method runs over the default equals method.
	 */
	public boolean equals(Object o) {
		//check if they are same type object
		if (this==o) return true;
		WGraph_DS g = (WGraph_DS) o;
		if (o==null|| getClass() != o.getClass()) return false;
		//compare two HASHMAPS
		if (!this.edges.equals(g.getEhash())) return false;
		if (!this.vertix.equals(g.getVhash())) return false;
		//compare num of changes
		if (this.MC != g.getMC()) return false;
		return true;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String k = ""+edges;
		return k;
	}
	/**
	 * this class implements the interface node_info
	 * each node hold's a unique ID (int based), a hashmap storing a 
	 * list of neighbors, a String to know if this node has been visited
	 * or not, and a tag to store the distance of this node from the source node;
	 * @author matan
	 *
	 */
	public class NodeInfo implements node_info,java.io.Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int id;
		private HashMap<Integer,node_info> neighbors = new HashMap<>();
		private	String metadata="N";
		private	double tag =0;
		/**
		 * A default constructor 
		 */
		public NodeInfo(int key) {
			metadata = "";
			neighbors = new HashMap<>();
			tag = 0;
			id=key;
		}
		/**
		 * 
		 * @param node 
		 * A copy constructor 
		 * @param neighbors -  ADD HERE EXPLANATION  
		 */

		public NodeInfo(NodeInfo node) {
			metadata = node.getInfo();
			neighbors = new HashMap<>();

			Iterator<node_info> i = node.getNi().iterator();
			while(i.hasNext()) {
				node_info temp = i.next();
				neighbors.put(temp.getKey(), temp);

			}

			tag = node.getTag();
			id = node.getKey();
		}
		/**
		 * this method runs over the default equals method
		 * @param object 
		 * @return boolean 
		 */
		public boolean equals(Object o) {
			if (this == o) return true;
			NodeInfo node = (NodeInfo) o;
			if (this.id != node.getKey()) return false;
			if (!this.metadata.equals(node.getInfo())) return false;
			if (this.tag != node.getTag()) return false;

			return true;
		}
		/**
		 * 
		 * @return a Collection of all the node's neighbors
		 */
		public Collection<node_info> getNi() {
			// TODO Auto-generated method stub
			return neighbors.values();
		}
		/**
		 * return true iff this<==>key are adjacent, as an edge between them.
		 * @param key
		 * @return true of false
		 */

		public boolean hasNi(int key) {
			if (neighbors.containsKey(key)) {
				return true;}
			return false;
		}
		/**
		 * @param node_info
		 * This method adds the node_data (t) to this node's list of neighbors
		 */
		public void addNi(node_info t) {
			if (!neighbors.containsKey(id) )
				neighbors.put(t.getKey(), t);
			//t.addNi(this.);

		}
		/**
		 * Removes the specific node from the list of neighbors,
		 * @param node
		 */

		public void removeNode(node_info node) {
			// TODO Auto-generated method stub
			if (neighbors.containsKey(node.getKey()))
				neighbors.remove(node.getKey(), node);
		}
		@Override
		public int getKey() {
			// TODO Auto-generated method stub
			return id;
		}

		@Override
		public String getInfo() {
			// TODO Auto-generated method stub
			return metadata;
		}

		@Override
		public void setInfo(String s) {
			// TODO Auto-generated method stub
			this.metadata = s;

		}

		@Override
		public double getTag() {
			// TODO Auto-generated method stub
			return tag;
		}

		@Override
		public void setTag(double t) {
			// TODO Auto-generated method stub
			this.tag=t;

		}
		public String toString() {
			// TODO Auto-generated method stub
			return ""+id;
		}
	}
}
