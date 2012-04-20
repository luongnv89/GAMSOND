import java.awt.peer.SystemTrayPeer;




public class Main {
	public static void main(String[] args) {
		Graph graph;
		LocalSearch tmp = new LocalSearch();
		double tmpDouble;
		long t1, t2;

		graph = new Graph("instances_real\\USA6_4_2.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA6_4_4.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA6_4_5.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA6_4_6.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA8_6_2.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA8_6_4.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA8_6_5.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA8_6_6.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA8_6_8.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA8_6_10.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA8_6_12.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA8_6_14.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA8_6_15.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA10_8_2.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA10_8_4.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA10_8_5.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA10_8_6.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA10_8_8.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA10_8_10.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA10_8_12.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA10_8_14.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA10_8_15.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA10_8_16.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA10_8_18.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA10_8_20.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA10_8_25.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA12_10_2.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA12_10_4.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA12_10_5.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA12_10_6.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA12_10_8.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA12_10_10.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA12_10_12.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA12_10_14.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);

		graph = new Graph("instances_real\\USA12_10_15.txt");
		t1 = System.currentTimeMillis();
		tmp.run(graph);
		t2 = System.currentTimeMillis();
		tmpDouble = 0.0;
		for (int i = 0; i < 50; i++) {
			tmpDouble += tmp.run(graph);
		}
		System.out.println(tmpDouble/50);
		
//		graph = new Graph("instances_real\\USA12_10_18.txt");
//		t1 = System.currentTimeMillis();
//		System.out.println(tmp.run2(graph));
//		System.out.println(tmp.run1(graph));
//		System.out.println(tmp.run(graph));
//		t2 = System.currentTimeMillis();
//		tmpDouble = 0.0;
//		for (int i = 0; i < 50; i++) {
//			tmpDouble += tmp.run2(graph);
//		}
//		System.out.println(tmpDouble/50);
//		System.out.println(t2-t1);
	}
}
