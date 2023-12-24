package utils;

import java.io.IOException;

public interface Simulation {

    void runSimulationBase() throws IOException;
    void runSimulationReal() throws IOException;
    void calculateFireEvolution();

}
