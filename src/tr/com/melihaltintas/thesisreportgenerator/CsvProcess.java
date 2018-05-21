package tr.com.melihaltintas.thesisreportgenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import tr.com.melihaltintas.smelldetector.markers.MarkerCreator.MarkerType;

public class CsvProcess {

	private static String[] columnNames = null;
	private static String[] values = null;

	public static HashMap<String, ClassBugMapping> readCsv(String filePath) {

		HashMap<String, ClassBugMapping> classBugMappings = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
			String line;
			int lineNumber = 0;
			while ((line = br.readLine()) != null) {
				if (lineNumber == 0) {
					columnNames = line.split(";");
				} else {
					if (line.contains("$")) {
						line = line.replaceAll("\\$\\d+", "");
						line = line.replaceAll("\\$", "\\.");
					}
					values = line.split(";");
					ClassBugMapping mapping = new ClassBugMapping();
					int index = 0;
					for (String value : values) {
						if (index == 0) {
							mapping.projectName = value;
						} else if (index == 1) {
							mapping.version = value;
						} else if (index == 2) {
							mapping.className = value;
						} else if (columnNames[index].equals("bugs")) {
							mapping.bug = Integer.parseInt(value);
						} else {

							mapping.metricValues.put(columnNames[index], value);
						}
						index++;
					}
					if (classBugMappings.containsKey(mapping.className)) {
						classBugMappings.get(mapping.className).bug += mapping.bug;
					} else {
						classBugMappings.put(mapping.className, mapping);
					}

				}
				lineNumber++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return classBugMappings;

	}

	public static void writeCsv(String filePath, HashMap<String, ClassBugMapping> classBugMappings) {
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {

			fileWriter = new FileWriter(new File(filePath));
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(String.join(";", columnNames) + ";smells;smells count");
			bufferedWriter.newLine();

			for (ClassBugMapping item : classBugMappings.values()) {
				StringBuilder builder = new StringBuilder();
				builder.append(item.projectName + ";");
				builder.append(item.version + ";");
				builder.append(item.className + ";");
				if (item.metricValues.size() > 0) {
					builder.append(String.join(";", item.metricValues.values()) + ";");
				}
				builder.append(item.bug + ";");
				for (Smell smell : item.smells) {
					builder.append(smell.toString() + " -- ");
				}
				if (item.smells.size() > 0) {
					builder.append(";");
				} else {
					builder.append("0;");
				}
				builder.append(item.smells.size());
				bufferedWriter.write(builder.toString());
				bufferedWriter.newLine();
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bufferedWriter != null)
					bufferedWriter.close();

				if (fileWriter != null)
					fileWriter.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

	public static void writeFaultNonFaultTable(String filePath, HashMap<String, ClassBugMapping> classBugMappings) {
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		HashMap<String, SmellFault> smellFaults = new HashMap<>();

		try {

			fileWriter = new FileWriter(new File(filePath));
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(String.join(";", new String[] { "Smell Name", "f", "nf" }));
			bufferedWriter.newLine();

			for (MarkerType smellType : MarkerType.values()) {

				smellFaults.put(smellType.name(), new SmellFault(smellType.name(), 0, 0));
			}

			for (ClassBugMapping item : classBugMappings.values()) {
				if (item.bug == 0) { // no bug
					for (Smell smell : item.smells) {
						SmellFault sf = smellFaults.get(smell.getSmellName());
						sf.setFault(sf.getFault() + 1);
						System.err.println(item);
					}
				} else {
					for (Smell smell : item.smells) {
						SmellFault sf = smellFaults.get(smell.getSmellName());
						sf.setNonFault(sf.getNonFault() + 1);
					}
				}
			}

			for (MarkerType smellType : MarkerType.values()) {
				StringBuilder builder = new StringBuilder();
				SmellFault sf = smellFaults.get(smellType.name());
				builder.append(sf.getSmellName() + ";");
				builder.append(sf.getFault() + ";");
				builder.append(sf.getNonFault());
				bufferedWriter.write(builder.toString());
				bufferedWriter.newLine();
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bufferedWriter != null)
					bufferedWriter.close();

				if (fileWriter != null)
					fileWriter.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

	public static void writeCorrelationTable(String filePath, HashMap<String, ClassBugMapping> classBugMappings) {
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {

			fileWriter = new FileWriter(new File(filePath));
			bufferedWriter = new BufferedWriter(fileWriter);

			MarkerType[] smellTypes = MarkerType.values();
			int[][] correlatationMatrix = new int[smellTypes.length][smellTypes.length];

			StringBuilder builder = new StringBuilder();
			builder.append(";");
			for (int i = 0; i < smellTypes.length; i++) {

				builder.append(smellTypes[i].name());
				if (i != smellTypes.length - 1) {
					builder.append(";");
				}
			}
			bufferedWriter.write(builder.toString());
			bufferedWriter.newLine();

			for (ClassBugMapping item : classBugMappings.values()) {
				for (Smell smell1 : item.smells) {
					for (Smell smell2 : item.smells) {
						if (smell1.getSmellName().equals(smell2.getSmellName())) {
							int index = getIndexOfSmell(smellTypes, smell1.getSmellName());
							correlatationMatrix[index][index] = 0;
						} else {
							int smell1Index = getIndexOfSmell(smellTypes, smell1.getSmellName());
							int smell2Index = getIndexOfSmell(smellTypes, smell2.getSmellName());
							correlatationMatrix[smell1Index][smell2Index] += 1;
						}
					}
				}
			}

			
			for (MarkerType smellType : smellTypes) {
				builder = new StringBuilder();
				builder.append(smellType.name());
				builder.append(";");
				int index = getIndexOfSmell(smellTypes, smellType.name());
				
				for(int i = 0;i< smellTypes.length;i++) {
					builder.append(correlatationMatrix[index][i]);
					if (i != smellTypes.length - 1) {
						builder.append(";");
					}
					
				}
				bufferedWriter.write(builder.toString());
				bufferedWriter.newLine();
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bufferedWriter != null)
					bufferedWriter.close();

				if (fileWriter != null)
					fileWriter.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

	private static int getIndexOfSmell(MarkerType[] smellTypes, String smellName) {

		for (int i = 0; i < smellTypes.length; i++) {

			if (smellTypes[i].name().equals(smellName)) {
				return i;
			}
		}

		return -1;
	}
}
