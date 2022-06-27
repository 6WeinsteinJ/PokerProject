package src;

//An enum of all the suits with a shortcut letter for each
public enum Suit {
    CLUBS("c"),
    DIAMONDS("d"),
    HEARTS("h"),
    SPADES("s");

    private String nickname;

    //Defines the nickname for each suit
    private Suit(String nickname) {
        this.nickname = nickname;
    }

    public String toString() {
        return nickname;
    }
}
