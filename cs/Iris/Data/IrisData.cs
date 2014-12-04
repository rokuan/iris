using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.ScriptElements;

namespace Iris.Data
{
    public class IrisData
    {
        public static Dictionary<string, IrisCharacter> characters = new Dictionary<string, IrisCharacter>();
        public static Dictionary<string, IrisBackground> backgrounds = new Dictionary<string, IrisBackground>();
        public static Dictionary<string, IrisImage> images = new Dictionary<string, IrisImage>();
        public static Dictionary<string, IrisSound> sounds = new Dictionary<string, IrisSound>();

        public static Dictionary<string, int> playerVariables = new Dictionary<string, int>();

        public static Dictionary<string, NpcStructure> npcs = new Dictionary<string, NpcStructure>();


        public static void addCharacter(string charName, IrisCharacter charac)
        {
            characters.Add(charName, charac);
        }

        public static void addBackground(string bgdName, IrisBackground background)
        {
            backgrounds.Add(bgdName, background);
        }

        public static void addImage(string imgName, IrisImage image)
        {
            images.Add(imgName, image);
        }

        public static void addSound(string soundName, IrisSound sound)
        {
            sounds.Add(soundName, sound);
        }

        public static void addPlayerVariable(string varName, int value)
        {
            playerVariables.Add(varName, value);
        }

        public static void setPlayerVariable(string varName, int value)
        {
            if (!playerVariables.ContainsKey(varName))
            {
                playerVariables.Add(varName, value);
            }
            else
            {
                playerVariables[varName] = value;
            }
        }

        public static int getPlayerVariable(string varName)
        {
            if (playerVariables.ContainsKey(varName))
            {
                return playerVariables[varName];
            }

            return 0;
        }

        public static void addNpc(string npcName, NpcStructure npc)
        {
            npcs.Add(npcName, npc);
        }

        public static void addScript(IrisScript scr)
        {
            scr.addContent();
        }
    }
}
