import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MyButton extends JButton
{
	private int X;
	private int Y;
	private JPanel Box;
	private ArrayList<String> Candidates = new ArrayList<String>();
	
	public MyButton(int x, int y, JPanel box)
	{
		X = x;
		Y = y;
		Box = box;
	}
	
	public MyButton(int x, int y)
	{
		X = x;
		Y = y;
	}
	
	public JPanel getBox()
	{
		return Box;
	}
	public void setList(String candidate)
	{
		Candidates.add(candidate);
	}
	public void removeCandidate(String candidate)
	{
		Candidates.remove(candidate);
	}
	public ArrayList<String> getCandidates()
	{
		return Candidates;
	}
	public int getR()
	{
		return X;
	}
	public int getC()
	{
		return Y;
	}
	public int getS() {
		return Candidates.size();
	}
	public void show()
	{
		System.out.print("Row " + X + "COlumen "+ Y+ "  :");
		for(int i =0; i < Candidates.size(); i++)
		{
			System.out.print(Candidates.get(i));
		}
		System.out.println();
	}
}