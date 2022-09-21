import java.util.Random;
import java.util.Scanner;

public class Field {
    Cell[][] field;
    int size;
    int nMines;
    public boolean bombRevealed = false;

    public Field (int size, int nMines) {
        this.size = size;
        this.nMines = nMines;

        generateField();
        findMines();
    }

    public void printField() {
        System.out.print("  | ");
        for(int i = 0; i < size; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();

        System.out.println(new String(new char[(size*2)+3]).replace('\0', '_'));
        for(int row = 0; row < field.length; row++) {
            System.out.print(row+1 + " | ");
            for(int col = 0; col < field[row].length; col++) {
                System.out.print(field[row][col].getStatus() + " ");
            }
            System.out.println();
        }
    }

    public void makeGuess() {
        Scanner sc = new Scanner(System.in);
        int row, col;
        String action;

        System.out.print("> ");
        row = sc.nextInt();
        col = sc.nextInt();
        action = sc.next();

        switch (action) {
            case "free":
                revealNeighbours(row-1, col-1);
                break;

            case "mark":
                markCell(row, col);
                break;

            case "exit":
                System.exit(1);
                break;

            default:
                System.out.println("Select a valid option!");
                break;
        }
    }

    private void generateField() {
        Random random = new Random();

        field = new Cell[size][size];

        // Initialize the array
        for (Cell[] row: field)
            for(int cell = 0; cell < row.length; cell++) {
                row[cell] = new Cell();
            }

        // Generate mines
        int minesToAdd = nMines;
        while(minesToAdd > 0) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);

            // If cell does not contain mine
            if (field[x][y].getValue() != "X") {
                field[x][y].setValue("X");
                minesToAdd--;
            }
        }
    }

    private void findMines() {
        int auxCounter = 0;

        for(int row = 0; row < field.length; row++) {
            for(int col = 0; col < field[row].length; col++) {
                // Top left
                if(row != 0 && col != 0 && field[row - 1][col - 1].getValue() == "X" && field[row][col].getValue() == ".") {
                    auxCounter++;
                }

                // Top middle
                if(row != 0 && field[row - 1][col].getValue() == "X" && field[row][col].getValue() == ".") {
                    auxCounter++;
                }

                // Top right
                if(row != 0 && col != field[row].length-1 && field[row - 1][col + 1].getValue() == "X" && field[row][col].getValue() == ".") {
                    auxCounter++;
                }

                // Center left
                if(col != 0 && field[row][col - 1].getValue() == "X" && field[row][col].getValue() == ".") {
                    auxCounter++;
                }

                // Center right
                if(col != field[row].length-1 && field[row][col + 1].getValue() == "X" && field[row][col].getValue() == ".") {
                    auxCounter++;
                }

                // Bottom left
                if(row != field.length-1 && col != 0 && field[row + 1][col - 1].getValue() == "X" && field[row][col].getValue() == ".") {
                    auxCounter++;
                }

                // Bottom middle
                if(row != field.length-1 && field[row + 1][col].getValue() == "X" && field[row][col].getValue() == ".") {
                    auxCounter++;
                }

                // Bottom right
                if(row != field.length-1 && col != field.length-1 && field[row + 1][col + 1].getValue() == "X" && field[row][col].getValue() == ".") {
                    auxCounter++;
                }

                // Set number of mines
                if (auxCounter > 0) {
                    field[row][col].setValue(String.valueOf(auxCounter));
                }
                auxCounter = 0;
            }
        }
    }

    private void revealNeighbours(int row, int col) {
        // Selected cell
        String selectedCellValue = field[row][col].getValue();
        switch (selectedCellValue) {
            case ".":
                field[row][col].setValue("/");
                field[row][col].enableVisibility();
                break;
            case "X":
                this.bombRevealed = true;
                field[row][col].setValue("X");
                field[row][col].enableVisibility();
                return;
            default:
                field[row][col].enableVisibility();
                return;
        }

        // Top left
        if(row != 0 && col != 0) {
            String cellValue = field[row - 1][col - 1].getValue();
            switch(cellValue) {
                case ".":
                    revealNeighbours(row-1, col-1);
                    break;
                case "X":
                    break;
                default:
                    field[row - 1][col - 1].enableVisibility();
            }
        }

        // Top middle
        if(row != 0) {
            String cellValue = field[row - 1][col].getValue();
            switch(cellValue) {
                case ".":
                    revealNeighbours(row-1, col);
                    break;
                case "X":
                    break;
                default:
                    field[row - 1][col].enableVisibility();
            }
        }

        // Top right
        if(row != 0 && col != field[row].length-1) {
            String cellValue = field[row - 1][col+1].getValue();
            switch(cellValue) {
                case ".":
                    revealNeighbours(row-1, col+1);
                    break;
                case "X":
                    break;
                default:
                    field[row - 1][col+1].enableVisibility();
            }
        }

        // Center left
        if(col != 0) {
            String cellValue = field[row][col-1].getValue();
            switch(cellValue) {
                case ".":
                    revealNeighbours(row, col-1);
                    break;
                case "X":
                    break;
                default:
                    field[row][col-1].enableVisibility();
            }
        }

        // Center right
        if(col != field[row].length-1) {
            String cellValue = field[row][col+1].getValue();
            switch(cellValue) {
                case ".":
                    revealNeighbours(row, col+1);
                    break;
                case "X":
                    break;
                default:
                    field[row][col+1].enableVisibility();
            }
        }

        // Bottom left
        if(row != field.length-1 && col != 0) {
            String cellValue = field[row + 1][col-1].getValue();
            switch(cellValue) {
                case ".":
                    revealNeighbours(row+1, col-1);
                    break;
                case "X":
                    break;
                default:
                    field[row + 1][col-1].enableVisibility();
            }
        }

        // Bottom middle
        if(row != field.length-1) {
            String cellValue = field[row + 1][col].getValue();
            switch(cellValue) {
                case ".":
                    revealNeighbours(row+1, col);
                    break;
                case "X":
                    break;
                default:
                    field[row + 1][col].enableVisibility();
            }
        }

        // Bottom right
        if(row != field.length-1 && col != field.length-1) {
            String cellValue = field[row + 1][col+1].getValue();
            switch(cellValue) {
                case ".":
                    revealNeighbours(row+1, col+1);
                    break;
                case "X":
                    break;
                default:
                    field[row + 1][col+1].enableVisibility();
            }
        }
    }

    private void markCell(int row, int col) {
        field[row][col].setPlaceholder("?");
    }
}
