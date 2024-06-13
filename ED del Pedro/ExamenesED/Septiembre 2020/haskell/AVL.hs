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
emptyBin c = B c []

remainingCapacity :: Bin -> Capacity
remainingCapacity (B c w) = c

addObject :: Weight -> Bin -> Bin
addObject w (B c wl)
  | w > c = error "weight higher than capacity"
  | otherwise = (B (c - w) (wl ++ [w]))

maxRemainingCapacity :: AVL -> Capacity
maxRemainingCapacity Empty = 0
maxRemainingCapacity (Node b h c lt rt) = c

height :: AVL -> Int
height Empty = 0
height (Node b h c lt rt) = h


nodeWithHeight :: Bin -> Int -> AVL -> AVL -> AVL
nodeWithHeight b h lt rt = Node b h maxW lt rt
  where maxW = max (remainingCapacity b) (max (maxRemainingCapacity lt) (maxRemainingCapacity rt))


node :: Bin -> AVL -> AVL -> AVL
node b lt rt = nodeWithHeight b maxH lt rt
  where maxH = 1 + (max (height lt) (height rt)) 

rotateLeft :: Bin -> AVL -> AVL -> AVL
rotateLeft b l (Node rb rh rc r1 r2) = node rb (node b l r1) r2


addNewBin :: Bin -> AVL -> AVL
addNewBin b Empty = Node b 1 (remainingCapacity b) Empty Empty
addNewBin b (Node bn h c lt Empty) = node bn lt (Node b 1 (remainingCapacity b) Empty Empty)
addNewBin b (Node bn h c Empty rt) = rotateLeft bn Empty (addNewBin b rt)
addNewBin b (Node bn h c lt rt) = node bn lt (addNewBin b rt)
 
addFirst :: Capacity -> Weight -> AVL -> AVL
addFirst c w Empty = addNewBin (B (c - w) [w]) Empty
addFirst c w t@(Node bn hn cn lt rt)
  | maxRemainingCapacity t < w = addNewBin (B (c - w) [w]) t
  | maxRemainingCapacity lt >= w = addFirst c w lt
  | remainingCapacity bn >= w = node (addObject w bn) lt rt 
  | otherwise = addFirst c w rt


addAll:: Capacity -> [Weight] -> AVL
addAll c ws = aux c ws Empty
  where
    aux c [] t = t
    aux c (w : ws) t = aux c ws (addFirst c w t)

toList :: AVL -> [Bin]
toList Empty = []
toList (Node b h c l r) = toList l ++ [b] ++ toList r 

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