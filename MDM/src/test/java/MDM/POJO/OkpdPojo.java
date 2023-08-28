package MDM.POJO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OkpdPojo {
    private String guid;
    private String code;
    private String name;



    public OkpdPojo() {
        super();
    }

    @JsonCreator
    public OkpdPojo(
            @JsonProperty(value = "guid", required = true) String guid,
            @JsonProperty(value ="code", required = true) String code,
            @JsonProperty(value ="name", required = true) String name)
    {
        this.guid = guid;
        this.code = code;
        this.name = name;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
