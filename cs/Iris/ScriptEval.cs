using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.ExpressionElement;
using Iris.ExpressionElement.Instruction;
using System.Threading;
using Iris.Framework;
using Iris.SimpleExpressionElement;
using Iris.Data;

namespace Iris
{
    public class ScriptEval
    {
        private Dictionary<string, ObjectExpression> variables = new Dictionary<string, ObjectExpression>();
        /*private Dictionary<string, ClassStructure> classes = new Dictionary<string, ClassStructure>();
        private Dictionary<string, List<FunctionDefinition>> functions = new Dictionary<string, List<FunctionDefinition>>();*/

        private Dictionary<string, IrisCharacter> characters = new Dictionary<string, IrisCharacter>();
        private Dictionary<string, IrisImage> images = new Dictionary<string, IrisImage>();
        private Dictionary<string, IrisBackground> backgrounds = new Dictionary<string, IrisBackground>();
        private Dictionary<string, IrisSound> sounds = new Dictionary<string, IrisSound>();

        private Dictionary<string, int> playerVariables = new Dictionary<string, int>();


        private ObjectExpression o1;


        private bool shouldEvalSeq = true;

        //-----SimpleExpression

        //private SimpleExpression simpleExp;
        private LiteralExpression lit;
        //private BinaryOperatorExpression binExp;
        private UnaryOperatorExpression unExp;
        private MemberAccessExpression memberExp;
        //private SubMemberExpression subMemberExp;
        //private VariableExpression varExp;
        //private MethodCallExpression methExp;
        //private NewInstanceExpression newExp;

        //-----Expression

        private List<SequenceExpression> runningSeqs = new List<SequenceExpression>();
        private List<SequenceExpression> seqs = new List<SequenceExpression>();

        private int insertIndex = 0;

        private IfExpression ifExp;
        //private ElseIfExpression elseIfExp;
        private SwitchExpression switchExp;
        //private CaseExpression caseExp;
        private WhileExpression whileExp;
        private DoWhileExpression doWhileExp;
        private ForExpression forExp;

        //-----InstructionExpression

        private InstructionExpression lastInstr;

        private ValueExpression valueExp;
        private AssignExpression assignExp;
        //private CloseMsgDialogInstruction closeExp;
        private GotoInstruction gotoExp;
        private MsgDisplayInstruction msgExp;
        //private NextMsgDialogInstruction nextExp;
        //private OpenMsgDialogInstruction openExp;
        private PlayMediaInstruction playExp;
        private ShowImageInstruction showExp;
        //private StopMediaInstruction stopExp;
        private SetVariableInstruction setExp;
        private InputInstruction inputExp;
        private MenuInstruction menuExp;
        private SetBackgroundInstruction bgdInstr;


        private IrisImage img;
        private IrisCharacter charac;
        private NpcMemberExpression npcExp;

        private GameScreen gScreen;

        public ScriptEval(GameScreen gameScreen)
        {
            gScreen = gameScreen;
        }

        public void restartEval()
        {
            try
            {
                if (lastInstr != null)
                {
                    if (lastInstr is InputInstruction)
                    {
                        inputExp = (InputInstruction)lastInstr;

                        if (gScreen.iBox.inputType == InputBox.InputType.INT_INPUT)
                        {
                            variables[inputExp.name] = new ObjectExpression(ObjectType.intType, gScreen.iBox.intValue);
                        }
                        else if (gScreen.iBox.inputType == InputBox.InputType.STRING_INPUT)
                        {
                            variables[inputExp.name] = new ObjectExpression(ObjectType.stringType, gScreen.iBox.stringValue);
                        }
                    }
                    else if (lastInstr is MenuInstruction)
                    {
                        menuExp = (MenuInstruction)lastInstr;

                        foreach (SwitchCaseExpression sce in menuExp.cases)
                        {
                            if (sce is CaseExpression)
                            {
                                if(isCatched(gScreen.menBox.selectionIndex, sce, false))
                                {
                                    break;
                                }
                            }
                            else if (sce is DefaultExpression)
                            {
                                evalExpression(((DefaultExpression)sce).body);
                                break;
                            }
                        }
                    }

                    lastInstr = null;
                }

                shouldEvalSeq = true;

                int insertIndex2;
                insertIndex = runningSeqs.Count - 1;

                while (runningSeqs.Count > 0)
                {
                    insertIndex = runningSeqs.Count - 1;
                    insertIndex2 = seqs.Count - 1;

                    evalExpression(runningSeqs.Last());

                    runningSeqs.RemoveAt(insertIndex);

                    if (!shouldEvalSeq)
                    {
                        return;
                    }

                    removeDeclaredVariables(seqs.ElementAt(insertIndex2).declaredVariables());

                    seqs.RemoveAt(insertIndex2);
                }

                variables.Clear();

            }
            catch (Exception e)
            {
                System.Windows.MessageBox.Show(e.StackTrace);
                System.Windows.MessageBox.Show(e.ToString());
            }
        }

        public void evalInstruction(InstructionExpression instr)
        {
            if (instr is ValueExpression)
            {
                valueExp = (ValueExpression)instr;

                if (valueExp.type == ObjectType.intType)
                {
                    variables.Add(valueExp.name, new ObjectExpression(valueExp.type, 0));
                }
                else if (valueExp.type == ObjectType.booleanType)
                {
                    variables.Add(valueExp.name, new ObjectExpression(valueExp.type, false));
                }
                else if (valueExp.type == ObjectType.stringType)
                {
                    variables.Add(valueExp.name, new ObjectExpression(valueExp.type, ""));
                }

                if (valueExp.value != null)
                {
                    variables[valueExp.name] = new ObjectExpression(valueExp.type, evalSimpleExpression(valueExp.value).value);
                }

                return;
            }
            else if (instr is AssignExpression)
            {
                assignExp = (AssignExpression)instr;

                variables[assignExp.name] = evalSimpleExpression(assignExp.value);

                return;
            }
            else if (instr is OpenMsgDialogInstruction)
            {
                gScreen.msgBox.openBox();
                return;
            }
            else if (instr is MsgDisplayInstruction)
            {
                msgExp = (MsgDisplayInstruction)instr;

                o1 = evalSimpleExpression(msgExp.msg);

                if (o1.type != ObjectType.stringType)
                {
                    return;
                }

                gScreen.msgBox.setText((string)o1.value);

                return;
            }
            else if (instr is NextMsgDialogInstruction)
            {
                shouldEvalSeq = false;
                gScreen.msgBox.showNextButton();
                return;
            }
            else if (instr is CloseMsgDialogInstruction)
            {
                shouldEvalSeq = false;
                gScreen.msgBox.showCloseButton();
                return;
            }
            else if (instr is SwitchCharacterInstruction)
            {
                try
                {
                    gScreen.msgBox.switchCharacter(IrisData.characters[((SwitchCharacterInstruction)instr).character.name]);
                }
                catch (Exception e)
                {
                    return;
                }

                return;
            }
            else if (instr is RemoveCharacter)
            {
                gScreen.msgBox.removeCharacter();
                return;
            }
            else if (instr is SetVariableInstruction)
            {
                setExp = (SetVariableInstruction)instr;

                /*if (!playerVariables.ContainsKey(setExp.variable.name))
                {
                    playerVariables.Add(setExp.variable.name, -1);
                }

                playerVariables[setExp.variable.name] = (int)(evalSimpleExpression(setExp.value).value);*/
                IrisData.setPlayerVariable(setExp.variable.name, (int)(evalSimpleExpression(setExp.value).value));

                return;
            }
            else if (instr is InputInstruction)
            {
                inputExp = (InputInstruction)instr;

                o1 = variables[inputExp.name];

                shouldEvalSeq = false;

                if (o1.type == ObjectType.intType)
                {
                    gScreen.iBox.openBox(InputBox.InputType.INT_INPUT);

                    lastInstr = inputExp;
                }
                else if (o1.type == ObjectType.stringType)
                {
                    gScreen.iBox.openBox(InputBox.InputType.STRING_INPUT);

                    lastInstr = inputExp;
                }

                return;
            }
            else if (instr is MenuInstruction)
            {
                menuExp = (MenuInstruction)instr;
                shouldEvalSeq = false;

                gScreen.menBox.setChoices(((MenuInstruction)instr).choices);
                gScreen.menBox.openMenu();

                lastInstr = menuExp;

                return;
            }
            else if (instr is PlayMediaInstruction)
            {
                playExp = (PlayMediaInstruction)instr;

                /*if (!IrisData.sounds.ContainsKey(playExp.name))
                {
                    return;
                }*/
                //else if(IrisData.
                try
                {
                    gScreen.playMedia(IrisData.sounds[playExp.name]);
                }
                catch (Exception e)
                {
                    return;
                }

                return;
            }
            else if (instr is StopMediaInstruction)
            {
                gScreen.stopMedia();
                return;
            }
            else if (instr is ShowImageInstruction)
            {
                showExp = (ShowImageInstruction)instr;

                if (showExp.image is VariableExpression)
                {
                    img = IrisData.images[((VariableExpression)showExp.image).name];
                }
                else
                {
                    memberExp = (MemberAccessExpression)showExp.image;

                    charac = IrisData.characters[memberExp.name];
                    img = charac.getImage(memberExp.field);
                }

                gScreen.showImage(img, showExp.position);

                return;
            }
            else if (instr is SetBackgroundInstruction)
            {
                bgdInstr = (SetBackgroundInstruction)instr;

                try
                {
                    gScreen.setBackground(IrisData.backgrounds[bgdInstr.image.name]);
                }
                catch (Exception e)
                {
                    return;
                }

                return;
            }
            else if (instr is CleanBackgroundInstruction)
            {
                gScreen.clearBackground();
                return;
            }
            else if (instr is CleanForegroundInstruction)
            {
                gScreen.clearForeGround();
                return;
            }
            else if (instr is GotoInstruction)
            {
                gotoExp = (GotoInstruction)instr;

                npcExp = gotoExp.npc;

                evalExpression(IrisData.npcs[npcExp.npc].labels[npcExp.label]);

                return;
            }
        }

        public void evalExpression(Expression exp)
        {
            if (exp == null)
            {
                return;
            }

            if (exp is InstructionExpression)
            {
                evalInstruction((InstructionExpression)exp);

                return;
            }
            else if (exp is SequenceExpression)
            {
                SequenceExpression seqExp = (SequenceExpression)exp, seqTmp;
                Expression expTmp;
                int index = insertIndex;

                seqTmp = new SequenceExpression(new List<Expression>(seqExp.exps));

                seqs.Add(seqExp);
                runningSeqs.Add(seqTmp);
                index = runningSeqs.Count - 1;

                while (seqTmp.exps.Count > 0)
                {
                    expTmp = seqTmp.exps.ElementAt(0);
                    evalExpression(expTmp);

                    seqTmp.exps.RemoveAt(0);

                    if (!shouldEvalSeq)
                    {
                        if (seqTmp.exps.Count == 0)
                        {
                            runningSeqs.RemoveAt(index);
                        }

                        return;
                    }
                }

                runningSeqs.RemoveAt(runningSeqs.Count - 1);

                removeDeclaredVariables(seqs.ElementAt(seqs.Count - 1).declaredVariables());
                seqs.RemoveAt(seqs.Count - 1);

                return;
            }
            else if (exp is IfExpression)
            {
                ifExp = (IfExpression)exp;

                o1 = evalSimpleExpression(ifExp.condition);

                if ((bool)o1.value)
                {
                    evalExpression(ifExp.body);

                    return;
                }
                if (ifExp.elseIfList != null)
                {
                    foreach (ElseIfExpression elseIfExp in ifExp.elseIfList)
                    {
                        o1 = evalSimpleExpression(elseIfExp.condition);

                        if ((bool)o1.value)
                        {
                            evalExpression(elseIfExp.corps);

                            return;
                        }
                    }
                }
                if (ifExp.elseBlock != null)
                {
                    evalExpression(ifExp.elseBlock.body);

                    return;
                }

                return;
            }
            else if (exp is SwitchExpression)
            {
                switchExp = (SwitchExpression)exp;

                o1 = evalSimpleExpression(switchExp.exp);

                foreach (SwitchCaseExpression sce in switchExp.cases)
                {
                    if (sce is CaseExpression)
                    {
                        if (o1.type == ObjectType.stringType
                            && isCatched((string)o1.value, sce, false))
                        {
                            return;
                        }
                        else if (o1.type == ObjectType.intType
                            && isCatched((int)o1.value, sce, false))
                        {
                            return;
                        }

                    }
                    else if (sce is DefaultExpression)
                    {
                        evalExpression(((DefaultExpression)sce).body);
                        return;
                    }
                }

                return;
            }
            else if (exp is WhileExpression)
            {
                whileExp = (WhileExpression)exp;

                while (true)
                {
                    o1 = evalSimpleExpression(whileExp.condition);

                    if (!(bool)o1.value)
                    {
                        return;
                    }

                    evalExpression(whileExp.body);
                }
            }
            else if (exp is DoWhileExpression)
            {
                doWhileExp = (DoWhileExpression)exp;

                do
                {
                    evalExpression(doWhileExp.body);

                    o1 = evalSimpleExpression(doWhileExp.condition);

                    if (!(bool)o1.value)
                    {
                        return;
                    }

                } while (true);
            }
            else if (exp is ForExpression)
            {
                forExp = (ForExpression)exp;

                evalExpression(forExp.init);

                while (true)
                {
                    o1 = evalSimpleExpression(forExp.condition);

                    if (!(bool)o1.value)
                    {
                        return;
                    }

                    evalExpression(forExp.body);
                    evalExpression(forExp.maj);
                }
            }
        }

        public bool isCatched(string value, SwitchCaseExpression sce, bool preCatch)
        {
            if (sce is CaseExpression)
            {
                CaseExpression cExp = (CaseExpression)sce;

                if (cExp.next == null)
                {
                    if (preCatch)
                    {
                        evalExpression(cExp.body);
                        return true;
                    }

                    //if (!cExp.literal.value.stringValue.Equals(value) && !preCatch)
                    if (!cExp.literal.value.stringValue.Equals(value))
                    {
                        return false;
                    }
                    else
                    {
                        evalExpression(cExp.body);
                        return true;
                    }
                }
                else
                {
                    if (preCatch)
                    {
                        return isCatched(value, cExp.next, preCatch);
                    }

                    if (cExp.literal.value.stringValue.Equals(value))
                    {
                        return isCatched(value, cExp.next, true);
                    }

                    return isCatched(value, cExp.next, preCatch);
                }

            }
            else if (sce is DefaultExpression)
            {
                evalExpression(((DefaultExpression)sce).body);
                return true;
            }

            return false;
        }

        public bool isCatched(int value, SwitchCaseExpression sce, bool preCatch)
        {
            if (sce is CaseExpression)
            {
                CaseExpression cExp = (CaseExpression)sce;

                if (cExp.next == null)
                {
                    if (preCatch)
                    {
                        evalExpression(cExp.body);
                        return true;
                    }

                    if (cExp.literal.value.intValue != value && !preCatch)
                    {
                        return false;
                    }
                    else
                    {
                        evalExpression(cExp.body);
                        return true;
                    }
                }
                else
                {
                    if (preCatch)
                    {
                        return isCatched(value, cExp.next, preCatch);
                    }

                    if (cExp.literal.value.intValue == value)
                    {
                        return isCatched(value, cExp.next, true);
                    }

                    return isCatched(value, cExp.next, preCatch);
                }

            }
            else if (sce is DefaultExpression)
            {
                evalExpression(((DefaultExpression)sce).body);
                return true;
            }

            return false;
        }

        public ObjectExpression evalSimpleExpression(SimpleExpression exp)
        {
            if (exp is LiteralExpression)
            {
                lit = (LiteralExpression)exp;

                switch (lit.value.type)
                {
                    case ParserUtils.Token.TokenValue.STRING:
                        return new ObjectExpression(ObjectType.stringType, lit.value.stringValue);

                    case ParserUtils.Token.TokenValue.INT:
                        return new ObjectExpression(ObjectType.intType, lit.value.intValue);

                    case ParserUtils.Token.TokenValue.CHAR:
                        return new ObjectExpression(ObjectType.charType, lit.value.charValue);

                    case ParserUtils.Token.TokenValue.BOOLEAN:
                        return new ObjectExpression(ObjectType.booleanType, lit.value.booleanValue);

                    default:
                        break;
                }
            }
            else if (exp is SubSimpleExpression)
            {
                return evalSimpleExpression(((SubSimpleExpression)exp).exp);
            }
            else if (exp is NullExpression)
            {
                return null;
            }
            else if (exp is BinaryOperatorExpression)
            {
                BinaryOperatorExpression binExp = (BinaryOperatorExpression)exp;
                ObjectExpression oLeft = evalSimpleExpression(binExp.e1), oRight;

                switch (binExp.op)
                {
                    case ParserUtils.Token.TokenValue.AND:
                        if ((bool)oLeft.value)
                        {
                            oRight = evalSimpleExpression(binExp.e2);
                            return new ObjectExpression(ObjectType.booleanType, (bool)oRight.value);
                        }

                        return new ObjectExpression(ObjectType.booleanType, false);

                    case ParserUtils.Token.TokenValue.OR:
                        if ((bool)oLeft.value)
                        {
                            return new ObjectExpression(ObjectType.booleanType, true);
                        }

                        oRight = evalSimpleExpression(binExp.e2);
                        return new ObjectExpression(ObjectType.booleanType, (bool)oRight.value);

                    case ParserUtils.Token.TokenValue.PLUS:
                        oRight = evalSimpleExpression(binExp.e2);

                        if (oLeft.type == ObjectType.stringType || oRight.type == ObjectType.stringType)
                        {
                            return new ObjectExpression(ObjectType.stringType, oLeft.value.ToString() + oRight.value.ToString());
                        }
                        if (oLeft.type == ObjectType.intType && oRight.type == ObjectType.intType)
                        {
                            return new ObjectExpression(ObjectType.intType, (int)oLeft.value + (int)oRight.value);
                        }
                        break;

                    case ParserUtils.Token.TokenValue.MINUS:
                        oRight = evalSimpleExpression(binExp.e2);
                        return new ObjectExpression(ObjectType.intType, (int)oLeft.value - (int)oRight.value);

                    case ParserUtils.Token.TokenValue.TIMES:
                        oRight = evalSimpleExpression(binExp.e2);
                        return new ObjectExpression(ObjectType.intType, (int)oLeft.value * (int)oRight.value);

                    case ParserUtils.Token.TokenValue.DIV:
                        oRight = evalSimpleExpression(binExp.e2);
                        return new ObjectExpression(ObjectType.intType, (int)oLeft.value / (int)oRight.value);

                    case ParserUtils.Token.TokenValue.LT:
                        oRight = evalSimpleExpression(binExp.e2);
                        return new ObjectExpression(ObjectType.booleanType, (int)oLeft.value < (int)oRight.value);

                    case ParserUtils.Token.TokenValue.GT:
                        oRight = evalSimpleExpression(binExp.e2);
                        return new ObjectExpression(ObjectType.booleanType, (int)oLeft.value > (int)oRight.value);

                    case ParserUtils.Token.TokenValue.LEQ:
                        oRight = evalSimpleExpression(binExp.e2);
                        return new ObjectExpression(ObjectType.booleanType, (int)oLeft.value <= (int)oRight.value);

                    case ParserUtils.Token.TokenValue.GEQ:
                        oRight = evalSimpleExpression(binExp.e2);
                        return new ObjectExpression(ObjectType.booleanType, (int)oLeft.value >= (int)oRight.value);

                    case ParserUtils.Token.TokenValue.EQBOOL:
                        oRight = evalSimpleExpression(binExp.e2);

                        if (oLeft.type == ObjectType.intType)
                        {
                            return new ObjectExpression(ObjectType.booleanType, (int)oLeft.value == (int)oRight.value);
                        }
                        if (oLeft.type == ObjectType.stringType)
                        {
                            return new ObjectExpression(ObjectType.booleanType, ((string)oLeft.value).Equals((string)oRight.value));
                        }
                        if (oLeft.type == ObjectType.booleanType)
                        {
                            return new ObjectExpression(ObjectType.booleanType, (bool)oLeft.value == (bool)oRight.value);
                        }
                        if (oLeft.type == ObjectType.charType)
                        {
                            return new ObjectExpression(ObjectType.booleanType, (char)oLeft.value == (char)oRight.value);
                        }
                        break;

                    default:
                        break;

                }
            }
            else if (exp is UnaryOperatorExpression)
            {
                unExp = (UnaryOperatorExpression)exp;

                switch (unExp.op)
                {
                    case ParserUtils.Token.TokenValue.NEG:
                        o1 = evalSimpleExpression(((UnaryOperatorExpression)exp).exp);
                        return new ObjectExpression(ObjectType.booleanType, !((bool)o1.value));
                }
            }
            else if (exp is VariableExpression)
            {
                return variables[((VariableExpression)exp).name];
            }
            else if (exp is CharacterVariableExpression)
            {
                return new ObjectExpression(ObjectType.intType, IrisData.getPlayerVariable(((CharacterVariableExpression)exp).name));
            }

            return null;
        }

        public ObjectExpression evalPathExpression(PathExpression exp)
        {
            if (exp is VariableExpression)
            {
                return variables[((VariableExpression)exp).name];
            }
            /*else if (exp is MemberAccessExpression)
            {
                memberExp = (MemberAccessExpression)exp;
            }*/

            //erreur
            return null;
        }

        public void removeDeclaredVariables(List<String> declVariables)
        {
            if (declVariables == null)
            {
                return;
            }

            foreach (String s in declVariables)
            {
                variables.Remove(s);
            }
        }
    }
}
