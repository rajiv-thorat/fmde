package org.upb.fmde.miniproject.delta;

import org.upb.fmde.de.categories.Category;
import org.upb.fmde.de.categories.colimits.pushouts.CoSpan;
import org.upb.fmde.de.categories.colimits.pushouts.Span;
import org.upb.fmde.de.categories.limits.Limit;

public interface DeltaCategory<Ob, Arr> extends Category<Ob, Arr>{

	default public Span<Arr> deltaCompose(CategoryWithPullbacks<Ob, Arr> cwb, Span<Arr> deltaF, Span<Arr> deltaG) {
		 Limit<Span<Arr>, Arr> pullback = cwb.pullback(new CoSpan<Arr>(cwb, deltaF.right , deltaG.left));
		 Arr left = pullback.obj.left;
		 Arr right = pullback.obj.right;
		 Arr leftDeltaFLeft = compose(left, deltaF.left);
		 Arr rightDeltaGRight = compose(right, deltaG.right);
		 return new Span(cwb, leftDeltaFLeft, rightDeltaGRight);
	}
	
}
