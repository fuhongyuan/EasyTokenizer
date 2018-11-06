package constantDic;

import java.util.HashMap;
import java.util.Map;

public class WordPos {
    private static Map<String, String> posName = new HashMap<String, String>();
    static {
        posName.put("num", "数字");
        posName.put("en", "英语单词");
        posName.put("a", "形容词");
        posName.put("ad", "副形词");
        posName.put("ag", "形语素");
        posName.put("an", "名形词");
        posName.put("b", "区别词");
        posName.put("c", "连词");
        posName.put("d", "副词");
        posName.put("dg", "副语素");
        posName.put("e", "叹词");
        posName.put("f", "方位词");
        posName.put("g", "语素");
        posName.put("h", "前接成分");
        posName.put("i", "成语");
        posName.put("j", "简称略语");
        posName.put("k", "后接成分");
        posName.put("l", "习用语");
        posName.put("m", "数词");
        posName.put("n", "名词");
        posName.put("ng", "名语素");
        posName.put("nr", "人名");
        posName.put("ns", "地名");
        posName.put("nt", "机构团体");
        posName.put("nx", "字母专名");
        posName.put("nz", "其他专名");
        posName.put("o", "拟声词");
        posName.put("p", "介词");
        posName.put("q", "量词");
        posName.put("r", "代词");
        posName.put("s", "处所词");
        posName.put("t", "时间词");
        posName.put("tg", "时语素");
        posName.put("u", "助词");
        posName.put("ud", "结构助词");
        posName.put("ug", "时态助词");
        posName.put("uj", "结构助词的");
        posName.put("ul", "时态助词了");
        posName.put("uv", "结构助词地");
        posName.put("uz", "时态助词着");
        posName.put("v", "动词");
        posName.put("vd", "副动词");
        posName.put("vg", "动语素");
        posName.put("vn", "名动词");
        posName.put("w", "标点符号");
        posName.put("x", "非语素字");
        posName.put("y", "语气词");
        posName.put("z", "状态词");
        posName.put("unknow", "未知");
    }
    public static String getPosName(String pos){
        if(!posName.containsKey(pos)){
            pos="unknow";
        }
        return posName.get(pos);
    }
}
