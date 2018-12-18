import java.util.ArrayList;

public class boardCards {
    public ArrayList<Card> boardCards  = new ArrayList<>();
        /* There are four states the board can be. None, Flop, Flop + Turn, Flop + Turn + River*/

        public boardCards(){
            /*I don't want to add any cards to my list of BoardCards if it's an empty board*/

        }

        public boardCards(Card cardOne, Card cardTwo, Card cardThree){
            /*Add three cards to boardCards list*/
            boardCards.add(cardOne);
            boardCards.add(cardTwo);
            boardCards.add(cardThree);

        }

        public boardCards(Card cardOne, Card cardTwo, Card cardThree, Card cardFour){
            /*Add four cards to boardCards list*/
            boardCards.add(cardOne);
            boardCards.add(cardTwo);
            boardCards.add(cardThree);
            boardCards.add(cardFour);
        }

        public boardCards(Card cardOne, Card cardTwo, Card cardThree, Card cardFour, Card cardFive){
            /*Add five cards to boardCards list*/
            boardCards.add(cardOne);
            boardCards.add(cardTwo);
            boardCards.add(cardThree);
            boardCards.add(cardFour);
            boardCards.add(cardFive);
        }




}
