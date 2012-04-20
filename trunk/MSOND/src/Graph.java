import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Graph {
	double graph[][];
	int graph1[][];
	int node = 0;
	int req = 0;
	Request listReq[];
	double listNode[][];
	ArrayList<Integer> listNodeUsed = new ArrayList<Integer>();
	ArrayList<Integer> listEdge = new ArrayList<Integer>();
	
	public Graph() {
		
	}
	
	public double[][] getGraph() {
		return graph;
	}

	public void setGraph(double[][] graph) {
		this.graph = graph;
	}

	public int[][] getGraph1() {
		return graph1;
	}

	public void setGraph1(int[][] graph1) {
		this.graph1 = graph1;
	}

	public int getNode() {
		return node;
	}

	public void setNode(int node) {
		this.node = node;
	}

	public int getReq() {
		return req;
	}

	public void setReq(int req) {
		this.req = req;
	}

	public Request[] getListReq() {
		return listReq;
	}

	public void setListReq(Request[] listReq) {
		this.listReq = listReq;
	}

	public double[][] getListNode() {
		return listNode;
	}

	public void setListNode(double[][] listNode) {
		this.listNode = listNode;
	}

	public ArrayList<Integer> getListNodeUsed() {
		return listNodeUsed;
	}

	public void setListNodeUsed(ArrayList<Integer> listNodeUsed) {
		this.listNodeUsed = listNodeUsed;
	}

	public Graph(String url) {
		File file = new File(url);
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String s = in.readLine();
			s = in.readLine();
			node = Integer.parseInt(s.split(" ")[0]);
			s = in.readLine();
			s = in.readLine();
			req = Integer.parseInt(s.split(" ")[0]);
			s = in.readLine();
			s = in.readLine();
			listNode = new double[node][2];
			for(int i = 0; i < node; i++) {
				s = in.readLine();
				listNode[i][0] = Double.parseDouble(s.split(" ")[6]);
				listNode[i][1] = Double.parseDouble(s.split(" ")[8]);
			}
			s = in.readLine();
			s = in.readLine();
			listReq = new Request[req];
			for(int i = 0; i < req; i++) {
				listReq[i] = new Request();
				s = in.readLine();
				String tmp[] = new String[3];
				int b = 0;
				int e = 0;
				int k = 0;
				for(int j = 0; j < s.length(); j++) {
					if(s.charAt(j) == '(' || j == s.length() - 1) {
						e = j;
						tmp[k++] = s.substring(b, e);
						b = e;
					}
				}
				listReq[i].s = Integer.parseInt(tmp[0].split(" ")[0]);
				listReq[i].t = Integer.parseInt(tmp[0].split(" ")[1]);
				String tmp1[] = tmp[1].split(" ");
				String tmp2[] = tmp[2].split(" ");
				for(int j = 1; j < tmp1.length -1; j++) {
					listReq[i].wk.add(Integer.parseInt(tmp1[j]));
				}
				for(int j = 1; j < tmp2.length; j++) {
					listReq[i].bk.add(Integer.parseInt(tmp2[j]));
				}
			}
			graph = new double[node][node];
			for (int i = 0; i < node; i++)
				for (int j = 0; j < node; j++) {
					graph[i][j] = distanceNode(listNode[i], listNode[j]);
				}
			graph1 = new int[node][node];
			for (int i = 0; i < req; i++) {
				for (int j = 0; j < listReq[i].getWk().size() - 1; j++) {
					graph1[listReq[i].getWk().get(j)][listReq[i].getWk().get(j + 1)] ++;
					graph1[listReq[i].getWk().get(j + 1)][listReq[i].getWk().get(j)] ++;
				}
				for (int j = 0; j < listReq[i].getBk().size() - 1; j++) {
					graph1[listReq[i].getBk().get(j)][listReq[i].getBk().get(j + 1)] ++;
					graph1[listReq[i].getBk().get(j + 1)][listReq[i].getBk().get(j)] ++;
				}
			}
 			System.out.print("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	public double distanceNode(double node1[], double node2[]) {
		double distance = 0;
		
		distance = Math.sqrt((node1[0] - node2[0])*(node1[0] - node2[0]) + (node1[1] - node2[1])*(node1[1] - node2[1]));
		return distance;	
	}
	
	public void copyGraph(Graph g) {
		node = g.node;
		graph = new double[node][node];
		graph1 = new int[node][node];
		listReq = new Request[g.listReq.length];
		listNode = new double[node][2];
		
		for (int i = 0; i < node; i++)
			for (int j = 0; j < node; j++)
				graph[i][j] = g.graph[i][j];
		for (int i = 0; i < node; i++)
			for (int j = 0; j < node; j++)
				graph1[i][j] = g.graph1[i][j];
		for (int i = 0; i < g.listReq.length; i++) {
			listReq[i] = new Request();
			listReq[i].bk = new ArrayList<Integer>();
			listReq[i].wk = new ArrayList<Integer>();
/*			for (int j = 0; j < g.listReq[i].bk.size(); j++) {
				Integer tmp = new Integer(g.listReq[i].bk.get(j).intValue());
				listReq[i].bk.add(tmp);
			}
			for (int j = 0; j < g.listReq[i].wk.size(); j++) {
				Integer tmp = new Integer(g.listReq[i].wk.get(j).intValue());
				listReq[i].wk.add(tmp);
			}*/
			for (int j = 0; j < g.listReq[i].bk.size(); j++)
				listReq[i].bk.add(g.listReq[i].bk.get(j));
			for (int j = 0; j < g.listReq[i].wk.size(); j++)
				listReq[i].wk.add(g.listReq[i].wk.get(j));
			listReq[i].s = g.listReq[i].s;
			listReq[i].t = g.listReq[i].t;
		}
		listNode = g.listNode.clone();
		for (int i = 0; i < g.listEdge.size(); i++) {
			listEdge.add(g.listEdge.get(i));
		}
	}
	
	public double sumCost(Graph tmpG, Graph g) {
		double tmp = 0;
		
		for (int i = 0; i < g.node; i++)
			for (int j = i + 1; j < g.node; j++) {
				if (tmpG.graph1[i][j] != 0)
					tmp += g.graph[i][j];
			}
		return tmp;
	}
	
	public int countGraph1(ArrayList<Edge> arrEdge) {
		int tmpInt = 0;
		Edge tmpEdge;
		
		for (int i = 0; i < this.node; i++)
			for (int j = i; j < this.node; j++)
				if (this.graph1[i][j] == 1) {
					tmpEdge = new Edge();
					tmpInt++;
					arrEdge.add(tmpEdge);
				}
		return tmpInt;
	}
}

