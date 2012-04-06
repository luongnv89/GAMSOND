import java.io.*;

public class Graph {
	public int node;
	public link E[][];
	
	public Graph() {
		
	}
	
	public Graph(String txt) {
		File file = new File(txt);
		CvString cv = new CvString();
		int l = 0;
		int tmp1 = 0;
		int tmp2 = 0;
		int tmp3 = 0;
		String tmp[];
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String s;
			s = in.readLine();
			tmp = cv.tach(s, 2);
			l = Integer.parseInt(tmp[0]);
			node = Integer.parseInt(tmp[1]);
			
			E = new link[node][node];
			for(int i = 0; i < node; i++)
				for(int j = 0; j < node; j++) {
					E[i][j] = new link();
				}
			
			for(int i = 0; i < l; i++) {
				do {
				s = in.readLine();
				}while(s.length() == 0);
				tmp = cv.tach(s, 2);
				tmp1 = Integer.parseInt(tmp[0]) - 1;
				tmp2 = Integer.parseInt(tmp[1]) - 1;
				this.E[tmp1][tmp2].e = 1;
				this.E[tmp1][tmp2].s = tmp1;
				this.E[tmp1][tmp2].t = tmp2;
				
				s = in.readLine();
				tmp3 = Integer.parseInt(s);
				this.E[tmp1][tmp2].lv = tmp3;
				
				s = in.readLine();
				tmp = cv.tach(s, tmp3);
				for(int j = 0; j < tmp3; j++) {
					this.E[tmp1][tmp2].bl[j] = Integer.parseInt(tmp[j]);
				}
				
				s = in.readLine();
				tmp = cv.tach(s, tmp3);
				for(int j = 0; j < tmp3; j++) {
					this.E[tmp1][tmp2].cl[j] = Integer.parseInt(tmp[j]);
				}
				
			}
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void copyGraph(Graph g) {
		node = g.node;
		E = new link[node][node];
		for(int i = 0; i < node; i++)
			for(int j = 0; j < node; j++) {
				E[i][j] = new link();
				E[i][j].b = g.E[i][j].b;
				E[i][j].e = g.E[i][j].e;
				E[i][j].s = g.E[i][j].s;
				E[i][j].t = g.E[i][j].t;
				E[i][j].lv = g.E[i][j].lv;
				E[i][j].bl = g.E[i][j].bl;
				E[i][j].cl = g.E[i][j].cl;
			}
	}
	
	public void dropLink(int s, int t) {
		E[s][t].e = 0;
	}
	
	public void dropConnect(Connection con) {
		for(int i = 0; i < con.size - 1; i++)
		{
			dropLink(con.path[i], con.path[i+1]);
		}
	}
	
	public int Costlink(link e, int bw) {
		int cost = 0;
		e.b += bw;
		if(e.b > e.bl[e.lv-1])
		{
			e.b -= e.bl[e.lv-1];
			cost = Costlink(e, 0);
			cost += e.cl[e.lv-1];
		}
		else if(e.b == 0)
		{
			cost = 0;
		}
		else for(int i = 0; i < e.lv; i++)
		{
			if(e.b <= e.bl[i])
			{
				cost = e.cl[i];
				break;
			}
		}
		return cost;
	}
	
	public int Cost() {
		int cost = 0;
		for(int i = 0; i < node; i++)
		{
			for(int j = 0; j < node; j++)
			{
				if(E[i][j].e == 1)
				{
					cost += Costlink(E[i][j], 0);
				}
			}
		}
		return cost;
	}
	
	public void update(Connection con) {
		int s, t;
		for(int i = 0; i < con.size - 1; i++)
		{
			s = con.path[i];
			t = con.path[i+1];
			E[s][t].b += con.b;
			E[t][s].b += con.b;
		}
	}
	
	public void update2(Connection con) {
		int s, t;
		System.out.print(con.path[0]);
		for(int i = 0; i < con.size - 1; i++)
		{
			s = con.path[i];
			t = con.path[i+1];
			System.out.print(" " + t);
			E[s][t].b += con.b;
			E[t][s].b += con.b;
		}
		System.out.println(" - " +con.b);
	}
	
	public void updateGraph(Cast cast)
	{
		for(int i = 0; i < cast.u; i++)
		{
			update(cast.uni[i].wp);
			update(cast.uni[i].bp);
		}
		for(int i = 0; i < cast.a; i++)
		{
			update(cast.any[i].wp);
			update(cast.any[i].bp);
		}
	}
	
	public void updateGraph2(Cast cast)
	{
		for(int i = 0; i < cast.u; i++)
		{
			update2(cast.uni[i].wp);
			update2(cast.uni[i].bp);
		}
		for(int i = 0; i < cast.a; i++)
		{
			update2(cast.any[i].wp);
			update2(cast.any[i].bp);
		}
	}
	
	public void printBandWidth() {
		for(int i = 0; i < node; i++) {
			for(int j = i; j < node; j++) {
				if(E[i][j].e == 1) {
					System.out.println(i+"-"+j+" " + E[i][j].b);
				}
			}
		}
		System.out.println("---------------");
	}
}
