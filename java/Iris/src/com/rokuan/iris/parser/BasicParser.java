package com.rokuan.iris.parser;

import java.util.ArrayList;

import com.rokuan.iris.exception.BaseException;
import com.rokuan.iris.exception.NoNextTokenException;
import com.rokuan.iris.exception.NoPreviousTokenException;
import com.rokuan.iris.exception.SyntaxError;
import com.rokuan.iris.exception.UndefinedException;
import com.rokuan.iris.exception.UnfinishedCommentary;
import com.rokuan.iris.exception.UnfinishedString;
import com.rokuan.iris.expression.LiteralExpression;
import com.rokuan.iris.interfaces.IIrisResources;
import com.rokuan.iris.parser.Token.TokenValue;
import com.rokuan.iris.process.IIrisStreamReader;

public abstract class BasicParser {
	protected ArrayList<Token> lexBuf = new ArrayList<Token>();
	protected int tokenIndex = 0;
	protected Token currentToken;

	protected String codeToParse = "";

	protected int lineNumber = 0;
	protected int colNumber = 1;
	
	protected IIrisStreamReader reader;
	protected IIrisResources data;
	
	public BasicParser(IIrisStreamReader rd, IIrisResources loader){
		this.reader = rd;
		this.data = loader;
	}
	
	protected abstract void lexCode() throws BaseException;
	protected abstract void parseCode() throws BaseException;
	
	public final void startProcess() throws Exception {
		if (codeToParse.isEmpty()) {
			do {
				if (reader.hasNextLine()) {
					codeToParse = reader.nextLine();
				} else {
					lexBuf.add(new Token(TokenValue.EOF));
					return;
				}

				lineNumber++;
				colNumber = 1;
			} while (codeToParse.isEmpty());
		}
		
		boolean endOfInput = false;
		
		while(true){			
			if (codeToParse.isEmpty()) {
				do {
					if (reader.hasNextLine()) {
						codeToParse = reader.nextLine();
					} else {
						lexBuf.add(new Token(TokenValue.EOF));
						endOfInput = true;
						break;
					}

					lineNumber++;
					colNumber = 1;
				} while (codeToParse.isEmpty());
			}			
			
			if(endOfInput){
				break;
			}
			
			lexCode();
		}
		
		if(this.lexBuf.isEmpty()){
			return;
		}
		
		//System.out.println(this.lexBuf);
		this.currentToken = this.lexBuf.get(0);
		
		parseCode();
	}
	
	/*
	 * LEXER
	 */
	
	protected final boolean isPrefix(String code, String prefix)
	{
		if (code.startsWith(prefix))
		{
			if (code.length() > prefix.length())
			{
				return !Character.isLetterOrDigit(code.charAt(prefix.length())) && code.charAt(prefix.length()) != '_';
			}

			return true;
		}

		return false;
	}
	
	protected final String lexIdentifier() {
		StringBuilder str = new StringBuilder();
		int indiceId = 0;

		while (Character.isLetterOrDigit(codeToParse.charAt(indiceId))
				|| codeToParse.charAt(indiceId) == '_') {
			str.append(codeToParse.charAt(indiceId));
			indiceId++;
		}

		return str.toString();
	}

	protected final void lexString() throws BaseException {
		StringBuilder strVal = new StringBuilder();
		char currentChar;
		int codeIndexStr = 0, codeLength = codeToParse.length();

		if (codeToParse.isEmpty()) {
			throw new UnfinishedString(lineNumber, colNumber);
		}
		
		if(codeToParse.charAt(0) == '"'){
			codeIndexStr++;
			colNumber++;
		} else {
			/* Should not happen */
			//throw new BaseException("String should start with");
			return;
		}

		while ((currentChar = codeToParse.charAt(codeIndexStr)) != '"'
				&& codeIndexStr < codeLength) {
			if (currentChar == '\\') {
				if (codeIndexStr + 1 < codeLength) {
					if (codeToParse.charAt(codeIndexStr + 1) == '"') {
						strVal.append('"');
					} else if (codeToParse.charAt(codeIndexStr + 1) == '\\') {
						strVal.append('\\');
					}

					codeIndexStr++;
				} else {
					throw new UnfinishedString(lineNumber, colNumber);
				}
			} else {
				strVal.append(currentChar);
			}

			colNumber++;
			codeIndexStr++;

			if (codeIndexStr >= codeLength) {
				throw new UnfinishedString(lineNumber, colNumber);
			}
		}

		lexBuf.add(new Token(TokenValue.STRING, strVal.toString()));

		// +1 : apres le guillemet fermant
		codeToParse = codeToParse.substring(codeIndexStr + 1);
	}
	
	protected final void lexCommentary() throws BaseException {
		int commentIndex = 0;
		
		if(codeToParse.startsWith("/*")){
			colNumber += 2;
			codeToParse = codeToParse.substring(2);			

			while ((commentIndex = codeToParse.indexOf("*/")) == -1) {
				if (!reader.hasNextLine()) {
					throw new UnfinishedCommentary(lineNumber,
							colNumber);
				}

				codeToParse = reader.nextLine();

				lineNumber++;
				colNumber = 1;
			}

			colNumber += commentIndex + 2;
			codeToParse = codeToParse.substring(commentIndex + 2);
		}
	}
	
	/*
	 * TOKEN
	 */
	
	public void previousToken() throws BaseException {
		tokenIndex--;

		if (tokenIndex < 0) {
			throw new NoPreviousTokenException(lineNumber, colNumber);
		}

		currentToken = lexBuf.get(tokenIndex);
	}

	public void nextToken() throws BaseException {
		tokenIndex++;

		if (tokenIndex >= lexBuf.size()) {
			// erreur
			// return;
			throw new NoNextTokenException(lineNumber, colNumber);
		}

		currentToken = lexBuf.get(tokenIndex);
	}

	protected void nextTokenIfEquals(Token.TokenValue value) throws BaseException {
		if (currentTokenEquals(value)) {
			nextToken();
		} else {
			throw new SyntaxError(lineNumber, colNumber, value);
		}
	}

	protected boolean currentTokenEquals(Token.TokenValue value) {
		return (currentToken.type == value);
	}
	
	/*
	 * EXPRESSION
	 */
	
	public LiteralExpression parseLiteralExpression() throws BaseException {
		LiteralExpression lit;

		switch (currentToken.type) {
		case STRING:
		case CHAR:
		case INT:
		case BOOLEAN:
			lit = new LiteralExpression(currentToken);
			nextToken();
			return lit;

		default:
			throw new UndefinedException(lineNumber, colNumber);
		}
	}
}