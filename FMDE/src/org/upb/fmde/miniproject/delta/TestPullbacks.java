package org.upb.fmde.miniproject.delta;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.upb.fmde.de.categories.colimits.pushouts.Span;
import org.upb.fmde.de.categories.concrete.finsets.FinSet;
import org.upb.fmde.de.categories.concrete.finsets.FinSetDiagram;
import org.upb.fmde.de.categories.concrete.finsets.FinSets;
import org.upb.fmde.de.categories.concrete.finsets.TotalFunction;
import org.upb.fmde.de.categories.diagrams.Diagram;
import org.upb.fmde.de.categories.limits.Limit;
import org.upb.fmde.de.tests.TestUtil;
public class TestPullbacks {
	
	private static final String diagrams = "diagrams/miniproj/";
	
	
	@BeforeClass
	public static void clear() {
		TestUtil.clear(diagrams);
	}
	
	@Test
	public void testProductUnivPropertyWithEmptyCandidate() {
		Diagram<FinSet, TotalFunction> d1 = createDiagram1();
		FinSetsWithPullbacks fwp = new FinSetsWithPullbacks();
		Limit<Span<TotalFunction>, TotalFunction> product =  fwp.product(d1.getObject("X"), d1.getObject("Z"));

		FinSet emptyUniv = new FinSet("empty");
		TotalFunction emptyToX = new TotalFunction(emptyUniv, "empty", d1.getObject("X"));
		TotalFunction emptyToZ = new TotalFunction(emptyUniv, "empty", d1.getObject("Z"));		
		System.out.println("univ_prop empty");
		TotalFunction univResult = product.up.apply(new Span<TotalFunction>(FinSetsWithPullbacks.FinSets, emptyToX, emptyToZ));
		printMap(univResult.mappings());	
		
	Diagram<FinSet, TotalFunction> diagToShow = new FinSetDiagram();
		
		diagToShow.objects(d1.getObject("X"),d1.getObject("Z"), product.obj.left.trg(), emptyUniv).arrows(product.obj.left,product.obj.right, emptyToX, emptyToZ, univResult, FinSets.FinSets.id(emptyUniv));
		
		
		
		
		try {
			diagToShow.saveAsDot(diagrams, "emptyCandidate")
			 .prettyPrint(diagrams, "emptyCandidate");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		assertTrue(univResult.mappings().isEmpty());	
	}
	@Test
	public void testProductUnivPropertyWithSmallCandidate() {
		Diagram<FinSet, TotalFunction> d1 = createDiagram1();
		FinSetsWithPullbacks fwp = new FinSetsWithPullbacks();
		Limit<Span<TotalFunction>, TotalFunction> product =  fwp.product(d1.getObject("X"), d1.getObject("Z"));		

		List<Object> smallList = new ArrayList<>();
		AbstractMap.SimpleEntry<Object, Object> simpleEntry =new AbstractMap.SimpleEntry<Object, Object>(d1.getObject("X").get("b"), d1.getObject("Z").get("3")); 
		smallList.add(simpleEntry);
		FinSet smallCandidate = new FinSet("small", smallList);
		
		TotalFunction smallCandidateToX = new TotalFunction(smallCandidate, "small", d1.getObject("X"));
		TotalFunction smallCandidateToZ = new TotalFunction(smallCandidate, "small", d1.getObject("Z"));
		smallCandidateToX.addMapping(simpleEntry,d1.getObject("X").get("b"));
		smallCandidateToZ.addMapping(simpleEntry,d1.getObject("Z").get("3"));	
		TotalFunction smallCandidateToProduct = product.up.apply(new Span<TotalFunction>(FinSetsWithPullbacks.FinSets, smallCandidateToX, smallCandidateToZ));
		System.out.println("univ_prop empty small");
		printMap(smallCandidateToProduct.mappings());

		Diagram<FinSet, TotalFunction> diagToShow = new FinSetDiagram();
		
		diagToShow.objects(d1.getObject("X"),d1.getObject("Z"), product.obj.left.trg(), smallCandidate).arrows(product.obj.left,product.obj.right, smallCandidateToX, smallCandidateToZ, smallCandidateToProduct, FinSets.FinSets.id(smallCandidate));
		
		
		
		
		try {
			diagToShow.saveAsDot(diagrams, "smallerCandidate")
			 .prettyPrint(diagrams, "smallerCandidate");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		assertTrue(FinSets.FinSets.compose(smallCandidateToProduct, product.obj.left).isTheSameAs(smallCandidateToX) &&
				FinSets.FinSets.compose(smallCandidateToProduct, product.obj.right).isTheSameAs(smallCandidateToZ));
	}
	
	@Test
	public void testProductUnivPropertyWithEquivalentCandidate() throws IOException {
		Diagram<FinSet, TotalFunction> diag = createDiagram2();
		FinSetsWithPullbacks fwp = new FinSetsWithPullbacks();
		Limit<Span<TotalFunction>, TotalFunction> product = fwp.product(diag.getObject("G"), diag.getObject("R"));
		
		
		
		
		List<Object> listofEntries = new ArrayList<>();
		
		// this is the actual candidate.
		AbstractMap.SimpleEntry<Object, Object> entry1 = new AbstractMap.SimpleEntry<Object, Object>(diag.getObject("G").get("a"), diag.getObject("R").get("3"));
		AbstractMap.SimpleEntry<Object, Object> entry2 = new AbstractMap.SimpleEntry<Object, Object>(diag.getObject("G").get("b"),diag.getObject("R").get("3"));
		
		listofEntries.add(entry1);
		listofEntries.add(entry2);
		
		FinSet candidate = new FinSet("candidate", listofEntries);
		
		TotalFunction candidateToG = new TotalFunction(candidate, "candidate_G", diag.getObject("G"));
		TotalFunction candidateToR = new TotalFunction(candidate, "candidate_R", diag.getObject("R"));
				
		candidateToG.addMapping(entry1,diag.getObject("G").get("a"));
		candidateToR.addMapping(entry1,diag.getObject("R").get("3"));
		candidateToG.addMapping(entry2,diag.getObject("G").get("b"));
		candidateToR.addMapping(entry2,diag.getObject("R").get("3"));		
		
		
		
		TotalFunction univCandidateToProduct = product.up.apply(new Span<TotalFunction>(FinSetsWithPullbacks.FinSets, candidateToG, candidateToR));
		System.out.println("univ_prop empty candidate");
		printMap(univCandidateToProduct.mappings());
		
		
		
		Diagram<FinSet, TotalFunction> diagToShow = new FinSetDiagram();
		
		diagToShow.objects(diag.getObject("G"),diag.getObject("R"), product.obj.left.trg(), candidate).arrows(product.obj.left,product.obj.right, candidateToG, candidateToR, univCandidateToProduct, FinSets.FinSets.id(candidate));
		
		
		
		
		diagToShow.saveAsDot(diagrams, "idcheck")
		 .prettyPrint(diagrams, "idcheck");
		
		
		
		//assertTrue(FinSets.FinSets.id(candidate).isTheSameAs(univResultSmaller));
		
		
		
		assertTrue(FinSets.FinSets.compose(univCandidateToProduct, product.obj.left).isTheSameAs(candidateToG) &&
				FinSets.FinSets.compose(univCandidateToProduct, product.obj.right).isTheSameAs(candidateToR));
		
		
	}
	@Test
	public void testProductResultnotEmpty() {
		Diagram<FinSet, TotalFunction> d1 = createDiagram1();
		FinSetsWithPullbacks fwp = new FinSetsWithPullbacks();
		Limit<Span<TotalFunction>, TotalFunction> product =  fwp.product(d1.getObject("X"), d1.getObject("Z"));
		
		System.out.println("product result");
		product.obj.left.src().elts().stream().forEach( (element) 
				-> {
					System.out.println(element);
				});
		System.out.println("left");
		printMap(product.obj.left.mappings());
		
		System.out.println("right");
		printMap(product.obj.right.mappings());
		
		
		
	
		assertFalse(product.obj.left.src().elts().isEmpty());
		
	}
	private void printMap(Map<Object, Object> pMap) {
		pMap.entrySet().forEach( (amapping) -> {
			System.out.println("Key: " + amapping.getKey() + ":" +amapping.getKey().getClass()+ ", Value: "+  amapping.getValue() + ":" +amapping.getValue().getClass());
		});
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
	
	private FinSetDiagram createDiagram2() {
		FinSet G = new FinSet("G", "a", "b");
		FinSet R = new FinSet("R", 3);
		
		TotalFunction g = new TotalFunction(G, "f", R)
				.addMapping(G.get("a"), R.get("3"))
				.addMapping(G.get("b"), R.get("3"));
				
		FinSetDiagram d1 = new FinSetDiagram();
		d1.objects(G, R).arrows(g);

		return d1;
	}
	
	

}
