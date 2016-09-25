import java.util.*;

/**
 * Created by Giampiero on 9/19/2016.
 */
public class CALC1 {

    //private static ArrayList<HashMap<String, Integer>> lstVars = new ArrayList<>();
    private static Map<String, Integer> lstVars = new HashMap<>();

    public static void main(String[] args){
        System.out.println("READY FOR INPUT");
        Scanner in = new Scanner(System.in);
        String command = "";
      while (true){
          System.out.println();
          //command = in.
          if (command.contains("stop")){
              break;
          }
          ProcessCommand(command);
          command = "";
          in.reset();
      }
      in.close();

    }

    private static void ProcessCommand(String command) {
        String firstCommand = "";
        String restOfCommand = "";
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
        in.reset();
        in.close();
    }
}
