package com.mba2dna.wechkhassek.model;

public class Artisan {
	private String title, thumbnailUrl, Adress, specialite, tele, lat, lang,
			calls;

	public Artisan() {
	}

	public Artisan(String name, String thumbnailUrl, String tele,
			String specialite, String lat, String lang, String Adress,
			String calls) {
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.tele = tele;
		this.specialite = specialite;
		this.Adress = Adress;
		this.lat = lat;
		this.lang = lang;
		this.calls = calls;
	}

	public Artisan(String name, String thumbnailUrl, String tele,
			String specialite, String Adress, String calls) {
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.tele = tele;
		this.specialite = specialite;
		this.Adress = Adress;
		this.calls = calls;
	}

	public String getName() {
		return title;
	}

	public void setName(String name) {
		this.title = name;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

	public String getspecialite() {
		return specialite;
	}

	public void setspecialite(String specialite) {
		this.specialite = specialite;
	}

	public String getAdress() {
		return Adress;
	}

	public void setAdress(String Adress) {
		this.Adress = Adress;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getCalls() {
		return calls;
	}

	public void setCalls(String calls) {
		this.calls = calls;
	}

}
