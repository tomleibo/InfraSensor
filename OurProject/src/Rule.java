import java.util.Collection;
import java.util.LinkedList;
import java.util.Observable;


public class Rule extends AbstractFilter {
	
	private Collection<Filter> filters;
	private Collection<Rule> registeredTo;
	
	
	public Rule(TYPE inputType, TYPE outputType, String id,
			Collection<String> registerToClasses,
			Collection<String> registerToIdCollection,
			Collection<String> canRegisterWithMeClasses, String description, Collection<Filter> filters) {
		super(inputType, outputType, id, registerToClasses, registerToIdCollection,
				canRegisterWithMeClasses, description);
		this.filters = filters;
		registeredTo = new LinkedList<Rule>();
		
	}
	
	public Rule(TYPE inputType, TYPE outputType,
			Collection<String> registerToClasses,
			Collection<String> registerToIdCollection,
			Collection<String> canRegisterWithMeClasses, String description, Collection<Filter> filters) {
		super(inputType, outputType, registerToClasses, registerToIdCollection,
				canRegisterWithMeClasses, description);
		this.filters = filters;
		registeredTo = new LinkedList<Rule>();
		
	}
	
	public Collection<Filter> getFilters(){
		return filters;
	}
	
	public Collection<Rule> getRegisteredTo(){
		return registeredTo;
	}



	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
