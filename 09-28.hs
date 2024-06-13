replicate :: Int -> a -> [a] --
replicate n x = [x | _ <- [1..n]]

divisores :: Integer -> [Integer]
divisores n
    | n<0 = error "El número debe ser positivo"
    | otherwise = [x | x <- [1..n], n `mod` x == 0]

divisores' :: Integer -> [Integer]
divisores' n
    | otherwise = [x | x <- [-na..na], x/=0 && n `mod` x == 0]
        where na = abs n