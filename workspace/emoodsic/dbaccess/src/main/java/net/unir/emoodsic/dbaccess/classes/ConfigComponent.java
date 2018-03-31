package net.unir.emoodsic.dbaccess.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Álvaro
 *
 */
@Component("configComponent")
public class ConfigComponent {

    /**
     * Class logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ConfigComponent.class);
    
    /**
     * The default number of neighbors to be searched by the KD Tree algorithm
     */
    private static final int KDTREE_KNN_DEFAULT = 3;
    
    /**
     * The minimum user to consider the MultiLayer Perceptron for Music-Preference dimension classification.
     */
    private static final int MLP_MIN_USERS_DEFAULT = 100;
    
    private static final double MLP_LEARNING_RATE_DEFAULT = 0.3;
    
    private static final double MLP_MOMENTUM_DEFAULT = 0.2;
    
    private static final String MLP_HIDDEN_LAYERS_DEFAULT = "a";
    
    private static final double NB_MIN_PCT_CORRECT_DEFAULT = 0.6;
    
    /**
     * The K-Nearest Neighbors to be computed by the KDTree algorithm
     */
    private int kdTreeKnn;
    
    /**
     * The minimum number of users in order to
     */
    private int mlpMinUsers;
    
    private double mlpLearningRate;
    
    private double mlpMomentum;
    
    /**
     * The hidden layers to use by the MLP classifier.
     */
    private String mlpHiddenLayers;
    
    /**
     * The minimum percent of correct classified instances to apply on-line Naive Bayes classifier
     */
    private double nbMinPctCorrect;
    
    public ConfigComponent() {
    	super();
    	this.mlpMinUsers = MLP_MIN_USERS_DEFAULT;
    	this.setMlpLearningRate(MLP_LEARNING_RATE_DEFAULT);
    	this.setMlpMomentum(MLP_MOMENTUM_DEFAULT);
    	this.mlpHiddenLayers = MLP_HIDDEN_LAYERS_DEFAULT;
    	this.kdTreeKnn = KDTREE_KNN_DEFAULT;
    	this.nbMinPctCorrect = NB_MIN_PCT_CORRECT_DEFAULT;
    }

	/**
	 * @return the mlpLearningRate
	 */
	public double getMlpLearningRate() {
		return mlpLearningRate;
	}

	/**
	 * @param mlpLearningRate the mlpLearningRate to set
	 */
	public void setMlpLearningRate(double mlpLearningRate) {
		this.mlpLearningRate = mlpLearningRate;
	}

	/**
	 * @return the mlpMomentum
	 */
	public double getMlpMomentum() {
		return mlpMomentum;
	}

	/**
	 * @param mlpMomentum the mlpMomentum to set
	 */
	public void setMlpMomentum(double mlpMomentum) {
		this.mlpMomentum = mlpMomentum;
	}

	/**
	 * @return the kdTreeKnn
	 */
	public int getKdTreeKnn() {
		return kdTreeKnn;
	}

	/**
	 * @param kdTreeKnn the kdTreeKnn to set
	 */
	public void setKdTreeKnn(int kdTreeKnn) {
		this.kdTreeKnn = kdTreeKnn;
	}

	/**
	 * @return the mlpMinUsers
	 */
	public int getMlpMinUsers() {
		return mlpMinUsers;
	}

	/**
	 * @param mlpMinUsers the mlpMinUsers to set
	 */
	public void setMlpMinUsers(int mlpMinUsers) {
		this.mlpMinUsers = mlpMinUsers;
	}

	/**
	 * @return the nbMinPctCorrect
	 */
	public double getNbMinPctCorrect() {
		return nbMinPctCorrect;
	}

	/**
	 * @param nbMinPctCorrect the nbMinPctCorrect to set
	 */
	public void setNbMinPctCorrect(double nbMinPctCorrect) {
		this.nbMinPctCorrect = nbMinPctCorrect;
	}

	/**
	 * @return the mlpHiddenLayers
	 */
	public String getMlpHiddenLayers() {
		return mlpHiddenLayers;
	}

	/**
	 * @param mlpHiddenLayers the mlpHiddenLayers to set
	 */
	public void setMlpHiddenLayers(String mlpHiddenLayers) {
		this.mlpHiddenLayers = mlpHiddenLayers;
	}    
}
