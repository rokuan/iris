using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.ParserUtils;
using Iris.ExpressionElement;

namespace Iris.SimpleExpressionElement
{
    public class BinaryOperatorExpression : SimpleExpression
    {
        public Token.TokenValue op;
        public SimpleExpression e1;
        public SimpleExpression e2;

        public BinaryOperatorExpression(Token.TokenValue opValue, SimpleExpression left, SimpleExpression right)
        {
            this.op = opValue;
            this.e1 = left;
            this.e2 = right;
        }

        /*public ObjectType getExpressionType()
        {
            Token.TokenValue e1Type = e1.getExpressionType(), e2Type = e2.getExpressionType();

            switch (value)
            {

                case Token.TokenValue.EQBOOL:
                    if (e1Type == e2Type
                        && e1Type != Token.TokenValue.NONE)
                    {
                        return e1Type;
                    }
                    break;

                case Token.TokenValue.AND:
                case Token.TokenValue.OR:                    
                    if (e1Type == Token.TokenValue.BOOL
                        && e2Type == Token.TokenValue.BOOL)
                    {
                        return Token.TokenValue.BOOL;
                    }
                    break;
                    
                case Token.TokenValue.LT:
                case Token.TokenValue.GT:
                case Token.TokenValue.LEQ:
                case Token.TokenValue.GEQ:
                    if (e1Type == e2Type
                        && (e1Type == Token.TokenValue.INT || e1Type == Token.TokenValue.STRING))
                    {
                        return Token.TokenValue.BOOL;
                    }
                    break;

                case Token.TokenValue.PLUSEQ:
                    if (e1Type == Token.TokenValue.STRING)
                    {
                        return Token.TokenValue.STRING;
                    }
                    else if(e1Type == Token.TokenValue.INT && e2Type == Token.TokenValue.INT)
                    {
                        return e1Type;  //INT
                    }
                    break;

                case Token.TokenValue.MINUSEQ:
                case Token.TokenValue.TIMESEQ:
                case Token.TokenValue.DIVEQ:
                    if (e1Type == e2Type
                        && e1Type == Token.TokenValue.INT)
                    {
                        return e1Type;  //INT
                    }
                    break;

                case Token.TokenValue.PLUS:
                    if (e1Type == Token.TokenValue.STRING 
                        || e2Type == Token.TokenValue.STRING)
                    {
                        return Token.TokenValue.STRING;
                    }
                    else if (e1Type == Token.TokenValue.INT
                        && e2Type == Token.TokenValue.INT)
                    {
                        return Token.TokenValue.INT;
                    }
                    break;

                case Token.TokenValue.MINUS:
                case Token.TokenValue.TIMES:
                case Token.TokenValue.DIV:
                    if (e1Type == Token.TokenValue.INT
                        && e2Type == Token.TokenValue.INT)
                    {
                        return Token.TokenValue.INT;
                    }
                    break;

                
            }

            Printer.printError("incompatible types");
            return Token.TokenValue.NULL;
        }*/

        public override ObjectType getExpressionType()
        {
            ObjectType e1Type = e1.getExpressionType(), e2Type = e2.getExpressionType();

            switch (op)
            {

                case Token.TokenValue.EQBOOL:
                    if (e1Type == e2Type)
                    {
                        return ObjectType.booleanType;
                    }
                    break;

                case Token.TokenValue.AND:
                case Token.TokenValue.OR:
                    if (e1Type == ObjectType.booleanType
                        && e2Type == ObjectType.booleanType)
                    {
                        return ObjectType.booleanType;
                    }
                    break;

                case Token.TokenValue.LT:
                case Token.TokenValue.GT:
                case Token.TokenValue.LEQ:
                case Token.TokenValue.GEQ:
                    if (e1Type == ObjectType.intType
                        && e2Type == ObjectType.intType)
                    {
                        return ObjectType.booleanType;
                    }
                    break;

                case Token.TokenValue.PLUS:
                    if (e1Type == ObjectType.stringType
                        || e2Type == ObjectType.stringType)
                    {
                        return ObjectType.stringType;
                    }
                    else if (e1Type == ObjectType.intType
                        && e2Type == ObjectType.intType)
                    {
                        return ObjectType.intType;
                    }

                    //erreur
                    break;
                //return ObjectType.intType;

                case Token.TokenValue.MINUS:
                case Token.TokenValue.TIMES:
                case Token.TokenValue.DIV:
                    if (e1Type == ObjectType.intType
                        && e2Type == ObjectType.intType)
                    {
                        return ObjectType.intType;
                    }

                    //erreur
                    break;
                //return ObjectType.intType;

                default:
                    return ObjectType.undefinedType;

            }

            return ObjectType.undefinedType;
        }

        public override string ToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append('(');
            str.Append(e1.ToString());
            str.Append(' ');

            switch (op)
            {
                case Token.TokenValue.AND:
                    str.Append("&&");
                    break;
                case Token.TokenValue.OR:
                    str.Append("||");
                    break;

                case Token.TokenValue.EQBOOL:
                    str.Append("==");
                    break;
                case Token.TokenValue.LT:
                    str.Append('<');
                    break;
                case Token.TokenValue.GT:
                    str.Append('>');
                    break;
                case Token.TokenValue.LEQ:
                    str.Append("<=");
                    break;
                case Token.TokenValue.GEQ:
                    str.Append(">=");
                    break;

                case Token.TokenValue.PLUS:
                    str.Append('+');
                    break;
                case Token.TokenValue.MINUS:
                    str.Append('-');
                    break;
                case Token.TokenValue.TIMES:
                    str.Append('*');
                    break;
                case Token.TokenValue.DIV:
                    str.Append('/');
                    break;

                default:
                    return "";
            }

            str.Append(' ');
            str.Append(e2.ToString());
            str.Append(')');

            return str.ToString();
        }

        /*public override string ToString(string alinea)
        {
            return alinea + this.ToString();
        }

        public static ObjectType expectedLeftType(Token.TokenValue opVal)
        {
            switch (opVal)
            {
                case Token.TokenValue.AND:
                case Token.TokenValue.OR:
                    return ObjectType.booleanType;

                case Token.TokenValue.LT:
                case Token.TokenValue.GT:
                case Token.TokenValue.LEQ:
                case Token.TokenValue.GEQ:

                case Token.TokenValue.MINUS:
                case Token.TokenValue.TIMES:
                case Token.TokenValue.DIV:
                    return ObjectType.intType;

                case Token.TokenValue.PLUS:
                    ObjectTypeList objList = new ObjectTypeList();

                    objList.addObjectType(ObjectType.stringType);
                    objList.addObjectType(ObjectType.intType);
                    objList.addObjectType(ObjectType.charType);

                    return objList;

                case Token.TokenValue.EQBOOL:
                    return ObjectType.undefinedType;

                default:
                    return ObjectType.errorType;

            }
        }

        public static ObjectType expectedRightType(ObjectType leftType, Token.TokenValue op)
        {
            ObjectTypeList objList;

            switch (op)
            {
                case Token.TokenValue.AND:
                case Token.TokenValue.OR:
                    return ObjectType.booleanType;

                case Token.TokenValue.LT:
                case Token.TokenValue.GT:
                case Token.TokenValue.LEQ:
                case Token.TokenValue.GEQ:

                case Token.TokenValue.MINUS:
                case Token.TokenValue.TIMES:
                case Token.TokenValue.DIV:
                    return ObjectType.intType;

                case Token.TokenValue.PLUS:
                    if (leftType == ObjectType.stringType)
                    {
                        objList = new ObjectTypeList();

                        objList.addObjectType(ObjectType.stringType);
                        objList.addObjectType(ObjectType.intType);
                        objList.addObjectType(ObjectType.charType);

                        return objList;
                    }

                    if (leftType == ObjectType.intType)
                    {
                        objList = new ObjectTypeList();

                        objList.addObjectType(ObjectType.stringType);
                        objList.addObjectType(ObjectType.intType);

                        return objList;
                    }

                    if (leftType == ObjectType.charType)
                    {
                        objList = new ObjectTypeList();

                        objList.addObjectType(ObjectType.stringType);
                        objList.addObjectType(ObjectType.charType);

                        return objList;
                    }

                    return ObjectType.errorType;

                case Token.TokenValue.EQBOOL:
                    return leftType;

                default:
                    return ObjectType.errorType;
            }
        }*/
    }
}
