package model1;

import utils.Cell;
import utils.Forest;

import java.io.*;
import java.util.Map;

public class FileManager {

    BufferedWriter bwDensity;
    BufferedWriter bwElevation;
    BufferedWriter bwBurnedCells;
    static int AMAZON_HEIGHT = 1323;
    static int AMAZON_WIDTH = 1304;
    static int TAVIRA_HEIGHT = 276;
    static int TAVIRA_WIDTH = 309;

    public FileManager(String path) {
        try {
            bwDensity = new BufferedWriter(new FileWriter(path + "ForestFire_Density.txt", false));
            bwDensity = new BufferedWriter(new FileWriter(path + "ForestFire_Density.txt", true));
            bwElevation = new BufferedWriter(new FileWriter(path + "ForestFire_Elevation.txt", false));
            bwElevation = new BufferedWriter(new FileWriter(path + "ForestFire_Elevation.txt", true));
            bwBurnedCells = new BufferedWriter(new FileWriter(path + "BurnedCells.txt", false));
            bwBurnedCells= new BufferedWriter(new FileWriter(path + "BurnedCells.txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printForestForAnimation(Forest forest) throws IOException {
        printForestByOption(forest, "density");
        printForestByOption(forest, "elevation");
    }

    /*
        Prints forest to a txt file in order to be read by Ovito.

        If option == density
        Color coding:
                      - Black: Burned down cell
                      - Red: Burning cell
                      - Green: Forest fuel cell with no fire. Green intensity varies according to density.
                      - Light blue: No fuel cell (waterways).


         If option == elevation
         Color coding:
                      - Black: Burned down cell
                      - Red: Burning cell
                      - Green: Forest fuel cell with no fire and elevation < x.
                      - Brown: Forest fuel with no fire and elevation > x. Brown intensity varies according
                      to cell elevation.
                      - Light blue: No fuel cell (waterways).

        Use square shape in Ovito!
     */
    private void printForestByOption(Forest forest, String option) throws IOException {
        int cellAmount = forest.getHeight() * forest.getWidth();
        StringBuilder builder = new StringBuilder().append(cellAmount + "\r\n").append("//ID\t X\t Y\t Radius\t R\t G\t B\r\n");
        //System.out.println("HEIGHT: " + forest.getHeight() + "WIDTH: " + forest.getWidth());
        for(int i = 0; i < forest.getWidth(); i++)  {
            for(int j = 0; j < forest.getHeight(); j++) {
                Cell current = forest.getCell(i, j);
                double id = Math.pow(2, i) * Math.pow(3, j);
                double state = current.getState();
                double rColor = 0.0;
                double gColor = 0.0;
                double bColor = 0.0;
                if(Double.compare(state, 1d) == 0) {
                    // 1d --> Cannot be burned: Waterways for example --> Light blue
                    rColor = 36.0/255.0;
                    gColor = 255.0/255.0;
                    bColor = 234.0/255.0;
                } else if (Double.compare(state, 2d) == 0) {
                    // 2d --> Forest fuel --> Green (intensity varies according to density)

                    if(option.equals("elevation")) {
                        /*
                        0 - 500m: green
                        500 - 1000m: yellow
                        1000 - 1500m: kinda brown
                        more than 1500m: brown
                         */
                        if(current.getElevation() < 500) {
                            rColor = 143.0/255.0;
                            gColor = 247.0/255.0;
                            bColor = 122.0/255.0;
                        } else if (current.getElevation() < 1000) {
                            rColor = 255.0/255.0;
                            gColor = 242.0/255.0;
                            bColor = 119.0/255.0;
                        } else if (current.getElevation() < 1500) {
                            rColor = 255.0/255.0;
                            gColor = 215.0/255.0;
                            bColor = 119.0/255.0;
                        } else {
                            rColor = 98.0/255.0;
                            gColor = 74.0/255.0;
                            bColor = 18.0/255.0;
                        }
                    } else {
                        //density is the default option
                        if(current.getPDen() < -0.1) {
                            //sparse
                            rColor = 130.0/255.0;
                            gColor = 235.0/255.0;
                            bColor = 141.0/255.0;
                        } else if ( current.getPDen()  > 0.1) {
                            //dense
                            rColor = 0.0;
                            gColor = 107.0/255.0;
                            bColor = 11.0/255.0;
                        } else {
                            // normal (between -0.1 and 0.1)
                            rColor = 0.0;
                            gColor = 182.0/255.0;
                            bColor = 18.0/255.0;
                        }
                    }


                } else if (Double.compare(state, 3d) == 0) {
                    // 3d --> Burning down --> Red
                    rColor = 255.0/255.0;
                }

                // else
                // 4d --> Burned down --> Black (0,0,0)

                builder.append(id)
                        .append(" ")
                        .append(current.getX())
                        .append(" ")
                        .append(current.getY())
                        .append(" ")
                        .append(current.getSquareLength()/2)
                        .append(" ")
                        .append(rColor)
                        .append(" ")
                        .append(gColor)
                        .append(" ")
                        .append(bColor)
                        .append("\r\n");
            }
        }
        if(option.equals("elevation")) {
            bwElevation.write(builder.toString());
        } else{
           // System.out.println(builder.toString());
            bwDensity.write(builder.toString());
        }
    }

    public void close() {
        if (bwDensity != null) try {
            bwDensity.flush();
            bwDensity.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        if (bwElevation != null) try {
            bwElevation.flush();
            bwElevation.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        if (bwBurnedCells != null) try {
            bwBurnedCells.flush();
            bwBurnedCells.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void printBurnedCells(Map<Double, Integer> burnedCells) throws IOException {
        StringBuilder builder = new StringBuilder();
        for(Double d: burnedCells.keySet()) {
            builder.append(d + " " + burnedCells.get(d));
            builder.append("\r\n");
        }
        bwBurnedCells.write(builder.toString());
    }

    /*
    File format should be:
    1st Line: Width
    2nd Line: Height
    Cells...

     */
    public static Forest readTerrain(String path) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(path));
        String width = bf.readLine();
        String height = bf.readLine();
        int w = Integer.valueOf(width);
        int h = Integer.valueOf(height);
        Forest ans = new Forest(w, h);
        Cell[][] cells = new Cell[w][h];
        for (int i = 0; i < w*h ; i++) {
            String[] s = bf.readLine().split(" ");
            cells[Integer.valueOf(s[0])][Integer.valueOf(s[1])] = new Cell(Integer.valueOf(s[0]), Integer.valueOf(s[1]), Double.valueOf(s[2]),
                    Integer.valueOf(s[3]), Integer.valueOf(s[4]), Double.valueOf(s[5]), Double.valueOf(s[6]));

        }
        ans.setForest(cells);
        bf.close();
        return ans;
    }

    public static Forest readTerrainFromMultiple(String elevPath, String densPath, String vegPath) throws IOException {
        BufferedReader elevR = new BufferedReader(new FileReader(elevPath));
        BufferedReader densR = new BufferedReader(new FileReader(densPath));
        BufferedReader vegR = new BufferedReader(new FileReader(vegPath));
        int w = TAVIRA_WIDTH;
        int h = TAVIRA_HEIGHT;
        Forest forest = new Forest(w, h);
        Cell[][] cells = new Cell[w][h];
        for (int j = 0; j < h; j++){
            String[] elev = elevR.readLine().split(" ");
            String[] dens = densR.readLine().split(" ");
            String[] veg = vegR.readLine().split(" ");
            for (int i = 0; i < w; i++) {
                int state = 2;
                /*Tavira Copernicus*/
                if(Double.valueOf(veg[i]) <= 142.0 || Double.valueOf(veg[i]) >= 331)
                    state = 1;

                /*Amazonas GlobalMap*/
//                if(Double.valueOf(veg[i]) >=16)
//                    state = 1;
                cells[i][h-j-1] = new Cell(i,h-j-1,state,Double.valueOf(veg[i]).intValue(),Double.valueOf(dens[i]).intValue(),Double.valueOf(elev[i]),100.0);
            }
        }

        forest.setForest(cells);
        elevR.close();
        densR.close();
        vegR.close();
        return forest;
    }

}
