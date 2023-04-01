package PojoData.Pet;

import lombok.Data;


    @Data
    public class category {
        private int id;
        private String name;

        public category(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
