package edu.msu.ece.proofOfWork;


import java.util.HashMap;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

//Server that handles challenges and requests.
public class Server {
	private String id;
	private static HashMap<String, Integer> clientAttempts = new HashMap<>();
	private HashMap<String, Integer> clientFailedAttempts = new HashMap<>();
	private ArrayList<String> blacklist = new ArrayList<>();
	
	public Server(String id){
		this.id = id;
	}
	
	public static Challenge generateChallenge(Client c, String msg){
		if (!(clientAttempts.containsKey(c.id))){
			clientAttempts.put(c.id,1);
		}
			
		return new Challenge(clientAttempts.get(c.id),msg);
		
	}
	public static String toBinaryString(byte[] bytes){
		String stringRep = Challenge.bytesToHex(bytes);
	    String b =  (new BigInteger(stringRep, 16).toString(2));
	    return String.format("%256s",b).replace(' ', '0');		
	}
	
	public boolean checkHash(byte[] att, int difficulty){
		if (att.length == 32){
			String binString = toBinaryString(att);
			for(int i = 0;i<difficulty;i++){
				if (binString.charAt(i) != '0' ){return false;}
				}
			return true;
		}
		else{return false;}
	}
	private boolean verifyChallenge(Client c, Challenge ch) throws NoSuchAlgorithmException{// recalculate difficulty so client can't spoof
		MessageDigest digest = MessageDigest.getInstance("SHA-256"); 
		if(clientAttempts.get(c.id)<=10000){
		digest.reset();
		int diff = clientAttempts.get(c.id);
		return checkHash(digest.digest(ch.getSolution()),diff);
		}
		return false;
	}
	
	public ChallengeResponse handleInitialRequest(Client c, String message){
		if (!(clientFailedAttempts.containsKey(c.id))){
			clientFailedAttempts.put(c.id,0);
		}
		if (clientFailedAttempts.get(c.id) < 10000){
		return new ChallengeResponse(("hello, "+c.id +"! I'm "+this.id+"."),generateChallenge(c,message));
		}
		else if(clientFailedAttempts.get(c.id) == 10000){
		return new ChallengeResponse("no more attempts allowed after this. quit spamming!",generateChallenge(c,message));
		}
		else{return null;}
	}
	
	public void PoWResponse(Client c, Challenge ch) throws NoSuchAlgorithmException{
		boolean ok = verifyChallenge(c,ch);
		int att = clientAttempts.get(c.id);
		att = att+1;
		clientAttempts.put(c.id,att);
		if(ok==true){
			att = att+1;		
		}
		else{
			System.out.println("request denied.");
			int fails = clientFailedAttempts.get(c.id);
			fails = fails+1;
			if (fails >= 200){blacklist.add(c.id);}
			clientFailedAttempts.put(c.id, fails);
		}
		
	}
	
}
