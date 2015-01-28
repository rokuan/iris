package com.rokuan.iris.parser;

import com.rokuan.iris.data.IrisBackground;
import com.rokuan.iris.data.IrisCharacter;
import com.rokuan.iris.data.IrisImage;
import com.rokuan.iris.data.IrisSound;
import com.rokuan.iris.exception.BaseException;
import com.rokuan.iris.exception.InvalidSequence;
import com.rokuan.iris.exception.SyntaxError;
import com.rokuan.iris.exception.UnknownField;
import com.rokuan.iris.interfaces.IIrisResources;
import com.rokuan.iris.parser.Token.TokenValue;
import com.rokuan.iris.process.IIrisStreamReader;

public class ContentParser extends BasicParser {
	private static final String[] patterns = { "true", "false", "character",
			"image", "sound", "background" };

	private static final Token[] tokens = {
			new Token(TokenValue.BOOLEAN, true),
			new Token(TokenValue.BOOLEAN, false),
			new Token(TokenValue.CHARACTER), new Token(TokenValue.IMAGE),
			new Token(TokenValue.SOUND), new Token(TokenValue.BACKGROUND) };

	public ContentParser(IIrisStreamReader rd, IIrisResources rs) {
		super(rd, rs);
	}

	@Override
	protected void lexCode() throws BaseException {
		while (codeToParse != null && !codeToParse.isEmpty()) {
			int codeIndex = 0, colStep = 1;
			String strId;
			boolean charMatch = true;

			switch (codeToParse.charAt(0)) {
			case '\t':
			case '\b':
			case ' ':
				colNumber++;
			case '\r':
				codeToParse = codeToParse.substring(1);
				// lexCode();
				continue;

			case '{':
				lexBuf.add(new Token(TokenValue.LBRACE));
				break;

			case '}':
				lexBuf.add(new Token(TokenValue.RBRACE));
				break;

			case ';':
				lexBuf.add(new Token(TokenValue.SEMICOLON));
				break;

			case '=':
				lexBuf.add(new Token(TokenValue.EQ));
				break;

			case '/':
				if (codeToParse.length() > 1 && codeToParse.charAt(1) == '*') {
					lexCommentary();
					continue;
				} else {
					throw new InvalidSequence(lineNumber, colNumber);
				}

			case '"':
				lexString();
				continue;

			default:
				charMatch = false;
				break;
			}

			if (!charMatch) {
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
					if (Character.isLowerCase(codeToParse.charAt(0))) {
						strId = lexIdentifier();
						lexBuf.add(new Token(TokenValue.VALUE_IDENTIFIER, strId));
						colStep = strId.length();
					} else {
						throw new InvalidSequence(lineNumber, colNumber);
					}
				}
			}

			colNumber += colStep;
			codeIndex += colStep;

			codeToParse = codeToParse.substring(codeIndex);
		}
	}

	protected void parseCode() throws BaseException {
		while (!currentTokenEquals(TokenValue.EOF)) {
			switch (currentToken.type) {
			case CHARACTER:
				IrisCharacter character = parseCharacter();
				this.data.addCharacter(character.name, character);
				break;

			case IMAGE:
				IrisImage image = parseImage();
				this.data.addImage(image.name, image);
				break;

			case SOUND:
				IrisSound sound = parseSound();
				this.data.addSound(sound.name, sound);
				break;

			case BACKGROUND:
				IrisBackground background = parseBackground();
				this.data.addBackground(background.name, background);
				break;

			default:
				// TODO: erreur, aucun des tokens attendus
				return;
			}
		}
	}

	public IrisCharacter parseCharacter() throws BaseException {
		IrisCharacter charac = new IrisCharacter();
		String fieldName, fieldValue;

		nextTokenIfEquals(TokenValue.CHARACTER);

		if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
			charac.name = currentToken.stringValue;

			nextToken();

			nextTokenIfEquals(TokenValue.LBRACE);

			while (!currentTokenEquals(TokenValue.RBRACE)) {
				if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
					fieldName = currentToken.stringValue;

					nextToken();

					nextTokenIfEquals(TokenValue.EQ);

					// name = "name";
					if (fieldName.equals("name")) {
						if (currentTokenEquals(TokenValue.STRING)) {
							charac.charName = currentToken.stringValue;

							nextToken();

							nextTokenIfEquals(TokenValue.SEMICOLON);
						} else {
							throw new SyntaxError(lineNumber, colNumber,
									TokenValue.STRING);
						}
					}
					// color = "#0077FF";
					else if (fieldName.equals("color")) {
						if (currentTokenEquals(TokenValue.STRING)) {
							charac.setColor(currentToken.stringValue);
						} else {
							throw new SyntaxError(lineNumber, colNumber,
									TokenValue.STRING);
						}
					}
					// images = {
					// happy = "happy_and_cool.png";
					// light = "emotions/shining.png";
					// ..... = ....................;
					// };
					else if (fieldName.equals("images")) {
						nextTokenIfEquals(TokenValue.LBRACE);

						while (!currentTokenEquals(TokenValue.RBRACE)) {
							if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
								fieldName = currentToken.stringValue;
								nextToken();

								nextTokenIfEquals(TokenValue.EQ);

								if (currentTokenEquals(TokenValue.STRING)) {
									fieldValue = currentToken.stringValue;

									nextToken();
									nextTokenIfEquals(TokenValue.SEMICOLON);

									charac.addImage(fieldName, fieldValue);
								} else {
									throw new SyntaxError(lineNumber,
											colNumber, TokenValue.STRING);
								}
							} else {
								throw new SyntaxError(lineNumber, colNumber,
										TokenValue.VALUE_IDENTIFIER);
							}
						}

						nextTokenIfEquals(TokenValue.RBRACE);
						nextTokenIfEquals(TokenValue.SEMICOLON);
					} else {
						throw new UnknownField(lineNumber, colNumber,
								charac.name, fieldName);
					}
				}
			}

			nextTokenIfEquals(TokenValue.RBRACE);
			// nextTokenIfEquals(SEMICOLON);

			return charac;
		} else {
			throw new SyntaxError(lineNumber, colNumber,
					TokenValue.VALUE_IDENTIFIER);
		}

		// erreur
		// return null;
	}

	public IrisImage parseImage() throws BaseException {
		IrisImage image = new IrisImage();
		String fieldName, fieldValue;

		nextTokenIfEquals(TokenValue.IMAGE);

		if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
			fieldName = currentToken.stringValue;

			nextToken();

			nextTokenIfEquals(TokenValue.EQ);

			if (fieldName.equals("stretch")) {

			}
		} else {
			throw new SyntaxError(lineNumber, colNumber,
					TokenValue.VALUE_IDENTIFIER);
		}

		return image;
	}

	public IrisSound parseSound() throws BaseException {
		IrisSound sound = new IrisSound();
		String fieldName, fieldValue;

		nextTokenIfEquals(TokenValue.SOUND);

		if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
			sound.name = currentToken.stringValue;

			nextToken();

			nextTokenIfEquals(TokenValue.LBRACE);

			while (!currentTokenEquals(TokenValue.RBRACE)) {
				if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
					fieldName = currentToken.stringValue;

					nextToken();

					nextTokenIfEquals(TokenValue.EQ);

					if (fieldName.equals("source")) {
						if (currentTokenEquals(TokenValue.STRING)) {
							fieldValue = currentToken.stringValue;

							nextToken();

							nextTokenIfEquals(TokenValue.SEMICOLON);
							sound.setFilepath(fieldValue);
						} else {

						}
					} else {

					}
				} else {
					throw new SyntaxError(lineNumber, colNumber,
							TokenValue.VALUE_IDENTIFIER);
				}
			}

			nextTokenIfEquals(TokenValue.RBRACE);

		} else {
			throw new SyntaxError(lineNumber, colNumber,
					TokenValue.VALUE_IDENTIFIER);
		}

		return sound;
	}

	public IrisBackground parseBackground() throws BaseException {
		IrisBackground bgd = new IrisBackground();
		String fieldName, fieldValue;

		nextTokenIfEquals(TokenValue.BACKGROUND);

		if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
			bgd.name = currentToken.stringValue;

			nextToken();

			nextTokenIfEquals(TokenValue.LBRACE);

			while (!currentTokenEquals(TokenValue.RBRACE)) {
				if (currentTokenEquals(TokenValue.VALUE_IDENTIFIER)) {
					fieldName = currentToken.stringValue;

					nextToken();

					nextTokenIfEquals(TokenValue.EQ);

					if (fieldName.equals("source")) {
						if (currentTokenEquals(TokenValue.STRING)) {
							fieldValue = currentToken.stringValue;

							nextToken();

							nextTokenIfEquals(TokenValue.SEMICOLON);
							bgd.setFilepath(fieldValue);
						} else {

						}
					} else {

					}
				} else {
					throw new SyntaxError(lineNumber, colNumber,
							TokenValue.VALUE_IDENTIFIER);
				}
			}

			nextTokenIfEquals(TokenValue.RBRACE);
		} else {
			throw new SyntaxError(lineNumber, colNumber,
					TokenValue.VALUE_IDENTIFIER);
		}

		return bgd;
	}
}