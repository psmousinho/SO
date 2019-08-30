import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class PageSubstitution {

	public static int FIFO(int frames, LinkedList<Integer> pageAcess) {
		LinkedList<Integer> queue = new LinkedList<>();
		int pageFaultCount = 0;

		while (!pageAcess.isEmpty()) {

			if (queue.contains(pageAcess.getFirst())) {
				pageAcess.remove();
			} else {
				pageFaultCount++;

				if (queue.size() == frames)
					queue.remove();

				queue.add(pageAcess.remove());
			}
		}

		return pageFaultCount;
	}

	public static int ORM(int frames, LinkedList<Integer> pageAcess) {
		LinkedList<Integer> queue = new LinkedList<>();
		int pageFaultCount = 0;

		while (!pageAcess.isEmpty()) {

			if (queue.contains(pageAcess.getFirst())) {
				pageAcess.remove();
			} else {
				pageFaultCount++;

				if (queue.size() == frames) {
					int biggestIndex = 0;
					boolean bol = true;

					for (int i = 0; i < queue.size(); i++) {

						if (pageAcess.contains(queue.get(i))) {
							int index = pageAcess.indexOf(queue.get(i));
							biggestIndex = (index > biggestIndex) ? index : biggestIndex;
						} else {
							biggestIndex = i;
							bol = false;
							break;
						}
					}
					if(bol)
						biggestIndex = queue.indexOf(pageAcess.get(biggestIndex));
					queue.remove(biggestIndex);
				}

				queue.add(pageAcess.remove());
			}
		}

		return pageFaultCount;
	}

	public static int LRU(int frames, LinkedList<Integer> pageAcess) {
		LinkedList<Integer> queue = new LinkedList<>();
		int pageFaultCount = 0;

		while (!pageAcess.isEmpty()) {

			if (queue.contains(pageAcess.getFirst())) {
				queue.add(queue.remove(queue.indexOf(pageAcess.remove())));
			} else {
				pageFaultCount++;

				if (queue.size() == frames)
					queue.removeFirst();

				queue.add(pageAcess.remove());
			}
		}

		return pageFaultCount;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String path = "textData.txt";
		if (args.length != 0)
			path = args[0];

		LinkedList<Integer> queue = new LinkedList<>();

		try {
			Scanner sc = new Scanner(new File(path));

			while (sc.hasNextInt()) {
				queue.add(sc.nextInt());
			}

			sc.close();

		} catch (FileNotFoundException e) {
			System.err.println("Erro ao Abrir o Arquivo");
			e.printStackTrace();
		}

		LinkedList<Integer> cloneA = (LinkedList<Integer>) queue.clone();
		LinkedList<Integer> cloneB = (LinkedList<Integer>) queue.clone();

		System.out.println("FIFO " + FIFO(queue.remove(), queue));
		System.out.println("ORM " + ORM(cloneA.remove(), cloneA));
		System.out.println("LRU " + LRU(cloneB.remove(), cloneB));

	}

}
