//This file is for handling events closest related to the user commands/actions

//importing required classes
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Command {
    //getting deBug
    boolean deBug = Debug.getDeBug();
    //setting attributes
    String cmd; //command
    boolean validCmd;
    boolean discarded;
    boolean turnEnd;
    boolean error;
    
    //creating scanner object that reads from System.in
    static Scanner InputReader = new Scanner(System.in);
    //creating function to get InputReader
    public static Scanner GetInputReader() {return (InputReader);}

    //creating CMD object
    public static Command CMD = new Command();
    //creating funtion to get CMD
    public static Command getCMD() {return (CMD);}
    //creating commands arraylist
    private static ArrayList<String> commands = new ArrayList<>();
    //creating function to get commands
    public static List<String> getCommands() {return (commands);}
    //getting players objects
    Hand comp = Hand.getComp();
    Hand user = Hand.getUser();
    //getting base hand object
    Hand base = Hand.getBase();

    public void playerAction() {
        //setting attributes
        turnEnd = false;
        validCmd = false;
        cmd = "empty";

        //while input is invalid
        while (validCmd != true) {
            System.out.println("Enter your desired action. To see the list of actions, enter "+'"'+"Actions"+'"');
            //getting command from user
            cmd = InputReader.nextLine();
            //if debug is true
            if (deBug) {
                // Checking command

                //printing original command
                System.out.println("OG Command: "+cmd);
            }

            System.out.println();
            //setting cmd to lower case
            cmd = cmd.toLowerCase();
            //if debug is true
            if (deBug) {
                // Checking modified command to make sure correct command is executed

                //printing updated command
                System.out.println("Updated Command: "+cmd);
            }

            //if input is atleast 3 chars long
            if (cmd.length() >= 3) {
                //setting cmd to first 3 chars
                cmd = cmd.substring(0,3);
                //if debug is true
                if (deBug) {
                    // Making sure cmd was updated correctly

                    //printing updated command
                    System.out.println("Trimmed Command: "+cmd);
                }

                //looping through commands arraylist
                for (int i = 0; i < commands.size(); i++) {
                    
                    //if cmd = current command
                    if (cmd.contains(commands.get(i))) {
                        
                        //updating var
                        validCmd = true;

                        //creating switch case
                        switch (cmd) {
                            case "act":
                                listActions();
                                break;
                            case "dis":
                                runDiscard();
                                break;
                            case "dra":
                                runDraw();
                                break;
                            case "han":
                                readHand();
                                break;
                            case "sco":
                                readScores();
                                break;
                            case "poi":
                                showPoints();
                                break;
                            case "rul":
                                readRules();
                                break;
                        }
                    }
                }

                //if command is not valid
                if (validCmd != true) {
                    System.out.println("That is an invalid command. Please make sure it is spelled correctly.");
                    System.out.println("Type "+'"'+"Actions"+'"'+" to see a list of player actions.");
                }
            //if input is less than 3 chars
            } else {
                System.out.println("Please enter a valid command of at least 3 characters.");
                System.out.println("Type "+'"'+"Actions"+'"'+" to see a list of player actions.");
            }
        }
    }
    
    //creating commands
    String actions = new String("act");
    String discard = new String("dis");
    String draw = new String("dra");
    String hand = new String("han");
    String scores = new String("sco");
    String points = new String("poi");
    String rules = new String("rul");
    
    //adding commands to arraylist
    public void setCommands() {
        //adding commands to arraylist
        commands.add(actions);
        commands.add(discard);
        commands.add(draw);
        commands.add(hand);
        commands.add(scores);
        commands.add(points);
        commands.add(rules);
    }

    //printing player action options
    public void listActions() {
        System.out.println("---Player Actions---");
        System.out.println("_________________________________________________");
        System.out.println("Actions - Displays list of player actions");
        System.out.println("Rules - Displays list of game rules");
        System.out.println("Hand - Displays player hand");
        System.out.println("Points - Displays point values of each card");
        System.out.println("Scores - Displays current scores of both players");
        System.out.println("Discard - Discards a card of your choice");
        System.out.println("Draw - Draws a card from the deck");
        System.out.println("_________________________________________________");
        System.out.println();
    }

    //discarding card of user's choice
    public void runDiscard() {
        //player has not discarded
        if (CMD.discarded != true) {
            //setting attributes
            int slot;
            error = true;

            //while there was an error
            while (error != false) {
                //resetting var
                error = false;
                try {
                    System.out.println("Which card slot would you like to discard? (1-7)");
                    System.out.println("Slot 1: "+Hand.user.hand.get(0));
                    System.out.println("Slot 2: "+Hand.user.hand.get(1));
                    System.out.println("Slot 3: "+Hand.user.hand.get(2));
                    System.out.println("Slot 4: "+Hand.user.hand.get(3));
                    System.out.println("Slot 5: "+Hand.user.hand.get(4));
                    System.out.println("Slot 6: "+Hand.user.hand.get(5));
                    System.out.println("Slot 7: "+Hand.user.hand.get(6));

                    //attempting to get slot from user
                    slot = InputReader.nextInt()-1;
                    //reading & discarding user input
                    InputReader.nextLine();
                    //if debug is true
                    if (deBug) {
                        // Checking user input to make sure correct card is discarded

                        //printing user input
                        System.out.println("Entered Slot: "+slot);
                    }

                    //if input is valid (1-7)
                    if (slot+1 > 0 && slot+1 < 8) {
                        //if debug is true
                        if (deBug) {
                            // Showing which card was discarded

                            //printing card
                            System.out.println("Discarding: "+Hand.user.hand.get(slot));
                        }
                        //discarding selected card
                        Hand.user.discard(Hand.user,Hand.user.hand.get(slot));
                        //updating var
                        CMD.discarded = true;
                    //else if input is invalid
                    } else {
                        //updating var
                        error = true;
                        System.out.println();
                        System.out.println("-==-==-==-==-==-Error-==-==-==-==-==-");
                        System.out.println("Please enter a whole number from 1-7");
                        System.out.println("-==-==-==-==-==-==-==-==-==-==-==-==-");
                        System.out.println();
                    }
                    
                } catch (InputMismatchException i) {
                    //updating var
                    error = true;
                    System.out.println();
                    System.out.println("-==-==-==-==-==-Error-==-==-==-==-==-");
                    System.out.println("Please enter a whole number from 1-7");
                    System.out.println("-==-==-==-==-==-==-==-==-==-==-==-==-");
                    System.out.println();
                    //reading & discarding user input
                    InputReader.nextLine();
                }
            }
        } else {
            System.out.println("You may only discard once per turn.");
            System.out.println("To end your turn, draw a card.");
            System.out.println("Type "+'"'+"Actions"+'"'+" to see a list of player actions.");
        }
        System.out.println();
    }

    //drawing card
    public void runDraw() {
        //if player has discarded
        if (CMD.discarded != false) {
            System.out.println("Drawing card...");
            //running draw function
            Hand.user.draw(Hand.user);
            //printing newest card in hand
            System.out.println("Card Drawn: "+Hand.user.hand.get(Hand.user.hand.size()-1));
            //updating var
            CMD.turnEnd = true;
        } else {
            System.out.println("Drawing ends your turn, you must discard first.");
            System.out.println("Type "+'"'+"Actions"+'"'+" to see a list of player actions.");
        }
        System.out.println();
    }
    
    //printing user's hand
    public void readHand() {
        //running printHand function
        Hand.base.printHand(Hand.user);
        System.out.println();
    }

    //printing player scores
    public void readScores() {
        //calculating player scores
        Hand.user.calcScore(Hand.user);
        Hand.comp.calcScore(Hand.comp);

        //printing player scores
        System.out.println(Hand.user.playerName+"'s Score: "+Hand.user.Score);
        System.out.println(Hand.comp.playerName+"'s Score: "+Hand.comp.Score);
        System.out.println();
    }

    //printing point values of each card
    public void showPoints() {
        System.out.println("---Card Points---");
        System.out.println("---------------------------------------------");
        System.out.println("Remember, the goal is to get the LEAST points");
        System.out.println("Ace: -10 pts");
        System.out.println("2-10: 5 pts");
        System.out.println("Jack & Queen: 10 pts");
        System.out.println("King: 15 pts");
        System.out.println("----------------------------------------------");
        System.out.println();
    }

    //printing game rules
    public void readRules() {
        System.out.println("----Game Rules----");
        System.out.println("_______________________________________________________________________________________________________________________________________");
        System.out.println("The overall goal is to have the LEAST points at the end of the game.");
        System.out.println("Each player's turn consists of discarding a card and then drawing a card.");
        System.out.println("Players continue taking turns until they have reached the total number of rounds which ends the game.");
        System.out.println("By default, the total number of rounds is 10, meaning each player will get 10 turns.");
        System.out.println("You can change the total rounds at the beginning of the game.");
        System.out.println("To interact with the game, you can use your player actions (enter "+'"'+"Actions"+'"'+" at any point during your turn to learn about them.");
        System.out.println("________________________________________________________________________________________________________________________________________");
        System.out.println();
    }
}