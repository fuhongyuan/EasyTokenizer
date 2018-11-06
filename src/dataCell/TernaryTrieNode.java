package dataCell;

public class TernaryTrieNode {
    //左子结点
    private TernaryTrieNode leftNode;
    //中间子结点
    private TernaryTrieNode middleNode;
    //右子结点
    private TernaryTrieNode rightNode;
    //当前节点字符
    private char pointChar;
    //词条信息，若到此结点不是词则为null
    private WordEntity entity;
    
    public TernaryTrieNode getLeftNode() {
        return leftNode;
    }
    public void setLeftNode(TernaryTrieNode leftNode) {
        this.leftNode = leftNode;
    }
    public TernaryTrieNode getMiddleNode() {
        return middleNode;
    }
    public void setMiddleNode(TernaryTrieNode middleNode) {
        this.middleNode = middleNode;
    }
    public TernaryTrieNode getRightNode() {
        return rightNode;
    }
    public void setRightNode(TernaryTrieNode rightNode) {
        this.rightNode = rightNode;
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