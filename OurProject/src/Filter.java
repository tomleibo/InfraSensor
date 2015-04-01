import java.util.Collection;
import java.util.Observable;
import java.util.Observer;


public interface Filter extends Observer {
	
	enum TYPE{
		
	}

	
	TYPE getInputType();
	
	TYPE getOutputType();
	
	String getId();
	
	Collection<String> getRegisterToClasses();
	
	Collection<String> getCanRegisterWithMeClasses();
	
	public Collection<String> getRegisterToIdCollection();
	
	boolean canRegister(Filter other);
	
	void register(Observable other);
	
	boolean checkCompatible(Filter other);
	
	void addToRegisterToClasses(String toAdd);
	
	void addToCanRegisterWithMeClasses(String toAdd);
	
	void addToRegisterToIdCollection(String toAdd);
	
	String getDescription();

	

	
}
