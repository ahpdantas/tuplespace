package logic;
import java.util.ArrayList;

public class Environment {
	private String name;
	private ArrayList<Device> devices = new ArrayList<Device>();
	
	public Environment(String name){
		this.name = name;
	}
		
    public ArrayList<Device> getDevices(){
    	return this.devices;
    }
    
    public void addDevice(String name){
		Device dev = new Device(name);
		devices.add(dev);
	}
	
	public void deleteDevice(String dev){
		for( Device d: this.devices){
			if( d != null && d.toString().equals(dev) ){
				this.devices.remove(d);
			}
		}
	}
	
	public void moveDevice(Device dev, Environment another){
		another.devices.add(dev);
		this.devices.remove(dev);
	}
	
	public String toString() {
        return this.name;
    }
    

}
