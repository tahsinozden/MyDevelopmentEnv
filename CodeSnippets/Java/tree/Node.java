package tree;

import java.util.ArrayDeque;
import java.util.Queue;

public class Node {

	private Node left, right;
	private int data;
	private Node node;
	
	public Node(){}
	public Node(int n){
		data = n;
	}
	
	public void insert(int number){
		if (node == null){
			node = new Node(number);
			return;
		}
		
		if (number <= node.data){
			if (node.left == null) {
				node.left = new Node(number);
			}
			else {
				node.left.insert(number);
			}
		}
		else {
			if (node.right == null) {
				node.right = new Node(number);
			}
			else {
				node.right.insert(number);
			}			
		}

	}
	
	private void printInOrder(Node nd){
		if (nd == null){
			return;
		}
		
		if (nd.left != null){
//			System.out.println(node.left.data);
			nd.left.printInOrder(nd.left);
		}
		System.out.println(nd.data);
		if (nd.right != null){
//			System.out.println(node.right.data);
			nd.right.printInOrder(nd.right);
		}
	}
	
	public void printByLevel(){
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(node);
		
		while(queue.size() > 0){
			Node n = queue.poll();
			System.out.println(n.data);
			
			if (n.left != null){
				queue.add(n.left);
			}
			if (n.right != null){
				queue.add(n.right);
			}
		}

	}
	
	public void print(){
		printInOrder(node);
	}
	
}
