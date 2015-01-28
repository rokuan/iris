package com.rokuan.iris.parser;

import java.util.Stack;
import java.util.ArrayList;

import com.rokuan.iris.enums.Constants;
import com.rokuan.iris.exception.BaseException;
import com.rokuan.iris.exception.CaseException;
import com.rokuan.iris.exception.DefaultCaseException;
import com.rokuan.iris.exception.EmptyExpression;
import com.rokuan.iris.exception.IncompatibleTypesException;
import com.rokuan.iris.exception.InvalidNpcException;
import com.rokuan.iris.exception.InvalidSequence;
import com.rokuan.iris.exception.SyntaxError;
import com.rokuan.iris.expression.BinaryOperatorExpression;
import com.rokuan.iris.expression.CaseExpression;
import com.rokuan.iris.expression.DefaultExpression;
import com.rokuan.iris.expression.DoWhileExpression;
import com.rokuan.iris.expression.ElseExpression;
import com.rokuan.iris.expression.ElseIfExpression;
import com.rokuan.iris.expression.Expression;
import com.rokuan.iris.expression.ForExpression;
import com.rokuan.iris.expression.IfExpression;
import com.rokuan.iris.expression.LabelExpression;
import com.rokuan.iris.expression.LiteralExpression;
import com.rokuan.iris.expression.MacroCallExpression;
import com.rokuan.iris.expression.MemberAccessExpression;
import com.rokuan.iris.expression.PathExpression;
import com.rokuan.iris.expression.PlayerIntVariableExpression;
import com.rokuan.iris.expression.PlayerStringVariableExpression;
import com.rokuan.iris.expression.SequenceExpression;
import com.rokuan.iris.expression.SimpleExpression;
import com.rokuan.iris.expression.SubSimpleExpression;
import com.rokuan.iris.expression.SwitchCaseExpression;
import com.rokuan.iris.expression.SwitchExpression;
import com.rokuan.iris.expression.VariableExpression;
import com.rokuan.iris.expression.WhileExpression;
import com.rokuan.iris.expression.MacroCallExpression.MacroValue;
import com.rokuan.iris.instruction.AssignExpression;
import com.rokuan.iris.instruction.CleanBackgroundInstruction;
import com.rokuan.iris.instruction.CleanForegroundInstruction;
import com.rokuan.iris.instruction.CloseMsgDialogInstruction;
import com.rokuan.iris.instruction.EndInstruction;
import com.rokuan.iris.instruction.GotoInstruction;
import com.rokuan.iris.instruction.HideImageInstruction;
import com.rokuan.iris.instruction.InputInstruction;
import com.rokuan.iris.instruction.MenuInstruction;
import com.rokuan.iris.instruction.MsgDisplayInstruction;
import com.rokuan.iris.instruction.NextMsgDialogInstruction;
import com.rokuan.iris.instruction.NpcMemberExpression;
import com.rokuan.iris.instruction.OpenMsgDialogInstruction;
import com.rokuan.iris.instruction.PlayMediaInstruction;
import com.rokuan.iris.instruction.RemoveCharacter;
import com.rokuan.iris.instruction.SetBackgroundInstruction;
import com.rokuan.iris.instruction.SetVariableInstruction;
import com.rokuan.iris.instruction.ShowImageInstruction;
import com.rokuan.iris.instruction.StopMediaInstruction;
import com.rokuan.iris.instruction.SwitchCharacterInstruction;
import com.rokuan.iris.instruction.ValueExpression;
import com.rokuan.iris.interfaces.IIrisResources;
import com.rokuan.iris.parser.Token.TokenValue;
import com.rokuan.iris.process.IIrisStreamReader;
import com.rokuan.iris.script.NpcStructure;
import com.rokuan.iris.types.ObjectType;

public class ScriptParser extends BasicParser {
	private static final String[] patterns = { "elseif", "if", "else",
			"switchchar", "removechar", "end", "switch", "case", "default",
			"break", "setbgd", "cleanfgd", "cleanbgd", "for", "while", "do",
			"npc", "function", "set", "show", "hide", "play", "stop", "open",
			"msg", "next", "close", "input", "menu", "goto", "end"
	/*
	 * , "countitem", "getitem", "deleteitem"
	 */
	};

	private static final Token[] tokens = { new Token(TokenValue.ELSEIF),
			new Token(TokenValue.IF), new Token(TokenValue.ELSE),
			new Token(TokenValue.SWITCHCHAR), new Token(TokenValue.REMOVECHAR),
			new Token(TokenValue.END), new Token(TokenValue.SWITCH),
			new Token(TokenValue.CASE), new Token(TokenValue.DEFAULT),
			new Token(TokenValue.BREAK), new Token(TokenValue.SETBGD),
			new Token(TokenValue.CLEANFGD), new Token(TokenValue.CLEANBGD),
			new Token(TokenValue.FOR), new Token(TokenValue.WHILE),
			new Token(TokenValue.DO), new Token(TokenValue.NPC),
			new Token(TokenValue.FUNCTION), new Token(TokenValue.SET),
			new Token(TokenValue.SHOW), new Token(TokenValue.HIDE),
			new Token(TokenValue.PLAY), new Token(TokenValue.STOP),
			new Token(TokenValue.OPENDLG), new Token(TokenValue.MSG),
			new Token(TokenValue.NEXTDLG), new Token(TokenValue.CLOSEDLG),
			new Token(TokenValue.INPUT), new Token(TokenValue.MENU),
			new Token(TokenValue.GOTO), new Token(TokenValue.END)
	/*
	 * , new Token(TokenValue .COUNTITEM), new Token(TokenValue .GETITEM), new
	 * Token(TokenValue .DELETEITEM)
	 */
	};

	public ScriptParser(IIrisStreamReader rd, IIrisResources loader) {
		super(rd, loader);
	}

	protected void lexCode() throws BaseException {
		while (codeToParse != null && !codeToParse.isEmpty()) {
			int codeIndex = 0, colStep = 1;
			String strId;
			boolean charCatch = true;

			switch (codeToParse.charAt(0)) {
			case '\t':
			case '\b':
			case ' ':
				colNumber++;
			case '\r':
				codeToParse = codeToParse.substring(1);
				continue;

			case '{':
				lexBuf.add(new Token(TokenValue.LBRACE));
				break;

			case '}':
				lexBuf.add(new Token(TokenValue.RBRACE));
				break;

			case '(':
				lexBuf.add(new Token(TokenValue.LPAR));
				break;

			case ')':
				lexBuf.add(new Token(TokenValue.RPAR));
				break;

			case '[':
				lexBuf.add(new Token(TokenValue.LBRACKET));
				break;

			case ']':
				lexBuf.add(new Token(TokenValue.RBRACKET));
				break;

			case ':':
				if (codeToParse.length() > 1 && codeToParse.charAt(1) == ':') {
					lexBuf.add(new Token(TokenValue.FOURPOINTS));
					colStep = 2;
				} else {
					lexBuf.add(new Token(TokenValue.TWOPOINTS));
				}
				break;

			case ',':
				lexBuf.add(new Token(TokenValue.COMMA));
				break;

			case '.':
				lexBuf.add(new Token(TokenValue.DOT));
				break;

			case ';':
				lexBuf.add(new Token(TokenValue.SEMICOLON));
				break;

			case '>':
				if (codeToParse.length() > 1 && codeToParse.charAt(1) == '=') {
					lexBuf.add(new Token(TokenValue.GEQ));
					colStep = 2;
				} else {
					lexBuf.add(new Token(TokenValue.GT));
				}
				break;

			case '<':
				if (codeToParse.length() > 1 && codeToParse.charAt(1) == '=') {
					lexBuf.add(new Token(TokenValue.LEQ));
					colStep = 2;
				} else {
					lexBuf.add(new Token(TokenValue.LT));
				}
				break;

			case '=':
				if (codeToParse.length() > 1 && codeToParse.charAt(1) == '=') {
					lexBuf.add(new Token(TokenValue.EQBOOL));
					colStep = 2;
				} else {
					lexBuf.add(new Token(TokenValue.EQ));
				}
				break;

			case '&':
				if (codeToParse.length() > 1 && codeToParse.charAt(1) == '&') {
					lexBuf.add(new Token(TokenValue.AND));
					colStep = 2;
				} else {
					throw new InvalidSequence(lineNumber, colNumber);
				}
				break;

			case '|':
				if (codeToParse.length() > 1 && codeToParse.charAt(1) == '|') {
					lexBuf.add(new Token(TokenValue.OR));
					colStep = 2;
				} else {
					throw new InvalidSequence(lineNumber, colNumber);
				}
				break;

			case '+':
				if (codeToParse.length() > 1) {
					colStep = lexOperator("PLUS", '+', codeToParse.charAt(1));
				} else {
					lexBuf.add(new Token(TokenValue.PLUS));
				}
				break;

			case '-':
				if (codeToParse.length() > 1) {
					colStep = lexOperator("MINUS", '-', codeToParse.charAt(1));
				} else {
					lexBuf.add(new Token(TokenValue.MINUS));
				}
				break;

			case '*':
				if (codeToParse.length() > 1) {
					colStep = lexOperator("TIMES", '*', codeToParse.charAt(1));
				} else {
					lexBuf.add(new Token(TokenValue.TIMES));
				}
				break;

			case '/':
				if (codeToParse.length() > 1) {
					if (codeToParse.charAt(1) == '*') {
						lexCommentary();
						continue;
					} else {
						colStep = lexOperator("DIV", '/', codeToParse.charAt(1));
					}
				} else {
					lexBuf.add(new Token(TokenValue.DIV));
				}
				break;

			/*
			 * case '\'': lexChar(); continue;
			 */

			case '"':
				lexString();
				continue;

			default:
				charCatch = false;
				break;
			}

			if (!charCatch) {
				if (codeToParse.charAt(0) >= '0'
						&& codeToParse.charAt(0) <= '9') {
					lexInt();
					continue;
				} else if (isPrefix(codeToParse, "true")) {
					lexBuf.add(new Token(TokenValue.BOOLEAN, true));
				} else if (isPrefix(codeToParse, "false")) {
					lexBuf.add(new Token(TokenValue.BOOLEAN, false));
				} else if (Character.isLowerCase(codeToParse.charAt(0))) {
					boolean patternMatch = false;

					for (int i = 0; i < patterns.length; i++) {
						if (isPrefix(codeToParse, patterns[i])) {
							colStep = patterns[i].length();
							lexBuf.add(tokens[i]);
							patternMatch = true;
							break;
						}
					}

					if (!patternMatch) {
						strId = lexIdentifier();
						lexBuf.add(new Token(TokenValue.VALUE_IDENTIFIER, strId));
						colStep = strId.length();
					}
				} else if (codeToParse.charAt(0) == '$') {
					if (codeToParse.length() == 1) {
						throw new InvalidSequence(lineNumber, colNumber);
					}

					colNumber++;
					codeToParse = codeToParse.substring(1);
					strId = lexIdentifier();
					lexBuf.add(new Token(
							TokenValue.PLAYER_STRING_VALUE_IDENTIFIER, strId));
					colStep = strId.length();
				} else if (codeToParse.charAt(0) == '#') {
					if (codeToParse.length() == 1) {
						throw new InvalidSequence(lineNumber, colNumber);
					}

					colNumber++;
					codeToParse = codeToParse.substring(1);
					strId = lexIdentifier();
					lexBuf.add(new Token(
							TokenValue.PLAYER_INT_VALUE_IDENTIFIER, strId));
					colStep = strId.length();
				} else if (codeToParse.charAt(0) == '@') {
					if (codeToParse.length() == 1) {
						throw new InvalidSequence(lineNumber, colNumber);
					}

					colNumber++;
					codeToParse = codeToParse.substring(1);
					strId = lexIdentifier();
					lexBuf.add(new Token(TokenValue.MACRO_IDENTIFIER, strId));
					colStep = strId.length();
				} else if (Character.isUpperCase(codeToParse.charAt(0))) {
					strId = lexIdentifier();
					lexBuf.add(new Token(TokenValue.TYPE_IDENTIFIER, strId));
					colStep = strId.length();
				} else {
					throw new InvalidSequence(lineNumber, colNumber);
				}
			}

			colNumber += colStep;
			codeIndex += colStep;
			codeToParse = codeToParse.substring(codeIndex);
		}
	}

	// TODO
	private void lexChar() {
		// char c = '\0';

		// return lexCode(code);
	}

	// OKAY
	private void lexInt() {
		StringBuilder intVal = new StringBuilder();
		int codeIndexInt = 0;

		while (Character.isDigit(codeToParse.charAt(codeIndexInt))) {
			intVal.append(codeToParse.charAt(codeIndexInt));

			colNumber++;
			codeIndexInt++;
		}

		lexBuf.add(new Token(TokenValue.INT,
				Integer.parseInt(intVal.toString())));
		codeToParse = codeToParse.substring(codeIndexInt);
	}

	// TODO
	private int lexOperator(String opName, char op, char nextChar) {
		int shiftIndex = 1;

		if (nextChar == '=') {
			lexBuf.add(new Token(TokenValue.valueOf(opName + "EQ")));
			shiftIndex++;
		} else if (nextChar == op) {
			lexBuf.add(new Token(TokenValue.valueOf(opName + opName)));
			shiftIndex++;
		} else {
			lexBuf.add(new Token(TokenValue.valueOf(opName)));
		}

		return shiftIndex;
	}

	@Override
	protected void parseCode() throws BaseException {
		while (!currentTokenEquals(TokenValue.EOF)) {
			switch (currentToken.type) {
			case NPC:
				NpcStructure npc = parseNpc();
				this.data.addNpc(npc.name, npc);
				break;

			default:
				return;
			}
		}
	}

	private NpcStructure parseNpc() throws BaseException {
		NpcStructure npc = new NpcStructure();

		nextTokenIfEquals(TokenValue.NPC);

		if (currentTokenEquals(TokenValue.STRING)) {
			npc.name = currentToken.stringValue;

			nextToken();

			if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
				npc.location = currentToken.stringValue;

				nextToken();

				if (currentTokenEquals(TokenValue.LPAR)) {
					nextToken();

					do {
						if (currentTokenEquals(TokenValue.INT)) {
							npc.addCordinate(currentToken.intValue);
							nextToken();
						} else {
							throw new SyntaxError(lineNumber, colNumber,
									TokenValue.INT);
						}

						if (currentTokenEquals(TokenValue.COMMA)) {
							nextToken();
						} else {
							break;
						}
					} while (true);

					nextTokenIfEquals(TokenValue.RPAR);
				}

				nextTokenIfEquals(TokenValue.LBRACE);

				while (!currentTokenEquals(TokenValue.RBRACE)) {
					npc.addLabel(parseLabelExpression());
				}

				nextTokenIfEquals(TokenValue.RBRACE);

				if (!npc.validNpc()) {
					throw new InvalidNpcException(lineNumber, colNumber,
							npc.name);
				}

				return npc;
			}
		}

		// TODO: throw exception
		return null;
	}

	private LabelExpression parseLabelExpression() throws BaseException {
		LabelExpression label;

		if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
			label = new LabelExpression(currentToken.stringValue);

			nextToken();

			nextTokenIfEquals(TokenValue.TWOPOINTS);

			label.body = parseExpression();

			return label;
		} else {
			throw new SyntaxError(lineNumber, colNumber,
					TokenValue.VALUE_IDENTIFIER);
		}
	}

	/*
	 * private void parseFunction() { // TODO: }
	 */

	private ValueExpression parseValueExpression() throws BaseException {
		ValueExpression valueExp = new ValueExpression();

		if (currentTokenEquals(TokenValue.TYPE_IDENTIFIER)) {
			valueExp.type = new ObjectType(currentToken.stringValue);
			nextToken();

			if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
				valueExp.name = currentToken.stringValue;
				nextToken();

				if (currentTokenEquals(TokenValue.EQ)) {
					nextToken();

					valueExp.value = parseSimpleExpression();
				}

				nextTokenIfEquals(TokenValue.SEMICOLON);

				return valueExp;
			}

			throw new SyntaxError(lineNumber, colNumber,
					TokenValue.VALUE_IDENTIFIER);
		}

		throw new SyntaxError(lineNumber, colNumber, TokenValue.TYPE_IDENTIFIER);
	}

	private Expression parseExpression() throws BaseException {
		ArrayList<Expression> exps = new ArrayList<Expression>();
		boolean parseEnd = false;

		while (!parseEnd) {
			switch (currentToken.type) {
			case IF:
				exps.add(parseIfExpression());
				break;

			case SWITCH:
				exps.add(parseSwitchExpression());
				break;

			case FOR:
				exps.add(parseForExpression());
				break;

			case WHILE:
				exps.add(parseWhileExpression());
				break;

			case DO:
				exps.add(parseDoWhileExpression());
				break;

			case TYPE_IDENTIFIER:
				exps.add(parseValueExpression());
				break;

			case VALUE_IDENTIFIER:
				nextToken();

				if (currentTokenEquals(TokenValue.TWOPOINTS)) {
					previousToken();
					parseEnd = true;
				} else if (isAnAssignOperator(currentToken.type)) {
					previousToken();
					exps.add(parseAssignExpression());
					nextTokenIfEquals(TokenValue.SEMICOLON);
				} else {
					throw new SyntaxError(lineNumber, colNumber, TokenValue.EQ);
				}
				break;

			case MACRO_IDENTIFIER:
				exps.add(parseMacroCallExpression());
				break;

			case SET:
				SetVariableInstruction setExp = new SetVariableInstruction();

				nextToken();

				if (currentTokenEquals(TokenValue.PLAYER_STRING_VALUE_IDENTIFIER)) {
					setExp.variable = new PlayerStringVariableExpression(
							currentToken.stringValue);
				} else if (currentTokenEquals(TokenValue.PLAYER_INT_VALUE_IDENTIFIER)) {
					setExp.variable = new PlayerIntVariableExpression(
							currentToken.stringValue);
				} else {
					throw new SyntaxError(lineNumber, colNumber,
							TokenValue.PLAYER_INT_VALUE_IDENTIFIER);
				}

				nextToken();
				nextTokenIfEquals(TokenValue.COMMA);

				setExp.value = parseSimpleExpression();

				nextTokenIfEquals(TokenValue.SEMICOLON);

				exps.add(setExp);
				break;

			case OPENDLG:
				nextToken();
				nextTokenIfEquals(TokenValue.SEMICOLON);
				exps.add(new OpenMsgDialogInstruction());
				break;

			case MSG:
				MsgDisplayInstruction mes = new MsgDisplayInstruction();

				nextToken();

				mes.msg = parseSimpleExpression();

				if (!mes.msg.getExpressionType().equals(ObjectType.stringType)) {
					throw new IncompatibleTypesException(lineNumber, colNumber,
							ObjectType.stringType, mes.msg.getExpressionType());
				}

				nextTokenIfEquals(TokenValue.SEMICOLON);

				exps.add(mes);
				break;

			case NEXTDLG:
				nextToken();
				nextTokenIfEquals(TokenValue.SEMICOLON);
				exps.add(new NextMsgDialogInstruction());
				break;

			case CLOSEDLG:
				nextToken();
				nextTokenIfEquals(TokenValue.SEMICOLON);
				exps.add(new CloseMsgDialogInstruction());
				break;

			case PLAY:
				PlayMediaInstruction play = new PlayMediaInstruction();

				nextToken();

				if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
					play.name = currentToken.stringValue;

					nextToken();
					nextTokenIfEquals(TokenValue.SEMICOLON);
				} else {
					throw new SyntaxError(lineNumber, colNumber,
							TokenValue.VALUE_IDENTIFIER);
				}

				exps.add(play);
				break;

			case STOP:
				exps.add(new StopMediaInstruction());
				nextToken();
				nextTokenIfEquals(TokenValue.SEMICOLON);
				break;

			case SHOW:
				ShowImageInstruction show = new ShowImageInstruction();
				String position;

				nextToken();

				if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
					show.image = parsePathExpression();

					nextTokenIfEquals(TokenValue.COMMA);

					// TODO: transformer l'identifier en simpleExpresion
					if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
						position = currentToken.stringValue.toUpperCase();

						try {
							show.position = Constants.Alignment
									.valueOf(position);
							// TODO:
						} catch (Exception e) {
							throw new SyntaxError(lineNumber, colNumber,
									TokenValue.VALUE_IDENTIFIER);
						}
					}

					nextTokenIfEquals(TokenValue.VALUE_IDENTIFIER);
					nextTokenIfEquals(TokenValue.SEMICOLON);
				}

				exps.add(show);
				break;

			case HIDE:
				HideImageInstruction hide = new HideImageInstruction();
				String imgPosition;

				if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
					imgPosition = currentToken.stringValue.toUpperCase();

					try {
						hide.position = Constants.Alignment
								.valueOf(imgPosition);
						// TODO: transformer l'identifier en simpleExpression
						// (string)
					} catch (Exception e) {
						throw new SyntaxError(lineNumber, colNumber,
								TokenValue.VALUE_IDENTIFIER);
					}
				}

				nextTokenIfEquals(TokenValue.VALUE_IDENTIFIER);
				nextTokenIfEquals(TokenValue.SEMICOLON);

				exps.add(hide);
				break;

			case SWITCHCHAR:
				SwitchCharacterInstruction switchChar = new SwitchCharacterInstruction();

				nextToken();

				if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
					switchChar.character = new VariableExpression(
							currentToken.stringValue);
					nextToken();
					nextTokenIfEquals(TokenValue.SEMICOLON);
				} else {
					throw new SyntaxError(lineNumber, colNumber,
							TokenValue.VALUE_IDENTIFIER);
				}

				exps.add(switchChar);
				break;

			case SETBGD:
				SetBackgroundInstruction bgdInstr = new SetBackgroundInstruction();

				nextToken();

				if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
					bgdInstr.image = new VariableExpression(
							currentToken.stringValue);
					nextToken();
					nextTokenIfEquals(TokenValue.SEMICOLON);
				} else {
					throw new SyntaxError(lineNumber, colNumber,
							TokenValue.VALUE_IDENTIFIER);
				}

				exps.add(bgdInstr);
				break;

			case CLEANBGD:
				exps.add(new CleanBackgroundInstruction());
				nextToken();
				nextTokenIfEquals(TokenValue.SEMICOLON);
				break;

			case CLEANFGD:
				exps.add(new CleanForegroundInstruction());
				nextToken();
				nextTokenIfEquals(TokenValue.SEMICOLON);
				break;

			case REMOVECHAR:
				nextToken();
				nextTokenIfEquals(TokenValue.SEMICOLON);
				exps.add(new RemoveCharacter());
				break;

			case GOTO:
				GotoInstruction gotoInstr = new GotoInstruction();

				nextToken();

				gotoInstr.npc = parseNpcMemberExpression();

				nextTokenIfEquals(TokenValue.SEMICOLON);

				exps.add(gotoInstr);
				break;

			case INPUT:
				InputInstruction inputInstr = new InputInstruction();

				nextToken();

				if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
					inputInstr.name = currentToken.stringValue;
					nextToken();
					nextTokenIfEquals(TokenValue.SEMICOLON);
				} else {
					throw new SyntaxError(lineNumber, colNumber,
							TokenValue.VALUE_IDENTIFIER);
				}

				exps.add(inputInstr);
				break;

			case MENU:
				MenuInstruction menuInstr = new MenuInstruction();

				nextToken();
				nextTokenIfEquals(TokenValue.LPAR);

				if (currentTokenEquals(TokenValue.RPAR)) {
					throw new EmptyExpression(lineNumber, colNumber);
				}

				if (currentTokenEquals(TokenValue.STRING)) {
					menuInstr.addChoice(currentToken.stringValue);
					nextToken();
				} else {
					throw new SyntaxError(lineNumber, colNumber,
							TokenValue.STRING);
				}

				while (!currentTokenEquals(TokenValue.RPAR)) {
					nextTokenIfEquals(TokenValue.COMMA);

					if (currentTokenEquals(TokenValue.STRING)) {
						menuInstr.addChoice(currentToken.stringValue);
						nextToken();
					} else {
						throw new SyntaxError(lineNumber, colNumber,
								TokenValue.STRING);
					}
				}

				nextTokenIfEquals(TokenValue.RPAR);
				nextTokenIfEquals(TokenValue.LBRACE);

				while (currentTokenEquals(TokenValue.CASE)) {
					if (menuInstr.hasDefaultCase) {
						throw new CaseException(lineNumber, colNumber);
					}

					menuInstr.addCase(parseCase());
				}

				if (currentTokenEquals(TokenValue.DEFAULT)) {
					if (menuInstr.hasDefaultCase) {
						throw new DefaultCaseException(lineNumber, colNumber);
					}

					menuInstr.addCase(parseCase());
				}

				nextTokenIfEquals(TokenValue.RBRACE);

				exps.add(menuInstr);
				break;

			case END:
				nextToken();
				nextTokenIfEquals(TokenValue.SEMICOLON);
				exps.add(new EndInstruction());
				break;

			default:
				parseEnd = true;
				break;
			}
		}

		switch (exps.size()) {
		case 0:
			return null;

		case 1:
			return exps.get(0);

		default:
			return new SequenceExpression(exps);
		}
	}

	private NpcMemberExpression parseNpcMemberExpression() throws BaseException {
		NpcMemberExpression npcMember = new NpcMemberExpression();

		// TODO: transformer le string en identifier
		if (currentTokenEquals(TokenValue.STRING)) {
			npcMember.npc = currentToken.stringValue;
			nextToken();

			if (currentTokenEquals(TokenValue.FOURPOINTS)) {
				nextToken();

				if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
					npcMember.label = currentToken.stringValue;
					nextToken();
				} else {
					throw new SyntaxError(lineNumber, colNumber,
							TokenValue.VALUE_IDENTIFIER);
				}
			}

			return npcMember;

		}

		throw new SyntaxError(lineNumber, colNumber,
				TokenValue.VALUE_IDENTIFIER);
	}

	private boolean isAnAssignOperator(Token.TokenValue op) {
		switch (op) {
		case EQ:

		case PLUSEQ:
		case MINUSEQ:
		case TIMESEQ:
		case DIVEQ:

		case PLUSPLUS:
		case MINUSMINUS:
		case TIMESTIMES:
			return true;

		default:
			return false;
		}
	}

	private SubSimpleExpression parseSubSimpleExpression() throws BaseException {
		SubSimpleExpression sse;

		nextTokenIfEquals(TokenValue.LPAR);

		sse = new SubSimpleExpression(parseSimpleExpression());

		nextTokenIfEquals(TokenValue.RPAR);

		return sse;
	}

	private BinaryOperatorExpression balanceBinaryExpression(
			SimpleExpression exp, Token.TokenValue op,
			BinaryOperatorExpression binExp) {
		if (tokenPriority(op) >= tokenPriority(binExp.op)) {
			return new BinaryOperatorExpression(op, exp, binExp);
		}

		if (binExp.e1 instanceof BinaryOperatorExpression) {
			return new BinaryOperatorExpression(binExp.op,
					balanceBinaryExpression(exp, op,
							(BinaryOperatorExpression) binExp.e1), binExp.e2);
		}

		return new BinaryOperatorExpression(binExp.op,
				new BinaryOperatorExpression(op, exp, binExp.e1), binExp.e2);
	}

	private SimpleExpression parseValueIdentifierExpression()
			throws BaseException {
		String valueName = currentToken.stringValue;

		nextTokenIfEquals(TokenValue.VALUE_IDENTIFIER);

		if (currentTokenEquals(TokenValue.LBRACKET)) {
			nextToken();

			if (currentTokenEquals(TokenValue.STRING)) {
				MemberAccessExpression memberAccess = new MemberAccessExpression(
						valueName, currentToken.stringValue);

				nextTokenIfEquals(TokenValue.RBRACKET);

				return memberAccess;
			} else {
				// erreur
			}
		} else {
			return new VariableExpression(valueName);
		}

		return null;
	}

	private PathExpression parsePathExpression() throws BaseException {
		String valueName, memberName;

		if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
			valueName = currentToken.stringValue;

			nextToken();

			switch (currentToken.type) {
			case LBRACKET:
				MemberAccessExpression memberAccess;

				nextToken();

				if (currentTokenEquals(TokenValue.STRING)) {
					memberName = currentToken.stringValue;
					nextToken();

					memberAccess = new MemberAccessExpression(valueName,
							memberName);

					nextTokenIfEquals(TokenValue.RBRACKET);

					return memberAccess;
				} else {
					throw new SyntaxError(lineNumber, colNumber,
							TokenValue.STRING);
				}

			default:
				return new VariableExpression(valueName);
			}
		} else {
			throw new SyntaxError(lineNumber, colNumber,
					TokenValue.VALUE_IDENTIFIER);
		}
	}

	private IfExpression parseIfExpression() throws BaseException {
		IfExpression ifExp = new IfExpression();
		ElseIfExpression elseIfExp;
		ElseExpression elseExp;

		nextTokenIfEquals(TokenValue.IF);
		nextTokenIfEquals(TokenValue.LPAR);

		if (currentTokenEquals(TokenValue.RPAR)) {
			throw new EmptyExpression(lineNumber, colNumber);
		}

		ifExp.condition = parseSimpleExpression();

		if (!ifExp.condition.getExpressionType().equals(ObjectType.booleanType)) {
			throw new IncompatibleTypesException(lineNumber, colNumber,
					ObjectType.booleanType, ifExp.condition.getExpressionType());
		}

		nextTokenIfEquals(TokenValue.RPAR);
		nextTokenIfEquals(TokenValue.LBRACE);

		// if(e){}
		if (currentTokenEquals(TokenValue.RBRACE)) {
			ifExp.corps = null;
			nextToken();
		}
		// if(e){ e }
		else {
			ifExp.corps = parseExpression();
			nextTokenIfEquals(TokenValue.RBRACE);
		}

		while (currentTokenEquals(TokenValue.ELSEIF)) {
			elseIfExp = new ElseIfExpression();

			nextToken();
			nextTokenIfEquals(TokenValue.LPAR);

			if (currentTokenEquals(TokenValue.RPAR)) {
				throw new EmptyExpression(lineNumber, colNumber);
			}

			elseIfExp.condition = parseSimpleExpression();

			if (!elseIfExp.condition.getExpressionType().equals(
					ObjectType.booleanType)) {
				throw new IncompatibleTypesException(lineNumber, colNumber,
						ObjectType.booleanType,
						elseIfExp.condition.getExpressionType());
			}

			nextTokenIfEquals(TokenValue.RPAR);
			nextTokenIfEquals(TokenValue.LBRACE);

			// else if(e){}
			if (currentTokenEquals(TokenValue.RBRACE)) {
				elseIfExp.corps = null;
				nextToken();
			}
			// else if(e){ e }
			else {
				elseIfExp.corps = parseExpression();
				nextTokenIfEquals(TokenValue.RBRACE);
			}

			ifExp.addElseIf(elseIfExp);
		}

		if (currentTokenEquals(TokenValue.ELSE)) {
			elseExp = new ElseExpression();

			nextToken();
			nextTokenIfEquals(TokenValue.LBRACE);

			// else{}
			if (currentTokenEquals(TokenValue.RBRACE)) {
				elseExp.corps = null;
				nextToken();
			}
			// else{ e }
			else {
				elseExp.corps = parseExpression();
				nextTokenIfEquals(TokenValue.RBRACE);
			}

			ifExp.addElse(elseExp);

		}

		return ifExp;
	}

	private SwitchExpression parseSwitchExpression() throws BaseException {
		SwitchExpression switchExp = new SwitchExpression();

		nextTokenIfEquals(TokenValue.SWITCH);
		nextTokenIfEquals(TokenValue.LPAR);

		if (currentTokenEquals(TokenValue.RPAR)) {
			throw new EmptyExpression(lineNumber, colNumber);
		}

		switchExp.exp = parseSimpleExpression();

		nextTokenIfEquals(TokenValue.RPAR);
		nextTokenIfEquals(TokenValue.LBRACE);

		while (currentTokenEquals(TokenValue.CASE)) {
			if (switchExp.hasDefaultCase) {
				throw new CaseException(lineNumber, colNumber);
			}

			switchExp.addCase(parseCase());
		}

		if (currentTokenEquals(TokenValue.DEFAULT)) {
			if (switchExp.hasDefaultCase) {
				throw new DefaultCaseException(lineNumber, colNumber);
			}

			switchExp.addCase(parseCase());
		}

		nextTokenIfEquals(TokenValue.RBRACE);

		return switchExp;
	}

	private SwitchCaseExpression parseCase() throws BaseException {
		CaseExpression caseExp;
		DefaultExpression defaultExp;

		if (currentTokenEquals(TokenValue.CASE)) {
			caseExp = new CaseExpression();

			nextToken();

			caseExp.literal = parseLiteralExpression();

			nextTokenIfEquals(TokenValue.TWOPOINTS);

			// case 'a': break;
			if (currentTokenEquals(TokenValue.BREAK)) {
				caseExp.body = null;
				nextToken();
				nextTokenIfEquals(TokenValue.SEMICOLON);
			}
			// case 'a':
			// case 'b': ...
			else if (currentTokenEquals(TokenValue.CASE)
					|| currentTokenEquals(TokenValue.DEFAULT)) {
				caseExp.body = null;
				caseExp.next = parseCase();
			}
			// case 'a':
			// e
			// break;
			else {
				caseExp.body = parseExpression();
				caseExp.next = null;

				nextTokenIfEquals(TokenValue.BREAK);
				nextTokenIfEquals(TokenValue.SEMICOLON);
			}

			return caseExp;

		} else if (currentTokenEquals(TokenValue.DEFAULT)) {
			defaultExp = new DefaultExpression();

			nextToken();
			nextTokenIfEquals(TokenValue.TWOPOINTS);

			// default: break;
			if (currentTokenEquals(TokenValue.BREAK)) {
				defaultExp.body = null;
				nextToken();
				nextTokenIfEquals(TokenValue.SEMICOLON);
			}
			// default:
			// e
			// break;
			else {
				defaultExp.body = parseExpression();
				nextTokenIfEquals(TokenValue.BREAK);
				nextTokenIfEquals(TokenValue.SEMICOLON);
			}

			return defaultExp;
		}

		throw new SyntaxError(lineNumber, colNumber, TokenValue.CASE);
	}

	private ForExpression parseForExpression() throws BaseException {
		ForExpression forExp = new ForExpression();

		nextTokenIfEquals(TokenValue.FOR);
		nextTokenIfEquals(TokenValue.LPAR);

		if (currentTokenEquals(TokenValue.RPAR)) {
			throw new EmptyExpression(lineNumber, colNumber);
		}

		forExp.init = parseAssignExpression();

		nextTokenIfEquals(TokenValue.SEMICOLON);

		forExp.condition = parseSimpleExpression();

		if (!forExp.condition.getExpressionType()
				.equals(ObjectType.booleanType)) {
			throw new IncompatibleTypesException(lineNumber, colNumber,
					ObjectType.booleanType,
					forExp.condition.getExpressionType());
		}

		nextTokenIfEquals(TokenValue.SEMICOLON);

		forExp.maj = parseAssignExpression();

		nextTokenIfEquals(TokenValue.RPAR);
		nextTokenIfEquals(TokenValue.LBRACE);

		// for(a; b; c){ }
		if (currentTokenEquals(TokenValue.RBRACE)) {
			forExp.body = null;
			nextToken();
		}
		// for(a; b; c){ e }
		else {
			forExp.body = parseExpression();
			nextTokenIfEquals(TokenValue.RBRACE);
		}

		return forExp;
	}

	private AssignExpression parseAssignExpression() throws BaseException {
		AssignExpression assignExp = new AssignExpression();
		String varName, opName;
		SimpleExpression value;
		Token.TokenValue op;

		if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
			varName = currentToken.stringValue;
			assignExp.name = varName;

			nextToken();

			switch (currentToken.type) {
			case EQ:
				nextToken();
				assignExp.value = parseSimpleExpression();

				return assignExp;

			case PLUSEQ:
			case MINUSEQ:
			case TIMESEQ:
			case DIVEQ:
				op = currentToken.type;

				nextToken();
				value = parseSimpleExpression();

				opName = op.toString();

				assignExp.value = new BinaryOperatorExpression(
						TokenValue.valueOf(opName.substring(0,
								opName.length() - 2)), new VariableExpression(
								assignExp.name), value);

				return assignExp;

			case PLUSPLUS:
				nextToken();

				assignExp.value = new BinaryOperatorExpression(TokenValue.PLUS,
						new VariableExpression(assignExp.name),
						new LiteralExpression(new Token(TokenValue.INT, 1)));

				return assignExp;

			case MINUSMINUS:
				nextToken();

				assignExp.value = new BinaryOperatorExpression(
						TokenValue.MINUS,
						new VariableExpression(assignExp.name),
						new LiteralExpression(new Token(TokenValue.INT, 1)));

				return assignExp;

			case TIMESTIMES:
				nextToken();

				assignExp.value = new BinaryOperatorExpression(
						TokenValue.TIMES,
						new VariableExpression(assignExp.name),
						new VariableExpression(assignExp.name));

				return assignExp;

			default:
				break;
			}
		}

		throw new SyntaxError(lineNumber, colNumber,
				TokenValue.VALUE_IDENTIFIER);
	}

	private WhileExpression parseWhileExpression() throws BaseException {
		WhileExpression whileExp = new WhileExpression();

		nextTokenIfEquals(TokenValue.WHILE);
		nextTokenIfEquals(TokenValue.LPAR);

		if (currentTokenEquals(TokenValue.RPAR)) {
			throw new EmptyExpression(lineNumber, colNumber);
		}

		whileExp.condition = parseSimpleExpression();

		if (!whileExp.condition.getExpressionType().equals(
				ObjectType.booleanType)) {
			throw new IncompatibleTypesException(lineNumber, colNumber,
					ObjectType.booleanType,
					whileExp.condition.getExpressionType());
		}

		nextTokenIfEquals(TokenValue.RPAR);
		nextTokenIfEquals(TokenValue.LBRACE);

		// while(e){}
		if (currentTokenEquals(TokenValue.RBRACE)) {
			whileExp.body = null;
			nextToken();
		}
		// while(e){ e }
		else {
			whileExp.body = parseExpression();
			nextTokenIfEquals(TokenValue.RBRACE);
		}

		return whileExp;
	}

	private DoWhileExpression parseDoWhileExpression() throws BaseException {
		DoWhileExpression doWhileExp = new DoWhileExpression();

		nextTokenIfEquals(TokenValue.DO);
		nextTokenIfEquals(TokenValue.LBRACE);

		// do{ }while(e);
		if (currentTokenEquals(TokenValue.RBRACE)) {
			doWhileExp.body = null;

			nextToken();
		}
		// do{ e }while(e);
		else {
			doWhileExp.body = parseExpression();

			nextTokenIfEquals(TokenValue.RBRACE);
		}

		nextTokenIfEquals(TokenValue.WHILE);
		nextTokenIfEquals(TokenValue.LPAR);

		if (currentTokenEquals(TokenValue.RPAR)) {
			throw new EmptyExpression(lineNumber, colNumber);
		}

		doWhileExp.condition = parseSimpleExpression();

		if (!doWhileExp.condition.getExpressionType().equals(
				ObjectType.booleanType)) {
			throw new IncompatibleTypesException(lineNumber, colNumber,
					ObjectType.booleanType,
					doWhileExp.condition.getExpressionType());
		}

		nextTokenIfEquals(TokenValue.RPAR);
		nextTokenIfEquals(TokenValue.SEMICOLON);

		return doWhileExp;
	}

	private SimpleExpression parseSimpleExpression() throws BaseException {
		Stack<SimpleExpression> exps = new Stack<SimpleExpression>();
		boolean parseEnd = false;
		SimpleExpression e1, e2;
		Token.TokenValue currentOp;

		while (!parseEnd) {
			switch (currentToken.type) {
			case LPAR:
				exps.push(parseSubSimpleExpression());
				break;

			case STRING:
			case CHAR:
			case INT:
			case BOOLEAN:
				exps.push(parseLiteralExpression());
				break;

			case MINUS:
				if (exps.isEmpty()) {
					nextToken();
					e1 = parseSimpleExpression();
					exps.push(e1);
					break;
				}
			case PLUS:
				// case MINUS:
			case TIMES:
			case DIV:
			case LT:
			case GT:
			case LEQ:
			case GEQ:
			case AND:
			case OR:
			case EQBOOL:
				e1 = exps.pop();

				currentOp = currentToken.type;
				nextToken();

				e2 = parseSimpleExpression();

				if (e2 instanceof BinaryOperatorExpression) {
					exps.push(balanceBinaryExpression(e1, currentOp,
							(BinaryOperatorExpression) e2));
				} else {
					exps.push(new BinaryOperatorExpression(currentOp, e1, e2));
				}

				return exps.pop();

			case PLAYER_STRING_VALUE_IDENTIFIER:
				exps.push(new PlayerStringVariableExpression(
						currentToken.stringValue));
				nextToken();
				break;

			case PLAYER_INT_VALUE_IDENTIFIER:
				exps.push(new PlayerIntVariableExpression(
						currentToken.stringValue));
				nextToken();
				break;

			case VALUE_IDENTIFIER:
				exps.push(parseValueIdentifierExpression());
				break;

			case MACRO_IDENTIFIER:
				exps.push(parseMacroCallExpression());
				nextTokenIfEquals(TokenValue.SEMICOLON);
				break;

			default:
				parseEnd = true;
				break;
			}
		}

		return exps.pop();
	}

	private MacroCallExpression parseMacroCallExpression() throws BaseException {
		MacroCallExpression mce = new MacroCallExpression();

		if (currentTokenEquals(TokenValue.MACRO_IDENTIFIER)) {
			try {
				mce.macro = MacroValue.valueOf(currentToken.stringValue
						.toUpperCase());
			} catch (Exception e) {
				// TODO: error, unknown macro
			}

			nextTokenIfEquals(TokenValue.LPAR);

			if (currentTokenEquals(TokenValue.RPAR)) {
				nextToken();
			} else {
				mce.addArgument(parseSimpleExpression());
				
				while(!currentTokenEquals(TokenValue.RPAR)){
					nextTokenIfEquals(TokenValue.COMMA);
					mce.addArgument(parseSimpleExpression());
				}
			}
		}

		return mce;
	}

	private static byte tokenPriority(Token.TokenValue tok) {
		switch (tok) {
		case AND:
			return 9;
		case OR:
			return 8;

		case GT:
		case LT:
		case GEQ:
		case LEQ:
		case EQBOOL:
			return 7;

		case NEG:
			return 6;

		case PLUS:
		case MINUS:
			return 4;

		case TIMES:
		case DIV:
			return 3;

		default:
			return 0;
		}
	}
}