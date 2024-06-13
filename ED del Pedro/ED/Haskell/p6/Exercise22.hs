-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Práctica 6 - Reconstrucción de un árbol binario a partir de sus recorridos
--
-- Alumno: APELLIDOS, NOMBRE
-------------------------------------------------------------------------------

module Exercise22 where

import           DataStructures.Graphics.DrawTrees
import           Test.QuickCheck

data TreeB a = EmptyB
             | NodeB a (TreeB a) (TreeB a) deriving Show
               --  root  left     right

{-
   Aunque usamos deriving Show, no es muy útil; estas instancias son
   para pintar árboles binarios en formato gráfico. No es necesario
   saber crear estas instancias, se facilitan cuando sea necesario.
-}

instance Subtrees (TreeB a) where
    isEmptyTree EmptyB = True
    isEmptyTree _      = False

    subtrees EmptyB        = []
    subtrees (NodeB _ i d) = [i,d]

instance Show a => ShowNode (TreeB a) where
    showNode EmptyB        = ""
    showNode (NodeB r _ _) = show r

-- |
-- >>> findTreeB [2,1,3,4,6] [3,1,4,2,6]
-- NodeB 2 (NodeB 1 (NodeB 3 EmptyB EmptyB) (NodeB 4 EmptyB EmptyB)) (NodeB 6 EmptyB EmptyB)
findTreeB :: Ord a => [a] -> [a] -> TreeB a
findTreeB = undefined
