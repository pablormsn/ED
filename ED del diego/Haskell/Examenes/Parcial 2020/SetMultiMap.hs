module SetMultiMap (
    SetMultiMap
    empty
    , isEmpty
    , size
    , isDefinedAt
    , insert
    , valuesOf
    , deleteKey
    , deleteKeyValue
    , filterValues
    , fold
) where

import Data.List
import Test.QuickCheck

import qualified DataStructures.Set.LinearSet as S

data SetMultiMap a b = Empty | Node a (S)