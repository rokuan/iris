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

namespace Iris.Framework
{
    /// <summary>
    /// Logique d'interaction pour MenuBox.xaml
    /// </summary>
    public partial class MenuBox : UserControl
    {
        public List<string> choicesList;
        public int selectionIndex;

        public MenuBox()
        {
            InitializeComponent();
        }

        public void addChoice(string choice)
        {
            if (choicesList == null)
            {
                choicesList = new List<string>();
            }
        }

        public void setChoices(List<string> choices)
        {
            TextBlock txtBlock;
            
            if (choices == null || choices.Count == 0)
            {
                return;
            }

            choicesList = choices;
            listBox.Items.Clear();

            foreach (string str in choices)
            {
                txtBlock = new TextBlock();

                txtBlock.TextWrapping = TextWrapping.Wrap;
                txtBlock.HorizontalAlignment = System.Windows.HorizontalAlignment.Center;
                txtBlock.Width = 370;
                txtBlock.Text = str;
                txtBlock.Margin = new Thickness(0, 2, 0, 2);

                listBox.Items.Add(txtBlock);
            }

            //listBox.ItemsSource = choices;
        }

        private void selectAnswer(object sender, RoutedEventArgs e)
        {
            if (listBox.SelectedIndex != -1)
            {
                selectionIndex = listBox.SelectedIndex;
                this.Visibility = System.Windows.Visibility.Hidden;

                MainWindow.ev.restartEval();
            }
        }

        public void openMenu()
        {
            if (this.Visibility == System.Windows.Visibility.Hidden)
            {
                this.Visibility = System.Windows.Visibility.Visible;
            }
        }
    }
}
