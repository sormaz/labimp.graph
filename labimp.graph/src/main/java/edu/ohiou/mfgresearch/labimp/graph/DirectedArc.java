/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.graph;

/**
 * @author sormaz
 *
 */
public class DirectedArc extends Arc {

	Node parent;
	Node child;
	double value;
	
	/**
	 * 
	 */
	public DirectedArc(Node parent, Node child, double val, Object user) {
		this.parent = parent;	
		this.child = child;
		parent.addChildArc(this);
		child.addParentArc(this);
		value = val;
		userObject = user;
	}
	public DirectedArc(Node parent, Node child, double val) {
		this(parent, child, val, null);
	}
	
	
	
	public DirectedArc(Node parent, Node child) {
		this (parent, child, 0.0);
	}
	
	public String toString () {
		if (userObject == null) {
		return "DArc[" + parent.getUserObject() + "=>" + child.getUserObject() + "]"+ value ;
		}
		else {
			return userObject.toString();
		}
	}
	
	
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	public Node getChildNode() {		
		return child;
	}
	
	public Node getParentNode() {		
		return parent;
	}
	
	public Node getOtherNode (Node n) {
		return n == parent ? child : parent;
	}

	@Override
	public Node[] getNodes() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("DirectedArc class does not implement getNodes() method");
	}


	public double getValue() {
		return value;
	}


	public void setValue(double value) {
		this.value = value;
	}
	
	

}
