import java.util.Scanner;

public class GameController {
    Field field;
    int size, nMines;

    public GameController() {
        if(initGame()) {
            gameLoop();
        }
    }

    private boolean initGame() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Field size > ");
        this.size = sc.nextInt();
        System.out.print("Number of mines > ");
        this.nMines = sc.nextInt();

        this.field = new Field(size, nMines);

        return true;
    }

    private void gameLoop() {
        while(!field.bombRevealed) {
            field.printField();
            field.makeGuess();
        }

        field.printField();
        System.out.println("You lost!");
    }
}
