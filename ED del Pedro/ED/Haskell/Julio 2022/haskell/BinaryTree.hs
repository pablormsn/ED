-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
-- Identity number (DNI if Spanish/passport if Erasmus):
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module BinaryTree
  ( BinaryTree
  , empty
  , isEmpty
  , insert
  , mkBST
  -- | todo
  , subTreesInRange -- EXERCISE 2
  ) where

data BinaryTree a = Empty
                  | Node a (BinaryTree a) (BinaryTree a)
                  deriving Show


-- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
-- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
-- EXERCISE 2

nSubTrees :: Ord a => BinaryTree a -> a -> a -> (Integer,Bool)
nSubTrees Empty min max = (0,True)
nSubTrees (Node x Empty Empty) min max = if x >= min && x <= max then (1,True) else (0,False)
nSubTrees (Node x lh rh) min max = if inRangeL && inRangeR then (1 + lN + rN, True) else (lN + rN, False)
  where
      (lN,inRangeL) = nSubTrees lh min max
      (rN,inRangeR) = nSubTrees rh min max

subTreesInRange :: Ord a => BinaryTree a -> a -> a -> Integer
subTreesInRange (Node x lh rh) min max
  | x > max = subTreesInRange lh min max
  | x < min = subTreesInRange rh min max
  | otherwise = if inRangeL && inRangeR then 1 + nL + nR else nL + nR
    where
      (nL,inRangeL) = nSubTrees lh min max
      (nR,inRangeR) = nSubTrees rh min max


-- | NO MODIFICAR A PARTIR DE AQUÍ --------------------------------------------
-- | DO NOT MODIFY CODE BELOW      --------------------------------------------

empty :: BinaryTree a
empty = Empty

isEmpty :: BinaryTree a -> Bool
isEmpty Empty = True
isEmpty _     = False

insert :: Ord a => a -> BinaryTree a -> BinaryTree a
insert x' Empty  =  Node x' Empty Empty
insert x' (Node x lt rt)
    | x'<x       = Node x (insert x' lt) rt
    | x'>x       = Node x lt (insert x' rt)
    | otherwise  = Node x' lt rt

mkBST :: Ord a => [a] -> BinaryTree a
mkBST xs  = foldl (flip insert) empty xs
