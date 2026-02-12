package de.unimarburg.diz.kafkagenetictomtbxml.hgnc;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class Gene {

  private final String hgncId;

  private final String ensemblId;

  private final String symbol;

  private final String name;

  private final String chromosome;

  /**
   * Returns a list of chromosomes using form 'chr?'
   *
   * @return a list of chromosomes
   */
  public List<String> getChromosomesInPropertyForm() {
    return Arrays.stream(this.chromosome.split(" "))
        .map(
            value -> {
              try {
                var pattern = Pattern.compile("^(\\d+|X|Y)");
                var matcher = pattern.matcher(value);
                if (matcher.find()) {
                  return String.format("chr%s", matcher.group(0));
                }
              } catch (Exception e) {
                // Nop
              }
              return null;
            })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  /**
   * Returns a chromosome using form 'chr...?' if one (single) chromosome listed
   *
   * @return an <code>Optional</code> containing the chromosome
   */
  public Optional<String> getSingleChromosomeInPropertyForm() {
    var fixedChromosomes = this.getChromosomesInPropertyForm();

    if (fixedChromosomes.size() == 1) {
      return Optional.of(fixedChromosomes.get(0));
    }

    return Optional.empty();
  }
}
