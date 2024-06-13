-- Ejercicio 2
module MyLinearPriorityQueue (
    empty
    , isEmpty
    , first
    , enqueue
    , dequeue
) where

import Data.List(intercalate)

-- Invariante:
--      - Los elementos no tienen por que estar ordenados
--      - Se inserta en la cabeza (como una stack)
data LinearPriorityQueue a = Empty | Node a (LinearPriorityQueue a)

empty :: LinearPriorityQueue a
empty = Empty

isEmpty :: LinearPriorityQueue a -> Bool
isEmpty Empty = True
isEmpty _ = False

-- Debo buscar el elemento mínimo
first :: Ord a => LinearPriorityQueue a -> a
first Empty = error "first on empty queue"
first qp@(Node x xs) = firstAux qp x
  where
    firstAux Empty f = f
    firstAux (Node x xs) f | x < f = firstAux xs x
      | otherwise = firstAux xs f

enqueue :: a -> LinearPriorityQueue a -> LinearPriorityQueue a
enqueue x qp = Node x qp

dequeue :: LinearPriorityQueue a -> LinearPriorityQueue a
dequeue Empty = Empty
dequeue (Node x xs) = xs

instance (Show a) => Show (LinearPriorityQueue a) where
  show q = "LinearPriorityQueue(" ++ intercalate "," (aux q) ++ ")"
    where
     aux Empty      =  []
     aux (Node x q) =  show x : aux q
