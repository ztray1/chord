package kmaru.jchord;

import java.io.PrintStream;

public class Node {

	Node successor;

	FingerTable fingerTable;

	Store store;

	String nodeId;

	Key nodeKey;

	Node predecessor;

	public int num_of_visited;

	public Node(String nodeId) {
		num_of_visited = 0;
		this.store = new Store(nodeId);
		this.create();
		this.nodeId = nodeId;
		this.nodeKey = new Key(nodeId);
		this.fingerTable = new FingerTable(this);

	}

	public Node findSuccessor(String identifier) {
		Key key = new Key(identifier);
		return findSuccessor(key);
	}

	public Node findSuccessor(Key key) { // find the successor

		if (this == successor) {
			return this;
		}
		if (key.isBetween(this.GetKey(), successor.GetKey()) || key.compareTo(successor.GetKey()) == 0) {
			return successor;

		} else {
			Node node = closestPrecedingNode(key);
			if (node == this) {
				return successor.findSuccessor(key);
			}
			num_of_visited++;
			return node.findSuccessor(key);
		}
	}

	private Node closestPrecedingNode(Key key) { // find the router
															// table from front
															// to back
		for (int i = Hash.KEY_LENGTH - 1; i >= 0; i--) {
			Finger finger = fingerTable.GetFinger(i);
			Key fingerKey = finger.GetNode().GetKey();
			if (fingerKey.isBetween(this.GetKey(), key)) {
				return finger.GetNode();
			}
		}
		return this;
	}

	public void create() { // creating a new node
		predecessor = null;
		successor = this;
	}

	public void join(Node node) { // initializing the node ring
		predecessor = null;
		successor = node.findSuccessor(this.getNodeId());
	}

	public void stabilize() { // finding whether the successor is itself
		Node node = successor.getPredecessor();
		if (node != null) {
			Key key = node.GetKey();
			if ((this == successor) || key.isBetween(this.GetKey(), successor.GetKey())) {
				successor = node;
			}
		}
		successor.notifyPredecessor(this);
	}

	public void notifyPredecessor(Node node) { // make the node notify the
													// predecessor
		Key key = node.GetKey();
		if (predecessor == null || key.isBetween(predecessor.GetKey(), this.GetKey())) {
			predecessor = node;
		}
	}

	/**
	 * Refreshes finger table entries.
	 */
	public void fixFingers() { // update the router table
		for (int i = 0; i < Hash.KEY_LENGTH; i++) {
			Finger finger = fingerTable.GetFinger(i);
			Key key = nodeKey.createindexKey(i);
			// out.println("key"+i+" "+key);
			finger.SetNode(findSuccessor(key));
			// out.println("finger"+i+" "+finger.getNode().getNodeKey());
		}
	}

	public String toString() { // transfer the node information to string
		StringBuilder sb = new StringBuilder();
		sb.append("ChordNode[");
		sb.append("ID=" + nodeId);
		sb.append(",KEY=" + nodeKey);
		sb.append("]");
		return sb.toString();
	}

	public void printFingerTable(PrintStream out) { // print the finger table
		out.println("=======================================================");
		out.println("FingerTable: " + this);
		out.println("-------------------------------------------------------");
		out.println("Predecessor: " + predecessor);
		out.println("Successor: " + successor);
		out.println("-------------------------------------------------------");
		for (int i = 0; i < Hash.KEY_LENGTH; i++) {
			Finger finger = fingerTable.GetFinger(i);
			out.println("finger" + i + "\t" + finger.GetNode());
		}
		out.println("=======================================================");
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Key GetKey() {
		return nodeKey;
	}

	public void setNodeKey(Key nodeKey) {
		this.nodeKey = nodeKey;
	}

	public Node getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(Node predecessor) {
		this.predecessor = predecessor;
	}

	public Node getSuccessor() {
		return successor;
	}

	public void setSuccessor(Node successor) {
		this.successor = successor;
	}

	public FingerTable getFingerTable() {
		return fingerTable;
	}

	public void setFingerTable(FingerTable fingerTable) {
		this.fingerTable = fingerTable;
	}

}
