package io.f1r3fly.itm

trait LambdaTheory[B,V] {
  trait Term
  case class Ground( b : B ) extends Term
  case class Mention( v : V ) extends Term
  case class Abstraction( v : V, body : Term ) extends Term
  case class Application( op : Term, arg : Term ) extends Term

  def equivalent( l : Term, r : Term ) : Boolean = ???

  def reduce( t : Term ) : Option[Term] = ???
}

trait RedLambda {
  type RV
  type BLC
  trait RTheory extends LambdaTheory[BLC,RV]
}
trait BlackLambda {
  type BV
  type RLC
  trait BTheory extends LambdaTheory[RLC,BV]
}

object RedBlackLambda extends RedLambda with BlackLambda {
  type BV = RV
  type BLC = BTheory
  type RLC = RTheory

  object RC extends RTheory 
  object BC extends BTheory 

  type RBTerm = Either[RC.Term,BC.Term]

  def equivalent( l : RBTerm, r : RBTerm ) : Boolean = {
    ( l, r ) match {
      case ( Left( t ), Left( u ) ) => {
        RC.equivalent( t, u )
      }
      case ( Right( t ), Right( u ) ) => {
        BC.equivalent( t, u )
      }
      case _ => false
    }
  }

  def reduce( t : RBTerm ) : Option[RBTerm] = {
    t match {
      case Left( lt ) => {
        RC.reduce( lt ) match {
          case Some( u : RC.Term ) => {
            u match {
              case RC.Ground( bu : BC.Term ) => reduce( Right( bu ) )
              case _ => Some( Left( u ) )
            }
          }
          case None => None
        }
      }
      case Right( rt ) => {
        BC.reduce( rt ) match {
          case Some( u : BC.Term ) => {
            u match {
              case BC.Ground( ru : RC.Term ) => reduce( Left( ru ) )
              case _ => Some( Right( u ) )
            }
          }
          case None => None
        }
      }
    }
  }
}
