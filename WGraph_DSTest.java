package ex1;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class WGraph_DSTest {
	public static void main(String[] args) {
		weighted_graph g = graph_creator(6,6,1,1);
		WGraph_DS g1 = (WGraph_DS) g;
		System.out.println(g.getV());
		g1.getEdges();
	}

	private static Random _rndV = null;
	private static Random _rndE = null;

	public static weighted_graph graph_creator(int v_size, int e_size, int seed1, int seed2) {
		weighted_graph g = new WGraph_DS();
		_rndV = new Random(seed1);
		_rndE = new Random(seed2);
		for(int i=0;i<v_size;i++) {
			g.addNode(i);
		}

		// Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
		int[] nodes = nodes(g);
		while(g.edgeSize() < e_size) {
			int a = nextRnd(0,v_size);
			int b = nextRnd(0,v_size);
			double lenght = nextRndE(1,100);
			int i = nodes[a];
			int j = nodes[b];
			g.connect(i,j,lenght);
		}
		return g;
	}

	private static int nextRnd(int min, int max) {
		double v = nextRndV(0.0+min, (double)max);
		int ans = (int)v;
		return ans;
	}
	private static double nextRndV(double min, double max) {
		double d = _rndV.nextDouble();
		double dx = max-min;
		double ans = d*dx+min;
		return ans;
	}
	private static double nextRndE(double min, double max) {
		double d = _rndE.nextDouble();
		double dx = max-min;
		double ans = d*dx+min;
		return ans;
	}
	/**
	 * Simple method for returning an array with all the node_data of the graph,
	 * Note: this should be using an  Iterator<node_edge> to be fixed in Ex1
	 * @param g
	 * @return
	 */
	private static int[] nodes(weighted_graph g) {
		int size = g.nodeSize();
		Collection<node_info> V = g.getV();
		node_info[] nodes = new node_info[size];
		V.toArray(nodes); // O(n) operation
		int[] ans = new int[size];
		for(int i=0;i<size;i++) {
			ans[i] = nodes[i].getKey();
		}
		Arrays.sort(ans);
		return ans;
	}
	void getNode() {
	
	}
}
