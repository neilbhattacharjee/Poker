import java.util.LinkedList;
import java.util.*;
import java.util.Comparator;

public class handEvaluator {
    /*This class will take in 7 cards and output the best hand*/
    String[] handCards = new String[2];
    String[] board = new String[5];
    String[] allCards = new String[7];
    String[] allCardsNOSUIT = new String[7];
    String[] allCardsNOSUITSORTED = new String[7];
    String[] noHand = new String[]{"X", "X", "X", "X", "X"};
    Map<String, Integer> rankValues = new HashMap<String, Integer>();


    public handEvaluator(String[] inHandCards, String[] inBoard) {
        this.handCards = inHandCards;
        this.board = inBoard;
        /*transfer handCards and board cards into the same array, allCards*/

        for (int i = 0; i < inHandCards.length; i++) {
            allCards[i] = inHandCards[i];
        }
        for (int i = 2; i < inBoard.length + 2; i++) {
            allCards[i] = inBoard[i - 2];
        }
        removeJPEG();
        deSuitSort();
        populateDict();

    }
    /*The card names are currently in the format "file:JPEG/XX.jpg" */
    /*This method will remove the fluff and keep the XX */

    private void removeJPEG() {

        for (int i = 0; i < handCards.length; i++) {
            handCards[i] = handCards[i].replace("file:JPEG/", "");
            handCards[i] = handCards[i].replace(".jpg", "");
        }
        for (int i = 0; i < board.length; i++) {
            board[i] = board[i].replace("file:JPEG/", "");
            board[i] = board[i].replace(".jpg", "");
        }
        for (int i = 0; i < allCards.length; i++) {
            allCards[i] = allCards[i].replace("file:JPEG/", "");
            allCards[i] = allCards[i].replace(".jpg", "");
        }
    }

    private void deSuitSort() {
        allCardsNOSUIT  = allCards.clone();
        for (int index = 0; index < allCardsNOSUIT.length; index++) {
            if (Character.toString(allCardsNOSUIT[index].charAt(0)).equals("1")) {
                allCardsNOSUIT[index] = "10";
            }
            else {
                allCardsNOSUIT[index] = Character.toString(allCardsNOSUIT[index].charAt(0));
            }
        }
    }

    private void populateDict() {
        rankValues.put("A", 14); rankValues.put("K", 13); rankValues.put("Q", 12);
        rankValues.put("J", 11); rankValues.put("10", 10); rankValues.put("9", 9);
        rankValues.put("8", 8); rankValues.put("7", 7); rankValues.put("6", 6);
        rankValues.put("5", 5); rankValues.put("4", 4); rankValues.put("3", 3);
        rankValues.put("2", 2);
    }



    /*The face cards are currently represented using "J, Q, K, A". We shall replace these with
    11, 12 , 13, while leaving A as is due to the fact that it can act as 14 or 1.*/

    /*THE HANDS OF POKER FROM BEST TO WORST. We shall check from the best ot the worst*/
    /*
     * ROYAL FLUSH
     * STRAIGHT FLUSH
     * FOUR OF A KIND
     * FULL HOUSE
     * FLUSH
     * STRAIGHT
     * THREE OF A KIND
     * TWO PAIR
     * PAIR
     * HIGH CARD
     * */

    public String[] royalFlush() {
        for (int i = 0; i < board.length; i++) {
            System.out.println(board[i]);
        }
        return handCards;
    }

    private String[] flush() {
        /*this method is proof I can use data structures besides arrayLists, even if it's really not
        necessary :)
         */
        /*TODO: Make sure that it returns the highest flush*/
        List<String> clubs = new LinkedList<>();
        List<String> diamonds = new LinkedList<>();
        List<String> hearts = new LinkedList<>();
        List<String> spades = new LinkedList<>();
        List<List<String> >  suits = new LinkedList<>();
        suits.add(clubs);
        suits.add(diamonds);
        suits.add(hearts);
        suits.add(spades);
        for (int i = 0; i < allCards.length; i++) {
            String card = allCards[i];
            if (card.contains("C")) {
                clubs.add(card);
            }
            if (card.contains("D")) {
                diamonds.add(card);
            }
            if (card.contains("H")) {
                hearts.add(card);
            }
            if (card.contains("S")) {
                spades.add(card);
            }
        }
        /*TODO: fix this so that it only returns 5 cards, as well as the highest possible flush*/
        for (List<String> candidate : suits) {
            if (candidate.size() > 4){
                String[] array = candidate.toArray(new String[candidate.size()]);
                return array;
            }
        }
        return noHand;
    }

    private String[] straight() {
        /*It's not as simple as checking to see if 5 numbers are consecutive, as they're */
        /*Put cards inside of a hashset*/
        /*We check the straights in decreasing order to prevent choosing a weaker straight*/
        String[] rank = new String[]{"A","2","3","4","5","6","7","8","9","10","J","Q","K","A"};
        Set<String> cardSet = new HashSet<String>();
        for (String card: allCardsNOSUIT) {
            cardSet.add(card);
        }
        for (int index = 13; index > 3; index-- ) {
            boolean c1 = cardSet.contains(rank[index]);
            boolean c2 = cardSet.contains(rank[index - 1 ]);
            boolean c3 = cardSet.contains(rank[index - 2 ]);
            boolean c4 = cardSet.contains(rank[index - 3 ]);
            boolean c5 = cardSet.contains(rank[index - 4 ]);
            if (c1 && c2 && c3 && c4 && c5) {
                String[] straight = new String[5];
                straight[0] = rank[index];
                straight[1] = rank[index - 1];
                straight[2] = rank[index - 2];
                straight[3] = rank[index - 3];
                straight[4] = rank[index - 4];
                return straight;
            }
        }
        return noHand;
    }

    private void pair() {
        /*Pair must be able to return the 3 high cards associated with it ~ this is very important*/
        /*These methods are being called in order from best to worst; therefore it's not necessary
        /*to make sure that pair() only checks for one pair. If this method is ever called, we know
        that there doesn't exist a two pair in the hand. */
        String[] hand = allCardsNOSUIT.clone();
    }


    /*I've made the realization that it's important to have a comparator to compare two cards
    * Let's make that.*/

    public int compare(String c1, String c2) {
        int card1 = rankValues.get(c1);
        int card2 = rankValues.get(c2);

        if (card1 > card2) {
            return 1;
        }
        if (card2 > card1) {
            return -1;
        }
        return 0;
    }

    public void sort() {
        /*Since we can't override compareTo for strings since the class is final, we're going to have
        * to write this method old fashion style; going to use n^2 solution since it's only O(91)*/
        String[] dupCards = allCardsNOSUIT.clone();
        String[] sortedCards = new String[allCardsNOSUIT.length];
        while (dupCards.length != 0) {
            int index = 0;
            for (int i = 0; i < allCardsNOSUIT.length; i++) {

            }
        }
    }



    public static void main(String[] args) {
        /*(String[] inHandCards, String[] inBoard)*/
        String[] inBoard = new String[]{"file:JPEG/AS.jpg","file:JPEG/10H.jpg","file:JPEG/KC.jpg","file:JPEG/9C.jpg","file:JPEG/2C.jpg" };
        String[] inHandCards = new String[]{"file:JPEG/QC.jpg","file:JPEG/AC.jpg"};
        handEvaluator hand = new handEvaluator(inHandCards, inBoard);
        System.out.println(Arrays.toString(hand.allCardsNOSUIT));
    }


}
