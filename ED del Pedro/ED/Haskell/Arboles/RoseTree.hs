------------------------------------------------------------
-- Estructuras de Datos
-- Tema 4. Árboles
-- Pablo López
--
-- Árboles generales (rose trees) en Haskell
------------------------------------------------------------

module RoseTree where

data Tree a = Empty
            | Node a [Tree a]
            deriving Show

gtree1 :: Tree Int
gtree1 =
  Node 1 [ Node 2 [ Node 4 [ ]
                  , Node 5 [ ]
                  , Node 6 [ ]
                  ]
         , Node 3 [ Node 7 [ ] ]
         ]

-- | suma los nodos de un árbol genérico
-- >>> sumT gtree1
-- 28
sumT :: Num a => Tree a -> a
sumT Empty = 0
sumT (Node x xs) = x + sum (map (sumT) xs)

-- | altura (número de niveles) de un árbol genérico
-- >>> heightT gtree1
-- 3
heightT :: Tree a -> Int
heightT Empty = 0
-- Cuando la lista de hijos sea vacia retornamos 1
heightT (Node _ []) = 1
-- Habría que comprobar la altura de cada nodo y coger el máximo. Una vez escogido el máximo se le suma 1 más (la raíz).
heightT (Node _ xs) = 1 + maximum (map (heightT) xs)

-- | frontera (conjunto de nodos hoja) de un árbol genérico
-- >>> borderT gtree1
-- [4,5,6,7]
borderT :: Tree a -> [a]
-- Caso base
borderT Empty = []
-- Un elem
borderT (Node x []) = [x]
-- Debemos concatenar los nodos del último nivel
borderT (Node _ xs) = concat [ borderT n | n <- xs]

-- | recorrido de un árbol genérico
-- >>> flattenT gtree1
-- [1,2,4,5,6,3,7]
flattenT :: Tree a -> [a]
flattenT Empty = []
flattenT (Node x []) = [x]
flattenT (Node x xs) = [x] ++ concat [ flattenT n | n <- xs]  -- (foldr (\ cab cola -> (flattenT cab) ++ cola) [] xs)
