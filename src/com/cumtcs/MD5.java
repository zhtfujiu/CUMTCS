package com.cumtcs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MD5 {
    private static String strObj;
    // ȫ������
    private final static String[] str = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public MD5(String str) {
    	strObj=str;
    }

    // ������ʽΪ���ָ��ַ���
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return str[iD1] + str[iD2];
    }

    // ������ʽֻΪ����
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // ת���ֽ�����Ϊ16�����ִ�
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code() {
        String resultString = null;
        try {
            resultString = new String(strObj);
            //����Java����MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() �ú�������ֵΪ��Ź�ϣֵ�����byte����
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
    public String ChangeToString(int i){
    	int a=0,b=0,c=0,d=0;
    	a=i/1000;
    	b=(i%1000)/100;
    	c=(i%100)/10;
    	d=i%10;
    	String str="0000";
    	str=""+a;
    	str+=b;
    	str+=c;
    	str+=d;
		return str;
    }

}