
/*
 * @author Sanidhya Singal 2015085
 * @author Pranav Nambiar 2015063
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class parseHelp {
	static TreeMap<String, String> kFind = new TreeMap<String, String>(Collections.reverseOrder());

	public static void main(String argv[]) {

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			String toSearch = "organisation";
			DefaultHandler handler = new DefaultHandler() {
				boolean authorname = false;
				boolean wwwname = false;
				int count = 0;
				String authr;

				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {
					if (qName.equalsIgnoreCase("WWW")) {
						wwwname = true;
					}

					if (qName.equalsIgnoreCase("AUTHOR") && wwwname == true) {
						authorname = true;
					}

				}

				@Override
				public void endElement(String uri, String localName, String qName) throws SAXException {
					if (qName.equalsIgnoreCase("www")) {
						count = 0;
					}
				}

				@Override
				public void characters(char ch[], int start, int length) throws SAXException {

					if (authorname) {
						if (count == 0) {
							authr = new String(ch, start, length);
							count++;
						}
						kFind.put(new String(ch, start, length), authr);
						authorname = false;
					}
				}

			};

			saxParser.parse("dblp.xml", handler);
			System.out.println("yowsa");
			Map<String, String> rm = kFind.descendingMap();
			Iterator iterator = rm.keySet().iterator();

			while (iterator.hasNext()) {
				String key = iterator.next().toString();
				String value = rm.get(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TreeMap<String, String> getTreeMap() {
		return kFind;
	}
}
