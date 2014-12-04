using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Media.Imaging;
using System.IO;

namespace Iris.Data
{
    public class IrisBackground
    {
        public string name;

        public BitmapImage bmp;

        public bool stretch = true;

        private static string baseFolder = Directory.GetCurrentDirectory() + "\\backgrounds\\";

        public IrisBackground()
        {

        }

        public IrisBackground(string bgName, string filePath)
        {
            name = bgName;
            bmp = new BitmapImage(new Uri(baseFolder + filePath, UriKind.Absolute));
        }

        public void setImage(string filepath)
        {
            bmp = new BitmapImage(new Uri(baseFolder + filepath, UriKind.Absolute));
        }
    }
}
