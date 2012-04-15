package com.gamsnd.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import com.gamsnd.graph.CopyGraph;
import com.gamsnd.graph.CopyNode;
import com.gamsnd.object.Chromosome;
import com.gamsnd.object.Connection;
import com.gamsnd.object.Edge;
import com.gamsnd.object.Gen;
import com.gamsnd.object.Graph;
import com.gamsnd.object.Individual;
import com.gamsnd.object.Node;
import com.gamsnd.object.Population;
import com.gamsnd.object.Request;

public class Gamsond {

	// Khai bao cac thong so bai toan
	static int noRequest;// So request cua bai toan
	static int noNode;// So nut trong bai toan
	static int noEdge;// So canh cua do thi

	static ArrayList<Node> listNode = new ArrayList<Node>();
	// Danh sach dinh cua do thi
	static ArrayList<Request> listRequest = new ArrayList<Request>();
	// Danh sach cac request cua bai toan
	static ArrayList<Edge> listEdges = new ArrayList<Edge>();
	// Danh sach canh cua do thi
	static ArrayList<Connection> listConnections = new ArrayList<Connection>();
	// Danh sach cac connection
	static Random random = new Random();// doi tuong ngau nhien
	static Config config = new Config();// Cau hinh kich thuoc cua GA
	static int noIndividual; // Tong so co ca the sinh ra cua bai toan
	static Individual bestIndividual;// Ca the tot nhat cua bai toan
	// Duong dan toi file du lieu
	static String url = "instances_alea/a100_95_150.txt";

	public static void main(String args[]) {

		// Khai bao cac thong so dau vao

		/*------------------- Doc file dau vao, khoi tao Node, Edge, Request------------------------------------------------------*/

		readData(url);

		// Khoi tao do thi
		Graph graph = new Graph(listNode, listEdges);
		// KHOI TAO CAC CONNECTION

		for (int noConnect = 0; noConnect < noRequest; noConnect++) {
			Request tempRequest = listRequest.get(noRequest);
			Connection tempConnection = createConnection(graph, tempRequest);
			listConnections.add(tempConnection);
		}
		// Node source = listNode.get(0);

		/*------------------- Khoi tao quan the ------------------------------------------------------*/
		Population population = initPopulation();

		// SINH QUAN THE MOI
		for (int noGeneration = 0; noGeneration < config.GENERATION; noGeneration++) {

			// int generationID = population.getpopulationID() + 1;
			ArrayList<Individual> listNewIndividuals = new ArrayList<Individual>();

			/*------------------- Thuc hien lai ghep ------------------------------------------------------*/
			ArrayList<Integer> usedIndiv = new ArrayList<Integer>();
			for (int i = 0; i < Config.NOCROSS; i++) {

				RandomInteger random = new RandomInteger(noRequest, usedIndiv);
				// Chon ngau nhien 1 ca the chua lai ghep lam cha 1
				int pa1 = random.RandomGenerator2();
				usedIndiv.add(pa1);
				// Chon ngau nhien 1 ca the chua lai ghep lam cha 2
				int pa2 = random.RandomGenerator2();
				usedIndiv.add(pa2);

				Individual crossMiddleCut = MiddleCut(population
						.getListIndividual().get(pa1), population
						.getListIndividual().get(pa2));
				listNewIndividuals.add(crossMiddleCut);
			}

			/*------------------- Thuc hien dot bien ------------------------------------------------------*/

			// So ca the dot bien : NOMUTANT = 10
			for (int i = 0; i < Config.NOMUTANT; i++) {
				// Danh sach cac ca the da su dung de dot bien
				ArrayList<Integer> usedIndiv2 = new ArrayList<Integer>();
				RandomInteger random = new RandomInteger(noRequest, usedIndiv);
				// Chon ngau nhien 1 ca the de dot bien tu tap ca the con lai
				int idMutant = random.RandomGenerator2();
				Individual mutantIndividual = mutant(population
						.getListIndividual().get(idMutant));
				// Them ca the vua dot bien vao danh sach cac ca the da dot bien
				usedIndiv2.add(idMutant);
				listNewIndividuals.add(mutantIndividual);
			}

			/*------------------- Dau tranh sinh ton ------------------------------------------------------*/

			// Chon chien luoc gi????
			newPopulation(population, listNewIndividuals);
		}
	}

	// Ham khoi tao quan the
	public static Population initPopulation() {
		ArrayList<Individual> listIndividual = new ArrayList<Individual>();
		for (int j = 0; j < Config.POPULATION; j++) {
			// List gen cua NST working
			ArrayList<Gen> listGensw = new ArrayList<Gen>();
			// List gen cua NST backup
			ArrayList<Gen> listGensb = new ArrayList<Gen>();

			for (int k = 0; k < noRequest; k++) {
				// Lay ngau nhien 1 gen trong tap working path
				int idwGen = random.nextInt(listConnections.get(k)
						.getwConnection().size());
				// Lay ngau nhien 1 gen trong tap backup path tuong tung voi
				// working path da lay
				int idbGen = random.nextInt(listConnections.get(k)
						.getbConnection().get(idwGen).size());
				Gen wGen = listConnections.get(k).getwConnection().get(idwGen);
				Gen bGen = listConnections.get(k).getbConnection().get(idwGen)
						.get(idbGen);
				// add vao 2 NST
				listGensw.add(wGen);
				listGensb.add(bGen);
			}
			Chromosome wChromosome = new Chromosome(listGensw);
			Chromosome bChromosome = new Chromosome(listGensb);

			// TINH FITNESS CUA CA THE VOI 2 NST DA CHON LA : listGensw va
			// listGenb
			// Khoi tao 1 ca the moi
			Individual temIndividual = new Individual(j, wChromosome,
					bChromosome);
			calculateFitnessIndividual(temIndividual);
			// Nap ca the vua tao vao quan the
			listIndividual.add(temIndividual);
		}
		// Tao 1 quan the moi
		Population population = new Population(0, listIndividual);
		// Thiet lap so thu tu ca the duoc sinh ra
		noIndividual = listIndividual.size();
		bestIndividual = population.getBestIndividual();
		return population;
	}

	// Ham dot bien ngau nhien

	public static Individual mutant(Individual indiv) {

		int mutantPosition = random.nextInt(noRequest);

		// Thay gia tri gen se dot bien bang 1 gen khac
		// kich thuoc bo gen ung voi gen se dot bien
		int lisGenSize = indiv.getwChromosome().getListGens().size();
		// Chi so gen se dot bien
		int mutantGen = indiv.getwChromosome().getListGens()
				.get(mutantPosition).getId();

		// Chi so gen thay the
		RandomInteger replaceGen = new RandomInteger(lisGenSize, mutantGen);
		int replaceGenid = replaceGen.RandomGenerator();
		// Thay doi gia tri gen o vi tri dot bien tren working path
		indiv.getwChromosome().getListGens().get(mutantPosition)
				.setId(replaceGenid);

		// Chi so gen thay the tuong ung o backup
		int replaceBackupid = random.nextInt(listConnections
				.get(mutantPosition).getbConnection().get(replaceGenid).size());
		indiv.getbChromosome().getListGens().get(mutantPosition)
				.setId(replaceBackupid);

		calculateFitnessIndividual(indiv);
		// Tang so thu tu ca the sinh ra
		indiv.setID(noIndividual + 1);
		return indiv;
	}

	// Ham lai ghep 1 diem cat cho 2 ca the ngau nhien cua quan the
	private static Individual MiddleCut(Individual pa1, Individual pa2) {
		// TODO Auto-generated method stub
		// int p1 = random.nextInt(config.getPOPULATION());
		// Individual parent1 = population.getListIndividual().get(p1);
		// RandomInteger random2 = new RandomInteger(p1,
		// config.getPOPULATION());
		// int p2 = random2.RandomGenerator();
		// Individual parent2 = population.getListIndividual().get(p2);
		ArrayList<Gen> listGensw = new ArrayList<Gen>();
		Chromosome wChromosome = new Chromosome(listGensw);

		ArrayList<Gen> listGensb = new ArrayList<Gen>();
		Chromosome bChromosome = new Chromosome(listGensb);

		for (int i = 0; i <= noRequest / 2; i++) {
			Gen tempw = pa1.getwChromosome().getListGens().get(i);
			listGensw.add(tempw);

			Gen tempb = pa1.getbChromosome().getListGens().get(i);
			listGensb.add(tempb);
		}

		for (int i = noRequest / 2 + 1; i < noRequest; i++) {
			Gen tempw = pa2.getwChromosome().getListGens().get(i);
			listGensw.add(tempw);

			Gen tempb = pa2.getbChromosome().getListGens().get(i);
			listGensb.add(tempb);
		}

		Individual child = new Individual(noIndividual + 1, wChromosome,
				bChromosome);
		calculateFitnessIndividual(child);
		return child;
	}

	// Tinh fitness cho 1 ca the
	public static void calculateFitnessIndividual(Individual idiv) {
		double fitness = 0;
		for (int nowGen = 0; nowGen < idiv.getwChromosome().getListGens()
				.size(); nowGen++) {
			for (int nowEdge = 0; noEdge < idiv.getwChromosome().getListGens()
					.get(nowGen).getListEdge().size(); nowEdge++) {
				listEdges.get(nowEdge).setUsed(true);
			}
		}

		for (int nobGen = 0; nobGen < idiv.getbChromosome().getListGens()
				.size(); nobGen++) {
			for (int nobEdge = 0; noEdge < idiv.getbChromosome().getListGens()
					.get(nobGen).getListEdge().size(); nobEdge++) {
				listEdges.get(nobEdge).setUsed(true);
			}
		}

		for (int noUseEdge = 0; noUseEdge < noEdge; noUseEdge++) {
			if (listEdges.get(noUseEdge).isUsed() == true) {
				fitness = fitness + listEdges.get(noUseEdge).getWeight();
			}

		}
		// Tinh lai gia tri fitness cua ca the
		idiv.setFitness(fitness);
		// return fitness;
	}

	// Chon 2 ca the ngau nhien

	public static Population newPopulation(Population population,
			ArrayList<Individual> listIndividuals) {
		ArrayList<Individual> listTotal = new ArrayList<Individual>();
		ArrayList<Double> listFitness = new ArrayList<Double>();

		for (int i = 0; i < population.getListIndividual().size(); i++) {
			listTotal.add(population.getListIndividual().get(i));
			listFitness.add(population.getListIndividual().get(i).getFitness());
		}

		for (int j = 0; j < listIndividuals.size(); j++) {
			listTotal.add(listIndividuals.get(j));
			listFitness.add(population.getListIndividual().get(j).getFitness());
		}

		ArrayList<Individual> listTotalSorted = sortedPopulation(listTotal);
		for (int x = 0; x < listIndividuals.size(); x++) {
			listTotalSorted.remove(x);
		}

		Population tempPopulation = new Population(
				population.getpopulationID() + 1, listTotalSorted);
		return tempPopulation;
		// Collections.sort(listFitness);
	}

	// Sap xep cac ca the trong mot mang danh sach : tu Max-> Min

	public static ArrayList<Individual> sortedPopulation(
			ArrayList<Individual> listIndividuals) {
		// ArrayList<Integer> tem2 = list;
		ArrayList<Individual> tempList = new ArrayList<Individual>();
		while (listIndividuals.size() > 0) {
			double max = listIndividuals.get(0).getFitness();
			int maxid = 0;
			for (int i = 0; i < listIndividuals.size(); i++) {
				if (max < listIndividuals.get(i).getFitness()) {
					max = listIndividuals.get(i).getFitness();
					maxid = i;
				}
			}
			tempList.add(listIndividuals.get(maxid));
			listIndividuals.remove(maxid);
			// tem2 = selectSort();
		}
		return tempList;
	}

	// Tim id cua canh co 2 node a va b trong danh sach canh listEdge. Neu khong
	// thay tra ve gia tri id=-1

	public static int findEdge(Node a, Node b, ArrayList<Edge> listEdge) {
		int id = -1;
		for (int i = 0; i < listEdge.size(); i++) {
			if ((listEdge.get(i).getaNode() == a.getId())
					&& (listEdge.get(i).getbNode() == b.getId()))
				id = i;
		}
		return id;
	}

	// Tim duong di giua 2 node a va b trong do thi

	public static ArrayList<Edge> findPath(Node a, Node b,
			ArrayList<Edge> listEdge) {
		ArrayList<Edge> tempListEdge = new ArrayList<Edge>();
		int istrue = findEdge(a, b, listEdge);

		if (istrue != -1)
			// Canh noi giua 2 dinh bat ky
			tempListEdge.add(listEdge.get(istrue));
		else {

		}
		return tempListEdge;
	}

	// Tim tap duong di giua 2 node a va b

	public static ArrayList<Node> Dijkstra(Graph graph, Node source,
			Node destination) {
		ArrayList<Node> tempListNode = new ArrayList<Node>();
		for (int i = 0; i < graph.getListNode().size(); i++) {

		}
		return tempListNode;
	}

	// Khoi tao connection
	public static Connection createConnection(Graph graph, Request request) {
		// Tao danh sach gen Working path
		ArrayList<Gen> wConnection = new ArrayList<Gen>();

		// Loai bo cac dinh co trong path 2 de tim duong di cho path 1
		Graph wGraph = reGraph(graph, request.getPath2());
		// Thuat toan sinh tap gen
		// Tap duong di cho path 1
		wConnection = arrayGen(wGraph, request.getPath1());

		// Tao danh sach gen backup path tuong ung working path
		ArrayList<ArrayList<Gen>> bConnection = new ArrayList<ArrayList<Gen>>();
		for (int i = 0; i < wConnection.size(); i++) {
			// Thuc hien voi moi ket noi woking
			ArrayList<Gen> listBackupGen = new ArrayList<Gen>();
			// Tinh lai do thi
			Graph bGraph = reGraph(graph, genToArrayNode(wConnection.get(i)));
			// Thuat toan sinh tap gen
			listBackupGen = arrayGen(bGraph, request.getPath2());
			bConnection.add(listBackupGen);
		}
		// Tao 1 connection
		Connection tempConnection = new Connection(request.getId(),
				wConnection, bConnection);
		return tempConnection;
	}

	// Thuat toan sinh tap gen

	public static ArrayList<Gen> setPath(ArrayList<Edge> listEdge,
			ArrayList<Node> listNode, ArrayList<Node> path) {
		ArrayList<Gen> listGen = new ArrayList<Gen>();
		return listGen;
	}

	public static void readData(String url) {
		File file = new File(url);
		// Bat dau doc du lieu
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String s = in.readLine();// DIMENSIONS
			s = in.readLine();// 6 sommetsG2
			noNode = Integer.parseInt(s.split(" ")[0]);
			System.out.println(noNode);

			s = in.readLine();// 4 sommetsG1
			s = in.readLine();// 2 dem
			noRequest = Integer.parseInt(s.split(" ")[0]);
			System.out.println(noRequest);
			s = in.readLine();// space
			s = in.readLine();// SOMMETS

			// KHOI TAO CAC NODE
			// listNode = new Node[noNode];
			for (int i = 0; i < noNode; i++) {
				s = in.readLine();// 0 N0 C 288 149
				int id;
				String name;
				boolean isLogical;
				int x;
				int y;
				Node tempNode;
				id = Integer.parseInt(s.split(" ")[0]);
				// String tmp[] = s.split(" ");
				name = s.split(" ")[2];
				if (s.split(" ")[4].equals("T"))
					isLogical = false;
				else
					isLogical = true;
				x = Integer.parseInt(s.split(" ")[6]);
				y = Integer.parseInt(s.split(" ")[8]);

				// Add Node into list
				tempNode = new Node(x, y, id, name, isLogical);
				listNode.add(tempNode);
			}
			s = in.readLine(); // space
			s = in.readLine(); // DEMANDES

			// KHOI TAO CAC REQUEST
			// listRequest = new Request[noRequest];
			for (int i = 0; i < noRequest; i++) {
				Request request;
				int idoNode;
				int iddNode;
				// Node oNode;
				// Node dNode;
				int id = i;
				ArrayList<Integer> path1 = new ArrayList<Integer>();
				ArrayList<Integer> path2 = new ArrayList<Integer>();

				// oNode
				s = in.readLine(); // 4 1 (4 1 ) ( 4 3 1 )
				idoNode = Integer.parseInt(s.split(" ")[0]);
				// oNode = listNode.get(idoNode);

				// dNode
				iddNode = Integer.parseInt(s.split(" ")[1]);
				// dNode = listNode.get(iddNode);

				// PATH1
				String tempPath11 = s.split("\\(")[1];
				String[] tempPath1;
				tempPath1 = tempPath11.split(" ");
				for (int j = 1; j < tempPath1.length - 1; j++) {
					int idNode;
					idNode = Integer.parseInt(tempPath1[j]);
					path1.add(listNode.get(idNode).getId());
				}

				// PATH2
				String tempPath12 = s.split("\\(")[2];
				String[] tempPath2;
				tempPath2 = tempPath12.split(" ");
				for (int j = 1; j < tempPath2.length - 1; j++) {
					int idNode;
					idNode = Integer.parseInt(tempPath2[j]);
					path2.add(listNode.get(idNode).getId());
				}

				// Add request into list
				ArrayList<Node> pathNode1 = new ArrayList<Node>();
				for (int k = 0; i < path1.size(); k++) {
					pathNode1.add(listNode.get(path1.get(k)));
				}

				ArrayList<Node> pathNode2 = new ArrayList<Node>();
				for (int x = 0; i < path2.size(); x++) {
					pathNode2.add(listNode.get(path2.get(x)));
				}
				request = new Request(id, idoNode, iddNode, pathNode1,
						pathNode2);
				listRequest.add(request);

			}

			// KHOI TAO CAC CANH
			for (int i = 0; i < listNode.size(); i++) {
				for (int j = i + 1; j < listNode.size(); j++) {
					noEdge++;
					int aNode = listNode.get(i).getId();
					int bNode = listNode.get(j).getId();
					boolean used = false;
					double weight;
					int x1 = listNode.get(i).getX();
					int y1 = listNode.get(i).getY();
					int x2 = listNode.get(j).getX();
					int y2 = listNode.get(j).getY();
					weight = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2)
							* (y1 - y2));
					Edge tempEdge = new Edge(aNode, bNode, noEdge, used, weight);
					listEdges.add(tempEdge);
				}
			}

			// KHOI TAO DO THI
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// Chuyen do thi sang do thi CopyGraph
	public static CopyGraph copyGraph(Graph graph) {
		// CopyGraph tempCopyGraph;
		ArrayList<CopyNode> listCopyNode = new ArrayList<CopyNode>();
		for (int i = 0; i < graph.getListNode().size(); i++) {
			int id;
			CopyNode prevID = null;
			boolean visited = false;
			ArrayList<Integer> listClose = new ArrayList<Integer>();
			id = graph.getListNode().get(i).getId();
			// prevID = graph.getListNode().
			for (int j = 0; j < graph.getListEdge().size(); j++) {
				if (graph.getListEdge().get(j).getaNode() == id) {
					listClose.add(graph.getListEdge().get(j).getbNode());
				} else if (graph.getListEdge().get(j).getbNode() == id) {
					listClose.add(graph.getListEdge().get(j).getaNode());
				}

			}
			CopyNode tempNode = new CopyNode(id, visited, prevID, listClose);
			listCopyNode.add(tempNode);
		}
		CopyGraph tempCopyGraph = new CopyGraph(listCopyNode);
		return tempCopyGraph;
	}

	// Ket noi cac mang voi nhau
	public static ArrayList<Integer> linkPath(
			ArrayList<ArrayList<Integer>> setPath) {
		ArrayList<Integer> tempLinkPath = new ArrayList<Integer>();

		for (int i = 0; i < setPath.size(); i++) {
			for (int j = 0; j < setPath.get(i).size() - 1; j++) {
				tempLinkPath.add(setPath.get(i).get(j));
			}
		}
		return tempLinkPath;
	}

	// Tra lai 1 duong di qua tap cac nut cho truoc tren 1 do thi
	public static ArrayList<Integer> oneShortestPath(CopyGraph copyGraph,
			ArrayList<CopyNode> listNode) {
		ArrayList<Integer> temSet = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> tempSetPath = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < listNode.size(); i++) {
			ArrayList<Integer> tempPathPart = pathPart(copyGraph,
					listNode.get(i), listNode.get(i + 1));
			tempSetPath.add(tempPathPart);
			for (int j = 1; j < tempPathPart.size() - 1; j++) {
				copyGraph.getListNode().remove(j);
			}
		}
		temSet = linkPath(tempSetPath);
		return temSet;
	}

	// Tim duong di giua 2 diem trong do thi
	public static ArrayList<Integer> pathPart(CopyGraph copyGraph, CopyNode a,
			CopyNode b) {
		ArrayList<Integer> tempPath = new ArrayList<Integer>();
		// tempPath.add(a.getID());
		while (b.isVisited() == false) {
			for (int i = 0; i < a.getSetClose().size(); i++) {
				copyGraph.getListNode().get(a.getSetClose().get(i))
						.setVisited(true);
				copyGraph.getListNode().get(a.getSetClose().get(i))
						.setPrevID(a);
			}
			if (b.isVisited() == false) {
				for (int j = 0; j < a.getSetClose().size(); j++) {
					pathPart(
							copyGraph,
							copyGraph.getListNode().get(a.getSetClose().get(j)),
							b);
				}
			}
			// tempPath.ad
		}
		// temptPath.add(b.getID());
		ArrayList<Integer> invertTemp = new ArrayList<Integer>();
		CopyNode tempb = b;
		while (tempb.getID() == a.getID()) {
			invertTemp.add(tempb.getID());
			tempb = tempb.getPrevID();
		}
		tempPath.add(a.getID());
		for (int i = invertTemp.size() - 1; i >= 0; i--) {
			tempPath.add(invertTemp.get(i));
		}

		// for (int i = 0; i < a.getSetClose().size(); i++) {
		//
		// if (a.getSetClose().get(i) == b.getID()) {
		// tempPath.add(b.getID());
		// b.setPrevID(a);
		// tempPath.add(b.getPrevID().getID());
		// } else
		// while (a.getSetClose().get(i) != b.getID()) {
		// pathPart(
		// copyGraph,
		// copyGraph.getListNode().get(a.getSetClose().get(i)),
		// b);
		// }
		// // {
		// // pathPart(copyGraph,
		// // copyGraph.getListNode().get(a.getSetClose().get(i)), b);
		// // }
		// }
		return tempPath;
	}

	// Thuat toan sinh 1 tap duong di tren do thi di qua tap dinh cho truoc
	public static ArrayList<ArrayList<Integer>> setPath(CopyGraph copyGraph,
			ArrayList<CopyNode> listRequiredNode) {
		ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> tempPath = new ArrayList<Integer>();
		do {
			tempPath = oneShortestPath(copyGraph, listRequiredNode);
			temp.add(tempPath);
			int randomNode = random.nextInt(tempPath.size());
			copyGraph.getListNode().get(randomNode).getSetClose()
					.remove(tempPath.get(randomNode + 1));
		} while (!tempPath.isEmpty());
		return temp;
	}

	// public static ArrayList<Gen> setGen(Graph graph, ArrayList<Node>
	// listRequireNode){
	//
	// }

	public static Graph reGraph(Graph graph, ArrayList<Node> listRemoveNode) {
		ArrayList<Node> tempNode = graph.getListNode();
		ArrayList<Edge> tempEdge = graph.getListEdge();
		for (int i = 1; i < listRemoveNode.size() - 1; i++) {
			int temp = listRemoveNode.get(i).getId();
			// tempNode.remove(listRemoveNode.get(i));
			for (int j = 0; j < tempEdge.size(); j++) {
				if ((tempEdge.get(j).getaNode() == temp)
						|| ((tempEdge.get(j).getaNode() == temp))) {
					tempEdge.remove(j);
				}
			}
			tempNode.remove(temp);
		}
		Graph newGraph = new Graph(tempNode, tempEdge);
		return newGraph;
	}

	// public static ArrayList<CopyNode> copyNode(ArrayList<Node> listNode){
	//
	// }
	public static ArrayList<Gen> arrayGen(Graph graph,
			ArrayList<Node> listRequireNode) {
		CopyGraph copyGraphProcess = copyGraph(graph);
		ArrayList<CopyNode> listRequreCopyNode = new ArrayList<CopyNode>();
		for (int i = 0; i < listRequireNode.size(); i++) {
			listRequreCopyNode.add(copyGraphProcess.getListNode().get(
					listRequireNode.get(i).getId()));
		}

		ArrayList<ArrayList<Integer>> setPathfound = setPath(copyGraphProcess,
				listRequreCopyNode);
		ArrayList<Gen> temSetGen = integerParseGen(setPathfound);
		return temSetGen;
	}

	// Chuyen tu mang cac mang so nguyen thanh mang cac gen
	public static ArrayList<Gen> integerParseGen(
			ArrayList<ArrayList<Integer>> listInteger) {
		ArrayList<Gen> tempSetGen = new ArrayList<Gen>();
		for (int i = 0; i < listInteger.size(); i++) {
			ArrayList<Edge> listTempEdge = new ArrayList<Edge>();
			for (int j = 0; j < listInteger.get(i).size(); j++) {
				Edge tempEdge = findEdge(listInteger.get(i).get(j), listInteger
						.get(i).get(j + 1));
				listTempEdge.add(tempEdge);
			}
			Gen tempGen = new Gen(i, listTempEdge);
			tempSetGen.add(tempGen);
		}
		return tempSetGen;
	}

	public static Edge findEdge(int a, int b) {
		int mark = -1;
		int i = 0;
		while ((i < listEdges.size()) && (mark == -1)) {
			if ((listEdges.get(i).getaNode() == a)
					&& (listEdges.get(i).getbNode() == b)) {
				mark = i;
				i++;
			}
		}
		Edge tempEdge = listEdges.get(mark);
		return tempEdge;

	}

	public static ArrayList<Node> genToArrayNode(Gen gen) {
		ArrayList<Edge> listgenEdge = gen.getListEdge();
		ArrayList<Node> listgenNode = new ArrayList<Node>();
		listgenNode.add(listNode.get(listgenEdge.get(0).getaNode()));
		listgenNode.add(listNode.get(listgenEdge.get(0).getbNode()));
		for (int i = 1; i < listgenEdge.size(); i++) {
			if (!findNodeinArray(listgenEdge.get(i).getaNode(), listgenNode)) {
				listgenNode.add(listNode.get(listgenEdge.get(i).getaNode()));
			}
			if (!findNodeinArray(listgenEdge.get(i).getbNode(), listgenNode)) {
				listgenNode.add(listNode.get(listgenEdge.get(i).getbNode()));
			}
		}
		return listgenNode;

	}

	private static boolean findNodeinArray(int getbNode,
			ArrayList<Node> listgenNode) {
		// TODO Auto-generated method stub
		boolean result = false;
		for (int i = 0; i < listgenNode.size(); i++) {
			if (listgenNode.get(i).getId() == getbNode)
				result = true;
		}
		return result;
	}
}