package kmaru.jchord;

import java.util.Random;
import java.util.Scanner;

public class Client {
	public static final int NUM_OF_DATAITEM = 100000;

	Node startnode;
	Scanner scanner;

	public Client(Node node) {
		startnode = node;
		scanner = new Scanner(System.in);
		System.out.println("welcome!");

		interaction();
	}

	void interaction() {
		while (true) {
			String line = scanner.nextLine();
			String para[] = line.split(" ");
			if (line.startsWith("successive")) {
				String key;
				String value;
				int start = Integer.valueOf(para[1]);
				int end = Integer.valueOf(para[2]);
				for (int i = start; i <= end; i++) {
					key = value = String.valueOf(i);

					startnode.findSuccessor(key).store.put(key, value);
				}
				System.out.println("you have input " + (end - start + 1) + " pieces of data");
			} else if (line.startsWith("random")) {
				Random rmd = new Random();
				String key;
				String value;
				float index = Float.valueOf(para[3]);
				int start = Integer.valueOf(para[1]);
				int length = Integer.valueOf(para[2]) - start + 1;
				int num = (int) (length * index);
				for (int i = 0; i < num; i++) {
					key = value = String.valueOf(Math.abs(rmd.nextInt() % length) + start);
					startnode.findSuccessor(key).store.put(key, value);
				}
				System.out.println("you have input " + num + " pieces of data");

			} else if (line.startsWith("put")) {
				startnode.findSuccessor(para[1]).store.put(para[1], para[2]);
			} else if (line.startsWith("get")) {
				System.out.println(startnode.findSuccessor(para[1]).store.get(para[1]));

			} else if (line.startsWith("rangequery")) {
				long start = System.currentTimeMillis();
				String value;
				for (int i = Integer.valueOf(para[1]); i <= Integer.valueOf(para[2]); i++) {
					value = startnode.findSuccessor(String.valueOf(i)).store.get(String.valueOf(i));
					if (value != null)
						System.out.println(value);
				}
				long end = System.currentTimeMillis();

				int interval = (int) (end - start);
				System.out.printf("Elapsed Time : %d.%d\n\r", interval / 1000, interval % 1000);

			} else if (line.startsWith("clearcounter")) {
				int counter = 0;
				Node p = startnode;
				do {
					counter += p.num_of_visited;

					p.num_of_visited = 0;

					p = p.successor;

				} while (p != startnode);
				System.out.println("the total visits of nodes is " + counter);
			} else if (line.startsWith("cleardata")) {
				Node p = startnode;
				do {
					p.store.clean();

					p = p.successor;
				} while (p != startnode);
			} else if (line.startsWith("exit"))
				break;

		}
	}

}
