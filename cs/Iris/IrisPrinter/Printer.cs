using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Media;
using System.Windows.Controls;

namespace Iris.IrisPrinter
{
    public class Printer
    {
        public static TextBox prompt;

        public static void println(string line)
        {
            prompt.Text += line + "\n";
        }

        public static void printError(string error, int lineNumber, int colNumber)
        {
            if (prompt == null)
            {
                return;
            }

            Brush exColor = prompt.Foreground;

            prompt.Foreground = Brushes.Red;
            prompt.Text += "[error]";
            prompt.Foreground = exColor;
            prompt.Text += " line " + lineNumber + ", character " + colNumber + ": " + error + "\n";
        }

        public static void printError(string error, int lineNumber)
        {
            if (prompt == null)
            {
                return;
            }

            Brush exColor = prompt.Foreground;

            prompt.Foreground = Brushes.Red;
            prompt.Text += "[error]";
            prompt.Foreground = exColor;
            prompt.Text += " line " + lineNumber + ": " + error + "\n";
        }

        public static void printError(string error)
        {
            if (prompt == null)
            {
                return;
            }

            Brush exColor = prompt.Foreground;

            prompt.Foreground = Brushes.Red;
            prompt.Text += "[error]";
            prompt.Foreground = exColor;
            prompt.Text += " " + error + "\n";
        }
    }
}
