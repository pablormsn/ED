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
createRankDict g = aux (vertices g) D.empty
    where
        aux [] d = d
        aux (v:vs) d = aux vs (D.insert v 0.0 d)

distribute :: Ord a => DiGraph a -> a -> Double -> D.Dictionary a Double -> D.Dictionary a Double
distribute g v r d = if r <= threshold then d else updateValues g v r' (successors g v) (D.insert v (fromJust (D.valueOf v d) + r') d) -- necesito conocer los sucesores del vértice
    where 
        r' = r / fromIntegral 2

updateValues :: Ord a => DiGraph a -> a -> Double -> [a] -> D.Dictionary a Double -> D.Dictionary a Double
updateValues g v r sucs d = if not (null sucs) then foldl (\ x res -> distribute g res partesIguales x) d sucs else d
    where 
        partesIguales = r / fromIntegral (length sucs)

edRank :: Ord a => DiGraph a -> Double -> D.Dictionary a Double
edRank g r = aux g (vertices g) r (createRankDict g)
    where
        aux g [] r d = d
        aux g (v:vs) r d = aux g vs r (distribute g v r d)