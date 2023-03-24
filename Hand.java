//This file is for handling events closest related to the player hands

//importing required classes
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Hand extends Card {
    //creating attributes
    int handCap = 7;
    String playerName;
    int Score;
    boolean gameOver;
    int round;
    int maxRounds;

    //base constructor (custom game rules)
    public Hand(int maxRounds) {
        this.maxRounds = maxRounds;
        gameOver = false;
        round = 0;
    }

    //constructor for empty input
    public Hand() {
        playerName = "empty";
        cardId = 0;
        seq = 0;
        court = "empty";
        suit = "empty";
    }

    //constructor for player info
    public Hand(String pName) {
        this.playerName = pName;
    }

    //constructor for card info
    public Hand(int cardID,int seqNum,String court,String suit) {
        this.cardId = cardID;
        this.seq = seqNum;
        this.court = court;
        this.suit = suit;
    }

    //creating base object (game rules)
    public static Hand base = new Hand(10);
    //creating function to get base
    public static Hand getBase() {return(base);}

    //getting pack and discard arraylist
    List<Card> pack = Deck.getPack();
    List<Card> discard = DiscardPile.getDiscard();
    //getting CMD object
    Command CMD = Command.getCMD();
    //getting InputReader
    Scanner InputReader = Command.GetInputReader();

    //getting discard object
    DiscardPile OBDiscard = DiscardPile.getOBDiscard();

    //creating hand arraylist
    ArrayList<Card> hand = new ArrayList<>();
    //creating function to get hand
    public List<Card> getHand() {return (hand);}

    //creating players
    static Hand comp = new Hand("COMPUTER");
    static Hand user = new Hand("USER");
    //creating function to get players
    public static Hand getComp() {return (comp);}
    public static Hand getUser() {return (user);}

    //dealing cards
    public void deal(Hand PLAYER) {
        //setting attribute
        Hand pl = new Hand();

        //player = computer
        if (PLAYER == comp) {
            pl = comp;
        //player = user
        } else {
            pl = user;
        }

        //if player has no cards
        if (pl.hand.size() == 0) {
            //looping through hand
            for (int i = 0; i < pl.handCap; i++) {
                //setting attribute
                Card p = pack.get(i);
                //if deck is empty
                if (pack.size() == 0) {
                    //adding discarded cards to pack
                    OBDiscard.circulate();
                }

                //adding card to hand
                pl.hand.add(p);
                //removing card from pack
                pack.remove(p);
            }
            System.out.println(pl.playerName+" was dealt "+pl.hand.size()+" cards");
            System.out.println();
            //if player = user
            if (pl == user) {
                //printing user's hand
                printHand(pl);
                System.out.println();
            }
        }
    }

    //drawing cards
    public void draw(Hand PLAYER) {
        //setting attribute
        int numDrawn = 0;
        Hand pl = new Hand();

        //player = computer
        if (PLAYER == comp) {
            pl = comp;
        //player = user
        } else {
            pl = user;
        }

        //looping until hand is full
        for (int i = pl.hand.size(); i < pl.handCap; i++) {
            //setting attribute
            Card p = pack.get(i);
            //if deck is empty
            if (pack.size() == 0) {
                //adding discarded cards to pack
                OBDiscard.circulate();
            }

            //adding card to hand
            pl.hand.add(p);
            //recognizing a card was drawn
            numDrawn++;
            //removing card from pack
            pack.remove(p);
        }
        System.out.println(pl.playerName+" drew "+numDrawn+" card(s)");
        System.out.println();
    }

    //discarding card
    public void discard(Hand PLAYER,Card CARD) {
        //setting attributes
        Hand pl = new Hand();
        Card C = CARD;

        //player = computer
        if (PLAYER == comp) {
            pl = comp;
        //player = user
        } else {
            pl = user;
        }

        //adding card to discard pile
        discard.add(C);

        //if card is Ace or 8
        if (C.court == "Ace" || C.seq == 8) {
            System.out.println(pl.playerName+" discarded an "+C.name);
        //if card is not Ace or 8
        } else {
            System.out.println(pl.playerName+" discarded a "+C.name);
        }

        //removing card from hand
        pl.hand.remove(C);

        //if player = comp
        if (pl == comp) {
            //running draw function
            draw(pl);
        }
    }

    //playing computer's turn
    public void compTurn() {
        //setting attribute
        int maxIND = 0;
        
        //if hand does not have correct num of cards
        if (comp.hand.size() < comp.handCap) {
            //running draw function
            comp.draw(comp);
            System.out.println();
        }

        //looping through hand
        for (int i = 0; i < comp.hand.size(); i++) {
            //if next val > current val
            if (comp.hand.get(maxIND).seq < comp.hand.get(i).seq) {
                //updating max val
                maxIND = i;
            }

            //if loop has finished
            if (i == comp.hand.size()-1) {
                //setting attribute
                Card m = comp.hand.get(maxIND);

                System.out.println();
                //discarding max card
                discard(comp, m);
            }
        }
    }

    //playing user's Turn
    public void userTurn() {
        //listing actions
        CMD.listActions();
        System.out.println();
        //printing user's hand
        printHand(user);
        System.out.println();

        //resetting vars
        CMD.turnEnd = false;
        CMD.discarded = false;
        //looping until turn ends
        while (CMD.turnEnd != true) {
            //running playerAction function
            CMD.playerAction();
        }
    }

    //playing game
    public void play() {
        //setting attributes
        round = 1;
        boolean error = true;

        System.out.println();
        //reading rules
        CMD.readRules();
        System.out.println();
        System.out.println("If you would like to change the number of rounds, please enter a number now. Otherwise, enter "+'"'+"0"+'"');
        //looping until valid input is recieved
        while (error != false) {
            try {
                //resetting var
                error = false;
                //getting temp var from user
                int temp = InputReader.nextInt();

                //if input != 0 & is greater than 3
                if (temp != 0 && temp > 3) {
                    //setting new maxRounds value
                    base.maxRounds = temp;
                    System.out.println("The max number of rounds is now "+temp+".");
                } else if (temp == 0) {
                    System.out.println("The max number of rounds will remain as 10.");
                } else {
                    //updating var
                    error = true;
                    System.out.println();
                    System.out.println("Please enter a whole number greater than 3. Enter 0 to skip this step.");
                }
            } catch (InputMismatchException i) {
                //updating var
                error = true;
                System.out.println();
                System.out.println("Please enter a whole number greater than 3. Enter 0 to skip this step.");
            }
            //reading & discarding user input
            InputReader.nextLine();
        }
        System.out.println();
        //showing points
        CMD.showPoints();
        System.out.println();

        //dealing cards to players
        Hand.comp.deal(Hand.comp);
        Hand.user.deal(Hand.user);

        //looping for max num of rounds
        for (int i = 0; i < maxRounds; i++) {
            System.out.println("--------------------");
            System.out.println("Round "+round);
            //running player turns
            userTurn();
            compTurn();
            //calculating player scores
            calcScore(comp);
            calcScore(user);
            
            //acknowledging a round was completed
            round++;
        }

        //calculating player scores
        calcScore(Hand.comp);
        calcScore(Hand.user);
        
        //if computer has least points
        if (Hand.comp.Score < Hand.user.Score) {
            System.out.println("CONGRATULATIONS, "+Hand.comp.playerName+"!!!");
            System.out.println(Hand.comp.playerName+" won with "+Hand.comp.Score+" pts.");
            System.out.println();
            System.out.println("As for you, player...");
            System.out.println("Don't feel bad, we all lose to a computer sometimes...");
        //if user has least points
        } else {
            System.out.println("CONGRATULATIONS, "+Hand.user.playerName+"!!!");
            System.out.println(Hand.user.playerName+" won with "+Hand.user.Score+" pts.");
            System.out.println();
            System.out.println("How does it feel? To win against the champion.");
        }
    }

    //printing hand
    public void printHand(Hand PLAYER) {
        //setting attributes
        Hand pl = new Hand();

        //player = computer
        if (PLAYER == comp) {
            pl = comp;
        //player = user
        } else {
            pl = user;
        }

        System.out.println(pl.playerName+"'s hand:");
        //looping through hand
        for (int i = 0; i < pl.hand.size(); i++) {
            //i is last card
            if (i == pl.hand.size()-1) {
                System.out.print("& "+pl.hand.get(i));
            //i is not last card
            } else {
                System.out.print(pl.hand.get(i)+", ");
            }
        }
        System.out.println();
    }

    //printing num of cards in hand
    public void handSize(Hand PLAYER) {
        //setting attribute
        Hand pl = new Hand();

        //player = computer
        if (PLAYER == comp) {
            pl = comp;
        //player = user
        } else {
            pl = user;
        }

        //creating switch case
        switch (pl.hand.size()) {
            case 0:
                System.out.println(pl.playerName+"'s hand is empty.");
                break;
            case 1:
                System.out.println(pl.playerName+" has 1 card in their hand.");
                break;
            case 7:
                System.out.println(pl.playerName+"'s hand is full.");
                break;
            default:
                System.out.println(pl.playerName+"has "+pl.hand.size()+" cards in their hand.");
                break;
        }
        System.out.println();
    }

    //getting player's score
    public void calcScore(Hand PLAYER) {
        //setting attributes
        Hand pl;
        int pts;
        int ttl = 0;

        //if PLAYER = comp
        if (PLAYER == comp) {
            //pl = comp
            pl = comp;
        //PLAYER != comp
        } else {
            //pl = user
            pl = user;
        }

        //looping through pl's hand
        for (int i = 0; i < pl.hand.size(); i++) {
            //setting attribute
            Card c = pl.hand.get(i);

            //creating switch case
            switch (c.seq) {
                //sequence = 1
                case 1:
                    //setting pts value
                    pts = -10;
                    break;
                case 11:
                    pts = 10;
                    break;
                case 12:
                    pts = 10;
                    break;
                case 13:
                    pts = 15;
                    break;
                default:
                    pts = 5;
                    break;
            }

            //adding pts to total
            ttl += pts;

            //if loop has finished
            if (i == pl.hand.size()-1) {
                //setting player's score
                pl.Score = ttl;
            }
        }
    }
}