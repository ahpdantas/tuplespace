package javaspace;

import net.jini.core.entry.Entry;

public class Device implements Entry{
	public String name;
	public String previous;
	public String next;
	public String env;
	
	public Device(){
		
	}
	
	public String toString() {
        return this.name;
    }
    
}
