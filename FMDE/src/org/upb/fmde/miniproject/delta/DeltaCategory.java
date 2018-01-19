package org.upb.fmde.miniproject.delta;

import org.upb.fmde.de.categories.Category;
import org.upb.fmde.de.categories.colimits.pushouts.CoSpan;
import org.upb.fmde.de.categories.colimits.pushouts.Span;
import org.upb.fmde.de.categories.limits.Limit;

public class DeltaCategory<Ob, Arr> implements Category<Ob, Span<Arr>>{
	protected CategoryWithPullbacks<Ob, Arr> cat;

	public DeltaCategory(CategoryWithPullbacks<Ob, Arr> pCat) {
		this.cat = pCat;
	}
	@Override
	public Span<Arr> compose(Span<Arr> f, Span<Arr> g) {
		 Limit<Span<Arr>, Arr> pullback = cat.pullback(new CoSpan<Arr>(cat, 
				 f.right , g.left));
		 Arr left = pullback.obj.left;
		 Arr right = pullback.obj.right;
		 Arr leftDeltaFLeft = cat.compose(left, f.left);
		 Arr rightDeltaGRight = cat.compose(right, g.right);
		 return new Span<Arr>(cat, leftDeltaFLeft, rightDeltaGRight);
	}

	@Override
	public Span<Arr> id(Ob o) {
		return new Span<Arr>(cat, cat.id(o), cat.id(o));
	}

	@Override
	public Ob source(Span<Arr> f) {
		return cat.target(f.left);
	}

	@Override
	public Ob target(Span<Arr> f) {
		return cat.target(f.right);
	}

	@Override
	public String showOb(Ob o) {
	return o.toString();
	}

	@Override
	public String showArr(Span<Arr> f) {
	return f.toString();
	}



}
