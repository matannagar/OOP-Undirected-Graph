package ex1;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import ex1.WGraph_DS.NodeInfo;


public class WGraph_Algo implements weighted_graph_algorithms{
	private weighted_graph g0;
	private node_info[] parent;

	public WGraph_Algo() {
		this.g0 = new WGraph_DS();
	}

	@Override
	public void init(weighted_graph g) {
		this.g0 = g;
		// TODO Auto-generated method stub

	}

	@Override
	public weighted_graph getGraph() {
		// TODO Auto-generated method stub
		return g0;
	}

	@Override
	public weighted_graph copy() {
		// TODO Auto-generated method stub
		weighted_graph g1 = new WGraph_DS(g0);

		return (weighted_graph) g1;
	}

	@Override
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
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		node_info source = g0.getNode(src);
		node_info destination = g0.getNode(dest);

		return shortestDist(source, destination);
	}
	@Override

	public List<node_info> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		if (dest < 0 || dest >= g0.nodeSize()) throw new IllegalArgumentException("Can't return an index bigger than the amount of nodes in graph");
		if (src < 0 || src >= g0.nodeSize()) throw new IllegalArgumentException("Invalid node index");
		double dist = shortestPathDist(src, dest);
		List<node_info> path = new ArrayList<>();
		if (dist == -1) return path;
		for (node_info at = g0.getNode(dest); at != null; at = parent[at.getKey()-1]) 
			path.add(at);
		Collections.reverse(path);
		reset();
		return path;
	}

	private double shortestDist(node_info src, node_info end){
		//create a priority queue that will be able to extract the "best" next node; 
		PriorityQueue<node_info> pq = new PriorityQueue<>(2*g0.nodeSize(),comparator);
		pq.offer(src);
		System.out.println(pq);
		src.setTag(0);
		src.setInfo("V");
		this.parent = new node_info[g0.nodeSize()];
		System.out.println(Arrays.toString(parent));

		double[] distance = new double[g0.nodeSize()*2];
		for (int i = 0; i < distance.length; i++) {
			distance[i]=Double.POSITIVE_INFINITY;
		}
		distance[src.getKey()-1]=0;

		System.out.println(Arrays.toString(distance));

		while (!pq.isEmpty()) {
			System.out.println("try 1");
			node_info node = pq.poll();
			System.out.println(pq);
			node.setInfo("V");
			if(distance[node.getKey()-1]< node.getTag()) continue;

			Collection <node_info> neighbors =   ((NodeInfo)node).getNi();
			System.out.println(neighbors + " im the neighbors of"+" "+node.getKey());
			for (node_info next: neighbors) {
				node_info temp = next;

				if(!temp.getInfo().equals("V")) {
					double newDist = distance[node.getKey()-1] + g0.getEdge(node.getKey(), next.getKey());
					System.out.println(newDist + "im the new dist");
					System.out.println(Arrays.toString(distance));
					parent[next.getKey()-1] =node;
					distance[next.getKey()-1]=newDist;
					next.setInfo("V");
					pq.offer(next);
					next.setTag(newDist);
				}
			}
			if (node.getKey()==end.getKey()) return distance[end.getKey()-1];
		}			
		return -1;
	}

	private node_info[] loop(node_info src){
		//we'll create a queue 
		Queue<node_info> q = new LinkedList<node_info>();
		q.add(src);
		src.setInfo("V");
		node_info[] parent = new node_info[1111111];

		while (!q.isEmpty()) {
			node_info node = q.poll();
			Collection <node_info> neighbors =  ((NodeInfo) node).getNi();
			for (node_info next: neighbors) {
				node_info temp = next;
				if(!temp.getInfo().equals("V")) {
					q.add(next);
					next.setInfo("V");
					parent[next.getKey()-1] = node;
				}
			}
		}			
		return parent;
	}
	@Override

	public boolean save(String file) {
		try {
			File file_ = new File("myGraph.txt");

			if (!file_.exists()) {
				file_.createNewFile();
			}
			PrintWriter pw = new PrintWriter(file_);
			pw.println("this is my file content");
			pw.println(100);
			System.out.println("done");
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return false;
	}
	@Override

	public boolean load(String file) {
		// TODO Auto-generated method stub
		System.out.println(new File("MyGraph.txt").getAbsolutePath());
		try{
			Scanner x = new Scanner(new File("MyGraph"));
			while (x.hasNext()){
				String a= x.next();
				int c= Integer.valueOf(x.next());
				String b= x.next();
				System.out.println(c);
			}
		}
		catch (Exception e){
			System.out.println("Couldn't find the file");
		}
	}


private void reset() {
	Iterator<node_info> i = g0.getV().iterator();
	while (i.hasNext()) {
		node_info temp = i.next();
		temp.setInfo("N");
	}
}

private Comparator<node_info> comparator = new Comparator<node_info>() {
	public int compare(node_info node1, node_info node2) {
		double small = 0.0000000001;
		if (Math.abs(node1.getTag() - node2.getTag()) < small) return 0;
		return (node1.getTag() - node2.getTag()) > 0 ? +1 : -1;
	}

};

public static class Node {
	int id;
	double value;

	public Node(int id, double value) {

		this.id = id;
		this.value = value;
	}
}
}
