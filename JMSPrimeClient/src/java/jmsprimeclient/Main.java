/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmsprimeclient;

import java.util.Scanner;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author sarun
 */
public class Main {
    @Resource(mappedName = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/SimpleJMSQueue")
    private static Queue queue;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection connection = null;
        TextListener listener = null;
                 
        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(
                        false,
                        Session.AUTO_ACKNOWLEDGE);
            listener = new TextListener();

            Queue tempDest = session.createTemporaryQueue();
            MessageConsumer responseConsumer = session.createConsumer(tempDest);
            responseConsumer.setMessageListener(listener);
            
            MessageProducer producer = session.createProducer(queue);
            TextMessage message = session.createTextMessage();
            connection.start();
            
            Scanner in = new Scanner(System.in);
            while(true){
                System.out.println("Enter 2 numbers. Use \',\' to seperate each number. To end the program press enter.");
                String input = in.nextLine();
                if(input.equals("")){
                    break;
                }
                System.out.println("Sending message: " + input);
                message.setText(input);
                message.setJMSReplyTo(tempDest);
                producer.send(message);
            }
            
        } catch (JMSException e) {
            System.err.println("Exception occurred: " + e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                }
            }
        }
    }
}
