package core;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;

public class dataManager {

	String filename;
	
	public dataManager(String dbName)
	{
		this.filename=dbName+".dat";
	}
	
	
	public String store(Map<String, Map> dbMap)
	{
		//save the serialized map into a file
		//return the hash of the map
		
		String serializedBlock = "";

		try {
		    ByteArrayOutputStream bo = new ByteArrayOutputStream();
		    ObjectOutputStream so = new ObjectOutputStream(bo);
		    so.writeObject(dbMap);
		    so.flush();
		    byte[] a1 = bo.toByteArray();
		    serializedBlock = Base64.encodeBase64String(a1);
			
        	FileWriter fw = new FileWriter(filename, false);
        	BufferedWriter bw = new BufferedWriter(fw);
        	PrintWriter out = new PrintWriter(bw);
        	out.println(serializedBlock);
        	out.close();

		           
		} 
		catch (Exception e) {
		    e.printStackTrace();
		}

		return this.createHash(serializedBlock);

	}
	
	
	public Map<String, Map> load(String lastHash)
	{
		//read the file and restore the map
		//check the integrity of the file
		
		String data="";
		Map<String, Map> a = null;
		
		try {
			  //read the file
		      File myObj = new File(filename);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        data = myReader.nextLine();
		      }
		      myReader.close();
		      
		      //compare the hash
		      String hash=this.createHash(data);
		      if(!hash.equals(lastHash))
		      {
		    	  //System.out.println("Integrity error");
		    	  return null;
		      }
		    	  
		      
		      	//deserialize the string into the map
			    byte b[] = Base64.decodeBase64(data); 
			    ByteArrayInputStream bi = new ByteArrayInputStream(b);
			    ObjectInputStream si = new ObjectInputStream(bi);
			    a = (Map<String, Map>) si.readObject();
		      
		    } catch (Exception e) {
		      //System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	    return a;

	}
	
	
	public boolean checkFile()
	{
		//check whether the file exists
		
		File f = new File(filename);
		if(f.exists() && !f.isDirectory()) 
		    return true;
		else
			return false;
	}


	public void delete()
	{
		//delete file
		
		File f = new File(filename);
		f.delete();
	}

	
	
	private String createHash(String serializedObject)
	{
		//create the hash of the string
		
		String newHash = null;	
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(serializedObject.getBytes("utf8"));
	        newHash = String.format("%040x", new BigInteger(1, digest.digest()));

		} catch (Exception e){
			e.printStackTrace();
		}

		return newHash; 
		
	}

}
