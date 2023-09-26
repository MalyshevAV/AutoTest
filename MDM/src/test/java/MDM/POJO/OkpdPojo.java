package MDM.POJO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OkpdPojo {
    private String guid;
    private String code;
    private String name;
    private String fullName;
    private boolean archive;
    private String dateOutputArchive;



    public OkpdPojo() {
        super();
    }

    @JsonCreator
    public OkpdPojo(
            @JsonProperty(value = "guid", required = true) String guid,
            @JsonProperty(value ="code", required = true) String code,
            @JsonProperty(value ="name", required = true) String name,
            @JsonProperty(value ="name", required = true) String fullName,
            @JsonProperty (value ="archive", required = true) boolean archive,
            @JsonProperty (value ="dateOutputArchive", required = true) String dateOutputArchive)
    {
        this.guid = guid;
        this.code = code;
        this.name = name;
        this.name = fullName;
        this.archive = archive;
        this.dateOutputArchive = dateOutputArchive;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public String getDateOutputArchive() {
        return dateOutputArchive;
    }

    public void setDateOutputArchive(String dateOutputArchive) {
        this.dateOutputArchive = dateOutputArchive;
    }
}
