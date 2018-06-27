package com.sail.mappingsound.mappingsound.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * should qeury the db and get all the items
 */
public class RecordsData {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<RecordItem> ITEMS = new ArrayList<RecordItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, RecordItem> ITEM_MAP = new HashMap<String, RecordItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(RecordItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static RecordItem createDummyItem(int position) {
        return new RecordItem(String.valueOf(position), "type"+Math.random(),
                "place"+Math.random(), "name"+
                Math.random(), 12,"asdasd");
    }


    /**
     * A dummy item representing a piece of content.
     */
    public static class RecordItem {
        public final String id;
        public final String type;
        public final String place;
        public final String name;
        public final int age;
        public final String timestamp;

        public RecordItem(String id, String type, String place, String name, int age, String timestamp) {
            this.id = id;
            this.type = type;
            this.place = place;
            this.name = name;
            this.age = age;
            this.timestamp = timestamp;

        }
    }
}
