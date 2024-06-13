-------------------------------------------------------------------------------
-- Ford-Fulkerson Algorithm. Maximal flow for a weighted directed graph.
--
-- Student's name:
-- Student's group:
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module DataStructures.Graph.FordFulkerson where

import Data.List  ((\\))
import DataStructures.Graph.WeightedDiGraph
import DataStructures.Graph.WeightedDiGraphBFT

maxFlowPath :: Path (WDiEdge a Integer) -> Integer
maxFlowPath ((E v w u):xs) = aux xs w
    where
        aux [E v w u] min = if w < min then w else min
        aux ((E v w u):xs) min = if w < min then aux xs w else aux xs min

updateEdge ::(Eq a) => a -> a -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
updateEdge src dst p [] = [E src p dst] -- Como no hemos encontrado dicho arco lo añadimos
updateEdge src dst p ((E v w u):xs)
    | (src,dst) == (v,u) = if w' == 0 then xs else (E v w' u):xs
    | otherwise = (E v w u):(updateEdge src dst p xs)
        where 
            w' = w + p

updateEdges :: (Eq a) => Path (WDiEdge a Integer) -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
updateEdges [] p edges = edges
updateEdges ((E v w u):xs) p edges = updateEdges xs p edges' 
    where 
        edges' = updateEdge v u p edges

addFlow :: (Eq a) => a -> a -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
addFlow x y p [] = [E x p y]
addFlow x y p ((E v w u):xs)
    | (x,y) == (v,u) = (E v (w+p) u):xs
    | (E y p x) == (E v w u) = xs
    | (y,x) == (v,u) && w < p = (E x (p-w) y):xs 
    | (y,x) == (v,u) && w > p = (E y (w-p) x):xs
    | otherwise = (E v w u):(addFlow x y p xs)

addFlows :: (Eq a) => Path (WDiEdge a Integer) -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
addFlows [] p sol = sol
addFlows ((E v w u):xs) p sol = addFlows xs p sol'
    where
        sol' = addFlow v u p sol

fordFulkerson :: (Ord a) => (WeightedDiGraph a Integer) -> a -> a -> [WDiEdge a Integer]
fordFulkerson g src dst = aux g src dst (bftPathTo g src dst) []
    where
        aux wdg src dst Nothing sol = sol
        aux wdg src dst (Just path) sol = aux newWdg src dst (bftPathTo newWdg src dst) (addFlows path mf sol)
            where
                mf = maxFlowPath path
                edges = updateEdges (reverseEdges path) mf (updateEdges path (-mf) (weightedDiEdges wdg))
                newWdg = mkWeightedDiGraphEdges (vertices wdg) edges

reverseEdges :: Path (WDiEdge a Integer) -> Path (WDiEdge a Integer)
reverseEdges [] = []
reverseEdges (x:xs) = (reverseEdges xs) ++ [(reverseEdge x)]
    where
        reverseEdge (E v w u) = E u w v

maxFlow :: (Ord a) => [WDiEdge a Integer] -> a -> Integer
maxFlow sol src = foldr (\(E v w u) res -> if v == src then w + res else res) 0 sol

maxFlowMinCut :: (Ord a) => (WeightedDiGraph a Integer) -> a -> a -> [a] -> Integer
maxFlowMinCut wdg src dst k
    | elem src k = sumaK - sumaK'
    | otherwise = sumaK' - sumaK 
        where 
            edges = fordFulkerson wdg src dst
            sumaK = sum (map (\(E v w u) -> if elem v k && elem u k' then w else 0) edges)
            sumaK' = sum (map (\(E v w u) -> if elem v k' && elem u k then w else 0) edges)
            k' = (vertices wdg) \\ k

-- A partir de aquí hasta el final
-- SOLO para alumnos a tiempo parcial 
-- sin evaluación continua

localEquilibrium :: (Ord a) => WeightedDiGraph a Integer -> a -> a -> Bool
localEquilibrium = undefined

sourcesAndSinks :: (Eq a) => WeightedDiGraph a b -> ([a],[a])
sourcesAndSinks = undefined

unifySourceAndSink :: (Eq a) => WeightedDiGraph a Integer -> a -> a -> WeightedDiGraph a Integer
unifySourceAndSink = undefined
