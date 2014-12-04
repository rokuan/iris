using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.ScriptElements;
using System.Text.RegularExpressions;
using Iris.ParserUtils.LexParseException;
using Iris.SimpleExpressionElement;
using Iris.Data;
using Iris.ExpressionElement;
using Iris.ExpressionElement.Instruction;
using System.IO;

namespace Iris.ParserUtils
{
    class ContentParser
    {
        private StreamReader fileStream;
        public IrisScript script;

        public List<Token> lexBuf = new List<Token>();

        //

        private int lineNumber = 1;
        private int colNumber = 1;

        private int indiceToken = 0;
        private Token currentToken;

        //REGEX

        //private readonly static String layout = @"(\s)+";

        private readonly static String valueIdExp = @"[a-z](\w)*";

        private readonly static String intExp = "[0-9]";

        private readonly static Regex valueIdRegex = new Regex("^" + valueIdExp + @"[\W]");
        private readonly static Regex intRegex = new Regex("^" + intExp + @"[\W]");

        private String codeToParse;

        public ContentParser()
        {
            script = new IrisScript();
        }

        public void parseCode(String code)
        {
            codeToParse = code;
            startParse();
        }

        public void lexCode()
        {
            int indiceCode = 0, decalage, indiceComment, indiceNewLine;
            String strId;

            if (codeToParse == "")
            {
                codeToParse = fileStream.ReadLine();

                while (codeToParse == string.Empty)
                {
                    codeToParse = fileStream.ReadLine();

                    if (codeToParse == null)
                    {
                        lexBuf.Add(new Token(Token.TokenValue.EOF));
                        return;
                    }
                }

                if (codeToParse == null)
                {
                    lexBuf.Add(new Token(Token.TokenValue.EOF));
                    return;
                }

                lineNumber++;
                colNumber = 1;
            }

            switch (codeToParse[0])
            {
                /*case '\n':
                    colNumber = 1;
                    lineNumber++;
                    codeToParse = codeToParse.Substring(1);
                    lexCode();
                    return;*/

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

                case ';':
                    lexBuf.Add(new Token(Token.TokenValue.SEMICOLON));
                    break;

                case '=':
                    lexBuf.Add(new Token(Token.TokenValue.EQ));
                    break;

                case '/':
                    if (codeToParse.Length > 1 && codeToParse[1] == '*')
                    {
                        codeToParse = codeToParse.Substring(2);
                        colNumber += 2;

                        while ((indiceComment = codeToParse.IndexOf("*/")) == -1)
                        {
                            codeToParse = fileStream.ReadLine();

                            if (codeToParse == null)
                            {
                                throw new UnfinishedCommentary(lineNumber, colNumber);
                            }

                            lineNumber++;
                            colNumber = 1;
                        }

                        colNumber += indiceComment + 2;
                        codeToParse = codeToParse.Substring(indiceComment + 2);

                        lexCode();
                        return;
                    }
                    else
                    {
                        throw new InvalidSequence(lineNumber, colNumber);
                    }         

                case '"':
                    colNumber++;
                    indiceCode++;

                    codeToParse = codeToParse.Substring(1);

                    lexString();
                    return;

                default:
                    if (isPrefix(codeToParse, "character"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.CHARACTER));

                        indiceCode += 8;
                        colNumber += 8;
                    }
                    else if (isPrefix(codeToParse, "image"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.IMAGE));

                        indiceCode += 4;
                        colNumber += 4;
                    }
                    else if (isPrefix(codeToParse, "sound"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.SOUND));

                        indiceCode += 4;
                        colNumber += 4;
                    }
                    else if (isPrefix(codeToParse, "background"))
                    {
                        lexBuf.Add(new Token(Token.TokenValue.BACKGROUND));

                        indiceCode += 9;
                        colNumber += 9;
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
                    else if (valueIdRegex.IsMatch(codeToParse))
                    {
                        strId = lexIdentifier();

                        lexBuf.Add(new Token(Token.TokenValue.VALUE_IDENTIFIER, strId));

                        decalage = strId.Length - 1;
                        indiceCode += decalage;
                        colNumber += decalage;
                    }
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
                codeToParse = string.Empty;
                return;
            }

            codeToParse = codeToParse.Substring(indiceCode);
        }

        public Boolean isPrefix(String code, String prefix)
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

        public String lexIdentifier()
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

        public void lexString()
        {
            StringBuilder strVal = new StringBuilder();
            char currentChar;
            int indiceCodeStr = 0, codeLength = codeToParse.Length;

            if (codeToParse == String.Empty)
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

        public bool startParse(string filepath)
        {
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

                        case Token.TokenValue.CHARACTER:
                            script.addCharacter(parseCharacter());
                            break;

                        case Token.TokenValue.IMAGE:
                            script.addImage(parseImage());
                            break;

                        case Token.TokenValue.SOUND:
                            script.addSound(parseSound());
                            break;

                        case Token.TokenValue.BACKGROUND:
                            script.addBackground(parseBackground());
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

        public IrisCharacter parseCharacter()
        {
            IrisCharacter charac = new IrisCharacter();
            String fieldName, fieldValue;

            nextTokenIfEquals(Token.TokenValue.CHARACTER);

            if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
            {
                charac.name = currentToken.stringValue;

                nextToken();

                nextTokenIfEquals(Token.TokenValue.LBRACE);

                while (!currentTokenEquals(Token.TokenValue.RBRACE))
                {
                    if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
                    {
                        fieldName = currentToken.stringValue;

                        nextToken();

                        nextTokenIfEquals(Token.TokenValue.EQ);

                        //name = "name";
                        if (fieldName.Equals("name"))
                        {
                            if (currentTokenEquals(Token.TokenValue.STRING))
                            {
                                charac.charName = currentToken.stringValue;

                                nextToken();

                                nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                            }
                            else
                            {
                                throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.STRING);
                            }
                        }
                        //color = "#0077FF";
                        else if (fieldName.Equals("color"))
                        {
                            if (currentTokenEquals(Token.TokenValue.STRING))
                            {
                                charac.setColor(currentToken.stringValue);
                            }
                            else
                            {
                                throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.STRING);
                            }
                        }
                        //images = {
                        //  happy = "happy_and_cool.png";
                        //  light = "emotions/shining.png";
                        //  ..... = ....................;
                        //};
                        else if (fieldName.Equals("images"))
                        {
                            nextTokenIfEquals(Token.TokenValue.LBRACE);

                            while (!currentTokenEquals(Token.TokenValue.RBRACE))
                            {
                                if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
                                {
                                    fieldName = currentToken.stringValue;
                                    nextToken();

                                    nextTokenIfEquals(Token.TokenValue.EQ);

                                    if (currentTokenEquals(Token.TokenValue.STRING))
                                    {
                                        fieldValue = currentToken.stringValue;

                                        nextToken();
                                        nextTokenIfEquals(Token.TokenValue.SEMICOLON);

                                        charac.addImage(fieldName, fieldValue);
                                    }
                                    else
                                    {
                                        throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.STRING);
                                    }
                                }
                                else
                                {
                                    throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
                                }
                            }

                            nextTokenIfEquals(Token.TokenValue.RBRACE);
                            nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                        }
                        else
                        {
                            throw new UnknownField(lineNumber, colNumber, charac.name, fieldName);
                        }
                    }
                }

                nextTokenIfEquals(Token.TokenValue.RBRACE);
                //nextTokenIfEquals(Token.TokenValue.SEMICOLON);

                return charac;
            }
            else
            {
                throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
            }

            //erreur
            //return null;
        }

        public IrisImage parseImage()
        {
            IrisImage image = new IrisImage();
            String fieldName, fieldValue;

            nextTokenIfEquals(Token.TokenValue.IMAGE);

            if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
            {
                fieldName = currentToken.stringValue;

                nextToken();

                nextTokenIfEquals(Token.TokenValue.EQ);

                if (fieldName.Equals("stretch"))
                {

                }
            }
            else
            {
                throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
            }

            return image;
        }

        public IrisSound parseSound()
        {
            IrisSound sound = new IrisSound();
            String fieldName, fieldValue;

            nextTokenIfEquals(Token.TokenValue.SOUND);

            if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
            {
                sound.name = currentToken.stringValue;

                nextToken();

                nextTokenIfEquals(Token.TokenValue.LBRACE);

                while (!currentTokenEquals(Token.TokenValue.RBRACE))
                {
                    if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
                    {
                        fieldName = currentToken.stringValue;

                        nextToken();

                        nextTokenIfEquals(Token.TokenValue.EQ);

                        if (fieldName.Equals("source"))
                        {
                            if (currentTokenEquals(Token.TokenValue.STRING))
                            {
                                fieldValue = currentToken.stringValue;

                                nextToken();

                                nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                                sound.setSource(fieldValue);
                            }
                            else
                            {

                            }
                        }
                        else
                        {

                        }
                    }
                    else
                    {
                        throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
                    }
                }

                nextTokenIfEquals(Token.TokenValue.RBRACE);
                
            }
            else
            {
                throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
            }

            return sound;
        }

        public IrisBackground parseBackground()
        {
            IrisBackground bgd = new IrisBackground();
            String fieldName, fieldValue;

            nextTokenIfEquals(Token.TokenValue.BACKGROUND);

            if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
            {
                bgd.name = currentToken.stringValue;

                nextToken();

                nextTokenIfEquals(Token.TokenValue.LBRACE);

                while (!currentTokenEquals(Token.TokenValue.RBRACE))
                {
                    if (currentTokenEquals(Token.TokenValue.VALUE_IDENTIFIER))
                    {
                        fieldName = currentToken.stringValue;

                        nextToken();

                        nextTokenIfEquals(Token.TokenValue.EQ);

                        if (fieldName.Equals("source"))
                        {
                            if (currentTokenEquals(Token.TokenValue.STRING))
                            {
                                fieldValue = currentToken.stringValue;

                                nextToken();

                                nextTokenIfEquals(Token.TokenValue.SEMICOLON);
                                bgd.setImage(fieldValue);
                            }
                            else
                            {

                            }
                        }
                        else
                        {

                        }
                    }
                    else
                    {
                        throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
                    }
                }

                nextTokenIfEquals(Token.TokenValue.RBRACE);
            }
            else
            {
                throw new SyntaxError(lineNumber, colNumber, Token.TokenValue.VALUE_IDENTIFIER);
            }

            return bgd;
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
                lexCode();
            }

            indiceToken++;

            if (indiceToken >= lexBuf.Count)
            {
                //erreur
                //return;
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

        public Boolean currentTokenEquals(Token.TokenValue value)
        {
            return (currentToken.type == value);
        }

        public void printParse(String line)
        {
            IrisPrinter.Printer.println(line);
        }

        public Boolean printError(String error)
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
