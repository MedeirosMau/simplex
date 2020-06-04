package br.helios.simplex.domain.dualproblem;

import static java.lang.String.format;
import static java.math.RoundingMode.HALF_UP;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;

import org.junit.Assert;

public class DualDataTableTestHelper {

	public static void containsData(DualDataTable dataTable, String variable, BigDecimal slackValue, BigDecimal dualValue) {
		DualData dualFromDataTable = dataTable.getDualWithVariableName(variable);
		Assert.assertNotNull(dualFromDataTable);
		assertThat(dualFromDataTable.dualValue.setScale(5, HALF_UP), is(dualValue.setScale(5, HALF_UP)));
		assertThat(dualFromDataTable.slackValue.setScale(5, HALF_UP), is(slackValue.setScale(5, HALF_UP)));
	}

	public static void containsData(DualDataTable dataTable, BigDecimal slackValue, BigDecimal dualValue) {
		boolean hasValues = false;

		BigDecimal expectedDualRounded = dualValue.setScale(5, HALF_UP).abs();
		BigDecimal expectedSlackRounded = slackValue.setScale(5, HALF_UP).abs();

		for (DualData dualData : dataTable.duals) {
			BigDecimal slackDataValue = dualData.slackValue.setScale(5, HALF_UP).abs();
			BigDecimal dualDataValue = dualData.dualValue.setScale(5, HALF_UP).abs();
			if (expectedSlackRounded.compareTo(slackDataValue) == 0 && expectedDualRounded.compareTo(dualDataValue) == 0) {
				hasValues = true;
				break;
			}
		}

		if (!hasValues) {
			Assert.fail(format("Does not contain dual data [slack: %s, dual: %s]", expectedSlackRounded.toString(), expectedDualRounded.toString()));
		}
	}
}
