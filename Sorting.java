package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may need it to implement fastSort

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) <0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }
    
    
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {
    	// ADD YOUR CODE HERE
    	
    	ArrayList URL_list = new ArrayList();
    	URL_list.addAll(results.keySet());
    	
    	return merge_sort (URL_list, results);
    }
    
    
    private static <K, V extends Comparable> ArrayList<K> merge_sort(ArrayList<K> URL_list, HashMap<K, V> results){

    	if(URL_list.size() == 1){
    		
    		return URL_list;
		}
    	else{
    		
    		int mid = ((URL_list.size()) / 2);
    		ArrayList<K> firstHalf = new ArrayList<>();
    		
    		for(int i = 0; i < mid; i++){
    			firstHalf.add(URL_list.get(i));
			}
    		
			ArrayList<K> secondHalf = new ArrayList<>();
			
			for(int i = mid; i < URL_list.size(); i++){
				secondHalf.add(URL_list.get(i));
			}
			firstHalf = merge_sort(firstHalf, results);
			
			secondHalf = merge_sort(secondHalf, results);
			
			return merging(firstHalf, secondHalf, results);
		}
	}
    
    
	private static <K, V extends Comparable> ArrayList<K> merging (ArrayList<K> firstHalf, ArrayList<K> secondHalf, HashMap<K, V> results){

    	ArrayList<K> sortedURLS = new ArrayList<>();
    	
    	while(!firstHalf.isEmpty() && !secondHalf.isEmpty()){
    		
    		K temp1 = firstHalf.get(0);
    		K temp2 = secondHalf.get(0);
    		
    		if(results.get(firstHalf.get(0)).compareTo(results.get(secondHalf.get(0))) < 0){
    			sortedURLS.add(secondHalf.remove(0));
			}
    		
    		else{
    			sortedURLS.add(firstHalf.remove(0));
			}
		}
    	
    	while(!firstHalf.isEmpty()){
    		sortedURLS.add( firstHalf.remove(0));
		}
    	
    	while(!secondHalf.isEmpty()){
    		sortedURLS.add( secondHalf.remove(0));
		}
    	
    	return sortedURLS;
	}
}