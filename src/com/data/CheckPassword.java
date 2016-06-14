package com.data;

public class CheckPassword {
	char[] array;
	public CheckPassword(String str) {
		array=str.toCharArray();
	}
	
	public boolean IsOK(){
		boolean flag=false;
		if(array.length>=6&&array.length<=16){
			flag=true;			
		}		
		return flag;
	}
}
