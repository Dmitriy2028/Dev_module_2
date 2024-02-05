package App;

import java.util.Scanner;
import java.util.logging.Logger;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());


    public void playGame() {

        Scanner scan = new Scanner(System.in);
        byte winner = 0;
        char [] box = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };

        LOGGER.info("Enter box number to select. Enjoy!\n");

        boolean boxEmpty = false;
        gameLogic(scan, winner, box, boxEmpty);

    }

    private void gameLogic(Scanner scan, byte winner, char[] box, boolean boxEmpty) {
        while (true) {
            boxEmpty = drawGameField(box, boxEmpty);

            if (gameResult(winner)) break;

            inputBox(scan, box);

            winner = gameWin(winner, box);

            if(!isBoxAvailable(box)){
                winner = 3;
                continue;
            }

            aiTurn(box);

            winner = gameLose(winner, box);
        }
    }

    //Перевірка на вільні клітинки
    private boolean isBoxAvailable(char[] box) {
        byte i;
        boolean boxAvailable;
        boxAvailable = false;
        for(i=0; i<9; i++){
            if(box[i] != 'X' && box[i] != 'O'){
                boxAvailable = true;
                break;
            }
        }
        return boxAvailable;
    }

    //Хід компьютера
    private void aiTurn(char[] box) {
        byte rand;
        while (true) {
            rand = (byte) (Math.random() * (9 - 1 + 1) + 1);
            if (box[rand - 1] != 'X' && box[rand - 1] != 'O') {
                box[rand - 1] = 'O';
                break;
            }
        }
    }

    //Перевірка на програш
    private byte gameLose(byte winner, char[] box) {
        char [] [] field = {
                {box[0], box[1], box[2]},
                {box[3], box[4], box[5]},
                {box[6], box[7], box[8]}
        };
        for (int i = 0; i < 3; i++) {
            if (field[i][0] == field[i][1] && field[i][1] == field[i][2] && field[i][0] == 'O') {
                winner = 2;  // Перемога в строці
            }
            if (field[0][i] == field[1][i] && field[1][i] == field[2][i] && field[0][i] == 'O') {
                winner = 2;  // Перемога в столбці
            }
        }
        if (field[0][0] == field[1][1] && field[1][1] == field[2][2] && field[0][0] == 'O') {
            winner = 2;  // Перемога по діагоналі \
        }
        if (field[0][2] == field[1][1] && field[1][1] == field[2][0] && field[0][2] == 'O') {
            winner = 2;  // Перемога по діагоналі /
        }
        return winner;
    }

    //Перевірка на виграш гравцем
    private byte gameWin(byte winner,char[] box) {
        char [] [] field = {
                {box[0], box[1], box[2]},
                {box[3], box[4], box[5]},
                {box[6], box[7], box[8]}
        };
        for (int i = 0; i < 3; i++) {
            if (field[i][0] == field[i][1] && field[i][1] == field[i][2] && field[i][0] == 'X') {
                winner = 1;  // Перемога в строці
            }
            if (field[0][i] == field[1][i] && field[1][i] == field[2][i] && field[0][i] == 'X') {
                winner = 1;  // Перемога в столбці
            }
        }
        if (field[0][0] == field[1][1] && field[1][1] == field[2][2] && field[0][0] == 'X') {
            winner = 1;  // Перемога по діагоналі \
        }
        if (field[0][2] == field[1][1] && field[1][1] == field[2][0] && field[0][2] == 'X') {
            winner = 1;  // Перемога по діагоналі /
        }
        return winner;
    }

    //Вибір клітинки гравцем
    private void inputBox(Scanner scan, char[] box) {
        byte input;
        while (true) {
            input = scan.nextByte();
            if (input > 0 && input < 10) {
                if (box[input - 1] == 'X' || box[input - 1] == 'O')
                    LOGGER.info("That one is already in use. Enter another.");
                else {
                    box[input - 1] = 'X';
                    break;
                }
            }
            else
                LOGGER.info("Invalid input. Enter again.");
        }
    }

    //Відмальовування ігрового поля
    private boolean drawGameField(char[] box, boolean boxEmpty) {
        byte i;
        LOGGER.info(String.format(
                "%n%n %s | %s | %s " +
                "%n ---------" +
                "%n %s | %s | %s " +
                "%n ---------" +
                "%n %s | %s | %s %n ",
                box[0], box[1], box[2],
                box[3], box[4], box[5],
                box[6], box[7], box[8]));
        if(!boxEmpty){
            for(i = 0; i < 9; i++)
                box[i] = ' ';
            boxEmpty = true;
        }
        return boxEmpty;
    }

    //Вивід результатів гри
    private boolean gameResult(byte winner) {
        if(winner == 1){
            LOGGER.info("You won the game!\nCreated by Shreyas Saha. Thanks for playing!");
            return true;
        } else if(winner == 2){
            LOGGER.info("You lost the game!\nCreated by Shreyas Saha. Thanks for playing!");
            return true;
        } else if(winner == 3){
            LOGGER.info("It's a draw!\nCreated by Shreyas Saha. Thanks for playing!");
            return true;
        }
        return false;
    }
}