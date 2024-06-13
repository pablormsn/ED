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
isEulerian :: Eq a => Graph a -> Bool
isEulerian g = if null (vertices g) then False else aux (vertices g)
    where
        aux [] = True
        aux (h : hs) = mod (degree g h) 2 == 0 && aux hs

-- H.2)
remove :: (Eq a) => Graph a -> (a,a) -> Graph a
remove g (v,u) = aux [v,u] (deleteEdge g (v,u))
    where
        aux [] g = g
        aux (h : hs) g = if degree g h == 0 then aux hs (deleteVertex g h) else aux hs g

-- H.3)
extractCycle :: (Eq a) => Graph a -> a -> (Graph a, Path a)
extractCycle g v0 = aux g v0 (successors g v0) [v0]
    where
        aux g v [] c = (g, c)
        aux g v (u : us) c = aux newG u (successors newG u) (c ++ [u])
            where 
                newG = remove g (v, u)

-- H.4)
connectCycles :: (Eq a) => Path a -> Path a -> Path a
connectCycles [] ys = ys
connectCycles (x : xs) (y : ys)
    | x == y = (y : ys) ++ xs
    | otherwise = x : (connectCycles xs (y : ys))

-- H.5)
vertexInCommon :: Eq a => Graph a -> Path a -> a
vertexInCommon g cycle = aux (vertices g) cycle
    where
        aux (v : vs) xs
            | elem v xs = v
            | otherwise = aux vs xs

-- H.6) 
eulerianCycle :: Eq a => Graph a -> Path a
eulerianCycle g = if isEulerian g then aux (extractCycle g (head (vertices g))) else error "The graph is not eulerian"
    where
        aux (g, c)
            | isEmpty g = c
            | otherwise = aux (newG, (connectCycles c newC))
            where
                newG = fst (extractCycle g (vertexInCommon g c))
                newC = snd (extractCycle g (vertexInCommon g c))
