package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPidInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.VariantLongList;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.DokumentierendeFachabteilung;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Eintrag;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.UnterformularSV;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Arrays;
@Component
public class UnterformularSVMapper {

    private String interpolationSystem;
    public  UnterformularSVMapper(@Value("${metadata.ngsReports.interpolationSystem}") String interpolationSystem){
        this.interpolationSystem = interpolationSystem;
    }

    public String parseStartEnd(String chromosomalModi){

        String regex = "g\\.(\\d+)([A-Za-z])>([A-Za-z])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chromosomalModi);
        String startEnd = "";
        if (matcher.find()) {
            startEnd = matcher.group(1);

        }
        return startEnd;
    }

    public String parserRefAlle(String chromosomalModi){

        String regex = "g\\.(\\d+)([A-Za-z])>([A-Za-z])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chromosomalModi);
        String refAlle = "";
        if (matcher.find()) {
            refAlle = matcher.group(2);

        }
        return refAlle;
    }

    public String parseAltAlle(String chromosomalModi){

        String regex = "g\\.(\\d+)([A-Za-z])>([A-Za-z])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chromosomalModi);
        String altAlle = "";
        if (matcher.find()) {
            altAlle = matcher.group(3);
        }
        return altAlle;
    }

    public String parseDnaChange(String chromosomalModi){
        String regex = "g\\.(\\d+)([A-Za-z].*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chromosomalModi);
        String dnaChange = "";
        if (matcher.find()) {
            dnaChange = matcher.group(1);

        }
       return dnaChange;
    }

    public String parseAminoAcidChange(String chromosomalModi){
        String regex = "p\\.([A-Za-z].*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chromosomalModi);
        String aminoChange = "";
        if (matcher.find()) {
            aminoChange = matcher.group(1);
        }
        return aminoChange;
    }

    public UnterformularSV createXmlUnterformularSV (MtbPidInfo mtbPidInfo, VariantLongList variantLongList, DokumentierendeFachabteilung dokumentierendeFachabteilung) {
        UnterformularSV unterformularSV = new UnterformularSV();
        // To find the export ID a function need to implement, that track the number of unterformular and add the number TODO
        unterformularSV.setExportID(1);
        unterformularSV.setTumorId(mtbPidInfo.getTumorId());
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
        allelfrequenz.setWert(variantLongList.getVariantAlleleFrequencyInTumor());

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
        // NA
        Eintrag eVAltNucleotide  = new Eintrag();
        eVAltNucleotide.setFeldname("EVAltNucleotide");
        eVAltNucleotide.setWert("");

        // SV-Unterformular: Eintrag: EVCOSMICID
        // NA
        Eintrag eVCOSMICID = new Eintrag();
        eVCOSMICID.setFeldname("EVCOSMICID");
        eVCOSMICID.setWert("");

        // SV-Unterformular: Eintrag: EVChromosom
        Eintrag eVChromosom = new Eintrag();
        eVChromosom.setFeldname("EVChromosom");
        eVChromosom.setWert(variantLongList.getChromosomeModifiedObject());
        eVChromosom.setFilterkategorie("{}");

        // SV-Unterformular: Eintrag: EVENSEMBLID
        // Wird automatisch aus der EVHGNCSymbol zugeordnet
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
        eVHGNCSymbol.setWert(variantLongList.getGeneSymbol());

        // SV-Unterformular: Eintrag: EVNMNummer
        Eintrag eVNMNummer = new Eintrag();
        eVNMNummer.setFeldname("EVNMNummer");

        // SV-Unterformular: Eintrag: EVReadDepth
        Eintrag eVReadDepth = new Eintrag();
        eVReadDepth.setFeldname("EVReadDepth");
        eVReadDepth.setWert(variantLongList.getTotalReadsInTumor());

        // SV-Unterformular: Eintrag: EVRefNucleotide
        Eintrag eVRefNucleotide = new Eintrag();
        eVRefNucleotide.setFeldname("EVRefNucleotide");

        // SV-Unterformular: Eintrag: EVStart
        Eintrag eVStart = new Eintrag();
        eVStart.setFeldname("EVStart");
        String evStartString = parseStartEnd(variantLongList.getChromosomeModification());
        eVStart.setWert(evStartString);

        // SV-Unterformular: EVdbSNPID
        Eintrag eVdbSNPID = new Eintrag();
        eVdbSNPID.setFeldname("EVdbSNPID");
        eVdbSNPID.setWert(variantLongList.getDbsnp());

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
        unterformularSV.setHauptTudokEintragExportID(3);
        unterformularSV.setRevision(1);
        unterformularSV.setBearbeitungStatus(0);
        return unterformularSV;
    }

}
