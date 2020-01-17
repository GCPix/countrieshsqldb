package com.countries.countriesAPI;

import java.io.IOException;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.container;

@Provider
@PreMatching
public class CORSResponseFilter
implements ContainerResponseFilter {

    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        Logger.getLogger(CORSResponseFilter.class.getName()).fine('Response intercepted by response filter!');
        responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
    }

}
