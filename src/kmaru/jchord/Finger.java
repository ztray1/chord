package kmaru.jchord;

public class Finger {

	Key start;

	Node node;

	public Finger(Key start, Node node) {
		this.node = node;
		this.start = start;
	}

	public Key getStart() {
		return start;
	}

	public void SetStart(Key start) {
		this.start = start;
	}

	public Node GetNode() {
		return node;
	}

	public void SetNode(Node node) {
		this.node = node;
	}

}
