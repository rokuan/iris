using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace Iris.Data
{
    public class IrisSound
    {
        private static readonly string baseFolder = Directory.GetCurrentDirectory() + "\\sounds\\";

        public Uri soundPath;
        public string name;

        public IrisSound()
        {

        }

        public IrisSound(string filepath)
        {
            soundPath = new Uri(baseFolder + filepath, UriKind.Absolute);
        }

        public IrisSound(string soundName, string filepath)
            : this(filepath)
        {
            name = soundName;
        }

        public void setSource(string filepath)
        {
            soundPath = new Uri(baseFolder + filepath, UriKind.Absolute);
        }
    }
}
