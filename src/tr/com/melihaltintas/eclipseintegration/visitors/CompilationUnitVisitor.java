package tr.com.melihaltintas.eclipseintegration.visitors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import tr.com.melihaltintas.smelldetector.detectors.classificationdisharmonies.RefusedParentBequestDetector;
import tr.com.melihaltintas.smelldetector.detectors.classificationdisharmonies.TraditionBreaker;
import tr.com.melihaltintas.smelldetector.detectors.collaborationdisharmonies.DispersedCouplingDetector;
import tr.com.melihaltintas.smelldetector.detectors.collaborationdisharmonies.IntensiveCouplingDetector;
import tr.com.melihaltintas.smelldetector.detectors.collaborationdisharmonies.ShotgunSurgeryDetector;
import tr.com.melihaltintas.smelldetector.detectors.identitydisharmonies.BrainClassDetector;
import tr.com.melihaltintas.smelldetector.detectors.identitydisharmonies.DataClassDetector;
import tr.com.melihaltintas.smelldetector.detectors.identitydisharmonies.FeatureEnvyDetector;
import tr.com.melihaltintas.smelldetector.detectors.identitydisharmonies.GodClassDetector;

public class CompilationUnitVisitor extends Job {

	private ICompilationUnit[] units;

	public CompilationUnitVisitor(ICompilationUnit[] units) {
		super("Compilation Unit Visitor");
		this.units = units;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			SubMonitor subMonitor = SubMonitor.convert(monitor, units.length);

			for (int i = 0; i < units.length; i++) {

				subMonitor.setTaskName(
						"Compilation Unit : " + units[i].getElementName() + " " + (i + 1) + " of " + units.length);

				subMonitor.split(i + 1);

				CompilationUnit parse = parse(units[i]);

				GodClassDetector godClassDetector = new GodClassDetector();
				DataClassDetector dataClassDetector = new DataClassDetector();
				FeatureEnvyDetector featureEnvyDetector = new FeatureEnvyDetector();
				BrainClassDetector brainClassDetector = new BrainClassDetector(parse);
				IntensiveCouplingDetector intensiveCouplingDetector = new IntensiveCouplingDetector();
				DispersedCouplingDetector dispersedCouplingDetector = new DispersedCouplingDetector();
				ShotgunSurgeryDetector shotgunSurgeryDetector = new ShotgunSurgeryDetector();
				RefusedParentBequestDetector refusedParentBequestDetector = new RefusedParentBequestDetector();
				TraditionBreaker traditionBreaker = new TraditionBreaker();

				parse.accept(godClassDetector);
				parse.accept(dataClassDetector);
				parse.accept(featureEnvyDetector);
				parse.accept(brainClassDetector);
				parse.accept(intensiveCouplingDetector);
				parse.accept(dispersedCouplingDetector);
				parse.accept(shotgunSurgeryDetector);
				parse.accept(refusedParentBequestDetector);
				parse.accept(traditionBreaker);

			}
		} catch (Exception ex) {
			System.err.println(ex);
		}

		return Status.OK_STATUS;

	}

	private CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setEnvironment(null, null, null, true);
		parser.setResolveBindings(true);

		return (CompilationUnit) parser.createAST(null); // parse
	}

}
