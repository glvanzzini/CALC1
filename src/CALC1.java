import java.util.*;

/**
 * Created by Giampiero L. Vanzzini on 9/19/2016.
 * Program: CALC1
 * Class: CSC 442
 * Dr. Cook
 * Purpose: To run a simple command line calculator that handles addition,
 * subtraction, multiplication, powers, square roots, dynamic variables, and comments.
 * Note: There is a LOT of string manipulation going on in this program and it is not always
 * apparent what it is doing with them. But it works well.
 */
public class CALC1 {

    //private static ArrayList<HashMap<String, Integer>> lstVars = new ArrayList<>();
    private static Map<String, Double> lstVars = new HashMap<>();
    private static Scanner in;
    public static void main(String[] args){
        System.out.println("You must put a space between all \"sqrt\" and values or variables.");
        System.out.println("$>READY FOR INPUT");

        String command = "";
        while (true){ // while loop runs until you type "stop"
            System.out.print("$>");
            in = new Scanner(System.in);    // get next command
            command = in.nextLine();
            if (command.contains("stop")){
                break;
            }
            ProcessCommand(command); // this function call starts the process of
            in.reset();             // deciphering the command.
        }
        in.close();
        System.exit(1);

    }
/*
This method will take in the command string, and decide what functions to pass it to based on the user input
*/
    private static void ProcessCommand(String command) {
        if(command.isEmpty()){
            //System.out.println();
            return;
        }
        String cmd = "";
        if(command.contains("//")) { // this handles the comments
            cmd = command.substring(0, command.indexOf("//")).trim();
        } else {
            cmd = command;
        }
        String firstCommand = ""; //holds the variable this expression is being assigned to.
        String restOfCommand = ""; // holds the rest of the command line
        in = new Scanner(cmd).useDelimiter(" ");
        firstCommand = in.next().trim();
        if(firstCommand.length() > 0){
            restOfCommand = in.nextLine().trim();
            in.reset();
            switch(firstCommand){
                case "load": Load(restOfCommand); //performs the load function
                    break;
                case "mem": Mem(restOfCommand); //stores a variable of value 0
                    break;
                case "print": Print(restOfCommand); // prints a variable value, default is 0.0
                    break;
                default: ParseMathString(firstCommand, restOfCommand); //evluates an expression
            }
        }

    }
/*
This method prepares a math expression ot be evalueate. It replaces alot of different types of characters
to make the string easy to process at the end. Some of it is probably redunant and unnecessary because
when I started on it I was taking a different approach, and changed my approach halfway through.
I'll probably redo and optimize a lot of this on my next pass through for phase 2 of CALC.
 */
    private static void ParseMathString(String firstCommand, String restOfCommand) {
        in.reset();

        String edits = restOfCommand.replaceAll("sqrt ", "&").trim(); //get rid of the "sqrt" and put &
        String newStr = edits.replaceAll("=", "").trim();            // dont need the "="
        String newStr2 = newStr.replaceAll(" ", "").trim();     //remove whitespace

        String strParsed = newStr2; //probably dont need this.

        //in = new Scanner(restOfCommand).useDelimiter("-|+|*|/|^|sqrt");
        String[] tempNums = strParsed.split("\\W"); //give me an array of only the values in the string

        Double[] numbers = new Double[tempNums.length];
        for(int i = 0; i < numbers.length; i++){ // this for loop replaces the variables contained in the string
            if (isNumeric(tempNums[i])){        // with thier numerical values.
                numbers[i] = Double.parseDouble(tempNums[i]);
            }else{
                if(lstVars.containsKey(tempNums[i])) {
                    numbers[i] = lstVars.get(tempNums[i]);
                    strParsed = strParsed.replaceAll(tempNums[i], (Double.toString(numbers[i])));

                }

            }
        }
        String moreStr = AddWhiteSpace(strParsed); // add white space again.
        String sqrtGone = moreStr.replaceAll(" 1 ", "1.0"); //standarize the 1's. because they can suck to process
        String parsed = sqrtGone.replaceAll("& 1.0 ", "1.0"); //get rid of these because they are hard to process
        String finalParse = RemoveSqrt(parsed); //this call will evaluate all square roots in the function before
        if(lstVars.containsKey(firstCommand)){  // the rest of the string is processed. Makes stuff easier.
            lstVars.put(firstCommand, Calculate(finalParse)); // finally update the variable by calculating the
        }                                                    //hte expression

    }
/*
this method adds white space. not much going on.
 */
    private static String AddWhiteSpace(String restOfCommand) {
        if(restOfCommand.contains("*")){
            restOfCommand = restOfCommand.replaceAll("\\*", " * ");
        }
        if(restOfCommand.contains("-")){
            restOfCommand = restOfCommand.replaceAll("-", " - ");
        }
        if(restOfCommand.contains("+")){
            restOfCommand = restOfCommand.replaceAll("\\+", " + ");
        }
        if(restOfCommand.contains("/")){
            restOfCommand = restOfCommand.replaceAll("/", " / ");
        }
        if(restOfCommand.contains("^")){
            restOfCommand = restOfCommand.replaceAll("\\^", " ^ ");
        }
        if(restOfCommand.contains("&")){
            restOfCommand = restOfCommand.replaceAll("\\&", "& ");
        }
        return restOfCommand.trim();
    }

    /*
    gets rid of the square root in a quick and dirty type of way
     */
    private static String RemoveSqrt(String strMath){
        String current = "";
        String returnStr = "";
        in.reset();
        in = new Scanner(strMath).useDelimiter(" ");
        String token = "";
        ArrayList<String> arr = new ArrayList<>();
        while (in.hasNext()) {
            token = in.next();
            if(token.equals("&")){
                current = in.next();
                returnStr += Double.toString(Math.sqrt(Double.parseDouble(current))) + " "; //builds new string
            } else {                                                    //with the value that equaled the sqrt <value>
                returnStr += token + " ";
            }
        }
        in.reset();
        return returnStr;
    }
    /*
    does some basic switch cases to calculate the expression.
     */
    private static Double Calculate(String strMath){
        String token;
        Double total = 0.;
        Double current = 0.;
        String operator = "";
        in = new Scanner(strMath).useDelimiter(" ");
        total = Double.parseDouble(in.next()); // this should always be the first value.
        while(in.hasNext()){
            token = in.next();
            if(isNumeric(token)){ //depending on the operator, do some math.
                current = Double.parseDouble(token);
                switch(operator){
                    case "+": total += current;
                        break;
                    case "-": total = total - current;
                        break;
                    case "*": total = total * current;
                        break;
                    case "/": total = total / current;
                        break;
                    case "^": total = Math.pow(total, current);
                        break;
                }
            }else {
                operator = token;
            }

        }
        return total;
    }
    /*
    this function just returns whether a string is numeric or not.
     */
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

    /*
    if the variable exists, print it out.
     */
    private static void Print(String restOfCommand) {
        if(lstVars.containsKey(restOfCommand)){
            System.out.println("VALUE OF " + restOfCommand + " IS " + lstVars.get(restOfCommand));
        }
    }

    /*
    stick a new variable in the map. initialize to 0.0
     */
    private static void Mem(String restOfCommand) {
        lstVars.put(restOfCommand, 0.0);
    }

    /*
    put a new variable in the map. ask user for the value.
     */
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