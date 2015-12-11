package edu.msu.ece.proofOfWork;

public class Main {

	public static void main(String[] args) {
		Client alice = new Client("Alice");
		Server bob = new Server("Bob");
		alice.connect("put me through to the White House, Bob.", bob);
		Client mallory = new Client("Mallory");
		for (int i=0;i<20;i++){
			mallory.connect("hahaha, I will flood your server.", bob);
		}
		 
	}

}
