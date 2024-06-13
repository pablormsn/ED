------------------------------------------------------------
-- Estructuras de Datos
-- Tema 6. Grafos
-- Pablo López
--
-- Grafos en Haskell
------------------------------------------------------------

module ConnectedComponents where

import           DataStructures.Graph.Graph
import           DataStructures.Graph.GraphDFT

-- grafo conexo

gConexo :: Graph Char
gConexo = mkGraphSuc "ABCDEFGH" suc
   where
      suc 'A' = "CDH"
      suc 'B' = "H"
      suc 'C' = "AG"
      suc 'D' = "AEF"
      suc 'E' = "DFGH"
      suc 'F' = "DE"
      suc 'G' = "CE"
      suc 'H' = "ABE"

-- grafo no conexo

gNoConexo :: Graph Char
gNoConexo = mkGraphSuc "ABCDEFGH" suc
   where
      suc 'A' = "BDF"
      suc 'B' = "AF"
      suc 'C' = "G"
      suc 'D' = "AF"
      suc 'E' = "GH"
      suc 'F' = "ABD"
      suc 'G' = "CEH"
      suc 'H' = "EG"

-- |
-- >>> isConnected gConexo
-- True
-- >>> isConnected gNoConexo
-- False

search :: Eq a => a -> [[a]] -> Bool
search vertice [] = False
search vertice (x:xs) = if vertice == (last x) then True else search vertice xs

isConnected :: Ord a => Graph a -> Bool
isConnected g = auxIsConnected (vertices g) (dftPaths g (head (vertices g))) -- Cogeré los vétices y las aristas
   where 
      auxIsConnected [] ps = True
      auxIsConnected (v:vs) ps = (search v ps) && (auxIsConnected vs ps)



-- |
-- >>> connectedComponents gConexo
-- ["ACGEDFHB"]
-- >>> connectedComponents gNoConexo
-- ["ABFD","CGEH"]
connectedComponents :: Ord a => Graph a -> [[a]]
connectedComponents g = if isConnected g then [dft g (head (vertices g))] else getSubGraphs (vertices g) [] 
   where
      getSubGraphs [] visited = []
      getSubGraphs (v:vs) visited
         | not (elem v visited) = subG:(getSubGraphs vs visited')
         | otherwise = getSubGraphs vs visited
         where 
            subG = (dft g v)
            visited' = foldr (:) visited subG
