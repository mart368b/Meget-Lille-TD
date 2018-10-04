package gfx;

public class SplashScreenDriver {

private SplashScreen SScreen;
	
	private final int loadTime = 2000;

	public SplashScreenDriver(){
		SScreen = new SplashScreen("logo.png");
		SScreen.setLocationRelativeTo(null);
		SScreen.setMaxProgress(100);
		SScreen.setVisible(true);
		
		long startTime = System.currentTimeMillis();
		long stopTime = startTime + loadTime;
		long time, lastUpdate = startTime;
		SScreen.setProgress(0);
		while ((time = System.currentTimeMillis()) < stopTime) {
			if (time - lastUpdate > 100) {
				lastUpdate = time;
				SScreen.setProgress((int) (loadTime - (stopTime - time))/2/1);
			}
		}
		
		SScreen.setVisible(false);
	}
	
}
