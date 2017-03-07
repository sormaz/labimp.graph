package edu.ohiou.mfgresearch.labimp.graph;

import org.jgrapht.util.FibonacciHeap;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    
	/**
	 * Dijkstra's algorithm using Fibonacci heap.
	 * The implementation of Nathan Fiedler is used from JGraphT project
	 * mplementing Dijkstra with Fibonacci heap gives best performance so far. -Ahuja
	 */
	@org.junit.Test
	public void DijkstraFiboHeap(){
		
		FibonacciHeap<Double> heap = new FibonacciHeap<Double>();
		
//		heap.insert(node, key);
		
	}
	
	
	
	
}
