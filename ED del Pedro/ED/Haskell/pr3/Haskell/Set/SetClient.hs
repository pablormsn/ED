------------------------------------------------------------
-- Estructuras de Datos
-- Tema 3. Estructuras de Datos Lineales
-- Pablo López
--
-- Módulo cliente del TAD Conjunto
------------------------------------------------------------

module SetClient where

import           Data.Char                          (toUpper)
import           DataStructures.Set.SortedLinearSet

-- |
-- >>> mkSet [1..10]
-- SortedLinearSet(1,2,3,4,5,6,7,8,9,10)
-- mkSet "abracadabra"
-- SortedLinearSet('a','b','c','d','r')
mkSet :: Ord a => [a] -> Set a
mkSet xs = foldr insert empty xs

primos :: Set Integer
primos = mkSet [13, 7, 3, 2, 5, 11, 19]

-- |
-- >>> cardinal primos
-- 7
-- >>> cardinal (mkSet "abracadabra")
-- 5
cardinal :: Set a -> Integer
cardinal xs = fold (\ _ n -> n+1 ) 0 xs
-- f x (f x' (f x'' 0)) == f x (f x' 1) == f x 2 == 3

-- |
-- >>> suma primos
-- 60
suma :: Num a => Set a -> a
suma xs = fold (+) 0 xs

-- |
-- >>> setToList primos
-- [2,3,5,7,11,13,19]
setToList :: Set a -> [a]
-- Primera posible forma
setToList s = fold f [] s
    where 
        f x s' = [x] ++ s' -- f x == [x] ++ && (recSet s) == s'
-- |
-- >>> filtra even primos
-- SortedLinearSet(2)
-- >>> filtra (\ x -> x `mod` 5 == 0) (mkSet [1..100])
-- SortedLinearSet(5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100)
filtra :: Ord a => (a -> Bool) -> Set a -> Set a
filtra f s = mkSet (fold check [] s) 
    where 
        check x s' = if not (f x) then s' else [x] ++ s'

-- |
-- >>> union' primos primos
-- SortedLinearSet(2,3,5,7,11,13,19)
-- >>> union primos (mkSet [1..10])
-- SortedLinearSet(1,2,3,4,5,6,7,8,9,10,11,13,19)
union' :: Ord a => Set a -> Set a -> Set a
union' s1 s2 = mkSet (auxUnion' (setToList s1) (setToList s2))
    where
        auxUnion' [] [] = []
        auxUnion' [] l2 = l2
        auxUnion' l1 [] = l1
        auxUnion' (x:xs) (y:ys) 
            | x == y = x:(auxUnion' xs ys)
            | x < y = x:(auxUnion' xs (y:ys))
            | otherwise = y:(auxUnion' (x:xs) ys)
-- |
-- >>> aplica (*2) primos
-- SortedLinearSet(4,6,10,14,22,26,38)
-- >>> aplica toUpper (mkSet "abracadabra")
-- SortedLinearSet('A','B','C','D','R')
aplica :: (Ord a, Ord b) => (a->b) -> Set a -> Set b
aplica f s = mkSet (map f (setToList s))

-- |
-- >>>  maximo primos
-- Just 19
-- >>> maximo (mkSet "abracadabra")
-- Just 'r'
-- maximo empty
-- Nothing
maximo :: Ord a => Set a -> Maybe a
maximo s = maxList (setToList s) 
    where
        maxList [] = Nothing
        maxList (x:[]) = Just x
        maxList (x:xs) = maxList xs