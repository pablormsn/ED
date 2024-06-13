-------------------------------------------------------------------------------
-- Estructuras de Datos. Grado en Informática, IS e IC. UMA.
-- Examen de Febrero 2015.
--
-- Implementación del TAD Deque
--
-- Apellidos:
-- Nombre:
-- Grado en Ingeniería ...
-- Grupo:
-- Número de PC:
-------------------------------------------------------------------------------

module TwoListsDoubleEndedQueue
   ( DEQue
   , empty
   , isEmpty
   , first
   , last
   , addFirst
   , addLast
   , deleteFirst
   , deleteLast
   ) where

import Prelude hiding (last)
import Data.List(intercalate)
import Test.QuickCheck

data DEQue a = DEQ [a] [a]

-- Complexity:
empty :: DEQue a
empty = DEQ [] []

-- Complexity:
isEmpty :: DEQue a -> Bool
isEmpty (DEQ [] []) = True
isEmpty _ = False

-- Complexity:
addFirst :: a -> DEQue a -> DEQue a
addFirst x (DEQ d p) = DEQ (x:d) p

-- Complexity:
addLast :: a -> DEQue a -> DEQue a
addLast x (DEQ d p) = DEQ d (p ++ [x])

-- Complexity:
first :: DEQue a -> a
first (DEQ [] p) = head p
first (DEQ d p) = head d
-- Complexity:
last :: DEQue a -> a
last (DEQ d []) = head d
last (DEQ d p) = head (reverse p)

-- Complexity:
deleteFirst :: DEQue a -> DEQue a
deleteFirst (DEQ (f : fs) sl) = (DEQ fs sl)
deleteFirst (DEQ [] sl) = deleteFirst (DEQ (reverse (snd half)) (fst half))
   where
      half = splitAt (div (length sl) 2) sl

-- Complexity:
deleteLast :: DEQue a -> DEQue a
deleteLast (DEQ d []) = if length d >= 2 then aux d [] else empty
   where 
      aux (x:xs) sol
         | length sol <= div (length (x:xs)) 2 = aux xs (x:sol)
         | otherwise = deleteLast (DEQ sol (x:xs))
deleteLast (DEQ d p) = DEQ d (tail p)



instance (Show a) => Show (DEQue a) where
   show q = "TwoListsDoubleEndedQueue(" ++ intercalate "," [show x | x <- toList q] ++ ")"

toList :: DEQue a -> [a]
toList (DEQ xs ys) =  xs ++ reverse ys

instance (Eq a) => Eq (DEQue a) where
   q == q' =  toList q == toList q'

instance (Arbitrary a) => Arbitrary (DEQue a) where
   arbitrary =  do
      xs <- listOf arbitrary
      ops <- listOf (oneof [return addFirst, return addLast])
      return (foldr id empty (zipWith ($) ops xs))
