package com.countries.Helpers;

import org.hsqldb.util.DatabaseManagerSwing;

public class RunGui {
    public static void main(String[] args) {
		
		DatabaseManagerSwing.main(new String[] { 
				"--url", "jdbc:hsqldb:file:db-data/countries", "--noexit"});

	}
}