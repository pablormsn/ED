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
        aux [] dic = dic
        aux (x:xs) dic = aux xs (D.insert x 0 dic)

distribute :: Ord a => DiGraph a -> a -> Double -> D.Dictionary a Double -> D.Dictionary a Double
distribute g v rank dic = aux g v sucs rank' dic'
    where
        sucs = successors g v
        rank' = (rank / fromIntegral 2) / fromIntegral (length sucs)
        dic' = D.insert v ((fromJust (D.valueOf v dic)) + (rank / fromIntegral 2)) dic
        aux g v [] rank dic = dic
        aux g v (s:ss) rank dic
            | rank < threshold = dic
            | otherwise = aux g v ss rank dic'
                where
                    dic' = distribute g s rank dic

edRank :: Ord a => DiGraph a -> Double -> D.Dictionary a Double
edRank g rank = foldr (\ v res -> distribute g v rank res) (createRankDict g) (vertices g)
