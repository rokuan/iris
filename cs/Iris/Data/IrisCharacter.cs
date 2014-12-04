using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Media;

namespace Iris.Data
{
    public class IrisCharacter
    {
        public static Dictionary<string, IrisCharacter> characters = new Dictionary<string, IrisCharacter>();

        public string name;
        public string charName;
        public Brush color;
        public Dictionary<string, IrisImage> images = new Dictionary<string, IrisImage>();

        public IrisCharacter()
        {
            
        }

        public IrisCharacter(string charName)
        {
            this.name = charName;
        }

        public void setColor(string hexColor)
        {
            color = new SolidColorBrush((Color)ColorConverter.ConvertFromString(hexColor));
        }

        public void addImage(string name, string imagePath)
        {
            images.Add(name, new IrisImage(name, imagePath));
        }

        public IrisImage getImage(string imgName)
        {
            if (images.ContainsKey(imgName))
            {
                return images[imgName];
            }

            return null;
        }
    }
}
