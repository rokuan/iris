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
using Iris.Data;

namespace Iris.Framework
{
    /// <summary>
    /// Logique d'interaction pour MessageBox.xaml
    /// </summary>
    public partial class MessageBox : UserControl
    {

        //StringBuilder str = new StringBuilder();
        //private StringBuilder str = null;
        private Brush baseBrush;
        private Brush currentBrush;


        public volatile bool textEnd = false;

        public MessageBox()
        {
            InitializeComponent();

            baseBrush = message.Foreground;
            currentBrush = message.Foreground;
        }

        public void openBox()
        {
            this.Visibility = System.Windows.Visibility.Visible;
        }

        /*public void addText(string text)
        {
            if (str == null)
            {
                str = new StringBuilder();
            }
            else
            {
                str.Append('\r');
            }

            str.Append(text);
        }*/

        public void setText(string text)
        {
            //int colorIndex;
            /*string tmp = text;

            if (tmp == string.Empty)
            {
                return;
            }*/

            if (text == null || text == string.Empty)
            {
                return;
            }

            /*if (str != null)
            {
                message.AppendText("\r");
                addText("\r");
            }*/

            /*colorIndex = tmp.IndexOf('#');

            if (colorIndex != -1)
            {
                tmp = text.Substring(0, colorIndex);

                message.AppendText(tmp);
                addText(tmp);

                //tmp = debut de la couleur (#0077FF [...])
                tmp = text.Substring(colorIndex);

                colorIndex = 1;    //apres #

                if (colorIndex == tmp.Length - 1 ||
                    colorIndex + 6 >= tmp.Length)
                { //0077FF
                    message.AppendText(tmp);
                    addText(tmp);
                }
                else
                {
                    try
                    {
                        Color c = (Color)ColorConverter.ConvertFromstring(tmp.Substring(0, colorIndex + 6));

                        tmp = tmp.Substring(colorIndex + 7 - 1);

                        Brush colorTmp = new SolidColorBrush(c);

                        currentBrush = colorTmp;
                        message.Foreground = colorTmp;

                        setText(tmp);
                    }
                    catch (Exception e)
                    {
                        message.AppendText(tmp.Substring(0, colorIndex + 6));
                        addText(tmp.Substring(0, colorIndex + 6));

                        setText(tmp.Substring(colorIndex + 7 - 1));
                    }
                }

            }
            else
            {*/
            /*if (message.Document.Blocks.Count != 0)
            {
                message.AppendText("\r");
            }*/
            if (message.Text != string.Empty)
            {
                message.AppendText("\n");
            }

            message.AppendText(text);
            //addText(text);
            //}

        }

        public void cleanText()
        {
            //message.Document.Blocks.Clear();

            /*if (str != null)
            {
                str.Clear();
                str = null;
            }*/

            message.Text = "";
        }

        /*public void addNewLine()
        {
            //message.Text += Environment.NewLine;
        }

        public void displayText()
        {
            if (str != null)
            {
                //message.Text = str.ToString();
            }
        }*/

        public void setNpcName(string name)
        {
            npcName.Text = name;
        }

        public void switchCharacter(IrisCharacter ic)
        {
            if (!npcName.IsVisible)
            {
                npcName.Visibility = System.Windows.Visibility.Visible;
            }

            if (ic.charName == null)
            {
                npcName.Text = "???";
            }
            else
            {
                npcName.Text = ic.charName;
            }
        }

        public void removeCharacter()
        {
            if (!npcName.IsVisible)
            {
                return;
            }

            npcName.Visibility = System.Windows.Visibility.Hidden;
            npcName.Text = "";
        }

        public void showNextButton()
        {
            enableNext();
        }

        public void showCloseButton()
        {
            enableClose();
        }

        private void enableNext()
        {
            close.Visibility = Visibility.Hidden;
            close.IsEnabled = false;

            next.Visibility = Visibility.Visible;
            next.IsEnabled = true;
        }

        private void enableClose()
        {
            next.Visibility = Visibility.Hidden;
            next.IsEnabled = false;

            close.Visibility = Visibility.Visible;
            close.IsEnabled = true;
        }

        private void showNext(object sender, RoutedEventArgs e)
        {
            //textEnd = true;
            next.Visibility = System.Windows.Visibility.Hidden;
            cleanText();

            MainWindow.ev.restartEval();
        }

        private void closeMessageBox(object sender, RoutedEventArgs e)
        {
            //textEnd = true;
            close.Visibility = System.Windows.Visibility.Hidden;
            this.Visibility = Visibility.Hidden;
            cleanText();

            MainWindow.ev.restartEval();
        }

    }
}
