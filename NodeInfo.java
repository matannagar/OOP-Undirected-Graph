package ex1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;


public class NodeInfo implements node_info{

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
}
