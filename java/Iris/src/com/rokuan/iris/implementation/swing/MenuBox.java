package com.rokuan.iris.implementation.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import com.rokuan.iris.interfaces.IIrisSelectBox;

public class MenuBox extends JPanel implements IIrisSelectBox {
	private JList<String> list;
	private JButton okButton;
	
	public MenuBox(){
		this.setLayout(new BorderLayout());
		
		this.list = new JList<String>();
		this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.list.setLayoutOrientation(JList.VERTICAL);
		this.list.setVisibleRowCount(3);
		JPanel listBorder = new JPanel(new BorderLayout());
		listBorder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		listBorder.add(list, BorderLayout.CENTER);
		
		this.okButton = new JButton("OK");
		this.okButton.setActionCommand("SELECT_OK");
		
		this.add(listBorder, BorderLayout.CENTER);
		this.add(okButton, BorderLayout.SOUTH);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}
	
	public JButton getButton(){
		return this.okButton;
	}
	
	@Override
	public void clearContent() {
		//this.list.setListData();
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
	public int getSelectedIndex() {
		return this.list.getSelectedIndex();
	}

	@Override
	public void setChoices(List<String> choices) {
		String[] strChoices = new String[choices.size()];
		choices.toArray(strChoices);
		this.list.setListData(strChoices);
	}

}
