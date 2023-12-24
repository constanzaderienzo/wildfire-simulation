package model1;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
       SimulationImpl simulation = new SimulationImpl(false, 1,0.1, "terrains/simpleSquare.txt",249,249, "generated_files/");
//        TerrainCreator.degradeTerrainElevation();
//        TerrainCreator.simpleSquare();
//        TerrainCreator.slopeSquare(100);
//        TerrainCreator.degradeTerrainDensity();
    }
}
