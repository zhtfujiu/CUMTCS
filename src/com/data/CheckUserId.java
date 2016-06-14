package com.data;

public class CheckUserId {

	private boolean flag = false;
	private boolean arrFlag[] = new boolean[8];
	char[] array;

	public CheckUserId(String userID) {
		array = userID.toCharArray();
		IsOK();
	}

	public boolean getFlag(){
		return flag;
	}
	public void IsOK(){
		if(array.length==8){
			for(int i=0;i<8;i++){
				arrFlag[i]=false;
			}
			for(int j=0;j<8;j++){
				if(array[j]>=48&&array[j]<=57)arrFlag[j]=true;
			}
			if(arrFlag[0]&&arrFlag[1]&&arrFlag[2]&&
					arrFlag[3]&&arrFlag[4]&&arrFlag[5]&&arrFlag[6]&&arrFlag[7]){
				flag=true;
			}
		}else{
			flag=false;
		}
	}
}
