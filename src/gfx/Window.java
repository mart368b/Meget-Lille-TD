package gfx;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Game;

public class Window extends JPanel {
	
	private JFrame frame;
	private Game game;
	
	public Window (int width, int height, Game game) {
		super();
		
		this.game = game;
		// create frame
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		// set display dimenstions
		this.setPreferredSize(new Dimension(width, height));
		frame.add(this);
	}
	
	@Override
	public void paintComponent (Graphics g) {
		game.render((Graphics2D) g);
	}
}
