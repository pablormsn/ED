-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Práctica 1
--
-- Alumno: ROBLES MANSILLA, PABLO
-------------------------------------------------------------------------------

import Test.QuickCheck

-------------------------------------------------------------------------------
-- Ejercicio 1 - esTerna
-- Tres enteros positivos x, y, z constituyen una terna pitagórica si x2+y2=z2, es decir, si son los lados de un triángulo rectángulo. 
-------------------------------------------------------------------------------

-- 1.a) Define la función 
esTerna :: Integer -> Integer -> Integer -> Bool --Recibe tres enteros y devuelve un booleano
esTerna x y z = x^2+y^2==z^2 --Comprueba si los tres enteros forman una terna pitagórica

--1.b) Es fácil demostrar que para cualesquiera x e y enteros positivos con x>y, la terna (x2-y2, 2xy, x2+y2) es pitagórica. Usando esto, escribe una función terna que tome dos parámetros y devuelva una terna pitagórica.
terna :: Integer -> Integer -> (Integer, Integer, Integer)
terna x y = if(x>y) then (x^2-y^2, 2*x*y, x^2+y^2) else error "x no es mayor que y"


-------------------------------------------------------------------------------
-- Ejercicio 2 - intercambia. Intercambia de posición los datos de la tupla.
-------------------------------------------------------------------------------

intercambia :: (a,b) -> (b,a)
intercambia (x,y) = (y,x)

-------------------------------------------------------------------------------
-- Ejercicio 3 - ordena2 y ordena3
-------------------------------------------------------------------------------

-- 3.a
ordena2 :: Ord a => (a,a) -> (a,a)
ordena2 (x,y) = if(y<x) then (y,x) else (x,y)

p1_ordena2 x y = enOrden (ordena2 (x,y))
   where enOrden (x,y) = x <= y

p2_ordena2 x y = mismosElementos (x,y) (ordena2 (x,y))
   where
      mismosElementos (x,y) (x',y') =
           (x == x' && y == y') || (x == y' && y == x')

-- 3.b
ordena3 :: Ord a => (a,a,a) -> (a,a,a)
ordena3 (x,y,z) 
   |y<x = ordena3(y,x,z) -- (3,1,2) --> (1,3,2) --> (1,2,3)
   |z<y = ordena3(x,z,y)
   |otherwise = (x,y,z)

-- 3.c
p1_ordena3 x y z = enOrden (ordena3(x,y,z))
   where enOrden (x,y,z) = x<=y && y<=z && x<=z

p2_ordena3 x y z = mismosElementos (x,y,z) (ordena3(x,y,z))
   where 
      mismosElementos (x,y,z) (x',y',z') =
         (x==x' && y==y' && z==z') || (x==x' && y==z' && z==y')
         || (x==y' && y==x' && z==z') || (x==z' && y==y' && z==x')
         || (x==y' && y==z' && z==x') || (x==z' && y==x' && z==y')

-------------------------------------------------------------------------------
-- Ejercicio 4 - max2
-------------------------------------------------------------------------------

-- 4.a
max2 :: Ord a => a -> a -> a
max2 x y = if(x>y) then x else y

-- 4.b
-- p1_max2: el máximo de dos números x e y coincide o bien con x o bien con y.

p1_max2 x y = (max2 x y)==x || (max2 x y)==y 

-- p2_max2: el máximo de x e y es mayor o igual que x y mayor o igual que y.

p2_max2 x y = (max2 x y)>=x && (max2 x y) >=y

-- p3_max2: si x es mayor o igual que y, entonces el máximo de x e y es x.

p3_max2 x y = x>=y ==> (max2 x y) == x

-- p4_max2: si y es mayor o igual que x, entonces el máximo de x e y es y.

p4_max2 x y = y>=x ==> (max2 x y) == y

-------------------------------------------------------------------------------
-- Ejercicio 5 - entre (resuelto, se usa en el ejercicio 7)
-------------------------------------------------------------------------------

entre :: Ord a => a -> (a, a) -> Bool
entre x (inf,sup) = inf <= x && x <= sup

-------------------------------------------------------------------------------
-- Ejercicio 6 - iguales3
-------------------------------------------------------------------------------

iguales3 :: Eq a => (a,a,a) -> Bool
iguales3 (x,y,z) = x==y && y==z

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
descomponer x = (h,m,s)
   where
      h = div x 3600 --dividir el total de segundos entre 3600 para obtener las horas
      m = div (mod x 3600) 60 --dividir el resto de la división anterior entre 60 para obtener los minutos
      s = mod (mod x 3600) 60 --el resto de la división anterior son los segundos

-- 7.b
p_descomponer x = x>=0 ==> h*3600 + m*60 + s == x
                           && m `entre` (0,59)
                           && s `entre` (0,59)
                             where (h,m,s) = descomponer x

-------------------------------------------------------------------------------
-- Ejercicio 8 - Sea la siguiente definición que representa que un euro son 166.386 pesetas:
unEuro :: Double
unEuro = 166.386
--a) Define una función pesetasAEuros que convierta una cantidad (de tipo Double) de pesetas en los
--correspondientes euros. Por ejemplo:
--pesetasAEuros 1663.86  10.0
-------------------------------------------------------------------------------
pesetasAEuros :: Double -> Double
pesetasAEuros x = x/unEuro
--b) Define la función eurosAPesetas que convierta euros en pesetas. Por ejemplo:
--eurosAPesetas 10  1663.86
eurosAPesetas :: Double -> Double
eurosAPesetas x = x*unEuro --fromIntegral convierte un entero en un double
--c) Sea la siguiente propiedad, que establece que si pasamos una cantidad de pesetas a euros y los
--euros los volvemos a pasar a pesetas, obtenemos las pesetas originales (es decir, que las funciones
--definidas previamente son inversas):
p_inversas x = eurosAPesetas (pesetasAEuros x) == x
-------------------------------------------------------------------------------
-- Ejercicio 14 - potencia
-------------------------------------------------------------------------------

-- 14.a
potencia :: Integer -> Integer -> Integer
potencia _ 0 = 1 --caso base. El operador _ significa que no nos importa el valor de la variable
potencia b n = b*(potencia b (n-1)) --caso recursivo

-- 14.b
potencia' :: Integer -> Integer -> Integer
potencia' b n 
   | mod n 2 == 0 = potencia (potencia b (div n 2)) 2
   | otherwise = b * potencia (potencia b ((n-1) `div` 2)) 2

-- 14.c
p_pot b n =
   potencia b m == sol && potencia' b m == sol
   where sol = b^m
         m = abs n
-- 14.d
{-

Escribe en este comentario tu razonamiento:

-}

-------------------------------------------------------------------------------
-- Ejercicio 17 - mediana
-------------------------------------------------------------------------------

mediana :: Ord a => (a, a, a, a, a) -> a
mediana (x,y,z,t,u)
 | x > z = mediana (z,y,x,t,u)
 | y > z = mediana (x,z,y,t,u)
 | t < z = mediana (x,y,t,z,u)
 | u < z = mediana (x,y,u,t,z)
 | otherwise = z