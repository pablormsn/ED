import Test.QuickCheck

entre :: Ord a => a -> (a, a) -> Bool
entre x (inf,sup) = inf <= x && x <= sup

-- Ejercicio 7 

type TotalSegundos = Integer
type Horas = Integer
type Minutos = Integer
type Segundos = Integer
descomponer :: TotalSegundos -> (Horas,Minutos,Segundos)
descomponer x = (horas,minutos,segundos)
    where
        horas = div x 3600
        minutos = div (mod x 3600) 60
        segundos = mod (mod x 3600) 60
        -- (horas,minutos,segundos) = (div x 3600, div (mod x 3600) 60, mod (mod x 3600) 60)

p_descomponer x = x>=0 ==> h*3600 + m*60 + s == x
                            && entre m (0,59)
                            && entre s (0,59)
    where (h,m,s) = descomponer x

-- Ejercicio 8
unEuro :: Double 
unEuro = 166.386

-- a)
pesetasAEuros :: Double -> Double
pesetasAEuros x = x / unEuro

eurosAPesetas :: Double -> Double
eurosAPesetas x = x * unEuro

-- p_inversas x = eurosAPesetas (pesetasAEuros x) == x

-- Ejercicio 9
infix 4 ~=
(~=) :: Double -> Double -> Bool
x ~= y = abs (x-y) < epsilon
    where epsilon = 1/1000

p_inversas x = eurosAPesetas (pesetasAEuros x) ~= x

-- Ejercicio 10
-- a)
raíces :: Double -> Double -> Double -> (Double,Double)
raíces x y z = if checkDiscr x y z then ((-y + sqrt (y^2 - 4*x*z))/2*x, (-y - sqrt (y^2 - 4*x*z))/2*x) else error "raíces no reales"
    where 
        checkDiscr x y z = y^2 - 4*x*z >= 0

-- b)
{-
p1_raíces a b c = esRaíz r1 && esRaíz r2
    where
        (r1,r2) = raíces a b c
        esRaíz r = a*r^2 + b*r + c ~= 0
-}

-- Ejercicio 11
esMúltiplo :: Integer -> Integer -> Bool
esMúltiplo x y = if mod x y == 0 then True else False

-- Ejercicio 12 
(==>>) :: Bool -> Bool -> Bool
False ==>> y = True
True ==>> False = False
True ==>> True = True

--Ejercicio 13 
esBisiesto :: Int -> Bool
esBisiesto x = if mod x 4 == 0 then (mod x 100 == 0) ==>> (mod x 400 == 0) else False

-- Ejercicio 14 
-- a)
potenciaRec :: Integer -> Integer -> Integer
potenciaRec x 0 = 1
potenciaRec x n = x * potenciaRec x (n-1)

--b)
potenciaRec' :: Integer -> Integer -> Integer
potenciaRec' x 0 = 1
potenciaRec' x n 
    | mod n 2 == 0 = (potenciaRec' x (div n 2)) * (potenciaRec' x (div n 2))
    | otherwise = x * (potenciaRec' x (div (n-1) 2)) * (potenciaRec' x (div (n-1) 2))

-- c)
p_pot b n = n>=0 ==> potenciaRec b n == sol
                    && potenciaRec' b n == sol
    where sol = b^n

-- Ejercicio 15 
factorial :: Integer -> Integer
factorial 0 = 1
factorial n = n * factorial (n-1)

-- Ejercicio 16 
-- a)
divideA :: Int -> Int -> Bool
divideA x y = if mod y x == 0 then True else False

-- b)
p1_divideA x y = y /= 0 && y `divideA` x ==> div x y * y == x

-- c) 
p2_divideA x y z = x /= 0 && x `divideA` y && x `divideA` z ==> x `divideA` (y+z) 

-- Ejercicio 17
mediana :: Ord a => (a,a,a,a,a) -> a
mediana (x,y,z,t,u)
    | x > z = mediana (z,y,x,t,u)
    | y > z = mediana (x,z,y,t,u)
    | t < z = mediana (x,y,t,z,u)
    | u < z = mediana (x,y,u,t,z)
    | otherwise = z 
