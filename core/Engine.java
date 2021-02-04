package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Engine {

	static Map<String, Map> dbMap;
	dataManager store;
		
	public Engine(String dbName)
	{
		//create new instance of the map
		//create new instance of the database manager
		
		dbMap=new HashMap<String, Map>();		
		store=new dataManager(dbName);
		
	}

	public ArrayList getAttributes(String entity, String key)
	{
		Map<String, Map> keyMap=new HashMap<String, Map>();
		Map<String, ArrayList> attributeMap=new HashMap<String, ArrayList>();

		keyMap=dbMap.get(entity);
		attributeMap=keyMap.get(key);
	
		Set<String> attributes = attributeMap.keySet();
		return this.extractMapKey(attributes);

		
	}

	
	public ArrayList getKeys(String entity)
	{
		Map<String, Map> keyMap=new HashMap<String, Map>();
		keyMap=dbMap.get(entity);
		Set<String> keys = keyMap.keySet();
		return this.extractMapKey(keys);

		
	}

	
	public ArrayList getEntities()
	{
		

		Set<String> entities = dbMap.keySet();
		return this.extractMapKey(entities);
		
	}
	

	private ArrayList extractMapKey(Set set)
	{
	
		ArrayList al=new ArrayList();
		int i;
		String strEntity=set.toString();
		int m=strEntity.length();
		String subEntity=strEntity.substring(1, m-1);
		//System.out.println(strEntity);
		
		String[] entityArray=subEntity.split(",");
		int n=entityArray.length;
		for(i=0;i<n;i++)
		{
			//System.out.println(entityArray[i].trim());
			al.add(entityArray[i].trim());
		}
		return al;
	
	}
	
	public void put(String key, String attribute,String value, String entity)
	{
		//put all the strings into the map
		
		Map<String, Map> keyMap=new HashMap<String, Map>();
		Map<String, ArrayList> attributeMap=new HashMap<String, ArrayList>();
		ArrayList listValue=new ArrayList();
		
		if(dbMap.containsKey(entity))
			keyMap=dbMap.get(entity);
		else
			dbMap.put(entity, keyMap);
		
		if(keyMap.containsKey(key))
			attributeMap=keyMap.get(key);
		else
			keyMap.put(key, attributeMap);
		
		if(attributeMap.containsKey(attribute))
			listValue=attributeMap.get(attribute);
		else
			attributeMap.put(attribute, listValue);
		
		if(!listValue.contains(value))	
			listValue.add(value);

		

	}
		

	public void del(String key, String attribute,String value, String entity)
	{
		//delete a value from the map
		
		Map<String, Map> keyMap=new HashMap<String, Map>();
		Map<String, ArrayList> attributeMap=new HashMap<String, ArrayList>();
		ArrayList listValue=new ArrayList();

		keyMap=dbMap.get(entity);
		attributeMap=keyMap.get(key);
		listValue=attributeMap.get(attribute);
		listValue.remove(value);			
				
	}


	public void upd(String key, String attribute,String oldvalue, String newvalue,String entity)
	{
		//update a value in the map
				
		this.del(key, attribute, oldvalue, entity);
		this.put(key, attribute, newvalue, entity);
				
	}

	
	public ArrayList get(String key, String attribute, String entity)
	{
		//get the value from the map 
		
		Map<String, Map> keyMap=new HashMap<String, Map>();
		Map<String, ArrayList> attributeMap=new HashMap<String, ArrayList>();
		ArrayList listValue=new ArrayList();

		keyMap=dbMap.get(entity);
		attributeMap=keyMap.get(key);
		listValue=attributeMap.get(attribute);
		
		return listValue;
		
	}
	
	
	public String save()
	{
		//save the map into the database
		
		return store.store(dbMap);
				
	}
	
	public boolean open(String lastHash)
	{
		//load the database into the map
		
		dbMap.clear();
		if(store.checkFile())
			dbMap=store.load(lastHash);
		
		if(dbMap==null)
			return false;
		else
			return true;
	}
	
	public void reset()
	{
		//delete the database
		
		store.delete();
		
	}
	
	
	
}
