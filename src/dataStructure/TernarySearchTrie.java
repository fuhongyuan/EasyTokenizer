package dataStructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

import dataCell.SentenceEntity;
import dataCell.SentenceToken;
import dataCell.TernaryTrieNode;
import dataCell.WordEntity;

public class TernarySearchTrie {


      private static TernarySearchTrie ternarySearchTrie;

//      public static synchronized TernarySearchTrie getInstance(){
//        if(null == ternarySearchTrie) {
//            ternarySearchTrie = new TernarySearchTrie();
//        }
//        return ternarySearchTrie;
//      }


    // 根结点
    private static TernaryTrieNode rootNode = null;

    private static int wordSum;
    
    public static int getWordSum() {
        return wordSum;
    }
    private static String  dicDir = "./dic/";
    private static String binDic = "coreDict.txt"; 
    static{
        dicDir = "./dic/";
        binDic = "coreDict.txt"; 
        create();
    }
    // 增加词
    public static void addWord(WordEntity entity) {
        if (null == entity || null == entity.getWord()
                || entity.getWord().isEmpty()) {
            return;
        }
        char[] word = entity.getWord().toCharArray();
        int wordLength = word.length;
        int index = 0;
        // 若不存在根结点，先初始化根结点
        if (null == rootNode) {
            rootNode = new TernaryTrieNode();
            rootNode.setPointChar(word[0]);
        }
        TernaryTrieNode currentNode = rootNode;
        /*
         * 对词中的所有字符进行遍历，根据字符的char值判断，首先判断是否与当前结点相同，
         * 不相同时char值小的放找左结点，如果该标点无左结点，则将该字符放在左结点。将当前结点变为左结点。如果char值大则为向右。
         * 相同时,当前字符已经找到，索引先指向下一个字符。如果当前结点没有中间子结点则将
         * 当当前字符与相前结点相同，同时词中的全部字符已经加入到结点中时，将词的信息保存到当前结点，跳出循环
         */
        while (true) {
            int diff = word[index] - currentNode.getPointChar();
            // 相等时 先把index移动到下一个字符
            // 如果已经取到全部的字符，就把词的信息放到这个当前结点上
            // 否则用当前结点的中间子结点作当前结点，如果中间子结点木有东西，就把当前字符的下一个字符放到子结点里面去
            if (diff == 0) {
                // 先把index移动到下一个字符
                index++;
                // 如果已经取到全部的字符，就把词的信息放到这个当前结点上
                if (index == wordLength) {
                    currentNode.setEntity(entity);
                    wordSum++;
                    break;
                }
                // 用当前结点的中间子结点作当前结点，如果中间子结点木有东西，就把当前子符的下一个字符放到子结点里面去
                if (null == currentNode.getMiddleNode()) {
                    TernaryTrieNode middleNode = new TernaryTrieNode();
                    middleNode.setPointChar(word[index]);
                    currentNode.setMiddleNode(middleNode);
                }
                currentNode = currentNode.getMiddleNode();
            }
            // 将当前结点变为右结点,如果该标点无右结点，则将该字符放在右结点。
            if (diff > 0) {
                if (null == currentNode.getRightNode()) {
                    TernaryTrieNode rightNode = new TernaryTrieNode();
                    rightNode.setPointChar(word[index]);
                    currentNode.setRightNode(rightNode);
                }
                currentNode = currentNode.getRightNode();
            }
            // 将当前结点变为左结点,如果该标点无左结点，则将该字符放在左结点。
            if (diff < 0) {
                if (null == currentNode.getLeftNode()) {
                    TernaryTrieNode leftNode = new TernaryTrieNode();
                    leftNode.setPointChar(word[index]);
                    currentNode.setLeftNode(leftNode);
                }
                currentNode = currentNode.getLeftNode();
            }
        }
    }

    // 查找一个词的信息 如果不存在返回空
    public static WordEntity findWord(String wordString) {
        if (null == wordString || wordString.isEmpty()) {
            return null;
        }
        TernaryTrieNode currentNode = rootNode;
        char[] word = wordString.toCharArray();
        int wordLength = word.length;
        int index = 0;
        while (true) {
            int diff = word[index] - currentNode.getPointChar();
            if (diff == 0) {
                index++;
                if (wordLength == index) {
                    return currentNode.getEntity();
                }
                if (null == currentNode.getMiddleNode()) {
                    return null;
                }
                currentNode = currentNode.getMiddleNode();
            }
            if (diff > 0) {
                if (null == currentNode.getRightNode()) {
                    return null;
                }
                currentNode = currentNode.getRightNode();
            }
            if (diff < 0) {
                if (null == currentNode.getLeftNode()) {
                    return null;
                }
                currentNode = currentNode.getLeftNode();
            }
        }
    }

    // 删除掉指定词，如果有词被删除掉则为true 反之为false
    public static boolean deleteWord(String wordString) {
        if (null == wordString || wordString.isEmpty()) {
            return false;
        }
        TernaryTrieNode currentNode = rootNode;
        char[] word = wordString.toCharArray();
        int wordLength = word.length;
        int index = 0;
        while (true) {
            int diff = word[index] - currentNode.getPointChar();
            if (diff == 0) {
                index++;
                if (wordLength == index) {
                    if (null != currentNode.getEntity()) {
                        currentNode.setEntity(null);
                        return true;
                    }
                    return false;
                }
                if (null == currentNode.getMiddleNode()) {
                    return false;
                }
                currentNode = currentNode.getMiddleNode();
            }
            if (diff > 0) {
                if (null == currentNode.getRightNode()) {
                    return false;
                }
                currentNode = currentNode.getRightNode();
            }
            if (diff < 0) {
                if (null == currentNode.getLeftNode()) {
                    return false;
                }
                currentNode = currentNode.getLeftNode();
            }
        }
    }
    public static SentenceEntity matchWord(String sentence) {
        return matchWord(sentence, new SentenceEntity(sentence));
    }
    public static SentenceEntity matchEnglish(String sentence) {
        return matchEnglish(sentence, new SentenceEntity(sentence));
    }
    public static SentenceEntity matchNum(String sentence) {
        return matchNum(sentence, new SentenceEntity(sentence));
    }
    public static SentenceEntity matchAll(String sentence) {
        SentenceEntity entity = matchNum(sentence, matchEnglish(sentence, matchWord(sentence, new SentenceEntity(sentence))));
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
                word.setFre(0);
                word.setLogProb(0-Math.log(wordSum));
                word.setWord(sentence.substring(i, i+1));
                entity.addWord(i, i+1, word);   
            }
        }
        return entity;
    }
    //匹配词典中的所有词
    public static SentenceEntity matchWord(String sentence,
            SentenceEntity entity) {
        char[] words = sentence.toCharArray();
        int wordLength = words.length;
        int offset = 0;
        while (offset < wordLength) {
            TernaryTrieNode currentNode = rootNode;
            int currentLength = wordLength - offset;
            int index = 0;
            while (true) {
//                if(index == 0 && isEnglish( words[offset + index])) {
//                	WordEntity e = new WordEntity();
//                	e.setFre(0);
//                	e.setLogProb(0-Math.log(wordSum));
//                	e.setPos("");
//                	e.setWord(sentence.substring(offset, offset+1));
//                	entity.addWord(offset, offset + index,
//                            e);
//                	break;
//                }
                int diff = words[offset + index] - currentNode.getPointChar();
                if (diff == 0) {
                    index++;
                        if(null == currentNode.getEntity() && index == 1 && !isEnglish(words[offset]) && !isNum(words[offset])) {
                        //if(null == currentNode.getEntity()&& index == 1) {
                		WordEntity e = new WordEntity();
                    	e.setFre(0);
                    	e.setLogProb(0-Math.log(wordSum));
                    	e.setPos("");
                    	e.setWord(sentence.substring(offset, offset+1));
                    	entity.addWord(offset, offset + index,
                                e);
                	}
                    if (null != currentNode.getEntity()) {
                        currentNode.getEntity().setLogProb(currentNode.getEntity().getLogFre()-Math.log(wordSum));
                        entity.addWord(offset, offset + index,
                                currentNode.getEntity());
                    }
                    if (currentLength == index
                            || null == currentNode.getMiddleNode()) {
                        break;
                    }
                    currentNode = currentNode.getMiddleNode();
                }
                if (diff > 0) {
                    if (null == currentNode.getRightNode()) {
                        break;
                    }
                    currentNode = currentNode.getRightNode();
                }
                if (diff < 0) {
                    if (null == currentNode.getLeftNode()) {
                        break;
                    }
                    currentNode = currentNode.getLeftNode();
                }
            }
            offset++;
        }
        return entity;
    }
    //切分英文
    public static SentenceEntity matchEnglish(String sentence,SentenceEntity entity) {
        char[] words = sentence.toCharArray();
        int wordLength = words.length;
        for(int offset = 0;offset<wordLength;offset++){
           int index=0;                   
           while(offset+index<wordLength&&isEnglish(words[offset+index])){
               index++;
           }
           if(index>0){
               WordEntity word = new WordEntity();
               word.setPos("en");
               word.setFre(0);
               word.setLogProb(0-Math.log(wordSum));
               word.setWord(sentence.substring(offset, offset+index));
               entity.addWord(offset, offset+index, word);
           }
           offset=offset+index;
        }
        return entity;
    }
    //切分数字
    public static SentenceEntity matchNum(String sentence, SentenceEntity entity) {
        char[] words = sentence.toCharArray();
        int wordLength = words.length;
        for(int offset = 0;offset<wordLength;offset++){
           int index=0;                   
           while(offset+index<wordLength&&isNum(words[offset+index])){
               index++;
           }
           if(index>0){
               WordEntity word = new WordEntity();
               word.setPos("num");
               word.setFre(0);
               word.setLogProb(0-Math.log(wordSum));
               word.setWord(sentence.substring(offset, offset+index));
               entity.addWord(offset, offset+index, word);
           }
           offset=offset+index;
        }
        return entity;
    }
    private static boolean isEnglish(char c){
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
            return true;
        return false;
    }
    private static boolean isNum(char c){
        if (c >= '0' && c <= '9') 
            return true;
        return false;
    }
    public static void main(String[] args) {
        create();
       /*WordEntity entity = new WordEntity();
        WordEntity entity2 = new WordEntity();
        WordEntity entity3 = new WordEntity();
        WordEntity entity4 = new WordEntity();
        WordEntity entity5 = new WordEntity();
        WordEntity entity6 = new WordEntity();
        WordEntity entity7 = new WordEntity();
        entity.setWord("苹果");
        entity.setFre(2613);
        entity2.setWord("香蕉");
        entity3.setWord("软工");
        entity4.setWord("工程");
        entity5.setWord("一个人");
        entity5.setPos("一个人是名词哦");
        entity6.setWord("一个");
        entity6.setPos("一个是量词哦");
        entity7.setWord("个人");
        addWord(entity);
        addWord(entity2);
        addWord(entity3);
        addWord(entity4);
        addWord(entity5);
        addWord(entity6);
        addWord(entity7);*/
        /*
         * System.err.println("苹果"+findWord("苹果"));
         * System.err.println("工程"+findWord("工程"));
         * System.err.println("工业"+findWord("工业"));
         * System.err.println("一个"+findWord("一个"));
         * System.err.println("一个人"+findWord("一个人"));
         * System.err.println("一个好人"+findWord("一个好人"));
         * System.err.println("一头猪"+findWord("一头猪")); deleteWord("一个");
         * System.err.println("苹果"+findWord("苹果"));
         * System.err.println("工程"+findWord("工程"));
         * System.err.println("工业"+findWord("工业"));
         * System.err.println("一个"+findWord("一个"));
         * System.err.println("一个人"+findWord("一个人"));
         * System.err.println("一个好人"+findWord("一个好人"));
         * System.err.println("一头猪"+findWord("一头猪"));
         */
        SentenceEntity e = matchAll("态度很好，槑讲解很123全面");
        /*List<SentenceToken> list = e.getSentenceToken();
        for (SentenceToken node : list) {
            System.out.println("词：" + node.getEntity().getWord() + "，起点："
                    + node.getStart() + "，终点：" + node.getEnd());
        }*/
        SentenceEntity.TokenSet[] tokenSet =e.getTokenSet();
        for(SentenceEntity.TokenSet set:tokenSet){
            if(null == set){
                continue;
            }
           String str="";
           for(SentenceToken node:set){
               str +="词：" + node.getEntity().getWord() + "，起点："+ node.getStart() + "，终点：" + node.getEnd()+"，概率："+node.getEntity().getLogProb()+"             ";               
           }
           System.err.println(str);
        }
    }

    
    public static void create() {
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
                    TernarySearchTrie.addWord(entity);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}