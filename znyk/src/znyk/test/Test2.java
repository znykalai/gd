package znyk.test;

import znyk.server.SqlTool;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String back=SqlTool.add动作指令("1", "60001", "10", "上货", 0, "1");
		String back=SqlTool.add动作指令("1", "1", "60002", "下货", 0, "1");
		System.out.println(back);

	}

}
