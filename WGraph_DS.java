package ex1;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class WGraph_DS implements weighted_graph,java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<Integer,node_info> vertix = new HashMap<>();
	private HashMap<String,Double> edges = new HashMap<>();
	private  int edgeSize;
	private  int MC;

	public WGraph_DS() {

		this.edgeSize = 0;
		this.MC=0;
		this.vertix = new HashMap<>();

	}
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
	@Override
	public node_info getNode(int key) {
		// TODO Auto-generated method stub
		if (vertix.containsKey(key))
			return vertix.get(key);
		return null;
	}
	public HashMap<Integer, node_info> getVhash(){
		return vertix;
	}
	public HashMap<String, Double> getEhash(){
		return edges;
	}
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
	//NOTICE: should adding the same node qualify as making change ?
	@Override
	public void addNode(int key) {
		// TODO Auto-generated method stub
		if (!vertix.containsKey(key)) {
			node_info n = new NodeInfo(key);
			vertix.put(n.getKey(), n);
			MC++;
		}
	}
	@Override
	public void connect(int node1, int node2, double w) {
		// TODO Auto-generated method stub
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
	@Override
	public Collection<node_info> getV() {
		// TODO Auto-generated method stub
		return vertix.values();
	}
	public void edgeList() {
		// TODO Auto-generated method stub
		System.out.println(edges.values());
		System.out.println(edges.keySet());
	}

	@Override
	public Collection<node_info> getV(int node_id) {
		// TODO Auto-generated method stub
		if(!vertix.containsKey(node_id)) return null;

		return ((NodeInfo) vertix.get(node_id)).getNi();
	}

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
	@Override
	public int nodeSize() {
		// TODO Auto-generated method stub
		return vertix.size();
	}

	@Override
	public int edgeSize() {
		// TODO Auto-generated method stub

		//return edges.size()/2;
		return edgeSize;
	}

	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return MC;
	}
	public boolean equals(Object o) {
		//check if they are same type object
		if (this==o) return true;
		WGraph_DS g = (WGraph_DS) o;
		System.out.println("1");
		if (o==null|| getClass() != o.getClass()) return false;
		//compare two HASHMAPS
		System.out.println("3");
		if (!this.edges.equals(g.getEhash())) return false;
		System.out.println("2");
		if (!this.vertix.equals(g.getVhash())) return false;
		//compare num of changes
		System.out.println("4");
		if (this.MC != g.getMC()) return false;
		System.out.println("5");
		return true;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String k = ""+edges;
		return k;
	}
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
		public boolean equals(Object o) {
			if (this == o) return true;
			NodeInfo node = (NodeInfo) o;
			if (this.id != node.getKey()) return false;
			if (!this.metadata.equals(node.getInfo())) return false;
			if (this.tag != node.getTag()) return false;
			
			return true;
		}
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
