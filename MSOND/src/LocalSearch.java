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
				tmpG.graph[i][j] += 0.0000000001;
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
	
	public double run(Graph g) {
		ArrayList<Graph> arrGraph = new ArrayList<Graph>();
		double tmpCost = 99999999999999.0;
		Graph tmpGraph = new Graph();
		Graph lastGraph = new Graph();
		Graph bestGraph = new Graph();
		
		lastGraph = searchRandom(g, g);
		arrGraph.add(lastGraph);
		for (int i = 0; i < 10; i++) {
			tmpGraph = searchRandom(lastGraph, g);
//			System.out.println(tmpGraph.sumCost(tmpGraph, g));
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
		return tmpCost;
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
				tmpG.graph[i][j] += 0.00000001;
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
	
	public Graph search(Graph tmpGraph, Graph g, int begin) {
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
		
		for (int k = 0; k < tmpG.listReq.length; k++) {
			int i = (k + begin)%tmpG.listReq.length;
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
	
	public double run1(Graph g) {
		int edgeMatrix[][];
		Graph tmpGraph;
		Graph lastGraph;
		Edge listEdge[];
		Graph bestGraph = new Graph();
		double bestCost = 9999999999999999.0;
		
		edgeMatrix = new int[g.node][g.node];
		for (int k = 0; k < g.listReq.length; k++) {
			tmpGraph = new Graph();
			tmpGraph = search(g, g, k);
			for (int i = 0; i < g.node; i++)
				for (int j = 0; j < g.node; j++) {
					edgeMatrix[i][j] += tmpGraph.getGraph1()[i][j];
				}
		}
		listEdge = new Edge[5];
		for (int i = 0; i < 5; i++)
			listEdge[i] = new Edge();
		for (int i = 0; i < g.node; i++)
			for (int j = i + 1; j < g.node; j++)
				if (edgeMatrix[i][j] > listEdge[0].key) {
					listEdge[0].key = edgeMatrix[i][j];
					listEdge[0].i = i;
					listEdge[0].j = j;
					sort(listEdge);
				}
		for (int i = 0; i < 5; i++) {
			g.listEdge.add(listEdge[i].i);
			g.listEdge.add(listEdge[i].j);
		}
		
		lastGraph = searchRandom(g, g);
		bestGraph.copyGraph(lastGraph);
		bestCost = bestGraph.sumCost(bestGraph, g);
		for (int i = 0; i < 10; i++) {
			tmpGraph = new Graph();
			tmpGraph = searchRandom(lastGraph, g);
			if (bestCost > tmpGraph.sumCost(tmpGraph, g)) {
				bestGraph.copyGraph(tmpGraph);
				bestCost = tmpGraph.sumCost(tmpGraph, g);
			}
			lastGraph.copyGraph(tmpGraph);
		}
		return bestCost;
	}
	
	public void sort(Edge list[]) {
		Edge tmp = new Edge();
		
		for (int i = 0; i < list.length; i++) 
			for (int j = i + 1; j < list.length; j++)
				if (list[i].key > list[j].key) {
					tmp.key = list[i].key;
					tmp.i = list[i].i;
					tmp.j = list[i].j;
					list[i].key = list[j].key;
					list[i].i = list[j].i;
					list[i].j = list[j].j;
					list[j].key = tmp.key;
					list[j].i = tmp.i;
					list[j].j = tmp.j;
				}
	}
	
	public Path Dijkstra1(int s, int t, Graph g) {
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
				tmpG.graph[i][j] += 0.000002;
			}
		for (int i = 0; i < tmpG.listEdge.size()-1; i+=2) {
			tmpG.graph[tmpG.listEdge.get(i).intValue()][tmpG.listEdge.get(i+1).intValue()] = 0.0000000001;
			tmpG.graph[tmpG.listEdge.get(i+1).intValue()][tmpG.listEdge.get(i).intValue()] = 0.0000000001;
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
	
	public Graph searchRandom(Graph tmpGraph, Graph g) {
		Random tmpRan = new Random();
		ArrayList<Integer> tmpArrInt = new ArrayList<Integer>();
		Graph tmpG = new Graph();
		int arrInt[], tmpInt = 0;
		int arr[];
		Path arrPath[];
		
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
		arrInt = new int[tmpG.listReq.length];
		for (int i = 0; i < tmpG.listReq.length; i++)
			arrInt[i] = i;
		for (int k = 0; k < tmpG.listReq.length/2; k++) {
			int i = (int)((tmpG.listReq.length - 1)*tmpRan.nextDouble());
			int j = (int)((tmpG.listReq.length - 1)*tmpRan.nextDouble());
			tmpInt = arrInt[i];
			arrInt[i] = arrInt[j];
			arrInt[j] = tmpInt;
		}
		
		for (int k = 0; k < tmpG.listReq.length; k++) {
			int i = arrInt[k];
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
			
			arr = new int[g.listReq[i].getWk().size() - 1];
			disorder(arr);
			arrPath = new Path[g.listReq[i].getWk().size() - 1];
			for (int l = 0; l < arr.length; l++) {
				int  j = arr[l];
				arrPath[j] = Dijkstra(g.listReq[i].getWk().get(j), g.listReq[i].getWk().get(j+1), tmpG);
			}
			tmpArrInt = new ArrayList<Integer>();
			tmpArrInt.add(g.listReq[i].s);
			for (int j = 0; j < arrPath.length; j++) {
				tmpArrInt.addAll(arrPath[j].getPath());
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
			
			arr = new int[g.listReq[i].getBk().size() - 1];
			disorder(arr);
			arrPath = new Path[g.listReq[i].getBk().size() - 1];
			for (int l = 0; l < arr.length; l++) {
				int  j = arr[l];
				arrPath[j] = Dijkstra(g.listReq[i].getBk().get(j), g.listReq[i].getBk().get(j+1), tmpG);
			}
			tmpArrInt = new ArrayList<Integer>();
			tmpArrInt.add(g.listReq[i].s);
			for (int j = 0; j < arrPath.length; j++) {
				tmpArrInt.addAll(arrPath[j].getPath());
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
	
	public void disorder(int arr[]) {
		int tmpInt;
		Random tmpRan = new Random();
		
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		for (int k = 0; k < arr.length/2; k++) {
			int i = (int)((arr.length - 1)*tmpRan.nextDouble());
			int j = (int)((arr.length - 1)*tmpRan.nextDouble());
			tmpInt = arr[i];
			arr[i] = arr[j];
			arr[j] = tmpInt;
		}
	}
	
	public double run2(Graph g) {
		double tmpCost, tmpC;
		double bestCost = 99999999999999.0;
		Graph tmpGraph = new Graph();
		Graph bestGraph = new Graph();
		ArrayList<Graph> arrGraph = new ArrayList<Graph>();
		
		tmpGraph = searchRandom(g, g);
		for (int i = 0; i < 10; i++) {
			tmpGraph = searchRandom(tmpGraph, g);
			tmpCost = tmpGraph.sumCost(tmpGraph, g);
//			System.out.println(tmpGraph.sumCost(tmpGraph, g));
			if (bestCost > tmpCost) {
				bestCost = tmpCost;
				bestGraph.copyGraph(tmpGraph);
			}
		}
		arrGraph.add(bestGraph);
		while (arrGraph.size() > 0) {
			tmpGraph = new Graph();
			tmpGraph.copyGraph(arrGraph.get(0));
			tmpCost = tmpGraph.sumCost(tmpGraph, g);
			arrGraph.remove(0);
			for (int i = 0; i < tmpGraph.node; i++)
				for (int j = i; j < tmpGraph.node; j++)
					if (tmpGraph.graph1[i][j] == 1) {
						Graph tmpG = new Graph();
						tmpG = changGraph(tmpGraph, g, i, j);
						tmpC = tmpG.sumCost(tmpG, g);
						if (tmpC < tmpCost) {
							arrGraph.add(tmpG);
						}
						if (tmpC < bestCost) {
							bestCost = tmpC;
							bestGraph.copyGraph(tmpG);
						}
					}
		}
		return bestCost;
	}
	
	public Graph changGraph(Graph tmpG, Graph g, int a, int b) {
		Graph tmpGraph = new Graph();
		int req = 0, wk = 0, bk = 0;
		
		tmpGraph.copyGraph(tmpG);
		
		boolean flag = false;
		for (int i = 0; i < tmpGraph.listReq.length; i++) {
			for (int j = 0; j < tmpGraph.listReq[i].wk.size() - 1; j++)
				if ((tmpGraph.listReq[i].wk.get(j).intValue() == a)&&(tmpGraph.listReq[i].wk.get(j+1).intValue() == b)
			|| (tmpGraph.listReq[i].wk.get(j).intValue() == b)&&(tmpGraph.listReq[i].wk.get(j+1).intValue() == a)) {
					wk = 1;
					req = i;
					flag = true;
					break;
				}
			for (int j = 0; j < tmpGraph.listReq[i].bk.size() - 1; j++)
				if ((tmpGraph.listReq[i].bk.get(j).intValue() == a)&&(tmpGraph.listReq[i].bk.get(j+1).intValue() == b)
			|| (tmpGraph.listReq[i].bk.get(j).intValue() == b)&&(tmpGraph.listReq[i].bk.get(j+1).intValue() == a)) {
					bk = 1;
					req = i;
					flag = true;
					break;
				}
			if (flag)
				break;
		}
		
		if (wk == 1) {
			tmpGraph.listNodeUsed.clear();
			for (int j = 0; j < g.listReq[req].getWk().size(); j++) {
				tmpGraph.listNodeUsed.add(g.listReq[req].wk.get(j));
			}
			for (int j = 1; j < tmpGraph.listReq[req].getBk().size() - 1; j++) {
				tmpGraph.listNodeUsed.add(tmpGraph.listReq[req].bk.get(j));
			}
			
			for (int j = 0; j < tmpGraph.listReq[req].getWk().size() - 1; j++) {
				tmpGraph.graph1[tmpGraph.listReq[req].getWk().get(j)][tmpGraph.listReq[req].getWk().get(j + 1)] --;
				tmpGraph.graph1[tmpGraph.listReq[req].getWk().get(j + 1)][tmpGraph.listReq[req].getWk().get(j)] --;
				if (tmpGraph.graph1[tmpGraph.listReq[req].getWk().get(j)][tmpGraph.listReq[req].getWk().get(j + 1)] == 0) {
					tmpGraph.graph[tmpGraph.listReq[req].getWk().get(j)][tmpGraph.listReq[req].getWk().get(j + 1)] =
							g.graph[tmpGraph.listReq[req].getWk().get(j)][tmpGraph.listReq[req].getWk().get(j + 1)];
					tmpGraph.graph[tmpGraph.listReq[req].getWk().get(j + 1)][tmpGraph.listReq[req].getWk().get(j)] =
							g.graph[tmpGraph.listReq[req].getWk().get(j + 1)][tmpGraph.listReq[req].getWk().get(j)];
				}
			}
			tmpGraph.graph[a][b] = 9999999999999999.0;
			tmpGraph.graph[b][a] = 9999999999999999.0;

			int arr[];
			arr = new int[g.listReq[req].getWk().size() - 1];
			disorder(arr);
			Path arrPath[];
			arrPath = new Path[g.listReq[req].getWk().size() - 1];
			for (int l = 0; l < arr.length; l++) {
				int  j = arr[l];
				arrPath[j] = Dijkstra(g.listReq[req].getWk().get(j), g.listReq[req].getWk().get(j+1), tmpGraph);
			}
			ArrayList<Integer> tmpArrInt = new ArrayList<Integer>();
			tmpArrInt.add(g.listReq[req].s);
			for (int j = 0; j < arrPath.length; j++) {
				tmpArrInt.addAll(arrPath[j].getPath());
				tmpArrInt.add(g.listReq[req].getWk().get(j+1));
			}
			tmpGraph.listReq[req].wk.clear();
			tmpGraph.listReq[req].wk.addAll(tmpArrInt);
			for (int j = 0; j < tmpGraph.listReq[req].getWk().size() - 1; j++) {
				tmpGraph.graph1[tmpGraph.listReq[req].getWk().get(j)][tmpGraph.listReq[req].getWk().get(j + 1)] ++;
				tmpGraph.graph1[tmpGraph.listReq[req].getWk().get(j + 1)][tmpGraph.listReq[req].getWk().get(j)] ++;
				tmpGraph.graph[tmpGraph.listReq[req].getWk().get(j)][tmpGraph.listReq[req].getWk().get(j + 1)] = 0;
				tmpGraph.graph[tmpGraph.listReq[req].getWk().get(j + 1)][tmpGraph.listReq[req].getWk().get(j)] = 0;
			}
		}
		
		if (bk == 1) {
			tmpGraph.listNodeUsed.clear();
			for (int j = 0; j < g.listReq[req].getBk().size(); j++) {
				tmpGraph.listNodeUsed.add(g.listReq[req].bk.get(j));
			}
			for (int j = 1; j < tmpGraph.listReq[req].getWk().size() - 1; j++) {
				tmpGraph.listNodeUsed.add(tmpGraph.listReq[req].wk.get(j));
			}
			
			for (int j = 0; j < tmpGraph.listReq[req].getBk().size() - 1; j++) {
				tmpGraph.graph1[tmpGraph.listReq[req].getBk().get(j)][tmpGraph.listReq[req].getBk().get(j + 1)] --;
				tmpGraph.graph1[tmpGraph.listReq[req].getBk().get(j + 1)][tmpGraph.listReq[req].getBk().get(j)] --;
				if (tmpGraph.graph1[tmpGraph.listReq[req].getBk().get(j)][tmpGraph.listReq[req].getBk().get(j + 1)] == 0) {
					tmpGraph.graph[tmpGraph.listReq[req].getBk().get(j)][tmpGraph.listReq[req].getBk().get(j + 1)] =
							g.graph[tmpGraph.listReq[req].getBk().get(j)][tmpGraph.listReq[req].getBk().get(j + 1)];
					tmpGraph.graph[tmpGraph.listReq[req].getBk().get(j + 1)][tmpGraph.listReq[req].getBk().get(j)] =
							g.graph[tmpGraph.listReq[req].getBk().get(j + 1)][tmpGraph.listReq[req].getBk().get(j)];
				}
			}
			tmpGraph.graph[a][b] = 9999999999999999.0;
			tmpGraph.graph[b][a] = 9999999999999999.0;

			int arr[];
			arr = new int[g.listReq[req].getBk().size() - 1];
			disorder(arr);
			Path arrPath[];
			arrPath = new Path[g.listReq[req].getBk().size() - 1];
			for (int l = 0; l < arr.length; l++) {
				int  j = arr[l];
				arrPath[j] = Dijkstra(g.listReq[req].getBk().get(j), g.listReq[req].getBk().get(j+1), tmpGraph);
			}
			ArrayList<Integer> tmpArrInt = new ArrayList<Integer>();
			tmpArrInt.add(g.listReq[req].s);
			for (int j = 0; j < arrPath.length; j++) {
				tmpArrInt.addAll(arrPath[j].getPath());
				tmpArrInt.add(g.listReq[req].getBk().get(j+1));
			}
			tmpGraph.listReq[req].bk.clear();
			tmpGraph.listReq[req].bk.addAll(tmpArrInt);
			for (int j = 0; j < tmpGraph.listReq[req].getBk().size() - 1; j++) {
				tmpGraph.graph1[tmpGraph.listReq[req].getBk().get(j)][tmpGraph.listReq[req].getBk().get(j + 1)] ++;
				tmpGraph.graph1[tmpGraph.listReq[req].getBk().get(j + 1)][tmpGraph.listReq[req].getBk().get(j)] ++;
				tmpGraph.graph[tmpGraph.listReq[req].getBk().get(j)][tmpGraph.listReq[req].getBk().get(j + 1)] = 0;
				tmpGraph.graph[tmpGraph.listReq[req].getBk().get(j + 1)][tmpGraph.listReq[req].getBk().get(j)] = 0;
			}
		}
		return tmpGraph;
	}
}
