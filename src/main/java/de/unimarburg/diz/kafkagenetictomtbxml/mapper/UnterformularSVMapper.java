package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPidInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.DokumentierendeFachabteilung;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Eintrag;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.UnterformularSV;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
public class UnterformularSVMapper {

    private String interpolationSystem;
    public  UnterformularSVMapper(@Value("${metadata.ngsReports.interpolationSystem}") String interpolationSystem){
        this.interpolationSystem = interpolationSystem;
    }

    public UnterformularSV createXmlUnterformularSV (MHGuide mhGuideInfo, MtbPidInfo mtbPidInfo, DokumentierendeFachabteilung dokumentierendeFachabteilung) {
        UnterformularSV unterformularSV = new UnterformularSV();
        unterformularSV.setExportID(1);
        unterformularSV.setTumorId("1");
        unterformularSV.setDokumentierendeFachabteilung(dokumentierendeFachabteilung);
        unterformularSV.setStartDatum("2023-08-10");
        unterformularSV.setFormularName("OS.Molekulargenetische Untersuchung");
        unterformularSV.setFormularVersion(1);
        unterformularSV.setProzedurtyp("Beobachtung");
        // SV-Unterformular: Eintrag: Dokumentation
        Eintrag dokumentationUnterformular = new Eintrag();
        dokumentationUnterformular.setFeldname("Dokumentation");
        dokumentationUnterformular.setWert("ERW");
        dokumentationUnterformular.setFilterkategorie("{}");
        dokumentationUnterformular.setVersion("OS.MolDokumentation.v1");
        dokumentationUnterformular.setKurztext("Erweitert");

        // SV-Unterformular: Eintrag: Aktivierend
        Eintrag aktivierend = new Eintrag();
        aktivierend.setFeldname("Aktivierend");
        aktivierend.setWert("");

        // SV-Unterformular: Eintrag: Allelfrequenz
        Eintrag allelfrequenz = new Eintrag();
        allelfrequenz.setFeldname("Allelfrequenz");
        allelfrequenz.setWert("");

        // SV-Unterformular: Eintrag: Allelzahl
        Eintrag allelzahl = new Eintrag();
        allelzahl.setFeldname("Allelzahl");
        allelzahl.setWert("");

        // SV-Unterformular: Eintrag: Analysemethode
        Eintrag analysemethode = new Eintrag();
        analysemethode.setFeldname("Analysemethode");
        analysemethode.setWert("");
        analysemethode.setFilterkategorie("{}");

        // SV-Unterformular: Eintrag: Bemerkung
        Eintrag bemerkung = new Eintrag();
        bemerkung.setFeldname("Bemerkung");
        bemerkung.setWert("");

        // SV-Unterformular: Eintrag: Datum
        // Oder_date
        Eintrag datumSV = new Eintrag();
        datumSV.setFeldname("Datum");
        datumSV.setWert("");

        // SV-Unterformular: Eintrag: "EVAltNucleotide
        Eintrag eVAltNucleotide  = new Eintrag();
        eVAltNucleotide.setFeldname("EVAltNucleotide");
        eVAltNucleotide.setWert("");

        // SV-Unterformular: Eintrag: EVCOSMICID
        Eintrag eVCOSMICID = new Eintrag();
        eVCOSMICID.setFeldname("EVCOSMICID");
        eVCOSMICID.setWert("");

        // SV-Unterformular: Eintrag: EVChromosom
        Eintrag eVChromosom = new Eintrag();
        eVChromosom.setFeldname("EVChromosom");
        eVChromosom.setWert("");
        eVChromosom.setFilterkategorie("{}");

        // SV-Unterformular: Eintrag: EVENSEMBLID
        Eintrag eVENSEMBLID = new Eintrag();
        eVENSEMBLID.setFeldname("EVENSEMBLID");
        eVENSEMBLID.setWert("");

        // SV-Unterformular: Eintrag: EVEnde
        Eintrag eVEnde = new Eintrag();
        eVEnde.setFeldname("EVEnde");
        eVEnde.setWert("");

        // SV-Unterformular: Eintrag: EVHGNCID
        Eintrag eVHGNCID = new Eintrag();
        eVHGNCID.setFeldname("EVHGNCID");
        eVHGNCID.setWert("");
        // SV-Unterformular: Eintrag: EVHGNCName
        Eintrag eVHGNCName = new Eintrag();
        eVHGNCName.setFeldname("EVHGNCName");

        // SV-Unterformular: Eintrag: EVHGNCSymbol
        Eintrag eVHGNCSymbol = new Eintrag();
        eVHGNCSymbol.setFeldname("EVHGNCSymbol");
        eVHGNCSymbol.setWert("");

        // SV-Unterformular: Eintrag: EVNMNummer
        Eintrag eVNMNummer = new Eintrag();
        eVNMNummer.setFeldname("EVNMNummer");

        // SV-Unterformular: Eintrag: EVReadDepth
        Eintrag eVReadDepth = new Eintrag();
        eVReadDepth.setFeldname("EVReadDepth");
        eVReadDepth.setWert("");

        // SV-Unterformular: Eintrag: EVRefNucleotide
        Eintrag eVRefNucleotide = new Eintrag();
        eVRefNucleotide.setFeldname("EVRefNucleotide");

        // SV-Unterformular: Eintrag: EVStart
        Eintrag eVStart = new Eintrag();
        eVStart.setFeldname("EVStart");

        // SV-Unterformular: Eintrag: Untersucht
        Eintrag untersucht = new Eintrag();
        untersucht.setFeldname("Untersucht");
        untersucht.setWert("");
        untersucht.setFilterkategorie("{}");
        untersucht.setKurztext("NF1");

        Eintrag ergebnisEintragSV = new Eintrag();
        ergebnisEintragSV.setFeldname("Ergebnis");
        ergebnisEintragSV.setWert("P");
        ergebnisEintragSV.setFilterkategorie("{&quot;OS.MolGenErgebnis.v1&quot;:&quot;Standard&quot;}");
        ergebnisEintragSV.setVersion("OS.MolGenErgebnis.v1");
        ergebnisEintragSV.setKurztext("Einfache Variante (Mutation / positiv)");

        unterformularSV.setEintraege(Arrays.asList(dokumentationUnterformular, aktivierend,
                allelfrequenz, allelzahl, analysemethode, bemerkung, datumSV, eVAltNucleotide,
                eVCOSMICID, eVChromosom, eVENSEMBLID, eVEnde, eVHGNCID, eVHGNCName, eVHGNCSymbol, eVNMNummer, eVReadDepth, eVRefNucleotide, eVStart, untersucht,ergebnisEintragSV));
        return unterformularSV;
    }

}
