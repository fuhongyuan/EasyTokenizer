package dataStructure;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections.Bag;
import org.apache.commons.collections.bag.HashBag;
import baseTool.Segmenter;
import dataCell.WeightWord;
import dataCell.Word;

public class TfIdfModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int wordSum = 0;

	private Bag wordsCount = new HashBag();

	private int sentenceSum = 0;

	private Bag wordAppearance = new HashBag();

	public void addSentence(String sentence) {
		try {
			List<String> words=sentenceToWords(sentence);
			sentenceSum++;			
			words.stream().forEach(word -> {
				wordSum++;
				wordsCount.add(word);
			});
			words.stream().distinct().forEach(word -> {
				wordAppearance.add(word);
			});
		} catch (Exception e) {
		}
	}


	
	public double tfidfCalculate(String word) {
		double tf = (double) wordsCount.getCount(word) / (double) wordSum;
		double idf = Math.log((double) sentenceSum
				/ ((double) wordAppearance.getCount(word) > 0 ? (double) wordAppearance.getCount(word) : 1));
		return tf * idf;
	}

	public List<WeightWord> tfidfCalculateFromSentence(String sentence) {
		List<WeightWord> list = new ArrayList<WeightWord>();
		List<String> words=sentenceToWords(sentence);
		words.stream().distinct().forEach(word->{WeightWord weightWord = new WeightWord();weightWord.setWeight(tfidfCalculate(word));weightWord.setWord(word);list.add(weightWord);});
		return list;
	}
	
	private List<String> sentenceToWords(String sentence){
		try {
			List<Word> wordEntrys = Segmenter.spliteSentence(sentence);
			return wordEntrys.stream().filter(word->isNotStopWord(word)).map(Word::getWord).collect(Collectors.toList());
		}
		catch(Exception e) {
			return new ArrayList<String>();
		}
	}

	private boolean isNotStopWord(Word word) {
		if(word.getWord().length()<2) {
			return false;
		}
		if(word.getPosName().equals("动词")) {
			return true;
		}
		if(word.getPosName().equals("名词")) {
			return true;
		}
		if(word.getPosName().equals("成语")) {
			return true;
		}	
		return false;
	}
	
	private boolean isStopWord(Word word) {
		if(word.getWord().length()<2) {
			return true;
		}
		if(word.getPosName().equals("数字")) {
			return true;
		}
		if(word.getPosName().equals("代词")) {
			return true;
		}		

		return false;
	}
	public void trainFile(String path) {
		try {
			Files.lines(Paths.get(path), StandardCharsets.UTF_8).forEach(line->addSentence(line));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("训练完成：发现单词"+wordSum+"个,"+"词汇量:"+wordsCount.size()+",样本数量"+sentenceSum+"个");
	}
	public void record(String path) {
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path));
			os.writeObject(this);
			os.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("训练结果保存完毕");
	}
	
	public static TfIdfModel getModel(String path) {
		ObjectInputStream oi;
		try {
			oi = new ObjectInputStream(new FileInputStream(
					path));
			TfIdfModel model = (TfIdfModel) oi.readObject();
	        oi.close();
	        return model;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public String[] printByLimit(String sentence,int limit) {	
		List<WeightWord> wws =tfidfCalculateFromSentence(sentence);
		Optional<String> str = wws.stream().sorted((model1,model2)->(model1.getWeight()<model2.getWeight()?1:-1)).distinct().limit(wws.size()>limit?limit:wws.size()).map(WeightWord::getWord).reduce((w1,w2)->w1+","+w2);
		System.err.println(str.orElse(""));
		return str.orElse("").split(",");
	}

	public static void main(String[] args) {
		String modelPath ="C:\\tfidf1";
		String filePath ="C:\\append.txt";
		Integer limit =15;

/*		{
			TfIdfModel model = new TfIdfModel();
			model.trainFile(filePath);
			model.record(modelPath);	

		}*/
		
/*		{
			TfIdfModel model = TfIdfModel.getModel(modelPath);
			List<WeightWord> wws =model.tfidfCalculateFromSentence("风景如画 不虚此行 完美");
			wws.forEach(System.err::println);
		}*/
		{
			TfIdfModel model = TfIdfModel.getModel(modelPath);

			try {
				List<String> words = new ArrayList<String>();
				Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)
						.forEach(line -> words.addAll(Arrays.asList(model.printByLimit(line, limit))));
				words.stream().distinct().forEach(System.out::println);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}