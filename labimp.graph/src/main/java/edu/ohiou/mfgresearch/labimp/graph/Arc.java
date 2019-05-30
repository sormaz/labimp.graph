/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.graph;

/**
 * @author sormaz
 *
 */
public abstract class Arc implements Drawable {
	
	protected Object userObject;

	/**
	 * 
	 */
	public Arc() {
		// TODO Auto-generated constructor stub
	}
	
	abstract public Node getOtherNode(Node n);
	abstract public Node [] getNodes();
	abstract public Node getParentNode();
	abstract public Node getChildNode();

	public Object getObject() {
		return userObject;
	}
}
