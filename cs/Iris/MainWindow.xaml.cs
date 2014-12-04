using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.IO;
using System.Threading;
using Iris.Framework;
using Iris.ParserUtils;
using Iris.ScriptElements;

namespace Iris
{
    /// <summary>
    /// Logique d'interaction pour MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {

        public readonly static string baseUri = Directory.GetCurrentDirectory() + "/";
        //public static Eval ev;
        public IrisScript scr;
        public static ScriptEval ev;

        public MainWindow()
        {
            InitializeComponent();

            ContentParser cont = new ContentParser();
            ScriptParser scriptPars = new ScriptParser();

            if (cont.startParse(System.IO.Path.GetFullPath("characters/fate.txt")))
            {
                scr = cont.script;
                scr.addContent();
            }
            else
            {
                System.Windows.MessageBox.Show("Fail Characters");
            }

            try
            {
                LexerParser lexpars = new LexerParser();

                if (lexpars.startParse(System.IO.Path.GetFullPath("new_bidon.txt")))
                {
                    scr = lexpars.script;
                    scr.addContent();
                    ev = new ScriptEval(gScreen);
                }
                else
                {
                    System.Windows.MessageBox.Show("Fail");
                }
            }
            catch (Exception e)
            {
                System.Windows.MessageBox.Show(e.ToString());
                System.Windows.MessageBox.Show(e.StackTrace);
            }

        }

        private void fonctionBidonne(object sender, RoutedEventArgs e)
        {
            if (ev != null)
            {
                try
                {
                    ev.evalExpression(scr.npcs.ElementAt(0).labels["main"]);
                }
                catch (Exception ex)
                {
                    System.Windows.MessageBox.Show(ex.ToString());
                }
            }
        }
    }
}
