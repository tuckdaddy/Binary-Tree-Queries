package iterators;


import java.util.Iterator;

public class Apply<InT,OutT> implements Iterator<OutT> {
		private final ApplyFunction<InT,OutT> f;
		private final Iterator<InT> input;		

		public Apply(ApplyFunction<InT, OutT> f, Iterator<InT> input) {
				this.f = f;
				this.input = input;
		}

		@Override
		public boolean hasNext() {
			return input.hasNext();
		}

		@Override
		public OutT next() {
			InT i = input.next();
			return f.apply(i);
		}
}
