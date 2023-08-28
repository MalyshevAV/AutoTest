package MDM.POJO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnifiedClassifirePojo {
    public String guid;
    public String code;
    public String parent;
    public String name;
    public String owner;
    public boolean consolidatedGroup;
    public String okp;
    public String tnved;
    public String okved;
    public String okpd2;
    @JsonCreator
    public UnifiedClassifirePojo(
            @JsonProperty(value = "guid", required = true) String guid,
            @JsonProperty(value = "code", required = true) String code,
            @JsonProperty(value = "parent", required = true) String parent,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "owner", required = true) String owner,
            @JsonProperty(value = "consolidatedGroup", required = true) boolean consolidatedGroup,
            @JsonProperty(value = "okp", required = true)String okp,
            @JsonProperty(value = "tnved", required = true) String tnved,
            @JsonProperty(value = "okved", required = true) String okved,
            @JsonProperty(value = "okpd2", required = true) String okpd2)
    {
        this.guid = guid;
        this.code = code;
        this.parent = parent;
        this.name = name;
        this.owner = owner;
        this.consolidatedGroup = consolidatedGroup;
        this.okp = okp;
        this.tnved = tnved;
        this.okved = okved;
        this.okpd2 = okpd2;
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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isConsolidatedGroup() {
        return consolidatedGroup;
    }

    public void setConsolidatedGroup(boolean consolidatedGroup) {
        this.consolidatedGroup = consolidatedGroup;
    }

    public String getOkp() {
        return okp;
    }

    public void setOkp(String okp) {
        this.okp = okp;
    }

    public String getTnved() {
        return tnved;
    }

    public void setTnved(String tnved) {
        this.tnved = tnved;
    }

    public String getOkved() {
        return okved;
    }

    public void setOkved(String okved) {
        this.okved = okved;
    }

    public String getOkpd2() {
        return okpd2;
    }

    public void setOkpd2(String okpd2) {
        this.okpd2 = okpd2;
    }
}
