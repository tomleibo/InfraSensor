import java.util.Collection;
import java.util.Observable;


public class FilterImpl extends AbstractFilter {
	
	



	public FilterImpl(TYPE inputType, TYPE outputType, String id,
			Collection<String> registerToClasses,
			Collection<String> registerToIdCollection,
			Collection<String> canRegisterWithMeClasses, String description) {
		super(inputType, outputType, id, registerToClasses, registerToIdCollection,
				canRegisterWithMeClasses,description);
	}
	
	public FilterImpl(TYPE inputType, TYPE outputType,
			Collection<String> registerToClasses,
			Collection<String> registerToIdCollection,
			Collection<String> canRegisterWithMeClasses, String description) {
		super(inputType, outputType, registerToClasses, registerToIdCollection,
				canRegisterWithMeClasses, description);
	}

	
	

	@Override
	public void update(Observable o, Object arg) {  // implement this method
		// TODO Auto-generated method stub
		
	}
	
	
	







}
