package com.gamsnd.object;

import java.util.ArrayList;

public class Connection {
	private int requestID;
	private ArrayList<Gen> wConnection;
	private ArrayList<ArrayList<Gen>> bConnection;

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public ArrayList<Gen> getwConnection() {
		return wConnection;
	}

	public void setwConnection(ArrayList<Gen> wConnection) {
		this.wConnection = wConnection;
	}

	public ArrayList<ArrayList<Gen>> getbConnection() {
		return bConnection;
	}

	public void setbConnection(ArrayList<ArrayList<Gen>> bConnection) {
		this.bConnection = bConnection;
	}

	public Connection(int requestID, ArrayList<Gen> wConnection,
			ArrayList<ArrayList<Gen>> bConnection) {
		super();
		this.requestID = requestID;
		this.wConnection = wConnection;
		this.bConnection = bConnection;
	}

}
