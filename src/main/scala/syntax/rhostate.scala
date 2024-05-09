package io.f1r3fly.itm

// This code transliterates the domain equations:
//  P[N] ::= N x N x P[N] + N x P[N] + P[N] x P[N] + N
//  RP   ::= P[1 + RP]
// The purpose of this code is to provide the template or decoder ring
// for the structure of the interacting trie maps.
// The key point is to have a single (or at least the fewest) generator(s).

trait ProcessStates {
  type Name

  trait ProcessState
  case class Input( channel : Name, variable : Name, cont : ProcessState ) extends ProcessState
  case class Output( channel : Name, payload : ProcessState ) extends ProcessState  
  case class Composition( left : ProcessState, right : ProcessState ) extends ProcessState
  case class Deref( name : Name ) extends ProcessState  
}

trait Nominals {
  type Process

  trait Nominal
  case class Quote( proc : Process ) extends Nominal
}

object RhoStates extends ProcessStates with Nominals {
  type Process = ProcessState
  type Name = Nominal
  case object Zero extends Process
}
