import java.util.Collection;
import java.util.Observable;
import java.util.UUID;




public abstract class AbstractFilter extends Observable implements Filter {

	private TYPE inputType;
	private TYPE outputType;
	private final String id;
	private Collection<String> registerToClasses;
	private Collection<String> registerToIdCollection;
	private Collection<String> canRegisterWithMeClasses;
	
	private final String description; 
	
	
	
	
	
	public AbstractFilter(TYPE inputType, TYPE outputType, String id, Collection<String> registerToClasses, Collection<String> registerToIdCollection, Collection<String> canRegisterWithMeClasses, String description){    
		this.id = id;
		this.inputType = inputType;
		this.outputType = outputType;
		this.registerToClasses = registerToClasses;
		this.registerToIdCollection = registerToIdCollection;
		this.canRegisterWithMeClasses = canRegisterWithMeClasses;
		this.description = description;
	}
	
	public AbstractFilter(TYPE inputType, TYPE outputType, Collection<String> registerToClasses, Collection<String> registerToIdCollection, Collection<String> canRegisterWithMeClasses, String description){    
		id = UUID.randomUUID().toString();
		this.inputType = inputType;
		this.outputType = outputType;
		this.registerToClasses = registerToClasses;
		this.registerToIdCollection = registerToIdCollection;
		this.canRegisterWithMeClasses = canRegisterWithMeClasses;
		this.description = description;
	}
	

	@Override
	public TYPE getInputType() {
		return inputType;
	}

	@Override
	public TYPE getOutputType() {
		return outputType;
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public Collection<String> getRegisterToClasses() {
		return registerToClasses;
	}

	@Override
	public Collection<String> getCanRegisterWithMeClasses() {
		return canRegisterWithMeClasses;
	}
	
	@Override
	public Collection<String> getRegisterToIdCollection() {
		return registerToIdCollection;
	}
	
	public void addToRegisterToClasses(String toAdd){
		registerToClasses.add(toAdd);
	}
	
	public void addToCanRegisterWithMeClasses(String toAdd){
		canRegisterWithMeClasses.add(toAdd);
	}
	
	public void addToRegisterToIdCollection(String toAdd){
		registerToIdCollection.add(toAdd);
	}
	
	public String getDescription(){
		return description;
	}
	
	public boolean canRegister(Filter other) {
		return (other.checkCompatible(this) && (other.getCanRegisterWithMeClasses().contains(this) | this.getRegisterToIdCollection().contains(other))) ;
	}

	public boolean checkCompatible(Filter other) {
		
		return getInputType().equals(other.getOutputType());
	}
	
	public void register(Observable other){
		other.addObserver(this);
	}

}
