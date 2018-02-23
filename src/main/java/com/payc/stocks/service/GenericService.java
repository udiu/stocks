package com.payc.stocks.service;

import java.util.List;

import com.payc.stocks.model.Identifiable;

public interface GenericService<T extends Identifiable> {

	T create(final T newItem);

	boolean update(final Long id, final T newItem);

	boolean delete(final Long id);

	T getById(final Long id);

	List<T> getAll();
}
