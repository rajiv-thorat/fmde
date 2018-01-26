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
	public void testDeltaFinSet2() throws IOException {
		Diagram<FinSet, TotalFunction> diag = createDiagram2();
		FinSetDeltas deltaCat = new FinSetDeltas();
		FinSet fsH = diag.getObject("H");
		FinSet fsG = diag.getObject("G");
		FinSet fsHPrime = diag.getObject("H'");
		FinSet fsR = diag.getObject("R");
		FinSet fsHPrimePrime = diag.getObject("H''");
		FinSet fsHBar = diag.getObject("Ħ");
		TotalFunction tfGDeltaMinus = diag.getArrow("Gδ₋");
		TotalFunction tfGDeltaPlus = diag.getArrow("Gδ₊");
		TotalFunction tfRDeltaMinus = diag.getArrow("Rδ₋");				
		TotalFunction tfRDeltaPlus = diag.getArrow("Rδ₊");
		TotalFunction tfHBarDeltaMinus = diag.getArrow("Ħδ₋");
		TotalFunction tfHBarDeltaPlus = diag.getArrow("Ħδ₊");
		TotalFunction tfDeltaCompMinus = diag.getArrow("(Ħδ₋;Gδ₋)");
		TotalFunction tfDeltaCompPlus = diag.getArrow("(Ħδ₊;Rδ₊)");			
		
		Span<TotalFunction> sDelta1 = new Span<TotalFunction>(deltaCat.cat, tfGDeltaMinus, tfGDeltaPlus);
		Span<TotalFunction> sDelta2 = new Span<TotalFunction>(deltaCat.cat, tfRDeltaMinus, tfRDeltaPlus);
		Span<TotalFunction> sDeltaCompose = deltaCat.compose(sDelta1, sDelta2);
		Span<TotalFunction> sSpanCompose = new Span<TotalFunction>(deltaCat.cat, FinSets.FinSets.compose(tfHBarDeltaMinus, tfGDeltaMinus),FinSets.FinSets.compose(tfHBarDeltaPlus, tfRDeltaPlus));
		sDeltaCompose.left.src().label("Ħ");
		sDeltaCompose.left.label("(Ħδ₋;Gδ₋)");
		sDeltaCompose.right.label("(Ħδ₊;Rδ₊)");
		diag.saveAsDot(diagrams, "testDeltaFinSets2")
		 .prettyPrint(diagrams, "testDeltaFinSets2");
		assertTrue(sDeltaCompose.left.isTheSameAs(tfDeltaCompMinus) &&
				sDeltaCompose.right.isTheSameAs(tfDeltaCompPlus)    &&
				sDeltaCompose.left.isTheSameAs(sSpanCompose.left) &&
				sDeltaCompose.right.isTheSameAs(sSpanCompose.right));
		
	}
	private FinSetDiagram createDiagram2() {
		FinSet fsH = new FinSet("H", "Diagnosis1", "Bed1",  "OP1", "Reception", "Emergency");
		FinSet fsG = new FinSet("G", "Bed1", "OP1", "Reception", "Emergency");
		FinSet fsHPrime = new FinSet("H'", "Bed2", "OP2", "Bed1", "OP1", "Reception",  "Emergency");
		FinSet fsR = new FinSet("R", "Bed2", "OP2", "Bed1", "OP1", "Emergency");
		FinSet fsHPrimePrime = new FinSet("H''", "Diagnosis2", "Bed2", "OP2", "Bed1", "OP1", "Emergency");
		FinSet fsHBar = new FinSet("Ħ", "Bed1", "OP1", "Emergency");
		
		
		TotalFunction tfGDeltaMinus = new TotalFunction(fsG, "Gδ₋", fsH)
				.addMapping(fsG.get("Bed1"), fsH.get("Bed1"))
				.addMapping(fsG.get("OP1"), fsH.get("OP1"))
				.addMapping(fsG.get("Reception"), fsH.get("Reception"))
				.addMapping(fsG.get("Emergency"), fsH.get("Emergency"));
		TotalFunction tfGDeltaPlus = new TotalFunction(fsG, "Gδ₊", fsHPrime)
				.addMapping(fsG.get("Bed1"), fsHPrime.get("Bed1"))
				.addMapping(fsG.get("OP1"), fsHPrime.get("OP1"))
				.addMapping(fsG.get("Reception"), fsHPrime.get("Reception"))
				.addMapping(fsG.get("Emergency"), fsHPrime.get("Emergency"));
		TotalFunction tfRDeltaMinus = new TotalFunction(fsR, "Rδ₋", fsHPrime)
				.addMapping(fsR.get("Bed2"), fsHPrime.get("Bed2"))
				.addMapping(fsR.get("OP2"), fsHPrime.get("OP2"))
				.addMapping(fsR.get("Bed1"), fsHPrime.get("Bed1"))
				.addMapping(fsR.get("OP1"), fsHPrime.get("OP1"))
				.addMapping(fsR.get("Emergency"), fsHPrime.get("Emergency"));				
		TotalFunction tfRDeltaPlus = new TotalFunction(fsR, "Rδ₊", fsHPrimePrime)
				.addMapping(fsR.get("Bed2"), fsHPrime.get("Bed2"))
				.addMapping(fsR.get("OP2"), fsHPrime.get("OP2"))
				.addMapping(fsR.get("Bed1"), fsHPrime.get("Bed1"))
				.addMapping(fsR.get("OP1"), fsHPrime.get("OP1"))
				.addMapping(fsR.get("Emergency"), fsHPrime.get("Emergency"));
		TotalFunction tfHCBarDeltaMinus = new TotalFunction(fsHBar, "Ħδ₋", fsG)
				.addMapping(fsHBar.get("Bed1"), fsG.get("Bed1"))
				.addMapping(fsHBar.get("OP1"), fsG.get("OP1"))
				.addMapping(fsHBar.get("Emergency"), fsG.get("Emergency"));
		TotalFunction tfHBarDeltaPlus = new TotalFunction(fsHBar, "Ħδ₊", fsR)
				.addMapping(fsHBar.get("Bed1"), fsR.get("Bed1"))
				.addMapping(fsHBar.get("OP1"), fsR.get("OP1"))
				.addMapping(fsHBar.get("Emergency"), fsR.get("Emergency"));
		TotalFunction tfDeltaCompMinus = new TotalFunction(fsHBar, "(Ħδ₋;Gδ₋)", fsH)
		.addMapping(fsHBar.get("Bed1"), fsH.get("Bed1"))
		.addMapping(fsHBar.get("OP1"), fsH.get("OP1"))
		.addMapping(fsHBar.get("Emergency"), fsH.get("Emergency"));
		TotalFunction tfDeltaCompPlus = new TotalFunction(fsHBar, "(Ħδ₊;Rδ₊)", fsHPrimePrime)
		.addMapping(fsHBar.get("Bed1"), fsHPrimePrime.get("Bed1"))
		.addMapping(fsHBar.get("OP1"), fsHPrimePrime.get("OP1"))
		.addMapping(fsHBar.get("Emergency"), fsHPrimePrime.get("Emergency"));		
		FinSetDiagram d1 = new FinSetDiagram();
		d1.objects(fsH, fsHPrime, fsHPrimePrime, fsG, fsR, fsHBar).arrows(tfHCBarDeltaMinus, tfHBarDeltaPlus, tfGDeltaMinus, tfGDeltaPlus, tfRDeltaMinus, tfRDeltaPlus, tfDeltaCompMinus, tfDeltaCompPlus);

		return d1;
	}
	@Test
	public void testDeltaFinSet1() throws IOException {
		Diagram<FinSet, TotalFunction> diag = createDiagram1();
		FinSetDeltas deltaCat = new FinSetDeltas();
		FinSet fsH = diag.getObject("H");
		FinSet fsHCirc = diag.getObject("Ĥ");
		FinSet fsHPrime = diag.getObject("H'");
		FinSet fsHPrimeCirc = diag.getObject("Ĥ'");
		FinSet fsHPrimePrime = diag.getObject("H''");
		FinSet fsHBar = diag.getObject("H#");
		TotalFunction tfHCircDeltaMinus = diag.getArrow("Ĥδ₋");
		TotalFunction tfHCircDeltaPlus = diag.getArrow("Ĥδ₊");
		TotalFunction tfHCircPrimeDeltaMinus = diag.getArrow("Ĥ'δ₋");				
		TotalFunction tfHCircPrimeDeltaPlus = diag.getArrow("Ĥ'δ₊");
		TotalFunction tfHCBarDeltaMinus = diag.getArrow("H#δ₋");
		TotalFunction tfHBarDeltaPlus = diag.getArrow("H#δ₊");
		TotalFunction tfDeltaCompMinus = diag.getArrow("(H#δ₋;Ĥδ₋)");
		TotalFunction tfDeltaCompPlus = diag.getArrow("(H#δ₊;Ĥ'δ₊)");			
		
		Span<TotalFunction> sDelta1 = new Span<TotalFunction>(deltaCat.cat, tfHCircDeltaMinus, tfHCircDeltaPlus);
		Span<TotalFunction> sDelta2 = new Span<TotalFunction>(deltaCat.cat, tfHCircPrimeDeltaMinus, tfHCircPrimeDeltaPlus);
		Span<TotalFunction> sDeltaCompose = deltaCat.compose(sDelta1, sDelta2);	
		sDeltaCompose.left.src().label("H#");
		sDeltaCompose.left.label("(H#δ₋;Ĥδ₋)");
		sDeltaCompose.right.label("(H#δ₊;Ĥ'δ₊)");
		diag.saveAsDot(diagrams, "testDeltaFinSets")
		 .prettyPrint(diagrams, "testDeltaFinSets");
		
	
		assertTrue(sDeltaCompose.left.isTheSameAs(tfDeltaCompMinus) &&
				sDeltaCompose.right.isTheSameAs(tfDeltaCompPlus));
		
	}
	private FinSetDiagram createDiagram1() {
		FinSet fsH = new FinSet("H", "doctor1", "doctor2",  "nurse1", "nurse2", "secretarian");
		FinSet fsHCirc = new FinSet("Ĥ", "doctor1", "nurse1", "nurse2", "secretarian");
		FinSet fsHPrime = new FinSet("H'", "doctor1", "nurse1", "nurse2", "nurse3", "nurse4",  "secretarian");
		FinSet fsHPrimeCirc = new FinSet("Ĥ'", "doctor1", "nurse1", "nurse2", "nurse3", "nurse4");
		FinSet fsHPrimePrime = new FinSet("H''", "doctor1", "nurse1", "nurse2", "nurse3",  "nurse4", "nurse5");
		FinSet fsHBar = new FinSet("H#", "doctor1", "nurse1", "nurse2");
		
		
		TotalFunction tfHCircDeltaMinus = new TotalFunction(fsHCirc, "Ĥδ₋", fsH)
				.addMapping(fsHCirc.get("doctor1"), fsH.get("doctor1"))
				.addMapping(fsHCirc.get("nurse1"), fsH.get("nurse1"))
				.addMapping(fsHCirc.get("nurse2"), fsH.get("nurse2"))
				.addMapping(fsHCirc.get("secretarian"), fsH.get("secretarian"));
		TotalFunction tfHCircDeltaPlus = new TotalFunction(fsHCirc, "Ĥδ₊", fsHPrime)
				.addMapping(fsHCirc.get("doctor1"), fsHPrime.get("doctor1"))
				.addMapping(fsHCirc.get("nurse1"), fsHPrime.get("nurse1"))
				.addMapping(fsHCirc.get("nurse2"), fsHPrime.get("nurse2"))
				.addMapping(fsHCirc.get("secretarian"), fsHPrime.get("secretarian"));
		TotalFunction tfHCircPrimeDeltaMinus = new TotalFunction(fsHPrimeCirc, "Ĥ'δ₋", fsHPrime)
				.addMapping(fsHPrimeCirc.get("doctor1"), fsHPrime.get("doctor1"))
				.addMapping(fsHPrimeCirc.get("nurse1"), fsHPrime.get("nurse1"))
				.addMapping(fsHPrimeCirc.get("nurse2"), fsHPrime.get("nurse2"))
				.addMapping(fsHPrimeCirc.get("nurse3"), fsHPrime.get("nurse3"))
				.addMapping(fsHPrimeCirc.get("nurse4"), fsHPrime.get("nurse4"));				
		TotalFunction tfHCircPrimeDeltaPlus = new TotalFunction(fsHPrimeCirc, "Ĥ'δ₊", fsHPrimePrime)
				.addMapping(fsHPrimeCirc.get("doctor1"), fsHPrime.get("doctor1"))
				.addMapping(fsHPrimeCirc.get("nurse1"), fsHPrime.get("nurse1"))
				.addMapping(fsHPrimeCirc.get("nurse2"), fsHPrime.get("nurse2"))
				.addMapping(fsHPrimeCirc.get("nurse3"), fsHPrime.get("nurse3"))
				.addMapping(fsHPrimeCirc.get("nurse4"), fsHPrime.get("nurse4"));
		TotalFunction tfHCBarDeltaMinus = new TotalFunction(fsHBar, "H#δ₋", fsHCirc)
				.addMapping(fsHBar.get("doctor1"), fsHCirc.get("doctor1"))
				.addMapping(fsHBar.get("nurse1"), fsHCirc.get("nurse1"))
				.addMapping(fsHBar.get("nurse2"), fsHCirc.get("nurse2"));
		TotalFunction tfHBarDeltaPlus = new TotalFunction(fsHBar, "H#δ₊", fsHPrimeCirc)
				.addMapping(fsHBar.get("doctor1"), fsHPrimeCirc.get("doctor1"))
				.addMapping(fsHBar.get("nurse1"), fsHPrimeCirc.get("nurse1"))
				.addMapping(fsHBar.get("nurse2"), fsHPrimeCirc.get("nurse2"));
		TotalFunction tfDeltaCompMinus = new TotalFunction(fsHBar, "(H#δ₋;Ĥδ₋)", fsH)
		.addMapping(fsHBar.get("doctor1"), fsH.get("doctor1"))
		.addMapping(fsHBar.get("nurse1"), fsH.get("nurse1"))
		.addMapping(fsHBar.get("nurse2"), fsH.get("nurse2"));
		TotalFunction tfDeltaCompPlus = new TotalFunction(fsHBar, "(H#δ₊;Ĥ'δ₊)", fsHPrimePrime)
		.addMapping(fsHBar.get("doctor1"), fsHPrimePrime.get("doctor1"))
		.addMapping(fsHBar.get("nurse1"), fsHPrimePrime.get("nurse1"))
		.addMapping(fsHBar.get("nurse2"), fsHPrimePrime.get("nurse2"));		
		FinSetDiagram d1 = new FinSetDiagram();
		d1.objects(fsH, fsHPrime, fsHPrimePrime, fsHCirc, fsHPrimeCirc, fsHBar).arrows(tfHCBarDeltaMinus, tfHBarDeltaPlus, tfHCircDeltaMinus, tfHCircDeltaPlus, tfHCircPrimeDeltaMinus, tfHCircPrimeDeltaPlus, tfDeltaCompMinus, tfDeltaCompPlus);

		return d1;
	}
}
