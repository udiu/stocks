package com.payc.stocks.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.payc.stocks.model.AuditLoggable;
import com.payc.stocks.model.Deletable;

public abstract class GenericServiceImpl<T extends Deletable> implements GenericService<T> {

	protected final HashMap<Long, T> store = new HashMap<>();

	private final AtomicLong idGenerator = new AtomicLong(0);

	@Override
	public T create(final T newItem){
		Long newId = idGenerator.incrementAndGet();
		newItem.setId(newId);
		newItem.setDeleted(Boolean.FALSE);
		if( newItem instanceof AuditLoggable ){
			((AuditLoggable) newItem).setCreationDate(new Date());
			((AuditLoggable) newItem).setLastUpdateDate(new Date());
		}
		store.put(newId, newItem);
		return newItem;
	}

	@Override
	public boolean update(final Long id, final T newItem){
		if( newItem instanceof AuditLoggable ){
			((AuditLoggable) newItem).setLastUpdateDate(new Date());
		}
		store.put(id, newItem);
		return true;
	}

	@Override
	public boolean delete(final Long id){
		if( !store.containsKey(id) ){
			return false;
		}
		T item = store.get(id);
		if( item instanceof AuditLoggable ){
			((AuditLoggable) item).setLastUpdateDate(new Date());
		}
		item.setDeleted(Boolean.TRUE);
		store.put(id, item);
		return true;
	}

	@Override
	public T getById(final Long id){
		return store.get(id);
	}

	@Override
	public List<T> getAll(){
		return store.values().stream().filter(s -> Boolean.FALSE.equals(s.getDeleted())).collect(Collectors.toList());
	}
}
