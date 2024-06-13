-- Hacer drop y splitAt
drop' :: Int -> [a] -> [a]
drop' 0 xs = xs
drop' n [] = []
drop' n (x:xs) = drop' (n-1) xs

splitAt' :: Int -> [a] -> ([a],[a])
splitAt' n xs = aux n xs []
    where 
        aux 0 xs ys = (ys,xs)
        aux n [] ys = (ys,[])
        aux n (x:xs) ys = aux (n-1) xs (ys ++ [x])