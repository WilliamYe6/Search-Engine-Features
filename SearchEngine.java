package finalproject;

import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}
	
	/* 
	 * This does a graph traversal of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 * 
	 * 	This method will fit in about 30-50 lines (or less)
	 */
	public void crawlAndIndex(String url) throws Exception {
		// TODO : Add code here
		
		ArrayList<String> new_queue = new ArrayList<>();
		new_queue.add(url);
		
		internet.addVertex(url);
		
		internet.setVisited(url, true);
		
		while(!new_queue.isEmpty()){
			
			String current = new_queue.remove(0);
			
			ArrayList<String> content = parser.getContent(current);
			
			ArrayList<String> url_link = parser.getLinks(current);
			
			for(String word : content){
				String w = word.toLowerCase();
				
				if( wordIndex.containsKey(w) &&!wordIndex.get(w).contains(current)){
					
					ArrayList<String> hold = wordIndex.get(w);
					hold.add(current);
				}
				else {
					ArrayList<String> hold = new ArrayList<>();
					
					hold.add(current);
					wordIndex.put(w, hold);
				}
			}
			for(String links : url_link){
				
				if(!internet.getVisited(links)) {
					
					internet.addVertex(links);
					internet.setVisited(links, true);
					new_queue.add(links);
				}
				internet.addEdge(current, links);
			}

		}
		
	}
	
	
	
	/* 
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex(). 
	 * To implement this method, refer to the algorithm described in the 
	 * assignment pdf. 
	 * 
	 * This method will probably fit in about 30 lines.
	 */
	public void assignPageRanks(double epsilon) {
		// TODO : Add code here
		ArrayList <Double> initializing = initializer();
		
		double diff;
		
		boolean convergenceCheck = false;
		
		
		
		
		ArrayList<Double> newRanks;

		while(!convergenceCheck){
			newRanks = computeRanks(internet.getVertices());

			for(int i = 0; i < newRanks.size(); i++){
				internet.setPageRank(internet.getVertices().get(i), newRanks.get(i));
			}
			int counter = 0;
			
			for(int i = 0; i < newRanks.size(); i++){
				
				diff = Math.abs(initializing.get(i) - newRanks.get(i));
				
				if(diff < epsilon){
					counter++;
				}
			}
			if(counter == newRanks.size()){
				convergenceCheck = true;
			}
			initializing = newRanks;
		}
		
	}

	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 */
	
	
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		// TODO : Add code here
		
		//creates a new ArrayList for the computed ranks
		ArrayList<Double> cRanks = new ArrayList<>();

		//set dampening factor to 0.5
		double dFactor = 0.5;

		for(int i = 0; i < vertices.size(); i++){

			ArrayList<String> innerEdges = internet.getEdgesInto(vertices.get(i));
			double innerBracket = 0;

			for(String edges : innerEdges){

				double innerEdgeRank = internet.getPageRank(edges);
				int outterDegree = internet.getOutDegree(edges);
				innerBracket += innerEdgeRank / outterDegree;
				
			}

			cRanks.add(i, (1-dFactor) + dFactor*innerBracket);
			
		}
		return cRanks;
	}

	
	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 * This method should take about 25 lines of code.
	 */
	
	
	private ArrayList<Double> initializer(){
		
		ArrayList<Double> init = new ArrayList<>();
		
		for(String vertice : internet.getVertices()){
			
			if(internet.getPageRank(vertice) == 0){
				internet.setPageRank(vertice, 1.0);
			}
			init.add(internet.getPageRank(vertice));
		}
		return init;

	}

	public ArrayList<String> getResults(String query) {
		// TODO: Add code here
		
		String search_queue = query.toLowerCase();
		
		
		
		ArrayList<String> link_content = wordIndex.get(search_queue);
		HashMap<String, Double> finalMap = new HashMap<>();

		for(String URL_link : link_content){
			finalMap.put(URL_link, internet.getPageRank(URL_link));
		}
		return Sorting.fastSort(finalMap);
		
		
	}
}