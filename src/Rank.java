package src;

public enum Rank {
    HIGH_CARD("High Card"),
    ONE_PAIR("One pair"),
    TWO_PAIR("Two pair"),
    TRIPS("Trips"),
    STRAIGHT("Straight"),
    FLUSH("Flush"),
    FULL_HOUSE("Full House"),
    QUADS("Quads"),
    STRAIGHT_FLUSH("Straight Flush");

    private String name;

    private Rank(String name){
        this.name = name;
    }
}
