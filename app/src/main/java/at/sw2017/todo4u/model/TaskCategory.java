package at.sw2017.todo4u.model;

public class TaskCategory extends BaseModel {
    private String name;
    private Integer color;
    // 0 = none, 1 = red, 2 = green, 3 = yellow, 4 = blue, 5 = cyan

    public TaskCategory() {
        color = 0;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public TaskCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TaskCategory{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", color=" + color +
                '}';
    }
}
