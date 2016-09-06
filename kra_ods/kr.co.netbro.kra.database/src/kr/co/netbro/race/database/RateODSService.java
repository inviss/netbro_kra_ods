package kr.co.netbro.race.database;

import java.util.List;

import kr.co.netbro.kra.entity.Cancel;
import kr.co.netbro.kra.entity.Change;
import kr.co.netbro.kra.entity.Final;
import kr.co.netbro.kra.entity.Result;

public interface RateODSService {
	  List<Cancel> findCancels();
	  List<Change> findChanges();
	  List<Final> findFinals();
	  List<Result> findResults();
}
