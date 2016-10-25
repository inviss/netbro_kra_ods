package kr.co.netbro.kra.rate.directs;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;

import kr.co.netbro.kra.entity.Change;
import kr.co.netbro.race.database.IRateODSService;

public class TestConn {
	@Optional
	@Inject
	private IRateODSService rateODSService;
	
	@Execute
    public void execute() {
		List<Change> cancels = rateODSService.findChanges("", "");
		System.out.println(cancels.size());
	}
}
