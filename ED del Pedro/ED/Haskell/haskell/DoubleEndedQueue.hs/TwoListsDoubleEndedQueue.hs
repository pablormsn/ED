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
addLast y (DEQ xs ys) = DEQ xs (y:ys)

last' :: [a] -> a
last' [x] = x 
last' (x:xs) = last' xs

-- Complexity:
first :: DEQue a -> a
first (DEQ [] []) = error "cola vacia" 
first (DEQ [] ys) = last' ys
first (DEQ xs ys) = head xs 

-- Complexity:
last :: DEQue a -> a
last (DEQ [] []) = error "cola vacia"
last (DEQ xs []) = last' xs 
last (DEQ xs ys) = head ys

-- Complexity:
deleteFirst :: DEQue a -> DEQue a
deleteFirst (DEQ [] []) = DEQ [] [] 
deleteFirst (DEQ [] ys) = deleteFirst (DEQ f l)
   where
      (f,l) = splitFirst ys
deleteFirst (DEQ (x:xs) ys) = DEQ xs ys

splitFirst :: [a] -> ([a],[a])
splitFirst l = splitAc l [] (div n 2)
      where
         n = length l
         splitAc xs ys 0 = (reverse xs,ys)
         splitAc (x:xs) ys p = splitAc xs (ys ++ [x]) (p-1)

-- Complexity:
deleteLast :: DEQue a -> DEQue a
deleteLast (DEQ [] []) = DEQ [] []
deleteLast (DEQ xs []) = deleteLast (DEQ l f)
   where
      (f,l) = splitFirst xs
deleteLast (DEQ xs (y:ys)) = DEQ xs ys


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
