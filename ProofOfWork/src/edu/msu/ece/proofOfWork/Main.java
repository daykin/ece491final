package edu.msu.ece.proofOfWork;

public class Main {

	public static void flood(Client c, int numRequests, Server srv){
		final Long begin = new Long(System.currentTimeMillis());
		Long timeTaken = new Long(begin);
		for (int i=1;i<=numRequests;i++){
			c.connect("hahahaha, I will flood your server.", srv);
			timeTaken = System.currentTimeMillis()-begin;
			System.out.println(i + " requests took " + timeTaken + " ms.");	
		}
		
		
		
	}
	
	public static void main(String[] args) {
		Client alice = new Client("Alice");
		Server bob = new Server("Bob");
		alice.connect("put me through to the White House, Bob.", bob);
		Client mallory = new Client("Mallory");
		flood(mallory,20,bob);
		
	}

}
