/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
package Control;

/**
 *
 * @author FERRETO
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Logica.*;
import java.util.Iterator;

public class Control {

	public static void main(String[] args) {
		// Define el path donde se encuentra el archivo
		String filePath = "src/AcesoADatos/datos.xml";

		// Se crea una instancia de la clase 'file' y se le envia de parametro el path
		// del XML
		File xmlFile = new File(filePath);

		// La funcionalidad de DocumentBuilderFactory y DocumentBuilder es leer el
		// archivo XML
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nodeList = doc.getElementsByTagName("Actividad");
			NodeList nodeList2 = doc.getElementsByTagName("Relacion");
			// now XML is loaded as Document in memory, lets convert it to Object List
			List<Actividad> actList = new ArrayList<Actividad>();
			for (int i = 0; i < nodeList.getLength(); i++) {
				actList.add(getActividad(nodeList.item(i)));
			}
			List<Relacion> relList = new ArrayList<Relacion>();
			for (int i = 0; i <= nodeList.getLength(); i++) {
				relList.add(getRelacion(nodeList2.item(i)));
			}

			// lets print Employee and Relation list information
			/*
			 * for (Actividad act : actList) { System.out.println(act.toString()); }
			 * 
			 * for (Relacion rel : relList) { System.out.println(rel.toString()); }
			 */
			
			setRelacion(relList, actList);
			calculos(actList);

			for (Actividad act : actList) {
				System.out.println(act.toString());
			}

		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		}

		// System.out.println("Hola");
	}

	private static Actividad getActividad(Node node) {
		// XMLReaderDOM domReader = new XMLReaderDOM();
		Actividad act = new Actividad();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			act.setId((getTagValue("id", element)));
			act.setDuracion((Integer.parseInt(getTagValue("duracion", element))));
			act.setX((Integer.parseInt(getTagValue("x", element))));
			act.setY((Integer.parseInt(getTagValue("y", element))));
		}

		return act;
	}

	private static Relacion getRelacion(Node node) {
		// XMLReaderDOM domReader = new XMLReaderDOM();
		Relacion rel = new Relacion();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			rel.setActividad((getTagValue("actividad", element)));
			rel.setSucesor((getTagValue("sucesor", element)));
		}

		return rel;
	}

	private static String getTagValue(String tag, Element element) {
		String node = element.getAttribute(tag);
		return node;
	}

	private static void setSucesores(List<Actividad> actList, List<Relacion> relList) {
		Actividad actividad;
		for (Relacion relacion : relList) {
			actividad = getActividadPredecesora(actList, relacion.getSucesor());
			actividad.setPredecesor(getActividadSucesora(actList, relacion.getActividad()));
		}
	}

	private static Actividad getActividadSucesora(List<Actividad> actList, String sucesor) {
		for (Actividad actividad : actList) {
			if (actividad.getId().equals(sucesor)) {
				return actividad;
			}
		}
		return null;

	}

	private static Actividad getActividadPredecesora(List<Actividad> actList, String predecesor) {
		for (Actividad actividad : actList) {
			if (actividad.getId().equals(predecesor)) {
				return actividad;
			}
		}
		return null;

	}

	// Para cada actividad, si el ID es igual al atributo actividad, asignele el
	// sucesor contenido en la relacion.
	private static void setRelacion(List<Relacion> relList, List<Actividad> actList) {
		for (Actividad actividad : actList) {
			for (Relacion relacion : relList) {
				if (relacion.getActividad().equals(actividad.getId())) {
					Actividad sucesor = getActividadSucesora(actList, relacion.getSucesor());
					actividad.setSucesor(sucesor);
				}
			}

		}
		setSucesores(actList, relList);
	}

	// define 0 para las actividades que son el inicio
	private static void definirInicio(List<Actividad> listaActividades) {

		for (Actividad actividad : listaActividades) {

			if (actividad.getPredecesor() == null) {
				actividad.setInicioCercano(0);
				actividad.setTerminoCercano(actividad.getDuracion());
			}
		}

	}

	// selecciona de los predecesores el mayor
	private static Actividad buscarMayor(List<Actividad> listaPredecesor) {
		Actividad aux = new Actividad();
		
		for (int i = 0; i < listaPredecesor.size(); i++) {
			if (listaPredecesor.get(i).getTerminoCercano() > aux.getTerminoCercano())
				aux = listaPredecesor.get(i);
		}
		return aux;
	}

	private static void calcularCercanos(List<Actividad> listaActividades) {

		for (Actividad actividad : listaActividades) {
			actividad.setInicioCercano(buscarMayor(actividad.getPredecesor()).getTerminoCercano());
			actividad.setTerminoCercano(actividad.getInicioCercano() + actividad.getDuracion());
		}

	}

	private static void definirFinal(List<Actividad> listaActividades) {

		for (Actividad actividad : listaActividades) {

			if (actividad.getSucesor().isEmpty()) {

				actividad.setTerminoLejano(actividad.getTerminoCercano());
				actividad.setInicioLejano(actividad.getTerminoLejano() - actividad.getDuracion());
			}
		}

	}

	// selecciona de los predecesores el menor
	private static Actividad buscarMenor(List<Actividad> listaSucesores) {
		Actividad aux = listaSucesores.get(0);

		for (int i = 1; i < listaSucesores.size(); i++) {
			if (listaSucesores.get(i).getInicioLejano() < aux.getInicioLejano()) {
				aux = listaSucesores.get(i);
			}
		}

		return aux;
	}

	private static void calcularLejanos(List<Actividad> listaActividades) {

		for (int i = listaActividades.size() - 1; i >= 0; i--) {
			if (!listaActividades.get(i).getSucesor().isEmpty()) {			
				listaActividades.get(i).setTerminoLejano(buscarMenor(listaActividades.get(i).getSucesor()).getInicioLejano());
				listaActividades.get(i).setInicioLejano(listaActividades.get(i).getTerminoLejano() - listaActividades.get(i).getDuracion());
			}

		}

	}

	private static void calculos(List<Actividad> listaActividades) {
		definirInicio(listaActividades);
		calcularCercanos(listaActividades);
		definirFinal(listaActividades);
		calcularLejanos(listaActividades);
	}
}
