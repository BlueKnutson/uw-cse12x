public abstract class Animal {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public void sayName() {
        System.out.print(this.name);
    }

    public abstract void speak();
}

    
