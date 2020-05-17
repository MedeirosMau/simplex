package br.helios.simplex.infrastructure.util;

import java.util.List;

public class CollectionUtil {

	public static <T> T lastElement(List<T> list) {
		return list.get(list.size() - 1);
	}
}
