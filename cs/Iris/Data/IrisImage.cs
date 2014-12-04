using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Media.Imaging;
using System.IO;

namespace Iris.Data
{
    public class IrisImage
    {
        public static readonly string baseFolder = Directory.GetCurrentDirectory() + "\\images\\";

        public string name;
        public BitmapImage bmp;

        public IrisImage()
        {

        }

        public IrisImage(string fileName)
        {
            bmp = new BitmapImage(new Uri(baseFolder + fileName, UriKind.Absolute));
        }

        public IrisImage(string imageName, string filePath) : this(filePath)
        {
            this.name = imageName;
        }
    }
}
