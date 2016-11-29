import java.awt.*;
import java.awt.event.*;

import javax.security.sasl.AuthorizeCallback;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class Query1 extends JFrame {
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	GridBagConstraints gbc = new GridBagConstraints();
	GridBagConstraints gbc2 = new GridBagConstraints();
	GridBagConstraints gbc3 = new GridBagConstraints();
	ArrayList<Data> row = new ArrayList<>();
	DefaultTableModel model = new DefaultTableModel();
	int count = 0;
	JLabel result = new JLabel();
	JButton next = new MyButton("Next");
	int clicks = 0, size = 0;
	Boolean isYearSelected = false, isRelevanceSelected = false, isAuthorSelected = false, isTitleSelected = false;
	
	public Query1() {
		setTitle("DBLP Query Engine");
		setSize(screenSize.width, screenSize.height - 28);	//1366 x 740
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    	
		queryOptions.addItem(queries[1]);
		queryOptions.addItem(queries[2]);
		queryOptions.addItem(queries[3]);
		
		JButton searchButton = new MyButton("Search");
		JButton resetButton = new MyButton("Reset");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 10, 50);	//top, left, bottom, right
		leftPanel.add(queryOptions, gbc);
		
		JLabel searchLabel = new JLabel();
		searchLabel.setFont(myFont2);
		searchLabel.setText("Search By: ");
		gbc.gridy++;
		gbc.insets = new Insets(1, 10, 1, 10);	//top, left, bottom, right
		leftPanel.add(searchLabel, gbc);
		
		JRadioButton authorName = new JRadioButton("Author Name");
		JRadioButton titleTags = new JRadioButton("Title Tags");
		authorName.setFont(new Font("Cambria", Font.PLAIN, 18));
		titleTags.setFont(new Font("Cambria", Font.PLAIN, 18));
		
		authorName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (authorName.isSelected()) {
					titleTags.setSelected(false);
					isAuthorSelected = !(isAuthorSelected);
				}
			}
		});

		titleTags.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (titleTags.isSelected()) {
					authorName.setSelected(false);
					isTitleSelected = !(isTitleSelected);
				}
			}
		});

		gbc.gridy++;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(1, 10, 1, 10);
		leftPanel.add(authorName, gbc);
		
		gbc.gridy++;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(1, 10, 1, 10);
		leftPanel.add(titleTags, gbc);
	
		JLabel nameTag = new JLabel("Name/Title Tags:");
		JLabel to = new JLabel("TO");
		JLabel sinceYear = new JLabel("Since Year:");
		JLabel customRange = new JLabel("Custom Range:");
		nameTag.setFont(new Font("Cambria", Font.PLAIN, 18));
		to.setFont(new Font("Cambria", Font.PLAIN, 18));
		sinceYear.setFont(new Font("Cambria", Font.PLAIN, 18));
		customRange.setFont(new Font("Cambria", Font.PLAIN, 18));
		
		JTextField nameTagF = new JTextField();
		JTextField sinceYearF = new JTextField();
		JTextField startYear = new JTextField();
		JTextField endYear = new JTextField();
		nameTagF.setFont(new Font("Cambria", Font.PLAIN, 24));
		sinceYearF.setFont(new Font("Cambria", Font.PLAIN, 24));
		startYear.setFont(new Font("Cambria", Font.PLAIN, 24));
		endYear.setFont(new Font("Cambria", Font.PLAIN, 24));
		
		gbc.gridy++;
		gbc.insets = new Insets(1, 10, 1, 10);
		leftPanel.add(nameTag, gbc);
		
		gbc.gridy++;
		gbc.insets = new Insets(1, 10, 1, 10);	//top, left, bottom, right
		gbc.gridwidth = 2;
		leftPanel.add(nameTagF, gbc);
		
		gbc.gridy++;
		gbc.insets = new Insets(1, 10, 1, 10);
		leftPanel.add(sinceYear, gbc);
		
		gbc.gridx++;
		gbc.insets = new Insets(1, 10, 1, 10);	//top, left, bottom, right
		gbc.gridwidth = 2;
		leftPanel.add(sinceYearF, gbc);
		
		gbc.gridx--;
		gbc.gridy++;
		gbc.insets = new Insets(1, 10, 1, 10);
		leftPanel.add(customRange, gbc);
		
		gbc.gridx++;
		gbc.insets = new Insets(1, 10, 1, 10);	//top, left, bottom, right
		gbc.gridwidth = 2;
		leftPanel.add(startYear, gbc);
		
		gbc.gridy++;
		gbc.insets = new Insets(1, 40, 1, 10);
		gbc.gridwidth = 2;
		leftPanel.add(to, gbc);
		
		gbc.gridy++;
		gbc.insets = new Insets(1, 10, 1, 10);	//top, left, bottom, right
		gbc.gridwidth = 2;
		leftPanel.add(endYear, gbc);
		
		JRadioButton sortYear = new JRadioButton("Year (Ascending)");
		JRadioButton sortRelevance = new JRadioButton("Relevance");
		sortYear.setFont(new Font("Cambria", Font.PLAIN, 18));
		sortRelevance.setFont(new Font("Cambria", Font.PLAIN, 18));

		sortYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sortYear.isSelected()) {
					sortRelevance.setSelected(false);
					isYearSelected = !(isYearSelected);
				}
			}
		});

		sortRelevance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sortRelevance.isSelected()) {
					sortYear.setSelected(false);
					isRelevanceSelected = !(isRelevanceSelected);
				}
			}
		});

		JLabel sortLabel = new JLabel();
		sortLabel.setFont(myFont2);
		sortLabel.setText("Sort By: ");
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(1, 10, 1, 10);	//top, left, bottom, right
		leftPanel.add(sortLabel, gbc);
		
		gbc.gridy++;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(1, 10, 1, 10);
		leftPanel.add(sortYear, gbc);
		
		gbc.gridy++;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(1, 10, 1, 10);
		leftPanel.add(sortRelevance, gbc);
		
		gbc3.gridx = 0;
		gbc3.gridy = gbc.gridy + 2;
		gbc3.insets = new Insets(1, 10, 1, 10);	//top, left, bottom, right
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tag, yearSince, yearStart, yearEnd, start, end;
				tag = nameTagF.getText();
				
				if(tag.length()>=20)
					nameTagF.setText(tag.substring(0, 19));
				    
				yearSince = sinceYearF.getText();
				yearStart = startYear.getText();
				yearEnd = endYear.getText();
				
				Prompts prompt = new Prompts();
				CheckValidString checker = new CheckValidString();
				
				if (tag.isEmpty()) {
					prompt.EmptyBoxPrompt();
					return;
				}
				
				if (!yearSince.isEmpty() && (!checker.isNumeric(yearSince) || (yearSince.length() != 4))) {
					prompt.InvalidEntryPrompt();
					return;
				}
				
				if (!yearStart.isEmpty() && (!checker.isNumeric(yearStart) || (yearStart.length() != 4))) {
					prompt.InvalidEntryPrompt();
					return;
				}
				
				if (!yearEnd.isEmpty() && (!checker.isNumeric(yearEnd) || (yearEnd.length() != 4))) {
					prompt.InvalidEntryPrompt();
					return;
				}
				
				if (yearStart.compareTo(yearEnd) > 0) {
					prompt.InvalidComparisonPrompt();
					return;
				}
				
				if (!(yearSince.isEmpty())) {
					start = yearSince;
					end = "";
					if (!(yearStart.isEmpty()) || !(yearEnd.isEmpty())) {
						prompt.LeaveEmptyPrompt();
						return;
					}
				}
				else { 
					start = yearStart;
					end = yearEnd;
				}
					
				if (isAuthorSelected) {	
					ParseCollection parse = new ParseCollection(tag, 1, start, end);
					if (isYearSelected)
						row = parse.getSortbyYear();
					else if (isRelevanceSelected)
						row = parse.getSortByRelevance();
					else
						row = parse.getResult();
				}
				else if (isTitleSelected) {
					ParseCollection parse = new ParseCollection(tag, 2, start, end);
					if (isYearSelected)
						row = parse.getSortbyYear();
					else if (isRelevanceSelected)
						row = parse.getSortByRelevance();
					else
						row = parse.getResult();
				}
				else {
					prompt.SelectButtonPrompt();
					return;
				}
					
				
//				for (Data i : row)
//					System.out.println(i);
				
				String[] columns = {"S.No.", "Author", "Title", "Pages", "Year", "Volume", "Journal/BookTitle", "URL"};
				String[][] rows = new String[row.size()][8];
				
				count = row.size();
				
				for (int i=0; i<row.size(); i++) {
					rows[i][0] = String.valueOf(i+1);
					rows[i][1] = row.get(i).getAuthor();
					rows[i][2] = row.get(i).getTitle();
					rows[i][3] = row.get(i).getPages();
					rows[i][4] = row.get(i).getYear();
					rows[i][5] = row.get(i).getVolume();
					rows[i][6] = row.get(i).getJournal();
					rows[i][7] = row.get(i).getUrl();
				}

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
		gbc3.insets = new Insets(10, 10, 10, 20);	//top, left, bottom, right
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				count = 0;
				isYearSelected = false; 
				isRelevanceSelected = false; 
				isAuthorSelected = false; 
				isTitleSelected = false;
				new Query1();
			}
		});
		leftPanel.add(resetButton, gbc3);

		panel.add(leftPanel);		
		
		JTable table = new JTable(model);

		table.setPreferredScrollableViewportSize(new Dimension(900, 450));
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
		gbc2.insets = new Insets(0, 800, 0, 0);
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