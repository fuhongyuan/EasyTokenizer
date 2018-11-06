package SetTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

public class SegTest {
public static void main(String[] args) {
	String str = "123,456";
	List<Integer> il = new ArrayList<Integer>();
    CollectionUtils.collect(Arrays.asList(str.split(",")), new Transformer() {
        @Override
        public Object transform(Object arg0) {
            return null;
        }
    }, il);   
    il.stream().forEach(System.err::println);
}
}
