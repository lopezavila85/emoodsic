/**
 * 
 */
package net.unir.emoodsic.common.classes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import net.unir.emoodsic.common.entities.BigFiveInventory;
import net.unir.emoodsic.common.entities.MusicPrefDimProb;
import net.unir.emoodsic.common.entities.MusicPrefDimension;
import net.unir.emoodsic.common.entities.User;

/**
 * @author Álvaro
 *
 */
public final class PersonalityUtils {

	private PersonalityUtils() {
		super();
	}
	
	//Methods to compute personality
	
	public static void calculatePersonality(User u, final BigFiveInventory bfi) {
		
		//Recode items
		BigFiveInventory bfiRecoded = PersonalityUtils.recodeItems(bfi);
		
		u.setPersTraitO(scaleOpenness(bfiRecoded));
		u.setPersTraitC(scaleConscientiousness(bfiRecoded));
		u.setPersTraitE(scaleExtraversion(bfiRecoded));
		u.setPersTraitA(scaleAgreeableness(bfiRecoded));
		u.setPersTraitN(scaleNeuroticism(bfiRecoded));
	}
	
	/**
	 * 
	 * @param u
	 * @param mpdList
	 * @see MusicPrefDimension
	 */
	public static void calculateMusicPrefDimension(User u, List<MusicPrefDimension> mpdList) {
		
		double[][] wData = new double[4][5];
		
		//4x5
		for (int i = 0; i < 4; i++) {
			wData[i][0] = mpdList.get(i).getPersTraitO();
			wData[i][1] = mpdList.get(i).getPersTraitC();
			wData[i][2] = mpdList.get(i).getPersTraitE();
			wData[i][3] = mpdList.get(i).getPersTraitA();
			wData[i][4] = mpdList.get(i).getPersTraitN();
		}
		RealMatrix w = MatrixUtils.createRealMatrix(wData);

		//5x1
		//The personality in range [1,5] has been previously scaled to [-1,1] using: y = 0.5x - 1.5.
		double[] psData = new double[5];
		psData[0] = u.getPersTraitO();
		psData[1] = u.getPersTraitC();
		psData[2] = u.getPersTraitE();
		psData[3] = u.getPersTraitA();
		psData[4] = u.getPersTraitN();		
		
		//4x1
		double[] mp = w.operate(psData);
		
		//Reflective and Complex
		u.setMusicPrefRC(mp[0]);
		//Intense and Rebellious
		u.setMusicPrefIR(mp[1]);
		//Upbeat and Conventional
		u.setMusicPrefUC(mp[2]);
		//Energetic and Rhythmic
		u.setMusicPrefER(mp[3]);
		
		//Search which is the higher value in order to assign initial music dimension
		int idMpd = -1;
		double dMax = -1.0;
		
		for (int i = 0; i < MusicPrefDimension.NUM_DIM; i++) {
			if (mp[i] > dMax) {
				//The dimension Ids go from 1 to 4.
				idMpd = i + 1;
				dMax = mp[i];
			}
		}		
		u.setIdMusicPrefDimension(idMpd);
	}
	
	
	public static void calculateInitialMusicPrefDimProb(User u, MusicPrefDimProb mpdp) {
		
		if (u == null
			|| mpdp == null
			|| u.getIdMusicPrefDimension() <= 0) {
			return;
		}
		
		int idMpd = u.getIdMusicPrefDimension();
		mpdp.setIdMusicPrefDimension(idMpd);
		mpdp.setIdUser(u.getIdUser());
		
		if (idMpd == MusicPrefDimension.REFLECTIVE_COMPLEX_ID) {
			mpdp.setProbRC(1);
			mpdp.setProbIR(0);
			mpdp.setProbUC(0);
			mpdp.setProbER(0);

		} else if (idMpd == MusicPrefDimension.INTENSE_REBELLIOUS_ID) {
			mpdp.setProbRC(0);
			mpdp.setProbIR(1);
			mpdp.setProbUC(0);
			mpdp.setProbER(0);
			
		} else if (idMpd == MusicPrefDimension.UPBEAT_CONVENTIONAL_ID) {
			mpdp.setProbRC(0);
			mpdp.setProbIR(0);
			mpdp.setProbUC(1);
			mpdp.setProbER(0);
			
		} else if (idMpd == MusicPrefDimension.ENERGETIC_RHYTHMIC_ID) {
			mpdp.setProbRC(0);
			mpdp.setProbIR(0);
			mpdp.setProbUC(0);
			mpdp.setProbER(1);
		}		
	}
	
	/**
	 * Scales a personality trait averaged value in [1,5] range
     * into [-1,1] using: y = 0.5x - 1.5.
	 * @param x
	 * @return
	 */
	private static double scalePersTrait(double x) {
		
		double y = (double)(0.5*x - 1.5);
		if (y > 1.0) {
			y = 1;
		} else if (y < -1.0) {
			y = -1;
		}		
		return y;
	}
	
	private static BigFiveInventory recodeItems(BigFiveInventory bfi) {
		
		BigFiveInventory bfiRecoded = new BigFiveInventory();
				
		bfiRecoded.setIdUser(bfi.getIdUser());
		
		bfiRecoded.setLikertScore1(bfi.getLikertScore1());		
		bfiRecoded.setLikertScore2(reverseScore(bfi.getLikertScore2()));		
		bfiRecoded.setLikertScore3(bfi.getLikertScore3());
		bfiRecoded.setLikertScore4(bfi.getLikertScore4());
		bfiRecoded.setLikertScore5(bfi.getLikertScore5());
		
		bfiRecoded.setLikertScore6(reverseScore(bfi.getLikertScore6()));
		bfiRecoded.setLikertScore7(bfi.getLikertScore7());
		bfiRecoded.setLikertScore8(reverseScore(bfi.getLikertScore8()));		
		bfiRecoded.setLikertScore9(reverseScore(bfi.getLikertScore9()));
		bfiRecoded.setLikertScore10(bfi.getLikertScore10());
		
		bfiRecoded.setLikertScore11(bfi.getLikertScore11());
		bfiRecoded.setLikertScore12(reverseScore(bfi.getLikertScore12()));
		bfiRecoded.setLikertScore13(bfi.getLikertScore13());
		bfiRecoded.setLikertScore14(bfi.getLikertScore14());
		bfiRecoded.setLikertScore15(bfi.getLikertScore15());
		
		bfiRecoded.setLikertScore16(bfi.getLikertScore16());
		bfiRecoded.setLikertScore17(bfi.getLikertScore17());
		bfiRecoded.setLikertScore18(reverseScore(bfi.getLikertScore18()));
		bfiRecoded.setLikertScore19(bfi.getLikertScore19());
		bfiRecoded.setLikertScore20(bfi.getLikertScore20());
		
		bfiRecoded.setLikertScore21(reverseScore(bfi.getLikertScore21()));
		bfiRecoded.setLikertScore22(bfi.getLikertScore22());
		bfiRecoded.setLikertScore23(reverseScore(bfi.getLikertScore23()));		
		bfiRecoded.setLikertScore24(reverseScore(bfi.getLikertScore24()));
		bfiRecoded.setLikertScore25(bfi.getLikertScore25());
		
		bfiRecoded.setLikertScore26(bfi.getLikertScore26());
		bfiRecoded.setLikertScore27(reverseScore(bfi.getLikertScore27()));
		bfiRecoded.setLikertScore28(bfi.getLikertScore28());
		bfiRecoded.setLikertScore29(bfi.getLikertScore29());
		bfiRecoded.setLikertScore30(bfi.getLikertScore30());
		
		bfiRecoded.setLikertScore31(reverseScore(bfi.getLikertScore31()));
		bfiRecoded.setLikertScore32(bfi.getLikertScore32());
		bfiRecoded.setLikertScore33(bfi.getLikertScore33());
		bfiRecoded.setLikertScore34(reverseScore(bfi.getLikertScore34()));		
		bfiRecoded.setLikertScore35(reverseScore(bfi.getLikertScore35()));
		
		bfiRecoded.setLikertScore36(bfi.getLikertScore36());
		bfiRecoded.setLikertScore37(reverseScore(bfi.getLikertScore37()));
		bfiRecoded.setLikertScore38(bfi.getLikertScore38());
		bfiRecoded.setLikertScore39(bfi.getLikertScore39());
		bfiRecoded.setLikertScore40(bfi.getLikertScore40());
		
		bfiRecoded.setLikertScore41(reverseScore(bfi.getLikertScore41()));
		bfiRecoded.setLikertScore42(bfi.getLikertScore42());
		bfiRecoded.setLikertScore43(reverseScore(bfi.getLikertScore43()));
		bfiRecoded.setLikertScore44(bfi.getLikertScore44());
		
		return bfiRecoded;
	}	
	
	private static int reverseScore(int score) {
		
		if (score == 1) {
			return 5;
		} else if (score == 2) {
			return 4;
		} else if (score == 3) {
			return 3;
		} else if (score == 4) {
			return 2;
		} else {
			//score == 5
			return 1;
		}	
	}
	
	private static double scaleOpenness(BigFiveInventory bfi) {
		
		List<Integer> scores = new ArrayList<Integer>();
		
		//5-10-15-20-25-30-35-40-41-44
		scores.add(bfi.getLikertScore5());
		scores.add(bfi.getLikertScore10());
		scores.add(bfi.getLikertScore15());
		scores.add(bfi.getLikertScore20());
		scores.add(bfi.getLikertScore25());
		scores.add(bfi.getLikertScore30());
		scores.add(bfi.getLikertScore35());
		scores.add(bfi.getLikertScore40());
		scores.add(bfi.getLikertScore41());
		scores.add(bfi.getLikertScore44());
		
		return scalePersTrait(calculateAvgScore(scores));
	}

	private static double scaleConscientiousness(BigFiveInventory bfi) {
		
		List<Integer> scores = new ArrayList<Integer>();

		//3-8-13-18-23-28-33-38-43
		scores.add(bfi.getLikertScore3());
		scores.add(bfi.getLikertScore8());
		scores.add(bfi.getLikertScore13());
		scores.add(bfi.getLikertScore18());
		scores.add(bfi.getLikertScore23());
		scores.add(bfi.getLikertScore28());
		scores.add(bfi.getLikertScore33());
		scores.add(bfi.getLikertScore38());
		scores.add(bfi.getLikertScore43());
	
		return scalePersTrait(calculateAvgScore(scores));
	}
	
	private static double scaleExtraversion(BigFiveInventory bfi) {
		
		List<Integer> scores = new ArrayList<Integer>();

		//1-6-11-16-21-26-31-36
		scores.add(bfi.getLikertScore1());
		scores.add(bfi.getLikertScore6());
		scores.add(bfi.getLikertScore11());
		scores.add(bfi.getLikertScore16());
		scores.add(bfi.getLikertScore21());
		scores.add(bfi.getLikertScore26());
		scores.add(bfi.getLikertScore31());
		scores.add(bfi.getLikertScore36());
	
		return scalePersTrait(calculateAvgScore(scores));
	}
	
	private static double scaleAgreeableness(BigFiveInventory bfi) {
		
		List<Integer> scores = new ArrayList<Integer>();

		//2-7-12-17-22-27-32-37-42
		scores.add(bfi.getLikertScore2());
		scores.add(bfi.getLikertScore7());
		scores.add(bfi.getLikertScore12());
		scores.add(bfi.getLikertScore17());
		scores.add(bfi.getLikertScore22());
		scores.add(bfi.getLikertScore27());
		scores.add(bfi.getLikertScore32());
		scores.add(bfi.getLikertScore37());
		scores.add(bfi.getLikertScore42());
	
		return scalePersTrait(calculateAvgScore(scores));
	}
	
	private static double scaleNeuroticism(BigFiveInventory bfi) {
		
		List<Integer> scores = new ArrayList<Integer>();

		//4-9-14-19-24-29-34-39
		scores.add(bfi.getLikertScore4());
		scores.add(bfi.getLikertScore9());
		scores.add(bfi.getLikertScore14());
		scores.add(bfi.getLikertScore19());
		scores.add(bfi.getLikertScore24());
		scores.add(bfi.getLikertScore29());
		scores.add(bfi.getLikertScore34());
		scores.add(bfi.getLikertScore39());
	
		return scalePersTrait(calculateAvgScore(scores));
	}

	private static double calculateAvgScore(List<Integer> scores) {
	  if(!scores.isEmpty()
	    && scores.size() > 0) {
		  
		Integer sum = 0;
	    for (Integer score : scores) {
	        sum += score;
	    }
	    return sum.doubleValue() / scores.size();
	  }
	  return (double)1;
	}
}
