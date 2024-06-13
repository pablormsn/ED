package wellBalanced;

import dataStructures.stack.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WellBalanced {
    private final static String OPEN_PARENTHESES ="{[(";
    private final static String CLOSED_PARENTHESES = "}])";

    public static void main(String [] args) throws IOException {
        // ...
        String prueba;
        boolean estaBalanceado = false;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Ingrese la cadena: ");

        prueba = bf.readLine();

        if(wellBalanced(prueba, new ArrayStack<>())){
            System.out.println("La cadena " + prueba + " esta balanceada");
        }else{
            System.out.println("La cadena " + prueba + " no esta balanceada");
        }
    }

    public static boolean wellBalanced(String exp, Stack<Character> stack){
        // Itero sobre el string
        for (int i = 0; i < exp.length(); i++){
            if(isOpenParentheses(exp.charAt(i))){
                stack.push(exp.charAt(i));
            }else if(isClosedParentheses(exp.charAt(i))){
                if(match(exp.charAt(i), stack.top())){
                    stack.pop();
                }
            }
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
