package customDatatypes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Marks {
//	This class encapsulates the basic functionality for the perCourseGrades Maps
	
//	For each assignment or quiz or exam or whatever means a faculty member can come up with to evaluate the students, we have the respective 
//	mark that the student received for that particular evaluation.
//	for example <"Final", "98.5"> 
//	this can be used to calculate the final grade of each student (check CourseOffering.calculateFinalGrades() for further details)
	Map<String, Double> evalStrategy;
	Iterator<Entry<String, Double>> entrySet;
	Entry<String, Double> currentEntry;

	public void addToEvalStrategy(String examOrAssignement, Double mark){
		if(evalStrategy == null)
			evalStrategy = new HashMap<String, Double>();
			evalStrategy.put(examOrAssignement, mark);
	}

	public void initializeIterator(){
		entrySet = evalStrategy.entrySet().iterator();
	}
	
	public boolean hasNext(){
		return entrySet.hasNext();
	}
	
	public Entry<String, Double> getNextEntry(){
		if(entrySet.hasNext()){
			return entrySet.next();
		}
		else
			return null;
	}
	
	public Double getValueWithKey(String key){
		return evalStrategy.get(key);
	}
	
	public void next(){
		if(entrySet.hasNext()){
			currentEntry = entrySet.next();
		}
	}
	
	public String getCurrentKey(){
		return currentEntry.getKey();
	}
	
	public Double getCurrentValue(){
		return currentEntry.getValue();
	}
	
}
