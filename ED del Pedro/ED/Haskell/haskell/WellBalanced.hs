-- Estructuras de Datos. 2º Curso. ETSI Informática. UMA
--
-- (completa y sustituye los siguientes datos)
-- Titulación: Grado en Ingeniería [Informática | del Software | de Computadores].
-- Alumno: APELLIDOS, NOMBRE
-- Fecha de entrega: DIA | MES | AÑO
--
-- Relación de Ejercicios 3. Ejercicios resueltos: ..........
--
-------------------------------------------------------------------------------

-- Ejercicio 1
module WellBalanced where

import Test.QuickCheck

import DataStructures.Stack.LinearStack as S

wellBalanced :: String -> Bool
wellBalanced xs = wellBalanced' xs S.empty

wellBalanced' :: String -> Stack Char -> Bool
wellBalanced' [] s = S.isEmpty s
wellBalanced' (x:xs) s
    | x == '(' || x == '[' || x == '{' = wellBalanced' xs (S.push x s)
    | x == ')' || x == ']' || x == '}' = wellBalanced' xs (S.pop s)
    | otherwise = wellBalanced' xs s
