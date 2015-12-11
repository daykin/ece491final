package edu.msu.ece.proofOfWork;


//wrapper for a response with a challenge
public class ChallengeResponse {
	private String resp;
	private Challenge ch;
	
	public ChallengeResponse(String rsp, Challenge c){
		this.resp = rsp;
		if (c != null){
			this.ch = c;
		}
		else{ 
			throw new UnsupportedOperationException("cut it out!");
		}
	}

	public String getResp() {
		return resp;
	}

	public void setResp(String resp) {
		this.resp = resp;
	}

	public Challenge getCh() {
		return ch;
	}

	public void setCh(Challenge ch) {
		this.ch = ch;
	}
	
}
