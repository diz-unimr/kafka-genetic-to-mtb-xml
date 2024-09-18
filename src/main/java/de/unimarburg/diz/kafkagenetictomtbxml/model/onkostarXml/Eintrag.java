package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Eintrag {
    @JacksonXmlProperty(isAttribute = true)
    private String Feldname;

    @JacksonXmlProperty(localName = "Wert")
    private String wert;

    @JsonIgnore
    private String feldname;

    @JacksonXmlProperty(localName = "Genauigkeit")
    private String genauigkeit;

    public Eintrag() {
    }

    @JacksonXmlProperty(localName = "Filterkategorie")
    private String filterkategorie;

    @JacksonXmlProperty(localName = "Version")
    private String version;

    @JacksonXmlProperty(localName = "Kurztext")
    private String kurztext;

    @JacksonXmlProperty(localName = "FachabteilungKennung")
    private String fachabteilungKennung;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Unterformular")
    private List<Unterformular> unterformulars;


    public Eintrag(String Feldname, String wert, String genauigkeit, String filterkategorie, String version, String kurztext, String fachabteilungKennung, List<Unterformular> unterformulars) {
        this.Feldname = Feldname;
        this.genauigkeit = genauigkeit;
        this.wert = wert;
        this.filterkategorie = filterkategorie;
        this.version = version;
        this.kurztext = kurztext;
        this.fachabteilungKennung = fachabteilungKennung;
        this.unterformulars = unterformulars;
    }

    public String getFeldname() {
        return Feldname;
    }

    public void setFeldname(String Feldname) {
        this.Feldname = Feldname;
    }

    public String getWert() {
        return wert;
    }

    public void setWert(String wert) {
        this.wert = wert;
    }

    public String getGenauigkeit() {
        return genauigkeit;
    }
    public void setGenauigkeit(String genauigkeit) {
        this.genauigkeit = genauigkeit;
    }


    public String getFilterkategorie() {

        return filterkategorie;
    }

    public void setFilterkategorie(String filterkategorie) {
        this.filterkategorie = filterkategorie;
    }

    public String getVersion() {
        return version;

    }
    public void setVersion(String version) {
        this.version = version;
    }

    public String getKurztext() {
        return kurztext;
    }
    public void setKurztext(String kurztext) {
        this.kurztext = kurztext;
    }

    public String getFachabteilungKennung() {
        return fachabteilungKennung;
    }

    public String setFachabteilungKennung(String fachabteilungKennung) {
        return fachabteilungKennung;
    }

    public List<Unterformular> getUnterformulars() {
        return unterformulars;
    }

    public void setUnterformulars(List<Unterformular> unterformular) {
        this.unterformulars = unterformular;
    }
}