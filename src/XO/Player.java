package XO;

import java.util.Random;
import java.util.Scanner;

public class Player {
    Scanner scanner = new Scanner(System.in);
    private String name;
    Random random = new Random();

    int playerTurnX() {
        int x = 0;
        while (true) {
            System.out.println("Координата по оси Х:");
            x = scanner.nextInt();
            if (x >= 0 && x <= 2) {
                return x;
            }
            System.out.println("Введите цифру от 0 до 2 включительно");

        }
    }

    int playerTurnY() {
        int y = 0;
        while (true) {
            System.out.println("Координата по оси y:");
            y = scanner.nextInt();
            if (y >= 0 && y <= 2) {
                return y;
            }
            System.out.println("Введите цифру от 0 до 2 включительно");
        }
    }

    int computerTurn() {
        return random.nextInt(3);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
