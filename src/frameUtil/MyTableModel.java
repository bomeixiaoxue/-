package frameUtil;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel{
	
	//���ñ���ܱ༭
	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO �Զ����ɵķ������
		return false;
	}
}
