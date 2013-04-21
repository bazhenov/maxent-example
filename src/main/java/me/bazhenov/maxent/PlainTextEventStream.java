package me.bazhenov.maxent;

import com.google.common.base.Splitter;
import opennlp.model.Event;
import opennlp.model.EventStream;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class PlainTextEventStream implements EventStream {

	private final Iterator<LearningSample> samples;

	public PlainTextEventStream(Collection<LearningSample> samples) {
		this.samples = samples.iterator();
	}

	@Override
	public Event next() throws IOException {
		return createEvent(samples.next());
	}

	private Event createEvent(LearningSample sample) {
		String[] context = buildContext(sample.getText());
		return new Event(sample.getKlass(), context);
	}

	@Override
	public boolean hasNext() throws IOException {
		return samples.hasNext();
	}

	public static String[] buildContext(String text) {
		List<String> tokens = newArrayList(Splitter.on(' ').split(text));
		return tokens.toArray(new String[tokens.size()]);
	}
}
