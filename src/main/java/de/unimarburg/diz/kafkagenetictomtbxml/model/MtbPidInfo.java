package de.unimarburg.diz.kafkagenetictomtbxml.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Data;

import java.io.Serializable;

public class MtbPidInfo implements Serializable {
    @JsonProperty
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String pid;

    @JsonProperty
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String tumorId;

}
