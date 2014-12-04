using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using Iris.ScriptElements;
using Iris.SimpleExpressionElement;
using Iris.ParserUtils.LexParseException;
using Iris.ExpressionElement;
using Iris.ExpressionElement.Instruction;
using Iris.Framework;
using System.IO;

namespace Iris.ParserUtils
{
    class LexerParser
    {
        private StreamReader fileStream;

        private Dictionary<string, ObjectExpression> variables = new Dictionary<string, ObjectExpression>();

        public List<Token> lexBuf;

        //

        private int lineNumber = 1;
        private int colNumber = 1;

        private int indiceToken = 0;
        private Token currentToken;

        public IrisScript script;

        //REGEX

        private readonly static string layout = @"(\s)+";

        private readonly static string elseIfExp = "else" + layout + "if";

        private readonly static string valueIdExp = @"[a-z](\w)*";
        private readonly static string typeIdExp = @"[A-Z](\w)*";
        private readonly static string constExp = @"\$[a-z](\w)*";

        private readonly static Regex valueIdRegex = new Regex("^" + valueIdExp + @"[\W]");
        private readonly static Regex typeIdRegex = new Regex("^" + typeIdExp + @"[\W]");
        //private readonly static Regex constIdRegex = new Regex("^" + constExp + @"[\W]");
        private readonly static Regex playerIdRegex = new Regex("^" + constExp + @"[\W]");

        private string codeToParse;

        public LexerParser()
        {

        }

        public void lexCode()
        {
            int indiceCode = 0, decalage, indiceComment, indiceNewLine;
            string strId;

            if (codeToParse == string.Empty)
            {
                do
                {
                    codeToParse = fileStream.ReadLine();

                    if (codeToParse == null)
                    {
                        lexBuf.Add(new Token(Token.TokenValue.EOF));
                        return;
                    }

                    lineNumber++;
                    colNumber = 1;
                } while (codeToParse == string.Empty);
            }

            switch (codeToParse[0])
            {
                case '\r':
                    codeToParse = codeToParse.Substring(1);
                    lexCode();
                    return;

                case '\t':
                case '\b':
                case ' ':
                    colNumber++;
                    codeToParse = codeToParse.Substring(1);
                    lexCode();
                    return;

                case '{':
                    lexBuf.Add(new Token(Token.TokenValue.LBRACE));
                    break;

                case '}':
                    lexBuf.Add(new Token(Token.TokenValue.RBRACE));
                    break;

                case '(':
                    lexBuf.Add(new Token(Token.TokenValue.LPAR));
                    break;

                case ')':
                    lexBuf.Add(new Token(Token.TokenValue.RPAR));
                    break;

                case '[':
                    lexBuf.Add(new Token(Token.TokenValue.LBRACKET));
                    break;

                case ']':
                    lexBuf.Add(new Token(Token.TokenValue.RBRACKET));
                    break;

                case ':':
                    if (codeToParse.Length > 1 && codeToParse[1] == ':')
                    {
                        lexBuf.Add(new Token(Token.TokenValue.FOURPOINTS));

                        indiceCode++;
                        colNumber++;
                    }
                    else
                    {
                        lexBuf.Add(new Token(Token.TokenValue.TWOPOINTS));
                    }
                    break;

                case ',':
                    lexBuf.Add(new Token(Token.TokenValue.COMMA));
                    break;

                case '.':
                    lexBuf.Add(new Token(Token.TokenValue.DOT));
                    break;

                case ';':
                    lexBuf.Add(new Token(Token.TokenValue.SEMICOLON));
                    break;

                case '>':
                    if (codeToParse.Length > 1 && codeToParse[1] == '=')
                    {
                        lexBuf.Add(new Token(Token.TokenValue.GEQ));
                        colNumber++;
                        indiceCode++;
                    }
                    else
                    {
                        lexBuf.Add(new Token(Token.TokenValue.GT));
                    }
                    break;

                case '<':
                    if (codeToParse.Length > 1 && codeToParse[1] == '=')
                    {
                        lexBuf.Add(new Token(Token.TokenValue.LEQ));
                        colNumber++;
                        indiceCode++;
                    }
                    else
                    {
                        lexBuf.Add(new Token(Token.TokenValue.LT));
                    }
                    break;

                case '=':
                    if (codeToParse.Length > 1 && codeToParse[1] == '=')
                    {
                        lexBuf.Add(new Token(Token.TokenValue.EQBOOL));
                        colNumber++;
                        indiceCode++;
                    }
                    else
                    {
                        lexBuf.Add(new Token(Token.TokenValue.EQ));
                    }
                    break;

                case '&':
                    if (codeToParse.Length > 1 && codeToParse[1] == '&')
                    {
                        lexBuf.Add(new Token(Token.TokenValue.AND));

                        colNumber++;
                        indiceCode++;
                    }
                    else
                    {
                        throw new InvalidSequence(lineNumber, colNumber);
                    }
                    break;

                case '|':
                    if (codeToParse.Length > 1 && codeToParse[1] == '|')
                    {
                        lexBuf.Add(new Token(Token.TokenValue.OR));

                        colNumber++;
                        indiceCode++;
                    }
                    else
                    {
                        throw new InvalidSequence(lineNumber, colNumber);
                    }
                    break;

                case '+':
                    if (codeToParse.Length > 1)
                    {
                        decalage = lexOperator("PLUS", '+', codeToParse[1]);
                        indiceCode += decalage;
                        colNumber += decalage;
                    }
                    else
                    {
                        lexBuf.Add(new Token(Token.TokenValue.PLUS));
                    }
                    break;

                case '-':
                    if (codeToParse.Length > 1)
                    {
                        decalage = lexOperator("MINUS", '-', codeToParse[1]);
                        indiceCode += decalage;
                        colNumber += decalage;
                    }
                    else
                    {
                        lexBuf.Add(new Token(Token.TokenValue.MINUS));
                    }
                    break;

                case '*':
                    if (codeToParse.Length > 1)
                    {
                        decalage = lexOperator("TIMES", '*', codeToParse[1]);
                        indiceCode += decalage;
                        colNumber += decalage;
                    }
                    else
                    {
                        lexBuf.Add(new Token(Token.TokenValue.TIMES));
                    }
                    break;

                case '/':
                    if (codeToParse.Length > 1)
                    {
                        if (codeToParse[1] == '*')
                        {
                            codeToParse = codeToParse.Substring(2);
                            colNumber += 2;

                            if ((indiceComment = codeToParse.IndexOf("*/")) == -1)
                            {
                                throw new UnfinishedCommentary(lineNumber, colNumber);
                            }
                            else
                            {
                                String tmp = codeToParse.Substring(0, indiceComment + 1);

                                while ((indiceNewLine = tmp.IndexOf('\n')) != -1)
                                {
                                    lineNumber++;
                                    tmp = tmp.Substring(indiceNewLine + 1);
                                }

                                colNumber += indiceComment + 2;
                                codeToParse = codeToParse.Substring(indiceComment + 2);

                                lexCode();
                                return;
                            }
                        }
                        else
                        {
                            decalage = lexOperator("DIV", '/', codeToParse[1]);
                            indiceCode += decalage;
                            colNumber += decalage;
                        }
                    }
                    else
                    {
                        lexBuf.Add(new Token(Token.TokenValue.DIV));
                    }
                    break;

                case '\'':
                    colNumber++;
                    indiceCode++;

                    codeToParse = codeToParse.Substring(1);

                    lexChar();
                    break;

                case '"':
                    colNumber++;
                    indiceCode++;

                    codeToParse = codeToParse.Substring(1);

                    lexstring();
                    return;

                default:
                    if (codeToParse[0] >= '0' && codeToParse[0] <= '9')
                    {
                        lexInt();
                        return;
                    }
                    else if (isPrefix(codeToParse, "elseif"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.ELSEIF));

                        /*decalage = codeToParse.IndexOf("if") + 1;
                        indiceCode += decalage;
                        colNumber += decalage;*/
                        indiceCode += 5;
                        colNumber += 5;
                    }
                    else if (isPrefix(codeToParse, "if"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.IF));

                        indiceCode++;
                        colNumber++;
                    }
                    else if (isPrefix(codeToParse, "else"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.ELSE));

                        indiceCode += 3;
                        colNumber += 3;
                    }
                    else if (isPrefix(codeToParse, "true"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.BOOLEAN, true));

                        indiceCode += 3;
                        colNumber += 3;
                    }
                    else if (isPrefix(codeToParse, "false"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.BOOLEAN, false));

                        indiceCode += 4;
                        colNumber += 4;
                    }
                    else if (isPrefix(codeToParse, "switch"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.SWITCH));

                        indiceCode += 5;
                        colNumber += 5;
                    }
                    else if (isPrefix(codeToParse, "case"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.CASE));

                        indiceCode += 3;
                        colNumber += 3;
                    }
                    else if (isPrefix(codeToParse, "default"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.DEFAULT));

                        indiceCode += 6;
                        colNumber += 6;
                    }
                    else if (isPrefix(codeToParse, "break"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.BREAK));

                        indiceCode += 4;
                        colNumber += 4;
                    }
                    else if (isPrefix(codeToParse, "for"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.FOR));

                        indiceCode += 2;
                        colNumber += 2;
                    }
                    else if (isPrefix(codeToParse, "while"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.WHILE));

                        indiceCode += 4;
                        colNumber += 4;
                    }
                    else if (isPrefix(codeToParse, "do"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.DO));

                        indiceCode++;
                        colNumber++;
                    }
                    else if (isPrefix(codeToParse, "npc"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.NPC));

                        indiceCode += 2;
                        colNumber += 2;
                    }
                    else if (isPrefix(codeToParse, "function"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.FUNCTION));

                        indiceCode += 7;
                        colNumber += 7;
                    }
                    //** NEW **
                    else if (isPrefix(codeToParse, "switchcharacter"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.SWITCHCHAR));

                        indiceCode += 14;
                        colNumber += 14;
                    }
                    else if (isPrefix(codeToParse, "removecharacter"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.REMOVECHAR));

                        indiceCode += 14;
                        colNumber += 14;
                    }
                    else if (isPrefix(codeToParse, "set"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.SET));

                        indiceCode += 2;
                        colNumber += 2;
                    }
                    else if (isPrefix(codeToParse, "show"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.SHOW));

                        indiceCode += 3;
                        colNumber += 3;
                    }
                    else if (isPrefix(codeToParse, "play"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.PLAY));

                        indiceCode += 3;
                        colNumber += 3;
                    }
                    else if (isPrefix(codeToParse, "stop"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.STOP));

                        indiceCode += 3;
                        colNumber += 3;
                    }
                    else if (isPrefix(codeToParse, "open"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.OPENDLG));

                        indiceCode += 3;
                        colNumber += 3;
                    }
                    else if (isPrefix(codeToParse, "msg"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.MSG));

                        indiceCode += 2;
                        colNumber += 2;
                    }
                    else if (isPrefix(codeToParse, "next"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.NEXTDLG));

                        indiceCode += 3;
                        colNumber += 3;
                    }
                    else if (isPrefix(codeToParse, "close"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.CLOSEDLG));

                        indiceCode += 4;
                        colNumber += 4;
                    }
                    else if (isPrefix(codeToParse, "input"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.INPUT));

                        indiceCode += 4;
                        colNumber += 4;
                    }
                    else if (isPrefix(codeToParse, "menu"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.MENU));

                        indiceCode += 3;
                        colNumber += 3;
                    }
                    else if (isPrefix(codeToParse, "goto"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.GOTO));

                        indiceCode += 3;
                        colNumber += 3;
                    }
                    else if (isPrefix(codeToParse, "setbackground"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.SETBGD));

                        indiceCode += 12;
                        colNumber += 12;
                    }
                    else if (isPrefix(codeToParse, "cleanforeground"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.CLEANFGD));

                        indiceCode += 14;
                        colNumber += 14;
                    }
                    else if (isPrefix(codeToParse, "cleanbackground"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.CLEANBGD));

                        indiceCode += 14;
                        colNumber += 14;
                    }
                    else if (isPrefix(codeToParse, "end"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.END));

                        indiceCode += 2;
                        colNumber += 2;
                    }
                    else if (valueIdRegex.IsMatch(codeToParse))
                    {
                        strId = lexIdentifier();

                        lexBuf.Add(new Token(Token.TokenValue.VALUE_IDENTIFIER, strId));

                        decalage = strId.Length - 1;
                        indiceCode += decalage;
                        colNumber += decalage;
                    }
                    else if (typeIdRegex.IsMatch(codeToParse))
                    {
                        strId = lexIdentifier();

                        lexBuf.Add(new Token(Token.TokenValue.TYPE_IDENTIFIER, strId));

                        decalage = strId.Length - 1;
                        indiceCode += decalage;
                        colNumber += decalage;
                    }
                    else if (playerIdRegex.IsMatch(codeToParse))
                    {
                        colNumber++;

                        codeToParse = codeToParse.Substring(1);

                        strId = lexIdentifier();

                        lexBuf.Add(new Token(Token.TokenValue.PLAYER_VALUE_IDENTIFIER, strId));

                        decalage = strId.Length - 1;
                        indiceCode += decalage;
                        colNumber += decalage;
                    }
                    /*else if (constIdRegex.IsMatch(codeToParse))
                    {
                        colNumber++;

                        codeToParse = codeToParse.Substring(1);

                        strId = lexIdentifier();

                        lexBuf.Add(new Token(Token.TokenValue.CONSTANT_IDENTIFIER, strId));

                        decalage = strId.Length - 1;
                        indiceCode += decalage;
                        colNumber += decalage;
                    }*/
                    else
                    {
                        throw new InvalidSequence(lineNumber, colNumber);
                    }

                    break;

            }

            colNumber++;
            indiceCode++;

            if (indiceCode == codeToParse.Length)
            {
                codeToParse = "";
                return;
            }


            codeToParse = codeToParse.Substring(indiceCode);
        }

        public bool isPrefix(string code, string prefix)
        {
            if (code.StartsWith(prefix))
            {
                if (code.Length > prefix.Length)
                {
                    return !char.IsLetterOrDigit(code[prefix.Length]) && code[prefix.Length] != '_';
                }

                return true;
            }

            return false;
        }

        public string lexIdentifier()
        {
            StringBuilder str = new StringBuilder();
            int indiceId = 0;

            while (char.IsLetterOrDigit(codeToParse[indiceId]) || codeToParse[indiceId] == '_')
            {
                str.Append(codeToParse[indiceId]);
                indiceId++;
            }

            return str.ToString();
        }

        public void lexstring()
        {
            StringBuilder strVal = new StringBuilder();
            char currentChar;
            int indiceCodeStr = 0, codeLength = codeToParse.Length;

            if (codeToParse == string.Empty)
            {
                throw new UnfinishedString(lineNumber, colNumber);
            }

            while ((currentChar = codeToParse[indiceCodeStr]) != '"' && indiceCodeStr < codeLength)
            {
                if (currentChar == '\\')
                {
                    if (indiceCodeStr + 1 < codeLength)
                    {
                        if (codeToParse[indiceCodeStr + 1] == '"')
                        {
                            strVal.Append('"');
                        }
                        else if (codeToParse[indiceCodeStr + 1] == '\\')
                        {
                            strVal.Append('\\');
                        }

                        indiceCodeStr++;
                    }
                    else
                    {
                        throw new UnfinishedString(lineNumber, colNumber);
                    }
                }
                else
                {
                    strVal.Append(currentChar);
                }

                if (currentChar == '\n')
                {
                    throw new UnfinishedString(lineNumber, colNumber);
                }

                colNumber++;
                indiceCodeStr++;

                if (indiceCodeStr == codeLength)
                {
                    throw new UnfinishedString(lineNumber, colNumber);
                }
            }

            lexBuf.Add(new Token(Token.TokenValue.STRING, strVal.ToString()));

            //+1 : apres le guillemet fermant
            codeToParse = codeToParse.Substring(indiceCodeStr + 1);
        }

        //TODO
        public void lexChar()
        {
            //char c = '\0';

            //return lexCode(code);
        }

        //OKAY
        public void lexInt()
        {
            StringBuilder intVal = new StringBuilder();
            int indiceCodeInt = 0;

            while (codeToParse[indiceCodeInt] >= '0' && codeToParse[indiceCodeInt] <= '9')
            {
                intVal.Append(codeToParse[indiceCodeInt]);

                colNumber++;
                indiceCodeInt++;
            }

            lexBuf.Add(new Token(Token.TokenValue.INT, int.Parse(intVal.ToString())));

            codeToParse = codeToParse.Substring(indiceCodeInt);
        }

        //TODO
        public int lexOperator(string opName, char op, char nextChar)
        {
            int indiceDecalage = 0;

            if (nextChar == '=')
            {
                lexBuf.Add(new Token((Token.TokenValue)Enum.Parse(typeof(Token.TokenValue), opName + "EQ")));
                indiceDecalage++;
            }
            else if (nextChar == op)
            {
                lexBuf.Add(new Token((Token.TokenValue)Enum.Parse(typeof(Token.TokenValue), opName + opName)));
                indiceDecalage++;
            }
            else
            {
                lexBuf.Add(new Token((Token.TokenValue)Enum.Parse(typeof(Token.TokenValue), opName)));
            }

            return indiceDecalage;
        }

        public bool startParse(string filepath)
        {
            script = new IrisScript();

            //lexBuf.Clear();
            lexBuf = new List<Token>();

            lineNumber = 1;
            colNumber = 1;

            indiceToken = 0;

            try
            {
                fileStream = new StreamReader(filepath);
                codeToParse = fileStream.ReadLine();
            }
            catch (Exception e)
            {
                return false;
            }

            return startParse();
        }

        public bool startParse()
        {
            if (codeToParse == string.Empty || codeToParse == null)
            {
                return true;
            }

            lexCode();

            currentToken = lexBuf.ElementAt(indiceToken);

            try
            {
                while (!currentTokenEquals(Token.TokenValue.EOF))
                {
                    switch (currentToken.type)
                    {
                        case Token.TokenValue.NPC:
                            script.addNpc(parseNpc());
                            break;

                        default:
                            return false;
                    }
                }
            }
            catch (BaseException e)
            {
                System.Windows.MessageBox.Show(e.ExceptionToString());
                return false;
            }

            fileStream.Close();

            return true;
        }

        public NpcStructure parseNpc()
        {
            NpcStructure npc = new NpcStructure();

            nextTokenIfEquals(Token.TokenValue.NPC);

            if (currentTokenEquals(Token.TokenValue.STRING))
            {
                npc.name = currentToken.stringValue;

                nextToken();

                nextTokenIfEquals(Token.TokenValue.LBRACE);

                while (!currentTokenEquals(Token.TokenValue.RBRACE))
                {
                    npc.addLabel(parseLabelExpression());
                }

                nextTokenIfEquals(Token.TokenValue.RBRACE);

                return npc;
            }

            return null;
        }

        public LabelExpression parseLabelExpression()
        {
            LabelExpression label;

            if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
            {
                label = new LabelExpression(currentToken.stringValue);

                nextToken();

                nextTokenIfEquals(Token.TokenValue.TWOPOINTS);

                label.body = parseExpression();

                return label;
            }
            else
            {
                throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
            }
        }

        public void parseFunction()
        {

        }

        public ValueExpression parseValueExpression()
        {
            ValueExpression valueExp = new ValueExpression();

            if (currentTokenEquals(Token.TokenValue.TYPE_IDENTIFIER))
            {
                valueExp.type = new ObjectType(currentToken.stringValue);
                nextToken();

                if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
                {
                    valueExp.name = currentToken.stringValue;
                    nextToken();

                    if (currentTokenEquals(Token.TokenValue.EQ))
                    {
                        nextToken();

                        valueExp.value = parseSimpleExpression();
                    }

                    nextTokenIfEquals(Token.TokenValue.SEMICOLON);

                    return valueExp;
                }

                throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
            }

            throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.TYPE_IDENTIFIER);
        }

        public Expression parseExpression()
        {
            List<Expression> exps = new List<Expression>();
            bool parseEnd = false;

            while (!parseEnd)
            {
                switch (currentToken.type)
                {
                    case Token.TokenValue.IF:
                        exps.Add(parseIfExpression());
                        break;

                    case Token.TokenValue.SWITCH:
                        exps.Add(parseSwitchExpression());
                        break;

                    case Token.TokenValue.FOR:
                        exps.Add(parseForExpression());
                        break;

                    case Token.TokenValue.WHILE:
                        exps.Add(parseWhileExpression());
                        break;

                    case Token.TokenValue.DO:
                        exps.Add(parseDoWhileExpression());
                        break;

                    case Token.TokenValue.TYPE_IDENTIFIER:
                        exps.Add(parseValueExpression());
                        break;

                    case Token.TokenValue.VALUE_IDENTIFIER:
                        nextToken();

                        if (currentTokenEquals(Token.TokenValue.TWOPOINTS))
                        {
                            previousToken();
                            parseEnd = true;
                        }
                        else if (isAnAssignOperator(currentToken.type))
                        {
                            previousToken();
                            exps.Add(parseAssignExpression());
                            nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        }
                        else
                        {
                            throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.EQ);
                        }
                        break;

                    //**TODO**
                    case Token.TokenValue.SET:
                        SetVariableInstruction setExp = new SetVariableInstruction();

                        nextToken();

                        if (currentTokenEquals(Token.TokenValue.PLAYER_VALUE_IDENTIFIER))
                        {
                            setExp.variable = new CharacterVariableExpression(currentToken.stringValue);
                            nextToken();

                            nextTokenIfEquals(Token.TokenValue.COMMA);

                            setExp.value = parseSimpleExpression();

                            nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        }
                        else
                        {
                            throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.PLAYER_VALUE_IDENTIFIER);
                        }

                        exps.Add(setExp);
                        break;

                    case Token.TokenValue.OPENDLG:
                        nextToken();
                        nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        exps.Add(new OpenMsgDialogInstruction());
                        break;

                    case Token.TokenValue.MSG:
                        MsgDisplayInstruction mes = new MsgDisplayInstruction();

                        nextToken();

                        mes.msg = parseSimpleExpression();

                        if (mes.msg.getExpressionType() != ObjectType.stringType)
                        {
                            throw new IncompatibleTypesException(lineNumber, colNumber, ObjectType.stringType, mes.msg.getExpressionType());
                        }

                        nextTokenIfEquals(Token.TokenValue.SEMICOLON);

                        exps.Add(mes);
                        break;

                    case Token.TokenValue.NEXTDLG:
                        nextToken();
                        nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        exps.Add(new NextMsgDialogInstruction());
                        break;

                    case Token.TokenValue.CLOSEDLG:
                        nextToken();
                        nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        exps.Add(new CloseMsgDialogInstruction());
                        break;

                    case Token.TokenValue.PLAY:
                        PlayMediaInstruction play = new PlayMediaInstruction();

                        nextToken();

                        if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
                        {
                            play.name = currentToken.stringValue;

                            nextToken();
                            nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        }
                        else
                        {
                            throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
                        }

                        exps.Add(play);
                        break;

                    case Token.TokenValue.STOP:
                        exps.Add(new StopMediaInstruction());
                        nextToken();
                        nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        break;

                    case Token.TokenValue.SHOW:
                        ShowImageInstruction show = new ShowImageInstruction();
                        string position;

                        nextToken();

                        if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
                        {
                            show.image = parsePathExpression();

                            nextTokenIfEquals(Token.TokenValue.COMMA);

                            //TODO
                            if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
                            {
                                position = currentToken.stringValue;

                                switch (position)
                                {
                                    case "LEFT":
                                    case "left":
                                        show.position = GameScreen.LEFT;
                                        break;

                                    case "CENTER":
                                    case "center":
                                        show.position = GameScreen.CENTER;
                                        break;

                                    case "RIGHT":
                                    case "right":
                                        show.position = GameScreen.RIGHT;
                                        break;

                                    default:
                                        //TODO
                                        throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
                                }
                            }

                            nextTokenIfEquals(Token.TokenValue.VALUE_IDENTIFIER);

                            nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        }

                        exps.Add(show);
                        break;

                    case Token.TokenValue.SWITCHCHAR:
                        SwitchCharacterInstruction switchChar = new SwitchCharacterInstruction();

                        nextToken();

                        if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
                        {
                            switchChar.character = new VariableExpression(currentToken.stringValue);
                            nextToken();
                            nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        }
                        else
                        {
                            throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
                        }

                        exps.Add(switchChar);
                        break;

                    case Token.TokenValue.SETBGD:
                        SetBackgroundInstruction bgdInstr = new SetBackgroundInstruction();

                        nextToken();

                        if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
                        {
                            bgdInstr.image = new VariableExpression(currentToken.stringValue);
                            nextToken();
                            nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        }
                        else
                        {
                            throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
                        }

                        exps.Add(bgdInstr);
                        break;

                    case Token.TokenValue.CLEANBGD:
                        exps.Add(new CleanBackgroundInstruction());
                        nextToken();
                        nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        break;

                    case Token.TokenValue.CLEANFGD:
                        exps.Add(new CleanForegroundInstruction());
                        nextToken();
                        nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        break;

                    case Token.TokenValue.REMOVECHAR:
                        nextToken();
                        nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        exps.Add(new RemoveCharacter());
                        break;

                    case Token.TokenValue.GOTO:
                        GotoInstruction gotoInstr = new GotoInstruction();

                        nextToken();

                        gotoInstr.npc = parseNpcMemberExpression();

                        nextTokenIfEquals(Token.TokenValue.SEMICOLON);

                        exps.Add(gotoInstr);
                        break;

                    case Token.TokenValue.INPUT:
                        InputInstruction inputInstr = new InputInstruction();

                        nextToken();

                        if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
                        {
                            inputInstr.name = currentToken.stringValue;
                            nextToken();
                            nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        }
                        else
                        {
                            throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
                        }

                        exps.Add(inputInstr);
                        break;

                    case Token.TokenValue.MENU:
                        MenuInstruction menuInstr = new MenuInstruction();

                        nextToken();
                        nextTokenIfEquals(Token.TokenValue.LPAR);

                        if (currentTokenEquals(Token.TokenValue.RPAR))
                        {
                            throw new EmptyExpression(lineNumber, colNumber);
                        }

                        if (currentTokenEquals(Token.TokenValue.STRING))
                        {
                            menuInstr.addChoice(currentToken.stringValue);
                            nextToken();
                        }
                        else
                        {
                            throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.STRING);
                        }

                        while (!currentTokenEquals(Token.TokenValue.RPAR))
                        {
                            nextTokenIfEquals(Token.TokenValue.COMMA);

                            if (currentTokenEquals(Token.TokenValue.STRING))
                            {
                                menuInstr.addChoice(currentToken.stringValue);
                                nextToken();
                            }
                            else
                            {
                                throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.STRING);
                            }
                        }

                        nextTokenIfEquals(Token.TokenValue.RPAR);
                        nextTokenIfEquals(Token.TokenValue.LBRACE);

                        while (currentTokenEquals(Token.TokenValue.CASE))
                        {
                            if (menuInstr.hasDefaultCase)
                            {
                                throw new CaseException(lineNumber, colNumber);
                            }

                            menuInstr.addCase(parseCase());
                        }

                        if (currentTokenEquals(Token.TokenValue.DEFAULT))
                        {
                            if (menuInstr.hasDefaultCase)
                            {
                                throw new DefaultCaseException(lineNumber, colNumber);
                            }

                            menuInstr.addCase(parseCase());
                        }

                        nextTokenIfEquals(Token.TokenValue.RBRACE);

                        exps.Add(menuInstr);
                        break;

                    case Token.TokenValue.END:
                        nextToken();
                        nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        exps.Add(new EndInstruction());
                        break;

                    default:
                        parseEnd = true;
                        break;
                }
            }

            switch (exps.Count)
            {
                case 0:
                    return null;

                case 1:
                    return exps.ElementAt(0);

                default:
                    return new SequenceExpression(exps);
            }
        }

        public NpcMemberExpression parseNpcMemberExpression()
        {
            NpcMemberExpression npcMember = new NpcMemberExpression();

            //TODO
            if (currentTokenEquals(Token.TokenValue.STRING))
            {
                npcMember.npc = currentToken.stringValue;
                nextToken();

                if (currentTokenEquals(Token.TokenValue.FOURPOINTS))
                {
                    nextToken();

                    if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
                    {
                        npcMember.label = currentToken.stringValue;
                        nextToken();
                    }
                    else
                    {
                        throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
                    }
                }

                return npcMember;

            }

            throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
        }

        public bool isAnAssignOperator(Token.TokenValue op)
        {
            switch (op)
            {
                case Token.TokenValue.EQ:

                case Token.TokenValue.PLUSEQ:
                case Token.TokenValue.MINUSEQ:
                case Token.TokenValue.TIMESEQ:
                case Token.TokenValue.DIVEQ:

                case Token.TokenValue.PLUSPLUS:
                case Token.TokenValue.MINUSMINUS:
                case Token.TokenValue.TIMESTIMES:
                    return true;

                default:
                    return false;
            }
        }

        public SubSimpleExpression parseSubSimpleExpression()
        {
            SubSimpleExpression sse;

            nextTokenIfEquals(Token.TokenValue.LPAR);

            sse = new SubSimpleExpression(parseSimpleExpression());

            nextTokenIfEquals(Token.TokenValue.RPAR);

            return sse;
        }

        public SimpleExpression parseSimpleExpression()
        {
            Stack<SimpleExpression> exps = new Stack<SimpleExpression>();
            bool parseEnd = false;
            SimpleExpression e1, e2;
            Token.TokenValue currentOp;

            while (!parseEnd)
            {
                switch (currentToken.type)
                {

                    case Token.TokenValue.LPAR:
                        exps.Push(parseSubSimpleExpression());
                        break;

                    case Token.TokenValue.STRING:
                    case Token.TokenValue.CHAR:
                    case Token.TokenValue.INT:
                    case Token.TokenValue.BOOLEAN:
                        exps.Push(parseLiteralExpression());
                        break;

                    case Token.TokenValue.PLUS:
                    case Token.TokenValue.MINUS:
                    case Token.TokenValue.TIMES:
                    case Token.TokenValue.DIV:
                    case Token.TokenValue.LT:
                    case Token.TokenValue.GT:
                    case Token.TokenValue.LEQ:
                    case Token.TokenValue.GEQ:
                    case Token.TokenValue.AND:
                    case Token.TokenValue.OR:
                    case Token.TokenValue.EQBOOL:
                        e1 = exps.Pop();

                        currentOp = currentToken.type;
                        nextToken();

                        e2 = parseSimpleExpression();

                        if (e2 is BinaryOperatorExpression)
                        {
                            exps.Push(balanceBinaryExpression(e1, currentOp, (BinaryOperatorExpression)e2));
                        }
                        else
                        {
                            exps.Push(new BinaryOperatorExpression(currentOp, e1, e2));
                        }

                        return exps.Pop();

                    case Token.TokenValue.PLAYER_VALUE_IDENTIFIER:
                        exps.Push(new CharacterVariableExpression(currentToken.stringValue));
                        nextToken();
                        break;

                    case Token.TokenValue.VALUE_IDENTIFIER:
                        exps.Push(parseValueIdentifierExpression());
                        break;

                    default:
                        parseEnd = true;
                        break;
                }
            }

            return exps.Pop();
        }

        private BinaryOperatorExpression balanceBinaryExpression(SimpleExpression exp, Token.TokenValue op, BinaryOperatorExpression binExp)
        {
            if (tokenPriority(op) >= tokenPriority(binExp.op))
            {
                return new BinaryOperatorExpression(op, exp, binExp);
            }

            if (binExp.e1 is BinaryOperatorExpression)
            {
                return new BinaryOperatorExpression(binExp.op, balanceBinaryExpression(exp, op, (BinaryOperatorExpression)binExp.e1), binExp.e2);
            }

            return new BinaryOperatorExpression(binExp.op, new BinaryOperatorExpression(op, exp, binExp.e1), binExp.e2);
        }

        private SimpleExpression parseValueIdentifierExpression()
        {
            string valueName = currentToken.stringValue;

            nextTokenIfEquals(Token.TokenValue.VALUE_IDENTIFIER);

            if (currentTokenEquals(Token.TokenValue.LBRACKET))
            {
                nextToken();

                if (currentTokenEquals(Token.TokenValue.STRING))
                {
                    MemberAccessExpression memberAccess = new MemberAccessExpression(valueName, currentToken.stringValue);

                    nextTokenIfEquals(Token.TokenValue.RBRACKET);

                    return memberAccess;
                }
                else
                {
                    //erreur
                }
            }
            else
            {
                return new VariableExpression(valueName);
            }

            return null;
        }

        private PathExpression parsePathExpression()
        {
            string valueName, memberName;

            if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
            {
                valueName = currentToken.stringValue;

                nextToken();

                switch (currentToken.type)
                {
                    case Token.TokenValue.LBRACKET:
                        MemberAccessExpression memberAccess;

                        nextToken();

                        if (currentTokenEquals(Token.TokenValue.STRING))
                        {
                            memberName = currentToken.stringValue;
                            nextToken();

                            memberAccess = new MemberAccessExpression(valueName, memberName);

                            nextTokenIfEquals(Token.TokenValue.RBRACKET);

                            return memberAccess;
                        }
                        else
                        {
                            throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.STRING);
                        }

                    default:
                        return new VariableExpression(valueName);
                }
            }
            else
            {
                throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
            }
        }

        private static byte tokenPriority(Token.TokenValue tok)
        {
            switch (tok)
            {

                case Token.TokenValue.AND:
                    return 9;
                case Token.TokenValue.OR:
                    return 8;

                case Token.TokenValue.GT:
                case Token.TokenValue.LT:
                case Token.TokenValue.GEQ:
                case Token.TokenValue.LEQ:
                case Token.TokenValue.EQBOOL:
                    return 7;

                case Token.TokenValue.NEG:
                    return 6;

                case Token.TokenValue.PLUS:
                case Token.TokenValue.MINUS:
                    return 4;

                case Token.TokenValue.TIMES:
                case Token.TokenValue.DIV:
                    return 3;

                default:
                    return 0;
            }
        }

        public IfExpression parseIfExpression()
        {
            IfExpression ifExp = new IfExpression();
            ElseIfExpression elseIfExp;
            ElseExpression elseExp;

            nextTokenIfEquals(Token.TokenValue.IF);
            nextTokenIfEquals(Token.TokenValue.LPAR);

            if (currentTokenEquals(Token.TokenValue.RPAR))
            {
                throw new EmptyExpression(lineNumber, colNumber);
            }

            ifExp.condition = parseSimpleExpression();

            if (ifExp.condition.getExpressionType() != ObjectType.booleanType)
            {
                throw new IncompatibleTypesException(lineNumber, colNumber, ObjectType.booleanType, ifExp.condition.getExpressionType());
            }

            nextTokenIfEquals(Token.TokenValue.RPAR);
            nextTokenIfEquals(Token.TokenValue.LBRACE);

            //if(e){}
            if (currentTokenEquals(Token.TokenValue.RBRACE))
            {
                ifExp.body = null;
                nextToken();
            }
            //if(e){ e }
            else
            {
                ifExp.body = parseExpression();
                nextTokenIfEquals(Token.TokenValue.RBRACE);
            }

            while (currentTokenEquals(Token.TokenValue.ELSEIF))
            {
                elseIfExp = new ElseIfExpression();

                nextToken();
                nextTokenIfEquals(Token.TokenValue.LPAR);

                if (currentTokenEquals(Token.TokenValue.RPAR))
                {
                    throw new EmptyExpression(lineNumber, colNumber);
                }

                elseIfExp.condition = parseSimpleExpression();

                if (elseIfExp.condition.getExpressionType() != ObjectType.booleanType)
                {
                    throw new IncompatibleTypesException(lineNumber, colNumber, ObjectType.booleanType, elseIfExp.condition.getExpressionType());
                }

                nextTokenIfEquals(Token.TokenValue.RPAR);
                nextTokenIfEquals(Token.TokenValue.LBRACE);

                //else if(e){}
                if (currentTokenEquals(Token.TokenValue.RBRACE))
                {
                    elseIfExp.corps = null;
                    nextToken();
                }
                //else if(e){ e }
                else
                {
                    elseIfExp.corps = parseExpression();
                    nextTokenIfEquals(Token.TokenValue.RBRACE);
                }

                ifExp.addElseIf(elseIfExp);
            }

            if (currentTokenEquals(Token.TokenValue.ELSE))
            {
                elseExp = new ElseExpression();

                nextToken();
                nextTokenIfEquals(Token.TokenValue.LBRACE);

                //else{}
                if (currentTokenEquals(Token.TokenValue.RBRACE))
                {
                    elseExp.body = null;
                    nextToken();
                }
                //else{ e }
                else
                {
                    elseExp.body = parseExpression();
                    nextTokenIfEquals(Token.TokenValue.RBRACE);
                }

                ifExp.addElse(elseExp);

            }

            return ifExp;
        }

        public SwitchExpression parseSwitchExpression()
        {
            SwitchExpression switchExp = new SwitchExpression();

            nextTokenIfEquals(Token.TokenValue.SWITCH);
            nextTokenIfEquals(Token.TokenValue.LPAR);

            if (currentTokenEquals(Token.TokenValue.RPAR))
            {
                throw new EmptyExpression(lineNumber, colNumber);
            }

            switchExp.exp = parseSimpleExpression();

            nextTokenIfEquals(Token.TokenValue.RPAR);
            nextTokenIfEquals(Token.TokenValue.LBRACE);

            while (currentTokenEquals(Token.TokenValue.CASE))
            {
                if (switchExp.hasDefaultCase)
                {
                    throw new CaseException(lineNumber, colNumber);
                }

                switchExp.addCase(parseCase());
            }

            if (currentTokenEquals(Token.TokenValue.DEFAULT))
            {
                if (switchExp.hasDefaultCase)
                {
                    throw new DefaultCaseException(lineNumber, colNumber);
                }

                switchExp.addCase(parseCase());
            }

            nextTokenIfEquals(Token.TokenValue.RBRACE);

            return switchExp;
        }

        public SwitchCaseExpression parseCase()
        {
            CaseExpression caseExp;
            DefaultExpression defaultExp;

            if (currentTokenEquals(Token.TokenValue.CASE))
            {
                caseExp = new CaseExpression();

                nextToken();

                caseExp.literal = parseLiteralExpression();

                nextTokenIfEquals(Token.TokenValue.TWOPOINTS);

                //case 'a': break;
                if (currentTokenEquals(Token.TokenValue.BREAK))
                {
                    caseExp.body = null;
                    nextToken();
                    nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                }
                //case 'a':
                //case 'b': ...
                else if (currentTokenEquals(Token.TokenValue.CASE) || currentTokenEquals(Token.TokenValue.DEFAULT))
                {
                    caseExp.body = null;
                    caseExp.next = parseCase();
                }
                //case 'a':
                //  e
                //  break;
                else
                {
                    caseExp.body = parseExpression();
                    caseExp.next = null;

                    nextTokenIfEquals(Token.TokenValue.BREAK);
                    nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                }

                return caseExp;

            }
            else if (currentTokenEquals(Token.TokenValue.DEFAULT))
            {
                defaultExp = new DefaultExpression();

                nextToken();
                nextTokenIfEquals(Token.TokenValue.TWOPOINTS);

                //default: break;
                if (currentTokenEquals(Token.TokenValue.BREAK))
                {
                    defaultExp.body = null;
                    nextToken();
                    nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                }
                //default:
                //  e
                //  break;
                else
                {
                    defaultExp.body = parseExpression();
                    nextTokenIfEquals(Token.TokenValue.BREAK);
                    nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                }

                return defaultExp;
            }

            throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.CASE);
        }

        public ForExpression parseForExpression()
        {
            ForExpression forExp = new ForExpression();

            nextTokenIfEquals(Token.TokenValue.FOR);
            nextTokenIfEquals(Token.TokenValue.LPAR);

            if (currentTokenEquals(Token.TokenValue.RPAR))
            {
                throw new EmptyExpression(lineNumber, colNumber);
            }

            forExp.init = parseAssignExpression();

            nextTokenIfEquals(Token.TokenValue.SEMICOLON);

            forExp.condition = parseSimpleExpression();

            if (forExp.condition.getExpressionType() != ObjectType.booleanType)
            {
                throw new IncompatibleTypesException(lineNumber, colNumber, ObjectType.booleanType, forExp.condition.getExpressionType());
            }

            nextTokenIfEquals(Token.TokenValue.SEMICOLON);

            forExp.maj = parseAssignExpression();

            nextTokenIfEquals(Token.TokenValue.RPAR);
            nextTokenIfEquals(Token.TokenValue.LBRACE);

            //for(a; b; c){ }
            if (currentTokenEquals(Token.TokenValue.RBRACE))
            {
                forExp.body = null;
                nextToken();
            }
            //for(a; b; c){ e }
            else
            {
                forExp.body = parseExpression();
                nextTokenIfEquals(Token.TokenValue.RBRACE);
            }

            return forExp;
        }

        public AssignExpression parseAssignExpression()
        {
            AssignExpression assignExp = new AssignExpression();
            string varName, opName;
            SimpleExpression value;
            Token.TokenValue op;

            if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
            {
                varName = currentToken.stringValue;
                assignExp.name = varName;

                nextToken();

                switch (currentToken.type)
                {
                    case Token.TokenValue.EQ:
                        nextToken();
                        assignExp.value = parseSimpleExpression();

                        return assignExp;

                    case Token.TokenValue.PLUSEQ:
                    case Token.TokenValue.MINUSEQ:
                    case Token.TokenValue.TIMESEQ:
                    case Token.TokenValue.DIVEQ:
                        op = currentToken.type;

                        nextToken();
                        value = parseSimpleExpression();

                        opName = op.ToString();

                        assignExp.value = new BinaryOperatorExpression(
                            (Token.TokenValue)Enum.Parse(typeof(Token.TokenValue), opName.Substring(0, opName.Length - 2)),
                            new VariableExpression(assignExp.name),
                            value
                            );

                        return assignExp;

                    case Token.TokenValue.PLUSPLUS:
                        nextToken();

                        assignExp.value = new BinaryOperatorExpression(
                            Token.TokenValue.PLUS,
                            new VariableExpression(assignExp.name),
                            new LiteralExpression(new Token(Token.TokenValue.INT, 1))
                            );

                        return assignExp;

                    case Token.TokenValue.MINUSMINUS:
                        nextToken();

                        assignExp.value = new BinaryOperatorExpression(
                            Token.TokenValue.MINUS,
                            new VariableExpression(assignExp.name),
                            new LiteralExpression(new Token(Token.TokenValue.INT, 1))
                            );

                        return assignExp;

                    case Token.TokenValue.TIMESTIMES:
                        nextToken();

                        assignExp.value = new BinaryOperatorExpression(
                            Token.TokenValue.TIMES,
                            new VariableExpression(assignExp.name),
                            new VariableExpression(assignExp.name)
                            );

                        return assignExp;

                }
            }

            throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
        }

        public WhileExpression parseWhileExpression()
        {
            WhileExpression whileExp = new WhileExpression();

            nextTokenIfEquals(Token.TokenValue.WHILE);
            nextTokenIfEquals(Token.TokenValue.LPAR);

            if (currentTokenEquals(Token.TokenValue.RPAR))
            {
                throw new EmptyExpression(lineNumber, colNumber);
            }

            whileExp.condition = parseSimpleExpression();

            if (whileExp.condition.getExpressionType() != ObjectType.booleanType)
            {
                throw new IncompatibleTypesException(lineNumber, colNumber, ObjectType.booleanType, whileExp.condition.getExpressionType());
            }

            nextTokenIfEquals(Token.TokenValue.RPAR);
            nextTokenIfEquals(Token.TokenValue.LBRACE);

            //while(e){}
            if (currentTokenEquals(Token.TokenValue.RBRACE))
            {
                whileExp.body = null;
                nextToken();
            }
            //while(e){ e }
            else
            {
                whileExp.body = parseExpression();
                nextTokenIfEquals(Token.TokenValue.RBRACE);
            }

            return whileExp;
        }

        public DoWhileExpression parseDoWhileExpression()
        {
            DoWhileExpression doWhileExp = new DoWhileExpression();

            nextTokenIfEquals(Token.TokenValue.DO);
            nextTokenIfEquals(Token.TokenValue.LBRACE);

            //do{ }while(e);
            if (currentTokenEquals(Token.TokenValue.RBRACE))
            {
                doWhileExp.body = null;

                nextToken();
            }
            //do{ e }while(e);
            else
            {
                doWhileExp.body = parseExpression();

                nextTokenIfEquals(Token.TokenValue.RBRACE);
            }

            nextTokenIfEquals(Token.TokenValue.WHILE);
            nextTokenIfEquals(Token.TokenValue.LPAR);

            if (currentTokenEquals(Token.TokenValue.RPAR))
            {
                throw new EmptyExpression(lineNumber, colNumber);
            }

            doWhileExp.condition = parseSimpleExpression();

            if (doWhileExp.condition.getExpressionType() != ObjectType.booleanType)
            {
                throw new IncompatibleTypesException(lineNumber, colNumber, ObjectType.booleanType, doWhileExp.condition.getExpressionType());
            }

            nextTokenIfEquals(Token.TokenValue.RPAR);
            nextTokenIfEquals(Token.TokenValue.SEMICOLON);

            return doWhileExp;
        }

        public LiteralExpression parseLiteralExpression()
        {
            LiteralExpression lit;

            switch (currentToken.type)
            {
                case Token.TokenValue.STRING:
                case Token.TokenValue.CHAR:
                case Token.TokenValue.INT:
                case Token.TokenValue.BOOLEAN:
                    lit = new LiteralExpression(currentToken);
                    nextToken();
                    return lit;

                default:
                    throw new UndefinedException(lineNumber, colNumber);
            }
        }

        public void previousToken()
        {
            indiceToken--;

            if (indiceToken < 0)
            {
                throw new Exception();
            }

            currentToken = lexBuf.ElementAt(indiceToken);
        }

        public void nextToken()
        {
            if (indiceToken == lexBuf.Count - 1)
            {
                if (codeToParse == string.Empty)
                {

                }
                lexCode();
            }

            indiceToken++;

            if (indiceToken >= lexBuf.Count)
            {
                throw new Exception();
            }

            currentToken = lexBuf.ElementAt(indiceToken);
        }

        public void nextTokenIfEquals(Token.TokenValue value)
        {
            if (currentTokenEquals(value))
            {
                nextToken();
            }
            else
            {
                throw new SyntaxError(lineNumber, colNumber, value);
            }
        }

        public bool currentTokenEquals(Token.TokenValue value)
        {
            return (currentToken.type == value);
        }

        public void printParse(string line)
        {
            IrisPrinter.Printer.println(line);
        }

        public bool printError(string error)
        {
            IrisPrinter.Printer.printError(error, lineNumber, colNumber);
            return false;
        }

        public void printContent()
        {
            StringBuilder str = new StringBuilder();

            foreach (Token tok in lexBuf)
            {
                str.Append(tok.ToString() + ' ');
            }

            printParse(str.ToString());
        }
    }
}
