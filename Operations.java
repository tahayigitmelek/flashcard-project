package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Operations {
    Scanner scan = new Scanner(System.in);
    CardUtils cardUtils = new CardUtils();
    Map<String,Integer> stats;

    public void addCard(){
        printLog("The card:");
        String name = scan.nextLine();
        cardUtils.addLog(name);
        if (!cardUtils.checkTerm(name)) {
            printLog("The definition of the card:");
            String def = scan.nextLine();
            cardUtils.addLog(def);
            if (!cardUtils.checkDef(def)) {
                cardUtils.addCard(new Card(name,def));
                printLog(String.format("The pair (\"%s\":\"%s\") has been added.",name,def));
            }else {
                printLog(String.format("The definition \"%s\" already exists.",def));
            }
        }else {
            printLog(String.format("The card \"%s\" already exists.",name));
        }
    }

    public void remove(){
        printLog("Which card?");
        String cardName = scan.nextLine();
        cardUtils.addLog(cardName);
        if (cardUtils.removeCard(cardName)) {
            printLog("The card has been removed.");
        } else {
            printLog(String.format("Can't remove \"%s\": there is no such card.",cardName));
        }
    }

    public void importCard(String gelen_string) {
        String fileName;
        if (gelen_string.equals("")) {
            printLog("File name:");
            fileName = scan.nextLine();
            cardUtils.addLog(fileName);
        }else fileName = gelen_string;
        int count = 0;
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                count++;
                String[] strings = myReader.nextLine().split("/");
                cardUtils.checkAddCard(new Card(strings[0],strings[1]),strings[2]);
            }
            myReader.close();
        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException e) {
            printLog("File not found.");
        }
        if (count > 0){
            printLog(String.format("%d cardUtils have been loaded.",count));
        }
    }

    public void exportCard(String gelen_string) {
        String cardName;
        if (gelen_string.equals("")) {
            printLog("File name:");
            cardName = scan.nextLine();
            cardUtils.addLog(cardName);
        }else {
            cardName = gelen_string;
        }
        stats = cardUtils.getStats();

        StringBuilder s = new StringBuilder();
        int count = 0;
        for (Map.Entry<String,String> entry : cardUtils.getCardMap().entrySet()) {
            count++;
            int value = stats.get(entry.getKey());
            s.append(entry.getKey()).append("/").append(entry.getValue())
                    .append("/").append(value).append("\n");
        }

        try {
            FileWriter myWriter = new FileWriter(cardName);
            myWriter.write(s.toString());
            myWriter.close();
            printLog(String.format("%d cardUtils have been saved.",count));
        } catch (IOException  e) {
            printLog("An error occurred.");
        }
    }

    public void askQ() {
        printLog("How many times to ask?");
        int cardName = Integer.parseInt(scan.nextLine());
        cardUtils.addLog(String.valueOf(cardName));
        List<Card> c = cardUtils.getCardList();
        for (int i = 0; i < cardName; i++) {
            printLog(String.format("Print the definition of \"%s\":", c.get(i).getCardName()));
            String scanString = scan.nextLine();
            cardUtils.addLog(scanString);
            if (scanString.equals(c.get(i).getCardDef())) {
                printLog("Correct!");
            } else {
                String wrong = String.format("Wrong. The right answer is \"%s\"", c.get(i).getCardDef());
                Card card = cardUtils.checkWrong(scanString, c.get(i).getCardName());
                if (card != null) {
                    wrong += String.format(", but your definition is correct for \"%s\".", card.getCardName());
                } else {
                    wrong += ".";
                }
                printLog(wrong);
            }
        }
    }

    public void hardestCard(){
        stats = cardUtils.getStats();
        int max = 0;
        String s = "";
        for (Map.Entry<String,Integer> entry : stats.entrySet()) {
            if (max < entry.getValue()) {
                max = entry.getValue();
                s = entry.getKey();
            }
        }
        if (!stats.isEmpty() && max != 0) {
            List<String> maxList = getMaxIndices(stats);
            if (!maxList.isEmpty() && !stats.isEmpty() && maxList.size() > 1) {
                StringBuilder endString = new StringBuilder("The hardest cardUtils are");
                for (String string : maxList) {
                    endString.append(" \"").append(string).append("\"");
                }
                endString.append(String.format(". You have %d errors answering them.", max));
                printLog(endString.toString());
            }else {
                printLog(String.format("The hardest card is \"%s\". You have %d errors answering it.",s,max));
            }
        }else {
            printLog("There are no cardUtils with errors.");
        }
    }

    public List<String> getMaxIndices(Map<String, Integer> values) {
        Integer max = Collections.max(values.values());
        List<String> maxIndices = new ArrayList<>();
        for (Map.Entry<String,Integer> entry : stats.entrySet()) {
            if (entry.getValue().equals(max)) {
                maxIndices.add(entry.getKey());
            }
        }
        return maxIndices;
    }

    public void logSave() {
        printLog("File name:");
        String cardName = scan.nextLine();
        cardUtils.addLog(cardName);
        try {
            FileWriter myWriter = new FileWriter(cardName);
            printLog("The log has been saved.");
            myWriter.write(cardUtils.getLog());
            myWriter.close();
        } catch (IOException  e) {
            printLog("An error occurred.");
        }
    }

    public void resetCards() {
        printLog("Card statistics have been reset.");
        cardUtils.resetCard();
    }

    public void printLog(String s) {
        System.out.println(s);
        cardUtils.addLog(s+"\n");
    }
}
