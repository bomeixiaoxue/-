package frameUtil;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
//初始化窗体位置和大小
public class InitFrame {
	
	public static void initFrame(Object object,int width,int height){
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		if(object instanceof JFrame){
			JFrame frame = (JFrame)object;
			frame.setBounds((dimension.width-width)/2, (dimension.height-height)/2, width, height);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}else if(object instanceof JDialog){
			JDialog dialog = (JDialog)object;
			dialog.setBounds((dimension.width-width)/2, (dimension.height-height)/2, width, height);
			dialog.setVisible(true);
			dialog.setModal(true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		
	}
	
}
