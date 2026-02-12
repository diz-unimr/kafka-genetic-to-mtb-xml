package de.unimarburg.diz.kafkagenetictomtbxml.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class DnaChangeTest {

  @ParameterizedTest
  @CsvSource({
    "c.123A>G,123,,A,G",
    "c.-123A>G,-123,,A,G",
    "c.123_456del,123,456,,,",
    "c.-123_123del,-123,123,,,",
    "c.123_124insACGT,123,124,,ACGT"
  })
  void shouldParseCdnaString(
      final String input,
      final String expectedStart,
      final String expectedEnd,
      final String expectedRef,
      final String expectedAlt) {
    final var actual = DnaChange.parse(input);

    assertThat(actual)
        .isNotNull()
        .hasFieldOrPropertyWithValue("start", expectedStart)
        .hasFieldOrPropertyWithValue("end", expectedEnd)
        .hasFieldOrPropertyWithValue("refAllele", expectedRef)
        .hasFieldOrPropertyWithValue("altAllele", expectedAlt)
        .hasFieldOrPropertyWithValue("type", DnaChange.Type.CDNA);
  }

  @ParameterizedTest
  @CsvSource({"g.123A>G,123,,A,G", "g.123_456del,123,456,,,", "g.123_124insACGT,123,124,,ACGT"})
  void shouldParseGenomicString(
      final String input,
      final String expectedStart,
      final String expectedEnd,
      final String expectedRef,
      final String expectedAlt) {
    final var actual = DnaChange.parse(input);

    assertThat(actual)
        .isNotNull()
        .hasFieldOrPropertyWithValue("start", expectedStart)
        .hasFieldOrPropertyWithValue("end", expectedEnd)
        .hasFieldOrPropertyWithValue("refAllele", expectedRef)
        .hasFieldOrPropertyWithValue("altAllele", expectedAlt)
        .hasFieldOrPropertyWithValue("type", DnaChange.Type.GENOMIC);
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "c.123A>X", // Invalid nucleotide
        "c.123_124ins", // Missing inserted nucleotides
        "x.123A>G" // Wrong type
      })
  void shouldReturnNullOnUnparsableInputString(final String cdnaString) {
    final var actual = DnaChange.parse(cdnaString);

    assertThat(actual).isNull();
  }
}
