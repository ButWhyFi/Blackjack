import java.util.Scanner;
/**
 * @author Johnny Zheng
 */
public class Blackjack {

  public static void main(String[] args) {

    int dealerTotal = 0;
    int userTotal = 0;
    int hand = 0;
    String hitOrStay = "";
    String dealer = "Dealer's";
    String user = "User's";
    int[] cardValue;
    String[] cardDrawn;
    boolean bust = false;
    boolean quit = true;
    cardDrawn = new String[10];
    cardValue = new int[10];
    
    
    intro();
    System.out.println("\n");

    
    do {
      do { // Delete to make the dealer able to bust
        for (;dealerTotal < 17; hand++) {
          dealerTotal = dealerTotal + drawCard(cardDrawn, cardValue, hand);
        }
        // Delete the following block of code to make the dealer able to bust
        bust = skipBust(dealerTotal);
        if (bust) {
          hand = 0;
          dealerTotal = 0;
        }
      } while (bust);

      display(cardDrawn, hand, dealerTotal, dealer);
      enter();
      System.out.println();

      hand = 0;

      cardDrawn = new String[10];
      cardValue = new int[10];

      for (int j = 0; j < 2; j++, hand++) { // 2 cards at once for the start of the game
        userTotal = userTotal + drawCard(cardDrawn, cardValue, hand);
      }

      display(cardDrawn, hand, userTotal, user);


      for (;userTotal < 21;) {
        hitOrStay = readHitOrStay("Type 'H' to HIT or 'S' to STAY: ");
        System.out.println();
        if ((hitOrStay.equals("H")) || (hitOrStay.equals("h"))) {
          hand++;
          userTotal = userTotal + drawCardHandMinus1(cardDrawn, cardValue, hand);
          display(cardDrawn, hand, userTotal, user);
        } 
        else
          break;
      }
      
      System.out.println();
      compareToDealer(userTotal, dealerTotal);
      
      quit = again("Do you want to play again? Y/N: ");
      System.out.println("\n\n");
      
      // Resets variables
      hand = 0;
      dealerTotal = 0;
      userTotal = 0;
  
    } while(!quit);

  System.out.println("\nThank you for playing!");
  }


  private static void intro() {
    System.out.println("Welcome to Blackjack!");
    System.out.println("\nYour goal is to beat the dealer by getting closer to 21.");
    System.out.println("Since the dealer is programmed to never bust, ties count as wins!\n");
    enter();
  }

  public static void enter() {
    Scanner input = new Scanner(System.in);
    System.out.print("Press enter to continue... ");
    input.nextLine();
    System.out.println();
  }

  private static int randNum() {
    int num = 1 + (int) (Math.random() * 13);
    return num;
  }

  /*
   * Converts the integer value of the card to a string representing the card
   */
  private static String numToCard(int num) {
    if (num == 1)
      return "A";
    else if (num == 11)
      return "J";
    else if (num == 12)
      return "Q";
    else if (num == 13)
      return "K";
    else
      return Integer.toString(num);
  }

  /*
   * Converts the card to a value
   */
  private static int numValue(int num) {
    if (num >= 10)
      return 10;
    else
      return num;
  }

  /*
   * Draws a card and adds it to both card and value arrays while incrementing hand
   * Used for the start of the game
   * 
   * @param card: An array representing the card name
   * @param value: An array representing the actual card value
   * @param hand: An integer representing the number of cards the hand has
   */
  private static int drawCard(String card[], int value[], int hand) {
    int draw;
    draw = randNum();
    int total = 0;
    card[hand] = numToCard(draw);
    value[hand] = numValue(draw);
    total = total + value[hand];
    return total;
  }

  /*
   * Same as drawCard, but the value of hand is lowered by one
   * Used for when the user decides to draw or not
   */
  private static int drawCardHandMinus1(String card[], int value[], int hand) {
    int draw;
    draw = randNum();
    int total = 0;
    card[hand - 1] = numToCard(draw);
    value[hand - 1] = numValue(draw);
    total = total + value[hand - 1];
    return total;
  }

/*
 * Returns true or false depending on whether n is greater than 21 or not
 * 
 * @param n: The current value of the hand
 */
  private static boolean skipBust(int n) {
    boolean bust = false;
    if (n > 21)
      bust = true;
    return bust;
  }

  private static void bustOrBlackjack(int n) {
    if (n > 21) 
      System.out.println("Bust!");
    else if (n == 21) 
      System.out.println("Blackjack!");
  }

  /*
   * Reads prompt to user and stores the input
   */
  private static String readHitOrStay(String prompt) {
    String hs = "";
    boolean valid = false;
    Scanner input = new Scanner(System.in);

    while (valid != true) {
      System.out.print(prompt);
      hs = input.nextLine();
      hs.trim();
      if ((hs.equals("H")) || (hs.equals("h")) || (hs.equals("S")) || (hs.equals("s"))) {
        valid = true;
      } else {
        System.out.println("Invalid response! Try again!\n");
        valid = false;
      }
    }
    return hs;
  }

  /*
   * Displays and formats the array
   * 
   * @param: arr is the String array representing the card names
   */
  private static void display(String arr[], int hand, int total, String turn) {
    Scanner input = new Scanner(System.in);
    System.out.print(turn + " hand: ");
    for (int i = 0; i < hand; i++)
      System.out.print(arr[i] + " ");
    System.out.println("\nTotal: " + total);
    bustOrBlackjack(total);
  }
  
 /*
  * Compares the user's total to the dealer's total
  */
  private static void compareToDealer(int user, int dealer) {
    System.out.println();
    if (user > 21) 
      System.out.println("You LOSE!");
    else if (user < dealer) 
      System.out.println("You LOSE!");
    else
      System.out.println("You WIN!");
    System.out.println();
  }

  /*
   * Prints out a prompt and asks for user's input
   */
  private static boolean again(String prompt) {
    boolean valid = false;
    boolean quit = false;
    String ans;
    Scanner input = new Scanner(System.in);
    System.out.print(prompt);
    do {
      ans = input.nextLine();
      if (ans.equals("Y") || ans.equals("y")) {
        valid = true;
        quit = false;
      } 
      else if (ans.equals("N") || ans.equals("n")) {
        valid = true;
        quit = true;
      } 
      else {
        valid = false;
        System.out.println("\nInvalid Response! Try again!");
      }
    } while (valid != true);
    return quit;
  }
}


