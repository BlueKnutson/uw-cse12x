public class Cat extends Animal {
    public Cat(String name) {
    super(name);
    }

    //Meow, says Max!
    public void speak() {
        System.out.print("Meow, says ");
        sayName();
        System.out.println("!");


    }
    public void sayName(){
        super.sayName();
    }
    }