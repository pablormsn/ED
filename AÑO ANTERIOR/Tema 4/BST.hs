------------------------------------------------------------
-- Estructuras de Datos
-- Tema 4. Árboles
-- Pablo López
--
-- Árboles de bísqueda en Haskell
------------------------------------------------------------

module BST where

import           Data.Maybe                        (isJust)
import           DataStructures.Graphics.DrawTrees
import           Test.QuickCheck
import GHC.CmmToAsm (x86NcgImpl)

-- árboles binarios de búsqueda en Haskell
data BST a = Empty
           | Node a (BST a) (BST a) deriving Show
            --  root left   right

{-
   Aunque usamos deriving Show, no es muy útil; estas instancias son
   para pintar árboles binarios en formato gráfico. No es necesario
   saber crear estas instancias, se facilitan cuando sea necesario.
-}

instance Subtrees (BST a) where
    isEmptyTree Empty = True
    isEmptyTree _     = False

    subtrees Empty        = []
    subtrees (Node _ i d) = [i,d]

instance Show a => ShowNode (BST a) where
    showNode Empty        = ""
    showNode (Node r _ _) = show r

mkBST :: Ord a => [a] -> BST a
mkBST xs = foldr insert empty xs

tree1 :: BST Int
tree1 = mkBST [7, 4, 10, 17, 19, 18, 27, 45, 90, 55, 15, 20, 25, 40, 12,  68, 73, 75, 60, 70, 80,50]

inOrder :: BST a -> [a]
inOrder Empty        = []
inOrder (Node x i d) = inOrder i ++ x : inOrder d

treeSort :: Ord a => [a] -> [a]
treeSort xs = (inOrder . mkBST) xs

empty :: BST a
empty = Empty

insert :: Ord a => a -> BST a -> BST a
insert x Empty = Node x Empty Empty
insert x (Node y l r)
    | x == y = Node x l r -- actualizar
    | x < y = Node y (insert x l) r
    | otherwise = Node y l(insert x r)

search :: Ord a => a -> BST a -> Maybe a
search _ Empty = Nothing 
search x (Node y i d)
    | x == y = Just y 
    | x < y = search x i
    | otherwise = search x d

isElem :: Ord a => a -> BST a -> Bool
isElem x t = isJust(search x t)

delete :: Ord a => a -> BST a -> BST a
delete _ Empty = Empty
delete x (Node y i d)
    | x < y = Node y (delete x i) d
    | x > y = Node y i (delete x d)
    | otherwise = deleteRoot (Node y i d)

deleteRoot :: Ord a => BST a -> BST a
deleteRoot (Node _ i Empty)     = i
deleteRoot (Node _ Empty r)     = r
deleteRoot (Node _ i d) = Node minimo i (delete minimo d)
    where minimo = minElem d 

minElem :: BST a -> a
minElem (Node x Empty _) = x 
minElem (Node _ i d)     = minElem i
