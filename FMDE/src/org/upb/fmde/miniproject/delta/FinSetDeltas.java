package org.upb.fmde.miniproject.delta;

import static org.junit.Assert.assertFalse;

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

public class FinSetDeltas<FinSet, TotalFunction> extends DeltaCategory<FinSet, TotalFunction> {
	private static final String diagrams = "diagrams/fsetDelta/";
	public FinSetDeltas() {
		// TODO Auto-generated constructor stub
		super((CategoryWithPullbacks<FinSet, TotalFunction>) new FinSetsWithPullbacks());
	}
	
	@BeforeClass
	public static void clear() {
		TestUtil.clear(diagrams);
	}
	
	@Test
	public void testDeltaFinSet1() {
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
		
		
Diagram<FinSet, TotalFunction> diagToShow = new FinSetDiagram();
		
		diagToShow.objects(diag.getObject("G"),diag.getObject("R"), product.obj.left.trg(), candidate).arrows(product.obj.left,product.obj.right, candidateToG, candidateToR, univCandidateToProduct, FinSets.FinSets.id(candidate));
		
		
		
		
		diagToShow.saveAsDot(diagrams, "idcheck")
		 .prettyPrint(diagrams, "idcheck");
		
	
		assertFalse(product.obj.left.src().elts().isEmpty());
		
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
