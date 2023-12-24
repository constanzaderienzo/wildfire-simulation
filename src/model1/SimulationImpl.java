package model1;


import javafx.util.Pair;
import utils.Cell;
import utils.Forest;
import utils.Simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class SimulationImpl implements Simulation {

    private long totalTime;
    private double timeStep;
    private Forest forest;
    private static double SQUARE_LENGTH = 1.0;
    private FileManager fm;
    private boolean forestOnFire = true;

    /* Variables */
    private static double Ph = 0.58;// probability constant
    private static double a = 0.078;
    private static double c1 = 0.045;
    private static double c2 = 0.131;
    private double windSpeed = 10; // m/s
    private WindEnum windDirection = WindEnum.NORTH;
    static boolean spottingActivated = false;
    private double pc0 = 0.25;

    private static double[][] northMatrix = {{45.0, 0.0, 45.0}, {90.0, 0.0, 90.0}, {135.0, 180.0, 135.0}};
    private static double[][] northEastMatrix = {{90.0, 45.0, 0}, {135.0, 0.0, 45.0}, {180.0, 135.0, 90.0}};
    private static double[][] northWestMatrix = {{0.0, 45.0, 90.0}, {45.0, 0.0, 135.0}, {90.0, 135.0, 180.0}};
    private static double[][] southMatrix = {{135.0, 180.0, 135.0}, {90.0, 0.0, 90.0}, {45.0, 0.0, 45.0}};
    private static double[][] southEastMatrix = {{180.0, 135.0, 90.0}, {135.0, 0.0, 45.0}, {90.0, 45.0, 0.0}};
    private static double[][] southWestMatrix = {{90.0, 135.0, 180.0}, {45.0, 0.0, 135.0}, {0.0, 45.0, 90.0}};
    private static double[][] eastMatrix = {{135.0, 90.0, 45.0}, {180.0, 0.0, 0.0}, {135.0, 90.0, 45.0}};
    private static double[][] westMatrix = {{45.0, 90.0, 135.0}, {0.0, 0.0, 180.0}, {45.0, 90.0, 135.0}};

    private static double[] blastAngles = {0.0, 45.0, 90.0, 135.0, 180.0, 225.0, 270.0, 315.0};
    private static double[] auxAngles = {0.0, 45.0, 90.0, 135.0, 180.0, 45.0, 90.0, 135.0};
    private static int[][] equivalents = {{1,2}, {0,2}, {0,1}, {0,0}, {1,0}, {2,0}, {2,1}, {2,2}};


    private int totalBurnedCells;
    private Map<Double, Integer> burnedCells;
    private int iterations;

    SimulationImpl(boolean real, long totalTime, double timeStep, String forestPath,  int fireStartX, int fireStartY, String filepath) throws IOException {
        this.totalTime = totalTime;
        this.timeStep = timeStep;
        fm = new FileManager(filepath);
        totalBurnedCells = 0;
        burnedCells = new TreeMap<>();
        iterations = 0;
        if(real) {
            String elev = "C:\\Users\\Constanza\\Documents\\ITBA\\Wildfire Simulation\\terrains\\amazonas\\smaller\\elev.txt";
            String dens = "C:\\Users\\Constanza\\Documents\\ITBA\\Wildfire Simulation\\terrains\\amazonas\\smaller\\dens.txt";
            String veg = "C:\\Users\\Constanza\\Documents\\ITBA\\Wildfire Simulation\\terrains\\amazonas\\smaller\\veg.txt";
            this.forest = FileManager.readTerrainFromMultiple(elev,dens,veg);
            this.forest.getCell(fireStartX,fireStartY).setState(3);
            System.out.println("Forest initialized.");
            runSimulationReal();
        } else {
            this.forest = FileManager.readTerrain(forestPath);
            this.forest.getCell(fireStartX,fireStartY).setState(3);

            System.out.println("Forest initialized.");
            runSimulationBase();
        }

    }



    private Forest initializeForest(int width, int height) {
        Forest forest = new Forest(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Cell cell = new Cell(i, j, 2d, 1, 2, 0, SQUARE_LENGTH);
                forest.getForest()[i][j] = cell;
            }
        }
        return forest;
    }
    private Forest copyForest(Forest previousForest) {
        Forest forest = new Forest(previousForest.getWidth(), previousForest.getHeight());
        for (int i = 0; i < previousForest.getWidth(); i++) {
            for (int j = 0; j < previousForest.getHeight(); j++) {
                Cell cell = new Cell(i, j, previousForest.getCell(i,j).getState(), previousForest.getCell(i,j).getVegetation(), previousForest.getCell(i,j).getDensity(),
                        previousForest.getCell(i,j).getElevation(), SQUARE_LENGTH);
                forest.getForest()[i][j] = cell;
            }
        }
        return forest;
    }
    @Override
    public void runSimulationBase() throws IOException {
        double i = 0.0;
        burnedCells.put(i, totalBurnedCells);
        while(forestOnFire) {
            i += timeStep;
            System.out.println("---------------------- Forest at time: "+i+" ----------------------");
            fm.printForestForAnimation(this.forest);
            iterations++;
            calculateFireEvolution();
            burnedCells.put(i, totalBurnedCells);
        }
        System.out.println("Total burned cells: " + totalBurnedCells);
        System.out.println("Burned cells per dt (average): " + (double)totalBurnedCells/(double)iterations);
        fm.printBurnedCells(burnedCells);
        fm.printForestForAnimation(this.forest);
        fm.close();
    }

    @Override
    public void runSimulationReal() throws IOException {
        for (double i = 0; i < totalTime; i += timeStep) {
            burnedCells.put(i, totalBurnedCells);
            i += timeStep;
            System.out.println("---------------------- Forest at time: "+i+" ----------------------");
            fm.printForestForAnimation(this.forest);
            iterations++;
            calculateFireEvolution();
            burnedCells.put(i, totalBurnedCells);
        }
        System.out.println("Total burned cells: " + totalBurnedCells);
        System.out.println("Burned cells per dt (average): " + (double)totalBurnedCells/(double)iterations);
        fm.printBurnedCells(burnedCells);
        fm.printForestForAnimation(this.forest);
        fm.close();
    }

    @Override
    public void calculateFireEvolution() {
        Forest newForest = copyForest(this.forest);
        int width = this.forest.getWidth();
        int height = this.forest.getHeight();
        forestOnFire = false;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Cell current = this.forest.getCell(i,j);
                //Uso la variable spreadInto para que no se reescriba lo de neighbours
                if(!current.isSpreadInto()) {
                    double currentState = current.getState();
                    if (currentState == 3) {
                        newForest.getCell(i, j).setState(4D);
                        totalBurnedCells++;
                        ArrayList<Cell> neighbours = new ArrayList<>();
                        //  | i-1, j-1 | i, j-1 | i+1, j-1 |
                        //  | i-1, j   | i, j   | i+1, j   |
                        //  | i-1, j+1 | i, j+1 | i+1, j+1 |
                        if(i-1 >= 0 && j-1 >= 0)        neighbours.add(this.forest.getCell(i-1,j-1));
                        if(j-1 >= 0)                    neighbours.add(this.forest.getCell(i,j-1));
                        if(i+1 < width && j-1 >= 0)     neighbours.add(this.forest.getCell(i+1,j-1));
                        if(i-1 >= 0)                    neighbours.add(this.forest.getCell(i-1,j));
                        if(i+1 < width)                 neighbours.add(this.forest.getCell(i+1,j));
                        if(i-1 >= 0 && j+1 < height)    neighbours.add(this.forest.getCell(i-1,j+1));
                        if(j+1 < height)                neighbours.add(this.forest.getCell(i,j+1));
                        if(i+1 < width && j+1 < height) neighbours.add(this.forest.getCell(i+1,j+1));

                        for (Cell c: neighbours) {
                            if(c.getState() != 3d) {
                                // si estaba quemandose no hagas nada, desps se va a poner negra sola
                                Cell n = newForest.getCell(c.getX(), c.getY());
                                if( Math.random() < calculatePBurn(c, current) && n.getState() != 4d && n.getState() != 1d) { //TODO pburn
                                    forestOnFire = true;
                                    n.setState(3d);
                                    this.forest.getCell(c.getX(), c.getY()).setSpreadInto(true);
                                }
                            }
                        }

                        /*
                        Spotting
                        Np = cantidad de ramas que se desprenden
                         */
                        int Np = 0;
                        if(spottingActivated) {
                            Np = (int) Math.floor(Math.random() * 5) + 1 ; // se desprenden entre 1 y 5 ramas
                        }

                        while(Np > 0) {
                            Np--;
                            int rn = (int)((Math.random())*15 + 1); // no dice mucho de este numero, solo q es random
                            int aux = (int) (Math.random() * 8); //entre 0 y 7
                            double blastAngle = blastAngles[aux]; // entre 0 y 360
                            double windAngle; // = getWindAngle(equivalents[aux][0], equivalents[aux][1]);
                            switch(windDirection) {
                                case NORTH: windAngle = 270.0;
                                    break;
                                case NORTHEAST: windAngle = 315.0;
                                    break;
                                case NORTHWEST: windAngle = 225.0;
                                    break;
                                case WEST: windAngle = 180.0;
                                    break;
                                case EAST: windAngle = 0.0;
                                    break;
                                case SOUTH: windAngle = 90.0;
                                    break;
                                case SOUTHEAST: windAngle = 45.0;
                                    break;
                                case SOUTHWEST: windAngle = 135.0;
                                    break;
                                default: throw new RuntimeException("Something went wrong");

                            }
                            double difference = Math.abs(windAngle - blastAngle);
                            double blastDistance = rn * Math.exp(windSpeed*c2*(Math.cos(Math.toRadians(difference)) - 1));
                            Pair<Integer, Integer> blastCellCoordinates = calculateBlastLandingCell(current, blastAngle, blastDistance);
                            /*
                            Chequear si cae dentro de la forest
                             */
                            if(blastCellCoordinates.getKey() >= 0 && blastCellCoordinates.getValue() >=0
                                    && blastCellCoordinates.getKey() < this.forest.getWidth() && blastCellCoordinates.getValue() < this.forest.getHeight()) {

                                Cell blastCell = newForest.getCell(blastCellCoordinates.getKey(), blastCellCoordinates.getValue());

                                if(blastCell.getState() == 2D) {
                                    // pc0 = probabilty that a cell will catch on fire by spotting
                                    // correct it by vegetation density and type
                                    double pc = pc0 * (1 + current.getPDen()) * (1 + current.getVegetation());
                                    if(Math.random() < pc) {
                                        newForest.getCell(blastCell.getX(), blastCell.getY()).setState(3D);
//                                        System.out.println("Spotting ocurred from cell [" + current.getX()
//                                        + ", " + current.getY() + "] to cell [" + blastCell.getX() + ", " +
//                                                blastCell.getY() + "].");
                                        this.forest.getCell(blastCell.getX(), blastCell.getY()).setSpreadInto(true);
                                    }
                                }
                            }
                        }

                    }
                    else {
                        newForest.getCell(i, j).setState(currentState);
                    }
                }
            }
        }
        this.forest = newForest;
        clearForest();
    }

    private Pair<Integer, Integer> calculateBlastLandingCell(Cell from, double blastAngle, double blastDistance) {
        int x = from.getX() + (int) (blastDistance * Math.cos(Math.toRadians(blastAngle)));
        int y = from.getY() + (int) (blastDistance * Math.sin(Math.toRadians(blastAngle)));
       // System.out.println("Va de celda [" + from.getX() + ", " + from.getY() + "] a [" + x + ", " + y + "]");
        return new Pair<>(x,y);
    }

    private void clearForest() {
        for (int i = 0; i < this.forest.getWidth(); i++) {
            for (int j = 0; j < this.forest.getHeight(); j++) {
                this.forest.getCell(i,j).setSpreadInto(false);
            }
        }
    }

    private void printForest() {
        for (int i = 0; i < this.forest.getHeight(); i++) {
            for (int j = 0; j < this.forest.getWidth(); j++) {
                System.out.print(this.forest.getCell(j,i)+" ");
            }
            System.out.println();
        }
    }

    private double calculatePBurn(Cell evaluatedCell, Cell burningCell) {

        double Pw = calculatePWind(evaluatedCell, burningCell);
        double Ps = calculatePSlope(evaluatedCell, burningCell);
        return Ph * (1 + evaluatedCell.getPVeg()) * (1 + evaluatedCell.getPDen()) * Pw * Ps;

    }

    private double calculatePWind(Cell evaluatedCell, Cell burningCell) {

        int column;
        int row;
        if((evaluatedCell.getX() - burningCell.getX()) < 0) {
            column = 0;
        } else if ((evaluatedCell.getX() - burningCell.getX()) == 0) {
            column = 1;
        } else {
            column = 2;
        }

        if((evaluatedCell.getY() - burningCell.getY()) < 0) {
            row = 0;
        } else if ((evaluatedCell.getY() - burningCell.getY()) == 0) {
            row = 1;
        } else {
            row = 2;
        }

        double angle = getWindAngle(row, column);
        double ft = Math.exp(windSpeed*c2*(Math.cos(Math.toRadians(angle)) - 1));

        return Math.exp(c1*windSpeed)*ft;
    }

    private double getWindAngle(int row, int column) {
        double angle;
        switch(windDirection) {
            case NORTH:
                angle = northMatrix[row][column];
                break;
            case NORTHEAST:
                angle = northEastMatrix[row][column];
                break;
            case NORTHWEST:
                angle = northWestMatrix[row][column];
                break;
            case SOUTH:
                angle = southMatrix[row][column];
                break;
            case SOUTHWEST:
                angle = southWestMatrix[row][column];
                break;
            case SOUTHEAST:
                angle = southEastMatrix[row][column];
                break;
            case WEST:
                angle = westMatrix[row][column];
                break;
            case EAST:
                angle = eastMatrix[row][column];
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + windDirection);
        }
        return angle;
    }

    private double calculatePSlope(Cell evaluatedCell, Cell burningCell) {


        double angle;
            /*
            Case 1: Adjacent
             */
        if((evaluatedCell.getX() == burningCell.getX()) || (evaluatedCell.getY() == burningCell.getY())) {
            angle = Math.atan((evaluatedCell.getElevation() - burningCell.getElevation())/SQUARE_LENGTH);
        } else {
            /*
            Case 2: Diagonal
             */
            angle = Math.atan((evaluatedCell.getElevation() - burningCell.getElevation())/(Math.sqrt(2)*SQUARE_LENGTH));
        }

        return Math.exp(a*angle);
    }
}

