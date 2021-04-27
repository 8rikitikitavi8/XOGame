package XO;

public class Field {
    final char EMPTY = '.';
     int SIZE = 3;
     char[][] cells;

    public Field() {
        cells = new char[SIZE][SIZE];
    }

    void initField() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = EMPTY;
            }
//            System.out.println();
        }
    }

    void showField() {
        System.out.println();
        System.out.println(" 0  1  2");
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("|"+cells[i][j] + "|");
            }
            System.out.print(" " +i);
            System.out.println();
            System.out.println("---------");
        }
        System.out.println();
    }
}
