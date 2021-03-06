package com.bowen.myDemo;
/**
 * 静态成员是类所有的对象的共享的成员，而不是某个对象的成员
 * 
 *静态代码块和非静态代码块的异同点如下：
    相同点：都是JVM加载类时且在构造函数执行之前执行，在类中都可以定义多个，一般在代码块中对一些static变量进行赋值。
    不同点：静态代码块在非静态代码块之前执行（静态代码块 > 非静态代码块）。静态代码块只在第一次new时执行一次，
                 之后不再执行。而非静态代码块每new一次就执行一次。
    在Java类被new的过程中，执行顺序如下：
    实现自身的静态属性和静态代码块。（根据代码出现的顺序决定谁先执行）
    实现自身的非静态属性和非静态代码块。
    执行自身的构造函数。
   将 private static Test t1 = new Test();注释后输出的的结果：1 2 4 3 
   这个符合：静态代码块 -> 构造代码块 -> 构造方法 的执行顺序
   出现4 2  1 2 4 3 结果的原因是 第一行代码private static Test t1 = new Test();所影响的
  原因：
          （1）虚拟机在首次加载Java类时，会对静态初始化块，即对静态成员变量、静态方法进行一次初始化，在第一行的
        private static Test t1 = new Test();会被初始化
          （2）定义的t1的同时，又new了一个新的对象出来，这个对象是本类对象，new的过程中，首先要执行构造代码块，即输出了"2"
                  原因是非静态代码块每new一次就执行一次 ，而静态代码块在静态成员的后面，其实静态代码和静态成员对象的执行顺序是按照出现
                的先后顺序执行的，  private static Test t1 = new Test()在前，所以先执行，即输出了"2"即执行构造代码块后，
               会执行构造方法，所以此时输出"4"
    (3)静态的成员对象完成后，程序会接着顺序加载与静态相关的变量或者代码块，即初始化静态的代码块中的内容，此刻找到了静态代码块
       static {
          System.out.println("1");
       }
            所以输出了："1"，此代码块在整个程序执行中只输出一次
   (4)程序完成对静态相关的变量或者代码初始化话后，接着按照顺序执行 main里面的方法，
      Test t2 = new Test();
          此时构造代码的优先级高于构造方法， 每new一次就执行一次的构造代码会被再次执行，所以输出："2",
          然后执行优先级低的构造方法，所以输出："4"
  (5)Test t2 = new Test();后执行main中的其他代码，即  System.out.println("3");
         所以输出："3"
   
   注意：如果将private static Test t1 = new Test();放在静态代码块的后面，运行的结果便是心中所想的样子：
   1 2 4  2 4 3
   如果类具有子父级关系的时候，执行顺序：
  1、先初始化父类的静态代码
  2、初始化子类的静态代码
 3、初始化父类的非静态代码
 4、初始化父类构造函数
 5、初始化子类非静态代码
 6、初始化子类构造函数
 * 
*/
public class Test {
	//静态属性
    private static Test t1 = new Test();
    //构造方法 
    public Test() {
        super();
        System.out.println("4");
    }
    //静态代码块
    static {
        System.out.println("1");
    }
    //构造代码块（非静态代码块）
    {
        System.out.println("2");
    }
    
    public static void main(String[] args) {
        Test t2 = new Test();
        System.out.println("3");
    }
}
