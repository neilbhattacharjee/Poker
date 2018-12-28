public class Solver {
    /*This class solely determines the winner once all the cards are dealt*/
    String[] inputs;
    String[] playerCards = new String[2];
    String[] compCards = new String[2];
    String[] board = new String[5];

    public Solver(String[] inputs){
        this.inputs = inputs;
        getPlayer(inputs);
        getComputer(inputs);
        getBoard(inputs);
    }

    private void getPlayer(String[] inputs) {
        playerCards[0] = inputs[0];
        playerCards[1] = inputs[1];

    }

    private void getComputer(String[] inputs) {
        compCards[0] = inputs[2];
        compCards[1] = inputs[3];

    }

    private void getBoard(String[] inputs) {
        board[0] = inputs[4];
        board[1] = inputs[5];
        board[2] = inputs[6];
        board[3] = inputs[7];
        board[4] = inputs[8];
    }

    public boolean winner() {
        /*this is the first method that actually requires brain work. Everything up to this point has been shitty framework*/
        /*Will create an instance of hand evaluator, which will take 7 cards and output the best card */
        handEvaluator handEvaluator = new handEvaluator(playerCards, board);
        handEvaluator.royalFlush();
        return false;
    }


}
