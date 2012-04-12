
public class Main {
	public static void main(String[] args) {
		Graph graph = new Graph("a6_4_2.txt");
		LocalSearch tmp = new LocalSearch();
		
		tmp.run(graph);
	}
}
