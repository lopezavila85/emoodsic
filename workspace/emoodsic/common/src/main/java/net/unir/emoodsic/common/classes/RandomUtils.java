/**
 * 
 */
package net.unir.emoodsic.common.classes;

import java.util.List;
import java.util.Random;

import net.unir.emoodsic.common.entities.MusicPrefDimProbItem;

/**
 * @author Álvaro
 *
 */
public final class RandomUtils {

	private RandomUtils() {
		super();
	}
	
	/**
	 * Random integer generator
	 * @param intList
	 * @return
	 */
	public static int getRandomInt(List<Integer> intList) {
		
		Random r = new Random();		
		int index = r.nextInt(intList.size());
		return intList.get(index);
	}
	
	public static String getRandomString(List<String> stringList) {
		
		Random r = new Random();		
		int index = r.nextInt(stringList.size());
		return stringList.get(index);
	}
	
	//http://stackoverflow.com/questions/6409652/random-weighted-selection-in-java

	public static MusicPrefDimProbItem chooseItemOnProbability(List<MusicPrefDimProbItem> items) {
        //double completeWeight = 0.0;
        //for (Item item : items)
        //    completeWeight += item.getWeight();
        //double r = Math.random() * completeWeight;
		
		double r = Math.random();		
        double countWeight = 0.0;
        for (MusicPrefDimProbItem item : items) {
            countWeight += item.getProbability();
            if (countWeight >= r)
                return item;
        }
        //throw new RuntimeException("Should never be shown.");
        return null;
    }
}
