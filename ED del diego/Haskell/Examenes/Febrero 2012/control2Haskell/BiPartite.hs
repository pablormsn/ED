﻿----------------------------------------------
-- Estructuras de Datos.  2011/12.
-- 2º Curso del Grado en Ingeniería [Informática | del Software | de Computadores].
-- Escuela Técnica Superior de Ingeniería en Informática. UMA
--
-- Examen 13-Febrero-2012
-- Estudio de grafos bipartitos por coloreado con búsqueda en profundidad
-- 
-- ALUMNO :  
-- GRADO:  
--  
----------------------------------------------

module BiPartite(
    biColored    -- :: Ord v  => Graph v -> Maybe (D.Dictionary v Color)
  , Color(..)
  )  where

import Graph 
import Data.Maybe(isJust)
import qualified Dictionary as D
import qualified Stack as S


data Color = Red |  Blue deriving (Eq,Show,Ord)
nextColor Red  = Blue
nextColor Blue = Red

pushAll :: S.Stack a -> [a] -> S.Stack a
pushAll = foldr S.push   

biColored :: Ord v => Graph v -> Maybe (D.Dictionary v Color)
biColored g 
 | null vs   = Just D.empty -- empty graph is bipartite
 | otherwise = aux g D.empty (S.push (src ,Red) S.empty)
 where 
  vs  = vertices g
  src = head vs -- initial vertex
 
aux :: Ord v => Graph v -> D.Dictionary v Color -> S.Stack (v, Color) -> 
                Maybe (D.Dictionary v Color)
aux g dict stack
  | S.isEmpty stack = Just dict
  | not (colored vertexS) = aux g (D.insert vertexS colorS dict) (insertSuccessor (successors g vertexS) (S.pop stack)) -- Añade la asociación al diccionario y añado los sucesores a la pila
  | colorD /= colorS = Nothing -- Cogeremos la cima de la pila y comprobaremos que esta coloreado. Además de ellos, veremos si el color extraido de la pila coincide con su valor en el diccionario
  | otherwise = aux g dict (S.pop stack)

-- 
-- ¡¡¡ completad el resto de guardas !! 
--
  where
    colored v = D.isDefinedAt v dict
    insertSuccessor [] s = s
    insertSuccessor (x:xs) s = S.push (x,(nextColor colorS)) (insertSuccessor xs s)
--  
-- ¡¡¡ completad las variables locales necesarias !!!
--
    vertexS = fst (S.top stack)
    colorD = maybeValue (D.valueOf (fst (S.top stack)) dict)
    colorS = snd (S.top stack)
 

maybeValue :: Maybe a -> a
maybeValue (Just x) = x
---------------------
--- EXAMPLES --------
---------------------
data MiVertice = A|B|C|D|E|F|G deriving (Show,Eq,Enum,Ord)

{- 
  A--B--D--F    
   \ |  |
     C--E--G
-}
g1  = mkGraphSuc vertices suc 
  where 
        vertices = [A .. G]
        suc A = [C,B]
        suc B = [A,C,D]
        suc C = [B,E]
	suc D = [B,F,E]
        suc E = [C,D,G]
        suc F = [D,D]
	suc G = [E]
-- *BiPartite> biColored g1
-- Nothing

{- 
  A--B--D--F    
     |  |
     C--E
-}
g2  = mkGraphEdges vertices edges 
  where 
        vertices =  [A .. F]
        edges = [(A,B),(B,C),(B,D),(D,E),(D,F),(C,E)]
-- *BiPartite> biColored g2
-- Just Dictionary(A->Red,B->Blue,C->Red,D->Red,E->Blue,F->Blue)

-- construcción de los bipartitos K n m
k n m = mkGraphEdges vertices edges
  where 
            vertices = [1 .. n + m]
            edges = [ (r,a) | r<-[1..n], a<-[n+1..n+m] ] 

-- *BiPartite> biColored (k 2 3)
-- Just Dictionary(1->Red,2->Red,3->Blue,4->Blue,5->Blue)
