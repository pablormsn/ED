----------------------------------------------
-- Estructuras de Datos.  2018/19
-- 2º Curso del Grado en Ingeniería [Informática | del Software | de Computadores].
-- Escuela Técnica Superior de Ingeniería en Informática. UMA
--
-- Examen 4 de febrero de 2019
--
-- ALUMNO/NAME:
-- GRADO/STUDIES:
-- NÚM. MÁQUINA/MACHINE NUMBER:
--
----------------------------------------------

module Kruskal(kruskal, kruskals) where

import qualified DataStructures.Dictionary.AVLDictionary as D
import qualified DataStructures.PriorityQueue.LinearPriorityQueue as Q
import DataStructures.Graph.DictionaryWeightedGraph

fromJust :: Maybe (D.Dictionary a w) -> D.Dictionary a w
fromJust Nothing = D.empty
fromJust (Just d) = d

connectedVertex :: (Ord a, Eq a) => WeightedEdge a w -> D.Dictionary a a ->((Bool, a), a)
connectedVertex (WE v w u) dict = (aux v u dict, u)
    where
        aux v u dict = (getRep v dict == getRep u dict, getRep v dict)
            where
                getRep vertex dict
                    | vertex == value = vertex
                    | otherwise = getRep value dict
                        where
                            value = fromJust (D.valueOf vertex dict)


kruskal :: (Ord a, Ord w) => WeightedGraph a w -> [WeightedEdge a w]
kruskal g = aux dict pq []
    where
        pq = foldr Q.enqueue Q.empty (edges g)
        dict = foldl (\res v -> D.insert v v res) D.empty (vertices g)
        aux pq dict ws
            | Q.isEmpty = ws
            | otherwise = if condition then aux Q.dequeue dict ws else aux Q.dequeue (D.insert rep u dict) ([edge]++ws)
                where 
                    edge = Q.first pq
                    ((condition, rep),u) = connectedVertex edge dict


-- Solo para evaluación continua / only for part time students
kruskals :: (Ord a, Ord w) => WeightedGraph a w -> [[WeightedEdge a w]]
kruskals = undefined
