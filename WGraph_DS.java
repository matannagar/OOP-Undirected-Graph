package ex1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import ex0.node_data;

public class WGraph_DS implements weighted_graph{
	private HashMap<Integer,node_info> vertix = new HashMap<>();
	private HashMap<pair,Double> edges = new HashMap<>();
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

	@Override
	public boolean hasEdge(int node1, int node2) {
		// TODO Auto-generated method stub
		if (((NodeInfo) vertix.get(node1)).hasNi(node2))
			return true;
		return false;
	}

	@Override
	public double getEdge(int node1, int node2) {
		// TODO Auto-generated method stub
		if (vertix.containsKey(node1)&&vertix.containsKey(node2)) {
			pair temp = new pair(node1,node2);
			return edges.get(temp);
		}
		return -1;
	}
//NOTICE: should adding the same node qualify as making change ?
	@Override
	public void addNode(int key) {
		// TODO Auto-generated method stub
		NodeInfo n = new NodeInfo(key);
		int temp_size = vertix.size();
		vertix.put(n.getKey(), n);
		if(temp_size!=vertix.size())
			MC++;
	}

	@Override
	public void connect(int node1, int node2, double w) {
		// TODO Auto-generated method stub
		if (vertix.containsKey(node1)&&vertix.containsKey(node2)&&node1!=node2){
			//adding each node to the neighbor's list
			if (!((NodeInfo) vertix.get(node1)).hasNi(node2) && !((NodeInfo) vertix.get(node2)).hasNi(node1)) {
				((NodeInfo) vertix.get(node1)).addNi(vertix.get(node2));
				((NodeInfo) vertix.get(node2)).addNi(vertix.get(node1));
				
				pair uno = new pair(node1,node2);
				pair dos = new pair(node2,node1);
				edges.put(uno, w);
				edges.put(dos,w);
				
				edgeSize++;
				MC++;
			}
		}
	}

	@Override
	public Collection<node_info> getV() {
		// TODO Auto-generated method stub
		return vertix.values();
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
				pair uno = new pair(key,temp.getKey());
				pair dos = new pair(temp.getKey(),key);
				edges.remove(uno);
				edges.remove(dos);
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
		if (node1!=node2) { 
			node_info ver1 = vertix.get(node1);
			node_info ver2 = vertix.get(node2);
			if (((NodeInfo) ver1).hasNi(node2) && ((NodeInfo)ver2).hasNi(node1)) {
				((NodeInfo)ver1).getNi().remove(ver2);
				((NodeInfo)ver2).getNi().remove(ver1);
				MC++;
				edgeSize--;
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

	class pair{
		private int node1, node2;

		pair(){
			node1 = 0;
			node2 = 0;
		}
		pair(int ver1,int ver2){
			this.node1=ver1;
			this.node2=ver2;
		}
		int getNode1() {
			return node1;
		}
		int getNode2() {
			return node2;
		}
	
		
	}

}
