using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.ParserUtils;

namespace Iris.ExpressionElement.Instruction
{
    public class MenuInstruction : InstructionExpression
    {
        //menu("Oui je veux bien", "Non merci, ca ira", "Avec plaisir"){
        //  case 0:
        //  case 2:
        //      open;
        //      msg "Tres bien suivez moi";
        //      close;
        //      break;
        //
        //  case 1:
        //      open;
        //      msg "Oh mais pourquoi etes vous si serieux ?";
        //      close;
        //}

        public List<string> choices;
        public List<SwitchCaseExpression> cases;

        public bool hasDefaultCase = false;

        public List<int> intCases;

        public MenuInstruction()
        {

        }

        public void addChoice(string choice)
        {
            if (choices == null)
            {
                choices = new List<string>();
            }

            choices.Add(choice);
        }

        public List<string> declaredVariables()
        {
            return null;
        }

        public void addCase(SwitchCaseExpression ce)
        {
            if (cases == null)
            {
                cases = new List<SwitchCaseExpression>();
            }

            cases.Add(ce);

            if (ce is DefaultExpression)
            {
                hasDefaultCase = true;
                return;
            }

            if (ce is CaseExpression)
            {
                SwitchCaseExpression tmp = ce;

                while (tmp != null)
                {
                    if (tmp is DefaultExpression)
                    {
                        hasDefaultCase = true;
                        return;
                    }
                    else
                    {
                        CaseExpression caseExp = (CaseExpression)tmp;

                        if (intCases == null)
                        {
                            intCases = new List<int>();
                        }

                        intCases.Add(caseExp.literal.value.intValue);
                        tmp = caseExp.next;
                    }
                }
            }
        }
    }
}
