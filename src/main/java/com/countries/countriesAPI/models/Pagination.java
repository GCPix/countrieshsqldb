package com.countries.countriesAPI.models;

import java.net.URL;

public class Pagination {
    private int pageNumber;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private String sortBy;
    private URL firstPagePath;
    private String lastPagePath;
    private String previousPagePath;
    private String nextPagePath;

    public Pagination(int pageSize, String sortField, int pageNumber){
        this.pageSize = pageSize;
        this.sortBy = sortField;
        this.pageNumber = pageNumber;
    };

    


    /**
     * @return int return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * @return int return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return int return the totalElements
     */
    public int getTotalElements() {
        return totalElements;
    }

    /**
     * @param totalElements the totalElements to set
     */
    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    /**
     * @return int return the totalPages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages the totalPages to set
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * @return String return the sortBy
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * @param sortBy the sortBy to set
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * @return String return the firstPagePath
     */
    public URL getFirstPagePath() {
        return firstPagePath;
    }

    /**
     * @param firstPagePath the firstPagePath to set
     */
    public void setFirstPagePath(URL firstPagePath) {
        this.firstPagePath = firstPagePath;
    }

    /**
     * @return String return the lastPagePath
     */
    public String getLastPagePath() {
        return lastPagePath;
    }

    /**
     * @param lastPagePath the lastPagePath to set
     */
    public void setLastPagePath(String lastPagePath) {
        this.lastPagePath = lastPagePath;
    }

    /**
     * @return String return the previousPagePath
     */
    public String getPreviousPagePath() {
        return previousPagePath;
    }

    /**
     * @param previousPagePath the previousPagePath to set
     */
    public void setPreviousPagePath(String previousPagePath) {
        this.previousPagePath = previousPagePath;
    }

    /**
     * @return String return the nextPagePath
     */
    public String getNextPagePath() {
        return nextPagePath;
    }

    /**
     * @param nextPagePath the nextPagePath to set
     */
    public void setNextPagePath(String nextPagePath) {
        this.nextPagePath = nextPagePath;
    }

}