package dataStructure;

import dataCell.SentenceEntity;
import dataCell.TernaryTrieNode;
import dataCell.WordEntity;
/**
* 基于3叉trie树的词匹配
* */
public class TernarySearchTrie extends Matcher{
    // 根结点
    private static TernaryTrieNode rootNode = null;
    // 增加词
    public void addWord(WordEntity entity) {
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
    //匹配词典中的所有词
    public SentenceEntity matchWord(String sentence,
            SentenceEntity entity) {
        char[] words = sentence.toCharArray();
        int wordLength = words.length;
        int offset = 0;
        while (offset < wordLength) {
            TernaryTrieNode currentNode = rootNode;
            int currentLength = wordLength - offset;
            int index = 0;
            while (true) {
                int diff = words[offset + index] - currentNode.getPointChar();
                if (diff == 0) {
                    index++;
                        if(null == currentNode.getEntity() && index == 1 && !isEnglish(words[offset]) && !isNum(words[offset])) {
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
    public void afterAllWordsAdd(){
        return;
    }
}