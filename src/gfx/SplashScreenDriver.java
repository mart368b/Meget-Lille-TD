package gfx;

public class SplashScreenDriver {

private SplashScreen SScreen;
	
	public SplashScreenDriver(){
		SScreen = new SplashScreen("logo.png");
		SScreen.setLocationRelativeTo(null);
		SScreen.setMaxProgress(1000);
		SScreen.setVisible(true);
		
		for(int i = 0; i <= 10000; i++){
			for(int j = 0; j <= 50000; j++){
				String s = "j" + (i + j);
			}
			SScreen.setProgress(i);
		}
		
		SScreen.setVisible(false);
	}
	
}
