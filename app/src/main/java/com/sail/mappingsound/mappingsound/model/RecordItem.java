package com.sail.mappingsound.mappingsound.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "record_table")
public class RecordItem {

    @PrimaryKey(autoGenerate = true)
    private  Integer id;

    private  String type;
    private  String place;
    private  String name;
    private  Integer age;
    private  String timestamp;
    private  String coordinates;
    private  String path_to_record;

    public RecordItem(Integer id, String type, String place, String name, Integer age, String timestamp,
                      String coordinates, String path_to_record) {
        this.id = id;
        this.type = type;
        this.place = place;
        this.name = name;
        this.age = age;
        this.timestamp = timestamp;
        this.coordinates = coordinates;
        this.path_to_record = path_to_record;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getPath_to_record() {
        return path_to_record;
    }

    public void setPath_to_record(String path_to_record) {
        this.path_to_record = path_to_record;
    }
}
