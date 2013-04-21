package me.bazhenov.maxent;

import com.google.common.collect.ImmutableList;
import opennlp.maxent.GIS;
import opennlp.maxent.GISModel;
import org.testng.annotations.Test;

import java.io.IOException;

import static me.bazhenov.maxent.PlainTextEventStream.buildContext;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClassifierTest {

	@Test
	public void learnAndVerifyMaxentModel() throws IOException {
		PlainTextEventStream stream = new PlainTextEventStream(ImmutableList.of(
			new LearningSample("SPAM", "предоставляю услуги бухгалтера"),
			new LearningSample("SPAM", "спешите купить виагру"),
			new LearningSample("HAM", "надо купить молоко")
		));

		GISModel model = GIS.trainModel(stream, 100, 1, false, true);
		double[] result = model.eval(buildContext("надо купить сигареты"));
		String bestOutcome = model.getBestOutcome(result);
		assertThat(bestOutcome, is("HAM"));
	}
}
