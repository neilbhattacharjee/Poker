import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
public class Deck {
    public ArrayList<String> deckOfCards = new ArrayList<String>();;
    String tempSuitString;
    String number;
    Random rand = new Random();
    boolean cardsDealt = false;
    boolean flopDealt = false;
    boolean turnDealt = false;
    boolean riverDealt = false;
    Integer actionOn = 0;
    Integer actionChange = 0;
    boolean playersTurn = true;
    Integer gameState = 0;
    String[] variousAction = {"flop","turn","river"};
    public Integer potMoney = 0;
    String flopCardOne;
    String flopCardTwo;
    String flopCardThree;
    String turnCard;
    String riverCard;





    public Deck(){
        populateDeck();

    }
    private void populateDeck(){
        for (int i = 0; i < 4; i++){
            for (int j = 1; j < 14; j++){
                if (i == 0){
                    tempSuitString = "C";
                }
                if (i == 1){
                    tempSuitString = "D";
                }
                if (i == 2){
                    tempSuitString = "H";
                }
                if (i == 3){
                    tempSuitString = "S";
                }
                number = Integer.toString(j);
                if (j == 1){
                    number = "A";
                }
                if (j == 11){
                    number = "J";
                }
                if (j == 12){
                    number = "Q";
                }
                if (j == 13){
                    number = "K";
                }
                deckOfCards.add(number + tempSuitString);
            }
        }
    }


    public String withdrawCard(){
        int index = rand.nextInt(deckOfCards.size());
        String returnCard = deckOfCards.get(index);
        deckOfCards.remove(index);
        return "file:JPEG/" + returnCard + ".jpg";
    }

    public String[] preFlop(){
        String[] returnList = new String[4];
        returnList[0] = withdrawCard();
        returnList[1] = withdrawCard();
        returnList[2] = withdrawCard();
        returnList[3] = withdrawCard();
        return returnList;
    }

    public String[] flop(){
        String[] returnList = new String[3];
        returnList[0] = withdrawCard();
        returnList[1] = withdrawCard();
        returnList[2] = withdrawCard();
        gameState += 1;
        return returnList;
    }

    public String[] turn(){
        String[] returnList = new String[1];
        returnList[0] = withdrawCard();
        gameState += 1;
        return returnList;
    }

    public String[] river(){
        String[] returnList = new String[1];
        returnList[0] = withdrawCard();
        return returnList;
    }






}
