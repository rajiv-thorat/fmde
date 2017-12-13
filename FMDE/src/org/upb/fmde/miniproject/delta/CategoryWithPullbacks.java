package org.upb.fmde.miniproject.delta;

import org.upb.fmde.de.categories.Category;
import org.upb.fmde.de.categories.colimits.CoLimit;
import org.upb.fmde.de.categories.colimits.pushouts.Span;

public interface CategoryWithPullbacks<Ob, Arr> extends Category<Ob, Arr> {

default CoLimit<Span<Arr>, Arr> pullback(Span<Arr> span) {
		Ob B = source(span.left);
		Ob C = source(span.right);
		
		CoLimit<Span<Arr>, Arr> product = product(B, C);
		
		Arr piC = product.obj.left;
		Arr piB = product.obj.right;
		CoLimit<Arr, Arr> equaliser = equaliser(
				compose(piC, span.right),
				compose(piB, span.left));
		
		return new CoLimit<>(
				new Span<Arr>(this,
				compose(equaliser.obj, piC),
				compose(equaliser.obj, piB)),
				spanofarrows -> {
					Arr a = product.up.apply(spanofarrows);
					return equaliser.up.apply(a);
				});
	}
CoLimit<Arr, Arr> equaliser(Arr f, Arr g);

CoLimit<Span<Arr>, Arr> product(Ob a, Ob b);
}
