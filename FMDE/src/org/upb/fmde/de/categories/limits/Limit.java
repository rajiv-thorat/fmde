package org.upb.fmde.de.categories.limits;

import java.util.function.Function;

import org.upb.fmde.de.categories.colimits.CoLimit;

public class Limit<O, A> extends CoLimit<O, A>{

	public Limit(O limitObj, Function<O, A> univProp) {
		super(limitObj, univProp);
		// TODO Auto-generated constructor stub
	}

}
