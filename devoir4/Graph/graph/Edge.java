package graph;

import graph.doublyLinkedList.Node;


public class Edge <E,T> {
	
	private Vertex<E,T> v1, v2;
	private Node<Edge<E,T>> incidentPositionV1, incidentPositionV2;
	private T label;
	private double weight;
	private Node<Edge<E,T>> position;
	private int status;
	
	
	public static final int UNDISCOVERED = 0;
	public static final int DISCOVERED = 1;
	public static final int BACK = 2;
	public static final int FORWARD = 3;
	public static final int CROSS = 4;
	
	
	protected Edge(Vertex<E,T> v1, Vertex<E,T> v2){
		this.v1 = v1;
		this.v2 = v2;
		this.incidentPositionV1 = this.v1.addOutEdge(this);
		this.incidentPositionV2 = this.v2.addInEdge(this);
	}
	
	public Vertex<E,T> getV1() {
		return v1;
	}


	public Vertex<E,T> getV2() {
		return v2;
	}


	/** Get the vertex opposite of v on this edge
	 * @param v The vertex to check
	 * @return The vertex opposite or null if v is not on the edge.
	 */
	public Vertex<E, T> getOpposite(Vertex<E, T> v) {
	    if (v1.equals(v)) {
		return v2;
	    }
	    if (v2.equals(v)) {
		return v1;
	    }
	    return null;
	}


	public T getLabel() {
		return label;
	}

	
	public void setLabel(T label) {
		this.label = label;
	}

	public double getWeight() {
		return weight;
	}

	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	
	protected Node<Edge<E,T>> getPosition() {
		return position;
	}

	
	protected void setPosition(Node<Edge<E,T>> position) {
		this.position = position;
	}

	
	public int getStatus() {
		return status;
	}
	
	
	public String getStatusString() {
		String statusString[] = {"Undiscovered","Discovered", "Back", "Forward","Cross"};
		return statusString[status];
	}
	

	protected void setStatus(int status) {
		this.status = status;
	}

	
	protected Node<Edge<E,T>> getIncidentPositionV1() {
		return incidentPositionV1;
	}


	protected Node<Edge<E,T>> getIncidentPositionV2() {
		return incidentPositionV2;
	}


	public String toString(){
		return label == null ? String.format("(%s, %s)", v1.toString(),v2.toString()) : String.format("(%s)", label);
	}
}
