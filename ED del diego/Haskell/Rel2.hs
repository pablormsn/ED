import Test.QuickCheck
import Data.List

-- Ejercicio 4
distintos :: Eq a => [a] -> Bool
distintos xs = distintosAc xs []
    where
        distintosAc [] ys = True
        distintosAc (x:xs) ys = if elem x ys then False else distintosAc xs (ys ++ [x])

-- Ejercicio 5
replicate' :: Int -> a -> [a]
replicate' 1 x = [x]
replicate' n x = [y | y <- ([x] ++ (replicate' (n-1) x))] 

divideA :: Int -> Int -> Bool
divideA n m = mod m n == 0

-- Ejercicio 6
divisores :: Int -> [Int]
divisores n = [d | d <- [1 .. n], divideA d n]

divisores' :: Int -> [Int]
divisores' n 
    | n > 0 = [d | d <- [(-n) .. n], d /= 0 && divideA d n]
    | otherwise = [d | d <- [n .. (-n)], d /= 0 && divideA d n]

-- Ejercicio 7 
mcd :: Int -> Int -> Int
mcd n m = maximum commonD
    where
        commonD = [d | d <- divisores' n, elem d ds]
        ds = divisores' m

-- b)
p_mcd x y z = x/= 0 && y /= 0 && z /= 0 ==> mcd (x*z) (y*z) == (abs z)* mcd x y 

-- c) 
mcm :: Int -> Int -> Int
mcm n m = div (n*m) (mcd n m)

-- Ejercicio 8 
-- a) 
esPrimo :: Int -> Bool
esPrimo x = length (divisores x) == 2

-- b) 
primosHasta :: Int -> [Int]
primosHasta n = [ p | p <- [1 .. n], esPrimo p]

-- c) 
primosHasta' :: Int -> [Int]
primosHasta' n = filter (esPrimo) [1 .. n]

-- d)
p1_primos x = primosHasta x == primosHasta' x

-- Ejercicio 9
-- a) 
pares :: Int -> [(Int,Int)]
pares n = [(p1,(n-p1)) | p1 <- primosHasta' n, (n-p1) >= p1 && elem (n-p1) (primosHasta' n)]

-- b)
goldbach :: Int -> Bool
goldbach n | n > 2 && mod n 2 == 0 = if null (pares n) then False else True
    | otherwise = False

-- c) 
goldbachHasta :: Int -> Bool
goldbachHasta n = and [goldbach x | x <- [3 .. n], mod x 2 == 0]

goldbachDebil :: Int -> Bool
goldbachDebil n = foldr (\(x,y) res -> (x+y+3) == n && res) True (pares (n-3))
-- d)
goldbachDébilHasta :: Int -> Bool
goldbachDébilHasta n 
    | n > 5 = and (map (goldbachDebil) (primosHasta n))
    | otherwise = error "Tiene que ser mayor a 5"

-- Ejercicio 10
-- a) 
esPerfecto :: Int -> Bool 
esPerfecto n = sum (divisores n) - n == n -- Segunda forma: ((foldr (+) 0 (divisores n))-n) == n -- Primera forma: sum (divisores n \\ [n]) == n

-- b) 
perfectosMenoresQue :: Int -> [Int]
perfectosMenoresQue n = [x | x <- [1 .. n], esPerfecto x]

-- Ejercicio 11
-- a) 
take' :: Int -> [a] -> [a]
take' n xs = [ x | (p,x) <- zip [0 .. (n-1)] xs]

-- b) 
drop' :: Int -> [a] -> [a]
drop' n xs = [ x | (p,x) <- zip [0 .. (length xs)] xs, p >= n ]

-- c) (Lo obviamos de momento)

-- Ejercicio 12
-- a)
concat' :: [[a]] -> [a]
concat' xss = foldr (++) [] xss

-- b) 
concat'' :: [[a]] -> [a]
concat'' xss = [z | xs <- xss, z <- xs]
-- Darle vueltas

-- Ejercicio 13
desconocida :: (Ord a) => [a] -> Bool
desconocida xs = and [x <= y | (x,y) <- zip xs (tail xs)]

-- Ejercicio 14
-- a)
inserta :: Ord a => a -> [a] -> [a]
inserta x xs = (takeWhile (<x) xs) ++ [x] ++ (dropWhile (<x) xs)

-- b)
insertaRec :: Ord a => a -> [a] -> [a]
insertaRec x [] = [x]
insertaRec x xs@(y:ys)
    | x > y = y:(insertaRec x ys)
    | otherwise = (x:xs)

-- c)
p1_inserta x xs = desconocida xs ==> desconocida (inserta x xs)

-- e)
ordena :: Ord a => [a] -> [a]
ordena xs = foldr (\ x res -> inserta x res) [] xs

-- f) (Lo hago luego)

-- Ejercicio 15
-- a)
geométrica :: Integer -> Integer -> [Integer]
geométrica n r = iterate (*r) n

-- b)
p1_geométrica x r = x > 0 && r > 0 ==> and [div z y == r | (y,z) <- zip xs (tail xs)]
                                            where
                                                xs = take 100 (geométrica x r)

-- c)
múltiplosDe :: Integer -> [Integer]
múltiplosDe n = iterate (+n) 0

-- d)
potenciasDe :: Integer -> [Integer]
potenciasDe n = iterate (*n) 1

-- Ejercicio 16
-- Darle más vueltas
-- a)
múltiplosDe' :: Integer -> [Integer]
múltiplosDe' n | n > 0 = múltiplosDe n

-- b)
primeroComún :: Ord a => [a] -> [a] -> a
primeroComún (x:xs) (y:ys) | x == y = x
    | x < y = primeroComún xs (y:ys)
    | otherwise = primeroComún (x:xs) ys

-- c)
mcm' :: Integer -> Integer -> Integer
mcm' 0 _ = 0
mcm' _ 0 = 0
mcm' n m = primeroComún (tail (múltiplosDe' n)) (tail (múltiplosDe' m))

-- d)
p_mcm x y = x >= 0 && y >= 0 ==> mcm' x y == lcm x y