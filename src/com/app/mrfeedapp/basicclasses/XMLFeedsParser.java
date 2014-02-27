package com.app.mrfeedapp.basicclasses;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLFeedsParser {
	
	public List<RSSNew> getNoticias(String uri){
		List<RSSNew> noticias = new ArrayList<RSSNew>();
		
		try{
			URL url=new URL("http://es.engadget.com/rss.xml");
			//URL url=new URL(uri);
			String ficheroSalida=url.getFile();//obtener el nombre del fichero para luego 
			//crear otro que se llame igual donde guardaremos nuestro código

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(url.openStream());
			
			Element root = doc.getDocumentElement();//obtener el elemento raiz
			root.normalize();
			NodeList hijos = root.getElementsByTagName("item");//lista de nodos hijos
			
			File salida1=new File(ficheroSalida.substring(1));//creación del fichero donde guardaremos el código
			String html="";//String donde guardaremos todos los elementos de los items, separados por "\n"
			for (int i = 0; i < hijos.getLength(); i++) {
				Element hijo = (Element) hijos.item(i);//para cada elemento hijo obtenemos el titulo, el link, la descripcion el autor y la fecha si la tiene
				String title=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("title").item(0));
				String link=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("link").item(0));
				String description=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("description").item(0));
				String author=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("dc:creator").item(0));
				Date pubDate=null;
				NodeList fechas = hijo.getElementsByTagName("pubDate");
				if(fechas != null){//para la fecha uso el mismo formato de conversión que se usa en RSSItem.java
					String pubDate1=XMLUtils.getTextTrim((Element) fechas.item(0));
					String pattern = "EEE, d MMM yyyy hh:mm:ss Z";
					pubDate=new SimpleDateFormat(pattern, Locale.ENGLISH).parse(pubDate1);
				}
				//crear el RSSItem
				RSSNew rs= new RSSNew(title, link, description, author, pubDate);
				//vamos concatenando en la variable html el resultado del metodo toHTML() de la clase RSSItem.java
				html=html+rs.toHTML()+"\n";
				
			}
			//leemos el string html y vamos escribiendo su resultado en el fichero que 
			//habiamos creado con anterioridad (con la codificación adecuada) y que se 
			//llama como el fichero contenido en la pagina web de la que obtenemos todo 
			//el contenido
			DataInputStream in1=new DataInputStream(new ByteArrayInputStream(html.getBytes("ISO-8859-1")));
			FileOutputStream out1=new FileOutputStream(salida1);
			byte[] buff1 = new byte[10024];
			int leidos1 = in1.read(buff1);
			while (leidos1 != -1) {
				//System.out.write(buff, 0, leidos);
				out1.write(buff1, 0, leidos1);
				leidos1 = in1.read(buff1);
			}
			
		}catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ParserConfigurationException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (SAXException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		} catch (ParseException e5) {
			// TODO Auto-generated catch block
			e5.printStackTrace();
		}
		
		return noticias;
	}
	
}
