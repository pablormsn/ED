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
addFirst x (DEQ fl sl) = DEQ (x : fl) sl

-- Complexity:
addLast :: a -> DEQue a -> DEQue a
addLast x (DEQ fl sl) = DEQ fl (x : sl)

-- Complexity:
first :: DEQue a -> a
first (DEQ (f : fs) _) = f
first (DEQ [] sl) = first (DEQ (reverse (snd half)) (fst half))
   where
      half = splitAt (div (length sl) 2) sl

-- Complexity:
last :: DEQue a -> a
last (DEQ _ (s : ss)) = s
last (DEQ fl []) = last (DEQ (fst half) (reverse (snd half)))
   where
      half = splitAt (div (length fl) 2) fl

-- Complexity:
deleteFirst :: DEQue a -> DEQue a
deleteFirst (DEQ (f : fs) sl) = (DEQ fs sl)
deleteFirst (DEQ [] sl) = deleteFirst (DEQ (reverse (snd half)) (fst half))
   where
      half = splitAt (div (length sl) 2) sl

-- Complexity:
deleteLast :: DEQue a -> DEQue a
deleteLast (DEQ fl (s : ss)) = (DEQ fl ss)
deleteLast (DEQ fl []) = deleteLast (DEQ (fst half) (reverse (snd half)))
   where
      half = splitAt (div (length fl) 2) fl



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
