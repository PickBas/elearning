package com.saied.elearning.report;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
public class Report {
    private String id;
    private String action;
    private String timeStamp;

    @JsonCreator
    public Report(String id, @JsonProperty("action") String action, @JsonProperty("timestamp") String timeStamp) {
        this.id = id;
        this.action = action;
        this.timeStamp = timeStamp;
    }
}
