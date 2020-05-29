package br.helios.simplex.infrastructure.util;

import static java.math.RoundingMode.HALF_UP;

import java.math.MathContext;

public class MathContextUtil {

	public static final MathContext MATH_CONTEXT = new MathContext(16, HALF_UP);
	public static final MathContext MATH_CONTEXT_OUTPUT = new MathContext(5, HALF_UP);
}
