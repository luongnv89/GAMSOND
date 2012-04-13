package com.gamsnd.object;

import java.util.ArrayList;

public class Graph {
	private ArrayList<Node> listNode;
	private ArrayList<Edge> listEdge;

	public ArrayList<Node> getListNode() {
		return listNode;
	}

	public void setListNode(ArrayList<Node> listNode) {
		this.listNode = listNode;
	}

	public ArrayList<Edge> getListEdge() {
		return listEdge;
	}

	public void setListEdge(ArrayList<Edge> listEdge) {
		this.listEdge = listEdge;
	}

	public Graph(ArrayList<Node> listNode, ArrayList<Edge> listEdge) {
		// super();
		this.listNode = listNode;
		this.listEdge = listEdge;
	}

	public Graph GraphRemoveNode(Node aNode) {
		ArrayList<Node> tempListNode = new ArrayList<Node>();
		ArrayList<Edge> tempEdges = new ArrayList<Edge>();

		for (int i = 0; i < listNode.size(); i++) {
			if (listNode.get(i).getId() != aNode.getId()) {
				tempListNode.add(listNode.get(i));
			}
		}

		for (int j = 0; j < listEdge.size(); j++) {
			if ((listEdge.get(j).getaNode() != aNode.getId() && (listEdge
					.get(j).getbNode() != aNode.getId()))) {
				tempEdges.add(listEdge.get(j));
			}
		}

		Graph tempGraph = new Graph(tempListNode, tempEdges);
		return tempGraph;
	}
}
