module BinaryTreeDemo where

import           BinaryTree
import           Data.List                         (nub)
import           DataStructures.Graphics.DrawTrees

t1 = mkBST [11,2,1,7,29,15,40,35]

demoGST = drawOn "t1-GST.svg" (greaterSumTree t1)

demoMirror = drawOn "t1-mirrored.svg" (mirroredTree t1)

demoTraversals = drawOn "from-traversals.svg" (traversals2Tree [7,5,8,6,13,4,1,15,2,9,17] [8,5,6,7,1,15,4,13,2,17,9])