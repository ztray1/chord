package kmaru.jchord;

public class FingerTable {

	Finger[] fingers;

	public FingerTable(Node node) {
		this.fingers = new Finger[Hash.KEY_LENGTH];
		for (int i = 0; i < fingers.length; i++) {
			Key start = node.GetKey().createStartKey(i);
			fingers[i] = new Finger(start, node);
		}
	}

	public Finger GetFinger(int i) {
		return fingers[i];
	}

}
