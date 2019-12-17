package com.countries.countriesAPI.controllers;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.io.InputStream;
import java.util.Scanner;

import javax.ws.rs.GET;

@Path("/")
public class MainController{
	@GET
	@Path("home")
	@Produces(MediaType.TEXT_HTML)
	public String home() {
		StringBuilder file = new StringBuilder();
		InputStream is = getClass().getResourceAsStream("../../../../templates/index.html");
		
		try(Scanner sc = new Scanner(is);){
			while(sc.hasNext()) {
				file.append(sc.nextLine());
			}
		}
		
		return  file.toString();
	}

	@GET
	@Path("readme")
	@Produces(MediaType.TEXT_HTML)
	public String getReadMe() {
		StringBuilder file = new StringBuilder();
		InputStream is = getClass().getResourceAsStream("../../../../templates/readme.md");
		
		try(Scanner sc = new Scanner(is);){
			while(sc.hasNext()) {
				file.append(sc.nextLine());
			}
		}
		
		return  file.toString();
	}
	
}