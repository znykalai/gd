package znyk.test;

import znyk.server.SqlTool;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String back=SqlTool.add动作指令("1", "60001", "10", "上货", 0, "1");
		//String back=SqlTool.add动作指令("1", "1", "60002", "下货", 0, "1");
		String sql="update 库存托盘  set 货位号=NULL,方向=NULL where 托盘编号="+"'"+1+"'";
		 
		String back=SqlTool.insert(new String[]{sql});
		System.out.println(back);

	}

}
