package dataCell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//存放一个句子在词典里面可以找到的全部词
public class SentenceEntity {
 
    private TokenSet[] tokenSet;
    
    public SentenceEntity(String sentence){
        tokenSet= new TokenSet[sentence.length()+2];         
        WordEntity startentity = new WordEntity();
        startentity.setFre(1);
        startentity.setWord("thisisstartnode");
        SentenceToken startnode = new SentenceToken();
        startnode.setEntity(startentity);
        startnode.setStart(-1);
        startnode.setEnd(0);
        tokenSet[0]= new TokenSet(); 
        tokenSet[0].addToken(startnode);        
        WordEntity endentity = new WordEntity();
        endentity.setFre(1);
        endentity.setWord("thisisendnode");
        SentenceToken endnode = new SentenceToken();
        endnode.setEntity(endentity);
        endnode.setStart(sentence.length());
        endnode.setEnd(sentence.length()+1);
        tokenSet[sentence.length()+1]= new TokenSet();
        tokenSet[sentence.length()+1].addToken(endnode);                
    }
    
    private List<SentenceToken> tokenList = new ArrayList<SentenceToken>();
    //向句子里面加一个词
    public boolean addWord(int start,int end,WordEntity entity) {
        if (!tokenList.contains(entity)) {
            SentenceToken node = new SentenceToken();
            node.setEntity(entity);
            node.setStart(start);
            node.setEnd(end);
            tokenList.add(node);
            if(tokenSet[end] == null){
                TokenSet set = new TokenSet(); 
                tokenSet[end]=set;
            }
            tokenSet[end].addToken(node);
            return true;
        }
        return false;
    }
    //获取句子中在词典里面全部能够找到的词。
    public List<SentenceToken> getSentenceToken() {
        return tokenList;
    }
    public class TokenSet implements Iterable<SentenceToken>{
        int index=0;
        int length=0;
        List<SentenceToken> list = new ArrayList<SentenceToken>();
        public void addToken(SentenceToken token){            
            list.add(token);
            length++;
        }       
        @Override
        public Iterator<SentenceToken> iterator() {
            return new Iterator<SentenceToken>(){
                @Override
                public boolean hasNext() {
                    index++;
                    boolean result=index<=length;
                    if(!result){
                        index=0;  
                    }
                    return result;
                }
                @Override
                public dataCell.SentenceToken next() {
                    return list.get(index-1);
                }
                @Override
                public void remove() {                   
                }                
            };
        }
    }
    public TokenSet[] getTokenSet() {
/*    	for(int i=0;i<tokenSet.length;i++) {
    		if(null == tokenSet[i]) {
    			TokenSet ts = new TokenSet();
    			SentenceToken token = new SentenceToken();
    			ts.addToken(token);
    		}
    	}*/
        return tokenSet;
    } 
}