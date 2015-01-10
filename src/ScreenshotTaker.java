
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ScreenshotTaker {
	
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		FillLayout layout = new FillLayout();
		layout.type = SWT.VERTICAL;
		shell.setLayout(null);
		shell.setText("Screenshot");
		shell.setSize(360, 110);
		
		Label label = new Label(shell, SWT.NONE);
		label.setText("Path to save:");
		label.setBounds(10, 12, 70, 20);
		
		final Text pathText = new Text(shell, SWT.WRAP | SWT.BORDER);
	    pathText.setBounds(80, 10, 250, 20);
		
		Button button = new Button(shell, SWT.PUSH);
		button.setText("Capture screen!");
		button.setBounds(10, 35, 100, 30);
		button.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// minimize the shell
				shell.setMinimized(true);
				
				// wait 0.5 second for window to be minimized
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// take the screen shot
				GC gc = new GC(display);
				final Image image = new Image(display, display.getBounds());
				gc.copyArea(image, 0, 0);
				gc.dispose();
				
				// verify if the path in text field is valid
				File file = new File(pathText.getText());
				if (!file.isDirectory()) {
					file = file.getParentFile();
				}
				   
				if (file.exists()) {
					// save the screen shot
					ImageLoader loader = new ImageLoader();
				    loader.data = new ImageData[] {image.getImageData()};
				    loader.save(file.getAbsolutePath() 
				    		+ new SimpleDateFormat("\\dd_MM_yyyy__HH__mm__SS").format(new Date()) 
				    		+ ".png", SWT.IMAGE_PNG);
				}
				
			}
			
		});
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
	    }
	}

}
