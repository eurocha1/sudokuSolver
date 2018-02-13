import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;

public class Main extends JFrame implements ActionListener{
    static Main main=null;
	private boolean isLoadOK = false;
	private JRadioButtonMenuItem checkMenuItem;
	private JRadioButtonMenuItem singleMenuItem;
	private JRadioButtonMenuItem hiddensingleMenuItem;
	private JRadioButtonMenuItem lockedcandidateMenuItem;
	private JRadioButtonMenuItem nakedpairMenuItem;
	private ArrayList<Integer> Rows;
	private ArrayList<Integer> Colums;
	private ArrayList<String> Values;
	private JLabel displayLabel;
	
	public Main()
	{
		this.Frame();
	}
	
	public void Frame()
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
	               //call load method and read file
	               Load();
	               
	               //does user select any file? if so is it .txt file?
	               if(isLoadOK == true)
	               {
		               //done with this frame
		               setVisible(false);
		               
		               //call Game class, Game class gives us new JFrame which frame contains data 
		               //passing object of Main class so the data won't change
	            	   Game game = new Game(main);
	               }
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
	            	JOptionPane.showMessageDialog(Main.this,
			                  "Current Sudoku board is empty",
			                  "ERROR!!!", JOptionPane.ERROR_MESSAGE);
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
	               JOptionPane.showMessageDialog(Main.this,
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
	               JOptionPane.showMessageDialog(Main.this,
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
	               JOptionPane.showMessageDialog(Main.this,
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
	      
	      ButtonGroup btngrp = new ButtonGroup();
	      btngrp.add(checkMenuItem);
	      btngrp.add(singleMenuItem);
	      btngrp.add(hiddensingleMenuItem);
	      btngrp.add(lockedcandidateMenuItem);
	      btngrp.add(nakedpairMenuItem);
	      
	      menubar.add(hintsMenu);
	      
	      // set up label to display text
	      displayLabel = new JLabel( "SUDOKU", SwingConstants.CENTER );
	      getContentPane().setBackground( Color.CYAN );
	      displayLabel.setFont( new Font( "Serif", Font.PLAIN, 72 ) );
	      getContentPane().add( displayLabel, BorderLayout.CENTER );
	      
	      setResizable(false);
	      setSize( 400, 400 );
	      setLocationRelativeTo(null);
		  setVisible( true );
		  setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		  
	      if(checkMenuItem.isSelected())
	      {
	    	  JOptionPane.showMessageDialog(Main.this,
	                  "Current Sudoku board is empty",
	                  "ERROR!!!", JOptionPane.ERROR_MESSAGE);
	      }
	      else if(singleMenuItem.isSelected())
	      {
	    	  JOptionPane.showMessageDialog(Main.this,
	                  "Current Sudoku board is empty",
	                  "ERROR!!!", JOptionPane.ERROR_MESSAGE);
	      }
	      else if(hiddensingleMenuItem.isSelected())
	      {
	    	  JOptionPane.showMessageDialog(Main.this,
	                  "Current Sudoku board is empty",
	                  "ERROR!!!", JOptionPane.ERROR_MESSAGE);
	      }
	      else if(lockedcandidateMenuItem.isSelected())
	      {
	    	  JOptionPane.showMessageDialog(Main.this,
	                  "Current Sudoku board is empty",
	                  "ERROR!!!", JOptionPane.ERROR_MESSAGE);
	      }
	      else if(nakedpairMenuItem.isSelected())
	      {
	    	  JOptionPane.showMessageDialog(Main.this,
	                  "Current Sudoku board is empty",
	                  "ERROR!!!", JOptionPane.ERROR_MESSAGE);
	      }
	}
	
	public void Load()
	{
		try
		{
			Rows = new ArrayList<Integer>();
			Colums = new ArrayList<Integer>();
			Values = new ArrayList<String>();
			
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
						 JOptionPane.showMessageDialog(Main.this,
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
		//this.Show();
	}
	
	public ArrayList<Integer> getRows()
	{
		return this.Rows;
	}
	
	public ArrayList<Integer> getColums()
	{
		return this.Colums;
	}
	
	public ArrayList<String> getValues()
	{
		return this.Values;
	}
	
	public static void main(String[] args) 
	{
		main = new Main();
	}
}
