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
addFirst x (DEQ xs ys) = DEQ (x:xs) ys

-- Complexity:
addLast :: a -> DEQue a -> DEQue a
addLast x (DEQ xs ys) = DEQ xs (x:ys)

-- Complexity:
first :: DEQue a -> a
first (DEQ xs _) = head xs
first (DEQ [] ys) = first (DEQ (reverse (snd half)) (fst half))
   where half = splitAt (div (length ys) 2) ys

-- Complexity:
last :: DEQue a -> a
last (DEQ _ ys) = head (reverse ys)
last (DEQ xs []) = last (DEQ (fst half) (reverse(snd half)))
   where half = splitAt (div (length xs) 2) xs

-- Complexity:
deleteFirst :: DEQue a -> DEQue a
deleteFirst (DEQ xs ys) = (DEQ (tail xs) ys)
deleteFirst (DEQ [] ys) = deleteFirst (DEQ (reverse (snd half)) (fst half))
   where half = splitAt (div (length ys) 2) ys

-- Complexity:
deleteLast :: DEQue a -> DEQue a
deleteLast (DEQ xs ys) = (DEQ xs (reverse(tail (reverse ys))))
deleteLast (DEQ xs []) = deleteLast (DEQ (fst half) (reverse(snd half)))
   where half = splitAt (div (length xs) 2) xs



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
