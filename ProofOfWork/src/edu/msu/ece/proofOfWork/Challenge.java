package edu.msu.ece.proofOfWork;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*Challenges are not salted, so pre-computation is possible for the same message with low difficulty.
*For our purposes, this is fine.
*/
public class Challenge {
	private Integer difficulty;
	private String message;
	private Long nonce;
	private byte[] solution;
	
	public Challenge(int diff, String message){
		this.difficulty = diff;
		this.nonce = new Long(0);
		this.message = message;
	}
	
	public void setSolution(byte[] soln){
		this.solution = soln;
	}
	
	public byte[] getSolution(){
		return this.solution;
	}
	
	public long getNonce(){
		return this.nonce;
	}
	
	public int getDifficulty(){
		return this.difficulty;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public static String toBinaryString(byte[] bytes){
		String stringRep = bytesToHex(bytes);
	    String b =  (new BigInteger(stringRep, 16).toString(2));
	    return String.format("%256s",b).replace(' ', '0');		
	}
    
	public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
	
	public boolean checkHash(byte[] att){
		if (att.length ==32){
			String binString = toBinaryString(att);
			for(int i = 0;i<this.difficulty;i++){
				if (binString.charAt(i) != '0' ){return false;}
				}
			return true;
		}
		else{return false;}
	}
	
	public void solve() throws NoSuchAlgorithmException{
		byte[] attempt = ((message+nonce.toString()).getBytes());
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		attempt = digest.digest(attempt);
		while (!(checkHash(attempt))){ //check if attempt meets difficulty requirements
			digest.reset();//clear digest
			nonce = nonce+1; //increment nonce
			attempt = digest.digest((message + nonce.toString()).getBytes());//digest
		}
		this.solution = ((message+nonce.toString()).getBytes());
	}
}
