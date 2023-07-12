package telran.util;

import java.util.ArrayList;

public class ListValidator {

	static class Node {
		public int value;
		public Node next;

		Node(int num) {
			value = num;
		}
	}

	public Node head;

	public ListValidator() {
		Node n0 = new Node(0);
		Node n1 = new Node(1);
		Node n2 = new Node(2);
		Node n3 = new Node(3);
		Node n4 = new Node(4);
		Node n5 = new Node(5);
		Node n6 = new Node(6);
		Node n7 = new Node(7);
		Node n8 = new Node(8);
		Node n9 = new Node(9);

		n0.next = n1;
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;
		n6.next = n7;
		n7.next = n8;
		n8.next = n9;
		n9.next = n3;

		head = n0;

	}

	public static int indexOfCircular(Node head) {
		int counter = 0;
		Node current = head;
		Node next;
		ArrayList<Node> savedOrder = new ArrayList<Node>();

		while (current.next != null) {
			counter++;
			savedOrder.add(current.next);
			next = current.next;
			current.next = null;
			current = next;
		}

		restoreNodes(head, savedOrder);
		current = head;
		
		for (int i = 0; i < counter; i++) {
			current = current.next;
		}

		return current.next == null ? -1 : counter - 1;

	}

	private static void restoreNodes(Node head, ArrayList<Node> savedOrder) {
		Node current = head;
		
		for (Node link : savedOrder) {
			current.next = link;
			current = link;
		}
	}

	public static void main(String[] args) {
		ListValidator test = new ListValidator();
		System.out.println(indexOfCircular(test.head));
	}
}
