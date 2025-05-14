/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package program;
import components.MainOffice;

public class Game {

    public static void main(String[] args) {
        MainOffice game = new MainOffice(5, 4);
        game.play(60);
    }

}