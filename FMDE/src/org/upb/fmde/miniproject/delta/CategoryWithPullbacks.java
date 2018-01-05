package org.upb.fmde.miniproject.delta;

import org.upb.fmde.de.categories.Category;
import org.upb.fmde.de.categories.colimits.pushouts.CoSpan;
import org.upb.fmde.de.categories.colimits.pushouts.Span;
import org.upb.fmde.de.categories.limits.Limit;

public interface CategoryWithPullbacks<Ob, Arr> extends Category<Ob, Arr> {
	
	default Limit<Span<Arr>, Arr> pullback(CoSpan<Arr> cospan) {
		Ob R = source(cospan.right);
		Ob G = source(cospan.left);
		
		Limit<Span<Arr>, Arr> product = product(R, G);
		
		Limit<Arr, Arr> equaliser = equaliser(
				compose(product.obj.left, cospan.right),
				compose(product.obj.right, cospan.left));
		
		return new Limit<Span<Arr>, Arr> (
				new Span<Arr>(this,
				compose(equaliser.obj, product.obj.left),
				compose(equaliser.obj, product.obj.right)),
				span -> {
					Arr a = product.up.apply(span);
					return equaliser.up.apply(a);
				});
	}
	
	Limit<Arr, Arr> equaliser(Arr f, Arr g);
	
	Limit<Span<Arr>, Arr> product(Ob a, Ob b);
}