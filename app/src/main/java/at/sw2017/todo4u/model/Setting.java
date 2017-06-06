package at.sw2017.todo4u.model;

public class Setting extends BaseModel {
    private String name;
    private Integer bool;

    public Setting() {
    }

    public Setting(String name) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBool() {
        return bool;
    }

    public void setBool(Integer bool) {
        this.bool = bool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", boolean='" + bool +
                '}';
    }
}
