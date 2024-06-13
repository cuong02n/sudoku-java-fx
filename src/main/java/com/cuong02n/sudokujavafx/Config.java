package com.cuong02n.sudokujavafx;

public class Config {

    public static double[] cellSizes = {0, 0, 0, 0, 100, 0, 0, 60, 0, 50, 0, 0, 0, 0, 0, 0, 26,0,22};
    public static double[] textSizes = {0, 0, 0, 0, 40, 0, 0, 32, 0, 25, 0, 0, 0, 0, 0, 0, 15,0,12};
    
    public static int[][] strokeThickLineSize7 = {// x1,y1,x2,y2
            {0,0,0,7},
            {0,0,7,0},
            {7,7,0,7},
            {7,7,7,0},
            
            {3,0,3,2},
            {3,2,1,2},
            {1,2,1,3},
            {1,3,0,3},

            {4,0,4,3},
            {4,3,2,3},
            {2,3,2,6},
            {2,4,0,4},
            {2,6,3,6},
            {3,7,3,4},
            {3,4,5,4},
            {5,4,5,1},
            {5,1,4,1},
            {5,3,7,3},
            {7,4,6,4},
            {6,4,6,5},
            {6,5,4,5},
            {4,5,4,7}
    }; 
}
