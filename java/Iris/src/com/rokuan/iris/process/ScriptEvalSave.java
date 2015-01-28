package com.rokuan.iris.process;

import com.rokuan.iris.enums.InputType;
import com.rokuan.iris.expression.CaseExpression;
import com.rokuan.iris.expression.DefaultExpression;
import com.rokuan.iris.expression.ObjectExpression;
import com.rokuan.iris.expression.SwitchCaseExpression;
import com.rokuan.iris.instruction.CloseMsgDialogInstruction;
import com.rokuan.iris.instruction.InputInstruction;
import com.rokuan.iris.instruction.MenuInstruction;
import com.rokuan.iris.instruction.NextMsgDialogInstruction;
import com.rokuan.iris.types.ObjectType;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
//
//import com.rokuan.iris.data.IrisCharacter;
//import com.rokuan.iris.data.IrisImage;
//import com.rokuan.iris.enums.InputType;
//import com.rokuan.iris.expression.*;
//import com.rokuan.iris.instruction.*;
//import com.rokuan.iris.interfaces.IIrisBackgroundFrame;
//import com.rokuan.iris.interfaces.IIrisDataLoader;
//import com.rokuan.iris.interfaces.IIrisDialogBox;
//import com.rokuan.iris.interfaces.IIrisImagePanel;
//import com.rokuan.iris.interfaces.IIrisInputBox;
//import com.rokuan.iris.interfaces.IIrisResources;
//import com.rokuan.iris.interfaces.IIrisSelectBox;
//import com.rokuan.iris.interfaces.IIrisSoundPlayer;
//import com.rokuan.iris.interfaces.IIrisTextBox;
//import com.rokuan.iris.interfaces.IIrisVideoPlayer;
//import com.rokuan.iris.types.ObjectType;
//
public class ScriptEvalSave {
//	/*
//	 * CONTEXT
//	 */
//	private IIrisDataLoader dataLoader;
//	private IIrisResources resources;
//
//	/*
//	 * COMPONENTS
//	 */
//	private IIrisTextBox leftSpeaker;
//	private IIrisTextBox rightSpeaker;
//	private IIrisDialogBox messageBox;
//
//	private IIrisInputBox inputBox;
//
//	private IIrisSelectBox selectBox;
//
//	private IIrisBackgroundFrame backgroundFrame;
//	private IIrisSoundPlayer soundPlayer;
//	private IIrisVideoPlayer videoPlayer;
//
//	private IIrisImagePanel leftImage;
//	private IIrisImagePanel centerPanel;
//	private IIrisImagePanel rightPanel;
//	// TODO: changer la taille pour coller au nombre de positions possibles
//	private IIrisImagePanel[] panels = new IIrisImagePanel[3];
//
//	// -----Expression
//
//	private HashMap<String, ObjectExpression> variables = new HashMap<String, ObjectExpression>();
//	private ObjectExpression o1;
//
//	private LiteralExpression lit;
//	private UnaryOperatorExpression unExp;
//	private MemberAccessExpression memberExp;
//
//	// -----Expression
//
//	private boolean shouldEvalSeq = true;
//	private LinkedList<SequenceExpression> runningSeqs = new LinkedList<SequenceExpression>();
//	private LinkedList<SequenceExpression> seqs = new LinkedList<SequenceExpression>();
//
//	private int insertIndex = 0;
//
//	private IfExpression ifExp;
//	private SwitchExpression switchExp;
//	private WhileExpression whileExp;
//	private DoWhileExpression doWhileExp;
//	private ForExpression forExp;
//
//	// -----InstructionExpression
//
//	private InstructionExpression lastInstr;
//
//	private ValueExpression valueExp;
//	private AssignExpression assignExp;
//	private GotoInstruction gotoExp;
//	private MsgDisplayInstruction msgExp;
//	private PlayMediaInstruction playExp;
//	private ShowImageInstruction showExp;
//	private SetVariableInstruction setExp;
//	private InputInstruction inputExp;
//	private MenuInstruction menuExp;
//	private SetBackgroundInstruction bgdInstr;
//
//	private IrisImage img;
//	private IrisCharacter charac;
//	private NpcMemberExpression npcExp;
//
//	public ScriptEvalSave(IIrisDataLoader data, IIrisResources res,
//			IIrisDialogBox message, IIrisInputBox input, IIrisSelectBox select,
//			IIrisImagePanel left, IIrisImagePanel center, IIrisImagePanel right,
//			IIrisBackgroundFrame bgd,
//			IIrisSoundPlayer sPlayer, IIrisVideoPlayer vPlayer) {
//		this.dataLoader = data;
//		this.resources = res;
//
//		this.messageBox = message;
//		this.inputBox = input;
//		this.selectBox = select;
//		
//		this.panels[0] = left;
//		this.panels[1] = center;
//		this.panels[2] = right;
//		
//		this.backgroundFrame = bgd;
//		
//		this.soundPlayer = sPlayer;
//		this.videoPlayer = vPlayer;
//		
//		this.messageBox.hideComponent();
//		this.inputBox.hideComponent();
//		this.selectBox.hideComponent();
//	}
//
//	public void talkToNpc(String npcName){
//		try{
//			evalExpression(this.resources.getNpc(npcName).labels.get("main"));
//		}catch(Exception e){
//			
//		}
//	}
//	
//	public void nextMessage() {
//		// TODO: verifier si l'evaluation est terminee
//		this.restartEval();
//	}
//
//	public void validateInput() {
//		// TODO: verifier la validite du contenu
//		this.inputBox.hideComponent();
//		this.messageBox.enableComponent();
//		this.restartEval();
//	}
//
//	public void validateSelection() {
//		if (this.selectBox.getSelectedIndex() >= 0) {
//			this.selectBox.hideComponent();
//			this.messageBox.enableComponent();
//			this.restartEval();
//		}
//	}
//
//	private void restartEval() {
//	try {
//		if (lastInstr != null) {
//			if (lastInstr instanceof InputInstruction) {
//				inputExp = (InputInstruction) lastInstr;
//
//				if (inputBox.getInputType() == InputType.INPUT_INT_TYPE) {
//					variables.put(inputExp.name, new ObjectExpression(
//							ObjectType.intType, inputBox.getIntValue()));
//				} else if (inputBox.getInputType() == InputType.INPUT_STRING_TYPE) {
//					variables.put(inputExp.name, new ObjectExpression(
//							ObjectType.stringType, inputBox.getValue()));
//				}
//			} else if (lastInstr instanceof MenuInstruction) {
//				menuExp = (MenuInstruction) lastInstr;
//
//				for (SwitchCaseExpression sce : menuExp.cases) {
//					if (sce instanceof CaseExpression) {
//						if (isCatched(selectBox.getSelectedIndex(), sce,
//								false)) {
//							break;
//						}
//					} else if (sce instanceof DefaultExpression) {
//						evalExpression(((DefaultExpression) sce).body);
//						break;
//					}
//				}
//			} else if (lastInstr instanceof CloseMsgDialogInstruction) {
//				// TODO:
//				this.messageBox.clearContent();
//				this.messageBox.hideComponent();
//			} else if (lastInstr instanceof NextMsgDialogInstruction) {
//				this.messageBox.clearContent();
//			}
//
//			lastInstr = null;
//		}
//
//		shouldEvalSeq = true;
//
//		int insertIndex2;
//		insertIndex = runningSeqs.size() - 1;
//
//		while (!runningSeqs.isEmpty()) {
//			insertIndex = runningSeqs.size() - 1;
//			insertIndex2 = seqs.size() - 1;
//
//			evalExpression(runningSeqs.getLast());
//
//			runningSeqs.remove(insertIndex);
//
//			if (!shouldEvalSeq) {
//				return;
//			}
//
//			removeDeclaredVariables(seqs.get(insertIndex2)
//					.declaredVariables());
//
//			seqs.remove(insertIndex2);
//		}
//
//		variables.clear();
//	} catch (Exception e) {
//		System.err.println(e.toString());
//	}
//	}
//
//	private void evalInstruction(InstructionExpression instr) {
//		if (instr instanceof ValueExpression) {
//			valueExp = (ValueExpression) instr;
//
//			if (valueExp.type.equals(ObjectType.intType)) {
//				variables.put(valueExp.name, new ObjectExpression(
//						valueExp.type, 0));
//			} else if (valueExp.type.equals(ObjectType.booleanType)) {
//				variables.put(valueExp.name, new ObjectExpression(
//						valueExp.type, false));
//			} else if (valueExp.type.equals(ObjectType.stringType)) {
//				variables.put(valueExp.name, new ObjectExpression(
//						valueExp.type, ""));
//			}
//
//			if (valueExp.value != null) {
//				variables.put(valueExp.name, new ObjectExpression(
//						valueExp.type,
//						evalSimpleExpression(valueExp.value).value));
//			}
//
//			return;
//		} else if (instr instanceof AssignExpression) {
//			assignExp = (AssignExpression) instr;
//
//			variables
//					.put(assignExp.name, evalSimpleExpression(assignExp.value));
//
//			return;
//		} else if (instr instanceof OpenMsgDialogInstruction) {
//			messageBox.showComponent();
//			return;
//		} else if (instr instanceof MsgDisplayInstruction) {
//			msgExp = (MsgDisplayInstruction) instr;
//
//			o1 = evalSimpleExpression(msgExp.msg);
//
//			if (!o1.type.equals(ObjectType.stringType)) {
//				return;
//			}
//
//			messageBox.appendText(((String) o1.value) + '\n');
//			return;
//		} else if (instr instanceof NextMsgDialogInstruction) {
//			shouldEvalSeq = false;
//			lastInstr = instr;
//			return;
//		} else if (instr instanceof CloseMsgDialogInstruction) {
//			shouldEvalSeq = false;
//			lastInstr = instr;
//			return;
//		} else if (instr instanceof SwitchCharacterInstruction) {
//			try {
//				IrisCharacter character = resources
//						.getCharacter(((SwitchCharacterInstruction) instr).character.name);
//				messageBox.setSpeaker(character.charName);
//			} catch (Exception e) {
//				return;
//			}
//
//			return;
//		} else if (instr instanceof RemoveCharacter) {
//			messageBox.removeSpeaker();
//			return;
//		} else if (instr instanceof SetVariableInstruction) {
//			setExp = (SetVariableInstruction)instr;
//			dataLoader.setPlayerVariable(setExp.variable.name,
//					(Integer) (evalSimpleExpression(setExp.value).value));
//			return;
//		} else if (instr instanceof InputInstruction) {
//			inputExp = (InputInstruction) instr;
//
//			o1 = variables.get(inputExp.name);
//
//			shouldEvalSeq = false;
//			
//			if (o1.type.equals(ObjectType.intType)) {
//				inputBox.setInputType(InputType.INPUT_INT_TYPE);
//
//				lastInstr = inputExp;
//			} else if (o1.type.equals(ObjectType.stringType)) {
//				inputBox.setInputType(InputType.INPUT_STRING_TYPE);
//
//				lastInstr = inputExp;
//			} else {
//				return;
//			}
//
//			inputBox.enableComponent();
//			inputBox.showComponent();
//			messageBox.disableComponent();
//			selectBox.disableComponent();
//
//			return;
//		} else if (instr instanceof MenuInstruction) {
//			menuExp = (MenuInstruction) instr;
//			shouldEvalSeq = false;
//
//			selectBox.setChoices(((MenuInstruction) instr).choices);
//
//			lastInstr = menuExp;
//
//			selectBox.enableComponent();
//			selectBox.showComponent();
//			messageBox.disableComponent();
//			inputBox.disableComponent();
//			
//			return;
//		} else if (instr instanceof PlayMediaInstruction) {
//			playExp = (PlayMediaInstruction) instr;
//
//			try {
//				soundPlayer.setSource(resources.getSound(playExp.name));
//				soundPlayer.playSound();
//			} catch (Exception e) {
//				return;
//			}
//
//			return;
//		} else if (instr instanceof StopMediaInstruction) {
//			soundPlayer.stopSound();
//			return;
//		} else if (instr instanceof ShowImageInstruction) {
//			showExp = (ShowImageInstruction) instr;
//
//			if (showExp.image instanceof VariableExpression) {
//				img = resources
//						.getImage(((VariableExpression) showExp.image).name);
//			} else {
//				memberExp = (MemberAccessExpression) showExp.image;
//
//				charac = resources.getCharacter(memberExp.name);
//				img = charac.getImage(memberExp.field);
//			}
//
//			try {
//				panels[showExp.position.ordinal()].setSource(img);
//			} catch (Exception e) {
//				return;
//			}
//
//			return;
//		} else if (instr instanceof SetBackgroundInstruction) {
//			bgdInstr = (SetBackgroundInstruction) instr;
//
//			try {
//				backgroundFrame.setSource(resources
//						.getBackground(bgdInstr.image.name));
//			} catch (Exception e) {
//				System.err.println(e.toString());
//				return;
//			}
//
//			return;
//		} else if (instr instanceof CleanBackgroundInstruction) {
//			backgroundFrame.removeSource();
//			return;
//		} else if (instr instanceof CleanForegroundInstruction) {
//			// gScreen.clearForeGround();
//			for (int i = 0; i < panels.length; i++) {
//				try {
//					panels[i].removeSource();
//				} catch (Exception e) {
//
//				}
//			}
//			return;
//		} else if (instr instanceof GotoInstruction) {
//			gotoExp = (GotoInstruction) instr;
//
//			npcExp = gotoExp.npc;
//
//			evalExpression(resources.getNpc(npcExp.npc).labels
//					.get(npcExp.label));
//
//			return;
//		}
//	}
//
//	private void evalExpression(Expression exp) {
//		if (exp == null) {
//			return;
//		}
//
//		if (exp instanceof InstructionExpression) {
//			evalInstruction((InstructionExpression) exp);
//
//			return;
//		} else if (exp instanceof SequenceExpression) {
//			SequenceExpression seqExp = (SequenceExpression) exp, seqTmp;
//			Expression expTmp;
//			int index = insertIndex;
//
//			seqTmp = new SequenceExpression(new ArrayList<Expression>(
//					seqExp.exps));
//
//			seqs.add(seqExp);
//			runningSeqs.add(seqTmp);
//			index = runningSeqs.size() - 1;
//
//			while (seqTmp.exps.size() > 0) {
//				expTmp = seqTmp.exps.get(0);
//				evalExpression(expTmp);
//
//				seqTmp.exps.remove(0);
//
//				if (!shouldEvalSeq) {
//					if (seqTmp.exps.size() == 0) {
//						runningSeqs.remove(index);
//					}
//
//					return;
//				}
//			}
//
//			runningSeqs.remove(runningSeqs.size() - 1);
//
//			removeDeclaredVariables(seqs.get(seqs.size() - 1)
//					.declaredVariables());
//			seqs.remove(seqs.size() - 1);
//
//			return;
//		} else if (exp instanceof IfExpression) {
//			ifExp = (IfExpression) exp;
//
//			o1 = evalSimpleExpression(ifExp.condition);
//
//			if ((Boolean) o1.value) {
//				evalExpression(ifExp.corps);
//
//				return;
//			}
//
//			if (ifExp.elseIfList != null) {
//				for (ElseIfExpression elseIfExp : ifExp.elseIfList) {
//					o1 = evalSimpleExpression(elseIfExp.condition);
//
//					if ((Boolean) o1.value) {
//						evalExpression(elseIfExp.corps);
//
//						return;
//					}
//				}
//			}
//
//			if (ifExp.elseBlock != null) {
//				evalExpression(ifExp.elseBlock.corps);
//				return;
//			}
//
//			return;
//		} else if (exp instanceof SwitchExpression) {
//			switchExp = (SwitchExpression) exp;
//
//			o1 = evalSimpleExpression(switchExp.exp);
//
//			for (SwitchCaseExpression sce : switchExp.cases) {
//				if (sce instanceof CaseExpression) {
//					if (o1.type.equals(ObjectType.stringType)
//							&& isCatched((String) o1.value, sce, false)) {
//						return;
//					} else if (o1.type.equals(ObjectType.intType)
//							&& isCatched((Integer) o1.value, sce, false)) {
//						return;
//					}
//
//				} else if (sce instanceof DefaultExpression) {
//					evalExpression(((DefaultExpression) sce).body);
//					return;
//				}
//			}
//
//			return;
//		} else if (exp instanceof WhileExpression) {
//			whileExp = (WhileExpression) exp;
//
//			while (true) {
//				o1 = evalSimpleExpression(whileExp.condition);
//
//				if (!(Boolean) o1.value) {
//					return;
//				}
//
//				evalExpression(whileExp.body);
//			}
//		} else if (exp instanceof DoWhileExpression) {
//			doWhileExp = (DoWhileExpression) exp;
//
//			do {
//				evalExpression(doWhileExp.body);
//
//				o1 = evalSimpleExpression(doWhileExp.condition);
//
//				if (!(Boolean) o1.value) {
//					return;
//				}
//
//			} while (true);
//		} else if (exp instanceof ForExpression) {
//			forExp = (ForExpression) exp;
//
//			evalExpression(forExp.init);
//
//			while (true) {
//				o1 = evalSimpleExpression(forExp.condition);
//
//				if (!(Boolean) o1.value) {
//					return;
//				}
//
//				evalExpression(forExp.body);
//				evalExpression(forExp.maj);
//			}
//		}
//	}
//
//	private boolean isCatched(String value, SwitchCaseExpression sce,
//			boolean preCatch) {
//		if (sce instanceof CaseExpression) {
//			CaseExpression cExp = (CaseExpression) sce;
//
//			if (cExp.next == null) {
//				if (preCatch) {
//					evalExpression(cExp.body);
//					return true;
//				}
//
//				// if (!cExp.literal.value.stringValue.equals(value) &&
//				// !preCatch)
//				if (!cExp.literal.value.stringValue.equals(value)) {
//					return false;
//				} else {
//					evalExpression(cExp.body);
//					return true;
//				}
//			} else {
//				if (preCatch) {
//					return isCatched(value, cExp.next, preCatch);
//				}
//
//				if (cExp.literal.value.stringValue.equals(value)) {
//					return isCatched(value, cExp.next, true);
//				}
//
//				return isCatched(value, cExp.next, preCatch);
//			}
//
//		} else if (sce instanceof DefaultExpression) {
//			evalExpression(((DefaultExpression) sce).body);
//			return true;
//		}
//
//		return false;
//	}
//
//	private boolean isCatched(int value, SwitchCaseExpression sce,
//			boolean preCatch) {
//		if (sce instanceof CaseExpression) {
//			CaseExpression cExp = (CaseExpression) sce;
//
//			if (cExp.next == null) {
//				if (preCatch) {
//					evalExpression(cExp.body);
//					return true;
//				}
//
//				if (cExp.literal.value.intValue != value && !preCatch) {
//					return false;
//				} else {
//					evalExpression(cExp.body);
//					return true;
//				}
//			} else {
//				if (preCatch) {
//					return isCatched(value, cExp.next, preCatch);
//				}
//
//				if (cExp.literal.value.intValue == value) {
//					return isCatched(value, cExp.next, true);
//				}
//
//				return isCatched(value, cExp.next, preCatch);
//			}
//
//		} else if (sce instanceof DefaultExpression) {
//			evalExpression(((DefaultExpression) sce).body);
//			return true;
//		}
//
//		return false;
//	}
//
//	private ObjectExpression evalSimpleExpression(SimpleExpression exp) {
//		if (exp instanceof LiteralExpression) {
//			lit = (LiteralExpression) exp;
//
//			switch (lit.value.type) {
//			case STRING:
//				return new ObjectExpression(ObjectType.stringType,
//						lit.value.stringValue);
//
//			case INT:
//				return new ObjectExpression(ObjectType.intType,
//						lit.value.intValue);
//
//			case CHAR:
//				return new ObjectExpression(ObjectType.charType,
//						lit.value.charValue);
//
//			case BOOLEAN:
//				return new ObjectExpression(ObjectType.booleanType,
//						lit.value.booleanValue);
//
//			default:
//				break;
//			}
//		} else if (exp instanceof SubSimpleExpression) {
//			return evalSimpleExpression(((SubSimpleExpression) exp).exp);
//		} else if (exp instanceof NullExpression) {
//			return null;
//		} else if (exp instanceof BinaryOperatorExpression) {
//			BinaryOperatorExpression binExp = (BinaryOperatorExpression) exp;
//			ObjectExpression oLeft = evalSimpleExpression(binExp.e1), oRight;
//
//			switch (binExp.op) {
//			case AND:
//				if ((Boolean) oLeft.value) {
//					oRight = evalSimpleExpression(binExp.e2);
//					return new ObjectExpression(ObjectType.booleanType,
//							(Boolean) oRight.value);
//				}
//
//				return new ObjectExpression(ObjectType.booleanType, false);
//
//			case OR:
//				if ((Boolean) oLeft.value) {
//					return new ObjectExpression(ObjectType.booleanType, true);
//				}
//
//				oRight = evalSimpleExpression(binExp.e2);
//				return new ObjectExpression(ObjectType.booleanType,
//						(Boolean) oRight.value);
//
//			case PLUS:
//				oRight = evalSimpleExpression(binExp.e2);
//
//				if (oLeft.type.equals(ObjectType.stringType)
//						|| oRight.type.equals(ObjectType.stringType)) {
//					return new ObjectExpression(ObjectType.stringType,
//							oLeft.value.toString() + oRight.value.toString());
//				}
//				if (oLeft.type.equals(ObjectType.intType)
//						&& oRight.type.equals(ObjectType.intType)) {
//					return new ObjectExpression(ObjectType.intType,
//							(Integer) oLeft.value + (Integer) oRight.value);
//				}
//				break;
//
//			case MINUS:
//				oRight = evalSimpleExpression(binExp.e2);
//				return new ObjectExpression(ObjectType.intType,
//						(Integer) oLeft.value - (Integer) oRight.value);
//
//			case TIMES:
//				oRight = evalSimpleExpression(binExp.e2);
//				return new ObjectExpression(ObjectType.intType,
//						(Integer) oLeft.value * (Integer) oRight.value);
//
//			case DIV:
//				oRight = evalSimpleExpression(binExp.e2);
//				return new ObjectExpression(ObjectType.intType,
//						(Integer) oLeft.value / (Integer) oRight.value);
//
//			case LT:
//				// TODO: ajouter les comparaisons de date/temps
//				oRight = evalSimpleExpression(binExp.e2);
//				return new ObjectExpression(ObjectType.booleanType,
//						(Integer) oLeft.value < (Integer) oRight.value);
//
//			case GT:
//				// TODO: ajouter les comparaisons de date/temps
//				oRight = evalSimpleExpression(binExp.e2);
//				return new ObjectExpression(ObjectType.booleanType,
//						(Integer) oLeft.value > (Integer) oRight.value);
//
//			case LEQ:
//				// TODO: ajouter les comparaisons de date/temps
//				oRight = evalSimpleExpression(binExp.e2);
//				return new ObjectExpression(ObjectType.booleanType,
//						(Integer) oLeft.value <= (Integer) oRight.value);
//
//			case GEQ:
//				// TODO: ajouter les comparaisons de date/temps
//				oRight = evalSimpleExpression(binExp.e2);
//				return new ObjectExpression(ObjectType.booleanType,
//						(Integer) oLeft.value >= (Integer) oRight.value);
//
//			case EQBOOL:
//				// TODO: ajouter les comparaisons de date/temps
//				oRight = evalSimpleExpression(binExp.e2);
//
//				if (oLeft.type.equals(ObjectType.intType)) {
//					return new ObjectExpression(ObjectType.booleanType,
//							(Integer) oLeft.value == (Integer) oRight.value);
//				}
//				if (oLeft.type.equals(ObjectType.stringType)) {
//					return new ObjectExpression(ObjectType.booleanType,
//							((String) oLeft.value)
//									.equals((String) oRight.value));
//				}
//				if (oLeft.type.equals(ObjectType.booleanType)) {
//					return new ObjectExpression(ObjectType.booleanType,
//							(Boolean) oLeft.value == (Boolean) oRight.value);
//				}
//				if (oLeft.type.equals(ObjectType.charType)) {
//					return new ObjectExpression(ObjectType.booleanType,
//							(Character) oLeft.value == (Character) oRight.value);
//				}
//				break;
//
//			default:
//				break;
//			}
//		} else if (exp instanceof UnaryOperatorExpression) {
//			unExp = (UnaryOperatorExpression) exp;
//
//			switch (unExp.op) {
//			case NEG:
//				o1 = evalSimpleExpression(unExp.exp);
//				return new ObjectExpression(ObjectType.booleanType,
//						!((Boolean) o1.value));
//
//			default:
//				break;
//			}
//		} else if (exp instanceof VariableExpression) {
//			return variables.get(((VariableExpression) exp).name);
//		} else if (exp instanceof CharacterVariableExpression) {
//			return new ObjectExpression(
//					ObjectType.intType,
//					dataLoader
//							.getPlayerVariable(((CharacterVariableExpression) exp).name));
//		}
//
//		return null;
//	}
//
//	private ObjectExpression evalPathExpression(PathExpression exp) {
//		if (exp instanceof VariableExpression) {
//			return variables.get(((VariableExpression) exp).name);
//		}
//		// else if (exp instanceof MemberAccessExpression)
//		// {
//		// memberExp = (MemberAccessExpression)exp;
//		// }
//
//		// erreur
//		return null;
//	}
//
//	private void removeDeclaredVariables(ArrayList<String> declVariables) {
//		if (declVariables == null) {
//			return;
//		}
//
//		for (String s : declVariables) {
//			variables.remove(s);
//		}
//	}
}
