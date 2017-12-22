package org.upb.fmde.miniproject.delta;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.upb.fmde.de.categories.colimits.pushouts.Span;
import org.upb.fmde.de.categories.concrete.finsets.FinSet;
import org.upb.fmde.de.categories.concrete.finsets.FinSetDiagram;
import org.upb.fmde.de.categories.concrete.finsets.TotalFunction;
import org.upb.fmde.de.categories.diagrams.Diagram;
import org.upb.fmde.de.categories.limits.Limit;
public class TestPullbacks {
	@Test
	public void testPullback() {
		Diagram<FinSet, TotalFunction> d1 = createDiagram1();
		FinSetsWithPullbacks fwp = new FinSetsWithPullbacks();
		FinSet emptyUniv = new FinSet("empty");
		List<Object> smallList = new ArrayList<>();
		AbstractMap.SimpleEntry<Object, Object> simpleEntry =new AbstractMap.SimpleEntry<Object, Object>(d1.getObject("X").elts().get(1), d1.getObject("Z").elts().get(1)); 
		smallList.add(simpleEntry);
		
		
		FinSet smallerUniv = new FinSet("small", smallList);
		TotalFunction univPropTest1 = new TotalFunction(emptyUniv, "empty", d1.getObject("X"));
		TotalFunction univPropTest2 = new TotalFunction(emptyUniv, "empty", d1.getObject("Z"));
		TotalFunction univPropTest3 = new TotalFunction(smallerUniv, "small", d1.getObject("X"));
		TotalFunction univPropTest4 = new TotalFunction(smallerUniv, "small", d1.getObject("Z"));
		univPropTest3.addMapping(simpleEntry,d1.getObject("X").elts().get(1));
		univPropTest4.addMapping(simpleEntry,d1.getObject("Z").elts().get(1));
		Limit<Span<TotalFunction>, TotalFunction> product =  fwp.product(d1.getObject("X"), d1.getObject("Z"));
		product.obj.left.src().elts().stream().forEach( (element) 
				-> {
					System.out.println(element);
				});
		System.out.println("left");
		product.obj.left.mappings().entrySet().forEach( (amapping) -> {
			System.out.println("Key: " + amapping.getKey() + ":" +amapping.getKey().getClass()+ ", Value: "+  amapping.getValue() + ":" +amapping.getValue().getClass());
		});
		System.out.println("right");
		product.obj.right.mappings().entrySet().forEach( (amapping) -> {
			System.out.println("Key: " + amapping.getKey() + ":" +amapping.getKey().getClass()+ ", Value: "+  amapping.getValue() + ":" +amapping.getValue().getClass());
		});
		System.out.println("univ_prop empty");
		TotalFunction univResult = product.up.apply(new Span<TotalFunction>(FinSetsWithPullbacks.FinSets, univPropTest1, univPropTest2));
		univResult.mappings().entrySet().forEach( (amapping) -> {
			System.out.println("Key: " + amapping.getKey() + ":" +amapping.getKey().getClass()+ ", Value: "+  amapping.getValue() + ":" +amapping.getValue().getClass());
		});
		
		System.out.println("univ_prop empty small");
		TotalFunction univResultSmaller = product.up.apply(new Span<TotalFunction>(FinSetsWithPullbacks.FinSets, univPropTest3, univPropTest4));
		univResultSmaller.mappings().entrySet().forEach( (amapping) -> {
			System.out.println("Key: " + amapping.getKey() + ":" +amapping.getKey().getClass()+ ", Value: "+  amapping.getValue() + ":" +amapping.getValue().getClass());
		});
		assertTrue(univResult.mappings().isEmpty());
		assertTrue(true);
	}
	private FinSetDiagram createDiagram1() {
		FinSet X = new FinSet("X", "a", "b", "c");
		FinSet Y = new FinSet("Y", 1, 2, "blup");
		FinSet Z = new FinSet("Z", 'a', 3, "foo");
		
		TotalFunction g = new TotalFunction(X, "g", Y)
				.addMapping(X.get("a"), Y.get("1"))
				.addMapping(X.get("b"), Y.get("2"))
				.addMapping(X.get("c"), Y.get("1"));
		
		TotalFunction h = new TotalFunction(X, "h", Y)
				.addMapping(X.get("a"), Y.get("1"))
				.addMapping(X.get("b"), Y.get("2"))
				.addMapping(X.get("c"), Y.get("blup"));
		
		TotalFunction f = new TotalFunction(Y, "f", Z)
				.addMapping(Y.get("1"), Z.get("3"))
				.addMapping(Y.get("2"), Z.get("a"))
				.addMapping(Y.get("blup"), Z.get("3"));
				
		FinSetDiagram d1 = new FinSetDiagram();
		d1.objects(X, Y, Z).arrows(g, h, f);

		return d1;
	}

}
