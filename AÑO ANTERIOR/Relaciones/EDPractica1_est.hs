-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Práctica 1
--
-- Alumno: APELLIDOS, NOMBRE
-------------------------------------------------------------------------------

module EDPractica1 where

import           Test.QuickCheck
import Data.Time.Format.ISO8601 (yearFormat)

-------------------------------------------------------------------------------
-- Ejercicio 2 - intercambia
-------------------------------------------------------------------------------

intercambia :: (a,b) -> (b,a)
intercambia (x,y) = (y,x)

-------------------------------------------------------------------------------
-- Ejercicio 3 - ordena2 y ordena3
-------------------------------------------------------------------------------

-- 3.a
ordena2 :: Ord a => (a,a) -> (a,a)
ordena2 (x, y) = if y<x then (y,x) else (x,y)

p1_ordena2 x y = enOrden (ordena2 (x,y))
   where enOrden (x,y) = x <= y

p2_ordena2 x y = mismosElementos (x,y) (ordena2 (x,y))
   where
      mismosElementos (x,y) (x',y') =
           (x == x' && y == y') || (x == y' && y == x')

-- 3.b
ordena3 :: Ord a => (a,a,a) -> (a,a,a)
ordena3 (x, y, z)
   |x<=y && x<=z && y<=z = (x,y,z)
   |x<=y && x<=z && z<=y = (x,z,y)
   |y<=x && y<=z && x<=z = (y,x,z)
   |y<=x && y<=z && z<=x = (y,z,x)
   |z<=x && z<=y && x<=y = (z,x,y)
   |otherwise = (z,y,x)

ordena3' :: Ord a => (a,a,a) -> (a,a,a)
ordena3' (x,y,z)
   |x <= u = (x,u,v)
   |x <= v = (x,v,u)
   |otherwise = (u,v,x)
   where (u,v) = ordena2 (y,z)

-- 3.c
p1_ordena3 x y z = enOrden (ordena3(x,y,z))
   where enOrden (x,y,z) = x<=y && x<=z && y<=z

p2_ordena3 x y z = mismosElem (x,y,z) (ordena3(x,y,z))
   where
      mismosElem (x,y,z) (x',y',z') =
         (x == x' && y == y' && z == z') || (x == x' && y == z' && z == y') || 
         (x == y' && y == x' && z == z') || (x == z' && y == y' && z == x')

-------------------------------------------------------------------------------
-- Ejercicio 4 - max2
-------------------------------------------------------------------------------

-- 4.a
max2 :: Ord a => a -> a -> a
max2 x y = if x>y then x else y

-- 4.b
-- p1_max2: el máximo de dos números x e y coincide o bien con x o bien con y.

p1_max2 x y = m == x || m == y
   where m = max2 x y

-- p2_max2: el máximo de x e y es mayor o igual que x y mayor o igual que y.

p2_max2 x y = m >= x && m>=y
   where m = max2 x y

-- p3_max2: si x es mayor o igual que y, entonces el máximo de x e y es x.

p3_max2 x y = if x >= y then m == x else undefined 
   where m = max2 x y 

-- p4_max2: si y es mayor o igual que x, entonces el máximo de x e y es y.

p4_max2 x y = if y >= x then m == y else undefined 
   where m = max2 x y 

-------------------------------------------------------------------------------
-- Ejercicio 5 - entre (resuelto, se usa en el ejercicio 7)
-------------------------------------------------------------------------------

entre :: Ord a => a -> (a, a) -> Bool
entre x (inf,sup) = inf <= x && x <= sup

-------------------------------------------------------------------------------
-- Ejercicio 7 - descomponer
-------------------------------------------------------------------------------

-- Para este ejercicio nos interesa utilizar la función predefinida:
--
--              divMod :: Integral a => a -> a -> (a, a)
--
-- que calcula simultáneamente el cociente y el resto de la división entera:
--
--   *Main> divMod 30 7
--   (4,2)

-- 7.a
type TotalSegundos = Integer
type Horas         = Integer
type Minutos       = Integer
type Segundos      = Integer

descomponer :: TotalSegundos -> (Horas,Minutos,Segundos)
descomponer x = (h, m, s)
   where
      h = fst dif
      m = div (snd dif) 60
      s = mod (snd dif) 60
      dif = divMod x 3600

-- 7.b
p_descomponer :: TotalSegundos -> Property 
p_descomponer x = x >= 0 ==> h*3600 + m*60 + s == x 
                             && m `entre` (0,59)
                             && s `entre` (0,59)
            where (h,m,s) = descomponer x

-------------------------------------------------------------------------------
-- Ejercicio 14 - potencia
-------------------------------------------------------------------------------

-- 14.a
potencia :: Integer -> Integer -> Integer
potencia b n = if n==0 then 1 else b*potencia b (n-1)

-- 14.b
potencia' :: Integer -> Integer -> Integer
potencia' b n 
   |n == 0 = 1
   |mod n 2 == 0 = pot*pot
   |otherwise = b*poti*poti
      where
         pot = potencia' b (div n 2)
         poti = potencia' b (div (n-1) 2)

-- 14.c
p_pot b n =
   potencia b m == sol && potencia' b m == sol
   where sol = b^m
         m = abs n
-- 14.d
{-

Escribe en este comentario tu razonamiento:

-}
