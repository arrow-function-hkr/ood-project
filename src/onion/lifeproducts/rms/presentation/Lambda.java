package onion.lifeproducts.rms.presentation;

import java.util.function.Consumer;
import java.lang.Runnable;

public class Lambda<T> implements Runnable {
	private Runnable noArgLambda;
	private Consumer<T> oneArgLambda;

	public Lambda(Runnable r) {
		this.noArgLambda = r;
	}

	public Lambda(Consumer<T> c) {
		this.oneArgLambda = c;
	}

	/**
	 * Run the specified lambda with optional parameter passing if it was a consumer.
	 */
	public void run(T arg) {
		if (oneArgLambda != null) {
			oneArgLambda.accept(arg);
		} else if (noArgLambda != null) {
			noArgLambda.run();
		}
	}

	@Override
	public void run() {
		if (oneArgLambda != null) {
			oneArgLambda.accept(null);
		} else if (noArgLambda != null) {
			noArgLambda.run();
		}
	}

	@Override
	public String toString() {
		return String.format(
			"Lambda<T>{%s=<java.%s>}",
			noArgLambda == null ? "singleParam" : "noParam",
			noArgLambda == null ? "util.function.Consumer<T>" : "lang.Runnable"
		);
	}
}
