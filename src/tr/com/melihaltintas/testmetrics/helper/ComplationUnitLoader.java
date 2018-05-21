package tr.com.melihaltintas.testmetrics.helper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.soap.AttachmentPart;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ComplationUnitLoader {

	public static CompilationUnit getCompilationUnit(Class<?> clazz) {


		ASTParser parser = null;
		String path = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length());
		String enviromentPath = path + "src";
		
		path += "src/tr/com/melihaltintas/testmetrics/metrics/" + clazz.getSimpleName() + ".java";

		
		
		String[] srcPaths = new String[] {enviromentPath};
		AstBuilder builder = new AstBuilder(srcPaths);
		
		try {
			String source = new String(Files.readAllBytes(Paths.get(path)));
			parser = builder.create();		
			parser.setSource(source.toCharArray());
			CompilationUnit unit = (CompilationUnit) parser.createAST(null); // parse
			return unit;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return null;
	}

}
