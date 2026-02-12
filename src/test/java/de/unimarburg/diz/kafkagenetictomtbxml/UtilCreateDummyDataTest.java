package de.unimarburg.diz.kafkagenetictomtbxml;

import static org.assertj.core.api.Assertions.assertThat;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import org.junit.jupiter.api.Test;

public class UtilCreateDummyDataTest {
  public static MHGuide getDummyMHGuide() {
    return new MHGuide();
  }

  public static MtbPatientInfo getDummyMtbPID() {
    return new MtbPatientInfo();
  }

  @Test
  public void check() {
    assertThat(getDummyMHGuide()).isNotNull();
    assertThat(getDummyMtbPID()).isNotNull();
  }
}
