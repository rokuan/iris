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
using Iris.Data;

namespace Iris.Framework
{
    /// <summary>
    /// Logique d'interaction pour GameScreen.xaml
    /// </summary>
    public partial class GameScreen : UserControl
    {
        public static readonly int LEFT = 0;
        public static readonly int CENTER = 1;
        public static readonly int RIGHT = 2;

        public InputBox iBox;
        public MenuBox menBox;
        public MessageBox msgBox;

        private Image[] images = new Image[3];

        public GameScreen()
        {
            InitializeComponent();

            iBox = inputBox;
            menBox = menuBox;
            msgBox = messageBox;

            images[LEFT] = leftImage;
            images[CENTER] = centerImage;
            images[RIGHT] = rightImage;

            //Thread t = new Thread(new ThreadStart(messageBox.closeDialog));

            //while (!iBox.inputEnd)
            //{
            //Monitor.Wait(iBox.inputEnd);
            //}

        }

        public void waitForInput()
        {
            /*iBox.openBox(InputBox.InputType.INT_INPUT);

            Thread.Sleep(3000);

            while (!iBox.inputEnd)
            {
                Thread.Sleep(300);
            }

            msgBox.Visibility = System.Windows.Visibility.Visible;
            msgBox.setText("Bonjour");*/

            //MainWindow.waitEvent.WaitOne();
        }

        public void showImage(IrisImage img, int position)
        {
            if (position >= LEFT && position <= RIGHT)
            {
                images[position].Source = img.bmp;
                //System.Windows.MessageBox.Show(IrisImage.baseFolder);
                //images[position].Source = new BitmapImage(new Uri("C:/Documents and Settings/LEBEAU Christophe/Mes documents/Visual Studio 2010/Projects/Iris/Iris/bin/Release/images/c3_big.png", UriKind.Absolute));
            }
        }

        public void setBackground(IrisBackground image)
        {
            background.Source = image.bmp;
        }

        public void clearBackground()
        {
            background.Source = null;
        }

        public void clearForeGround()
        {
            images[LEFT].Source = null;
            images[CENTER].Source = null;
            images[RIGHT].Source = null;
        }

        public void playMedia(IrisSound sound)
        {
            if (sound != null)
            {
                if (player.Source != null)
                {
                    player.Close();
                    player.Source = null;
                }

                player.Source = sound.soundPath;
                player.Play();
            }
        }

        public void stopMedia()
        {
            if (player.Source != null)
            {
                player.Stop();
                player.Close();
                player.Source = null;
            }
        }
    }
}
