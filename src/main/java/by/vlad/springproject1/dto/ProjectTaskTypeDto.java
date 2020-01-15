package by.vlad.springproject1.dto;

import by.vlad.springproject1.entity.enums.TaskType;

public class ProjectTaskTypeDto {
    public TaskType type;
    public Integer count;
    public ProjectTaskTypeDto() {
    }
    public ProjectTaskTypeDto(TaskType type, Integer count) {
        this.type = type;
        this.count = count;
    }
}