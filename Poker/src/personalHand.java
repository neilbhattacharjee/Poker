public class personalHand {
    /*This class will serve to hold the cards of whoever is playing, as well as what their best hand is*/

    Card firstcard;
    Card secondcard;
    public personalHand(Card cardOne, Card cardTwo){
        firstcard = cardOne;
        secondcard = cardTwo;
    }
    public void printHand(){
        System.out.println(firstcard.fullcard + " and " + secondcard.fullcard);
    }

}
