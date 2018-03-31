/**
 * 
 */
package net.unir.emoodsic.common.classes;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.unir.emoodsic.common.entities.MoodCategory;
import net.unir.emoodsic.common.entities.QbmPlaylist;
import net.unir.emoodsic.common.entities.QbmPlaylistInfo;

/**
 * @author Álvaro
 *
 */
public final class QbmPlaylistUtils {

	private QbmPlaylistUtils() {
		super();
	}
	
	public static void sortSongs(QbmPlaylist qp, List<QbmPlaylistInfo> playlist,
		MoodCategory initialMoodCat, MoodCategory finalMoodCat) {
		
		//Three cases depending on equal or different initial and final mood categories.
		if (initialMoodCat.getIdMoodCategory() == finalMoodCat.getIdMoodCategory()) {
			sortSongsEqualMoodCategory(qp, playlist, initialMoodCat);
		
		} else {
			sortSongsDifferentMoodCategory(qp, playlist, initialMoodCat, finalMoodCat);
		}	
	}
	
	
	private static void sortSongsEqualMoodCategory(final QbmPlaylist qp,
		final List<QbmPlaylistInfo> playlist, final MoodCategory moodCat) {
		
		// 1 Inc | /|  2 Inc |\ | 
		// 3 Dec |/ |  4 Dec | \|
		//(1, 'LRDU'),
		//(2, 'RLDU'),
		//(3, 'RLUD'),
		//(4, 'LRUD');
		
		//Determine randomly if the values will be sorted increasingly or decreasingly
		List<Integer> intList = new ArrayList<Integer>();
		intList.add(1);
		intList.add(2);
		intList.add(3);
		intList.add(4);
		
		int iOrder = RandomUtils.getRandomInt(intList);				
		double initX = 0;
		double initY = 0;
		double finalX = 0;
		double finalY = 0;
		
		if (iOrder == 1) {
			initX = moodCat.getMinValence();
			initY = moodCat.getMinArousal();
			finalX = moodCat.getMaxValence();
			finalY = moodCat.getMaxArousal();

		} else if (iOrder == 2) {
			initX = moodCat.getMaxValence();
			initY = moodCat.getMinArousal();
			finalX = moodCat.getMinValence();
			finalY = moodCat.getMaxArousal();
			
		} else if (iOrder == 3) {
			initX = moodCat.getMaxValence();
			initY = moodCat.getMaxArousal();
			finalX = moodCat.getMinValence();
			finalY = moodCat.getMinArousal();			
		
		} else {
			initX = moodCat.getMinValence();
			initY = moodCat.getMaxArousal();
			finalX = moodCat.getMaxValence();
			finalY = moodCat.getMinArousal();
		}
		
		qp.setIdSortOrder(iOrder);
		sortSongsByPlaylistDistance(playlist, initX, initY, finalX, finalY);
	}

	
	
	private static void sortSongsDifferentMoodCategory(final QbmPlaylist qp, 
		List<QbmPlaylistInfo> playlist, final MoodCategory initialMoodCat, final MoodCategory finalMoodCat) {
		
		// 1 Inc | /|  2 Inc |\ | 
		// 3 Dec |/ |  4 Dec | \|
		//(1, 'LRDU'),
		//(2, 'RLDU'),
		//(3, 'RLUD'),
		//(4, 'LRUD');
		
		//Determine which are the initial and final points.
		int iOrder = 0;

		if (initialMoodCat.getMinValence() == finalMoodCat.getMinValence()) {			
			
			if (initialMoodCat.getMaxArousal() < finalMoodCat.getMaxArousal()) {
				//[150916] There are two special cases when the min valence is equal
				List<Integer> intList = new ArrayList<Integer>();
				intList.add(1);
				intList.add(2);				
				iOrder = RandomUtils.getRandomInt(intList);
			} else {
				List<Integer> intList = new ArrayList<Integer>();
				intList.add(3);
				intList.add(4);				
				iOrder = RandomUtils.getRandomInt(intList);
			}
			//If arousal is equal it is the same category, which is considered in other method.
			
		} else if (initialMoodCat.getMinValence() > finalMoodCat.getMinValence()) {
			
			if (initialMoodCat.getMaxArousal() == finalMoodCat.getMaxArousal()) {
				//[150916] There are two special cases when the max arousal is equal
				List<Integer> intList = new ArrayList<Integer>();
				intList.add(2);
				intList.add(3);				
				iOrder = RandomUtils.getRandomInt(intList);

			} else if (initialMoodCat.getMaxArousal() < finalMoodCat.getMaxArousal()) {
				iOrder = 2;
			} else {
				iOrder = 3;
			}			
		} else if (initialMoodCat.getMinValence() < finalMoodCat.getMinValence()) {
			
			if (initialMoodCat.getMaxArousal() == finalMoodCat.getMaxArousal()) {
				//[150916] There are two special cases when the max arousal is equal
				List<Integer> intList = new ArrayList<Integer>();
				intList.add(1);
				intList.add(4);				
				iOrder = RandomUtils.getRandomInt(intList);

			} else if (initialMoodCat.getMaxArousal() < finalMoodCat.getMaxArousal()) {
				iOrder = 1;
			} else {
				iOrder = 4;
			}
		}
		
		double initX = 0;
		double initY = 0;
		double finalX = 0;
		double finalY = 0;
		
		if (iOrder == 1) {
			initX = initialMoodCat.getMinValence();
			initY = initialMoodCat.getMinArousal();
			finalX = finalMoodCat.getMaxValence();
			finalY = finalMoodCat.getMaxArousal();
		
		} else if (iOrder == 2) {
			initX = initialMoodCat.getMaxValence();
			initY = initialMoodCat.getMinArousal();
			finalX = finalMoodCat.getMinValence();
			finalY = finalMoodCat.getMaxArousal();
			
		} else if (iOrder == 3) {
			initX = initialMoodCat.getMaxValence();
			initY = initialMoodCat.getMaxArousal();
			finalX = finalMoodCat.getMinValence();
			finalY = finalMoodCat.getMinArousal();
			
		} else {
			//4
			initX = initialMoodCat.getMinValence();
			initY = initialMoodCat.getMaxArousal();
			finalX = finalMoodCat.getMaxValence();
			finalY = finalMoodCat.getMinArousal();
		}
		
		qp.setIdSortOrder(iOrder);
		sortSongsByPlaylistDistance(playlist, initX, initY, finalX, finalY);
	}
	
	/**
	 * 
	 * @param playlist
	 * @param initX
	 * @param initY
	 * @param finalX
	 * @param finalY
	 * TODO update song's arousal and valence values with one on the line between init and final???
	 */
	private static void sortSongsByPlaylistDistance(List<QbmPlaylistInfo> playlist, double initX, double initY,
		double finalX, double finalY) {
		
		boolean clampToSegment = true;
		Point2D pFinal = new Point2D.Double(finalX, finalY);
		
		for (QbmPlaylistInfo song: playlist) {
			
			double px = song.getValence();
			double py = song.getArousal();
			
			if (px < 0 || px > 1 || py < 0 || py > 1) {
				//Consider the maximum distance
				song.setPlaylistDistance(QbmPlaylistInfo.MAX_PLAYLIST_DISTANCE);
				continue;
			}
			
			Point2D pSong = CommonUtils.nearestPointOnLine(initX, initY, finalX, finalY, px, py, clampToSegment, null);
			song.setPlaylistDistance(pFinal.distance(pSong));
		}
		
		//Finally, sort the items
		Collections.sort(playlist);
	}	
	
	public static double getAvgTasteWeight(List<Double> tasteList) {
		
		double avgTasteWeight = 0.5;
		
		//http://stackoverflow.com/questions/10516287/find-the-average-within-variable-number-of-doubles
		if (tasteList.size() > 0) {
			Collections.sort(tasteList);
			
			double countTotal = 0;
			for (Double number : tasteList) {
			    countTotal += number;
			}
			avgTasteWeight = countTotal / tasteList.size();
		}
		return avgTasteWeight;
	}
}
