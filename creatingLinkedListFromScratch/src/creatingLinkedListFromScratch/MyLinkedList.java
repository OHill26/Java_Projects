package creatingLinkedListFromScratch;

public class MyLinkedList {
	private Node head;
	private Node tail;
	
	MyLinkedList() {
		head = null;
	}
	//only adds to the begining of the list
	public void addFirst(String newElement) {
		Node newNode = new Node(newElement);
		newNode.next = head;
		head = newNode;
	}
	
	public void addLast(String newElement) {
		Node newNode = new Node(newElement);
		if (head == null) {
			head = newNode;
		}
		else {
			Node current = head;
			while (current.next != null) {
				current = current.next;
				
			}
			current.next = newNode;
		}
	}
	
	public String get(int position) {
		Node myPosition = head;
		if (head == null) {
			throw new IndexOutOfBoundsException();
		}
		
		for (int i = 0; i < position; i++) {
			if (myPosition.next == null) {
				throw new IndexOutOfBoundsException();
			
		}
		else {
			myPosition = myPosition.next;
		}
		}
		return myPosition.element;
	}
	
	class Node{
		String element;
		Node next;
		
		public Node(String newElement) {
			element = newElement;
			next = null;
		}
	}
	
	
}
