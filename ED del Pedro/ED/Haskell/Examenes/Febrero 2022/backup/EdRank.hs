-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
-- Identity number (DNI if Spanish/passport if Erasmus):
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module EdRank where

import DataStructures.Graph.DiGraph
import DataStructures.Dictionary.AVLDictionary as D
import Data.Maybe(fromJust)

threshold = 0.0001
createRankDict :: (Ord a) => DiGraph a -> D.Dictionary a Double
createRankDict = undefined

distribute :: Ord a => DiGraph a -> a -> Double -> D.Dictionary a Double -> D.Dictionary a Double
distribute = undefined

updateValues :: Ord a => DiGraph a -> a -> Double -> [a] -> D.Dictionary a Double -> D.Dictionary a Double
updateValues = undefined

edRank :: Ord a => DiGraph a -> Double -> D.Dictionary a Double
edRank = undefined
