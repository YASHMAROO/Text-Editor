package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.undo.UndoManager;


public class SimpleTextEditor{
	
	private final String title = "Simple Text Editor";

	private JFrame frame;
	private JTextPane textArea;
	private File openFile;
	private JButton bold;
	private JButton italics;
	private JButton undo;
	private JButton redo;
	UndoManager manager = new UndoManager();

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
		frame = new JFrame("seperator");
		frame.setAutoRequestFocus(false);
		frame.setTitle(title);
		frame.setBounds(100, 100, 616, 444);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextPane();
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
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setSize(100, 100);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		bold = new JButton();
		bold.setText("Bold");
		bold.setBackground(new java.awt.Color(0, 140, 255));
		bold.addActionListener(e -> changeStyle());
		
		italics = new JButton("Italics");
		italics.setBackground(new java.awt.Color(0, 140, 255));
		italics.addActionListener(e -> changeItalics());
		
		undo = new JButton();
		undo.setText("Undo");
		undo.setBackground(new java.awt.Color(0, 140, 255));
		
		redo = new JButton();
		redo.setText("Redo");
		redo.setBackground(new java.awt.Color(0, 140, 255));
		
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					manager.undo();
				} catch (Exception ex) {
				}
			}
		});
		
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					manager.redo();
				} catch (Exception ex) {
				}
			}
		});
		
		textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				manager.addEdit(e.getEdit());
			}
		});
		
		panel.add(undo);
		panel.add(redo);
		panel.add(bold);
		panel.add(italics);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		menuBar.add(new JMenu("|")).setEnabled(false);
		
		JMenuItem newFile = new JMenuItem("New");
		newFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newFile();
			}
		});
		mnNewMenu.add(newFile);
		mnNewMenu.addSeparator();
		
		JMenuItem open = new JMenuItem("Open File");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				open();
			}
		});
		mnNewMenu.add(open);
		mnNewMenu.addSeparator();
				
		JMenuItem saveAs = new JMenuItem("Save As");
		saveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				create();
			}
		});
		mnNewMenu.add(saveAs);
		mnNewMenu.addSeparator();
		
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		mnNewMenu.add(save);
		mnNewMenu.addSeparator();
		
		JMenuItem print = new JMenuItem("Print");
		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				printFile();
			}
		});
		mnNewMenu.add(print);
		mnNewMenu.addSeparator();
		
		JMenuItem close = new JMenuItem("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});
		mnNewMenu.add(close);
		
		
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
		edit.addSeparator();
		
		JMenuItem copyOption = new JMenuItem("Copy");
		copyOption.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				copyText();
			}
		});
		edit.add(copyOption);
		edit.addSeparator();
		
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
			int save = chooser.showSaveDialog(null);
			
			if(save == JFileChooser.CANCEL_OPTION) {
				System.exit(-1);
			}
			
			openFile = chooser.getSelectedFile();
			
			save();
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void save() {
		try {
			String text = textArea.getText().trim();
			if(!text.equals("") && openFile == null) {
				create();
				return ;
			}
			if(openFile == null && text.equals("")) {
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
		System.exit(0);
	}
	
	private void changeStyle() {
		StyledDocument doc = (StyledDocument) textArea.getDocument();
	    int selectionEnd = textArea.getSelectionEnd();
	    int selectionStart = textArea.getSelectionStart();
	    if (selectionStart == selectionEnd) {
	      return;
	    }
	    Element element = doc.getCharacterElement(selectionStart);
	    AttributeSet as = element.getAttributes();

	    MutableAttributeSet asNew = new SimpleAttributeSet(as.copyAttributes());
	    StyleConstants.setBold(asNew, !StyleConstants.isBold(as));
	    doc.setCharacterAttributes(selectionStart, textArea.getSelectedText()
	        .length(), asNew, true);
	    String text = (StyleConstants.isBold(as) ? "Cancel Bold" : "Bold");
	    bold.setText(text);
	  }
	
	private void changeItalics() {
		StyledDocument doc = (StyledDocument) textArea.getDocument();
	    int selectionEnd = textArea.getSelectionEnd();
	    int selectionStart = textArea.getSelectionStart();
	    if (selectionStart == selectionEnd) {
	      return;
	    }
	    Element element = doc.getCharacterElement(selectionStart);
	    AttributeSet as = element.getAttributes();

	    MutableAttributeSet asNew = new SimpleAttributeSet(as.copyAttributes());
	    StyleConstants.setItalic(asNew, !StyleConstants.isItalic(as));
	    doc.setCharacterAttributes(selectionStart, textArea.getSelectedText()
	        .length(), asNew, true);		
	}

}