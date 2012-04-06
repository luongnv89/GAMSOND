import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Graph {
	double graph[][];
	int graph1[][];
	int node = 0;
	int req = 0;
	Request listReq[];
	
	public Graph() {
		
	}
	
	public Graph(String url) {
		File file = new File(url);
		double listNode[][];
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
		for (int i = 0; i < node; i++)
			for (int j = 0; j < node; j++) {
				graph[i][j] = g.graph[i][j];
			}
	}
}
