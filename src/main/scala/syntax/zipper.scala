package io.f1r3fly.itm.zam
import scala.language.higherKinds

// Codes for polynomial functors in finitely many variables.
sealed trait Poly[N <: Nat]
case class Z[N <: Nat]() extends Poly[N]
case class U[N <: Nat]() extends Poly[N]
case class Sum[N <: Nat](left: Poly[N], right: Poly[N]) extends Poly[N]
case class Product[N <: Nat](left: Poly[N], right: Poly[N]) extends Poly[N]
case class I[N <: Nat](index: Fin[N]) extends Poly[N]
case class Mu[N <: Nat](poly: Poly[Succ[N]]) extends Poly[N]
case class Substitution[N <: Nat](poly: Poly[Succ[N]], sub: Poly[N]) extends Poly[N]
case class Weakening[N <: Nat](poly: Poly[N]) extends Poly[Succ[N]]

// // An environment of length n is a telescope of n types. Each type may refer to its predecessors.
// sealed trait Env[N <: Nat]
// case object EmptyEnv extends Env[_0]
// case class ExtendEnv[N <: Nat, T](prev: Env[N], elem: Poly[N]) extends Env[Succ[N]]

// // The interpretation function
// trait Interpretation[N <: Nat, P[_ <: Nat]] {
//   def interpret[A <: Env[N]](poly: Poly[N], env: A): P[A]
// }

// object Interpretation {
//   implicit def baseCase[N <: Nat]: Interpretation[N, Nothing] = new Interpretation[N, Nothing] {
//     override def interpret[A <: Env[N]](poly: Poly[N], env: A): Nothing = poly match {
//       case _: Z[_] => throw new RuntimeException("Empty type not allowed")
//       case _: U[_] => throw new RuntimeException("Unit type not allowed")
//       case _: Sum[N] => throw new RuntimeException("Sum type not allowed")
//       case _: Product[N] => throw new RuntimeException("Product type not allowed")
//       case _: I[N] => throw new RuntimeException("Identity type not allowed")
//       case _: Mu[Succ[N]] => throw new RuntimeException("Mu type not allowed")
//       case _: Substitution[Succ[N]] => throw new RuntimeException("Substitution type not allowed")
//       case _: Weakening[N] => throw new RuntimeException("Weakening type not allowed")
//     }
//   }

//   implicit def recursiveCase[N <: Nat, F[_ <: Nat]: Interpretation](implicit rec: Interpretation[Succ[N], F]): Interpretation[N, F] = 
//     new Interpretation[N, F] {
//       override def interpret[A <: Env[N]](poly: Poly[N], env: A): F[A] = poly match {
//         case _: Z[_] => rec.interpret(Z(), env._prev)
//         case _: U[_] => rec.interpret(U(), env._prev)
//         case Sum(left, right) => rec.interpret(left, env) || rec.interpret(right, env) // Assuming union type
//         case Product(left, right) => (rec.interpret(left, env) && rec.interpret(right, env)) // Assuming product type
//         case Mu(poly) => rec.interpret(Mu(poly), ExtendEnv(env, Mu(poly)))
//         case Substitution(poly, sub) => rec.interpret(Substitution(poly, sub), ExtendEnv(env, sub))
//         case Weakening(innerPoly) => rec.interpret(innerPoly, env._prev)
//         case _ => throw new RuntimeException("Unexpected input type")
//       }
//     }
// }

// object Delta {

//   // The derivative takes a code to a code. This is quite nice because it means that we can in principle take the derivative of a derivative.
//   // Note further that this is a partial derivative. It takes a variable as an argument.
//   def derivative[N <: Nat, I <: Fin[N], F[_ <: Nat]](i: I, poly: Poly[N])(implicit interp: Interpretation[N, F]): Poly[N] = poly match {
//     case I(j) => if (i == j) U[N]() else Z[N]()
//     case Z() => Z[N]()
//     case U() => Z[N]()
//     case Sum(left, right) => Sum(derivative(i, left), derivative(i, right))
//     case Product(left, right) => Sum(Product(derivative(i, left), right), Product(left, derivative(i, right)))
//     case Mu(innerPoly) => {
//       val newPoly1 = Weakening(Substitution(derivative(Succ(i), innerPoly), Mu(innerPoly)))
//       val newPoly2 = Substitution(derivative(_0, innerPoly), Mu(innerPoly))
//       Mu(Sum(newPoly1, Product(newPoly2, I(_0))))
//     }
//     case Substitution(subPoly, sub) => Sum(Substitution(derivative(Succ(i), subPoly), sub), Product(Substitution(_0, subPoly), derivative(i, sub)))
//     case Weakening(innerPoly) => Z[N]()
//   }

//   // Plugging takes a derivative and a suitable argument and merges the two together.
//   def plug[N <: Nat, F[_ <: Nat], I <: Fin[N], A <: Env[N]](derivative: F[A], arg: I): F[A] = derivative match {
//     case Z() => throw new RuntimeException("Z type not allowed")
//     case U() => throw new RuntimeException("U type not allowed")
//     case I(j) => if (arg == j) implicitConversion(arg) else throw new RuntimeException("Invalid argument for I type")
//       // Handle other cases similarly
//   }
// }
