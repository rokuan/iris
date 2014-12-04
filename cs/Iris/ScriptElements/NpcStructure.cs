using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.ExpressionElement;

namespace Iris.ScriptElements
{
    public class NpcStructure
    {
        public string name;

        //public List<LabelExpression> labels;
        public Dictionary<string, Expression> labels = new Dictionary<string, Expression>();

        public NpcStructure()
        {

        }

        public NpcStructure(string npcName)
        {
            name = npcName;
        }

        public void addLabel(LabelExpression exp)
        {
            addLabel(exp.labelName, exp.body);
        }

        public void addLabel(string labelName, Expression exp)
        {
            if (labels.ContainsKey(labelName) || exp == null)
            {
                return;
            }

            labels.Add(labelName, exp);
        }

        public bool validNpc()
        {
            return labels.ContainsKey("main");
        }
    }
}
