package flashcards;

public class Card {
    String cardName;
    String cardDef;

    public Card(String cardName, String cardDef) {
        this.cardName = cardName;
        this.cardDef = cardDef;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardDef() {
        return cardDef;
    }
}
