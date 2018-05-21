package tr.com.melihaltintas.eclipseintegration.parts;

import org.eclipse.ui.views.markers.MarkerField;
import org.eclipse.ui.views.markers.MarkerItem;

public class ApplyToField extends MarkerField{

	public ApplyToField() {
		super();
	}
	
	@Override
	public String getName() {

		return "Apply To";
	}
	
	@Override
    public String getValue(MarkerItem item) { 
        return item.getMarker().getResource().getProject().getName(); 
    }
}
