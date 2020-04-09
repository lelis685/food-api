package com.food.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenericDtoAssembler<DomainObject, DtoObject> {

	@Autowired
	private ModelMapper mapper;

	public DtoObject toRepresentationModel(DomainObject domainObject, Class<DtoObject> dtoObject) {
		return mapper.map(domainObject, dtoObject);
	}

	public List<DtoObject> toCollectionRepresentationModel(Collection<DomainObject> objects, Class<DtoObject> dtoObject) {
		return objects.stream()
				.map(obj -> toRepresentationModel(obj, dtoObject))
				.collect(Collectors.toList());
	}

}
