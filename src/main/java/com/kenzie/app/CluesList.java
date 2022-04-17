package com.kenzie.app;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CluesList {

    @JsonProperty("clues")
    private List<CluesDTO> clues;

    public List<CluesDTO> getClues() {
        return clues;
    }

    public void setClues(List<CluesDTO> clues) {
        this.clues = clues;
    }

}
