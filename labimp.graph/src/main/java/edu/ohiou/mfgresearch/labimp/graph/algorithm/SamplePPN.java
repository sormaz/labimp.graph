package edu.ohiou.mfgresearch.labimp.graph.algorithm;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import edu.ohiou.mfgresearch.labimp.graph.AlreadyMemberException;
import edu.ohiou.mfgresearch.labimp.graph.Graph;
import edu.ohiou.mfgresearch.labimp.graph.Node;
import edu.ohiou.mfgresearch.labimp.graph.NotMemberException;

public class SamplePPN {
	
	int numFeature;
	int numMachine;
	Graph fpn;
	Graph ppn;
	double[][] featureMachineAlloc;

	/**
	 * Set the number of features and machines the ppn will have
	 * @param numFeature
	 * @param numMachine
	 */
	public SamplePPN(int numFeature, int numMachine) {
		this.numFeature = numFeature;
		this.numMachine = numMachine;
	}
	
	public void setFeatureMachineAlloc(double[][] featureMachineAlloc) {
		this.featureMachineAlloc = featureMachineAlloc;
	}
	
	/**
	 * generate random feature machine allocation matrix
	 * @param percentageFree percentage of total machine should NOT be allocated to each feature
	 * @param maxCost maximum machine cost
	 * @param minCost minimum machine cost
	 */
	public void generateFeatureMachineAllocation(int percentageFree, int maxCost, int minCost){
		
		//random generator
		Random rand = new Random();
		
		//create and initialize the machine allocation matrix
		featureMachineAlloc = new double[numFeature][numMachine];
		for(int i = 0; i < numFeature; i++){
			for(int j = 0; j < numMachine; j++){
				featureMachineAlloc[i][j] = 0.0;
			}
		}
		
		//get the max percentage in 10s which is less than percentageFree
		percentageFree = percentageFree - (percentageFree % 10);
		
		//number of available machine for each feature
		int m = (int) Math.floor(numMachine - (numMachine * percentageFree / 100));
		for(int i = 0; i < numFeature; i++){
			for(int k = 0; k < m; k++){
				int j = rand.nextInt(numMachine);
				featureMachineAlloc[i][j] = rand.nextInt(maxCost)+minCost;
			}
			m = (int) Math.floor(numMachine - (numMachine * percentageFree / 100));
		}
		
	}
	
	public void generatePPN(){
		
		Set<Integer> cf = new HashSet<Integer>();
		Stack<State> s = new Stack<State>();
		Node start = new Node("S");
		
		//initialize the PPN
		ppn = new Graph();
		ppn.addNode(start);
		
//		//add next available features to CF
//		cf.addAll(getNextFeatures(-1));
//		
		for(int i=0; i<numFeature; i++){
			cf.add(i);
		}
		
		//initializing first state
		State si = new State(new HashSet<Integer[]>(), new HashSet<Integer>(), new HashSet<Integer>(), cf);
		si.allocation = start;
		s.push(si);
		
		while(!s.isEmpty()){
			//depth first search
			State currentState =  s.pop();
			System.out.println("Current State := "+ currentState.toString());
			
			//generate all possible next states
			Set<State> nextStates = getNextStates(currentState);
			
			//add all next States to stack
			for(State n:nextStates){
				s.push(n);
			}
		}
		
	}
	
	/**
	 * get the next feature
	 * @param feature
	 * @return
	 */
	private Set<State> getNextStates(State s){
		//assuming equal precedence
		Set<State> nss = new HashSet<State>();
		Set<Integer> cf = s.cf;
		
		//add all machine allocations possible for every feature in cf
		for(int i:cf){
			for(int j=0;j<numMachine; j++){
				if(featureMachineAlloc[i][j] > 0){
					State ns = s.getNextState(new Integer[]{j,i});
					Node n = addNode2PPN(s.allocation, new Node(ns.toString()));
					n.permanentLabel = featureMachineAlloc[i][j];
					ns.allocation = n;
					nss.add(ns);
				}
			}
		}
		return nss;
	}
	
	
	private Node addNode2PPN(Node s, Node n){
		ppn.addNode(n);
		try {
			ppn.addDirectedArc(s.getUserObject(), n.getUserObject());
//			ppn.addDirectedArc(s, n, <machine transfer cost>);
		} catch (AlreadyMemberException | NotMemberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n;
	}
	
	
	public void printFeatureMachineAllcation(){
		System.out.print("\t");
		for(int j = 0; j < numMachine; j++){
			System.out.print("M"+(j+1)+"\t");
		}
		System.out.print("\n");
		for(int i = 0; i < numFeature; i++){
			System.out.print("F"+(i+1)+"\t");
			for(int j = 0; j < numMachine; j++){
				System.out.print(featureMachineAlloc[i][j]+"\t");
			}
			System.out.print("\n");
		}
	}
	
	public Graph getPpn() {
		return ppn;
	}

	class State{
		Node allocation;
		int machine;
		int feature;
		Set<Integer[]> p;					   //already planned state
		Set<Integer> uf;
		Set<Integer> unf;
		Set<Integer> cf;
		
		
		public State(Set<Integer[]> p, Set<Integer> uf, Set<Integer> unf, Set<Integer> cf) {
			super();
			this.p = p;
			this.uf = uf;
			this.unf = unf;
			this.cf = cf;
		}
		
		public State getNextState(Integer allocation[]){
			State ns =  new State(new HashSet<Integer[]>(), new HashSet<Integer>(), new HashSet<Integer>(), new HashSet<Integer>());
			ns.p.addAll(p);
			ns.p.add(allocation);
			ns.uf.addAll(uf);
			ns.uf.add(allocation[1]);
			ns.unf.addAll(cf);
			ns.unf.remove(allocation[1]);
			ns.cf.addAll(ns.unf);
			ns.machine = allocation[0];
			ns.feature = allocation[1];
			//next features are added to cf here, not added here assuming equal precedence
//			ns.allocation = addNode2PPN(this.allocation, allocation[0], allocation[1]);
			return ns;
		}

		/**
		 * 
		 */
		@Override
		public String toString() {
			String s ;
			s = "["+ machine+","+feature;
			s = s + "|p:";
			for(Integer[] i:p){
				s = s + "<" + (i[0]+1) + ","+ (i[1]+1) + ">,";
			}
			s = s.substring(0, s.length()-1);
			s = s + "|uf:";
			for(int i:uf){
				s = s + i + ",";
			}
			s = s.substring(0, s.length()-1);
			s = s + "|unf:";
			for(int i:unf){
				s = s + i + ",";
			}
			s = s.substring(0, s.length()-1);
			s = s + "|cf:";
			for(int i:cf){
				s = s + i + ",";
			}
			s = s.substring(0, s.length()-1);
//			s = s + "|p:";
//			for(Integer[] i:p){
//				s = s + i[0] + i[1] + ",";
//			}
			s  = s + "]";
			return s;
		}
		
		
		
	}
	
}
