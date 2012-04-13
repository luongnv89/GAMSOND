package com.gamsnd.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.gamsnd.object.Chromosome;
import com.gamsnd.object.Connection;
import com.gamsnd.object.Edge;
import com.gamsnd.object.Gen;
import com.gamsnd.object.Individual;
import com.gamsnd.object.Node;
import com.gamsnd.object.Population;
import com.gamsnd.object.Request;

public class Gamsond {

	// Khai bao cac thong so bai toan
	static int noRequest;
	static int noNode;
	static int noEdge = -1;

	static ArrayList<Node> listNode = new ArrayList<Node>();
	static ArrayList<Request> listRequest = new ArrayList<Request>();
	static ArrayList<Edge> listEdges = new ArrayList<Edge>();
	static ArrayList<Connection> listConnections = new ArrayList<Connection>();
	static Random random = new Random();
	static Config config = new Config();
	static int noIndividual;
	static Individual bestIndividual;

	public static void main(String args[]) {

		// Khai bao cac thong so dau vao

		/*------------------- Doc file dau vao, khoi tao Node, Edge, Request------------------------------------------------------*/

		// Duong dan toi file du lieu
		String url = "instances_alea/a100_95_150.txt";
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
				request = new Request(id, idoNode, iddNode, path1, path2);
				listRequest.add(request);

			}

			// TEST NODE INPUT

			FileOutputStream out;
			PrintStream p;
			try {
				out = new FileOutputStream("myFile.txt");
				p = new PrintStream(out);
				p.println("Cac node trong do thi");
				for (int i = 0; i < listNode.size(); i++) {
					p.println(listNode.get(i).getId() + "	"
							+ listNode.get(i).getName() + " "
							+ listNode.get(i).isLogical() + " "
							+ listNode.get(i).getX() + " "
							+ listNode.get(i).getY());
				}

				// TEST REQUEST INPUT
				// System.out.println(listNode);
				p.println("Cac Request trong do thi");
				p.println(listRequest.size());
				for (int i = 0; i < listRequest.size(); i++) {
					p.println(listRequest.get(i).getoNode() + " "
							+ listRequest.get(i).getdNode());
					for (int j = 0; j < listRequest.get(i).getPath1().size(); j++) {
						p.print(listRequest.get(i).getPath1().get(j).toString()
								+ "-");
					}
					p.println("----");
					for (int j = 0; j < listRequest.get(i).getPath2().size(); j++) {
						p.print(listRequest.get(i).getPath2().get(j).toString()
								+ "-");
					}

					p.println("----");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		/*------------------- Khoi tao cac canh ------------------------------------------------------*/
		// CREATE CAC CANH CUA DO THI
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

		// TEST CAC CANH
		FileOutputStream out;
		PrintStream p;
		try {
			out = new FileOutputStream("Edges.txt");
			p = new PrintStream(out);
			p.println("ID	" + "aNode	" + "bNode	" + "weight	" + "used");
			for (int i = 0; i < listEdges.size(); i++) {

				p.println(listEdges.get(i).getEdgeID() + "    "
						+ listEdges.get(i).getaNode() + "    "
						+ listEdges.get(i).getbNode() + "    "
						+ listEdges.get(i).getWeight() + "    "
						+ listEdges.get(i).isUsed());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		// KHOI TAO CAC CONNECTION

		for (int noConnect = 0; noConnect < noRequest; noConnect++) {
			ArrayList<Gen> tempWork = new ArrayList<Gen>();
			ArrayList<ArrayList<Gen>> tempBackup = new ArrayList<ArrayList<Gen>>();
			Connection tempConnection = new Connection(noConnect, tempWork,
					tempBackup);
			listConnections.add(tempConnection);
		}
		// Node source = listNode.get(0);
		/*------------------- Khoi tao gen Dijkstra------------------------------------------------------*/

		/*------------------- Khoi tao ca the ------------------------------------------------------*/

		/*------------------- Khoi tao quan the ------------------------------------------------------*/
		Population population = initPopulation();

		// SINH QUAN THE MOI
		for (int noGeneration = 0; noGeneration < config.GENERATION; noGeneration++) {

			// int generationID = population.getpopulationID() + 1;
			ArrayList<Individual> listNewIndividuals = new ArrayList<Individual>();

			/*------------------- Thuc hien lai ghep ------------------------------------------------------*/
			ArrayList<Integer> usedIndiv = new ArrayList<Integer>();
			for (int i = 0; i < config.NOCROSS; i++) {

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
			for (int i = 0; i < config.NOMUTANT; i++) {
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
		for (int j = 0; j < config.POPULATION; j++) {
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

	
	public static int findEdge(Node a, Node b, ArrayList<Edge> listEdge){
		int id = -1;
		for(int i=0;i<listEdge.size();i++){
			if((listEdge.get(i).getaNode()==a.getId())&&(listEdge.get(i).getbNode()==b.getId())) id = i;
		}
		return id;
	}
	
	public static ArrayList<Edge> findPath(Node a, Node b, ArrayList<Edge> listEdge){
		ArrayList<Edge> tempListEdge = new ArrayList<Edge>();
		int istrue = findEdge(a, b, listEdge);
		if(istrue!=-1) tempListEdge.add(listEdge.get(istrue));
		else{
			
		}
		return tempListEdge;
	}
}
