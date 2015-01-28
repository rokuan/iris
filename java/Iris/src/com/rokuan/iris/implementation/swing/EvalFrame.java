package com.rokuan.iris.implementation.swing;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;

import com.rokuan.iris.interfaces.IIrisDataLoader;
import com.rokuan.iris.interfaces.IIrisResources;
import com.rokuan.iris.process.ScriptEval;

public class EvalFrame implements ActionListener {
	private ScriptEval eval;
	private JFrame frame;
	
	private ImagePanel left;
	private ImagePanel right;
	private BackgroundPanel background;
	private MessageBox messageBox;
	private InputBox inputBox;
	private MenuBox menuBox;
	
	private IIrisResources resources;
	private IIrisDataLoader dataLoader;
	
	private OverlayLayout frames;
	
	public EvalFrame(IIrisResources res, IIrisDataLoader data){
		this.resources = res;
		this.dataLoader = data;
	}
	
	public void createAndShowGUI(){
		frame = new JFrame();		

		JPanel contentPanel = new JPanel();
		
		GridBagConstraints c = new GridBagConstraints();
		//JPanel contentPanel = new JPanel();
		
		frames = new OverlayLayout(contentPanel);
		contentPanel.setLayout(frames);
		
		messageBox = new MessageBox();
		left = new ImagePanel();
		right = new ImagePanel();
		background = new BackgroundPanel();
		inputBox = new InputBox();
		menuBox = new MenuBox();
		
		/* BACKGROUND */
		JPanel backgroundPanel = new JPanel(new BorderLayout());		
		backgroundPanel.add(background, BorderLayout.CENTER);
		
		/* INPUT BOX */
		JPanel inputPanel = new JPanel(new GridBagLayout());
		inputPanel.setOpaque(false);
		inputBox.setPreferredSize(new Dimension(400, 50));		
		inputPanel.add(inputBox);		
		
		/* MENU BOX */
		JPanel menuPanel = new JPanel(new GridBagLayout());
		menuPanel.setOpaque(false);
		menuBox.setPreferredSize(new Dimension(500, 200));
		menuPanel.add(menuBox);
		
		/* MESSAGE BOX, LEFT/RIGHT IMAGES */
		JPanel messagePanel = new JPanel(new BorderLayout());
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		
		bottomPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));				
				
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.2;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		bottomPanel.add(left, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.6;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		bottomPanel.add(messageBox, c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.2;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		bottomPanel.add(right, c);
		
		messagePanel.add(bottomPanel, BorderLayout.SOUTH);
		
		JButton startButton = new JButton("START");
		startButton.setActionCommand("START");
		startButton.addActionListener(this);
		
		
		contentPanel.add(inputPanel);
		contentPanel.add(menuPanel);
		//contentPanel.add(bottomPanel);
		contentPanel.add(messagePanel);
		//contentPanel.add(backgroundPanel);
		contentPanel.add(background);
		contentPanel.add(startButton);
		
		this.eval = new ScriptEval(dataLoader, resources, messageBox, inputBox, menuBox, left, left, right, background, null, null);
		
		loadActions();
				
		frame.setContentPane(contentPanel);
		frame.setSize(800, 480);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public ScriptEval getScriptEval(){
		return this.eval;
	}
	
	private void loadActions(){
		this.inputBox.getButton().addActionListener(this);
		this.menuBox.getButton().addActionListener(this);
		this.messageBox.getButton().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command = arg0.getActionCommand(); 
		
		if(command.equals("NEXT_MESSAGE")){
			this.eval.nextMessage();
		} else if(command.equals("SELECT_OK")){
			this.eval.validateSelection();
		} else if(command.equals("INPUT_OK")){
			this.eval.validateInput();
		} else if(command.equals("START")){
			this.eval.talkToNpc("Chastel");
		}
	}
}
