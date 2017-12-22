package org.upb.fmde.miniproject.delta;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.hamcrest.core.IsInstanceOf;
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
		
		List<Object> list = new ArrayList<>();
		TotalFunction piA;
		TotalFunction piB; 
		Map<Object, Object> mappingstoA = new HashMap<>(); //for piA
		Map<Object, Object> mappingstoB = new HashMap<>(); //for piB
		Span<TotalFunction> span;
		Function<Span<TotalFunction>, TotalFunction> universalProperty;
		a.elts().forEach(elementA->{
			b.elts().forEach(elementB->{
				Entry<Object, Object> entry = new AbstractMap.SimpleEntry<Object, Object>(elementA, elementB);
				list.add(entry);
				mappingstoA.put(entry, elementA);
				mappingstoB.put(entry, elementB);
			});
		});
		FinSet product = new FinSet("cartesian product_"+ a.label() +"_"+b.label(), list);
		piA = new TotalFunction(product, "pi_"+a.label(), a);
		piA.setMappings(mappingstoA);
		piB = new TotalFunction(product, "pi_"+b.label(), b);
		piB.setMappings(mappingstoB);
		span = new Span<TotalFunction>(this, piA, piB);
		universalProperty = (tfSpan) -> {
			TotalFunction left = tfSpan.left;
			TotalFunction right = tfSpan.right;
			FinSet source = source(left);
			FinSet trgLeft = target(left);
			FinSet trgRight = target(right);
			TotalFunction x = new TotalFunction(product, product.label()+"_to_"+source.label(), source);
			Map<Object, Object> xMappings = new HashMap<>();
			product.elts().stream().forEach( (productElement) ->{
				Entry<Object, Object> productEntry = (Entry<Object, Object>) productElement;
			source.elts().stream().forEach( (xElement) -> {
				Entry<Object, Object> xEntry;
				if(xElement instanceof Entry<?,?>) {
					xEntry = (Entry<Object, Object>) xElement;
					if(productEntry.equals(xEntry)) {
						xMappings.put(xEntry, productEntry);
					}
				}
			});});
			x.setMappings(xMappings);
			return x;
		};
		return new Limit<Span<TotalFunction>, TotalFunction>(span, universalProperty);
	}




}
