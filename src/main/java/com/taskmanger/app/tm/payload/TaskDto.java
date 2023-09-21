package com.taskmanger.app.tm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private String title;
    private String description;
    private String user;

}
