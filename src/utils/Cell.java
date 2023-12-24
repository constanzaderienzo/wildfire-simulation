package utils;

import model1.StateEnum;

public class Cell{
    private double state;
    private int x,y;
    private double ros;
    private int vegetation;
    private int density;
    private double elevation;
    private double squareLength;
    private boolean spreadInto;


    public Cell(int x, int y, double initialState, int vegetation, int density, double elevation, double squareLength) {
        this.state = initialState;
        this.x = x;
        this.y = y;
        this.vegetation = vegetation;
        this.density = density;
        this.elevation = elevation;
        this.squareLength = squareLength;
        this.spreadInto = false;
    }

    public double getSquareLength() {
        return squareLength;
    }

    public void setSquareLength(double squareLength) {
        this.squareLength = squareLength;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public int getDensity() {
        return density;
    }

    public double getPDen() {
        switch (density) {
            case 1:
                return -0.4;
            case 2:
                return 0;
            case 3:
                return 0.3;
        }
        return -0.4;
        /*Tavira and Amazonas*/
//        if(density >= 10 && density <= 40){
//            return -0.3;
//        }
//        else if(density <= 80) {
//            return 0;
//        }
//        else if (density <= 100){
//            return 0.3;
//        }
//        else {
//            return -1;
//        }
    }

    public void setDensity(int density) {
        this.density = density;
    }

    public double getState(){
        return this.state;
    }

    public void setState(double state) {
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getRos() {
        return ros;
    }

    public void setRos(double ros) {
        this.ros = ros;
    }

    public int getVegetation() {
        return vegetation;
    }

    public void setVegetation(int vegetation) {
        this.vegetation = vegetation;
    }

    public double getPVeg() {
        switch (vegetation) {
            case 1:
                return -0.3;
            case 2:
                return 0;
            case 3:
                return 0.4;

        }
        return 0;
        /*Amazonas GlobalMap*/
//        switch (vegetation) {
//            case 1:
//            case 2:
//            case 3:
//            case 4:
//            case 5:
//            case 6:
//            case 7:
//                return 0.4;
//            case 8:
//            case 9:
//            case 10:
//                return -0.4;
//            case 11:
//            case 12:
//            case 13:
//            case 14:
//            case 15:
//                return -0.5;
//            case 16:
//            case 17:
//            case 18:
//            case 19:
//            case 20:
//                return -1;
//        }
//        return 0;

        /*Tavira Copernicus*/
//        if(vegetation <= 200 || vegetation >= 331){
//            return -1;
//        }
//        else if (vegetation <= 300) {
//            return -0.4;
//        }
//        else {
//            return 0.4;
//        }
    }

    @Override
    public String toString() {
        return "[" +
                "s=" + state +
//                ", x=" + x +
//                ", y=" + y +
//                ", v=" + vegetation +
                ']';
    }

    public void setSpreadInto(boolean spreadInto) {
        this.spreadInto = spreadInto;
    }

    public boolean isSpreadInto() {
        return spreadInto;
    }
}
