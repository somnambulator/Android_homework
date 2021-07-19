package com.byted.camp.todolist.db;

import android.provider.BaseColumns;

public final class TodoContract {

    //  1. 定义创建数据库以及升级的操作
    public static final String SQL_CREATE_NOTES =
            "CREATE TABLE " + TodoNote.TABLE_NAME + "(" +
                    TodoNote._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    TodoNote.DATE + " INTEGER,"+
                    TodoNote.STATE + " INTEGER,"+
                    TodoNote.CONTENT + " TEXT,"+
                    TodoNote.PRIORITY + " INTEGER)";

    public static final String SQL_ADD_PRIORITY_COLUMN =
            "ALTER TABLE " + TodoNote.TABLE_NAME + " ADD " + TodoNote.PRIORITY + " INTEGER";

    private TodoContract() {
    }

    public static class TodoNote implements BaseColumns {

        //  2.此处定义表名以及列明
        public static final String TABLE_NAME = "todo_note";
        public static final String DATE = "date";
        public static final String STATE = "state";
        public static final String CONTENT = "content";
        public static final String PRIORITY = "priority";
    }

}
