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

fromJust :: Maybe a -> a
fromJust Nothing = error "No tiene valor asociado"
fromJust (Just x) = x

connectedVertex :: (Ord a, Eq a) => WeightedEdge a w -> D.Dictionary a a -> ((Bool, a),a)
connectedVertex (WE v w u) dic = (aux v u dic,u)
    where 
        aux v u dic = (getRep v dic == getRep u dic, getRep v dic)
            where 
                getRep vertex dic
                    | vertex == value = vertex
                    | otherwise = getRep value dic
                        where
                            value = fromJust (D.valueOf vertex dic)

kruskal :: (Ord a, Ord w) => WeightedGraph a w -> [WeightedEdge a w]
kruskal g = aux pq dict []
    where 
        pq = foldr (Q.enqueue) (Q.empty) (edges g)
        dict = foldl (\res v -> D.insert v v res) D.empty (vertices g)
        aux pq dict ws
            | Q.isEmpty pq = ws
            | otherwise = if condition then aux pq' dict ws else aux pq' (D.insert rep u dict) ([edge] ++ ws)
                where
                    edge = Q.first pq
                    pq' = Q.dequeue pq
                    ((condition,rep), u) = connectedVertex edge dict
            -- Si están conectados no se hace nada, sino actualizamos dict y lo añadimos ws

-- Solo para los que no tienen evaluación continua / only for part time students
kruskals :: (Ord a, Ord w) => WeightedGraph a w -> [[WeightedEdge a w]]
kruskals = undefined
