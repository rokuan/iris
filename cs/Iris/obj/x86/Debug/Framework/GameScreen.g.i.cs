﻿#pragma checksum "..\..\..\..\Framework\GameScreen.xaml" "{406ea660-64cf-4c82-b6f0-42d48172a799}" "347882C50C271F0124913744F82C5C18"
//------------------------------------------------------------------------------
// <auto-generated>
//     Ce code a été généré par un outil.
//     Version du runtime :4.0.30319.586
//
//     Les modifications apportées à ce fichier peuvent provoquer un comportement incorrect et seront perdues si
//     le code est régénéré.
// </auto-generated>
//------------------------------------------------------------------------------

using Iris.Framework;
using System;
using System.Diagnostics;
using System.Windows;
using System.Windows.Automation;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Media.Effects;
using System.Windows.Media.Imaging;
using System.Windows.Media.Media3D;
using System.Windows.Media.TextFormatting;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Shell;


namespace Iris.Framework {
    
    
    /// <summary>
    /// GameScreen
    /// </summary>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "4.0.0.0")]
    public partial class GameScreen : System.Windows.Controls.UserControl, System.Windows.Markup.IComponentConnector {
        
        
        #line 9 "..\..\..\..\Framework\GameScreen.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.MediaElement player;
        
        #line default
        #line hidden
        
        
        #line 10 "..\..\..\..\Framework\GameScreen.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Image background;
        
        #line default
        #line hidden
        
        
        #line 12 "..\..\..\..\Framework\GameScreen.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Image leftImage;
        
        #line default
        #line hidden
        
        
        #line 13 "..\..\..\..\Framework\GameScreen.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Image centerImage;
        
        #line default
        #line hidden
        
        
        #line 14 "..\..\..\..\Framework\GameScreen.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Image rightImage;
        
        #line default
        #line hidden
        
        
        #line 16 "..\..\..\..\Framework\GameScreen.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal Iris.Framework.MessageBox messageBox;
        
        #line default
        #line hidden
        
        
        #line 17 "..\..\..\..\Framework\GameScreen.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal Iris.Framework.InputBox inputBox;
        
        #line default
        #line hidden
        
        
        #line 18 "..\..\..\..\Framework\GameScreen.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal Iris.Framework.MenuBox menuBox;
        
        #line default
        #line hidden
        
        private bool _contentLoaded;
        
        /// <summary>
        /// InitializeComponent
        /// </summary>
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public void InitializeComponent() {
            if (_contentLoaded) {
                return;
            }
            _contentLoaded = true;
            System.Uri resourceLocater = new System.Uri("/Iris;component/framework/gamescreen.xaml", System.UriKind.Relative);
            
            #line 1 "..\..\..\..\Framework\GameScreen.xaml"
            System.Windows.Application.LoadComponent(this, resourceLocater);
            
            #line default
            #line hidden
        }
        
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1811:AvoidUncalledPrivateCode")]
        internal System.Delegate _CreateDelegate(System.Type delegateType, string handler) {
            return System.Delegate.CreateDelegate(delegateType, this, handler);
        }
        
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Never)]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Design", "CA1033:InterfaceMethodsShouldBeCallableByChildTypes")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Maintainability", "CA1502:AvoidExcessiveComplexity")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1800:DoNotCastUnnecessarily")]
        void System.Windows.Markup.IComponentConnector.Connect(int connectionId, object target) {
            switch (connectionId)
            {
            case 1:
            this.player = ((System.Windows.Controls.MediaElement)(target));
            return;
            case 2:
            this.background = ((System.Windows.Controls.Image)(target));
            return;
            case 3:
            this.leftImage = ((System.Windows.Controls.Image)(target));
            return;
            case 4:
            this.centerImage = ((System.Windows.Controls.Image)(target));
            return;
            case 5:
            this.rightImage = ((System.Windows.Controls.Image)(target));
            return;
            case 6:
            this.messageBox = ((Iris.Framework.MessageBox)(target));
            return;
            case 7:
            this.inputBox = ((Iris.Framework.InputBox)(target));
            return;
            case 8:
            this.menuBox = ((Iris.Framework.MenuBox)(target));
            return;
            }
            this._contentLoaded = true;
        }
    }
}

