package com.datastax.workshop.todo;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoEntity {
    
    public static final String TABLENAME        = "todos";
    public static final String COLUMN_UID       = "uid";
    public static final String COLUMN_TITLE     = "title";
    public static final String COLUMN_COMPLETED = "completed";
    public static final String COLUMN_ORDER     = "offset";
    
    private UUID uid;
    
    private String title;
    
    private boolean completed = false;
    
    private int order = 0;
    
    public TodoEntity(String title, int offset) {
        this(UUID.randomUUID(), title, false, offset);
    }

}
