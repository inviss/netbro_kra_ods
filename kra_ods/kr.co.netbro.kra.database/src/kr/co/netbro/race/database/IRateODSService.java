package kr.co.netbro.race.database;

import java.util.List;

import kr.co.netbro.kra.entity.Cancel;
import kr.co.netbro.kra.entity.Change;
import kr.co.netbro.kra.entity.Final;
import kr.co.netbro.kra.entity.Result;

public interface IRateODSService {
	  List<Cancel> findCancels(String zone, String date);
	  List<Change> findChanges(String zone, String date);
	  List<Final> findFinals(String zone, String date);
	  List<Result> findResults(String zone, String date);
}
