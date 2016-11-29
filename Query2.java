import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class Query2 extends JFrame {
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	GridBagConstraints gbc = new GridBagConstraints();
	GridBagConstraints gbc2 = new GridBagConstraints();
	GridBagConstraints gbc3 = new GridBagConstraints();
	
	TreeMap<String,Integer> row;
	DefaultTableModel model = new DefaultTableModel();
	int count = 0;
	JLabel result = new JLabel();
	JButton next = new MyButton("Next");
	int clicks = 0, size = 0;
	
	public Query2() {
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
    	
		queryOptions.addItem(queries[2]);
		queryOptions.addItem(queries[1]);
		queryOptions.addItem(queries[3]);
		
		JButton searchButton = new MyButton("Search");
		JButton resetButton = new MyButton("Reset");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 10, 50);	//down, left, up, right
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		leftPanel.add(queryOptions, gbc);
		
		JLabel input = new JLabel();
		input.setFont(myFont2);
		input.setText("No. of Publications: ");
		gbc.gridy++;
		gbc.insets = new Insets(1, 10, 1, 10);	//down, left, up, right
		leftPanel.add(input, gbc);
		
		JTextField field = new JTextField();
		field.setFont(new Font("Cambria", Font.PLAIN, 24));
		gbc.gridy++;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(1, 10, 200, 10);	//down, left, up, right
		leftPanel.add(field, gbc);
		
		gbc3.gridx = 0;
		gbc3.gridy = 10;
		gbc3.insets = new Insets(10, 10, 10, 10);	//down, left, up, right
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String k = field.getText();
				
				Prompts prompt = new Prompts();
				if (k.isEmpty()) {
					prompt.EmptyBoxPrompt();
					return;
				}
				
				CheckValidString checker = new CheckValidString();
				if (!(checker.isNumeric(k))) {
					prompt.InvalidEntryPrompt();
					return;
				}
				
				row = View.parse.getKMap();
				
				String[] columns = {"S.No.", "Author", "No. of Publications"};
				String[][] rows = new String[row.size()][3];
				
				SortedSet<Map.Entry<String, Integer>> c = new TreeSet<Map.Entry<String, Integer>>(new Comparator<Map.Entry<String, Integer>>() {
					public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
						return e2.getValue().compareTo(e1.getValue());
					}
				});
				c.addAll(row.entrySet());
				Iterator itr = c.iterator();
				int counn=0;
				while (itr.hasNext()){
					Map.Entry<String, Integer> me=(Map.Entry<String, Integer>)itr.next();
					if (me.getValue().intValue()>=Integer.parseInt(k) && counn<row.size()){
						rows[counn][0] = String.valueOf(counn+1);
						rows[counn][1] = me.getKey();
						rows[counn][2] = String.valueOf(me.getValue());
						counn++;
					}
				}
				
				count = counn;

				for (int i=0; i<columns.length; i++)
					model.addColumn(columns[i]);

				result.setText("No. of Results Obtained: \t" + count);

				size = (count > 20) ? 20 : count ;
				for (int i=0; i<size; i++) {
					model.insertRow(i, rows[i]);
				}
				
				if (count > 20) {
					next.setEnabled(true);
				}
				
				next.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						clicks++;
						for (int i=(size-((clicks-1)*20))-1; i>=0; i--)
							model.removeRow(i);
						size = ((size + 20) > count)? count : (size + 20);
						for (int i=0; i<(size-(clicks*20)); i++) {
							model.insertRow(i, rows[i + (clicks*20)]);
						}
						if (size == count) {
							next.setEnabled(false);
						}
					}
				});
				
				if (size == count) {
					next.setEnabled(false);
				}

			}
		});
		leftPanel.add(searchButton, gbc3);
		
		gbc3.gridx++;
		gbc3.insets = new Insets(10, 10, 10, 20);	//down, left, up, right
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				count = 0;
				new Query2();
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

		result.setFont(myFont2);
		result.setText("No. of Results Obtained: \t" + count);
		
		gbc2.gridy++;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.anchor = GridBagConstraints.CENTER;
		rightPanel.add(result, gbc2);

		gbc2.gridy += 2;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.anchor = GridBagConstraints.CENTER;
		gbc2.insets = new Insets(0, 600, 0, 0);
		next.setEnabled(false);
		rightPanel.add(next, gbc2);

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
}
