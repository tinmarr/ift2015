package graph;

import java.util.ArrayList;

import graph.doublyLinkedList.DoublyLinkedList;
import graph.doublyLinkedList.Node;
import graph.doublyLinkedList.NodeIterator;


public class Vertex <E,T> {
	
	
	private E data;
	private DoublyLinkedList<Edge<E,T>> inEdges,outEdges;
	private Node<Vertex<E,T>> position;
	private int status;
	private int color;
	private final int id;
	

	
	
	public static final int UNVISITED = 0;
	public static final int VISITING = 1;
	public static final int VISITED = 2;
	
	
	protected static final int UNCOLORED = 0;
	
	
	protected Vertex(E data, int id) {
		this.data = data;
		this.status = UNVISITED;
		this.color = 0;
		this.id = id;
		inEdges = new DoublyLinkedList<Edge<E,T>>();
		outEdges = new DoublyLinkedList<Edge<E,T>>();
	}

	protected Vertex(E data) {
		this(data,0);
	}
	
	public NodeIterator<Edge<E,T>> getOutEdges(){
		return outEdges.iterator();
	}
	
	public NodeIterator<Edge<E,T>> getInEdges(){
		return inEdges.iterator();
	}
	
	protected Node<Edge<E,T>> addOutEdge(Edge<E,T> e){
		return outEdges.add(e);
	}

	protected Node<Edge<E,T>> addInEdge(Edge<E,T> e){
		return inEdges.add(e);
	}
	
	protected void removeInEdge(Node <Edge<E,T>> node){
		inEdges.remove(node);
	}
	
	protected void removeOutEdge(Node <Edge<E,T>> node){
		outEdges.remove(node);
	}
	
	public E getData() {
		return data;
	}
	
	protected Node<Vertex<E,T>> getPosition() {
		return position;
	}
	
	protected void setPosition(Node<Vertex<E,T>> position) {
		this.position = position;
	}

	public void setData(E data) {
		this.data = data;
	}
	
	public int getStatus() {
		return status;
	}

	protected void setStatus(int status) {
		this.status = status;
	}

	public int getColor() {
		return color;
	}
	
	protected void setColor(int color) {
		this.color = color;
	}
	
	public int getID(){
		return id;
	}

	/** Get the neighbors of this vertex. A neighbor is defined as a vertex that is on an outgoing node.
	 * @return An array with all the neighbors
	 */
	@SuppressWarnings("unchecked")
	public Vertex<E, T>[] getNeighbors() {
		ArrayList<Vertex<E, T>> neighbors = new ArrayList<Vertex<E, T>>();
		NodeIterator<Edge<E, T>> edges = getOutEdges();
		while (edges.hasNext()) {
			Vertex<E, T> potential = edges.next().getOpposite(this);
			if (potential != null) {
				neighbors.add(potential);
			}
		}
		return (Vertex<E, T>[]) neighbors.toArray();
	}
	
	public String toString(){
		return String.format("<%s>", data.toString());
	}
}
