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
isEulerian g = aux (vertices g)
    where 
        aux [] = True
        aux (v:vs) = mod (degree g v) 2 == 0 && aux vs

-- H.2)
remove :: (Eq a) => Graph a -> (a,a) -> Graph a
remove g (v,u) = aux (deleteEdge g (v,u)) (vertices g)
    where
        aux g' [] = g'
        aux g' (v:vs) = if degree g' v == 0 then aux (deleteVertex g' v) vs else aux g' vs

-- H.3)

modify :: Eq a => Graph a -> Path a -> Graph a
modify g (x:y:xs) = if edges g /= [] then modify (remove g (x,y)) (y:xs) else mkGraphEdges (vertices g) []
modify empty _ = empty

searchCycle :: Eq a => Graph a -> a -> a -> [a] -> [a] -> Path a
searchCycle g' v v0 (suc:resSucs) sol = if suc /= v0 then sol ++ [suc] ++ searchCycle g' suc v0 (successors g' suc) sol else sol ++ [v0]

extractCycle :: (Eq a) => Graph a -> a -> (Graph a, Path a)
extractCycle g v0 = aux g v0 (successors g v0) [v0]
    where
        aux g v [] c = (g, c)
        aux g v (u : us) c = aux newG u (successors newG u) (c ++ [u])
            where 
                newG = remove g (v, u)

-- H.4)
connectCycles :: (Eq a) => Path a -> Path a -> Path a
connectCycles [] ys' = ys'
connectCycles xs (y:ys) = if head xs == y then (y:ys) ++ tail xs else head xs:connectCycles (tail xs) (y:ys)

-- H.5)
vertexInCommon :: Eq a => Graph a -> Path a -> a
vertexInCommon g cycle = aux (vertices g) cycle
    where 
        aux (v:vs) cycle = if v `elem` cycle then v else aux vs cycle

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
