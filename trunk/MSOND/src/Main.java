
public class Main {
	public static void main(String[] args) {
		Graph graph = new Graph("bier100_95_150.txt");
		LocalSearch tmp = new LocalSearch();
		
		tmp.Run(graph);
	}
}
