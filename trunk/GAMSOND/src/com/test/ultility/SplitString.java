package com.test.ultility;

public class SplitString {
	public static void main(String[] args)
	{
		String input = "4 9 ( 4 5 9 ) ( 4 8 7 9 )";
		
		String temp1;
		temp1 = input.split("\\(")[0];
		
		String temp2;
		temp2 = input.split("\\(")[1];
		
		String temp3;
		temp3 = input.split("\\(")[2];
		
		String[] stemp1;
		stemp1 = temp1.split(" ");
		System.out.println(temp1);
		for(int i=0; i<stemp1.length;i++)
		{
//			System.out.println(stemp1.length);
			System.out.println("id		value");
			System.out.println(i + "		" + stemp1[i]);
		}
		
		String[] stemp2;
		stemp2 = temp2.split(" ");
		System.out.println(temp2);
		for(int i=0; i<stemp2.length;i++)
		{
//			System.out.println(stemp2.length);
			System.out.println("id		value");
			System.out.println(i + "		" + stemp2[i]);
		}
		
		String[] stemp3;
		stemp3 = temp3.split(" ");
		System.out.println(temp3);
		for(int i=0; i<stemp3.length;i++)
		{
//			System.out.println(stemp3.length);
			System.out.println("id		value");
			System.out.println(i + "		" + stemp3[i]);
		}
	}
}
