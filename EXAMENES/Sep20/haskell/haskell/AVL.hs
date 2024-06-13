{------------------------------------------------------------------------------
 - Student's name:
 -
 - Student's group:
 -----------------------------------------------------------------------------}

module AVL 
  ( 
    Weight
  , Capacity
  , AVL (..)
  , Bin
  , emptyBin
  , remainingCapacity
  , addObject
  , maxRemainingCapacity
  , height
  , nodeWithHeight
  , node
  , rotateLeft
  , addNewBin
  , addFirst
  , addAll
  , toList
  , linearBinPacking
  , seqToList
  , addAllFold
  ) where

type Capacity = Int
type Weight= Int

data Bin = B Capacity [Weight] 

data AVL = Empty | Node Bin Int Capacity AVL AVL deriving Show


emptyBin :: Capacity -> Bin
emptyBin c = (B c [])

remainingCapacity :: Bin -> Capacity
remainingCapacity (B c w) = c

addObject :: Weight -> Bin -> Bin
addObject w (B c ws)
  |w>c = error "El objeto no cabe en el cubo"
  |otherwise = (B (c-w) (w:ws))

maxRemainingCapacity :: AVL -> Capacity
maxRemainingCapacity Empty = 0
maxRemainingCapacity (Node b h c lh rh) = c

height :: AVL -> Int
height Empty = 0
height (Node b h c lh rh) = h
 
nodeWithHeight :: Bin -> Int -> AVL -> AVL -> AVL
nodeWithHeight bin@(B c w) h Empty Empty = (Node bin h c Empty Empty)
nodeWithHeight bin@(B c w) h lh@(Node _ _ cl lhl lhr) rh@(Node _ _ cr rhl rhr)
  |c>cl && c>cr = (Node bin h c lh rh)
  |cl>c && cl>cr = (Node bin h cl lh rh)
  |otherwise = (Node bin h cr lh rh)

node :: Bin -> AVL -> AVL -> AVL
node bin@(B c w) lh@(Node _ hl cl lhl lhr) rh@(Node _ hr cr rhl rhr) = nodeWithHeight bin hmax lh rh
  where hmax = 1 + (max hl hr)

rotateLeft :: Bin -> AVL -> AVL -> AVL
rotateLeft bin lh@(Node bl hl cl lhl lhr) rh@(Node br hr cr rhl rhr) = (node br (node bin lh rhl) rhr)

addNewBin :: Bin -> AVL -> AVL
addNewBin bin Empty = (Node bin 1 (remainingCapacity bin) Empty Empty)
addNewBin bin (Node b h c lh rh)
  |(height rh-height lh) > 1 = rotateLeft b lh (addNewBin bin rh)
  |otherwise = node b lh (addNewBin bin rh)
 
addFirst :: Capacity -> Weight -> AVL -> AVL
addFirst c w Empty = (addNewBin (B (c-w) [w]) Empty)
addFirst c w avl@(Node bin h cap lh rh)
  |(maxRemainingCapacity lh) >= w = (Node bin h cap (addFirst c w lh) rh)
  |(remainingCapacity bin) >= w = node (addObject w bin) lh rh
  |(maxRemainingCapacity avl) < w = (addNewBin (B (c-w) [w]) avl)
  |otherwise = (Node bin h cap lh (addFirst c w rh))

addAll:: Capacity -> [Weight] -> AVL
addAll c ws = aux c ws Empty
  where
    aux c [] avl = avl
    aux c (x:xs) avl = aux c xs (addFirst c x avl)

toList :: AVL -> [Bin]
toList Empty = []
toList (Node bin h c lh rh) = (toList lh) ++ [bin] ++ (toList rh)

{-
	SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
 -}

data Sequence = SEmpty | SNode Bin Sequence deriving Show  

linearBinPacking:: Capacity -> [Weight] -> Sequence
linearBinPacking _ _ = undefined

seqToList:: Sequence -> [Bin]
seqToList _ = undefined

addAllFold:: [Weight] -> Capacity -> AVL 
addAllFold _ _ = undefined



{- No modificar. Do not edit -}

objects :: Bin -> [Weight]
objects (B _ os) = reverse os

  
instance Show Bin where
  show b@(B c os) = "Bin("++show c++","++show (objects b)++")"