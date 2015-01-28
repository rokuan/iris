package com.rokuan.iris.parser;

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
		ELSEIF, //  elseif
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
		PLAYER_STRING_VALUE_IDENTIFIER,		//  $variable
		PLAYER_INT_VALUE_IDENTIFIER,		//  #variable
		MACRO_IDENTIFIER,					// @macro(...)				
		//CONSTANT_IDENTIFIER,    //  @constant_id

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
		HIDE,	//	hide
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
		
		/*COUNTITEM,
		GETITEM,
		DELETEITEM,*/

		SET,    //  set

		GOTO,   //  goto

		INPUT,  //  input

		MENU,   //  menu

		END,    //  end

		//[Special]
		EOF

	}

	//-----CHAMPS-----

	public TokenValue type;

	public int intValue;
	public String stringValue;
	public char charValue;
	public boolean booleanValue;

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

	public Token(TokenValue typeVal, String value)
	{
		this.type = typeVal;
		this.stringValue = value;
	}

	public Token(TokenValue typeVal, boolean value)
	{
		this.type = typeVal;
		this.booleanValue = value;
	}

	public String toString()
	{
		//return Enum.GetName(typeof(TokenValue), type);
		/* TODO: check */
		//return tokenstring(this);
		return this.type.toString();
	}

	public static String tokenstring(Token tok)
	{
		return tokenstring(tok.type);
	}

	public static String tokenstring(TokenValue tok)
	{
		switch (tok)
		{
		case TWOPOINTS:
			return ":";
		case LPAR:
			return "(";
		case RPAR:
			return ")";
		case LBRACKET:
			return "[";
		case RBRACKET:
			return "]";
		case LBRACE:
			return "{";
		case RBRACE:
			return "}";
		case COMMA:
			return ",";
		case DOT:
			return ".";
		case SEMICOLON:
			return ";";
		case DOLLAR:
			return "$";
		case SHARP:
			return "#";

			//[Identificateurs]
		case VALUE_IDENTIFIER:
			return "value identifier";
		case TYPE_IDENTIFIER:
			return "type identifier";

			//[Structures de controle]

		case IF:
			return "if";
		case ELSEIF:
			return "else if";
		case ELSE:
			return "else";

		case SWITCH:
			return "switch";
		case CASE:
			return "case";
		case DEFAULT:
			return "default";
		case BREAK:
			return "break";

		case WHILE:
			return "while";
		case DO:
			return "do";

		case FOR:
			return "for";

			//[Operateurs booleens]      

		case AND:
			return "&&";
		case OR:
			return "||";
		case GT:
			return ">";
		case LT:
			return "<";
		case GEQ:
			return ">=";
		case LEQ:
			return "<=";
		case EQBOOL:
			return "==";
		case NEG:
			return "!";

		case EQ:
			return "=";
		case PLUSEQ:
			return "+=";
		case MINUSEQ:
			return "-=";
		case TIMESEQ:
			return "*=";
		case DIVEQ:
			return "/=";

		case PLUSPLUS:
			return "++";
		case MINUSMINUS:
			return "--";
		case TIMESTIMES:
			return "**";

			//[Operateurs]
		case PLUS:
			return "+";
		case MINUS:
			return "-";
		case TIMES:
			return "*";
		case DIV:
			return "/";



			//[Types]
		case STRING:
			return "string";
		case CHAR:
			return "char";
		case INT:
			return "int";
		case BOOLEAN:
			return "boolean";

			//[Autres]
		case NEW:
			return "new";

			//[Mots-cles]
		case NPC:
			return "npc";
		case FUNCTION:
			return "function";
		case CONSTANT:
			return "constant";
		case CLASS:
			return "class";

			//[Special]
		case EOF:
			return "eof";

		default:
			return "";
		}
	}
}
