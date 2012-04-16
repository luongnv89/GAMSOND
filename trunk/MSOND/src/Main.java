
public class Main {
	public static void main(String[] args) {
		Graph graph;
		LocalSearch tmp = new LocalSearch();
		double tmpDouble;
		
		graph = new Graph("instances_alea\\a12_10_15.txt");
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);
	}
}
