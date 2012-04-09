import java.util.ArrayList;


public class Request {
	int s;
	int t;
	ArrayList<Integer> wk = new ArrayList<Integer>();
	ArrayList<Integer> bk = new ArrayList<Integer>();
	
	public ArrayList<Integer> getWk() {
		return wk;
	}
	
	public ArrayList<Integer> getBk() {
		return bk;
	}

	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public void setWk(ArrayList<Integer> wk) {
		this.wk = wk;
	}

	public void setBk(ArrayList<Integer> bk) {
		this.bk = bk;
	}
}
