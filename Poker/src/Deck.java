import java.util.ArrayList;
public class Deck {
    public ArrayList<Card> deckOfCards;
    String tempSuitString;

    public Deck(){
        this.deckOfCards = new ArrayList<Card>();
        populateDeck();

    }
    public void populateDeck(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 13; j++){
                if (i == 0){
                    tempSuitString = "Club";
                }
                if (i == 1){
                    tempSuitString = "Diamond";
                }
                if (i == 2){
                    tempSuitString = "Heart";
                }
                if (i == 3){
                    tempSuitString = "Spade";
                }
                Card addCard  = new Card(tempSuitString, j);
                deckOfCards.add(addCard);
            }
        }

    }
    public Card getCard(int index) {
         Card returnCard = deckOfCards.get(index);
         deckOfCards.remove(index);
         return returnCard;
    }
    public int size(){
        return deckOfCards.size();
    }
    public static void main(String args[]){
        Deck myDeck = new Deck();
        for (Card item : myDeck.deckOfCards) {
            System.out.println(item.faceValue + " " + item.suit);
        }
    }


}
