import java.util.*;
import java.io.*;
/**
 * Created by Giampiero on 9/19/2016.
 */
public class CALC1 {

    //private static ArrayList<HashMap<String, Integer>> lstVars = new ArrayList<>();
    private static Map<String, Integer> lstVars = new HashMap<>();

    public static void main(String[] args) throws IOException {
        int i = 30;


        String command = "";
        int value = 0;

            System.out.println("READY FOR INPUT ");
           while(true){
               Scanner scanner = new Scanner(System.in);
               command = scanner.nextLine();
               ProcessCommand(command);
               System.out.println();
           }





    }
    private static void ProcessCommand (String command){

        String firstCommand = "";
        String restOfCommand = "";
        if (command.contains("stop")){
            System.exit(1);
        }
        Scanner strParser = new Scanner(command).useDelimiter(" ");

        firstCommand = strParser.next().trim();
        if(firstCommand.length() > 0){
            restOfCommand = strParser.nextLine().trim();
            switch(firstCommand){
                case "load": Load(restOfCommand);
                    break;
                case "mem": Mem(restOfCommand);
                    break;
                case "print": Print(restOfCommand);
                    break;
                default: Calculate(firstCommand, restOfCommand);
            }
        }
        strParser.close();
    }

//    private static int ProcessCommand() {
//        String command = "";
//        String firstCommand = "";
//        String restOfCommand = "";
//        if (command.contains("stop")){
//            return -1;
//        }
//        Scanner strParser = new Scanner(command).useDelimiter(" ");
//
//        firstCommand = strParser.next().trim();
//        if(firstCommand.length() > 0){
//            restOfCommand = strParser.nextLine().trim();
//            switch(firstCommand){
//                case "load": Load(restOfCommand);
//                    break;
//                case "mem": Mem(restOfCommand);
//                    break;
//                case "print": Print(restOfCommand);
//                    break;
//                default: Calculate(firstCommand, restOfCommand);
//            }
//        }
//        strParser.close();
//
//        return 1;
//    }

    private static void Calculate(String firstCommand, String restOfCommand) {

    }

    private static void Print(String restOfCommand) {
        if(lstVars.containsKey(restOfCommand)){
            System.out.println("VALUE OF " + restOfCommand + " IS " + lstVars.get(restOfCommand));
        }
    }

    private static void Mem(String restOfCommand) {
        lstVars.put(restOfCommand, 0);
    }

    private static void Load(String command) {
        Scanner in = new Scanner(System.in);
        String key = command;
        Integer value = 0;
        System.out.println("ENTER VALUE FOR " + command.toUpperCase());
        value = in.nextInt();
        //tempVar.put(key, value);
        lstVars.put(key, value);
        //System.out.println(lstVars.get(key));
        in.nextLine();
        in.close();
    }

    private static void clearThisFuckingScreen(){
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }
}
