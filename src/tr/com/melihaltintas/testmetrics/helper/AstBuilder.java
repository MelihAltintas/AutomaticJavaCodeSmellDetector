package tr.com.melihaltintas.testmetrics.helper;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.omg.PortableInterceptor.ORBInitializerOperations;

public class AstBuilder {

	
	private String[] sourcePaths;
	private String[] encoding;
	
	public String[] getEncoding() {
		return encoding;
	}
	
	public AstBuilder (String[] sourcePaths) {
		
		this.sourcePaths = sourcePaths;
		this.encoding = new String[this.sourcePaths.length];
		Arrays.fill(this.encoding, "UTF-8");
		
	
		
	}
	
	public ASTParser create() {
		
		ASTParser parser = ASTParser.newParser(AST.JLS9);

		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		parser.setEnvironment(null, sourcePaths, this.encoding, true);
		parser.setUnitName("any_name");
		return parser;
	}
}
