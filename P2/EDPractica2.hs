-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º Curso. ETSI Informática. UMA
--
-- PRACTICA 2ª (Características de la Programación Funcional)
--
-- (completa y sustituye los siguientes datos)
-- Titulación: Grado en Ingeniería …………………………………… [Informática | del Software | de Computadores].
-- Alumno: APELLIDOS, NOMBRE
-- Fecha de entrega:  DIA | MES | AÑO
--
-- Ejercicios resueltos de la Relación : ..........
--
-------------------------------------------------------------------------------
module Practica2 where

import Test.QuickCheck



-------------------------------------------------------------------------------
-- Ejercicio 4
-------------------------------------------------------------------------------
distintos :: Eq a => [a] -> Bool
distintos [] = True -- Si es vacía, son todos distintos
distintos (x:xs) = all (/=x) xs && distintos xs --La función all recibe como parámetro una comparación y una lista. Devuelve True si todos los elementos de la lista cumplen la comparación. En este caso, la comparación es (/=x), que devuelve True si el elemento de la lista es distinto de x. Por tanto, si todos los elementos de la lista son distintos de x, la función devuelve True. Además, se llama a la función distintos con la cola de la lista, para comprobar que todos los elementos de la lista son distintos entre sí.

-------------------------------------------------------------------------------
-- Ejercicio 11
-------------------------------------------------------------------------------
take' :: Int -> [a] -> [a]
take' n xs = [ x | (p, x) <- zip [0..n-1] xs]--Lista por comprensión: [expresión | patrón <- lista] -< se lee como ∈ (x∈[1,2,3,4]). La expresión es lo que devuelve. El patrón, en este caso, es (p, x), que sería (posición, elemento). La lista sería un zip de [0..n-1] y xs. El zip de dos listas devuelve una lista de tuplas, en la que cada tupla es un par de elementos de las listas [0,1,2],[3,5,8]=[(0,3),(1,5),(2,8)].Por tanto, la lista por comprensión devolvería [3,5,8]. Por último, la función take' devuelve los n primeros elementos de la lista por comprensión, por eso va desde 0 hasta n-1.

drop' :: Int -> [a] -> [a]
drop' n xs = [x | (p,x) <- zip [0..length xs] xs, p>n-1]--Detrás de la lista, va una guarda, que debe ser una expresión booleana.

p_positivos n xs = n>=0 ==> (take n xs) ++ (drop n xs) == xs-- el ==> es un antecedente/consecuente. En este caso, quiere decir que para todos los n >=0, se cumple lo siguiente.

-------------------------------------------------------------------------------
-- Ejercicio 13
-------------------------------------------------------------------------------
estaOrdenada :: (Ord a) => [a] -> Bool
estaOrdenada xs = and [ x<=y | (x,y) <- zip xs (tail xs) ]

--Una lista de ordinales devuelve un bool. Se le aplica el and a una lista que esté formada por tuplas que salen de un zip entre una lista y la cola de esa lista. si la lista es [1,2,3,4,5], el zip sería[(1,2),(2,3),(3,4),(4,5)].
--La lista estará formada por los valores booleanos respectivos a comprobar si x<=y. Entonces, lo que hace está función, es que comprueba si la lista está ordenada.

-------------------------------------------------------------------------------
-- Ejercicio 14
-------------------------------------------------------------------------------
-- apartados a, b, e y f
-- a)
inserta :: (Ord a) => a -> [a] -> [a]
inserta x xs = (takeWhile (<x) xs)++[x]++(dropWhile (<x) xs)


-- b)
inserta' :: (Ord a) => a -> [a] -> [a]
inserta' x [] = [x]
inserta' x (y:ys) 
  |x<=y = (x:y:ys)
  |otherwise = y:(inserta' x ys)

-- e)

ordena :: (Ord a) => [a] -> [a]
ordena xs = foldr inserta [] xs

-- f)  Utiliza para ello la función sorted definida en las transarencias
p1_ordenado xs = True ==> sorted xs
  where
    sorted :: (Ord a) => [a] -> Bool
    sorted [] = True
    sorted [_] = True
    sorted (x:y:zs) = x<=y && sorted (y:zs)




-------------------------------------------------------------------------------
-- Ejercicio 22
-------------------------------------------------------------------------------
binarios ::Integer -> [String]
binarios 0 = [""]
binarios x | x > 0 = 


todosEn :: (Eq a) => [a] -> [a] -> Bool
ys `todosEn` xs = all (`elem` xs) ys


-------------------------------------------------------------------------------
-- Ejercicio 34
-------------------------------------------------------------------------------

type Izdo = Double
type Dcho = Double
type Epsilon = Double
type Función = Double -> Double
biparticion :: Función -> Izdo -> Dcho -> Epsilon -> Double

biparticion f a b epsilon
  | long < epsilon    = undefined
-- sigue aqui
  where
      long = b - a
-- sigue aqui


-------------------------------------------------------------------------------
-- Lista de ejercicios extra. Ejercicio [lista de pares] 
-------------------------------------------------------------------------------

cotizacion :: [(String, Double)]
cotizacion = [("apple", 116), ("intel", 35), ("google", 824), ("nvidia", 67)]

-- buscarRec
-- buscarC
-- buscarP
-- valorCartera. DIFICIL

-------------------------------------------------------------------------------
-- Lista de ejercicios extra. Ejercicio [mezcla]
-------------------------------------------------------------------------------
-- mezcla

-------------------------------------------------------------------------------
-- Lista de ejercicios extra. Ejercicio [agrupar]
-------------------------------------------------------------------------------
-- agrupar. DIFICIL