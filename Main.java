package life;

import javax.swing.*;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

class Universe {
    int range;
    boolean[][] arr;
    int generation = 1;
    int alive;
    String generationStr;
    String aLiveStr;


    public boolean[][] getArr() {
        return arr;
    }

    public void alive() {
        for (boolean[] booleans : arr) {
            for (int j = 0; j < arr.length; j++) {
                if (booleans[j]) {
                    alive++;
                }
            }
        }
    }

    public void clean() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        }
        catch (IOException | InterruptedException e) {}
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void output() throws InterruptedException {
        clean();
        generationStr = "Generation #" + generation;
        System.out.println(generation);
        alive();
        aLiveStr = "Alive: " + alive;
        System.out.println(aLiveStr);

        for (int i = 0; i < range; i++) {
            for (int j = 0; j < range; j++) {
                System.out.print(arr[i][j] ? "O" : " ");
            }
            System.out.println();
        }
        Thread.sleep(500);
    }

    private int getNeighbourPosition(int position, int maxPosition) {
        int neighbourPosition = 0;
        if (position >= 0 && position < maxPosition) {
            neighbourPosition = position;
        } else if (position < 0) {
            neighbourPosition = maxPosition - 1;
        } else if (position >= maxPosition) {
            neighbourPosition = 0;
        }
        return neighbourPosition;
    }

    public boolean getNeighbourValue(int row, int column, boolean[][] matrix) {
        int neighbourRow;
        int neighbourColumn;
        int numberOfRows = matrix.length;
        int numberOfColumns = matrix.length;
        neighbourRow = getNeighbourPosition(row, numberOfRows);
        neighbourColumn = getNeighbourPosition(column, numberOfColumns);
        return matrix[neighbourRow][neighbourColumn];
    }

    public int getSumOfNeighbours(boolean[][] matrix, int y, int x) {
        int result = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                result += (getNeighbourValue(y + i, x + j, matrix)) ? 1 : 0;
            }
        }
        return result;
    }

    public void getGeneration(int z) throws InterruptedException {
            alive = 0;
            generation++;
            boolean[][] arr2 = new boolean[range][range];
            for (int i = 0; i < range; i++) {
                for (int j = 0; j < range; j++) {
                    int countNeighbors = getSumOfNeighbours(arr, i, j);
                    boolean cellIsAlive = arr[i][j];
                    if (cellIsAlive) {
                        cellIsAlive = (countNeighbors >= 2 && countNeighbors < 4);
                    } else {
                        cellIsAlive = (countNeighbors == 3);
                    }
                    arr2[i][j] = cellIsAlive;
                }
            }
            arr = arr2;
            output();
//        }
    }

//    public void launch() throws InterruptedException {
//        output();
//        getGeneration(1);
//    }

    public void play() throws InterruptedException {
        Random random = new Random();
        arr = new boolean[range][range];
        for (int i = 0; i < range; i++) {
            for (int j = 0; j < range; j++) {
                arr[i][j] = random.nextBoolean();
            }
        }
//        launch();
    }

    public Universe(int range) throws InterruptedException {
        this.range = range;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        new GameOfLife();
    }
}
