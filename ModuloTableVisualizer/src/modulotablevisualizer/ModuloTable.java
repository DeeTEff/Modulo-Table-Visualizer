package modulotablevisualizer;

import java.util.Vector;

public class ModuloTable {
	
	Vector<Vector<Integer>> table = new Vector<Vector<Integer>>();
	
	public void setTable(Vector<Vector<Integer>> t)
	{
		table = t;
	}
	
	public Vector<Vector<Integer>> getTable()
	{
		return table;
	}

}
