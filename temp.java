package ex1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

import javax.print.DocFlavor.URL;

import ex0.NodeInfo;

public class temp {
	public static void main(String[] args) {
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

		System.out.println(g.edgeSize());
		System.out.println(g.toString());
		System.out.println(g.getV(2));
		g.removeNode(6);
		System.out.println(g.getV(2));
		System.out.println(g.getV(2).contains(g.getNode(6)));
		
		System.out.println(g.getEdge(1, 2));
		g.connect(1, 2, 2);
		System.out.println(g.getEdge(2, 1));
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
		g.connect(4, 6, 3);
		g.connect(2, 6, 4);
		g.connect(4, 5, 2);
		g.connect(1, 5, 6);
		return g;
	}
}