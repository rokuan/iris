package com.rokuan.iris.implementation.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.rokuan.iris.interfaces.IIrisDialogBox;

public class MessageBox extends JPanel implements IIrisDialogBox {
	private JLabel speaker;
	private JTextArea message;
	private JButton nextButton;

	public MessageBox(){
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setBackground(Color.decode("#373737"));
		this.speaker = new JLabel();
		this.message = new JTextArea();
		
		this.speaker.setOpaque(false);
		this.speaker.setForeground(Color.WHITE);
		
		this.message.setOpaque(false);
		this.message.setForeground(Color.WHITE);
		this.message.setEditable(false);
		
		this.nextButton = new JButton(" > ");
		this.nextButton.setActionCommand("NEXT_MESSAGE");
		this.nextButton.setForeground(Color.WHITE);
		this.nextButton.setBackground(Color.decode("#373737"));
		this.nextButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		
		GridBagConstraints c = new GridBagConstraints();	
		
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.setOpaque(false);
		leftPanel.add(speaker, BorderLayout.NORTH);
		leftPanel.add(message, BorderLayout.CENTER);		
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.9;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		
		this.add(leftPanel, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		
		/*this.add(speaker, BorderLayout.NORTH);
		this.add(message, BorderLayout.CENTER);*/
		//this.add(nextButton, BorderLayout.EAST);
		this.add(nextButton, c);
	}
	
	public JButton getButton(){
		return this.nextButton;
	}
	
	@Override
	public void clearContent() {
		this.message.setText("");
		this.message.revalidate();
	}

	@Override
	public void showComponent() {
		this.setVisible(true);
	}

	@Override
	public void enableComponent() {
		this.nextButton.setEnabled(true);
		this.nextButton.setVisible(true);
	}

	@Override
	public void disableComponent() {
		this.nextButton.setEnabled(false);
		this.nextButton.setVisible(false);
	}

	@Override
	public void hideComponent() {
		this.setVisible(false);
	}

	@Override
	public void removeSpeaker() {
		this.speaker.setVisible(false);
		this.speaker.setText("");
	}

	@Override
	public void setSpeaker(String name) {
		this.speaker.setText(name);
		this.speaker.setVisible(true);
	}

	@Override
	public void appendText(String text) {
		//this.message.append(text);
		this.message.setText(this.message.getText() + text);
	}
}
