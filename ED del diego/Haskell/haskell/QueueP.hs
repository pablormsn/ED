-- Ejercicio 8
module QueueP(
    QueueP
    , isEmpty
    , enqueue
    , dequeue
    , first
    , empty
) where

import Data.List(intercalate)

data QueueP a =  Empty | Node a (QueueP a)

empty :: QueueP a
empty = Empty

isEmpty :: QueueP a -> Bool
isEmpty Empty = True
isEmpty _ = False

first :: QueueP a -> a
first (Node x q) = x

-- Al encolar hay que mirar el orden
enqueue :: Ord a => a -> QueueP a -> QueueP a
enqueue x Empty = Node x Empty
enqueue x (Node y q)
    | x <= y = Node x (Node y q)
    | otherwise = Node y (enqueue x q)

dequeue :: Ord a => QueueP a -> QueueP a
dequeue Empty = error "cola vacia"
dequeue (Node x q) = q

instance (Show a) => Show (QueueP a) where
  show q = "QueueP(" ++ intercalate "," (aux q) ++ ")"
    where
    aux Empty      =  []
    aux (Node x q) =  show x : aux q