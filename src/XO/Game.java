package XO;

import java.util.Random;
import java.util.Scanner;

public class Game {
    Random random;
    Scanner scanner;
    Field field;
    Player player1;
    Player player2;
    boolean computerMode;
    boolean whoFirst;

    public Game() {
        scanner = new Scanner(System.in);
        random = new Random();
        whoFirst = random.nextBoolean();
        field = new Field();
    }

    public void greating() {
        System.out.println("Приветствую вас на игре КРЕСТИКИ - НОЛИКИ!!!");
    }

    public void chooseGameMode() {
        System.out.println("Введите в консоль 1 , если хотите продолжить с другом или 0, " +
                "если будете побеждать компьютер.");
        String s = scanner.nextLine();

        if (s.equalsIgnoreCase("1")) {
            computerMode = false;
        } else if (s.equalsIgnoreCase("0")) {
            computerMode = true;
        } else {
            System.out.println("Нужно ввести либо цифру 1, если будете играть с другом. " +
                    "Либо цифру 0, если хотите продолжить с компьютером");
        }
    }


    public void gameMode(boolean mode) {
        if (mode) {
            System.out.println("Игрок, напишите свое имя");
            player1 = new Player();
            player1.setName(scanner.nextLine());
            while (true) {
                gameLogic(player1);
                System.out.println("Если хотите повторить нажмите цифру 1  или цифру 0 для выхода");
                if (scanner.nextLine().equals("0")) {
                    return;
                }
            }
        } else {
            System.out.println("Игрок 1, напишите свое имя");
            player1 = new Player();
            player1.setName(scanner.nextLine());
            System.out.println("Игрок 2, напишите свое имя");
            player2 = new Player();
            player2.setName(scanner.nextLine());
            while (true) {
                gameLogic(player1, player2);
                System.out.println("Если хотите повторить нажмите цифру 1  или цифру 0 для выхода");
                if (scanner.nextLine().equals("0")) {
                    return;
                }
            }
        }
    }

    private void gameLogic(Player player1, Player player2) {
        field.initField();
        while (true) {
            field.showField();
            if (whoFirst) {
                System.out.println(player1.getName() + ", твой ход.");
                if (isWinPlayer(player1, 'X')) {
                    return;
                }
                field.showField();
                System.out.println(player2.getName() + ", твой ход.");
                if (isWinPlayer(player2, '0')) {
                    return;
                }
            } else {
                System.out.println(player2.getName() + ", твой ход.");
                if (isWinPlayer(player2, '0')) {
                    return;
                }
                field.showField();
                System.out.println(player1.getName() + ", твой ход.");
                if (isWinPlayer(player1, 'X')) {
                    return;
                }
            }
        }

    }

    private void gameLogic(Player player) {
        field.initField();
        while (true) {
            field.showField();
            if (whoFirst) {
                System.out.println(player.getName() + ", твой ход.");
                if (isWinPlayer(player, 'X')) {
                    return;
                }
                field.showField();
                if (isWinComputer(player, '0', 'X')) {
                    return;
                }
            } else {
                if (isWinComputer(player, '0', 'X')) {
                    return;
                }
                field.showField();
                System.out.println(player.getName() + ", твой ход.");
                if (isWinPlayer(player, 'X')) {
                    return;
                }
            }
        }
    }

    private void checkAndMovePlayer(int y, int x, Field field, char p, Player player) {
        int X = y;
        int Y = x;
        while (true) {
            if (field.cells[X][Y] != 'X' && field.cells[X][Y] != '0') {
                field.cells[X][Y] = p;
                return;
            } else {
                System.out.println("Сюда уже кто то ходил, сделайте другой ход.");
                X = player.playerTurnY();
                Y = player.playerTurnX();
            }
        }
    }

    private void checkAndMoveComputer(int x, int y, Field field, char c, char p, Player player) {
        int X = y;
        int Y = x;

//        Проверка выигрышной 3й клетки

        System.out.println("Ход компьютера");
        for (int i = 0; i < field.SIZE; i++) {
            for (int j = 0; j < field.SIZE; j++) {
                if (field.cells[i][j] == c) {

                    //                                    проверка на среднюю клетку


                    for (int s = i - 2; s < i + 3; s++) {
                        for (int q = j - 2; q < j + 5; q = q + 4) {
                            if (s < 0 || q < 0 || s > 2 || q > 2) {
                                continue;
                            }
                            try {
                                if (field.cells[s][q] == c && field.cells[i][j - 1] == field.EMPTY) {
                                    field.cells[i][j - 1] = c;
                                    return;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                            try {
                                if (field.cells[s][q] == c && field.cells[i][j + 1] == field.EMPTY) {
                                    field.cells[i][j + 1] = c;
                                    return;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                        }
                    }
                    for (int s = i - 2; s < i + 5; s = s + 4) {
                        for (int q = j - 2; q < j + 3; q++) {
                            if (s < 0 || q < 0 || s > 2 || q > 2) {
                                continue;
                            }
                            try {
                                if (field.cells[s][q] == c && field.cells[i - 1][j] == field.EMPTY) {
                                    field.cells[i - 1][j] = c;
                                    return;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                            try {
                                if (field.cells[s][q] == c && field.cells[i + 1][j] == field.EMPTY) {
                                    field.cells[i + 1][j] = c;
                                    return;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                        }
                    }
                    try {
                        if (field.cells[i + 2][j - 2] == c && field.cells[i + 1][j - 1] == field.EMPTY) {
                            field.cells[i + 1][j - 1] = c;
                            return;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                    try {
                        if (field.cells[i - 2][j - 2] == c && field.cells[i - 1][j - 1] == field.EMPTY) {
                            field.cells[i - 1][j - 1] = c;
                            return;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                    try {
                        if (field.cells[i - 2][j + 2] == c && field.cells[i - 1][j + 1] == field.EMPTY) {
                            field.cells[i - 1][j + 1] = c;
                            return;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                    try {
                        if (field.cells[i + 2][j + 2] == c && field.cells[i + 1][j + 1] == field.EMPTY) {
                            field.cells[i + 1][j + 1] = c;
                            return;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }


//проверка по x
                    for (int k = i - 1; k < i + 2; k++) {
                        for (int l = j - 1; l < j + 2; l++) {
                            if (k < 0 || l < 0 || k > 2 || l > 2) {
                                continue;
                            }
                            if (k == i && l == j) {
                                continue;
                            }
                            if (field.cells[k][l] == c) {
                                if (l == j) {
                                    try {
                                        if (field.cells[k + 1][l] == field.EMPTY) {
                                            if (field.cells[k + 1][l] != 'X' && field.cells[k + 1][l] != '0') {
                                                field.cells[k + 1][l] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (field.cells[k - 1][l] == field.EMPTY) {
                                            if (field.cells[k - 1][l] != 'X' && field.cells[k - 1][l] != '0') {
                                                field.cells[k - 1][l] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }

                                }
//проверка по y
                                if (k == i) {
                                    try {
                                        if (field.cells[k][l + 1] == field.EMPTY) {
                                            if (field.cells[k][l + 1] != 'X' && field.cells[k][l + 1] != '0') {
                                                field.cells[k][l + 1] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (field.cells[k][l - 1] == field.EMPTY) {
                                            if (field.cells[k][l - 1] != 'X' && field.cells[k][l - 1] != '0') {
                                                field.cells[k][l - 1] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                } else {
//                                    проверка по диагонали
                                    try {
                                        if (k == i - 1 && l == j - 1 && field.cells[i + 1][j + 1] == field.EMPTY) {
                                            if (field.cells[i + 1][j + 1] != 'X' && field.cells[i + 1][j + 1] != '0') {
                                                field.cells[i + 1][j + 1] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (k == i + 1 && l == j + 1 && field.cells[i - 1][j - 1] == field.EMPTY) {
                                            if (field.cells[i - 1][j - 1] != 'X' && field.cells[i - 1][j - 1] != '0') {
                                                field.cells[i - 1][j - 1] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (k == i - 1 && l == j + 1 && field.cells[i + 1][j - 1] == field.EMPTY) {
                                            if (field.cells[i + 1][j - 1] != 'X' && field.cells[i + 1][j - 1] != '0') {
                                                field.cells[i + 1][j - 1] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (k == i + 1 && l == j - 1 && field.cells[i - 1][j + 1] == field.EMPTY) {
                                            if (field.cells[i - 1][j + 1] != 'X' && field.cells[i - 1][j + 1] != '0') {
                                                field.cells[i - 1][j + 1] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }


        //Препятствование игроку выиграть
        for (int i = 0; i < field.SIZE; i++) {
            for (int j = 0; j < field.SIZE; j++) {
                if (field.cells[i][j] == p) {
//                                    проверка на среднюю клетку
                    for (int s = i - 2; s < i + 3; s++) {
                        for (int q = j - 2; q < j + 5; q = q + 4) {
                            if (s < 0 || q < 0 || s > 2 || q > 2) {
                                continue;
                            }
                            try {
                                if (field.cells[s][q] == p && field.cells[i][j - 1] == field.EMPTY) {
                                    field.cells[i][j - 1] = c;
                                    return;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                            try {
                                if (field.cells[s][q] == p && field.cells[i][j + 1] == field.EMPTY) {
                                    field.cells[i][j + 1] = c;
                                    return;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                        }
                    }
                    for (int s = i - 2; s < i + 5; s = s + 4) {
                        for (int q = j - 2; q < j + 3; q++) {
                            if (s < 0 || q < 0 || s > 2 || q > 2) {
                                continue;
                            }
                            try {
                                if (field.cells[s][q] == p && field.cells[i - 1][j] == field.EMPTY) {
                                    field.cells[i - 1][j] = c;
                                    return;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                            try {
                                if (field.cells[s][q] == p && field.cells[i + 1][j] == field.EMPTY) {
                                    field.cells[i + 1][j] = c;
                                    return;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                        }
                    }
                    try {
                        if (field.cells[i + 2][j - 2] == p && field.cells[i + 1][j - 1] == field.EMPTY) {
                            field.cells[i + 1][j - 1] = c;
                            return;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                    try {
                        if (field.cells[i - 2][j - 2] == p && field.cells[i - 1][j - 1] == field.EMPTY) {
                            field.cells[i - 1][j - 1] = c;
                            return;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                    try {
                        if (field.cells[i - 2][j + 2] == p && field.cells[i - 1][j + 1] == field.EMPTY) {
                            field.cells[i - 1][j + 1] = c;
                            return;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                    try {
                        if (field.cells[i + 2][j + 2] == p && field.cells[i + 1][j + 1] == field.EMPTY) {
                            field.cells[i + 1][j + 1] = c;
                            return;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }

//проверка по x
                    for (int k = i - 1; k < i + 2; k++) {
                        for (int l = j - 1; l < j + 2; l++) {
                            if (k < 0 || l < 0 || k > 2 || l > 2) {
                                continue;
                            }
                            if (k == i && l == j) {
                                continue;
                            }
                            if (field.cells[k][l] == p) {
                                if (l == j) {

                                    try {
                                        if (field.cells[k + 1][l] == field.EMPTY) {
                                            if (field.cells[k + 1][l] != 'X' && field.cells[k + 1][l] != '0') {
                                                field.cells[k + 1][l] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (field.cells[k - 1][l] == field.EMPTY) {
                                            if (field.cells[k - 1][l] != 'X' && field.cells[k - 1][l] != '0') {
                                                field.cells[k - 1][l] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }

                                }
//проверка по y
                                if (k == i) {
                                    try {
                                        if (field.cells[k][l + 1] == field.EMPTY) {
                                            if (field.cells[k][l + 1] != 'X' && field.cells[k][l + 1] != '0') {
                                                field.cells[k][l + 1] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (field.cells[k][l - 1] == field.EMPTY) {
                                            if (field.cells[k][l - 1] != 'X' && field.cells[k][l - 1] != '0') {
                                                field.cells[k][l - 1] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                } else {
                                    //проверка по диагонали
                                    try {
                                        if (k == i - 1 && l == j - 1 && field.cells[i + 1][j + 1] == field.EMPTY) {
                                            if (field.cells[i + 1][j + 1] != 'X' && field.cells[i + 1][j + 1] != '0') {
                                                field.cells[i + 1][j + 1] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (k == i + 1 && l == j + 1 && field.cells[i - 1][j - 1] == field.EMPTY) {
                                            if (field.cells[i - 1][j - 1] != 'X' && field.cells[i - 1][j - 1] != '0') {
                                                field.cells[i - 1][j - 1] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (k == i - 1 && l == j + 1 && field.cells[i + 1][j - 1] == field.EMPTY) {
                                            if (field.cells[i + 1][j - 1] != 'X' && field.cells[i + 1][j - 1] != '0') {
                                                field.cells[i + 1][j - 1] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (k == i + 1 && l == j - 1 && field.cells[i - 1][j + 1] == field.EMPTY) {
                                            if (field.cells[i - 1][j + 1] != 'X' && field.cells[i - 1][j + 1] != '0') {
                                                field.cells[i - 1][j + 1] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


//        Проверка соседней клетки

        for (int i = 0; i < field.SIZE; i++) {
            for (int j = 0; j < field.SIZE; j++) {
                if (field.cells[i][j] == c) {

                    for (int k = i - 1; k < i + 2; k++) {
                        for (int l = j - 1; l < j + 2; l++) {
                            if (k < 0 || l < 0 || k > 2 || l > 2) {
                                continue;
                            }
                            if (k == i && l == j) {
                                continue;
                            }
                            if (field.cells[k][l] == field.EMPTY) {
                                if (l == j) {
                                    try {
                                        if (field.cells[k + 1][l] == field.EMPTY) {
                                            if (field.cells[k][l] != 'X' && field.cells[k][l] != '0') {
                                                field.cells[k][l] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (field.cells[k - 1][l] == field.EMPTY) {
                                            if (field.cells[k][l] != 'X' && field.cells[k][l] != '0') {
                                                field.cells[k][l] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }

                                }

                                if (k == i) {
                                    try {
                                        if (field.cells[k][l + 1] == field.EMPTY) {
                                            if (field.cells[k][l] != 'X' && field.cells[k][l] != '0') {
                                                field.cells[k][l] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (field.cells[k][l - 1] == field.EMPTY) {
                                            if (field.cells[k][l] != 'X' && field.cells[k][l] != '0') {
                                                field.cells[k][l] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                } else {
                                    try {
                                        if (k == i - 1 && l == j - 1 && field.cells[i + 1][j + 1] == field.EMPTY) {
                                            if (field.cells[k][l] != 'X' && field.cells[k][l] != '0') {
                                                field.cells[k][l] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (k == i + 1 && l == j + 1 && field.cells[i - 1][j - 1] == field.EMPTY) {
                                            if (field.cells[k][l] != 'X' && field.cells[k][l] != '0') {
                                                field.cells[k][l] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (k == i - 1 && l == j + 1 && field.cells[i + 1][j - 1] == field.EMPTY) {
                                            if (field.cells[k][l] != 'X' && field.cells[k][l] != '0') {
                                                field.cells[k][l] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                    try {
                                        if (k == i + 1 && l == j - 1 && field.cells[i - 1][j + 1] == field.EMPTY) {
                                            if (field.cells[k][l] != 'X' && field.cells[k][l] != '0') {
                                                field.cells[k][l] = c;
                                                return;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        while (true) {
            if (field.cells[X][Y] != 'X' && field.cells[X][Y] != '0') {
                field.cells[X][Y] = c;
                return;
            } else {
                X = player.computerTurn();
                Y = player.computerTurn();
            }
        }

//Вариант с рандомным ходом компьютера


//        System.out.println("Ход компьютера");
//        while (true) {
//            if (field.cells[X][Y] != 'X' && field.cells[X][Y] != '0') {
//                field.cells[X][Y] = c;
//                return;
//            } else {
//                X = player.computerTurn();
//                Y = player.computerTurn();
//            }
//        }
    }

    private boolean isEndGame(char c) {
        boolean b = false;
        for (int i = 0; i < field.SIZE; i++) {
            if (field.cells[i][0] == c && field.cells[i][1] == c && field.cells[i][2] == c) {
                b = true;
                return b;
            }
        }
        for (int i = 0; i < field.SIZE; i++) {
            if (field.cells[0][i] == c && field.cells[1][i] == c && field.cells[2][i] == c) {
                b = true;
                return b;
            }
        }
        if ((field.cells[0][0] == c && field.cells[1][1] == c && field.cells[2][2] == c) ||
                (field.cells[2][0] == c && field.cells[1][1] == c && field.cells[0][2] == c)) {
            b = true;
            return b;
        }
        return b;
    }

    private boolean draw() {
        boolean b = false;
        int counter = 0;
        for (int i = 0; i < field.SIZE; i++) {
            for (int j = 0; j < field.SIZE; j++) {
                if (field.cells[i][j] == field.EMPTY) {
                    counter++;
                }
            }
        }
        if (counter == 0) {
            b = true;
            System.out.println("----Ничья----");
        }
        return b;
    }

    private boolean isWinPlayer(Player player, char p) {
        boolean win = false;
        checkAndMovePlayer(player.playerTurnY(), player.playerTurnX(), field, p, player);
        if (isEndGame(p)) {
            field.showField();
            System.out.println(player.getName() + " выиграл!");
            win = true;
        }
        if (draw()) {
            win = true;
        }
        return win;
    }

    private boolean isWinComputer(Player player, char c, char p) {
        boolean win = false;
        checkAndMoveComputer(player.computerTurn(), player.computerTurn(), field, c, p, player);
        if (isEndGame(c)) {
            field.showField();
            System.out.println("Компьютер выиграл!");
            win = true;
        }
        if (!win) {
        if (draw()) {
            win = true;
        }
        }
        return win;
    }


}
