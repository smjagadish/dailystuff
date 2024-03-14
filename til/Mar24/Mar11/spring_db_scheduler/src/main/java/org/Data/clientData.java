package org.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class clientData {

    private int client_id;
    private String client_name;
}
