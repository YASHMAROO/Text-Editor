package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class SimpleTextEditor {
	
	private final String title = "Simple Text Editor";

	private JFrame frame;
	private JTextArea textArea;
	private File openFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					SimpleTextEditor window = new SimpleTextEditor();
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SimpleTextEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setAutoRequestFocus(false);
		frame.setTitle(title);
		frame.setBounds(100, 100, 616, 444);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setForeground(Color.BLACK);
		textArea.setFont(new Font("Arial", Font.PLAIN, 14));
		frame.getContentPane().add(textArea, BorderLayout.NORTH);
		
		JScrollPane scroll = new JScrollPane(textArea);
		frame.getContentPane().add(scroll, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(0, 5, 0, 0));
		menuBar.setBackground(Color.BLACK);
		menuBar.setForeground(Color.WHITE);
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		menuBar.add(new JMenu("|")).setEnabled(false);
		
		JMenuItem open = new JMenuItem("Open New File");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				open();
			}
		});
		mnNewMenu.add(open);
				
		JMenuItem saveAs = new JMenuItem("Save As");
		saveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				create();
			}
		});
		mnNewMenu.add(saveAs);
		
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		mnNewMenu.add(save);
		
		JMenuItem print = new JMenuItem("Print");
		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				printFile();
			}
		});
		mnNewMenu.add(print);
		
		JMenuItem close = new JMenuItem("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});
		mnNewMenu.add(close);
		
		JMenuItem newFile = new JMenuItem("New");
		newFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newFile();
			}
		});
		mnNewMenu.add(newFile);
		
		
		JMenu edit = new JMenu("Edit");
		menuBar.add(edit);
		menuBar.add(new JMenu("|")).setEnabled(false);
		
		JMenuItem cutOption = new JMenuItem("Cut");
		cutOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cutText();
			}
		});
		edit.add(cutOption);
		
		JMenuItem copyOption = new JMenuItem("Copy");
		copyOption.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				copyText();
			}
		});
		edit.add(copyOption);
		
		JMenuItem pasteOption = new JMenuItem("Paste");
		pasteOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pasteText();
			}
		});
		edit.add(pasteOption);
		
		JMenu exit = new JMenu("Exit");
		menuBar.add(exit);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exitTheFrame();
			}
		});
		
	}
	
	private void open() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Select a file To Open");
			chooser.showOpenDialog(null);
			
			File selectedFile = chooser.getSelectedFile();
			if(!selectedFile.exists()) {
				//Error message 
				JOptionPane.showMessageDialog(null, "Failed to open a file, file doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
				openFile = null;
				return ;
			}
			
			Scanner reader = new Scanner(selectedFile);
			String contents = "";
			while(reader.hasNextLine()) {
				contents += reader.nextLine()+"\n";
			}
			reader.close();
			textArea.setText(contents);
			
			openFile = selectedFile;
			
			frame.setTitle(title + " - " + selectedFile.getName());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void create() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Choose location to Save");
			chooser.showSaveDialog(null);
			
			openFile = chooser.getSelectedFile();
			
			save();
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void save() {
		try {
			if(openFile == null) {
				//Error Message
				JOptionPane.showMessageDialog(null, "Can't Save , No file is selected", "Error", JOptionPane.ERROR_MESSAGE);
				return ;
			}
			
			String contents = textArea.getText();
			
			Formatter form = new Formatter(openFile);
			form.format("%s", contents);
			form.close();
			
			frame.setTitle(title + " - " + openFile.getName());
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void printFile() {
		try { 
            // print the file 
            textArea.print(); 
        } 
        catch (Exception e) { 
            JOptionPane.showMessageDialog(frame, e.getMessage()); 
        }
	}
	
	private void close() {
		if(openFile == null) {
			JOptionPane.showMessageDialog(null, "Failed to close the file,No file is selected", "Error", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		try {
			
			int input = JOptionPane.showConfirmDialog(null,"Do You want to save before closing?", "Wait!" ,JOptionPane.YES_NO_OPTION);
			if(input == JOptionPane.YES_OPTION) {
				save();
			}
			
			textArea.setText("");
			openFile = null;
			frame.setTitle(title);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void newFile() {
		if(openFile != null) {
			JOptionPane.showMessageDialog(null, "Please close or save the opened file", "Error", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		textArea.setText("");
	}
	
	private void cutText() {
		textArea.cut();
	}
	
	private void copyText() {
		textArea.copy();
	}
	
	private void pasteText() {
		textArea.paste();;
	}
	
	private void exitTheFrame() {
		frame.setVisible(false); 
	}
	

}