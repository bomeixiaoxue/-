package frameUtil;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

//实现窗体的拖动
public class DragPicListener extends MouseAdapter implements MouseMotionListener{
	Point point =new Point();
	Point nextPoint =new Point();
	JFrame frame;
	public DragPicListener(JFrame frame){
		this.frame = frame;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO 自动生成的方法存根
		nextPoint = e.getPoint();
		frame.setLocation(frame.getX()+nextPoint.x-point.x, frame.getY()+nextPoint.y-point.y);
	}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		point = e.getPoint();
	}
}
