package graph;


import graph.doublyLinkedList.DoublyLinkedList;
import graph.doublyLinkedList.Node;
import graph.doublyLinkedList.NodeIterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph <E,T> {
	

	private DoublyLinkedList<Vertex <E,T>> vertexList;
	private DoublyLinkedList<Edge<E,T>> edgeList;

	private boolean directed;
	private boolean isCyclic;
	private boolean isConnected;
	private int connectedComponents;
	
	private int unique_id = 0;
	
	
	public Graph(boolean directed) {
		vertexList = new DoublyLinkedList<Vertex<E,T>>();
		edgeList = new DoublyLinkedList<Edge<E,T>>();
		this.directed = directed;
	}
	

	private Vertex<E,T> addVertex(E data, int id){
		Vertex<E,T> vertex = new Vertex<E,T>(data, id);
		Node<Vertex<E,T>> node = vertexList.add(vertex);
		vertex.setPosition(node);
		return vertex;
	}
	public Vertex<E,T> addVertex(E data){
		return addVertex(data, unique_id++);
	}
	

	public NodeIterator<Vertex<E,T>> vertices() {
		return vertexList.iterator();
	}


	public NodeIterator<Edge<E,T>> edges() {
		return edgeList.iterator();
	}
	

	public Vertex<E,T>[] vertices_array(){
		Vertex<E,T>[] tmp = new Vertex[vertexList.size()];
		NodeIterator<Vertex<E,T>> iter = vertices();
		int index = 0;
		while(iter.hasNext())
			tmp[index++] = iter.next();
		return tmp;
	}
	

	public Edge<E,T>[] edges_array(){
		Edge<E,T>[] tmp = new Edge[edgeList.size()];
		NodeIterator<Edge<E,T>> iter = edges();
		int index = 0;
		while(iter.hasNext())
			tmp[index++] = iter.next();
		return tmp;
	}

	public String toString(){
		String output = "Vertices:\n";
		for(Vertex<E,T> v : vertices_array())
			output += String.format("%s ", v.toString());
		
		output += "\n\nEdges:\n";
		
		for(Edge<E,T> e : edges_array()){
			output += String.format("%s\n", e.toString());
		}
		return output;
	}
	
	public static Graph<String,String> inParser (String fileName, boolean directed) throws FileNotFoundException{
		Graph<String,String> graph = new Graph<String,String>(directed);
		
		Scanner scan = new Scanner(new File(fileName));
		String readLine;
		Pattern pattern;
		Matcher matcher;
		
		readLine = scan.nextLine();
		pattern = Pattern.compile("size\\s*=\\s*(\\d+)");
		matcher = pattern.matcher(readLine);
		matcher.find();
		Vertex<String,String> vertices[] = new Vertex[Integer.parseInt(matcher.group(1))];
		
		while(!(readLine = scan.nextLine()).equals(";") ){
			pattern = Pattern.compile("([^0-9]*)\\s*(\\d+)\\s*=\\s*(.*)");
			matcher = pattern.matcher(readLine);
			matcher.find();
			if(matcher.group(1) == null || matcher.group(1).isEmpty()){
				vertices[Integer.parseInt(matcher.group(2))] = graph.addVertex(matcher.group(3));
			}else if(matcher.group(1).trim().equals("//") || matcher.group(1).trim().equals("#")){
				continue;
			}else{
				throw new InputMismatchException();
			}
		}
		
		while(!(readLine = scan.nextLine()).equals(";") ){
			pattern = Pattern.compile("(.*)\\s*\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*(,\\s*(\\d+|\\d+\\.\\d+)\\s*)?\\)(\\s*=\\s*(.*))?");
			matcher = pattern.matcher(readLine);
			matcher.find();
			if(matcher.group(1) == null || matcher.group(1).isEmpty()){
				double weight = 0.0;
				int v1Index = Integer.parseInt(matcher.group(2));
				int v2Index = Integer.parseInt(matcher.group(3));
				if(matcher.group(5) != null)
					weight = Double.parseDouble(matcher.group(5));
				String label = matcher.group(7);
				
				graph.addEdge(vertices[v1Index], vertices[v2Index], label, weight);
			}else if(matcher.group(1).trim().equals("//") || matcher.group(1).trim().equals("#")){
				continue;
			}else{
				throw new InputMismatchException();
			}
		}
		return graph;
	}	

}
