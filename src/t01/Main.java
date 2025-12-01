package t01;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Subtask 1: %s%n", s1());
        System.out.printf("Subtask 2: %s%n", s2());
    }
    public static int s1() {
        int r = 50;
        int c = 0;
        try (var file = new Scanner(Path.of("src/t01/input"))) {
            while (file.hasNextLine()) {
                var line = file.nextLine();
                if (line.isBlank()) {
                    continue;
                }

                var dir = line.charAt(0);
                var amount = Integer.parseInt(line.substring(1), 10);
                r += switch(dir) {
                    case 'L' -> -amount;
                    case 'R' -> amount;
                    default -> throw new IllegalArgumentException("Unknown direction " + dir);
                };
                r += 100;
                r %= 100;

                if (r == 0) {
                    ++c;
                }
            }
        } catch (IOException e) {
            System.err.println("Could not open input.");
        }

        return c;
    }
    public static int s2() {
        int r = 50;
        int c = 0;
        try (var file = new Scanner(Path.of("src/t01/input"))) {
            while (file.hasNextLine()) {
                var line = file.nextLine();
                if (line.isBlank()) {
                    continue;
                }

                var dir = line.charAt(0);
                var amount = Integer.parseInt(line.substring(1), 10);
                int before = r;

                r += switch(dir) {
                    case 'L' -> -(amount % 100);
                    case 'R' -> amount % 100;
                    default -> throw new IllegalArgumentException("Unknown direction " + dir);
                };

                c += amount / 100;

                if ((before != 0 && r <= 0) || r >= 100) {
                    ++c;
                }

                r += 100;
                r %= 100;
            }
        } catch (IOException e) {
            System.err.println("Could not open input.");
        }

        return c;
    }
}
