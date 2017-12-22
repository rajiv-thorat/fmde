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
	
		@Override
	public Limit<TotalFunction, TotalFunction> equaliser(TotalFunction f, TotalFunction g) {
		// TODO Auto-generated method stub
		FinSet fsProduct = f.src();
		FinSet fsEqual;
		List<Object> lEqual = new ArrayList<>();
		Map<Object, Object> mMappings = new HashMap<>();
		Object rightElement;
		for(Object productElement: fsProduct.elts()){
			if(f.map(productElement).equals(g.map(productElement))) {
				if (productElement instanceof Entry<?, ?>) {
				rightElement = ((Entry<Object, Object>)productElement).getValue();
				lEqual.add(rightElement);
				mMappings.put(rightElement, productElement);
				}
			}
		}
		fsEqual = new FinSet("Equaliser", lEqual);
		TotalFunction tfEqual = new TotalFunction(fsEqual, "equaliserFunction", fsProduct);
		tfEqual.setMappings(mMappings);
		Limit<TotalFunction, TotalFunction> lEqualiser = 
				new Limit<TotalFunction, TotalFunction>(tfEqual, (tfEqualCandidate) -> {
					TotalFunction xTilde = new TotalFunction(tfEqualCandidate.src(), "xTilde", tfEqualCandidate.trg());
					
					tfEqualCandidate.mappings().entrySet().forEach( (mapEntryCandidate) -> {
						tfEqual.mappings().entrySet().forEach( (mapEntryEqualiser) -> {
							if(mapEntryCandidate.getValue().equals(mapEntryEqualiser.getValue())) {
								xTilde.addMapping(mapEntryCandidate.getKey(), mapEntryEqualiser.getKey());
							}
						});
					});
					return xTilde;
				});
		return lEqualiser;
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
			source.elts().stream().forEach( (xElement) -> {
				Object leftMapping;
				Object rightMapping;
					leftMapping = left.map(xElement);
					rightMapping = right.map(xElement);
					product.elts().stream().forEach( (productElement) ->{
						if(leftMapping.equals(piA.map(productElement)) 
								&& rightMapping.equals(piB.map(productElement))) {
							xMappings.put(xElement, productElement);
						}
					}
					);
				
			});
			
			x.setMappings(xMappings);
			return x;
		};
		return new Limit<Span<TotalFunction>, TotalFunction>(span, universalProperty);
	}




}
