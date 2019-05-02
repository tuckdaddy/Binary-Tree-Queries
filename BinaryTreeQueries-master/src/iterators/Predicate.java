package iterators;


// Interface for classes to define a function from T to boolean
public interface Predicate<T> {
	public boolean check(T data);		
}
