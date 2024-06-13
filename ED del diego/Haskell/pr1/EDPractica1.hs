-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Práctica 1
--
-- Alumno: López Ruiz, Diego 
-------------------------------------------------------------------------------

module EDPractica1 where

import           Test.QuickCheck

-------------------------------------------------------------------------------
-- Ejercicio 2 - intercambia
-------------------------------------------------------------------------------

intercambia :: (a,b) -> (b,a)
intercambia (x,y) = (y,x) -- completar

-------------------------------------------------------------------------------
-- Ejercicio 3 - ordena2 y ordena3
-------------------------------------------------------------------------------

-- 3.a
ordena2 :: Ord a => (a,a) -> (a,a)
ordena2 (x,y) = if y < x then (y,x) else (x,y) -- completar

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
p1_ordena3 x y z = enOrden (ordena3 (x,y,z)) 
   where enOrden (x,y,z) = (x <= y) && (y <= z) -- completar

p2_ordena3 x y z = x `pertenece` salida && y `pertenece` salida && z `pertenece` salida
   where 
      salida = ordena3 (x,y,z)
      pertenece v (x',y',z') = elem v [x',y',z'] -- completar


-------------------------------------------------------------------------------
-- Ejercicio 4 - max2
-------------------------------------------------------------------------------

-- 4.a
max2 :: Ord a => a -> a -> a
max2 x y = if x >= y then x else y -- completar

-- 4.b
-- p1_max2: el máximo de dos números x e y coincide o bien con x o bien con y.

p1_max2 x y = (max2 x y == x) || (max2 x y == y) -- completar

-- p2_max2: el máximo de x e y es mayor o igual que x y mayor o igual que y.

p2_max2 x y = (max2 x y >= x) || (max2 x y >= y) -- completar

-- p3_max2: si x es mayor o igual que y, entonces el máximo de x e y es x.

p3_max2 x y = x >= y ==> max2 x y == x -- completar

-- p4_max2: si y es mayor o igual que x, entonces el máximo de x e y es y.

p4_max2 x y = y >= x ==> max2 x y == y -- completar

-------------------------------------------------------------------------------
-- Ejercicio 5 - entre (resuelto, se usa en el ejercicio 7)
-------------------------------------------------------------------------------

entre :: Ord a => a -> (a, a) -> Bool
entre x (inf,sup) = inf <= x && x <= sup

-------------------------------------------------------------------------------
-- Ejercicio 6 - iguales3
-------------------------------------------------------------------------------

iguales3 :: Eq a => (a,a,a) -> Bool
iguales3 (x,y,z) = (x == y) && (y == z)

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
descomponer x = (horas, minutos, segundos)
   where
      horas = div x 3600 -- completar
      minutos = div (mod x 3600) 60
      segundos = mod (mod x 3600) 60
-- 7.b
p_descomponer x = x>=0 ==> h*3600 + m*60 + s == x
                           && m `entre` (0,59)
                           && s `entre` (0,59)
          where (h,m,s) = descomponer x

-------------------------------------------------------------------------------
-- Ejercicio 8 - convertir
-------------------------------------------------------------------------------

unEuro :: Double
unEuro = 166.836

pesetasAEuros :: Double -> Double 
pesetasAEuros x = (x / unEuro)

eurosAPesetas :: Int -> Double 
eurosAPesetas x = fromIntegral x * unEuro

-- Probamos la función
-- p_inversas x = eurosAPesetas (pesetasAEuros x) == x

-------------------------------------------------------------------------------
-- Ejercicio 10 - ecuación de segundo grado
-------------------------------------------------------------------------------

raíces :: Double -> Double -> Double -> (Double,Double)
raíces x y z 
{-
   PRIMERA FORMA
   | x == 0 = error "No es una ecuación de segundo grado" 
   | otherwise = if comprobarDiscriminante x y z < 0 then error "Raíces no reales" else (r1,r2)
      where 
         comprobarDiscriminante x' y' z' = (y' * y' - 4 * x' * z')
         r1 = (-y + sqrt (y * y - 4 * x * z))/(2*x)
         r2 = (-y - sqrt (y * y - 4 * x * z))/(2*x)
-}
   | x == 0 = error "No es una ecuación de segundo grado"
   | y * y - 4 * x * z < 0  = error "Raíces no reales"
   | otherwise = ((-y + sqrt (y * y - 4 * x * z))/(2*x), (-y - sqrt (y * y - 4 * x * z))/(2*x)) 

-------------------------------------------------------------------------------
-- Ejercicio 14 - potencia
-------------------------------------------------------------------------------

-- 14.a
potencia :: Integer -> Integer -> Integer
potencia b n = undefined -- completar

-- 14.b
potencia' :: Integer -> Integer -> Integer
potencia' b n = undefined -- completar

-- 14.c
p_pot b n =
   potencia b m == sol && potencia' b m == sol
   where sol = b^m
         m = abs n
-- 14.d
{-

Escribe en este comentario tu razonamiento:

-}
