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

-- Complexity: 1
empty :: DEQue a
empty = DEQ [] []

-- Complexity: 1
isEmpty :: DEQue a -> Bool
isEmpty (DEQ [] []) = True
isEmpty _ = False

-- Complexity: 1
addFirst :: a -> DEQue a -> DEQue a
addFirst x (DEQ a p) = DEQ (x:a) p

-- Complexity: 1
addLast :: a -> DEQue a -> DEQue a
addLast x (DEQ a p) = DEQ a (x:p)

-- Complexity:
first :: DEQue a -> a
first (DEQ [] p) = head (reverse p)
first (DEQ a p) = head a

-- Complexity:
last :: DEQue a -> a
last (DEQ a []) = head (reverse a)
last (DEQ a p) = head p

-- Complexity:
deleteFirst :: DEQue a -> DEQue a
deleteFirst (DEQ [] []) = empty
deleteFirst (DEQ (x:xs) p) = DEQ xs p
deleteFirst (DEQ [] p) = deleteFirst (DEQ a' p')
   where
      a' = reverse (snd (splitAt pos p))
      p' = fst (splitAt pos p)
      pos = div (length p) 2

-- Complexity:
deleteLast :: DEQue a -> DEQue a
deleteLast (DEQ [] []) = empty
deleteLast (DEQ a (y:ys)) = DEQ a ys
deleteLast (DEQ a p) = deleteLast (DEQ a' p')
   where
      a' = fst (splitAt pos a)
      p' = reverse (snd (splitAt pos a))
      pos = div (length a) 2


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
