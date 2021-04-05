package com.aca.certification.utils.enumaration;

public enum Status {
    ON_HOLD(1){
        public String toString(){
            return "id: 1, status: ON_HOLD";
        }
    },

    IN_PROGRESS(2){
        public String toString(){
            return "id: 2, status: IN_PROGRESS";
        }
    },

    COMPLETED(3){
        public String toString(){
            return "id: 3, status: COMPLETED";
        }
    };


    private int id;
    Status(int id) {
        this.id =id;
    }

    public int getId() {
        return id;
    }

    public static Status getById(int id){
        for(Status type : values()) {
            if(type.id == (id))
                return type;
        }

        return null;
    }
}
