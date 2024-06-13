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
isEulerian g
    | length vs < 2 = error "Este grafo no es conexo"
    | otherwise = foldr (\x xs -> (mod (degree g x) 2 == 0) && xs) True vs 
        where vs = vertices g

-- H.2)
-- remove :: (Eq a) => Graph a -> (a,a) -> Graph a
-- remove g (v,u) = check g2 (vertices g)
--     where 
--         g2 = deleteEdge g (v,u)
--         check g [] = g
--         check g (v:vs) = if (degree g v) == 0 then check (deleteVertex g v) vs else check g vs

remove :: (Eq a) => Graph a -> (a,a) -> Graph a
remove g (v,u) = check g2 (v,u)
    where
        g2 = deleteEdge g (v,u)
        check g' (v,u)
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
                    g' = remove g (v, successor)

-- H.4)
connectCycles :: (Eq a) => Path a -> Path a -> Path a
connectCycles [] ys = ys
connectCycles (x:xs) (y:ys) 
    | x == y = (y:ys) ++ xs
    | otherwise = [x]++(connectCycles xs (y:ys))
    --otherwise = x:(connectCycles xs (y:ys))

-- H.5)
vertexInCommon :: Eq a => Graph a -> Path a -> a
vertexInCommon g [x] = x
vertexInCommon g (x:xs) = if elem x vs then x else vertexInCommon g xs
    where vs = vertices g

-- H.6) 
eulerianCycle :: Eq a => Graph a -> Path a
eulerianCycle g = if not (isEulerian g) then error "El grafo no es euleriano" else getEulerianCycle g' p'
    where 
        (v:vs) = vertices g
        (g', p') = extractCycle g v

getEulerianCycle :: Eq a => Graph a -> Path a -> Path a
getEulerianCycle g p
    | isEmpty g = p
    | otherwise = getEulerianCycle g' (connectCycles p p')
        where (g', p') = extractCycle g (vertexInCommon g p)
    
