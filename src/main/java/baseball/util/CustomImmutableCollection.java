package baseball.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class CustomImmutableCollection<E> implements Collection<E> {
	private final Collection<E> elements;

	public CustomImmutableCollection(final Collection<E> elements) {
		Objects.requireNonNull(elements, "elements must not be null");

		this.elements = Collections.unmodifiableCollection(elements);
	}

	public CustomImmutableCollection(final E[] elements) {
		Objects.requireNonNull(elements, "elements must not be null");

		this.elements = Collections.unmodifiableCollection(Arrays.asList(elements));
	}

	public final <O> List<O> map(final Function<E, O> mapFunction) {
		return mapTo(new ArrayList<>(), mapFunction);
	}

	public final <O, T extends Collection<O>> T mapTo(final T destination, final Function<E, O> mapFunction) {
		for (E element : elements) {
			destination.add(mapFunction.apply(element));
		}

		return destination;
	}

	public final <O> List<O> mapIndexed(final BiFunction<Integer, E, O> mapIndexedFunction) {
		return mapIndexedTo(new ArrayList<>(), mapIndexedFunction);
	}

	public final <O, T extends Collection<O>> T mapIndexedTo(
		final T destination, final BiFunction<Integer, E, O> mapIndexedFunction) {

		Iterator<E> iterator = elements.iterator();

		for (int i = 0; iterator.hasNext(); i++) {
			destination.add(mapIndexedFunction.apply(i, iterator.next()));
		}

		return destination;
	}

	public final <O> List<O> mapFlat(final Function<E, Collection<O>> mapFlatFunction) {
		return mapFlatTo(new ArrayList<>(), mapFlatFunction);
	}

	public final <O, T extends Collection<O>> T mapFlatTo(
		final T destination, final Function<E, Collection<O>> mapFlatFunction) {

		for (E element : elements) {
			destination.addAll(mapFlatFunction.apply(element));
		}

		return destination;
	}

	public final boolean isDistinct() {
		Set<E> set = new HashSet<>(elements);

		return set.size() == elements.size();
	}

	public final int countIf(final Predicate<E> predicate) {
		int count = 0;

		for (E element : elements) {
			count += toInt(predicate.test(element));
		}

		return count;
	}

	private int toInt(final boolean bool) {
		return bool ? 1 : 0;
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public boolean contains(Object obj) {
		return elements.contains(obj);
	}

	@Override
	public Iterator<E> iterator() {
		return elements.iterator();
	}

	@Override
	public Object[] toArray() {
		return elements.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arr) {
		return elements.toArray(arr);
	}

	@Override
	public boolean add(E elem) {
		return elements.add(elem);
	}

	@Override
	public boolean remove(Object obj) {
		return elements.remove(obj);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return elements.containsAll(collection);
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		return elements.addAll(collection);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return elements.removeAll(collection);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		return elements.retainAll(collection);
	}

	@Override
	public void clear() {
		elements.clear();
	}
}
