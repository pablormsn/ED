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
maxFlowPath hs = minimum (map getWeight hs)

getWeight :: WDiEdge a Integer -> Integer
getWeight (E x w y) = w

updateEdge ::(Eq a) => a -> a -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
updateEdge x y w [] = [E x w y]
updateEdge x y w (h : hs)
    | (x, y) == getOrigDest h = (updateWeight w h) ++ hs
    | otherwise = h : (updateEdge x y w hs)

getOrigDest :: WDiEdge a Integer -> (a, a)
getOrigDest (E x w y) = (x, y)

updateWeight :: Integer -> WDiEdge a Integer -> [WDiEdge a Integer]
updateWeight w (E x w' y)
    | w + w' == 0 = []
    | otherwise = [E x (w + w') y]

updateEdges :: (Eq a) => Path (WDiEdge a Integer) -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
updateEdges [] _ edges = edges
updateEdges (p : ps) w edges = updateEdges ps w (updateEdge x y w edges)
    where
        (x, y) = getOrigDest p

addFlow :: (Eq a) => a -> a -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
addFlow x y p [] = [E x p y]
addFlow x y p (s : ss)
    | (x, y) == getOrigDest s = (E x (p + w) y) : ss
    | (y, x) == getOrigDest s && p == w = ss
    | (y, x) == getOrigDest s = if w < p then (E x (p - w) y) : ss else (E y (w - p) x) : ss
    | otherwise = s : (addFlow x y p ss)
        where w = getWeight s

addFlows :: (Eq a) => Path (WDiEdge a Integer) -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
addFlows [] _ sol = sol
addFlows (p : ps) w sol = addFlows ps w (addFlow x y w sol)
    where
        (x, y) = getOrigDest p

fordFulkerson :: (Ord a) => (WeightedDiGraph a Integer) -> a -> a -> [WDiEdge a Integer]
fordFulkerson g src dst = aux g src dst [] (bftPathTo g src dst)
    where
        aux _ _ _ sol Nothing = sol
        aux wdg src dst sol (Just path) =  aux newWdg src dst (addFlows path mf sol) (bftPathTo newWdg src dst)
            where
                mf = maxFlowPath path
                reverseEdges = reversePath path
                edges = (updateEdges reverseEdges mf (updateEdges path (-mf) (weightedDiEdges wdg)))
                newWdg = mkWeightedDiGraphEdges (vertices wdg) edges

reversePath :: Path (WDiEdge a Integer) -> Path (WDiEdge a Integer)
reversePath [] = []
reversePath (e : es) = reversePath es ++ [reverseEdge e]

reverseEdge :: WDiEdge a Integer -> WDiEdge a Integer
reverseEdge (E x w y) = E y w x

--reversePath [E 'a' 5 'b', E 'b' 2 'c', E 'c' 3 'd']

maxFlow :: (Ord a) => [WDiEdge a Integer] -> a -> Integer
maxFlow [] src = 0
maxFlow (s : ss) src
    | x == src = w + maxFlow ss src
    | otherwise = maxFlow ss src
        where 
            (x, y) = getOrigDest s
            w = getWeight s

maxFlowMinCut :: (Ord a) => (WeightedDiGraph a Integer) -> a -> a -> [a] -> Integer
maxFlowMinCut g src dst set
    | elem src set = sumaKK' - sumaK'K
    | otherwise = sumaK'K - sumaKK'
        where
            edges = fordFulkerson g src dst
            sumaKK' = sum (map (\ (E x w y) -> if elem x set && elem y dif then w else 0) edges)
            sumaK'K = sum (map (\ (E x w y) -> if elem y set && elem x dif then w else 0) edges)
            dif = (vertices g) \\ set



-- A partir de aquí hasta el final
-- SOLO para alumnos a tiempo parcial 
-- sin evaluación continua

localEquilibrium :: (Ord a) => WeightedDiGraph a Integer -> a -> a -> Bool
localEquilibrium = undefined

sourcesAndSinks :: (Eq a) => WeightedDiGraph a b -> ([a],[a])
sourcesAndSinks = undefined

unifySourceAndSink :: (Eq a) => WeightedDiGraph a Integer -> a -> a -> WeightedDiGraph a Integer
unifySourceAndSink = undefined
