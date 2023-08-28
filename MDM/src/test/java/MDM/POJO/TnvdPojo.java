package MDM.POJO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TnvdPojo {
    private String guid;
    private String code;
    private String name;
    private String unit;
    private boolean commodity;
    private boolean traceableItem;


    public TnvdPojo() {
        super();
    }

    @JsonCreator
    public TnvdPojo(
            @JsonProperty(value = "guid", required = true) String guid,
            @JsonProperty(value ="code", required = true) String code,
            @JsonProperty(value ="name", required = true) String name,
            @JsonProperty(value ="unit", required = true) String unit,
            @JsonProperty(value ="commodity", required = true) boolean commodity,
            @JsonProperty (value ="traceableItem", required = true) boolean traceableItem)
    {
        this.guid = guid;
        this.code = code;
        this.name = name;
        this.unit = unit;
        this.commodity = commodity;
        this.traceableItem = traceableItem;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isCommodity() {
        return commodity;
    }

    public void setCommodity(boolean commodity) {
        this.commodity = commodity;
    }
    public boolean isTraceableItem() {
        return traceableItem;
    }

    public void setTraceableItem(boolean traceableItem) {
        this.traceableItem = traceableItem;
    }


}




