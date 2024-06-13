package infix;

import postFix.*;
import dataStructures.stack.LinkedListStack;
import dataStructures.stack.Stack;

public class InFix {
    static int evaluate(Item[] exprList) {
        Stack<Integer> stackDatas = new LinkedListStack<Integer>();
        Stack<Item> stackOperations = new LinkedListStack<Item>();
        for (Item expr : exprList) {
            if (expr.isData()) {
                stackDatas.push(expr.getValue());
            } else if (expr.isOperation()) {
                stackOperations.push(expr);
            } else if (expr.isParentheses()) {
                if(expr instanceof RightP){
                    int a = stackDatas.top();
                    stackDatas.pop();
                    int b = stackDatas.top();
                    stackDatas.pop();
                    Item op = stackOperations.top();
                    stackOperations.pop();
                    stackDatas.push(op.evaluate(b,a));
                }
            }
        }
        return stackDatas.top();
    }

    public static void main(String [] args) {

        Item[] sample = {
                new LeftP(),
                new LeftP(),
                new Data(4),
                new Mul(),
                new Data(5),
                new RightP(),
                new Dif(),
                new Data(6),
                new RightP()
        };

        System.out.println(InFix.evaluate(sample));
    }
}