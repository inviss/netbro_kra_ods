package kr.co.netbro.kra.rate.tools;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;

import kr.co.netbro.kra.database.PersonDBService;

public class AddPerson {
	
	@Inject
	@Optional
	private PersonDBService personDBService;
	
	@Execute
    public void execute() {
		System.out.println(personDBService.getPersons());
		//PersonDBService personService = Services.getPersonService();
    }
}
