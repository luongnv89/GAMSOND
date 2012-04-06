
public class LocalSearch {
	double MAX = 9999999999999999.0;
	double min = 0;
	
	public LocalSearch() {
		
	}
	
	public Path Dijkstra(int s, int t, Graph g) {
		Path p = new Path();
		double dist[] = new double[g.node];
		int previous[] = new int[g.node];
		int k[] = new int[g.node];
		int u = 0;
		
		for (int i = 0; i < g.node; i++) {
			dist[i] = g.graph[s][i];
			previous[i] = s;
		}
		dist[s] = 0;
		k[s] = 1;
		for (int i = 0; i < g.node; i++) {
			min = MAX;
			for (int j = 0; j < g.node; j++) {
				if ((k[j] == 0) && (dist[j] < min)) {
					min = dist[j];
					u = j;
				}
			}
			k[u] = 1;
			if (u == t)
				break;
			for (int j = 0; j < g.node; j++) {
				if ((k[j] == 0) && (dist[j] > dist[u] + g.graph[u][j])) {
					dist[j] = dist[u] + g.graph[u][j];
					previous[j] = u;
				}
			}
		}
		u = t;
		p.getPath().add(Integer.valueOf(u));
		while (u != s) {
			u = previous[u];
			p.getPath().add(0, Integer.valueOf(u));
		}
		return p;
	}
}
