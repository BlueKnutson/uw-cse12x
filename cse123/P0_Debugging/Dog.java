public class Dog extends Animal {
    public Dog(String name) {
    super(name);
    }
    public void speak() {
        this.sayName();
        System.out.println(" says woof!");

    }
    public void sayName(){
        super.sayName();
    }
    }
    