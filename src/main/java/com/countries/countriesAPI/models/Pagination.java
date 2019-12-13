package com.countries.countriesAPI.models;

import javax.validation.constraints.NotNull;

public class Pagination {
	@NotNull
	private int pageNumber;
	@NotNull
	private final int pageSize;
	private int totalElements;
	private int totalPages;
	@NotNull
	private final String sortBy;
	private String firstPagePath;
	private String lastPagePath;
	private String previousPagePath;
	private String nextPagePath;

	public Pagination(int pageSize, String sortField, int pageNumber) {
		this.pageSize = pageSize;
		this.sortBy = sortField;
		this.pageNumber = pageNumber;
	}

	public void setPagePaths(String basePath) {
		String firstPageURL;
		String previousPage;
		String nextPage;
		String lastPage;
		String sort = "?sortField=" + getSortBy();
		String pageSizeString = "&pageSize=" + getPageSize();

		if (getPageNumber() != 1) {

			String pageNumberFirst = "&pageNumber=1";
			String pageNumberPrevious = "&pageNumber=" + Integer.toString(getPageNumber() - 1);
			String mainFirstPath = sort + pageSizeString + pageNumberFirst;

			firstPageURL = basePath + mainFirstPath;
			previousPage = basePath + sort + pageSizeString + pageNumberPrevious;

			setFirstPagePath(firstPageURL);
			setPreviousPagePath(previousPage);
		}

		if (getTotalPages() != getPageNumber()) {
			String pageNumberNext = "&pageNumber=" + Integer.toString(getPageNumber() + 1);
			String pageNumberLast = "&pageNumber=" + Integer.toString(getTotalPages());

			nextPage = basePath + sort + pageSizeString + pageNumberNext;
			lastPage = basePath + sort + pageSizeString + pageNumberLast;

			setNextPagePath(nextPage);
			setLastPagePath(lastPage);
		}
	}

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

//	/**
//	 * @param pageSize the pageSize to set
//	 */
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}

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

//	/**
//	 * @param sortBy the sortBy to set
//	 */
//	public void setSortBy(String sortBy) {
//		this.sortBy = sortBy;
//	}

	/**
	 * @return String return the firstPagePath
	 */
	public String getFirstPagePath() {
		return firstPagePath;
	}

	/**
	 * @param firstPagePath the firstPagePath to set
	 */
	public void setFirstPagePath(String firstPagePath) {
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