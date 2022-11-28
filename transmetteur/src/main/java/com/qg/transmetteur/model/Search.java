package com.qg.transmetteur.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Search
{
    public String resultNumber;
    public String language;
    public String query;
    public String from;
    public String to;
    public String sortBy;

}
