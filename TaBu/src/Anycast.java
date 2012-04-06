
public class Anycast {
	public int s;
	public int t1;
	public int t2;
	public int b;
	Connection wp = new Connection();
	Connection bp = new Connection();
	
	public void copy(Anycast anycast) {
		s = anycast.s;
		t1 = anycast.t1;
		t2 = anycast.t2;
		b = anycast.b;
		wp.b = anycast.wp.b;
		wp.s = anycast.wp.s;
		wp.t = anycast.wp.t;
		wp.size = anycast.wp.size;
		for(int j = 0; j < wp.size; j++) {
			wp.path[j] = anycast.wp.path[j];
		}
		bp.b = anycast.bp.b;
		bp.s = anycast.bp.s;
		bp.t = anycast.bp.t;
		bp.size = anycast.bp.size;
		for(int j = 0; j < bp.size; j++) {
			bp.path[j] = anycast.bp.path[j];
		}
	}
}
