package com.countries.countriesAPI.models;

public class ResponsePaged {
    private Pagination paged;
    private Object object;
    
    public ResponsePaged(Pagination paged, Object object){
        this.paged = paged;
        this.object = object;
    }

    /**
     * @return the object
     */
    public Object getObject() {
        return object;
    }

    /**
     * @return the paged
     */
    public Pagination getPaged() {
        return paged;
    }
}