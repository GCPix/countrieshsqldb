package com.countries.Helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class SqlScriptParser {

	public String getSqlString(String script) throws IOException {
		StringBuilder sb = new StringBuilder();

		 try(InputStream is = getClass().getResourceAsStream(script)){
			 if (is != null) {
				 try (Scanner sc = new Scanner(is)){

		                while(sc.hasNext()){
		                    sb.append(sc.nextLine());
		                }
		         }
			 }
	           
		 }
	                
		
		String sqlScript = sb.toString();
		return sqlScript;
	}
}
