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
using System.Threading;

namespace Iris.Framework
{

    /// <summary>
    /// Logique d'interaction pour InputBox.xaml
    /// </summary>
    public partial class InputBox : UserControl
    {
        public enum InputType
        {
            STRING_INPUT,
            INT_INPUT
        }

        public InputType inputType;
        public string stringValue;
        public int intValue;
        public Button okButton;

        public readonly object inputObject = new Object();

        public volatile bool inputEnd = false;
        //public bool inputEnd = false;

        /*public bool Stopping
        {
            get
            {
                lock (inputObject)
                {
                    return stopping;
                }
            }
        }*/


        /*public bool InputEnd
        {
            get
            {
                lock (inputObject)
                {
                    return inputEnd;
                }
            }
        }

        public void stopInput()
        {
            lock (inputObject)
            {
                inputEnd = true;
            }
        }*/

        public InputBox()
        {
            InitializeComponent();
            okButton = ok;
        }

        public InputBox(InputType type)
            : this()
        {
            setInputType(type);
            initializeBox();
        }

        public void openBox(InputType type)
        {
            inputEnd = false;
            setInputType(type);
            Visibility = System.Windows.Visibility.Visible;
        }

        public void setInputType(InputType type)
        {
            inputType = type;
            initializeBox();
        }

        public void initializeBox()
        {
            if (inputType == InputType.INT_INPUT)
            {
                input.Text = "0";
                //input.SelectAll();
            }
            else
            {
                input.Text = "";
            }
        }

        private void validateInput(object sender, RoutedEventArgs e)
        {
            if (input.Text == string.Empty)
            {
                return;
            }

            if (inputType == InputType.STRING_INPUT)
            {
                stringValue = input.Text;
                //inputEnd = true;
                //stopInput();
            }
            else if (inputType == InputType.INT_INPUT)
            {
                try
                {
                    intValue = int.Parse(input.Text);
                    //inputEnd = true;
                    //stopInput();
                }
                catch (Exception ex)
                {
                    return;
                }
            }

            this.Visibility = System.Windows.Visibility.Hidden;

            MainWindow.ev.restartEval();

            //Monitor.Pulse(inputEnd);
            //MainWindow.waitEvent.Set();
        }
    }
}
