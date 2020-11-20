package ex1.src;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import ex1.src.WGraph_DS.NodeInfo;

public class WGraph_Algo implements weighted_graph_algorithms,java.io.Serializable{
	/**
	 * This class implements the interface weighted_graph_algorithms
	 * We used dijkstra's algorithm, a bfs based algorithm 
	 * this class hold's an array for storing the distance of a node
	 * from source node 
	 */
	private static final long serialVersionUID = 1L;
	private weighted_graph g0;
	private node_info[] parent;
	private int space;
	/**
	 * default constructor
	 */
	public WGraph_Algo() {
		this.g0 = new WGraph_DS();
		this.space = space + g0.edgeSize();
	}
	/**
	 * Initialize an existing graph so that this set of algorithms
	 * can be implemented
	 */
	@Override
	public void init(weighted_graph g) {
		this.g0 = g;
		space = g.nodeSize() + space;
		// TODO Auto-generated method stub

	}
	/**
	 * @return the init graph
	 */
	@Override
	public weighted_graph getGraph() {
		// TODO Auto-generated method stub
		return g0;
	}
	/**
	 * return a deep copy of this weighted_graph
	 */
	@Override
	public weighted_graph copy() {
		// TODO Auto-generated method stub
		weighted_graph g1 = new WGraph_DS(g0);

		return (weighted_graph) g1;
	}
	@Override
	/**
	 * @return true iff every node in the graph has been visited
	 * by the bfs algorithm - loop 	
	 */
	public boolean isConnected() {
		// TODO Auto-generated method stub
		if (g0.getV().isEmpty()) return true;

		else {
			//we'll choose the first vertix from the graph's list as source 
			Iterator<node_info> i = g0.getV().iterator();
			node_info src = i.next();
			//we'll send the src to a private function which will try to loop through all the nodes in the graph
			// and mark them as Visitied ('V')
			loop(src);

			//with this for each, we'll loop through the list of nodes in the graph and check whether they
			// all have been visited
			for (node_info next : g0.getV()) {
				if (!next.getInfo().contains("V")) {
					//reset's the string data of the node's back into unvisited.
					reset();
					return false;
				}
			}
		}
		reset();
		return true;
	}
	@Override
	/**
	 * @param node_info source, node_info distance
	 * @return double, showing the minimal distance from source node to an end node
	 * this function is based on dijkstra's algorithm and return -1 if such a path
	 * does not exist.
	 * 
	 */
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		node_info source = g0.getNode(src);
		node_info destination = g0.getNode(dest);

		return shortestDist(source, destination);
	}
	@Override
/**
 * @param node_info source, node_info end
 * @return a list on nodes, representing the shortest path from source node to end node
 * considering the weights of each edge
 */
	public List<node_info> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		List<node_info> path = new ArrayList<>();
		if (g0.getV().contains(g0.getNode(src))&&g0.getV().contains(g0.getNode(dest))) {
			if (dest < 0 || dest >= 2*g0.nodeSize()) return path;
			if (src < 0 || src >= 2*g0.nodeSize()) return path;
			double dist = shortestPathDist(src, dest);
			if (dist == -1) return path;
			//***
			for (node_info at = g0.getNode(dest); at != null; at = parent[at.getKey()]) 
				path.add(at);
			Collections.reverse(path);
			reset();
			return path;
		}
		return path;
	}
	/**
	 * @param source node, end node
	 * @return a parent array of nodes and a min distance 
	 * this function is based on Dijkstra's algorithm, implemented by using a Priority Queue 
	 * and an inner comparator. 
	 * The func fixes a single node as the "source" node and finds shortest paths from the source to all other 
	 * nodes in the graph, producing a shortest-path array.
	 * For a given source node in the graph, the algorithm finds the shortest path between that node and every other.
	 * 
	 */
	private double shortestDist(node_info src, node_info end){
		//create a priority queue that will be able to extract the "best" next node; 
		if (g0.getV().contains(src)&&g0.getV().contains(end)) {
			PriorityQueue<node_info> pq = new PriorityQueue<>(2*space,myCompare);
			pq.add((src));
			src.setTag(0);
			src.setInfo("V");
			this.parent = new node_info[2*space];

			double[] distance = new double[space*2];
			for (int i = 0; i < distance.length; i++) {
				distance[i]=Double.POSITIVE_INFINITY;
			}
			//***
			distance[src.getKey()]=0;

			while (!pq.isEmpty()) {
				node_info node = pq.poll();
				node.setInfo("V");
//***
				if(distance[node.getKey()]>= node.getTag()) { 
					Collection <node_info> neighbors =   ((NodeInfo)node).getNi();
					for (node_info next: neighbors) {
						node_info temp = next;

						if(!temp.getInfo().equals("V")) { 
							//***
							double newDist = distance[node.getKey()] + g0.getEdge(node.getKey(), temp.getKey());
							//***
							if (newDist<distance[temp.getKey()]) {
								//***
								parent[temp.getKey()] =node;
								//***
								distance[temp.getKey()]=newDist;
								temp.setTag(newDist);
								pq.add(temp);	
							}	
						}		
					}
				}
				if (node.getKey()==end.getKey()) {reset();
				//***
				return distance[end.getKey()];
				}
			}
			reset();
		}
		return -1;
	}
	/**
	 * 
	 * @param src
	 * @return an array that hold's the parent node of each node
	 * in the node's id place in the array.
	 * this method is based on a BFS algorithm that tries to loop through
	 * the entire list of nodes in the graph and mark each one of them as "V"
	 */
	private node_info[] loop(node_info src){
		//we'll create a queue 
		Queue<node_info> q = new LinkedList<node_info>();
		q.add(src);
		src.setInfo("V");
		node_info[] parent = new node_info[g0.nodeSize()*2];

		while (!q.isEmpty()) {
			node_info node = q.poll();
			Collection <node_info> neighbors =  ((NodeInfo) node).getNi();
			for (node_info next: neighbors) {
				node_info temp = next;
				if(!temp.getInfo().equals("V")) {
					q.add(next);
					next.setInfo("V");
					//***
					parent[next.getKey()] = node;
				}
			}
		}			
		return parent;
	}
	/**
	 *Saves this weighted (undirected) graph to the given
	 * file name using the Serialized method implemented in java
	 * @param file - the file name (may include a relative path).
	 * @return true - iff the file was successfully saved
	 */
	@Override
	public boolean save(String file) {
//		String filename = "myGraph.txt";
		try {
			//saving object in a file -- not readable, only to the computer
			FileOutputStream gFile = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(gFile);
			//Serialization of object
			out.writeObject(g0);
			out.close();
			gFile.close();

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		// TODO Auto-generated method stub
		System.out.println("Object has been Serialized");
		return true;
	}
	/**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
	@Override
	public boolean load(String file) {
		// TODO Auto-generated method stub
		System.out.println("Desrialize from: "+ file );
		try{
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			weighted_graph g1 = (weighted_graph) objectInputStream.readObject();

			fileInputStream.close();
			objectInputStream.close();
			this.g0 = g1;
		}		catch (IOException | ClassNotFoundException e){
			e.printStackTrace();
			return false;
		}
		System.out.println("Object has been DeSerialized");
		return true;
	}
	private void reset() {
		Iterator<node_info> i = g0.getV().iterator();
		while (i.hasNext()) {
			node_info temp = i.next();
			temp.setInfo("");
		}
	}
	/**
	 * This comparator helps the PriorityQueue prioritize which
	 * node he should pull out of the Queue next based on the
	 * tag info.
	 */
	private Comparator<node_info> myCompare = new Comparator<node_info>() {
		public int compare(node_info node1, node_info node2) {
			double small = 0.00001;
			if (Math.abs(node1.getTag() - node2.getTag()) < small) return 0;
			return (node1.getTag() - node2.getTag()) > 0 ? +1 : -1;
		}
	};
}
