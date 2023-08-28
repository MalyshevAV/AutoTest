package MDM.POJO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnitsPojo {

    private String guid;
    private String name;
    private String nameFull;
    private  String code;
    private  String internationalReduction;


    public UnitsPojo() {
        super();
    }

    @JsonCreator
    public UnitsPojo(
            @JsonProperty(value = "guid", required = true) String guid,
            @JsonProperty(value ="name", required = true) String name,
            @JsonProperty(value ="nameFull", required = true) String nameFull,
            @JsonProperty(value ="code", required = true) String code,
            @JsonProperty (value ="internationalReduction", required = true) String internationalReduction)
    {
        this.guid = guid;
        this.name = name;
        this.nameFull = nameFull;
        this.code = code;
        this.internationalReduction = internationalReduction;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInternationalReduction() {
        return internationalReduction;
    }

    public void setInternationalReduction(String internationalReduction) {
        this.internationalReduction = internationalReduction;
    }
}
