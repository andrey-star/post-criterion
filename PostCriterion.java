import java.util.Arrays;
import java.util.Scanner;

public class PostCriterion {

	public static boolean isZeroPreserving(int[] f, int n) {
		return f[0] == 0;
	}

	public static boolean isOnePreserving(int[] f, int n) {
		return f[(1 << n) - 1] == 1;
	}

	public static boolean isBigger(int a, int b, int n) {
		for (int i = 0; i < n; i++) {
			if (((a >> i) & 1) > ((b >> i) & 1)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isMonotonous(int[] f, int n) {
		for (int i = 0; i < (1 << n); i++) {
			for (int j = i + 1; j < (1 << n); j++) {
				if (!isBigger(i, j, n)) {
					if (f[i] > f[j]) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static boolean isSelfDual(int[] f, int n) {
		for (int i = 0; i < (1 << n); i++) {
			if (f[i ^ ((1 << n) - 1)] == f[i]) {
				return false;
			}
		}
		return true;
	}

	public static boolean isLinear(int[] f, int n) {
		mark: for (int i = 0; i < (1 << (n + 1)); i++) { // all xor combinations (1 - take arg)
			int[] res = new int[1 << n];
			for (int k = 0; k < (1 << n); k++) { // all inputs
				int ans = 0;
				for (int j = 0; j < n + 1; j++) { // which arguments to take, j = n refers to logical 1
					int cur = ((i >> j) & 1); // taking cur arg or not
					if (cur == 1) {
						if (j < n) {
							ans ^= ((k >> j)) & 1;
						} else {
							ans ^= 1;
						}
					}
				}
				res[k] = ans;
			}

			for (int k = 0; k < (1 << n); k++) {
				if (f[k] != res[k]) {
					continue mark;
				}
			}

			return true;
		}

		return false;
	}

	public static boolean isFull(int[] f, int n) {
		return !(isZeroPreserving(f, n) || isOnePreserving(f, n) || isMonotonous(f, n) || isSelfDual(f, n)
				|| isLinear(f, n));
	}

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		int n = in.nextInt();

		int l = (1 << n);
		int[] f = new int[l];

		for (int i = 0; i < l; i++) {
			f[i] = in.nextInt();
		}

		in.close();

		boolean isFull = isFull(f, n);

		System.out.println("Preserves 0: " + isZeroPreserving(f, n));
		System.out.println("Preserves 1: " + isOnePreserving(f, n));
		System.out.println("Monotonous: " + isMonotonous(f, n));
		System.out.println("Self-dual: " + isSelfDual(f, n));
		System.out.println("Is Linear: " + isLinear(f, n));
		System.out.println("\nIs Full: " + isFull);

		// All full functions

		// int c = 0;
		// for (int i = 0; i < 1 << (1 << n); i++) {
		// int[] table = new int[1 << n];
		// for (int j = 0; j < 1 << n; j++) {
		// table[j] = (i >> j) & 1;
		// }
		//
		// if (isFull(table, n)) {
		// System.out.println(Arrays.toString(table));
		// c++;
		// }
		// }
		// System.out.println(c);

	}

}
