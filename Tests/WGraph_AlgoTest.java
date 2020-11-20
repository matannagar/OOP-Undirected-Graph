package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import ex1.src.weighted_graph_algorithms;

class WGraph_AlgoTest {
	private weighted_graph g;

	@Test
	void testInitGG() {
		this.g = generateGraph(1000,2000,100);
		weighted_graph_algorithms ga = new WGraph_Algo();
		ga.init(g);
		assertEquals(this.g,ga.getGraph());
		
	}

	@Test
	void testGetGraph() {
	
	}
//**************HOW SHOULD I TEST COPY***************
	@Test
	void testCopy() {
		weighted_graph g = myGraph();
		weighted_graph_algorithms ga = new WGraph_Algo();
		ga.init(g);
		weighted_graph test = new WGraph_DS();	
		test = ga.copy();
		g.addNode(7);
		//lets change G and see if it changes test_graph
		assertEquals(6, test.nodeSize());
	}

	@Test
	void testIsConnected() {
		this.g = myGraph();
		weighted_graph_algorithms ga = new WGraph_Algo();
		ga.init(this.g);
		assertEquals(true, ga.isConnected());
		g.removeEdge(2, 6);
		assertEquals(false, ga.isConnected());
	}

	@Test
	void testShortestPathDist() {
		weighted_graph g = myGraph();
		weighted_graph_algorithms ga = new WGraph_Algo();
		ga.init(g);
		assertEquals(6,ga.shortestPathDist(1, 6));
		g.removeNode(6);
		assertEquals(-1,ga.shortestPathDist(1, 6));
		ga.init(myGraph2());
		assertEquals(7.7, ga.shortestPathDist(1, 4));
	}

	@Test
	void testShortestPath() {
		weighted_graph g = myGraph();
		weighted_graph_algorithms ga = new WGraph_Algo();
		ga.init(g);
		List<node_info> com1 = ga.shortestPath(1, 6);
		List<node_info> comp = new ArrayList<>();
		comp.add(g.getNode(1));
		comp.add(g.getNode(3));
		comp.add(g.getNode(2));
		comp.add(g.getNode(6));
		assertEquals(comp,com1);
		g.removeNode(6);
		comp.clear();
		assertEquals(comp, ga.shortestPath(1, 6));
	}

	@Test
	void testSaveLoad() {
	weighted_graph g = myGraph();
	weighted_graph_algorithms ga = new WGraph_Algo();
	ga.init(g);
	ga.save("myGraph.txt");
	weighted_graph_algorithms gb = new WGraph_Algo();
	gb.load("myGraph.txt");
	assertEquals(true, g.equals(gb.getGraph()));
	}

	@Test
	void testLoad() {
		
	}
	private static weighted_graph myGraph() {
		weighted_graph g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(5);
		g.addNode(6);

		g.connect(1,2,3);
		g.connect(2, 3, 1);
		g.connect(1, 3, 1);
		g.connect(3, 4, 5);
		g.connect(2, 6, 4);
		g.connect(4, 5, 2);
		g.connect(1, 5, 6);
		return g;
	}
	private static weighted_graph generateGraph(int nodes, int edges, double length) {
		weighted_graph g = new WGraph_DS();
		for (int i = 1; i < nodes; i++) {
			g.addNode(i);
		}
		while (g.edgeSize()<edges) {
			int a = (int) getRan(nodes);
			int b = (int) getRan(nodes);
			double c = getRan(length);
			g.connect(a, b, c);
		}

		return g;
	}
	private static double getRan(double max) {
		Double d = ((Math.random() * (max - 1 + 1)) + 1);

		return (double)Math.round(d * 100d) / 100d;
	}
	
	private static weighted_graph myGraph2() {
		weighted_graph g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(5);
		g.addNode(6);
		g.addNode(7);
		g.addNode(8);

		g.connect(1,2,2.8);
		g.connect(1, 8, 7.1);
		g.connect(2	, 3, 3.5);
		g.connect(2, 4, 4.9);
		g.connect(2, 5, 4.7);
		g.connect(2, 6, 4.1);
		g.connect(2, 7, 4);
		g.connect(8, 2, 4.2);
		g.connect(8, 3, 3.5);
		g.connect(8, 4, 1.7);
		g.connect(8, 5, 2.1);
		g.connect(8, 6, 3.5);
		g.connect(3, 4, 6);
		g.connect(4, 5, 2.1);
		g.connect(5, 6, 1.1);
		g.connect(6, 7, 7.5);
		g.connect(7, 8, 9);
			
		return g;
	}
	
}
