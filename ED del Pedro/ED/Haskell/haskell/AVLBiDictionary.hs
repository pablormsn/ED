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
isEmpty (Bi ks vs) = (D.isEmpty ks) && (D.isEmpty vs)

size :: (Ord a, Ord b) => BiDictionary a b -> Int
size (Bi ks vs) = D.size ks

-- | Exercise b. insert

insert :: (Ord a, Ord b) => a -> b -> BiDictionary a b -> BiDictionary a b
insert x y (Bi a b)
  | elem x (D.values b) = Bi (D.insert x y a) (D.insert y x (D.delete (fromJust (D.valueOf x a)) b))
  | otherwise = Bi (D.insert x y a) (D.insert y x b)

-- | Exercise c. valueOf

valueOf :: (Ord a, Ord b) => a -> BiDictionary a b -> Maybe b
valueOf k (Bi ks _) = D.valueOf k ks

-- | Exercise d. keyOf

keyOf :: (Ord a, Ord b) => b -> BiDictionary a b -> Maybe a
keyOf v (Bi _ vs) = D.valueOf v vs

-- | Exercise e. deleteByKey

deleteByKey :: (Ord a, Ord b) => a -> BiDictionary a b -> BiDictionary a b
deleteByKey k bd@(Bi ks vs) |D.isDefinedAt k ks = Bi (D.delete k ks) (D.delete v vs)
  | otherwise = bd
  where v = fromJust (valueOf k bd)

-- | Exercise f. deleteByValue

deleteByValue :: (Ord a, Ord b) => b -> BiDictionary a b -> BiDictionary a b
deleteByValue v bd@(Bi ks vs) = Bi (D.delete k ks) (D.delete v vs)
  where k = fromJust (keyOf v bd)

-- | Exercise g. toBiDictionary

toBiDictionary :: (Ord a, Ord b) => D.Dictionary a b -> BiDictionary a b
toBiDictionary dic | not (isOne2One dic) = error "No es inyectivo"
  | otherwise = D.foldKeysValues insert empty dic

isOne2One :: Ord b => D.Dictionary a b -> Bool
isOne2One dic = szDic == szSet
  where 
    szDic = D.size dic
    szSet = S.size (foldr (S.insert) (S.empty) (D.values dic))

-- | Exercise h. compose

compose :: (Ord a, Ord b, Ord c) => BiDictionary a b -> BiDictionary b c -> BiDictionary a c
compose d (Bi ks' _) = aux (D.keysValues ks') empty
  where
    aux [] bd = bd
    aux (x:xs) bd = aux xs (insert k v bd)
      where
        k = fromJust (keyOf (fst x) d)
        v = snd x

-- | Exercise i. isPermutation

isPermutation :: Ord a => BiDictionary a a -> Bool
isPermutation (Bi ks _) = (D.keys ks) \\ (D.values ks) == [] 



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
