-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
-- Identity number (DNI if Spanish/passport if Erasmus):
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module Seq (Seq (..),
    addSingleDigit
) where

data Seq a = Empty | Node a (Seq a) deriving (Eq, Show)

-- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
-- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
-- EXERCISE 1

addSingleDigit :: (Integral a) => a -> Seq a -> Seq a
addSingleDigit d xs = num2Seq (d + seq2Num xs [] 0)
    where
        num2Seq n = aux n Empty
            where
                aux n seq
                    | n < 10 = Node n seq
                    | otherwise = aux (div n 10) (Node (mod n 10) seq)
        seq2Num Empty n sz = makeNum n sz 0 
            where
                makeNum [] pos num = num
                makeNum (x:xs) pos num = makeNum xs (pos - 1) (num + x*10^(pos-1)) 
        seq2Num (Node x (seq)) n sz = seq2Num seq (n ++ [x]) (sz+1)


