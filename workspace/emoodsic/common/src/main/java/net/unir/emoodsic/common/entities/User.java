/**
 * 
 */
package net.unir.emoodsic.common.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Álvaro
 *
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6280206955230062238L;
	
	private int idUser;
	
	private String email;
	
	private String encryptedPassword;
	
	private String firstName;
	
	private String lastName;
	
	private char gender;
	
	private int age;
	
	private int idEducation;
	
	private int idMusicKnowledge;
	
    /**
     * List of favorite artists.
     * 
     * @note It marks a member variable not to be serialized when it is persisted to 
     * streams of bytes. When an object is transferred through the network, the object needs to be 'serialized'.
     *  Serialization converts the object state to serial bytes. Those bytes are sent over the network and the object
     *  is recreated from those bytes. Member variables marked by the java transient keyword are not transferred, 
     *  they are lost intentionally.
     */
    private transient List<FavArtist> favArtists;
	
    /**
     * List of favorite styles.
     */
    private transient List<FavStyle> favStyles;
    
    /**
     * Openness to new experience.
     * Apertura a la experiencia (Apertura al cambio)
     */
	private double persTraitO;
	
	/**
	 * Conscientiousness
	 * Responsabilidad
	 */
	private double persTraitC;

	/**
	 * Extraversion
	 * Extraversión
	 */
	private double persTraitE;
	
	/**
	 * Agreeableness
	 * Cordialidad, amabilidad, afabilidad
	 */
	private double persTraitA;
	
	/**
	 * Neuroticism
	 * Inestabilidad emocional o neuroticismo
	 */
	private double persTraitN;
	
	
	private int numberSongsPlaylist;
	
	/**
	 * Reflective and Complex
	 */
	private double musicPrefRC;
	
	/**
	 * Intense and Rebellious
	 */
	private double musicPrefIR;
	
	/**
	 * Upbeat and Conventional
	 */
	private double musicPrefUC;
	
	/**
	 * Energetic and Rhythm
	 */
	private double musicPrefER;
	
	
	private int idMusicPrefDimension;
	
	/**
	 * Determines whether the user is a real human being or it is fake,
	 * which means that it has been inserted by development purposes.
	 */
	private boolean fake;
	
	public User() {
		super();
		
		this.age = 0;
		this.setEmail("");
		this.setEncryptedPassword("");
		this.setFirstName("");
		this.setGender('\0');
		this.idEducation = 1;
		this.idMusicKnowledge = 1;	

		this.setLastName("");
		this.numberSongsPlaylist = 10;

		this.setFavArtists(new ArrayList<FavArtist>());
		this.setFavStyles(new ArrayList<FavStyle>());
		this.fake = false;
	}


	/**
	 * @return the idUser
	 */
	public int getIdUser() {
		return idUser;
	}


	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the encryptedPassword
	 */
	public String getEncryptedPassword() {
		return encryptedPassword;
	}


	/**
	 * @param encryptedPassword the encryptedPassword to set
	 */
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the gender
	 */
	public char getGender() {
		return gender;
	}


	/**
	 * @param gender the gender to set
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}


	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}


	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the numberSongsPlaylist
	 */
	public int getNumberSongsPlaylist() {
		return numberSongsPlaylist;
	}


	/**
	 * @param numberSongsPlaylist the numberSongsPlaylist to set
	 */
	public void setNumberSongsPlaylist(int numberSongsPlaylist) {
		this.numberSongsPlaylist = numberSongsPlaylist;
	}


	/**
	 * @return the idEducation
	 */
	public int getIdEducation() {
		return idEducation;
	}


	/**
	 * @param idEducation the idEducation to set
	 */
	public void setIdEducation(int idEducation) {
		this.idEducation = idEducation;
	}


	/**
	 * @return the idMusicKnowledge
	 */
	public int getIdMusicKnowledge() {
		return idMusicKnowledge;
	}


	/**
	 * @param idMusicKnowledge the idMusicKnowledge to set
	 */
	public void setIdMusicKnowledge(int idMusicKnowledge) {
		this.idMusicKnowledge = idMusicKnowledge;
	}


	/**
	 * @return the favArtists
	 */
	public List<FavArtist> getFavArtists() {
		return favArtists;
	}


	/**
	 * @param favArtists the favArtists to set
	 */
	public void setFavArtists(List<FavArtist> favArtists) {
		this.favArtists = favArtists;
	}


	/**
	 * @return the favStyles
	 */
	public List<FavStyle> getFavStyles() {
		return favStyles;
	}


	/**
	 * @param favStyles the favStyles to set
	 */
	public void setFavStyles(List<FavStyle> favStyles) {
		this.favStyles = favStyles;
	}


	/**
	 * @return the persTraitO
	 */
	public double getPersTraitO() {
		return persTraitO;
	}


	/**
	 * @param persTraitO the persTraitO to set
	 */
	public void setPersTraitO(double persTraitO) {
		this.persTraitO = persTraitO;
	}


	/**
	 * @return the persTraitC
	 */
	public double getPersTraitC() {
		return persTraitC;
	}


	/**
	 * @param persTraitC the persTraitC to set
	 */
	public void setPersTraitC(double persTraitC) {
		this.persTraitC = persTraitC;
	}


	/**
	 * @return the persTraitE
	 */
	public double getPersTraitE() {
		return persTraitE;
	}


	/**
	 * @param persTraitE the persTraitE to set
	 */
	public void setPersTraitE(double persTraitE) {
		this.persTraitE = persTraitE;
	}


	/**
	 * @return the persTraitA
	 */
	public double getPersTraitA() {
		return persTraitA;
	}


	/**
	 * @param persTraitA the persTraitA to set
	 */
	public void setPersTraitA(double persTraitA) {
		this.persTraitA = persTraitA;
	}


	/**
	 * @return the persTraitN
	 */
	public double getPersTraitN() {
		return persTraitN;
	}


	/**
	 * @param persTraitN the persTraitN to set
	 */
	public void setPersTraitN(double persTraitN) {
		this.persTraitN = persTraitN;
	}


	/**
	 * @return the musicPrefRC
	 */
	public double getMusicPrefRC() {
		return musicPrefRC;
	}


	/**
	 * @param musicPrefRC the musicPrefRC to set
	 */
	public void setMusicPrefRC(double musicPrefRC) {
		this.musicPrefRC = musicPrefRC;
	}


	/**
	 * @return the musicPrefIR
	 */
	public double getMusicPrefIR() {
		return musicPrefIR;
	}


	/**
	 * @param musicPrefIR the musicPrefIR to set
	 */
	public void setMusicPrefIR(double musicPrefIR) {
		this.musicPrefIR = musicPrefIR;
	}


	/**
	 * @return the musicPrefUC
	 */
	public double getMusicPrefUC() {
		return musicPrefUC;
	}


	/**
	 * @param musicPrefUC the musicPrefUC to set
	 */
	public void setMusicPrefUC(double musicPrefUC) {
		this.musicPrefUC = musicPrefUC;
	}


	/**
	 * @return the musicPrefER
	 */
	public double getMusicPrefER() {
		return musicPrefER;
	}


	/**
	 * @param musicPrefER the musicPrefER to set
	 */
	public void setMusicPrefER(double musicPrefER) {
		this.musicPrefER = musicPrefER;
	}


	/**
	 * @return the idMusicPrefDimension
	 */
	public int getIdMusicPrefDimension() {
		return idMusicPrefDimension;
	}


	/**
	 * @param idMusicPrefDimension the idMusicPrefDimension to set
	 */
	public void setIdMusicPrefDimension(int idMusicPrefDimension) {
		this.idMusicPrefDimension = idMusicPrefDimension;
	}
	
	public List<String> getGidsFavArtists() {
		
		List<String> gidsList = new ArrayList<String>();
		
		for (FavArtist fa: this.favArtists) {
			gidsList.add(fa.getGid());
		}
		
		return gidsList;
	}
	
	public List<Integer> getIdsFavStyles() {
		List<Integer> idList = new ArrayList<Integer>();
		
		for (FavStyle fs: this.favStyles) {
			idList.add(fs.getIdStyle());
		}
		
		return idList;
	}


	/**
	 * @return the fake
	 */
	public boolean isFake() {
		return fake;
	}


	/**
	 * @param fake the fake to set
	 */
	public void setFake(boolean fake) {
		this.fake = fake;
	}
}
