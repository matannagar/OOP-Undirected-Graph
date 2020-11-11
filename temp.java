package ex1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.print.DocFlavor.URL;

public class temp {
	public static void main(String[] args) {
		WGraph_DS g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);

		g.connect(1,2,3);
		g.connect(2, 3, 1);
		g.connect(1, 3, 1);
		weighted_graph_algorithms g0 = new WGraph_Algo();
		g0.init(g);

		try {
			File file_ = new File("myGraph.txt");

			if (!file_.exists()) {
				file_.createNewFile();
			}
			PrintWriter pw = new PrintWriter(file_);
			pw.println("this is my file content");
			pw.println(5);
			pw.close();
			System.out.println("done");
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		try{
			Scanner x = new Scanner(new File("myGraph.txt"));
			while (x.hasNext()){
				System.out.println(x.next());
			}
		}
		catch (Exception e){
			System.out.println("Couldn't find the file");
		}
	}
}