package de.unimarburg.diz.kafkagenetictomtbxml.hgnc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GeneListTest {

  @Test
  void findByHgncId() {
    var actual = GeneList.findByHgncId("HGNC:1097");

    assertThat(actual)
        .isPresent()
        .hasValueSatisfying(
            gene -> {
              assertThat(gene.getHgncId()).isEqualTo("HGNC:1097");
              assertThat(gene.getEnsemblId()).isEqualTo("ENSG00000157764");
              assertThat(gene.getSymbol()).isEqualTo("BRAF");
              assertThat(gene.getName()).isEqualTo("B-Raf proto-oncogene, serine/threonine kinase");
              assertThat(gene.getChromosome()).isEqualTo("7q34");
              assertThat(gene.getSingleChromosomeInPropertyForm()).hasValue("chr7");
            });
  }

  @Test
  void findByHgncSymbol() {
    var actual = GeneList.findBySymbol("ABCD1");

    assertThat(actual)
        .isPresent()
        .hasValueSatisfying(
            gene -> {
              assertThat(gene.getHgncId()).isEqualTo("HGNC:61");
              assertThat(gene.getEnsemblId()).isEqualTo("ENSG00000101986");
              assertThat(gene.getSymbol()).isEqualTo("ABCD1");
              assertThat(gene.getName()).isEqualTo("ATP binding cassette subfamily D member 1");
              assertThat(gene.getChromosome()).isEqualTo("Xq28");
              assertThat(gene.getSingleChromosomeInPropertyForm()).hasValue("chrX");
            });
  }
}
