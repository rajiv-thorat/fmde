package org.upb.fmde.miniproject.delta;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
		Map<Object, Object> hm = new HashMap<>();
		TotalFunction piG = new TotalFunction(b, null, b);
		a.elts().forEach(elementA->{
			b.elts().forEach(elementB->{
				hm.put(elementA, elementB);
			});
		});
		FinSet product = new FinSet("cartesian product", hm.entrySet().stream().collect(Collectors.toList()));
		
		return null;
	}




}
