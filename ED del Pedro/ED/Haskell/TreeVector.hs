-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
--
-- Data Structures. November 2022. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module TreeVector( Vector
                 , vector
                 , size
                 , get
                 , set
                 , mapVector
                 , toList
                 , fromList
                 , pretty
                 ) where

import Test.QuickCheck hiding (vector)

data Vector a = TreeVector Int (BinTree a) -- size, binary tree
                deriving Show

data BinTree a = Leaf a
               | Node (BinTree a) (BinTree a)
               deriving Show

-- | Exercise a. vector

{- |

>>> pretty $ vector 2 'a'
    _______
   /       \
   _       _
  / \     / \
'a' 'a' 'a' 'a'

>>> pretty $ vector 0 7
7

>>> pretty $ vector (-1) 5
*** Exception: vector: negative exponent
...

-}
vector :: Int -> a -> Vector a
vector n x | n >= 0 = TreeVector sz bt
  | otherwise = error " valor introducido incorrecto (< 0)" 
    where 
      sz = 2^n
      bt = mkBinTree n x

mkBinTree :: Int -> a -> BinTree a 
mkBinTree 0 x = Leaf x
mkBinTree n x = Node (mkBinTree (n-1) x) (mkBinTree (n-1) x)

-- | Exercise b. size

{- |

>>> size $ vector 3 'a'
8

>>> size $ vector 0 5
1

-}
size :: Vector a -> Int
size (TreeVector n t) = n

-- | Exercise c. get

{- |

>>> get 0 (vector 3 'a')
'a'

>>> get 5 (vector 3 'a')
'a'

>>> get 7 (vector 3 'a')
'a'

>>> get 8 (vector 3 'a')
*** Exception: get: index out of bounds
...

-}
get :: Int -> Vector a -> a -- Va a recibir un índice y un vector. Devuelve el elemento almacenado en la posición i-ésima
get i (TreeVector n t)
  | i < 0 || i >= n = error "index out of bounds"
  | otherwise = binarySearch i t

binarySearch :: Int -> BinTree a -> a
binarySearch _ (Leaf x) = x
binarySearch i (Node l r)
  | mod i 2 == 0 = binarySearch (div i 2) l
  | otherwise = binarySearch (div i 2) r

-- | Exercise d. set

{- |

>>> pretty (set 0 'b' (vector 2 'a'))
    _______
   /       \
   _       _
  / \     / \
'b' 'a' 'a' 'a'

>>> pretty (set 1 'b' (vector 2 'a'))
    _______
   /       \
   _       _
  / \     / \
'a' 'a' 'b' 'a'

>>> pretty (set 3 'b' (vector 2 'a'))
    _______
   /       \
   _       _
  / \     / \
'a' 'a' 'a' 'b'

> pretty $ foldr (\ (i, x) v -> set i x v) (vector 3 '*') (zip [0..] "function")
        _______________
       /               \
    _______         _______
   /       \       /       \
   _       _       _       _
  / \     / \     / \     / \
'f' 't' 'n' 'o' 'u' 'i' 'c' 'n'

>>> pretty (set 4 'b' (vector 2 'a'))
*** Exception: set: index out of bounds
...

-}
set :: Int -> a -> Vector a -> Vector a
set i x v@(TreeVector sz t)
  | i < 0 || i >= sz = error "index out of bounds"
  | otherwise = TreeVector sz (setOnTree i x t)

setOnTree :: Int -> a -> BinTree a -> BinTree a
setOnTree i x (Leaf y) = Leaf x
setOnTree i x (Node l r)
  | mod i 2 == 0 = Node (setOnTree (div i 2) x l) r
  | otherwise = Node l (setOnTree (div i 2) x r)

-- | Exercise e. mapVector

{- |

>>> pretty $ mapVector (*2) (vector 2 3)
  ___
 /   \
 _   _
/ \ / \
6 6 6 6

>>> pretty $ mapVector (\ x -> 2*x + 5) (foldr (\ (i, x) v -> set i x v) (vector 3 0) (zip [0..] [4, 0, 9, 2, 0, 1, 7, -3]))
     _________
    /         \
   ____      ___
  /    \    /   \
  _    _    _   _
 / \  / \  / \ / \
13 5 23 19 5 7 9 -1

-}
mapVector :: (a -> a) -> Vector a -> Vector a
mapVector f (TreeVector sz t) = TreeVector sz (mapTree f t)
  where
    mapTree f (Leaf x) = Leaf (f x)
    mapTree f (Node l r) = Node (mapTree f l) (mapTree f r)

-- | Exercise f. intercalate

{- |

>>> intercalate [0,1,2,3] [4,5,6,7]
[0,4,1,5,2,6,3,7]

>>> intercalate "haskell" "java"
"hjaasvka"

>>> intercalate [] "java"
""

-}
intercalate :: [a] -> [a] -> [a]
intercalate xs ys = intercalateAc xs ys 0
  where
    intercalateAc xs [] cnt = []
    intercalateAc [] (y:ys) cnt = [y]
    intercalateAc (x:xs) (y:ys) cnt
      | mod cnt 2 == 0 = x:(intercalateAc xs (y:ys) (cnt + 1))
      | otherwise = y:(intercalateAc (x:xs) ys (cnt + 1))

-- | Exercise g. toList

{- |

>>> toList (vector 3 'a')
"aaaaaaaa"

>>> toList $ foldr (\ (i, x) v -> set i x v) (vector 3 '*') (zip [0..] "function")
"function"

-}

toList :: Vector a -> [a]
toList (TreeVector sz t) = toListTree t
  where
    toListTree (Leaf x) = [x]
    toListTree (Node l r) = intercalate (toListTree l) (toListTree r)

-- | Exercise h. Complexity

-- Fill the table below:
--
--    operation        complexity class
--    ---------------------------------
--    vector
--    ---------------------------------
--    size
--    ---------------------------------
--    get
--    ---------------------------------
--    set
--    ---------------------------------
--    mapVector
--    ---------------------------------
--    intercalate
--    ---------------------------------
--    toList
--    ---------------------------------


-- | Exercise i. isPowerOfTwo

{- |

>>> isPowerOfTwo 16
True

>>> isPowerOfTwo 17
False

-}

isPowerOfTwo :: Int -> Bool
isPowerOfTwo 1 = True
isPowerOfTwo n | n < 0 = error "Entero negativo"
  | mod n 2 == 0 = isPowerOfTwo (div n 2)
  | otherwise = False

-- | Exercise j. fromList

{- |

>>> pretty $ fromList [0..7]
    _______
   /       \
  ___     ___
 /   \   /   \
 _   _   _   _
/ \ / \ / \ / \
0 4 2 6 1 5 3 7

>>> pretty $ fromList "property"
        _______________
       /               \
    _______         _______
   /       \       /       \
   _       _       _       _
  / \     / \     / \     / \
'p' 'e' 'o' 't' 'r' 'r' 'p' 'y'

-}

fromList :: [a] -> Vector a
fromList l@(x:xs) = if not (isPowerOfTwo sz) then error "La longitud no es potencia de 2" else insertListOnVector xs sz 1 (Leaf x)
  where
    sz = length l
    h = powerOfSize sz 0
    powerOfSize 1 cnt = cnt
    powerOfSize sz cnt = powerOfSize (div sz 2) (cnt+1)

insertListOnVector :: [a] -> Int -> Int -> BinTree a -> Vector a
insertListOnVector [] sz currentPos bt = TreeVector sz bt
insertListOnVector (x:xs) sz currentPos bt = insertListOnVector xs sz (currentPos+1) (insertOnTree x currentPos bt)
  where
    insertOnTree x i (Leaf y) = Node (Leaf y) (Leaf x)
    insertOnTree x i (Node (Leaf y) r) = Node l' r
      where 
        l' = Node (Leaf y) (Leaf x)
    insertOnTree x i (Node l (Leaf z)) = Node l r'
      where
        r' = Node (Leaf z) (Leaf x)
    insertOnTree x i (Node l r)
      | mod i 2 == 0 = Node (insertOnTree x i l) r
      | otherwise = Node l (insertOnTree x i r)

-------------------------------------------------------------------------------
-- Do not write any code below
-------------------------------------------------------------------------------

-------------------------------------------------------------------------------
-- QuickCheck Properties to check your implementation
-------------------------------------------------------------------------------

{-

> checkAll
+++ OK, passed 100 tests.
+++ OK, passed 100 tests.

> checkAll
*** Gave up! Passed only 99 tests.
+++ OK, passed 100 tests.

-}

-- This instace is used by QuickCheck to generate random arrays
instance (Arbitrary a) => Arbitrary (Vector a) where
    arbitrary  = do
      exp <- arbitrary
      v <- arbitrary
      return ((vector :: Int -> a -> Vector a) (abs exp) v)

prop_vector_OK :: Eq a => Int -> a -> Property
prop_vector_OK n v =
  n >= 0 && n < 10 ==> size a == 2^n &&
                       and [ get i a == v | i <- [0..size a-1] ]
  where a = vector n v

prop_set_OK :: Eq a => Int -> a -> Vector a -> Property
prop_set_OK i v a =
  i >= 0 && i < size a ==> get i (set i v a) == v

type T = Char

checkAll :: IO ()
checkAll =
  do
    quickCheck (prop_vector_OK :: Int -> T -> Property)
    quickCheck (prop_set_OK :: Int -> T -> Vector T -> Property)

-------------------------------------------------------------------------------
-- Pretty Printing a Vector
-- (adapted from http://stackoverflow.com/questions/1733311/pretty-print-a-tree)
-------------------------------------------------------------------------------

pretty :: (Show a) => Vector a -> IO ()
pretty (TreeVector _ t)  = putStr (unlines xss)
 where
   (xss,_,_,_) = pprint t

pprint :: Show t => BinTree t -> ([String], Int, Int, Int)
pprint (Leaf x)              = ([s], ls, 0, ls-1)
  where
    s = show x
    ls = length s
pprint (Node lt rt)         =  (resultLines, w, lw'-swl, totLW+1+swr)
  where
    nSpaces n = replicate n ' '
    nBars n = replicate n '_'
    -- compute info for string of this node's data
    s = ""
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
