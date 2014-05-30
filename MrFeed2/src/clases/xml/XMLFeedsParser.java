package clases.xml;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import clases.rss.RSSImage;
import clases.rss.RSSNew;
import clases.rss.RepoRSS;



import android.graphics.Bitmap;


public class XMLFeedsParser {
	
	public ArrayList<RepoRSS> getNoticias(String [] uris){
		ArrayList<RepoRSS> listaRepos = new ArrayList<RepoRSS>();
		RepoRSS repo;
		HashMap<String, RSSNew> news;
		ArrayList<RSSNew> noticias;
		ArrayList<String> categorias;
		URL url;
		
		for (String uri : uris) {
			categorias = new ArrayList<String>();
			noticias = new ArrayList<RSSNew>();
			news = new HashMap<String, RSSNew>();
			repo = new RepoRSS();
			
			try{
				url = new URL(uri);
				
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				////System.out.println(url.toString());
				Document doc = db.parse(new InputSource(url.openStream()));
				
				////System.out.println("openStream parseado");
				
				Element root = doc.getDocumentElement();//obtener el elemento raiz
				root.normalize();
				
				////System.out.println("VOY A LEER EL CHANNEL");
				//Leer nodo channel
				NodeList canales = root.getElementsByTagName("channel");
				if (canales.getLength()>0){
					Element channel = (Element) canales.item(0);
					
					String chanTitle = XMLUtils.getTextTrim((Element) channel.getElementsByTagName("title").item(0));				
					String chanLink = XMLUtils.getTextTrim((Element) channel.getElementsByTagName("link").item(0));
					String chanDesc = XMLUtils.getTextTrim((Element) channel.getElementsByTagName("description").item(0));
					////System.out.println("PRIMERO DENTRO DE CHANNEL");
//					Element chImg = (Element) channel.getElementsByTagName("image").item(0);
//					RSSImage chanImg = new RSSImage("","","", null);
//					chanImg.setUrl(XMLUtils.getTextTrim((Element) chImg.getElementsByTagName("url").item(0)));
//					chanImg.setTitle(XMLUtils.getTextTrim((Element) chImg.getElementsByTagName("title").item(0)));
//					chanImg.setLink(XMLUtils.getTextTrim((Element) chImg.getElementsByTagName("link").item(0)));
//					System.out.println("SEGUNDO DENTRO DE CHANNEL");
					repo.setTit(chanTitle);
					repo.setLnk(chanLink);
					repo.setDesc(chanDesc);
//					repo.setChannelImg(chanImg);
				}
				////System.out.println("VOY A LEER LOS ITEM");
				//Leer nodos item
				NodeList hijos = root.getElementsByTagName("item");//lista de nodos hijos
				for (int i = 0; i < hijos.getLength(); i++) {
					Element hijo = (Element) hijos.item(i);//para cada elemento hijo obtenemos el titulo, el link, la descripcion el autor y la fecha si la tiene
					String title=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("title").item(0));
					////System.out.println("TITULO");
					String link=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("link").item(0));
					////System.out.println("LINK");
					String description=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("description").item(0));
					////System.out.println("DESCRIPTION");
					String author = "";
					try {
						author=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("dc:creator").item(0));
					} catch (Exception e) {
						// TODO: handle exception
						author=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("author").item(0));
					}
					////System.out.println("AUTHOR");
					int cats = hijo.getElementsByTagName("category").getLength();
					////System.out.println("ANTER DEL FOR DE CATEGORIAS");
					categorias = new ArrayList<String>();
					for (int j = 0; j < cats; j++){
						////System.out.println("CATEGORIAS");
						categorias.add(XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("category").item(j)));
					}
					StringTokenizer st = new StringTokenizer(description, "\"");
					boolean encontrado = false;
					String trozo = "";
					while (st.hasMoreTokens() && !encontrado){
						trozo = st.nextToken();
						if (trozo.contains("src=")){
							encontrado=true;
							trozo = st.nextToken();
						}
					}
					////System.out.println("PRIMERO DENTRO DE ITEM");
					trozo = trozo.substring(0, trozo.length());
					RSSImage ri = null;
					Bitmap imagen = null;
					if (!encontrado){
						ri = new RSSImage("", "", "http://4.bp.blogspot.com/-7pftmuC8T4E/TsI-MyWgp7I/AAAAAAAAAD8/3cH-YaVo9mo/s340/icono-twitter-1.png", imagen);
					}else{						
						ri = new RSSImage("","",trozo, imagen);
					}
					Date pubDate=null;
					NodeList fechas = hijo.getElementsByTagName("pubDate");
					if(fechas != null){//para la fecha uso el mismo formato de conversión que se usa en RSSItem.java
						String pubDate1=XMLUtils.getTextTrim((Element) fechas.item(0));
						String pattern = "EEE, d MMM yyyy hh:mm:ss Z";
						pubDate=new SimpleDateFormat(pattern, Locale.ENGLISH).parse(pubDate1);
					}
					////System.out.println("SEGUNDO DENTRO DE ITEM");
					//crear el RSSItem
					RSSNew rs= new RSSNew(repo.getTit(), title, description, link, ri, author, pubDate, categorias, false);
					//guardo las noticias en el TAD
					noticias.add(rs);
					news.put(rs.getLink(), rs);
				}	
				repo.setNews(news);
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
			listaRepos.add(repo);
		}
		return listaRepos;
	}
	
	public RepoRSS getNoticias(String uri){
		ArrayList<String> categorias;
		ArrayList<RSSNew> noticias = new ArrayList<RSSNew>();
		HashMap<String, RSSNew> news = new HashMap<String, RSSNew>();
		RepoRSS repo = new RepoRSS();
		
		try{
			//URL url=new URL("http://es.engadget.com/rss.xml");
			//URL url = new URL("http://www.bbc.co.uk/mundo/ultimas_noticias/index.xml");
			URL url=new URL(uri);
			//String ficheroSalida=url.getFile();//obtener el nombre del fichero para luego 
			//crear otro que se llame igual donde guardaremos nuestro código

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			////System.out.println(url.toString());
			Document doc = db.parse(new InputSource(url.openStream()));
			
			////System.out.println("openStream parseado");
			
			Element root = doc.getDocumentElement();//obtener el elemento raiz
			root.normalize();
			
			////System.out.println("VOY A LEER EL CHANNEL");
			//Leer nodo channel
			NodeList canales = root.getElementsByTagName("channel");
			if (canales.getLength()>0){
				Element channel = (Element) canales.item(0);
				
				String chanTitle = XMLUtils.getTextTrim((Element) channel.getElementsByTagName("title").item(0));				
				String chanLink = XMLUtils.getTextTrim((Element) channel.getElementsByTagName("link").item(0));
				String chanDesc = XMLUtils.getTextTrim((Element) channel.getElementsByTagName("description").item(0));
				////System.out.println("PRIMERO DENTRO DE CHANNEL");
//				Element chImg = (Element) channel.getElementsByTagName("image").item(0);
//				RSSImage chanImg = new RSSImage("","","", null);
//				chanImg.setUrl(XMLUtils.getTextTrim((Element) chImg.getElementsByTagName("url").item(0)));
//				chanImg.setTitle(XMLUtils.getTextTrim((Element) chImg.getElementsByTagName("title").item(0)));
//				chanImg.setLink(XMLUtils.getTextTrim((Element) chImg.getElementsByTagName("link").item(0)));
//				System.out.println("SEGUNDO DENTRO DE CHANNEL");
				repo.setTit(chanTitle);
				repo.setLnk(chanLink);
				repo.setDesc(chanDesc);
//				repo.setChannelImg(chanImg);
			}
			////System.out.println("VOY A LEER LOS ITEM");
			//Leer nodos item
			NodeList hijos = root.getElementsByTagName("item");//lista de nodos hijos
			for (int i = 0; i < hijos.getLength(); i++) {
				Element hijo = (Element) hijos.item(i);//para cada elemento hijo obtenemos el titulo, el link, la descripcion el autor y la fecha si la tiene
				String title=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("title").item(0));
				////System.out.println("TITULO");
				String link=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("link").item(0));
				////System.out.println("LINK");
				String description=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("description").item(0));
				////System.out.println("DESCRIPTION");
				String author = "";
				try {
					author=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("dc:creator").item(0));
				} catch (Exception e) {
					// TODO: handle exception
					author=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("author").item(0));
				}
				////System.out.println("AUTHOR");
				int cats = hijo.getElementsByTagName("category").getLength();
				////System.out.println("ANTER DEL FOR DE CATEGORIAS");
				categorias = new ArrayList<String>();
				for (int j = 0; j < cats; j++){
					////System.out.println("CATEGORIAS");
					categorias.add(XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("category").item(j)));
				}
				StringTokenizer st = new StringTokenizer(description, "\"");
				boolean encontrado = false;
				String trozo = "";
				while (st.hasMoreTokens() && !encontrado){
					trozo = st.nextToken();
					if (trozo.contains("src=")){
						encontrado=true;
						trozo = st.nextToken();
					}
				}
				////System.out.println("PRIMERO DENTRO DE ITEM");
				trozo = trozo.substring(0, trozo.length());
				RSSImage ri = null;
				Bitmap imagen = null;
				if (!encontrado){
					ri = new RSSImage("", "", "http://4.bp.blogspot.com/-7pftmuC8T4E/TsI-MyWgp7I/AAAAAAAAAD8/3cH-YaVo9mo/s340/icono-twitter-1.png", imagen);
				}else{						
					ri = new RSSImage("","",trozo, imagen);
				}
				Date pubDate=null;
				NodeList fechas = hijo.getElementsByTagName("pubDate");
				if(fechas != null){//para la fecha uso el mismo formato de conversión que se usa en RSSItem.java
					String pubDate1=XMLUtils.getTextTrim((Element) fechas.item(0));
					String pattern = "EEE, d MMM yyyy hh:mm:ss Z";
					pubDate=new SimpleDateFormat(pattern, Locale.ENGLISH).parse(pubDate1);
				}
				////System.out.println("SEGUNDO DENTRO DE ITEM");
				//crear el RSSItem
				RSSNew rs= new RSSNew(repo.getTit(), title, description, link, ri, author, pubDate, categorias, false);
				//guardo las noticias en el TAD
				noticias.add(rs);
				news.put(rs.getLink(), rs);
			}	
			repo.setNews(news);
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
		return repo;
	}
	
	public RepoRSS getNuevasNoticias(String uri, ArrayList<RSSNew> viejasNoticias){
		ArrayList<String> categorias;
		ArrayList<RSSNew> noticias = new ArrayList<RSSNew>();
		Map<String, RSSNew> news = new HashMap<String, RSSNew>();
		RepoRSS repo = new RepoRSS();
		
		try{
			//URL url=new URL("http://es.engadget.com/rss.xml");
			//URL url = new URL("http://www.bbc.co.uk/mundo/ultimas_noticias/index.xml");
			URL url = new URL(uri);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			System.out.println(url.toString());
			Document doc = db.parse(new InputSource(url.openStream()));
			
			System.out.println("openStream parseado");
			
			Element root = doc.getDocumentElement();//obtener el elemento raiz
			root.normalize();
			
			//Leer nodo channel
			NodeList canales = root.getElementsByTagName("channel");
			if (canales.getLength()>0){
				Element channel = (Element) canales.item(0);
				
				String chanTitle = XMLUtils.getTextTrim((Element) channel.getElementsByTagName("title").item(0));				
				String chanLink = XMLUtils.getTextTrim((Element) channel.getElementsByTagName("link").item(0));
				String chanDesc = XMLUtils.getTextTrim((Element) channel.getElementsByTagName("description").item(0));
				
				Element chImg = (Element) channel.getElementsByTagName("image").item(0);
				RSSImage chanImg = new RSSImage("","","", null);
				chanImg.setUrl(XMLUtils.getTextTrim((Element) chImg.getElementsByTagName("url").item(0)));
				chanImg.setTitle(XMLUtils.getTextTrim((Element) chImg.getElementsByTagName("title").item(0)));
				chanImg.setLink(XMLUtils.getTextTrim((Element) chImg.getElementsByTagName("link").item(0)));
				
				repo.setTit(chanTitle);
				repo.setLnk(chanLink);
				repo.setDesc(chanDesc);
				repo.setChannelImg(chanImg);
			}
			
			//Leer nodos item
			NodeList hijos = root.getElementsByTagName("item");//lista de nodos hijos
			
			int i = 0;
			boolean coincide=false;
			while(i < hijos.getLength() && !coincide){
				Element hijo = (Element) hijos.item(i);//para cada elemento hijo obtenemos el titulo, el link, la descripcion el autor y la fecha si la tiene
				String title=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("title").item(0));
				if (viejasNoticias.get(0).getTitle().equals(title)){
					coincide=true;
				}else{
					String link=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("link").item(0));
					String description=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("description").item(0));
					String author=XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("dc:creator").item(0));
					int cats = hijo.getElementsByTagName("category").getLength();
					categorias = new ArrayList<String>();
					for (int j = 0; j < cats; j++){
						categorias.add(XMLUtils.getTextTrim((Element) hijo.getElementsByTagName("category").item(j)));
					}
					StringTokenizer st = new StringTokenizer(description, "\"");
					boolean encontrado = false;
					String trozo = "";
					while (st.hasMoreTokens() && !encontrado){
						trozo = st.nextToken();
						if (trozo.contains("src=")){
							encontrado=true;
							trozo = st.nextToken();
						}
					}
					trozo = trozo.substring(0, trozo.length());
					RSSImage ri = null;
					Bitmap imagen = null;
					if (!encontrado){
						ri = new RSSImage("", "", "http://4.bp.blogspot.com/-7pftmuC8T4E/TsI-MyWgp7I/AAAAAAAAAD8/3cH-YaVo9mo/s340/icono-twitter-1.png", imagen);
					}else{						
						ri = new RSSImage("","",trozo, imagen);
					}
					Date pubDate=null;
					NodeList fechas = hijo.getElementsByTagName("pubDate");
					if(fechas != null){//para la fecha uso el mismo formato de conversión que se usa en RSSItem.java
						String pubDate1=XMLUtils.getTextTrim((Element) fechas.item(0));
						String pattern = "EEE, d MMM yyyy hh:mm:ss Z";
						pubDate=new SimpleDateFormat(pattern, Locale.ENGLISH).parse(pubDate1);
					}
					//crear el RSSItem
					RSSNew rs= new RSSNew(repo.getTit(), title, description, link, ri, author, pubDate, categorias, false);
					//guardo las noticias en el ArrayList
					noticias.add(rs);
					news.put(rs.getLink(), rs);
				}
				i++;
			}
			for (RSSNew rssNew : viejasNoticias) {
				noticias.add(rssNew);
				news.put(rssNew.getLink(), rssNew);
			}
			repo.setNews(news);
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
		
		return repo;
	}
	
}
