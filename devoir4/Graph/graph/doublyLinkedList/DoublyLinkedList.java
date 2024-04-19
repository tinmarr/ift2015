package graph.doublyLinkedList;

public class DoublyLinkedList <E> {
	
	private Node <E> head,tail;
	private int size;
	
	
	public DoublyLinkedList() {
		size = 0;
		head = null;
		tail = null;
	}
	

	public Node<E> add(E data){
		Node<E> node = new Node<E>(data);
		
		if(size == 0){
			head = node;
		
		}else{
			tail.next = node;
			node.previous = tail;
		}
		
		tail = node;
		size++;
		return node;
	}
	
	
	public Node<E> addFirst(E data){
		Node<E> node = new Node<E>(data);
		
		if(size > 0)
			head.previous = node;
		node.next = head;
		head = node;
		
		size++;
		return node;
	}
	
	
	public void remove(Node<E> node){
		
		if(head == node){
			
			if(size == 1){
				head = null;
			
			}else{
				node.next.previous = null;
				head = node.next;
			}
			
		}else if(tail == node){
			node.previous.next = null;
			tail = node.previous;
			
		}else{
			node.previous.next = node.next;
			node.next.previous =  node.previous;
		}
		
		node.destroy();
		size--;
	}
	
	
	public int size(){
		return size;
	}
	
	
	public Node<E> first(){
		return head;
	}
	
	
	public String toString(){
		String output = "[";
		Node<E> tmp = head;
		
		while(tmp != null){
			output += tmp.toString();
			if(tmp.next != null)
				output += ", ";
			tmp = tmp.next;
		}
		output += "]";
		return output;
	}
	
	
	public NodeIterator<E> iterator(){
		
		return new NodeIterator<E>() {
			private Node<E> position = head;
			
			
			public E next(){
				Node<E> node = position;
				position = position.next;
				return node.getData();
			}
			
			
			public boolean hasNext(){
				return position != null;
			}
			
			
			public NodeIterator<E> concatenate(NodeIterator<E> secondIter){
				DoublyLinkedList<E> newList = new DoublyLinkedList<E>();
				while(this.hasNext())
					newList.add(this.next());
				while(secondIter.hasNext())
					newList.add(secondIter.next());
				return newList.iterator();
			}
			
			public int size(){
				return DoublyLinkedList.this.size();
			}
			
			
			public String toString(){
				return DoublyLinkedList.this.toString();
			}
		};
	}
}
