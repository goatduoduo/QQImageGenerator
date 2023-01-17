/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/12/4 20:01
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONPath;
import com.alibaba.fastjson2.TypeReference;

public class TestFastJson {

    static class Person{
        private String id ;
        private String name;
        private int age ;

        public Person(){

        }
        public Person(String id,String name,int age){
            this.id=id;
            this.name=name;
            this.age=age;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
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
        @Override
        public String toString() {
            return "Person [age=" + age + ", id=" + id + ", name=" + name + "]";
        }


    }
    public static void main(String[] args) {
        method1();
        method2();
    }

    static void method1(){
        System.out.println("javabean转化示例开始----------");
        Person person = new Person("1","fastjson",1);

        //这里将javabean转化成json字符串
        String jsonString = JSON.toJSONString(person);
        System.out.println(jsonString);
        //这里将json字符串转化成javabean对象,
        person =JSON.parseObject(jsonString,Person.class);
        System.out.println(person.toString());

        System.out.println("javabean转化示例结束----------");
    }

    static void method2(){
        System.out.println("List<javabean>转化示例开始----------");

        Person person1 = new Person("1","fastjson1",1);
        Person person2 = new Person("2","fastjson2",2);
        List<Person> persons = new ArrayList<Person>();
        persons.add(person1);
        persons.add(person2);
        String jsonString = JSON.toJSONString(persons);
        System.out.println("json字符串:"+jsonString);

        //解析json字符串
        List<Person> persons2 = JSON.parseArray(jsonString,Person.class);
        //输出解析后的person对象，也可以通过调试模式查看persons2的结构
        System.out.println("person1对象："+persons2.get(0).toString());
        System.out.println("person2对象："+persons2.get(1).toString());

        List<Object> result =(List<Object>) JSONPath.eval(persons,"[id = 1001]");
        System.out.println(result);

        System.out.println("List<javabean>转化示例结束----------");
    }
}