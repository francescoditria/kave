package ui;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.Engine;

public class Parser {

	Engine db;
	
	public boolean parse(String command)
	{

		command=command.toLowerCase();
		
		
		Pattern pattern;
		Matcher matcher;
		String operation= new String();
		
		operation="bye";
		pattern=Pattern.compile(operation);
		matcher=pattern.matcher(command);
		while(matcher.find()){
			return true;
		}
		
		operation="use database (.+)";
		pattern=Pattern.compile(operation);
		matcher=pattern.matcher(command);
		while(matcher.find()){
			//db.extactProfile();
			String dbName=matcher.group(1);
			db=new Engine(dbName);
			System.out.println("Using database "+dbName);
			return false;
		}

		String key;
		String attribute;
		String value;
		String oldValue;
		String entity;
		String lastHash;
		
		operation="put (.+) (.+) (.+) (.+)";
		pattern=Pattern.compile(operation);
		matcher=pattern.matcher(command);
		while(matcher.find()){
			key=matcher.group(1);
			attribute=matcher.group(2);
			value=matcher.group(3);
			entity=matcher.group(4);
			System.out.println("Putting key: "+key+", attribute: "+attribute+", value: "+value+", entity: "+entity);
			db.put(key, attribute, value, entity);
			return false;
		}


		operation="get entities";
		pattern=Pattern.compile(operation);
		matcher=pattern.matcher(command);
		while(matcher.find()){
			this.showList(db.getEntities());
			return false;
		}

		operation="get keys (.+)";
		pattern=Pattern.compile(operation);
		matcher=pattern.matcher(command);
		while(matcher.find()){
			entity=matcher.group(1);
			this.showList(db.getKeys(entity));
			return false;
		}

		operation="get attributes (.+) (.+)";
		pattern=Pattern.compile(operation);
		matcher=pattern.matcher(command);
		while(matcher.find()){
			entity=matcher.group(1);
			key=matcher.group(2);
			this.showList(db.getAttributes(entity, key));
			return false;
		}

		operation="get (.+) (.+) (.+)";
		pattern=Pattern.compile(operation);
		matcher=pattern.matcher(command);
		while(matcher.find()){
			key=matcher.group(1);
			attribute=matcher.group(2);
			entity=matcher.group(3);
			ArrayList listValue=db.get(key, attribute, entity);
			this.showList(listValue);		
			return false;
		}

		
		operation="delete (.+) (.+) (.+) (.+)";
		pattern=Pattern.compile(operation);
		matcher=pattern.matcher(command);
		while(matcher.find()){
			key=matcher.group(1);
			attribute=matcher.group(2);
			value=matcher.group(3);
			entity=matcher.group(4);
			db.del(key, attribute, value, entity);
			System.out.println("Deleting key: "+key+", attribute: "+attribute+", value: "+value+", entity: "+entity);
			return false;
		}

		operation="update (.+) (.+) (.+) (.+) (.+)";
		pattern=Pattern.compile(operation);
		matcher=pattern.matcher(command);
		while(matcher.find()){
			key=matcher.group(1);
			attribute=matcher.group(2);
			oldValue=matcher.group(3);
			value=matcher.group(4);
			entity=matcher.group(5);
			db.upd(key, attribute, oldValue,value, entity);
			System.out.println("Updating key: "+key+", attribute: "+attribute+", value: "+value+", entity: "+entity);
			return false;
		}
		

		operation="save database";
		pattern=Pattern.compile(operation);
		matcher=pattern.matcher(command);
		while(matcher.find()){
			lastHash=db.save();
			System.out.println("Saving database");
			System.out.println("Database hash is "+lastHash);
			return false;
		}

		operation="open database (.+)";
		pattern=Pattern.compile(operation);
		matcher=pattern.matcher(command);
		while(matcher.find()){
			lastHash=matcher.group(1);
			if(!db.open(lastHash))
				System.out.println("Database not open.\nIntegrity problem");
			else
				System.out.println("Database open");
			return false;
		}
		

		operation="delete database";
		pattern=Pattern.compile(operation);
		matcher=pattern.matcher(command);
		while(matcher.find()){
			db.reset();
			System.out.println("Deleting database");
			return false;
		}

		
		return false;
	}
	
	public void showList(ArrayList al)
	{
		
		int n=al.size();
		int i;
		for(i=0;i<n;i++)
		{
			System.out.println(al.get(i));
		}
		
	}
	
}
