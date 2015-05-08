package modulotablevisualizer;

import java.awt.Color;
import java.util.Vector;

public class ModuloTable {
	
	Vector<Vector<Color>> tableColor = new Vector<Vector<Color>>();
	
	Vector<Vector<Integer>> tableInt = new Vector<Vector<Integer>>();

	
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

}
