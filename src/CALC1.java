import java.util.*;

/**
 * Created by Giampiero on 9/19/2016.
 */
public class CALC1 {

    //private static ArrayList<HashMap<String, Integer>> lstVars = new ArrayList<>();
    private static Map<String, Double> lstVars = new HashMap<>();
    private static Scanner in;
    public static void main(String[] args){
        System.out.println("$>READY FOR INPUT");

        String command = "";
        while (true){
            System.out.print("$>");
            in = new Scanner(System.in);
            command = in.nextLine();
            if (command.contains("stop")){
                break;
            }
            ProcessCommand(command);
            in.reset();
        }
        in.close();
        System.exit(1);

    }

    private static void ProcessCommand(String command) {
        if(command.isEmpty()){
            //System.out.println();
            return;
        }
        String firstCommand = "";
        String restOfCommand = "";
        in = new Scanner(command).useDelimiter(" ");
        firstCommand = in.next().trim();
        if(firstCommand.length() > 0){
            restOfCommand = in.nextLine().trim();
            in.reset();
            switch(firstCommand){
                case "load": Load(restOfCommand);
                    break;
                case "mem": Mem(restOfCommand);
                    break;
                case "print": Print(restOfCommand);
                    break;
                default: ParseMathString(firstCommand, restOfCommand);
            }
        }

    }

    private static void ParseMathString(String firstCommand, String restOfCommand) {
        in.reset();
        restOfCommand = restOfCommand.replaceAll(" ", "").trim();
        restOfCommand = restOfCommand.replaceAll("=", "");
        restOfCommand = restOfCommand.replaceAll("sqrt", "&");
        //System.out.println("rest of command: " + restOfCommand);
        //in = new Scanner(restOfCommand).useDelimiter("-|+|*|/|^|sqrt");
        String[] tempNums = restOfCommand.split("\\W|sqrt");
        ArrayList<String> operators = new ArrayList<>();
        in = new Scanner(restOfCommand).useDelimiter("");
        String token = "";
        while(in.hasNext()){
            token = in.next();
            if(token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") ||
                    token.equals("^") || token.equals("&")){
                operators.add(token);
            }

        }
        Double[] numbers = new Double[tempNums.length];
        for(int i = 0; i < numbers.length; i++){
            if (isNumeric(tempNums[i])){
                numbers[i] = Double.parseDouble(tempNums[i]);
            }else{
                numbers[i] = lstVars.get(tempNums[i]);
            }
            //System.out.print(numbers[i] + " ");
        }
        //System.out.println(restOfCommand + " ");
        //System.out.println();
//        for(int i = 0; i < operators.size(); i++){
//            System.out.print(operators.get(i) + " ");
//        }

//
        System.out.println("The total is " + Calculate(numbers, operators));
    }

    private static Double Calculate(Double[] pNumbers, ArrayList<String> pOperators){
        Double total = 0.;
        Double head = pNumbers[0];
        Double tail = pNumbers[pNumbers.length-1];
        Double current = 0.;
        Double prev = 0.0;
        Double next = 0.0;
        String operator = "";
        int opIndex = 0;
        int i = 0;
        boolean sqrt = false;

        while(i < pNumbers.length-1) {
            current = pNumbers[i];
            if (i > 0) {
                prev = pNumbers[i - 1];
            }
            if (i < pNumbers.length - 1) {
                next = pNumbers[i + 1];
            }
            if (!pOperators.isEmpty()) {
                if (pOperators.get(0).equals("&")){
                    sqrt = true;
                }
                operator = pOperators.get(0);
                pOperators.remove(0);
            }



            if (sqrt) {
                switch (operator) {
                    case "+":
                        total += (current + Math.sqrt(next));
                        i++;
                        break;
                    case "-":
                        total += (current - Math.sqrt(next));
                        i++;
                        break;
                    case "/":
                        total += (current / Math.sqrt(next));
                        i++;
                        break;
                    case "*":
                        total += (current * Math.sqrt(next));
                        i++;
                        break;
                    case "^":
                        total += (Math.pow(current, Math.sqrt(next)));
                        i++;
                        break;
                }
            } else {
                switch (operator) {
                    case "+":
                        total = (current + next);
                        //i++;
                        break;
                    case "-":
                        total = (current - next);
                        //i++;
                        break;
                    case "/":
                        total = (current / next);
                        //i++;
                        break;
                    case "*":
                        total = (current * next);
                        //i++;
                        break;
                    case "^":
                        total = (Math.pow(current, next));
                        //i++;
                        break;
                }

            }
            sqrt = false;
            i++;
        }

        System.out.println("$>");
        return total;
    }

    private static boolean isNumeric(String str){
        try
        {
            Double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    private static void Print(String restOfCommand) {
        if(lstVars.containsKey(restOfCommand)){
            System.out.println("VALUE OF " + restOfCommand + " IS " + lstVars.get(restOfCommand));
        }
    }

    private static void Mem(String restOfCommand) {
        lstVars.put(restOfCommand, 0.0);
    }

    private static void Load(String command) {
        in = new Scanner(System.in);
        String key = command;
        Double value = 0.0;
        System.out.println("$>ENTER VALUE FOR " + command.toUpperCase());
        System.out.print("$>");
        value = in.nextDouble();
        //tempVar.put(key, value);
        lstVars.put(key, value);
        //System.out.println(lstVars.get(key));
        in.nextLine();
        in.reset();
    }
}