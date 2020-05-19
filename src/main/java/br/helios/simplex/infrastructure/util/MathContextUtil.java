package br.helios.simplex.infrastructure.util;

import java.math.MathContext;
import java.math.RoundingMode;

public class MathContextUtil {

	public static final MathContext MATH_CONTEXT = new MathContext(16, RoundingMode.HALF_UP);
}
