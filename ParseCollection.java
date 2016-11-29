import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;

public class ParseCollection {
	static ArrayList<Data> store;
	TreeMap<String,Integer> kFind = new TreeMap <String,Integer>();
	String authTemp;
	TreeMap<String,String> tM;
	
   public ParseCollection(String toSearch, int choice, String startYear, String endYear) {
	   if (startYear.equals(""))
		   startYear = "0000";
		   
	   if (endYear.equals(""))
		   endYear = "2020";
	   
	   final String startyear = startYear, endyear = endYear;
	   
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
				Data temp;
			
				public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			
					if (qName.equalsIgnoreCase("ARTICLE") || qName.equalsIgnoreCase("INPROCEEDINGS") || qName.equalsIgnoreCase("PROCEEDINGS") || qName.equalsIgnoreCase("BOOK") || qName.equalsIgnoreCase("INCOLLECTION") || qName.equalsIgnoreCase("PHDTHESIS") || qName.equalsIgnoreCase("MASTERSTHESIS") || qName.equalsIgnoreCase("WWW") || qName.equalsIgnoreCase("DATA")){
						temp=new Data();
					}
					if (qName.equalsIgnoreCase("AUTHOR")) {
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
				
				public int compare2(String s1, String s2){
					String[] string1;
					int count=0;
					string1=s2.split(" ");
					for (String i:string1){
						if (s1.contains(i))
							count++;
					}
					return count;
				}
			
				public void endElement(String uri, String localName, String qName) throws SAXException {
					if (qName.equalsIgnoreCase("ARTICLE") || qName.equalsIgnoreCase("INPROCEEDINGS") || qName.equalsIgnoreCase("PROCEEDINGS") || qName.equalsIgnoreCase("BOOK") || qName.equalsIgnoreCase("INCOLLECTION") || qName.equalsIgnoreCase("PHDTHESIS") || qName.equalsIgnoreCase("MASTERSTHESIS") || qName.equalsIgnoreCase("DATA")){
						if (((temp.getAuthor().toUpperCase()).contains(toSearch.toUpperCase()) || (temp.getEditor().toUpperCase()).contains(toSearch.toUpperCase())) && choice==1){
							if ((temp.getYear()).compareTo(startyear)>=0 && (temp.getYear()).compareTo(endyear)<=0){
								store.add(temp);
							}
						}
						else if (choice==2)
						{
							temp.setComp(compare2(temp.getTitle().toUpperCase(),toSearch.toUpperCase()));
							if (temp.getComp()>0){
								if (temp.getYear().compareTo(startyear)>=0 && temp.getYear().compareTo(endyear)<=0){
									store.add(temp);
								}
							}
						}
						else if (choice==3){
							if (temp.getAuthor().length()==0)
								return;
							parseHelp pH=new parseHelp();
							tM=pH.getTreeMap();
							if (tM.containsKey(temp.getAuthor())){
								authTemp=tM.get(temp.getAuthor());
							}
							else
							{
								authTemp=temp.getAuthor();
							}
							if (kFind.containsKey(authTemp)) {
//								System.out.println(authTemp);
								kFind.put(authTemp,kFind.get(authTemp)+1); 
							}
							else {
//								System.out.println("\t" + authTemp);
								kFind.put(authTemp,1);
							}
						}
						temp=null;
					}
					
					if (qName.equalsIgnoreCase("www")){
						temp=null;
					}
				}
			
				public void characters(char ch[], int start, int length) throws SAXException {
					if (authorname) {
						temp.setAuthor(temp.getAuthor()+" "+new String(ch,start,length));
						authorname = false;
					}
					if (editorname){
						temp.setEditor(temp.getEditor()+new String(ch,start,length));
						editorname=false;
					}
					if (titlename) {
						temp.setTitle(temp.getTitle()+new String(ch,start,length));
						titlename = false;
					}
					if (pagesn) {
						temp.setPages(new String(ch,start,length));
						pagesn = false;
					}
					if (yearname) {
						temp.setYear(new String(ch,start,length));
						yearname = false;
					}
					if (volumename) {
						temp.setVolume(new String(ch,start,length));
						volumename = false;
					}
					if (journalname) {
						temp.setJournal(new String(ch,start,length));
						journalname = false;
					}
					if (numbername) {
						temp.setNumber(new String(ch,start,length));
						numbername = false;
					}
					if (urlname) {
						temp.setUrl(new String(ch,start,length));
						urlname = false;
					}
					if (eename) {
						temp.setEe(new String(ch,start,length));
						eename = false;
					}
				}
			};
			saxParser.parse("src\\dblp.xml", handler);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } 
   }
   public ArrayList<Data> getResult() {
	   return store;
   }
  
   public ArrayList<Data> getSortbyYear() {
	  Collections.sort(store, new Comp1());
	  return store;
   }
  
   public ArrayList<Data> getSortByRelevance() {
	  Collections.sort(store, new Comp2());
	  return store;
   }
  
   public TreeMap<String,Integer> getKMap() {
	  return kFind;
   }
}

class Comp1 implements Comparator<Data>{
	public int compare(Data o1, Data o2) {			
		return o1.getYear().compareTo(o2.getYear());
	}
}

class Comp2 implements Comparator<Data>{
	public int compare(Data o1, Data o2) {
		if (o1.getComp()>o2.getComp())
			return -1;
		else if (o1.getComp()<o2.getComp())
			return 1;			
		return 0;
	}
}