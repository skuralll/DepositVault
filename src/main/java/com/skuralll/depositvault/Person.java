package com.skuralll.depositvault;

public class Person {
    // フィールド
    // コンストラタでの初期化あり
    private String name;
    private int age;
    private String address;
    //コンストラクタでの初期化なし
    private String height;

    // コンストラクタ
    public Person(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    // ゲッターとセッター
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    // メソッド: 自己紹介
    public String introduce() {
        return "Hi, my name is " + name + ". I am " + age + " years old and live at " + address + ".";
    }

    // メソッド: 年齢を増加させる
    public void incrementAge() {
        this.age++;
    }

    // メソッド: 名前が一致するか確認
    public boolean isSameName(String otherName) {
        return this.name.equals(otherName);
    }

    // メインメソッド（テスト用）
    public static void main(String[] args) {
        Person person = new Person("Alice", 25, "123 Main St");
        System.out.println(person.introduce());
        person.incrementAge();
        System.out.println("New age: " + person.getAge());
        System.out.println("Is the name Alice? " + person.isSameName("Alice"));
    }
}
