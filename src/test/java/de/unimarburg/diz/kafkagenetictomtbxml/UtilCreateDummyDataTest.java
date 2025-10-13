package de.unimarburg.diz.kafkagenetictomtbxml;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilCreateDummyDataTest {
    public static MHGuide getDummyMHGuide(){
        return new MHGuide();

    }

    public static MtbPatientInfo getDummyMtbPID(){
        return new MtbPatientInfo();
    }


    @Test
    public void check() {
        assertThat(getDummyMHGuide()).isNotNull();
        assertThat(getDummyMtbPID()).isNotNull();
    }

}
