-------------------------------------------------------------------
-- Estructuras de Datos
-- Grado en Ingeniería Informática, del Software y de Computadores
-- Tema 1. Introducción a la Programación Funcional
-- Pablo López
-------------------------------------------------------------------

module Punto where

import           Test.QuickCheck

-- Representaremos un punto en el plano por sus coordenadas cartesianas
-- almacenadas en una tupla de dimensión 2. Por ejemplo:

unPunto :: (Int, Int)
unPunto = (5, 2)

-- Completa las siguientes funciones sobre puntos:

-- |
-- >>> esOrigen (0,0)
-- True
-- >>> esOrigen (1,0)
-- False

esOrigen :: (Int, Int) -> Bool
esOrigen x = fst x == 0 && snd x == 0 --patron variable
esOrigen (x,y) = x == 0 && y == 0 --este es un patrón tupla no una variable
esOrigen (0,0) = True
esOrigen _ = False

-- |
-- >>> estaEnX (5,0)
-- True
-- >>> estaEnX (7,2)
-- False

estaEnX :: (Int, Int) -> Bool
estaEnX = undefined

-- |
-- >>> modulo (2,5)
-- 29

modulo :: (Int, Int) -> Int
modulo (x,y) = x*x + y*y

-- |
-- >>> trasladar (4, 3) 6
-- (10, 9)

trasladar :: (Int, Int) -> Int -> Int
trasladar (x,y) d = (x+d, y+d)

-- Definimos una relación de igualdad sobre los puntos. Supondremos que los
-- puntos son iguales si lo son ambas componentes (igualdad lexicoggráfica).

-- |
-- >>> iguales (4,2) (4, 2)
-- True
-- >>> iguales (6,8) (8,6)
-- False

iguales :: (Int, Int) -> (Int, Int) -> Bool
iguales (x,y) (u,v) = x == u && y == v

-- Definimos ua relación de orden sobre los puntos. Supondremos que los puntos
-- están ordenados por su primera componente y, en caso de igualdad, por su segunda
-- componente (orden lexicográfico).

-- |
-- >>> orden (5,3) (5,3)
-- EQ
-- >>> orden (1,2) (1,3)
-- LT
-- >>> orden (2,1) (3,7)
-- LT

orden :: (Int, Int) -> (Int,Int) -> Ordering
orden (x,y) (u,v) 
    | c1 /= EQ = c1
    | otherwise = compare y v
    where 
        c1 = compare x u 
