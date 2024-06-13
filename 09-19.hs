factorial :: Integer -> Integer -- Funcion que recibe un entero y devuelve un entero
factorial 0 = 1 -- Si el entero es 0, devuelve 1 
factorial n | n>0 = n*factorial (n-1) -- Si el entero es mayor que 0, se multiplica por el factorial del entero menos 1

suma :: Integer -> Integer -> Integer -- Funcion que recibe dos enteros y devuelve un entero 
suma x y = x + y -- Los dos valores son enteros y se suman

hipotenusa :: Float -> Float -> Float -- Funcion que recibe dos flotantes y devuelve un flotante
hipotenusa c1 c2 = sqrt(c1^2 + c2^2) -- Los dos valores son flotantes y se calcula la hipotenusa

sumaHasta :: Integer -> Integer -- Funcion que recibe un entero y devuelve un entero
sumaHasta 0 = 0 -- Si el entero es 0, devuelve 0
sumaHasta n = if n>0 then n + sumaHasta (n-1) else error "argumento negativo" -- Si el entero es mayor que 0, se suma el entero con la suma de los enteros anteriores, si no, devuelve un error

minMax :: Integer -> Integer -> (Integer, Integer) -- Funcion que recibe dos enteros y devuelve una tupla de enteros
minMax x y = if(x <= y) then (x,y) else (y,x) -- Si el primer entero es menor o igual que el segundo, devuelve una tupla con el primer entero y el segundo entero, si no, devuelve una tupla con el segundo entero y el primer entero