-------------------------------------------------------------------------------
-- Apellidos, Nombre: 
-- Titulacion, Grupo: 
--
-- Estructuras de Datos. Grados en Informatica. UMA.
-------------------------------------------------------------------------------

module AVLBiDictionary( BiDictionary
                      , empty
                      , isEmpty
                      , size
                      , insert
                      , valueOf
                      , keyOf
                      , deleteByKey
                      , deleteByValue
                      , toBiDictionary
                      , compose
                      , isPermutation
                      , orbitOf
                      , cyclesOf
                      ) where

import qualified DataStructures.Dictionary.AVLDictionary as D
import qualified DataStructures.Set.BSTSet               as S

import           Data.List                               (intercalate, nub,
                                                          (\\))
import           Data.Maybe                              (fromJust, fromMaybe,
                                                          isJust)
import           Test.QuickCheck


data BiDictionary a b = Bi (D.Dictionary a b) (D.Dictionary b a)

-- | Exercise a. empty, isEmpty, size

empty :: (Ord a, Ord b) => BiDictionary a b
empty = Bi (D.empty) (D.empty)

isEmpty :: (Ord a, Ord b) => BiDictionary a b -> Bool
isEmpty (Bi dk dv) = (D.isEmpty dk) && (D.isEmpty dv)

size :: (Ord a, Ord b) => BiDictionary a b -> Int
size (Bi dk dv) = D.size dk

-- | Exercise b. insert

insert :: (Ord a, Ord b) => a -> b -> BiDictionary a b -> BiDictionary a b
insert k v (Bi dk dv)
  |elem k (D.values dv) = Bi (D.insert k v dk) (D.insert v k (D.delete (fromJust (D.valueOf k dk)) dv))
  | otherwise = (Bi (D.insert k v dk) (D.insert v k dv))

-- | Exercise c. valueOf

valueOf :: (Ord a, Ord b) => a -> BiDictionary a b -> Maybe b
valueOf k (Bi dk dv) = D.valueOf k dk

-- | Exercise d. keyOf

keyOf :: (Ord a, Ord b) => b -> BiDictionary a b -> Maybe a
keyOf v (Bi dk dv) = D.valueOf v dv

-- | Exercise e. deleteByKey

deleteByKey :: (Ord a, Ord b) => a -> BiDictionary a b -> BiDictionary a b
deleteByKey k (Bi dk dv)
  | not (elem k (D.values dv)) = (Bi dk dv)
  | otherwise = (Bi (D.delete k dk) (D.delete v dv))
    where v = fromJust (D.valueOf k dk)

-- | Exercise f. deleteByValue

deleteByValue :: (Ord a, Ord b) => b -> BiDictionary a b -> BiDictionary a b
deleteByValue v (Bi dk dv)
  |not (D.isDefinedAt v dv) = (Bi dk dv)
  |otherwise = (Bi (D.delete k dk) (D.delete v dv))
    where k = fromJust (D.valueOf v dv)

-- | Exercise g. toBiDictionary

toBiDictionary :: (Ord a, Ord b) => D.Dictionary a b -> BiDictionary a b
toBiDictionary dict
  | not (esInyectivo dict) = error "El diccionario no es inyectivo"
  | otherwise = D.foldKeysValues insert empty dict

esInyectivo :: Ord b => D.Dictionary a b -> Bool
esInyectivo dict = szKeys == szValues
  where 
    szKeys = length (D.keys dict)
    szValues = length (nub (D.values dict))


-- | Exercise h. compose

compose :: (Ord a, Ord b, Ord c) => BiDictionary a b -> BiDictionary b c -> BiDictionary a c
compose bd (Bi dk dv) = aux (D.keysValues dk) empty
  where
    aux [] sol = sol
    aux (x:xs) sol = aux xs (insert k v sol)
      where
        k = fromJust (keyOf (fst x) bd)
        v = snd x

-- | Exercise i. isPermutation

isPermutation :: Ord a => BiDictionary a a -> Bool
isPermutation (Bi dk dv) = (D.keys dk) \\ (D.keys dv) == []



-- |------------------------------------------------------------------------


-- | Exercise j. orbitOf

orbitOf :: Ord a => a -> BiDictionary a a -> [a]
orbitOf = undefined

-- | Exercise k. cyclesOf

cyclesOf :: Ord a => BiDictionary a a -> [[a]]
cyclesOf = undefined

-- |------------------------------------------------------------------------


instance (Show a, Show b) => Show (BiDictionary a b) where
  show (Bi dk dv)  = "BiDictionary(" ++ intercalate "," (aux (D.keysValues dk)) ++ ")"
                        ++ "(" ++ intercalate "," (aux (D.keysValues dv)) ++ ")"
   where
    aux kvs  = map (\(k,v) -> show k ++ "->" ++ show v) kvs
