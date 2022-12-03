import java.util.Scanner;

public class FullCalculator
{
    class nullValue extends RuntimeException
    {
        nullValue()
        {
            super("please try again");
        }
    }
    class divideOnZero extends RuntimeException
    {
        divideOnZero()
        {
            super("you can not divide numbers on zero");
        }
    }
    class unbalancedParantese extends RuntimeException
    {
        unbalancedParantese()
        {
            super("please enter paranteses correctly");
        }
    }
    private stackClass<Character> operatorStack;
    private stackClass<Double> valueStack;
    private boolean error;

    public FullCalculator() {
        operatorStack = new stackClass<>();
        valueStack = new stackClass<>();
        error = false;
    }

    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
    }

    private int getPrecedence(char ch) {
        if (ch == '+' || ch == '-') {
            return 1;
        }
        if (ch == '*' || ch == '/') {
            return 2;
        }
        if (ch == '^' )
            return 3;
        return 0;
    }

    private void processOperator(char t) {
        double a, b;
        if (valueStack.isEmpty()) {
            throw new nullValue();
        }
        else
        {
            b = valueStack.peek();
            valueStack.pop();
        }
        if (valueStack.isEmpty()) {
            return;
        }
        else
        {
            a = valueStack.peek();
            valueStack.pop();
        }
        double r = 0;
        if (t == '+') {
            r = a + b;
            System.out.println(a +"+"+ b);
        }
        else if (t == '-')
        {
            r = a - b;
            System.out.println(a +"-"+ b);
        }
        else if (t == '*')
        {
            r = a * b;
            System.out.println(a +"*"+ b);
        }
        else if(t == '/')
        {
            if (b == 0)
            throw new divideOnZero();
            r = a / b;
            System.out.println(a +"/"+ b);
        }
        else if (t == '^')
        {
            r = Math.pow(a , b);
            System.out.println(a +"^"+ b);
        }
        else
        {
            System.out.println("Operator error.");
            error = true;
        }
        valueStack.push(r);
    }

    public void processInput(String input) {
        String[] tokens = input.split(" ");

        for (int n = 0; n < tokens.length; n++) {
            String nextToken = tokens[n];
            char ch = nextToken.charAt(0);
            if (ch >= '0' && ch <= '9') {
                double value = Double.parseDouble(nextToken);
                valueStack.push(value);
            } else if (isOperator(ch)) {
                if (operatorStack.isEmpty() || getPrecedence(ch) > getPrecedence(operatorStack.peek())) {
                    operatorStack.push(ch);
                } else {
                    while (!operatorStack.isEmpty() && getPrecedence(ch) <= getPrecedence(operatorStack.peek())) {
                        char toProcess = operatorStack.peek();
                        operatorStack.pop();
                        try {
                            processOperator(toProcess);
                        } catch (nullValue nu) {
                            nu.getMessage();
                            System.out.print("Enter an expression to compute: ");
                            Scanner scanner = new Scanner(System.in);
                            String userInput = scanner.nextLine();
                            FullCalculator f1 = new FullCalculator();
                            f1.processInput(userInput);
                        }
                    }
                    operatorStack.push(ch);
                }
            } else if (ch == '(') {
                operatorStack.push(ch);
            } else if (ch == ')') {
                while (!operatorStack.isEmpty() && isOperator(operatorStack.peek())) {
                    char toProcess = operatorStack.peek();
                    operatorStack.pop();
                    try {
                        processOperator(toProcess);
                    } catch (nullValue nu) {
                        nu.getMessage();
                        System.out.print("Enter an expression to compute: ");
                        Scanner scanner = new Scanner(System.in);
                        String userInput = scanner.nextLine();
                        FullCalculator f1 = new FullCalculator();
                        f1.processInput(userInput);
                    }
                }
                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                    operatorStack.pop();
                } else {
                    try
                    {
                        throw new unbalancedParantese();
                    }
                    catch (unbalancedParantese u1)
                    {
                        processInput(scanner.scanString());
                    }
                }
            }
        }
        while (!operatorStack.isEmpty() && isOperator(operatorStack.peek())) {
            char toProcess = operatorStack.peek();
            operatorStack.pop();
            try {
                processOperator(toProcess);
            } catch (nullValue nu) {
                nu.getMessage();
                System.out.print("Enter an expression to compute: ");
                Scanner scanner = new Scanner(System.in);
                String userInput = scanner.nextLine();
                FullCalculator f1 = new FullCalculator();
                f1.processInput(userInput);
            }
        }
        if (error == false) {
            double result = valueStack.peek();
            valueStack.pop();
            if (!operatorStack.isEmpty() || !valueStack.isEmpty()) {
                throw new unbalancedParantese();

            } else {
                System.out.println("The result is " + result);
            }
        }
    }
}
