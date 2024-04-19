import graph.Graph;
import graph.Vertex;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class GraphMain {
	public static void main(String[] args) {
		try {
			
			Graph<String, String> graph = Graph.inParser("STM.txt", true);
			PrintStream fileOut = new PrintStream("./Output.txt");
            System.setOut(fileOut);
			
			System.out.println("Is the graph connected? " + graph.isConnected());
			System.out.println("Is the graph directed? " + graph.isDirected());
			System.out.println("Does the graph contain cycles? " + graph.isCyclic());
			System.out.println("Number of connected components in the graph: " + graph.connectedComponents());
			
			
			System.out.println("\nPerforming Breadth-First Search (BFS):");
			for (Vertex<String, String> v : graph.BFS()) {
				System.out.print(v + " ");
			}
			
			
			System.out.println("\n\nPerforming Depth-First Search (DFS):");
			for (Vertex<String, String> v : graph.DFS()) {
				System.out.print(v + " ");
			}
	
			
			System.out.println("\n\nCurrent state of the original graph:");
			System.out.println(graph);
	
			
	
			
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
