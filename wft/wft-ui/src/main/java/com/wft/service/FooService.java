package com.wft.service;

import java.util.List;

import com.wft.model.Foo;

public interface FooService {

	public void saveFoo(Foo foo);

	public Foo updateFoo(Foo foo);

	public List<Foo> findFoos();

}