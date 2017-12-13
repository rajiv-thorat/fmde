package org.upb.fmde.miniproject.delta;

import org.upb.fmde.de.categories.colimits.CoLimit;
import org.upb.fmde.de.categories.colimits.pushouts.Span;
import org.upb.fmde.de.categories.concrete.finsets.*;
public class FinSetDeltaComposition extends FinSets implements CategoryWithPullbacks<FinSet, TotalFunction>{

	
	public Span<TotalFunction> compose(Span<TotalFunction> f, Span<TotalFunction> g) {
		
		
		return null ;
	}
	
	public FinSetDeltaComposition pullback() {
		
		return null;
		
	}

	@Override
	public FinSet source(TotalFunction f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FinSet target(TotalFunction f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String showOb(FinSet o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String showArr(TotalFunction f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoLimit<TotalFunction, TotalFunction> equaliser(TotalFunction f, TotalFunction g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoLimit<Span<TotalFunction>, TotalFunction> product(FinSet a, FinSet b) {
		// TODO Auto-generated method stub
		return null;
	}
}
