public class Client {
    public static void main(String[] args) {
        Peak var1 = new Cliff();   // var1 references a Cliff object
        Gorge var2 = new Gorge();  // var2 references a Gorge object
        Peak var3 = new Hill();    // var3 references a Hill object
        Peak var4 = new Gorge();   // var4 references a Gorge object
        Peak var5 = new Peak();    // var5 references a Peak object
        Object var6 = new Cliff(); // var6 references an Object, but it's actually a Cliff

        ((Gorge)var6).method1(); 
    }

    public class AdmissionsEntry{
        


    }
}
