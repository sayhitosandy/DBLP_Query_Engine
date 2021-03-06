
/*
 * @author Sanidhya Singal 2015085
 * @author Pranav Nambiar 2015063
 */

import java.util.ArrayList;

public class Data {
	String author = new String();
	String title = new String();
	String pages = new String();
	String year = new String();
	String volume = new String();
	String journal = new String();
	String number = new String();
	String url = new String();
	String ee = new String();
	String editor = new String();
	ArrayList<String> auth = new ArrayList<String>();
	int comp;

	public ArrayList<String> getAuth() {
		return this.auth;
	}

	public void setAuth(ArrayList<String> auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return (author + " " + title + " " + pages + " " + year + " " + volume + " " + journal + " " + number + " "
				+ url + " " + ee);
	}

	public void setComp(int comp) {
		this.comp = comp;
	}

	public int getComp() {
		return this.comp;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEe() {
		return ee;
	}

	public void setEe(String ee) {
		this.ee = ee;
	}

	public Data copy() {
		Data n = new Data();
		n.setComp(comp);
		n.setEditor(editor);
		n.setAuthor(author);
		n.setTitle(title);
		n.setPages(pages);
		n.setYear(year);
		n.setVolume(volume);
		n.setJournal(journal);
		n.setNumber(number);
		n.setUrl(url);
		n.setEe(ee);
		return n;
	}
}