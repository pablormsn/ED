------------------------------------------------------------
-- Estructuras de Datos
-- Tema 6. Grafos
-- Pablo López
--
-- Grafos en Haskell
------------------------------------------------------------

module DemoGrafos where

import           DataStructures.Graph.Graph
import           DataStructures.Graph.GraphBFT
import           DataStructures.Graph.GraphDFT

-- ejemplos de grafos
------------------------------------------------------------

g1 :: Graph Int
g1 = mkGraphSuc [1,2,3,4] suc
         where
          suc 1 = [2,3]
          suc 2 = [1,3]
          suc 3 = [1,2,4]
          suc 4 = [3]

g1' :: Graph Int
g1' = mkGraphEdges [1,2,3,4] [(1,2), (1,3), (2,3), (3,4)]

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

gAciclico :: Graph Int
gAciclico = mkGraphSuc [1,2] suc
   where
     suc 1 = [2]
     suc 2 = [1]

g2 :: Int -> Graph Int
g2 n = mkGraphSuc [0..n] (\ x -> map (`mod` (n+1)) [2*x+1, 2*x+2])

gInfinito :: Graph Int
gInfinito = mkGraphSuc [0..] (\ x -> [2*x+1, 2*x+2])
