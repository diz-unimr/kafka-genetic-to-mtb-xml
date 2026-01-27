package de.unimarburg.diz.kafkagenetictomtbxml.hgnc;

import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GeneList {

    private GeneList() {
        // Empty
    }

    public static Optional<Gene> findByHgncId(String hgncId) {
        return genes().stream().filter(gene -> gene.getHgncId().equalsIgnoreCase(hgncId)).findFirst();
    }

    public static Optional<Gene> findBySymbol(String symbol) {
        return genes().stream().filter(gene -> gene.getSymbol().equalsIgnoreCase(symbol)).findFirst();
    }

    private static List<Gene> genes() {
        var result = new ArrayList<Gene>();

        try {
            var inputStream = GeneList.class.getClassLoader().getResourceAsStream("hgnc.csv");

            if (inputStream == null) {
                return result;
            }

            var parser = CSVFormat.RFC4180.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setDelimiter('\t')
                    .get()
                    .parse(new InputStreamReader(inputStream));
            for (var row : parser) {
                result.add(
                        new Gene(
                                row.get("HGNC ID"),
                                row.get("Ensembl ID(supplied by Ensembl)"),
                                row.get("Approved symbol"),
                                row.get("Approved name"),
                                row.get("Chromosome")
                        )
                );
            }

            return result;
        } catch (IOException e) {
            return result;
        }
    }
}
