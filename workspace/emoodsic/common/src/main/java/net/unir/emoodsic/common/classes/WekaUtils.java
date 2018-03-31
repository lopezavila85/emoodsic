/**
 * 
 */
package net.unir.emoodsic.common.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.unir.emoodsic.common.entities.BigFiveInventory;
import net.unir.emoodsic.common.entities.MoodCategory;
import net.unir.emoodsic.common.entities.MusicPrefDimProb;
import net.unir.emoodsic.common.entities.MusicPrefDimension;
import net.unir.emoodsic.common.entities.Neighbor;
import net.unir.emoodsic.common.entities.QbmPersonalityCfInfo;
import net.unir.emoodsic.common.entities.Style;
import net.unir.emoodsic.common.entities.User;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.EuclideanDistance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.InstanceComparator;
import weka.core.Instances;
import weka.core.neighboursearch.KDTree;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Reorder;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 * @author Álvaro
 *
 */
public final class WekaUtils {

    /**
     * Class logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(WekaUtils.class);
    
    //private static final String UNKNOWN_CLASS = "Unknown";
    //private static EM mpdClustererEm;	
	//private static MultilayerPerceptron mlpClassifier;
	
	private WekaUtils() {
		super();		
	}

	/**
	 * Creates a MultiLayerPerceptron and classifies existing and new users giving them a probability of pertaining to a certain
	 * Music Preference Dimension
	 * @param train		the existing users, already classified into a Music-Preference dimension
	 * @param evaluate	new users to be classified
	 * @return			a list containing MusicPrefDimProb objects if OK.
	 */
	public static List<MusicPrefDimProb> createMusicPrefDimMLP(List<MusicPrefDimension> train, List<MusicPrefDimension> evaluate,
		double mlpLearningRate, double mlpMomentum, String hiddenLayers) {
		
		List<MusicPrefDimProb> probList = new ArrayList<MusicPrefDimProb>();
		
		// Declare five numeric attributes
        Attribute attrO = new Attribute(BigFiveInventory.OPENNESS);
        Attribute attrC = new Attribute(BigFiveInventory.CONSCIENTIOUSNESS);
        Attribute attrE = new Attribute(BigFiveInventory.EXTRAVERSION);
        Attribute attrA = new Attribute(BigFiveInventory.AGREEABLENESS);
        Attribute attrN = new Attribute(BigFiveInventory.NEUROTICISM);
	
        // Declare the class attribute along with its values
        FastVector fvClassVal = new FastVector(MusicPrefDimension.NUM_DIM);
        fvClassVal.addElement(MusicPrefDimension.REFLECTIVE_COMPLEX);
        fvClassVal.addElement(MusicPrefDimension.INTENSE_REBELLIOUS);
        fvClassVal.addElement(MusicPrefDimension.UPBEAT_CONVENTIONAL);
        fvClassVal.addElement(MusicPrefDimension.ENERGETIC_RHYTHMIC); 
        Attribute attrClass = new Attribute("class", fvClassVal);
        
        // Declare the feature vector
        FastVector fvWekaAttributes = new FastVector(6);
        fvWekaAttributes.addElement(attrO);    
        fvWekaAttributes.addElement(attrC);
        fvWekaAttributes.addElement(attrE);
        fvWekaAttributes.addElement(attrA);
        fvWekaAttributes.addElement(attrN);
        fvWekaAttributes.addElement(attrClass);
        
        // Create an empty training set
        Instances trainSet = new Instances("TrainMLP", fvWekaAttributes, train.size());
        Instances evalSet = new Instances("EvalMLP", fvWekaAttributes, evaluate.size());   
         
       //Create instances
        for (MusicPrefDimension mpd: train) {
        	Instance ins = new Instance(6);
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(0), mpd.getPersTraitO());   
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(1), mpd.getPersTraitC());    
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(2), mpd.getPersTraitE());    
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(3), mpd.getPersTraitA());    
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(4), mpd.getPersTraitN()); 
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(5), mpd.getName());
        	trainSet.add(ins);
        }
        //Set class index
        trainSet.setClassIndex(trainSet.numAttributes() - 1);
        
        for (MusicPrefDimension mpd: evaluate) {
        	Instance ins = new Instance(6);
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(0), mpd.getPersTraitO());   
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(1), mpd.getPersTraitC());    
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(2), mpd.getPersTraitE());    
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(3), mpd.getPersTraitA());    
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(4), mpd.getPersTraitN()); 
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(5), mpd.getName());
        	evalSet.add(ins);
        }
        evalSet.setClassIndex(evalSet.numAttributes() - 1);
  
        try {
        	MultilayerPerceptron mlpClassifier = new MultilayerPerceptron();
        	//Set options
        	mlpClassifier.setLearningRate(mlpLearningRate);
        	mlpClassifier.setMomentum(mlpMomentum);
        	//[150830] Do not normalize
        	mlpClassifier.setNormalizeAttributes(false);
        	//[151004] Hidden layers
        	mlpClassifier.setHiddenLayers(hiddenLayers);
        	
        	mlpClassifier.buildClassifier(trainSet);
        				
			//Check the classification for the training instances to update their probabilities of pertaining
			//to a certain dimension
			for (int i = 0; i < trainSet.numInstances(); i++) {
				Instance insi = trainSet.instance(i);
				MusicPrefDimension mpdi = train.get(i);
				
				double dClass = mlpClassifier.classifyInstance(insi);
				double[] dProbs = mlpClassifier.distributionForInstance(insi);
				//insi.setClassValue(dClass);				
				String insiClass = trainSet.classAttribute().value((int)dClass);
				
				MusicPrefDimProb p = new MusicPrefDimProb();
				//Note: inserted in the objet as -> m.setIdMusicPrefDimension(u.getIdUser());
				p.setIdUser(mpdi.getIdMusicPrefDimension());
				
				//The clusters go in order: RC - IR - UC - ER
				p.setProbRC(dProbs[0]);
				p.setProbIR(dProbs[1]);
				p.setProbUC(dProbs[2]);
				p.setProbER(dProbs[3]);
			
				if (MusicPrefDimension.REFLECTIVE_COMPLEX.equals(insiClass)) {
					p.setIdMusicPrefDimension(MusicPrefDimension.REFLECTIVE_COMPLEX_ID);
				} else if (MusicPrefDimension.INTENSE_REBELLIOUS.equals(insiClass)) {
					p.setIdMusicPrefDimension(MusicPrefDimension.INTENSE_REBELLIOUS_ID);
				} else if (MusicPrefDimension.UPBEAT_CONVENTIONAL.equals(insiClass)) {
					p.setIdMusicPrefDimension(MusicPrefDimension.UPBEAT_CONVENTIONAL_ID);
				} else if (MusicPrefDimension.ENERGETIC_RHYTHMIC.equals(insiClass)) {
					p.setIdMusicPrefDimension(MusicPrefDimension.ENERGETIC_RHYTHMIC_ID);
				}
				probList.add(p);
			}
			
			//Classify the evaluation instances
			for (int i = 0; i < evalSet.numInstances(); i++) {
				Instance insi = evalSet.instance(i);
				MusicPrefDimension mpdi = evaluate.get(i);
				
				double dClass = mlpClassifier.classifyInstance(insi);
				double[] dProbs = mlpClassifier.distributionForInstance(insi);
				//insi.setClassValue(dCluster);				
				String insiClass = evalSet.classAttribute().value((int)dClass);
				
				MusicPrefDimProb p = new MusicPrefDimProb();
				//Note: inserted in the objet as -> m.setIdMusicPrefDimension(u.getIdUser());
				p.setIdUser(mpdi.getIdMusicPrefDimension());
				
				//The clusters go in order
				p.setProbRC(dProbs[0]);
				p.setProbIR(dProbs[1]);
				p.setProbUC(dProbs[2]);
				p.setProbER(dProbs[3]);
			
				if (MusicPrefDimension.REFLECTIVE_COMPLEX.equals(insiClass)) {
					p.setIdMusicPrefDimension(MusicPrefDimension.REFLECTIVE_COMPLEX_ID);
				} else if (MusicPrefDimension.INTENSE_REBELLIOUS.equals(insiClass)) {
					p.setIdMusicPrefDimension(MusicPrefDimension.INTENSE_REBELLIOUS_ID);
				} else if (MusicPrefDimension.UPBEAT_CONVENTIONAL.equals(insiClass)) {
					p.setIdMusicPrefDimension(MusicPrefDimension.UPBEAT_CONVENTIONAL_ID);
				} else if (MusicPrefDimension.ENERGETIC_RHYTHMIC.equals(insiClass)) {
					p.setIdMusicPrefDimension(MusicPrefDimension.ENERGETIC_RHYTHMIC_ID);
				}
				probList.add(p);
			}
			
			//Finally, evaluate the classifier
			//https://weka.wikispaces.com/Generating+classifier+evaluation+output+manually
			//http://www.programcreek.com/java-api-examples/index.php?api=weka.classifiers.Evaluation
////if (evalSet.numInstances() == 0) {
				//Not enough users to update probabilities with MLP --> 10-cross fold validation
				 MultilayerPerceptron mlpCls = new MultilayerPerceptron();
				 Evaluation evalTenFold = new Evaluation(trainSet);
				 Random rand = new Random(1);  // using seed = 1
				 int folds = 10;
				 evalTenFold.crossValidateModel(mlpCls, trainSet, folds, rand);
				 LOG.warn(evalTenFold.toSummaryString());
////}			
		} catch (Exception e) {
			LOG.error(String.format("Error building Weka MLP: %s", e.getMessage()));
			return null;
		}
        
        return probList;
	}
	
	
	/**
	 * Searches for K-nearest neighbors for each user using a KD-tree search algorithm.
	 * @param k			the number of neighbors to search
	 * @param userList	the user list
	 * @return			a list contining Neighbor objects if OK.
	 * @see 			http://weka.sourceforge.net/doc.dev/weka/core/neighboursearch/KDTree.html
	 * @see 			http://fiji.sc/Using_Weka
	 * @see				http://weka.sourceforge.net/doc.dev/weka/core/InstanceComparator.html
	 */
	public static List<Neighbor> searchKnn(int k, List<User> userList) {
		
		List<Neighbor> neighborList = new ArrayList<Neighbor>();
		
		// Declare five numeric attributes
        Attribute attrO = new Attribute(BigFiveInventory.OPENNESS);
        Attribute attrC = new Attribute(BigFiveInventory.CONSCIENTIOUSNESS);
        Attribute attrE = new Attribute(BigFiveInventory.EXTRAVERSION);
        Attribute attrA = new Attribute(BigFiveInventory.AGREEABLENESS);
        Attribute attrN = new Attribute(BigFiveInventory.NEUROTICISM);
	     
	 	// Create vector of the above attributes
        FastVector fvWekaAttributes = new FastVector(5);
        fvWekaAttributes.addElement(attrO);    
        fvWekaAttributes.addElement(attrC);
        fvWekaAttributes.addElement(attrE);
        fvWekaAttributes.addElement(attrA);
        fvWekaAttributes.addElement(attrN);
	 
	    // Create the empty datasets "wekaPoints" with above attributes
	    Instances wekaPoints = new Instances("KNN-KDTree", fvWekaAttributes, 0);

	    for (User u: userList) {
	        // Create empty instance with five attribute values (personality traits)
	        Instance inst = new Instance(5);
            
	        // Set instance's values for each personality trait attribute
	        inst.setValue(attrO, u.getPersTraitO());
	        inst.setValue(attrC, u.getPersTraitC());
	        inst.setValue(attrE, u.getPersTraitE());
	        inst.setValue(attrA, u.getPersTraitA());
	        inst.setValue(attrN, u.getPersTraitN());
	 
	        // Set instance's dataset to be the dataset "wekaPoints"
	        inst.setDataset(wekaPoints);
	             
	        // Add the Instance to Instances
	        wekaPoints.add(inst);
	    }
	    
	    
	    //Set up the KDTree without normalizing values
	    KDTree tree = new KDTree();     
	    
	    try { 
	    	//Instances
	        tree.setInstances(wekaPoints);
	         
	        //Distance
	        final EuclideanDistance df = new EuclideanDistance(wekaPoints);
	        df.setDontNormalize(true);	         
	        tree.setDistanceFunction(df);
	        
	        //Searching the KDTree
	        
	        InstanceComparator insComp = new InstanceComparator();
	        
	        for (int i = 0; i < userList.size(); i++) {
	        	
	        	User u = userList.get(i);
	        	Neighbor nn0 = new Neighbor();
	        	nn0.setIdUser(u.getIdUser());
	        	nn0.setIdNeighbors("");
	        	
	        	Instance currentInstance = wekaPoints.instance(i);
	        	
	        	Instances neighbors = tree.kNearestNeighbours(currentInstance, k);
	        	if (neighbors == null
	        		|| neighbors.numInstances() == 0) {
	        		continue;
	        	}
	        	
	        	//TODO ver si devuelve la misma instancia como vecina (la que es igual)
	        	
	        	//Iterate the neighbours to search for the user ids
	        	StringBuilder stb = new StringBuilder(nn0.getIdNeighbors());
	        	
	        	for (int j = 0; j < neighbors.numInstances(); j++) {	        		
	        		Instance nnj = neighbors.instance(j);
	        		//LOG.warn(String.format("currentInstance <-> nnj [%d]: %f",
	        		//	j, df.distance(currentInstance, nnj)));
	        		
	        		for (int w = 0; w < wekaPoints.numInstances(); w++) {
	        		
	        			Instance insW = wekaPoints.instance(w);
	        			if (insComp.compare(nnj,insW) == 0) {
	        			
	        				//Corresponding user found for the current neighbor	        				
	        				User uw = userList.get(w);
	        				stb.append(uw.getIdUser() + Neighbor.NEIGHBOR_SPLIT);	        				
	        				break;
	        			}	        			
	        		}        		
	        	}
	        	
	        	//Replace the trailing NEIGHBOR_SPLIT character
	        	String str = stb.toString();
	        	if (str.endsWith(Neighbor.NEIGHBOR_SPLIT)) {
	        		str = str.substring(0, str.length() - 1);
	        	}	        	
	        	nn0.setIdNeighbors(str);
        	
	            neighborList.add(nn0);
	        }	        
	    } 
	    catch (Exception e) { 
	    	LOG.error(String.format("Error building Weka KDTree: %s", e.getMessage()));
			return null;
	    }
		return neighborList;		
	}
	
	
	
	
	
	
		
	
	
	
	
	
	
	
	/**
	 * Classify songs into relevant or non-relevant using a Naive Bayes technique with two attributes:
	 * artist and final mood category of the songs
	 * 
	 * @param train 			the training set.
	 * @param eval				the evaluation set.
	 * @param moodCategoryList	the mood category list.
	 * @see https://weka.wikispaces.com/Programmatic+Use
	 * @see http://stackoverflow.com/questions/18658906/how-to-incorporate-weka-naive-bayes-model-into-java-code
	 * @see https://weka.wikispaces.com/Text+categorization+with+Weka
	 * @see http://jmgomezhidalgo.blogspot.com.es/2013/04/a-simple-text-classifier-in-java-with.html
	 * @see https://github.com/jmgomezh/tmweka/blob/master/FilteredClassifier/MyFilteredLearner.java
	 * @see http://www.quora.com/How-is-root-mean-square-error-RMSE-and-classification-related
	 */
	public static void classifyQbmPersonalitySongs(List<QbmPersonalityCfInfo> train, List<QbmPersonalityCfInfo> eval,
		List<MoodCategory> moodCategoryList, List<Style> styleList, double minPctCorrect) {
		
		// Artist attribute
		//Attribute attrArtist = new Attribute("artist", (FastVector) null);
		//Attribute attrArtist = new Attribute("artistCredit");
		
		Attribute attrValence = new Attribute("valence");
		Attribute attrArousal = new Attribute("arousal");
		
        // Music style attribute
        FastVector fvStyle = new FastVector(styleList.size());
        for (Style s: styleList) {
        	fvStyle.addElement(s.getName());
        }
        Attribute attrStyle = new Attribute("style", fvStyle);
        
        // Final mood category attribute
        FastVector fvFinalMoodVal = new FastVector(moodCategoryList.size());
        for (MoodCategory mc: moodCategoryList) {
        	fvFinalMoodVal.addElement(mc.getName());
        }
        Attribute attrFinalMood = new Attribute("finalMood", fvFinalMoodVal);
        
        // Declare the class attribute along with its values
        FastVector fvClassVal = new FastVector(2);
        fvClassVal.addElement(QbmPersonalityCfInfo.NON_RELEVANT_CF);
        fvClassVal.addElement(QbmPersonalityCfInfo.RELEVANT_CF);        
        Attribute attrClass = new Attribute("class", fvClassVal);
        
        // Declare the feature vector
        FastVector fvWekaAttributes = new FastVector(5);
        fvWekaAttributes.addElement(attrValence); 
        fvWekaAttributes.addElement(attrArousal);
        fvWekaAttributes.addElement(attrStyle);
        fvWekaAttributes.addElement(attrFinalMood);
        fvWekaAttributes.addElement(attrClass);
		
        // Create an empty training set
        Instances trainSet = new Instances("TrainNB", fvWekaAttributes, train.size());
        Instances evalSet = new Instances("EvalNB", fvWekaAttributes, eval.size());  
        
        //Create instances
        for (QbmPersonalityCfInfo qpci: train) {
        	Instance ins = new Instance(5);
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(0), qpci.getValence());
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(1), qpci.getArousal());
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(2), qpci.getStyle());
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(3), qpci.getFinalMoodCat()); 
        	if (qpci.getLikertScore() < QbmPersonalityCfInfo.MIN_RELEVANT_SCORE) {
        		ins.setValue((Attribute)fvWekaAttributes.elementAt(4), QbmPersonalityCfInfo.NON_RELEVANT_CF); 
        	} else {
        		ins.setValue((Attribute)fvWekaAttributes.elementAt(4), QbmPersonalityCfInfo.RELEVANT_CF); 
        	}
        	trainSet.add(ins);
        }
        //Set class index
        trainSet.setClassIndex(trainSet.numAttributes() - 1);
        //trainSet.setClassIndex(0);
        
        for (QbmPersonalityCfInfo qpci: eval) {
        	Instance ins = new Instance(5);
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(0), qpci.getValence());
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(1), qpci.getArousal());
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(2), qpci.getStyle());
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(3), qpci.getFinalMoodCat());
        	//Unknown class
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(4), QbmPersonalityCfInfo.NON_RELEVANT_CF);
        	evalSet.add(ins);
        }
        //Set class index
        evalSet.setClassIndex(evalSet.numAttributes() - 1);
        //evalSet.setClassIndex(0);
        
        try {
           	
        	NaiveBayes classifier = new NaiveBayes();
        	classifier.buildClassifier(trainSet);
        	
        	//10-fold croos validation of the classifier
        	Evaluation evalTenFold = new Evaluation(trainSet);
        	Random rand = new Random(1);  // using seed = 1
        	int folds = 10;
        	evalTenFold.crossValidateModel(classifier, trainSet, folds, rand);			 
        	LOG.warn(evalTenFold.toSummaryString());
            
        	//If the correct classified instances in the validation are low, do not continue.
        	if (evalTenFold.pctCorrect() < minPctCorrect) {
        		LOG.warn(String.format("classifyQbmPersonalitySongs: 10-fold validation. Correct instances low [%f] !!!",
        			evalTenFold.pctCorrect()));
        		return;
        	}
        	
			//Classify the evaluation instances
			for (int i = 0; i < evalSet.numInstances(); i++) {
				Instance insi = evalSet.instance(i);
				QbmPersonalityCfInfo qpci = eval.get(i);
				
				double dClass = classifier.classifyInstance(insi);
				double[] dProbs = classifier.distributionForInstance(insi);
				//insi.setClassValue(dCluster);				
				String insiClass = evalSet.classAttribute().value((int)dClass);
				
				if (QbmPersonalityCfInfo.RELEVANT_CF.equals(insiClass)) {
					//Give the maximum value to the song
					qpci.setLikertScore(5);
					//TODO evaluación final
					evalSet.instance(i).setValue((Attribute)fvWekaAttributes.elementAt(4), QbmPersonalityCfInfo.RELEVANT_CF); 
				} else {
					//Non-relevant, assign the minimum
					qpci.setLikertScore(1);
				}
			}
			
			//The evaluation instances (songs recently received) are updated accordingly			
			 Evaluation evalTrainTest = new Evaluation(trainSet);
			 evalTrainTest.evaluateModel(classifier, evalSet);			 
			 LOG.warn(evalTrainTest.toSummaryString());

		} catch (Exception e) {
			LOG.error(String.format("Error building Weka NaiveBayes: %s", e.getMessage()));
		}
	}
	
	/* Cluster EM not working */
	/*
	 * 
	 * @param train
	 * @param evaluate
	 * @return	the new clustered instances
	 * @see https://ianma.wordpress.com/2010/01/16/weka-with-java-eclipse-getting-started/
	 * @see https://svn.cms.waikato.ac.nz/svn/weka/branches/stable-3-6/wekaexamples/src/main/java/wekaexamples/clusterers/OutputClusterDistribution.java
	 *
	public static List<MusicPrefDimProb> createMusicPrefDimClusters(List<MusicPrefDimension> train,
		List<MusicPrefDimension> evaluate) {
		
		//Note: the first FOUR elemens of the train set will be the centroids of the Music
		//Preference dimensions based on Renftfrow's article.
		
		List<MusicPrefDimProb> probList = new ArrayList<MusicPrefDimProb>();
		
		// Declare five numeric attributes
        Attribute attrO = new Attribute(BigFiveInventory.OPENNESS);
        Attribute attrC = new Attribute(BigFiveInventory.CONSCIENTIOUSNESS);
        Attribute attrE = new Attribute(BigFiveInventory.EXTRAVERSION);
        Attribute attrA = new Attribute(BigFiveInventory.AGREEABLENESS);
        Attribute attrN = new Attribute(BigFiveInventory.NEUROTICISM);
	
        // Declare the class attribute along with its values
//        FastVector fvClassVal = new FastVector(MusicPrefDimension.NUM_DIM);
//        fvClassVal.addElement(MusicPrefDimension.REFLECTIVE_COMPLEX);
//        fvClassVal.addElement(MusicPrefDimension.INTENSE_REBELLIOUS);
//        fvClassVal.addElement(MusicPrefDimension.UPBEAT_CONVENTIONAL);
//        fvClassVal.addElement(MusicPrefDimension.ENERGETIC_RHYTHMIC); 
//        fvClassVal.addElement(UNKNOWN_CLASS); 
//        Attribute attrClass = new Attribute("MusicPrefDimension", fvClassVal);
        
        // Declare the feature vector
        FastVector fvWekaAttributes = new FastVector(5);
        fvWekaAttributes.addElement(attrO);    
        fvWekaAttributes.addElement(attrC);
        fvWekaAttributes.addElement(attrE);
        fvWekaAttributes.addElement(attrA);
        fvWekaAttributes.addElement(attrN);
//        fvWekaAttributes.addElement(attrClass);
        
        // Create an empty training set
        Instances trainSet = new Instances("TrainEM", fvWekaAttributes, train.size());
        Instances evalSet = new Instances("EvalEM", fvWekaAttributes, evaluate.size());   
         
       //Create instances
        for (MusicPrefDimension mpd: train) {
        	Instance ins = new Instance(5);
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(0), mpd.getPersTraitO());   
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(1), mpd.getPersTraitC());    
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(2), mpd.getPersTraitE());    
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(3), mpd.getPersTraitA());    
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(4), mpd.getPersTraitN()); 
        	//ins.setValue((Attribute)fvWekaAttributes.elementAt(5), mpd.getName());
        	trainSet.add(ins);
        }
        //Set class index
        //trainSet.setClassIndex(trainSet.numAttributes() - 1);
        
        for (MusicPrefDimension mpd: evaluate) {
        	Instance ins = new Instance(5);
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(0), mpd.getPersTraitO());   
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(1), mpd.getPersTraitC());    
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(2), mpd.getPersTraitE());    
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(3), mpd.getPersTraitA());    
        	ins.setValue((Attribute)fvWekaAttributes.elementAt(4), mpd.getPersTraitN()); 
        	//ins.setValue((Attribute)fvWekaAttributes.elementAt(5), UNKNOWN_CLASS);
        	evalSet.add(ins);
        }
       // evalSet.setClassIndex(evalSet.numAttributes() - 1);
        
        // generate data for clusterer (w/o class)
        //Remove emFilter = new Remove();
        //emFilter.setAttributeIndices("" + (trainSet.classIndex() + 1));        
        
        //Classes to clusters
        try {
        	mpdClustererEm = new EM();
        	//emFilter.setInputFormat(trainSet);       
        	
        	//Instances trainDataClusterer = Filter.useFilter(trainSet, emFilter);
			//Instances evalDataClusterer = Filter.useFilter(evalSet, emFilter);
			
        	mpdClustererEm.setNumClusters(MusicPrefDimension.NUM_DIM);
			//mpdClustererEm.buildClusterer(trainDataClusterer);
        	mpdClustererEm.buildClusterer(trainSet);
			
		    // evaluate clusterer
		    //ClusterEvaluation clusterEval = new ClusterEvaluation();
		    //clusterEval.setClusterer(mpdClustererEm);
			//clusterEval.evaluateClusterer(trainDataClusterer);		   		    
		    // print results
			//LOG.warn(clusterEval.clusterResultsToString());		    
			//double[] priors = mpdClustererEm.clusterPriors();
			//double[] trainAssignments = clusterEval.getClusterAssignments();
			//int miau = mpdClustererEm.clusterInstance(evalSet.instance(0));
			//double[] miauProbs = mpdClustererEm.distributionForInstance(evalSet.instance(0));
			//double[] rcProbs = mpdClustererEm.distributionForInstance(trainSet.instance(0));
			//LOG.warn(String.format("RC[%f] IR[%f] UC[%f] ER[%f]", rcProbs[0], rcProbs[1], rcProbs[2], rcProbs[3]));
        	
			//Obtain the cluster numbers to the four Music Preference Dimensions
			int iRCCluster = mpdClustererEm.clusterInstance(trainSet.instance(0));
			int iIRCluster = mpdClustererEm.clusterInstance(trainSet.instance(1));
			int iUCCluster = mpdClustererEm.clusterInstance(trainSet.instance(2));
			int iERCluster = mpdClustererEm.clusterInstance(trainSet.instance(3));
			
			LOG.warn(String.format("RC[%d] IR[%d] UC[%d] ER[%d]", iRCCluster, iIRCluster, iUCCluster, iERCluster));
			
			//Check the classification for the training instances
			for (int i = MusicPrefDimension.NUM_DIM; i < trainSet.numInstances(); i++) {
				Instance insi = trainSet.instance(i);
				MusicPrefDimension mpdi = train.get(i);
				
				int iCluster = mpdClustererEm.clusterInstance(insi);
				double[] dProbs = mpdClustererEm.distributionForInstance(insi);
				
				MusicPrefDimProb p = new MusicPrefDimProb();
				//Note: inserted in the objet as -> m.setIdMusicPrefDimension(u.getIdUser());
				p.setIdUser(mpdi.getIdMusicPrefDimension());
				p.setProbRC(dProbs[iRCCluster]);
				p.setProbIR(dProbs[iIRCluster]);
				p.setProbUC(dProbs[iUCCluster]);
				p.setProbER(dProbs[iERCluster]);
				
				if (iCluster == iRCCluster) {
					p.setIdMusicPrefDimension(MusicPrefDimension.REFLECTIVE_COMPLEX_ID);
				} else if (iCluster == iIRCluster) {
					p.setIdMusicPrefDimension(MusicPrefDimension.INTENSE_REBELLIOUS_ID);
				} else if (iCluster == iUCCluster) {
					p.setIdMusicPrefDimension(MusicPrefDimension.UPBEAT_CONVENTIONAL_ID);
				} else if (iCluster == iERCluster) {
					p.setIdMusicPrefDimension(MusicPrefDimension.ENERGETIC_RHYTHMIC_ID);
				}				
				probList.add(p);
			}			
			
			//Classify the evaluation instances
			for (int i = 0; i < evalSet.numInstances(); i++) {
				Instance insi = evalSet.instance(i);
				MusicPrefDimension mpdi = evaluate.get(i);
				
				int iCluster = mpdClustererEm.clusterInstance(insi);
				double[] dProbs = mpdClustererEm.distributionForInstance(insi);
				
				MusicPrefDimProb p = new MusicPrefDimProb();
				//Note: inserted in the objet as -> m.setIdMusicPrefDimension(u.getIdUser());
				p.setIdUser(mpdi.getIdMusicPrefDimension());
				p.setProbRC(dProbs[iRCCluster]);
				p.setProbIR(dProbs[iIRCluster]);
				p.setProbUC(dProbs[iUCCluster]);
				p.setProbER(dProbs[iERCluster]);
				
				if (iCluster == iRCCluster) {
					p.setIdMusicPrefDimension(MusicPrefDimension.REFLECTIVE_COMPLEX_ID);
				} else if (iCluster == iIRCluster) {
					p.setIdMusicPrefDimension(MusicPrefDimension.INTENSE_REBELLIOUS_ID);
				} else if (iCluster == iUCCluster) {
					p.setIdMusicPrefDimension(MusicPrefDimension.UPBEAT_CONVENTIONAL_ID);
				} else if (iCluster == iERCluster) {
					p.setIdMusicPrefDimension(MusicPrefDimension.ENERGETIC_RHYTHMIC_ID);
				}				
				probList.add(p);
			}
		} catch (Exception e) {
			LOG.error(String.format("Error building EM clusterer: %s", e.getMessage()));
			return null;
		}
        
        return probList;
	}
	*/
}
