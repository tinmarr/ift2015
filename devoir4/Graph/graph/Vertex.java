package graph;

import graph.doublyLinkedList.DoublyLinkedList;
import graph.doublyLinkedList.Node;
import graph.doublyLinkedList.NodeIterator;


public class Vertex <E,T> implements Comparable<Vertex<E,T>>{
	
	
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
	
	public String toString(){
		return String.format("<%s>", data.toString());
	}
}
