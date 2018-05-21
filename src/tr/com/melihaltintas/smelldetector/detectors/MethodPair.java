package tr.com.melihaltintas.smelldetector.detectors;

public class MethodPair<L, R> {

	private final L left;
	private final R right;

	public MethodPair(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MethodPair))
			return false;
		MethodPair pairo = (MethodPair) o;
		return (this.left.equals(pairo.getLeft()) && this.right.equals(pairo.getRight()))
				|| (this.left.equals(pairo.getRight()) && this.right.equals(pairo.getLeft()));
	}

	@Override
	public String toString() {
		return "Pair : " + left.toString() + "<=>" + right.toString();
	}
}
