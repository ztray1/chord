package kmaru.jchord;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Store {
	String filename;
	RandomAccessFile file;

	Store(String nodeId) {

		try {
			filename = "store\\" + nodeId.toString().substring(7).replace(':', '_') + ".txt";
			// new FileWriter(filename);
			file = new RandomAccessFile(filename, "rw");
		} catch (Exception e) {
			System.out.println("new file error");
			System.exit(0);
		}
		// clean();
	}

	void put(String key, String value) {
		try {
			file.seek(file.length());
			file.writeBytes(key);
			file.writeBytes(" ");
			file.writeBytes(value);
			file.writeBytes("\r\n");

		} catch (Exception e) {
			System.out.println("put error");
			System.exit(0);
		}

	}

	String get(String key) {
		String str = null;
		String[] para;
		try {
			file.seek(0);

			do {
				str = file.readLine();
				if (str == null)
					return null;
				para = str.split(" ");

			} while (!para[0].equals(key));
			if (para[0].equals(key)) {

				str = para[1];
			} else
				str = null;

		} catch (Exception e) {
			System.out.println("get error");
			System.exit(0);
		}

		return str;

	}

	void clean() {
		try {
			file.setLength(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
