package client;

import client.communications.PreProCommunication;
import client.configuration.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig.class);
        PreProCommunication preProCommunication = applicationContext.getBean("preProCommunication", PreProCommunication.class);
        String header = preProCommunication.getHeader();
        System.out.printf("header: %s\n", header);
        String partOne = preProCommunication.getCodePartOne(header);
        System.out.printf("partOne: %s\n", partOne);
        String partTwo = preProCommunication.getCodePartTwo(header);
        String partThree = preProCommunication.getCodePartThree(header);
        System.out.printf("partOne: %s;\npartTwo: %s;\npartThree: %s", partOne, partTwo, partThree);
    }
}
