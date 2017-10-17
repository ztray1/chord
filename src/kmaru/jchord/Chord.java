package kmaru.jchord;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Chord {

	List<Node> List = new ArrayList<Node>();
	SortedMap<Key, Node> sortedNodeMap = new TreeMap<Key, Node>();
	Object[] sortedArray;

	public void createNode(String NoteId) throws ChordException {
		Node node = new Node(NoteId);
		List.add(node);

		if (sortedNodeMap.get(node.GetKey()) != null) {
			throw new ChordException("Duplicated Key: " + node);
		}

		sortedNodeMap.put(node.GetKey(), node);
	}

	public Node getNode(int i) {
		return (Node) List.get(i);
	}

	public Node GetSortedNode(int i) {
		if (sortedArray == null) {
			sortedArray = sortedNodeMap.keySet().toArray();
		}
		return (Node) sortedNodeMap.get(sortedArray[i]);
	}
}
