/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.graph;

/**
 * @author sormaz
 *
 */
public class UndirectedArc extends Arc{
	Node [] nodes = new Node [2];;

	/**
	 * 
	 */
	public UndirectedArc(Node n1, Node n2, Object o) {
		nodes [0] = n1;
		nodes [1] = n2;
		userObject = o;
	}
	
	public UndirectedArc(Node n1, Node n2) {
		this (n1, n2,null);
	}
	
	public String toString () {
		if (userObject == null) {
			return "UndArc<" + nodes[0].getUserObject() + 
					"-" + nodes[1].getUserObject() + ">";
		}
		else {
			return userObject.toString();
		}
	}
	/**
	 * 
	 */
	public UndirectedArc(Node [] ns) {
		if (ns.length == 2 ) {
		nodes [0] = ns[0];
		nodes [1] = ns[1];
		}
	}

	public Node getOtherNode(Node node) {
		if (node == nodes[0]) {
			return nodes[1];
		}
		else {
			return nodes[0];
		}
	}
	
	
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Node[] getNodes() {
		return nodes;
	}

	@Override
	public Node getParentNode() {
		throw new UnsupportedOperationException("UnirectedArc class does not implement getParentNode() method.");
	}

	@Override
	public Node getChildNode() {
		throw new UnsupportedOperationException("UnirectedArc class does not implement getChildNode() method.");
	}

}
