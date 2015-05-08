package modulotablevisualizer;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

public class ModuloTable {
	
	Vector<Vector<Color>> tableColor = new Vector<Vector<Color>>();
	
	Vector<Vector<Integer>> tableInt = new Vector<Vector<Integer>>();
	
	int modNumber;

	
	public int getModNumber() {
		return modNumber;
	}

	public void setModNumber(int modNumber) {
		this.modNumber = modNumber;
	}

	public Vector<Vector<Integer>> getTableInt() {
		return tableInt;
	}

	public void setTableInt(Vector<Vector<Integer>> tableInt) {
		this.tableInt = tableInt;
	}

	public void setTable(Vector<Vector<Color>> t)
	{
		tableColor = t;
	}
	
	public Vector<Vector<Color>> getTable()
	{
		return tableColor;
	}
	
	public void parseFile(File file) {
		try {
			// parse the file.
			int lineCount = 0;
			String[] values = null;

			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			
			
			boolean broken = false;
			while ((line = br.readLine()) != null) {
				if (line.trim().length() == 0)
					continue;
				String[] crap = { "(", ")", ",", ";" };
				for (String replace : crap) {
					line = line.replace(replace, " ").trim();
				}
				// This replaces any multiple spaces with a single space
				while (line.contains("  ")) {
					line = line.replace("  ", " ");
				}
				values = line.split(" ");
				if (line.equals(""))
					continue;
				
				this.tableColor.add(new Vector<Color>());
				this.tableInt.add(new Vector<Integer>());
				// convert each line to integers
				for (int index = 0; index < values.length; index++) {
					try {

						this.tableInt.get(lineCount).add((Integer
								.parseInt(values[index].trim()) * (16777215)) / (values.length));
					} catch (Exception e) {
						broken = true;
						JOptionPane.showMessageDialog(null,
								"Failed to parse mod file",
								"" + "", JOptionPane.INFORMATION_MESSAGE);
						break;
					}
				}
				if (broken)
					break;
				lineCount++;
			}
			br.close();
			modNumber = values.length;
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null,
					"please open a properly formatted .mod file", "" + "",
					JOptionPane.INFORMATION_MESSAGE);

		}


	}
	

}
