package com.rokuan.iris.implementation.swing;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rokuan.iris.enums.InputType;
import com.rokuan.iris.interfaces.IIrisInputBox;

public class InputBox extends JPanel implements IIrisInputBox {
	private JTextField input;
	private JButton okButton;
	private InputType inputType;
	
	public InputBox(){
		this.setLayout(new BorderLayout());
		this.input = new JTextField();
		this.okButton = new JButton("OK");
		this.okButton.setActionCommand("INPUT_OK");
		
		JPanel inputPanel = new JPanel(new BorderLayout());
		inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		inputPanel.add(input, BorderLayout.CENTER);
		
		this.add(inputPanel, BorderLayout.CENTER);
		this.add(okButton, BorderLayout.EAST);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}
	
	public JButton getButton(){
		return this.okButton;
	}
	
	@Override
	public void clearContent() {
		this.input.setText("");
	}

	@Override
	public void showComponent() {
		this.setVisible(true);
	}

	@Override
	public void enableComponent() {
		this.setEnabled(true);
	}

	@Override
	public void disableComponent() {
		this.setEnabled(false);
	}

	@Override
	public void hideComponent() {
		this.setVisible(false);
	}

	@Override
	public void setInputType(InputType type) {
		this.inputType = type;
		
		if(this.inputType == InputType.INPUT_INT_TYPE){
			this.input.setText("0");
			this.input.selectAll();
		}
	}

	@Override
	public InputType getInputType() {
		return this.inputType;
	}

	@Override
	public int getIntValue() {
		try{
			return Integer.parseInt(this.input.getText());
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public String getValue() {
		return this.input.getText();
	}
}
