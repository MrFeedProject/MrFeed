package clases.xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XMLUtils {

  /**
   * Devuelve el contenido textual de un elemento DOM, eliminando espacios en
   * blanco por delante y por detrás.
   *
   * @param elto Element
   * @return String
   */
  public static String getTextTrim(Element elto) {
    StringBuffer content = new StringBuffer();
    NodeList contentE = elto.getChildNodes();
    int i = 0;
    while (contentE.item(i) != null &&
           (contentE.item(i).getNodeType() == Node.TEXT_NODE || contentE.item(i).getNodeType() == Node.CDATA_SECTION_NODE)) {
      content.append( ( (Text) contentE.item(i)).getNodeValue());
      i++;
    }
    return content.toString().trim();
  }

  /**
   * Escribe un arbol XML por un canal de tipo Writer. Es posible especificar si
   * se desea indentar los elementos anidados. Usa una codificación ISO-8859-1.
   *
   * @param doc Documento XML a escribir
   * @param indenting si se desea indentar los elementos anidados
   * @param w canal de salida donde se escribirá el documento XML
   */
//  public static void write(Document doc, boolean indenting, java.io.Writer w) {
//    String encoding = "ISO-8859-1";
//
//    try {
//      OutputFormat of = new OutputFormat(doc, encoding, indenting);
//      XMLSerializer serial = new XMLSerializer(w, of);
//      Element root = doc.getDocumentElement();
//      if (root != null)
//        serial.serialize(root);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }

  /**
   * Escribe un arbol XML por un canal de tipo OutputStream. Es posible
   * especificar si se desea indentar los elementos anidados. Usa una
   * codificación ISO-8859-1
   *
   * @param doc Documento XML a escribir
   * @param indenting si se desea indentar los elementos anidados
   * @param w canal de salida donde se escribirá el documento XML
   */
//  public static void write(Document doc, boolean indenting, java.io.OutputStream w) {
//    String encoding = "ISO-8859-1";
//    try {
//      write(doc, indenting, new OutputStreamWriter(w,encoding));
//    } catch (UnsupportedEncodingException e) {
//      write(doc, indenting, new OutputStreamWriter(w));
//    }
//  }

  /**
   * Escribe un arbol XML por un canal de tipo Writer. Es posible especificar si
   * se desea indentar los elementos anidados. Usa una codificación ISO-8859-1.
   *
   * @param doc Documento XML a escribir
   * @param indenting si se desea indentar los elementos anidados
   * @param w canal de salida donde se escribirá el documento XML
   */
//  public static void write(Element elto, boolean indenting, java.io.Writer w) {
//    String encoding = "ISO-8859-1";
//    try {
//      OutputFormat of = new OutputFormat(elto.getOwnerDocument(), encoding, indenting);
//      XMLSerializer serial = new XMLSerializer(w, of);
//      serial.serialize(elto);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }

  /**
   * Escribe un arbol XML por un canal de tipo OutputStream. Es posible
   * especificar si se desea indentar los elementos anidados. Usa una
   * codificación ISO-8859-1
   *
   * @param doc Documento XML a escribir
   * @param indenting si se desea indentar los elementos anidados
   * @param w canal de salida donde se escribirá el documento XML
   */
//  public static void write(Element elto, boolean indenting, java.io.OutputStream w) {
//    String encoding = "ISO-8859-1";
//    try {
//      write(elto, indenting, new OutputStreamWriter(w,encoding));
//    } catch (UnsupportedEncodingException e) {
//      write(elto, indenting, new OutputStreamWriter(w));
//    }
//  }
}
