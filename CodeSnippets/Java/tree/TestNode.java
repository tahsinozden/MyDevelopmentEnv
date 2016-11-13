package tree;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestNode {

	Node2 node2;
	Node node1;
	@Before
	public void setUp() throws Exception {
		node2 = new Node2(12);
		node2.insert(34);
		node2.insert(4);
		node2.insert(38);
		node2.insert(1);
		node2.insert(15);
		
		
		node1 = new Node();
		node1.insert(34);
		node1.insert(4);
		node1.insert(38);
		node1.insert(1);
		node1.insert(15);
		
	}

	@Test
//	@Ignore
	public void testNode() {
		System.out.println("===== testNode");
		Node tree = new Node();
		tree.insert(34);
		tree.insert(4);
		tree.insert(38);
		tree.insert(1);
		tree.insert(15);
		
		tree.print();
	}
	
	@Test
	public void testNodePrintLevel(){
		System.out.println("===== testNodePrintLevel");
		node1.printByLevel();
	}
	
	@Test
	public void testNode2() {
		System.out.println("===== testNode2");
		Node2 tree = new Node2(12);
		tree.insert(34);
		tree.insert(4);
		tree.insert(38);
		tree.insert(1);
		tree.insert(15);
		
		tree.printInOrder();
	}
	
	@Test
	public void testContains(){
		System.out.println(node2.contains(23));
		System.out.println(node2.contains(15));
	}

	@Test
	public void testInOrderPrint(){
		System.out.println("===== testInOrderPrint");
		node2.printInOrder();
	}
	
	@Test
	public void testPreOrderPrint(){
		System.out.println("===== testPreOrderPrint");
		node2.printPreOrder();
	}
	
	@Test
	public void testPostOrderPrint(){
		System.out.println("===== testPostOrderPrint");
		node2.printPostOrder();
	}

}
