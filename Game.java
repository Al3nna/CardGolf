//This program was made by Alenna for the CTE course "Software & Programming Development 2" instructed by Mr. Gross
//Email - alenna.castaneda@oneidaihla.org
//This file is for handling events closest related to the overall game

//importing required classes
//import java.util.List;

public class Game {
    //getting deBug
    boolean deBug = Debug.getDeBug();
    public static void main(String[] args) {
        //getting main deck & discard
        Deck main = Deck.getMain();
        DiscardPile OBDiscard = DiscardPile.getOBDiscard();
        //adding cards to pack
        main.createPack();
        //printing cards in pack
        main.printPack();
        //shuffling pack
        main.shuffle();
        
        System.out.println();
        //printing num of cards in pack & discard pile
        main.packSize();
        OBDiscard.discardSize();

        //getting base Hand
        Hand base = Hand.getBase();
        //getting CMD object
        Command CMD = Command.getCMD();

        //putting commands in arraylist
        CMD.setCommands();
        //running play function
        base.play();
    }
}