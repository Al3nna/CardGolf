//This file is for handling events closest related to the cards

//importing required classes
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Card {
    //creating attributes
    int cardId;
    int seq; //1-13
    String court; //king, queen, jack, ace, basic
    String suit; //diamonds, hearts, spades, clubs
    String name;

    //constructor for empty input
    public Card() {
        cardId = 0;
        seq = 0;
        court = "empty";
        suit = "empty";
    }

    //constructor for filled input
    public Card(int cardID,int seqNum,String court,String suit) {
        this.cardId = cardID;
        this.seq = seqNum;
        this.court = court;
        this.suit = suit;
    }

    //putting attributes into string
    public String toString() {
        //if seq is 2-10
        if (seq > 1 && seq < 11) {
            //setting name
            name = (seq+" of "+suit);
            //returning name
            return (name);
        //if seq is 1 or 11-13
        } else {
            //setting name
            name = (court+" of "+suit);
            //returning name
            return (name);
        }
    }
}

//this section is to handle events closest related to the deck
class Deck {
    //creating attribute
    int deckCap = 52;

    //creating pack arraylist
    private static ArrayList<Card> pack = new ArrayList<>();
    //creating function to get pack
    public static List<Card> getPack() {return (pack);}
    //creating main Deck object
    static Deck main = new Deck();
    //creating function to get main
    public static Deck getMain() {return (main);}

    //creating new pack
    public void createPack() {
        //clearing pack arraylist
        pack.clear();
        //id counts each card, starting from 0
        int id = 0;
        //creates sequence of 13 cards
        for (int s = 0; s < 13; s++) {
            //making 4 batches
            for (int b = 0; b < 4; b++) {
                //creating new card
                pack.add(new Card());
                //setting values
                pack.get(id).cardId = id;
                pack.get(id).seq = s+1;

                    //setting suit
                //creating switch case
                switch (b) {
                    //b = 0
                    case 0:
                        //setting current card's suit to "Diamonds"
                        pack.get(id).suit = "Diamonds";
                        break;
                    case 1:
                        pack.get(id).suit = "Hearts";
                        break;
                    case 2:
                        pack.get(id).suit = "Clubs";
                        break;
                    case 3:
                        pack.get(id).suit = "Spades";
                        break;
                }

                    //setting court
                //creating switch case
                switch (pack.get(id).seq) {
                    //current card seq = 1
                    case 1:
                        //setting current card's court to "Ace"
                        pack.get(id).court = "Ace";
                        break;
                    case 11:
                        pack.get(id).court = "Jack";
                        break;
                    case 12:
                        pack.get(id).court = "Queen";
                        break;
                    case 13:
                        pack.get(id).court = "King";
                        break;
                    default:
                        pack.get(id).court = "Basic";
                        break;
                }

                //recognizing card has been added
                id++;
            }
        }
    }

    //printing cards in pack
    public void printPack() {
        System.out.println("Current card(s) in the deck:");
        //looping through pack
        for (int i = 0; i < pack.size(); i++) {
            //i is last card in pack
            if (i == pack.size()-1) {
                System.out.print("& "+ pack.get(i));
            //if is not last card in pack
            } else {
                System.out.print(pack.get(i)+", ");
            }
        }
        System.out.println();
    }

    //printing num of cards in pack
    public void packSize() {
        //creating switch case
        switch (pack.size()) {
            //pack.size() = 0
            case 0:
                //printing message
                System.out.println("The pack is empty.");
                break;
            case 1:
                System.out.println("There is 1 card is left in the pack.");
                break;
            case 52:
                System.out.println("All 52 cards are still in the pack.");
                break;
            default:
                System.out.println("There are "+pack.size()+" cards left in the pack.");
                break;
        }
        System.out.println();
    }

    //shuffling pack
    public void shuffle() {
        //shuffling pack arraylist
        Collections.shuffle(pack);
        //shuffling pack arraylist again
        Collections.shuffle(pack);
    }
}

//this section is to handle events closest related to the discard pile
class DiscardPile extends Card{  
    //getting pack arraylist
    List<Card> pack = Deck.getPack();

    //creating discard arraylist
    private static ArrayList<Card> discard = new ArrayList<>();
    //creating function to get discard
    public static List<Card> getDiscard() {return (discard);}
    //creating discard object
    static DiscardPile OBDiscard = new DiscardPile();
    //creating function to get OBDiscard
    public static DiscardPile getOBDiscard() {return (OBDiscard);}

    //shuffling and transferring discard pile to main pack
    public void circulate() {
        //shuffling discard pile
        Collections.shuffle(discard);

        //looping through discard
        for (int i = 0; i < discard.size(); i++) {
            //setting attribute
            Card d = discard.get(i);
            //adding card to pack
            pack.add(d);
            //removing card from discard pile
            discard.remove(d);
        }
        System.out.println();
        System.out.println("----------------------------------------------------------------");
        System.out.println("The discard pile has been shuffled and added back into the deck.");
        System.out.println("----------------------------------------------------------------");
        System.out.println();
    }

    //printing cards in discard arraylist
    public void printDiscard() {
        System.out.println("Current card(s) in the discard pile:");
        //looping through discard pile
        for (int i = 0; i < discard.size(); i++) {
            //if i is last card
            if (i == discard.size()-1) {
                System.out.print("& "+discard.get(i));
            //if i is not last card
            } else {
                System.out.print(discard.get(i)+", ");
            }
        }
        System.out.println();
    }

    //printing num of cards in discard arraylist
    public void discardSize() {
        //creating switch case
        switch (discard.size()) {
            //pack.size() = 0
            case 0:
            //printing message
                System.out.println("The discard pile is empty.");
                break;
            case 1:
                System.out.println("There is 1 card is left in the discard pile.");
                break;
            case 52:
                System.out.println("All 52 cards are still in the discard pile.");
                break;
            default:
                System.out.println("There are "+discard.size()+" cards left in the discard pile.");
                break;
        }
        System.out.println();
    }
}