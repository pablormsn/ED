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
isConnected :: Ord a => Graph a -> Bool
isConnected g = undefined

-- |
-- >>> connectedComponents gConexo
-- ["ACGEDFHB"]
-- >>> connectedComponents gNoConexo
-- ["ABFD","CGEH"]
connectedComponents :: Ord a => Graph a -> [[a]]
connectedComponents g = undefined
