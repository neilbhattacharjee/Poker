import java.util.ArrayList;

public class scoreCalculator {
    public ArrayList<personalHand> allPersonalHands  = new ArrayList<>();
    public ArrayList<Integer> correspondingScores = new ArrayList<>();
    public boardCards communalCards = new boardCards();
    public scoreCalculator(ArrayList<personalHand> personalHands, boardCards someCards){
        this.allPersonalHands = personalHands;
        communalCards.boardCards = someCards.boardCards;
        for (personalHand hand: allPersonalHands){
            findScore(hand, communalCards);
        }
    }
    /*we now have an array list of hands, and an array list of the cards on the board */
    /*We will now try to figure out how to assign scores to each hand, and calculate the score*/


    public Integer findScore(personalHand hand, boardCards board){
        


    }


}
