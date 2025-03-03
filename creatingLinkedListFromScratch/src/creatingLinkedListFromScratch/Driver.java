package creatingLinkedListFromScratch;

public class Driver {

	public static void main(String[] args) {
		MyLinkedList l = new MyLinkedList();
		l.addLast("A");
		l.addLast("B");
		l.addLast("C");
		System.out.println("Done");
		
		System.out.println(l.get(0));
		System.out.println(l.get(1));
		System.out.println(l.get(2));
		System.out.println(l.get(3));
	}

}
