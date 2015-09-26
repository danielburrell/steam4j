package uk.co.solong.steam4j.tf2.data.schema;

public class TF2SchemaQuality {
    private int id;
    private String name;

    public TF2SchemaQuality(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
