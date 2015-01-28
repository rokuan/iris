package com.rokuan.iris.implementation.swing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.rokuan.iris.data.IrisImage;
import com.rokuan.iris.interfaces.IIrisImagePanel;

public class ImagePanel extends JPanel implements IIrisImagePanel {
	private BufferedImage image;
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(image != null){
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

	@Override
	public void setSource(IrisImage src) {
		if(src == null){
			return;
		}
		
		try{
			this.image = ImageIO.read(new File(src.getFilepath()));
			this.invalidate();
		}catch(Exception e){
			return;
		}
	}

	@Override
	public void removeSource() {
		this.image = null;
		this.repaint();
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

}
