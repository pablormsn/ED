-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Práctica 3 - Uso del TAD Bag
--
-- Alumno: APELLIDOS, NOMBRE
-------------------------------------------------------------------------------

module BagClient where

import           LinearBag

-- `mkBag`: convierte una lista en una bolsa
-- |
-- >>> mkBag "abracadabra"
-- LinearBag { 'a' 'a' 'a' 'a' 'a' 'b' 'b' 'c' 'd' 'r' 'r' }
mkBag :: Ord a => [a] -> Bag a
mkBag xs = foldr insert empty xs

{-
   El TAD Bag es casi inútil sin una función de plegado. El plegado es
   un iterador que permite recorrer una bolsa sin conocer su
   implementación.

   Los siguientes ejercicios se resuelven mediante un plegado de bolsa.
-}

-- `keys`: devuelve una lista con las claves (elementos) que aparecen en la bolsa
-- |
-- >>> keys (mkBag "abracadabra")
-- "abcdr"
keys :: Ord a => Bag a -> [a]
keys bx = foldBag (\ x _ resX -> x:resX) [] bx

-- `bag2list`: convierte una bolsa en una lista de pares `(a,Int)`
-- |
-- >>> bag2List (mkBag "abracadabra")
-- [('a',5),('b',2),('c',1),('d',1),('r',2)]
bag2List :: Ord a => Bag a -> [(a, Int)]
bag2List bx = foldBag (\ x ox resX -> [(x,ox)] ++ resX) [] bx 

-- `cardinal`: devuelve el número de elementos de una bolsa
-- |
-- >>> cardinal (mkBag "abracadabra")
-- 11
cardinal :: Ord a => Bag a -> Int
cardinal bx = foldBag (\ _ ox resX -> resX+ox) 0 bx

-- `contains`: devuelve `True` si el dato aparece en la bolsa, `False` en caso contrario
-- |
-- > contains 'a' (mkBag "abracadabra")
-- True
-- > contains 'z' (mkBag "abracadabra")
-- False
contains :: Ord a => a -> Bag a -> Bool
contains x bx = foldBag (\ y _ resY -> if x == y then True else resY) False bx
-- `maxOcurrences`: devuelve el número de veces que aparece el elemento que
-- aparece más veces en una bolsa
-- |
-- >>> maxOcurrences (mkBag "abracadabra")
-- 5
maxOcurrences :: Ord a => Bag a -> Int
maxOcurrences bx = foldBag (\ _ ox resX -> max ox resX) 0 bx 

-- `mostCommons`: devuelve los elementos que aparecen más veces en la bolsa y su cardinal común
-- |
-- >>> mostCommons (mkBag "abcabcab")
-- [('a',3),('b',3)]
mostCommons :: Ord a => Bag a -> [(a,Int)]
mostCommons bx = aux (bag2List bx) (maxOcurrences bx)
   where 
      aux [] m = []
      aux xs m
         | snd == m = [(fst,snd)] ++ (aux (tail xs) m)
         | otherwise = aux (tail xs) m
         where
            (fst,snd) = head xs

