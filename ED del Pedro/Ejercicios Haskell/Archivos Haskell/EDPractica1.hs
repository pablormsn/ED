-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Práctica 1
--
-- Alumno: APELLIDOS, NOMBRE
-------------------------------------------------------------------------------

module EDPractica1 where

import           Test.QuickCheck

-------------------------------------------------------------------------------
-- Ejercicio 2 - intercambia
-------------------------------------------------------------------------------

intercambia :: (a,b) -> (b,a)
intercambia (x,y) = (y,x) -- inicializa variables el patron

-------------------------------------------------------------------------------
-- Ejercicio 3 - ordena2 y ordena3
-------------------------------------------------------------------------------

-- 3.a
ordena2 :: Ord a => (a,a) -> (a,a)
ordena2 (x,y) = (min x y, max x y)
   -- | x <= y = (x,y)
   -- | otherwise = (y,x)

p1_ordena2 x y = enOrden (ordena2 (x,y))
   where enOrden (x,y) = x <= y

p2_ordena2 x y = mismosElementos (x,y) (ordena2 (x,y))
   where
      mismosElementos (x,y) (x',y') =
           (x == x' && y == y') || (x == y' && y == x')

-- 3.b
ordena3 :: Ord a => (a,a,a) -> (a,a,a)
ordena3 (x,y,z)
   | x <= u = (x,u,v)
   | x <= v = (u,x,v)
   | otherwise = (u,v,x)
   where (u,v) = ordena2 (y,z)
-- 3.c
p1_ordena3 x y z = u <= v && v <= w 
   where 
    (u,v,w) = ordena3 (x,y,z) -- completar

p2_ordena3 x y z = x `pertenece` salida && y `pertenece` salida && z `pertenece` salida
   where 
      salida = ordena3 (x,y,z)
      pertenece v (c1,c2,c3) = elem v [c1,c2,c3] -- completar

-------------------------------------------------------------------------------
-- Ejercicio 4 - max2
-------------------------------------------------------------------------------

-- 4.a
max2 :: Ord a => a -> a -> a
max2 x y 
   | x >= y = x
   | otherwise = y

-- 4.b
-- p1_max2: el máximo de dos números x e y coincide o bien con x o bien con y.

p1_max2 x y = m == x || m == y 
   where m = max2 x y 

-- p2_max2: el máximo de x e y es mayor o igual que x y mayor o igual que y.

p2_max2 = m >= x && m >= y
   where m = max2 x y 

-- p3_max2: si x es mayor o igual que y, entonces el máximo de x e y es x.

p3_max2 x y =  x >= y ==> max2(x,y) = x

-- p4_max2: si y es mayor o igual que x, entonces el máximo de x e y es y.

p4_max2 x y =  y >= x ==> max2(x,y) = y

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
descomponer x = (u,m,s)
   where
      (u,res) = divMod x 3600
      (m,s) = divMod res 60 -- completar

-- 7.b
p_descomponer x = x>=0 ==> h*3600 + m*60 + s == x
                           && m `entre` (0,59)
                           && s `entre` (0,59)
          where (h,m,s) = descomponer x

-------------------------------------------------------------------------------
-- Ejercicio 14 - potencia
-------------------------------------------------------------------------------

-- 14.a
potencia :: Integer -> Integer -> Integer
potencia _ 0 = 1
potencia b n = b * potencia b (n-1)

-- 14.b
potencia' :: Integer -> Integer -> Integer
potencia' _ n = 1
potencia' b n 
   | even n = p*p
   | otherwise = b * p * p
   where
      p = potencia' b * (n `div` 2)

-- 14.c
p_pot b n =
   potencia b m == sol && potencia' b m == sol
   where sol = b^m
         m = abs n
-- 14.d
{-

Escribe en este comentario tu razonamiento:

-}
