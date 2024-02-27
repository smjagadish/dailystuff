package org.ServerModel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.ClientModel.clientData;

import java.util.ArrayList;
import java.util.List;

@Data
public class serverManager {
    private int ID;
    private String zone;
    @JsonBackReference
    private List<serverData> serverDataList = new ArrayList<>();
    public void addToList(serverData data)
    {
        this.serverDataList.add(data);
    }
}
