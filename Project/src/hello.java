// ((-2)) and ((2)) and more brackets are not allowed to use since it is the same as (-2) and (2)

// a negative number has to have the negative sign in front of it without a space between. Otherwise it counts as operator
// - 2 =/= -2 but --2 = - -2

public class hello {
    static int begin;
    static int end;
    static int lengthOfCalcArray = 0;
    static int countBrackets = 0;
    static boolean finishedCalc = false;
    static boolean brackets;
    static String formula;
    static String[] array;
    static String[] subArray;

    public static boolean validating() {
        boolean nextSign = false;
        boolean nextNumber = false;
        try {
            for (int i = 0; i < formula.length(); i++) {
                switch (formula.charAt(i)) {
                    case ' ' -> { }
                    case '*', '%', '/', '+' -> {
                        // sign after another sign
                        if (!nextSign) {
                            System.out.println("sign");
                            return false;
                        }
                        nextSign = false;
                        nextNumber = true;
                    }
                    case '(' -> {
                        // the next thing has to be a number not a sign
                        if (nextSign) {
                            System.out.println("(");
                            return false;
                        }
                        // boolean | int
                        String[] valid = validateOpenBracket(i);
                        i = Integer.parseInt(valid[1]);
                        // does it only contain one element?
                        if (Boolean.parseBoolean(valid[0])) { // Yes
                            nextNumber = true;
                            nextSign = false;
                            countBrackets++;
                            continue;
                        } // No
                        nextSign = true;
                        nextNumber = false;
                        countBrackets++;
                    }
                    case ')' -> {
                        // the next thing has to be a operator
                        if (!nextNumber) {
                            nextSign = true;
                        }
                        countBrackets--;
                    }
                    case '-' -> {
                        // -2 negative number
                        if (!nextSign && formula.substring(i+1, i+2).matches("[0-9]")) {
                            nextNumber = true;
                        }
                        // - operator
                        else if (nextSign) {
                            nextSign = false;
                            nextNumber = true;
                        }
                        // something is wrong
                        else {
                            System.out.println("-");
                            return false;
                        }
                    }
                    // Number
                    default -> {
                        // it isn't a number
                        if (!formula.substring(i, i + 1).matches("[0-9]")) {
                            System.out.println(formula.charAt(i));
                            System.out.println("default 1");
                            return false;
                        }
                        // it should be a number | can't even be happening, so no idea why i have it in
                        else if (nextNumber && !formula.substring(i, i+1).matches("[0-9]")) {
                            System.out.println("default 2");
                            return false;
                        }
                        // should be a sign but i have a number instead -> (2+2)2 -> should be (2+2)+2
                        else if (nextSign && formula.substring(i, i+1).matches("[0-9]")) {
                            System.out.println("default 3");
                            return false;
                        }
                        nextNumber = false;
                        nextSign = true;
                    }
                }
            }
            return countBrackets == 0;
        }
        catch(Exception e) {
            return false;
        }
    }

    // checks if there is only one number inside
    public static String[] validateOpenBracket(int begin) {
        // boolean | int
        String[] returnThing = new String[2];
        int counter = begin + 1;
        returnThing[0] = "true";

        for (int i = begin + 1; i < array.length; i++) {
            counter++;
            // just some space, therefore do nothing
            if (formula.charAt(i) == ' ') {counter--; }
            // end of the bracket
            else if (formula.charAt(i) == ')') {
                countBrackets--;
                returnThing[0] = "false";
                returnThing[1] = Integer.toString(i);
                return returnThing;
            }
            // there are more object in the array
            else if (formula.charAt(i) == '*' || formula.charAt(i) == '/' ||
                    formula.charAt(i) == '%' || formula.charAt(i) == '+' || formula.charAt(i) == '-' && i != counter) {
                returnThing[1] = Integer.toString(i);
                return returnThing;
            }
            // the first number is a negative number
            else if (i == counter && formula.charAt(i) == '-' || formula.substring(i, i + 1).matches("[0-9]")) {
                returnThing[0] = "true";
                returnThing[1] = Integer.toString(i);
            }
            // something does not look wright
            else {
                returnThing[0] = "false";
                returnThing[1] = Integer.toString(i);
                return returnThing;
            }
        }
        returnThing[0] = "false";
        return returnThing;
    }

    public static void stringToArray() {
        for (int i = 0; i < array.length; i++) {
            switch (formula.charAt(i)) {
                case ' ' -> array[i] = "X";
                case '*','/','%','+','(',')' -> array[i] = ""+formula.charAt(i);
                case '-' -> {
                    // minus before space| operator | 2 - 2
                    if (formula.charAt(i+1) == ' ') {
                        array[i] = "-";
                        array[++i] = "X";
                    }
                    // special case minus into minus | - -> - | --2
                    else if (formula.charAt(i+1) == '-') {
                        array[++i] = "-";
                        array[++i] = "-";
                        // after the minus it has to be a number
                        i = putNumberInArray(i);
                    }
                    // minus belong to the number | -2
                    else {
                        array[++i] = "-";
                        i = putNumberInArray(i);
                    }
                }
                // number
                default -> {
                    array[i] = "";
                    i = putNumberInArray(i);
                }
            }
        }
    }

    public static int putNumberInArray(int i) {
        for (int j = i; j < array.length; j++) {
            switch (formula.charAt(j)) {
                // end of the number
                case '*','/','%','+','-','(',')' -> {
                    array[++i] = ""+formula.charAt(j);
                    return j;
                }
                // end of the number
                case ' ' -> {
                    array[++i] = "X";
                    return j;
                }
                // fills the number into the array
                default -> {
                    array[i] += ""+formula.charAt(j);
                    // end of the array
                    if (j + 1 == array.length) {
                        return j;
                    }
                }
            }
        }
        // something went wrong
        return array.length;
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



    //-----------------------------------------------------------------------------------------
    // not tested so far
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
            subArray = new String[(end - begin) - 1]; // don't want the brackets in the new array
            lengthOfCalcArray = (end - begin) - 1;

            for (int i = begin + 1; i < end; i++) {
                subArray[i - (begin + 1)] = array[i];
            }
        } // outer's layer = no layers
        else {
            // how long is the array
            // in case the subarray is bigger then the last formula
            subArray = new String[lengthOfCalcArray];
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals("X")) {
                    lengthOfCalcArray = i;
                    break;
                }
                subArray[i] = array[i];
            }
        }
    }

    public static void calculation(String sign) {
        finishedCalc = false;
        while(!finishedCalc) {
            finishedCalc = true;
            for (int i = 0; i < lengthOfCalcArray; i++) {
                if (subArray[i].equals(sign)) {
                    finishedCalc = false;
                    double sum = calc(sign, i);
                    subArray[i - 1] = Double.toString(sum);
                    subArray[i] = "X";
                    subArray[i + 1] = "X";
                    bubbleSort(subArray);
                }
            }
        }
    }

    public static double calc(String sign, int i) {
        return switch (sign) {
            case "*" -> Double.parseDouble(subArray[i - 1]) * Double.parseDouble(subArray[i + 1]);
            case "%" -> Double.parseDouble(subArray[i - 1]) % Double.parseDouble(subArray[i + 1]);
            case "/" -> Double.parseDouble(subArray[i - 1]) / Double.parseDouble(subArray[i + 1]);
            case "+" -> Double.parseDouble(subArray[i - 1]) + Double.parseDouble(subArray[i + 1]);
            case "-" -> Double.parseDouble(subArray[i - 1]) - Double.parseDouble(subArray[i + 1]);
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
                array[begin] = subArray[0];
                // fill the rest with "X"
                for (int i = begin + 1; i <= end; i++) {
                    array[i] = "X";
                }
            }
        }
        else {
            array[0] = subArray[0];
            for (int i = 1; i < array.length; i++) {
                array[i] = "X";
            }
        }
        // sorting the array so the "X" go to the right
        bubbleSort(array);
    }
    //-----------------------------------------------------------------------------------------




    public static void main(String[] args) {
        formula = "(-2) * ( 10+5*(4+5 *2*2) - -2 /4)+8";
//        formula = "( -2)*2 *(5* ( 5+ 1)) -8";
//        formula = "  -2 + ( 2 --2 ) + 2 ";

        array = new String[formula.length()];

        // test if the String has right input
        boolean input = validating();
        System.out.print("String: " + formula);
        String text = (input) ? "Correct" : "Wrong";
        System.out.println(" " + text);

        if (input) {
            stringToArray();
            nullBubleSort();

            //-----------------------------------------------------------------------------------------
            // not tested so far
            subArray = new String[lengthOfCalcArray];
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
                //-----------------------------------------------------------------------------------------
            }
        }


        // Debug
//        for (int i = 0; i < array.length; i++) {
//            System.out.print(array[i] + " ");
//        }
//        System.out.println();
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals("X")) {
                break;
            }
            System.out.print(array[i]);
        }
        System.out.println();
    }
}


//          test if the validation works | all should be wrong
//        // -----------------------------------------
//        formula = "2(2+2)+2";
//
//        // test if the String has right input
//        input = validating();
//
//        System.out.print("String: " + formula);
//        text = (input) ? "Correct" : "Wrong";
//        System.out.println(" " + text);
//        // -----------------------------------------
//        formula = "2(+2+2)+2";
//
//        // test if the String has right input
//        input = validating();
//
//        System.out.print("String: " + formula);
//        text = (input) ? "Correct" : "Wrong";
//        System.out.println(" " + text);
//
//        // -----------------------------------------
//        formula = "2+(2+2+)+2";
//
//        // test if the String has right input
//        input = validating();
//
//        System.out.print("String: " + formula);
//        text = (input) ? "Correct" : "Wrong";
//        System.out.println(" " + text);
//
//        // -----------------------------------------
//        formula = "2+(2+2)2";
//
//        // test if the String has right input
//        input = validating();
//
//        System.out.print("String: " + formula);
//        text = (input) ? "Correct" : "Wrong";
//        System.out.println(" " + text);