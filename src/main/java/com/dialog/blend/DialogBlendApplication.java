package com.dialog.blend;

import com.dialog.blend.logX.LogLevel;
import com.dialog.blend.logX.LogOperator;
import com.dialog.blend.logX.Logger;
import core_nlp.SentimentAnalyzer.SentimentAnalyzer;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationHome;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Properties;

@SpringBootApplication
public class DialogBlendApplication {

	public static boolean vadState = false;

	public static void main(String[] args) {
		SpringApplication.run(DialogBlendApplication.class, args);
		Logger logger = LogOperator.getLogger();
		ApplicationHome home = new ApplicationHome(DialogBlendApplication.class);

		for (String cmd : args) {
			if (cmd.contains("log.level")) {
				configureLoggingLevel(cmd, logger);
			}
		}

		String sent = "Good Morning, In the bustling city of New York, John Smith, a renowned entrepreneur and philanthropist, founded XYZ Corporation in 2005. The company, headquartered on Wall Street, quickly became a leading player in the technology sector. On September 15, 2022, XYZ Corporation announced a strategic partnership with Global Innovations Inc., a cutting-edge research organization based in Silicon Valley. The collaboration aims to revolutionize artificial intelligence and enhance the capabilities of natural language processing. Driven by a commitment to sustainability, both organizations pledged to launch 'InnoBot,' an eco-friendly AI product, in early 2023. This groundbreaking initiative received accolades from environmental groups like GreenTech Alliance and CleanTech Now. The announcement drew the attention of major media outlets, including The New York Times and CNN, as well as influential personalities such as Elon Musk, CEO of SpaceX and Tesla, who expressed enthusiasm for the project. As anticipation builds, XYZ Corporation and Global Innovations Inc. remain at the forefront of innovation and corporate responsibility.";


		String text = "Good morning! How are you today?";
		Document doc = new Document(text);

// Example: Get the sentences and print the tokens and their part-of-speech tags
		for (Sentence sentence : doc.sentences()) {
			System.out.println("Tokens and POS tags: " + sentence.words() + " " + sentence.posTags()+" "+sentence.nerTags());
		}

		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(sent);
		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				String word = token.get(CoreAnnotations.TextAnnotation.class);
				String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
				String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
				System.out.println("Word: " + word + ", POS: " + pos + ", NER: " + ner);
			}
		}

		String[] sentence = {
				"Yes ,I had very wonderful day today.",
				"This movie is absolutely fantastic!",
				"The weather is perfect for a picnic.",
				"I feel so grateful for all the support I've received.",
				"The food at that restaurant was terrible.",
				"This book is incredibly boring.",
				"I'm really excited about the upcoming vacation.",
				"The customer service was excellent, and I received quick assistance.",
				"I can't stand the traffic during rush hour.",
				"Losing my phone was such a frustrating experience."
		};

		String[] sentiments = {
				"Very positive",
				"Very positive",
				"Positive",
				"Positive",
				"Negative",
				"Negative",
				"Positive",
				"Positive",
				"Negative",
				"Negative"
		};

		SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
		sentimentAnalyzer.initialize();

		// Access the sentences and their sentiments in parallel
		// for (int i = 0; i < sentence.length; i++) {
		//
		// SentimentResult sentimentResult =
		// sentimentAnalyzer.getSentimentResult(sentence[i]);
		// System.out.println("Sentence: " + sentence[i]);
		// System.out.println("Sentiment Score: " +
		// sentimentResult.getSentimentScore());
		// System.out.println("Sentiment Type: " + sentimentResult.getSentimentType());
		// System.out.println("Very positive: " +
		// sentimentResult.getSentimentClass().getVeryPositive() + "%");
		// System.out.println("Positive: " +
		// sentimentResult.getSentimentClass().getPositive() + "%");
		// System.out.println("Neutral: " +
		// sentimentResult.getSentimentClass().getNeutral() + "%");
		// System.out.println("Negative: " +
		// sentimentResult.getSentimentClass().getNegative() + "%");
		// System.out.println("Very negative: " +
		// sentimentResult.getSentimentClass().getVeryNegative() + "%");
		// System.out.println("Expected Result Sentiment: " + sentiments[i]);
		// System.out.println("Expected Result Found: " +
		// sentiments[i].equals(sentimentResult.getSentimentType()));
		//
		// System.out.println();
		// }

	}

	private static void configureLoggingLevel(String cmd, Logger logger) {
		if (cmd.contains("log.level=ALL")) {
			logger.setLogLevel(LogLevel.ALL);
		} else if (cmd.contains("log.level=TRACE")) {
			logger.setLogLevel(LogLevel.TRACE);
		} else if (cmd.contains("log.level=DEBUG")) {
			logger.setLogLevel(LogLevel.DEBUG);
		} else if (cmd.contains("log.level=INFO")) {
			logger.setLogLevel(LogLevel.INFO);
		} else if (cmd.contains("log.level=WARN")) {
			logger.setLogLevel(LogLevel.WARN);
		} else if (cmd.contains("log.level=ERROR")) {
			logger.setLogLevel(LogLevel.ERROR);
		} else if (cmd.contains("log.level=FATAL")) {
			logger.setLogLevel(LogLevel.FATAL);
		} else if (cmd.contains("log.level=OFF")) {
			logger.setLogLevel(LogLevel.OFF);
		}
	}

}