using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.ScriptElements.FunctionStructureElement;
using Iris.Data;

namespace Iris.ScriptElements
{
    public class IrisScript
    {
        public List<ConstantStructure> constants;

        public List<NpcStructure> npcs;

        public List<FunctionStructure> functions;

        public List<IrisCharacter> characters;
        public List<IrisImage> images;
        public List<IrisSound> sounds;
        public List<IrisBackground> backgrounds;

        public IrisScript()
        {

        }

        public void addNpc(NpcStructure npc)
        {
            if (npcs == null)
            {
                npcs = new List<NpcStructure>();
            }

            npcs.Add(npc);
        }

        public void addConstant(ConstantStructure constant)
        {
            if (constants == null)
            {
                constants = new List<ConstantStructure>();
            }

            constants.Add(constant);
        }

        public void addFunction(FunctionStructure function)
        {
            if (functions == null)
            {
                functions = new List<FunctionStructure>();
            }

            functions.Add(function);
        }

        public void addCharacter(IrisCharacter charac)
        {
            if (characters == null)
            {
                characters = new List<IrisCharacter>();
            }

            characters.Add(charac);
        }

        public void addImage(IrisImage image)
        {
            if (images == null)
            {
                images = new List<IrisImage>();
            }

            images.Add(image);
        }

        public void addSound(IrisSound sound)
        {
            if (sounds == null)
            {
                sounds = new List<IrisSound>();
            }

            sounds.Add(sound);
        }

        public void addBackground(IrisBackground background)
        {
            if (backgrounds == null)
            {
                backgrounds = new List<IrisBackground>();
            }

            backgrounds.Add(background);
        }

        public void addContent()
        {
            if (characters != null)
            {
                foreach (IrisCharacter charac in characters)
                {
                    IrisData.addCharacter(charac.name, charac);
                }
            }

            if (images != null)
            {
                foreach (IrisImage image in images)
                {
                    IrisData.addImage(image.name, image);
                }
            }

            if (sounds != null)
            {
                foreach (IrisSound sound in sounds)
                {
                    IrisData.addSound(sound.name, sound);
                }
            }

            if (backgrounds != null)
            {
                foreach (IrisBackground background in backgrounds)
                {
                    IrisData.addBackground(background.name, background);
                }
            }

            if (npcs != null)
            {
                foreach (NpcStructure npc in npcs)
                {
                    IrisData.addNpc(npc.name, npc);
                }
            }
        }
    }
}
