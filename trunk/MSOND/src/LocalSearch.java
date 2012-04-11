import java.util.ArrayList;
import java.util.Random;


public class LocalSearch {
	double MAX = 9999999999999999.0;
	double MIN = 0;
	
	public LocalSearch() {
		
	}
	
	public Path Dijkstra(int s, int t, Graph g) {
		Path p = new Path();
		double dist[] = new double[g.node];
		int previous[] = new int[g.node];
		int k[] = new int[g.node];
		int u = 0;
		Graph tmpG = new Graph();
		double min = 0.0;
		
		tmpG.copyGraph(g);
		for (int i = 0; i < tmpG.node; i++)
			for (int j = 0; j < tmpG.node; j++) {
				tmpG.graph[i][j] += 1.0;
			}
		for (int i = 0; i < g.listNodeUsed.size(); i++) {
			k[g.listNodeUsed.get(i).intValue()] = 1;
		}
		for (int i = 0; i < tmpG.node; i++) {
			dist[i] = tmpG.graph[s][i];
			previous[i] = s;
		}
		dist[s] = 0;
		k[s] = 1; k[t] = 0;
		for (int i = 0; i < tmpG.node; i++) {
			min = MAX;
			for (int j = 0; j < tmpG.node; j++) {
				if ((k[j] == 0) && (dist[j] < min)) {
					min = dist[j];
					u = j;
				}
			}
			k[u] = 1;
			if (u == t)
				break;
			for (int j = 0; j < tmpG.node; j++) {
				if ((k[j] == 0) && (dist[j] > dist[u] + tmpG.graph[u][j])) {
					dist[j] = dist[u] + tmpG.graph[u][j];
					previous[j] = u;
				}
			}
		}
		u = t;
		while (u != s) {
			u = previous[u];
			g.listNodeUsed.add(Integer.valueOf(u));
			p.getPath().add(0, Integer.valueOf(u));
		}
		p.getPath().remove(0);
		return p;
	}
	
	public Graph search(Graph tmpGraph, Graph g) {
		ArrayList<Integer> tmpArrInt = new ArrayList<Integer>();
		Graph tmpG = new Graph();
		
		tmpG.copyGraph(tmpGraph);

		for (int i = 0; i < tmpG.listReq.length; i++) {
			for (int j = 0; j < tmpG.listReq[i].getWk().size() - 1; j++) {
				tmpG.graph[tmpG.listReq[i].getWk().get(j)][tmpG.listReq[i].getWk().get(j + 1)] = 0;
				tmpG.graph[tmpG.listReq[i].getWk().get(j + 1)][tmpG.listReq[i].getWk().get(j)] = 0;
			}
			for (int j = 0; j < tmpG.listReq[i].getBk().size() - 1; j++) {
				tmpG.graph[tmpG.listReq[i].getBk().get(j)][tmpG.listReq[i].getBk().get(j + 1)] = 0;
				tmpG.graph[tmpG.listReq[i].getBk().get(j + 1)][tmpG.listReq[i].getBk().get(j)] = 0;
			}
		}
		
		for (int i = 0; i < tmpG.listReq.length; i++) {
			tmpG.listNodeUsed.clear();
			for (int j = 0; j < g.listReq[i].getWk().size(); j++) {
				tmpG.listNodeUsed.add(g.listReq[i].wk.get(j));
			}
			for (int j = 1; j < g.listReq[i].getBk().size() - 1; j++) {
				tmpG.listNodeUsed.add(g.listReq[i].bk.get(j));
			}
			
			for (int j = 0; j < tmpG.listReq[i].getWk().size() - 1; j++) {
				tmpG.graph1[tmpG.listReq[i].getWk().get(j)][tmpG.listReq[i].getWk().get(j + 1)] --;
				tmpG.graph1[tmpG.listReq[i].getWk().get(j + 1)][tmpG.listReq[i].getWk().get(j)] --;
				if (tmpG.graph1[tmpG.listReq[i].getWk().get(j)][tmpG.listReq[i].getWk().get(j + 1)] == 0) {
					tmpG.graph[tmpG.listReq[i].getWk().get(j)][tmpG.listReq[i].getWk().get(j + 1)] =
							g.graph[tmpG.listReq[i].getWk().get(j)][tmpG.listReq[i].getWk().get(j + 1)];
					tmpG.graph[tmpG.listReq[i].getWk().get(j + 1)][tmpG.listReq[i].getWk().get(j)] =
							g.graph[tmpG.listReq[i].getWk().get(j + 1)][tmpG.listReq[i].getWk().get(j)];
				}
			}
			
			for (int j = 0; j < tmpG.listReq[i].getBk().size() - 1; j++) {
				tmpG.graph1[tmpG.listReq[i].getBk().get(j)][tmpG.listReq[i].getBk().get(j + 1)] --;
				tmpG.graph1[tmpG.listReq[i].getBk().get(j + 1)][tmpG.listReq[i].getBk().get(j)] --;
				if (tmpG.graph1[tmpG.listReq[i].getBk().get(j)][tmpG.listReq[i].getBk().get(j + 1)] == 0) {
					tmpG.graph[tmpG.listReq[i].getBk().get(j)][tmpG.listReq[i].getBk().get(j + 1)] =
							g.graph[tmpG.listReq[i].getBk().get(j)][tmpG.listReq[i].getBk().get(j + 1)];
					tmpG.graph[tmpG.listReq[i].getBk().get(j + 1)][tmpG.listReq[i].getBk().get(j)] =
							g.graph[tmpG.listReq[i].getBk().get(j + 1)][tmpG.listReq[i].getBk().get(j)];
				}
			}
			
			tmpArrInt = new ArrayList<Integer>();
			tmpArrInt.add(g.listReq[i].s);
			for (int j = 0; j < g.listReq[i].getWk().size() - 1; j++) {
				tmpArrInt.addAll(Dijkstra(g.listReq[i].getWk().get(j), g.listReq[i].getWk().get(j+1), tmpG).getPath());
				tmpArrInt.add(g.listReq[i].getWk().get(j+1));
			}
			tmpG.listReq[i].wk.clear();
			tmpG.listReq[i].wk.addAll(tmpArrInt);
			for (int j = 0; j < tmpG.listReq[i].getWk().size() - 1; j++) {
				tmpG.graph1[tmpG.listReq[i].getWk().get(j)][tmpG.listReq[i].getWk().get(j + 1)] ++;
				tmpG.graph1[tmpG.listReq[i].getWk().get(j + 1)][tmpG.listReq[i].getWk().get(j)] ++;
				tmpG.graph[tmpG.listReq[i].getWk().get(j)][tmpG.listReq[i].getWk().get(j + 1)] = 0;
				tmpG.graph[tmpG.listReq[i].getWk().get(j + 1)][tmpG.listReq[i].getWk().get(j)] = 0;
			}
			
			tmpArrInt = new ArrayList<Integer>();
			tmpArrInt.add(g.listReq[i].s);
			for (int j = 0; j < g.listReq[i].getBk().size() - 1; j++) {
				tmpArrInt.addAll(Dijkstra(g.listReq[i].getBk().get(j), g.listReq[i].getBk().get(j+1), tmpG).getPath());
				tmpArrInt.add(g.listReq[i].getBk().get(j+1));
			}
			tmpG.listReq[i].bk.clear();
			tmpG.listReq[i].bk.addAll(tmpArrInt);
			for (int j = 0; j < tmpG.listReq[i].getBk().size() - 1; j++) {
				tmpG.graph1[tmpG.listReq[i].getBk().get(j)][tmpG.listReq[i].getBk().get(j + 1)] ++;
				tmpG.graph1[tmpG.listReq[i].getBk().get(j + 1)][tmpG.listReq[i].getBk().get(j)] ++;
				tmpG.graph[tmpG.listReq[i].getBk().get(j)][tmpG.listReq[i].getBk().get(j + 1)] = 0;
				tmpG.graph[tmpG.listReq[i].getBk().get(j + 1)][tmpG.listReq[i].getBk().get(j)] = 0;
			}
		}
		return tmpG;
	}
	
	public void run(Graph g) {
		ArrayList<Graph> arrGraph = new ArrayList<Graph>();
		double tmpCost = 99999999999999.0;
		Graph tmpGraph = new Graph();
		Graph lastGraph = new Graph();
		Graph bestGraph = new Graph();
		
		lastGraph = search(g, g);
		arrGraph.add(lastGraph);
		for (int i = 0; i < 10; i++) {
			tmpGraph = search(lastGraph, g);
			System.out.println(tmpGraph.sumCost(tmpGraph, g));
			if (tmpGraph.sumCost(tmpGraph, g) < tmpCost) {
				tmpCost = tmpGraph.sumCost(tmpGraph, g);
				arrGraph.add(tmpGraph);
				bestGraph.copyGraph(tmpGraph);
			}
			else {
				if (!arrGraph.isEmpty()) {
					lastGraph = new Graph();
					lastGraph.copyGraph(arrGraph.get(arrGraph.size() - 1));
					arrGraph.remove(arrGraph.size() - 1);
					changeGraph(lastGraph, g);
				}
				else {
//					break;
				}
			}
		}
	}
	
	public void changeGraph(Graph tmpG, Graph g) {
		Random tmpRan = new Random();
		int i = (int)((g.listReq.length - 1)*tmpRan.nextDouble());
		ArrayList<Integer> tmpArrInt = new ArrayList<Integer>();
		
		tmpG.listNodeUsed.clear();
		for (int j = 0; j < g.listReq[i].getWk().size(); j++) {
			tmpG.listNodeUsed.add(g.listReq[i].wk.get(j));
		}
		for (int j = 1; j < g.listReq[i].getBk().size() - 1; j++) {
			tmpG.listNodeUsed.add(g.listReq[i].bk.get(j));
		}
		
		for (int j = 0; j < tmpG.listReq[i].getWk().size() - 1; j++) {
			tmpG.graph1[tmpG.listReq[i].getWk().get(j)][tmpG.listReq[i].getWk().get(j + 1)] --;
			tmpG.graph1[tmpG.listReq[i].getWk().get(j + 1)][tmpG.listReq[i].getWk().get(j)] --;
			if (tmpG.graph1[tmpG.listReq[i].getWk().get(j)][tmpG.listReq[i].getWk().get(j + 1)] == 0) {
				tmpG.graph[tmpG.listReq[i].getWk().get(j)][tmpG.listReq[i].getWk().get(j + 1)] =
						g.graph[tmpG.listReq[i].getWk().get(j)][tmpG.listReq[i].getWk().get(j + 1)];
				tmpG.graph[tmpG.listReq[i].getWk().get(j + 1)][tmpG.listReq[i].getWk().get(j)] =
						g.graph[tmpG.listReq[i].getWk().get(j + 1)][tmpG.listReq[i].getWk().get(j)];
			}
		}
		
		for (int j = 0; j < tmpG.listReq[i].getBk().size() - 1; j++) {
			tmpG.graph1[tmpG.listReq[i].getBk().get(j)][tmpG.listReq[i].getBk().get(j + 1)] --;
			tmpG.graph1[tmpG.listReq[i].getBk().get(j + 1)][tmpG.listReq[i].getBk().get(j)] --;
			if (tmpG.graph1[tmpG.listReq[i].getBk().get(j)][tmpG.listReq[i].getBk().get(j + 1)] == 0) {
				tmpG.graph[tmpG.listReq[i].getBk().get(j)][tmpG.listReq[i].getBk().get(j + 1)] =
						g.graph[tmpG.listReq[i].getBk().get(j)][tmpG.listReq[i].getBk().get(j + 1)];
				tmpG.graph[tmpG.listReq[i].getBk().get(j + 1)][tmpG.listReq[i].getBk().get(j)] =
						g.graph[tmpG.listReq[i].getBk().get(j + 1)][tmpG.listReq[i].getBk().get(j)];
			}
		}
		
		tmpArrInt = new ArrayList<Integer>();
		tmpArrInt.add(g.listReq[i].s);
		for (int j = 0; j < g.listReq[i].getWk().size() - 1; j++) {
			tmpArrInt.addAll(NevDijkstra(g.listReq[i].getWk().get(j), g.listReq[i].getWk().get(j+1), tmpG).getPath());
			tmpArrInt.add(g.listReq[i].getWk().get(j+1));
		}
		tmpG.listReq[i].wk.clear();
		tmpG.listReq[i].wk.addAll(tmpArrInt);
		for (int j = 0; j < tmpG.listReq[i].getWk().size() - 1; j++) {
			tmpG.graph1[tmpG.listReq[i].getWk().get(j)][tmpG.listReq[i].getWk().get(j + 1)] ++;
			tmpG.graph1[tmpG.listReq[i].getWk().get(j + 1)][tmpG.listReq[i].getWk().get(j)] ++;
			tmpG.graph[tmpG.listReq[i].getWk().get(j)][tmpG.listReq[i].getWk().get(j + 1)] = 0;
			tmpG.graph[tmpG.listReq[i].getWk().get(j + 1)][tmpG.listReq[i].getWk().get(j)] = 0;
		}
		
		tmpArrInt = new ArrayList<Integer>();
		tmpArrInt.add(g.listReq[i].s);
		for (int j = 0; j < g.listReq[i].getBk().size() - 1; j++) {
			tmpArrInt.addAll(NevDijkstra(g.listReq[i].getBk().get(j), g.listReq[i].getBk().get(j+1), tmpG).getPath());
			tmpArrInt.add(g.listReq[i].getBk().get(j+1));
		}
		tmpG.listReq[i].bk.clear();
		tmpG.listReq[i].bk.addAll(tmpArrInt);
		for (int j = 0; j < tmpG.listReq[i].getBk().size() - 1; j++) {
			tmpG.graph1[tmpG.listReq[i].getBk().get(j)][tmpG.listReq[i].getBk().get(j + 1)] ++;
			tmpG.graph1[tmpG.listReq[i].getBk().get(j + 1)][tmpG.listReq[i].getBk().get(j)] ++;
			tmpG.graph[tmpG.listReq[i].getBk().get(j)][tmpG.listReq[i].getBk().get(j + 1)] = 0;
			tmpG.graph[tmpG.listReq[i].getBk().get(j + 1)][tmpG.listReq[i].getBk().get(j)] = 0;
		}
	}
	
	public Path NevDijkstra(int s, int t, Graph g) {
		Path p = new Path();
		double dist[] = new double[g.node];
		int previous[] = new int[g.node];
		int k[] = new int[g.node];
		int u = 0;
		Graph tmpG = new Graph();
		double max = MIN;
		
		tmpG.copyGraph(g);
		for (int i = 0; i < tmpG.node; i++)
			for (int j = 0; j < tmpG.node; j++) {
				tmpG.graph[i][j] += 1.0;
			}
		for (int i = 0; i < g.listNodeUsed.size(); i++) {
			k[g.listNodeUsed.get(i).intValue()] = 1;
		}
		for (int i = 0; i < tmpG.node; i++) {
			dist[i] = tmpG.graph[s][i];
			previous[i] = s;
		}
		dist[s] = 0;
		k[s] = 1; k[t] = 0;
		for (int i = 0; i < tmpG.node; i++) {
			max = MAX;
			for (int j = 0; j < tmpG.node; j++) {
				if ((k[j] == 0) && (dist[j] > max)) {
					max = dist[j];
					u = j;
				}
			}
			k[u] = 1;
			if (u == t)
				break;
			for (int j = 0; j < tmpG.node; j++) {
				if ((k[j] == 0) && (dist[j] <= dist[u] + tmpG.graph[u][j])) {
					dist[j] = dist[u] + tmpG.graph[u][j];
					previous[j] = u;
				}
			}
		}
		u = t;
		while (u != s) {
			u = previous[u];
			g.listNodeUsed.add(Integer.valueOf(u));
			p.getPath().add(0, Integer.valueOf(u));
		}
		p.getPath().remove(0);
		return p;
	}
}
