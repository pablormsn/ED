package wellBalanced;
import dataStructures.stack.*;
public class WellBalanced {
    private final static String OPEN_PARENTHESES = "{[(";
    private final static String CLOSED_PARENTHESES = "}])";
    public static void main(String [] args) {
    }
    public static boolean wellBalanced(String exp, Stack<Character> stack) {
        int i = 0;
        while(i < exp.length()){
            if (isOpenParentheses(exp.charAt(i))){
                stack.push(exp.charAt(i));
            }else if(isClosedParentheses(exp.charAt(i))){
                if(match(exp.charAt(i),stack.top())) stack.pop();
            }
            i++;
        }
        return stack.isEmpty();
    }
    public static boolean isOpenParentheses(char c) {
        return OPEN_PARENTHESES.indexOf(Character.toString(c)) >= 0;
    }
    public static boolean isClosedParentheses(char c) {
        return CLOSED_PARENTHESES.indexOf(Character.toString(c)) >= 0;
    }
    public static boolean match(char x, char y) {
        return OPEN_PARENTHESES.indexOf(Character.toString(x)) ==
                CLOSED_PARENTHESES.indexOf(Character.toString(y));
    }
}