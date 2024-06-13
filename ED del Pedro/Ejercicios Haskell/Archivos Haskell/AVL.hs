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
remainingCapacity (B c vs) = c

addObject :: Weight -> Bin -> Bin
addObject p (B c vs)
  | p > c = error "No cabe en el cubo"
  | p == c = B 0 (p:vs)
  | otherwise = B (c-p) (p:vs)

maxRemainingCapacity :: AVL -> Capacity
maxRemainingCapacity Empty = 0
maxRemainingCapacity (Node b h c l r) = max (remainingCapacity b) (max (maxRemainingCapacity l) (maxRemainingCapacity r)) 

height :: AVL -> Int
height Empty = 0
height (Node b h p l r) = 1 + max (height l) (height r)


 
nodeWithHeight :: Bin -> Int -> AVL -> AVL -> AVL
nodeWithHeight b h l r = Node b h (max (remainingCapacity b) (max (maxRemainingCapacity l) (maxRemainingCapacity r))) l r


node :: Bin -> AVL -> AVL -> AVL
node b l r = Node b (1 + max (height l) (height r)) (max (remainingCapacity b) (max (maxRemainingCapacity l) (maxRemainingCapacity r))) l r

rotateLeft :: Bin -> AVL -> AVL -> AVL
rotateLeft b l (Node br hr cr lr rr) = node br (node b l lr) rr

addNewBin :: Bin -> AVL -> AVL
addNewBin b Empty = Node b 1 (remainingCapacity b) Empty Empty
addNewBin b (Node b' h c l r) = if (height r - height l) > 1 then rotateLeft b' l (addNewBin b r) else node b' l (addNewBin b r)
 
addFirst :: Capacity -> Weight -> AVL -> AVL
addFirst w peso avl@Empty = addNewBin (B (w-peso) [peso]) avl
addFirst w peso avl@(Node bin alt cap lt rt)
  | peso > crm = addNewBin (B (w-peso) [peso]) avl
  | crmlt >= peso = Node bin alt cap (addFirst w peso lt) rt
  | cap >= peso = Node (addObject peso bin) alt (cap-peso) lt rt
  | otherwise = Node bin alt cap lt (addFirst w peso rt)
    where
      crm = maxRemainingCapacity avl
      crmlt = maxRemainingCapacity lt


addAll:: Capacity -> [Weight] -> AVL
addAll w pesos = addAllAux w pesos Empty
  where
    addAllAux w [] sol = sol
    addAllAux w (p:ps) sol = addAllAux w ps (addFirst w p sol)

toList :: AVL -> [Bin]
toList Empty = []
toList (Node bin alt cap lt rt) = toList lt ++ [bin] ++ toList rt

{-
	SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
 -}

data Sequence = SEmpty | SNode Bin Sequence deriving Show  

linearBinPacking:: Capacity -> [Weight] -> Sequence
linearBinPacking c weightL = undefined

seqToList:: Sequence -> [Bin]
seqToList _ = undefined

addAllFold:: [Weight] -> Capacity -> AVL 
addAllFold _ _ = undefined



{- No modificar. Do not edit -}

objects :: Bin -> [Weight]
objects (B _ os) = reverse os

  
instance Show Bin where
  show b@(B c os) = "Bin("++show c++","++show (objects b)++")"