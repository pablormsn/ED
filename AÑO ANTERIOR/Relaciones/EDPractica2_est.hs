-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Práctica 2
--
-- Alumno: APELLIDOS, NOMBRE
-------------------------------------------------------------------------------

module Practica2 where

import           Data.List
import           Test.QuickCheck

-------------------------------------------------------------------------------
-- Ejercicio 2 - máximoYResto
-------------------------------------------------------------------------------

máximoYRestoOrden :: Ord a => [a] -> (a,[a])
máximoYRestoOrden [] = error "lista vacía"
máximoYRestoOrden xs = (maximum xs, [x | x <- xs, x/= maximum xs])


máximoYResto :: Ord a => [a] -> (a,[a])
máximoYResto [] = error "lista vacía"
máximoYResto [x] = (x,[])
máximoYResto (x:xs) = if maximum xs <= x then (x,xs) else máximoYResto (xs ++ [x])

-------------------------------------------------------------------------------
-- Ejercicio 3 - reparte
-------------------------------------------------------------------------------

reparte :: [a] -> ([a], [a])
reparte [] = ([],[])
reparte [x] = ([x], [])
reparte (x:y:xs) = (x:lista1, y:lista2)
    where
        (lista1,lista2) = reparte xs


-------------------------------------------------------------------------------
-- Ejercicio 4 - distintos
-------------------------------------------------------------------------------

distintos :: Eq a => [a] -> Bool
distintos [] = True 
distintos (x:xs) = not (elem x xs) && distintos xs

-------------------------------------------------------------------------------
-- Ejercicio 13 - hoogle
-------------------------------------------------------------------------------

-- Hoogle (https://www.haskell.org/hoogle/) es un buscador para el API de Haskell.
-- Usa Hoogle para encontrar información sobre las funciones 'and', y 'zip'

desconocida :: Ord a => [a] -> Bool
desconocida xs = and [ x <= y | (x, y) <- zip xs (tail xs) ]

-------------------------------------------------------------------------------
-- Ejercicio 14 - inserta y ordena
-------------------------------------------------------------------------------

-- 14.a - usando takeWhile y dropWhile
inserta :: Ord a => a -> [a] -> [a]
inserta x xs = takeWhile  (<x) xs ++ [x] ++ dropWhile (<x) xs

-- 14.b - mediante recursividad
insertaRec :: Ord a => a -> [a] -> [a]
insertaRec x (y:xs)
    |x<y = [x] ++ (y:xs)
    |otherwise = [y] ++ insertaRec x xs

-- 14.c

-- La línea de abajo no compila hasta que completes los apartados
-- anteriores.

p1_inserta x xs = desconocida xs ==> desconocida (inserta x xs)

-- 14.e - usando foldr
ordena :: Ord a => [a] -> [a]
ordena xs = foldr f [] xs
    where
        f cab solCola = inserta cab solCola

-- 14.f
prop_ordena_OK xs = ordena xs == sort xs

-------------------------------------------------------------------------------
-- Ejercicio 21 - nub
-------------------------------------------------------------------------------

-- 21.a
nub' :: Eq a => [a] -> [a]
nub' xs = foldr f [] xs
    where
        f cab solCola
            |not(elem cab solCola) = [cab] ++ solCola
            |otherwise = solCola

-- 21.b
p_nub' xs = nub xs == nub' xs

-- 21.c (distintos se define en el ejercicio 2)

p_sinRepes xs = distintos (nub' xs)

{-

Escribe aquí tu razonamiento de por qué p_sinRepes no
es suficiente para comprobar que nub' es correcta:

La funcion distintos verifica que dada una lista todos los
elementos son diferentes entre si, necesario para nuestro nub'
pero no verifica totalmente la corrección ya que esta función no 
comprueba elementos de la lista inicial que no se hayan añadido
y sean únicos en la primera lista.


-}

-- 21.d
todosEn :: Eq a => [a] -> [a] -> Bool
ys `todosEn` xs = all (`elem` xs) ys

p_sinReps' xs = todosEn xs (nub' xs) && distintos (nub' xs)
