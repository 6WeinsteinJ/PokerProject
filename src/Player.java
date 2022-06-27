package src;

public class Player {
    private int chipCount;
    private String name;
    private PlayerHand currentHand = new PlayerHand(this);
    private int seatNum;
    private boolean isActive;
    private int currentBet = 0;

    public Player(int chipCount, String name, int seatNum){
        this.chipCount = chipCount;
        this.name = name;
        this.seatNum = seatNum;
        isActive = false;
    }



    public void setActive(boolean isActive){
        this.isActive = isActive;
    }
    public void setCurrentHand(PlayerHand currentHand){
        this.currentHand = currentHand;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setChipCount(int chipCount){
        this.chipCount = chipCount;
    }

    public int getChipCount(){
        return chipCount;
    }

    public String getName(){
        return name;
    }

    public PlayerHand getCurrentHand(){
        return currentHand;
    }

    public int getSeatNum(){
        return seatNum;
    }

    public boolean getActive(){
        return isActive;
    }

    public int getCurrentBet(){
        return currentBet;
    }

    public void setCurrentBet(int currentBet){
        this.currentBet = currentBet;
    }
}
