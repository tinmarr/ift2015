// Complexité des methodes demandé
// | Méthode             | Complexité |
// |---------------------+------------|
// | addEdge             | O(1)       |
// | removeVertex        | O(n)       |
// | areAdjacent         | O(n)       |
// | connectedComponents | O(n^2)     |
// | isConnected         | O(n)       |
// | isCyclic            | O(n^2)     |
// | isDirected          | O(1)       |

package graph;


import graph.doublyLinkedList.DoublyLinkedList;
import graph.doublyLinkedList.Node;
import graph.doublyLinkedList.NodeIterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

	/**
	 * Removes a vertex from the graph. Also removes all edges connecting to it.
	 * 
	 * @param v The vertex to remove
	 */
	public void removeVertex(Vertex<E, T> v) {
		Node<Vertex<E, T>> currentNode = vertexList.first();
		while (currentNode != null) {
			if (currentNode.getData() == v) {
				// Remove edges
				NodeIterator<Edge<E, T>> inEdges = currentNode.getData().getInEdges();
				while (inEdges.hasNext()) {
					removeEdge(inEdges.next());
				}
				NodeIterator<Edge<E, T>> outEdges = currentNode.getData().getOutEdges();
				while (outEdges.hasNext()) {
					removeEdge(outEdges.next());
				}
				// Remove node
				vertexList.remove(currentNode);
				return;
			}
			currentNode = currentNode.next();
		}

	}

	/**
	 * Add an edge to the Graph. Does not add the verticies to the Graph.
	 * 
	 * @param v1     The first vertex (from if directed)
	 * @param v2     The second vertex (to if directed)
	 * @param label  The label for the edge
	 * @param weight The weight of the edge
	 * @return The array of added edges
	 */
	@SuppressWarnings("unchecked")
	public Edge<E, T>[] addEdge(Vertex<E, T> v1, Vertex<E, T> v2, T label, double weight) {
		Edge<E, T>[] edges = new Edge[2];
		Edge<E, T> edge = new Edge<E, T>(v1, v2);
		edge.setLabel(label);
		edge.setWeight(weight);
		edgeList.add(edge);
		edges[0] = edge;
		if (!directed) {
			Edge<E, T> otherEdge = new Edge<E, T>(v2, v1);
			otherEdge.setLabel(label);
			otherEdge.setWeight(weight);
			edgeList.add(otherEdge);
			edges[1] = otherEdge;
		}
		return edges;
	}

	/**
	 * Removes an edge from the graph. Does not remove corresponding verticies.
	 * 
	 * @param e The edge to remove
	 */
	public void removeEdge(Edge<E, T> e) {
		int numDelete = 0; // Tracks the number of deleted edges (useful for non-directed graphs)
		Node<Edge<E, T>> currentNode = edgeList.first();
		while (currentNode != null) {
			if (currentNode.getData().getLabel() == e.getLabel()) {
				edgeList.remove(currentNode);
				numDelete += 1;
				if ((numDelete == 1 && directed) || (numDelete == 2)) {
					return;
				}
			}
			currentNode = currentNode.next();
		}
	}

	/**
	 * Check if two vertices are adjacent.
	 * 
	 * @param v1 the first vertex
	 * @param v2 the second vertex
	 * @return true if there is an edge between v1 and v2, false otherwise
	 */
	public boolean areAdjacent(Vertex<E, T> v1, Vertex<E, T> v2) {
		NodeIterator<Edge<E, T>> it = edgeList.iterator();
		while (it.hasNext()) {
			Edge<E, T> edge = it.next();
			if (directed) {
				if ((edge.getV1() == v1 && edge.getV2() == v2)) {
					return true;
				}
			} else {
				if ((edge.getV1() == v1 && edge.getV2() == v2) || (edge.getV1() == v2 && edge.getV2() == v1)) {
					return true;
				}
			}
		}
		return false;
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


	/**
	 * Performs a breadth-first search (BFS) over the entire graph, covering all
	 * components.
	 *
	 * @return An array of all vertices in the graph, as discovered by BFS.
	 */
	public Vertex<E, T>[] BFS() {
		// Reset all vertices to UNVISITED
		NodeIterator<Vertex<E, T>> vertices = this.vertices();
		while (vertices.hasNext()) {
			vertices.next().setStatus(Vertex.UNVISITED);
		}

		ArrayList<Vertex<E, T>> visited = new ArrayList<>();
		vertices = this.vertices(); // Reset iterator

		while (vertices.hasNext()) {
			Vertex<E, T> vertex = vertices.next();
			if (vertex.getStatus() == Vertex.UNVISITED) {
				bfsComponent(vertex, visited);
			}
		}

		return visited.toArray(new Vertex[visited.size()]);
	}

	private void bfsComponent(Vertex<E, T> start, ArrayList<Vertex<E, T>> visited) {
		ArrayList<Vertex<E, T>> queue = new ArrayList<>();
		queue.add(start);
		start.setStatus(Vertex.VISITING);

		int index = 0;
		while (index < queue.size()) {
			Vertex<E, T> current = queue.get(index++);
			visited.add(current);
			current.setStatus(Vertex.VISITED);

			NodeIterator<Edge<E, T>> edges = current.getOutEdges();
			while (edges.hasNext()) {
				Vertex<E, T> neighbor = edges.next().getOpposite(current);
				if (neighbor != null && neighbor.getStatus() == Vertex.UNVISITED) {
					neighbor.setStatus(Vertex.VISITING);
					queue.add(neighbor);
				}
			}
		}
	}

	/**
	 * Performs a depth-first search (DFS) over the entire graph, covering all
	 * components.
	 *
	 * @return An array of all vertices in the graph, as discovered by DFS.
	 */
	public Vertex<E, T>[] DFS() {
		// Reset all vertices to UNVISITED
		NodeIterator<Vertex<E, T>> vertices = this.vertices();
		while (vertices.hasNext()) {
			vertices.next().setStatus(Vertex.UNVISITED);
		}

		ArrayList<Vertex<E, T>> visited = new ArrayList<>();
		vertices = this.vertices(); // Reset iterator

		while (vertices.hasNext()) {
			Vertex<E, T> vertex = vertices.next();
			if (vertex.getStatus() == Vertex.UNVISITED) {
				dfsRecursive(vertex, visited);
			}
		}

		return visited.toArray(new Vertex[visited.size()]);
	}

	private void dfsRecursive(Vertex<E, T> vertex, ArrayList<Vertex<E, T>> visited) {
		visited.add(vertex);
		vertex.setStatus(Vertex.VISITED);

		NodeIterator<Edge<E, T>> edges = vertex.getOutEdges();
		while (edges.hasNext()) {
			Vertex<E, T> neighbor = edges.next().getOpposite(vertex);
			if (neighbor != null && neighbor.getStatus() == Vertex.UNVISITED) {
				dfsRecursive(neighbor, visited);
			}
		}
	}


	/**
	 * Counts the number of connected components in the graph.
	 * 
	 * @return The number of connected components in the graph.
	 */
	public int connectedComponents() {
		int count = 0;
		// Reset all vertices to UNVISITED
		NodeIterator<Vertex<E, T>> vertices = this.vertices();
		while (vertices.hasNext()) {
			vertices.next().setStatus(Vertex.UNVISITED);
		}

		// Perform DFS from each unvisited vertex, each new DFS call indicates a new
		// component
		vertices = this.vertices(); // Reset iterator to reuse
		while (vertices.hasNext()) {
			Vertex<E, T> vertex = vertices.next();
			if (vertex.getStatus() == Vertex.UNVISITED) {
				ArrayList<Vertex<E, T>> visited = new ArrayList<>();
				dfsRecursive(vertex, visited);
				count++; // Each call of dfsRecursive indicates a new connected component
			}
		}

		return count;
	}

	/**
	 * Determines if the graph is connected. For directed graphs, this checks if the
	 * graph is strongly connected.
	 * 
	 * @return true if the graph is connected, false otherwise.
	 */
	public boolean isConnected() {
		if (vertexList.first() == null)
			return true; // An empty graph is trivially connected

		// Reset all vertices to UNVISITED
		NodeIterator<Vertex<E, T>> vertices = this.vertices();
		while (vertices.hasNext()) {
			Vertex<E, T> vertex = vertices.next();
			vertex.setStatus(Vertex.UNVISITED);
		}

		// Use DFS to see if all vertices can be reached from the first vertex
		ArrayList<Vertex<E, T>> visited = new ArrayList<>();
		Vertex<E, T> startVertex = vertexList.first().getData();
		dfsRecursive(startVertex, visited);

		if (visited.size() != vertexList.size()) {
			return false; // Not all vertices were reachable from the start vertex
		}

		// If directed, also need to check reachability from each vertex
		if (directed) {
			for (Vertex<E, T> vertex : visited) {
				visited.clear();
				dfsRecursive(vertex, visited);
				if (visited.size() != vertexList.size()) {
					return false; // Not all vertices are reachable from this vertex
				}
			}
		}

		return true;
	}

	/**
	 * Checks if the graph contains a cycle.
	 * 
	 * @return true if the graph contains at least one cycle, false otherwise.
	 */
	public boolean isCyclic() {
		// Reset all vertices to UNVISITED
		NodeIterator<Vertex<E, T>> vertices = this.vertices();
		while (vertices.hasNext()) {
			Vertex<E, T> vertex = vertices.next();
			vertex.setStatus(Vertex.UNVISITED);
		}

		// Check for cycle in different components
		vertices = this.vertices();
		while (vertices.hasNext()) {
			Vertex<E, T> vertex = vertices.next();
			if (vertex.getStatus() == Vertex.UNVISITED) {
				if (dfsCheckCycle(vertex, null)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Helper method to perform DFS to detect cycles.
	 * 
	 * @param vertex The current vertex.
	 * @param parent The parent vertex in the DFS tree.
	 * @return true if a cycle is found, false otherwise.
	 */
	private boolean dfsCheckCycle(Vertex<E, T> vertex, Vertex<E, T> parent) {
		vertex.setStatus(Vertex.VISITING);
		NodeIterator<Edge<E, T>> edges = vertex.getOutEdges();
		while (edges.hasNext()) {
			Vertex<E, T> neighbor = edges.next().getOpposite(vertex);
			if (neighbor != null) {
				if (neighbor.getStatus() == Vertex.UNVISITED) {
					if (dfsCheckCycle(neighbor, vertex)) {
						return true;
					}
				} else if (neighbor != parent || directed) { // For undirected, check if the neighbor is not the parent
					return true;
				}
			}
		}
		vertex.setStatus(Vertex.VISITED);
		return false;
	}

	/**
	 * Returns whether the graph is directed.
	 * 
	 * @return true if the graph is directed, false otherwise.
	 */
	public boolean isDirected() {
		return directed;
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
