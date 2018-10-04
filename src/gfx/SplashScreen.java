
package gfx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ProgressBarUI;

public class SplashScreen extends JWindow {

	private BorderLayout BL;
	private JLabel imgLabel;
	private JPanel southPanel;
	private FlowLayout southFlow;
	private JProgressBar Pbar;
	private ImageIcon imgIcon;
	
	public SplashScreen(String name){
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/sprites/huds/" + name));
		}catch(Exception e){
			e.printStackTrace();
		}
		this.imgIcon = new ImageIcon(img);
		BL = new BorderLayout();
		imgLabel = new JLabel();
		southPanel = new JPanel();
		southFlow = new FlowLayout();
		Pbar = new JProgressBar();
		Pbar.setStringPainted(true);
		try {
			init();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void init() throws Exception {
		imgLabel.setIcon(imgIcon);
		getContentPane().setLayout(BL);
		southPanel.setLayout(southFlow);
		getContentPane().add(imgLabel, BorderLayout.CENTER);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		southPanel.add(Pbar, null);
		setBackground(new Color(0, 0, 0, 0));
		pack();
	}
	
	public void setMaxProgress(int max){
		Pbar.setMaximum(max);
	}
	
	public void setProgress(final int progress){
		final int procentage = (progress /  Pbar.getMaximum()) * 10;
		
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				Pbar.setValue(progress / 10);
				Pbar.setString("Indlæser: " + procentage + "%");
			}
		});
	}
}
