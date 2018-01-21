package org.upb.fmde.miniproject.delta;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.upb.fmde.de.categories.colimits.pushouts.Span;
import org.upb.fmde.de.categories.concrete.finsets.FinSet;
import org.upb.fmde.de.categories.concrete.finsets.FinSetDiagram;
import org.upb.fmde.de.categories.concrete.finsets.FinSets;
import org.upb.fmde.de.categories.diagrams.Diagram;
import org.upb.fmde.de.categories.limits.Limit;
import org.upb.fmde.de.tests.TestUtil;

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
public class TestDelta {
	private static final String diagrams = "diagrams/fsetDelta/";
	@BeforeClass
	public static void clear() {
		TestUtil.clear(diagrams);
	}
	
	@Test
	public void testDeltaFinSet1() throws IOException {
		Diagram<FinSet, TotalFunction> diag = createDiagram1();
		FinSetDeltas deltaCat = new FinSetDeltas();
		TotalFunction tfXbarDeltaMinus = diag.getArrow("Xbar_Delta_minus");
		TotalFunction tfXbarDeltaPlus = diag.getArrow("Xbar_Delta_plus");
		TotalFunction tfYbarDeltaMinus = diag.getArrow("Ybar_Delta_minus");
		TotalFunction tfYbarDeltaPlus = diag.getArrow("Ybar_Delta_plus");
		TotalFunction tfBarBarDeltaMinus = diag.getArrow("BarBar_Delta_minus");
		TotalFunction tfBarBarDeltaPlus = diag.getArrow("BarBar_Delta_plus");
		TotalFunction tfDeltaCompMinus = diag.getArrow("BarBar_Delta_plus");
		TotalFunction tfDeltaCompPlus = diag.getArrow("BarBar_Delta_plus");
		FinSet fsX = diag.getObject("hospital_old");
		FinSet fsXbar =diag.getObject("hospital_old_bar");
		FinSet fsY = diag.getObject("hospital_between");
		FinSet fsYbar = diag.getObject("hospital_between_bar");
		FinSet fsZ = diag.getObject("hospital_end");
		FinSet fsBarBar = diag.getObject("hospital_barbar");
		
		
		Span<TotalFunction> sDelta1 = new Span<TotalFunction>(deltaCat.cat, tfXbarDeltaMinus, tfXbarDeltaPlus);
		Span<TotalFunction> sDelta2 = new Span<TotalFunction>(deltaCat.cat, tfYbarDeltaMinus, tfYbarDeltaPlus);
		Span<TotalFunction> sDeltaCompose = deltaCat.compose(sDelta1, sDelta2);	
		diag.arrows(sDeltaCompose.left, sDeltaCompose.right);
		diag.saveAsDot(diagrams, "testDeltaFinSets")
		 .prettyPrint(diagrams, "testDeltaFinSets");
		
	
		assertTrue(sDeltaCompose.left.isTheSameAs(tfDeltaCompMinus) &&
				sDeltaCompose.right.isTheSameAs(tfDeltaCompPlus));
		
	}
	private FinSetDiagram createDiagram1() {
		FinSet fsX = new FinSet("hospital_old", "doctor1", "doctor2",  "nurse1", "nurse2", "secretarian");
		FinSet fsXbar = new FinSet("hospital_old_bar", "doctor1", "nurse1", "nurse2", "secretarian");
		FinSet fsY = new FinSet("hospital_between", "doctor1", "nurse1", "nurse2", "nurse3", "nurse4",  "secretarian");
		FinSet fsYbar = new FinSet("hospital_between_bar", "doctor1", "nurse1", "nurse2", "nurse3", "nurse4");
		FinSet fsZ = new FinSet("hospital_end", "doctor1", "nurse1", "nurse2", "nurse3",  "nurse4", "nurse5");
		FinSet fsBarBar = new FinSet("hospital_barbar", "doctor1", "nurse1", "nurse2");
		
		
		TotalFunction tfXbarDeltaMinus = new TotalFunction(fsXbar, "Xbar_Delta_minus", fsX)
				.addMapping(fsXbar.get("doctor1"), fsX.get("doctor1"))
				.addMapping(fsXbar.get("nurse1"), fsX.get("nurse1"))
				.addMapping(fsXbar.get("nurse2"), fsX.get("nurse2"))
				.addMapping(fsXbar.get("secretarian"), fsX.get("secretarian"));
		TotalFunction tfXbarDeltaPlus = new TotalFunction(fsXbar, "Xbar_Delta_plus", fsY)
				.addMapping(fsXbar.get("doctor1"), fsY.get("doctor1"))
				.addMapping(fsXbar.get("nurse1"), fsY.get("nurse1"))
				.addMapping(fsXbar.get("nurse2"), fsY.get("nurse2"))
				.addMapping(fsXbar.get("secretarian"), fsY.get("secretarian"));
		TotalFunction tfYbarDeltaMinus = new TotalFunction(fsYbar, "Ybar_Delta_minus", fsY)
				.addMapping(fsYbar.get("doctor1"), fsY.get("doctor1"))
				.addMapping(fsYbar.get("nurse1"), fsY.get("nurse1"))
				.addMapping(fsYbar.get("nurse2"), fsY.get("nurse2"))
				.addMapping(fsYbar.get("nurse3"), fsY.get("nurse3"))
				.addMapping(fsYbar.get("nurse4"), fsY.get("nurse4"));				
		TotalFunction tfYbarDeltaPlus = new TotalFunction(fsYbar, "Ybar_Delta_plus", fsZ)
				.addMapping(fsYbar.get("doctor1"), fsY.get("doctor1"))
				.addMapping(fsYbar.get("nurse1"), fsY.get("nurse1"))
				.addMapping(fsYbar.get("nurse2"), fsY.get("nurse2"))
				.addMapping(fsYbar.get("nurse3"), fsY.get("nurse3"))
				.addMapping(fsYbar.get("nurse4"), fsY.get("nurse4"));
		TotalFunction tfBarBarDeltaMinus = new TotalFunction(fsBarBar, "BarBar_Delta_minus", fsXbar)
				.addMapping(fsBarBar.get("doctor1"), fsXbar.get("doctor1"))
				.addMapping(fsBarBar.get("nurse1"), fsXbar.get("nurse1"))
				.addMapping(fsBarBar.get("nurse2"), fsXbar.get("nurse2"));
		TotalFunction tfBarBarDeltaPlus = new TotalFunction(fsBarBar, "BarBar_Delta_plus", fsYbar)
				.addMapping(fsBarBar.get("doctor1"), fsYbar.get("doctor1"))
				.addMapping(fsBarBar.get("nurse1"), fsYbar.get("nurse1"))
				.addMapping(fsBarBar.get("nurse2"), fsYbar.get("nurse2"));
		TotalFunction tfDeltaCompMinus = new TotalFunction(fsBarBar, "BarBar_Delta_plus", fsX)
		.addMapping(fsBarBar.get("doctor1"), fsX.get("doctor1"))
		.addMapping(fsBarBar.get("nurse1"), fsX.get("nurse1"))
		.addMapping(fsBarBar.get("nurse2"), fsX.get("nurse2"));
		TotalFunction tfDeltaCompPlus = new TotalFunction(fsBarBar, "BarBar_Delta_plus", fsZ)
		.addMapping(fsBarBar.get("doctor1"), fsZ.get("doctor1"))
		.addMapping(fsBarBar.get("nurse1"), fsZ.get("nurse1"))
		.addMapping(fsBarBar.get("nurse2"), fsZ.get("nurse2"));		
		FinSetDiagram d1 = new FinSetDiagram();
		d1.objects(fsX, fsY, fsZ, fsXbar, fsYbar, fsBarBar).arrows(tfBarBarDeltaMinus, tfBarBarDeltaPlus, tfXbarDeltaMinus, tfXbarDeltaPlus, tfYbarDeltaMinus, tfYbarDeltaPlus, tfDeltaCompMinus, tfDeltaCompPlus);

		return d1;
	}
}
