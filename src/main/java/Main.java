import models.Game;

public class Main {
    public static void main(String[] args) {
        System.out.println("Tic-tac-toe game");
        Game game = new Game();
        game.loop();
//        int d = 9;
//        int x = (d-1)%3+1;
//        int y = (d-1)/3+1;
//        System.out.printf("%d %d ",x,y);
    }
}
