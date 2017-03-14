package edu.ohiou.mfgresearch.labimp.graph.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.ohiou.mfgresearch.labimp.graph.Graph;
import edu.ohiou.mfgresearch.labimp.graph.Node;
import edu.ufl.cise.bsmock.graph.util.Path;

public class GraphAlgorithm {
	
	Graph graph;
	edu.ufl.cise.bsmock.graph.Graph algoGraph = new edu.ufl.cise.bsmock.graph.Graph();
	List<Path> paths = new LinkedList<Path>();
	
	public GraphAlgorithm(Graph g) {
		graph = g;
		convertGraph();
	}

	private void convertGraph() {
		
		//copy nodes
		graph.getNodeStream()
			 .map(n->{
				return new edu.ufl.cise.bsmock.graph.Node(n.toString()); 
			 })
			 .forEach(n->{
				 algoGraph.addNode(n);
			 });
		
		//copy all directed edges
		graph.findArcs()
			 .stream()
			 .map(a->{
				 return new edu.ufl.cise.bsmock.graph.Edge(a.getParentNode().toString(), a.getChildNode().toString(), a.getValue());
			 })
			 .forEach(a->{
				 algoGraph.addEdge(a);
			 });
	}
	
}
