package com.ds.utils;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

public final class QueryTool {
	public static PageRequest buildPageRequest(int pageNumber, int pageSize, String sortType){
		Sort sort = null;
		if("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else {
			sort = new Sort(Direction.ASC, "title");
		}
		
		return new PageRequest(pageNumber, pageSize, sort);
	}
	
	public static <T> Specification<T> buildSpecification(Map<String, Object> searchParams, Class<T> domainClass){
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), domainClass);
		return spec;
	}
}
