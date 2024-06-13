-- module: controlling name-spaces and creating abstract data types.
module Expression (
      Item (..)
    , Expression
    , value
    , showExpr
    , sample1, sample2
    ) where

import DataStructures.Stack.LinearStack

data Item = Add | Dif | Mul | Value Integer | LeftP | RightP deriving Show,Eq
type Expression = [Item]

-- sample1 corresponde con 5 + (6-2) * 3
sample1 = [Value 5, Add, LeftP, Value 6, Dif, Value 2, RightP, Mul, Value 3]

evaluateInFix :: Expression -> Integer
evaluateInFix xs = evaluateInFixAux xs empty empty