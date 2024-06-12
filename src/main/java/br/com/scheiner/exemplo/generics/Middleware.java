package br.com.scheiner.exemplo.generics;

public abstract class Middleware<T> {
    
	private Middleware<T> next;

    @SafeVarargs
	public static <T> Middleware<T> link(Middleware<T> first, Middleware<T>... chain) {
        
    	Middleware<T> head = first;
        
        for (Middleware<T> nextInChain: chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract boolean check(T data);

    protected boolean checkNext(T data) {
        if (next == null) {
            return true;
        }
        return next.check(data);
    }
}
