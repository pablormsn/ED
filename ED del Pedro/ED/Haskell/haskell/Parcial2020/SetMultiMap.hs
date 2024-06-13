module SetMultiMap (
    SetMultiMap
    , empty
    , isEmpty
    , size
    , isDefinedAt
    , insert
    , valuesOf
    , deleteKey
    , deleteKeyValue
    , filterValues
    , fold
)where

import Data.List (intercalate)
import Test.QuickCheck

import qualified DataStructures.Set.LinearSet as S
-- Si hace falta importo más librerías

-- Los nodos estan ordenados por clave
-- No hay claves repetidas
-- No hay claves que tengan asociado un conjunto vacío

-- 'a' sería el tipo de la clave y 'b' el tipo de los valores que estarán guardados en un conjunto
data SetMultiMap a b = Empty | Node a (S.Set b) (SetMultiMap a b) deriving Eq

m1 :: SetMultiMap String Int 
m1 = Node "alfredo" (mkSet [9]) (
     Node "juan" (mkSet [0,1,8]) (
     Node "maria" (mkSet [4,-6,8])
     Empty))

mkSet :: Eq a => [a] -> S.Set a
mkSet = foldr S.insert S.empty
-- Defino los métodos
-- empty
empty :: SetMultiMap a b
empty = Empty

-- isEmpty
isEmpty :: SetMultiMap a b -> Bool
isEmpty Empty = True
isEmpty _ = False

-- size
size :: SetMultiMap a b -> Integer
size Empty = 0
size (Node _ _ sCola) = 1 + size sCola

-- isDefinedAt
isDefinedAt :: (Ord a, Eq a) => a -> SetMultiMap a b -> Bool
isDefinedAt k Empty = False
isDefinedAt k (Node x y cola) 
    | k == x = True
    | otherwise = isDefinedAt k cola

-- insert
insert :: (Ord a, Eq b) => a -> b -> SetMultiMap a b -> SetMultiMap a b
insert k v Empty = Node k (S.insert v S.empty) Empty
insert k v s1@(Node x y cola)
    | k < x = Node k (S.insert v S.empty) s1
    | k == x = Node x (S.insert v y) cola
    | otherwise = Node x y (insert k v cola)

-- valuesOf
valuesOf :: (Ord a, Eq b) => a -> SetMultiMap a b -> S.Set b
valuesOf k Empty = S.empty
valuesOf k (Node x y cola) = if k == x then y else valuesOf k cola

-- deleteKey
deleteKey :: (Ord a, Eq b) => a -> SetMultiMap a b -> SetMultiMap a b
deleteKey k Empty = Empty
deleteKey k s1@(Node x y cola)
    | k < x = s1
    | k == x = cola
    | otherwise = Node x y (deleteKey k cola)

-- deleteKeyValue
deleteKeyValue :: (Ord a, Eq b) => a -> b -> SetMultiMap a b -> SetMultiMap a b
deleteKeyValue k v Empty = error "delete on empty set"
deleteKeyValue k v s1@(Node x y Empty)
    | k == x && S.isElem v y && S.size y > 1 = Node x (S.delete v y) Empty
    | k == x && S.isElem v y && S.size y == 1 = Empty
    | otherwise = s1
deleteKeyValue k v s1@(Node x y cola)
    | k < x = s1
    | k == x && S.isElem v y = if S.size y == 1 then cola else Node x (S.delete v y) cola 
    | otherwise = Node x y (deleteKeyValue k v cola)

-- filterValues
filterValues :: (Ord a, Eq b) => (b -> Bool) -> SetMultiMap a b -> SetMultiMap a b
filterValues p Empty = Empty
filterValues p (Node x y cola) = Node x y' (filterValues p cola)
    where 
        y' = S.fold (\ x res -> if p x then S.insert x res else res) S.empty y

fold :: (Ord a, Eq b) => (a -> b -> c -> c) -> c -> SetMultiMap a b -> c
fold f z ms = recSetMultiMap ms
    where
        recSetMultiMap Empty = z
        recSetMultiMap (Node k s ms)
            | S.isEmpty s = recSetMultiMap ms 
            | otherwise = f k v (recSetMultiMap (Node k s' ms))
            where
                (v,s') = pickOne s
                pickOne s = (v,S.delete v s)
                    where v = head $ S.fold (:) [] s

instance (Show a, Show b) => Show(SetMultiMap a b) where
    show Empty = "{}"
    show ms = intercalate "\n" (showKeyValues ms)
        where
            showKeyValues Empty = []
            showKeyValues (Node k s ms) = (show k ++ "-->" ++ show s) : showKeyValues ms

instance (Ord a, Arbitrary a, Eq b, Arbitrary b) => Arbitrary (SetMultiMap a b)
    where
        arbitrary = do
        xs <- listOf arbitrary
        ys <- listOf arbitrary
        return (foldr (uncurry insert) empty (zip xs ys))