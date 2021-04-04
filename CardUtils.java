package flashcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CardUtils {
    private final Map<String,String> cardMap;
    private final Map<String,Integer> stats;
    private String log = "";

    public CardUtils() {
        this.cardMap = new TreeMap<>();
        this.stats = new TreeMap<>();
    }

    public boolean removeCard(String s){
        for (Map.Entry<String,String> entry : cardMap.entrySet()) {
            if (entry.getKey().equals(s)){
                this.cardMap.remove(entry.getKey(),entry.getValue());
                this.stats.remove(entry.getKey());
                return true;
            }
        }
        return false;
    }

    public boolean checkTerm(String s) {
        for (Map.Entry<String,String> entry : cardMap.entrySet()) {
            if (entry.getKey().equals(s)){
                System.out.printf("The term \"%s\" already exists. Try again:\n",s);
                addLog(String.format("The term \"%s\" already exists. Try again:",s));
                return true;
            }
        }
        return false;
    }

    public boolean checkDef(String s) {
        for (Map.Entry<String,String> entry : cardMap.entrySet()) {
            if (entry.getValue().equals(s)){
                System.out.printf("The definition \"%s\" already exists. Try again:\n",s);
                addLog(String.format("The definition \"%s\" already exists. Try again:\n",s));
                return true;
            }
        }
        return false;
    }

    public Card checkWrong(String s, String key) {
        this.stats.put(key,this.stats.get(key)+1);
        for (Map.Entry<String,String> entry : cardMap.entrySet()) {
            if (entry.getValue().equals(s)){
                return new Card(entry.getKey(),entry.getValue());
            }
        }
        return null;
    }

    public void checkAddCard(Card card, String string) {
        this.cardMap.put(card.getCardName(),card.getCardDef());
        this.stats.put(card.getCardName(),Integer.parseInt(string));
    }

    public List<Card> getCardList() {
        List<Card> c = new ArrayList<>();
        for (Map.Entry<String,String> entry : cardMap.entrySet()) {
            c.add(new Card(entry.getKey(),entry.getValue()));
        }
        return c;
    }

    public void resetCard() {
        for (Map.Entry<String,String> cardMap : cardMap.entrySet()) {
            this.stats.put(cardMap.getKey(),0);
        }
    }

    public String getLog() {
        return log;
    }

    public void addLog(String s) {
        this.log += s+"\n";
    }

    public void addCard(Card c){
        this.cardMap.put(c.getCardName(),c.getCardDef());
        this.stats.put(c.getCardName(),0);
    }

    public Map<String, String> getCardMap() {
        return cardMap;
    }

    public Map<String, Integer> getStats() {
        return stats;
    }

}
