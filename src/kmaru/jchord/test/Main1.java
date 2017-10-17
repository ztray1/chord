package kmaru.jchord.test;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.URL;

import kmaru.jchord.Chord;
import kmaru.jchord.ChordException;
import kmaru.jchord.Node;
import kmaru.jchord.Client;
import kmaru.jchord.Hash;

public class Main1 {

	public static final String HASH_FUNCTION = "SHA-1";

	public static final int KEY_LENGTH = 160;

	public static final int NUM_OF_NODES = 64;

	public static int count;

	public static void main(String[] args) throws Exception {

		PrintStream out = System.out;

		out = new PrintStream("result.log");

		String host = InetAddress.getLocalHost().getHostAddress();
		int port = 9000;

		Hash.setFunction(HASH_FUNCTION);
		Hash.setKeyLength(KEY_LENGTH);

		count = 0;
		Chord chord = new Chord();
		for (int i = 0; i < NUM_OF_NODES; i++) {
			URL url = new URL("http", host, port + i, "");
			try {
				chord.createNode(url.toString());
			} catch (ChordException e) {
				e.printStackTrace();
				System.exit(0);
			}

		}
		out.println(NUM_OF_NODES + " nodes are created.");

		for (int i = 0; i < NUM_OF_NODES; i++) {
			Node node = chord.GetSortedNode(i);
			out.println(node);
		}
		for (int i = 1; i < NUM_OF_NODES; i++) {
			Node node = chord.getNode(i);
			node.join(chord.getNode(0));// node[i] will join the system with the
										// help of node[1]
			node.fixFingers();
			node.getSuccessor().notifyPredecessor(node);// the successor of
														// node[i] notify the
														// predecessor
			node.getSuccessor().fixFingers();
			for (int j = 0; j < i; j++) {
				chord.getNode(j).stabilize(); // other node make sure the
												// successor of this node

				chord.getNode(j).fixFingers();
			}

		}

		out.println("Finger Tables are fixed.");

		for (int i = 0; i < NUM_OF_NODES; i++) {
			Node node = chord.GetSortedNode(i);
			node.printFingerTable(out);
		}
		for (int i = 0; i < NUM_OF_NODES; i++) {
			chord.GetSortedNode(i).num_of_visited = 0;
		}

		new Client(chord.getNode(1));

		for (int i = 0; i < NUM_OF_NODES; i++) {
			count += chord.GetSortedNode(i).num_of_visited;
		}
		System.out.println("the total visits of nodes is " + count);

		long end = System.currentTimeMillis();

	}
}
