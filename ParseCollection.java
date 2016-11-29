
/*
 * @author Sanidhya Singal 2015085
 * @author Pranav Nambiar 2015063
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParseCollection {
	static ArrayList<Data> store;
	TreeMap<String, Integer> kFind = new TreeMap<String, Integer>();
	String authTemp;
	TreeMap<String, String> tM;
	int choice;

	public ParseCollection(String toSearch, int choice, String startYear, String endYear, int relevance) {
		if (startYear.equals("")) {
			startYear = "0000";
		}

		if (endYear.equals("")) {
			endYear = "2020";
		}

		final String startyear = startYear, endyear = endYear;
		this.choice = choice;

		store = new ArrayList<>();
		System.setProperty("jdk.xml.entityExpansionLimit", "0");

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new DefaultHandler() {
				boolean authorname = false;
				boolean titlename = false;
				boolean pagesn = false;
				boolean yearname = false;
				boolean volumename = false;
				boolean journalname = false;
				boolean numbername = false;
				boolean urlname = false;
				boolean eename = false;
				boolean editorname = false;
				int compare;
				Data temp;
				ArrayList<String> ath = new ArrayList<String>();

				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {

					if (qName.equalsIgnoreCase("ARTICLE") || qName.equalsIgnoreCase("INPROCEEDINGS")
							|| qName.equalsIgnoreCase("PROCEEDINGS") || qName.equalsIgnoreCase("BOOK")
							|| qName.equalsIgnoreCase("INCOLLECTION") || qName.equalsIgnoreCase("PHDTHESIS")
							|| qName.equalsIgnoreCase("MASTERSTHESIS") || qName.equalsIgnoreCase("WWW")
							|| qName.equalsIgnoreCase("DATA")) {
						temp = new Data();
						ath.clear();
					}
					if (qName.equalsIgnoreCase("AUTHOR")) {
						authTemp = "";
						authorname = true;
					}
					if (qName.equalsIgnoreCase("EDITOR")) {
						editorname = true;
					}
					if (qName.equalsIgnoreCase("TITLE")) {
						titlename = true;
					}
					if (qName.equalsIgnoreCase("PAGES")) {
						pagesn = true;
					}
					if (qName.equalsIgnoreCase("YEAR")) {
						yearname = true;
					}
					if (qName.equalsIgnoreCase("VOLUME")) {
						volumename = true;
					}
					if (qName.equalsIgnoreCase("JOURNAL")) {
						journalname = true;
					}
					if (qName.equalsIgnoreCase("NUMBER")) {
						numbername = true;
					}
					if (qName.equalsIgnoreCase("URL")) {
						urlname = true;
					}
					if (qName.equalsIgnoreCase("EE")) {
						eename = true;
					}
				}

				public boolean compareStrings(String i, String j) {
					if (i.length() == 0 || j.length() == 0) {
						return false;
					}
					if (i.equals(j)) {
						return true;
					}
					return false;
				}

				public int compare2(String s1, String s2) {
					String[] string1;
					int count = 0;
					string1 = s2.split(" ");
					for (String i : string1) {
						if (i.length() <= 3) {
							continue;
						}
						if (s1.contains(i)) {
							count++;
						}
					}
					return count;
				}

				public boolean cp(ArrayList<Integer> arr) {
					for (Integer i : arr) {
						if (i == 0) {
							return false;
						}
					}
					return true;
				}

				public boolean compare(String str1, String str2) {
					String[] string1, string2;
					string1 = str1.split(" ");
					string2 = str2.split(" ");
					ArrayList<Integer> s1, s2;
					s1 = new ArrayList<Integer>();
					s2 = new ArrayList<Integer>();
					boolean comp = false;
					for (String i : string1) {
						s1.add(0);
					}
					for (String i : string2) {
						s2.add(0);
					}
					for (int i = 0; i < string1.length; i++) {
						if (string1[i].length() == 0) {
							s1.set(i, 1);
						}
					}
					for (int i = 0; i < string2.length; i++) {
						if (string2[i].length() == 0) {
							s2.set(i, 1);
						}
					}
					for (int i = 0; i < string1.length; i++) {
						for (int j = 0; j < string2.length; j++) {
							if (string1[i].length() == 0) {
								System.out.println("yo");
							}
							if (s1.get(i) == 0 && s2.get(j) == 0) {
								comp = compareStrings(string1[i].toUpperCase(), string2[j].toUpperCase());
								if (comp == true) {
									s1.set(i, 1);
									s2.set(j, 1);
								}
							}
						}
					}
					if (cp(s1) || cp(s2)) {
						return true;
					} else {
						return false;
					}
				}

				@Override
				public void endElement(String uri, String localName, String qName) throws SAXException {
					if (qName.equalsIgnoreCase("AUTHOR")) {
						ath.add(authTemp);
						authTemp = "";
						authorname = false;
					}
					if (qName.equalsIgnoreCase("ARTICLE") || qName.equalsIgnoreCase("INPROCEEDINGS")
							|| qName.equalsIgnoreCase("PROCEEDINGS") || qName.equalsIgnoreCase("BOOK")
							|| qName.equalsIgnoreCase("INCOLLECTION") || qName.equalsIgnoreCase("PHDTHESIS")
							|| qName.equalsIgnoreCase("MASTERSTHESIS") || qName.equalsIgnoreCase("DATA")) {

						if (choice == 1) {
							if (relevance == 1) {
								if (temp.getYear().compareTo(startyear) >= 0
										&& temp.getYear().compareTo(endyear) <= 0) {
									for (String athr : ath) {
										if (tM.containsKey(athr)) {
											authTemp = tM.get(athr);
										} else {
											authTemp = athr;
										}
										athr = authTemp;
										compare = getLevenshteinDistance(athr.toUpperCase(), toSearch.toUpperCase());
										if (compare <= (toSearch.length() / 3)) {
											temp.setAuthor(athr);
											temp.setComp(compare);
											store.add(temp);
											temp = temp.copy();
										}
									}
								}
							} else {
								if (temp.getYear().compareTo(startyear) >= 0
										&& temp.getYear().compareTo(endyear) <= 0) {
									for (String athr : ath) {
										if (tM.containsKey(athr)) {
											authTemp = tM.get(athr);
										} else {
											authTemp = athr;
										}
										athr = authTemp;
										if (compare(athr.toUpperCase(), toSearch.toUpperCase())) {
											temp.setAuthor(athr);
											store.add(temp);
											temp = temp.copy();
										}
									}
								}

							}
						} else if (choice == 2) {
							if (temp.getYear().compareTo(startyear) >= 0 && temp.getYear().compareTo(endyear) <= 0) {
								temp.setComp(compare2(temp.getTitle().toUpperCase(), toSearch.toUpperCase()));

								if (temp.getComp() > 0) {
									if (temp.getYear().compareTo(startyear) >= 0
											&& temp.getYear().compareTo(endyear) <= 0) {
										temp.setAuth(ath);
										if (ath.size() > 0) {
											temp.setAuthor(ath.get(0));
										}
										store.add(temp);
									}
								}
							}
						} else if (choice == 3) {
							for (String athr : ath) {
								if (tM.containsKey(athr)) {
									authTemp = tM.get(athr);
								} else {
									authTemp = athr;
								}
								// authTemp = athr;
								if (kFind.containsKey(authTemp)) {
									// System.out.println(authTemp);
									kFind.put(authTemp, kFind.get(authTemp) + 1);
								} else {
									// System.out.println("\t" + authTemp);
									kFind.put(authTemp, 1);
								}
							}
						}
						temp = null;
					}

					if (qName.equalsIgnoreCase("www")) {
						temp = null;
					}
				}

				public int getLevenshteinDistance(String s, String t) {
					if (s == null || t == null) {
						throw new IllegalArgumentException("Strings must not be null");
					}

					int n = s.length(); // length of s
					int m = t.length(); // length of t

					if (n == 0) {
						return m;
					} else if (m == 0) {
						return n;
					}

					if (n > m) {
						// swap the input strings to consume less memory
						String tmp = s;
						s = t;
						t = tmp;
						n = m;
						m = t.length();
					}

					int p[] = new int[n + 1]; // 'previous' cost array,
												// horizontally
					int d[] = new int[n + 1]; // cost array, horizontally
					int _d[]; // placeholder to assist in swapping p and d

					// indexes into strings s and t
					int i; // iterates through s
					int j; // iterates through t

					char t_j; // jth character of t

					int cost; // cost

					for (i = 0; i <= n; i++) {
						p[i] = i;
					}

					for (j = 1; j <= m; j++) {
						t_j = t.charAt(j - 1);
						d[0] = j;

						for (i = 1; i <= n; i++) {
							cost = s.charAt(i - 1) == t_j ? 0 : 1;
							// minimum of cell to the left+1, to the top+1,
							// diagonally left and up +cost
							d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
						}

						// copy current distance counts to 'previous row'
						// distance counts
						_d = p;
						p = d;
						d = _d;
					}
					return p[n];
				}

				@Override
				public void characters(char ch[], int start, int length) throws SAXException {
					if (authorname) {
						authTemp = authTemp + new String(ch, start, length);
					}
					if (editorname) {
						temp.setEditor(temp.getEditor() + new String(ch, start, length));
						editorname = false;
					}
					if (titlename) {
						temp.setTitle(temp.getTitle() + new String(ch, start, length));
						titlename = false;
					}
					if (pagesn) {
						temp.setPages(new String(ch, start, length));
						pagesn = false;
					}
					if (yearname) {
						temp.setYear(new String(ch, start, length));
						yearname = false;
					}
					if (volumename) {
						temp.setVolume(new String(ch, start, length));
						volumename = false;
					}
					if (journalname) {
						temp.setJournal(new String(ch, start, length));
						journalname = false;
					}
					if (numbername) {
						temp.setNumber(new String(ch, start, length));
						numbername = false;
					}
					if (urlname) {
						temp.setUrl(new String(ch, start, length));
						urlname = false;
					}
					if (eename) {
						temp.setEe(new String(ch, start, length));
						eename = false;
					}
				}
			};
			if (choice == 1 || choice == 3) {
				parseHelp pH = new parseHelp();
				tM = pH.getTreeMap();
			}
			saxParser.parse("src\\dblp.xml", handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Data> getResult() {
		Collections.sort(store, new Comp1a());
		return store;
	}

	public ArrayList<Data> getSortbyYear() {
		Collections.sort(store, new Comp1());
		return store;
	}

	public ArrayList<Data> getSortByRelevance() {
		if (choice == 1) {
			Collections.sort(store, new Comp3());
		} else {
			Collections.sort(store, new Comp2());
		}
		return store;
	}

	public TreeMap<String, Integer> getKMap() {
		return kFind;
	}
}

class Comp1 implements Comparator<Data> {
	@Override
	public int compare(Data o1, Data o2) {
		return o1.getYear().compareTo(o2.getYear());
	}
}

class Comp1a implements Comparator<Data> {
	@Override
	public int compare(Data o1, Data o2) {
		return o2.getYear().compareTo(o1.getYear());
	}
}

class Comp2 implements Comparator<Data> {
	@Override
	public int compare(Data o1, Data o2) {
		if (o1.getComp() > o2.getComp()) {
			return -1;
		} else if (o1.getComp() < o2.getComp()) {
			return 1;
		}
		return 0;
	}
}

class Comp3 implements Comparator<Data> {
	@Override
	public int compare(Data o1, Data o2) {
		if (o1.getComp() > o2.getComp()) {
			return 1;
		} else if (o1.getComp() < o2.getComp()) {
			return -1;
		}
		return 0;
	}
}