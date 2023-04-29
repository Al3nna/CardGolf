//This file is for handling events closest related to debugging

public class Debug {
    public static boolean deBug = false;
    //creating function to get deBug
    public static boolean getDeBug() {return (deBug);}

    //constructor
    public Debug (boolean debugStatus) {
        deBug = debugStatus;
    }
}