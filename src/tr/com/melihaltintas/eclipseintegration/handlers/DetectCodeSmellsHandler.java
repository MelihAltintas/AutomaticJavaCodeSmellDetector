package tr.com.melihaltintas.eclipseintegration.handlers;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;	
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import tr.com.melihaltintas.eclipseintegration.visitors.ProjectVisitor;
import tr.com.melihaltintas.thesisreportgenerator.BugInformation;



public class DetectCodeSmellsHandler {

	

	@Execute
	public void execute(Shell shell) {

		BugInformation.getInstance();

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
				

		try {
			root.deleteMarkers("tr.com.melihaltintas.codesmellmarker", true, IProject.DEPTH_INFINITE);
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView("tr.com.melihaltintas.eclipseintegration.parts.CodeSmellView");

		} catch (PartInitException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		IProject[] projects = root.getProjects();
		ProjectVisitor projectVisitor = new ProjectVisitor(projects);
		
		projectVisitor.setUser(true);

		projectVisitor.schedule();
		
		
		System.out.println("melih");

	}

}
