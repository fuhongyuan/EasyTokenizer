package dataCell;

public class Word {
    
    private String word;
    
    private String pos;
    
    private String posName;
    
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getPos() {
        return pos;
    }
    public void setPos(String pos) {
        this.pos = pos;
    }
    public String getPosName() {
        return posName;
    }
    public void setPosName(String posName) {
        this.posName = posName;
    }
    public String toString(){
        return word+"，"+"词性："+pos+"，"+posName;
    }
}