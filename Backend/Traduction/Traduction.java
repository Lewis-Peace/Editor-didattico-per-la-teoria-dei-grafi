package Backend.Traduction;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Traduction {
    public static String language;

    public static void setLanguage(String language) {
        Traduction.language = language;
    }

    public static String translate(String string) {
        File file = new File("./Backend/Traduction/language.xml");
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList languages = doc.getElementsByTagName("language");
            for (int i = 0; i < languages.getLength(); i++) {
                Element candidateLanguage = (Element) languages.item(i);
                if (candidateLanguage.getAttribute("lang").equals(language)){
                    NodeList words = ((Element) languages.item(i)).getElementsByTagName("str");
                    for (int j = 0; j < words.getLength(); j++) {
                        Element candidateWord = (Element) words.item(j);
                        if (candidateWord.getAttribute("name").equals(string)){
                            return candidateWord.getTextContent();
                        }
                    }
                }
            }
            return "Error on loading translated string";
        } catch (Exception e) {
            return "Error on loading translated string";
        }
    }
}
