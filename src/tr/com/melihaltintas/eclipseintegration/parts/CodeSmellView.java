package tr.com.melihaltintas.eclipseintegration.parts;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.markers.MarkerSupportView;

public class CodeSmellView extends MarkerSupportView {

	public CodeSmellView() {

		super("tr.com.melihaltintas.eclipseintegration.parts.MarkerGenerator");
		setPartName("Detected Code Smells");

	}

	

}
