package utils;

public class Forest {
    private int width,height;
    private static double SQUARE_LENGTH = 1.0; // vamos a tener celdas de 1mx1m

    private Cell[][] forest;

    public Forest(int width, int height){
        this.width = width;
        this.height = height;
        this.forest = new Cell[width][height];
       // initializeForest();
    }

    private void initializeForest() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.forest[i][j] = new Cell(i,j, 1, 1, 2,0, SQUARE_LENGTH);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Cell[][] getForest() {
        return forest;
    }

    public void setForest(Cell[][] forest) {
        this.forest = forest;
    }

    public Cell getCell(int i, int j) {
        return this.forest[i][j];
    }
}
