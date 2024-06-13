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
isEulerian g = auxIsEulerian g (vertices g)
    where 
        auxIsEulerian g [x] = (mod (degree g x) 2) == 0
        auxIsEulerian g (x:xs) = (mod (degree g x) 2) == 0 && auxIsEulerian g xs

-- H.2)
remove :: (Eq a) => Graph a -> (a,a) -> Graph a
remove g (v,u) = checkVertex (deleteEdge g (v,u)) (v,u)
    where
        checkVertex g' (v,u)
            | (degree g' v) == 0 && (degree g' u) == 0 = deleteVertex (deleteVertex g' v) u
            | (degree g' v) == 0 = deleteVertex g' v 
            | (degree g' u) == 0 = deleteVertex g' u 
            | otherwise = g'

-- H.3)
extractCycle :: (Eq a) => Graph a -> a -> (Graph a, Path a)
extractCycle g v0 = aux g v0 [v0]
    where
        aux g v p
            | (successors g v) == [] = (g,p)
            | otherwise = aux g' successor (p ++ [successor]) 
            where
                successor = head (successors g v)
                g' = remove g (v,successor)
-- H.4)
connectCycles :: (Eq a) => Path a -> Path a -> Path a
connectCycles [] p = p
connectCycles (x:xs) ys
    | x == head ys = ys ++ xs 
    | otherwise = x:(connectCycles xs ys)

-- H.5)
vertexInCommon :: Eq a => Graph a -> Path a -> a
vertexInCommon g [x] = x
vertexInCommon g (x:xs) = if elem x (vertices g) then x else vertexInCommon g xs

-- H.6) 
eulerianCycle :: Eq a => Graph a -> Path a
eulerianCycle g = if not (isEulerian g) then error "Este grafo no es euleriano" else getEulerianCycle g' p 
    where
        (g',p) = extractCycle g (head (vertices g))

getEulerianCycle :: Eq a => Graph a -> Path a -> Path a
getEulerianCycle g p
    | isEmpty g = p
    | otherwise = getEulerianCycle g' (connectCycles p p')
    where 
        (g',p') = extractCycle g (vertexInCommon g p)
