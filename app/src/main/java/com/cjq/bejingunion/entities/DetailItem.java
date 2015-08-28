package com.cjq.bejingunion.entities;

import java.util.Map;

/**
 * Created by CJQ on 2015/8/26.
 */
public class DetailItem {
    Map<Integer,DetailChoice> detailChoices;
    String name;
    String id;
    String chosenId;

    public String getChosenId() {
        return chosenId;
    }

    public void setChosenId(String chosenId) {
        this.chosenId = chosenId;
    }

    public DetailItem(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Map<Integer,DetailChoice> getDetailChoices() {
        return detailChoices;
    }

    public void setDetailChoices(Map<Integer,DetailChoice> detailChoices) {
        this.detailChoices = detailChoices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
