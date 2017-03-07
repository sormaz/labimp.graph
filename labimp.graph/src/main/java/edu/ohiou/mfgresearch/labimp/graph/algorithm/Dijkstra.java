package edu.ohiou.mfgresearch.labimp.graph.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

import edu.ohiou.mfgresearch.labimp.graph.Graph;
import edu.ohiou.mfgresearch.labimp.graph.Node;

public class Dijkstra {
	
	Graph graph;
	Map<Node, Node> path = new HashMap<Node, Node>();

	public Dijkstra(Graph g) {
		graph = g;
	}
	
	public void runForward(Node source, Node sink){
		
		double value;
		
		//create the heap
		FibonacciHeap<Node> heap = new FibonacciHeap<Node>();
		
		//initialize root node and label other nodes as infinity
//		graph.getNodeStream()
//			 .forEach(n->{
//				 n.temporaryLabel = Double.MAX_VALUE;
//			 });		
		//label source node with 0 (at source node no distance is yet covered) 
		source.temporaryLabel = 0;
		//mark no preceding node of source
		path.put(source, null);
		//insert the source to the heap
		heap.insert(new FibonacciHeapNode<Node>(source), source.temporaryLabel);
		
		//loop till the heap is empty
		do{
			
			//pop the minimum node from the heap 
			Node nextNode = heap.removeMin().getData();
			
			//label every neighbor of this nextNode
			for(Node n: nextNode.getNeighbors()){
//				value = nextNode.temporaryLabel + nextNode.getDirectedArc(n).value;
			}
			
		}while(heap.isEmpty());
		
		
	}
	

}
