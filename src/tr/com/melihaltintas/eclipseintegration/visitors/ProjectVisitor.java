package tr.com.melihaltintas.eclipseintegration.visitors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class ProjectVisitor extends Job {

	private IProject[] projects;

	public ProjectVisitor(IProject[] projects) {
		super("Project Visitor");
		this.projects = projects;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {

		SubMonitor subMonitor = SubMonitor.convert(monitor, projects.length);

		for (int i = 0; i < projects.length; i++) { // Loop over all projects
			try {

				if (projects[i].isNatureEnabled("org.eclipse.jdt.core.javanature")) {

					IJavaProject javaProject = JavaCore.create(projects[i]);

					subMonitor.setTaskName(
							"Project : " + javaProject.getElementName() + " " + (i + 1) + " of " + projects.length);

					subMonitor.split(i + 1);

					visitPackages(javaProject);
					

				}
			} catch (CoreException e) {
				e.printStackTrace();
			}

		}
		// monitor.done();
		return Status.OK_STATUS;
	}

	private void visitPackages(IJavaProject javaProject) {

		PackageVisitor packageVisitor = new PackageVisitor(javaProject);
		packageVisitor.schedule();

	}

}
