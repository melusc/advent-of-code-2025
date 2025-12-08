package d08;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Main {
	static Coordinate[] parseInput() {
		var result = new ArrayList<Coordinate>();
		result.ensureCapacity(1000);
		int i = 0;
		try (var file = new Scanner(Path.of("src/d08/input"))) {
			while (file.hasNextLine()) {
				String line = file.nextLine();
				if (line.isBlank()) {
					continue;
				}

				Long[] coordinates = Arrays.stream(line.split(",")).map(Long::parseLong).toArray(Long[]::new);
				result.addLast(new Coordinate(i, coordinates[0], coordinates[1], coordinates[2]));
				++i;
			}
		}  catch (IOException e) {
			System.err.println("Could not open input.");
		}

		return result.toArray(Coordinate[]::new);
	}

	public static void main(String[] args) {
		System.out.printf("Subtask 1: %s%n", s1());
		System.out.printf("Subtask 2: %s%n", s2());
	}

	public static int s1() {
		var coordinates = parseInput();

		PriorityQueue<Edge> q = new PriorityQueue<>(Comparator.comparing(Edge::length));

		for (int i = 0; i < coordinates.length; ++i) {
			for (int j = i + 1; j < coordinates.length; ++j) {
				q.add(new Edge(coordinates[i], coordinates[j]));
			}
		}

		Integer[] repr = Arrays.stream(coordinates).map(v -> v.v).toArray(Integer[]::new);
		List<List<Coordinate>> l = new LinkedList<>();

		for (Coordinate coordinate : coordinates) {
			var g = new LinkedList<Coordinate>();
			g.add(coordinate);
			l.add(g);
		}

		for (int k = 0; k < 1000; ++k) {
			var b = q.poll();
			if (repr[b.l.v].equals(repr[b.r.v])) {
				continue;
			}

			var zhkl = l.get(repr[b.l.v]);
			var zhkr = l.get(repr[b.r.v]);
			if (zhkl.size() < zhkr.size()) {
				for (var p: zhkl) {
					repr[p.v] = repr[b.r.v];
				}
				l.set(repr[b.l.v], zhkr);
				zhkr.addAll(zhkl);
			}
			else {
				for (var p: zhkr) {
					repr[p.v] = repr[b.l.v];
				}
				l.set(repr[b.r.v], zhkl);
				zhkl.addAll(zhkr);
			}
		}

		var w = l.stream().map(List::size).sorted(Comparator.comparing(j -> -j)).toArray(Integer[]::new);
		return w[0] * w[1] * w[2];
	}

	public static long s2() {
		var coordinates = parseInput();

		PriorityQueue<Edge> q = new PriorityQueue<>(Comparator.comparing(Edge::length));

		for (int i = 0; i < coordinates.length; ++i) {
			for (int j = i + 1; j < coordinates.length; ++j) {
				q.add(new Edge(coordinates[i], coordinates[j]));
			}
		}

		Integer[] repr = Arrays.stream(coordinates).map(v -> v.v).toArray(Integer[]::new);
		List<List<Coordinate>> l = new LinkedList<>();

		for (Coordinate coordinate : coordinates) {
			var g = new LinkedList<Coordinate>();
			g.add(coordinate);
			l.add(g);
		}

		int zhkA = 1000;

		Edge b = null;

		while (zhkA > 1) {
			b = q.poll();
			if (repr[b.l.v].equals(repr[b.r.v])) {
				continue;
			}

			var zhkl = l.get(repr[b.l.v]);
			var zhkr = l.get(repr[b.r.v]);
			if (zhkl.size() < zhkr.size()) {
				for (var p: zhkl) {
					repr[p.v] = repr[b.r.v];
				}
				l.set(repr[b.l.v], zhkr);
				zhkr.addAll(zhkl);
			}
			else {
				for (var p: zhkr) {
					repr[p.v] = repr[b.l.v];
				}
				l.set(repr[b.r.v], zhkl);
				zhkl.addAll(zhkr);
			}

			--zhkA;
		}

		return b.l.x * b.r.x;
	}
}




class Edge {
	Coordinate l, r;
	Edge(Coordinate l, Coordinate r) {
		this.l = l;
		this.r = r;
	}

	public long length() {
		return this.l.squareDistance(this.r);
	}
}


class Coordinate {
	long x, y, z;
	int v;

	Coordinate(int v, long x, long y, long z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.v = v;
	}

	public long squareDistance(Coordinate other) {
		return (long) (Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2));
	}
}