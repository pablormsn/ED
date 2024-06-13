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
  , isBST
  , mkBST
  , pretty
  -- | todo
  , greaterSumTree
  , mirroredTree
  , traversals2Tree
  ) where

import           DataStructures.Graphics.DrawTrees
import           Test.QuickCheck

data BinaryTree a = Empty
                  | Node a (BinaryTree a) (BinaryTree a)
                  deriving Show

greaterSumTree :: BinaryTree Int -> BinaryTree Int
greaterSumTree bst = undefined

-- | SOLO PARA ALUMNOS A TIEMPO PARCIAL ---------------------------------------
-- | ONLY FOR PART TIME STUDENTS        ---------------------------------------

mirroredTree :: BinaryTree a -> BinaryTree a
mirroredTree = undefined

searchSplit :: Eq a => a -> [a] -> [a] -> [a] -> (([a],[a]),([a],[a]))
searchSplit x ps (y:is) l
  | x == y = ((m1,l),(m2,is))
  | otherwise = searchSplit x ps is (l ++ [y])
  where
    m1 = fst (splitAt (length l) ps)
    m2 = snd (splitAt (length l) ps)


traversals2Tree :: Eq a => [a] -> [a] -> BinaryTree a
traversals2Tree = undefined
-- | NO MODIFICAR A PARTIR DE AQUÍ --------------------------------------------
-- | DO NOT MODIFY CODE BELOW      --------------------------------------------

empty :: BinaryTree a
empty  = Empty

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

isBST :: Ord a => BinaryTree a -> Bool
isBST Empty           = True
isBST (Node x lt rt)  = forAll (<x) lt && forAll (>=x) rt
                      && isBST lt && isBST rt
  where
    forAll p Empty          = True
    forAll p (Node x lt rt) = forAll p lt && p x && forAll p rt

-------------------------------------------------------------------------------
-- Generating arbritray Binary Search Trees
-------------------------------------------------------------------------------

instance  (Ord a, Arbitrary a) => Arbitrary (BinaryTree a) where
  arbitrary  = do
    xs <- arbitrary
    return (mkBST xs)

-------------------------------------------------------------------------------
-- Drawing a BST
-------------------------------------------------------------------------------

instance Subtrees (BinaryTree a) where
  subtrees Empty          = []
  subtrees (Node x lt rt) = [lt,rt]

  isEmptyTree  = isEmpty

instance Show a => ShowNode (BinaryTree a) where
  showNode (Node x lt rt)  = show x

-------------------------------------------------------------------------------
-- Pretty Printing a BST
-- (adapted from http://stackoverflow.com/questions/1733311/pretty-print-a-tree)
-------------------------------------------------------------------------------

pretty :: Show a => BinaryTree a -> IO ()
pretty t  = putStrLn (unlines xss)
 where
   (xss,_,_,_) = pprint t

pprint Empty                 = ([], 0, 0, 0)
pprint (Node x Empty Empty)  = ([s], ls, 0, ls-1)
  where
    s = show x
    ls = length s
pprint (Node x lt rt)         =  (resultLines, w, lw'-swl, totLW+1+swr)
  where
    nSpaces n = replicate n ' '
    nBars n = replicate n '_'
    -- compute info for string of this node's data
    s = show x
    sw = length s
    swl = div sw 2
    swr = div (sw-1) 2
    (lp,lw,_,lc) = pprint lt
    (rp,rw,rc,_) = pprint rt
    -- recurse
    (lw',lb) = if lw==0 then (1," ") else (lw,"/")
    (rw',rb) = if rw==0 then (1," ") else (rw,"\\")
    -- compute full width of this tree
    totLW = maximum [lw', swl,  1]
    totRW = maximum [rw', swr, 1]
    w = totLW + 1 + totRW
{-
A suggestive example:
     dddd | d | dddd__
        / |   |       \
      lll |   |       rr
          |   |      ...
          |   | rrrrrrrrrrr
     ----       ----           swl, swr (left/right string width (of this node) before any padding)
      ---       -----------    lw, rw   (left/right width (of subtree) before any padding)
     ----                      totLW
                -----------    totRW
     ----   -   -----------    w (total width)
-}
    -- get right column info that accounts for left side
    rc2 = totLW + 1 + rc
    -- make left and right tree same height
    llp = length lp
    lrp = length rp
    lp' = if llp < lrp then lp ++ replicate (lrp - llp) "" else lp
    rp' = if lrp < llp then rp ++ replicate (llp - lrp) "" else rp
    -- widen left and right trees if necessary (in case parent node is wider, and also to fix the 'added height')
    lp'' = map (\s -> if length s < totLW then nSpaces (totLW - length s) ++ s else s) lp'
    rp'' = map (\s -> if length s < totRW then s ++ nSpaces (totRW - length s) else s) rp'
    -- first part of line1
    line1 = if swl < lw' - lc - 1 then
                nSpaces (lc + 1) ++ nBars (lw' - lc - swl) ++ s
            else
                nSpaces (totLW - swl) ++ s
    -- line1 right bars
    lline1 = length line1
    line1' = if rc2 > lline1 then
                line1 ++ nBars (rc2 - lline1)
             else
                line1
    -- line1 right padding
    line1'' = line1' ++ nSpaces (w - length line1')
    -- first part of line2
    line2 = nSpaces (totLW - lw' + lc) ++ lb
    -- pad rest of left half
    line2' = line2 ++ nSpaces (totLW - length line2)
    -- add right content
    line2'' = line2' ++ " " ++ nSpaces rc ++ rb
    -- add right padding
    line2''' = line2'' ++ nSpaces (w - length line2'')
    resultLines = line1'' : line2''' : zipWith (\lt rt -> lt ++ " " ++ rt) lp'' rp''
