package br.helios.simplex.infrastructure.util;

import static br.helios.simplex.infrastructure.util.MathContextUtil.MATH_CONTEXT;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class MatrixUtil {

	public static RealMatrix inverse(BigDecimal[][] matrix) {
		RealMatrix realMatrix = createRealMatrix(matrix);
		return MatrixUtils.inverse(realMatrix);
	}

	public static double[][] convert(BigDecimal[][] matrix) {
		double[][] newMatrix = new double[matrix.length][matrix[0].length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				newMatrix[i][j] = matrix[i][j].doubleValue();
			}
		}
		return newMatrix;
	}

	public static BigDecimal[][] convert(RealMatrix matrix) {
		double[][] matrixData = matrix.getData();
		BigDecimal[][] newMatrix = new BigDecimal[matrixData.length][matrixData[0].length];
		for (int i = 0; i < matrixData.length; i++) {
			for (int j = 0; j < matrixData[i].length; j++) {
				newMatrix[i][j] = new BigDecimal(matrixData[i][j], MATH_CONTEXT);
				BigDecimal roundedValue = newMatrix[i][j].setScale(5, BigDecimal.ROUND_DOWN);
				if (roundedValue.compareTo(ZERO) == 0) {
					newMatrix[i][j] = ZERO;
				}

			}
		}
		return newMatrix;
	}

	public static RealMatrix createRealMatrix(BigDecimal[][] matrix) {
		return new Array2DRowRealMatrix(convert(matrix));
	}
}
