package tr.com.melihaltintas.eclipseintegration.visitors;



import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;

import tr.com.melihaltintas.eclipseintegration.handlers.DetectCodeSmellsHandler;

public class PackageVisitor extends Job {

	private IJavaProject javaProject;

	public PackageVisitor(IJavaProject javaProject) {
		super("Package Visitor For " + javaProject.getElementName() + " Project");
		this.javaProject = javaProject;
	}

	protected IStatus run(IProgressMonitor monitor) {

		IPackageFragment[] packages = null;
		try {
			packages = javaProject.getPackageFragments();

			System.out.println("!!!!!! PACKAGES LENGTH " +packages.length);
		} catch (JavaModelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
		SubMonitor subMonitor = SubMonitor.convert(monitor, packages.length);
		
		for (int i = 0; i < packages.length; i++) {
			
	
			subMonitor.setTaskName(
					"Package : " + packages[i].getElementName() + " " +(i + 1) + " of " + packages.length);

			subMonitor.split(i + 1);
	
			try {
				if (packages[i].getKind() == IPackageFragmentRoot.K_SOURCE) {
					visitCompilationUnits(packages[i]);

				}
			} catch (JavaModelException e) {
			
				e.printStackTrace();
			}
			


		}

	
		return Status.OK_STATUS;
	}

	private void visitCompilationUnits(IPackageFragment currentPackage) {

		try {
			
			ICompilationUnit[] units = currentPackage.getCompilationUnits();
			CompilationUnitVisitor compilationUnitVisitor = new CompilationUnitVisitor(units);
		
			compilationUnitVisitor.schedule();
			

		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
