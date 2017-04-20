package at.sw2017.todo4u.model;

import java.util.Date;

public class Task extends BaseModel {
    private String title;
    private String description;
    private Date dueDate;
    private Date creationDate;
    private Date reminderDate;
    private TaskCategory category;

    public Task() {
    }

    public Task(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Long getDueDateAsNumber() {
        if(dueDate == null) return null;
        return dueDate.getTime();
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setDueDate(long dueDateMillis) {
        this.dueDate = new Date(dueDateMillis);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Long getCreationDateAsNumber() {
        if(creationDate == null) return null;
        return creationDate.getTime();
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreationDate(long creationDateMillis) {
        this.creationDate = new Date(creationDateMillis);
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public Long getReminderDateAsNumber() {
        if(reminderDate == null) return null;
        return reminderDate.getTime();
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    public void setReminderDate(long reminderDateMillis) {
        this.reminderDate = new Date(reminderDateMillis);
    }

    public TaskCategory getCategory() {
        return category;
    }

    public void setCategory(TaskCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", creationDate=" + creationDate +
                ", reminderDate=" + reminderDate +
                '}';
    }
}
