
public class Main {
	public static void main(String[] args) {
		Graph graph = new Graph("a12_10_15.txt");
		LocalSearch tmp = new LocalSearch();
		
		tmp.run1(graph);
	}
}
