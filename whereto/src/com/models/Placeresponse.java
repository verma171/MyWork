package com.models;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class Placeresponse implements Serializable{
	private String id;
    private String icon;
    private String name;
    private String vicinity;
    private Double latitude;
    private Double longitude;
    private String references;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getVicinity() {
        return vicinity;
    }
    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
    public String getReferences() {
		return references;
	}
	public void setReferences(String references) {
		this.references = references;
	}
    public static Placeresponse jsonToPontoReferencia(JSONObject pontoReferencia) {
        try {
        	Placeresponse result = new Placeresponse();
            JSONObject geometry = (JSONObject) pontoReferencia.get("geometry");
            JSONObject location = (JSONObject) geometry.get("location");
            result.setLatitude((Double) location.get("lat"));
            result.setLongitude((Double) location.get("lng"));
            result.setIcon(pontoReferencia.getString("icon"));
            result.setName(pontoReferencia.getString("name"));
            result.setVicinity(pontoReferencia.getString("vicinity"));
            result.setId(pontoReferencia.getString("id"));
            result.setReferences(pontoReferencia.getString("reference"));
            return result;
        } catch (JSONException ex) {
            Logger.getLogger(Placeresponse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Place{" + "id=" + id + ", icon=" + icon + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }
}
