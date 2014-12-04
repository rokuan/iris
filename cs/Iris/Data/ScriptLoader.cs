using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using Iris.ParserUtils;

namespace Iris.Data
{
    class ScriptLoader
    {
        private static String baseFileNpcs;
        private static String baseFileContent;

        public static void loadNpcs(String file)
        {
            try
            {
                baseFileNpcs = System.IO.Path.GetDirectoryName(file);

                try
                {
                    StreamReader sr = new StreamReader(file);
                    String line;
                    //TODO: remplacer par la bonne classe qui lex/parse les scripts NPC
                    LexParsa lex;

                    while ((line = sr.ReadLine()) != null)
                    {
                        try
                        {
                            lex = new LexParsa();

                            if (lex.startParse(baseFileNpcs + line))
                            {
                                Data.IrisData.addScript(lex.script);
                            }
                        }
                        catch (Exception e)
                        {
                            //e.ToString();
                        }
                    }
                }
                catch (Exception e)
                {
                    //e.ToString();
                }
            }
            catch (Exception e)
            {
                //fichier inexistant
            }
        }

        public static void loadContent(String file)
        {
            try
            {
                baseFileContent = System.IO.Path.GetDirectoryName(file);

                try
                {
                    StreamReader sr = new StreamReader(file);
                    String line;
                    ContentParser cont;

                    while ((line = sr.ReadLine()) != null)
                    {
                        try
                        {
                            cont = new ContentParser();

                            if (cont.startParse(baseFileNpcs + line))
                            {
                                Data.IrisData.addScript(cont.script);
                            }
                        }
                        catch (Exception e)
                        {
                            //e.ToString();
                        }
                    }
                }
                catch (Exception e)
                {
                    //e.ToString();
                }
            }
            catch (Exception e)
            {
                //fichier inexistant
            }
        }
    }
}
