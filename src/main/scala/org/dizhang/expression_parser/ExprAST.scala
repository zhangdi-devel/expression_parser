package org.dizhang.expression_parser

sealed trait ExprAST

case class IntLit(value: Int) extends ExprAST

case class BooleanLit(value: Boolean) extends ExprAST

case class StringLit(value: String) extends ExprAST

case class Variable(name: String) extends ExprAST

case class BinaryOp(name: String, left: ExprAST, right: ExprAST) extends ExprAST

case class UnaryOp(name: String, operand: ExprAST) extends ExprAST

case class FuncCall0(name: String) extends ExprAST

case class FuncCall1(name: String, arg: ExprAST) extends ExprAST

case class IfElse(condition: ExprAST, yes: ExprAST, no: ExprAST) extends ExprAST