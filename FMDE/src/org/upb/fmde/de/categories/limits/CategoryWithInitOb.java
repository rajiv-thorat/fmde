package org.upb.fmde.de.categories.limits;

import org.upb.fmde.de.categories.Category;

public interface CategoryWithInitOb<Ob, Arr> extends Category<Ob, Arr> {
	Limit<Ob, Arr> initialObject();
}
