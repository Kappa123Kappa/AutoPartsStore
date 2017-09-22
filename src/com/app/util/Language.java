package com.app.util;

import com.app.model.Position;

public enum Language {
	English(1), Ukrainian(2), Russian(3);
	
	private int id;
	
	Language(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static String getClassName() {
        return Position.class.getName();
    }
}
