package graph.doublyLinkedList;


public class Node<E>{
	protected Node<E> next, previous;
	private E data;
	

	protected Node(E data, Node<E> previous, Node<E> next){
		this.data = data;
		this.previous = previous;
		this.next = next;
	}

	
	protected Node(E data){
		this(data,null,null);
	}
	
	
	public boolean hasNext(){
		return next != null;
	}
	
	
	public boolean hasPrevious(){
		return previous != null;
	}
	
	
	public E getData() {
		return data;
	}
	
	
	public void setData(E data) {
		this.data = data;
	}
	
	
	public Node<E> next(){
		return next;
	}
	

	public Node<E> previous(){
		return previous;
	}
	
	
	protected void destroy(){
		data = null;
		next = null;
		previous = null;
	}
	
	
	public String toString(){
		return String.format("%s", data.toString());
	}
}