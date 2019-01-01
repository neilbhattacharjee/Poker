import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.collections.ListChangeListener.Change;
import javafx.util.converter.IntegerStringConverter;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextArea;
import static java.lang.Integer.parseInt;


public class Main extends Application{

    Button dealCards;
    Button playerCheck;
    Button playerFold;
    Button computerCheck;
    Button computerFold;
    Button computerCall;
    Button playerCall;
    Deck officialDeck = new Deck();
    public Integer betCount = 0;
    /*initialize human and computer players*/
    Human human = new Human();
    Computer computer = new Computer();
    boolean cardsDealt = false;
    boolean humanDealer = true;

    /*framework of money/betting GUI*/
    Label AIStack = new Label(" Stack:" + Integer.toString(computer.stack));
    Label playerStack = new Label(" Stack:" + Integer.toString(human.stack));
    Label betAmountPlayer = new Label(" Bet:" + Integer.toString(human.bet));
    Label betAmountComp = new Label(" Bet:" + Integer.toString(computer.bet));
    Label raiseFieldComputer = new Label("R/RR:");
    Label raiseFieldPlayer = new Label("R/RR:");
    TextField bettingWindowAI = new TextField();
    TextField bettingWindow = new TextField();
    Label pot = new Label(" Pot:" + Integer.toString(officialDeck.potMoney));



    @Override
    public void start(Stage primaryStage) throws Exception{
        /* name of box*/
        primaryStage.setTitle("Poker!");
        bettingWindow.setPrefWidth(40);
        bettingWindowAI.setPrefWidth(40);
        /*code for call, fold buttons*/
        playerCheck = new Button("Check");
        playerFold = new Button("Fold");
        playerCall = new Button("Call");
        VBox bottomButt = new VBox(5, playerCall, playerCheck, playerFold);
        bottomButt.setAlignment(Pos.CENTER);
        computerCheck = new Button("Check");
        computerFold = new Button("Fold");
        computerCall = new Button("Call");
        VBox topButt = new VBox(5, computerCall, computerCheck, computerFold);
        topButt.setAlignment(Pos.CENTER);
        dealCards = new Button("Deal Cards!");

        /*Computer's cards*/
        ImageView compCard1 = new ImageView();
        compCard1.setImage(computerCard1);
        compCard1.setFitWidth(75);
        compCard1.setFitHeight(100);

        ImageView compCard2 = new ImageView();
        compCard2.setImage(computerCard2);
        compCard2.setFitWidth(75);
        compCard2.setFitHeight(100);

        /*Community Cards*/

        ImageView Flop1 = new ImageView();
        Flop1.setImage(community1);
        Flop1.setFitWidth(75);
        Flop1.setFitHeight(100);

        ImageView Flop2 = new ImageView();
        Flop2.setImage(community2);
        Flop2.setFitWidth(75);
        Flop2.setFitHeight(100);

        ImageView Flop3 = new ImageView();
        Flop3.setImage(community3);
        Flop3.setFitWidth(75);
        Flop3.setFitHeight(100);

        ImageView Turn = new ImageView();
        Turn.setImage(community4);
        Turn.setFitWidth(75);
        Turn.setFitHeight(100);

        ImageView River = new ImageView();
        River.setImage(community5);
        River.setFitWidth(75);
        River.setFitHeight(100);

        /*Personal Cards*/

        ImageView Person1 = new ImageView();
        Person1.setImage(playerCard1);
        Person1.setFitWidth(75);
        Person1.setFitHeight(100);

        ImageView Person2 = new ImageView();
        Person2.setImage(playerCard2);
        Person2.setFitWidth(75);
        Person2.setFitHeight(100);

        /*the events we must deal with are: Deal Cards, check, fold, raise, call FOR EACH PLAYER*/


        /*this class deals with clicking "Deal Cards!"*/

        dealCards.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!cardsDealt){
                    cardsDealt = true;
                    initialCards();
                    compCard1.setImage(computerCard1);
                    compCard2.setImage(computerCard2);
                    Person1.setImage(playerCard1);
                    Person2.setImage(playerCard2);
                    /*AI & player now bet against each other */
                }
            }
        });

        /*this class deals with clicking "Check" for human Will call method within Human*/

        playerCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (human.checkEqualsZero(human.bet - computer.bet) == 1 && officialDeck.playersTurn && cardsDealt){
                    officialDeck.playersTurn = false;
                    officialDeck.actionChange += 1;
                    bettingOver();
                    /*disgusting hack for now, basically update all 5 cards whenever possible*/
                    Flop1.setImage(community1);
                    Flop2.setImage(community2);
                    Flop3.setImage(community3);
                    Turn.setImage(community4);
                    River.setImage(community5);
                    compCard1.setImage(computerCard1);
                    compCard2.setImage(computerCard2);
                    Person1.setImage(playerCard1);
                    Person2.setImage(playerCard2);
                }
            }
        });

        computerCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (computer.checkEqualsZero(human.bet - computer.bet) == 1 && !officialDeck.playersTurn && cardsDealt){
                    officialDeck.actionChange += 1;
                    officialDeck.playersTurn = true;
                    bettingOver();
                    /*disgusting hack for now, basically update all 5 cards whenever possible*/
                    Flop1.setImage(community1);
                    Flop2.setImage(community2);
                    Flop3.setImage(community3);
                    Turn.setImage(community4);
                    River.setImage(community5);
                    compCard1.setImage(computerCard1);
                    compCard2.setImage(computerCard2);
                    Person1.setImage(playerCard1);
                    Person2.setImage(playerCard2);
                }
            }
        });


        bettingWindow.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent event) {
                if (officialDeck.playersTurn && cardsDealt){
                    bettingWindow.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
                    Integer betAmount = human.correctBet(parseInt(bettingWindow.getText()), computer.bet);
                    human.bet(betAmount);
                    officialDeck.potMoney += betAmount;
                    officialDeck.playersTurn = false;
                    officialDeck.actionChange += 1;
                    /*update the values, and see if the betting is over*/
                    updatesValues();
                    bettingOver();
                    /*disgusting hack for now, basically update all 5 cards whenever possible*/
                    Flop1.setImage(community1);
                    Flop2.setImage(community2);
                    Flop3.setImage(community3);
                    Turn.setImage(community4);
                    River.setImage(community5);
                    compCard1.setImage(computerCard1);
                    compCard2.setImage(computerCard2);
                    Person1.setImage(playerCard1);
                    Person2.setImage(playerCard2);
                    bettingWindow.clear();
                }
            }
        });

        bettingWindowAI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!officialDeck.playersTurn &&  cardsDealt){
                    bettingWindowAI.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
                    Integer betAmount = computer.correctBet(parseInt(bettingWindowAI.getText()), human.bet);
                    computer.bet(betAmount);
                    officialDeck.potMoney += betAmount;
                    officialDeck.playersTurn = true;
                    officialDeck.actionChange += 1;
                    /*update the values, and see if the betting is over*/
                    updatesValues();
                    bettingOver();
                    /*disgusting hack for now, basically update all 5 cards whenever possible*/
                    Flop1.setImage(community1);
                    Flop2.setImage(community2);
                    Flop3.setImage(community3);
                    Turn.setImage(community4);
                    River.setImage(community5);
                    compCard1.setImage(computerCard1);
                    compCard2.setImage(computerCard2);
                    Person1.setImage(playerCard1);
                    Person2.setImage(playerCard2);
                    bettingWindowAI.clear();
                }
            }
        });

        playerCall.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                /*Check that it's the players turn, that the cards have been dealt, and the bet amounts aren't equal*/
                if (officialDeck.playersTurn && cardsDealt && computer.checkEqualsZero(human.bet - computer.bet) == 0 && cardsDealt) {
                    Integer betAmount = human.correctCall(computer.bet - human.bet);
                    human.call(betAmount);
                    officialDeck.potMoney += betAmount;
                    officialDeck.playersTurn = false;
                    officialDeck.actionChange += 1;
                    /*whenever a call occurs, the betting is automatically over. bettingOver() should catch this.*/
                    updatesValues();
                    bettingOver();
                    /*disgusting hack for now, basically update all 5 cards whenever possible*/
                    Flop1.setImage(community1);
                    Flop2.setImage(community2);
                    Flop3.setImage(community3);
                    Turn.setImage(community4);
                    River.setImage(community5);
                    compCard1.setImage(computerCard1);
                    compCard2.setImage(computerCard2);
                    Person1.setImage(playerCard1);
                    Person2.setImage(playerCard2);
                }
            }
        });

        computerCall.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                /*Check that it's the players turn, that the cards have been dealt, and the bet amounts aren't equal*/
                if (!officialDeck.playersTurn && cardsDealt && computer.checkEqualsZero(human.bet - computer.bet) == 0) {
                    Integer betAmount = computer.correctCall(human.bet - computer.bet);
                    computer.call(betAmount);
                    officialDeck.potMoney += betAmount;
                    officialDeck.playersTurn = true;
                    officialDeck.actionChange += 1;
                    /*whenever a call occurs, the betting is automatically over. bettingOver() should catch this.*/
                    updatesValues();
                    bettingOver();
                    /*disgusting hack for now, basically update all 5 cards whenever possible*/
                    Flop1.setImage(community1);
                    Flop2.setImage(community2);
                    Flop3.setImage(community3);
                    Turn.setImage(community4);
                    River.setImage(community5);
                    compCard1.setImage(computerCard1);
                    compCard2.setImage(computerCard2);
                    Person1.setImage(playerCard1);
                    Person2.setImage(playerCard2);
                }
            }
        });

        computerFold.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if (!officialDeck.playersTurn && cardsDealt) {
                    reset();
                    updatesValues();
                    Flop1.setImage(community1);
                    Flop2.setImage(community2);
                    Flop3.setImage(community3);
                    Turn.setImage(community4);
                    River.setImage(community5);
                    compCard1.setImage(computerCard1);
                    compCard2.setImage(computerCard2);
                    Person1.setImage(playerCard1);
                    Person2.setImage(playerCard2);
                }
            }
            });
        playerFold.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if (officialDeck.playersTurn && cardsDealt) {
                    reset();
                    updatesValues();
                    Flop1.setImage(community1);
                    Flop2.setImage(community2);
                    Flop3.setImage(community3);
                    Turn.setImage(community4);
                    River.setImage(community5);
                    compCard1.setImage(computerCard1);
                    compCard2.setImage(computerCard2);
                    Person1.setImage(playerCard1);
                    Person2.setImage(playerCard2);
                }
            }
        });



        /*mumbo jumbo to create the GUI*/
        HBox topCards = new HBox(10, AIStack, raiseFieldComputer, bettingWindowAI, compCard1, compCard2, betAmountComp, topButt);
        HBox commCards = new HBox(10, pot, Flop1, Flop2, Flop3, Turn, River, dealCards);
        HBox botCards = new HBox(10,playerStack,raiseFieldPlayer, bettingWindow, Person1, Person2, betAmountPlayer, bottomButt);
        commCards.setAlignment(Pos.CENTER);
        topCards.setAlignment(Pos.CENTER);
        botCards.setAlignment(Pos.CENTER);
        VBox entireScope = new VBox(90, topCards, commCards, botCards);
        entireScope.setStyle("-fx-background-color: #076324;");
        Scene scene = new Scene(entireScope, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();



    }

    public void updatesValues(){
        betAmountPlayer.setText("Bet:" + Integer.toString(human.bet));
        AIStack.setText("Stack:" + Integer.toString(computer.stack));
        playerStack.setText("Stack:" + Integer.toString(human.stack));
        betAmountComp.setText("Bet:" + Integer.toString(computer.bet));
        pot.setText("Pot:" + Integer.toString(officialDeck.potMoney));
    }

    public void initialCards(){
        String[] fourCards = officialDeck.preFlop();
        String compCardOne = fourCards[0];
        String compCardTwo = fourCards[1];
        String humanCardOne = fourCards[2];
        String humanCardTwo = fourCards[3];
        computerCard1 = new Image(compCardOne);
        computerCard2 = new Image(compCardTwo);
        playerCard1 = new Image(humanCardOne);
        playerCard2 = new Image(humanCardTwo);
        human.card1 = humanCardOne;
        human.card2 =  humanCardTwo;
        computer.card1 = compCardOne;
        computer.card2 = compCardTwo;
    }

    public void flopCards(){
        String[] threeCards = officialDeck.flop();
        String flopOne = threeCards[0];
        String flopTwo = threeCards[1];
        String flopThree = threeCards[2];
        community1 = new Image(flopOne);
        community2 = new Image(flopTwo);
        community3 = new Image(flopThree);
        officialDeck.flopCardOne = flopOne;
        officialDeck.flopCardTwo = flopTwo;
        officialDeck.flopCardThree = flopThree;
    }

    public void turnCard(){
        String[] turnCard = officialDeck.turn();
        String theTurn = turnCard[0];
        community4 = new Image(theTurn);
        officialDeck.turnCard = theTurn;
    }

    public void riverCard(){
        String[] riverCard = officialDeck.river();
        String theRiver = riverCard[0];
        community5 = new Image(theRiver);
        officialDeck.riverCard = theRiver;

    }

    public void reset(){

        human.stack += officialDeck.potMoney;
        officialDeck.potMoney = 0;
        officialDeck.actionChange = 0;
        human.bet = 0;
        officialDeck.copyDeck();
        computer.bet = 0;
        officialDeck.gameState = 0;
        officialDeck.playersTurn = !humanDealer;
        humanDealer = !humanDealer;
        computerCard1 = new Image("file:JPEG/blue_back.jpg");
        computerCard2 = new Image("file:JPEG/blue_back.jpg");
        playerCard1 = new Image("file:JPEG/blue_back.jpg");
        playerCard2 = new Image("file:JPEG/blue_back.jpg");
        community1 = new Image("file:JPEG/blue_back.jpg");
        community2 = new Image("file:JPEG/blue_back.jpg");
        community3 = new Image("file:JPEG/blue_back.jpg");
        community4 = new Image("file:JPEG/blue_back.jpg");
        community5 = new Image("file:JPEG/blue_back.jpg");
        cardsDealt = false;
    }
    /*this method checks whether or not betting is over at any given state change*/
    public void bettingOver(){
        if (human.bet - computer.bet == 0 && officialDeck.actionChange > 1){
            String action = officialDeck.variousAction[officialDeck.gameState];
            if (action.equals("flop")){
                updatesValues();
                flopCards();
                officialDeck.actionChange = 0;
                /*must reset to whoever started the hand with the betting*/
                officialDeck.playersTurn = humanDealer;

            }
            if (action.equals("turn")){
                updatesValues();
                turnCard();
                officialDeck.actionChange = 0;
                officialDeck.playersTurn = humanDealer;
            }
            if (action.equals("river")){
                updatesValues();
                riverCard();
                officialDeck.actionChange = 0;
                officialDeck.playersTurn = humanDealer;
            }

            if (action.equals("postRiver")){
                /*if this if statement is called, the hand is over*/
                String[] inputs = new String[9];
                inputs[0] = human.card1;
                inputs[1] = human.card2;
                inputs[2] = computer.card1;
                inputs[3] = computer.card2;
                inputs[4] = officialDeck.flopCardOne;
                inputs[5] = officialDeck.flopCardTwo;
                inputs[6] = officialDeck.flopCardThree;
                inputs[7] = officialDeck.turnCard;
                inputs[8] = officialDeck.riverCard;
                Solver solver = new Solver(inputs);
                solver.winner();
                reset();
                updatesValues();
            }
        }
    }



    /*initial background of cards */

    Image computerCard1 = new Image("file:JPEG/blue_back.jpg");
    Image computerCard2 = new Image("file:JPEG/blue_back.jpg");
    Image playerCard1 = new Image("file:JPEG/blue_back.jpg");
    Image playerCard2 = new Image("file:JPEG/blue_back.jpg");
    Image community1 = new Image("file:JPEG/blue_back.jpg");
    Image community2 = new Image("file:JPEG/blue_back.jpg");
    Image community3 = new Image("file:JPEG/blue_back.jpg");
    Image community4 = new Image("file:JPEG/blue_back.jpg");
    Image community5 = new Image("file:JPEG/blue_back.jpg");

    public static void main(String[] args){
        launch(args);
    }
}
