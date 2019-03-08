package baseTool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import constantDic.WordPos;
import dataCell.SentenceEntity;
import dataCell.SentenceToken;
import dataCell.Word;
import dataCell.WordEntity;
import dataStructure.TernarySearchTrie;
//分词器
public class Segmenter {
    private static final double minValue =  -100000000.0;
    private static final double lambda1 = 0.5;  //一元概率权重
    private static final double lambda2 = 0.5;  //二元概率权重
    public static List<WordEntity> splite(String sentence){
        List<WordEntity> wordEntityList = new ArrayList<>();
        SentenceToken indexToken = createSentenceToken(sentence);
        for(;;){
            if(indexToken.getStart()<0){
                break;
            }
            wordEntityList.add(indexToken.getEntity());
            indexToken=indexToken.getBestPreWord();
        }
        Collections.reverse(wordEntityList);
        return wordEntityList;
    }

    public static List<Word> spliteSentence(String sentence){
        List<Word> wordList = new ArrayList<>();
        SentenceToken indexToken = createSentenceToken(sentence);
        while(true){
            if(indexToken.getStart()<0){
                break;
            }
            Word word = new Word();
            word.setWord(indexToken.getEntity().getWord());
            word.setPos(indexToken.getEntity().getPos());
            word.setPosName(WordPos.getPosName(indexToken.getEntity().getPos()));
            wordList.add(word);
            indexToken=indexToken.getBestPreWord();
        }
        Collections.reverse(wordList);
        return wordList;
    }

    public static SentenceToken createSentenceToken(String sentence){
        SentenceEntity entity = TernarySearchTrie.matchAll(sentence);
        List<SentenceToken> tokenList = entity.getSentenceToken();
        SentenceEntity.TokenSet[] tokenSet =entity.getTokenSet();
        int sentenceLength = sentence.length();
        double endNodeProb=minValue;
        SentenceToken endToken=null;
        //算出每个词的最佳前驱词
        for(SentenceToken currentToken:tokenList){
            double candidateMaxProb = minValue;
            if(null == tokenSet[currentToken.getStart()]) {
                continue;
            }
            for(SentenceToken candidateToken:tokenSet[currentToken.getStart()]){
                if(candidateToken==null){
                    continue;
                }
                double prob = transProb(candidateToken, currentToken)+candidateToken.getAccProb();
                if(prob>candidateMaxProb){
                    candidateMaxProb=prob;
                    currentToken.setAccProb(prob);
                    currentToken.setBestPreWord(candidateToken);
                }
            }
            //找出最佳的最后节点
            if(currentToken.getEnd()==sentenceLength){
                if(currentToken.getAccProb()>endNodeProb){
                    endNodeProb=currentToken.getAccProb();
                    endToken=currentToken;
                }
            }
        }
        return endToken;
    }

    // 前后两个词的转移概率
    private static double transProb(SentenceToken candidateToken, SentenceToken currentToken) {
        double biProb;  //二元转移概率
        int preLen = candidateToken.getEntity().getWord().length();
        int nextLen = currentToken.getEntity().getWord().length();
        if (preLen < nextLen) {
            biProb = 0.2;
        } else if (preLen == nextLen) {
            biProb = 0.1;
        } else {
            biProb = 0.0001;
        }

        return lambda1 * candidateToken.getEntity().getLogProb() + lambda2 * Math.log(biProb);
        // return prevWord.logProb;
    }

    
    public static void main(String[] args){
        String sentence2="阿Qb超aa制发布活动评审出18项代表性的领先科技成果，其中独立发布14项，分别是：华为技术有限公司的“华为3GPP 5G预商用系统、Arm公司的“Arm安全架构、微软公司的“微软小冰——情感计算人工智能、中国卫星导航系统管理办公室的“北斗卫星导航系统、美国高通公司的“高通基于其面向移动终端的5G调制解调器芯片组实现全球首个正式发布的5G数据连接、国家超级计算无锡中心的“基于‘神威•太湖之光’超级计算机系统的重大应用成果、中国科学院量子信息与量子科技创新研究院的“世界首台超越早期经典计算机的光量子计算机、特斯拉公司的“特斯拉垂直整合能源解决方案、北京嘀嘀无限科技发展有限公司的“基于大数据的新一代移动出行平台、北京摩拜科技有限公司的“摩拜无桩智能共享单车、阿里巴巴集团的“ET大脑、北京百度网讯科技有限公司的“DuerOS对话式人工智能系统、亚马逊公司的“AWS GreenGrass和苹果公司的“AR Kit。除了以上14项独立成果，组委会还联合发布了入围的4项先进技术，分别是：腾讯公司的“腾讯人工智能开放平台、中电数据服务有限公司和国际商业机器有限公司的“Watson健康助力‘健康中国’、清华大学的“下一代互联网关键技术、美国机器触觉公司的“机器触觉。".trim();
        System.out.println(sentence2);
        List<Word> list2 = spliteSentence(sentence2);
        for(Word entity:list2){
            System.out.println(entity);
        }
        List<WordEntity> list3 = splite(sentence2);
        for(WordEntity entity:list3){
            System.err.println(entity);
        }

    }
}