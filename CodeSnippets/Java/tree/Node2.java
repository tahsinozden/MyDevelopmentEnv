package tree;

import java.util.ArrayDeque;
import java.util.Queue;

public class Node2 {

	private Node2 left, right;
	private int data;
	
	public Node2(int n){
		data = n;
	}
	
	public void insert(int number){
		if (number <= data){
			if (left == null) {
				left = new Node2(number);
			}
			else {
				left.insert(number);
			}
		}
		else {
			if (right == null) {
				right = new Node2(number);
			}
			else {
				right.insert(number);
			}			
		}

	}
	
	public void printInOrder(){
		if (left != null){
			left.printInOrder();
		}
		System.out.println(data);
		if (right != null){
			right.printInOrder();
		}
	}
	
	public void printPreOrder(){
		System.out.println(data);
		if (left != null){
			left.printInOrder();
		}
		if (right != null){
			right.printInOrder();
		}
	}
	
	public void printPostOrder(){
		if (left != null){
			left.printInOrder();
		}
		if (right != null){
			right.printInOrder();
		}
		System.out.println(data);
	}
	
//	public void printByLevel(){
//		Queue<Node2> queue = new ArrayDeque<>();
//		System.out.println(data);
//		do {
//			if (left != null){
//				queue.add(left);
//			}
//			if (right != null){
//				queue.add(right);
//			}
//			
//			Node2 n = queue.poll();
//			System.out.println(n.data);
//			
//		} while(queue.size() > 0);
//	}
	
	public boolean contains(int number){
		if (data == number){
			return true;
		}
		
		if (number <= data){
			if (left != null){
				return left.contains(number);
			}
			else {
				return false;
			}
		}
		else {
			if (right != null){
				return right.contains(number);
			}
			else {
				return false;
			}
		}

	}
	
}
