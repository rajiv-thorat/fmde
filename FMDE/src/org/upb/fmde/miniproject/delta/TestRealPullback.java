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
import org.upb.fmde.de.categories.colimits.pushouts.CoSpan;
import org.upb.fmde.de.categories.colimits.pushouts.Span;
import org.upb.fmde.de.categories.concrete.finsets.FinSet;
import org.upb.fmde.de.categories.concrete.finsets.FinSetDiagram;
import org.upb.fmde.de.categories.concrete.finsets.TotalFunction;
import org.upb.fmde.de.categories.diagrams.Diagram;
import org.upb.fmde.de.categories.limits.Limit;
public class TestRealPullback {
	private static final String diagrams = "diagrams/miniprojdelta/";
	@Test
	public void testEqualiserUniversality() {
		FinSetDiagram d1 = createDiagram1();
		TotalFunction tfDPrime = d1.getArrow("d'"); 
		TotalFunction tfBPrime = d1.getArrow("b'");
		FinSetsWithPullbacks fwp = new FinSetsWithPullbacks();
		Limit<Span<TotalFunction>, TotalFunction> limitPullback = fwp.pullback(new CoSpan<TotalFunction>(fwp.FinSets, tfBPrime, tfDPrime));
	}
	@Test
	public void testPullback() throws IOException {
		FinSetDiagram d1 = createDiagram1();
		d1.saveAsDot(diagrams, "testPullback")
		 .prettyPrint(diagrams, "testPullback");
		TotalFunction tfDPrime = d1.getArrow("d'"); 
		TotalFunction tfBPrime = d1.getArrow("b'");
		FinSetsWithPullbacks fwp = new FinSetsWithPullbacks();
		Limit<Span<TotalFunction>, TotalFunction> limitPullback = fwp.pullback(new CoSpan<TotalFunction>(fwp.FinSets, tfBPrime, tfDPrime));
		assertTrue(limitPullback.obj.left.src().elts().contains("P_B"));
		
	}
	private FinSetDiagram createDiagram1() {
		FinSet fsB = new FinSet("B", "P_B", "I_B");
		FinSet fsA = new FinSet("A", "X_A", "C_A", "D_A");
		FinSet fsC = new FinSet("C", "P_C", "A_C");
		TotalFunction tfDPrime = new TotalFunction(fsB, "d'", fsA); 
		TotalFunction tfBPrime = new TotalFunction(fsC, "b'", fsA);
		tfDPrime.addMapping(fsB.get("P_B"), fsA.get("X_A"));
		tfDPrime.addMapping(fsB.get("I_B"), fsA.get("D_A"));
		tfBPrime.addMapping(fsC.get("P_C"), fsA.get("C_A"));
		tfBPrime.addMapping(fsC.get("A_C"), fsA.get("X_A"));
		FinSetsWithPullbacks fwp = new FinSetsWithPullbacks();
		Limit<Span<TotalFunction>, TotalFunction> limitPullback = fwp.pullback(new CoSpan<TotalFunction>(fwp.FinSets, tfBPrime, tfDPrime));
		Span<TotalFunction> prodSpanObj = fwp.product(fsC, fsB).obj;
		
		
		
		
		FinSetDiagram d1 = new FinSetDiagram();
		d1.objects(fsB, fsA, fsC, limitPullback.obj.left.src(), limitPullback.obj.left.trg(),prodSpanObj.left.src()).arrows(tfDPrime, tfBPrime, limitPullback.obj.left, prodSpanObj.left, limitPullback.obj.right, prodSpanObj.right);

		return d1;
	}
}

