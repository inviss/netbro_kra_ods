package kr.co.netbro.kra.rate.binding;

import kr.co.netbro.kra.rate.parts.CustomWidget;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.swt.widgets.Widget;

public class CustomWidgetObservableValue extends AbstractObservableValue
implements ISWTObservableValue {

	private CustomWidget customWidget;

	public CustomWidgetObservableValue(CustomWidget customWidget) {
		this(customWidget, Realm.getDefault());
	}

	public CustomWidgetObservableValue(CustomWidget customWidget, Realm realm) {
		super(realm);
		this.customWidget = customWidget;
	}

	@Override
	public Object getValueType() {
		return String.class;
	}

	@Override
	protected Object doGetValue() {
		return customWidget.getText();
	}

	@Override
	protected void doSetValue(Object value) {
		customWidget.setText(value.toString());
	}

	@Override
	public Widget getWidget() {
		// implement the ISWTObservableValue interface to enable ControlDecorationSupport
		return customWidget;
	}

}
