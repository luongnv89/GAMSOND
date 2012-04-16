
public class Main {
	public static void main(String[] args) {
		Graph graph = new Graph("instances_alea\\a8_6_15.txt");
		LocalSearch tmp = new LocalSearch();
		
		tmp.run1(graph);
	}
}
