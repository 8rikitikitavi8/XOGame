package XO;
/*
Игра крестики-нолики
-2 режима игры: 2 игрока и игрок с "умным" компьютером
-каждую новую игру очередь ходить выпадает в случайном порядке
-"умный" компьютер способен оценивать на 2 хода вперед и второй ход делать в том направлении,
где есть сводобная 3я клетка.Компьютер блокирует ваш выигрышный ход
 */
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.greating();
        game.chooseGameMode();
        game.gameMode(game.computerMode);
    }
}
