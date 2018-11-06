package dataCell;


public class SentenceToken implements Comparable<SentenceToken>{
    //词在句子从的起点位置
    private int start;
    //词在句子中的终点位置
    private int end;
    //词的信息
    private WordEntity entity;
    //词和前一个词的累积概率
    private double accProb=0.0;
    
    private SentenceToken bestPreWord;
    
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public WordEntity getEntity() {
        return entity;
    }

    public void setEntity(WordEntity entity) {
        this.entity = entity;
    }

    public SentenceToken getBestPreWord() {
        return bestPreWord;
    }

    public void setBestPreWord(SentenceToken bestPreWord) {
        this.bestPreWord = bestPreWord;
    }

    public double getAccProb() {
        return accProb;
    }

    public void setAccProb(double accProb) {
        this.accProb = accProb;
    }

    @Override
    public int compareTo(SentenceToken o) {
        //end在后面的排在前面
        if(this.end<o.getEnd()){
            return 1;
        }
        //start在前面的排在前面
        if(this.start<o.getStart()){
            return 1;
        }
        return 0;
    }
    @Override
    public boolean equals(Object o) {
        try {
            SentenceToken token = (SentenceToken) o;
            // 不能只通过开始结束位置来判断，因为同一个词可能对应不同的词性，这时要看作两个词
            if (this.start == token.getStart() && this.end == token.end
                    && this.entity.equals(token.getEntity())) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    @Override
    public String toString(){
        return "start："+start+"，end："+end+"，"+entity+"，最佳前驱词："+bestPreWord;
    }
}