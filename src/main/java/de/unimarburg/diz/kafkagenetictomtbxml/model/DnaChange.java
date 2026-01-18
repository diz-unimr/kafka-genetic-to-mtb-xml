package de.unimarburg.diz.kafkagenetictomtbxml.model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.regex.Pattern;

@Data
public class DnaChange {
    private static Logger log = LoggerFactory.getLogger(DnaChange.class);

    private String start;
    private String end;

    private String refAllele;
    private String altAllele;

    private Type type;

    public DnaChange(Type type, String start, String end, String refAllele, String altAllele) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.refAllele = refAllele;
        this.altAllele = altAllele;
    }

    /**
     * Parses the input string representing a DNA change and converts it into a {@code DnaChange} object.
     * The method supports three types of DNA changes: substitution, deletion, and insertion.
     *
     * @param input the string representation of the DNA change, following specific patterns.
     *              For example, "c.123A>G" for substitution, "c.123_456del" for deletion, or
     *              "c.123_124insACGT" for insertion.
     * @return a {@code DnaChange} object representing the parsed DNA change, or {@code null} if the input
     *         is invalid or does not match any supported pattern.
     */
    public static DnaChange parse(final String input) {
        if (null == input) {
            return null;
        }

        final var rules = Map.of(
                AlterationType.SUBSTITUTION, Pattern.compile("(?<type>[cg])\\.(?<start>\\d+)(?<ref>[ACGT])>(?<alt>[ACGT])"),
                AlterationType.DELETION, Pattern.compile("(?<type>[cg])\\.(?<start>\\d+)(?:_(?<end>\\d+))?del"),
                AlterationType.INSERTION, Pattern.compile("(?<type>[cg])\\.(?<start>\\d+)_(?<end>\\d+)ins(?<alt>[ACGT]+)")
        );

        for (var rule : rules.entrySet()) {
            final var matcher = rule.getValue().matcher(input);
            if (matcher.matches()) {
                switch (rule.getKey()) {
                    case SUBSTITUTION:
                        return new DnaChange(
                                matcher.group("type").equals("g") ? Type.GENOMIC : Type.CDNA,
                                matcher.group("start"),
                                null,
                                matcher.group("ref"),
                                matcher.group("alt")
                        );
                    case DELETION:
                        log.warn("Parsing deletion variant: {} - No 'refAllele' possible but may be required", input);
                        return new DnaChange(
                                matcher.group("type").equals("g") ? Type.GENOMIC : Type.CDNA,
                                matcher.group("start"),
                                matcher.group("end"),
                                null,
                                null
                        );
                    case INSERTION:
                        return new DnaChange(
                                matcher.group("type").equals("g") ? Type.GENOMIC : Type.CDNA,
                                matcher.group("start"),
                                matcher.group("end"),
                                null,
                                matcher.group("alt")
                        );
                }
            }
        }

        return null;
    }

    public enum AlterationType {
        SUBSTITUTION, DELETION, INSERTION
    }

    public enum Type {
        GENOMIC,
        CDNA
    }
}
