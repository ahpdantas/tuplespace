package javaspace;
import java.util.ArrayList;

import net.jini.core.entry.Entry;

public class Environment implements Entry {
	/**
	 * 
	 */
	public String name = null;
	public String next = null;
	public String previous = null;
	public String dev = null;		
	public Environment(){
	}
		
	public String toString() {
        return this.name;
    }

}
