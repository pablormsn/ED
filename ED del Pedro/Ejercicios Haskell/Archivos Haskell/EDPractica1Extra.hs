-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Práctica 1 - Ejercicios extra
--
-- Alumno: APELLIDOS, NOMBRE
-------------------------------------------------------------------------------

module Practica1Extra where

import           Test.QuickCheck

----------------------------------------------------------------------
-- Ejercicio - esPrimo
----------------------------------------------------------------------

esPrimo :: (Integral a) => a -> Bool
esPrimo n   | n <= 0 = error "argumento negativo o cero"
            | n == 1 = True
            | otherwise = comprobarDiv r == n
    where
        r = 2
        comprobarDiv r 
            | mod n r == 0 = r
            | otherwise = comprobarDiv (r+1)

----------------------------------------------------------------------
-- Ejercicio - cocienteYResto
----------------------------------------------------------------------
func :: (Integral a) => a -> a -> a
func x y  | x < y = 0
          | otherwise = 1+(func (x-y) y)

cocienteYResto :: (Integral a) => a -> a -> (a,a)
cocienteYResto x y  | y == 0 = error "división por 0"
                    | x < 0 || y < 0 = error "argumentos negativos" 
                    | otherwise = (funcion, x-funcion*y)
    where
        funcion = func x y


prop_cocienteYResto_OK x y = (x >= 0 && y > 0) ==> cocienteYResto x y == divMod x y

-- Ejercicio - libre de cuadrados
----------------------------------------------------------------------

libreDeCuadrados :: Integer -> Bool
libreDeCuadrados n  | n <= 0 = error "argumento cero o negativo"
                    | n == 1 = True
                    | otherwise = mod n ((numAnt c)^2) /= 0
    where
        c = 2
        numAnt c
            | mod n (c^2) == 0 || c == n = c
            | otherwise = numAnt (c+1) 

----------------------------------------------------------------------
-- Ejercicio - raiz entera
----------------------------------------------------------------------

raizEntera :: Integer -> Integer
raizEntera x
        | x < 0 = error "número no válido"
        | otherwise = candidato r 
        where
            r = 1
            candidato r   | r^2 > x = (r-1)
                          | otherwise = candidato (r+1)

prop_raizEntera_OK n = n >= 0 ==> truncate (sqrt (fromIntegral n)) == raizEntera n

raizEnteraRapida :: Integer -> Integer
raizEnteraRapida n  | n < 0 = error "número no válido"
                    | otherwise = rapidas 0 n 
                    where
                        rapidas a b | n == 0 = 0
                                    | n == 1 = 1
                                    | a+1 == b = a
                                    | (medio)^2 <= n = rapidas (medio) b
                                    | otherwise = rapidas a (medio)
                                    where 
                                        medio = div (a+b) 2
----------------------------------------------------------------------
-- Ejercicio - números de Harshad
----------------------------------------------------------------------

sumaDigitos :: Integer -> Integer
sumaDigitos n
    | n < 0 = error "argumento negativo"
    | n < 10 = n
    | otherwise = (mod n 10) + sumaDigitos (div n 10)

harshad :: Integer -> Bool
harshad x
    | sumaDigitos x == 0 || x < 0 = error "argumento no positivo"
    | otherwise = mod x (sumaDigitos x) == 0

harshadMultiple :: Integer -> Bool
harshadMultiple n
    | sumaDigitos n == 0 || n < 0 = error "argumento no positivo"
    | otherwise = mod n (sumaDigitos n) == 0 && harshad (div n (sumaDigitos n))

vecesHarshad :: Integer -> Integer
vecesHarshad n
    | sumaDigitos n == 0 || n < 0 = error "argumento no positivo"
    | n == 1 = 1
    | harshad n == False = 0
    | otherwise = 1 + vecesHarshad (div n (sumaDigitos n))

prop_Bloem_Harshad_OK :: Integer -> Property
prop_Bloem_Harshad_OK n = n > 0 ==> n + 2 == vecesHarshad (1008 * 10^n)

----------------------------------------------------------------------
-- Ejercicio - ceros del factorial
----------------------------------------------------------------------

factorial :: Integer -> Integer
factorial n
    | n == 0 = 1
    |otherwise = n * factorial (n-1)

cerosDe :: Integer -> Integer
cerosDe n
{-   | n == 0 = 1
    | n > 0 && n < 10 = 0
    | mod n 10 /= 0 = 0
    | otherwise = 1 + cerosDe (div n 10)

prop_cerosDe_OK :: Integer -> Integer -> Property
prop_cerosDe_OK n m = (cerosDe n == 0) && m >= 0 && m <= 1000 ==> cerosDe (n*10^m) == m
-}
    | n == 0 = 1
    | n >= 10 && (mod n 10) == 0 = 1 + (cerosDe (div n 10))
    | n <= -10 && (mod n 10) == 0 = 1 + (cerosDe (div n 10))
    | otherwise = 0
{-

Responde las siguientes preguntas:

¿En cuántos ceros acaba el factorial de 10?

¿En cuántos ceros acaba el factorial de 100?

¿En cuántos ceros acaba el factorial de 1000?

¿En cuántos ceros acaba el factorial de 10000?


-}

----------------------------------------------------------------------
-- Ejercicio - números de Fibonacci y fórmula de Binet
----------------------------------------------------------------------

fib :: Integer -> Integer
fib n  = undefined

llamadasFib :: Integer -> Integer
llamadasFib n = undefined

{-

Responde a las siguientes preguntas:

¿Cuántas llamadas a fib son necesarias para calcular fib 30?


¿Cuántas llamadas a fib son necesarias para calcular fib 60?


-}

fib' :: Integer -> Integer
fib' n = undefined

prop_fib_OK :: Integer -> Property
prop_fib_OK n = undefined

phi :: Double
phi = undefined

binet :: Integer -> Integer
binet n = undefined

prop_fib'_binet_OK :: Integer -> Property
prop_fib'_binet_OK n = undefined

{-

Responde a la siguiente pregunta:

¿A partir de qué valor devuelve binet resultados incorrectos?

-}
