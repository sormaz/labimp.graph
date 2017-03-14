package edu.ohiou.mfgresearch.labimp.graph;

import org.junit.Test;

import edu.ohiou.mfgresearch.labimp.graph.algorithm.SamplePPN;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    
	@Test
	public void testDijkstraManjit1(){
		
		
		
	}
	
	@Test
	public void testFeatureMachineAllocation(){
		
		SamplePPN ppn = new SamplePPN(5, 8);
		ppn.generateFeatureMachineAllocation(70, 10, 50);
		ppn.printFeatureMachineAllcation();	
		
	}
	
	@Test
	public void testPPN1(){
		
//		double[][] a = new double[][]{{12,0,20,0},
//								{0,14,0,7},
//								{20,15,0,25}};
		SamplePPN ppn = new SamplePPN(3, 1);
//		ppn.setFeatureMachineAlloc(a);
		ppn.generateFeatureMachineAllocation(0, 50, 10);
		ppn.printFeatureMachineAllcation();	
		ppn.generatePPN();
		Graph ppng = ppn.getPpn();
		ppng.printout();
	}
	
}
