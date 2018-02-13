import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Game extends JFrame implements ActionListener{
	private Main main;
	private JRadioButtonMenuItem checkMenuItem;
	private JRadioButtonMenuItem singleMenuItem;
	private JRadioButtonMenuItem hiddensingleMenuItem;
	private JRadioButtonMenuItem lockedcandidateMenuItem;
	private JRadioButtonMenuItem nakedpairMenuItem;
	private JRadioButtonMenuItem allAlgorithmMenuItem;
	private ArrayList<Integer> Rows = new ArrayList<Integer>();
	private ArrayList<Integer> Colums = new ArrayList<Integer>();
	private ArrayList<String> Values = new ArrayList<String>();
    private ArrayList<MyButton> objectButtons;
	private JPanel container;
	private JPanel panel[][];
	private JPanel panel1;
	private JPanel panel2;
	private MyButton buttons1[][];
	private JButton buttons2[][];
	private String click;
	private boolean isLoadOK = false;
	private boolean isClicked = false;
	private boolean checkonfill = false;
	private final int ROW = 9;
	private final int COLUM = 9;
	
	public Game(Main m)
	{
		this.main = m;
		GetData();
		SetBoard();
	}
	
	public Game(ArrayList<Integer> Rows,ArrayList<Integer> Colums, ArrayList<String> Values)
	{
		Load();
		if(isLoadOK == true) 
		{SetBoard();}
		else
		{
			this.Rows = Rows;
			this.Colums = Colums;
			this.Values = Values;
			SetBoard();
		}
	}

	//getting info from Main class
	void GetData()
	{
		this.Rows = main.getRows();
		this.Colums = main.getColums();
		this.Values = main.getValues();
	}

	void SetBoard()
	{
		//set up menu bar
		JMenuBar menubar = new JMenuBar();
	    setJMenuBar( menubar );

	    // set up File menu and its menu items
	    JMenu fileMenu = new JMenu( "File" );

	    JMenuItem loadMenuItem = new JMenuItem( "Load" );
	    loadMenuItem.setMnemonic( 'L' );
	    fileMenu.add(loadMenuItem);
	    loadMenuItem.addActionListener(
	    new ActionListener() {  // anonymous inner class
		  public void actionPerformed( ActionEvent event )
	      {
	    	  setVisible(false);
	    	  Game obj = new Game(Rows,Colums,Values);
		  }
	    }  // end anonymous inner class
	    ); // end call
	    
	    JMenuItem storeMenuItem = new JMenuItem( "Store" );
	      storeMenuItem.setMnemonic( 'o' );
	      fileMenu.add(storeMenuItem);
	      storeMenuItem.addActionListener(
	         new ActionListener() {  // anonymous inner class
	            public void actionPerformed( ActionEvent event )
	            {
	            	try {
						Store();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	         }  // end anonymous inner class
	      ); // end call

	      JMenuItem exitMenuItem = new JMenuItem( "Exit" );
	      exitMenuItem.setMnemonic( 'x' );
	      fileMenu.add(exitMenuItem);
	      exitMenuItem.addActionListener(
	         new ActionListener() {  // anonymous inner class
	            public void actionPerformed( ActionEvent event )
	            {
	               System.exit(0);
	            }
	         }  // end anonymous inner class
	      ); // end call

	      menubar.add( fileMenu );

	      JMenu helpMenu = new JMenu( "Help!" );

	      JMenuItem sudokuMenuItem = new JMenuItem( "Sudoku" );
	      sudokuMenuItem.setMnemonic( 'A' );
	      helpMenu.add( sudokuMenuItem );
	      sudokuMenuItem.addActionListener(
	         new ActionListener() {  // anonymous inner class
	            // display message dialog when user selects About...
	            public void actionPerformed( ActionEvent event )
	            {
	            	JOptionPane.showMessageDialog(Game.this,
	  	                  "Sudoku is a puzzle that often uses a 9x9 grid of 81 cells.\n" 
	  	            	  +"The grid is divided into rows, columns and boxes. \n"
	  	                  +"The boxes are 3x3 sub-grids of 9 cells. \n"
	  	            	  +"Thus each row, column and box contains 9 cells. \n"
	  	            	  + "The object is the fill in the numbers from 1 to 9.\n"
	  	            	  + "So that each row, column and box contain each number from 1 to 9 only once.\n",
	  	                  "How to play Sudoku", JOptionPane.PLAIN_MESSAGE);
	            }
	         }  // end anonymous inner class
	      ); // end call to addActionListener

	      JMenuItem interfaceMenuItem = new JMenuItem( "Our Interface" );
	      interfaceMenuItem.setMnemonic( 'A' );
	      helpMenu.add( interfaceMenuItem );
	      interfaceMenuItem.addActionListener(
	         new ActionListener() {  // anonymous inner class
	            // display message dialog when user selects About...
	            public void actionPerformed( ActionEvent event )
	            {
	            	JOptionPane.showMessageDialog(Game.this,
	  	                  "Sudoku, 3rd project of CS342.\n"
	  	                  +"FILE MENU\n"
	  	                  +"Load: Allowing the user to search the file system for a puzzle stored in a text file.\n"
	  	                  +"Store: Allow the user to store the current puzzle into a text file named by the user.\n"
	  	                  +"Exit: Exit the program\n"
	  	                  +"\n"
	  	                  +"HELP MENU\n"
	  	                  +"Sudkoku: A dialog box on how to play Sudoku.\n"
	  	                  +"Our Interface: A dialog box you're seeing right now, how to use our interface.\n"
	  	                  +"About: Name and Net-id of our members\n"
	  	                  +"\n"
	  	                  +"HINTS MENU\n"
	  	                  +"Check on Fill: This is kind of 'Switch/Toggle'. If this mode is on user can't place the digit which is not supposed to place.\n"
	  	                  +"Single Algorithm: When user click on this, our program generate the algorithm and try to place one digit applying that algorithm.\n"
	  	                  +"Hidden Single Algorithm: When user click on this, our program generate the algorithm and try to place one digit applying that algorithm.\n"
	  	                  +"Locked Candidate Algorithm: When user click on this, our program generate the algorithm and try to place one digit applying that algorithm.\n"
	  	                  +"Naked Pair Algorithm: When user click on this, our program generate the algorithm and try to place one digit applying that algorithm.\n"
	  	                  +"Apply all Algorithm: When user click on this, our program generate all algorithms and try to solve puzzle applyin all algorithms.",
	  	                  "How to use our interface", JOptionPane.PLAIN_MESSAGE);
	            }
	         }  // end anonymous inner class
	      ); // end call to addActionListener

	      JMenuItem aboutMenuItem = new JMenuItem( "About..." );
	      aboutMenuItem.setMnemonic( 'A' );
	      helpMenu.add( aboutMenuItem );
	      aboutMenuItem.addActionListener(
	         new ActionListener() {  // anonymous inner class
	            // display message dialog when user selects About...
	            public void actionPerformed( ActionEvent event )
	            {
	               JOptionPane.showMessageDialog(Game.this,
	                  "Sudoku, 3rd project of CS342.\n"
	                          + "1. Youlho Cha, ycha8\n"
	                          + "2. Viktor Partyka, vparty2\n"
	                          + "3. Saurav Das, sdas31\n",
	                  "About", JOptionPane.PLAIN_MESSAGE);
	            }
	         }  // end anonymous inner class
	      ); // end call to addActionListener

	      menubar.add(helpMenu);

	      JMenu hintsMenu = new JMenu( "Hints" );

	      checkMenuItem = new  JRadioButtonMenuItem ("Check on Fill");
	      hintsMenu.add( checkMenuItem );
	      checkMenuItem.addActionListener(this);

	      singleMenuItem = new  JRadioButtonMenuItem ("Single Algorithm");
	      hintsMenu.add( singleMenuItem );
	      singleMenuItem.addActionListener(this);

	      hiddensingleMenuItem = new  JRadioButtonMenuItem ("Hidden Single Algorithm");
	      hintsMenu.add( hiddensingleMenuItem );
	      hiddensingleMenuItem.addActionListener(this);

	      lockedcandidateMenuItem = new  JRadioButtonMenuItem ("Locked candidate Algorithm");
	      hintsMenu.add( lockedcandidateMenuItem );
	      lockedcandidateMenuItem.addActionListener(this);

	      nakedpairMenuItem = new  JRadioButtonMenuItem ("Naked pair Algorithm");
	      hintsMenu.add( nakedpairMenuItem );
	      nakedpairMenuItem.addActionListener(this);
	      
	      allAlgorithmMenuItem = new  JRadioButtonMenuItem ("Apply all Algorithm");
	      hintsMenu.add( allAlgorithmMenuItem );
	      allAlgorithmMenuItem.addActionListener(this);

	      ButtonGroup btngrp = new ButtonGroup();
	      btngrp.add(checkMenuItem);
	      btngrp.add(singleMenuItem);
	      btngrp.add(hiddensingleMenuItem);
	      btngrp.add(lockedcandidateMenuItem);
	      btngrp.add(nakedpairMenuItem);
	      btngrp.add(allAlgorithmMenuItem);

	      menubar.add(hintsMenu);

	      setResizable(false);
	      setSize( 600, 400 );

	      setLocationRelativeTo(null);

	      container = new JPanel();

	      //making 3x3 panel
	      panel = new JPanel[3][3];
	      for(int i=0; i<3; i++)
	      {
	    	  for(int j=0; j<3; j++)
	    	  {
	    		  panel[i][j] = new JPanel(new GridLayout(3, 3));
	    	  }
	      }

	      panel1 = new JPanel(new GridLayout(3,3,1,2));
	      panel2 = new JPanel(new GridLayout(10,1));

	      //container is a panel(600x400), consists of another two panels, panel1 & panel2
	      //set the size of two panels
	      panel1.setPreferredSize(new Dimension (550,400));
	      panel2.setPreferredSize(new Dimension (50,400));


	      //adding buttons into the 1st panel
	      buttons1 = new MyButton[ROW][COLUM];

	      //adding buttons into every panels of panel
	      for(int i=0; i<ROW; i++)
	      {
	    	  for(int j=0; j<COLUM; j++)
	    	  {
	    		  buttons1[i][j] = new MyButton(i,j);
	    		  panel[i/3][j/3].add(buttons1[i][j]);
	    	  }
	      }

	      //adding buttons into the 2nd panel
	      buttons2 = new JButton[10][1];
	      for(int i=0; i<10; i++)
	      {
	    	  for(int j=0; j<1; j++)
	    	  {
	    		  buttons2[i][j] = new JButton();
	    		  panel2.add(buttons2[i][j]);
	    	  }
	      }

	      //set the values of the buttons of 1st panel
	      for(int i=0; i<Rows.size(); i++)
	      {
	    	  //these values come from the text file
	    	  buttons1[Rows.get(i)-1][Colums.get(i)-1].setText(Values.get(i));
	    	  //change the color of those buttons
	    	  buttons1[Rows.get(i)-1][Colums.get(i)-1].setBackground(Color.LIGHT_GRAY);
	      }

	      //add action
	      for(int i=0; i<ROW; i++)
	      {
	    	  int p = 1;
	    	  for(int j=0; j<COLUM; j++)
	    	  {
	    		  for(int k=0; k<Rows.size(); k++)
	    		  {
	    			  if((i == (Rows.get(k) - 1)) && (j == (Colums.get(k) - 1)))
		    		  {
	    				  p = 0;
		    			  break;
		    		  }
	    		  }
	    		  if(p==1) {buttons1[i][j].addActionListener(new buttonHandler());}
	    		  p = 1;
	    	  }
	      }

	      //set the values of the buttons of 2nd panel
	      for(int i=0; i<10; i++)
	      {
	    	  String x = Integer.toString(i+1);
	    	  buttons2[i][0].setText(x);
	    	  buttons2[i][0].addActionListener(new buttonHandler2());
	      }
	      //adding image for eraser button
	      buttons2[9][0].setIcon(new ImageIcon(getClass().getResource("/eraser.png")));

	      //adding 3x3 panel(total 9 panels) into panel1
	      for(int i=0; i<3; i++)
	      {
	    	  for(int j=0; j<3; j++)
	    	  {
	    		  panel1.add(panel[i][j]);
	    	  }
	      }

	      //adding two panels into another panel, container
	      container.add(panel1);
	      container.add(panel2);
	      
	      //adding panel(container) into the frame
	      add(container);

	      getCandidate();
	      
	      pack();
	      setVisible( true );
		  setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
	
	public void getCandidate()
	{
		 objectButtons = new ArrayList<MyButton>();
		 for(int i=0; i<9; i++)
    	 {
    		 for(int j=0; j<9; j++)
    		 {
    			 //make sure the button is empty
    			 if(buttons1[i][j].getText().equals(""))
    			 {
    				 objectButtons.add(UpdateCandidate(i,j));
    			 }
    		 }
    	 }
	}
	
	public void actionPerformed(ActionEvent e)
	{
		 if(checkMenuItem.isSelected())
	     {
	    	checkonfill = ! checkonfill;
	     }
	     else if(singleMenuItem.isSelected())
	     {
	    	 System.out.println("Single Algorithm");
	    	 
	    	 for(int i=0; i<9; i++)
	    	 {
	    		 for(int j=0; j<9; j++)
	    		 {
	    			 //make sure the button is empty
	    			 if(buttons1[i][j].getText().equals(""))
	    			 {
	    				 int value = SingleAlgorithm(i,j);
	    				 //System.out.println(value);
	    				 if(value != 0)
	    				 {
	    					 buttons1[i][j].setText(String.valueOf(value));
	    					 return;
	    				 }
	    			 }
	    		 }
	    	 }
	     }
	     else if(hiddensingleMenuItem.isSelected())
	     {
	    	 System.out.println("Hidden Single Algorithm");
	    	 HiddenSingle();
	     }
	     else if(lockedcandidateMenuItem.isSelected())
	     {
	    	 System.out.println("Locked Candidate Algorithm");
	    	 getCandidate();
	    	 LockedCandidate();
	     }
	     else if(nakedpairMenuItem.isSelected())
	     {
	    	 System.out.println("Naked Pair Algorithm");
	    	 getCandidate();
	    	 NakedPair();
	    	 
	     }//end naked
	     else if(allAlgorithmMenuItem.isSelected())
	     {
	    	 AutoSolve();
	     }
	}
	
	class buttonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			MyButton temp = (MyButton) e.getSource(); //temp is an instance of clicked Button
			
			if(isClicked)
			{
				if(click.equals("10")) {
					temp.setText("");
				}
				else
				{
					//if user selects check on fill then
					// and the user attempts to place a digit into a cell
					//that is NOT in the cells current candidate list,
					// an error message will be given and the digit is not placed into the cell
					if(checkonfill == true)
					{
						int _row = temp.getR();
						int _col = temp.getC();
						getCandidate();
						for(int i=0; i<objectButtons.size(); i++)
						{
							if(_row == objectButtons.get(i).getR() && _col == objectButtons.get(i).getC())
							{
								if(objectButtons.get(i).getCandidates().contains(click))
								{
									temp.setText(click);
									break;
								}
								else
								{
									JOptionPane.showMessageDialog(Game.this,
							                  "You can't put '"+click+"' this cell",
							                  "ERROR!!!", JOptionPane.ERROR_MESSAGE);
									break;
								}
							}
						}
					}
					else
					{temp.setText(click);}
				}
				isClicked = false;
				
				//check the sudoku every time is solved or not when user click on the board
				boolean isEmpty = false;
				for(int i=0; i<9; i++)
				{
					for(int j=0; j<9; j++)
					{
						if(buttons1[i][j].getText() == "")
						{
							isEmpty = true; break;
						}
					}
				}
				//there is no empty cell so we should check if user win or not
				if(isEmpty == false)
				{
					IsSolved();
				}
			}
		}
	}
	
	class buttonHandler2 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			isClicked = true;
			click = e.getActionCommand();
			//System.out.println(e.getActionCommand());
		}
	}
	
	public void Store() throws IOException
	{
		String[] lines = new String[100];
		int counter = 0;
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				if(buttons1[i][j].getText()!="")
				{
					lines[counter] = Integer.toString(i+1)+" "+Integer.toString(j+1)+" "+buttons1[i][j].getText();
					counter++;
				}
			}
		}
		try
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory( new File( "./") );
			int actionDialog = chooser.showSaveDialog(this);
			if ( actionDialog == JFileChooser.APPROVE_OPTION )
			{
				File fileName = new File( chooser.getSelectedFile( ) + ".txt" );
				if(fileName != null)
				{
					if(fileName.exists())
					{
						actionDialog = JOptionPane.showConfirmDialog(this, "Replace existing file?");
						while (actionDialog == JOptionPane.NO_OPTION)
						{
							actionDialog=chooser.showSaveDialog(this);
							if (actionDialog == JFileChooser.APPROVE_OPTION)
							{
								fileName = new File( chooser.getSelectedFile( ) + ".txt" );
								if(fileName.exists())
								{
									actionDialog = JOptionPane.showConfirmDialog(this, "Replace existing file?");
								}
							}
						 }
						 if(actionDialog == JOptionPane.YES_OPTION)
						 {
							 BufferedWriter outFile = new BufferedWriter( new FileWriter( fileName ) );
							 for(int i=0; i<counter; i++)
						     {
						        outFile.write(lines[i]);
						        outFile.write("\n");
						     }
							 outFile.flush( ); // redundant, done by close()
							 outFile.close( );
						 }
						 return;
			        }//exist
			        BufferedWriter outFile = new BufferedWriter( new FileWriter( fileName ) );
			        for(int i=0; i<counter; i++)
			        {
			        	outFile.write(lines[i]);
			        	outFile.write("\n");
			        }
			        
			        outFile.flush( ); // redundant, done by close()
			        outFile.close( );
				}
		   }
		}
		//catches nullpointer exception, file not found
		catch(NullPointerException ex)
		{
			
		}
	}
	
	public MyButton UpdateCandidate(int row, int colum)
	{
		ArrayList<String> Value = new ArrayList<String>();
		ArrayList<String> Candidate = new ArrayList<String>();
		JPanel box = (JPanel) buttons1[row][colum].getParent();
		
		//getting all values of that particular box/panel
		Value = searchBox(box);
		
		//getting all values of that particular row
		for(int k=0; k<9; k++)
	   	{
			if(buttons1[row][k].getText()!="")
				Value.add(buttons1[row][k].getText());
	   	}
		
		//getting all values of that particular colum
		for(int k=0; k<9; k++)
		{
			if(buttons1[k][colum].getText()!="")
				Value.add(buttons1[k][colum].getText());
		}
		MyButton button = new MyButton(row, colum,(JPanel) buttons1[row][colum].getParent());
		

	   	for(int i=0; i<9; i++)
	   	{
   			 String x = Integer.toString(i+1);
   			 if(!Value.contains(x))
   			 {
   				button.setList(x);
   			 }
	   	}
	   	//button.show();
	   	return button;
	}
	
	public void AutoSolve()
	{
		for(int a=0; a<81; a++)
	   	{
	   		 //applying Single Algorithm
	   		 for(int i=0; i<9; i++)
		    	 {
		    		 for(int j=0; j<9; j++)
		    		 {
		    			 //make sure the button is empty
		    			 if(buttons1[i][j].getText().equals(""))
		    			 {
		    				 int value = SingleAlgorithm(i,j);
		    				 //System.out.println(value);
		    				 if(value != 0)
		    				 {
		    					 buttons1[i][j].setText(String.valueOf(value));
		    				 }
		    			 }
		    		 }
		    	 }
	   		 
	   		 //applying Hidden Single algorithm
	   		 HiddenSingle();
	   		 
	   		 //applying locked candidate
	   	     getCandidate();
		     LockedCandidate();
	   		
	   		 //applying naked pair
	   		 getCandidate();
		     NakedPair();
	    	 
	   		 //checking is there any empty cell
	   		 boolean isempty = false;
	   		 for(int i=0; i<9; i++)
	   		 {
	   			 for(int j=0; j<9; j++)
	   			 {
	   				 if(buttons1[i][j].getText() == "") //empty cell
	   				 {
	   					 isempty = true;break;
	   				 }
	   			 }
	   		 }
	   		 if(isempty == false) //no empty cell, we're done
	   		 {
	   			 IsSolved();
	   		 }
	   		 //System.out.println("ALL " + a);
	   	 }
	}
	
    public void IsSolved()
	{
		//check there is no repetition
		//first check inside every box
		for(int i=0; i<9; i=i+3)
		{
			for(int j=0; j<9; j=j+3)
			{
				 ArrayList<String> list = new ArrayList<String>();
				 JPanel box = (JPanel) buttons1[i][j].getParent();
				 Component[] b = box.getComponents();
				 for(int k = 0; k < b.length; k++)
				 {
					 JButton x = (JButton) b[k];
					 //add all values of that particular box
					 list.add(x.getText());
				 }
				 //then check that particular box contains 1-9, only once
				 for(int k=1; k<=9; k++)
				 {
					 String key = Integer.toString(k);
					 //if doesn't contain then sudoku is not solved yet, so return
					 if(!list.contains(key))
					 {
						 return;
					 }
				 }
			}
		}
		//secondly check all 9 rows
		for(int i=0; i<9; i++)
		{
			ArrayList<String> list = new ArrayList<String>();
			for(int j=0; j<9; j++)
			{
				//add all values of that particular row
				list.add(buttons1[i][j].getText());
			}
			//then check that particular row contains 1-9, only once
			 for(int k=1; k<=9; k++)
			 {
				 String key = Integer.toString(k);
				 //if doesn't contain then sudoku is not solved yet, so return
				 if(!list.contains(key))
				 {
					 return;
				 }
			 }
		}
		//finally check all 9 columns
		for(int i=0; i<9; i++)
		{
			ArrayList<String> list = new ArrayList<String>();
			for(int j=0; j<9; j++)
			{
				//add all values of that particular row
				list.add(buttons1[j][i].getText());
			}
			//then check that particular column contains 1-9, only once
			 for(int k=1; k<=9; k++)
			 {
				 String key = Integer.toString(k);
				 //if doesn't contain then sudoku is not solved yet, so return
				 if(!list.contains(key))
				 {
					 return;
				 }
			 }
		}
		JOptionPane.showMessageDialog(Game.this,
                  "CONGRATS, You solve the sudoku !!.\n",
                  "SOLVED !!!", JOptionPane.PLAIN_MESSAGE);
			 System.exit(0);
	}
    
    public int SingleAlgorithm(int row, int colum)
	{
		ArrayList<String> Value = new ArrayList<String>();
		JPanel box = (JPanel) buttons1[row][colum].getParent();
		
		//getting all values of that particular box/panel
		Value = searchBox(box);
		
		//getting all values of that particular row
		for(int k=0; k<9; k++)
	   	{
			if(buttons1[row][k].getText()!="")
				Value.add(buttons1[row][k].getText());
	   	}
		
		//getting all values of that particular colum
		for(int k=0; k<9; k++)
		{
			if(buttons1[k][colum].getText()!="")
				Value.add(buttons1[k][colum].getText());
		}
		
	   	int counter = 0;
	   	int val = 0;
	   	
	   	for(int i=0; i<9; i++)
	   	{
   			 String x = Integer.toString(i+1);
   			 if(!Value.contains(x))
   			 {
   				 counter++;
   				 val = i+1;
   				 if(counter == 2)
   					return 0;
   			 }
	   	}
	   	
	   	return val;
	}

	public ArrayList<String> searchBox(JPanel box)
	{ 
		 ArrayList<String> Value = new ArrayList<String>();
		 Component[] b = box.getComponents();
		 for(int i = 0; i < b.length; i++)
		 {
			 JButton x = (JButton) b[i];
			 if(x.getText()!="")
				 Value.add(x.getText());
		 }
   		
   	 return Value;
	}
	
    public void LockedCandidate()
    {
    	 int flagrow = 0;
    	 int flagcolumn = 0;
    	 for(int i = 0; i<objectButtons.size(); i++)
    	 {
    		 int r = objectButtons.get(i).getR();
    		 int c = objectButtons.get(i).getC();
    		 ArrayList<String> l = objectButtons.get(i).getCandidates();
    		 JPanel b = objectButtons.get(i).getBox();
    		 for(int k = 0; k < l.size(); k++)
    		 { 		 
	    			 for(int j = 0; j<objectButtons.size(); j++)
			    	 {
	    				 if(r == objectButtons.get(j).getR() && b != objectButtons.get(j).getBox())
			    		 {
			    			
		    				 if(objectButtons.get(j).getCandidates().contains(l.get(k)))
		    				 {
		    					flagrow = 1;
		    				 }
			    		 }
	    			 
			    	 }
	    	
	    			 if(flagrow == 0)
		    		 {
		    			 for(int m = 0; m<objectButtons.size(); m++)
				    	 {
							 if(r != objectButtons.get(m).getR() && b == objectButtons.get(m).getBox())
				    		 {
								 objectButtons.get(m).removeCandidate(l.get(k));
				    		 }
				    	 }
		    		 }
	    			 
	    			 flagrow =0;
	    	 }
    		 
    		 for(int k = 0; k < l.size(); k++)
    		 { 		 
	    			 for(int j = 0; j<objectButtons.size(); j++)
			    	 {
	    				 if(c == objectButtons.get(j).getC() && b != objectButtons.get(j).getBox())
			    		 {
			    			
		    				 if(objectButtons.get(j).getCandidates().contains(l.get(k)))
		    				 {
		    					flagcolumn = 1;
		    				 }
			    		 }
	    			 
			    	 }
	    	
	    			 if(flagcolumn == 0)
		    		 {
		    			 for(int m = 0; m<objectButtons.size(); m++)
				    	 {
							 if(c != objectButtons.get(m).getC() && b == objectButtons.get(m).getBox())
				    		 {
								 objectButtons.get(m).removeCandidate(l.get(k));
				    		 }
				    	 }
		    		 }
	    			 
	    			 flagcolumn =0;
    		 }
    		 
    	 }
    	 for(int i = 0; i < objectButtons.size(); i++)
    	 {
    		if(objectButtons.get(i).getS()==1)
    		{
    			 buttons1[objectButtons.get(i).getR()][objectButtons.get(i).getC()].setText(objectButtons.get(i).getCandidates().get(0));
    		}
    		//objectButtons.get(i).show();
    	 }
    }
    
   public void NakedPair()
   {
	     for(int i = 0; i<objectButtons.size(); i++)
		 {
			 
			 int r = objectButtons.get(i).getR();
			 int c = objectButtons.get(i).getC();
			 ArrayList<String> l = objectButtons.get(i).getCandidates();
			 JPanel b = objectButtons.get(i).getBox();
			 if(objectButtons.get(i).getS() == 2)
			 { 
		    	 for(int j = 0; j<objectButtons.size(); j++)
		    	 {
		    		 int r2 = objectButtons.get(j).getR();
		    		 int c2 = objectButtons.get(j).getC();
		    		 JPanel b2 = objectButtons.get(j).getBox();
		    		 
	   			 if(c != objectButtons.get(j).getC() && r==objectButtons.get(j).getR() && objectButtons.get(j).getS()== 2)
	   			 {		    				 
		    			 if(objectButtons.get(j).getCandidates().contains(l.get(0)) && objectButtons.get(j).getCandidates().contains(l.get(1)))
		    			 {
		    					 for(int k = 0; k<objectButtons.size(); k++)
		    			    	 {
		    						 if(r == objectButtons.get(k).getR()&&c != objectButtons.get(k).getC() && objectButtons.get(j).getC() != objectButtons.get(k).getC())
		    						 {
		    							 objectButtons.get(k).removeCandidate(l.get(0));
		    							 objectButtons.get(k).removeCandidate(l.get(1));
		    						 }
		    			    	 }
		  				 }
		  			 }
	   			 
	   			 //======colum============
	   			 
	   			 if(c == objectButtons.get(j).getC() && r!=objectButtons.get(j).getR() && objectButtons.get(j).getS()== 2)
	   			 {		    				 
		    			 if(objectButtons.get(j).getCandidates().contains(l.get(0)) && objectButtons.get(j).getCandidates().contains(l.get(1)))
		    			 {
		    					 for(int k = 0; k<objectButtons.size(); k++)
		    			    	 {
		    						 if(r != objectButtons.get(k).getR() && c == objectButtons.get(k).getC() && objectButtons.get(j).getR() != objectButtons.get(k).getR())
		    						 {
		    							 objectButtons.get(k).removeCandidate(l.get(0));
		    							 objectButtons.get(k).removeCandidate(l.get(1));
		    						 }
		    			    	 }
		  				 }
		  			 }
	   			 
	   			 //=====box================
	   			 if(b == b2) { 
		    			 if(r!=objectButtons.get(j).getR()|| c!=objectButtons.get(j).getC() && objectButtons.get(j).getS()== 2)
		    			 {		    				 
			    			 if(objectButtons.get(j).getCandidates().contains(l.get(0)) && objectButtons.get(j).getCandidates().contains(l.get(1)))
			    			 {
			    					 for(int k = 0; k<objectButtons.size(); k++)
			    			    	 {
			    						 if(b2== objectButtons.get(k).getBox()) {
				    						 if(r != objectButtons.get(k).getR()&& c != objectButtons.get(k).getC())
				    						 {
				    							 if(c2 != objectButtons.get(k).getC()&& r2 != objectButtons.get(k).getR())
				    							 {
				    								 objectButtons.get(k).removeCandidate(l.get(0));
				    								 objectButtons.get(k).removeCandidate(l.get(1));
				    							 }
				    						 }
			    						 }
			    			    	 }
			  				 }
			  			 }
	   			 }
		   		 }
		   	 }
		 }
		 
		 for(int i = 0; i < objectButtons.size(); i++)
		 {
			if(objectButtons.get(i).getS()==1)
			{
				 buttons1[objectButtons.get(i).getR()][objectButtons.get(i).getC()].setText(objectButtons.get(i).getCandidates().get(0));
			}
			//objectButtons.get(i).show();
		 }
   }
    
	public void Load()
	{
		try
		{
			this.Rows = new ArrayList<Integer>();
			this.Colums = new ArrayList<Integer>();
			this.Values = new ArrayList<String>();
			
			JFileChooser loadEmp = new JFileChooser();//new dialog
			File selectedFile;
			BufferedReader in;
			FileReader reader = null;
			
			//opens dialog if button clicked
			if (loadEmp.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
			{
				//getting file from dialog
				selectedFile = loadEmp.getSelectedFile();
				
				//make sure files can be processed before proceeding
				if (selectedFile.canRead() && selectedFile.exists())
				{
					//make sure user selects only .txt file
					if(selectedFile.getName().endsWith(".txt"))
					{
						reader = new FileReader(selectedFile);
						isLoadOK = true;
					}
					//otherwise give an error message
					else
					{
						 JOptionPane.showMessageDialog(Game.this,
				                  "Select .txt file",
				                  "ERROR!!!", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			
			in = new BufferedReader(reader);
			
			//inputLine receives file text
			String inputLine = in.readLine();
			
			int row;
			int col;
			int val;//no need
			
			while(inputLine!=null)
			{
				StringTokenizer tokenizer = new StringTokenizer(inputLine);
				
				//displays text file
				//System.out.println(inputLine + "\n");
				
				//taking string values which is separated by space
				String line[] = inputLine.split(" ");
				
				//parsing into integer
	            row = Integer.parseInt(line[0]);
	            col = Integer.parseInt(line[1]);
	            
	            //adding into the array list
	            this.Rows.add(row);
	            this.Colums.add(col);
	            this.Values.add(line[2]);
	            
				//next line in File opened
				inputLine = in.readLine();
			}
			//close stream, files stops loading
			in.close();
		}
		catch(IOException ex)
		{
		
		}
		//catches nullpointer exception, file not found
		catch(NullPointerException ex)
		{
			
		}
	}
	
	public ArrayList<String> candidateList(int row, int colum)
	{
		//ArrayList<String> possibleValue = new ArrayList<String>();
				ArrayList<String> Value = new ArrayList<String>();
				ArrayList<String> candidateList = new ArrayList<String>();

				JPanel box = (JPanel) buttons1[row][colum].getParent();
				Value = searchBox(box);
				//System.out.println(Value.size());
				//getting all values of that particular row
				for(int k=0; k<9; k++)
			   	{
					if(buttons1[row][k].getText()!="")
						Value.add(buttons1[row][k].getText());
			   	}

				//getting all values of that particular colum
				for(int k=0; k<9; k++)
				{
					if(buttons1[k][colum].getText()!="")
						Value.add(buttons1[k][colum].getText());
					
				}
				

				//	Collections.sort(Value);
				
				for(int k=1; k<10; k++)
				{
			    	  String x = Integer.toString(k);
					if((Value.contains(x))==false){
						if((candidateList.contains(x))==false)
							candidateList.add(x);
					}
				}

				
				return candidateList;
	}
	
	public void HiddenSingle()
	{
		ArrayList<ArrayList<String>> candList = new ArrayList<ArrayList<String>>();
	    ArrayList<String> innerList = new ArrayList<String>();   
	    
	    int count = 0;
	    int cnt = 0;
	    for(int x = 0; x <3; x++)
			for(int y=0 ; y<3; y++)
			{   
				if(buttons1[x][y].getText() ==""){
					innerList = candidateList(x,y);
					candList.add(innerList);
					
				}
			}
	    
		for(int j=1; j<10; j++)
    	{
			
			 String x = Integer.toString(j);
			
		    for(int i =0; i < candList.size(); i++)
		    {
	    		if(candList.get(i).contains(x))
	    			count ++;
	    	}
	    	if(count == 1){
	    		
	    		for(int i =0; i < candList.size(); i++)
			    {	
		    		if(candList.get(i).contains(x))
		    		{	    	
		    			for(int row = 0; row <3; row++)
		    			{
		    				for(int col=0 ; col<3; col++)
		    				{   
		    					if(buttons1[row][col].getText() ==""){
		    						cnt++;
		    					}
		    					if(cnt-1 ==i)
		    					{
		    						System.out.println("Row: "+row+", Col: "+col+ ", Changed to : "+ j);
		    						buttons1[row][col].setText(String.valueOf(j));
			    					 //setVisible(true);
			    					 return;
		    					}
		    					
		    				}
		    		
		    			}
		    		}
		    			//System.out.println("zeroth: button: "+i+"Found: "+x);
		    	}
	    	}
	    	count = 0;
	    	cnt = 0;

	    }
	    innerList.clear();
	    candList.clear();
	    //==========================================================
	    for(int x = 3; x <6; x++)
			for(int y=0 ; y<3; y++)
			{   
				if(buttons1[x][y].getText() ==""){
					innerList = candidateList(x,y);
					candList.add(innerList);		
				}
			}
		for(int j=1; j<10; j++)
    	{
			 String x = Integer.toString(j);

		    for(int i =0; i < candList.size(); i++)
		    {
	    		if(candList.get(i).contains(x))
	    			count ++;
	    	}
	    	if(count == 1){
	    		for(int i =0; i < candList.size(); i++)
			    {
		    		if(candList.get(i).contains(x))
		    		{
		    			for(int row = 3; row <6; row++)
		    				for(int col=0 ; col<3; col++)
		    				{   
		    					if(buttons1[row][col].getText() ==""){
		    						cnt++;
		    					}
		    					if(cnt-1 ==i)
		    					{
		    						System.out.println("Row: "+row+", Col: "+col+ ", Changed to : "+ j);
		    						buttons1[row][col].setText(String.valueOf(j));
			    					 //setVisible(true);
			    					 return;
		    					}
		    					
		    				}
		    		}
			    }
	    	}
	    	count = 0;
	    	cnt = 0;

	    }
	    innerList.clear();
	    candList.clear();
	  //==========================================================
	    for(int x = 6; x <9; x++)
			for(int y=0 ; y<3; y++)
			{   
				if(buttons1[x][y].getText() ==""){
					innerList = candidateList(x,y);
					candList.add(innerList);		
				}
			}
		for(int j=1; j<10; j++)
    	{
			 String x = Integer.toString(j);

		    for(int i =0; i < candList.size(); i++)
		    {
	    		if(candList.get(i).contains(x))
	    			count ++;
	    	}
	    	if(count == 1){
	    		for(int i =0; i < candList.size(); i++)
			    {
		    		if(candList.get(i).contains(x))
		    		{
		    			for(int row = 6; row <9; row++)
		    				for(int col=0 ; col<3; col++)
		    				{   
		    					if(buttons1[row][col].getText() ==""){
		    						cnt++;
		    					}
		    					if(cnt-1==i)
		    					{
		    						System.out.println("Row: "+row+", Col: "+col+ ", Changed to : "+ j);
		    						buttons1[row][col].setText(String.valueOf(j));
			    					 //setVisible(true);
			    					 return;
		    					}
		    					
		    				}
		    		}
		    	}
	    	}
	    	count = 0;
	    	cnt = 0;
	    }
	    innerList.clear();
	    candList.clear();
	  //==========================================================
	    for(int x = 0; x <3; x++)
			for(int y=3 ; y<6; y++)
			{   
				if(buttons1[x][y].getText() ==""){
					innerList = candidateList(x,y);
					candList.add(innerList);		
				}
			}
		for(int j=1; j<10; j++)
    	{
			 String x = Integer.toString(j);
		    for(int i =0; i < candList.size(); i++)
		    {
	    		if(candList.get(i).contains(x))
	    			count ++;
	    	}
	    	if(count == 1){
	    		for(int i =0; i < candList.size(); i++)
			    {
		    		if(candList.get(i).contains(x))
		    		{	
		    			for(int row = 0; row <3; row++)
		    				for(int col=3 ; col<6; col++)
		    				{   
		    					if(buttons1[row][col].getText() ==""){
		    						cnt++;
		    					}
		    					if(cnt-1==i)
		    					{
		    						System.out.println("Row: "+row+", Col: "+col+ ", Changed to : "+ j);
		    						buttons1[row][col].setText(String.valueOf(j));
			    					 //setVisible(true);
			    					 return;
		    					}
		    					
		    				}
		    		}
		    	}
	    	}
	    	count = 0;
	    	cnt = 0;
	    }
	    innerList.clear();
	    candList.clear();
	  //==========================================================
	    for(int x = 3; x <6; x++)
			for(int y=3 ; y<6; y++)
			{   
				if(buttons1[x][y].getText() ==""){
					innerList = candidateList(x,y);
					candList.add(innerList);		
				}
			}
		for(int j=1; j<10; j++)
    	{
			 String x = Integer.toString(j);
		    for(int i =0; i < candList.size(); i++)
		    {
	    		if(candList.get(i).contains(x))
	    			count ++;
	    	}
	    	if(count == 1){
	    		for(int i =0; i < candList.size(); i++)
			    {
		    		if(candList.get(i).contains(x))
		    		{
		    			for(int row = 3; row <6; row++)
		    				for(int col=3 ; col<6; col++)
		    				{   
		    					if(buttons1[row][col].getText() ==""){
		    						cnt++;
		    					}
		    					if(cnt-1==i)
		    					{
		    						System.out.println("Row: "+row+", Col: "+col+ ", Changed to : "+ j);
		    						buttons1[row][col].setText(String.valueOf(j));
			    					 //setVisible(true);
			    					 return;
		    					}
		    					
		    				}
		    		}
		    	}
	    	}
	    	count = 0;
	    	cnt = 0;
	    }
	    innerList.clear();
	    candList.clear();
	  //==========================================================
	    for(int x = 6; x <9; x++)
			for(int y=3 ; y<6; y++)
			{   
				if(buttons1[x][y].getText() ==""){
					innerList = candidateList(x,y);
					candList.add(innerList);		
				}
			}
		for(int j=1; j<10; j++)
    	{
			 String x = Integer.toString(j);
		    for(int i =0; i < candList.size(); i++)
		    {
	    		if(candList.get(i).contains(x))
	    			count ++;
	    	}
	    	if(count == 1){
	    		for(int i =0; i < candList.size(); i++)
			    {
		    		if(candList.get(i).contains(x))
		    		{
		    			for(int row = 6; row <9; row++)
		    				for(int col=3 ; col<6; col++)
		    				{   
		    					if(buttons1[row][col].getText() ==""){
		    						cnt++;
		    					}
		    					if(cnt-1==i)
		    					{
		    						System.out.println("Row: "+row+", Col: "+col+ ", Changed to : "+ j);
		    						buttons1[row][col].setText(String.valueOf(j));
			    					 //setVisible(true);
			    					 return;
		    					}
		    					
		    				}
		    		}
		    	}
	    	}
	    	count = 0;
	    	cnt = 0;
	    }
	    innerList.clear();
	    candList.clear();
	  //==========================================================
	    for(int x = 0; x <3; x++)
			for(int y=6 ; y<9; y++)
			{   
				if(buttons1[x][y].getText() ==""){
					innerList = candidateList(x,y);
					candList.add(innerList);		
				}
			}
		for(int j=1; j<10; j++)
    	{
			 String x = Integer.toString(j);
		    for(int i =0; i < candList.size(); i++)
		    {
	    		if(candList.get(i).contains(x))
	    			count ++;
	    	}
	    	if(count == 1){
	    		for(int i =0; i < candList.size(); i++)
			    {
		    		if(candList.get(i).contains(x))
		    		{
		    			for(int row = 0; row <3; row++)
		    				for(int col=6 ; col<9; col++)
		    				{   
		    					if(buttons1[row][col].getText() ==""){
		    						cnt++;
		    					}
		    					if(cnt-1==i)
		    					{
		    						System.out.println("Row: "+row+", Col: "+col+ ", Changed to : "+ j);
		    						buttons1[row][col].setText(String.valueOf(j));
			    					 //setVisible(true);
			    					 return;
		    					}
		    					
		    				}
		    		}
		    	}
	    	}
	    	count = 0;
	    	cnt = 0;
	    }
	    innerList.clear();
	    candList.clear();
	    
	  //==========================================================
	    for(int x = 3; x <6; x++)
			for(int y=6 ; y<9; y++)
			{   
				if(buttons1[x][y].getText() ==""){
					innerList = candidateList(x,y);
					candList.add(innerList);		
				}
			}
		for(int j=1; j<10; j++)
    	{
			 String x = Integer.toString(j);
		    for(int i =0; i < candList.size(); i++)
		    {
	    		if(candList.get(i).contains(x))
	    			count ++;
	    	}
	    	if(count == 1){
	    		for(int i =0; i < candList.size(); i++)
			    {
		    		if(candList.get(i).contains(x))
		    		{
		    			for(int row = 3; row <6; row++)
		    				for(int col=6 ; col<9; col++)
		    				{   
		    					if(buttons1[row][col].getText() ==""){
		    						cnt++;
		    					}
		    					if(cnt-1==i)
		    					{
		    						System.out.println("Row: "+row+", Col: "+col+ ", Changed to : "+ j);
		    						buttons1[row][col].setText(String.valueOf(j));
			    					 //setVisible(true);
			    					 return;
		    					}
		    					
		    				}
		    		}
		    	}
	    	}
	    	count = 0;
	    	cnt = 0;
	    }
	    innerList.clear();
	    candList.clear();
	    //==========================================================
	    for(int x = 6; x <9; x++)
			for(int y=6 ; y<9; y++)
			{   
				if(buttons1[x][y].getText() ==""){
					innerList = candidateList(x,y);
					candList.add(innerList);		
				}
			}
		for(int j=1; j<10; j++)
    	{
			 String x = Integer.toString(j);
		    for(int i =0; i < candList.size(); i++)
		    {
	    		if(candList.get(i).contains(x))
	    			count ++;
	    	}
	    	if(count == 1){
	    		for(int i =0; i < candList.size(); i++)
			    {
		    		if(candList.get(i).contains(x))
		    		{
		    			for(int row = 6; row <9; row++)
		    				for(int col=6 ; col<9; col++)
		    				{   
		    					if(buttons1[row][col].getText() ==""){
		    						cnt++;
		    					}
		    					if(cnt-1==i)
		    					{
		    						System.out.println("Row: "+row+", Col: "+col+ ", Changed to : "+ j);
		    						buttons1[row][col].setText(String.valueOf(j));
			    					 //setVisible(true);
			    					 return;
		    					}
		    					
		    				}
		    		}
		    	}
	    	}
	    	count = 0;
	    	cnt = 0;
    	}
	    innerList.clear();
	    candList.clear();
	    
	    //==============for every row==============================================
	    for(int i = 0 ; i <9; i++)
	    {
	    	for(int j= 0; j <9; j++)
	    	{
	    		if(buttons1[i][j].getText() ==""){
					innerList = candidateList(i,j);
					candList.add(innerList);		
				}
	    	}
	    
	    	for(int p=1; p<10; p++)
	        {
	    			 String x = Integer.toString(p);
	    		    for(int q =0; q < candList.size(); q++)
	    		    {
	    	    		if(candList.get(q).contains(x))
	    	    			count ++;
	    	    	}
	    	    	if(count == 1){
	    	    		for(int q =0; q < candList.size(); q++)
	    			    {
	    		    		if(candList.get(q).contains(x))
	    		    		{
	    		    				for(int col=0 ; col<9; col++)
	    		    				{   
	    		    					if(buttons1[i][col].getText() ==""){
	    		    						cnt++;
	    		    					}
	    		    					if(cnt-1==q)
	    		    					{
	    		    						System.out.println("Row: "+i+", Col: "+col+ ", Changed to : "+ p);
	    		    						buttons1[i][col].setText(String.valueOf(p));
	    			    					 //setVisible(true);
	    			    					 return;
	    		    					}	
	    		    				}
	    		    		}
	    		    	}
	    	    	}
	    	    	count = 0;
	    	    	cnt = 0;

	    	  }

		    innerList.clear();
		    candList.clear();	
	    }
	    
	    //==============for every cols==============================================
	    for(int j = 0 ; j <9; j++)
	    {
	    	for(int i= 0; i <9; i++)
	    	{
	    		if(buttons1[i][j].getText() ==""){
					innerList = candidateList(i,j);
					candList.add(innerList);		
				}
	    	}
	    
	    	for(int p=1; p<10; p++)
	        {
	    			 String x = Integer.toString(p);
	    		    for(int q =0; q < candList.size(); q++)
	    		    {
	    	    		if(candList.get(q).contains(x))
	    	    			count ++;
	    	    	}
	    	    	if(count == 1){
	    	    		for(int q =0; q < candList.size(); q++)
	    			    {
	    		    		if(candList.get(q).contains(x))
	    		    		{
	    		    			for(int row=0 ; row<9; row++)
    		    				{   
    		    					if(buttons1[row][j].getText() ==""){
    		    						cnt++;
    		    					}
    		    					if(cnt-1==q)
    		    					{
    		    						System.out.println("Row: "+row+", Col: "+j+ ", Changed to : "+ p);
    		    						buttons1[row][j].setText(String.valueOf(p));
    			    					 //setVisible(true);
    			    					 return;
    		    					}
    		    					
    		    				}
	    		    		}
	    		    	}
	    	    	}
	    	    	count = 0;
	    	    	cnt = 0;
	    	  }

		    innerList.clear();
		    candList.clear();	
	    }
   }
}
