package PojoData.Pet;

import lombok.Data;

import java.util.List;

@Data
public class SearchPetPojo {
    private int id;
    private category category;
    private String name;
    private List<tags> tags;
    private String status;

    public SearchPetPojo(int id, category category, String name, List<tags> tags, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.tags = tags;
        this.status = status;
    }

}
