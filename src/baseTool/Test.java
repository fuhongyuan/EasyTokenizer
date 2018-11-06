package baseTool;

public class Test {
    public static void main(String[] args){
        int i=1;
        float f=0.1f;
        double d=0.1d;
        for(int j=1;j<8;j++){
            f+=0.1f;
            d+=0.1d;
            i+=1;
        }
        System.err.print(f);
        System.err.print("\r\n");
        System.err.print(d);
        System.err.print("\r\n");
        System.err.print(i);
    }
}
