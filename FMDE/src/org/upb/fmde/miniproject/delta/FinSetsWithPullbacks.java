package org.upb.fmde.miniproject.delta;

import org.upb.fmde.de.categories.colimits.pushouts.Span;
import org.upb.fmde.de.categories.concrete.finsets.FinSet;
import org.upb.fmde.de.categories.concrete.finsets.FinSets;
import org.upb.fmde.de.categories.concrete.finsets.TotalFunction;
import org.upb.fmde.de.categories.limits.Limit;
public class FinSetsWithPullbacks extends FinSets implements CategoryWithPullbacks<FinSet, TotalFunction>{

	
	public Span<TotalFunction> compose(Span<TotalFunction> f, Span<TotalFunction> g) {
		
		
		return null ;
	}
	
		@Override
	public Limit<TotalFunction, TotalFunction> equaliser(TotalFunction f, TotalFunction g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Limit<Span<TotalFunction>, TotalFunction> product(FinSet a, FinSet b) {
		// TODO Auto-generated method stub
		return null;
	}




}
