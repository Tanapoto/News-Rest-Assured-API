package PojoData.Pet;

import lombok.Data;


@Data
public class tags {
    private int id;
    private String name;

    public tags(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
