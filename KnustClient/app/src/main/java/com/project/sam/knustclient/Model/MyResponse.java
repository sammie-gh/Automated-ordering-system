package com.project.sam.knustclient.Model;

import java.util.List;

/**
 * Created by A.Richard on 14/10/2017.
 */

public class MyResponse {
    public long multicast_id;
    public int success;
    public int failure;
    public int canonical_ids;

    public List<Result> results;
}
