-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module DataStructures.Graph.EulerianCycle(isEulerian, eulerianCycle) where

import DataStructures.Graph.Graph
import Data.List

--H.1)
-- Un grafo es euleriano cuando tiene un ciclo de Euler. Para que tenga un circuito de Euler todos los vértices deben ser de grado par
isEulerian :: Eq a => Graph a -> Bool
isEulerian g = aux g (vertices g)
    where
        aux g [] = True
        aux g vs@(x:xs) 
            | length vs == 2 && d == 1 = False
            | otherwise = mod d 2 == 0 && aux g xs
                where
                    d = degree g x

-- H.2)
remove :: (Eq a) => Graph a -> (a,a) -> Graph a
{-
remove g (v,u) = aux [v,u] (deleteEdge g (v,u))
    where
        aux [] g = g
        aux (h:hs) g = if degree g h == 0 then aux hs (deleteVertex g h) else aux hs g
-}
remove g (v,u) = aux (deleteEdge g (v,u)) [v,u]
    where 
        aux newg [] = newg
        aux newg (x:xs)
            | (degree newg x) == 0 = aux (deleteVertex newg x) xs
            | otherwise = aux newg xs


-- H.3)
extractCycle :: (Eq a) => Graph a -> a -> (Graph a, Path a)
extractCycle g v0 = aux g v0 [v0] (successors g v0)
    where 
        aux g v list [] = (g, list)
        aux g v list (x:xs) = aux g' x (list++[x]) (successors g' x)
                where
                    g' = (remove g (v,x))
{-
extractCycle g v0 = aux g v0 (successors g v0) [v0]
    where
        aux g v [] cycle = (g,cycle)
        aux g v (x:xs) cycle = aux g' x (successors g' x) (cycle ++ [x])
                where
                    g' = remove g (v,x)
-}

-- H.4)
connectCycles :: (Eq a) => Path a -> Path a -> Path a
connectCycles [] ys = ys
connectCycles (x:xs) c@(y:ys)
    | x == y = c ++ xs
    | otherwise = x:(connectCycles xs c)

-- H.5)
vertexInCommon :: Eq a => Graph a -> Path a -> a
vertexInCommon g cycle = aux (vertices g) cycle
    where
        aux vs (x:xs) = if elem x vs then x else aux vs xs 

-- H.6) 
eulerianCycle :: Eq a => Graph a -> Path a
eulerianCycle g 
    | isEulerian g = aux (fst (extractCycle g x)) (snd (extractCycle g x))
    | otherwise = error "Not Eulerian"
    where 
        x = head (vertices g)
        aux g cycle 
            | isEmpty g = cycle
            | otherwise = aux (fst (extractCycle g y)) (connectCycles cycle (snd (extractCycle g y)))
                where
                    y = vertexInCommon g cycle
{-eulerianCycle g
    | not (isEulerian g) = error "No es euleriano"
    | otherwise = auxEulerian g' pth 
        where
            (g',pth) = extractCycle g (head (vertices g))
            auxEulerian g0 ys
                | isEmpty g0 = ys
                | otherwise = auxEulerian g1 (connectCycles ys xs)
                    where
                        vs = vertices g0
                        (g1,xs) = extractCycle g0 (vertexInCommon g0 ys)-}
