package io.f1r3fly.itm.zam
sealed trait Nat
case object _0 extends Nat
sealed trait Succ[N <: Nat] extends Nat

trait UsefulNatTypeAliases {
  // Helper type aliases for readability
  type _0 = _0.type
  type _1 = Succ[_0.type]
  type _2 = Succ[_1]
  type _3 = Succ[_2]
  type _4 = Succ[_3]
  type _5 = Succ[_4]
  type _6 = Succ[_5]
  // and so on
}

trait UsefulNatTypeOperations extends UsefulNatTypeAliases {
  // Type-level arithmetic operations
  // Type-level addition
  sealed trait Plus[N <: Nat, M <: Nat, R <: Nat]
  implicit def plusZero[M <: Nat]: Plus[_0, M, M] = new Plus[_0, M, M] {}
  implicit def plusSucc[N <: Nat, M <: Nat, R <: Nat](implicit plusNM: Plus[N, M, R]): Plus[Succ[N], M, Succ[R]] = new Plus[Succ[N], M, Succ[R]] {}

  // Type-level multiplication
  sealed trait Mult[N <: Nat, M <: Nat, R <: Nat]
  implicit def multZero[M <: Nat]: Mult[_0, M, _0] = new Mult[_0, M, _0] {}
  implicit def multSucc[N <: Nat, M <: Nat, NM <: Nat, R <: Nat](implicit multNM: Mult[N, M, NM], plusNM: Plus[NM, M, R]): Mult[Succ[N], M, R] = new Mult[Succ[N], M, R] {}
}

object NatUsage extends UsefulNatTypeOperations {
  // Example usage
  type Three = _3
  type FourPlusTwo = Plus[_4, _2, _0]
  type SixTimesThree = Mult[_6, _3, _1]
  implicitly[Plus[_2, _3, _5]]
  implicitly[Mult[_2, _3, _6]]
}

sealed trait Fin[N <: Nat]
//case object _0Fin extends Fin[Succ[_0]]
//case class SuccFin[F <: Fin[N], N <: Nat](prev: F) extends Fin[Succ[N]]
