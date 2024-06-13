-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Práctica 1 - Ejercicios extra
--
-- Alumno: APELLIDOS, NOMBRE
-------------------------------------------------------------------------------

import           Test.QuickCheck

----------------------------------------------------------------------
-- Ejercicio - esPrimo
----------------------------------------------------------------------

esPrimo :: Integer -> Bool
esPrimo n   | n <= 0 = error "argumento negativo o cero"
            | otherwise = comprobarDiv r == n
    where
        r = 2
        comprobarDiv r 
            | mod n r == 0 = r
            | otherwise = comprobarDiv (r+1)

----------------------------------------------------------------------
-- Ejercicio - cocienteYResto
----------------------------------------------------------------------

-- cocienteYResto :: completa la declaración de tipo
cocienteYResto x y = undefined

prop_cocienteYResto_OK x y = undefined

-- Ejercicio - libre de cuadrados
----------------------------------------------------------------------

libreDeCuadrados :: Integer -> Bool
libreDeCuadrados n = undefined

----------------------------------------------------------------------
-- Ejercicio - raiz entera
----------------------------------------------------------------------

raizEntera :: Integer -> Integer
raizEntera x
        | x <= 0 = error "número no válido"
        | otherwise = candidato r 
        where
            r = 1
            candidato r   | r^2 > x = (r-1)
                          | otherwise = candidato (r+1)
            
raizEnteraRapida :: Integer -> Integer
raizEnteraRapida n = undefined

----------------------------------------------------------------------
-- Ejercicio - números de Harshad
----------------------------------------------------------------------

sumaDigitos :: Integer -> Integer
sumaDigitos n = undefined

harshad :: Integer -> Bool
harshad x = undefined

harshadMultiple :: Integer -> Bool
harshadMultiple n = undefined

vecesHarshad :: Integer -> Integer
vecesHarshad n = undefined

prop_Bloem_Harshad_OK :: Integer -> Property
prop_Bloem_Harshad_OK n = undefined

----------------------------------------------------------------------
-- Ejercicio - ceros del factorial
----------------------------------------------------------------------

factorial :: Integer -> Integer
factorial n  = undefined

cerosDe :: Integer -> Integer
cerosDe n = undefined

prop_cerosDe_OK :: Integer -> Integer -> Property
prop_cerosDe_OK n m = undefined

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
