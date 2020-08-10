public class Calculator {
    static int begin;
    static int end;
    static int lengthOfCalcArray = 0;
    static boolean brackets;
    static boolean finishedCalc = false;
    static String number = "";
    static String formula;
    static String[] array;
    static String[] calcarray;

    public static boolean validating() {
        int countBrackets = 0;
        boolean sign = false;
        boolean number = false;
        boolean inBrackets = false;
        try {
            for (int i = 0; i < formula.length(); i++) {
                switch (formula.charAt(i)) {
                    case '*', '%', '/', '+' -> {
                        if (sign) {
                            return false;
                        }
                        sign = true;
                        number = false;
                    }
                    case ' ' -> {
                    }
                    case '(' -> {
                        sign = false;
                        number = false;
                        countBrackets++;
                    }
                    case ')' -> {
                        sign = false;
                        number = false;
                        countBrackets--;
                    }
                    case '-' -> {
                        if (formula.substring(i+1, i+2).matches("[0-9]")) {
                            sign = false;
                        }
                        else if (!sign) {
                            sign = true;
                            number = false;
                        }
                        else {
                            return false;
                        }
                    }
                    default -> {
                        sign = false;
                        if (!formula.substring(i, i + 1).matches("[0-9]")) {
                            System.out.println(formula.charAt(i));
                            return false;
                        }
                        else if (number && !formula.substring(i-1, i).matches("[0-9]")) {
                            return false;
                        }
                        number = true;
                    }
                }
            }
            return countBrackets == 0;
        }
        catch(Exception e) {
            return false;
        }
    }

    public static void stringToArray() {
        for (int i = 0; i < formula.length(); i++){
            number += formula.charAt(i);
            // every other thing in the String
            if (formula.charAt(i) == '(') { array[i] = "("; fillArray(i);}
            else if (formula.charAt(i) == ')') { array[i] = ")"; fillArray(i);}
            else if (formula.charAt(i) == '*') { array[i] = "*"; fillArray(i);}
            else if (formula.charAt(i) == '%') { array[i] = "%"; fillArray(i);}
            else if (formula.charAt(i) == '/') { array[i] = "/"; fillArray(i);}
            else if (formula.charAt(i) == '+') { array[i] = "+"; fillArray(i);}
            else if (formula.charAt(i) == ' ') { array[i] = "X"; fillArray(i);}
            // special cass when a minus is in front of a another minus || --
            else if (formula.charAt(i) == '-' && formula.charAt(i+1) == '-'){
                array[i] = "-";
                number = "";
                lengthOfCalcArray = i;
            }
            // end of array
            else if (i == formula.length() - 1) { array[i] = formula.substring(lengthOfCalcArray+1, i+1);}
        }
    }

    // helps to convert String to Array
    public static void fillArray(int i) {
        if (i == 0) { lengthOfCalcArray++; }
        else if (lengthOfCalcArray == 1 || lengthOfCalcArray == 0) {
            array[i-1] = formula.substring(lengthOfCalcArray, i);
        }
        else if (lengthOfCalcArray + 1 < i) {
            array[i - 1] = formula.substring(lengthOfCalcArray + 1, i);
        }
        number = "";
        if (i > 0) { lengthOfCalcArray = i; }
    }

    // also writes the length of the array into "lengthOfCalcArray"
    public static void nullBubleSort() {
        boolean done = false;
        while (!done) {
            done = true;
            for(int i = 0; i < array.length - 1; i++){
                if (array[i] == null) {
                    array[i] = "X";
                }
                if (array[i+1] == null){
                    array[i+1] = "X";
                }
                if (array[i].equals("X") && !array[i+1].equals("X")) {
                    done = false;
                    array[i] = array[i+1];
                    array[i+1] = "X";
                    lengthOfCalcArray = i + 1;
                }
            }
        }
    }

    public static void bubbleSort(String[] ar) {
        boolean done = false;
        while (!done) {
            done = true;
            for(int i = 0; i < ar.length - 1; i++){
                if (ar[i].equals("X") && !ar[i+1].equals("X")) {
                    done = false;
                    ar[i] = ar[i+1];
                    ar[i+1] = "X";
                    lengthOfCalcArray = i + 1;
                }
            }
        }
    }

    // need to know the begin and end of the subset in order to set up the array correctly :(
    public static void deepestLayer() {
        begin = 0;
        end = 0;
        brackets = false;
        for (int i = 0; i < lengthOfCalcArray; i++) {
            if (array[i].equals("(")) {
                begin = i;
            } else if (array[i].equals(")")) {
                end = i;
                brackets = true;
                break;
            }
        }
    }

    public static void createSubArray() {
        if (brackets) {
            calcarray = new String[(end - begin) - 1]; // don't want the brackets in the new array
            lengthOfCalcArray = (end - begin) - 1;

            for (int i = begin + 1; i < end; i++) {
                calcarray[i - (begin + 1)] = array[i];
            }
        } // outer's layer = no layers
        else {
            // how long is the array
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals("X")) {
                    lengthOfCalcArray = i;
                    break;
                }
                calcarray[i] = array[i];
            }
        }
    }

    public static void calculation(String sign) {
        finishedCalc = false;
        while(!finishedCalc) {
            finishedCalc = true;
            for (int i = 0; i < lengthOfCalcArray; i++) {
                if (calcarray[i].equals(sign)) {
                    finishedCalc = false;
                    double sum = calc(sign, i);
                    calcarray[i - 1] = Double.toString(sum);
                    calcarray[i] = "X";
                    calcarray[i + 1] = "X";
                    bubbleSort(calcarray);
                }
            }
        }
    }

    public static double calc(String sign, int i) {
        return switch (sign) {
            case "*" -> Double.parseDouble(calcarray[i - 1]) * Double.parseDouble(calcarray[i + 1]);
            case "%" -> Double.parseDouble(calcarray[i - 1]) % Double.parseDouble(calcarray[i + 1]);
            case "/" -> Double.parseDouble(calcarray[i - 1]) / Double.parseDouble(calcarray[i + 1]);
            case "+" -> Double.parseDouble(calcarray[i - 1]) + Double.parseDouble(calcarray[i + 1]);
            case "-" -> Double.parseDouble(calcarray[i - 1]) - Double.parseDouble(calcarray[i + 1]);
            default -> -1;
        };
    }

    public static void replaceBrackets() {
        if (brackets) {
            if(end - begin == 1) {
                array[begin] = "X";
                array[end] = "X";
            }
            else {
                array[begin] = calcarray[0];
                // fill the rest with "X"
                for (int i = begin + 1; i <= end; i++) {
                    array[i] = "X";
                }
            }
        }
        else {
            array[0] = calcarray[0];
            for (int i = 1; i < array.length; i++) {
                array[i] = "X";
            }
        }
        // sorting the array so the "X" go to the right
        bubbleSort(array);
    }

    public static void main(String[] args) {
        formula = "(-2) - ()()( 10+5*(4+20 *2*2) - -2 /4)+8";
        System.out.println("String: " + formula);

        // test if the String has rigth input
//        boolean input = validating();

        // Debug
        array = new String[formula.length()];
        stringToArray();
        boolean input = false;
        for (String x : array) {
            System.out.print(x + " ");
        }
        System.out.println();


        String text = (input) ? "Correct" : "Wrong";
        System.out.println("Your input was " + text);

        if (input) {
            // Set length of array
            array = new String[formula.length()];

            // Step 1 String to Array
            // make String to Array
            stringToArray();

            // fill the the array with "X" where it is "null" and sort it
            nullBubleSort();

            // Step 2 Calculating the Array
            calcarray = new String[lengthOfCalcArray];
            boolean done = false;
            while (!done) {
                // find the deepest Layer
                deepestLayer();

                // make a new Array with the deepest layer
                createSubArray();

                // calc the deepest layer | multi > div = mod > plus = minus | * > / = % > + = -
                // multi
                calculation("*");
                // div
                calculation("/");
                // mod
                calculation("%");
                // plus
                calculation("+");
                // minus
                calculation("-");

                // replace the bracket with the calculated number in the brackets
                replaceBrackets();

                // Debug
                for (String x : array) {
                    System.out.print(x + " ");
                }
                System.out.println();

                // done?
                if (array[1].equals("X")) {
                    done = true;
                }

            }

            System.out.println("Answer: " + array[0]);
        }
    }
}