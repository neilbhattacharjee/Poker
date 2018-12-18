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

    public Integer check(Integer difference){
        if (difference == 0) {
            return 1;
        }
        return 0;
    }
}
