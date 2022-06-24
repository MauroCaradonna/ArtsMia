package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private Graph<ArtObject, DefaultWeightedEdge> grafo;
	private ArtsmiaDAO dao;
	private HashMap<Integer, ArtObject> idMap;
	
	public Model() {
		dao = new ArtsmiaDAO();
		idMap = new HashMap<Integer, ArtObject>();
	}
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungere i vertici
		//1. -> Recupero tutti gli ArtObject
		//2. -> Li inserisco nel grafo
		dao.listObjects(idMap);
		Graphs.addAllVertices(grafo, idMap.values());
		
/*		// Aggiungere gli archi
		// APPROCCIO 1
		// -> Doppio ciclo for sui vertici e controllo se sono collegati
		for(ArtObject a1 : this.grafo.vertexSet()) {
			for(ArtObject a2 : this.grafo.vertexSet()) {
				if(!a1.equals(a2) && !this.grafo.containsEdge(a1, a2)) {
					int peso = dao.getPeso(a1,a2);
					if(peso > 0) {
						Graphs.addEdge(this.grafo, a1, a2, peso);
					}
				}
			}
		}
		System.out.println("GRAFO CREATO\n# VERTICI: "+grafo.vertexSet().size()+"\n# ARCHI: "+grafo.edgeSet().size());
*/
		//APPROCCIO 3 da sare sempre, soprattutto all'esame
		for(Adiacenza a : dao.getAdiacenze()) {
				Graphs.addEdge(this.grafo, idMap.get(a.getId1()), idMap.get(a.getId2()), a.getPeso());
		}
		System.out.println("GRAFO CREATO\n# VERTICI: "+grafo.vertexSet().size()+"\n# ARCHI: "+grafo.edgeSet().size());
	}
	
}
