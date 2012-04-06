
public class link {
	public int e;
	public int b;
	public int s;
	public int t;
	public int lv;
	public int bl[] = new int[10];
	public int cl[] = new int[10];
	
	public void copyLink(link E) {
		this.e = E.e;
		this.b = E.b;
		this.s = E.s;
		this.t = E.t;
		this.lv = E.lv;
		for(int i = 0; i < E.lv; i++) {
			this.bl[i] = E.bl[i];
			this.cl[i] = E.cl[i];
		}
	}
}
