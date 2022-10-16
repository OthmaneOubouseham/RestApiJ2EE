package rest.model.util;

import java.util.Comparator;
import rest.model.Multimedia;

public class SorterByAverage implements Comparator<Multimedia> {

	@Override
	public int compare(Multimedia arg0, Multimedia arg1) {
		return (int)(arg1.getAverage() - arg0.getAverage());
	}

}
