package znyk.test;

import znyk.server.SqlTool;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String back=SqlTool.add����ָ��("1", "60001", "10", "�ϻ�", 0, "1");
		//String back=SqlTool.add����ָ��("1", "1", "60002", "�»�", 0, "1");
		String sql="update �������  set ��λ��=NULL,����=NULL where ���̱��="+"'"+1+"'";
		 
		String back=SqlTool.insert(new String[]{sql});
		System.out.println(back);

	}

}
