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

-- Complejidad: O(1)
-- |
-- >>> empty
-- Empty
empty :: Stack a
empty = Empty

-- Complejidad: O(1)
-- |
-- | push "frank" customers
-- Node "frank" (Node "peter" (Node "mary" (Node "john" Empty)))
push :: a -> Stack a -> Stack a
push x p = Node x p
--push x Empty = Node x Empty
--push x (Node c rs)= Node x (Node c rs)

-- Complejidad: O(1)
-- |
-- >>> pop customers
-- Node "mary" (Node "john" Empty)
pop :: Stack a -> Stack a
pop Empty = error "pop sobre vacía"
pop (Node x p) = p  

-- Complejidad: O(?)
-- |
-- >>> top customers
-- "peter"
top :: Stack a -> a
top Empty = error "Lista vacia"
top (Node x p) = x

-- Complejidad: O(?)
-- |
-- >>> isEmpty empty
-- True
--
-- isEmpty customers
-- False
isEmpty :: Stack a -> Bool
isEmpty Empty = True
isEmpty _ = False

-- esto es para enseñar a QuickCheck a generar Stack aleatorias:
-- no hay que saber cómo hacerlo; siempre se facilita

instance Arbitrary a => Arbitrary (Stack a) where
  arbitrary =  do
                xs <- listOf arbitrary
                return (foldr push empty xs)
