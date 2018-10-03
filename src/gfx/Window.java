package gfx;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel {
	
	private BufferStrategy strategy;
	private JFrame frame;
	
	public Window (int width, int height) {
		super();
		frame = new JFrame("Argon TD");
		// create frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setResizable(false);
		frame.setVisible(true);
		frame.setResizable(false);
		
		// set display dimenstions
		frame.add(this);
		setPreferredSize(new Dimension(width, height));
		//frame.add(this);
		frame.pack();
		frame.pack();
		frame.setLocationRelativeTo(null);
		System.out.println(this.getSize().toString());
		frame.createBufferStrategy(2);
		strategy = frame.getBufferStrategy();
	}
	
	public Graphics getDrawGraphics() {
		return strategy.getDrawGraphics();
	}
	
	public boolean strategyContentsRestored() {
		return strategy.contentsRestored();
	}
	
	public boolean strategyContentsLost() {
		return strategy.contentsLost();
	}

	public void showStrategy() {
		strategy.show();
	}
	
	public int getMenuBarHeight() {
		return frame.getHeight() - this.getHeight();
	}
	public int getMenuBarWidth() {
		return frame.getWidth() - this.getWidth();
	}
}
