package io.f1r3fly.itm.zam

// A[X,E[_],V,G] ::= G + X + V
//                   + X x E[V] x A[X,E,V,G] + X x E[V] x A[X,E,V,G] 
//                   + A[X,E,V,G] x A[X,E,V,G]

// An example term language
trait SequentialLambdaTheory {
  type SLT_V
  trait LambdaTheory1T[V]

  case class LT1Mention( v : SLT_V ) extends LambdaTheory1T[SLT_V]
  case class LT1Abstraction( v : SLT_V, M : LambdaTheory1T[SLT_V] ) extends LambdaTheory1T[SLT_V]
  case class LT1Application( M : LambdaTheory1T[SLT_V], N : LambdaTheory1T[SLT_V] ) extends LambdaTheory1T[SLT_V]
}

trait UnifiedAgency {
  type X
  type UA_V
  type G
  type E[_]
  trait AgencyT

  case class GroundAgent( g : G ) extends AgencyT
  case class ReflectedAgent( x : X ) extends AgencyT
  case class VariableMentionAgent( v : UA_V ) extends AgencyT
  case class GuardedAgent( x : X, term : E[UA_V], k : AgencyT ) extends AgencyT
  case class CompositeAgent( l : AgencyT, r : AgencyT ) extends AgencyT
}

object SequentialLTGuardedAgents extends UnifiedAgency with SequentialLambdaTheory {
  case class Quote( a : AgencyT )
  case object Zero extends AgencyT

  type G = Either[Zero.type,Either[Boolean,Int]]
  type SLT_V = String
  type X = Either[String,Quote]
  type UA_V = SLT_V
  type E[V] = LambdaTheory1T[V]
}
