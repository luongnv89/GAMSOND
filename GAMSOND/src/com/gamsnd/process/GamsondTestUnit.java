package com.gamsnd.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

import com.gamsnd.object.Chromosome;
import com.gamsnd.object.Connection;
import com.gamsnd.object.Edge;
import com.gamsnd.object.Gen;
import com.gamsnd.object.Individual;
import com.gamsnd.object.Node;
import com.gamsnd.object.Population;
import com.gamsnd.object.Request;

public class GamsondTestUnit {

	// Khai bao cac thong so bai toan
	static int noRequest = 0;// So request cua bai toan
	static int noNode = 0;// So nut trong bai toan
	static int noEdge = 0;// So canh cua do thi
	static int noNode2 = 0;// So nut tren do thi logical
	static double bestCost = 0;// Chi phi nho nhat

	static ArrayList<Node> listNode = new ArrayList<Node>();
	// Danh sach dinh cua do thi
	static ArrayList<Request> listRequest = new ArrayList<Request>();
	// Danh sach cac request cua bai toan
	static ArrayList<Edge> listEdges = new ArrayList<Edge>();
	// Danh sach canh cua do thi
	static ArrayList<Connection> listConnections = new ArrayList<Connection>();
	// Danh sach cac connection
	static ArrayList<Individual> listTotalIndividuals = new ArrayList<Individual>();
	static int noTotalIndividual = 0; // Tong so co ca the sinh ra cua bai toan
	static Individual bestIndividual;// Ca the tot nhat cua bai toan
	static int noGen = 0;// Tong so gen cua bai toan

	public static void main(String args[]) {

		long t1, t2;
		FileOutputStream result; // declare a file output object
		PrintStream p; // declare a print stream object

		String url = Config.DATAFILE;

		// Danh dau thoi gian tinh
		t1 = System.currentTimeMillis();

		// TODO DOC DU LIEU DAU VAO
		readData(url);
		String fileOuput = "result_" + noNode + "_" + noNode2 + "_" + noRequest
				+ ".txt";
		System.out.println("Hoan thanh doc du lieu dau vao");

		// TODO KHOI TAO CAC CONNECTION
		for (int noConnect = 0; noConnect < noRequest; noConnect++) {
			Request request = listRequest.get(noConnect);
			Connection newConnection = createConnection(listNode, request);
			listConnections.add(newConnection);
		}
		System.out.println("Hoan thanh khoi tao cac connection");

		// TODO KHOI TAO QUAN THE BAN DAU
		System.out.println("Bat dau khoi tao quan the");
		Population population = initPopulation();
		bestIndividual = population.getBestIndividual();
		bestCost = bestIndividual.getFitness();

		// Ghi thong tin quan the ban dau ra file
		try {
			// Create a new file output stream
			// connected to "myfile.txt"
			result = new FileOutputStream(fileOuput);
			// Connect print stream to the output stream
			p = new PrintStream(result);
			p.println(Config.DATAFILE);
			p.println(population.getpopulationID() + " " + bestCost);
			p.close();
		} catch (Exception e) {
			System.err.println("Error writing to file");
		}
		System.out.println("Hoan thanh khoi tao quan the");

		// TODO Test mot so ham
		// Test ham so sanh 2 ca the:
		// boolean testComparedIndi =
		// existIndividualInArray(listTotalIndividuals,
		// population.getBestIndividual());
		//
		// // Test ham lai ghep 2 ca the:
		// Individual newIndividual = population.getBestIndividual();
		// Individual newIndividual2 = population.getListIndividual().get(0);
		// Individual newIndividual3 = crossTwoIndividual(newIndividual,
		// newIndividual2);
		// while (existIndividualInArray(listTotalIndividuals, newIndividual3))
		// {
		// newIndividual3 = crossTwoIndividual(newIndividual, newIndividual2);
		// }

		// Test ham dot biet
		// Individual newIndividual = population.getBestIndividual();
		// Individual newIndividual2 = mutantedIndividual2(newIndividual);
		// int count = 0;
		// while (existIndividualInArray(listTotalIndividuals, newIndividual2))
		// {
		// newIndividual2 = mutantedIndividual2(newIndividual);
		// System.out.println("Mutant while " + count);
		// count++;
		// }
		// TODO SINH QUAN THE MOI
		System.out.println("Bat dau thuc hien sinh cac ca the moi");
		for (int noGeneration = 0; noGeneration < Config.GENERATION; noGeneration++) {

			ArrayList<Individual> listNewIndividuals = new ArrayList<Individual>();

			// TODO THUC HIEN LAI GHEP

			for (int i = 0; i < Config.NOCROSS; i++) {
				Individual crossMiddleCut = null;
				// int g = 0;
				boolean existIndi = true;
				while (existIndi == true) {
					// g++;
					// System.out.println("Hehe" + g);
					Random randomCross = new Random();
					// Chon ngau nhien 1 ca the lam cha 1
					int pa1 = randomCross.nextInt(population
							.getListIndividual().size());

					// Chon ngau nhien 1 ca the lam cha 2
					int pa2 = randomCross.nextInt(population
							.getListIndividual().size());
					// Kiem tra neu cha 2 bang cha 1 thi sinh lai cha 2
					while (pa2 == pa1) {
						pa2 = randomCross.nextInt(population
								.getListIndividual().size());
					}

					// Sinh 1 ca the moi
					crossMiddleCut = crossTwoIndividual(population
							.getListIndividual().get(pa1), population
							.getListIndividual().get(pa2));
					// Kiem tra su trung lap cua ca the vua sinh ra
					existIndi = existIndividualInArray(listTotalIndividuals,
							crossMiddleCut);
					if (existIndi == false) {
						calculateFitnessIndividual(crossMiddleCut);
					}
				}

				// System.out.println("Sinh(Lai ghep) duoc thang thu " + i);
				listNewIndividuals.add(crossMiddleCut);
				listTotalIndividuals.add(crossMiddleCut);
				noTotalIndividual++;
			}

			// TODO THUC HIEN DOT BIEN

			for (int i = 0; i < Config.NOMUTANT; i++) {

				// Chon ngau nhien 1 ca the de dot bien tu tap ca the cua quan
				// the
				Individual mutantIndividual = null;
				// boolean existIndi = false;
				// while (existIndi == true) {
				Random randomMutant = new Random();

				// Chon ngau nhien 1 ca the de dot bien
				int idMutant = randomMutant.nextInt(population
						.getListIndividual().size());

				// Sinh ca the moi
				mutantIndividual = mutantedIndividual2(population
						.getListIndividual().get(idMutant));

				// Kiem tra tinh trung lap
				// existIndi = existIndividualInArray(listTotalIndividuals,
				// mutantIndividual);
				// if (existIndi == false) {
//				calculateFitnessIndividual(mutantIndividual);
				// }
				// }

				// Them ca the vua dot bien vao danh sach cac ca the da dot bien
//				System.out.println("Dot bien duoc thang thu " + i);
				listNewIndividuals.add(mutantIndividual);
				listTotalIndividuals.add(mutantIndividual);
				noTotalIndividual++;
			}

			// TODO DAU TRANH SINH TON
			// Chon chien luoc giu lai cac ca the uu tu

			Population population2 = newPopulation(population,
					listNewIndividuals);

			// Cap nhat ca the tot nhat va gia tri tot nhat cua bai toan
			Individual tempBestIndividual = population2.getBestIndividual();
			if (tempBestIndividual.getFitness() < bestCost) {
				bestIndividual = tempBestIndividual;
				bestCost = tempBestIndividual.getFitness();
			}
			population = population2;
			//
			// System.out.println("Hoan thanh sinh quan the moi thu "
			// + (noGeneration + 1));

			// Ghi ket qua ra file thong ke
			try {
				result = new FileOutputStream(fileOuput, true);
				// Connect print stream to the output stream
				p = new PrintStream(result);
				// Ghi du lieu ket qua ra file
				p.println(population.getpopulationID() + " " + bestCost + ";");
				p.close();
			} catch (Exception e) {
				System.err.println("Error writing to file");
			}
		}

		System.out.println("Hoan thanh thuc hien lai ghep, dot bien");

		// Danh dau thoi gian ket thuc chuong trinh, va tinh thoi gian chay
		t2 = System.currentTimeMillis();
		long timeRun = t2 - t1;
		timeRun = (long) ((long) timeRun / 1000.0);

		// Ghi ket qua tot nhat cua bai toan
		try {
			// Create a new file output stream
			// connected to "myfile.txt"
			result = new FileOutputStream(fileOuput, true);
			// Connect print stream to the output stream
			p = new PrintStream(result);
			p.println(bestCost);
			// Ghi du lieu ket qua ra file
			p.println(timeRun);
			p.close();
		} catch (Exception e) {
			System.err.println("Error writing to file");
		}
		System.out.println("Done!");
	}

	// Thuc hien dot bien 1 ca the
	// Thuc hien phep dot bien
	public static Individual mutantedIndividual(Individual indiv) {
		// Random vi tri gen dot bien
		Individual newIndividual = indiv;
		double fitness = Double.POSITIVE_INFINITY;
		Random random1 = new Random();
		int mutantPosition = random1.nextInt(noRequest);
		// Thay gia tri gen se dot bien bang 1 gen khac
		// kich thuoc bo gen ung voi gen se dot bien
		// Lay gen dot bien
		Gen genMutant = indiv.getWorkingChromosome().getListGens()
				.get(mutantPosition);
		// Lay gen thay the tren gen working
		Gen genReplaceWorking = RandomGenFromArrayGen1(
				listConnections.get(mutantPosition).getWorkingSetConnection(),
				genMutant);
		// Lay chi so gen working trong tap gen working de lay tap gen backup
		// tuong ung
		int positionGenInArray = getIndexGenInArrayGen(
				listConnections.get(mutantPosition).getWorkingSetConnection(),
				genReplaceWorking);
		// Lay gen thay the tren gen Backup tuong ung
		Gen genReplaceBackup = RandomGenFromArrayGen(listConnections
				.get(mutantPosition).getBackupSetConnection()
				.get(positionGenInArray));
		// Thay the cac gen moi de tao ca the moi
		newIndividual.getWorkingChromosome().getListGens()
				.set(mutantPosition, genReplaceWorking);
		Chromosome workingChromosome = newIndividual.getWorkingChromosome();
		newIndividual.getBackupChromosome().getListGens()
				.set(mutantPosition, genReplaceBackup);
		Chromosome backupChromosome = newIndividual.getBackupChromosome();

		// calculateFitnessIndividual(newIndividual);
		// Tang so thu tu ca the sinh ra
		Individual newIndividual2 = new Individual(noTotalIndividual + 1,
				fitness, workingChromosome, backupChromosome);
		return newIndividual2;
	}

	// Sinh ca the moi

	// Thuc hien dau tranh sinh ton de tao quan the moi
	public static Population newPopulation(Population population,
			ArrayList<Individual> listNewIndividuals) {
		// Tao 1 mang thong nhat cac NST
		ArrayList<Individual> listTotal = new ArrayList<Individual>();
		listTotal.addAll(population.getListIndividual());
		listTotal.addAll(listNewIndividuals);

		// Thuc hien viec sap xep cac ca the trong mang theo thu tu giam dan cua
		// do fitness
		ArrayList<Individual> listTotalSorted = sortedPopulation(listTotal);

		// Loai bo so ca the dung bang so ca the moi duoc sinh ra
		for (int x = 0; x < listNewIndividuals.size(); x++) {
			listTotalSorted.remove(x);
		}

		// Khoi tao 1 quan the moi
		Population tempPopulation = new Population(
				population.getpopulationID() + 1, listTotalSorted);
		return tempPopulation;
		// Collections.sort(listFitness);
	}

	// Sap xep cac ca the trong quan the theo thu tu giam dan cua do fitness

	// Sap xep cac ca the trong NST theo do fitness
	public static ArrayList<Individual> sortedPopulation(
			ArrayList<Individual> listTotal) {
		// ArrayList<Integer> tem2 = list;

		ArrayList<Individual> listIndividualSorted = new ArrayList<Individual>();
		while (listTotal.size() > 0) {
			// Gia tri cao nhat cua do fitness trong quan the
			double max = listTotal.get(0).getFitness();
			// Chi so cua ca the co do fitness cao nhat
			int maxid = 0;

			// Tim ca the co do fitness cao nhat
			for (int i = 0; i < listTotal.size(); i++) {

				if (max < listTotal.get(i).getFitness()) {
					max = listTotal.get(i).getFitness();
					maxid = i;
				}

			}

			// Them ca the co do fitness cao nhat vao quan the moi
			listIndividualSorted.add(listTotal.get(maxid));
			// Loai bo quan the vua tim duoc khoi quan the ban dau
			listTotal.remove(maxid);
			// tem2 = selectSort();
		}
		return listIndividualSorted;
	}

	// Ham lai ghep 1 diem cat cho 2 ca the ngau nhien cua quan the

	// Thuc hien viec lai ghep 2 ca the
	public static Individual crossTwoIndividual(Individual pa1, Individual pa2) {

		// list gen cua NST working
		ArrayList<Gen> listGensw = new ArrayList<Gen>();
		// list gen cua NST backup
		ArrayList<Gen> listGensb = new ArrayList<Gen>();
		// Khoi tao do fitness cua ca the ban dau
		double fitness = Double.POSITIVE_INFINITY;

		Random cuRandom = new Random();
		// Vi tri diem cat ngau nhien: 1-> noRequest-1
		int cutPosition = cuRandom.nextInt(noRequest);
		if (cutPosition == 0)
			cutPosition = cutPosition + 1;
		if (cutPosition == noRequest - 1)
			cutPosition = cutPosition - 1;

		for (int i = 0; i <= cutPosition; i++) {
			// Gan doan working dau cua cha 1 cho con
			Gen tempw = pa1.getWorkingChromosome().getListGens().get(i);
			listGensw.add(tempw);
			// Gan doan backup dau cua cha 1 cho con
			Gen tempb = pa1.getBackupChromosome().getListGens().get(i);
			listGensb.add(tempb);
		}

		for (int i = cutPosition + 1; i < noRequest; i++) {
			// Gan doan working cuoi cua cha 2 cho con
			Gen tempw = pa2.getWorkingChromosome().getListGens().get(i);
			listGensw.add(tempw);
			// Gan doan backup cuoi cua cha 2 cho con
			Gen tempb = pa2.getBackupChromosome().getListGens().get(i);
			listGensb.add(tempb);
		}
		Chromosome wChromosome = new Chromosome(listGensw);
		Chromosome bChromosome = new Chromosome(listGensb);
		Individual child = new Individual(noTotalIndividual + 1, fitness,
				wChromosome, bChromosome);
		// calculateFitnessIndividual(child);
		return child;
	}

	// TODO Khoi tao quan the

	// Khoi tao quan the ban dau
	public static Population initPopulation() {
		ArrayList<Individual> listIndividual = new ArrayList<Individual>();
		for (int j = 0; j < Config.POPULATION; j++) {
			Individual tempIndividual = null;
			boolean existIndi = true;
			while (existIndi == true) {
				// Tao 1 ca the moi
				tempIndividual = createIndividualunFitness(
						noTotalIndividual + 1, listNode, listRequest);
				// Kiem tra su ton tai cua ca the trong cac ca the da tao ra
				existIndi = existIndividualInArray(listTotalIndividuals,
						tempIndividual);
				if (existIndi == false) {
					// Neu la ca the moi thi tinh do fitness
					calculateFitnessIndividual(tempIndividual);
				}
			}
			// Nap ca the moi tao ra vao list ca the cua quan the
			listIndividual.add(tempIndividual);
			// Nap ca the moi tao vao tap cac ca the cua bai toan sinh ra
			listTotalIndividuals.add(tempIndividual);
			noTotalIndividual++;
		}

		// Tao 1 quan the moi
		Population population = new Population(0, listIndividual);
		// Danh dau so luong ca the duoc sinh ra cua bai toan
		// noTotalIndividual = listIndividual.size();
		return population;
	}

	// Tinh do fitness cua 1 ca the

	// Tinh do fitness cua ca the
	public static void calculateFitnessIndividual(Individual indiv) {
		double fitness = 0;
		// Lay ra list cac canh su dung trong NST
		ArrayList<Edge> listUsedEdge = listEdgesInIndividual(indiv);
		// Tinh tong trong so cua ca the
		for (int k = 0; k < listUsedEdge.size(); k++) {
			double tempFitness = listUsedEdge.get(k).getWeight();
			fitness = tempFitness + fitness;
		}
		// Cap nhat gia tri fitness cho ca the
		indiv.setFitness(fitness);
	}

	// Doc du lieu dau vao khoi tao: Node, Edge, Request, Graph

	// Doc du lieu dau vao
	public static void readData(String url) {
		// url: duong dan toi du lieu
		File file = new File(url);
		// Bat dau doc du lieu
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));

			String s = in.readLine();// DIMENSIONS

			s = in.readLine();// 6 sommetsG2
			noNode = Integer.parseInt(s.split(" ")[0]);

			s = in.readLine();// 4 sommetsG1
			noNode2 = Integer.parseInt(s.split(" ")[0]);

			s = in.readLine();// 2 dem
			noRequest = Integer.parseInt(s.split(" ")[0]);

			s = in.readLine();// space

			s = in.readLine();// SOMMETS

			// TODO KHOI TAO CAC NODE
			int idNode;
			String nameNode;
			boolean isLogical;
			int x;
			int y;
			ArrayList<Node> setClose = new ArrayList<Node>();
			for (int i = 0; i < noNode; i++) {
				s = in.readLine();// 0 N0 C 288 149
				Node newNode;
				idNode = Integer.parseInt(s.split(" ")[0]);
				// String tmp[] = s.split(" ");
				nameNode = s.split(" ")[2];
				if (s.split(" ")[4].equals("T"))
					isLogical = false;
				else
					isLogical = true;
				x = Integer.parseInt(s.split(" ")[6]);
				y = Integer.parseInt(s.split(" ")[8]);

				// Add Node into list
				newNode = new Node(x, y, idNode, nameNode, isLogical, setClose);
				listNode.add(newNode);
			}

			// Cap nhat danh sach dinh ke. Do thi day du
			for (int i = 0; i < noNode; i++) {
				ArrayList<Node> setCloseCurrentNode = new ArrayList<Node>();
				setCloseCurrentNode.addAll(listNode);
				setCloseCurrentNode.remove(listNode.get(i));
				listNode.get(i).setSetClose(setCloseCurrentNode);
			}

			s = in.readLine(); // space
			s = in.readLine(); // DEMANDES

			// TODO KHOI TAO CAC REQUEST
			int idoNode;
			Node oNode;
			int iddNode;
			Node dNode;

			for (int i = 0; i < noRequest; i++) {

				ArrayList<Node> path1 = new ArrayList<Node>();
				ArrayList<Node> path2 = new ArrayList<Node>();

				s = in.readLine();
				// 4 1 ( 4 1 ) ( 4 3 1 )
				// oNode
				idoNode = Integer.parseInt(s.split(" ")[0]);
				oNode = listNode.get(idoNode);
				// dNode
				iddNode = Integer.parseInt(s.split(" ")[1]);
				dNode = listNode.get(iddNode);
				// PATH1: working path
				String workingpath = s.split("\\(")[1];
				// 4 1 )

				String[] tempPath1;
				tempPath1 = workingpath.split(" ");
				// -4-1-)
				int idNode2;
				for (int j = 1; j < tempPath1.length - 1; j++) {
					idNode2 = Integer.parseInt(tempPath1[j]);
					path1.add(listNode.get(idNode2));
				}

				String backupPath = s.split("\\(")[2];
				String[] tempPath2;
				tempPath2 = backupPath.split(" ");
				for (int j = 1; j < tempPath2.length - 1; j++) {
					idNode2 = Integer.parseInt(tempPath2[j]);
					path2.add(listNode.get(idNode2));
				}

				// Add request into list
				Request request = new Request(i, oNode, dNode, path1, path2);
				listRequest.add(request);
			}

			// //TODO KHOI TAO CAC CANH
			boolean used = false;
			double weight;
			for (int i = 0; i < listNode.size(); i++) {
				for (int j = i + 1; j < listNode.size(); j++) {

					// Xac dinh 2 dau mut cua canh
					Node aNode = listNode.get(i);
					Node bNode = listNode.get(j);
					int x1 = listNode.get(i).getX();
					int y1 = listNode.get(i).getY();
					int x2 = listNode.get(j).getX();
					int y2 = listNode.get(j).getY();
					double tempWeight = (x1 - x2) * (x1 - x2) + (y1 - y2)
							* (y1 - y2);
					// Tinh trong so cua canh
					weight = Math.sqrt(tempWeight);
					Edge tempEdge = new Edge(aNode, bNode, used, weight);
					listEdges.add(tempEdge);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// Ham tim duong di giua 2 duong tren do thi
	// Tim duong di giua 2 node
	public static ArrayList<Node> findPathTwoNode(
			ArrayList<Node> listRemainNode, Node a, Node b) {
		ArrayList<Node> foundPath = new ArrayList<Node>();
		// Lay ngau nhien 1 mang cac node trong listRemainNode de cho vao giua 2
		// node tao thanh duong di
		ArrayList<Node> arrayNodeMiddle = randomArrayNodeFromListNode(listRemainNode);
		if (arrayNodeMiddle == null) {
			// Truong hop khong them node, noi truc tiep 2 node
			foundPath.add(a);
			foundPath.add(b);
		} else {
			// Truong hop them 1 so node, thu tu noi la thu tu trong mang
			// foundPath
			foundPath.add(a);
			foundPath.addAll(arrayNodeMiddle);
			foundPath.add(b);
		}

		return foundPath;
	}

	// Random 1 node trong 1 danh sach cac node. Co the tra ve null

	// Lay 1 Node ngau nhien trong mang cac Node
	public static Node randomNodeFromArray(ArrayList<Node> listRemainNode) {
		// TODO Auto-generated method stub
		Random randomdd = new Random();
		Node randomNode;
		// Random chi so Node se lay ngau nhien trong mang cac Node
		int randomID = randomdd.nextInt(listRemainNode.size());
		randomNode = listRemainNode.get(randomID);
		return randomNode;
	}

	// Tim duong di qua nhieu nut trong do thi

	// Khoi tao gen
	public static ArrayList<Node> createGen(ArrayList<Node> listRemainNode,
			ArrayList<Node> listRequiredNodes) {

		// multiPath là duong di tong hop tu cac duong di giua 2 dinh
		ArrayList<Node> multiPath = new ArrayList<Node>();
		ArrayList<Node> PathTwoNode = new ArrayList<Node>();
		ArrayList<Node> listRemain = new ArrayList<Node>();
		listRemain.addAll(listRemainNode);

		for (int i = 0; i < listRequiredNodes.size() - 1; i++) {
			PathTwoNode = findPathTwoNode(listRemain, listRequiredNodes.get(i),
					listRequiredNodes.get(i + 1));
			PathTwoNode.remove(listRequiredNodes.get(i + 1));
			multiPath.addAll(PathTwoNode);
			PathTwoNode.remove(listRequiredNodes.get(i));
			listRemain.removeAll(PathTwoNode);
		}
		multiPath.add(listRequiredNodes.get(listRequiredNodes.size() - 1));
		return multiPath;
	}

	// Tim 1 tap duong di qua 1 tap cac nut trong do thi

	// Khoi tao mang gen
	public static ArrayList<Gen> createArrayGen(ArrayList<Node> listRemainNode,
			ArrayList<Node> listRequiredNode) {

		ArrayList<Gen> newMultiPath = new ArrayList<Gen>();

		int count = 0;
		int countGen = 0;
		// Viec tim duong se dung lai khi tim thay 10 duong , hoac duong tim
		// duoc bi lap lai 10 lan
		while ((count < 10) && (countGen < 10)) {
			ArrayList<Node> foundPathMultiNode = createGen(listRemainNode,
					listRequiredNode);
			// Kiem tra tinh lap lai cua duong tim duoc
			if (findGenInArrayGen(newMultiPath, foundPathMultiNode) == true) {
				count++;
			} else {
				// Tao 1 gen moi
				Gen newGen = new Gen(noGen, foundPathMultiNode);
				newMultiPath.add(newGen);
				noGen++;
				countGen++;
			}
		}

		return newMultiPath;
	}

	// Kiem tra su ton tai cua Gen trong mang Gen da co

	public static boolean findGenInArrayGen(ArrayList<Gen> newMultiPath,
			ArrayList<Node> newGen) {
		// TODO Auto-generated method stub
		boolean existGen = false;
		for (int i = 0; i < newMultiPath.size(); i++) {
			if (compareGen(newMultiPath.get(i).getGenNode(), newGen) == true) {
				existGen = true;
				return existGen;
			}
		}
		return existGen;
	}

	// So sanh 2 mang Gen voi nhau

	public static boolean compareGen(ArrayList<Node> gen, ArrayList<Node> newGen) {
		// TODO Auto-generated method stub
		boolean isEqual = true;
		for (int i = 0; i < gen.size(); i++) {
			if (gen.size() != newGen.size()) {
				isEqual = false;
				return isEqual;
			} else if (gen.get(i) != newGen.get(i)) {
				isEqual = false;
				return isEqual;
			}
		}
		return isEqual;
	}

	// Khoi tao 1 connection cua bai toan: gom co 1 tap cac duong working , voi
	// moi duong working se co 1 tap cac duong backup tuong ung
	// Khoi tao connection
	public static Connection createConnection(ArrayList<Node> listTotalNode,
			Request request) {
		// Cac thong so khoi tao
		int requestID = request.getId();
		ArrayList<Gen> workingSetConnection = new ArrayList<Gen>();
		ArrayList<ArrayList<Gen>> backupSetConnection = new ArrayList<ArrayList<Gen>>();

		// TODO KHOI TAO CAC GEN CHO NST WORKING

		// Xac dinh tap cac node co the co trong duong di working
		ArrayList<Node> listNodeRemain = new ArrayList<Node>();
		listNodeRemain.addAll(listTotalNode);
		listNodeRemain.removeAll(request.getPath1());
		listNodeRemain.removeAll(request.getPath2());

		// Tim tap duong di working
		workingSetConnection = createArrayGen(listNodeRemain,
				request.getPath1());
		for (int i = 0; i < workingSetConnection.size(); i++) {
			ArrayList<Gen> backupConnection2 = new ArrayList<Gen>();

			// Xac dinh tap cac node co the co trong duong di backup
			ArrayList<Node> listNodeRemainBackup = new ArrayList<Node>();
			listNodeRemainBackup.addAll(listNodeRemain);
			listNodeRemainBackup.removeAll(workingSetConnection.get(i)
					.getGenNode());

			// Tim tap duong di backup tuong ung voi duong di working thu i
			backupConnection2 = createArrayGen(listNodeRemain,
					request.getPath2());
			backupSetConnection.add(backupConnection2);
		}

		// Tao 1 connection
		Connection newConnection = new Connection(requestID,
				workingSetConnection, backupSetConnection);

		return newConnection;

	}

	// Tim 1 canh co 2 node a va b

	// Tim canh co 2 node a va b
	public static Edge getEdgeFromListEdge(Node a, Node b) {
		Edge newEdgeOK = null;

		for (int i = 0; i < listEdges.size(); i++) {
			Edge newEdge = listEdges.get(i);
			if (((newEdge.getaNode() == a) && (newEdge.getbNode() == b))
					|| ((newEdge.getaNode() == b) && (newEdge.getbNode() == a))) {
				newEdgeOK = newEdge;
			}
		}
		return newEdgeOK;
	}

	// Khoi tao 1 ca the

	// Tao 1 Ca the
	public static Individual createIndividualunFitness(int id,
			ArrayList<Node> listNodeTotal, ArrayList<Request> listRequest) {
		double fitness = Double.POSITIVE_INFINITY;
		// List gen cua NST working
		ArrayList<Gen> listGensw = new ArrayList<Gen>();
		// List gen cua NST backup
		ArrayList<Gen> listGensb = new ArrayList<Gen>();

		Random random2 = new Random();
		// Chon ngau nhien 1 gen working, 1 gen backup cho moi request
		for (int k = 0; k < noRequest; k++) {
			// Lay ngau nhien 1 gen trong tap working path
			// Kich thuoc tap working path
			int workingSize = listConnections.get(k).getWorkingSetConnection()
					.size();
			// Chi so ngau nhien cho working gen
			int indexGenRandomInWorking = random2.nextInt(workingSize);
			Gen wGen = listConnections.get(k).getWorkingSetConnection()
					.get(indexGenRandomInWorking);
			// Lay ngau nhien 1 gen trong tap backup path tuong ung
			// Kich thuoc tap backup path
			int backupSize = listConnections.get(k).getBackupSetConnection()
					.get(indexGenRandomInWorking).size();
			// Chi so ngau nhien gen backup
			int indexGenRandomInBackup = random2.nextInt(backupSize);
			Gen bGen = listConnections.get(k).getBackupSetConnection()
					.get(indexGenRandomInWorking).get(indexGenRandomInBackup);
			// add vao 2 NST
			listGensw.add(wGen);
			listGensb.add(bGen);
		}
		Chromosome wChromosome = new Chromosome(listGensw);
		Chromosome bChromosome = new Chromosome(listGensb);
		// Khoi tao 1 ca the moi
		Individual newIndividual = new Individual(id, fitness, wChromosome,
				bChromosome);
		return newIndividual;
	}

	// lay 1 gen ngau nhien trong listGen

	public static Gen getRandomGen(ArrayList<Gen> listGen) {
		Random random3 = new Random();
		int idGen = random3.nextInt(listGen.size());
		Gen newGen = listGen.get(idGen);
		return newGen;
	}

	// Lay danh sach canh su dung trong 1 gen
	public static ArrayList<Edge> listEdgesInGen(Gen gen) {
		ArrayList<Edge> listEdge = new ArrayList<Edge>();
		for (int i = 0; i < gen.getGenNode().size() - 1; i++) {
			Edge aEdge = getEdgeFromListEdge(gen.getGenNode().get(i), gen
					.getGenNode().get(i + 1));
			listEdge.add(aEdge);
		}
		return listEdge;
	}

	// Lay danh sach cac canh su dung trong listGen
	public static ArrayList<Edge> listEdgesInArrayGen(ArrayList<Gen> listGen) {
		ArrayList<Edge> listEdgeTotal = new ArrayList<Edge>();
		for (int i = 0; i < listGen.size(); i++) {
			ArrayList<Edge> aArrayEdges = listEdgesInGen(listGen.get(i));
			listEdgeTotal = composeArrayEdges(listEdgeTotal, aArrayEdges);
		}
		return listEdgeTotal;
	}

	// Tim danh sach canh cua 1 ca the
	public static ArrayList<Edge> listEdgesInIndividual(Individual individual) {
		// Danh sach canh tren Working path
		ArrayList<Edge> listEdgesWorking = listEdgesInArrayGen(individual
				.getWorkingChromosome().getListGens());
		// Danh sach canh tren Backup path
		ArrayList<Edge> listEdgesBackup = listEdgesInArrayGen(individual
				.getBackupChromosome().getListGens());
		// Tong hop danh sach canh su dung trong ca the
		ArrayList<Edge> listEdgesIndividual = composeArrayEdges(
				listEdgesWorking, listEdgesBackup);
		return listEdgesIndividual;
	}

	// Tong hop 2 list canh, tra ve 1 list canh chua cac canh duy nhat
	public static ArrayList<Edge> composeArrayEdges(ArrayList<Edge> listEdge1,
			ArrayList<Edge> listEdge2) {
		ArrayList<Edge> listCompose = new ArrayList<Edge>();
		listCompose.addAll(listEdge1);
		for (int i = 0; i < listEdge2.size(); i++) {
			if (findEdgeInArray(listEdge1, listEdge2.get(i)) == false) {
				listCompose.add(listEdge2.get(i));
			}
		}
		return listCompose;
	}

	// Kiem tra su ton tai cua 1 canh trong mot list canh da co
	public static boolean findEdgeInArray(ArrayList<Edge> listEdge, Edge edge) {
		boolean existEdge = false;
		for (int i = 0; i < listEdge.size(); i++) {
			if (compareEdge(listEdge.get(i), edge) == true) {
				existEdge = true;
				return existEdge;
			}
		}
		return existEdge;
	}

	// So sanh 2 canh
	public static boolean compareEdge(Edge edge1, Edge edge2) {
		boolean onEdge = false;
		if (((edge1.getaNode() == edge2.getaNode()) && (edge1.getbNode() == edge2
				.getbNode()))
				|| ((edge1.getaNode() == edge2.getbNode()) && (edge1.getbNode() == edge2
						.getaNode())))
			onEdge = true;
		return onEdge;
	}

	// Lay ngau nhien 1 so nguyen trong mang cho truoc
	public static int RandomIntArray(ArrayList<Integer> array) {
		Random randomNew = new Random();
		int idRandom = randomNew.nextInt(array.size());
		int temp = array.get(idRandom);
		array.remove(array.get(idRandom));
		return temp;
	}

	// Lay ngau nhien 1 gen trong list gen
	public static Gen RandomGenFromArrayGen(ArrayList<Gen> listGen) {
		Random randomPosition = new Random();
		int position = randomPosition.nextInt(listGen.size());
		Gen tempGen = listGen.get(position);
		return tempGen;
	}

	// Lay ngau nhien 1 gen trong listGen
	public static Gen RandomGenFromArrayGen1(ArrayList<Gen> listGen, Gen gen) {
		ArrayList<Gen> listGenTemp = new ArrayList<Gen>();
		listGenTemp.addAll(listGen);
		listGenTemp.remove(gen);
		Gen temp = RandomGenFromArrayGen(listGenTemp);
		return temp;
	}

	// Lay chi so cua gen trong mang Gen
	public static int getIndexGenInArrayGen(ArrayList<Gen> listGen, Gen gen) {
		int position = 0;
		for (int i = 0; i < listGen.size(); i++) {
			if (gen.getId() == listGen.get(i).getId()) {
				position = i;
				return position;
			}
		}
		return position;
	}

	// So sanh 2 ca the
	public static boolean compareIndividual(Individual a, Individual b) {
		boolean isEqual = true;
		for (int i = 0; i < noRequest; i++) {
			// Voi moi request
			// Lay gen tren NST working cua ca the a
			Gen aWorkingGen = a.getWorkingChromosome().getListGens().get(i);
			// Lay gen tren NST backup cua ca the a
			Gen aBackupGen = a.getBackupChromosome().getListGens().get(i);
			// Lay gen tren NST working cua ca the b
			Gen bWorkingGen = b.getWorkingChromosome().getListGens().get(i);
			// Lay gen tren NST backup cua ca the b
			Gen bBackupGen = b.getBackupChromosome().getListGens().get(i);
			// Thuc hien viec so sanh, neu phat hien ra 1 vi tri sai thi ket
			// luan 2 ca the khong bang nhau
			// So sanh tren NST working
			if (compareGen(bWorkingGen.getGenNode(), aWorkingGen.getGenNode()) == false) {
				isEqual = false;
				return isEqual;
			} else
			// Neu gen working bang nhau, so sanh toi gen backup
			if (compareGen(aBackupGen.getGenNode(), bBackupGen.getGenNode()) == false) {
				isEqual = false;
				return isEqual;
			}
		}
		return isEqual;
	}

	// Kiem tra su lap lai cua ca the moi sinh ra
	public static boolean existIndividualInArray(
			ArrayList<Individual> listTotalIndividuals, Individual individual) {
		boolean existIndi = false;
		for (int i = 0; i < noTotalIndividual; i++) {
			if (compareIndividual(individual, listTotalIndividuals.get(i)) == true) {
				existIndi = true;
				return existIndi;
			}
		}
		return existIndi;
	}

	public static ArrayList<Node> randomArrayNodeFromListNode(
			ArrayList<Node> listNode) {
		ArrayList<Node> newArrayNode = new ArrayList<Node>();
		Random randomD = new Random();
		// Sinh ngau nhien so node cua mang
		int noNodeOfArray = randomD.nextInt(listNode.size());
		if (noNodeOfArray == 0)
			// Neu so ngau nhien bang 0, tra lai mot mang rong
			newArrayNode = null;
		else {
			for (int i = 0; i < noNodeOfArray; i++) {
				// Lay ngau nhien 1 node trong listNode
				Node temp = randomNodeFromArray(listNode);
				// Them vao mang node moi
				newArrayNode.add(temp);
				// Loai bo khoi tap listNode
				listNode.remove(temp);
			}
		}
		return newArrayNode;

	}

	public static Individual mutantedIndividual2(Individual indiv) {

		Individual newIndividual = indiv;
		Individual newIndividual2 = null;
		double fitness = Double.POSITIVE_INFINITY;
		Random random1 = new Random();
		// Random vi tri gen dot bien
		int mutantPosition = random1.nextInt(noRequest);
		// Thay gia tri gen se dot bien bang 1 gen khac
		// kich thuoc bo gen ung voi gen se dot bien
		// Lay gen dot bien
		Gen genWorking = indiv.getWorkingChromosome().getListGens()
				.get(mutantPosition);
		// Lay ra tap gen working co the thay the duoc
		ArrayList<Gen> workingSetArrayGen = new ArrayList<Gen>();
		workingSetArrayGen.addAll(listConnections.get(mutantPosition)
				.getWorkingSetConnection());
		int positionGenWorkingInArray = getIndexGenInArrayGen(workingSetArrayGen,
				genWorking);
		workingSetArrayGen.remove(workingSetArrayGen.get(positionGenWorkingInArray));
		// Lay ra tap gen backup co the thay the duoc tuong ung
		ArrayList<ArrayList<Gen>> backupSetArrayArrayGen = new ArrayList<ArrayList<Gen>>();
		backupSetArrayArrayGen.addAll(listConnections.get(mutantPosition)
				.getBackupSetConnection());
		backupSetArrayArrayGen.remove(backupSetArrayArrayGen.get(positionGenWorkingInArray));
		int c = 0;
		while (workingSetArrayGen.size() > 0) {
			c++;
			System.out
					.println("Chon 1 gen ngau nhien tren NST working lan thu "
							+ c);
			// Lay ngau nhien 1 gen de thay the gen working
			Gen genReplaceWorking = RandomGenFromArrayGen(workingSetArrayGen);
			
//			workingSetArrayGen.remove(genWorking);
			
			backupSetArrayArrayGen.remove(backupSetArrayArrayGen.get(positionGenWorkingInArray));
			
			
			// Lay chi so gen working thay the trong tap gen working
			int positionGenInArray = getIndexGenInArrayGen(workingSetArrayGen,
					genReplaceWorking);
			workingSetArrayGen.remove(genReplaceWorking);
			// Lay tap gen backup phu hop voi gen working da lay
			ArrayList<Gen> backupSetGen = new ArrayList<Gen>();
			backupSetGen.addAll(backupSetArrayArrayGen.get(positionGenInArray));
			backupSetArrayArrayGen.remove(backupSetArrayArrayGen.get(positionGenInArray));
			// Lay gen thay the tren gen Backup tuong ung
			// Ngau nhien chi so thay the cua gen backup
			// int k = 0;
			boolean exist = true;
			while (((backupSetGen.size() > 0) && (exist == true)) == true) {

//				System.out.println("Chon 1 gen ngau nhien trong tap backup");
				// Chon gen dau tien trong tap backup
				Gen genBackup = backupSetGen.get(0);
				backupSetGen.remove(genBackup);
				newIndividual.getWorkingChromosome().getListGens()
						.set(mutantPosition, genReplaceWorking);
				Chromosome workingChromosome = newIndividual
						.getWorkingChromosome();
				newIndividual.getBackupChromosome().getListGens()
						.set(mutantPosition, genBackup);
				Chromosome backupChromosome = newIndividual
						.getBackupChromosome();
				// calculateFitnessIndividual(newIndividual);
				// Tang so thu tu ca the sinh ra
				newIndividual2 = new Individual(noTotalIndividual + 1, fitness,
						workingChromosome, backupChromosome);
				exist = existIndividualInArray(listTotalIndividuals,
						newIndividual2);
				if (exist == false) {
					calculateFitnessIndividual(newIndividual2);
					return newIndividual2;
					// mutantedIndividual(newIndividual2) = newIndividual2;
				}
				// Thay the cac gen moi de tao ca the moi
			}

		}
		return newIndividual2;
	}

}