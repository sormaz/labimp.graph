package edu.ohiou.mfgresearch.labimp.graph;

import java.util.*;

public class Node implements Comparable<Node> {

	// private Collection<UndirectedArc> undirArcs = new
	// ArrayList<UndirectedArc>();
	// private ArrayList<DirectedArc> parents = new ArrayList<DirectedArc>();
	// private ArrayList<DirectedArc> children = new ArrayList<DirectedArc>();
	private Map<Node, UndirectedArc> undirArcMap = new HashMap();
	private Map<Node, DirectedArc> parentMap = new HashMap();
	private Map<Node, DirectedArc> childrenMap = new HashMap();

	public double temporaryLabel = Double.POSITIVE_INFINITY;
	public double permanentLabel;

	public Node previous;
	public double shortestPath = Double.POSITIVE_INFINITY;
	public double backwardShortestPath = Double.POSITIVE_INFINITY;
	private HashSet<Node> nodeValues = new HashSet<Node>();// set with node
															// values(distances)

	private Object userObject;

	Graph graph;

	public Node(Object o) {
		userObject = o;
	}

	public Object getUserObject() {
		return userObject;
	}

	public DirectedArc addDirectedArc(Node node) throws AlreadyMemberException {
		return addDirectedArc(node, 0.0);
	}

	public DirectedArc addDirectedArc(Node n, double val) throws AlreadyMemberException {
		if (n == null) {
			System.out.println("n is null");
		}
		// check if connected first
		if (!childrenMap.containsKey(n)) {
			DirectedArc a = new DirectedArc(this, n, val);

			childrenMap.put(n, a);

			n.parentMap.put(this, a);
			return a;
		} else {
			throw new AlreadyMemberException("The directed arc between " + this + " and " + n + " exists!");
		}
	}

	public DirectedArc getDirectedArc(Node n) {
		return (DirectedArc) childrenMap.get(n);
	}

	public DirectedArc getDirectedArc1(Node n) {
		return (DirectedArc) parentMap.get(n);
	}

	public ArrayList<Arc> getArcs() {
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		arcs.addAll(undirArcMap.values());
		arcs.addAll(parentMap.values());
		arcs.addAll(childrenMap.values());
		return arcs;
	}

	public String printArcs() {
		StringBuffer result = new StringBuffer();
		result.append(" Arcs{");
		for (Arc a : undirArcMap.values()) {
			result.append(a);
			result.append(" ");
		}
		result.append("}");
		result.append(", Parents{");
		for (Arc a : parentMap.values()) {
			result.append(a);
			result.append(" ");
		}
		result.append("}");
		result.append(", Children{");
		for (Arc a : childrenMap.values()) {
			result.append(a);
			result.append(" ");
		}
		result.append("}");
		return result.toString();
	}

	public String toLongString() {
		return "Node<" + userObject + ">, undir arcs:" + undirArcMap.keySet().size() + ", parents:"
				+ parentMap.keySet().size() + ", children:" + childrenMap.keySet().size();
	}

	public String toString() {
		return "Node<" + userObject + ">"; // + printArcs();
	}

	public UndirectedArc addUndirectedArc(Node n) throws AlreadyMemberException {

		if (!undirArcMap.containsKey(n)) {
			UndirectedArc a = new UndirectedArc(this, n);
			undirArcMap.put(n, a);
			n.undirArcMap.put(this, a);
			return a;
		} else {
			throw new AlreadyMemberException("The undirected arc between " + this + " and " + n + " exists!");
		}
	}

	public Collection<DirectedArc> getParents() {

		return parentMap.values();
	}

	public Collection<DirectedArc> getChildren() {
		return childrenMap.values();
	}

	public void remove() {
		for (UndirectedArc a : undirArcMap.values()) {
			a.getOtherNode(this).removeArc(this);
		}
		undirArcMap.clear();
		for (DirectedArc a : parentMap.values()) {
			a.getOtherNode(this).removeChildArc(this);
		}
		parentMap.clear();
		for (DirectedArc a : childrenMap.values()) {
			a.getOtherNode(this).removeParentArc(this);
		}
		childrenMap.clear();
	}

	public Arc removeArc(Node a) {
		return undirArcMap.remove(a);

	}

	public Arc removeChildArc(Node a) {
		return childrenMap.remove(a);

	}

	public Arc removeParentArc(Node a) {
		return parentMap.remove(a);

	}

	public boolean isChild(Node n) {

		return childrenMap.containsKey(n);
	}

	public void addChildArc(DirectedArc directedArc) {
		// children.add(directedArc);
		childrenMap.put(directedArc.getChildNode(), directedArc);

	}

	public void addParentArc(DirectedArc directedArc) {
		// parents.add(directedArc);
		parentMap.put(directedArc.getParentNode(), directedArc);

	}

	public boolean isConnected(Node node) {

		return undirArcMap.keySet().contains(node);
	}

	public double getPermanentLabel() {
		return permanentLabel;
	}

	public void setPermanentLabel() {
		permanentLabel = temporaryLabel;
	}

	public boolean isPermanent() {
		return !(Double.isInfinite(shortestPath) || Double.isInfinite(backwardShortestPath));
	}

	/**
	 * Compute new shortest distance for neighboring nodes and update if a
	 * shorter distance is found.
	 * 
	 * @param Node
	 *            node
	 */
	public void updateChildrenNodes() {

		for (Node node : getNeighbors())

		// TODO Sormaz no need to test ***
		{
			// if (isPermanent(node)){ continue;}
			DirectedArc arc = getDirectedArc(node);

			double shortP = getShortestPath() + arc.value;
			System.out.println("Shortest path is" + shortP);

			if (shortP < node.getShortestPath()) {
				node.setShortestPath(shortP);// assign new shortest distance and
												// set in setSdash
			}
		}
	}

	public void updateBackwardChildrenNodes() {

		for (Node node : getNeighbors())

		// TODO Sormaz no need to test ***
		{
			// if (isPermanent(node)){ continue;}
			DirectedArc arc = getDirectedArc(node);

			double shortP = getBackwardShortestPath() + arc.value;
			System.out.println("BackwardShortest path is" + shortP);

			if (shortP < node.getBackwardShortestPath()) {
				node.setBackwardShortestPath(shortP);// assign new shortest
														// distance and set in
														// setSdash
			}
		}
	}

	private double getDistance(Node node1, Node node2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getShortestPath() {
		return permanentLabel;
	}

	/**
	 * Set the new shortest distance for the given node,
	 * 
	 * @param Node
	 *            node to set
	 * @param distance
	 *            new shortest distance value
	 */
	private void setShortestPath(double distance) {

		permanentLabel = distance;
		// graph.setSdash.remove(node);
		// shortestPath.put(node, distance);//Update the shortest distance.
		// graph.setSdash.add(node);// adding the updated distance according to
		// the new shortest distance found
	}

	public double getBackwardShortestPath() {
		return permanentLabel;
	}

	private void setBackwardShortestPath(double distance) {

		permanentLabel = distance;

	}

	public Collection<Node> getNeighbors() {
		// Collection<Node> neighbors = new ArrayList<Node>();
		// for(DirectedArc arc: getChildren())
		// {
		// Node node = null;
		// if ( arc.getChildNode() == node)
		// {
		// neighbors.add(node);
		// }
		// }
		return childrenMap.keySet();
	}

	public Collection<Node> getNeighbors1() {
		return parentMap.keySet();
	}

	public void setNodeValue() {

	}

	public int compareTo1(Node n1) {

		return Double.compare(backwardShortestPath, n1.backwardShortestPath);

	}

	public int compareTo(Node n1) {
		return Double.compare(shortestPath, n1.shortestPath);
	}

	@Override
	public int hashCode() {
		Random rand = new Random();
		// TODO Auto-generated method stub
		return userObject.hashCode();
	}
	
	

}

// class NodeComparator implements Comparator<Node> {
//
// public int compare(Node n1, Node n2) {
// double shortestDistance1 = n1.getShortestPath();
// double shortestDistance2 = n2.getShortestPath();
// int sign = Double.compare(shortestDistance1, shortestDistance2);
// if (sign != 0) {
// return sign;
// }
// return n1.toString().compareTo(n2.toString());
// }
//
// }
