package dataStructure;

import dataCell.SentenceEntity;
import dataCell.TrieNode;
import dataCell.WordEntity;
import java.util.*;
/**
 * 基于AC自动机的词匹配
 * */
public class AcAutomation extends Matcher{
    private static TrieNode rootNode;
    //添加词
    @Override
    public void addWord(WordEntity entity){
        if(null ==entity || null == entity.getWord()){
            return;
        }
        String word = entity.getWord();
        if(null == rootNode){
            rootNode = new TrieNode();
        }
        TrieNode currentNode = rootNode;
        for(char c:word.toCharArray()){
            if(null == currentNode.getSubNodes()){
                currentNode.setSubNodes(new HashMap<>());
            }
            if(!currentNode.getSubNodes().containsKey(c)){
                TrieNode node = new TrieNode();
                node.setPointChar(c);
                currentNode.getSubNodes().put(c,node);
            }
            currentNode = currentNode.getSubNodes().get(c);
        }
        currentNode.setEntity(entity);
        wordSum++;
    }

    @Override
    public void afterAllWordsAdd(){
        bfsFailePointProcess(Arrays.asList(rootNode));
    }
    @Override
    public SentenceEntity matchWord(String sentence,SentenceEntity sentenceEntity){
        TrieNode currentNode = rootNode;
        for(int i=0; i<sentence.length();i++){
            char c = sentence.charAt(i);
            TrieNode nextNode = null;
            while(null == currentNode.getSubNodes() || null == currentNode.getSubNodes().get(c)){
                if(currentNode.getFaileNode() == null){
                    break;
                }
                currentNode = currentNode.getFaileNode();
            }
            if(null != currentNode.getSubNodes()){
                nextNode = currentNode.getSubNodes().get(c);
            }
            if(nextNode == null){
                continue;
            }
            if(null !=nextNode.getEntity()){
                sentenceEntity.addWord(i + 1 - nextNode.getEntity().getWord().length(),i+1,nextNode.getEntity());
            }
            currentNode = nextNode;
        }
        return sentenceEntity;
    }
    //广度优先创建失败指针
    private static void bfsFailePointProcess(List<TrieNode> nodes){
        if(nodes.isEmpty()){
            return;
        }
        List<TrieNode> nextNodes = new ArrayList<>();
        nodes.stream().forEach(node -> {
            if(null != node.getSubNodes()) {
                node.getSubNodes().forEach((k, v) -> {
                    nextNodes.add(v);
                    if (node.getFaileNode() == null) {
                        v.setFaileNode(node);
                    } else {
                        TrieNode currentNode = node;
                        while (currentNode.getFaileNode().getSubNodes() == null || !currentNode.getFaileNode().getSubNodes().containsKey(k)) {
                            currentNode = currentNode.getFaileNode();
                            if (null == currentNode.getFaileNode()) {
                                v.setFaileNode(currentNode);
                                break;
                            }
                        }
                        if (null == v.getFaileNode()) {
                            v.setFaileNode(currentNode.getFaileNode().getSubNodes().get(k));
                        }
                    }
                });
            }
        });
        bfsFailePointProcess(nextNodes);
    }
}