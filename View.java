/*
 * @author Sanidhya Singal 2015085
 * @author Pranav Nambiar 2015063
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View {
	static ParseCollection parse;
	public static void main(String[] args) {
		System.out.println("Setting Up... Please Wait.");
		parse = new ParseCollection("", 3, "", "",0);
		System.out.println("You're ready to go!! :)");
		new MyWelcomeFrame();
	}
}

class MyWelcomeFrame extends JFrame {
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();

	public MyWelcomeFrame() {
		setTitle("DBLP Query Engine : Welcome User");
		setSize(screenSize.width - 342, screenSize.height - 168);	//1024 x 600
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(screenSize.width/10, screenSize.height/10);
		
		Container c = getContentPane();
		c.setBackground(Color.BLACK);
		
		Box box = Box.createVerticalBox();
		
		JLabel label = new JLabel("DBLP Query Engine");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Font myFont = new Font("Mistral", Font.PLAIN, 96);
		label.setFont(myFont);
		label.setForeground(Color.CYAN);
		
		JButton wb1 = new MyWelcomeButton("Start");
		wb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dispose();
				new MyFrame();	//Start
			}
		});
		
		JButton wb2 = new MyWelcomeButton("Exit");
		wb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);		//Exit
			}
		});
		
		box.add(Box.createVerticalStrut(150));
		box.add(label);
		box.add(Box.createVerticalStrut(50));
		box.add(wb1);
		box.add(Box.createVerticalStrut(20));
		box.add(wb2);
		box.add(Box.createVerticalStrut(120));
		JLabel footnote = new JLabel("Copyright: Pranav-Sanidhya");
		footnote.setFont(new Font("Agency FB", Font.PLAIN, 20));
		footnote.setForeground(Color.WHITE);
		footnote.setAlignmentX(CENTER_ALIGNMENT);
		box.add(footnote);
		
		add(box);
		
		setVisible(true);
	}
}

class MyWelcomeButton extends JButton {
	public MyWelcomeButton(String title) {
		Font myFont = new Font("Agency FB", Font.PLAIN, 32);
		setFocusable(false);
		setText(title);
		setFont(myFont);
		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
		setAlignmentX(Component.CENTER_ALIGNMENT);
	}
}

class MyFrame extends JFrame {
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	GridBagConstraints gbc = new GridBagConstraints();
	GridBagConstraints gbc2 = new GridBagConstraints();
	
	public MyFrame() {
		setTitle("DBLP Query Engine");
		setSize(screenSize.width - 342, screenSize.height - 168);	//1024 x 600
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(screenSize.width/10, screenSize.height/10);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		JLabel label = new JLabel("DBLP Query Engine");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Font myFont = new Font("Bearpaw", Font.PLAIN, 72);
		Font myFont2 = new Font("Agency FB", Font.BOLD, 24);
		label.setFont(myFont);
		add(Box.createVerticalStrut(20));
		add(label);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridBagLayout());
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridBagLayout());
		
		final String[] queries = {"Select Query :", "Query 1", "Query 2", "Query 3"};
		final String initialQuery = queries[0];
		JComboBox<String> queryOptions = new JComboBox<>();
		queryOptions.setForeground(Color.BLACK);
		queryOptions.setBackground(Color.WHITE);
		queryOptions.setFont(myFont2);

//		queryOptions.setModel(new DefaultComboBoxModel<String>() {
//			boolean selectionAllowed = true;
//			public void setSelectedItem(Object obj) {
//				if (!initialQuery.equals(obj))
//					super.setSelectedItem(obj);
//				else if (selectionAllowed) {
//					selectionAllowed = false;
//					super.setSelectedItem(obj);
//				}
//			}
//		});

		queryOptions.addItem(queries[0]);
		queryOptions.addItem(queries[1]);
		queryOptions.addItem(queries[2]);
		queryOptions.addItem(queries[3]);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 200, 50);	//down, left, up, right
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		leftPanel.add(queryOptions, gbc);
		
		panel.add(leftPanel);		
		
		String[] columns = {"", "", ""};
		Object[][] rows = {};
		JTable table = new JTable(rows, columns);
		table.setPreferredScrollableViewportSize(new Dimension(700, 350));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.fill = GridBagConstraints.BOTH;
		rightPanel.add(scrollPane, gbc2);

		JLabel result = new JLabel();
		result.setFont(myFont2);
		int count = 0;
		result.setText("No. of Results Obtained: \t" + count);
		
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.anchor = GridBagConstraints.CENTER;
		rightPanel.add(result, gbc2);

		panel.add(rightPanel);
		
		queryOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> combo = (JComboBox<String>) e.getSource();
		        String selectedQuery = (String) combo.getSelectedItem();
		        if (selectedQuery.equals("Query 1")) {
		        	dispose();
		        	new Query1();
		        }
		        else if (selectedQuery.equals("Query 2")) {
		        	dispose();
			        new Query2();
		        }
		        else if (selectedQuery.equals("Query 3")) {
		        	dispose();
			        new Query3();
		        }
			}
		});
		
		add(panel);
		setVisible(true);
	}
}

class MyButton extends JButton {
	Font myFont = new Font("Agency FB", Font.BOLD, 24);
	public MyButton(String title) {
		setText(title);
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		setFont(myFont);
	}
}

class CheckValidString {
	public boolean isNumeric(String s) {
		char[] chars = s.toCharArray();
	    for (char c : chars) {
	        if(!Character.isDigit(c)) 
	            return false;
	    }
	    return true;
	}
}

class Prompts {
	public void EmptyBoxPrompt() {
		JFrame pop = new JFrame();
		JOptionPane.showMessageDialog(pop, "Kindly enter some value.");
	}
	
	public void InvalidEntryPrompt() {
		JFrame pop = new JFrame();
		JOptionPane.showMessageDialog(pop, "Kindly enter a valid value.");
	}
	
	public void LeaveEmptyPrompt() {
		JFrame pop = new JFrame();
		JOptionPane.showMessageDialog(pop, "Kindly Leave Custom Range as empty.");
	}
	
	public void InvalidComparisonPrompt() {
		JFrame pop = new JFrame();
		JOptionPane.showMessageDialog(pop, "Value of Start Year should be less than the value of End Year.");
	}
	
	public void SelectButtonPrompt() {
		JFrame pop = new JFrame();
		JOptionPane.showMessageDialog(pop, "Kindly select Author/Title.");
	}

	public void zeroResultCount() {
		JFrame pop = new JFrame();
		JOptionPane.showMessageDialog(pop, "No Result Found.");
	}
}