
public class Main {
	public static void main(String[] args) {
		Graph graph = new Graph("tsp100_95_150.txt");
		LocalSearch tmp = new LocalSearch();
		
		tmp.Run(graph);
	}
}
