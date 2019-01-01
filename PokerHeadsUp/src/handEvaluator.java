import java.util.LinkedList;
import java.util.*;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class handEvaluator {
    /*This class will take in 5 cards and output the best hand*/
    String[] allCards = new String[5];
    String[] allCardsNOSUIT = new String[5];
    Object[] allCardsSuited = new String[5];
    String[] noHand = new String[]{"X", "X", "X", "X", "X"};
    Map<String, Integer> rankValues = new HashMap<String, Integer>();
    HashMap<String, Integer> handDict = new HashMap<String, Integer>();
    Integer handValue = 0;


    public handEvaluator( String[] board) {
        /*transfer handCards and board cards into the same array, allCards*/
        for (int i = 0; i < board.length; i++) {
            allCards[i] = board[i];
        }
        removeJPEG();
        deSuitSort();
        populateDict();
        addHandValues();
        sort();

    }
    /*The card names are currently in the format "file:JPEG/XX.jpg" */
    /*This method will remove the fluff and keep the XX */

    private void removeJPEG() {
        for (int i = 0; i < allCards.length; i++) {
            allCards[i] = allCards[i].replace("file:JPEG/", "");
            allCards[i] = allCards[i].replace(".jpg", "");
        }
    }

    /*this method will populate handDict*/
    public void addHandValues() {
        for (String card: allCards) {
            handDict.put(card, rankValues.get(card));
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
        /*major spaghetti, please ignore */
        rankValues.put("AS", 14); rankValues.put("KS", 13); rankValues.put("QS", 12);
        rankValues.put("JS", 11); rankValues.put("10S", 10); rankValues.put("9S", 9);
        rankValues.put("8S", 8); rankValues.put("7S", 7); rankValues.put("6S", 6);
        rankValues.put("5S", 5); rankValues.put("4S", 4); rankValues.put("3S", 3);
        rankValues.put("2S", 2);
        rankValues.put("AH", 14); rankValues.put("KH", 13); rankValues.put("QH", 12);
        rankValues.put("JH", 11); rankValues.put("10H", 10); rankValues.put("9H", 9);
        rankValues.put("8H", 8); rankValues.put("7H", 7); rankValues.put("6H", 6);
        rankValues.put("5H", 5); rankValues.put("4H", 4); rankValues.put("3H", 3);
        rankValues.put("2H", 2);
        rankValues.put("AD", 14); rankValues.put("KD", 13); rankValues.put("QD", 12);
        rankValues.put("JD", 11); rankValues.put("10D", 10); rankValues.put("9D", 9);
        rankValues.put("8D", 8); rankValues.put("7D", 7); rankValues.put("6D", 6);
        rankValues.put("5D", 5); rankValues.put("4D", 4); rankValues.put("3D", 3);
        rankValues.put("2D", 2);
        rankValues.put("AC", 14); rankValues.put("KC", 13); rankValues.put("QC", 12);
        rankValues.put("JC", 11); rankValues.put("10C", 10); rankValues.put("9C", 9);
        rankValues.put("8C", 8); rankValues.put("7C", 7); rankValues.put("6C", 6);
        rankValues.put("5C", 5); rankValues.put("4C", 4); rankValues.put("3C", 3);
        rankValues.put("2C", 2);
    }

    private String[] findBestHand() {
        /*call the methods in the order for best hand to worst. Eventually, I should have this
        * method return a "score". This will make it easier to settle tiebreakers when the computer
        * and the player both have the same hand (AA KKK) vs (QQ KKK) Hands will be */
        /* TODO: Must format all the methods below so that their outputs "sort" the hands. This will
        * TODO: make the above problem easy. Below are the formats for each hand*/
        /*Straight Flush: [high to low]
        *Four of a Kind: [X, X, X, X, Y]
        * Full House: [X, X, X, Y, Y]
        *  Flush: [high to low]
        *  Straight: [high to low]
        *  Three of a Kind: [X, X, X, high to low]
        *  Two Pair: [X, X, Y, Y, Z]
        *  Pair: [X, X, high to low]
        *  High Card: [High to low]
        *  might want to make separate method that makes high to low as many of these require it */

        String[] theHand = new String[]{"X", "X", "X", "X", "X"};

        if (!Arrays.equals(straightFlush(), noHand)) {
            System.out.println("straight flush ");
            return straightFlush();
        }
        if (!Arrays.equals(fourKind(), noHand)) {
            System.out.println("four of a kind  ");
            return fourKind();
        }

        if (!Arrays.equals(fullHouse(), noHand)) {
            System.out.println("full house ");
            return fullHouse();
        }
        if (!Arrays.equals(flush(), noHand)) {
            System.out.println("flush ");
            return flush();
        }

        if (!Arrays.equals(straight(), noHand)) {
            System.out.println("straight ");
            return straight();
        }

        if (!Arrays.equals(threeKind(), noHand)) {
            System.out.println("three kind  ");
            return threeKind();
        }

        if (!Arrays.equals(twoPair(), noHand)) {
            System.out.println("two pair ");
            return twoPair();
        }

        if (!Arrays.equals(pair(), noHand)) {
            System.out.println("pair");
            return pair();
        }
        System.out.println("high card");
        return allCards;
    }



    /*The face cards are currently represented using "J, Q, K, A". We shall replace these with
    11, 12 , 13, while leaving A as is due to the fact that it can act as 14 or 1.*/

    /*THE HANDS OF POKER FROM BEST TO WORST. We shall check from the best ot the worst*/
    /*
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


    private String[] straightFlush() {
        /* I can just call flush, and straight */
        String[] hand = allCardsNOSUIT.clone();
        if (!Arrays.equals(straight(), noHand) && !Arrays.equals(flush(), noHand)) {
            return hand;
        }
        handValue = 9;
        return noHand;
    }


    private String[] fourKind() {
        String[] hand = allCardsNOSUIT.clone();
        Map<String, Integer> cardFreq = new HashMap<String, Integer>();
        for (String card: hand) {
            cardFreq.merge(card, 1, Integer::sum);
            if (cardFreq.get(card) == 4) {
                return hand;
            }
        }
        handValue = 8;
        return noHand;

    }


    private String[] fullHouse() {
        /*just check if there are only 2 different cards */
        String[] hand = allCardsNOSUIT.clone();
        Set<String> cardSet = new HashSet<String>();
        for (String card: hand) {
            cardSet.add(card);
        }
        if (cardSet.size() == 2) {
            return hand;
        }
        handValue = 7;
        return noHand;


    }

    private String[] flush() {
        /*this method is proof I can use data structures besides arrayLists, even if it's really not
        necessary :)
         */
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
        for (List<String> candidate : suits) {
            if (candidate.size() > 4){
                String[] array = candidate.toArray(new String[candidate.size()]);
                return array;
            }
        }
        handValue = 6;
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
        handValue = 5;
        return noHand;
    }
    private String[] threeKind() {
        /*Throw them into a hashSet, see if there are duplicates.*/
        String[] hand = allCardsNOSUIT.clone();
        Map<String, Integer> cardFreq = new HashMap<String, Integer>();
        for (String card: hand) {
            cardFreq.merge(card, 1, Integer::sum);
            if (cardFreq.get(card) == 3) {
                return hand;
            }
        }
        handValue = 4;
        return noHand;
    }

    private String[] twoPair() {
        /*Throw them into a hashSet, see if size is 3.*/
        String[] hand = allCardsNOSUIT.clone();
        Set<String> cardSet = new HashSet<String>();
        for (String card: hand) {
            cardSet.add(card);
        }
        if (cardSet.size() != 3) {
            return noHand;
        }
        handValue = 3;
        return hand;
    }

    private String[] pair() {
        /*Throw them into a hashSet, see if there are duplicates.*/
        String[] hand = allCardsNOSUIT.clone();
        Set<String> cardSet = new HashSet<String>();
        for (String card: hand) {
            cardSet.add(card);
        }
        if (cardSet.size() == hand.length) {
            return noHand;
        }
        handValue = 2;
        return hand;
    }



    /*This method will sort the values by rank.
     * Can work for Straight Flushes, Straights, Flushes, High Cards */
    public void sort(){
        Comparator<String> comparator = new ValueComparator(handDict);
        //TreeMap is a map sorted by its keys.
        //The comparator is used to sort the TreeMap by keys.
        TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
        result.putAll(handDict);
        allCardsSuited = result.navigableKeySet().toArray();
    }



    public static void main(String[] args) {
        /*(String[] inHandCards, String[] inBoard)*/
        String[] inBoard = new String[]{"file:JPEG/9H.jpg","file:JPEG/JD.jpg","file:JPEG/6H.jpg","file:JPEG/6C.jpg","file:JPEG/5H.jpg" };
        handEvaluator hand = new handEvaluator(inBoard);
        System.out.println(hand.findBestHand());
    }


}
