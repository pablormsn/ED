module Demos.Graph.EdRankDemo where

import EdRank
import DataStructures.Graph.DiGraph
import DataStructures.Dictionary.AVLDictionary

-- | functions to check results

{-
  DiGraphs g3, g5, g6, and g10 createRankDict
-}

main :: IO ()
main = checkThemAll

checkThemAll :: IO ()
checkThemAll =
    putStr $ unlines [ "Tests for digraph " ++ name ++ ":\n" ++                  
                        show (edRank graph 1) | (name, graph) <- graphs]
    where
      graphs = [("g3", g3), ("g4",g4), ("g5", g5), ("g6", g6), ("g7",g7),("g10", g10)]

---------------------
--- EXAMPLES --------
---------------------

data Vertex = A | B | C | D | E | F | G | H | I | J deriving (Show,Eq,Enum,Ord)

g3 :: DiGraph Vertex 
g3 = mkDiGraphEdges [A .. C]
                  [(A:->B), (B:->C), (C:->A)]

g4 = mkDiGraphSuc [A .. H] suc
    where
        suc A = [C,D]
        suc B = [C,F]
        suc C = []
        suc D = [F]
        suc E = [B,H]
        suc F = [G,H]
        suc G = [E,H]
        suc H = []

g5 :: DiGraph Vertex
g5 = mkDiGraphEdges [A .. E]
                  [(A:-> C), (A:-> D), (B:-> C), (B:-> E), (C:-> D), (C:-> E), (C :-> A)]

g6 :: DiGraph Vertex
g6 = mkDiGraphSuc vertices suc
    where
      vertices = [A .. F]
      suc A = [B,E]
      suc B = [C,D,E]
      suc C = [D,E,F]
      suc D = [E,F]
      suc E = []
      suc F = []
      suc G = []
      suc H = []

{-
  A--B--D--F
   \ |  |
     C--E--G
-}
g7  = mkDiGraphSuc vertices suc 
    where
      vertices = [A .. G]
      suc A = [B,C]
      suc B = [A,C,D]
      suc C = [A,B,E]
      suc D = [B,F,E]
      suc E = [C,D,G]
      suc F = [D]
      suc G = [E]

g10 = mkDiGraphSuc vertices suc 
    where
      vertices = [A .. J]
      suc A = [E,J,I]
      suc B = [A,I,H]
      suc C = [D,G,H]
      suc D = [E,F,G]
      suc E = [D,F,J]
      suc F = [E,D,G]
      suc G = [D,C,H]
      suc H = [G,I,C]
      suc I = [A,B,J]
      suc J = [E,F,A]

