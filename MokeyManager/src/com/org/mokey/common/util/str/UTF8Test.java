package com.org.mokey.common.util.str;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

//http://blog.csdn.net/jackpk/article/details/5702964/
public class UTF8Test {
	public static void main(String[] args) throws IOException {
		File f  = new File("e:\\5000x2.txt");
		FileInputStream in = new FileInputStream(f);
//		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		BufferedReader br = new BufferedReader(new UnicodeReader(in, Charset.defaultCharset().name()));
		
		String line = br.readLine();
		int i=0;
		while(line != null)
		{
			System.out.print(line+"\t");
			line = br.readLine();
			i++;
			if(i%10==0){
				System.out.println();
			}
		}
	}
}
