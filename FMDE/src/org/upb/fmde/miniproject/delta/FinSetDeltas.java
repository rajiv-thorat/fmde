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
	
	public FinSetDeltas() {
		// TODO Auto-generated constructor stub
		super((CategoryWithPullbacks<FinSet, TotalFunction>) new FinSetsWithPullbacks());
	}
	
	
}
