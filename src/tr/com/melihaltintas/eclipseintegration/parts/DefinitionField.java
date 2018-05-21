package tr.com.melihaltintas.eclipseintegration.parts;

import org.eclipse.ui.views.markers.MarkerField;
import org.eclipse.ui.views.markers.MarkerItem;

public class DefinitionField extends MarkerField {

	
	public DefinitionField() {
		super();
	}
	
	
	@Override
	public String getName() {

		return "Definition";
	}

	@Override
	public String getValue(MarkerItem item) {
		return item.getMarker().getResource().getProject().getName();
	}

}
