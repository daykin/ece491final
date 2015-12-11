package edu.msu.ece.proofOfWork;

import java.security.NoSuchAlgorithmException;

public class Client {
	public String id;
	
	public Client(String id){
		this.id = id;
	}
	
	public void connect(String message, Server srv){
		ChallengeResponse r = srv.handleInitialRequest(this,message);
		if(r != null){
			handleChallenge(r, srv);
		}
	
	}
	
	public void handleChallenge (ChallengeResponse resp, Server srv){
		System.out.println(resp.getResp());
		try {
			Challenge challenge = resp.getCh();
			challenge.solve();
			srv.PoWResponse(this, challenge);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
