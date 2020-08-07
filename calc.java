// Using Java 8 !!!!!!!!!! no improved Switch version !!!!!!!!!!!!!!

// if the number is negative the input can not have a space between the minus sign and the number!!!!!!
// Correct: -20, - -20, --20 | False: - 20, - - 20, -- 20

//                  Verbesserung von String to Array
// man loop nur einmal durch mit space complexity von O(n) | nur ein loop.
// Da wir loopen bis wir auf ein anderes Zeichen stoßen, wissen wir auch was das zeichen ist und müssen es nicht nochmal aufrufen.
// Bei einer negativen Zahl muss man der Zahl beim eintragen ins array ein minus vorne hinzufügen!
// man zählt dann nach dem anderen Zeichen weiter.
// spaces sind egal, wenn man den String to Double parst verschwieden sie.

public class calc {
    // Only for the first iteration over the String
    public static String[] returnString(int i, String test) {
        String myString = "";
        int counter = 0;
        for (int j = i; j < test.length(); j++){
            if (j == i && test.charAt(i) == '-') {
                myString += test.charAt(j);
            }
            else if (test.charAt(j) == ' ' || test.charAt(j) == '+' || test.charAt(j) == '-' || test.charAt(j) == '/' || test.charAt(j) == '*' || test.charAt(j) == '%' || test.charAt(j) == ')') {
                counter = j;
                break;
            }
            else {
                myString += test.charAt(j);
                // end of the String
                counter = j;
            }
        }
        if (counter == test.length() - 1) { // end of the line
            String[] placeholder = {myString, Integer.toString(counter)};
            return placeholder;
        }
        else {
            String[] placeholder = {myString, Integer.toString(counter - 1)};
            return placeholder;
        }
    }
    public static String[] returnString(int i, String test, boolean sign) {
        String myString = "";
        int counter = 0;
        for (int j = i; j < test.length(); j++){
            if (sign && test.charAt(i) == '-') {
                sign = false;
                myString += test.charAt(j);
            }
            else if (test.charAt(j) == ' ' || test.charAt(j) == '+' || test.charAt(j) == '-' || test.charAt(j) == '/' || test.charAt(j) == '*' || test.charAt(j) == '%' || test.charAt(j) == ')') {
                counter = j;
                break;
            }
            else {
                myString += test.charAt(j);
                // end of the String
                counter = j;
            }
        }

        if (counter == test.length() - 1) { // end of the line
            String[] placeholder = {myString, Integer.toString(counter)};
            return placeholder;
        }
        else {
            String[] placeholder = {myString, Integer.toString(counter - 1)};
            return placeholder;
        }
    }

    public static int bubbleSort(String[] array) {
        boolean done = false;
        while (!done) {
            done = true;
            for(int i = 0; i < array.length - 1; i++){
                if (array[i] == "X" && array[i+1] != "X") {
                    done = false;
                    array[i] = array[i+1];
                    array[i+1] = "X";
                }
            }
        }
        for (int i = 0; i < array.length; i++){
            // an "X" exist somewhere in there
            if (array[i] == "X"){
                return (i);
            }
        }
        // no "X" exist in the array
        return array.length;
    }

    public static void main(String[] args) {
        String test = " -2 * ( 10+5*(4+5 *2) - -2 /4)+8";
        System.out.println("String: " + test);
        String[] array = new String[test.length()];

        // transform String to Array
        boolean firstNumber = true;
        for (int i = 0; i < test.length(); i++) {
            // checks if the char is a space -> X
            if (test.charAt(i) == ' ') {
                array[i] = "X";
                continue;
            }
            else if (firstNumber) {
                // first thing it grabs is either a minus sign or a number, since we handle the space's one step above;
                firstNumber = false;
                String[] placeholder = new String[2];
                placeholder = returnString(i, test);
                array[i] = placeholder[0];
                i = Integer.parseInt(placeholder[1]);

            }
            else {
                if (test.charAt(i) == '+') {
                    array[i] = "+";
                    continue;
                }
                else if (test.charAt(i) == '/') {
                    array[i] = "/";
                    continue;
                }
                else if (test.charAt(i) == '*') {
                    array[i] = "*";
                    continue;
                }
                else if (test.charAt(i) == '%') {
                    array[i] = "%";
                    continue;
                }
                else if (test.charAt(i) == '(') {
                    array[i] = "(";
                    continue;
                }
                else if (test.charAt(i) == ')') {
                    array[i] = ")";
                    continue;
                }
                else if (test.charAt(i) == '-') {
                    boolean sign = false;
                    if (array[i-1] == " " || array[i-1] == "+" || array[i-1] == "-" || array[i-1] == "/" || array[i-1] == "*" || array[i-1] == "%" || array[i-1] == null || array[i-1] == "X") {
                        sign = true;
                    }
                    //array[i] = returnString(i, test, sign);
                    String[] placeholder = new String[2];
                    placeholder = returnString(i, test, sign);
                    array[i] = placeholder[0];
                    i = Integer.parseInt(placeholder[1]);
                }
                else {
                    String[] placeholder = new String[2];
                    placeholder = returnString(i, test);
                    array[i] = placeholder[0];
                    i = Integer.parseInt(placeholder[1]);
                }
            }
        }

        // replace null with "X"
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = "X";
            }
        }
        // Bubblesort -> moving all the "X" to the right
        int endOfArray = bubbleSort(array); // not gonna iterate over the X's

        // Solving the Equation
        boolean solved = false;
        int k = 3;
        while (k-- > 0) {
            // find the deepest layer
            int begin = 0;
            int end = 0;
            boolean brackets = false;
            for (int i = 0; i < endOfArray; i++) {
                if (array[i] == "(") {
                    begin = i;
                } else if (array[i] == ")") {
                    end = i;
                    brackets = true;
                    break;
                }
            }

            // initiate variable
            String[] calcarray = new String[array.length];
            int lengthOfCalcArray = calcarray.length; // not gonna iterate over the X's

            // Calculation
            // make a new Array with the deepest layer
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
                    calcarray[i] = array[i];
                    if (array[i] == "X") {
                        lengthOfCalcArray = i;
                        break;
                    }
                }
            }

            // calc the deepest layer
            // multi > div = mod > plus = minus | * > / = % > + = -

            // Verbesserung: eine funktion erstellen, die den swap macht und bubbleSort aufruft
            // multi
            for (int i = 0; i < lengthOfCalcArray; i++) {
                if (calcarray[i] == null){
                    break;
                }
                else if (calcarray[i] == "*") {
                    double sum = Double.parseDouble(calcarray[i-1]) * Double.parseDouble(calcarray[i+1]);
                    calcarray[i-1] = Double.toString(sum);
                    calcarray[i] = "X";
                    calcarray[i+1] = "X";
                    bubbleSort(calcarray);
                }
            }
            lengthOfCalcArray = bubbleSort(calcarray);

            // div
            for (int i = 0; i < lengthOfCalcArray; i++) {
                if (calcarray[i] == null){
                    break;
                }
                else if (calcarray[i] == "/") {
                    double sum = Double.parseDouble(calcarray[i-1]) / Double.parseDouble(calcarray[i+1]);
                    calcarray[i-1] = Double.toString(sum);
                    calcarray[i] = "X";
                    calcarray[i+1] = "X";
                    bubbleSort(calcarray);
                }
            }
            lengthOfCalcArray = bubbleSort(calcarray);

            // mod
            for (int i = 0; i < lengthOfCalcArray; i++) {
                if (calcarray[i] == null){
                    break;
                }
                else if (calcarray[i] == "%") {
                    double sum = Double.parseDouble(calcarray[i-1]) % Double.parseDouble(calcarray[i+1]);
                    calcarray[i-1] = Double.toString(sum);
                    calcarray[i] = "X";
                    calcarray[i+1] = "X";
                    bubbleSort(calcarray);
                }
            }
            lengthOfCalcArray = bubbleSort(calcarray);

            // plus
            for (int i = 0; i < lengthOfCalcArray; i++) {
                if (calcarray[i] == null){
                    break;
                }
                else if (calcarray[i] == "+") {
                    double sum = Double.parseDouble(calcarray[i-1]) + Double.parseDouble(calcarray[i+1]);
                    calcarray[i-1] = Double.toString(sum);
                    calcarray[i] = "X";
                    calcarray[i+1] = "X";
                    bubbleSort(calcarray);
                }
            }
            lengthOfCalcArray = bubbleSort(calcarray);

            // minus
            for (int i = 0; i < lengthOfCalcArray; i++) {
                if (calcarray[i] == null){
                    break;
                }
                else if (calcarray[i].equals("-")) {
                    double sum = Double.parseDouble(calcarray[i-1]) - Double.parseDouble(calcarray[i+1]);
                    calcarray[i-1] = Double.toString(sum);
                    calcarray[i] = "X";
                    calcarray[i+1] = "X";
                    bubbleSort(calcarray);
                }
            }
            bubbleSort(calcarray);

            // Debug
//            System.out.println("Hello!!!!!");
//            for (String x : calcarray) {
//                System.out.print(x + " ");
//            }
//            System.out.println();

            // replace the bracket with the calculated number in the brackets
            if (brackets) {
                array[begin] = calcarray[0];
                // fill the rest with "X"
                for (int i = begin + 1; i <= end; i++) {
                    array[i] = "X";
                }
            }
            else {
                array[0] = calcarray[0];
                for (int i = 1; i < array.length; i++) {
                    array[i] = "X";
                }
            }

            bubbleSort(array);


            // Debug
            for (String x : array){
                System.out.print(x + " ");
            }
            System.out.println();

            // done?
            if (array[1] == "X") {
                solved = true;
            }
        }

        System.out.println("Answer: " + array[0]);


        // Debug
//        System.out.println(test);
//        for (String x : array) {
//            System.out.print(x + " ");
//        }
//        System.out.println();

    }
}
