import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Main {
	public static void main(String[] args) {
		int max = 1;
		Graph graph;
		LocalSearch tmp = new LocalSearch();
		long t1, t2;
		File file = new File("test");
		String[] list = file.list();
		double best, worst, tmpDouble;
		double arr[] = new double[max];

		try {
			FileWriter fw = new FileWriter("E:\\result.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < list.length; i++) {
				graph = new Graph(file.getPath() + "\\" + list[i]);
				bw.write(list[i]);
				bw.write("\t");
				t1 = System.currentTimeMillis();
				for (int j = 0; j < max; j++) {
					arr[j] = tmp.run3(graph);
				}
				t2 = System.currentTimeMillis();
				tmpDouble = 0.0;
				best = 999999999999999999.0;
				worst = 0.0;
				for (int j = 0; j < max; j++) {
					tmpDouble += arr[j];
					if (arr[j] > worst)
						worst = arr[j];
					if (arr[j] < best)
						best = arr[j];
				}
				bw.write(best + "\t" + tmpDouble / max + "\t" + worst + "\t"
						+ (t2 - t1) / max);
				bw.newLine();
				bw.flush();
			}
			fw.close();
		} catch (Exception e) {
		}
	}
}
