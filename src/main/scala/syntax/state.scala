package io.f1r3fly.itm

// This scala code transliterates the following domain equations
// ITM[K,V,X] ::=
//   K x ITM[K,V,X] + ITM[ITM[K,V,X],V,X] + ITM[K,V,X] x ITM[K,V,X] + K + V + X
// RITM ::= ITM[RITM,1+RITM,RITM]

trait InteractingTrieMaps {
  type K
  type V
  type X
  type NestedInteractingTrieMapT[K,V,X]

  trait InteractingTrieMap

  case class Trieggered(
    k : K, cont : InteractingTrieMap
  ) extends InteractingTrieMap
  case class KeyedUp(
    k : NestedInteractingTrieMapT[InteractingTrieMap,V,X]
  ) extends InteractingTrieMap
  case class Composed(
    left : InteractingTrieMap,
    right : InteractingTrieMap
  ) extends InteractingTrieMap
  case class Reflective(
    k : K
  ) extends InteractingTrieMap
  case class BoxedIn(
    v : V
  ) extends InteractingTrieMap
  case class Mentioned(
    x : X
  ) extends InteractingTrieMap
}

trait Ground {
  type ITM  
}

object ReflectiveInteractingTrieMaps extends InteractingTrieMaps with Ground {
  type ITM = InteractingTrieMap
  type K = ITM
  type V = ITM
  type X = ITM
  trait NestedInteractingTrieMap[K,V,X] extends InteractingTrieMap 
  type NestedInteractingTrieMapT[K,V,X] = NestedInteractingTrieMap[InteractingTrieMap,V,X]
  object Identity extends ITM with NestedInteractingTrieMap[InteractingTrieMap,V,X]
}
