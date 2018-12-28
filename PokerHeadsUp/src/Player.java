public class Player {
    public Integer stack = 1000;
    public Integer bet = 0;
    String card1;
    String card2;

    public void bet(Integer amount){
        if (amount > stack){
            bet = stack;
            stack = 0;
        }
        else{
            bet += amount;
            stack -= amount;
        }
    }

    public Integer correctBet(Integer amount, Integer opponentBet){
        if (amount > stack) {
            return stack;
        }
        return amount + (opponentBet - bet);
    }

    public Integer correctCall(Integer amount){
        if (amount > stack) {
            return stack;
        }
        return amount;
    }

    public void call(Integer amount){
        if (amount > stack) {
            bet = stack;
            stack = 0;
        }
        else {
            bet += amount;
            stack -= amount;
        }
    }

    public Integer checkEqualsZero(Integer difference){
        if (difference == 0) {
            return 1;
        }
        return 0;
    }
}
