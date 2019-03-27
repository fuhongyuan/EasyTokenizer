package dataStructure;

import dataCell.SentenceEntity;
import dataCell.SentenceToken;
import dataCell.WordEntity;
import java.io.*;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Predicate;

public abstract class Matcher {

    protected int wordSum;

    private static String  dicDir = "./dic/";

    private static String binDic = "coreDict.txt";

    public abstract SentenceEntity matchWord(String sentence,SentenceEntity sentenceEntity);

    public abstract void addWord(WordEntity entity);

    public abstract void afterAllWordsAdd();

    public SentenceEntity matchAll(String sentence) {
        SentenceEntity entity = new SentenceEntity(sentence);
        entity.setWordSum(wordSum);
        entity = matchNum(sentence, matchEnglish(sentence, matchWord(sentence, entity)));
        //匹配词典中没有同时也不是英文和数字的单个字及各种符号
        int [] index = new int[sentence.length()];
        List<SentenceToken> tokens = entity.getSentenceToken();
        for(SentenceToken token:tokens){
            int start = token.getStart();
            int end = token.getEnd();
            for(int i=start;i<end;i++){
                index[i]=1;
            }
        }
        for(int i=0;i<sentence.length();i++){
            if(index[i]==0){
                WordEntity word = new WordEntity();
                word.setPos("non");
                String unKnowWord = sentence.substring(i, i+1);
                if(Matcher.isNum(unKnowWord.charAt(0))){
                    word.setPos("num");
                }
                else if(Matcher.isEnglish(unKnowWord.charAt(0))){
                    word.setPos("en");
                }
                word.setFre(0);
                word.setLogProb(0-Math.log(wordSum));
                word.setWord(unKnowWord);
                entity.addWord(i, i+1, word);
            }
        }
        return entity;
    }
    // 切分英语
    private SentenceEntity matchEnglish(String sentence,SentenceEntity entity) {
        return matchPattern(sentence,"en",Matcher::isEnglish,entity);
    }
    // 切分数字
    private SentenceEntity matchNum(String sentence, SentenceEntity entity) {
        return matchPattern(sentence,"num",Matcher::isNum,entity);
    }

    //待优化
    public SentenceEntity matchPattern(String sentence,String pos, Predicate<Character> isPattern, SentenceEntity entity) {
        char[] words = sentence.toCharArray();
        int wordLength = words.length;
        for(int offset = 0;offset<wordLength;offset++){
            int index=0;
            while(offset+index<wordLength&&isPattern.test(words[offset+index])){
                index++;
                WordEntity word = defaultWordEntity();
                word.setPos(pos);
                word.setWord(sentence.substring(offset, offset+index));
                entity.addWord(offset, offset+index, word);
            }
            offset=offset+index;
        }
        return entity;
    }



    private WordEntity defaultWordEntity(){
        WordEntity word = new WordEntity();
        word.setFre(0);
        word.setLogProb(0-Math.log(wordSum));
        return word;
    }

    public void create() {
        File dataFile = new File(dicDir + binDic);
        try {
            InputStream input = new FileInputStream(dataFile);
            BufferedReader read = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            String line;
            try {
                while ( ((line = read.readLine()) != null)) {
                    if("".equals(line))
                        continue;
                    StringTokenizer st = new StringTokenizer(line,":");
                    String key = st.nextToken();
                    String pos = st.nextToken();
                    int freq;
                    try {
                        freq = Integer.parseInt(st.nextToken());
                    } catch (Exception e) {
                        freq = 1;
                    }
                    WordEntity entity = new WordEntity();
                    entity.setFre(freq);
                    entity.setPos(pos);
                    entity.setWord(key);
                    addWord(entity);
                }
                afterAllWordsAdd();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static boolean isEnglish(char c){
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
            return true;
        return false;
    }
    protected static boolean isNum(char c){
        if (c >= '0' && c <= '9')
            return true;
        return false;
    }
}