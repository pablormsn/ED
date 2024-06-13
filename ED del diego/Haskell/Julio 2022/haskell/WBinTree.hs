-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
-- Identity number (DNI if Spanish/passport if Erasmus):
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module WBinTree( WBinTree
               , empty
               , insert
               , isWeightBalanced
               , mkWBinTree
               ) where

data WBinTree a = Empty
                | Node Int a (WBinTree a) (WBinTree a)
                deriving Show

-- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
-- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
-- EXERCISE 4

isWeightBalanced :: WBinTree a -> Bool
isWeightBalanced Empty = True
isWeightBalanced (Node _ _ Empty Empty) = True
isWeightBalanced (Node _ _ lh Empty) = True
isWeightBalanced (Node _ _ Empty rh) = True
isWeightBalanced (Node _ _ (Node wL _ ll rl) (Node wR _ lr rr)) = wL - wR < 1

insert :: a -> WBinTree a -> WBinTree a
insert x Empty = Node 1 x Empty Empty
insert x (Node w y Empty rh) = Node (w+1) y (insert x Empty) rh
insert x (Node w y lh Empty) = Node (w+1) y lh (insert x Empty)
insert x wb@(Node w y lh rh)
    | isWeightBalanced wb = Node (w+1) y (insert x lh) rh
    | otherwise = Node (w+1) y lh (insert x rh)



-- | NO MODIFICAR A PARTIR DE AQUÍ --------------------------------------------
-- | DO NOT MODIFY CODE BELOW      --------------------------------------------

empty :: WBinTree a
empty = Empty

mkWBinTree :: [a] -> WBinTree a
mkWBinTree = foldr insert empty
