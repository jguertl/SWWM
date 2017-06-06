package at.sw2017.todo4u.model;

public class Setting extends BaseModel {
    private String name;
    private Integer bool;

    public Setting() {
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
}
