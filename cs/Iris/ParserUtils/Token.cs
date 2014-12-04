using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils
{
    public class Token
    {
        public enum TokenValue
        {
            TWOPOINTS,  //  :
            LPAR,   //  (
            RPAR,   //  )
            LBRACKET,   //  [
            RBRACKET,   //  ]
            LBRACE, //  {
            RBRACE, //  }
            COMMA,  //  ,
            DOT,    //  .
            SEMICOLON,  //  ;
            SHARP,  //  #
            DOLLAR, //  $
            FOURPOINTS, //  ::

            //[Structures de controle]
            IF, //  if
            ELSEIF, //  else if
            ELSE,   //  else

            SWITCH, //  switch
            CASE,   //  case
            DEFAULT,    //  default
            BREAK,  //  break

            WHILE,  //  while
            DO, //  do

            FOR,    //  for

            //[Operateurs booleens]
            AND,    //  &&
            OR, //  ||
            GT, //  >
            LT, //  <
            GEQ,    //  >=
            LEQ,    //  <=
            EQBOOL, //  ==
            NEG,    //  !

            EQ, //  =
            PLUSEQ, //  +=
            MINUSEQ,    //  -=
            TIMESEQ,    //  *=
            DIVEQ,  //  /=

            PLUSPLUS,   //  ++
            MINUSMINUS, //  --
            TIMESTIMES, //  **

            //[Operateurs]
            PLUS,   //  +
            MINUS,  //  -
            TIMES,  //  *
            DIV,    //  /

            //[Identificateurs]
            VALUE_IDENTIFIER,   //  variable
            TYPE_IDENTIFIER,    //  MyType
            PLAYER_VALUE_IDENTIFIER,    //  $variable
            CONSTANT_IDENTIFIER,    //  #constant_id

            //[Types]
            STRING, //  "..."
            CHAR,   //  '.'
            INT,    //  1234
            BOOLEAN,   //  true

            //[Autres]
            NEW,    //  new

            //[Mots-cles]
            NPC,    //  npc
            FUNCTION,   //  function
            CONSTANT,   //  const
            CLASS,  //  class

            //[Instructions]
            OPENDLG,    //  open
            MSG,    //  msg
            NEXTDLG,    //  next
            CLOSEDLG,   //  close

            SHOW,   //  show
            PLAY,   //  play
            PAUSE,  //  pause
            STOP,   //  stop

            SETBGD, //  setbackground
            CLEANBGD,   //  cleanbackground
            CLEANFGD,   //  cleanforeground

            SWITCHCHAR, //  switchcharacter
            REMOVECHAR, //  removecharacter

            CHARACTER,  //  character
            IMAGE,  //  image
            SOUND,  //  sound
            BACKGROUND, //  background

            SET,    //  set

            GOTO,   //  goto

            INPUT,  //  input

            MENU,   //  menu

            END,    //  end

            //[Special]
            EOF

        }

        //-----CHAMPS-----

        public TokenValue type
        {
            get;
            private set;
        }

        public int intValue
        {
            get;
            private set;
        }
        public string stringValue
        {
            get;
            private set;
        }
        public char charValue
        {
            get;
            private set;
        }
        public bool booleanValue
        {
            get;
            private set;
        }

        //-----CONSTRUCTEURS-----

        public Token(TokenValue typeVal)
        {
            this.type = typeVal;
        }

        public Token(TokenValue typeVal, int value)
        {
            this.type = typeVal;
            this.intValue = value;
        }

        public Token(TokenValue typeVal, char value)
        {
            this.type = typeVal;
            this.charValue = value;
        }

        public Token(TokenValue typeVal, string value)
        {
            this.type = typeVal;
            this.stringValue = value;
        }

        public Token(TokenValue typeVal, bool value)
        {
            this.type = typeVal;
            this.booleanValue = value;
        }

        public override string ToString()
        {
            return Enum.GetName(typeof(Token.TokenValue), type);
        }

        public static string tokenstring(Token tok)
        {
            return tokenstring(tok);
        }

        public static string tokenstring(TokenValue tok)
        {
            switch (tok)
            {
                case TokenValue.TWOPOINTS:
                    return ":";
                case TokenValue.LPAR:
                    return "(";
                case TokenValue.RPAR:
                    return ")";
                case TokenValue.LBRACKET:
                    return "[";
                case TokenValue.RBRACKET:
                    return "]";
                case TokenValue.LBRACE:
                    return "{";
                case TokenValue.RBRACE:
                    return "}";
                case TokenValue.COMMA:
                    return ",";
                case TokenValue.DOT:
                    return ".";
                case TokenValue.SEMICOLON:
                    return ";";
                case TokenValue.DOLLAR:
                    return "$";
                case TokenValue.SHARP:
                    return "#";

                //[Identificateurs]
                case TokenValue.VALUE_IDENTIFIER:
                    return "value identifier";
                case TokenValue.TYPE_IDENTIFIER:
                    return "type identifier";

                //[Structures de controle]

                case TokenValue.IF:
                    return "if";
                case TokenValue.ELSEIF:
                    return "else if";
                case TokenValue.ELSE:
                    return "else";

                case TokenValue.SWITCH:
                    return "switch";
                case TokenValue.CASE:
                    return "case";
                case TokenValue.DEFAULT:
                    return "default";
                case TokenValue.BREAK:
                    return "break";

                case TokenValue.WHILE:
                    return "while";
                case TokenValue.DO:
                    return "do";

                case TokenValue.FOR:
                    return "for";

                //[Operateurs booleens]      

                case TokenValue.AND:
                    return "&&";
                case TokenValue.OR:
                    return "||";
                case TokenValue.GT:
                    return ">";
                case TokenValue.LT:
                    return "<";
                case TokenValue.GEQ:
                    return ">=";
                case TokenValue.LEQ:
                    return "<=";
                case TokenValue.EQBOOL:
                    return "==";
                case TokenValue.NEG:
                    return "!";

                case TokenValue.EQ:
                    return "=";
                case TokenValue.PLUSEQ:
                    return "+=";
                case TokenValue.MINUSEQ:
                    return "-=";
                case TokenValue.TIMESEQ:
                    return "*=";
                case TokenValue.DIVEQ:
                    return "/=";

                case TokenValue.PLUSPLUS:
                    return "++";
                case TokenValue.MINUSMINUS:
                    return "--";
                case TokenValue.TIMESTIMES:
                    return "**";

                //[Operateurs]
                case TokenValue.PLUS:
                    return "+";
                case TokenValue.MINUS:
                    return "-";
                case TokenValue.TIMES:
                    return "*";
                case TokenValue.DIV:
                    return "/";



                //[Types]
                case TokenValue.STRING:
                    return "string";
                case TokenValue.CHAR:
                    return "char";
                case TokenValue.INT:
                    return "int";
                case TokenValue.BOOLEAN:
                    return "boolean";

                //[Autres]
                case TokenValue.NEW:
                    return "new";

                //[Mots-cles]
                case TokenValue.NPC:
                    return "npc";
                case TokenValue.FUNCTION:
                    return "function";
                case TokenValue.CONSTANT:
                    return "constant";
                case TokenValue.CLASS:
                    return "class";

                //[Special]
                case TokenValue.EOF:
                    return "eof";

                default:
                    return "";
            }
        }
    }
}
