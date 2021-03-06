package alai.znyk.plc;

public class _DST extends ST_Father implements STInterface {
	
	private int boolContent;//保存几个布尔值
    private boolean 允许工位动作标志;//D10001.1
	private boolean 翻B面;//D10001.2
	private boolean 立库RDY;//D10001.3
	private boolean 数据更新完;//D10001.4
	private int 电芯类型标志;//D10001.4
	

	
	protected  _DST(PLC plc,int machineID,String startAddress){
		super(plc,machineID,startAddress,2);
		}
	public int getBoolContent(){return boolContent;}
	public boolean is允许工位动作标志() {
		return 允许工位动作标志;
	}
	public void set允许工位动作标志(boolean 允许工位动作标志) {
		this.允许工位动作标志 = 允许工位动作标志;
		if(允许工位动作标志)
	boolContent=boolContent|0b1;else boolContent=boolContent&0b1111111111111110;
	}
	public boolean is翻B面() {
		return 翻B面;
	}
	public void set翻B面(boolean 翻b面) {
		翻B面 = 翻b面;
		if(翻b面)
		boolContent=boolContent|0b10;else boolContent=boolContent&0b1111111111111101;
	}
	public boolean is立库RDY() {
		return 立库RDY;
	}
	public void set立库RDY(boolean 立库rdy) {
		立库RDY = 立库rdy;
		if(立库rdy)
		boolContent=boolContent|0b100;else boolContent=boolContent&0b1111111111111011;
	}
	public boolean is数据更新完() {
		return 数据更新完;
	}
	
	public void set数据更新完(boolean 数据更新完) {
		this.数据更新完 = 数据更新完;
		if(数据更新完)
		boolContent=boolContent|0b1000;else boolContent=boolContent&0b1111111111110111;
	}
	
	
	
    public int get电芯类型标志() {
		return 电芯类型标志;
	}

	public void set电芯类型标志(int 电芯类型标志) {
		this.电芯类型标志 = 电芯类型标志;
	}

	
	public int getLength(){return length;}
    public String getStartAddress(){return startAddress;}
    public String writeToPLC(){
    	
    	return plc.writeBlockToBLC(startAddress, length, new int[]{boolContent,电芯类型标志},machineID);
    	
    }
    public void intFromST(ST_Father st){
	     super.intFromST(st);
	     boolContent=((_DST)st).getBoolContent();
	     允许工位动作标志=((_DST)st).is允许工位动作标志();
		 立库RDY=((_DST)st).is立库RDY();
		 翻B面=((_DST)st).is翻B面();
		 数据更新完=((_DST)st).is数据更新完();
		 电芯类型标志=((_DST)st).get电芯类型标志();
	 
}
    @Override
	 public boolean isChange(){
		 if(old==null){
			 old=new _DST(plc, machineID,startAddress);
			 return true;
			 
		 }else{
			 if(this.boolContent!=old.getBoolContent()||!this.getName().equals(old.getName())){return true;} 
			 
		 }
		 return false;}

    public void clear(){
    	boolContent=0;
    	允许工位动作标志=false;
	     翻B面=false;
	     立库RDY=false;
		 数据更新完=false;
    	电芯类型标志=0;
    	
    }
   
    public String updataFromPLC(){
    	 int back[]= plc.readBlockFromBLC(startAddress, length, machineID);
    	 if(back!=null){
    		 boolContent=back[0]; 
    		 int tem= boolContent;
    		     允许工位动作标志=((tem&0b01)==1);
    		     翻B面=((tem&0b10)==1);
    		     立库RDY=((tem&0b100)==1);
    			 数据更新完=((tem&0b1000)==1);
    			 电芯类型标志=back[1];
    	 
    	   return "FST 读取成功";
    	 }
    	 else{
    	  return "FST 读取失败";}
    	
    }
    
  
    
  
}
