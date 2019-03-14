package dataCell;

import java.util.HashMap;

public class TrieNode {
    private HashMap<Character,TrieNode> subNodes;
    private TrieNode faileNode;
    //当前节点字符
    private char pointChar;
    //词条信息，若到此结点不是词则为null
    private WordEntity entity;

    public HashMap<Character, TrieNode> getSubNodes() {
        return subNodes;
    }

    public void setSubNodes(HashMap<Character, TrieNode> subNodes) {
        this.subNodes = subNodes;
    }

    public TrieNode getFaileNode() {
        return faileNode;
    }

    public void setFaileNode(TrieNode faileNode) {
        this.faileNode = faileNode;
    }

    public char getPointChar() {
        return pointChar;
    }

    public void setPointChar(char pointChar) {
        this.pointChar = pointChar;
    }

    public WordEntity getEntity() {
        return entity;
    }

    public void setEntity(WordEntity entity) {
        this.entity = entity;
    }
}
