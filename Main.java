package flashcards;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        CardUtils cardUtils = new CardUtils();
        Operations operations = new Operations();
        Scanner scan = new Scanner(System.in);
        String exit = "";

        for(int i = 0; i<args.length; i++) {
            if (args[i].equals("-import")) {
                operations.importCard(args[i+1]);
            }
            if (args[i].equals("-export")) {
                exit = args[i+1];
            }
        }

        all:
        while (true) {
            operations.printLog("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String durum = scan.nextLine();
            cardUtils.addLog(durum);
            switch (durum) {
                case "add":
                    operations.addCard();
                    break;
                case "remove":
                    operations.remove();
                    break;
                case "import":
                    operations.importCard("");
                    break;
                case "export":
                    operations.exportCard("");
                    break;
                case "ask":
                    operations.askQ();
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    if (!exit.equals("")) {
                        operations.exportCard(exit);
                    }
                    break all;
                case "hardest card":
                    operations.hardestCard();
                    break;
                case "reset stats":
                    operations.resetCards();
                    break;
                case "log":
                    operations.logSave();
                    break;
            }
        }
    }
}
