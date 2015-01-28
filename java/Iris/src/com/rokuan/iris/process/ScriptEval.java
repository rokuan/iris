package com.rokuan.iris.process;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.rokuan.iris.data.IrisCharacter;
import com.rokuan.iris.data.IrisImage;
import com.rokuan.iris.enums.InputType;
import com.rokuan.iris.events.EvalListener;
import com.rokuan.iris.expression.*;
import com.rokuan.iris.instruction.*;
import com.rokuan.iris.interfaces.IIrisBackgroundFrame;
import com.rokuan.iris.interfaces.IIrisDataLoader;
import com.rokuan.iris.interfaces.IIrisDialogBox;
import com.rokuan.iris.interfaces.IIrisImagePanel;
import com.rokuan.iris.interfaces.IIrisInputBox;
import com.rokuan.iris.interfaces.IIrisResources;
import com.rokuan.iris.interfaces.IIrisSelectBox;
import com.rokuan.iris.interfaces.IIrisSoundPlayer;
//import com.rokuan.iris.interfaces.IIrisTextBox;
import com.rokuan.iris.interfaces.IIrisVideoPlayer;
import com.rokuan.iris.types.ObjectType;

public class ScriptEval {
	/*
	 * CONTEXT
	 */
	private IIrisDataLoader dataLoader;
	private IIrisResources resources;

	/*
	 * COMPONENTS
	 */
	/*
	 * private IIrisTextBox leftSpeaker; private IIrisTextBox rightSpeaker;
	 */
	private IIrisDialogBox messageBox;

	private IIrisInputBox inputBox;

	private IIrisSelectBox selectBox;

	private IIrisBackgroundFrame backgroundFrame;
	private IIrisSoundPlayer soundPlayer;
	private IIrisVideoPlayer videoPlayer;

	// TODO: changer la taille pour coller au nombre de positions possibles
	private IIrisImagePanel[] panels = new IIrisImagePanel[3];

	// -----Expression

	private LinkedList<VariableEnvironment> env = new LinkedList<VariableEnvironment>();
	private ObjectExpression o1;

	private LiteralExpression lit;
	private UnaryOperatorExpression unExp;
	private MemberAccessExpression memberExp;

	// -----Expression

	private boolean shouldEvalSeq = true;
	private boolean continueEval = false;
	private LinkedList<SequenceExpression> runningSeqs = new LinkedList<SequenceExpression>();

	private IfExpression ifExp;
	private SwitchExpression switchExp;
	private WhileExpression whileExp;
	private DoWhileExpression doWhileExp;
	private ForExpression forExp;

	// -----InstructionExpression

	private IInstruction lastInstr;

	private ValueExpression valueExp;
	private AssignExpression assignExp;
	private GotoInstruction gotoExp;
	private MsgDisplayInstruction msgExp;
	private PlayMediaInstruction playExp;
	private ShowImageInstruction showExp;
	private SetVariableInstruction setExp;
	private InputInstruction inputExp;
	private MenuInstruction menuExp;
	private SetBackgroundInstruction bgdInstr;

	private IrisImage img;
	private IrisCharacter charac;
	private NpcMemberExpression npcExp;

	// -----Listener

	private EvalListener listener = null;

	public ScriptEval(IIrisDataLoader data, IIrisResources res,
			IIrisDialogBox message, IIrisInputBox input, IIrisSelectBox select,
			IIrisImagePanel left, IIrisImagePanel center,
			IIrisImagePanel right, IIrisBackgroundFrame bgd,
			IIrisSoundPlayer sPlayer, IIrisVideoPlayer vPlayer) {
		this.dataLoader = data;
		this.resources = res;

		this.messageBox = message;
		this.inputBox = input;
		this.selectBox = select;

		this.panels[0] = left;
		this.panels[1] = center;
		this.panels[2] = right;

		this.backgroundFrame = bgd;

		this.soundPlayer = sPlayer;
		this.videoPlayer = vPlayer;

		this.messageBox.hideComponent();
		this.inputBox.hideComponent();
		this.selectBox.hideComponent();
	}

	public void talkToNpc(String npcName) {
		this.talkToNpc(npcName, "main");
	}

	public void talkToNpc(String npcName, String labelName) {
		try {
			this.shouldEvalSeq = true;
			this.continueEval = false;
			evalExpression(this.resources.getNpc(npcName).labels.get(labelName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void talkToNpc(String npcName, EvalListener eList) {
		this.setEvaluationListener(eList);
		this.talkToNpc(npcName);
		this.checkForEnd();
		this.setEvaluationListener(null);
	}

	/*
	 * private void checkForStart(){
	 * 
	 * }
	 */

	private void checkForEnd() {
		if (this.runningSeqs.isEmpty() && lastInstr == null
				&& this.listener != null) {
			this.listener.onEvalEnd();
		}
	}

	public void setEvaluationListener(EvalListener eListener) {
		this.listener = eListener;
	}

	public void nextMessage() {
		// TODO: verifier si l'evaluation est terminee
		if (lastInstr != null
				&& ((lastInstr instanceof NextMsgDialogInstruction) || (lastInstr instanceof CloseMsgDialogInstruction))) {
			this.restartEval();
		}
	}

	public void validateInput() {
		// TODO: verifier la validite du contenu
		if (lastInstr != null && lastInstr instanceof InputInstruction) {
			this.restartEval();
		}
	}

	public void validateSelection() {
		if (lastInstr != null && lastInstr instanceof MenuInstruction
				&& this.selectBox.getSelectedIndex() >= 0) {
			this.restartEval();
		}
	}

	private void clearForeground() {
		for (int i = 0; i < panels.length; i++) {
			if (panels[i] != null) {
				panels[i].removeSource();
			}
		}
	}

	private void clearBackground() {
		if (backgroundFrame != null) {
			backgroundFrame.removeSource();
		}
	}

	private void endEval() {
		runningSeqs.clear();
		env.clear();

		shouldEvalSeq = true;
		continueEval = false;

		this.clearMessage();
		this.clearInput();
		this.clearMenu();
		this.clearBackground();
		this.clearForeground();
		// TODO: Stop sound, video

		this.hideAll();

		this.checkForEnd();
	}

	private void clearMessage() {
		if (messageBox != null) {
			messageBox.removeSpeaker();
			messageBox.clearContent();
		}
	}

	private void clearInput() {
		if (inputBox != null) {
			inputBox.clearContent();
		}
	}

	private void clearMenu() {
		if (selectBox != null) {
			selectBox.clearContent();
		}
	}

	private void hideAll() {
		if (messageBox != null) {
			messageBox.hideComponent();
		}
		if (inputBox != null) {
			inputBox.hideComponent();
		}
		if (selectBox != null) {
			selectBox.hideComponent();
		}

		for (int i = 0; i < panels.length; i++) {
			if (panels[i] != null) {
				panels[i].hideComponent();
			}
		}

		if (backgroundFrame != null) {
			backgroundFrame.hideComponent();
		}
	}

	private void newVariable(String varName, ObjectExpression varValue) {
		this.env.getFirst().addVariable(varName, varValue);
	}

	private void updateVariable(String varName, ObjectExpression varValue) {
		for (VariableEnvironment vEnv : env) {
			if (vEnv.containsVariable(varName)) {
				vEnv.addVariable(varName, varValue);
				return;
			}
		}
	}

	private ObjectExpression getVariable(String varName) {
		for (VariableEnvironment vEnv : env) {
			if (vEnv.containsVariable(varName)) {
				return vEnv.getVariable(varName);
			}
		}

		return null;
	}

	private void restartEval() {
		shouldEvalSeq = true;
		continueEval = true;

		try {
			if (lastInstr != null) {
				if (lastInstr instanceof InputInstruction) {
					inputExp = (InputInstruction) lastInstr;

					if (inputBox.getInputType() == InputType.INPUT_INT_TYPE) {
						this.updateVariable(inputExp.name,
								new ObjectExpression(ObjectType.intType,
										inputBox.getIntValue()));
					} else if (inputBox.getInputType() == InputType.INPUT_STRING_TYPE) {
						this.updateVariable(inputExp.name,
								new ObjectExpression(ObjectType.stringType,
										inputBox.getValue()));
					}

					this.inputBox.hideComponent();
					this.selectBox.enableComponent();
					this.messageBox.enableComponent();

					lastInstr = null;
				} else if (lastInstr instanceof MenuInstruction) {
					menuExp = (MenuInstruction) lastInstr;
					int selectedIndex = selectBox.getSelectedIndex();

					this.selectBox.hideComponent();
					this.inputBox.enableComponent();
					this.messageBox.enableComponent();

					lastInstr = null;

					for (SwitchCaseExpression sce : menuExp.cases) {
						if (sce instanceof CaseExpression) {
							if (isCatched(selectedIndex, sce, false)) {
								break;
							}
						} else if (sce instanceof DefaultExpression) {
							evalExpression(((DefaultExpression) sce).body);
							break;
						}
					}
				} else if (lastInstr instanceof CloseMsgDialogInstruction) {
					// TODO:
					this.messageBox.clearContent();
					this.messageBox.hideComponent();
					lastInstr = null;
				} else if (lastInstr instanceof NextMsgDialogInstruction) {
					this.messageBox.clearContent();
					lastInstr = null;
				}
			}

			if (!shouldEvalSeq) {
				return;
			}

			while (!runningSeqs.isEmpty()) {
				evalExpression(runningSeqs.removeLast());

				if (!shouldEvalSeq) {
					return;
				}

				//env.removeFirst();
			}

			env.clear();
			this.checkForEnd();
		} catch (Exception e) {
			// System.err.println(e.);
			e.printStackTrace();
		}
	}

	private void evalInstruction(IInstruction instr) {
		if (instr instanceof ValueExpression) {
			valueExp = (ValueExpression) instr;

			if (valueExp.type.equals(ObjectType.intType)) {
				this.newVariable(valueExp.name, new ObjectExpression(
						valueExp.type, 0));
			} else if (valueExp.type.equals(ObjectType.booleanType)) {
				this.newVariable(valueExp.name, new ObjectExpression(
						valueExp.type, false));
			} else if (valueExp.type.equals(ObjectType.stringType)) {
				this.newVariable(valueExp.name, new ObjectExpression(
						valueExp.type, ""));
			}

			if (valueExp.value != null) {
				this.newVariable(valueExp.name, new ObjectExpression(
						valueExp.type,
						evalSimpleExpression(valueExp.value).value));
			}

			return;
		} else if (instr instanceof AssignExpression) {
			assignExp = (AssignExpression) instr;

			this.updateVariable(assignExp.name,
					evalSimpleExpression(assignExp.value));

			return;
		} else if (instr instanceof OpenMsgDialogInstruction) {
			messageBox.showComponent();
			return;
		} else if (instr instanceof MsgDisplayInstruction) {
			msgExp = (MsgDisplayInstruction) instr;

			o1 = evalSimpleExpression(msgExp.msg);

			if (!o1.type.equals(ObjectType.stringType)) {
				return;
			}

			messageBox.appendText(((String) o1.value) + '\n');
			return;
		} else if (instr instanceof NextMsgDialogInstruction) {
			shouldEvalSeq = false;
			lastInstr = instr;
			return;
		} else if (instr instanceof CloseMsgDialogInstruction) {
			shouldEvalSeq = false;
			lastInstr = instr;
			return;
		} else if (instr instanceof SwitchCharacterInstruction) {
			try {
				IrisCharacter character = resources
						.getCharacter(((SwitchCharacterInstruction) instr).character.name);
				messageBox.setSpeaker(character.charName);
			} catch (Exception e) {
				System.out.println(e.toString());
				return;
			}

			return;
		} else if (instr instanceof RemoveCharacter) {
			messageBox.removeSpeaker();
			return;
		} else if (instr instanceof SetVariableInstruction) {
			setExp = (SetVariableInstruction) instr;

			if (setExp.variable instanceof PlayerStringVariableExpression) {
				dataLoader.setPlayerStringVariable(setExp.variable.name,
						(String) (evalSimpleExpression(setExp.value).value));
			} else if (setExp.variable instanceof PlayerIntVariableExpression) {
				dataLoader.setPlayerIntVariable(setExp.variable.name,
						(Integer) (evalSimpleExpression(setExp.value).value));
			}
			return;
		} else if (instr instanceof InputInstruction) {
			inputExp = (InputInstruction) instr;

			o1 = this.getVariable(inputExp.name);

			shouldEvalSeq = false;

			if (o1.type.equals(ObjectType.intType)) {
				inputBox.setInputType(InputType.INPUT_INT_TYPE);

				lastInstr = inputExp;
			} else if (o1.type.equals(ObjectType.stringType)) {
				inputBox.setInputType(InputType.INPUT_STRING_TYPE);

				lastInstr = inputExp;
			} else {
				return;
			}

			inputBox.enableComponent();
			inputBox.showComponent();
			messageBox.disableComponent();
			selectBox.disableComponent();

			return;
		} else if (instr instanceof MenuInstruction) {
			menuExp = (MenuInstruction) instr;
			shouldEvalSeq = false;

			selectBox.setChoices(((MenuInstruction) instr).choices);

			lastInstr = menuExp;

			selectBox.enableComponent();
			selectBox.showComponent();
			messageBox.disableComponent();
			inputBox.disableComponent();

			return;
		} else if (instr instanceof PlayMediaInstruction) {
			playExp = (PlayMediaInstruction) instr;

			try {
				soundPlayer.setSource(resources.getSound(playExp.name));
				soundPlayer.playSound();
			} catch (Exception e) {
				return;
			}

			return;
		} else if (instr instanceof StopMediaInstruction) {
			soundPlayer.stopSound();
			return;
		} else if (instr instanceof ShowImageInstruction) {
			showExp = (ShowImageInstruction) instr;

			if (showExp.image instanceof VariableExpression) {
				img = resources
						.getImage(((VariableExpression) showExp.image).name);
			} else {
				memberExp = (MemberAccessExpression) showExp.image;

				charac = resources.getCharacter(memberExp.name);
				img = charac.getImage(memberExp.field);
			}

			try {
				panels[showExp.position.ordinal()].setSource(img);
			} catch (Exception e) {
				return;
			}

			return;
		} else if (instr instanceof SetBackgroundInstruction) {
			bgdInstr = (SetBackgroundInstruction) instr;

			try {
				backgroundFrame.setSource(resources
						.getBackground(bgdInstr.image.name));
			} catch (Exception e) {
				System.err.println(e.toString());
				return;
			}

			return;
		} else if (instr instanceof CleanBackgroundInstruction) {
			backgroundFrame.removeSource();
			return;
		} else if (instr instanceof CleanForegroundInstruction) {
			for (int i = 0; i < panels.length; i++) {
				try {
					panels[i].removeSource();
				} catch (Exception e) {

				}
			}
			return;
		} else if (instr instanceof GotoInstruction) {
			gotoExp = (GotoInstruction) instr;

			npcExp = gotoExp.npc;

			evalExpression(resources.getNpc(npcExp.npc).labels
					.get(npcExp.label));

			return;
		} else if (instr instanceof MacroCallExpression) {
			evalMacroExpression((MacroCallExpression) instr);
		} else if (instr instanceof EndInstruction) {
			this.endEval();
		}
	}

	private ObjectExpression evalMacroExpression(MacroCallExpression mce) {
		switch (mce.macro) {
		case SLEEP:
			if (mce.arguments.isEmpty()) {
				return null;
			}

			ObjectExpression sleepTime = evalSimpleExpression(mce.arguments
					.get(0));
			int seconds = 0;

			if (sleepTime.type.equals(ObjectType.intType)) {
				seconds = (Integer) sleepTime.value;

				try {
					Thread.sleep(1000 * seconds);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;

		case GETTIME:
			if (mce.arguments.isEmpty()) {
				return new ObjectExpression(ObjectType.intType, 0);
			}

			ObjectExpression timeField = evalSimpleExpression(mce.arguments
					.get(0));
			int timeResult = 0;

			if (timeField.type.equals(ObjectType.intType)) {
				Integer intValue = (Integer) timeField.value;

				switch (intValue) {
				case 0:
					timeResult = dataLoader.getSeconds();
					break;
				case 1:
					timeResult = dataLoader.getMinutes();
					break;
				case 2:
				default:
					timeResult = dataLoader.getHour();
					break;
				}
			} else if (timeField.type.equals(ObjectType.stringType)) {
				String strValue = (String) timeField.value;

				if (strValue.equals("SECONDS")) {
					timeResult = dataLoader.getSeconds();
				} else if (strValue.equals("MINUTES")) {
					timeResult = dataLoader.getMinutes();
				}
				// } else if(strValue.equals("HOUR")){
				else {
					timeResult = dataLoader.getHour();
				}
			} else {
				return new ObjectExpression(ObjectType.intType, 0);
			}

			return new ObjectExpression(ObjectType.intType, timeResult);

		case GETDATE:
			if (mce.arguments.isEmpty()) {
				return new ObjectExpression(ObjectType.intType, 0);
			}

			ObjectExpression dateField = evalSimpleExpression(mce.arguments
					.get(0));
			int dateResult = 0;

			if (dateField.type.equals(ObjectType.intType)) {
				Integer intValue = (Integer) dateField.value;

				switch (intValue) {
				case 0:
					dateResult = dataLoader.getDay();
					break;
				case 1:
					dateResult = dataLoader.getMonth();
					break;
				case 2:
				default:
					dateResult = dataLoader.getYear();
					break;
				}
			} else if (dateField.type.equals(ObjectType.stringType)) {
				String strValue = (String) dateField.value;

				if (strValue.equals("DAY")) {
					dateResult = dataLoader.getDay();
				} else if (strValue.equals("MONTH")) {
					dateResult = dataLoader.getMonth();
				} else {
					dateResult = dataLoader.getYear();
				}
			} else {
				return new ObjectExpression(ObjectType.intType, 0);
			}

			return new ObjectExpression(ObjectType.intType, dateResult);
		case GETFULLTIME:
			return new ObjectExpression(ObjectType.stringType,
					dataLoader.getTime());
		case GETFULLDATE:
			return new ObjectExpression(ObjectType.stringType,
					dataLoader.getDate());

		case GETITEM:
			if (mce.arguments.size() < 2) {
				return new ObjectExpression(ObjectType.booleanType, false);
			}

			ObjectExpression itemToGet = evalSimpleExpression(mce.arguments
					.get(0));
			ObjectExpression qtyToGet = evalSimpleExpression(mce.arguments
					.get(1));

			if (itemToGet.type.equals(ObjectType.intType)
					&& qtyToGet.type.equals(ObjectType.intType)) {
				return new ObjectExpression(ObjectType.booleanType,
						dataLoader.addItem((Integer) itemToGet.value,
								(Integer) qtyToGet.value));
			}

			return new ObjectExpression(ObjectType.booleanType, false);
		case COUNTITEM:
			if (mce.arguments.size() < 2) {
				return new ObjectExpression(ObjectType.booleanType, false);
			}

			ObjectExpression itemToCount = evalSimpleExpression(mce.arguments
					.get(0));

			if (itemToCount.type.equals(ObjectType.intType)) {
				return new ObjectExpression(ObjectType.intType,
						dataLoader.countItem((Integer) itemToCount.value));
			}

			return new ObjectExpression(ObjectType.intType, 0);
		case DELETEITEM:
			if (mce.arguments.size() < 2) {
				return new ObjectExpression(ObjectType.booleanType, false);
			}

			ObjectExpression itemToDelete = evalSimpleExpression(mce.arguments
					.get(0));
			ObjectExpression qtyToDelete = evalSimpleExpression(mce.arguments
					.get(1));

			if (itemToDelete.type.equals(ObjectType.intType)
					&& qtyToDelete.type.equals(ObjectType.intType)) {
				return new ObjectExpression(ObjectType.booleanType,
						dataLoader.deleteItem((Integer) itemToDelete.value,
								(Integer) qtyToDelete.value));
			}

			return new ObjectExpression(ObjectType.booleanType, false);

		case RAND:
			int randValue = 1;
			Random r = new Random();

			if (mce.arguments.isEmpty()) {
				randValue = r.nextInt();
			} else {
				ObjectExpression randBound = evalSimpleExpression(mce.arguments
						.get(0));

				if (randBound.type.equals(ObjectType.intType)) {
					randValue = r.nextInt((Integer) randBound.value);
				} else {
					randValue = r.nextInt();
				}
			}

			return new ObjectExpression(ObjectType.intType, randValue);
		}

		return null;
	}

	private void evalExpression(Expression exp) {
		if (exp == null) {
			return;
		}

		if (exp instanceof InstructionExpression) {
			evalInstruction((InstructionExpression) exp);

			return;
		} else if (exp instanceof SequenceExpression) {
			SequenceExpression seqExp = (SequenceExpression) exp, seqTmp;
			Expression expTmp;

			if (!shouldEvalSeq) {
				return;
			}

			if(!continueEval){
				this.env.addFirst(new VariableEnvironment());
			} else {
				continueEval = false;
			}
			
			if (!seqExp.exps.isEmpty()) {
				seqTmp = new SequenceExpression(new ArrayList<Expression>(
						seqExp.exps));				
				
				this.runningSeqs.add(seqTmp);

				while (seqTmp.exps.size() > 0) {
					expTmp = seqTmp.exps.remove(0);
					evalExpression(expTmp);

					if (!shouldEvalSeq) {
						return;
					}
				}

				this.runningSeqs.removeLast();
			}
			
			this.env.removeFirst();

			return;
		} else if (exp instanceof IfExpression) {
			ifExp = (IfExpression) exp;

			o1 = evalSimpleExpression(ifExp.condition);

			if ((Boolean) o1.value) {
				evalExpression(ifExp.corps);

				return;
			}

			if (ifExp.elseIfList != null) {
				for (ElseIfExpression elseIfExp : ifExp.elseIfList) {
					o1 = evalSimpleExpression(elseIfExp.condition);

					if ((Boolean) o1.value) {
						evalExpression(elseIfExp.corps);

						return;
					}
				}
			}

			if (ifExp.elseBlock != null) {
				evalExpression(ifExp.elseBlock.corps);
				return;
			}

			return;
		} else if (exp instanceof SwitchExpression) {
			switchExp = (SwitchExpression) exp;

			o1 = evalSimpleExpression(switchExp.exp);

			for (SwitchCaseExpression sce : switchExp.cases) {
				if (sce instanceof CaseExpression) {
					if (o1.type.equals(ObjectType.stringType)
							&& isCatched((String) o1.value, sce, false)) {
						return;
					} else if (o1.type.equals(ObjectType.intType)
							&& isCatched((Integer) o1.value, sce, false)) {
						return;
					}

				} else if (sce instanceof DefaultExpression) {
					evalExpression(((DefaultExpression) sce).body);
					return;
				}
			}

			return;
		} else if (exp instanceof WhileExpression) {
			whileExp = (WhileExpression) exp;

			while (true) {
				o1 = evalSimpleExpression(whileExp.condition);

				if (!(Boolean) o1.value) {
					return;
				}

				evalExpression(whileExp.body);
			}
		} else if (exp instanceof DoWhileExpression) {
			doWhileExp = (DoWhileExpression) exp;

			do {
				evalExpression(doWhileExp.body);

				o1 = evalSimpleExpression(doWhileExp.condition);

				if (!(Boolean) o1.value) {
					return;
				}

			} while (true);
		} else if (exp instanceof ForExpression) {
			forExp = (ForExpression) exp;

			evalExpression(forExp.init);

			while (true) {
				o1 = evalSimpleExpression(forExp.condition);

				if (!(Boolean) o1.value) {
					return;
				}

				evalExpression(forExp.body);
				evalExpression(forExp.maj);
			}
		}
	}

	private boolean isCatched(String value, SwitchCaseExpression sce,
			boolean preCatch) {
		if (sce instanceof CaseExpression) {
			CaseExpression cExp = (CaseExpression) sce;

			if (cExp.next == null) {
				if (preCatch) {
					evalExpression(cExp.body);
					return true;
				}

				// if (!cExp.literal.value.stringValue.equals(value) &&
				// !preCatch)
				if (!cExp.literal.value.stringValue.equals(value)) {
					return false;
				} else {
					evalExpression(cExp.body);
					return true;
				}
			} else {
				if (preCatch) {
					return isCatched(value, cExp.next, preCatch);
				}

				if (cExp.literal.value.stringValue.equals(value)) {
					return isCatched(value, cExp.next, true);
				}

				return isCatched(value, cExp.next, preCatch);
			}

		} else if (sce instanceof DefaultExpression) {
			evalExpression(((DefaultExpression) sce).body);
			return true;
		}

		return false;
	}

	private boolean isCatched(int value, SwitchCaseExpression sce,
			boolean preCatch) {
		if (sce instanceof CaseExpression) {
			CaseExpression cExp = (CaseExpression) sce;

			if (cExp.next == null) {
				if (preCatch) {
					evalExpression(cExp.body);
					return true;
				}

				if (cExp.literal.value.intValue != value && !preCatch) {
					return false;
				} else {
					evalExpression(cExp.body);
					return true;
				}
			} else {
				if (preCatch) {
					return isCatched(value, cExp.next, preCatch);
				}

				if (cExp.literal.value.intValue == value) {
					return isCatched(value, cExp.next, true);
				}

				return isCatched(value, cExp.next, preCatch);
			}

		} else if (sce instanceof DefaultExpression) {
			evalExpression(((DefaultExpression) sce).body);
			return true;
		}

		return false;
	}

	private ObjectExpression evalSimpleExpression(SimpleExpression exp) {
		if (exp instanceof LiteralExpression) {
			lit = (LiteralExpression) exp;

			switch (lit.value.type) {
			case STRING:
				return new ObjectExpression(ObjectType.stringType,
						lit.value.stringValue);

			case INT:
				return new ObjectExpression(ObjectType.intType,
						lit.value.intValue);

			case CHAR:
				return new ObjectExpression(ObjectType.charType,
						lit.value.charValue);

			case BOOLEAN:
				return new ObjectExpression(ObjectType.booleanType,
						lit.value.booleanValue);

			default:
				break;
			}
		} else if (exp instanceof SubSimpleExpression) {
			return evalSimpleExpression(((SubSimpleExpression) exp).exp);
		} else if (exp instanceof NullExpression) {
			return null;
		} else if (exp instanceof BinaryOperatorExpression) {
			BinaryOperatorExpression binExp = (BinaryOperatorExpression) exp;
			ObjectExpression oLeft = evalSimpleExpression(binExp.e1), oRight;

			switch (binExp.op) {
			case AND:
				if ((Boolean) oLeft.value) {
					oRight = evalSimpleExpression(binExp.e2);
					return new ObjectExpression(ObjectType.booleanType,
							(Boolean) oRight.value);
				}

				return new ObjectExpression(ObjectType.booleanType, false);

			case OR:
				if ((Boolean) oLeft.value) {
					return new ObjectExpression(ObjectType.booleanType, true);
				}

				oRight = evalSimpleExpression(binExp.e2);
				return new ObjectExpression(ObjectType.booleanType,
						(Boolean) oRight.value);

			case PLUS:
				oRight = evalSimpleExpression(binExp.e2);

				if (oLeft.type.equals(ObjectType.stringType)
						|| oRight.type.equals(ObjectType.stringType)) {
					return new ObjectExpression(ObjectType.stringType,
							oLeft.value.toString() + oRight.value.toString());
				}
				if (oLeft.type.equals(ObjectType.intType)
						&& oRight.type.equals(ObjectType.intType)) {
					return new ObjectExpression(ObjectType.intType,
							(Integer) oLeft.value + (Integer) oRight.value);
				}
				break;

			case MINUS:
				oRight = evalSimpleExpression(binExp.e2);
				return new ObjectExpression(ObjectType.intType,
						(Integer) oLeft.value - (Integer) oRight.value);

			case TIMES:
				oRight = evalSimpleExpression(binExp.e2);
				return new ObjectExpression(ObjectType.intType,
						(Integer) oLeft.value * (Integer) oRight.value);

			case DIV:
				oRight = evalSimpleExpression(binExp.e2);
				return new ObjectExpression(ObjectType.intType,
						(Integer) oLeft.value / (Integer) oRight.value);

			case LT:
			case GT:
			case LEQ:
			case GEQ:
				boolean result = false;
				int cmp = 0;
				oRight = evalSimpleExpression(binExp.e2);
				
				if(oLeft.type.equals(oRight.type)){
					if(oLeft.type.equals(ObjectType.intType)){
						cmp = ((Integer)oLeft.value).compareTo((Integer)oRight.value);
					} else if(oLeft.type.equals(ObjectType.stringType)){
						cmp = ((String)oLeft.value).compareTo((String)oRight.value);
					} else if(oLeft.type.equals(ObjectType.characterType)){
						cmp = ((Character)oLeft.value).compareTo((Character)oRight.value);
					}
					
					switch(binExp.op){
					case LT:
						result = (cmp < 0);
						break;
					case GT:
						result = (cmp > 0);
						break;
					case LEQ:
						result = (cmp <= 0);
						break;
					case GEQ:
						result = (cmp >= 0);
						break;
					default:
						result = false;
						break;
					}
				}
				
				return new ObjectExpression(ObjectType.booleanType, result);
				
			/*case LT:
				// TODO: ajouter les comparaisons de date/temps
				oRight = evalSimpleExpression(binExp.e2);
				return new ObjectExpression(ObjectType.booleanType,
						(Integer) oLeft.value < (Integer) oRight.value);

			case GT:
				// TODO: ajouter les comparaisons de date/temps
				oRight = evalSimpleExpression(binExp.e2);
				
				if(oLeft.type.equals(oRight.type)){
					boolean result = false;
					
					if(oLeft.type.equals(ObjectType.intType)){
						result = (Integer)oLeft.value > (Integer)oRight.value;
					} else if(oLeft.type.equals(ObjectType.stringType)){
						result = ((String)oLeft.value).compareTo((String)oRight.value) > 0;
					} else {
						// TODO: throw exception
					}
					
					return new ObjectExpression(ObjectType.booleanType, result);
				}
				
				// TODO: throw exception

			case LEQ:
				// TODO: ajouter les comparaisons de date/temps
				oRight = evalSimpleExpression(binExp.e2);
				return new ObjectExpression(ObjectType.booleanType,
						(Integer) oLeft.value <= (Integer) oRight.value);

			case GEQ:
				// TODO: ajouter les comparaisons de date/temps
				oRight = evalSimpleExpression(binExp.e2);
				return new ObjectExpression(ObjectType.booleanType,
						(Integer) oLeft.value >= (Integer) oRight.value);*/

			case EQBOOL:
				// TODO: ajouter les comparaisons de date/temps
				oRight = evalSimpleExpression(binExp.e2);

				/*if (oLeft.type.equals(ObjectType.intType)) {
					return new ObjectExpression(ObjectType.booleanType,
							(Integer) oLeft.value == (Integer) oRight.value);
				}
				if (oLeft.type.equals(ObjectType.stringType)) {
					return new ObjectExpression(ObjectType.booleanType,
							((String) oLeft.value)
									.equals((String) oRight.value));
				}
				if (oLeft.type.equals(ObjectType.booleanType)) {
					return new ObjectExpression(ObjectType.booleanType,
							(Boolean) oLeft.value == (Boolean) oRight.value);
				}
				if (oLeft.type.equals(ObjectType.charType)) {
					return new ObjectExpression(ObjectType.booleanType,
							(Character) oLeft.value == (Character) oRight.value);
				}*/
				return new ObjectExpression(ObjectType.booleanType, oLeft.value.equals(oRight.value));
				//break;

			default:
				break;
			}
		} else if (exp instanceof UnaryOperatorExpression) {
			unExp = (UnaryOperatorExpression) exp;

			switch (unExp.op) {
			case NEG:
				o1 = evalSimpleExpression(unExp.exp);
				return new ObjectExpression(ObjectType.booleanType,
						!((Boolean) o1.value));

			default:
				break;
			}
		} else if (exp instanceof VariableExpression) {
			return this.getVariable(((VariableExpression) exp).name);
		} else if (exp instanceof PlayerStringVariableExpression) {
			return new ObjectExpression(
					ObjectType.stringType,
					dataLoader
							.getPlayerStringVariable(((CharacterVariableExpression) exp).name));
		} else if (exp instanceof PlayerIntVariableExpression) {
			return new ObjectExpression(
					ObjectType.intType,
					dataLoader
							.getPlayerIntVariable(((CharacterVariableExpression) exp).name));

		}

		return null;
	}

	private ObjectExpression evalPathExpression(PathExpression exp) {
		if (exp instanceof VariableExpression) {
			return this.getVariable(((VariableExpression) exp).name);
		}
		// else if (exp instanceof MemberAccessExpression)
		// {
		// memberExp = (MemberAccessExpression)exp;
		// }

		// erreur
		return null;
	}

	/*
	 * private void removeDeclaredVariables(ArrayList<String> declVariables) {
	 * if (declVariables == null) { return; }
	 * 
	 * for (String s : declVariables) { variables.remove(s); } }
	 */
}
