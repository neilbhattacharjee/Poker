import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class currentHand {

    public Integer numberofPlayers;
    public ArrayList<personalHand> allPersonalHands = new ArrayList<>();
    public Deck currentDeck;

    public currentHand(Integer players){
        this.numberofPlayers = players;
        populateHands();
    }
    public void populateHands(){
        currentDeck = new Deck();
        for (int i = 0; i < numberofPlayers; i ++){
            int randomNum = ThreadLocalRandom.current().nextInt(0, currentDeck.size());
            Card firstCard = currentDeck.getCard(randomNum);
            int randomNum2 = ThreadLocalRandom.current().nextInt(0, currentDeck.size());
            Card secondCard = currentDeck.getCard(randomNum2);
            personalHand addHand = new personalHand(firstCard,secondCard);
            allPersonalHands.add(addHand);
        }
    }
    public void calculateScores(){
        scoreCalculator findScores = new scoreCalculator(allPersonalHands);
    }

    public static void main(String args[]){
        currentHand tester = new currentHand(4);
        for (int i = 0; i < tester.allPersonalHands.size(); i++){
            tester.allPersonalHands.get(i).printHand();
        }


    }


}
