public class Card {
    public final String suit;
    public final Integer number;
    public String faceValue;
    public String fullcard;
    public Card(String suit, Integer num){

        this.suit = suit;
        this.number = num + 1;

        if (num == 10){
            faceValue = "Jack";
        }
        if (num == 11){
            faceValue = "Queen";
        }
        if (num == 12){
            faceValue = "King";
        }

        if (num < 10){
            if (num == 0){
                faceValue = "Ace";
            }
            else{
                this.faceValue = number.toString();
            }
        }
        fullcard = faceValue + " of " + this.suit;


    }
    public boolean equivalennce(Card otherCard){
        if (otherCard.suit == this.suit && otherCard.number == this.number){
            return true;
        }
        return false;
    }



}
