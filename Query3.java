/*
 * @author Sanidhya Singal 2015085
 * @author Pranav Nambiar 2015063
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Query3 extends JFrame {
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	GridBagConstraints gbc = new GridBagConstraints();
	GridBagConstraints gbc2 = new GridBagConstraints();
	GridBagConstraints gbc3 = new GridBagConstraints();
	ArrayList<Data> row = new ArrayList<>();
	DefaultTableModel model = new DefaultTableModel();
	
	public Query3() {
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
		
		final String[] queries = {"Queries :", "Query 1", "Query 2", "Query 3"};
		JComboBox<String> queryOptions = new JComboBox<>();
		queryOptions.setForeground(Color.BLACK);
		queryOptions.setBackground(Color.WHITE);
		queryOptions.setFont(myFont2);
    	
		queryOptions.addItem(queries[3]);
		queryOptions.addItem(queries[1]);
		queryOptions.addItem(queries[2]);
		
		JButton searchButton = new MyButton("Predict");
		JButton resetButton = new MyButton("Reset");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 10, 50);	//down, left, up, right
		gbc.fill = GridBagConstraints.HORIZONTAL;
//		gbc.anchor = GridBagConstraints.CENTER;
		leftPanel.add(queryOptions, gbc);
		
		JLabel yearLabel = new JLabel("Upto Year: ");
		yearLabel.setFont(myFont2);
		gbc.gridy++;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(1, 10, 1, 10);	//down, left, up, right
		leftPanel.add(yearLabel, gbc);
		
		gbc.gridy++;
		JTextField field = new JTextField();
		field.setFont(new Font("Cambria", Font.PLAIN, 24));
		gbc.insets = new Insets(1, 10, 1, 10);	//down, left, up, right
		leftPanel.add(field, gbc);
		
		JLabel input = new JLabel("Enter Name of 5 Authors: ");
		input.setFont(myFont2);
		gbc.gridy++;
		gbc.insets = new Insets(1, 10, 1, 10);	//down, left, up, right
		leftPanel.add(input, gbc);
		
		gbc.gridy++;
		JTextField field1 = new JTextField();
		field1.setFont(new Font("Cambria", Font.PLAIN, 24));
		gbc.insets = new Insets(1, 10, 1, 10);	//down, left, up, right
		leftPanel.add(field1, gbc);
		
		gbc.gridy++;
		JTextField field2 = new JTextField();
		field2.setFont(new Font("Cambria", Font.PLAIN, 24));
		gbc.insets = new Insets(1, 10, 1, 10);	//down, left, up, right
		leftPanel.add(field2, gbc);
		
		gbc.gridy++;
		JTextField field3 = new JTextField();
		field3.setFont(new Font("Cambria", Font.PLAIN, 24));
		gbc.insets = new Insets(1, 10, 1, 10);	//down, left, up, right
		leftPanel.add(field3, gbc);
		
		gbc.gridy++;
		JTextField field4 = new JTextField();
		field4.setFont(new Font("Cambria", Font.PLAIN, 24));
		gbc.insets = new Insets(1, 10, 1, 10);	//down, left, up, right
		leftPanel.add(field4, gbc);
		
		gbc.gridy++;
		JTextField field5 = new JTextField();
		field5.setFont(new Font("Cambria", Font.PLAIN, 24));
		gbc.insets = new Insets(1, 10, 1, 10);	//down, left, up, right
		leftPanel.add(field5, gbc);
		
		gbc3.gridx = 0;
		gbc3.gridy = 10;
		gbc.insets = new Insets(10, 10, 10, 10);	//down, left, up, right
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String year = field.getText();
				ArrayList<String> author = new ArrayList<>();
				ArrayList<Integer> predictAuthor = new ArrayList<>();
				author.add(field1.getText());
				author.add(field2.getText());
				author.add(field3.getText());
				author.add(field4.getText());
				author.add(field5.getText());
				
				Prompts prompt = new Prompts();
				CheckValidString checker = new CheckValidString();
				
				if (year.isEmpty()) {
					prompt.EmptyBoxPrompt();
					return;
				}
				
				if (!year.isEmpty() && (!checker.isNumeric(year) || (year.length() != 4))) {
					prompt.InvalidEntryPrompt();
					return;
				}
				
				Iterator<String> itr = author.iterator();
				while (itr.hasNext()) {
					if (itr.next().isEmpty()) {
						prompt.EmptyBoxPrompt();
						return;
					}
				}
				
				for (int i=0; i<5; i++) {
//					predictAuthor.add(predict(author.get(i), year));
				}
				
				String[] columns = {"S.No.", "Author", "Prediction"};
				String[][] rows = new String[row.size()][3];
				
				for (int i=0; i<5; i++) {
					rows[i][0] = String.valueOf(i+1);
					rows[i][1] = author.get(i);
					rows[i][2] = String.valueOf(predictAuthor.get(i));
				}
				
				for (int i=0; i<columns.length; i++)
					model.addColumn(columns[i]);
				
				for (int i=0; i<row.size(); i++) {
					model.insertRow(i, rows[i]);
				}
				
			}
		});
		leftPanel.add(searchButton, gbc3);
		
		gbc3.gridx++;
		gbc3.insets = new Insets(10, 10, 10, 20);	//down, left, up, right
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Query3();
			}
		});
		leftPanel.add(resetButton, gbc3);

		
		panel.add(leftPanel);		
		
		JTable table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(700, 350));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.fill = GridBagConstraints.BOTH;
		rightPanel.add(scrollPane, gbc2);

		panel.add(rightPanel);
		
		queryOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> combo = (JComboBox<String>) e.getSource();
		        String selectedQuery = (String) combo.getSelectedItem();
		        dispose();
	        	if (selectedQuery.equals("Query 1")) {
		        	new Query1();
		        }
		        else if (selectedQuery.equals("Query 2")) {
		        	new Query2();
		        }
		        else if (selectedQuery.equals("Query 3")) {
		        	new Query3();
		        }
			}
		});
		
		add(panel);
		setVisible(true);
	}
	
	int predict(String name, String year) {
		int n, sumx, sumy, sumxy, sumxsq;
		double a, b;
		return 0;
	}
}
