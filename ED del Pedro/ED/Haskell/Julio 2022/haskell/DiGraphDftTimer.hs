-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
-- Identity number (DNI if Spanish/passport if Erasmus):
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module DiGraphDftTimer(
    diGraphDftTimer
) where

import           DataStructures.Dictionary.AVLDictionary as D
import           DataStructures.Graph.DiGraph
import           DataStructures.Set.BSTSet               as S

-- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
-- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
-- EXERCISE 3

diGraphDftTimer :: (Ord v) => DiGraph v -> (Dictionary v Int, Dictionary v Int)
diGraphDftTimer diGraph = aux vs diGraph (S.empty) (D.empty) (D.empty) 0
    where
        vs = vertices diGraph
        aux [] g visited arrivalT departureT _ = (arrivalT,departureT)
        aux (x:xs) g visited arrivalT departureT timer 
            | S.isElem x visited = aux xs g visited arrivalT departureT timer
            | otherwise = aux xs g visited' arrivalT' (D.insert x timer' departureT') (timer' + 1)
                where
                    (visited',arrivalT',departureT',timer') = checkSuccessors x g (S.insert x visited) (D.insert x timer arrivalT) departureT (timer+1) 

checkSuccessors :: (Ord v) => v -> DiGraph v -> Set v -> Dictionary v Int -> Dictionary v Int -> Int -> (Set v,Dictionary v Int, Dictionary v Int, Int)
checkSuccessors x g visited aT dT time = aux x sucs g visited aT dT time
    where
        sucs = successors g x
        aux v [] g s aT dT time = (s,aT,dT',time)
            where
                dT' = D.insert v time dT
        aux v (x:xs) g s aT dT time
            | S.isElem x s = aux v xs g s aT dT time
            | otherwise = aux v xs g s' aT' dT' (time'+1)
                where
                    (s',aT',dT',time') = aux x (successors g x) g (S.insert x s) (D.insert x time aT) dT (time+1)



