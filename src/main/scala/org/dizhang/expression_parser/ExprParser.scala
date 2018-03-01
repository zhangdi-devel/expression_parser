package org.dizhang.expression_parser

import scala.util.parsing.combinator._

class ExprParser extends JavaTokenParsers {

  def expr: Parser[ExprAST] = intLit | booleanLit | stringLit | id

  def intLit: Parser[ExprAST] = "[+\\-]?\\d+".r ^^ {x => IntLit(x.toInt)}
  def booleanLit: Parser[ExprAST] = ("true"|"false") ^^ {b => BooleanLit(b.toBoolean)}
  def stringLit: Parser[ExprAST] = "'" ~ """[^']*""".r ~ "'" ^^ {
    case _ ~ str ~ _ => StringLit(str)
  } | "\"" ~ """[^"]*""".r ~ "\"" ^^ {
    case _ ~ str ~ _ => StringLit(str)
  }
  def id: Parser[ExprAST] = """[a-zA-Z]\w*(\.[a-zA-Z]\w*)*""".r ^^ {v => Variable(v)}

  def unary: Parser[ExprAST] = """[\-+!]""".r ~ expr ^^ {case op ~ v => UnaryOp(op, v)}

  def factor: Parser[ExprAST] = "(" ~> expr <~ ")" | intLit | booleanLit | stringLit

  def term: Parser[ExprAST] = factor ~ rep("[*/]".r ~ factor) ^^ {
    case t ~ ts => ts.foldLeft(t){
      case (a, op ~ b) => BinaryOp(op, a, b)
    }
  }

  def binary: Parser[ExprAST] = expr ~ """[\-+*/]""".r ~ expr ^^ {
    case e1 ~ op ~ e2 => BinaryOp(op, e1, e2)
  }

  def func0: Parser[ExprAST] = """[a-zA-Z]\w*""".r ~ "(" ~ ")" ^^ {
    case n ~ _ ~ _ => FuncCall0(n)
  }

  def func1: Parser[ExprAST] = """[a-zA-Z]\w*""".r ~ "(" ~ expr ~ ")" ^^ {
    case n ~ _ ~ e ~ _ => FuncCall1(n, e)
  }

}

object ExprParser {


}