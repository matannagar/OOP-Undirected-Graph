package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;

class WGraph_DSTest {
	private weighted_graph g;

	@Test
	void testWGraph_DS() {

	}

	@Test
	void testWGraph_DSWeighted_graph() {
	}

	@Test
	void testGetNode() {
		this.g=myGraph();
		//get a non-existent node
		assertEquals(g.getNode(50),null);
		//get the same expected node ID
		assertEquals(g.getNode(4).getKey(),4);

	}

	@Test
	void testHasEdge() {
		this.g=myGraph();
		//check for existing edge
		assertEquals(g.hasEdge(1,3),true);
		//check for an non existent edge
		assertEquals(g.hasEdge(1,6),false);
		//check for an edge between 2 non existent nodes
		assertEquals(g.hasEdge(6,7),false);
		//check for an edge between an existing node to a none existing node
		assertEquals(g.hasEdge(1,7),false);
	}

	@Test
	//build specific graph
	void testGetEdge() {
		this.g = myGraph();
		//fetch a none existing edge between two inner nodes
		assertEquals(-1,g.getEdge(1,6));
		//fetch the size of existing edge
		assertEquals(6,g.getEdge(1,5));
		assertEquals(1,g.getEdge(1,3));
		//remove edge and check if it's there
		g.removeEdge(1, 3);
		assertEquals(-1,g.getEdge(1,3));
		//add edge an check if he was added
		g.connect(1, 6, 4);
		assertEquals(4,g.getEdge(1,6));
		//remove node and try to get an edge that was connected to him
		g.removeNode(6);
		assertEquals(-1,g.getEdge(2, 6));
	}
	//************INSERT MORE SITUATIONS*********************
	@Test
	void testAddNode() {
		this.g = myGraph();
		//add node and check if he was added
		g.addNode(7);
		assertEquals(7,g.getNode(7).getKey());
		//change node's info and check if it has been changed
		g.getNode(7).setInfo("K");
		assertEquals("K",g.getNode(7).getInfo());
		//add same node with same number and check if was updated
	}

	@Test
	void testConnect() {
		this.g = myGraph();
		//connect two existing nodes
		g.connect(1, 6, 4);
		assertEquals(4, g.getEdge(1, 6));
		assertEquals(true, g.hasEdge(1, 6));
		//connect two non-existent nodes
		g.connect(7, 8, 1);
		assertEquals(-1, g.getEdge(7,8));
		assertEquals(false, g.hasEdge(7, 8));
		//connect a node to itself and check if was added
		g.connect(1, 1, 0);
		assertEquals(8, g.edgeSize());
		//connect two nodes with distance 0 
		g.connect(3, 6, 0);
		assertEquals(true, g.hasEdge(3, 6));
		//update a connected existing edge
		g.connect(1, 2, 2);
		assertEquals(2.0, g.getEdge(2, 1));
	}

	@Test
	void testGetV() {
		this.g = myGraph();
		//check if list has same num of nodes
		assertEquals(6,g.nodeSize());
		//add existing node and see if it changed num of nodes
		g.addNode(1);
		assertEquals(6,g.nodeSize());
	}

	@Test
	void testGetList() {
		//get non-existent edges

	}

	@Test
	void testGetVInt() {
		this.g=myGraph();
		//get neighbor list of non-existent node
		assertEquals(null, g.getV(7));	
	}

	@Test
	void testRemoveNode() {
		this.g=myGraph();
		g.removeNode(6);
		//check num of nodes
		assertEquals(5,g.getV().size());
		//check if it is not in the list anymore
		assertEquals(false,(((WGraph_DS) g).getVhash().containsKey(6)));
		//check if 6 was erased from 2 neighbor's list
		assertEquals(false, g.getV(2).contains(g.getNode(6)));
		//check if exists an edge between 2 and 6
		assertEquals(-1, g.getEdge(2, 6));
		//remove non-existent node
		assertEquals(null, g.removeNode(7));
	}

	@Test
	void testRemoveEdge() {
		this.g=myGraph();
		//remove non-existent edge
		g.removeEdge(1, 6);
		assertEquals(7, g.edgeSize());
		//remove neighbors
		g.removeEdge(2, 6);
		assertEquals(false, g.getV(2).contains(g.getNode(6)));
	}

	@Test
	void testNodeSize() {
		this.g=myGraph();
		//first check of nodes in graph
		assertEquals(6, g.nodeSize());
		//remove 2 nodes
		g.removeNode(1);
		g.removeNode(2);
		assertEquals(4, g.nodeSize());
		//add 2 nodes
		g.addNode(7);
		g.addNode(8);
		assertEquals(6, g.nodeSize());
	}

	@Test
	void testEdgeSize() {
		this.g=myGraph();
		//first test
		assertEquals(7, g.edgeSize());
		//remove center node
		g.removeNode(3);
		assertEquals(4, g.edgeSize());
		g.removeNode(6);
		assertEquals(3, g.edgeSize());

	}

	@Test

	void testGetMC() {
		this.g=myGraph();
		assertEquals(13, g.getMC());
		g.removeEdge(2, 6);
		assertEquals(14, g.getMC());
		g.removeNode(2);
		assertEquals(17, g.getMC());
		g.removeEdge(2, 6);
		assertEquals(17, g.getMC());
		g.addNode(1);
		assertEquals(17, g.getMC());
		g.removeNode(2);
		assertEquals(17,g.getMC());
	}
	@Test

	void testEquals() {
		this.g=myGraph();
		weighted_graph h = new WGraph_DS();
		h=myGraph();
		assertEquals(true,g.equals(h) );	
	}
	@Test
	void testTime(){
		//big graph
		long start = new Date().getTime();
		int no = 1000*50, ed = 302650;
		weighted_graph g = generateGraph(no,ed,100);
		long end   = new Date().getTime();
		double dt = (end-start)/1000.0;
		boolean t = dt<5;
		assertEquals(true, t);
		for (int i = 0; i < g.nodeSize(); i++) {
			g.connect(1, i, getRan(100));
		}
		long begin = new Date().getTime();
		g.removeNode(1);
		long stop   = new Date().getTime();
		dt = (stop-begin)/1000.0;
		t = dt<5;
		assertEquals(true, t);
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
}
