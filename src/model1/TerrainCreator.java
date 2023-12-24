package model1;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TerrainCreator {

    /*
        700 width
        300 height
        De izquierda a derecha va teniendo tres partes, aumentando la densidad
     */
    public static void degradeTerrainDensity() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("terrains/densTerrain.txt", true));
        StringBuilder builder = new StringBuilder().append(700 + "\r\n").append(300 + "\r\n");

        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 300; j++) {
                builder.append(i + " " + j + " " + "2.0" + " " + "2" + " " + "1" + " " + "0.0" + " " + "1.0" + "\r\n");
            }
        }

        for(int i = 100; i < 300; i++) {
            for(int j = 0; j < 300; j++) {
                builder.append(i + " " + j + " " + "2.0" + " " + "2" + " " + "2" + " " + "0.0" + " " + "1.0" + "\r\n");
            }
        }

        for(int i = 300; i < 700; i++) {
            for(int j = 0; j < 300; j++) {
                builder.append(i + " " + j + " " + "2.0" + " " + "2" + " " + "3" + " " + "0.0" + " " + "1.0" + "\r\n");
            }
        }
        bw.write(builder.toString());
        bw.flush();
        bw.close();
        System.out.println("Terrain created.");
     }



    /*
       700 width
       300 height
       De izquierda a derecha va teniendo tres partes, disminuyendo la elevacion
    */
    public static void degradeTerrainElevation() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("terrains/elevTerrain.txt", true));
        StringBuilder builder = new StringBuilder().append(700 + "\r\n").append(300 + "\r\n");
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 300; j++) {
                builder.append(i + " " + j + " " + "2.0" + " " + "2" + " " + "3" + " " + "1505.0" + " " + "1.0" + "\r\n");
            }
        }

        for(int i = 100; i < 300; i++) {
            for(int j = 0; j < 300; j++) {
                builder.append(i + " " + j + " " + "2.0" + " " + "2" + " " + "3" + " " + "1002.0" + " " + "1.0" + "\r\n");
            }
        }

        for(int i = 300; i < 700; i++) {
            for(int j = 0; j < 300; j++) {
                builder.append(i + " " + j + " " + "2.0" + " " + "2" + " " + "3" + " " + "400.0" + " " + "1.0" + "\r\n");
            }
        }
        bw.write(builder.toString());
        bw.flush();
        bw.close();
        System.out.println("Terrain created.");
    }

    /*
       500 width
       500 height
       Sin elevacion
       Densidad siempre igual.
    */
    public static void simpleSquare() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("terrains/simpleSquare.txt", true));
        StringBuilder builder = new StringBuilder().append(500 + "\r\n").append(500 + "\r\n");
        for(int i = 0; i < 500; i++) {
            for(int j = 0; j < 500; j++) {
                builder.append(i + " " + j + " " + "2.0" + " " + "2" + " " + "3" + " " + "0.0" + " " + "1.0" + "\r\n");
            }
        }

        bw.write(builder.toString());
        bw.flush();
        bw.close();
        System.out.println("Simple square terrain created.");
    }

    /*
    Terreno inclinado.
     */
    public static void slopeSquare(int max) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("terrains/slopeSquare" + max + ".txt", true));
        StringBuilder builder = new StringBuilder().append(500 + "\r\n").append(500 + "\r\n");
        double maxD = (double) max;
        for(int i = 0; i < 500; i++) {
            for(int j = 0; j < 500; j++) {
                double elev = max/499.0 * i;
                builder.append(i + " " + j + " " + "2.0" + " " + "2" + " " + "3" + " " + elev + " " + "1.0" + "\r\n");
            }
        }

        bw.write(builder.toString());
        bw.flush();
        bw.close();
        System.out.println("Slope square terrain created.");
    }

    /*
       500 width
       500 height
       Elevacion intercalada
       Densidad siempre igual.
    */
    public static void differentElevationsSquare() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("terrains/differentTerrain.txt", true));
        StringBuilder builder = new StringBuilder().append(500 + "\r\n").append(500 + "\r\n");
        boolean auxi =  true;
        for(int i = 0; i < 500; i++) {
            for(int j = 0; j < 500; j++) {
                if(auxi) {
                    if(j % 2 == 0) {
                        builder.append(i + " " + j + " " + "2.0" + " " + "2" + " " + "3" + " " + "0.0" + " " + "1.0" + "\r\n");
                    } else {
                        builder.append(i + " " + j + " " + "2.0" + " " + "2" + " " + "3" + " " + "800.0" + " " + "1.0" + "\r\n");
                    }
                } else {
                    if(j % 2 != 0) {
                        builder.append(i + " " + j + " " + "2.0" + " " + "2" + " " + "3" + " " + "0.0" + " " + "1.0" + "\r\n");
                    } else {
                        builder.append(i + " " + j + " " + "2.0" + " " + "2" + " " + "3" + " " + "800.0" + " " + "1.0" + "\r\n");
                    }
                }

            }
            auxi = !auxi;
        }

        bw.write(builder.toString());
        bw.flush();
        bw.close();
        System.out.println("Different elevations terrain created.");
    }
}
