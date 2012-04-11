import java.sql.Time;

public class Main {
//	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Time time1, time2; 
		String s = "polska/";
		LocalSearch tabu = new LocalSearch();
		Graph g = new Graph();
		Graph graph = new Graph("polska.txt");
		for(int i = 1; i <= 10; i++) {
			Cast cast = new Cast(s + i + "/unicast.txt", s + i + "/anycast.txt", s + i + "/replica.txt");
			tabu.initConnect(graph, cast);
			g.copyGraph(graph);
			g.updateGraph(cast);
//			g.printBandWidth();
			cast.c = g.Cost();
			time1 = new Time(System.currentTimeMillis());
			tabu.FBB(graph, cast);
//			tabu.tabuSearch(graph, cast);
//			int num = 0;
//			int tmp = 0;
//			int j = 0;
//			List tl = new List();
//			Cast[] c = new Cast[1000];
//			do{
//				do {
//					tl.setList(cast);
//					tmp = cast.c;
////					tabu.tabuSearch3(graph, cast);//thay đổi phương thức thực hiện tabusearch tương ứng là 1, 2, 3
//					tabu.FBB(graph, cast);
//				} while(tmp > cast.c);
//				cast.copyCast(tl.getList());
//				c[num] = new Cast();
//				c[num++].copyCast(cast);
//				if(tl.num == 0)
//					break;
//				cast.copyCast(tl.getList());
//				cast.copyCast(tabu.backtrack(cast, graph));
//				j++;
//			}while(j < 15);
//			for(j = 0; j < num; j++) {
//				if(cast.c >= c[j].c) {
//					cast.copyCast(c[j]);
//				}
//			}
			time2 = new Time(System.currentTimeMillis());
//			System.out.print(time2.getSeconds()-time1.getSeconds() + (time2.getMinutes() - time1.getMinutes())*60 + "\t");
			System.out.print(cast.c + "\t");
		}
	}
}
