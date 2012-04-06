import java.util.ArrayList;
import java.util.Random;


public class LocalSearch {
	int MAX = 99999;
	int MIN = 0;
	
	public Connection shortest_path1(int s, int t, int bw, Graph graph) {
		int d[];
		int p[];
		int ks[];
		Connection con = new Connection();
		con.s = s;
		con.t = t;
		con.b = bw;
		Graph g = new Graph();
		g.copyGraph(graph);
		int node = g.node;
		int st = s;
		d = new int[node];
		ks = new int[node];
		p = new int[node];
		
		for(int i = 0; i < node; i++)
		{
			ks[i] = 0;
			d[i] = MAX + 1;
		}
		d[st] = 0;
		int v;
		int min;
		int dt;
		for(int i = 0; i < node; i++)
		{
			v = 0;
			min = MAX + 1;
			for(int j = 0; j < node; j++)
			{
				if((g.E[st][j].e == 1) && (ks[j] == 0))
				{
					d[j] = g.E[st][j].b + 1;
					g.copyGraph(graph);
				}
			}
			for(int j = 0; j < node; j++)
			{
				if (ks[j] == 0)
				{
					if(d[j] < min)
					{
						min = d[j];
						v = j;
					}
				}
			}
			ks[v] = 1;
			for(int j = 0; j < node; j++)
			{
				if((g.E[v][j].e == 1) && (ks[j] == 0))
				{
					dt = g.E[v][j].b + 1;
					g.copyGraph(graph);
					if(d[j] >= d[v] + dt)
					{
						d[j] = d[v] + dt;
						p[j] = v;
					}
				}
			}
			st = v;
		}

		int path[] = new int[1000];
		int dem = 0;
		int u;
		for(u = t; u != con.s; u = p[u])
		{
			path[dem++] = u;
		}
		path[dem] = u;
		con.size = dem + 1;
		con.path = new int[con.size];
		for(int i = 0; i < con.size; i++)
		{
			con.path[dem--] = path[i];
		}
		return con;
	}
	
	public Connection shortest_path2(int s, int t, int bw, Graph graph) {
		int d[];
		int p[];
		int ks[];
		Connection con = new Connection();
		con.s = s;
		con.t = t;
		con.b = bw;
		Graph g = new Graph();
		g.copyGraph(graph);
		int node = g.node;
		int st = s;
		d = new int[node];
		ks = new int[node];
		p = new int[node];
		
		for(int i = 0; i < node; i++)
		{
			ks[i] = 0;
			d[i] = MAX + 1;
		}
		d[st] = 0;

		int v;
		int min;
		int dt;
		for(int i = 0; i < node; i++)
		{
			v = 0;
			min = MAX + 1;
			for(int j = 0; j < node; j++)
			{
				if((g.E[st][j].e == 1) && (ks[j] == 0))
				{
					d[j] = dv(bw, g.E[st][j], g) + 1;
					g.copyGraph(graph);
				}
			}
			for(int j = 0; j < node; j++)
			{
				if (ks[j] == 0)
				{
					if(d[j] < min)
					{
						min = d[j];
						v = j;
					}
				}
			}
			ks[v] = 1;
			for(int j = 0; j < node; j++)
			{
				if((g.E[v][j].e == 1) && (ks[j] == 0))
				{
					dt = dv(bw, g.E[v][j], g);
					g.copyGraph(graph);
					if(d[j] >= d[v] + dt)
					{
						d[j] = d[v] + dt;
						p[j] = v;
					}
				}
			}
			st = v;
		}

		int path[] = new int[1000];
		int dem = 0;
		int u;
		for(u = t; u != con.s; u = p[u])
		{
			path[dem++] = u;
		}
		path[dem] = u;
		con.size = dem + 1;
		con.path = new int[con.size];
		for(int i = 0; i < con.size; i++)
		{
			con.path[dem--] = path[i];
		}
		return con;
	}
	
	public Connection longest_path(int s, int t, int bw, Graph graph) {
		int d[];
		int p[];
		int ks[];
		Connection con = new Connection();
		con.s = s;
		con.t = t;
		con.b = bw;
		Graph g = new Graph();
		g.copyGraph(graph);
		int node = g.node;
		int st = s;
		d = new int[node];
		ks = new int[node];
		p = new int[node];
		
		for(int i = 0; i < node; i++)
		{
			ks[i] = 0;
			d[i] = MIN - 1;
		}
		d[st] = MAX;
		int v;
		int max;
		int dt;
		for(int i = 0; i < node; i++)
		{
			v = 0;
			max = MIN - 1;
			for(int j = 0; j < node; j++)
			{
				if((g.E[st][j].e == 1) && (ks[j] == 0))
				{
					d[j] = g.E[st][j].b + 1;
					g.copyGraph(graph);
				}
			}
			for(int j = 0; j < node; j++)
			{
				if (ks[j] == 0)
				{
					if(d[j] > max)
					{
						max = d[j];
						v = j;
					}
				}
			}
			ks[v] = 1;
			for(int j = 0; j < node; j++)
			{
				if((g.E[v][j].e == 1) && (ks[j] == 0))
				{
					dt = g.E[v][j].b + 1;
					g.copyGraph(graph);
					if(d[j] <= d[v] + dt)
					{
						d[j] = d[v] + dt;
						p[j] = v;
					}
				}
			}
			st = v;
		}

		int path[] = new int[1000];
		int dem = 0;
		int u;
		for(u = t; u != con.s; u = p[u])
		{
			path[dem++] = u;
		}
		path[dem] = u;
		con.size = dem + 1;
		con.path = new int[con.size];
		for(int i = 0; i < con.size; i++)
		{
			con.path[dem--] = path[i];
		}
		return con;
	}
	
	public void initConnect(Graph g, Cast cast)
	{
		Graph g1 = new Graph();
		Graph g2 = new Graph();
		g2.copyGraph(g);
		for(int i = 0; i < cast.u; i++)
		{
			g1.copyGraph(g2);
			cast.uni[i].wp = shortest_path1(cast.uni[i].s, cast.uni[i].t, cast.uni[i].b, g1);
			g1.dropConnect(cast.uni[i].wp);
			cast.uni[i].bp = shortest_path1(cast.uni[i].s, cast.uni[i].t, cast.uni[i].b, g1);
		}
		for(int i = 0; i < cast.a; i++)
		{
			g1.copyGraph(g2);
			cast.any[i].wp = shortest_path1(cast.any[i].s, cast.any[i].t1, cast.any[i].b, g1);
			g1.dropConnect(cast.any[i].wp);
			cast.any[i].bp = shortest_path1(cast.any[i].s, cast.any[i].t2, cast.any[i].b, g1);
		}
	}
	
	public int dv(int bw, link e, Graph g) {
		int tmp = 0;
		Graph g1 = new Graph();
		link e1 = new link();
		g1.copyGraph(g);
		e1.copyLink(e);
		int t1 = 0, t2 =0;
		t1 = g1.Costlink(e1, 0);
		g1.copyGraph(g);
		e1.copyLink(e);
		t2 = g1.Costlink(e, bw);
		g1.copyGraph(g);
		e1.copyLink(e);
		tmp = t2 - t1;
		return tmp;
	}
	
	public Cast tabuSearch(Graph graph, Cast cast) {
		Graph g1 = new Graph();
		Cast cast1 = new Cast();
		TabuList tabuList = new TabuList();
		ArrayList<Cast> arrayList = new ArrayList<Cast>();
		int i = 0;
		for(int k = 0; k < 20; k++) {
			do {
				if(i >= cast.u) {
					i = 0;
					break;
				}
				cast1.copyCast(cast);
				g1.copyGraph(graph);
				for (int j = 0; j < cast1.u; j++)
				{
					if(i != j)
					{
						g1.update(cast1.uni[j].wp);
						g1.update(cast1.uni[j].bp);
					}
				}
				for(int j = 0; j < cast.a; j++)
				{
					g1.update(cast1.any[j].wp);
					g1.update(cast1.any[j].bp);
				}
				cast1.uni[i].wp = shortest_path1(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b, g1);
				g1.dropConnect(cast1.uni[i].wp);
				cast1.uni[i].bp = shortest_path1(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b ,g1);
				g1.copyGraph(graph);
				g1.updateGraph(cast1);
				cast1.c = g1.Cost();
				if(cast1.c <= cast.c)
				{
					tabuList.setList(cast1, 0, i);
					cast.copyCast(cast1);
				} else {
					Chil chil = tabuList.getList();
					if(chil != null) {
						cast1.copyCast(chil.cast);
						Cast cast2 = new Cast();
						cast2.copyCast(cast1);
						arrayList.add(cast2);
						if(chil.type == 1) {
							g1.copyGraph(graph);
							i = chil.num;
							cast1.any[i].wp = longest_path(cast1.any[i].s, cast1.any[i].t1, cast1.any[i].b, g1);
							g1.dropConnect(cast1.any[i].wp);
							cast1.any[i].bp = longest_path(cast1.any[i].s, cast1.any[i].t2, cast1.any[i].b ,g1);
							g1.copyGraph(graph);
							g1.updateGraph(cast1);
							cast1.c = g1.Cost();
							cast.copyCast(cast1);
							i++;
							break;
						} else {
							g1.copyGraph(graph);
							i = chil.num;
							cast1.uni[i].wp = longest_path(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b, g1);
							g1.dropConnect(cast1.uni[i].wp);
							cast1.uni[i].bp = longest_path(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b ,g1);
							g1.copyGraph(graph);
							g1.updateGraph(cast1);
							cast1.c = g1.Cost();
							cast.copyCast(cast1);
						}
					}
					
				}
				i++;
//				System.out.println("1 "+ i + " "+ cast.c);
			} while(true);
			do {
				if(i >= cast.a) {
					i = 0;
					break;
				}
				cast1.copyCast(cast);
				g1.copyGraph(graph);
				for (int j = 0; j < cast1.u; j++)
				{
					g1.update(cast1.uni[j].wp);
					g1.update(cast1.uni[j].bp);
				}
				for(int j = 0; j < cast.a; j++)
				{
					if(i != j)
					{
						g1.update(cast1.any[j].wp);
						g1.update(cast1.any[j].bp);
					}
				}
				cast1.any[i].wp = shortest_path1(cast1.any[i].s, cast1.any[i].t1, cast1.any[i].b, g1);
				g1.dropConnect(cast1.any[i].wp);
				cast1.any[i].bp = shortest_path1(cast1.any[i].s, cast1.any[i].t2, cast1.any[i].b ,g1);
				g1.copyGraph(graph);
				g1.updateGraph(cast1);
				cast1.c = g1.Cost();
				if(cast1.c <= cast.c)
				{
					tabuList.setList(cast1, 1, i);
					cast.copyCast(cast1);
				} else {
					Chil chil = tabuList.getList();
					if(chil != null) {
						cast1.copyCast(chil.cast);
						Cast cast2 = new Cast();
						cast2.copyCast(cast1);
						arrayList.add(cast2);
						if(chil.type == 0) {
							g1.copyGraph(graph);
							i = chil.num;
							cast1.uni[i].wp = longest_path(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b, g1);
							g1.dropConnect(cast1.uni[i].wp);
							cast1.uni[i].bp = longest_path(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b ,g1);
							g1.copyGraph(graph);
							g1.updateGraph(cast1);
							cast1.c = g1.Cost();
							cast.copyCast(cast1);
							i++;
							break;
						} else {
							g1.copyGraph(graph);
							i = chil.num;
							cast1.any[i].wp = longest_path(cast1.any[i].s, cast1.any[i].t1, cast1.any[i].b, g1);
							g1.dropConnect(cast1.any[i].wp);
							cast1.any[i].bp = longest_path(cast1.any[i].s, cast1.any[i].t2, cast1.any[i].b ,g1);
							g1.copyGraph(graph);
							g1.updateGraph(cast1);
							cast1.c = g1.Cost();
							cast.copyCast(cast1);
						}
					}
					
				}
				i++;
//				System.out.println("2 "+ i + " "+ cast.c);
			}while(true);
		}
		int size = arrayList.size();
		for(int k = 0; k < size; k++) {
			if(cast.c > arrayList.get(k).c) {
				cast.copyCast(arrayList.get(k));
			}
		}
//		System.out.println("--------------------------");
//		System.out.print(cast.c + "\t");
		return cast;
	}
	
	public Cast tabuSearch2(Graph g, Cast cast)
	{
		Graph g1 = new Graph();
		Cast cast1 = new Cast();
		for (int i = 0; i < cast.u; i++)
		{
			cast1.copyCast(cast);
			g1.copyGraph(g);
			for (int j = 0; j < cast1.u; j++)
			{
				if(i != j)
				{
					g1.update(cast1.uni[j].wp);
					g1.update(cast1.uni[j].bp);
				}
			}
			for(int j = 0; j < cast.a; j++)
			{
				g1.update(cast1.any[j].wp);
				g1.update(cast1.any[j].bp);
			}
//			g1.printBandWidth();
			cast1.uni[i].wp = shortest_path2(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b, g1);
			g1.dropConnect(cast1.uni[i].wp);
			cast1.uni[i].bp = shortest_path2(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b ,g1);
			g1.copyGraph(g);
			g1.updateGraph(cast1);
			cast1.c = g1.Cost();
			if(cast1.c <= cast.c)
			{
				cast.copyCast(cast1);
			}
//			System.out.println("1 "+ i + " "+ cast.c);
		}
		for (int i = 0; i < cast.a; i++)
		{
			cast1.copyCast(cast);
			g1.copyGraph(g);
			for (int j = 0; j < cast1.u; j++)
			{
				g1.update(cast1.uni[j].wp);
				g1.update(cast1.uni[j].bp);
			}
			for(int j = 0; j < cast.a; j++)
			{
				if(i != j)
				{
					g1.update(cast1.any[j].wp);
					g1.update(cast1.any[j].bp);
				}
			}
//			g1.printBandWidth();
			cast1.any[i].wp = shortest_path2(cast1.any[i].s, cast1.any[i].t1, cast1.any[i].b, g1);
			g1.dropConnect(cast1.any[i].wp);
			cast1.any[i].bp = shortest_path2(cast1.any[i].s, cast1.any[i].t2, cast1.any[i].b ,g1);
			g1.copyGraph(g);
			g1.updateGraph(cast1);
			cast1.c = g1.Cost();
			if(cast1.c <= cast.c)
			{
				cast.copyCast(cast1);
			}
//			System.out.println("2 "+ i + " "+ cast.c);
		}
		return cast;
	}
	
	public List2[][] castList(Graph graph, Cast cast) {
		int node = graph.node;
		List2 list[][] = new List2[node][];
		Cast cast1 = new Cast();
		cast1.copyCast(cast);
		for(int i = 0; i < node; i++) {
			list[i] = new List2[node];
			for(int j = 0; j < node; j++) {
				list[i][j] = new List2();
			}
		}
		for(int i = 0; i < cast.u; i++) {
			int size = cast.uni[i].wp.size;
			int b = cast.uni[i].wp.b;
			for(int j = 0; j < size - 1; j++) {
				int s = cast.uni[i].wp.path[j];
				int t = cast.uni[i].wp.path[j+1];
				CastList castList = new CastList();
				castList.type = 0;
				castList.num = i;
				castList.b = b;
				list[s][t].arrayList.add(castList);
				list[t][s].arrayList.add(castList);
			}
			size = cast.uni[i].bp.size;
			b = cast.uni[i].bp.b;
			for(int j = 0; j < size - 1; j++) {
				int s = cast.uni[i].bp.path[j];
				int t = cast.uni[i].bp.path[j+1];
				CastList castList = new CastList();
				castList.type = 0;
				castList.num = i;
				castList.b = b;
				list[s][t].arrayList.add(castList);
				list[t][s].arrayList.add(castList);
			}
		}
		for(int i = 0; i < cast.a; i++) {
			int size = cast.any[i].wp.size;
			int b = cast.any[i].wp.b;
			for(int j = 0; j < size - 1; j++) {
				int s = cast.any[i].wp.path[j];
				int t = cast.any[i].wp.path[j+1];
				CastList castList = new CastList();
				castList.type = 1;
				castList.num = i;
				castList.b = b;
				list[s][t].arrayList.add(castList);
				list[t][s].arrayList.add(castList);
			}
			size = cast.any[i].bp.size;
			b = cast.any[i].bp.b;
			for(int j = 0; j < size - 1; j++) {
				int s = cast.any[i].bp.path[j];
				int t = cast.any[i].bp.path[j+1];
				CastList castList = new CastList();
				castList.type = 1;
				castList.num = i;
				castList.b = b;
				list[s][t].arrayList.add(castList);
				list[t][s].arrayList.add(castList);
			}
		}
		return list;
	}
	
	public void FBB(Graph graph, Cast cast) {
		Graph g1 = new Graph();
		Graph g2 = new Graph();
		Graph g3 = new Graph();
		g3.copyGraph(graph);
		Cast cast1 = new Cast();
		List2[][] list;
		ArrayList<Integer> arrayList1 = new ArrayList<Integer>();
		Random rd = new Random();
		for(int i = 0; i < 12; i++) {
			arrayList1.add(i);
		}
		do {
			ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
			int i = (int) ((arrayList1.size() - 1)*rd.nextDouble());
			i = arrayList1.get(i);
			for(int j = i; j < 12; j++) {
				if(g3.E[i][j].e == 1)
					arrayList2.add(j);
			}
			int j = 0;
			if(arrayList2.size() != 0) {
				j = (int) ((arrayList2.size() - 1)*rd.nextDouble());
				j = arrayList2.get(j);
				g3.E[i][j].e = 0;
				arrayList2.remove(Integer.valueOf(j));
				
				g1.copyGraph(graph);
				cast1.copyCast(cast);
				list = castList(graph, cast1);
				int size = list[i][j].arrayList.size();
				ArrayList<Integer> tmp1 = new ArrayList<Integer>();
				ArrayList<Integer> tmp2 = new ArrayList<Integer>();
				ArrayList<Integer> tmp3 = new ArrayList<Integer>();
				ArrayList<Integer> tmp4 = new ArrayList<Integer>();
				for(int k = 0; k < size; k++) {
					int type = list[i][j].arrayList.get(k).type;
					int num = list[i][j].arrayList.get(k).num;
					if(type == 0) {
						tmp1.add(num);
					}
					if(type == 1) {
						tmp2.add(num);
					}
				}
				for (int h = 0; h < cast1.u; h++)
				{
					boolean tf = true;
					for(int e : tmp1) {
						if(e == h) {
							tf = false;
							break;
						}
					}
					if(tf) {
						tmp3.add(h);
					}
				}
				for (int h = 0; h < cast1.a; h++)
				{
					boolean tf = true;
					for(int e : tmp2) {
						if(e == h) {
							tf = false;
							break;
						}
					}
					if(tf) {
						tmp4.add(h);
					}
				}
				for(int h : tmp3) {
					g1.update(cast1.uni[h].wp);
					g1.update(cast1.uni[h].bp);
				}
				for(int h : tmp4) {
					g1.update(cast1.any[h].wp);
					g1.update(cast1.any[h].bp);
				}
//				g1.printBandWidth();
				for(int h : tmp1) {
					g2.copyGraph(g1);
					cast1.uni[h].wp = shortest_path2(cast1.uni[h].s, cast1.uni[h].t, cast1.uni[h].b, g2);
					g2.dropConnect(cast1.uni[h].wp);
					cast1.uni[h].bp = shortest_path2(cast1.uni[h].s, cast1.uni[h].t, cast1.uni[h].b ,g2);
					g1.update(cast1.uni[h].wp);
					g1.update(cast1.uni[h].bp);
//					g1.printBandWidth();
				}
//				g1.printBandWidth();
				for(int h : tmp2) {
					g2.copyGraph(g1);
					cast1.any[h].wp = shortest_path2(cast1.any[h].s, cast1.any[h].t1, cast1.any[h].b, g2);
					g2.dropConnect(cast1.any[h].wp);
					cast1.any[h].bp = shortest_path2(cast1.any[h].s, cast1.any[h].t2, cast1.any[h].b ,g2);
					g1.update(cast1.any[h].wp);
					g1.update(cast1.any[h].bp);
//					g1.printBandWidth();
				}
				g2.copyGraph(graph);
				g2.updateGraph(cast1);
				cast1.c = g2.Cost();
				if(cast1.c <= cast.c) {
					cast.copyCast(cast1);
				}
//				System.out.println(cast.c);
				
			}
			if(arrayList2.size() == 0) {
				arrayList1.remove(Integer.valueOf(i));
			}
			
		}while(arrayList1.size() != 0);
	}
	
	public Cast tabuSearch3(Graph g, Cast cast)
	{
		Graph g1 = new Graph();
		Cast cast1 = new Cast();
		for (int i = 0; i < cast.u; i++)
		{
			cast1.copyCast(cast);
			g1.copyGraph(g);
			for (int j = 0; j < cast1.u; j++)
			{
				if(i != j)
				{
					g1.update(cast1.uni[j].wp);
					g1.update(cast1.uni[j].bp);
				}
			}
			for(int j = 0; j < cast.a; j++)
			{
				g1.update(cast1.any[j].wp);
				g1.update(cast1.any[j].bp);
			}
			cast1.uni[i].wp = shortest_path2(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b, g1);
			g1.dropConnect(cast1.uni[i].wp);
			cast1.uni[i].bp = shortest_path2(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b ,g1);
			g1.copyGraph(g);
			g1.updateGraph(cast1);
			cast1.c = g1.Cost();
			if(cast1.c < cast.c)
			{
				cast.copyCast(cast1);
			}
//			System.out.println("1 "+ i + " "+ cast.c);
		}
		for (int i = 0; i < cast.a; i++)
		{
			cast1.copyCast(cast);
			g1.copyGraph(g);
			for (int j = 0; j < cast1.u; j++)
			{
				g1.update(cast1.uni[j].wp);
				g1.update(cast1.uni[j].bp);
			}
			for(int j = 0; j < cast.a; j++)
			{
				if(i != j)
				{
					g1.update(cast1.any[j].wp);
					g1.update(cast1.any[j].bp);
				}
			}
			cast1.any[i].wp = shortest_path2(cast1.any[i].s, cast1.any[i].t1, cast1.any[i].b, g1);
			g1.dropConnect(cast1.any[i].wp);
			cast1.any[i].bp = shortest_path2(cast1.any[i].s, cast1.any[i].t2, cast1.any[i].b ,g1);
			g1.copyGraph(g);
			g1.updateGraph(cast1);
			cast1.c = g1.Cost();
			if(cast1.c < cast.c)
			{
				cast.copyCast(cast1);
			}
//			System.out.println("2 "+ i + " "+ cast.c);
		}
		return cast;
	}
	
	public Cast backtrack(Cast c, Graph g) {
		Cast cast = new Cast();
		cast.copyCast(c);
		Graph g1 = new Graph();
		g1.copyGraph(g);
		int num = c.a + c.u;
		Random rd = new Random();
		num = (int) (num*rd.nextDouble());
//		System.out.println(num);
		if(num < c.u) {
			for (int j = 0; j < cast.u; j++)
			{
				if(num != j)
				{
					g1.update(cast.uni[j].wp);
					g1.update(cast.uni[j].bp);
				}
			}
			for(int j = 0; j < cast.a; j++)
			{
				g1.update(cast.any[j].wp);
				g1.update(cast.any[j].bp);
			}
			cast.uni[num].wp = longest_path(cast.uni[num].s, cast.uni[num].t, cast.uni[num].b, g1);
			g1.dropConnect(cast.uni[num].wp);
			cast.uni[num].bp = longest_path(cast.uni[num].s, cast.uni[num].t, cast.uni[num].b ,g1);
			g1.copyGraph(g);
		} else {
			num = num - cast.u;
			for (int j = 0; j < cast.u; j++)
			{
				g1.update(cast.uni[j].wp);
				g1.update(cast.uni[j].bp);
			}
			for(int j = 0; j < cast.a; j++)
			{
				if(num != j)
				{
					g1.update(cast.any[j].wp);
					g1.update(cast.any[j].bp);
				}
			}
			cast.any[num].wp = longest_path(cast.any[num].s, cast.any[num].t1, cast.any[num].b, g1);
			g1.dropConnect(cast.any[num].wp);
			cast.any[num].bp = longest_path(cast.any[num].s, cast.any[num].t2, cast.any[num].b ,g1);
			g1.copyGraph(g);
		}
		return cast;
	}
	
	public Cast tabuSearch4(Graph g, Cast cast)
	{
		Graph g1 = new Graph();
		Cast cast1 = new Cast();
		for (int i = 0; i < cast.u; i++)
		{
			cast1.copyCast(cast);
			g1.copyGraph(g);
			for (int j = 0; j < cast1.u; j++)
			{
				if(i != j)
				{
					g1.update(cast1.uni[j].wp);
					g1.update(cast1.uni[j].bp);
				}
			}
			for(int j = 0; j < cast.a; j++)
			{
				g1.update(cast1.any[j].wp);
				g1.update(cast1.any[j].bp);
			}
			cast1.uni[i].wp = shortest_path1(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b, g1);
			g1.dropConnect(cast1.uni[i].wp);
			cast1.uni[i].bp = shortest_path1(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b ,g1);
			g1.copyGraph(g);
			g1.updateGraph(cast1);
			cast1.c = g1.Cost();
			if(cast1.c < cast.c)
			{
				cast.copyCast(cast1);
			}
//			System.out.println("1 "+ i + " "+ cast.c);
//			cast1.uni[i].wp = shortest_path(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b, g1);
//			g1.dropConnect(cast1.uni[i].wp);
//			cast1.uni[i].bp = shortest_path(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b ,g1);
//			g1.copyGraph(g);
//			g1.updateGraph(cast1);
//			cast1.c = g1.Cost();
//			if(cast1.c < cast.c)
//			{
//				cast.copyCast(cast1);
//			}
			cast1.uni[i].wp = shortest_path2(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b, g1);
			g1.dropConnect(cast1.uni[i].wp);
			cast1.uni[i].bp = shortest_path2(cast1.uni[i].s, cast1.uni[i].t, cast1.uni[i].b ,g1);
			g1.copyGraph(g);
			g1.updateGraph(cast1);
			cast1.c = g1.Cost();
			if(cast1.c < cast.c)
			{
				cast.copyCast(cast1);
			}
		}
		for (int i = 0; i < cast.a; i++)
		{
			cast1.copyCast(cast);
			g1.copyGraph(g);
			for (int j = 0; j < cast1.u; j++)
			{
				g1.update(cast1.uni[j].wp);
				g1.update(cast1.uni[j].bp);
			}
			for(int j = 0; j < cast.a; j++)
			{
				if(i != j)
				{
					g1.update(cast1.any[j].wp);
					g1.update(cast1.any[j].bp);
				}
			}
			cast1.any[i].wp = shortest_path1(cast1.any[i].s, cast1.any[i].t1, cast1.any[i].b, g1);
			g1.dropConnect(cast1.uni[i].wp);
			cast1.any[i].bp = shortest_path1(cast1.any[i].s, cast1.any[i].t2, cast1.any[i].b ,g1);
			g1.copyGraph(g);
			g1.updateGraph(cast1);
			cast1.c = g1.Cost();
			if(cast1.c < cast.c)
			{
				cast.copyCast(cast1);
			}
//			System.out.println("2 "+ i + " "+ cast.c);
//			cast1.any[i].wp = shortest_path(cast1.any[i].s, cast1.any[i].t1, cast1.any[i].b, g1);
//			g1.dropConnect(cast1.uni[i].wp);
//			cast1.any[i].bp = shortest_path(cast1.any[i].s, cast1.any[i].t2, cast1.any[i].b ,g1);
//			g1.copyGraph(g);
//			g1.updateGraph(cast1);
//			cast1.c = g1.Cost();
//			if(cast1.c < cast.c)
//			{
//				cast.copyCast(cast1);
//			}
			cast1.any[i].wp = shortest_path2(cast1.any[i].s, cast1.any[i].t1, cast1.any[i].b, g1);
			g1.dropConnect(cast1.uni[i].wp);
			cast1.any[i].bp = shortest_path2(cast1.any[i].s, cast1.any[i].t2, cast1.any[i].b ,g1);
			g1.copyGraph(g);
			g1.updateGraph(cast1);
			cast1.c = g1.Cost();
			if(cast1.c < cast.c)
			{
				cast.copyCast(cast1);
			}
		}
		return cast;
	}
}
