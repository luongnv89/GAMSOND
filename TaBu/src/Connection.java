
public class Connection {
	public int s;
	public int t;
	public int b;
	public int size;
	public int path[];
	
	public void copy(Connection connection) {
		s = connection.s;
		t = connection.t;
		b = connection.b;
		size = connection.size;
		for(int i = 0; i < size; i++) {
			path[i] = connection.path[i];
		}
	}
}
