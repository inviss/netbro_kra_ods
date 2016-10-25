package kr.co.netbro.kra.rate.binding;

import org.eclipse.core.databinding.property.value.DelegatingValueProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.jface.internal.databinding.swt.DateTimeSelectionProperty;
import org.eclipse.swt.widgets.DateTime;

@SuppressWarnings("restriction")
public class VogellaDelegatingValueProperty extends DelegatingValueProperty {

	public VogellaDelegatingValueProperty() {
		this(null);
	}

	public VogellaDelegatingValueProperty(Object valueType) {
		super(valueType);
	}


	@Override
	protected IValueProperty doGetDelegate(Object source) {
		if (source instanceof DateTime) {
			return new DateTimeSelectionProperty();
		}

		return null;
	}
}
