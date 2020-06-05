package br.helios.simplex.domain.dualproblem;

import static java.lang.String.format;
import static java.math.RoundingMode.HALF_UP;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;

import org.junit.Assert;

import br.helios.simplex.infrastructure.util.StringUtil;

public class DualDataTableTestHelper {

	public static void containsData(DualDataTable dataTable, String variable, BigDecimal slackValue, BigDecimal dualValue) {
		DualData dualFromDataTable = dataTable.getDualWithVariableName(variable);
		Assert.assertNotNull(dualFromDataTable);
		assertThat(dualFromDataTable.dualValue.setScale(5, HALF_UP), is(dualValue.setScale(5, HALF_UP)));
		assertThat(dualFromDataTable.slackValue.setScale(5, HALF_UP), is(slackValue.setScale(5, HALF_UP)));
	}

	public static void containsData(DualDataTable dataTable, BigDecimal slackValue, BigDecimal dualValue) {
		boolean hasValues = false;

		BigDecimal expectedDualRounded = dualValue.setScale(5, HALF_UP);
		BigDecimal expectedSlackRounded = slackValue.setScale(5, HALF_UP);

		for (DualData dualData : dataTable.duals) {
			BigDecimal slackDataValue = dualData.slackValue.setScale(5, HALF_UP);
			BigDecimal dualDataValue = dualData.dualValue.setScale(5, HALF_UP);
			if (expectedSlackRounded.compareTo(slackDataValue) == 0 && expectedDualRounded.compareTo(dualDataValue) == 0) {
				hasValues = true;
				break;
			}
		}

		if (!hasValues) {
			Assert.fail(format("Does not contain dual data [slack: %s, dual: %s]", expectedSlackRounded.toString(), expectedDualRounded.toString()));
		}
	}

	public static void containsDualPrices(DualDataTable dataTable, BigDecimal... expectedDuals) {
		boolean failed = false;
		String errorMessage = StringUtil.EMPTY;

		// Check real duals

		for (DualData dualData : dataTable.duals) {
			BigDecimal dualDataValue = dualData.dualValue.setScale(5, HALF_UP);

			boolean hasValue = false;
			for (BigDecimal expectedDual : expectedDuals) {
				BigDecimal expectedDualValue = expectedDual.setScale(5, HALF_UP);

				if (dualDataValue.compareTo(expectedDualValue) == 0) {
					hasValue = true;
					break;
				}
			}

			if (!hasValue) {
				failed = true;
				errorMessage += format("Dual not in expected list [dual: %s]\n", dualDataValue.toString());
			}
		}

		// Check expected duals

		for (BigDecimal expectedDual : expectedDuals) {
			BigDecimal expectedDualValue = expectedDual.setScale(5, HALF_UP);

			boolean hasValue = false;
			for (DualData dualData : dataTable.duals) {
				BigDecimal dualDataValue = dualData.dualValue.setScale(5, HALF_UP);

				if (dualDataValue.compareTo(expectedDualValue) == 0) {
					hasValue = true;
					break;
				}
			}

			if (!hasValue) {
				failed = true;
				errorMessage += format("Expected dual does not exist [expected dual: %s]\n", expectedDual.toString());
			}
		}

		if (failed) {
			Assert.fail(errorMessage);
		}
	}
}
