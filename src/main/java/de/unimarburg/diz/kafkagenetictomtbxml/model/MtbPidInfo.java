package de.unimarburg.diz.kafkagenetictomtbxml.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Data;

import java.io.Serializable;
@Data
public class MtbPidInfo implements Serializable {
    @JsonProperty("pid")
    private String pid;

    @JsonProperty("tumorId")
    private String tumorId;

    @JsonProperty("orderId")
    private String orderId;

}
