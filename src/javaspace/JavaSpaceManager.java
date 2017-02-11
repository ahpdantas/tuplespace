package javaspace;

import java.rmi.RemoteException;
import java.util.ArrayList;

import net.jini.core.entry.UnusableEntryException;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

public class JavaSpaceManager {
	JavaSpace space = null;
	
	public JavaSpaceManager(JavaSpace space){
		this.space = space;
		
	}
	
	public void createEnvironment(String name){
		System.out.println("Creating Environment");
  		try {
			Environment template = new Environment();
	    	template.name = name;	
			Environment env = (Environment) this.space.readIfExists(template, null, 60*1000);
		
			if( env != null ){
				System.out.println("Ambiente já existe. Tente outro");
			} else {
				System.out.println("Criando um novo ambiente");
				env = new Environment();
				env.name = name;
				env.next = "null";
				env.previous = "null";
				
				template = new Environment();
				template.next = "null";
				Environment lastEnv = (Environment) this.space.takeIfExists(template, null, 60*1000);
				if( lastEnv != null ){
					lastEnv.next = env.name;  
					env.previous = lastEnv.name;
					this.space.write(lastEnv, null, 60*1000);
				} else {
					System.out.println("Inserindo primeiro ambiente");
				}
				
				this.space.write(env, null, 60 * 1000);
				System.out.println("Ambiente:"+env.name+":"+env.next);
				
			}
    	} catch (RemoteException | UnusableEntryException | TransactionException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
	
	public void deleteEnvironment(String envName){
    	
    	Device template = new Device();
    	template.env = envName;
    	try {
			Device dev = (Device) this.space.readIfExists(template, null, 60*1000);
			if( dev != null ){
				System.out.println("Não é possível deletar um ambiente com dispositivos");
			}
			else{
				System.out.println("Ambiente sem dispositivos");
				Environment envTemplate = new Environment();
				envTemplate.name = envName;
				Environment env = (Environment)this.space.takeIfExists(envTemplate,null,60*1000);
				if( env != null ){
					
					System.out.println("Delete:"+env.previous+":"+env.name+":"+env.next);
					if( env.previous.equals("null")){
						System.out.println("Deletando primeiro ambiente da lista");
						envTemplate = new Environment();
						envTemplate.name = env.next;
						Environment nextEnv = (Environment)this.space.takeIfExists(envTemplate,null,60*1000);
						if( nextEnv != null ){
							nextEnv.previous = "null";
							this.space.write(nextEnv, null, 60*1000);
						}

					} else {
						envTemplate = new Environment();
						envTemplate.name = env.previous;
						Environment previousEnv = (Environment)this.space.takeIfExists(envTemplate,null,60*1000);
						if( previousEnv != null ){
							if( env.next.equals("null")){
								System.out.println("Deletando último ambiente da lista");
								previousEnv.next = "null";
							}else{
								System.out.println("Deletando ambiente intermediário da lista");
								previousEnv.next = env.next;
							}
							this.space.write(previousEnv, null, 60*1000);
						}
						
					}
				}else{
					System.out.println("Ambiente não existe");
				}
			}
		
		} catch (RemoteException | UnusableEntryException | TransactionException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
    }
    
    public void createDevice(String name, String envName){
    	
    	try{
        	Environment template = new Environment();
        	template.name = envName;
        	
			Environment env = (Environment)this.space.readIfExists(template, null, 60*1000);
			if( env != null ){
				System.out.println("Ambiente existe!!!!");
				
				Device devTemplate = new Device();
				devTemplate.name = name;
				devTemplate.env = envName;
				Device dev = (Device) this.space.readIfExists(devTemplate,null,60*1000);
		    	if( dev != null ){
		    		System.out.println("Dispositivo já existe no ambiente");
		    	} else {
		    		System.out.println("Dispositivo não existe no ambiente");
		    		dev = new Device();
	    			dev.name = name;
	    			dev.env = envName;
	    			dev.next = "null";
	    			
	    			devTemplate = new Device();
	    			devTemplate.next = "null";
	    			devTemplate.env = envName;
	    			Device lastDev = (Device) this.space.takeIfExists(devTemplate,null,60*1000);
	    			if( lastDev != null ){
	    				System.out.println("Existe um dispositivo no ambiente");
	    				lastDev.next = dev.name;
	    				dev.previous = lastDev.name;
	    				System.out.println(lastDev.previous+":"+lastDev.name+":"+lastDev.next+":"+lastDev.env);
	    				this.space.write(lastDev, null, 60 * 1000);
	    			} else {
	    				System.out.println("Ambiente vazio");
	    				dev.previous = "null";
	    			}
	    			System.out.println("Salvando dispositivo");
	    			this.space.write(dev,null, 60*1000);
		    	}

			}
			else{
				System.out.println("Ambiente não existe!!!!");
			}
			
			
    	}catch (RemoteException | UnusableEntryException | TransactionException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	

     }
    
    public void deleteDevice(String name){
    	Device template = new Device();
    	template.name = name;
    	try{
	    	Device dev = (Device) this.space.readIfExists(template,null,60*1000);
	    	if( dev != null ){
	    		System.out.println(dev.previous+":"+dev.name+":"+dev.next+":"+dev.env);
	    		if( dev.previous != null ){
	    			
	    			template = new Device();
	    			template.name = dev.previous;
	    			
	    			Device previousDev = (Device) this.space.takeIfExists(template,null,60*1000);
	    			if( previousDev != null ){
	    				if( dev.next.equals("null")){
	    					previousDev.next = "null";
	    				} else {
	    					previousDev.next = dev.next;
	    				}
	    				this.space.write(previousDev, null, 60*1000);
	    				System.out.println(previousDev.previous+":"+previousDev.name+":"+previousDev.next+":"+previousDev.env);
	    				
	    			} else {
	    				template = new Device();
		    			template.name = dev.next;
		    			Device nextDev = (Device) this.space.takeIfExists(template,null,60*1000);
		    			if( nextDev != null ){
		    				nextDev.previous = "null";
		    				this.space.write(nextDev, null, 60*1000);
		    			}
	    			}
	    			this.space.take(dev, null, 60*1000);
	    		}
	    	} else {
	    		System.out.println("Device not found");
	    	}
	    	
    	} catch (RemoteException | UnusableEntryException | TransactionException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    public void moveDevice(String devName, String envName ){
    	System.out.println("Movendo dispositivo");
  		try {
			Environment template = new Environment();
	    	template.name = envName;	
			Environment env = (Environment) this.space.readIfExists(template, null, 60*1000);
			if( env != null ){
				Device devTemplate = new Device();
				devTemplate.name = devName;
				Device dev = (Device) this.space.readIfExists(devTemplate, null, 60*1000);
				if( dev != null ){
					System.out.println("Dispositivo existe. Movendo-o.");
					deleteDevice(devName);
					createDevice(devName, envName);
				}
				else{
					System.out.println("Dispositivo não existe. Tente outro");
				}
			} else {
				System.out.println("Ambiente destino não existe. Tente outro");
			} 
  		}catch (RemoteException | UnusableEntryException | TransactionException | InterruptedException e1) {
  			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    public ArrayList<String> listDevices(String envName){
    	ArrayList<String> devices = null;
    	
    	try{
        	Device template = new Device();
        	template.env = envName;
        	template.name = null;
        	template.next = null;
        	template.previous = "null";
			Device dev = (Device)this.space.readIfExists(template, null, 60*1000);
			if( dev != null ){
				devices = new ArrayList<String>();
				devices.add(dev.name);
				while( true ){
					template = new Device();
					template.name = dev.next;
					dev = (Device)this.space.readIfExists(template, null, 60*1000);
					if( dev != null ){
						devices.add(dev.name);
						if( dev.next.equals("null")){
							break;
						}
					}
					else{
						break;
					}
				}
			}else{
				System.out.println("Device not found");
			}
			
    	}catch (RemoteException | UnusableEntryException | TransactionException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return devices;
    	
    }
    
    public ArrayList<String> listEnvironments(){
    	ArrayList<String> environments = null;
    	    	    	
    	try{
    		Environment template = new Environment();
        	template.previous = "null";
			Environment env = (Environment)this.space.readIfExists(template, null, Lease.FOREVER);
			if( env != null ){
				System.out.println(env);
				System.out.println(env.next);
				environments = new ArrayList<String>();
				environments.add(env.name);
				
				while( true ){
					template = new Environment();
					template.name = env.next;
					env = (Environment)this.space.readIfExists(template, null, Lease.FOREVER);
					if( env != null ){
						environments.add(env.name);
						System.out.println("Adding another environment");
						System.out.println(env.name);
						System.out.println(env.next);
						if( env.next == null){
							break;
						}
					}
					else{
						System.out.println("There is no environment found");
						break;
					}
				}
				
			}else{
				System.out.println("Environment not found");
			}
			
    	}catch (RemoteException | UnusableEntryException | TransactionException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	return environments;
    	
    }
    
}
