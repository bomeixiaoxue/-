package frameUtil;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel{
	
	//设置表格不能编辑
	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO 自动生成的方法存根
		return false;
	}
}
