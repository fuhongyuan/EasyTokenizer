package dataCell;

public class WordEntity {
    //词文本
    private String word;
    //词性
    private String pos;
    //词出现的次数
    private int fre;
    //词出现次数的对数，预先算好，减少分词时计算词频的复杂度
    private double logFre;
    
    private double logProb;
    
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
    public int getFre() {
        return fre;
    }
    public void setFre(int fre) {
        this.fre = fre;       
        logFre=Math.log((double)(fre==0?1:fre));
    }
    public double getLogFre() {
        return logFre;
    }    
    public double getLogProb() {
        return logProb;
    }
    public void setLogProb(double logProb) {
        this.logProb = logProb;
    }
    @Override
    public boolean equals(Object o){
        try{
            WordEntity entity = (WordEntity) o;
            if(this.word.equals(entity.getWord()) && this.pos.equals(entity.getPos())){
                return true;
            }
            return false;
        }
        catch(Exception e){
          return false;  
        }
    }
    @Override
    public String toString(){
       return "词："+word+"，词频："+logFre+"，词性："+pos ;
    }
}