------------------------------------------------------------
-- Estructuras de Datos
-- Tema 3. Estructuras de Datos Lineales
-- Pablo López
--
-- Módulo del TAD Stack
------------------------------------------------------------

------------------------------------------------------------
-- Implementación del TAD Stack
------------------------------------------------------------

module Stack where

import           Test.QuickCheck

data Stack a = Empty
             | Node a (Stack a)
             deriving (Show, Eq)

customers :: Stack String
--                 top
customers = Node "peter" (Node "mary" (Node "john" Empty))

-- Complejidad: O(?)
-- |
-- >>> empty
-- Empty
empty :: Stack a
empty = undefined

-- Complejidad: O(?)
-- |
-- | push "frank" customers
-- Node "frank" (Node "peter" (Node "mary" (Node "john" Empty)))
push :: a -> Stack a -> Stack a
push = undefined

-- Complejidad: O(?)
-- |
-- >>> pop customers
-- Node "mary" (Node "john" Empty)
pop :: Stack a -> Stack a
pop = undefined

-- Complejidad: O(?)
-- |
-- >>> top customers
-- "peter"
top :: Stack a -> a
top = undefined

-- Complejidad: O(?)
-- |
-- >>> isEmpty empty
-- True
--
-- isEmpty customers
-- False
isEmpty :: Stack a -> Bool
isEmpty = undefined

-- esto es para enseñar a QuickCheck a generar Stack aleatorias:
-- no hay que saber cómo hacerlo; siempre se facilita

instance Arbitrary a => Arbitrary (Stack a) where
  arbitrary =  do
                xs <- listOf arbitrary
                return (foldr push empty xs)
