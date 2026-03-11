public class client {
    public static void main(String []args){
        Repository r = new Repository("name");
        Repository r2 = new Repository("name2");
        r.commit("Hello, world!");
        r.commit("Hello, world!2222222222222222222");
        //boolean s = r.contains(r.getRepoHead());
        //boolean p = r.drop("Hello, world!");
        //System.out.println(s);
        //String history = r.getHistory(r.getRepoSize());
        System.out.println(r.toString());
    }
}
