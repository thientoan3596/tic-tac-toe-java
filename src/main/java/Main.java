import models.Game;

public class Main {
    public static void main(String[] args) {
        System.out.println("Tic-tac-toe game");
        Game game = new Game();
        game.loop();
    }
}
